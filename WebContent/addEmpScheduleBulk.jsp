<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Add Employee Schedule</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@include file="commonHeader.jsp" %>
<%@include file="commonJtables.jsp" %>
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />
<link rel="stylesheet" type="text/css" href="css/styleTables.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/calendarmodal.css" />
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link href="js/validationEngine/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/validationEngine/jquery.validationEngine.js"></script>
<script type="text/javascript" src="js/validationEngine/jquery.validationEngine-en.js"></script>
<script type="text/javascript" src="js/common.js"></script>


<script type="text/javascript">
	$(document).ready(function() {
		var empId = '${employeeLoggedIn.empId}'; 
		
		populateShiftingScheduleDropDown();
		populateEmployeeCheckBoxes(empId);
		
		$('#selectAllCheckBox').click(function(event) {  //on click 
	        if(this.checked) { // check select status
	            $('.checkbox1').each(function() { //loop through each checkbox
	                this.checked = true;  //select all checkboxes with class "checkbox1"               
	            });
	        }else{
	            $('.checkbox1').each(function() { //loop through each checkbox
	                this.checked = false; //deselect all checkboxes with class "checkbox1"                       
	            });         
	        }
	    });
		
		
	});

	
	
	function saveSchedule() {		
		if (confirm('Are you sure you want to apply these schedule to the following persons?')) {
			var scheduleDateFrom = document.getElementById("scheduleDateFrom").value;
			var scheduleDateTo = document.getElementById("scheduleDateTo").value;
			var shiftingScheduleId = document.getElementById("shiftingScheduleDropDownID").value;
			
			var monday = document.getElementById("monday");
			var tuesday = document.getElementById("tuesday");
			var wednesday = document.getElementById("wednesday");
			var thursday = document.getElementById("thursday");
			var friday = document.getElementById("friday");
			var saturday = document.getElementById("saturday");
			var sunday = document.getElementById("sunday");
			
			
			var x = 0;
			
			if(monday.checked){
				//alert("monday");
				x = x + 1;
			}
			
			if(tuesday.checked){
				//alert("tuesday");
				x = x + 1;
			}
			
			if(wednesday.checked){
				//alert("wednesday");
				x = x + 1;
			}
			
			if(thursday.checked){
				//alert("thursday");
				x = x + 1;
			}
			
			if(friday.checked){
				//alert("friday");
				x = x + 1;
			}
			
			if(saturday.checked){
				//alert("saturday");
				
				x = x + 1;
			}
			
			if(sunday.checked){
				//alert("sunday");
				x = x + 1;
			}
			
			if(x == 0) {
				alert("Please select at least 1 day of the week.");				
				return;
			}
			
			if(scheduleDateFrom == ""){
				alert("Date From is a required field.");
				return;
			}
			
			if(scheduleDateTo == ""){
				alert("Date To is a required field.");
				return;
			}
			
			if(shiftingScheduleId == ""){
				alert("Shift Schedule is a required field.");
				return;
			}
			
			
			
			$.ajax({
				type:"POST",
				url:"/hris/SaveEmployeeScheduleBulkAction",
				data: JSON.stringify($('#addEmpShiftForm').serializeObject()),
				cache: false,
				async: true,
				dataType: 'json',
				success: function (data) {
					alert('Employee schedule has been successfully saved.');

					window.location = "employeeScheduleCalendar.jsp";
				
				},
				error: function (data) {alert('error: '+data)}
			});
		}
		
		
		//document.addEmpShiftForm.submit();
	}
	
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};
	
</script>

</head>
<body>
<div><c:import url="header.jsp" /></div>	
<div id="content">
 
	
	<!-- Right Side of Dashboard -->
	<div  style="margin: 0px auto 0 auto; width: 930px;">	
		
	  	<div style="margin: 0px auto; border: 1px solid black; height: 510px;">
			<div id="resolutionHeader">Add/Edit Employee Shift With Date Range</div>
			<input type="hidden" name="empId" id="empId" value="">
			<input type="hidden" name="empIdLoggedIn" id="empIdLoggedIn" value="${employeeLoggedIn.empId}" />
			<form method="POST" id="addEmpShiftForm" name="addEmpShiftForm" action="SaveEmployeeScheduleNewAction">			
			
			<input type="hidden" name="saveMethod" id="saveMethod" value="">			
			<input type="hidden" name="empScheduleId" id="empScheduleId" value="">
			
			<div>
				<table style="margin-left: 30px;">
					<tr>
						<td>Date From</td>
						<td>
							<input type="text" id="scheduleDateFrom" name="scheduleDateFrom" class="useDPicker" placeholder="Date From" size="23" />
						</td>
						<td>Date To</td>
						<td>
							<input type="text" id="scheduleDateTo" name="scheduleDateTo" class="useDPicker" placeholder="Date To" size="23" />
						</td>
						<td>Shift Schedule</td>
						<td>
							<select name="shiftingScheduleId" id="shiftingScheduleDropDownID" style="width: 180px; height: 35px; background: #fff; border: 1px solid #52833b; margin-top: 5px; color: black;">
							</select>
						</td>
					</tr>
					
				</table>
			
			</div>
			<div style="margin-left: 30px;">
				<div class="cb" style="margin: 0px 0px 0px 0px;">			
					Select day of the week that apply				
				</div>
				<div class="cb" style="margin: 5px 0px 0px 0px;">			
					<div style="float: left; margin-right: 10px;"><input type="checkbox" id="monday" name="weekday" value="2" style="margin: 0px 5px 0px 0px;">Monday</div>
					<div style="float: left; margin-right: 10px;"><input type="checkbox" id="tuesday" name="weekday" value="3" style="margin: 0px 5px 0px 0px;">Tuesday</div>
					<div style="float: left; margin-right: 10px;"><input type="checkbox" id="wednesday" name="weekday" value="4" style="margin: 0px 5px 0px 0px;">Wednesday</div>
					<div style="float: left; margin-right: 10px;"><input type="checkbox" id="thursday" name="weekday" value="5" style="margin: 0px 5px 0px 0px;">Thursday</div>
					<div style="float: left; margin-right: 10px;"><input type="checkbox" id="friday" name="weekday" value="6" style="margin: 0px 5px 0px 0px;">Friday</div>
					<div style="float: left; margin-right: 10px;"><input type="checkbox" id="saturday" name="weekday" value="7" style="margin: 0px 5px 0px 0px;">Saturday</div>
					<div style="float: left; margin-right: 10px;"><input type="checkbox" id="sunday" name="weekday" value="1" style="margin: 0px 5px 0px 0px;">Sunday</div>
				</div>
				
			</div>
			<div style="margin: 30px 0px 0px 30px; clear: both;">
				<div class="cb" style="margin: 0px 0px 20px 0px;">			
					Select employees to apply shift&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="selectAllCheckBox" value="">Select All Employee				
				</div>
				
				<div id="sidebar" style="width: 800px;">
					<div id="scroller" style="width: 800px;"></div>
				</div>
			</div>
			
			
			
						
			<div class="cb" style="height: 20px;"></div>
			<div style="float: left; margin-left: 30px;">
				<input id="addEmployeeButton" value="Submit Schedule" border="0" onclick="javascript:saveSchedule();"> 
				<input id="addEmployeeButton" value="Clear" border="0" type="reset" style="padding: 0px 30px 0px 30px;">
			</div>
			
			
			</form>
		</div>
	</div>	
</div>
<div style=""><c:import url="footer.jsp" /></div>
</body>
</body>
</html>