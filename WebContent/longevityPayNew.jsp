<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Longevity Pay</title>
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
    	
    	<c:forEach var="fm" items="${sessionScope.moduleAccess.payrollList}">
			<c:if test="${fm == 'py_longevity_pay'}">
				isAllowed = 'YES';
			</c:if>			
		</c:forEach>
		    	
		
		if(isAllowed == 'NO'){
			alert("You are not Viewed to view the page. Please login.");
			window.location = "LogoutAction";
			return;
		}
		
		populateCommonDropDownInLeftSearch();
    	
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
	
	var netAmountDue = document.getElementById("netAmountDue").value;
	var year = document.getElementById("year").value;
	var month = document.getElementById("month").value;
	var remarks = document.getElementById("remarks").value;
	
	var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
	var digitsOnly = new RegExp(/^[0-9]+$/);
	
	
	
	if(year == ""){
		alert("Year is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(digitsOnly.test(year)){
		//alert("Pass");
		if(year == "0"){
			alert("Year should be numberic and should be greater than 0.");
			errorExist = "Y";
		}
	} else {
		alert("Year should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
	
	if(month == ""){
		alert("Month is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(digitsOnly.test(month)){
		//alert("Pass");
		if(month == "0"){
			alert("Month should be numberic and should be greater than 0.");
			errorExist = "Y";
		}
	} else {
		alert("Month should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
	
	
	if(netAmountDue == ""){
		alert("Net Amount Due is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(rx.test(netAmountDue)){
		//alert("Pass");
	} else {
		alert("Net Amount Due should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
	
		
	var empId = [];	
	
	$("#saveMemoForm").find(":input[name*='empId']").each(function(){
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
			url : "/hris/SaveLongevityPayNewAction?empId="+empId+"&year="+year+"&month="+month+"&netAmountDue="+netAmountDue+"&remarks="+remarks,
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {
				
				alert('Longevity Pay has been successfully saved to the selected employees.');
				
				window.location = "longevityPayNew.jsp";
			},
			error : function(data) {
				alert('error submitTransaction Longevity Pay(): ' + data);
			}

		});
	}
	
	
}

</script>

<script type="text/javascript">

function cancelProcessing() {	
	if (confirm('Are you sure you want to cancel the transaction?')) {
		window.location = "longevityPay.jsp";		
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
			<form method="POST" id="saveMemoForm" name="saveMemoForm" action="SaveLongevityPayNewAction">
			<div style="width: 730px; border: 1px solid black; height: 240px; margin: 0px 0px 0px 15px;">
				<div style="background-color: black; color: white; padding: 10px;">SAVE LONGEVITY PAY</div>
				
				<div class="dataEntryText" style="text-indent: 10px;">Net Amount Due</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="netAmountDue" name="netAmountDue" size="150"  value="" style="width:520px;"  placeholder="Net Amount Due" />
				</div>
				<div class="dataEntryText" style="text-indent: 10px;">Year</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="year" name="year" size="150"  value="" style="width:520px;"  placeholder="Year" />
				</div>
				<div class="dataEntryText" style="text-indent: 10px;">Month</div>
				<div class="dataEntryInputRequisition">
					<select name="month" id="month" style="width:520px; height:31px; background:#fff; border: 1px solid #52833b; margin-top: 5px; color: black;" >													
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
				<div class="cb"></div>
				<div class="dataEntryText" style="text-indent: 10px;">Remarks</div>
				<div class="dataEntryInputRequisition">
					<textarea rows="4" cols="61" id="remarks" name="remarks" placeholder="Enter your remarks here..." style="margin: 10px 0px 0px 0px; width:520px;"></textarea>
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