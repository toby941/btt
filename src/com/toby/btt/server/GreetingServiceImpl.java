package com.toby.btt.server;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import winterwell.jtwitter.OAuthSignpostClient;
import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;

import com.toby.btt.client.GreetingService;
import com.toby.btt.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	private Log log = LogFactory.getLog(GreetingServiceImpl.class);

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back
			// to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script
		// vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);
		OAuthSignpostClient oauthClient = new OAuthSignpostClient(
				"rdiMpbTlGOFDWBBnY8PIg",
				"06gDhLk3NsIYYHgLR5pCnVM1Au9i6ql87aMlKOtH9k",
				"16851131-gWQs0iv9pdLBS8mgx6NLd7dFEC6rm6HBtKX1MFAP4",
				// 16851131-gWQs0iv9pdLBS8mgx6NLd7dFEC6rm6HBtKX1MFAP4
				"3kn2tw2l5xL5UsGNC2Omikjn4fF12ODKs7JY2KxFOo");
		Twitter jtwit = new Twitter("toby941", oauthClient);
		// Status s= jtwit.setStatus("test from"+serverInfo+ "Time: "+new
		// Date());
		log.error("now Date" + new Date());
		return "haha Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
