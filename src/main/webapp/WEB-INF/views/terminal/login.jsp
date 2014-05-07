<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
<form name="f" action="<c:url value='../../j_spring_security_check'/>"
	method="POST">
<table   background="blue" align="center">

	<tr>
		<td>User:</td>
		<td><input type='text' name='j_username'></td>
	</tr>
	<tr>
		<td>Password:</td>
		<td><input type='password' name='j_password'></td>
	</tr>
	<tr>
		<td align="right">Remember me</td>
		<td><input type="checkbox" name="_spring_security_remember_me" /></td>
	</tr>

	<tr>
		<td></td>
		<td><font color="red">${error}</font></td>
	</tr>
	<tr>
		<td colspan='2'><input name="submit" type="submit"></td>
	</tr>
	<tr>
		<td colspan='2'><input name="reset" type="reset"></td>
	</tr>
</table>

</form>

</body>
</html>