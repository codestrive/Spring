<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        padding-top: 50px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
      .form_span{
      	padding-left: 3px;
      	padding-right: 3px;
      	font-weight: bold;
      	line-height: 2;
      }
      .search_form{
      	background-color: #F5F5F5;
	    border: 1px solid rgba(0, 0, 0, 0.05);
	    border-radius: 4px 4px 4px 4px;
	    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.05) inset;
	    margin-bottom: 5px;
	    min-height: 20px;
	    padding: 5px;
      }
      .search_form_input{
      	float:left;
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
			    <div class="btn-group pull-right">
            <a href="#" data-toggle="dropdown" class="btn dropdown-toggle">
              <i class="icon-user"></i> Username
              <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
              <li><a href="#">Profile</a></li>
              <li class="divider"></li>
              <li><a href="#">Sign Out</a></li>
            </ul>
          </div>
			    <div class="nav-collapse">
			      <ul class="nav">
			        <li class="active"><a href="#">PNR Search</a></li>
			        <li><a href="#about">Reservations</a></li>
			      </ul>
			    </div><!--/.nav-collapse -->
			    </div>
			</div>
		</div>
	    <div class="container">
	    	<form:form  action="" method="GET" class="search_form">
    			<span class="form_span">PNR</span><input class="search_form_input" type="text" name="pnr" style="width: 80px;"/>
    			<span class="form_span">Status</span><select class="search_form_input " name="status" style="width: 100px;">
								<option value=""></option>
								<option value="CONFIRMED">CONFIRMED</option>
								<option value="RESERVED">RESERVED</option>
							</select>
    			<span class="form_span">From</span><input class="search_form_input" type="text" name="fromCode" style="width: 80px;"/>
    			<span class="form_span">To</span><input class="search_form_input" type="text" name="toCode" style="width: 80px;"/>
    			<span class="form_span">Flight</span><input class="search_form_input" type="text" name="flight" style="width: 80px;"/>
    			<span class="form_span">Journey Date</span><input class="search_form_input" type="text" name="jdate" style="width: 80px;"/>
    			<span class="form_span">&nbsp;</span><button class="btn btn-info" type="submit"> Search!</button>
			</form:form>
			<h4>Latest reservations:</h4>
			<table class="table">
				<tr>
					<th>PNR</th>
					<th>Status</th>
					<th>From</th>
					<th>To</th>
					<th>Flight</th>
					<th>Journey Date</th>
					<th>Booking Date</th>
					<th>Base Fare</th>
					<th>Card Fee</th>
					<th>Service Fee</th>
					<th>Currency</th>
				</tr>
				<c:forEach items="${page.content}" var="item">
					<tr>
						<td>${item.pnr}</td>
						<td>${item.status}</td>
						<td>${item.mainItinerary.from}</td>
						<td>${item.mainItinerary.to}</td>
						<td>${item.mainItinerary.flight}</td>
						<td><fmt:formatDate value="${item.mainItinerary.journeyDate}" pattern="dd-MM-yyyy" /></td>	
						<td><fmt:formatDate value="${item.bookingDate}" pattern="dd-MM-yyyy" /></td>
						<td>${item.baseFare}</td>
						<td>${item.cardFee}</td>
						<td>${item.serviceFee}</td>
						<td>${item.currency}</td>
					</tr>
				</c:forEach>
			</table>
			<ul class="pager">
			<c:if test="${!page.firstPage}">
				<c:url value="./latest" var="prevUrl">
					<c:param name="page" value="${page.number - 1}"/>
				</c:url>
				<li class="previous">
					<a href="${nextUrl}">Previous</a>
				</li>
			</c:if>
			
			<c:if test="${!page.lastPage}">
				<c:url value="./latest" var="nextUrl">
					<c:param name="page" value="${page.number + 1}"/>
				</c:url>
				<li class="next">
					<a href="${nextUrl}">Next</a>
				</li>
			</c:if>
			 </ul>
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