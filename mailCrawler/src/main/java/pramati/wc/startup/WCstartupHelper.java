package pramati.wc.startup;

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
	 */
	public void runWebCrawler(String[] args){
		prepareProcess();
	}

	private void prepareProcess() {
		String urlInStringfrmt=null;
		String stringUrlWithYrbody=null;
		extractYearFrmTag(urlInStringfrmt);
		extractMnthHyprLnksFrmXml(stringUrlWithYrbody);
	}

	private void extractMnthHyprLnksFrmXml(String stringUrlWithYrbody) {
		
	}

	private void extractYearFrmTag(String urlInStringfrmt) {
		
	}
	
}
