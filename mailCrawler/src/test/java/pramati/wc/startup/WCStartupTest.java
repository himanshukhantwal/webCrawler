package pramati.wc.startup;

import static org.junit.Assert.*;

import java.net.MalformedURLException;

import org.junit.Before;
import org.junit.Test;

public class WCStartupTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRunWebCrawler() {
		WCStartup wcStartup=new WCStartup();
		try {
			wcStartup.runWebCrawler(new String[3]);
		} catch (Exception e) {
				assertTrue(e instanceof MalformedURLException);
		}
	}

}
