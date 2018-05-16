<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Incomes/Allowances</title>
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
		populateIncomeDropDown();
    	
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

//$('#incomeType').change(function () {
//	alert("xxx");	  	
//});

</script>

<script type="text/javascript">
	
function submitTransaction(){
	
	var errorExist = "N";
	var incomeId = document.getElementById("incomeDropDownID").value;
	var amount = document.getElementById("amount").value;
	var payrollPeriodId = document.getElementById("payrollPeriodDropDownID").value;
	var payrollCycle = document.getElementById("payrollCycle").value;
	var incomeType = document.getElementById("incomeType").value;
	
	
	var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
	
	if(incomeId == ""){
		alert("Income Name is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(amount == ""){
		alert("Amount is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(rx.test(amount)){
		//alert("Pass");
	} else {
		alert("Amount should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
			
	if(payrollCycle == ""){
		alert("For Second Half is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(incomeType == ""){
		alert("Income Type is a required field.");
		errorExist = "Y";
		return;
	} else {
		if(incomeType == "R"){
			if(payrollPeriodId != ""){
				alert("Payroll Period should not be selected when deduction type is recurring.");
				errorExist = "Y";
				return;
			}
		} else {
			if(payrollPeriodId == ""){
				alert("Payroll Period is a required field if the deduction type is One Time.");
				errorExist = "Y";
				return;
			}
		}
	}
	
	
	
	var empId = [];	
	
	$("#saveIncomeForm").find(":input[name*='empId']").each(function(){
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
			url : "/hris/SaveIncomeNewAction?empId="+empId+"&incomeId="+incomeId+"&amount="+amount+"&payrollPeriodId="+payrollPeriodId+"&payrollCycle="+payrollCycle+"&incomeType="+incomeType,
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {
				
				alert('Income has been successfully saved to the selected employees.');
				
				window.location = "addIncomeNew.jsp";
			},
			error : function(data) {
				alert('error submitTransaction Deduction(): ' + data);
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
			<form method="POST" id="saveIncomeForm" name="saveIncomeForm" action="SaveIncomeNewAction">
			<div style="width: 730px; border: 1px solid black; height: 230px; margin: 0px 0px 0px 15px;">
				<div style="background-color: black; color: white; padding: 10px;">ADD EMPLOYEE INCOME</div>
				<div class="dataEntryText" style="text-indent: 10px;">Income Name</div>
				<div class="dataEntryInputRequisition">
					<select name="incomeId" id="incomeDropDownID" style="width: 520px; padding: 6.5px;" >										
						
					</select>
				</div>
				<div class="dataEntryText" style="text-indent: 10px;">Amount</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="amount" name="amount" size="150"  value="" style="width:520px;"  placeholder="Amount" />
				</div>				
				<div class="cb"></div>
				<div class="dataEntryText" style="text-indent: 10px;">Income Type</div>
				<div class="dataEntryInputRequisition">
					<select name="incomeType" id="incomeType" style="width: 520px; padding: 6.5px;" >										
						<option value="">Select</option>
						<option value="R">Recurring</option>
						<option value="O">One Time</option>
					</select>
				</div>		
				<div class="cb"></div>
				<div class="dataEntryText" style="text-indent: 10px;">Payroll Cycle</div>
				<div class="dataEntryInputRequisition">
					<select name="payrollCycle" id="payrollCycle" style="width: 520px; padding: 6.5px;" >										
						<option value="">Select</option>
						<option value="1">1st Half</option>
						<option value="2">2nd Half</option>
						<option value="2">Both</option>
					</select>
				</div>				
				<div class="cb"></div>		
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