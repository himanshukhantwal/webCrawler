package pramati.wc.processor;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

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
				this.getMsgUrls();
				
				if(messageList.size()>0)
				createDirForMnth();
				
				Iterator<MessagesDatatype> msgForDwnld=messageList.iterator();
				while(msgForDwnld.hasNext()){
					MessagesDatatype snglMsg=msgForDwnld.next();
					//TODO do this in multi threading
					System.out.println("logging: month is "+month+"subject:+"+snglMsg.getSubjectOfMsg());
					downloadMessageAndSave(snglMsg);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void createDirForMnth() throws Exception {
		String[] tokens=(month.trim()).split(" ");
		WCFileHandler.getInstance().createDir("web_crawler_downloads/"+"Year_"+year+"/Month_"+tokens[0]);
	}

	private void getMsgUrls() throws Exception {
		String msgBody=extractMsgBodyfrmUrl();
		extrctMsgUrlsFrmMsgBody(msgBody);
	}


	private String extractMsgBodyfrmUrl() throws Exception {
		return XmlTagExtractor.getIntance().getXmlWithOnlyPassdStrngTag(urlForMnth,
				WCEnvironment.getInstance().getMsgListTagNameForMonth());
	}
	
	private void extrctMsgUrlsFrmMsgBody(String msgBody) {
		messageList=XmlHyperlinkExtractor.getInstance().getMsgsFrmXmlForMnth(msgBody);
	}

	private void downloadMessageAndSave(MessagesDatatype snglMsg) {
		try {
			URL fullmsgUrl=new URL(urlForMnth, snglMsg.getUrlOfActualMsgTxt());
			String rawMsgText=getMsgTxt(fullmsgUrl);
			
			saveMsgTextInfile(snglMsg,rawMsgText);
		} catch (Exception e) {
			e.printStackTrace();
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
		String changedSubject=((snglMsg.getSubjectOfMsg()).trim().replaceAll("Re: ","")).trim();
		
		//creating dir for each subject
		String dirBySubjct="web_crawler_downloads/"+"Year_"+year+"/Month_"+tokens[0]+"/"+(changedSubject.replaceAll(" ","_")).replaceAll("/", "or");
		WCFileHandler.getInstance().createDir(dirBySubjct);
		
		String fileName=snglMsg.getAuthorOfMsg().replaceAll(" ", "_")+"\\\\"+
							snglMsg.getDateOfMsg().replaceAll(" ", "_");
								
		WCFileHandler.getInstance().createFileAndWriteTxt(fileName,dirBySubjct,textTosave);
	}


}
