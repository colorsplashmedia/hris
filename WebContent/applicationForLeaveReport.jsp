<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>APPLICATION FOR LEAVE</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" type="text/css" href="css/navstyle.css" />
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link rel="stylesheet"  title="formal" href="css/tableStyle.css">
<link rel="stylesheet" href="css/datePickerStyle.css">
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />

<script type="text/javascript" src="js/jquery-ui-1.10.0.min.js"></script>
<script src="js/jquery-ui-1.11.4/jquery-ui.js"></script>
<script src="js/jquery-1.10.2.js"></script>
<script src="js/common.js"></script>
<script type="text/javascript" src="js/moment.min.js"></script>



<script src="js/jquery-ui-1.11.4/jquery-ui.js"></script>


<!-- Pagination -->
<script type="text/javascript">



$(document).ready(function() {	
	var empId = '${employeeLoggedIn.empId}';	
	
	if(empId.length == 0){
		alert("You are not allowed to view the page. Please login.");
		window.location = "LogoutAction";
		return;
	}
	
	var isAllowed = 'NO';
	var printButtonEnabled = 'NO';
	var exportButtonEnabled = 'NO';
		
	initDropDown();	
});	

function initDropDown() {		
	populateUnitDropDown();
}


function clearFields () {		
	
}

function viewReport (){
	
	var signatory1 = document.getElementById("signatory1").value;
	var signatory2 = document.getElementById("signatory2").value;	
	var signatory3 = document.getElementById("signatory3").value;	
	var leaveType = document.getElementById("leaveType").value;	
	var location = document.getElementById("location").value;	
	var commutation = document.getElementById("commutation").value;	
	var noOfDays = document.getElementById("noOfDays").value;	
	var specify1 = document.getElementById("specify1").value;	
	var specify2 = document.getElementById("specify2").value;	
	var dateFrom = document.getElementById("datepicker1").value;
	var dateTo = document.getElementById("datepicker2").value;
	
	if(dateFrom == ""){
		alert("Date From is a mandatory field.");
		return;
	}
	
	if(dateTo == ""){
		alert("Date To is a mandatory field.");
		return;
	} 
	
	if(noOfDays == ""){
		alert("No. of Days is a mandatory field.");
		return;
	} 
	
	
	if(leaveType == ""){
		alert("Leave Type is a mandatory field.");
		return;
	}
	
	if(location == ""){
		alert("Location is a mandatory field.");
		return;
	}
	
	
	
	if(signatory1 == ""){
		alert("Section Head is a mandatory field.");
		return;
	}
	
	if(signatory2 == ""){
		alert("Division Head is a mandatory field.");
		return;
	}
	
		    		    	
	url = "/hris/ActionPdfExportApplicationForLeaveReport?forExport=N&signatory1="+signatory1+"&signatory2="+signatory2+"&signatory3="+signatory3+"&dateFrom="+dateFrom+"&dateTo="+dateTo+"&leaveType="+leaveType+"&location="+location+"&commutation="+commutation+"&noOfDays="+noOfDays+"&specify1="+specify1+"&specify2="+specify2;
	swidth = screen.availWidth;
	sheight = screen.availHeight;
	
	openNewPopUpWindow(url, swidth, sheight)
	
}

function exportReport (){
	
	var signatory1 = document.getElementById("signatory1").value;
	var signatory2 = document.getElementById("signatory2").value;	
	var signatory3 = document.getElementById("signatory3").value;	
	var leaveType = document.getElementById("leaveType").value;	
	var location = document.getElementById("location").value;	
	var commutation = document.getElementById("commutation").value;	
	var noOfDays = document.getElementById("noOfDays").value;	
	var specify1 = document.getElementById("specify1").value;	
	var specify2 = document.getElementById("specify2").value;	
	var dateFrom = document.getElementById("datepicker1").value;
	var dateTo = document.getElementById("datepicker2").value;
	
	if(dateFrom == ""){
		alert("Date From is a mandatory field.");
		return;
	}
	
	if(dateTo == ""){
		alert("Date To is a mandatory field.");
		return;
	} 
	
	if(noOfDays == ""){
		alert("No. of Days is a mandatory field.");
		return;
	} 
	
	
	if(leaveType == ""){
		alert("Leave Type is a mandatory field.");
		return;
	}
	
	if(location == ""){
		alert("Location is a mandatory field.");
		return;
	}
	
	
	
	if(signatory1 == ""){
		alert("Section Head is a mandatory field.");
		return;
	}
	
	if(signatory2 == ""){
		alert("Division Head is a mandatory field.");
		return;
	}
	
	  	
		    		    	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/ActionPdfExportApplicationForLeaveReport",
		
		data: "forExport=N&signatory1="+signatory1+"&signatory2="+signatory2+"&signatory3="+signatory3+"&dateFrom="+dateFrom+"&dateTo="+dateTo+"&leaveType="+leaveType+"&location="+location+"&commutation="+commutation+"&noOfDays="+noOfDays+"&specify1="+specify1+"&specify2="+specify2,
		cache : false,
		async : false,
		success : function(data) {
			window.location.href = "report/" + data;
		},
		error : function(data) {
			alert('error: ' + data);
		}

	});  
	
}





