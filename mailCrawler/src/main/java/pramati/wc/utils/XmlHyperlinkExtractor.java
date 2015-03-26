package pramati.wc.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.w3c.dom.ls.LSInput;

import pramati.wc.datatypes.MessagesDatatype;
import pramati.wc.datatypes.MonthAndLinkDatatype;

/**
 * singleton class
 * 
 * purpose:- provide the capability to inspect a xml and return all the hyperlinks present in it.   
 * @author himanshuk
 *
 */
public class XmlHyperlinkExtractor {
	private static XmlHyperlinkExtractor instance;
	private XmlHyperlinkExtractor(){}
	
	public static XmlHyperlinkExtractor getInstance() {
		if(instance!=null)
			return instance;
		else
			return instance=new XmlHyperlinkExtractor();
	}

	public List<MonthAndLinkDatatype> getHyprLynkFrmXmlForYear(String stringUrlWithYrbody) {
		List<MonthAndLinkDatatype> list=new ArrayList<MonthAndLinkDatatype>();
		String rawPage=stringUrlWithYrbody;
		int index=0;
			
			while ((index=stringUrlWithYrbody.indexOf("class=\"date\""))!=-1){
				String dateMnthYear=getNextTagTitle(index,stringUrlWithYrbody);
				
				if((index = stringUrlWithYrbody.indexOf("<a ", index)) == -1) break;
			    if ((index = stringUrlWithYrbody.indexOf("href", index)) == -1) break;
			    if ((index = stringUrlWithYrbody.indexOf("=", index)) == -1) break;
			    String remaining = rawPage.substring(++index);
			    StringTokenizer st 
					= new StringTokenizer(remaining, "\t\n\r\"'>#");
			    String strLink = st.nextToken();
			    MonthAndLinkDatatype monthAndLinkDatatype=new MonthAndLinkDatatype(dateMnthYear, strLink);
			    list.add(monthAndLinkDatatype);
			}		
		return list;
	}

	public String getNextTagTitle(int index, String stringUrlWithYrbody) {
		String remaining = stringUrlWithYrbody.substring(++index);
		StringTokenizer st 
		= new StringTokenizer(remaining, "><");
		return st.nextToken();
	}

	public List<MessagesDatatype> getMsgsFrmXmlForMnth(String msgBody) {
		List<MessagesDatatype> list=new ArrayList<MessagesDatatype>();
		String rawPage=msgBody;
		int index=0;
		
		while ((index=msgBody.indexOf("id=\"msg-"))!=-1){
			int msgId=getMsgId(msgBody,index);
			
			if((index = msgBody.indexOf("class=\"author\"", index)) == -1) break;
			String authorOfMsg=getNextTagTitle(index,msgBody);
			
			if((index = msgBody.indexOf("<a ", index)) == -1) break;
		    if ((index = msgBody.indexOf("href", index)) == -1) break;
		    if ((index = msgBody.indexOf("=", index)) == -1) break;
		    String remaining = rawPage.substring(++index);
		    StringTokenizer st 
				= new StringTokenizer(remaining, "\t\n\r\"'>#");
		    String strLink = st.nextToken();
		    
		    if ((index = msgBody.indexOf(">", index)) == -1) break;
		    String subjectOfMsg=getNextTagTitle(index,msgBody);
		    if ((index = msgBody.indexOf("class=\"date\"", index)) == -1) break;
		    String dateOfMsg=getNextTagTitle(index,msgBody);
		    MessagesDatatype msg=new MessagesDatatype(authorOfMsg, subjectOfMsg, dateOfMsg, strLink,msgId);
		    list.add(msg);
		}		
	return list;
	}

	private int getMsgId(String msgBody, int index) {
		int begIndex=msgBody.indexOf("-",index);
		int endIndex=msgBody.indexOf("\"",begIndex);
		String str=msgBody.substring(begIndex, endIndex);
		return Integer.parseInt(str);
		
	}
	
	

}
