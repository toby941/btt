package com.toby.btt;

import java.io.IOException;
import java.util.List;

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
		super.doGet(req, resp);

		Query query = PMF.get().getPersistenceManager().newQuery(
				BuzzFeedEntryStorge.class);
		query.setOrdering("postDate desc");
		List<BuzzFeedEntryStorge> results = (List<BuzzFeedEntryStorge>) query
				.execute();
		if (results.size() > 0) {
			req.setAttribute("list", results);
		}
		getServletConfig().getServletContext().getRequestDispatcher(
				"/JSP/buzz.jsp").forward(req, resp);
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
