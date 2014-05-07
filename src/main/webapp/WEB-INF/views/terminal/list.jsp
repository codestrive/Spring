<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List of devices</title>
</head>
<body>
<table align="right">
	<tr>
		<td><a href="<c:url value='/j_spring_security_logout' />">Logout</a></td>
	</tr>
</table>
<br>
<br>

<table>
	<form:form commandName="searchTerminal" method="get">
		<tr>
			<td>Device-Id :</td>
			<td><form:input path="deviceId" /></td>
		</tr>
		<tr>
			<td>Address :</td>
			<td><form:input path="address" /></td>
		</tr>
		<tr>
			<td>City :</td>
			<td><form:input path="city" /></td>
		</tr>

		<tr>
			<td>State :</td>
			<td><form:input path="state" /></td>
		</tr>

		<tr>
			<td>Country :</td>
			<td><form:input path="country" /></td>
		</tr>

		<tr>
			<td><input type="submit" value="Find" /></td>

		</tr>
	</form:form>
</table>
<br>
<hr>
<br>
${searchTerminal.address }
<table>
	<tr>
		<c:if test="${!page.firstPage}">
			<td><a
				href="list?page=${page.number}&&deviceId=${searchTerminal.deviceId}&address=${searchTerminal.address }&city=${searchTerminal.city}&state=${searchTerminal.state}&country=${searchTerminal.country}">Previous</a></td>
		</c:if>
		<c:forEach var="pageNo" begin="1" end="${page.totalPages}">
			<td><a
				href="list?page=${pageNo}&&deviceId=${searchTerminal.deviceId}&address=${searchTerminal.address }&city=${searchTerminal.city}&state=${searchTerminal.state}&country=${searchTerminal.country}">
			<c:if test="${page.number==pageNo-1}">
				<font color="red">${pageNo}</font>
			</c:if> <c:if test="${page.number!=pageNo-1}">
				<font color="blue">${pageNo}</font>
			</c:if> </a></td>
		</c:forEach>
		<c:if test="${!page.lastPage}">
			<td><a
				href="list?page=${page.number+2}&&deviceId=${searchTerminal.deviceId}&address=${searchTerminal.address }&city=${searchTerminal.city}&state=${searchTerminal.state}&country=${searchTerminal.country}">
			Next</a></td>
		</c:if>
	</tr>
</table>
<table border="1px solid black" cellspacing="0px" width="100%">
	<thead>
		<tr>
			<td>ID</td>
			<td>Device ID</td>
			<td>Device Key</td>
			<td>Active?</td>
			<td>Address</td>
			<td>City</td>
			<td>State</td>
			<td>Country</td>
			<td>Post-Code</td>
		</tr>
	</thead>


	<tbody>
		<c:forEach items="${page.content}" var="terminal">
			<tr>
				<td>${terminal.id}</td>
				<td>${terminal.deviceId}</td>
				<td>${terminal.deviceKey}</td>
				<td>${terminal.active}</td>
				<td>${terminal.location.address}</td>
				<td>${terminal.location.city}</td>
				<td>${terminal.location.state}</td>
				<td>${terminal.location.country}</td>
				<td>${terminal.location.postCode}</td>
				<td><a href="show/${terminal.id}">view</a></td>
				<td><a href="">edit</a></td>
				<td><a href="delete/${terminal.id}">delete</a></td>
				<td><c:if test="${terminal.active}">
					<a
						href="activation?id=${terminal.id}&&page=${page.number+1}&&link=${link}">Deactivate</a>
				</c:if> <c:if test="${!terminal.active}">
					<a
						href="activation?id=${terminal.id}&&page=${page.number+1}&&link=${link}">Activate</a>
				</c:if></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<c:url value="create" var="createUrl" />
<a href="${createUrl}">new</a>

</body>
</html>