<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Provision a new device</title>
<link href="style/style.css" rel="stylesheet" type="text/css" />
<style>
.error {
	color: #ff0000;
}

</style>
</head>
<body>

<form:form commandName="location">
	<table>
		<tr>
			<td>Address :</td>
			<td><form:input path="address" /></td>
			<td><form:errors path="address" cssClass="error" /></td>
		</tr>
		<tr>
			<td>City :</td>
			<td><form:input path="city" /></td>
			<td><form:errors path="city" cssClass="error" /></td>
		</tr>

		<tr>
			<td>State :</td>
			<td><form:input path="state" /></td>
			<td><form:errors path="state" cssClass="error" /></td>
		</tr>

		<tr>
			<td>Country :</td>
			<td><form:input path="country" /></td>
			<td><form:errors path="country" cssClass="error" /></td>
		</tr>

		<tr>
			<td>PostCode :</td>
			<td><form:input path="postCode" /></td>
			<td><form:errors path="postCode" cssClass="error" /></td>
		</tr>
		<tr>
			<td><input type="submit" value="Provision New" /></td>
			
		</tr>
	</table>
</form:form>
</body>
</html>