package com.toby.btt;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import winterwell.jtwitter.OAuthSignpostClient;
import winterwell.jtwitter.Twitter;

import com.google.buzz.MyBuzz;
import com.google.buzz.model.BuzzFeed;
import com.google.buzz.model.BuzzFeedEntry;
import com.google.buzz.model.BuzzFeedEntryStorge;
import com.google.buzz.model.BuzzUser;
import com.toby.btt.storge.PMF;
import com.toby.buzz.BuzzClient;

public class BuzzToTwitter extends HttpServlet {

	private final Logger logger = Logger.getLogger(BuzzToTwitter.class
			.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(BuzzUser.class);
			List<BuzzUser> users = (List<BuzzUser>) query.execute();
			if (users.size() > 0) {
				for (BuzzUser buzzUser : users) {
					String userId = buzzUser.getBuzzId().trim();
					MyBuzz buzz = null;
					BuzzFeed feed = null;
					int tryTimes = 8;
					while (feed == null && tryTimes > 0) {
						try {
							buzz = new MyBuzz(userId);
							feed = buzz.getPosts(BuzzFeed.Type.PUBLIC);
						} catch (Exception e) {
							tryTimes--;
							buzz = null;
							feed = null;
						}
					}

					if (feed == null || feed.getEntries().isEmpty()) {
						continue;
					}
					List<BuzzFeedEntryStorge> buzzFeedEntryStorges = BuzzClient
							.getBuzzFeedEntryStorges(userId);
					Date lastSyncTime = buzzFeedEntryStorges.get(0)
							.getPostDate();
					OAuthSignpostClient oauthClient = new OAuthSignpostClient(
							buzzUser.getConsumerKey(), buzzUser
									.getConsumerSecret(), buzzUser
									.getAccessToken(), buzzUser
									.getAccessTokenSecret());
					Twitter jtwit = new Twitter(buzzUser.getTwitterId(),
							oauthClient);
					for (BuzzFeedEntry entry : feed.getEntries()) {
						boolean isSynced = true;
						if (entry.getPublished().after(lastSyncTime)) {
							try {
								jtwit.setStatus(entry.getTitle() + " source: "
										+ entry.getSourceLink());
							} catch (Exception e) {
								isSynced = false;
								e.printStackTrace();
								logger.log(Level.SEVERE, "send twitter error",
										e);
							}
						}
						BuzzClient.saveBuzzFeedEntryStorge(entry, isSynced,
								userId);
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
