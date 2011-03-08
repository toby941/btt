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

/**
 *  定时触发doGET方法，由GAE的corn负责定时执行，在WEB─INF/corn.xml中配置
 * @author toby
 *
 */
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
			//获取当前需自动同步的buzz user
			List<BuzzUser> users = (List<BuzzUser>) query.execute();
			if (users.size() > 0) {
				for (BuzzUser buzzUser : users) {
					String userId = buzzUser.getBuzzId().trim();
					MyBuzz buzz = null;
					BuzzFeed feed = null;
					int tryTimes = 8;
					//获取buzz user 的public post，由于目前api不完善，对于一些reshare类的post在google返回时有一定概率会导致xml
					//解析报错，此处反复尝试8次，任何一次成功，流程继续
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
					//获取user已经同步的post，GAE持久化
					List<BuzzFeedEntryStorge> buzzFeedEntryStorges = BuzzClient
							.getBuzzFeedEntryStorges(userId);
					Date lastSyncTime = buzzFeedEntryStorges.get(0)
							.getPostDate();
					//根据用户提交的api key 建立twitter更新机器人 
					OAuthSignpostClient oauthClient = new OAuthSignpostClient(
							buzzUser.getConsumerKey(), buzzUser
									.getConsumerSecret(), buzzUser
									.getAccessToken(), buzzUser
									.getAccessTokenSecret());
					Twitter jtwit = new Twitter(buzzUser.getTwitterId(),
							oauthClient);
					for (BuzzFeedEntry entry : feed.getEntries()) {
						boolean isSynced = true;
						//判断当前的post publish time是否在最后一条已经同步的post之后
						if (entry.getPublished().after(lastSyncTime)) {
							try {
								//post时间晚于最后一次同步时间，需向twitter同步
								jtwit.setStatus(entry.getTitle() + " source: "
										+ entry.getSourceLink());
							} catch (Exception e) {
								//若twitter更新状态失败，则记录信息，留待下次同步在补充执行
								isSynced = false;
								e.printStackTrace();
								logger.log(Level.SEVERE, "send twitter error",
										e);
							}
						}
						//将已经同步过的post持久化
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
