<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Employee Schedule Change Request</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/styleTables.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />
<script type="text/javascript" src="js/common.js"></script>
<script src="js/jquery-1.10.2.js"></script>

<script src="js/jquery-ui-1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="css/datePickerStyle.css">
<link href="jtables/jquery-ui.css" rel="stylesheet" type="text/css" />

<script>
  $(function() {
	  $( "input[id^='datepicker']").datepicker({
	        changeMonth: true,
	        changeYear: true,
	        beforeShow: function(input, inst){	            	    	
	            $(".ui-datepicker").css('font-size', 11);
	     	  },
	        dateFormat: 'mm/dd/yy',
	  	  	yearRange: '1910:2100'
	      });
    
    
  });
</script>

<script>
	$(document).ready(function() {	
		
		//$('#timeInHrs').timepicker({ 'timeFormat': 'H:i' });
		//$('#timeOutHrs').timepicker({ 'timeFormat': 'H:i' });
		
		//clockDate
		
		//$('#datepicker1').val();
		
		
		
		$('#resolutionButton').click(function() {
			document.resolutionForm.submit();
		});	
		
		
		populateShiftingScheduleDropDown();

	});
	
function resolveTimeEntry(timeEntryId, empId, shiftScheduleId){
		
		
		
		var id = "#dialog";			
		
		//Get the screen height and width
		var maskHeight = $(document).height();
		var maskWidth = $(window).width();

		//Set heigth and width to mask to fill up the whole screen
		$('#mask').css({
			'width' : maskWidth,
			'height' : maskHeight
		});

		//transition effect		
		$('#mask').fadeIn(1000);
		$('#mask').fadeTo("slow", 0.8);

		//Get the window height and width
		var winH = $(window).height();
		var winW = $(window).width();

		//Set the popup window to center
		$(id).css('top', winH / 2 - $(id).height() / 2);
		$(id).css('left', winW / 2 - $(id).width() / 2);

		//transition effect
		$(id).fadeIn(2000);
		
		
		$('#empId').val(empId);
		$('#shiftScheduleId').val(shiftScheduleId);
		$('#timeEntryId').val(timeEntryId);
		
	}
	
	
</script>



</head>
<body>
	<div>
		<c:import url="header.jsp" />
	</div>
	<div id="content">

		<div style="height: 350px; width: 400px; margin: 0px auto; background-color: #E8EDFF;">
				<form method="POST" id="resolutionForm" name="resolutionForm" action="EmpScheduleChangeRqstAction">
				
								
				
				<input type="hidden" id="empId" name="empId"  value="${employeeLoggedIn.empId}" />
				<input type="hidden" id="resolvedBy" name="resolvedBy"  value="${employeeLoggedIn.empId}" />
				<div id="resolutionHeader">Employee Shift Change Request</div>
				
				<div class="resolutionText" style="margin-left: 25px;float: left">
					<div class="datepickerTxt" style="width:110px;">Date From</div>					
				</div>
				<div class="datepicker"><input type="text" id="datepicker1" name="clockDate" value="${param.clockDate}" style="width:180px;"></div>
				<div class="cb"></div>
				<div class="resolutionText" style="margin-left: 25px;float: left">
					<div class="datepickerTxt" style="width:90px;">Shift Schedule</div>					
				</div>
				<div class="resolutionText" style="float: left">
					<select name="shiftScheduleId" id="shiftingScheduleDropDownID"
						style="width: 180px; float: left; height: 35px; background: #fff; border: 1px solid #52833b; margin-top: 5px; color: black;">
					</select>
				</div>
				<div class="cb"></div>
				<div style="margin: 0px 0px 0px 25px;">
					Update Schedule Reason<br />
					<!-- 
					<textarea rows="6" cols="45" name="resolutionRemarks"></textarea>
					 -->
					<textarea rows="6" cols="61" id="resolutionRemarks" name="resolutionRemarks" placeholder="Update Schedule Reason" style="width: 95%; margin: 10px 0px 20px 0px;"></textarea>
					<br />
				</div>
				<div style="width: 400px; margin: 0 auto;">
					&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
					<input id="resolutionButton" value="Submit Request" border="0" > 
					<input id="addEmployeeButton" value="Clear" border="0" type="reset" style="padding: 0px 50px 0px 50px;">
				</div>
				</form>
			</div>

		

	</div>
	<div>
		<c:import url="footer.jsp" />
	</div>
</body>
</html>