package pramati.wc.startup.mailCrawler;

import org.apache.log4j.Logger;



/**
 * this class is the starting point of the crawler.
 *
 */
 abstract public class BasicStartup {
	 private static final Logger log=Logger.getLogger(BasicStartup.class);
	/**
	 * processing start from main method. 
	 * then it decide based on input which implementation to use for crawling(i.e. which class to use) 
	 * @param args
	 * 
	 * args[0] -- which implementation to use
	 * args[1] -- URL on which crawling will be done
	 * args[2] -- "year" on which you want to perform crawling.
	 * @throws Exception 
	 */
    public static void main( String[] args ) throws Exception{
    	Thread.currentThread().setName("Thread-main");
    	
    	args=new String[3];
    	args[0]="pramati.wc.startup.WCStartup";
    	args[1]="http://mail-archives.apache.org/mod_mbox/maven-users/";
    	args[2]="2015";
    	
    	BasicStartup startup;
    	if(args.length<3)
    		throw new Exception("INVALID_NO_ARGUMENTS");
    	
    	startup=(BasicStartup) Class.forName(args[0]).newInstance();
    	
    	log.info("Crawler Implementation Name: " +args[0]);
    	log.info("URL to which crawling will be done: "+ args[1]);
    	log.info("YEAR is: "+args[2]);
    	startup.runWebCrawler(args);
    	
    }
    
    /**
     * this method provides the kick start to the crawler and this must be implemented by each 
     * web crawler
     * @throws Exception 
     */
 abstract protected void runWebCrawler(String[] args) throws Exception;
}
