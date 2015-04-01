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
			String strUrlOfActualMsgTxt){
		this.authorOfMsg=authorOfMsg;
		this.subjectOfMsg=subjectOfMsg;
		this.dateOfMsg=dateOfMsg;
		this.strUrlOfActualMsgTxt=strUrlOfActualMsgTxt;
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
	
	@Override
	public String toString() {
		return "\nAuthor is:"+authorOfMsg+"\nsubject is:"+subjectOfMsg+"\ndate of message is: "+dateOfMsg;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MessagesDatatype){
			MessagesDatatype messagesDatatype=(MessagesDatatype) obj;
			return this.authorOfMsg.equals(messagesDatatype.authorOfMsg)
					&& this.dateOfMsg.equals(messagesDatatype.dateOfMsg)
						&& this.subjectOfMsg.equals(messagesDatatype.subjectOfMsg);
		}else
			return false;
	}
	
	
}
