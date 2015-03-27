package pramati.wc.startup;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import pramati.wc.datatypes.MonthAndLinkDatatype;
import pramati.wc.utils.WCEnvironment;
import pramati.wc.utils.XmlHyperlinkExtractor;
import pramati.wc.utils.XmlTagExtractor;

/**
 * single threaded class which helps in basic functioning of web crawler used for downloading mails.
 * this must be extended by startup class in order to use the features present in helper class
 * @author himanshuk
 *
 */
public class WCstartupHelper {
	/**
	 *provide steps to crawl 
	 * @param args
	 * @throws Exception 
	 */
	
	protected URL url=null;
	protected String stringUrl=null;
	protected int yrNeedsToBeInspctd;
	protected List<MonthAndLinkDatatype> mnthAndLink;
	
	public void runWebCrawler(String[] args) throws Exception{
		this.stringUrl=args[1];
		this.validateAndGetTxtfrmUrl(args[1]);
		this.yrNeedsToBeInspctd=Integer.parseInt(args[2]);
		prepareProcess();
	}

	protected void prepareProcess() throws Exception {
		String stringUrlWithYrbody=null;
		stringUrlWithYrbody=extractYrTagFrmUrlCntent();
		extractMnthHyprLnksFrmXml(stringUrlWithYrbody);		
	}
	private void validateAndGetTxtfrmUrl(String urlString) throws Exception {
		try {
			this.url = new URL(urlString);
		} catch (MalformedURLException e) {
			throw new Exception("URL_NOT_PROPER");
		}
		
	}
	
	private String extractYrTagFrmUrlCntent() throws Exception {
		String tagName=WCEnvironment.getInstance().getYearTagNameBegStrng()+" "+String.valueOf(this.yrNeedsToBeInspctd);
		return XmlTagExtractor.getIntance().getXmlWithOnlyPassdStrngTag(this.url,tagName);
	}
	
	private void extractMnthHyprLnksFrmXml(String stringUrlWithYrbody) {
		this.mnthAndLink=XmlHyperlinkExtractor.getInstance().getHyprLynkFrmXmlForYear(stringUrlWithYrbody);
	}
	
}
