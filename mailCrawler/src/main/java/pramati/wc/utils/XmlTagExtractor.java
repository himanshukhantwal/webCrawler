package pramati.wc.utils;

import java.net.URL;

/**
 * singleton class
 * 
 * purpose:-utility class used to extract a particular named tag from the xml 
 * @author himanshuk
 *
 */
public class XmlTagExtractor {
	private static XmlTagExtractor instance;
	private XmlTagExtractor(){}
	
	public static XmlTagExtractor getIntance() {
		if(instance!=null)
			return instance;
		else
			return instance=new XmlTagExtractor();
	}

	public String getXmlWithOnlyPassdStrngTag(URL url, String strToBeInspctd) throws Exception {
		return getXmlWithOnlyPassdStrngTag(URLHelper.getInstance().getPageContentInTxtFrmt(url),strToBeInspctd);
	}

	public String getXmlWithOnlyPassdStrngTag(String pageContentInTxtFrmt,String strToBeInspctd) {
		
		int index=0;
		int begIndex=0,endIndex=0;
		if((index=pageContentInTxtFrmt.indexOf(strToBeInspctd, index))!=-1){
			begIndex=pageContentInTxtFrmt.indexOf('>',index);
			index=pageContentInTxtFrmt.indexOf(WCEnvironment.getInstance().getEndingTagForAtableEnd(),begIndex);
			endIndex=index;
		}
		return pageContentInTxtFrmt.substring(begIndex+1, endIndex);				
	}
	
}
