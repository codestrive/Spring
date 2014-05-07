
<html>
	<head>
		<title>Booking On Hold Confirmation </title>
	</head>
	<body>
	<h3>Booking On Hold Confirmation</h3>
	Name: ${name}<br></br>
	Booking Reference: ${pnr} <br></br>
	ATM Reference: ${atmReference} <br></br>
	<hr></hr>
	Fare: <br></br>
	Basefare : ${baseFare}<br></br>
	Book On Hold Fees: ${bohFees}<br></br>
	Flights:<br></br>
	Date: ${outbound.date}<br></br>
	Flight:  ${outbound.fcode}<br></br>
	Segment: ${outbound.from}-${outbound.to}<br></br>
	</br>
	<#if inbound?? >
	  Date: ${inbound.date}<br></br>
	Flight:  ${inbound.fcode}<br></br>
	Segment: ${inbound.from}-${inbound.to}<br></br>
	</#if>
	</body>
	
</html>