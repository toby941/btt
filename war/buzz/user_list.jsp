<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/import/jstl.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.util.List"%>
<%@page import="com.google.buzz.model.BuzzFeedEntryStorge"%>
<%@page import="com.toby.buzz.BuzzClient"%>
<%@page import="java.util.ArrayList"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>User List</title>
</head>
<%
	String userId=request.getParameter("userId");
   request.setAttribute("userId",userId);
List<BuzzFeedEntryStorge> entryStorges=new ArrayList<BuzzFeedEntryStorge>();
	if(userId!=null){
   entryStorges=BuzzClient.getBuzzFeedEntryStorges(userId);
   request.setAttribute("entryStorges",entryStorges);
	}
%>
<body>
<c:choose>
<c:when test="${not empty entryStorges}">
 <c:forEach var="item" items="${entryStorges}">
 title <c:out value="${item.title}"></c:out>
 url <c:out value="${item.bitlyUrl}"></c:out><br/>
  issync <c:out value="${item.synced}"></c:out>
 </c:forEach>
</c:when>
<c:otherwise>
 You input ${userId} is not record in DB!,Please <a href="/buzz/user_query.jsp">enter</a> again

</c:otherwise>
</c:choose>




</body>
</html>