package pramati.wc.processor;

import java.net.URL;

/**
 * responsible for processing a month in year message list.
 * multiple instances of this class are passed to executor framework to complete
 * downloading of messages in parallel for months.
 * 
 * @author himanshuk
 *
 */
public class WorkerForMonths implements Runnable {
	
	URL urlForMnth;
	String month;
	WorkerForMonths(URL url,String month){
		this.urlForMnth=url;
		this.month=month;
	}
	
	public void run() {
		
	}

}
