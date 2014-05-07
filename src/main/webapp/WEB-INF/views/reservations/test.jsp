<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Hub Portal</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="/airkiosk-hub/assets/css/bootstrap.css" rel="stylesheet">
    <link href="/airkiosk-hub/assets/css/bootstrap-responsive.css" rel="stylesheet">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    
</head>
	<body>
 		<div class="navbar navbar-fixed-top">
			<div class="navbar-inner">
			  <div class="container">
			    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
			      <span class="icon-bar"></span>
			      <span class="icon-bar"></span>
			      <span class="icon-bar"></span>
			    </a>
			    <a class="brand" href="#">Hub Portal</a>
			    <div class="nav-collapse">
			      <ul class="nav">
			        <li class="active"><a href="#">PNR Search</a></li>
			        <li><a href="#about">Reservations</a></li>
			        <li><a href="#contact">Latest</a></li>
			      </ul>
			    </div><!--/.nav-collapse -->
			    </div>
			</div>
		</div>
	    <div class="container">
			<div class="row">
	 			<div class="span3">
	 				 
	 				<form:form class="well" action="" method="POST">
						<input class="" type="text" name="pnr"/><label>Enter PNR above</label>
						<button class="btn" type="submit"> Please Send!</button>
					</form:form>
					<c:if test="${pnr!=null}">
						<div class="well">
							<h2>${pnr.ref}</h2>
						</div>
				     </c:if>
	 			</div>
	  			<div class="span9">
	  				
					<c:if test="${pnr!=null}">
						<div class="accordion" id="accordion2">
							<%int count=1; %>
				        	<c:forEach items="${pnr.passengers}" var="passenger">
				            	<div class="accordion-group">
				              		<div class="accordion-heading">
				                		<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapse<%=count%>">
				                 		 P<%=count%>. ${passenger.name} 
				                		</a>
				             		 </div>
				             		 <%	if (count ==1 ){%>
				             		 		<div id="collapse<%=count++ %>" class="accordion-body collapse in"> 
				             		 <%	}else{%>
				             		 		<div id="collapse<%=count++ %>" class="accordion-body collapse"> 
				             		 <%	} %>
				             		 
				                	<div class="accordion-inner">
				                 	Flights
				                 	<table class="table ">
							        <thead>
							          <tr>
							            <th>#</th>  <th>Airline</th>   <th>Flight_number</th>  <th>Segment</th>
							            <th>Date</th>  <th>STD</th> <th>STA</th> <th>Class</th> <th>Status</th> <th>Seats</th>
							          </tr>
							        </thead>
							        <tbody>
							        	<% int fligntCount=1; %>
				            	  		<c:forEach items="${passenger.flights}" var="flight">
								          <tr>
								            <td><%=fligntCount++ %></td>
								            <td>${flight.airline}</td>
								            <td>${flight.flightNumber}</td>
								            <td>${flight.segment}</td>
								            <td>${flight.date}</td>
								            <td>${flight.departure}</td>
								            <td>${flight.arrival}</td>
								            <td>${flight.clazz}</td>
								            <td>${flight.status}</td>
								            <td>${flight.seats}</td>
								          </tr>
							           </c:forEach>
							        </tbody>
							     </table>
							     <c:if test="${passenger.tickets != null}">
							    Tickets
							    <table class="table" style="width:50%;">
							        <thead>
							          <tr>
							            <th>#</th>  <th>Ticket Number</th> 
							          </tr>
							        </thead>
							        <tbody>
							        	<% int ticketCount=1; %>
				            	  		<c:forEach items="${passenger.tickets}" var="ticket">
								          <tr>
								            <td><%=ticketCount++ %></td>
								            <td>${ticket.number}</td>
								          </tr>
							           </c:forEach>
							        </tbody>
							     </table>
							     </c:if>
							     	<c:if test="${passenger.payment != null}">
								     Payments<table class="table">
								        <thead>
								          <tr>
								            <th>FOP<th>CC Type</th> <th>Auth Code</th> <th>Currency</th> <th>Amount </th><th>Agt Ref</th> 
								            <th>CC Fees</th>  <th>Service Fee</th>
								          </tr>
								        </thead>
								        <tbody>
									          <tr>
									            <td>${passenger.payment.fop}</td><td>${passenger.payment.ccType}</td> <td>${passenger.payment.authCode}</td>
									            <td>${passenger.payment.currency}</td> <td>${passenger.payment.amount}</td> <td>${passenger.payment.agtRef}</td>
									             <td>${passenger.payment.ccFee}</td>  <td>${passenger.payment.serviceFee}</td>
									          </tr>
								        </tbody>
								     </table>
								    </c:if>	 
				                 		</div>
				              		</div>
				            	</div>
				            </c:forEach>
			    	    </div>
			      		<table class="table table-bordered">
					     	<thead>
					          <tr>
					            <th>Contacts</th>  <th>&nbsp;</th>
					          </tr>
					        </thead>
					        <tbody>
					         <tr><td>
					          <c:forEach items="${pnr.contacts}" var="contact">
						            ${contact}</br>
					          </c:forEach>
					          </td>
					          <td><span style="font-weight:bold;">Agent :</span> ${pnr.agent}</br> 
					             <span style="font-weight:bold;">Equipment :</span>${pnr.equipment}
					           </td> 
					          </tr>
					        </tbody>
					     </table>
					</c:if>
				</div>
			</div>
	    </div> <!-- /container -->
    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="/airkiosk-hub/assets/js/jquery.js"></script>
    <script src="/airkiosk-hub/assets/js/bootstrap-transition.js"></script>
    <script src="/airkiosk-hub/assets/js/bootstrap-alert.js"></script>
    <script src="/airkiosk-hub/assets/js/bootstrap-modal.js"></script>
    <script src="/airkiosk-hub/assets/js/bootstrap-dropdown.js"></script>
    <script src="/airkiosk-hub/assets/js/bootstrap-scrollspy.js"></script>
    <script src="/airkiosk-hub/assets/js/bootstrap-tab.js"></script>
    <script src="/airkiosk-hub/assets/js/bootstrap-tooltip.js"></script>
    <script src="/airkiosk-hub/assets/js/bootstrap-popover.js"></script>
    <script src="/airkiosk-hub/assets/js/bootstrap-button.js"></script>
    <script src="/airkiosk-hub/assets/js/bootstrap-collapse.js"></script>
    <script src="/airkiosk-hub/assets/js/bootstrap-carousel.js"></script>
    <script src="/airkiosk-hub/assets/js/bootstrap-typeahead.js"></script>
	</body>
</html>