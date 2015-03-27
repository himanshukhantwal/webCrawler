package pramati.wc.utils;

/**
 * singleton class
 * 
 * purpose:- Act as interface between environment and web crawler JVM
 * by reading all the property files for Web Crawler
 * @author himanshuk
 *
 */
public class WCEnvironment {
	private static WCEnvironment instance;
	private static final String WC_NUMBER_OF_THREADS="pramati.wc.numberOfThreadsConfigured";
	
	private WCEnvironment(){
	}
	
	/**
	 * 
	 * @return
	 */
	public static WCEnvironment getInstance(){
		if(instance!=null)
			return instance;
		else
			return instance=new WCEnvironment();
	}

	public int getNoOfThreadsForWebCrawler() {
		// TODO add logic for retrieving value from property file
		return 4;
	}

	public String getYearTagNameBegStrng() {
		return "Year";
	}

	public String getEndingTagForAtableEnd() {
		return "</table>";
	}

	public String getMsgListTagNameForMonth() {
		return "msglist\">";
	}

	public String getRawMsgTxtTag() {
		return "class=\"raw\"";
	}
	
	

}
