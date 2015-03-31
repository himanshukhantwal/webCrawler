package pramati.wc.utils;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pramati.wc.datatypes.MessagesDatatype;
import pramati.wc.datatypes.MonthAndLinkDatatype;

public class XmlHyperlinkExtractorTest {
	XmlHyperlinkExtractor xmlHyperlinkExtractor;
	String strWithMnthHyperlynk;
	String strWithMsgUrls;
	@Before
	public void setUp() throws Exception {
		xmlHyperlinkExtractor=XmlHyperlinkExtractor.getInstance();
		strWithMnthHyperlynk="<td class=\"date\">some Date 1992</td>\n<td class=\"links\"><span class=\"links\" id=\"201503\"><a href=\"201503.mbox/thread\">Thread</a> &middot; <a href=\"201503.mbox/date\">Date</a> &middot; <a href=\"201503.mbox/author\">Author</a></span></td>";
		strWithMsgUrls="<tr>\n		    <td class=\"author\">Philipp Kraus</td>\n		     <td class=\"subject\"><a href=\"%3c383A5304-FEA1-4032-B712-F771525B3EF6@tu-clausthal.de%3e\">write developers &amp; contributors into Jar</a>     </td>\n		    <td class=\"date\">Sun, 01 Mar, 11:41</td>\n		   </tr>";
		
	}

	@Test
	public void testGetHyprLynkFrmXmlForYear() {
		MonthAndLinkDatatype expected=new MonthAndLinkDatatype("some Date 1992", "201503.mbox/thread");
		List<MonthAndLinkDatatype> actualList=xmlHyperlinkExtractor.getHyprLynkFrmXmlForYear(strWithMnthHyperlynk);
		if(actualList.size()!=1){
			fail("should contain only month link");
		}
		assertEquals(expected, actualList.get(0));		
	}

	@Test
	public void testGetNextTagTitle() {
		String actual=xmlHyperlinkExtractor.getNextTagTitle(strWithMnthHyperlynk.lastIndexOf("class=\"date\""), strWithMnthHyperlynk);
		String expected="some Date 1992";
		assertEquals(expected, actual);
	}

	@Test
	public void testGetMsgsFrmXmlForMnth() {
		MessagesDatatype expected=new MessagesDatatype("Philipp Kraus", "write developers &amp; contributors into Jar","Sun, 01 Mar, 11:41", "%3c383A5304-FEA1-4032-B712-F771525B3EF6@tu-clausthal.de%3e");
		List<MessagesDatatype> actualList = xmlHyperlinkExtractor.getMsgsFrmXmlForMnth(strWithMsgUrls);
		if(actualList.size()!=1)
			fail("should contain only msg link");
		assertEquals(expected, actualList.get(0));
		
	}

	@Test
	public void testGetFirstHyperLynk() {
		String actual="%3c383A5304-FEA1-4032-B712-F771525B3EF6@tu-clausthal.de%3e";
		String expected=xmlHyperlinkExtractor.getFirstHyperLynk(strWithMsgUrls);
		
		assertEquals(expected, actual);
	}

}
