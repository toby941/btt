package com.toby.buzz;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;

public class BitLyUrl {

	private static String templete = "http://api.bitly.com/v3/shorten?login={0}&apiKey={1}&longUrl={2}&format=txt";

	public static String makeShortUrl(String urlStr) throws Exception {
		String encodeUrl = URLEncoder.encode(urlStr);
		String request = MessageFormat.format(templete, "toby941",
		        "R_c264d92a1f354a7f664acd85934fef36", encodeUrl);
		HttpURLConnection con;
		URL url = new URL(request);
		con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.connect();
		// Read response
		StringBuffer response = null;
		InputStream is = con.getInputStream();
		response = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = is.read(b)) != -1;) {
			response.append(new String(b, 0, n, "GBK"));
		}
		return response.toString();
	}

	public static void main(String[] args) throws Exception {
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("http.proxySet", "true");
		System.getProperties().put("http.proxyHost", "192.168.16.187");
		System.getProperties().put("http.proxyPort", "8080");
		System.getProperties().put("https.proxyHost", "192.168.16.187");
		System.getProperties().put("https.proxyPort", "8080");
		System.getProperties().put("http.proxyUser", "baojun");
		System.getProperties().put("http.proxyPassword", "tobytoby5");
		BitLyUrl bitLyUrl = new BitLyUrl();
		String url = "http://code.google.com/p/bitly-api/wiki/ApiDocumentation#/v3/expand";
		System.out.println(BitLyUrl.makeShortUrl(url));
	}
}
