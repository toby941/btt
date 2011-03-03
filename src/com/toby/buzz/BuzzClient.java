package com.toby.buzz;

import java.text.SimpleDateFormat;

import com.google.buzz.MyBuzz;
import com.google.buzz.model.BuzzFeed;
import com.google.buzz.model.BuzzFeedEntry;
import com.toby.btt.storge.PMF;

public class BuzzClient {
	public static void main(String[] args) throws Exception {
		MyBuzz buzz = new MyBuzz();
		// buzz.setAccessToken("AIzaSyBWF-5cKKJ_MZIdYg1CC5EnBBpePoPk5G8");
		BuzzFeed feed = buzz.getPosts("toby941", BuzzFeed.Type.PUBLIC);
		// BuzzFeed feed = buzz.getPosts("firemica", BuzzFeed.Type.PUBLIC);

		if (!feed.getEntries().isEmpty()) {
			for (BuzzFeedEntry entry : feed.getEntries()) {
				System.out.print("title: " + entry.getTitle());
				System.out.print(" | Date: "
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm").format(entry
								.getPublished()));
				System.out.print(" | link: " + entry.getSourceLink() + "\r\n");
			}
		} else {
			System.out.println("The feed doesn't have any entries.");
		}
	}

	public void saveBuzzEntity(BuzzFeedEntry entry) {
		PMF.get().getPersistenceManager().makePersistent(entry);
	}
}
