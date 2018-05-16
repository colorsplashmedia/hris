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

	function clickSearchResult(empid) {		
		
		
		$('#empNo').html(empSearchMap[empid].empno);
		$('#fullname').html(empSearchMap[empid].lastname + ", " + empSearchMap[empid].firstname);		
		$('#empId').val(empid);
		$('#saveMethod').val("ADD");
	}
	
	function saveSchedule() {	
		
		var scheduleDate = document.getElementById("scheduleDate").value;		
		var shiftingScheduleId = document.getElementById("shiftingScheduleDropDownID").value;
		
		if(scheduleDate == ""){
			alert("Schedule Date is a required field.");
			return;
		}		
		
		if(shiftingScheduleId == ""){
			alert("Shift Schedule is a required field.");
			return;
		}
		
		$.ajax({
			type:"POST",
			url:"/hris/SaveEmployeeScheduleAction",
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
	<div  style="margin: 0px auto 0 auto; width: 430px;">	
		
	  	<div style="margin: 0px auto; border: 1px solid black; height: 470px;">
			<div id="resolutionHeader">Add Employee Shift</div>
			<input type="hidden" name="empId" id="empId" value="">
			<input type="hidden" name="empIdLoggedIn" id="empIdLoggedIn" value="${employeeLoggedIn.empId}" />
			<form method="POST" id="addEmpShiftForm" name="addEmpShiftForm" action="SaveEmployeeScheduleNewAction">			
			
			<input type="hidden" name="saveMethod" id="saveMethod" value="">			
			<input type="hidden" name="empScheduleId" id="empScheduleId" value="">
			
			<div>
				<table style="margin-left: 30px;">
					<tr>
						<td>Shift Date</td>
						<td>
							<input type="text" id="scheduleDate" name="scheduleDate" class="useDPicker" placeholder="Date" size="23" />
						</td>
						
					</tr>
					<tr>
						<td width="120px">Shift Schedule</td>
						<td>
							<select name="shiftingScheduleId" id="shiftingScheduleDropDownID" style="width: 180px; height: 35px; background: #fff; border: 1px solid #52833b; margin-top: 5px; color: black;">
							</select>
						</td>
					</tr>
					
				</table>
			
			</div>
			
			<div style="margin-left: 30px;">
				<div class="cb" style="margin: 0px 0px 20px 0px;">			
					Select employees to apply shift&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="selectAllCheckBox" value="">Select All				
				</div>
				
				<div id="sidebar">
					<div id="scroller"></div>
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