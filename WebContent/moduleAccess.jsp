<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Module Access</title>
<%@include file="commonHeader.jsp" %>
<%@include file="commonJtables.jsp" %>
<link rel="stylesheet" type="text/css" href="css/styleTables.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/moment.min.js"></script>
<script type="text/javascript">

$(document).ready(function() {	
	var empId = '${employeeLoggedIn.empId}';	
	
	
	if(empId.length == 0){
		alert("You are not Viewed to view the page. Please login.");
		window.location = "LogoutAction";
		return;
	}
	
	var isAllowed = 'NO';
	var buttonEnabled = 'NO';
	
	<c:forEach var="stl" items="${sessionScope.moduleAccess.systemTabList}">
		<c:if test="${stl == 'st_view_fileManagementTab'}">
			<c:forEach var="cr" items="${sessionScope.moduleAccess.fileManagementList}">
				<c:if test="${cr == 'fm_view_module_access'}">
					isAllowed = 'YES';
				</c:if>
				
				<c:if test="${cr == 'fm_edit_module_access'}">
					buttonEnabled = 'YES';
				</c:if>
			</c:forEach>
		</c:if>
	</c:forEach>
	
	//if(isAllowed == 'NO'){
	//	alert("You are not Viewed to view the page. Please login.");
	//	window.location = "LogoutAction";
	//	return;
	//}
	
	//if(buttonEnabled == 'NO'){
	//	$('#fixedButtonPlaceHolder').html('<div id="DisabledFixedSubmitButton">SAVE ACCESS</div>');	
	//}
	
	
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
	$('#dashBoardRightPannel2').show();
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetModuleAccessAction?empId=" + empid,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			console.log(data);
			$('.myCheckbox').attr('checked', false);
			if (data.Record) {
				$('#moduleAccessId').val(data.Record.moduleAccessId);
				if (data.Record.fileManagementList){
				$.each(data.Record.fileManagementList, function(i, item) {
					document.getElementById(item).checked = true;				    			
				});
				}
				if (data.Record.employeeList){
				$.each(data.Record.employeeList, function(i, item) {
					document.getElementById(item).checked = true;
				});	
				}
				if (data.Record.timeManagementList){
				$.each(data.Record.timeManagementList, function(i, item) {
					document.getElementById(item).checked = true;
				});	
				}
				if (data.Record.payrollList){
				$.each(data.Record.payrollList, function(i, item) {
					document.getElementById(item).checked = true;
				});	
				}
				if (data.Record.employeesLoanList){
				$.each(data.Record.employeesLoanList, function(i, item) {
					document.getElementById(item).checked = true;
				});	
				}
				if (data.Record.payrollReportsList){
				$.each(data.Record.payrollReportsList, function(i, item) {
					document.getElementById(item).checked = true;
				});	
				}
				if (data.Record.systemTabList){
				$.each(data.Record.systemTabList, function(i, item) {
					document.getElementById(item).checked = true;
				});	
				}
			}
		},
		error : function(data) {
			alert('error: moduleAccess' + data);
		}
	});
}

function saveModuleAccess() {
	alert("Module Access for employee saved.");
	document.moduleAccessForm.submit();
	//alert("Module Access for employee saved.");
}

