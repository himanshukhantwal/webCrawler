package pramati.wc.startup;

import static org.junit.Assert.*;

import java.net.MalformedURLException;

import org.junit.Before;
import org.junit.Test;

public class WCstartupHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRunWebCrawler() {
		WCstartupHelper wcStartupHelper=new WCstartupHelper();
		try {
			wcStartupHelper.runWebCrawler(new String[3]);
		} catch (Exception e) {
			assertTrue(e instanceof MalformedURLException);
		}
	}

	@Test
	public void testPrepareProcess() {
		
	}

}
