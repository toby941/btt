package com.toby.btt;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.buzz.model.BuzzFeedEntryStorge;
import com.toby.btt.storge.PMF;

public class BuzzServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1489066433972074579L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(BuzzFeedEntryStorge.class);
		query.setOrdering("postDate desc");
		resp.setContentType("text/plain");
		List<BuzzFeedEntryStorge> results = (List<BuzzFeedEntryStorge>) query
				.execute();
		if (results.size() > 0) {
			for (BuzzFeedEntryStorge storge : results) {
				resp.getWriter().println(storge.getBitlyUrl());
			}
		}
		BuzzFeedEntryStorge entryStorge = new BuzzFeedEntryStorge("toby941",
				new Date(), "result size:" + results.size(), "www.toby941.com",
				true);
		pm.makePersistent(entryStorge);
		pm.close();
		resp.getWriter().println(results.size());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}

}
