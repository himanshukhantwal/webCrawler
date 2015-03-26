package pramati.wc.processor;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import pramati.wc.datatypes.MessagesDatatype;
import pramati.wc.utils.URLHelper;
import pramati.wc.utils.WCEnvironment;
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
	private List<MessagesDatatype> message;
	public WorkerForMonths(URL url,String month){
		this.urlForMnth=url;
		this.month=month;
	}
	
	public void run() {
		try {
				this.getMsgUrls();
				Iterator<MessagesDatatype> msgForDwnld=message.iterator();
				while(msgForDwnld.hasNext()){
					MessagesDatatype snglMsg=msgForDwnld.next();
					//TODO do this in multi threading
					downloadMessageAndSave(snglMsg);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void getMsgUrls() throws Exception {
		String msgBody=extractMsgBodyfrmUrl();
		extrctMsgUrlsFrmMsgBody(msgBody);
	}

	private void extrctMsgUrlsFrmMsgBody(String msgBody) {
		message=XmlHyperlinkExtractor.getInstance().getMsgsFrmXmlForMnth(msgBody);
	}

	private void downloadMessageAndSave(MessagesDatatype snglMsg) {
		try {
			URL fullmsgUrl=URLHelper.getInstance().getFullUrlFromHyperLink(urlForMnth.getPath(),snglMsg.getUrlOfActualMsgTxt());
			String textTosave=URLHelper.getInstance().getPageContentInTxtFrmt(fullmsgUrl);
			saveMsgTextInfile(textTosave);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveMsgTextInfile(String textTosave) {
		
	}

	private String extractMsgBodyfrmUrl() throws Exception {
		return XmlTagExtractor.getIntance().getXmlWithOnlyPassdStrngTag(urlForMnth,
				WCEnvironment.getInstance().getMsgListTagNameForMonth());
	}

}
