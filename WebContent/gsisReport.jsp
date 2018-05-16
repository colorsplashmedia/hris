<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>GSIS CONTRIBUTION REPORT</title>
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
	
}


function clearFields () {		
	
}

function viewReport (){
	
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
		    		    	
	url = "/hris/ActionPdfExportContributionGSISReport?dateFrom="+dateFrom+"&dateTo="+dateTo+"&forIndividual=Y&forExport=N";
	swidth = screen.availWidth;
	sheight = screen.availHeight;	
	
	openNewPopUpWindow(url, swidth, sheight)
	
}

function exportReport (){
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
		    		    	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/ActionPdfExportContributionGSISReport",
		
		data: "dateFrom="+dateFrom+"&dateTo="+dateTo+"&forIndividual=Y&forExport=N",
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
 		
		<div style="width: 730px; margin: 0 auto; border: 1px solid black; height: 130px; margin-bottom: 20px;">
			<div style="background-color: black; color: white; padding: 10px;">GSIS CONTRIBUTION REPORT</div>
			
			<div class="dataEntryText" style="text-indent: 20px;">Date From</div>
			<div class="dataEntryInputRequisition"><input type="text" id="datepicker1" name="datepicker1" class="useDPicker" style="width:165px;" value="${param.datepicker1}" placeholder="Date From" /></div>
			<div class="dataEntryText">Date To</div>			
			<div><input type="text" id="datepicker2" name="datepicker2"  value="${param.datepicker2}" class="useDPicker" style="width:165px;" placeholder="Date To" /></div>
			
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