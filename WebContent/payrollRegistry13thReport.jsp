<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>PAYROLL REGISTER 13TH MONTH REPORT</title>
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
	
	
	var year = document.getElementById("year").value;
	var digitsOnly = new RegExp(/^[0-9]+$/);
	
	if(year == ""){
		alert("Year is a mandatory field.");
		return;
	}
	
	if(digitsOnly.test(year)){
		if(year == "0"){
			alert("Year should be numberic and should be greater than 0.");
			return false;
		}
	} else {
		alert("Year should only be numeric and greater than 0.");
		return false;
	}    	
		    		    	
	url = "/hris/ActionPdfExportPayrollRegister13thMonthReport?year="+year+"&forExport=N";
	swidth = screen.availWidth;
	sheight = screen.availHeight;
	
	openNewPopUpWindow(url, swidth, sheight)
	
}

function exportReport (){
	
	var year = document.getElementById("year").value;
	var digitsOnly = new RegExp(/^[0-9]+$/);
	
	if(year == ""){
		alert("Year is a mandatory field.");
		return;
	}
	
	if(digitsOnly.test(year)){
		if(year == "0"){
			alert("Year should be numberic and should be greater than 0.");
			return false;
		}
	} else {
		alert("Year should only be numeric and greater than 0.");
		return false;
	}    	
		    		    	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/ActionPdfExportPayrollRegister13thMonthReport",
		
		data: "year="+year+"&forExport=Y",	    	
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
			<div style="background-color: black; color: white; padding: 10px;">PAYROLL REGISTER 13TH MONTH REPORT</div>
			
			
			<div class="dataEntryText" style="width:60px;text-indent: 20px;">Year</div>			
			<div><input type="text" id="year" name="year"  value="${param.year}" style="width:650px;" placeholder="Year" /></div>
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