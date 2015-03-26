package pramati.wc.startup.mailCrawler;

/**
 * this class is the starting point of the crawler.
 *
 */
 public class BasicStartup {
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
    	BasicStartup startup;
    	args[0]="pramati.wc.startup.WCStartup.class";
    	args[1]="http://mail-archives.apache.org/mod_mbox/maven-users/";
    	args[2]="2015";
    	if(args.length<3)
    		throw new Exception("INVALID_NO_ARGUMENTS");
    	
    	startup=(BasicStartup) Class.forName(args[0]).newInstance();
    	System.arraycopy(args,1, args, 0,args.length-1);
    	
    	startup.runWebCrawler(args);
    	
    }
    
    /**
     * this method provides the kick start to the crawler and this must be implemented by each 
     * web crawler
     * @throws Exception 
     */
 protected void runWebCrawler(String[] args) throws Exception{};
}
