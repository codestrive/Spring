<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Reservations List</title>
</head>
<body>
	${pnr.ref}
	<c:forEach items="${pnr.passengers}" var="passenger">
		-----------------------------------------------Passenger---------------<br>
			Pax Name: ${passenger.name} <br>
			PTC : ${passenger.ptc}  <br>
	    ----------------Flights-----------------<br>
    	<c:forEach items="${passenger.flights}" var="flight">
			Air Line : ${flight.airline} 
   			
    
		</c:forEach>
	</c:forEach>
	<form:form action="" method="POST">
		<input type="text" name="pnr"/>
			<button type="submit"> Please Send!</button>
	</form:form>
</body>
</html>