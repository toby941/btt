package com.google.buzz.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.google.buzz.exception.BuzzIOException;
import com.google.buzz.exception.BuzzParsingException;
import com.google.buzz.model.BuzzFeed;
import com.google.buzz.model.BuzzFeedEntry;
import com.google.buzz.parser.handler.FeedHandler;

/**
 * Parser for element: <b>feed<b/>.
 * 
 * @author roberto.estivill
 */
public class BuzzFeedParser {
	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");

	/**
	 * Parse an xml string into a BuzzFeed model object.
	 * 
	 * @param xmlResponse
	 *            to be parsed.
	 * @return the feed object.
	 * @throws BuzzIOException
	 *             if any IO error occurs.
	 * @throws BuzzParsingException
	 *             if a parsing error occurs.
	 */
	public static BuzzFeed parseFeed(String xmlResponse) throws BuzzParsingException,
	        BuzzIOException {
		FeedHandler handler;
		XMLReader xr;
		try {
			xr = XMLReaderFactory.createXMLReader();
			handler = new FeedHandler(xr);
			xr.setContentHandler(handler);
			xr.setErrorHandler(handler);
			xr.parse(new InputSource(new ByteArrayInputStream(xmlResponse.getBytes("UTF-8"))));
		} catch (SAXException e) {
			throw new BuzzParsingException(e);
		} catch (IOException e) {
			throw new BuzzIOException(e);
		}
		return handler.getFeed();
	}

	public static BuzzFeed parseFeedEntityWithDom4jOnlyContentAndContent(String xmlResponse)
	        throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new ByteArrayInputStream(xmlResponse.getBytes("UTF-8")));
		Element rootElement = document.getRootElement();
		Iterator<?> entryElements = rootElement.elementIterator("entry");
		BuzzFeed bf = new BuzzFeed();
		List<BuzzFeedEntry> entries = new ArrayList<BuzzFeedEntry>();
		while (entryElements.hasNext()) {
			Element e = (Element) entryElements.next();
			BuzzFeedEntry buzzFeedEntry = new BuzzFeedEntry(e);
			entries.add(buzzFeedEntry);
		}
		bf.setEntries(entries);
		return bf;
	}

	public static void main(String[] args) {
		String s = "对java 下解析xml火大的同学可以试试这个<br /><a href=\"http://www.xom.nu/\" >http://www.xom.nu/</a>";
		System.err.println(s.replaceAll("<br />", " ").replaceAll("\\<.*?>", ""));
		System.err.println(s.replaceAll("\\<.*?>", ""));
		String s1 = "周末倒腾了2天的btt(buzz to twitter)小有进展,应用已经在GAE上跑起来了 http://buzztot.appspot.com/ buzz的获取，通过api更新twitter";
		String s2 = "中国人的权力靠中国人自己去争取，不要梦想专制政权会主动赏赐你这种权力。请加入我们，用非暴力不合作，让中国政府尊重中国人民的基本权力。 时间：2011年2月20日开始 每周日下午2时（如果中国政府";
		System.out.println(s1.length());
		System.out.println(s2.length());
	}
}