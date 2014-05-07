<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Passenger List</title>
	<style type=”text/css”>
		body {
			background-color: #d2b48c;
			margin-left: 20%;
			margin-right: 20%;
			border: 1px dotted gray;
			padding: 10px 10px 10px 10px;
			font-family: sans-serif;
		}
	</style>
</head>

<body>
	<h4>List of Passengers:</h4>
	<table border="1" bordercolor="#000000" cellpadding=4 cellspacing=2>
		<tr>
			<td>Passenger Name</td>
			<td>Email Address</td>
			<td>Phone Number</td>
		</tr>
		<c:forEach items="${page.content}" var="item">
			<tr>
				<td>${item.passengerName}</td>
				<td>${item.eMail}</td>
				<td>${item.phone}</td>
			</tr>
		</c:forEach>
	</table>
	<c:if test="${!page.firstPage}">
		<c:url value="./" var="prevUrl">
			<c:param name="page" value="${page.number - 1}" />
		</c:url>
		<a href="${nextUrl}">Previous</a>
	</c:if>

	<c:if test="${!page.lastPage}">
		<c:url value="./" var="nextUrl">
			<c:param name="page" value="${page.number + 1}" />
		</c:url>
		<a href="${nextUrl}">Next</a>
	</c:if>
</body>
</html>