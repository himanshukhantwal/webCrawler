package pramati.wc.datatypes;

import java.net.URL;
import java.util.Date;

/**
 * this datatype is used to read complete msg from message list of a particular month.
 * 
 * @author himanshuk
 *
 */
public class MessagesDatatype {
	String authorOfMsg;
	String subjectOfMsg;
	String dateOfMsg;
	String strUrlOfActualMsgTxt;
	int msgId;
	MessagesDatatype(){		
	}
	
	/**
	 * 
	 * @param authorOfMsg
	 * @param subjectOfMsg
	 * @param dateOfMsg
	 * @param strUrlOfActualMsgTxt
	 */
	public MessagesDatatype(String authorOfMsg,String subjectOfMsg,String dateOfMsg,
			String strUrlOfActualMsgTxt,int msgId){
		this.authorOfMsg=authorOfMsg;
		this.subjectOfMsg=subjectOfMsg;
		this.dateOfMsg=dateOfMsg;
		this.strUrlOfActualMsgTxt=strUrlOfActualMsgTxt;
		this.msgId=msgId;
	}
	
	
	public String getAuthorOfMsg() {
		return authorOfMsg;
	}
	public void setAuthorOfMsg(String authorOfMsg) {
		this.authorOfMsg = authorOfMsg;
	}
	public String getSubjectOfMsg() {
		return subjectOfMsg;
	}
	public void setSubjectOfMsg(String subjectOfMsg) {
		this.subjectOfMsg = subjectOfMsg;
	}
	public String getDateOfMsg() {
		return dateOfMsg;
	}
	public void setDateOfMsg(String dateOfMsg) {
		this.dateOfMsg = dateOfMsg;
	}
	public String getUrlOfActualMsgTxt() {
		return strUrlOfActualMsgTxt;
	}
	public void setUrlOfActualMsgTxt(String urlOfActualMsgTxt) {
		this.strUrlOfActualMsgTxt = urlOfActualMsgTxt;
	}
	public int getMsgId() {
		return msgId;
	}
	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}	
	
	
	
	
}
