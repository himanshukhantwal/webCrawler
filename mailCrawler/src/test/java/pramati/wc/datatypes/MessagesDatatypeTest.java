package pramati.wc.datatypes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MessagesDatatypeTest {
	MessagesDatatype messagesDatatype;
	@Before
	public void setUp() throws Exception {
		messagesDatatype=new MessagesDatatype("abc", "xyz", "some Date","URL/123");
	}

	@Test
	public void testEqualsObject() {
		MessagesDatatype messagesDatatypeTwo=new MessagesDatatype("abc", "xyz", "some Date","URL/456");
		assertTrue(messagesDatatype.equals(messagesDatatypeTwo));
	}

}