</script>
</head>
<body>
<div><c:import url="header.jsp" /></div>
<div id="content">
    <div id="dashBoardLeftPannel2">
		<c:import url="searchEmployee_solr.jsp" />
		<div class="cb"></div>
		<div>
			<div id="searchHolderId" style="margin-bottom: 50px;"></div>	
		</div>
	</div>
	<div id="dashBoardRightPannel2" style="display:none">	
		<div id="fixedButtonPlaceHolder"><div id="FixedSubmitButton" onclick="javascript:saveModuleAccess();">SAVE ACCESS</div></div>
		<div class="dataEntryText">Employee No:</div>
	    <div class="dataEntryTextBlue" id="empNo"></div>	    
	    <div class="dataEntryText">Employee Name:</div>			    
	    <div class="dataEntryTextBlue" id="fullname"></div>	    
	    <div class="cb"></div>	 
	     			    
	    <div class="dataEntryTextBlue" style="font-size: 14px;">&nbsp;&nbsp;&nbsp;<input type="checkbox" id="selectAllCheckBox" value="">&nbsp;&nbsp;&nbsp;Select All</div>   				
	  	<div class="cb" style="height: 20px;"></div>	 
	    <div>
			<div style="height: 2500px;">			    
			  <form method="POST" id="moduleAccessForm" name="moduleAccessForm" action="SaveModuleAccessAction">
				  	<input type="hidden" name="createdBy" id="createdBy" value="${employeeLoggedIn.empId}" />
				   	<input type="hidden" name="empId" id="empId" value="" />
				   	<input type="hidden" name="moduleAccessId" id="moduleAccessId" value="" />				
				   	
				   	<div>
			  			<div class="moduleAccessGroupHeader">SYSTEM TABS</div>
			  			<div class="moduleAccessDetailContanier" style="height: 220px;">
			  				<ul>					
								<li>
									<div class="floatLeftTextModuleAccess">View File Management Tab</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="systemTabs" id="st_view_fileManagementTab" value="st_view_fileManagementTab"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">View Employee Tab</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="systemTabs" id="st_view_employeeTab" value="st_view_employeeTab"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">View Employee Time Management Tab</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="systemTabs" id="st_view_employeeTimeMgtTab" value="st_view_employeeTimeMgtTab"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">View Payroll Tab</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="systemTabs" id="st_view_payrollTab" value="st_view_payrollTab"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">View Employee Loans Tab</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="systemTabs" id="st_view_employeeLoansTab" value="st_view_employeeLoansTab"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">View Payroll Reports Tab</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="systemTabs" id="st_view_payrollReportsTab" value="st_view_payrollReportsTab"></div>
									<div class="cb"></div>
								</li>										
							</ul>
			  			</div>
			  		</div>
				   	
				   	<div>
			  			<div class="moduleAccessGroupHeader">FILE MANAGEMENT</div>
			  			<div class="moduleAccessDetailContanier" style="height: 590px;">
			  				<ul>
			  					<li>
									<div class="floatLeftTextModuleAccess">File Management Options</div>
									<div class="floatLeftTextFirst">View</div>
									<div class="floatLeftText">Add</div>
									<div class="floatLeftText">Edit</div>
									<div class="floatLeftText">Delete</div>
									<div class="floatLeftText">Print</div>
									<div class="floatLeftText">Export</div>
									<div class="cb"></div>
								</li>		
								<li>
									<div class="floatLeftTextModuleAccess">Module Access</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_view_module_access" value="fm_view_module_access"></div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_edit_module_access" value="fm_edit_module_access"></div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">Holidays</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_holidays" value="fm_holidays"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_add_holidays" value="fm_add_holidays"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_edit_holidays" value="fm_edit_holidays"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_delete_holidays" value="fm_delete_holidays"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_print_holidays" value="fm_print_holidays"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_export_holidays" value="fm_export_holidays"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess" style="color: blue; font-weight: bold;">Payroll Settings</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_view_payrollSettings" value="fm_view_payrollSettings"></div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">Loan Type</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_loan_type" value="fm_loan_type"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_add_loan_type" value="fm_add_loan_type"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_edit_loan_type" value="fm_edit_loan_type"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_delete_loan_type" value="fm_delete_loan_type"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_print_loan_type" value="fm_print_loan_type"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_export_loan_type" value="fm_export_loan_type"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">Leave Type</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_leave_type" value="fm_leave_type"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_add_leave_type" value="fm_add_leave_type"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_edit_leave_type" value="fm_edit_leave_type"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_delete_leave_type" value="fm_delete_leave_type"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_print_leave_type" value="fm_print_leave_type"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_export_leave_type" value="fm_export_leave_type"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">Income &amp; Benefits Type</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_income" value="fm_income"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_add_income" value="fm_add_income"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_edit_income" value="fm_edit_income"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_delete_income" value="fm_delete_income"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_print_income" value="fm_print_income"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_export_income" value="fm_export_income"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">Deduction Type</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_deduction" value="fm_deduction"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_add_deduction" value="fm_add_deduction"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_edit_deduction" value="fm_edit_deduction"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_delete_deduction" value="fm_delete_deduction"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_print_deduction" value="fm_print_deduction"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_export_deduction" value="fm_export_deduction"></div>
									<div class="cb"></div>
								</li>
								
								<li>
									<div class="floatLeftTextModuleAccess" style="color: blue; font-weight: bold;">Employee Settings</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_view_employeeSettings" value="fm_view_employeeSettings"></div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="cb"></div>
								</li>		
								<li>
									<div class="floatLeftTextModuleAccessIndent">Section</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_section" value="fm_section"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_add_section" value="fm_add_section"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_edit_section" value="fm_edit_section"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_delete_section" value="fm_delete_section"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_print_section" value="fm_print_section"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_export_section" value="fm_export_section"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">Unit</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_unit" value="fm_unit"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_add_unit" value="fm_add_unit"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_edit_unit" value="fm_edit_unit"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_delete_unit" value="fm_delete_unit"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_print_unit" value="fm_print_unit"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_export_unit" value="fm_export_unit"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">Sub Unit</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_subUnit" value="fm_subUnit"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_add_subUnit" value="fm_add_subUnit"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_edit_subUnit" value="fm_edit_subUnit"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_delete_subUnit" value="fm_delete_subUnit"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_print_subUnit" value="fm_print_subUnit"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_export_subUnit" value="fm_export_subUnit"></div>
									<div class="cb"></div>
								</li>	
								<li>
									<div class="floatLeftTextModuleAccessIndent">Job Title</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_job_title" value="fm_job_title"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_add_job_title" value="fm_add_job_title"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_edit_job_title" value="fm_edit_job_title"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_delete_job_title" value="fm_delete_job_title"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_print_job_title" value="fm_print_job_title"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_export_job_title" value="fm_export_job_title"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">Salary Grade</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_salary_grade" value="fm_salary_grade"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_add_salary_grade" value="fm_add_salary_grade"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_edit_salary_grade" value="fm_edit_salary_grade"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_delete_salary_grade" value="fm_delete_salary_grade"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_print_salary_grade" value="fm_print_salary_grade"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_export_salary_grade" value="fm_export_salary_grade"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">System Settings</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_system_approvers" value="fm_system_approvers"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_add_system_approvers" value="fm_add_system_approvers"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_edit_system_approvers" value="fm_edit_system_approvers"></div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="cb"></div>
								</li>
								
								
								<!-- 
								<li>
									<div class="floatLeftTextModuleAccess">Department</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_department" value="fm_department"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_add_department" value="fm_add_department"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_edit_department" value="fm_edit_department"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_delete_department" value="fm_delete_department"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_print_department" value="fm_print_department"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_export_department" value="fm_export_department"></div>
									<div class="cb"></div>
								</li>
								 
								<li>
									<div class="floatLeftTextModuleAccess">Division</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_division" value="fm_division"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_add_division" value="fm_add_division"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_edit_division" value="fm_edit_division"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_delete_division" value="fm_delete_division"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_print_division" value="fm_print_division"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_export_division" value="fm_export_division"></div>
									<div class="cb"></div>
								</li>
								-->
								<li>
									<div class="floatLeftTextModuleAccess" style="color: blue; font-weight: bold;">Location Settings</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_view_locationSettings" value="fm_view_locationSettings"></div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">City</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_city" value="fm_city"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_add_city" value="fm_add_city"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_edit_city" value="fm_edit_city"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_delete_city" value="fm_delete_city"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_print_city" value="fm_print_city"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_export_city" value="fm_export_city"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">Province</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_province" value="fm_province"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_add_province" value="fm_add_province"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_edit_province" value="fm_edit_province"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_delete_province" value="fm_delete_province"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_print_province" value="fm_print_province"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_export_province" value="fm_export_province"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">Country</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_country" value="fm_country"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_add_country" value="fm_add_country"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_edit_country" value="fm_edit_country"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_delete_country" value="fm_delete_country"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_print_country" value="fm_print_country"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="fm_export_country" value="fm_export_country"></div>
									<div class="cb"></div>
								</li>
																		
							</ul>
			  			</div>
			  		</div>
			  		
			  		<div>
			  			<div class="moduleAccessGroupHeader">EMPLOYEE</div>
			  			<div class="moduleAccessDetailContanier" style="height:620px;">
			  				<ul>				
			  					<li>
									<div class="floatLeftTextModuleAccess">Employee Options</div>
									<div class="floatLeftTextFirst">View</div>
									<div class="floatLeftText">Add</div>
									<div class="floatLeftText">Edit</div>
									<div class="floatLeftText">Print</div>
									<div class="floatLeftText">Export</div>
									<div class="cb"></div>
								</li>		
								<li>
									<div class="floatLeftTextModuleAccess" style="color: blue; font-weight: bold;">Employee Management</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_view_empManagement" value="em_view_empManagement"></div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">Employee</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_employee" value="em_employee"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_add_employee" value="em_add_employee"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_edit_employee" value="em_edit_employee"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_print_employee" value="em_print_employee"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_export_employee" value="em_export_employee"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">Memo</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_memo" value="em_memo"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_add_memo" value="em_add_memo"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_edit_memo" value="em_edit_memo"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_print_memo" value="em_print_memo"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_export_memo" value="em_export_memo"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">Notification</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_notification" value="em_notification"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_add_notification" value="em_add_notification"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_edit_notification" value="em_edit_notification"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_print_notification" value="em_print_notification"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_export_notification" value="em_export_notification"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess" style="color: blue; font-weight: bold;">Employee Entries</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_view_empEntries" value="em_view_empEntries"></div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">File Leave</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_file_leave" value="em_file_leave"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_add_file_leave" value="em_add_file_leave"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_edit_file_leave" value="em_edit_file_leave"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_print_file_leave" value="em_print_file_leave"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_export_file_leave" value="em_export_file_leave"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">File Out of Office</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_file_ooo" value="em_file_ooo"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_add_file_ooo" value="em_add_file_ooo"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_edit_file_ooo" value="em_edit_file_ooo"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_print_file_ooo" value="em_print_file_ooo"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_export_file_ooo" value="em_export_file_ooo"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">Request Change Shift</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_file_changeshift" value="em_file_changeshift"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_add_file_changeshift" value="em_add_file_changeshift"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_edit_file_changeshift" value="em_edit_file_changeshift"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_print_file_changeshift" value="em_print_file_changeshift"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_export_file_changeshift" value="em_export_file_changeshift"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">File Overtime</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_file_ot" value="em_file_ot"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_add_file_ot" value="em_add_file_ot"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_edit_file_ot" value="em_edit_file_ot"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_print_file_ot" value="em_print_file_ot"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_export_file_ot" value="em_export_file_ot"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">File OffSet</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_file_offset" value="em_file_offset"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_add_file_offset" value="em_add_file_offset"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_edit_file_offset" value="em_edit_file_offset"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_print_file_offset" value="em_print_file_offset"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_export_file_offset" value="em_export_file_offset"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">File Undertime</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_file_undertime" value="em_file_undertime"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_add_file_undertime" value="em_add_file_undertime"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_edit_file_undertime" value="em_edit_file_undertime"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_print_file_undertime" value="em_print_file_undertime"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_export_file_undertime" value="em_export_file_undertime"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">File Hourly Attendance</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_file_hourly_attendance" value="em_file_hourly_attendance"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_add_file_hourly_attendance" value="em_add_file_hourly_attendance"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_edit_file_hourly_attendance" value="em_edit_file_hourly_attendance"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_print_file_hourly_attendance" value="em_print_file_hourly_attendance"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_export_file_hourly_attendance" value="em_export_file_hourly_attendance"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess" style="color: blue; font-weight: bold;">Approvals</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_view_empApprovals" value="em_view_empApprovals"></div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="floatLeftCheckBox">&nbsp;</div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">Leave Approval</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_leave_approval" value="em_leave_approval"></div>
									<div class="cb"></div>
								</li>								
								<li>
									<div class="floatLeftTextModuleAccessIndent">Out of Office Approval</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_ooo_approval" value="em_ooo_approval"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">Overtime Approval</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_ot_approval" value="em_ot_approval"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">OffSet Approval</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_offset_approval" value="em_offset_approval"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccessIndent">Undertime Approval</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_undertime_approval" value="em_undertime_approval"></div>
									<div class="cb"></div>
								</li>		
								<li>
									<div class="floatLeftTextModuleAccessIndent">Hourly Attendance Approval</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeeModule" id="em_hourly_attendance_approval" value="em_hourly_attendance_approval"></div>
									<div class="cb"></div>
								</li>								
							</ul>
			  			</div>
			  		</div>
			  		
			  		<div>
			  			<div class="moduleAccessGroupHeader">EMPLOYEE TIME MANAGEMENT</div>
			  			<div class="moduleAccessDetailContanier" style="height: 165px;">
			  				<ul>			
			  					<li>
									<div class="floatLeftTextModuleAccess">Time Management Options</div>
									<div class="floatLeftTextFirst">View</div>
									<div class="floatLeftText">Add</div>
									<div class="floatLeftText">Edit</div>
									<div class="floatLeftText">Print</div>
									<div class="floatLeftText">Export</div>
									<div class="cb"></div>
								</li>			
								<li>
									<div class="floatLeftTextModuleAccess">Create Employee Shift</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="timeManagementModule" id="tm_create_employee_shift" value="tm_create_employee_shift"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="timeManagementModule" id="tm_add_create_employee_shift" value="tm_add_create_employee_shift"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="timeManagementModule" id="tm_edit_create_employee_shift" value="tm_edit_create_employee_shift"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="timeManagementModule" id="tm_print_create_employee_shift" value="tm_print_create_employee_shift"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="timeManagementModule" id="tm_export_create_employee_shift" value="tm_export_create_employee_shift"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">Attendance Monitoring</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="timeManagementModule" id="tm_attendance_monitoring" value="tm_attendance_monitoring"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">Employee Schedule</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="timeManagementModule" id="tm_employee_schedule" value="tm_employee_schedule"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">Time Dispute HR Approval</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="timeManagementModule" id="tm_time_dispute_hr_approval" value="tm_time_dispute_hr_approval"></div>
									<div class="cb"></div>
								</li>							
							</ul>
			  			</div>
			  		</div>
			  		
			  		<div>
			  			<div class="moduleAccessGroupHeader">PAYROLL</div>
			  			<div class="moduleAccessDetailContanier" style="height: 450px;">
			  				<ul>	
			  					<li>
									<div class="floatLeftTextModuleAccess">Payroll Options</div>
									<div class="floatLeftTextFirst">View</div>
									<div class="floatLeftText">Add</div>
									<div class="floatLeftText">Edit</div>
									<div class="floatLeftText">Print</div>
									<div class="floatLeftText">Export</div>
									<div class="cb"></div>
								</li>					
								<li>
									<div class="floatLeftTextModuleAccess">Generate/Lock Payroll</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_generate_lock_payroll" value="py_generate_lock_payroll"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">Exclude From Payroll</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_exclude_payroll" value="py_exclude_payroll"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">Payroll Period</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_payroll_period" value="py_payroll_period"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_add_payroll_period" value="py_add_payroll_period"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_edit_payroll_period" value="py_edit_payroll_period"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_print_payroll_period" value="py_print_payroll_period"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_export_payroll_period" value="py_export_payroll_period"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">Income and Benefits</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_income_benefits" value="py_income_benefits"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_add_income_benefits" value="py_add_income_benefits"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_edit_income_benefits" value="py_edit_income_benefits"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_print_income_benefits" value="py_print_income_benefits"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_export_income_benefits" value="py_export_income_benefits"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">Deductions</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_deductions" value="py_deductions"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_add_deductions" value="py_add_deductions"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_edit_deductions" value="py_edit_deductions"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_print_deductions" value="py_print_deductions"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_export_deductions" value="py_export_deductions"></div>
									<div class="cb"></div>
								</li>	
								<li>
									<div class="floatLeftTextModuleAccess">Case Rate</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_case_rate" value="py_case_rate"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_add_case_rate" value="py_add_case_rate"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_edit_case_rate" value="py_edit_case_rate"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_print_case_rate" value="py_print_case_rate"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_export_case_rate" value="py_export_case_rate"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">Professional Fee</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_professional_fee" value="py_professional_fee"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_add_professional_fee" value="py_add_professional_fee"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_edit_professional_fee" value="py_edit_professional_fee"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_print_professional_fee" value="py_print_professional_fee"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_export_professional_fee" value="py_export_professional_fee"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">Hazard Pay</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_hazard_pay" value="py_hazard_pay"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_add_hazard_pay" value="py_add_hazard_pay"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_edit_hazard_pay" value="py_edit_hazard_pay"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_print_hazard_pay" value="py_print_hazard_pay"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_export_hazard_pay" value="py_export_hazard_pay"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">Longevity Pay</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_longevity_pay" value="py_longevity_pay"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_add_longevity_pay" value="py_add_longevity_pay"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_edit_longevity_pay" value="py_edit_longevity_pay"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_print_longevity_pay" value="py_print_longevity_pay"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_export_longevity_pay" value="py_export_longevity_pay"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">Medicare Share</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_medicare_share" value="py_medicare_share"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_add_medicare_share" value="py_add_medicare_share"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_edit_medicare_share" value="py_edit_medicare_share"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_print_medicare_share" value="py_print_medicare_share"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_export_medicare_share" value="py_export_medicare_share"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">Year End Bonus and Cash Gift</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_yearend_bonus" value="py_yearend_bonus"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_add_yearend_bonus" value="py_add_yearend_bonus"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_edit_yearend_bonus" value="py_edit_yearend_bonus"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_print_yearend_bonus" value="py_print_yearend_bonus"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_export_yearend_bonus" value="py_export_yearend_bonus"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">PHIC Contribution Table</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_phic_contribution" value="py_phic_contribution"></div>
									<div class="cb"></div>
								</li>
								<li>
									<div class="floatLeftTextModuleAccess">Tax Table</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="payrollModule" id="py_tax_table" value="py_tax_table"></div>
									<div class="cb"></div>
								</li>							
							</ul>
			  			</div>
			  		</div>
			  		
			  		<div>
			  			<div class="moduleAccessGroupHeader">EMPLOYEES LOAN</div>
			  			<div class="moduleAccessDetailContanier" style="height: 75px;">
			  				<ul>
			  					<li>
									<div class="floatLeftTextModuleAccess">Loan Options</div>
									<div class="floatLeftTextFirst">View</div>
									<div class="floatLeftText">Add</div>
									<div class="floatLeftText">Edit</div>
									<div class="floatLeftText">Print</div>
									<div class="floatLeftText">Export</div>
									<div class="cb"></div>
								</li>					
								<li>
									<div class="floatLeftTextModuleAccess">Loan and Payments</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="employeesLoanModule" id="el_loan_payments" value="el_loan_payments"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeesLoanModule" id="el_add_loan_payments" value="el_add_loan_payments"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeesLoanModule" id="el_edit_loan_payments" value="el_edit_loan_payments"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeesLoanModule" id="el_print_loan_payments" value="el_print_loan_payments"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="employeesLoanModule" id="el_export_loan_payments" value="el_export_loan_payments"></div>
									<div class="cb"></div>
								</li>															
							</ul>
			  			</div>
			  		</div>
			  		
			  		<div>
			  			<div class="moduleAccessGroupHeader">PAYROLL REPORTS</div>
			  			<div class="moduleAccessDetailContanier" style="height: 75px;">
			  				<ul>	
			  					<li>
									<div class="floatLeftTextModuleAccess">Report Options</div>
									<div class="floatLeftTextFirst">View</div>									
									<div class="floatLeftText">Print</div>
									<div class="floatLeftText">Export</div>
									<div class="cb"></div>
								</li>				
								<li>
									<div class="floatLeftTextModuleAccess">Payroll Reports</div>
									<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="payrollReportsModule" id="pr_payroll_reports" value="pr_payroll_reports"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollReportsModule" id="pr_print_payroll_reports" value="pr_print_payroll_reports"></div>
									<div class="floatLeftCheckBox"><input type="checkbox" class="checkbox1" name="payrollReportsModule" id="pr_export_payroll_reports" value="pr_export_payroll_reports"></div>						
									<div class="cb"></div>
								</li>															
							</ul>
			  			</div>
			  		</div>
				   	
				   	<!-- 
				   			
				    <div class="moduleAccessHeader" style="margin-top: 20px;">File Management</div>			    
				    <div class="myCheckboxTxt">
				    <input type="checkbox" name="fileManagementModule" class="myCheckbox" id="fm_job_title" value="fm_job_title" />Job Title
				    <input type="checkbox" name="fileManagementModule" class="myCheckbox" id="fm_holidays" value="fm_holidays" />Holidays
				    <input type="checkbox" name="fileManagementModule" class="myCheckbox" id="fm_loan_type" value="fm_loan_type" />Loan Type
				    <input type="checkbox" name="fileManagementModule" class="myCheckbox" id="fm_leave_type" value="fm_leave_type" />Leave Type
				    <input type="checkbox" name="fileManagementModule" class="myCheckbox" id="fm_department" value="fm_department" />Department
				    <input type="checkbox" name="fileManagementModule" class="myCheckbox" id="fm_division" value="fm_division" />Division
				    <input type="checkbox" name="fileManagementModule" class="myCheckbox" id="fm_city" value="fm_city" />City
				    <input type="checkbox" name="fileManagementModule" class="myCheckbox" id="fm_province" value="fm_province" />Province
				    <input type="checkbox" name="fileManagementModule" class="myCheckbox" id="fm_country" value="fm_country" />Country
				    <input type="checkbox" name="fileManagementModule" class="myCheckbox" id="fm_module_access" value="fm_module_access" />Module Access
				    </div>
				    <div class="cb"></div>
				    <div class="moduleAccessHeader">Employee</div>
				    <div class="myCheckboxTxt">
				    <input type="checkbox" name="employeeModule" class="myCheckbox" id="em_employee" value="em_employee" />Employee
				    <input type="checkbox" name="employeeModule" class="myCheckbox" id="em_memo" value="em_memo" />Memo
				    <input type="checkbox" name="employeeModule" class="myCheckbox" id="em_notification" value="em_notification" />Notification
				    <input type="checkbox" name="employeeModule" class="myCheckbox" id="em_file_leave" value="em_file_leave" />File Leave
				    <input type="checkbox" name="employeeModule" class="myCheckbox" id="em_file_ooo" value="em_file_ooo" />File Out of Office
				    <input type="checkbox" name="employeeModule" class="myCheckbox" id="em_file_ot" value="em_file_ot" />File Overtime
				    <input type="checkbox" name="employeeModule" class="myCheckbox" id="em_leave_approval" value="em_leave_approval" />Leave Approval<br/>
				    <input type="checkbox" name="employeeModule" class="myCheckbox" id="em_leave_approval_hr" value="em_leave_approval_hr" />Leave Approval for HR
				    <input type="checkbox" name="employeeModule" class="myCheckbox" id="em_ooo_approval" value="em_ooo_approval" />Out of Office Approval
				    <input type="checkbox" name="employeeModule" class="myCheckbox" id="em_ot_approval" value="em_ot_approval" />Overtime Approval
				    </div>
				    <div class="cb"></div>
				    <div class="moduleAccessHeader">Employee Time Management</div>
				    <div class="myCheckboxTxt">
				    <input type="checkbox" name="timeManagementModule" class="myCheckbox" id="tm_create_employee_shift" value="tm_create_employee_shift" />Create Employee Shift
				    <input type="checkbox" name="timeManagementModule" class="myCheckbox" id="tm_attendance_monitoring" value="tm_attendance_monitoring" />Attendance Monitoring
				    <input type="checkbox" name="timeManagementModule" class="myCheckbox" id="tm_employee_schedule" value="tm_employee_schedule" />Employee Schedule
				    <input type="checkbox" name="timeManagementModule" class="myCheckbox" id="tm_time_dispute_hr_approval" value="tm_time_dispute_hr_approval" />Time Dispute HR Approval
				    </div>
				    <div class="cb"></div>
				    <div class="moduleAccessHeader">Payroll</div>
				    <div class="myCheckboxTxt">
				    <input type="checkbox" name="payrollModule" class="myCheckbox" id="py_generate_lock_payroll" value="py_generate_lock_payroll" />Generate/Lock Payroll
				    <input type="checkbox" name="payrollModule" class="myCheckbox" id="py_payroll_period" value="py_payroll_period" />Payroll Period
				    <input type="checkbox" name="payrollModule" class="myCheckbox" id="py_income_benefits" value="py_income_benefits" />Income and Benefits
				    <input type="checkbox" name="payrollModule" class="myCheckbox" id="py_deductions" value="py_deductions" />Deductions
				    <input type="checkbox" name="payrollModule" class="myCheckbox" id="py_case_rate" value="py_case_rate" />Case Rate
				    <input type="checkbox" name="payrollModule" class="myCheckbox" id="py_professional_fee" value="py_professional_fee" />Professional Fee<br/>
				    <input type="checkbox" name="payrollModule" class="myCheckbox" id="py_phic_contribution" value="py_phic_contribution" />PHIC Contribution Table
				    <input type="checkbox" name="payrollModule" class="myCheckbox" id="py_tax_table" value="py_tax_table" />Tax Table
				    </div>
				    <div class="cb"></div>
				    <div class="moduleAccessHeader">Employee's Loan</div>
				    <div class="myCheckboxTxt">
				    <input type="checkbox" name="employeesLoanModule" class="myCheckbox" id="el_loan_payments" value="el_loan_payments" />Loan and Payments
				    </div>
				    <div class="cb"></div>
				    <div class="moduleAccessHeader">Payroll Reports</div>
				    <div class="myCheckboxTxt">
				    <input type="checkbox" name="payrollReportsModule" class="myCheckbox" id="pr_payroll_reports" value="pr_payroll_reports" />Payroll Reports
				    </div>
				     -->
				    <div class="cb"></div>				    		    
			   <div class="cb" style="height: 200px;"></div>
			  
			  <div class="cb"></div>
			</form>
			</div>
		</div>		
	</div>
</div>
<div><c:import url="footer.jsp" /></div>
</body>
</html>