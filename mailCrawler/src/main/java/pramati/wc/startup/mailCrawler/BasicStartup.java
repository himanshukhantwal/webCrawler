package pramati.wc.startup.mailCrawler;

/**
 * this class is the starting point of the crawler.
 *
 */
abstract public class BasicStartup {
	/**
	 * processing start from main method. 
	 * then it decide based on input which implementation to use for crawling(i.e. which class to use) 
	 * @param args
	 * 
	 * args[0] -- which implementation to use
	 * args[1] -- URL on which crawling will be done
	 * args[2] -- "year" on which you want to perform crawling.
	 */
    public static void main( String[] args ){
    }
    
    /**
     * this method provides the kick start to the crawler and this must be implemented by each 
     * web crawler
     */
    abstract protected void runWebCrawler(String[] args);
}
