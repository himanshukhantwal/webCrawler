package pramati.wc.processor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import pramati.wc.datatypes.MessagesDatatype;
import pramati.wc.utils.URLHelper;
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
	public WorkerForMonths(URL url,String month,int year){
		this.urlForMnth=url;
		this.month=month;
		this.year=year;
	}
	
	public void run() {
		try {
				Thread.currentThread().setName("Thread-"+month);
				
				messageList=getMsgUrls(this.urlForMnth);
				Set<String> allPagesHyperLynk=XmlHyperlinkExtractor.getInstance().getPaginationHyperLynk(urlForMnth);
				this.addMsgUrlFromAllPages(allPagesHyperLynk);	
				log.info("No of Messages in month {"+month+"} is :"+messageList.size());
				if(messageList.size()>0)
				createDirForMnth();
				
				Iterator<MessagesDatatype> msgForDwnld=messageList.iterator();
				MessagesDatatype snglMsg=null;
				while(msgForDwnld.hasNext()){
					snglMsg=msgForDwnld.next();
					//TODO do this in multi threading
					log.debug("Message in progress:"+snglMsg);
					downloadMessageAndSave(snglMsg);
				}
		} catch (Exception e) {
			log.error("failed download msg for month {"+month+"}",e);
		}
		
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

	private void downloadMessageAndSave(MessagesDatatype snglMsg) {
		try {
			URL fullmsgUrl=new URL(urlForMnth, snglMsg.getUrlOfActualMsgTxt());
			String rawMsgText=getMsgTxt(fullmsgUrl);
			
			saveMsgTextInfile(snglMsg,rawMsgText);
		} catch (Exception e) {
			log.error("Problem saving msg"+snglMsg,e);
		}
	}

	private String getMsgTxt(URL msgPopUpUrlText) {
		URL urlOfRawMsg = null;
		String rawMsgText=null;
		try {
			String msgInHTMLFormat=XmlTagExtractor.getIntance().getXmlWithOnlyPassdStrngTag(msgPopUpUrlText, WCEnvironment.getInstance().getRawMsgTxtTag());
			String hyperLynkOfRawMsg=XmlHyperlinkExtractor.getInstance().getFirstHyperLynk(msgInHTMLFormat);
			urlOfRawMsg=new URL(msgPopUpUrlText,hyperLynkOfRawMsg);	
			rawMsgText=URLHelper.getInstance().getPageContentInTxtFrmt(urlOfRawMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawMsgText;
	}

	private void saveMsgTextInfile(MessagesDatatype snglMsg, String textTosave) throws Exception {
		String[] tokens=(month.trim()).split(" ");
		
		//RE: and simple msg putting them in one folder.
		String changedSubject=((snglMsg.getSubjectOfMsg()).trim().replaceAll("Re: |RE: ","")).trim();
		
		//creating dir for each subject
		String dirBySubjct="web_crawler/downloads/"+"Year_"+year+"/Month_"+tokens[0]+"/"+(changedSubject.replaceAll(" ","_")).replaceAll("/", "or");
		WCFileHandler.getInstance().createDir(dirBySubjct);
		
		String fileName=snglMsg.getAuthorOfMsg().replaceAll(" ", "_")+"\\\\"+
							snglMsg.getDateOfMsg().replaceAll(" ", "_");
								
		WCFileHandler.getInstance().createFileAndWriteTxt(fileName,dirBySubjct,textTosave);
	}


}
