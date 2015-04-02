package pramati.wc.processor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import pramati.wc.datatypes.MessagesDatatype;
import pramati.wc.recovery.FailureRecovery;
import pramati.wc.utils.WCEnvironment;
import pramati.wc.utils.WCFileHandler;
import pramati.wc.utils.XmlHyperlinkExtractor;
import pramati.wc.utils.XmlTagExtractor;

/**
 * responsible for processing a month in year message list.
 * multiple instances of this class are passed to executor framework to complete
 * downloading of messages in parallel for months.
 * 
 * @author himanshuk
 *
 */
public class WorkerForMonths implements Runnable {
	private static final Logger log=Logger.getLogger(WorkerForMonths.class);
	private URL urlForMnth;
	private String month;
	private int year;
	private List<MessagesDatatype> messageList;
	private ExecutorService executor;
	public WorkerForMonths(URL url,String month,int year){
		this.urlForMnth=url;
		this.month=month;
		this.year=year;
	}
	
	public void run() {
		try {
			Thread.currentThread().setName("Thread-" + month);
			int noOfThreads = WCEnvironment.getInstance()
					.getNoOfMsgDownloaderThread();
			executor = Executors.newFixedThreadPool(noOfThreads);
			messageList = getMsgUrls(this.urlForMnth);
			Set<String> allPagesHyperLynk = XmlHyperlinkExtractor.getInstance()
					.getPaginationHyperLynk(urlForMnth);
			this.addMsgUrlFromAllPages(allPagesHyperLynk);
			this.skipAlreadyDownloadedMsgs();
			
			log.info("No of Messages in month {" + month + "} is :"
					+ messageList.size());
			
			if (messageList.size() > 0)
				createDirForMnth();

			
			Iterator<MessagesDatatype> msgForDwnld = messageList.iterator();
			MessagesDatatype snglMsg = null;
			while (msgForDwnld.hasNext()) {
				snglMsg = msgForDwnld.next();
				executor.execute(new WorkerForMsgDwnld(snglMsg, urlForMnth,
						month, year));
			}
			finishMnthMsgDwnld();
		} catch (Exception e) {
			log.error("failed download msg for month {"+month+"}",e);
		}
		
	}
	
	private void finishMnthMsgDwnld() {
		executor.shutdown();
		try {
			executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method helps in handling failure recovery
	 */
	private void skipAlreadyDownloadedMsgs() {
		List<MessagesDatatype> recoveryMapList=FailureRecovery.getInstance().getRecoveryMap(month);
		if(recoveryMapList!=null && recoveryMapList.size()>0)
		this.messageList.removeAll(recoveryMapList);
	}

	private void addMsgUrlFromAllPages(Set<String> allPagesHyperLynk) throws Exception {
		for(String s:allPagesHyperLynk){
			URL url=null;
			try {
				url = new URL(urlForMnth, s);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			List<MessagesDatatype> msgListFrmUrl = getMsgUrls(url);
			messageList.addAll(msgListFrmUrl);
		}
	}

	private void createDirForMnth() throws Exception {
		String[] tokens=(month.trim()).split(" ");
		WCFileHandler.getInstance().createDir("web_crawler/downloads/"+"Year_"+year+"/Month_"+tokens[0]);
		WCFileHandler.getInstance().createDir("web_crawler/Recovery/"+"Year_"+year+"/Month_"+tokens[0]);
	}

	private List<MessagesDatatype> getMsgUrls(URL url) throws Exception {
		String msgBody=extractMsgBodyfrmUrl(url);
		return extrctMsgUrlsFrmMsgBody(msgBody);
	}


	private String extractMsgBodyfrmUrl(URL url) throws Exception {
		return XmlTagExtractor.getIntance().getXmlWithOnlyPassdStrngTag(url,
				WCEnvironment.getInstance().getMsgListTagNameForMonth());
	}
	
	private List<MessagesDatatype> extrctMsgUrlsFrmMsgBody(String msgBody) {
		return XmlHyperlinkExtractor.getInstance().getMsgsFrmXmlForMnth(msgBody);
	}


}
