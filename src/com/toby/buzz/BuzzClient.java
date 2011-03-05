package com.toby.buzz;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.lang.StringUtils;

import com.google.buzz.MyBuzz;
import com.google.buzz.model.BuzzFeed;
import com.google.buzz.model.BuzzFeedEntry;
import com.google.buzz.model.BuzzFeedEntryStorge;
import com.google.buzz.model.BuzzUser;
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

	public static List<BuzzFeedEntryStorge> getBuzzFeedEntryStorges(
			String userId) {
		userId = userId.trim();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(BuzzFeedEntryStorge.class, "who == userId ");
		query.declareParameters("String userId");
		query.setOrdering("postDate desc");
		List<BuzzFeedEntryStorge> entryStorges = (List<BuzzFeedEntryStorge>) query
				.execute(userId);
		return entryStorges;
	}

	public void saveBuzzFeedEntryStorge(BuzzFeedEntry entry, boolean isSynced) {
		BuzzFeedEntryStorge storge = new BuzzFeedEntryStorge(entry.getId(),
				entry.getPublished(), entry.getTitle(), entry.getSourceLink(),
				isSynced);
		PMF.get().getPersistenceManager().makePersistent(storge);
	}

	public static List<BuzzUser> getBuzzUser() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(BuzzUser.class);
		List<BuzzUser> buzzUsers = (List<BuzzUser>) query.execute();
		return buzzUsers;
	}

	public static void addBuzzUser(String buzzId, String twitterId,
			String consumerKey, String consumerSecret, String accessToken,
			String accessTokenSecret) {
		if (StringUtils.isNotEmpty(buzzId) && StringUtils.isNotEmpty(twitterId)
				&& StringUtils.isNotEmpty(consumerKey)
				&& StringUtils.isNotEmpty(consumerSecret)
				&& StringUtils.isNotEmpty(accessToken)
				&& StringUtils.isNotEmpty(accessTokenSecret)) {
			BuzzUser buzzUser = new BuzzUser(buzzId, twitterId, consumerKey,
					consumerSecret, accessToken, accessTokenSecret);
			PMF.get().getPersistenceManager().makePersistent(buzzUser);
		}
	}

	public static void deleteBuzzUser(String buzzId) {
		if (StringUtils.isNotBlank(buzzId)) {
			buzzId = StringUtils.trimToEmpty(buzzId);
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(BuzzUser.class, "buzzId ==id");
			query.declareParameters("String id");
			List<BuzzUser> users = (List<BuzzUser>) query.execute(buzzId);
			if (users.size() > 0) {
				for (BuzzUser user : users) {
					pm.deletePersistent(user);
				}
			}
		}

	}
}
