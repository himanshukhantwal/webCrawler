package pramati.wc.startup;

import pramati.wc.startup.mailCrawler.BasicStartup;

/**
 * this class gives the web crawler implementation which can download mails of particular 
 * year from the URL provided
 * 
 * @author himanshuk
 *
 */
public class WCStartup extends BasicStartup{

	@Override
	protected void runWebCrawler(String[] args) {		
	}
	

	/**
	 * manager will manage all kind of functioning of web Crawler
	 *  1) Perform actual execution with help of WCstartupHelper
	 *  1) deciding the multi-threaded behavior of crawler (create executor framework)
	 *  2) create and start the logging behavior
	 *  
	 * @author himanshuk
	 *
	 */
	private class WCStartupManager extends WCstartupHelper {
		
		@Override
		public void runWebCrawler(String[] args){	
		}
		
	}
	
}
