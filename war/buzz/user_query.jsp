<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@include file="/import/jstl.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>USER QUERY</title>
</head>
<body>
user query:
<form action="/buzz/user_list.jsp" method="get">
<label>user id: </label><input type="text" name="userId"/>
<input type="submit" value="submit"/>
</form>
<br/>
user delete:
<form action="/buzz/user_delete.jsp" method="post">
</form>
<%@include file="/buzz/foot.jsp"%>
</body>
</html>