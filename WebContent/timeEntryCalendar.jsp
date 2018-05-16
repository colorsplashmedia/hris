<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Time Entry Calendar</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link href='css/timeentry/fullcalendar.css' rel='stylesheet' />
<link href='css/timeentry/fullcalendar.print.css' rel='stylesheet' media='print' />
<link rel='stylesheet' href='css/timeentry/lib/cupertino/jquery-ui.min.css' />
<link rel="stylesheet" type="text/css" href="css/calendarmodal.css" />
<script src='js/timeentry/lib/moment.min.js'></script>
<script src='js/timeentry/lib/jquery.min.js'></script>
<script src='js/timeentry/fullcalendar.min.js'></script>
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />


<script>

	$(document).ready(function() {
		var empId = '${employeeLoggedIn.empId}';	
    	
    	if(empId.length == 0){
    		alert("You are not allowed to view the page. Please login.");
    		window.location = "LogoutAction";
    		return;
    	}
		
		var superVisorId = document.getElementById("empIdLoggedIn").value;

		//alert(superVisorId);
		$('#calendar').fullCalendar({
			theme: true,
			editable: true,
			eventLimit: true, // allow "more" link when too many events
			eventOrder: "sequence, title",
			events: {
				url: '/hris/GetTimeEntryCalendarAction',
				data: function () { // a function that returns an object
	                return {
	                	personnelType: document.getElementById("personnelType").value,
	                };

	            },
				error: function() {
					$('#script-warning').show();
				}
			},
			loading: function(bool) {
				$('#loading').toggle(bool);
			}
		});	
		
	});
	
</script>
	
<script>
	function filterTimeEntry(){
		$('#calendar').fullCalendar('refetchEvents');		
	}

</script>

</head>
<body>
<div><c:import url="header.jsp" /></div>
<input type="hidden" name="empIdLoggedIn" id="empIdLoggedIn" value="${employeeLoggedIn.empId}" />
<div id="content">
<div id="legendArea">
	<div style="margin: 0px 0px 5px 10px;">Filter Display by Personnel Type</div>
	<div class="dataEntryInput" style="margin-bottom: 20px;">				    	
		<select name="personnelType" id="personnelType" style="width: 250px; padding: 5px; margin: 0px 0px 0px 8px;" onchange="filterTimeEntry()" >		
			<option value="">Select Personnel Type</option>				    													
			<option value="RS">REGULAR STAFF</option>						
			<option value="AS">ALLIED MEDICAL STAFF</option>
			<option value="DC">DOCTOR</option>
			<option value="NR">NURSE</option>
			<option value="OT">OTHERS</option>
		</select>			    	
	</div> 
	<div style="margin: 0px 0px 5px 10px;">
		<div class="cb"></div>
		<div style="margin-top: 20px;">Legend</div>
		<div>Paid - Rest Day <div style="width: 60px; height: 10px; background-color: #838389; float:left; margin: 5px 10px 0px 0px"></div></div>
		<div class="cb"></div>
		<div>Unpaid - Rest Day <div style="width: 60px; height: 10px; background-color: #000000; float:left; margin: 5px 10px 0px 0px"></div></div>
		<div class="cb"></div>
		<div>Problematic Time Entry <div style="width: 60px; height: 10px; background-color: #FF0000; float:left; margin: 5px 10px 0px 0px"></div></div>
		<div class="cb"></div>
		<div>Perfect Time Entry <div style="width: 60px; height: 10px; background-color: #008000; float:left; margin: 5px 10px 0px 0px"></div></div>
		<div class="cb"></div>
		<div>Incomplete Time Entry <div style="width: 60px; height: 10px; background-color: #9900FF; float:left; margin: 5px 10px 0px 0px"></div></div>
		<div class="cb"></div>
		<div>Time Entry Without Schedule<div style="width: 60px; height: 10px; background-color: #1874CD; float:left; margin: 5px 10px 0px 0px"></div></div>
		<div class="cb"></div>
	</div>
</div>
<div id='calendar'></div>
<div class="cb" style="height: 50px;"></div>
</div>
<div><c:import url="footer.jsp" /></div>
</body>
</html>