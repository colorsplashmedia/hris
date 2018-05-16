<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<title>Employee Reports</title>
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />
<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="jtables/jquery-ui-1.10.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link rel="stylesheet" href="css/datePickerStyle.css">
<link href="jtables/jquery-ui.css" rel="stylesheet" type="text/css" />





<script type="text/javascript">
	function openApplicationWindow(appId){		
		//alert("pdf app ID: " + appId);
		var swidth = 400;
	    var sheight = 500;
	    var url = "";
	    var sectionId = document.getElementById("sectionId").value;	 
	    var empId = document.getElementById("empId").value; 
	    var digitsOnly = new RegExp(/^[0-9]+$/);
	    
	    //alert("pdf empId: " + empId);
	    
	    if(appId == "1"){
	    	var payrollPeriodId = document.getElementById("payrollPeriodDropDownID").value;
	    	
	    	if(payrollPeriodId == ""){
	    		alert("Payroll Period is a mandatory field.");
	    		return;
	    	}	    	
	    		    		    	
	    	url = "/hris/ActionPdfExportPayslipReport?payrollPeriodId="+payrollPeriodId+"&singlePayslip=Y&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;	
	    	
	    	openNewPopUpWindow(url, swidth, sheight)
	    } else if(appId == "2"){
	    	
	    	var month = document.getElementById("month").value;
	    	var year = document.getElementById("year").value;
	    	
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
	    	
	    	
	    	
	    	url = "/hris/ActionPdfExportDTRReport?empId="+empId+"&month="+month+"&year="+year+"&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;

	    	
	    	
	    	openNewPopUpWindow(url, swidth, sheight)
	    	
	    	
	    	
	    	
	    } else if(appId == "3"){
	    	
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
	    	
	    	
	    	
	    	url = "/hris/ActionPdfEmpLeaveReport?dateFrom="+dateFrom+"&dateTo="+dateTo+"&singlePayslip=Y&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;
	    	
	    	

	    	if ((isInvalid(dateFrom) && isInvalid(dateTo))===false) {
	    		
	    		
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    	
	    } else if(appId == "4"){
	    	
	    	var dateFrom = document.getElementById("datepicker3").value;
	    	var dateTo = document.getElementById("datepicker4").value;
	    	
	    	if(dateFrom == ""){
	    		alert("Date From is a mandatory field.");
	    		return;
	    	}
	    	
	    	if(dateTo == ""){
	    		alert("Date To is a mandatory field.");
	    		return;
	    	}
	    	
	    	
	    	
	    	url = "/hris/ActionPdfExportOvertimeSummaryReport?dateFrom="+dateFrom+"&dateTo="+dateTo+"&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;
	    	
	    	

	    	if ((isInvalid(dateFrom) && isInvalid(dateTo))===false) {
	    		
	    		
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    	
	    } else if(appId == "5"){
	    	
	    	var dateFrom = document.getElementById("datepicker5").value;
	    	var dateTo = document.getElementById("datepicker6").value;
	    	
	    	if(dateFrom == ""){
	    		alert("Date From is a mandatory field.");
	    		return;
	    	}
	    	
	    	if(dateTo == ""){
	    		alert("Date To is a mandatory field.");
	    		return;
	    	}
	    	
	    	
	    	
	    	url = "/hris/ActionPdfExportOutOfOfficeSummaryReport?dateFrom="+dateFrom+"&dateTo="+dateTo+"&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;
	    	
	    	

	    	if ((isInvalid(dateFrom) && isInvalid(dateTo))===false) {
	    		
	    		
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    	
	    } else if(appId == "6"){
	    	
	    	var dateFrom = document.getElementById("datepicker7").value;
	    	var dateTo = document.getElementById("datepicker8").value;
	    	
	    	if(dateFrom == ""){
	    		alert("Date From is a mandatory field.");
	    		return;
	    	}
	    	
	    	if(dateTo == ""){
	    		alert("Date To is a mandatory field.");
	    		return;
	    	}
	    	
	    	
	    	
	    	url = "/hris/ActionPdfExportAttendanceSummaryReport?dateFrom="+dateFrom+"&dateTo="+dateTo+"&forIndividual=Y&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;
	    	
	    	

	    	if ((isInvalid(dateFrom) && isInvalid(dateTo))===false) {
	    		
	    		
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    	
	    } else if(appId == "7"){
	    	
	    	var dateFrom = document.getElementById("datepicker9").value;
	    	var dateTo = document.getElementById("datepicker10").value;
	    	
	    	if(dateFrom == ""){
	    		alert("Date From is a mandatory field.");
	    		return;
	    	}
	    	
	    	if(dateTo == ""){
	    		alert("Date To is a mandatory field.");
	    		return;
	    	}
	    	
	    	
	    	
	    	url = "/hris/ActionPdfExportEmployeeScheduleReport?dateFrom="+dateFrom+"&dateTo="+dateTo+"&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;
	    	
	    	

	    	if ((isInvalid(dateFrom) && isInvalid(dateTo))===false) {
	    		
	    		
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    	
	    } else if(appId == "8"){
	    	
	    	var dateFrom = document.getElementById("datepicker11").value;
	    	var dateTo = document.getElementById("datepicker12").value;
	    	
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
	    	
	    	

	    	if ((isInvalid(dateFrom) && isInvalid(dateTo))===false) {
	    		
	    		
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    	
	    }  else if(appId == "9"){
	    	
	    	var dateFrom = document.getElementById("datepicker13").value;
	    	var dateTo = document.getElementById("datepicker14").value;
	    	
	    	if(dateFrom == ""){
	    		alert("Date From is a mandatory field.");
	    		return;
	    	}
	    	
	    	if(dateTo == ""){
	    		alert("Date To is a mandatory field.");
	    		return;
	    	}
	    	
	    	
	    	
	    	url = "/hris/ActionPdfExportContributionWithholdingTaxReport?dateFrom="+dateFrom+"&dateTo="+dateTo+"&forIndividual=Y&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;
	    	
	    	

	    	if ((isInvalid(dateFrom) && isInvalid(dateTo))===false) {
	    		
	    		
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    	
	    }  else if(appId == "10"){
	    	
	    	var dateFrom = document.getElementById("datepicker15").value;
	    	var dateTo = document.getElementById("datepicker16").value;
	    	
	    	if(dateFrom == ""){
	    		alert("Date From is a mandatory field.");
	    		return;
	    	}
	    	
	    	if(dateTo == ""){
	    		alert("Date To is a mandatory field.");
	    		return;
	    	}
	    	
	    	
	    	
	    	url = "/hris/ActionPdfExportContributionPHICReport?dateFrom="+dateFrom+"&dateTo="+dateTo+"&forIndividual=Y&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;
	    	
	    	

	    	if ((isInvalid(dateFrom) && isInvalid(dateTo))===false) {
	    		
	    		
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    	
	    } else if(appId == "11"){
	    	
	    	var dateFrom = document.getElementById("datepicker17").value;
	    	var dateTo = document.getElementById("datepicker18").value;
	    	
	    	if(dateFrom == ""){
	    		alert("Date From is a mandatory field.");
	    		return;
	    	}
	    	
	    	if(dateTo == ""){
	    		alert("Date To is a mandatory field.");
	    		return;
	    	}
	    	
	    	
	    	
	    	url = "/hris/ActionPdfExportContributionHDMFReport?dateFrom="+dateFrom+"&dateTo="+dateTo+"&forIndividual=Y&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;
	    	
	    	

	    	if ((isInvalid(dateFrom) && isInvalid(dateTo))===false) {
	    		
	    		
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    	
	    } else if(appId == "12"){
	    	
	    	var dateFrom = document.getElementById("datepicker19").value;
	    	var dateTo = document.getElementById("datepicker20").value;
	    	
	    	if(dateFrom == ""){
	    		alert("Date From is a mandatory field.");
	    		return;
	    	}
	    	
	    	if(dateTo == ""){
	    		alert("Date To is a mandatory field.");
	    		return;
	    	}
	    	
	    	
	    	
	    	url = "/hris/ActionPdfExportContributionSummaryReport?dateFrom="+dateFrom+"&dateTo="+dateTo+"&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;
	    	
	    	

	    	if ((isInvalid(dateFrom) && isInvalid(dateTo))===false) {
	    		
	    		
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    	
	    }
	    
	}
	
	
	function exportReport(appId){		
		
	    var url = "";
	    var empId = document.getElementById("empId").value;   
	    
	    if(appId == "1"){
	    	var payrollPeriodId = document.getElementById("payrollPeriodDropDownID").value;
	    	
	    	if(payrollPeriodId == ""){
	    		alert("Payroll Period is a mandatory field.");
	    		return;
	    	}	
	    	
	    	var oAjaxCall = $.ajax({
				type : "POST",
				url : "/hris/ActionPdfExportPayslipReport",				
				
				data: "payrollPeriodId=" + payrollPeriodId +"&singlePayslip=Y&forExport=Y",
				cache : false,
				async : false,
				success : function(data) {
					window.location.href = "report/" + data;
				},
				error : function(data) {
					alert('error: ' + data);
				}

			});
	    		    		    	
	    	
	    } else if(appId == "2"){
	    	
	    	var month = document.getElementById("month").value;
	    	var year = document.getElementById("year").value;
	    	
	    	if(year == ""){
	    		alert("Year is a mandatory field.");
	    		return;
	    	}
	    	
	    	
	    	
	    	var oAjaxCall = $.ajax({
				type : "POST",
				url : "/hris/ActionPdfExportDTRReport",
				
				data: "empId="+empId+"&month="+month+"&year="+year+"&forExport=Y",
				cache : false,
				async : false,
				success : function(data) {
					window.location.href = "report/" + data;
				},
				error : function(data) {
					alert('error: ' + data);
				}

			});    	
	    	
	    	
	    } else if(appId == "3"){
	    	
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
				url : "/hris/ActionPdfEmpLeaveReport",
				
				data: "dateFrom="+dateFrom+"&dateTo="+dateTo+"&singlePayslip=Y&forExport=Y",
				cache : false,
				async : false,
				success : function(data) {
					
					
					window.location.href = "report/" + data;
				},
				error : function(data) {
					alert('error: ' + data);
				}

			});    	
	    	
	    	
	    	
	    } else if(appId == "4"){
	    	
	    	var dateFrom = document.getElementById("datepicker3").value;
	    	var dateTo = document.getElementById("datepicker4").value;
	    	
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
				url : "/hris/ActionPdfExportOvertimeSummaryReport",
				
				data: "dateFrom="+dateFrom+"&dateTo="+dateTo+"&forExport=Y",
				cache : false,
				async : false,
				success : function(data) {
					
					
					window.location.href = "report/" + data;
				},
				error : function(data) {
					alert('error: ' + data);
				}

			});    	
	    	
	    	
	    	
	    } else if(appId == "5"){
	    	
	    	var dateFrom = document.getElementById("datepicker5").value;
	    	var dateTo = document.getElementById("datepicker6").value;
	    	
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
				url : "/hris/ActionPdfExportOutOfOfficeSummaryReport",
				
				data: "dateFrom="+dateFrom+"&dateTo="+dateTo+"&forExport=N",
				cache : false,
				async : false,
				success : function(data) {
					
					
					window.location.href = "report/" + data;
				},
				error : function(data) {
					alert('error: ' + data);
				}

			});    	
	    	
	    	
	    	
	    } else if(appId == "6"){
	    	
	    	var dateFrom = document.getElementById("datepicker7").value;
	    	var dateTo = document.getElementById("datepicker8").value;
	    	
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
				url : "/hris/ActionPdfExportAttendanceSummaryReport",
				
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
	    	
	    	
	    	
	    } else if(appId == "7"){
	    	
	    	var dateFrom = document.getElementById("datepicker9").value;
	    	var dateTo = document.getElementById("datepicker10").value;
	    	
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
				url : "/hris/ActionPdfExportEmployeeScheduleReport",
				
				data: "dateFrom="+dateFrom+"&dateTo="+dateTo+"&forExport=N",
				cache : false,
				async : false,
				success : function(data) {
					
					
					window.location.href = "report/" + data;
				},
				error : function(data) {
					alert('error: ' + data);
				}

			});    	
	    	
	    	
	    	
	    } else if(appId == "8"){
	    	
	    	var dateFrom = document.getElementById("datepicker11").value;
	    	var dateTo = document.getElementById("datepicker12").value;
	    	
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
	    	
	    	
	    	
	    } else if(appId == "9"){
	    	
	    	var dateFrom = document.getElementById("datepicker13").value;
	    	var dateTo = document.getElementById("datepicker14").value;
	    	
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
				url : "/hris/ActionPdfExportContributionWithholdingTaxReport",
				
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
	    	
	    	
	    	
	    } else if(appId == "10"){
	    	
	    	var dateFrom = document.getElementById("datepicker15").value;
	    	var dateTo = document.getElementById("datepicker16").value;
	    	
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
				url : "/hris/ActionPdfExportContributionPHICReport",
				
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
	    	
	    	
	    	
	    } else if(appId == "11"){
	    	
	    	var dateFrom = document.getElementById("datepicker17").value;
	    	var dateTo = document.getElementById("datepicker18").value;
	    	
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
				url : "/hris/ActionPdfExportContributionHDMFReport",
				
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
	    	
	    	
	    	
	    } else if(appId == "12"){
	    	
	    	var dateFrom = document.getElementById("datepicker19").value;
	    	var dateTo = document.getElementById("datepicker20").value;
	    	
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
				url : "/hris/ActionPdfExportContributionSummaryReport",
				
				data: "dateFrom="+dateFrom+"&dateTo="+dateTo+"&forExport=N",
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
	    
	}
	
	
	function isInvalid(variabols) {
		if (typeof(variabols) === "undefined" || variabols === null || variabols === "") {
			return true;
		}
		return false;
	}
	
</script>

<script type="text/javascript">
	$(document).ready(function() {
		populatePayrollPeriod();		
	});	
</script>

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

</head>
<body>	
<div><c:import url="header.jsp" /></div>	
<input type="hidden" name="empId" id="empId" value="${employeeLoggedIn.empId}" />
<input type="hidden" name="sectionId" id="sectionId" value="${employeeLoggedIn.sectionId}" />		
<div id="content"> 
	<div id="reportsArea">							
		<input type="hidden" name="empIdLoggedIn" id="empIdLoggedIn" value="${employeeLoggedIn.empId}" />
		<div class="reportItem">				
			<div class="reportsName">PAYSLIP</div>	
			<div class="dateArea">
				<div class="datepickerTxt" style="width:120px;">Payroll Period</div>
				<div style="float: left; margin-right: 20px;">
					<select name="payrollPeriodId" id="payrollPeriodDropDownID" style="width:396px; height:31px; background:#fff; border: 1px solid #52833b; margin-top: 5px; color: black;" >			    													
							
					</select>
				</div>				
			</div>		
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('1');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('1');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('1');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
		</div>
		<div style="clear: both;"></div>
		<div class="reportItem">			
			<div class="reportsName">DAILY TIME RECORD</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:120px;">Month</div>
				<div class="datepicker">
					<select name="month" id="month" style="width:150px; height:31px; background:#fff; border: 1px solid #52833b; margin-top: 5px; color: black;" >													
						<option value="1">January</option>
						<option value="2">February</option>
						<option value="3">March</option>
						<option value="4">April</option>
						<option value="5">May</option>
						<option value="6">June</option>
						<option value="7">July</option>
						<option value="8">August</option>
						<option value="9">September</option>
						<option value="10">October</option>
						<option value="11">November</option>
						<option value="12">December</option>
					</select>
				</div>
				<div class="datepickerTxt" style="width:60px;">Year</div>
				<div class="datepicker"><input type="text" id="year" name="year" style="width:150px;"></div>
			</div>			
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('2');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('2');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('2');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
		</div>		
		<div style="clear: both;"></div>
		
		<div class="reportItem">			
			<div class="reportsName">LEAVE REPORT</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:120px;">Date From</div>
				<div class="datepicker"><input type="text" id="datepicker1" style="width:150px;"></div>
				<div class="datepickerTxt" style="width:60px;">Date To</div>
				<div class="datepicker"><input type="text" id="datepicker2" style="width:150px;"></div>
			</div>			
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('3');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('3');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('3');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
		</div>		
		
		
		
		<div style="clear: both;"></div>
		<div class="reportItem">			
			<div class="reportsName">OVERTIME REPORT</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:120px;">Date From</div>
				<div class="datepicker"><input type="text" id="datepicker3" style="width:150px;"></div>
				<div class="datepickerTxt" style="width:60px;">Date To</div>
				<div class="datepicker"><input type="text" id="datepicker4" style="width:150px;"></div>
			</div>				
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('4');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('4');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('4');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
		</div>		
		
		<div style="clear: both;"></div>
		
		<div class="reportItem">			
			<div class="reportsName">OUT OF OFFICE REPORT</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:120px;">Date From</div>
				<div class="datepicker"><input type="text" id="datepicker5" style="width:150px;"></div>
				<div class="datepickerTxt" style="width:60px;">Date To</div>
				<div class="datepicker"><input type="text" id="datepicker6" style="width:150px;"></div>
			</div>			
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('5');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('5');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('5');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
		</div>		
		
		<div style="clear: both;"></div>
		
		<div class="reportItem">			
			<div class="reportsName">ATTENDANCE REPORT</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:120px;">Date From</div>
				<div class="datepicker"><input type="text" id="datepicker7" style="width:150px;"></div>
				<div class="datepickerTxt" style="width:60px;">Date To</div>
				<div class="datepicker"><input type="text" id="datepicker8" style="width:150px;"></div>
			</div>			
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('6');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('6');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('6');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
		</div>		
		
		<div style="clear: both;"></div>
		
		<div class="reportItem">			
			<div class="reportsName">SCHEDULE REPORT</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:120px;">Date From</div>
				<div class="datepicker"><input type="text" id="datepicker9" style="width:150px;"></div>
				<div class="datepickerTxt" style="width:60px;">Date To</div>
				<div class="datepicker"><input type="text" id="datepicker10" style="width:150px;"></div>
			</div>			
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('7');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('7');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('7');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
		</div>
		
		<div style="clear: both;"></div>
		
		<div class="reportItem">			
			<div class="reportsName">GSIS CONTRIBUTION REPORT</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:120px;">Date From</div>
				<div class="datepicker"><input type="text" id="datepicker11" style="width:150px;"></div>
				<div class="datepickerTxt" style="width:60px;">Date To</div>
				<div class="datepicker"><input type="text" id="datepicker12" style="width:150px;"></div>
			</div>			
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('8');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('8');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('8');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
		</div>
		
		<div style="clear: both;"></div>
		
		<div class="reportItem">			
			<div class="reportsName">TAX CONTRIBUTION REPORT</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:120px;">Date From</div>
				<div class="datepicker"><input type="text" id="datepicker13" style="width:150px;"></div>
				<div class="datepickerTxt" style="width:60px;">Date To</div>
				<div class="datepicker"><input type="text" id="datepicker14" style="width:150px;"></div>
			</div>				
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('9');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('9');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('9');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
		</div>
		
		<div style="clear: both;"></div>
		
		<div class="reportItem">			
			<div class="reportsName">PHIC CONTRIBUTION REPORT</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:120px;">Date From</div>
				<div class="datepicker"><input type="text" id="datepicker15" style="width:150px;"></div>
				<div class="datepickerTxt" style="width:60px;">Date To</div>
				<div class="datepicker"><input type="text" id="datepicker16" style="width:150px;"></div>
			</div>			
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('10');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('10');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('10');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
		</div>
		
		<div style="clear: both;"></div>
		
		<div class="reportItem">			
			<div class="reportsName">HDMF CONTRIBUTION REPORT</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:120px;">Date From</div>
				<div class="datepicker"><input type="text" id="datepicker17" style="width:150px;"></div>
				<div class="datepickerTxt" style="width:60px;">Date To</div>
				<div class="datepicker"><input type="text" id="datepicker18" style="width:150px;"></div>
			</div>			
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('11');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('11');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('11');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
		</div>
		
		<div style="clear: both;"></div>
		
		<div class="reportItem">			
			<div class="reportsName">CONTRIBUTION SUMMARY REPORT</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:120px;">Date From</div>
				<div class="datepicker"><input type="text" id="datepicker19" style="width:150px;"></div>
				<div class="datepickerTxt" style="width:60px;">Date To</div>
				<div class="datepicker"><input type="text" id="datepicker20" style="width:150px;"></div>
			</div>			
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('12');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('12');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('12');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			
		</div>
		
		<div style="clear: both;"></div>
		
		
		
		<div style="height: 50px;"></div>
								
	</div>
	<div style="height: 50px;"></div>
</div>
<div style=""><c:import url="footer.jsp" /></div>




	
		
</body>
</html>