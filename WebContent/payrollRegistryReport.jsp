<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>PAYROLL REGISTRY REPORT</title>
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
}


function clearFields () {		
	$('#payrollPeriodDropDownID option:first').prop('selected', true);	
}

function viewReportCover (){
	
	var payrollPeriodId = document.getElementById("payrollPeriodDropDownID").value;	
	
	if(payrollPeriodId == ""){
		alert("Payroll Period is a mandatory field.");
		return;
	}	    	
		    		    	
	url = "/hris/ActionPdfExportPayrollRegisterCoverReport?payrollPeriodId="+payrollPeriodId+"&forExport=N";
	swidth = screen.availWidth;
	sheight = screen.availHeight;	
	
	openNewPopUpWindow(url, swidth, sheight)
	
}

function viewReport (){
	
	var payrollPeriodId = document.getElementById("payrollPeriodDropDownID").value;
	var signatory1 = document.getElementById("signatory1").value;
	var signatory2 = document.getElementById("signatory2").value;
	var signatory3 = document.getElementById("signatory3").value;
	var signatory4 = document.getElementById("signatory4").value;
	
	var position1 = document.getElementById("position1").value;
	var position2 = document.getElementById("position2").value;
	var position3 = document.getElementById("position3").value;
	var position4 = document.getElementById("position4").value;
	
	if(payrollPeriodId == ""){
		alert("Payroll Period is a mandatory field.");
		return;
	}	    	
		    		    	
	url = "/hris/ActionPdfExportPayrollRegisterReport?payrollPeriodId="+payrollPeriodId+"&singlePayslip=Y&forExport=N&signatory1="+signatory1+"&signatory2="+signatory2+"&signatory3="+signatory3+"&signatory4="+signatory4+"&position1="+position1+"&position2="+position2+"&position3="+position3+"&position4="+position4;
	swidth = screen.availWidth;
	sheight = screen.availHeight;	
	
	openNewPopUpWindow(url, swidth, sheight)
	
}

function exportReport (){
	var payrollPeriodId = document.getElementById("payrollPeriodDropDownID").value;
	var signatory1 = document.getElementById("signatory1").value;
	var signatory2 = document.getElementById("signatory2").value;
	var signatory3 = document.getElementById("signatory3").value;
	var signatory4 = document.getElementById("signatory4").value;
	
	var position1 = document.getElementById("position1").value;
	var position2 = document.getElementById("position2").value;
	var position3 = document.getElementById("position3").value;
	var position4 = document.getElementById("position4").value;
	
	if(payrollPeriodId == ""){
		alert("Payroll Period is a mandatory field.");
		return;
	}	    	
		    		    	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/ActionPdfExportPayrollRegisterReport",				
		
		data: "payrollPeriodId=" + payrollPeriodId +"&singlePayslip=Y&forExport=Y&signatory1="+signatory1+"&signatory2="+signatory2+"&signatory3="+signatory3+"&signatory4="+signatory4+"&position1="+position1+"&position2="+position2+"&position3="+position3+"&position4="+position4,
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

function exportReportCover (){
	var payrollPeriodId = document.getElementById("payrollPeriodDropDownID").value;
	
	if(payrollPeriodId == ""){
		alert("Payroll Period is a mandatory field.");
		return;
	}	    	
		    		    	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/ActionPdfExportPayrollRegisterCoverReport",				
		
		data: "payrollPeriodId=" + payrollPeriodId +"&forExport=Y",
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
 		
		<div style="width: 730px; margin: 0 auto; border: 1px solid black; height: 350px; margin-bottom: 20px;">
			<div style="background-color: black; color: white; padding: 10px;">PAYROLL REGISTRY REPORT</div>
			
			<div class="dataEntryText" style="text-indent: 20px;">Payroll Period</div>			
			<div>
				<select name="payrollPeriodId" id="payrollPeriodDropDownID" style="width:490px;" >			    													
								
				</select>
			</div>
			
			<div class="cb"></div>
			<div style="background-color: black; color: white; padding: 10px; margin-top: 20px;">REPORT PARAMETERS</div>
			<div class="dataEntryText" style="text-indent: 20px;">1st Signatory</div>
			<div class="dataEntryInputRequisition"><input type="text" id="signatory1" name="signatory1" style="width:165px;" value="${param.signatory1}" placeholder="1st Signatory" /></div>
			<div class="dataEntryText">Position</div>			
			<div><input type="text" id="position1" name="position1"  value="${param.position1}" style="width:165px;" placeholder="Position" /></div>
			<div class="cb"></div>
			<div class="dataEntryText" style="text-indent: 20px;">2nd Signatory</div>
			<div class="dataEntryInputRequisition"><input type="text" id="signatory2" name="signatory2" style="width:165px;" value="${param.signatory2}" placeholder="2nd Signatory" /></div>
			<div class="dataEntryText">Position</div>			
			<div><input type="text" id="position2" name="position2"  value="${param.position2}" style="width:165px;" placeholder="Position" /></div>
			
			
			<div class="cb"></div>		
			<div class="dataEntryText" style="text-indent: 20px;">3rd Signatory</div>
			<div class="dataEntryInputRequisition"><input type="text" id="signatory3" name="signatory3" style="width:165px;" value="${param.signatory3}" placeholder="3rd Signatory" /></div>
			<div class="dataEntryText">Position</div>			
			<div><input type="text" id="position3" name="position3"  value="${param.position3}" style="width:165px;" placeholder="Position" /></div>
			<div class="cb"></div>
			<div class="dataEntryText" style="text-indent: 20px;">4th Signatory</div>
			<div class="dataEntryInputRequisition"><input type="text" id="signatory4" name="signatory4" style="width:165px;" value="${param.signatory4}" placeholder="4th Signatory" /></div>
			<div class="dataEntryText">Position</div>			
			<div><input type="text" id="position4" name="position4"  value="${param.position4}" style="width:165px;" placeholder="Position" /></div>
			<div class="cb"></div>		
			<div class="cb">
			
				<div id="buttonPlaceHolderPrint"><div style="margin: 15px 0px 0px 15px; cursor: pointer; float: left;" class="employeeButton" onClick="viewReport();">View Report</div></div>
				<div id="buttonPlaceHolderExport"><div style="margin: 15px 0px 0px 15px; cursor: pointer; float: left;" class="employeeButton" onClick="exportReport();">Export Report</div></div>
				<div id="buttonPlaceHolderPrint"><div style="margin: 15px 0px 0px 15px; cursor: pointer; float: left;" class="employeeButton" onClick="viewReportCover();">Print Cover</div></div>
				<div id="buttonPlaceHolderExport"><div style="margin: 15px 0px 0px 15px; cursor: pointer; float: left;" class="employeeButton" onClick="exportReportCover();">Export Cover</div></div>
	
								
			</div>			
		</div>
		<div class="cb"></div>		
	</div>
	
</div>	
<div style=""><c:import url="footer.jsp" /></div>
</body>
</html>