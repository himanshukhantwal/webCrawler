package pramati.wc.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class XmlTagExtractorTest {
	XmlTagExtractor xmltagExtractor=null;
	String strWithMultipleTags;
	@Before
	public void setUp() throws Exception {
		strWithMultipleTags="<table id=\"grid\">   <table class=\"year\">\n    <thead><tr>\n     <th colspan=\"3\">Year 2002</th>\n    </tr></thead>\n    <tbody>\n    <tr>     <td class=\"date\">Nov 2002</td>\n     <td some string</td>\n    </tr>\n    </tbody>\n   </table>	</table>";
		xmltagExtractor=XmlTagExtractor.getIntance();
	}

	@Test
	public void testGetXmlWithOnlyPassdStrngTag() {
		String expected="\n    <thead><tr>\n     <th colspan=\"3\">Year 2002</th>\n    </tr></thead>\n    <tbody>\n    <tr>     <td class=\"date\">Nov 2002</td>\n     <td some string</td>\n    </tr>\n    </tbody>\n   ";
		String actual=xmltagExtractor.getXmlWithOnlyPassdStrngTag(strWithMultipleTags, "year", WCEnvironment.getInstance().getEndingTagForAtableEnd());
		assertEquals(expected, actual);
	}

	@Test
	public void testGetXmlWithOnlyPassdStrngTagAndDiffEnd() {
		String expected="Year 2002";
		String actual=xmltagExtractor.getXmlWithOnlyPassdStrngTag(strWithMultipleTags, "colspan=\"3\"", WCEnvironment.getInstance().getEndingTagForAthEnd());
		assertEquals(expected, actual);
	}

}
