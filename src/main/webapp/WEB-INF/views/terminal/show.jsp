<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table align="right">
	<tr>
		<td><a href="<c:url value='/j_spring_security_logout' />">Logout</a></td>
	</tr>
</table>
<br>
<br>

<table border="1px solid black" cellspacing="0px">
	<tr>
		<td>Device-Id :</td>
		<td>${terminal.deviceId}</td>
	</tr>
	<tr>
		<td>Device-Key :</td>
		<td>${terminal.deviceKey}</td>
	</tr>
	<tr>
		<td>Active :</td>
		<td>${terminal.active}</td>
	</tr>
	<tr>
		<td>Address :</td>
		<td>${terminal.location.address}</td>
	</tr>
	<tr>
		<td>City :</td>
		<td>${terminal.location.city}</td>
	</tr>
	<tr>
		<td>State :</td>
		<td>${terminal.location.state}</td>
	</tr>
	<tr>
		<td>Country :</td>
		<td>${terminal.location.country}</td>
	</tr>
	<tr>
		<td>Post-Code :</td>
		<td>${terminal.location.postCode}</td>
	</tr>
</table>

<br />
<c:url value="../list" var="listUrl" />
<a href="${listUrl}">list</a>

</body>
</html>