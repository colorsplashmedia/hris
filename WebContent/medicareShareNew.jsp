<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Medicare Share</title>
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
			<c:if test="${fm == 'py_medicare_share'}">
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
	var numDays = document.getElementById("numDays").value;
	var ratePerDay = document.getElementById("ratePerDay").value;
	var remarks = document.getElementById("remarks").value;
	var date = document.getElementById("date").value;
	
	var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
	var digitsOnly = new RegExp(/^[0-9]+$/);
	
	
	
	if(numDays == ""){
		alert("No. of Days is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(digitsOnly.test(numDays)){
		//alert("Pass");
		if(numDays == "0"){
			alert("No. of Days should be numberic and should be greater than 0.");
			errorExist = "Y";
		}
	} else {
		alert("No. of Days should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
	
	if(ratePerDay == ""){
		alert("Rate per day is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(rx.test(ratePerDay)){
		//alert("Pass");
	} else {
		alert("Rate per day should be numberic and should be greater than 0.");
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
	
	if(date == ""){
		alert("Date is a required field.");
		errorExist = "Y";
		return;
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
			url : "/hris/SaveMedicareShareNewAction?empId="+empId+"&numDays="+numDays+"&ratePerDay="+ratePerDay+"&date="+date+"&netAmountDue="+netAmountDue+"&remarks="+remarks,
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {
				
				alert('Medicare Share has been successfully saved to the selected employees.');
				
				window.location = "medicareShareNew.jsp";
			},
			error : function(data) {
				alert('error submitTransaction MedicareShare(): ' + data);
			}

		});
	}
	
	
}

</script>

<script type="text/javascript">

function cancelProcessing() {	
	if (confirm('Are you sure you want to cancel the transaction?')) {
		window.location = "medicareShare.jsp";		
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
			<div style="width: 730px; border: 1px solid black; height: 280px; margin: 0px 0px 0px 15px;">
				<div style="background-color: black; color: white; padding: 10px;">SAVE MEDICARE SHARE</div>
				
				<div class="dataEntryText" style="text-indent: 10px;">No. of Days</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="numDays" name="numDays" size="150"  value="" style="width:520px;"  placeholder="No. of Days" />
				</div>
				<div class="dataEntryText" style="text-indent: 10px;">Rate per day</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="ratePerDay" name="ratePerDay" size="150"  value="" style="width:520px;"  placeholder="Rate per day" />
				</div>
				<div class="dataEntryText" style="text-indent: 10px;">Net Amount Due</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="netAmountDue" name="netAmountDue" size="150"   value="" style="width:520px;"  placeholder="Net Amount Due" />
				</div>
				<div class="cb"></div>
				<div class="dataEntryText" style="text-indent: 10px;">Date</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="date" name="date" size="150" class="useDPicker"  value="" style="width:520px;"  placeholder="Date" />
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