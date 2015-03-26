package pramati.wc.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import pramati.wc.datatypes.MonthAndLinkDatatype;

/**
 * singleton class
 * 
 * purpose:- used to manipulate url's and getting url text and other URL information.
 * @author himanshuk
 *
 */
public class URLHelper {
	private static URLHelper instance; 
	private URLHelper(){}
	
	public static URLHelper getInstance() {
		if(instance!=null)
			return instance;
		else
			return instance=new URLHelper();
	}

	public String getPageContentInTxtFrmt(URL url) throws Exception {
		return getPageContent(url);
	}

	private String getPageContent(URL url) throws Exception {
		BufferedInputStream reader=null;
		try {
			reader=new BufferedInputStream(url.openStream());
		} catch (IOException e) {
			throw new Exception("PROBLEM_IN_URL_READING");
		}
		
		Writer writer=new StringWriter();
		for(int c=reader.read();c!=-1;c=reader.read()){
			writer.write(c);
		}
		return writer.toString();
	}

	public URL getFullUrlFromHyperLink(String stringUrl,String string) throws Exception {
		String fullUrlStr=stringUrl+getDecodedURL(string);
		try {
			return new URL(fullUrlStr);
		} catch (MalformedURLException e) {
			throw new Exception("PROBLEM_IN_HYPERLINK");
		}
	}
	
	public String getDecodedURL(String encoded){
		try {
			return URLDecoder.decode(encoded, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return encoded;
		}
	}

}