</script>

<style type="text/css">

input,select { 
	border: 1px solid #52833b;    
    padding: 7.5px;
    background-color: white;
    margin: 5px 0px 0px 0px;
    font: 12px Arial, Helvetica, sans-serif;
	color: black;
}

</style>



</head>
<body>
<div><c:import url="header.jsp" /></div>
<div>		
	<div id="content">
		<input type="hidden" name="empIdLoggedIn" id="empIdLoggedIn" value="${employeeLoggedIn.empId}" />
 		
		<div style="width: 730px; margin: 0 auto; border: 1px solid black; height: 400px; margin-bottom: 20px;">
			<div style="background-color: black; color: white; padding: 10px;">APPLICATION FOR LEAVE</div>
			
			<div class="dataEntryText" style="text-indent: 20px;">Date From</div>
			<div class="dataEntryInputRequisition"><input type="text" id="datepicker1" name="datepicker1" class="useDPicker" style="width:165px;" value="${param.datepicker1}" placeholder="Date From" /></div>
			<div class="dataEntryText">Date To</div>			
			<div><input type="text" id="datepicker2" name="datepicker2"  value="${param.datepicker2}" class="useDPicker" style="width:165px;" placeholder="Date To" /></div>
			
			<div class="cb"></div>		
			<div class="cb"></div>
			<div class="dataEntryText" style="width:120px;text-indent: 20px;">Leave Type</div>
			<div class="dataEntryInput">
				<select name="leaveType" id="leaveType" style="width: 165px;" >			    													
					<option selected="selected" value="">Select Leave Type</option>									
					<option value="SL">SICK LEAVE</option>
					<option value="VE">VACATION LEAVE - For Employment</option>
					<option value="VO">VACATION LEAVE - For Other Purposes (Specify)</option>
					<option value="OL">OTHER</option>
				</select>			    	
			</div>
			<div class="dataEntryText">Specify Reason</div>			
			<div><input type="text" id="specify1" name="specify1"  value="${param.specify1}" style="width:165px;" placeholder="Specify Reason For Leave" /></div>
			<div class="cb"></div>
			<div class="dataEntryText" style="width:120px;text-indent: 20px;">Location</div>
			<div class="dataEntryInput">
				<select name="location" id="location" style="width: 165px;" >			    													
					<option selected="selected" value="">Select Location</option>									
					<option value="PH">Philippines</option>
					<option value="AB">Abroad (Specify)</option>
				</select>			    	
			</div>
			<div class="dataEntryText">Specify Location</div>			
			<div><input type="text" id="specify2" name="specify2"  value="${param.specify2}" style="width:165px;" placeholder="Specify Reason For Leave" /></div>
			<div class="cb"></div>
			<div class="dataEntryText" style="width:120px;text-indent: 20px;">Commutation</div>
			<div class="dataEntryInput">
				<select name="commutation" id="commutation" style="width: 165px;" >			    													
					<option selected="selected" value="">Select Commutation</option>									
					<option value="R">Requested</option>
					<option value="N">Not Requested</option>
				</select>			    	
			</div>
			<div class="dataEntryText">No. of Days</div>			
			<div><input type="text" id="noOfDays" name="noOfDays"  value="${param.noOfDays}" style="width:165px;" placeholder="No. of Days Applied For" /></div>
			<div class="cb"></div>
			<div style="background-color: black; color: white; padding: 10px; margin-top: 20px;">REPORT PARAMETERS</div>
			<div class="dataEntryText" style="text-indent: 20px; width: 240px;">Section Head</div>
			<div class="dataEntryInputRequisition"><input type="text" id="signatory1" name="signatory1" style="width:385px;" value="${param.signatory1}" placeholder="Section Head" /></div>
			<div class="cb"></div>
			<div class="dataEntryText" style="text-indent: 20px; width: 240px;">Supervising Administrative Officer</div>
			<div class="dataEntryInputRequisition"><input type="text" id="signatory2" name="signatory2" style="width:385px;" value="${param.signatory2}" placeholder="Supervising Administrative Officer" /></div>
			<div class="cb"></div>
			<div class="dataEntryText" style="text-indent: 20px; width: 240px;">Chief Administrative Officer</div>			
			<div><input type="text" id="signatory3" name="signatory3"  value="${param.signatory3}" style="width:385px;" value="${param.signatory3}" placeholder="Chief Administrative Officer" /></div>
			<div class="cb"></div>
			
			<div class="cb">
			
				<div id="buttonPlaceHolderPrint"><div style="margin: 15px 0px 0px 15px; cursor: pointer; float: left;" class="employeeButton" onClick="viewReport();">View Report</div></div>
				<div id="buttonPlaceHolderExport"><div style="margin: 15px 0px 0px 15px; cursor: pointer; float: left;" class="employeeButton" onClick="exportReport();">Export Report</div></div>
				<div style="margin: 15px 0px 0px 15px; cursor: pointer; float: left;" class="employeeButton" onclick="clearFields();">Clear</div>					
			</div>			
		</div>
		<div class="cb"></div>		
	</div>
	
</div>	
<div style=""><c:import url="footer.jsp" /></div>
</body>
</html>