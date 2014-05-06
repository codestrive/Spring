<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Spring MVC - JDBC Template</title>
<style>
body {
	font-size: 20px;
	color: teal;
	font-family: Calibri;
	background-color: #f7f7f7;
}

td {
	font-size: 15px;
	color: black;
	width: 100px;
	height: 22px;
	text-align: center;
}
.heading {
	font-size: 18px;
	color: white;
	font: bold;
	background-color: #CCC;
	border: thick;
}
a, a input{
	text-decoration: none;
	cursor: pointer;
}
</style>
</head>
<body>
	<center>
		<br /> <br /> <br /> <b>Spring MVC - JDBC Template </b><br />
		User List
		 <br />

		<table border="1">
			<tr>
				<td class="heading">User Id</td>
				<td class="heading">First Name</td>
				<td class="heading">Last Name</td>
				<td class="heading">Gender</td>
				<td class="heading">City</td>
				<td class="heading">Edit</td>
				<td class="heading">Delete</td>
			</tr>
			<c:forEach var="user" items="${userList}">
				<tr>
					<td>${user.userId}</td>
					<td>${user.firstName}</td>
					<td>${user.lastName}</td>
					<td>${user.gender}</td>
					<td>${user.city}</td>
					<td><a href="edit?id=${user.userId}"><input type="button" value="Edit"/></a></td>
					<td><a href="delete?id=${user.userId}"><input type="button" value="Delete"/></a></td>
				</tr>
			</c:forEach>
			<tr><td colspan="7"><a href="register"><input type="button" value="Add New User"/></a></td></tr>
		</table>
		<p>&copy;codestrive</p>
	</center>
</body>
</html>