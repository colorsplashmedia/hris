<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>CERTIFICATE OF EMPLOYMENT REPORT</title>
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
	populatePayrollPeriod();	
	populateEmployeeDropDown();
}


function clearFields () {		
	$('#payrollPeriodDropDownID option:first').prop('selected', true);	
}

function viewReport (){
	
	
	var signatory1 = document.getElementById("signatory1").value;	
	var position1 = document.getElementById("position1").value;
	var purpose = document.getElementById("purpose").value;
	var empId = document.getElementById("employeeDropDownID").value;	
	
	if(signatory1 == ""){
		alert("Signatory is a mandatory field.");
		return;
	}
	
	if(position1 == ""){
		alert("Position is a mandatory field.");
		return;
	}
	
	if(purpose == ""){
		alert("Purpose is a mandatory field.");
		return;
	}
	
	if(empId == ""){
		alert("Employee is a mandatory field.");
		return;
	}
		    		    	
	url = "/hris/ActionPdfExportCertificateOfEmploymentReport?forExport=N&signatory1="+signatory1+"&position1="+position1+"&purpose="+purpose+"&empId="+empId;
	swidth = screen.availWidth;
	sheight = screen.availHeight;	
	
	openNewPopUpWindow(url, swidth, sheight)
	
}

function exportReport (){
	
	var signatory1 = document.getElementById("signatory1").value;
	var purpose = document.getElementById("purpose").value;
	var position1 = document.getElementById("position1").value;
	var empId = document.getElementById("employeeDropDownID").value;	
	
	if(signatory1 == ""){
		alert("Signatory is a mandatory field.");
		return;
	}
	
	if(position1 == ""){
		alert("Position is a mandatory field.");
		return;
	}
	
	if(purpose == ""){
		alert("Purpose is a mandatory field.");
		return;
	}
	
	if(empId == ""){
		alert("Employee is a mandatory field.");
		return;
	}
		
		    		    	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/ActionPdfExportCertificateOfEmploymentReport",				
		
		data: "forExport=Y&signatory1="+signatory1+"&position1="+position1+"&purpose="+purpose+"&empId="+empId,
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
 		
		<div style="width: 730px; margin: 0 auto; border: 1px solid black; height: 200px; margin-bottom: 20px;">
			<div style="background-color: black; color: white; padding: 10px;">CERTIFICATE OF EMPLOYMENT REPORT</div>			
			
			<div class="dataEntryText" style="text-indent: 20px; width: 140px;">Name of Signatory</div>
			<div class="dataEntryInputRequisition"><input type="text" id="signatory1" name="signatory1" style="width:265px;" value="${param.signatory1}" placeholder="Name of Signatory" /></div>
			<div class="dataEntryText" style="width: 60px;">Position</div>			
			<div><input type="text" id="position1" name="position1"  value="${param.position1}" style="width:205px;" placeholder="Position" /></div>
			<div class="cb"></div>
			<div class="dataEntryText" style="text-indent: 20px; width: 140px;">Purpose</div>			
			<div><input type="text" id="purpose" name="purpose"  value="${param.purpose}" style="width:570px;" placeholder="Purpose" /></div>
			<div class="dataEntryText" style="text-indent: 20px; width: 140px;">Employee</div>			
			<div>
				<select name="empId" id="employeeDropDownID" style="width:570px;" >			    													
								
				</select>
			</div>
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