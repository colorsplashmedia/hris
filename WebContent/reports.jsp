<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<title>Payroll Reports</title>

<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="jtables/jquery-ui-1.10.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link rel="stylesheet" href="css/datePickerStyle.css">
<link href="jtables/jquery-ui.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />

<script type="text/javascript">
	function openApplicationWindow(appId){		
		
		var swidth = 400;
	    var sheight = 500;
	    var url = "";
	    var sectionId = document.getElementById("sectionId").value;	 
	    var empId = document.getElementById("empId").value;	
	    var digitsOnly = new RegExp(/^[0-9]+$/);
	    
	    if(appId == "1"){
	    	//var dateFrom = document.getElementById("datepicker1").value;
	    	//var dateTo = document.getElementById("datepicker2").value;
	    	
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
	    		    		    	
	    	url = "/hris/ActionPdfExportGeneralCaseRateReport?month="+month+"&year="+year+"&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;	
	    	if ((isInvalid(month) || isInvalid(year))===false) {
	    		openNewPopUpWindow(url, swidth, sheight)
	    	}
	    } else if(appId == "2"){
	    	var dateFrom = document.getElementById("datepicker3").value;
	    	var dateTo = document.getElementById("datepicker4").value;
	    	
	    	url = "/hris/ActionPdfExportGeneralProfessionalFeeReport?dateFrom="+dateFrom+"&dateTo="+dateTo+"&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;
	    	if ((isInvalid(dateFrom) || isInvalid(dateTo))===false) {	    	
	    		openNewPopUpWindow(url, swidth, sheight)
	    	}
	    } else if(appId == "0"){
	    	var payrollPeriodId = document.getElementById("payrollPeriodId2").value;
	    	url = "/hris/ActionPdfExportPayslipReport?payrollPeriodId="+payrollPeriodId+"&singlePayslip=N&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;
	    	if (isInvalid(payrollPeriodId)===false) {    	
	    		openNewPopUpWindow(url, swidth, sheight)
	    	}
	    }  else if(appId == "3"){   //payroll register
	    	var payrollPeriodId = document.getElementById("payrollPeriodId").value;
	    	url = "/hris/ActionPdfExportPayrollRegisterReport?payrollPeriodId="+payrollPeriodId+"&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;
	    	if (isInvalid(payrollPeriodId)===false) {
	    		openNewPopUpWindow(url, swidth, sheight)
	    	}
	    }   else if(appId == "4"){	 //YTD payroll register	    	
	    	
			var year = document.getElementById("year6").value;
	    	
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

	    	url = "/hris/ActionPdfExportPayrollRegisterYTDReport?year="+year+"&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;

	    	if (isInvalid(year)===false) {
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    }  else if(appId == "5"){  //employee attendance summary
	    	var dateFrom = document.getElementById("datepickerEASFrom").value;
	    	var dateTo = document.getElementById("datepickerEASTo").value;
	    	
	    	url = "/hris/ActionPdfExportAttendanceSummaryReport?dateFrom="+dateFrom+"&dateTo="+dateTo+"&forIndividual=N&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;
	    	
	    	if ((isInvalid(dateFrom) || isInvalid(dateTo))===false) {
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    }  else if(appId == "6"){  //employee attendance summary
	    	var month = document.getElementById("month2").value;
	    	var year = document.getElementById("year2").value;
	    	
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
	    		    		    	
	    	url = "/hris/ActionPdfExportGeneralHazardPayReport?month="+month+"&year="+year+"&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;	
	    	if ((isInvalid(month) || isInvalid(year))===false) {
	    		openNewPopUpWindow(url, swidth, sheight)
	    	}
	    }  else if(appId == "7"){  //employee attendance summary
	    	var month = document.getElementById("month3").value;
	    	var year = document.getElementById("year3").value;
	    	
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
	    		    		    	
	    	url = "/hris/ActionPdfExportGeneralLongivityPayReport?month="+month+"&year="+year+"&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;	
	    	if ((isInvalid(month) || isInvalid(year))===false) {
	    		openNewPopUpWindow(url, swidth, sheight)
	    	}
	    }  else if(appId == "8"){  //employee attendance summary
	    	
	    	var year = document.getElementById("year4").value;
	    	
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
	    		    		    	
	    	url = "/hris/ActionPdfExportGeneralYearEndBonusReport?year="+year+"&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;	
	    	if (isInvalid(year)===false) {
	    		openNewPopUpWindow(url, swidth, sheight)
	    	}
		}  else if(appId == "17"){  //employee attendance summary
	    	
	    	var year = document.getElementById("year8").value;
	    	
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
	    	
	    	url = "/hris/ActionPdfExportAlphalistReport?year="+year+"&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;	
	    	if (isInvalid(year)===false) {
	    		openNewPopUpWindow(url, swidth, sheight)
	    	}
	    }else if(appId == "9"){ //my leave history
	    	var dateFrom = document.getElementById("datepickerMLHFrom").value;
	    	var dateTo = document.getElementById("datepickerMLHTo").value;

	    	url = "/hris/ActionPdfExportGeneralMedicareShareAllowanceReport?empId="+empId+"&dateFrom="+dateFrom+"&dateTo="+dateTo+"&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;

	    	if ((isInvalid(dateFrom) || isInvalid(dateTo))===false) {
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    	
	    }else if(appId == "12"){ //my leave history
	    	var payrollPeriodId = document.getElementById("payrollPeriodId3").value;
	    	url = "/hris/ActionPdfExportContributionGSISReport?payrollPeriodId="+payrollPeriodId+"&forIndividual=N&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;

	    	if (isInvalid(payrollPeriodId)===false) {    	
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    }else if(appId == "13"){ //my leave history
	    	var payrollPeriodId = document.getElementById("payrollPeriodId4").value;
	    	url = "/hris/ActionPdfExportContributionPHICReport?payrollPeriodId="+payrollPeriodId+"&forIndividual=N&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;

	    	if (isInvalid(payrollPeriodId)===false) {    	
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    }else if(appId == "14"){ //my leave history
	    	var payrollPeriodId = document.getElementById("payrollPeriodId5").value;
	    	url = "/hris/ActionPdfExportContributionHDMFReport?payrollPeriodId="+payrollPeriodId+"&forIndividual=N&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;

	    	if (isInvalid(payrollPeriodId)===false) {    	
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    }else if(appId == "15"){ //my leave history
	    	var payrollPeriodId = document.getElementById("payrollPeriodId6").value;
	    	url = "/hris/ActionPdfExportContributionWithholdingTaxReport?payrollPeriodId="+payrollPeriodId+"&forIndividual=N&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;

	    	if (isInvalid(payrollPeriodId)===false) {    	
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    }else if(appId == "16"){ //my leave history
			var year = document.getElementById("year7").value;
	    	
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

	    	if (isInvalid(year)===false) {
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    }else if(appId == "11"){ //employee overtime report
	    	var dateFrom = document.getElementById("datepickerEOSRFrom").value;
	    	var dateTo = document.getElementById("datepickerEOSRTo").value;
	    	
	    	url = "/hris/ActionPdfExportGeneralOvertimeSummaryReport?dateFrom="+dateFrom+"&dateTo="+dateTo+"&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;

	    	if ((isInvalid(dateFrom) || isInvalid(dateTo))===false) {
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    } else if(appId == "18"){ //system report
	    	var dateFrom = document.getElementById("datepickerSTFrom").value;
	    	var dateTo = document.getElementById("datepickerSTTo").value;

	    	url = "/hris/ActionPdfExportSystemTrailReport?dateFrom="+dateFrom+"&dateTo="+dateTo+"&forExport=N";
	    	swidth = screen.availWidth;
	    	sheight = screen.availHeight;

	    	if ((isInvalid(dateFrom) || isInvalid(dateTo))===false) {
	    		openNewPopUpWindow(url, swidth, sheight);
	    	}
	    }
	    
	}
	
	
	function exportReport(appId){		
	    var url = "";
	    var sectionId = document.getElementById("sectionId").value;	 
	    var empId = document.getElementById("empId").value;	
	    var digitsOnly = new RegExp(/^[0-9]+$/);
	    
	    if(appId == "1"){
	    	//var dateFrom = document.getElementById("datepicker1").value;
	    	//var dateTo = document.getElementById("datepicker2").value;
	    	
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
	    	
	    	if ((isInvalid(month) || isInvalid(year))===false) {
		    	var oAjaxCall = $.ajax({
					type : "POST",
					url : "/hris/ActionPdfExportGeneralCaseRateReport",
					
					data: "month="+month+"&year="+year+"&forExport=Y",
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
	    	
	    } else if(appId == "2"){
	    	var dateFrom = document.getElementById("datepicker3").value;
	    	var dateTo = document.getElementById("datepicker4").value;
	    	
	    	if ((isInvalid(dateFrom) || isInvalid(dateTo))===false) {
		    	var oAjaxCall = $.ajax({
					type : "POST",
					url : "/hris/ActionPdfExportGeneralProfessionalFeeReport",
					
					data: "dateFrom=" + dateFrom + "&dateTo=" + dateTo+"&forExport=Y",
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
	    } else if(appId == "0"){
			var payrollPeriodId = document.getElementById("payrollPeriodId2").value;
	    	
			if (isInvalid(payrollPeriodId)===false) {    	
		    	var oAjaxCall = $.ajax({
					type : "POST",
					url : "/hris/ActionPdfExportPayslipReport",
					
					data: "payrollPeriodId="+payrollPeriodId+"&singlePayslip=N&forExport=Y",
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
	    	
	    }  else if(appId == "3"){   //payroll register
	    	var payrollPeriodId = document.getElementById("payrollPeriodId").value;
	    	
	    	if (isInvalid(payrollPeriodId)===false) {
		    	var oAjaxCall = $.ajax({
					type : "POST",
					url : "/hris/ActionPdfExportPayrollRegisterReport",
					
					data: "payrollPeriodId=" + payrollPeriodId+"&forExport=Y",
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
	    
	    
	    }   else if(appId == "4"){	 //YTD payroll register	    	
	    	
			var year = document.getElementById("year6").value;
	    	
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
	    	
	    	if (isInvalid(year)===false) {
		    	var oAjaxCall = $.ajax({
					type : "POST",
					url : "/hris/ActionPdfExportPayrollRegisterYTDReport",
					
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
	    	
	    	
	    }  else if(appId == "5"){  //employee attendance summary
	    	
	    	
	    	var dateFrom = document.getElementById("datepickerEASFrom").value;
	    	var dateTo = document.getElementById("datepickerEASTo").value;
	    	
	    	if ((isInvalid(dateFrom) || isInvalid(dateTo))===false) {
		    	var oAjaxCall = $.ajax({
					type : "POST",
					url : "/hris/ActionPdfExportAttendanceSummaryReport",
					
					data: "dateFrom=" + dateFrom + "&dateTo=" + dateTo+"&forIndividual=N&forExport=N",
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
	    	
	    }  else if(appId == "6"){  //employee attendance summary
	    	var month = document.getElementById("month2").value;
	    	var year = document.getElementById("year2").value;
	    	
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
	    	
	    	if ((isInvalid(month) || isInvalid(year))===false) {
		    	var oAjaxCall = $.ajax({
					type : "POST",
					url : "/hris/ActionPdfExportGeneralHazardPayReport",
					
					data: "month="+month+"&year="+year+"&forExport=Y",
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
	    }  else if(appId == "7"){  //employee attendance summary
	    	var month = document.getElementById("month3").value;
	    	var year = document.getElementById("year3").value;
	    	
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
	    	
	    	if ((isInvalid(month) || isInvalid(year))===false) {
		    	var oAjaxCall = $.ajax({
					type : "POST",
					url : "/hris/ActionPdfExportGeneralLongivityPayReport",
					
					data: "month="+month+"&year="+year+"&forExport=Y",
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
	    }  else if(appId == "8"){  //employee attendance summary
	    	
	    	var year = document.getElementById("year4").value;
	    	
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
	    	
	    	if (isInvalid(year)===false) {
		    	var oAjaxCall = $.ajax({
					type : "POST",
					url : "/hris/ActionPdfExportGeneralYearEndBonusReport",
					
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
		}  else if(appId == "17"){  //employee attendance summary
	    	
	    	var year = document.getElementById("year8").value;
	    	
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
	    	
	    	if (isInvalid(year)===false) {
		    	var oAjaxCall = $.ajax({
					type : "POST",
					url : "/hris/ActionPdfExportAlphalistReport",
					
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
	    }else if(appId == "9"){ //my leave history
	    	var dateFrom = document.getElementById("datepickerMLHFrom").value;
	    	var dateTo = document.getElementById("datepickerMLHTo").value;

	    	
	    	if ((isInvalid(dateFrom) || isInvalid(dateTo))===false) {
		    	var oAjaxCall = $.ajax({
					type : "POST",
					url : "/hris/ActionPdfExportGeneralMedicareShareAllowanceReport",
					
					data: "empId=" + empId + "&dateFrom=" + dateFrom + "&dateTo=" + dateTo+"&forExport=Y",
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
	    }else if(appId == "12"){ //my leave history
			var payrollPeriodId = document.getElementById("payrollPeriodId3").value;
	    	
			if (isInvalid(payrollPeriodId)===false) {    	
		    	var oAjaxCall = $.ajax({
					type : "POST",
					url : "/hris/ActionPdfExportContributionGSISReport",
					
					data: "payrollPeriodId="+payrollPeriodId+"&forIndividual=N&forExport=Y",	    	
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
	    }else if(appId == "13"){ //my leave history
			var payrollPeriodId = document.getElementById("payrollPeriodId4").value;
	    	
			if (isInvalid(payrollPeriodId)===false) {    	
		    	var oAjaxCall = $.ajax({
					type : "POST",
					url : "/hris/ActionPdfExportContributionPHICReport",
					
					data: "payrollPeriodId="+payrollPeriodId+"&forIndividual=N&forExport=Y",	    	
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
	    }else if(appId == "14"){ //my leave history
			var payrollPeriodId = document.getElementById("payrollPeriodId5").value;
	    	
			if (isInvalid(payrollPeriodId)===false) {    	
		    	var oAjaxCall = $.ajax({
					type : "POST",
					url : "/hris/ActionPdfExportContributionHDMFReport",
					
					data: "payrollPeriodId="+payrollPeriodId+"&forIndividual=N&forExport=Y",	    	
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
	    }else if(appId == "15"){ //my leave history
			var payrollPeriodId = document.getElementById("payrollPeriodId6").value;
	    	
			if (isInvalid(payrollPeriodId)===false) {    	
		    	var oAjaxCall = $.ajax({
					type : "POST",
					url : "/hris/ActionPdfExportContributionWithholdingTaxReport",
					
					data: "payrollPeriodId="+payrollPeriodId+"&forIndividual=N&forExport=Y",	    	
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
	    }else if(appId == "16"){ //my leave history
			var year = document.getElementById("year7").value;
	    	
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
	    	
	    	if (isInvalid(year)===false) {    	
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
	    }else if(appId == "11"){ //employee overtime report
	    	var dateFrom = document.getElementById("datepickerEOSRFrom").value;
	    	var dateTo = document.getElementById("datepickerEOSRTo").value;
	    	
	    	if ((isInvalid(dateFrom) || isInvalid(dateTo))===false) {
		    	var oAjaxCall = $.ajax({
					type : "POST",
					url : "/hris/ActionPdfExportGeneralOvertimeSummaryReport",
					
					data: "dateFrom=" + dateFrom + "&dateTo=" + dateTo +"&forExport=Y",
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
	    	
	    	
	    } else {
	    		    	
	    	var dateFrom = document.getElementById("datepickerSTFrom").value;
	    	var dateTo = document.getElementById("datepickerSTTo").value;
	    	
	    	if ((isInvalid(dateFrom) || isInvalid(dateTo))===false) {
		    	var oAjaxCall = $.ajax({
					type : "POST",
					url : "/hris/ActionPdfExportSystemTrailReport",
					
					data: "dateFrom=" + dateFrom + "&dateTo=" + dateTo +"&forExport=Y",
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
	    
	}
	
	function isInvalid(variabols) {
		if (typeof(variabols) === "undefined" || variabols === null || variabols === "") {
			return true;
		}
		return false;
	}
	
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
    

    populatePayrollPeriodDropDown();
    populatePayrollPeriodDropDown2();
    populatePayrollPeriodDropDown3();
    populatePayrollPeriodDropDown4();
    populatePayrollPeriodDropDown5();
    populatePayrollPeriodDropDown6();
    
  });
  
  
	function populatePayrollPeriodDropDown() {
		var oAjaxCall = $.ajax({
			type : "POST",
			url : "/hris/GetAllPayrollPeriodAction?includeGenerated=true",
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {
				var items = '<option value=""></option>';
				if (data.Records.length) {
					$.each(data.Records, function(i, item) {
						items += "<option value='" + item.payrollPeriodId + "'>"
								+ "PayCode: " + item.payrollCode + ", PayrollType: " + item.payrollType +  ", PayDate: " + item.payDate + "</option>";
					});
					$('#payrollPeriodId').html(items);
				}
				;

			},
			error : function(data) {
				alert('error: populatePayrollPeriodDropDown' + data);
			}

		});
	};
	
	function populatePayrollPeriodDropDown2() {
		var oAjaxCall = $.ajax({
			type : "POST",
			url : "/hris/GetAllPayrollPeriodAction?includeGenerated=true",
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {
				var items = '<option value=""></option>';
				if (data.Records.length) {
					$.each(data.Records, function(i, item) {
						items += "<option value='" + item.payrollPeriodId + "'>"
								+ "PayCode: " + item.payrollCode + ", PayrollType: " + item.payrollType +  ", PayDate: " + item.payDate + "</option>";
					});
					$('#payrollPeriodId2').html(items);
				}
				;

			},
			error : function(data) {
				alert('error: populatePayrollPeriodDropDown2' + data);
			}

		});
	};
	
	function populatePayrollPeriodDropDown3() {
		var oAjaxCall = $.ajax({
			type : "POST",
			url : "/hris/GetAllPayrollPeriodAction?includeGenerated=true",
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {
				var items = '<option value=""></option>';
				if (data.Records.length) {
					$.each(data.Records, function(i, item) {
						items += "<option value='" + item.payrollPeriodId + "'>"
								+ "PayCode: " + item.payrollCode + ", PayrollType: " + item.payrollType +  ", PayDate: " + item.payDate + "</option>";
					});
					$('#payrollPeriodId3').html(items);
				}
				;

			},
			error : function(data) {
				alert('error: populatePayrollPeriodDropDown3' + data);
			}

		});
	};
	
	function populatePayrollPeriodDropDown4() {
		var oAjaxCall = $.ajax({
			type : "POST",
			url : "/hris/GetAllPayrollPeriodAction?includeGenerated=true",
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {
				var items = '<option value=""></option>';
				if (data.Records.length) {
					$.each(data.Records, function(i, item) {
						items += "<option value='" + item.payrollPeriodId + "'>"
								+ "PayCode: " + item.payrollCode + ", PayrollType: " + item.payrollType +  ", PayDate: " + item.payDate + "</option>";
					});
					$('#payrollPeriodId4').html(items);
				}
				;

			},
			error : function(data) {
				alert('error: populatePayrollPeriodDropDown4' + data);
			}

		});
	};
	
	function populatePayrollPeriodDropDown5() {
		var oAjaxCall = $.ajax({
			type : "POST",
			url : "/hris/GetAllPayrollPeriodAction?includeGenerated=true",
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {
				var items = '<option value=""></option>';
				if (data.Records.length) {
					$.each(data.Records, function(i, item) {
						items += "<option value='" + item.payrollPeriodId + "'>"
								+ "PayCode: " + item.payrollCode + ", PayrollType: " + item.payrollType +  ", PayDate: " + item.payDate + "</option>";
					});
					$('#payrollPeriodId5').html(items);
				}
				;

			},
			error : function(data) {
				alert('error: populatePayrollPeriodDropDown5' + data);
			}

		});
	};
	
	function populatePayrollPeriodDropDown6() {
		var oAjaxCall = $.ajax({
			type : "POST",
			url : "/hris/GetAllPayrollPeriodAction?includeGenerated=true",
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {
				var items = '<option value=""></option>';
				if (data.Records.length) {
					$.each(data.Records, function(i, item) {
						items += "<option value='" + item.payrollPeriodId + "'>"
								+ "PayCode: " + item.payrollCode + ", PayrollType: " + item.payrollType +  ", PayDate: " + item.payDate + "</option>";
					});
					$('#payrollPeriodId6').html(items);
				}
				;

			},
			error : function(data) {
				alert('error: populatePayrollPeriodDropDown6' + data);
			}

		});
	};
  </script>

</head>
<body>	
<div><c:import url="header.jsp" /></div>	
<%--input type="hidden" name="empId" id="empId" value="${user.empId}" />
<input type="hidden" name="departmentId" id="departmentId" value="${user.departmentId}" /--%>	

<input type="hidden" name="empId" id="empId" value="${employeeLoggedIn.empId}" />
<input type="hidden" name="sectionId" id="sectionId" value="${employeeLoggedIn.sectionId}" />	
	
		
<div id="content"> 
	<div id="reportsArea" style="height: 1200px;">							
		
		<div class="reportItem">				
			<div class="reportsName">PAYSLIP</div>	
			<div class="dateArea">
				<div class="datepickerTxt" style="width:80px;">PayPeriod</div>
				<div class="datepicker">				    	
					<select name="payrollPeriod2" id="payrollPeriodId2" style="width:397px; height:32px; background:#fff; border: 1px solid #52833b; margin-top: 5px; color: black;" ></select>	
				</div>
				
			</div>		
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('0');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('0');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('0');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
		</div>
		<div style="clear: both;"></div>
		
		<div class="reportItem">				
			<div class="reportsName">CASE RATE PAYMENT REPORT</div>	
			<div class="dateArea">
				<div class="datepickerTxt" style="width:47px;">Month</div>
				<div class="datepicker" style="margin-left: 32px;">
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
				<div class="datepicker"><input type="text" id="year" name="year"  style="width:150px;">
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
			<div class="reportsName">PROFESSIONAL FEE REPORT</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:80px;">Date From</div>
				<div class="datepicker"><input type="text" id="datepicker3" style="width:150px;"></div>
				<div class="datepickerTxt" style="width:60px;">Date To</div>
				<div class="datepicker"><input type="text" id="datepicker4" style="width:150px;"></div>
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
		
		<%--Payroll Register #3--%>
		<div class="reportItem">				
			<div class="reportsName">PAYROLL REGISTER</div>	
			<div class="dateArea">
				<div class="datepickerTxt" style="width:80px;">PayPeriod</div>
				<div class="datepicker">				    	
					<select name="payrollPeriod" id="payrollPeriodId" style="width:397px; height:32px; background:#fff; border: 1px solid #52833b; margin-top: 5px; color: black;" ></select>	
				</div>
				
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
		
		<%--YTD Payroll Register #4--%>
		 
		<div class="reportItem">			
			<div class="reportsName">YTD PAYROLL REGISTER</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:80px;">Year</div>
				<div class="datepicker"><input type="text" id="year6" name="year6" style="width:397px;"></div>
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
		
		<div class="reportItem">			
			<div class="reportsName">PAYROLL REGISTER 13TH MONTH</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:80px;">Year</div>
				<div class="datepicker"><input type="text" id="year7" name="year7" style="width:397px;"></div>
			</div>			
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('16');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('16');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('16');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
		</div>
		
		
		<%--Employee Attendance Summary #5--%>
		<div class="reportItem">			
			<div class="reportsName">EMPLOYEE ATTENDANCE SUMMARY</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:80px;">Date From</div>
				<div class="datepicker"><input type="text" id="datepickerEASFrom" style="width:150px;"></div>
				<div class="datepickerTxt" style="width:60px;">Date To</div>
				<div class="datepicker"><input type="text" id="datepickerEASTo" style="width:150px;"></div>
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
		
		<%--Employee Overtime #11--%>
		<div class="reportItem">
		<div class="reportsName">EMPLOYEE OVERTIME SUMM. REPORT</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:80px;">Date From</div>
				<div class="datepicker"><input type="text" id="datepickerEOSRFrom" style="width:150px;"></div>
				<div class="datepickerTxt" style="width:60px;">Date To</div>
				<div class="datepicker"><input type="text" id="datepickerEOSRTo" style="width:150px;"></div>
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
		
		<%--
		<div class="reportItem">			
			<div class="reportsName">PAYROLL REGISTER WITH TAXBLE INCOME</div>
			<div class="dateArea">
				<div class="datepickerTxt">Date From</div>
				<div class="datepicker"><input type="text" id="datepicker4443"></div>
				<div class="datepickerTxt">Date From</div>
				<div class="datepicker"><input type="text" id="datepicker4444"></div>
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
		--%>
		
		<div class="reportItem">				
			<div class="reportsName">HAZARD PAY REPORT</div>	
			<div class="dateArea">
				<div class="datepickerTxt" style="width:47px;">Month</div>
				<div class="datepicker" style="margin-left: 32px;">
					<select name="month2" id="month2" style="width:150px; height:31px; background:#fff; border: 1px solid #52833b; margin-top: 5px; color: black;" >													
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
				<div class="datepicker"><input type="text" id="year2" name="year2" style="width:150px;">
				</div>
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
			<div class="reportsName">LONGEVITY PAY REPORT</div>	
			<div class="dateArea">
				<div class="datepickerTxt" style="width:47px;">Month</div>
				<div class="datepicker" style="margin-left: 32px;">
					<select name="month3" id="month3" style="width:150px; height:31px; background:#fff; border: 1px solid #52833b; margin-top: 5px; color: black;" >													
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
				<div class="datepicker"><input type="text" id="year3" name="year3" style="width:150px;">
				</div>
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
		
		<%--My leave history #9--%>
		<div class="reportItem">			
			<div class="reportsName">MEDICARE SHARE ALLOWANCE REPORT</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:80px;">Date From</div>
				<div class="datepicker"><input type="text" id="datepickerMLHFrom" style="width:150px;"></div>
				<div class="datepickerTxt" style="width:60px;">Date To</div>
				<div class="datepicker"><input type="text" id="datepickerMLHTo" style="width:150px;"></div>
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
			<div class="reportsName">YEAR END BONUS AND CASH GIFT REPORT</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:80px;">Year</div>
				<div class="datepicker"><input type="text" id="year4" name="year4" style="width:397px;"></div>
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
			<div class="reportsName">GSIS CONTRIBUTION REPORT</div>	
			<div class="dateArea">
				<div class="datepickerTxt" style="width:80px;">PayPeriod</div>
				<div class="datepicker">				    	
					<select name="payrollPeriod3" id="payrollPeriodId3" style="width:397px; height:32px; background:#fff; border: 1px solid #52833b; margin-top: 5px; color: black;" ></select>	
				</div>
				
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
		
		<div class="reportItem">				
			<div class="reportsName">PHIC CONTRIBUTION REPORT</div>	
			<div class="dateArea">
				<div class="datepickerTxt" style="width:80px;">PayPeriod</div>
				<div class="datepicker">				    	
					<select name="payrollPeriod4" id="payrollPeriodId4" style="width:397px; height:32px; background:#fff; border: 1px solid #52833b; margin-top: 5px; color: black;" ></select>	
				</div>
				
			</div>		
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('13');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('13');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('13');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
		</div>
		<div style="clear: both;"></div>
		
		
		<div class="reportItem">				
			<div class="reportsName">HDMF CONTRIBUTION REPORT</div>	
			<div class="dateArea">
				<div class="datepickerTxt" style="width:80px;">PayPeriod</div>
				<div class="datepicker">				    	
					<select name="payrollPeriod5" id="payrollPeriodId5" style="width:397px; height:32px; background:#fff; border: 1px solid #52833b; margin-top: 5px; color: black;" ></select>	
				</div>
				
			</div>		
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('14');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('14');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('14');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
		</div>
		<div style="clear: both;"></div>
		
		<div class="reportItem">				
			<div class="reportsName">WITHHOLDING TAX REPORT</div>	
			<div class="dateArea">
				<div class="datepickerTxt" style="width:80px;">PayPeriod</div>
				<div class="datepicker">				    	
					<select name="payrollPeriod6" id="payrollPeriodId6" style="width:397px; height:32px; background:#fff; border: 1px solid #52833b; margin-top: 5px; color: black;" ></select>	
				</div>
				
			</div>		
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('15');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('15');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('15');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
		</div>
		<div style="clear: both;"></div>
		
		<div class="reportItem">				
			<div class="reportsName">BIR ALPHALIST REPORT</div>	
			<div class="dateArea">
				<div class="datepickerTxt" style="width:80px;">Year</div>
				<div class="datepicker"><input type="text" id="year8" name="year8" style="width:397px;"></div>
			</div>		
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('17');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('17');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('17');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
		</div>
		<div style="clear: both;"></div>
		<div class="reportItem">			
			<div class="reportsName">SYSTEM TRAIL REPORT</div>
			<div class="dateArea">
				<div class="datepickerTxt" style="width:80px;">Date From</div>
				<div class="datepicker"><input type="text" id="datepickerSTFrom" style="width:150px;"></div>
				<div class="datepickerTxt" style="width:60px;">Date To</div>
				<div class="datepicker"><input type="text" id="datepickerSTTo" style="width:150px;"></div>
			</div>			
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('18');"><img src="images/view.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="exportReport('18');"><img src="images/excel2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
			<div class="reportsIconButton">
				<div onclick="openApplicationWindow('18');"><img src="images/pdf2.png" alt="alternate text" width="30px" height="30px" /></div>				
			</div>
		</div>
		
		<div style="clear: both; height: 250px;"></div>	
		
		
								
	</div>
</div>
<div style=""><c:import url="footer.jsp" /></div>




	
		
</body>
</html>
