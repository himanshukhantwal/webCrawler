package pramati.wc.processor;

import java.net.URL;

import org.apache.log4j.Logger;

import pramati.wc.datatypes.MessagesDatatype;
import pramati.wc.utils.URLHelper;
import pramati.wc.utils.WCEnvironment;
import pramati.wc.utils.WCFileHandler;
import pramati.wc.utils.XmlHyperlinkExtractor;
import pramati.wc.utils.XmlTagExtractor;

public class WorkerForMsgDwnld implements Runnable{
	
	private static final Logger log=Logger.getLogger(WorkerForMsgDwnld.class);
	private URL urlForMnth;
	private MessagesDatatype snglMsg;
	private String month;
	private int year;

	public WorkerForMsgDwnld(MessagesDatatype snglMsg, URL urlForMnth, String month, int year) {
		this.urlForMnth=urlForMnth;
		this.snglMsg=snglMsg;
		this.month=month;
		this.year=year;
	}
	public void run() {
		Thread.currentThread().setName(Thread.currentThread().getName()+"-{"+ month+"}");
		log.debug("Message in progress:"+snglMsg);
		downloadMessageAndSave(snglMsg);
	}
	private void downloadMessageAndSave(MessagesDatatype snglMsg) {
		try {
			URL fullmsgUrl=new URL(urlForMnth, snglMsg.getUrlOfActualMsgTxt());
			String rawMsgText=getMsgTxt(fullmsgUrl);
			
			saveMsgTextInfile(snglMsg,rawMsgText);
			addMsgCompletedInRecovery(snglMsg);
		} catch (Exception e) {
			log.error("Problem saving msg"+snglMsg,e);
		}
	}
	
	private String getMsgTxt(URL msgPopUpUrlText) throws Exception {
		URL urlOfRawMsg = null;
		String rawMsgText=null;
		try {
			String msgInHTMLFormat=XmlTagExtractor.getIntance().getXmlWithOnlyPassdStrngTag(msgPopUpUrlText, WCEnvironment.getInstance().getRawMsgTxtTag());
			String hyperLynkOfRawMsg=XmlHyperlinkExtractor.getInstance().getFirstHyperLynk(msgInHTMLFormat);
			urlOfRawMsg=new URL(msgPopUpUrlText,hyperLynkOfRawMsg);	
			rawMsgText=URLHelper.getInstance().getPageContentInTxtFrmt(urlOfRawMsg);
		} catch (Exception e) {
			log.error("ERROR getting text", e);
			throw e;
		}
		return rawMsgText;
	}
	
	private void saveMsgTextInfile(MessagesDatatype snglMsg, String textTosave) throws Exception {
		String[] tokens=(month.trim()).split(" ");
		
		//RE: and simple msg putting them in one folder.
		String changedSubject=((snglMsg.getSubjectOfMsg()).trim().replaceAll("Re: |RE: ","")).trim();
		
		//creating dir for each subject
		String dirBySubjct="web_crawler/downloads/"+"Year_"+year+"/Month_"+tokens[0]+"/"+changedSubject.replaceAll("/", "-or-");
		WCFileHandler.getInstance().createDir(dirBySubjct);
		
		String fileName=snglMsg.getAuthorOfMsg()+"-And-"+
							snglMsg.getDateOfMsg();
								
		WCFileHandler.getInstance().createFileAndWriteTxt(fileName,dirBySubjct,textTosave);
	}
	
	/**
	 * adding msg in recovery folder which will help in failure recovery
	 * @param snglMsg
	 * @throws Exception
	 */
	private void addMsgCompletedInRecovery(MessagesDatatype snglMsg) throws Exception {
		String[] tokens=(month.trim()).split(" ");
		String dirForRecFile = "web_crawler/Recovery/" + "Year_" + year
				+ "/Month_" + tokens[0];
		String recFileName=snglMsg.getSubjectOfMsg() + "-And-" + snglMsg.getAuthorOfMsg() + "-And-"
				+ snglMsg.getDateOfMsg();
		WCFileHandler.getInstance().createFile(recFileName,dirForRecFile);
	}
}
