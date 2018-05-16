<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Exclude From Payroll</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@include file="commonHeader.jsp" %>
<%@include file="commonJtables.jsp" %>
<link rel="stylesheet" type="text/css" href="css/styleTables.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link href="js/validationEngine/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/validationEngine/jquery.validationEngine.js"></script>
<script type="text/javascript" src="js/validationEngine/jquery.validationEngine-en.js"></script>
<script type="text/javascript" src="js/common.js"></script>

<link rel="stylesheet"  title="formal" href="css/tableStyle.css">

<script type="text/javascript">

    $(document).ready(function () {
		var empId = '${employeeLoggedIn.empId}';	
    	
    	if(empId.length == 0){
    		alert("You are not allowed to view the page. Please login.");
    		window.location = "LogoutAction";
    		return;
    	}
    	
    	var isAllowed = 'NO';
    	
    	<c:forEach var="fm" items="${sessionScope.moduleAccess.employeeList}">
			<c:if test="${fm == 'em_memo'}">
				isAllowed = 'YES';
			</c:if>			
		</c:forEach>
		    	
		
		if(isAllowed == 'NO'){
			alert("You are not Viewed to view the page. Please login.");
			window.location = "LogoutAction";
			return;
		}
		
		populateCommonDropDownInLeftSearch();
		populatePayrollPeriod();
		populateDeductionDropDown();
    	
    });
    
    
    $(document).on('click','.removebutton', function() {    
    	if (confirm('Are you sure you want to remove the employee?')) {
    		
    		
    		//TO REMOVE ITEM IN ITEM SET
    		$(this).closest('tr').remove();		    		
      		
      		return false;
    	}
    });
</script>

<script type="text/javascript">

function submitTransaction(){
	
	var errorExist = "N";
	var payrollPeriodId = document.getElementById("payrollPeriodDropDownID").value;
	
	
	var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
		
	
	var empId = [];	
	
	$("#saveDeductionForm").find(":input[name*='empId']").each(function(){
		if($(this).val() == ""){
			alert("Employee ID is a required field.");
			errorExist = "Y";
		}
		empId.push($(this).val());
	});
	
	if (empId.length == 0) {
		alert("You need to select at least one employee to proceed.");
		errorExist = "Y";
	}
	
	if(errorExist == "Y"){
		return;
	} else {
		var oAjaxCall = $.ajax({
			type : "POST",			
			url : "/hris/SavePayrollExclusionAction?empId="+empId+"&payrollPeriodId="+payrollPeriodId,
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {
				
				alert('Exclusion has been successfully applied to the selected employees.');
				
				window.location = "excludePayroll.jsp";
			},
			error : function(data) {
				alert('error submitTransaction Exclusion(): ' + data);
			}

		});
	}
	
	
}
	
</script>



</head>
<body>
<div><c:import url="header.jsp" /></div>	
<div>		
	<div id="content">
		<input type="hidden" name="empIdLoggedIn" id="empIdLoggedIn" value="${employeeLoggedIn.empId}" />
 		<input type="hidden" name="ctrOfItems" id="ctrOfItems" value="0" />
		<div id="dashBoardLeftPannel2">
			<c:import url="leftSideFilter.jsp" />
		</div>
		
		<!-- Right Side of Dashboard -->
		<div  id="dashBoardRightPannel2" width="100%">	
			<form method="POST" id="saveDeductionForm" name="saveDeductionForm" action="SaveReceivingEntryAction">
			<div style="width: 730px; border: 1px solid black; height: 90px; margin: 0px 0px 0px 15px;">
				<div style="background-color: black; color: white; padding: 10px;">ADD EMPLOYEE PAYROLL EXCLUSION</div>
						
				<div class="dataEntryText" style="text-indent: 10px;">Payroll Period</div>
				<div class="dataEntryInputRequisition">
					<select name="payrollPeriodId" id="payrollPeriodDropDownID" style="width: 520px; padding: 6.5px;" >										
						
					</select>
				</div>		
				<div class="cb"></div>		
			</div>			
		    <div class="cb" style="height: 20px;"></div>    				
		  	<c:import url="empSearchTable.jsp" />	
			<div class="cb" style="margin: 20px 0px 0px 10px; height: 20px;"></div>
		  	</form>
		</div>	
	</div>
	<div class="cb" style="margin: 0px 0px 30px 10px; height: 60px;"></div>
</div>
<div><c:import url="footer.jsp" /></div>
</body>
</body>
</html>