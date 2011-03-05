<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="/import/jstl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.util.List"%>
<%@page import="com.toby.buzz.BuzzClient"%>
<%@page import="com.google.buzz.model.BuzzUser"%><html
	xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>User add</title>
</head>
<%
	String delId = request.getParameter("d");
	BuzzClient.deleteBuzzUser(delId);

	String buzzId = request.getParameter("buzzId");
	if (buzzId != null && buzzId.length() > 0) {
		String twitterId = request.getParameter("twitterId");
		String consumerKey = request.getParameter("consumerKey");
		String consumerSecret = request.getParameter("consumerSecret");
		String accessToken = request.getParameter("accessToken");
		String accessTokenSecret = request
				.getParameter("accessTokenSecret");
		BuzzClient.addBuzzUser(buzzId, twitterId, consumerKey,
				consumerSecret, accessToken, accessTokenSecret);

	}
	List<BuzzUser> users = BuzzClient.getBuzzUser();
	request.setAttribute("buzzUsers", users);
%>
<body>
<c:if test="${not empty buzzUsers}">
existing user:
<c:forEach var="item" items="${buzzUsers}">
		<lable>buzzId: ${item.buzzId} twitter:${item.twitterId } </lable>
		<a href="/buzz/user_add.jsp?d=${item.buzzId}">delete user</a>
		<br />
	</c:forEach>

</c:if>
<form action="" method="post">
<p>buzz id <input type="text" name="buzzId" /></p>
<p>twitterId id<input type="text" name="twitterId" /></p>
<p>consumerKey<input type="text" name="consumerKey" /></p>
<p>consumerSecret<input type="text" name="consumerSecret" /></p>
<p>accessToken<input type="text" name="accessToken" /></p>
<p>accessTokenSecret<input type="text" name="accessTokenSecret" /></p>
<input type="submit" value="submit" /></form>
</body>
</html>