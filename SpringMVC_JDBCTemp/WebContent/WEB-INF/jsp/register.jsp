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
	text-align: left;
}
a, a input{
	text-decoration: none;
	cursor: pointer;
}

.heading {
	font-size: 18px;
	color: white;
	font: bold;
	background-color: orange;
	border: thick;
}
</style>
</head>
<body>

<script  src="resources/js/jquery.min.js"> </script>

	<center>
		<br /> <br /> <br /> <b>Spring MVC - JDBC Template </b> <br />
		<br />
		<div>
			<form:form method="post" action="insert" modelAttribute="user" >
				<table>
					<tr>
						<td>First Name :</td>
						<td><form:input path="firstName" /></td>
					</tr>
					<tr>
						<td>Last Name :</td>
						<td><form:input path="lastName" /></td>
					</tr>
					<tr>
						<td>Gender :</td>
						<td><form:radiobuttons path="gender"
								items="${map.genderList}" /></td>
					</tr>
					<tr>
						<td>City :</td>
						<td><form:select path="city" items="${map.cityList}" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td><input type="submit" value="Save" /></td>
					</tr>
					<tr>
						
						<td colspan="2"><a href="getList"><input type="button" value="See User List"/></a></td>
					</tr>
				</table>
			</form:form>
		</div><p>&copy;codestrive</p>
	</center>
	
</body>
</html>