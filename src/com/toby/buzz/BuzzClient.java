package com.toby.buzz;

import java.text.SimpleDateFormat;

import com.google.buzz.MyBuzz;
import com.google.buzz.model.BuzzFeed;
import com.google.buzz.model.BuzzFeedEntry;

public class BuzzClient {
	public static void main(String[] args) throws Exception {
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("http.proxySet", "true");
		System.getProperties().put("http.proxyHost", "192.168.16.187");
		System.getProperties().put("http.proxyPort", "8080");
		System.getProperties().put("https.proxyHost", "192.168.16.187");
		System.getProperties().put("https.proxyPort", "8080");
		System.getProperties().put("http.proxyUser", "baojun");
		System.getProperties().put("http.proxyPassword", "tobytoby5");
		MyBuzz buzz = new MyBuzz();
		// buzz.setAccessToken("AIzaSyBWF-5cKKJ_MZIdYg1CC5EnBBpePoPk5G8");
		BuzzFeed feed = buzz.getPosts("toby941", BuzzFeed.Type.PUBLIC);
		// BuzzFeed feed = buzz.getPosts("firemica", BuzzFeed.Type.PUBLIC);

		System.out.println("out");
		if (!feed.getEntries().isEmpty()) {
			for (BuzzFeedEntry entry : feed.getEntries()) {
				System.out.print("title: " + entry.getTitle());
				System.out.print(" | Date: "
				        + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(entry.getPublished()));
				System.out.print(" | link: " + entry.getSourceLink() + "\r\n");
			}
		} else {
			System.out.println("The feed doesn't have any entries.");
		}
	}
}
