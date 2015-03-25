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
	Date dateOfMsg;
	URL urlOfActualMsgTxt;
	
	MessagesDatatype(){		
	}
	
	/**
	 * 
	 * @param authorOfMsg
	 * @param subjectOfMsg
	 * @param dateOfMsg
	 * @param urlOfActualMsgTxt
	 */
	MessagesDatatype(String authorOfMsg,String subjectOfMsg,Date dateOfMsg,URL urlOfActualMsgTxt){
		this.authorOfMsg=authorOfMsg;
		this.subjectOfMsg=subjectOfMsg;
		this.dateOfMsg=dateOfMsg;
		this.urlOfActualMsgTxt=urlOfActualMsgTxt;
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
	public Date getDateOfMsg() {
		return dateOfMsg;
	}
	public void setDateOfMsg(Date dateOfMsg) {
		this.dateOfMsg = dateOfMsg;
	}
	public URL getUrlOfActualMsgTxt() {
		return urlOfActualMsgTxt;
	}
	public void setUrlOfActualMsgTxt(URL urlOfActualMsgTxt) {
		this.urlOfActualMsgTxt = urlOfActualMsgTxt;
	}
	
	
	
	
	
}
