<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Year End Bonus and Cash Gift</title>
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
	var firstHalf13thMonth = document.getElementById("firstHalf13thMonth").value;
	var secondHalf13thMonth = document.getElementById("secondHalf13thMonth").value;
	var firstHalfCashGift = document.getElementById("firstHalfCashGift").value;
	var secondHalfCashGift = document.getElementById("secondHalfCashGift").value;
	
	var totalYearEndBonusCashGift = document.getElementById("totalYearEndBonusCashGift").value;
	var eamcCoopDeduction = document.getElementById("eamcCoopDeduction").value;
	var netAmountDue = document.getElementById("netAmountDue").value;
	var year = document.getElementById("year").value;
	
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
	
	if(firstHalf13thMonth == ""){
		alert("First Half 13th Month is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(rx.test(firstHalf13thMonth)){
		//alert("Pass");
	} else {
		alert("First Half 13th Month should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
	
	if(secondHalf13thMonth == ""){
		alert("Second Half 13th Month is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(rx.test(secondHalf13thMonth)){
		//alert("Pass");
	} else {
		alert("Second Half 13th Month should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
	
	if(firstHalfCashGift == ""){
		alert("First Half Cash Gift is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(rx.test(firstHalfCashGift)){
		//alert("Pass");
	} else {
		alert("First Half Cash Gift should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
	
	if(secondHalfCashGift == ""){
		alert("Second Half Cash Gift is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(rx.test(secondHalfCashGift)){
		//alert("Pass");
	} else {
		alert("Second Half Cash Gift should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
	
	
	if(totalYearEndBonusCashGift == ""){
		alert("Total Year End Bonus and Cash Gift is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(rx.test(totalYearEndBonusCashGift)){
		//alert("Pass");
	} else {
		alert("Total Year End Bonus and Cash Gift should be numberic and should be greater than 0.");
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
			url : "/hris/SaveYearEndBonusCashGiftNewAction?empId="+empId+"&firstHalf13thMonth="+firstHalf13thMonth+"&secondHalf13thMonth="+secondHalf13thMonth+"&firstHalfCashGift="+firstHalfCashGift+"&secondHalfCashGift="+secondHalfCashGift+"&totalYearEndBonusCashGift="+totalYearEndBonusCashGift+"&eamcCoopDeduction="+eamcCoopDeduction+"&netAmountDue="+netAmountDue+"&year="+year,
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {
				
				alert('Year End Bonus and Cash Gift has been successfully saved to the selected employees.');
				
				window.location = "yearEndBonusNew.jsp";
			},
			error : function(data) {
				alert('error submitTransaction YearEndBonus(): ' + data);
			}

		});
	}
	
	
}

</script>

<script type="text/javascript">

function cancelProcessing() {	
	if (confirm('Are you sure you want to cancel the transaction?')) {
		window.location = "yearEndBonus.jsp";		
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
			<div style="width: 730px; border: 1px solid black; height: 200px; margin: 0px 0px 0px 15px;">
				<div style="background-color: black; color: white; padding: 10px;">SAVE YEAR END BONUS AND CASH GIFT</div>
				
				<div class="dataEntryText" style="text-indent: 10px; width: 200px;">1st Half 13 Month Pay</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="firstHalf13thMonth" name="firstHalf13thMonth" size="150"  value="" style="width:150px;"  placeholder="1st Half 13 Month Pay" />
				</div>
				<div class="dataEntryText" style="text-indent: 10px; width: 150px;">2nd Half 13 Month Pay</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="secondHalf13thMonth" name="secondHalf13thMonth" size="150"  value="" style="width:150px;"  placeholder="2nd Half 13 Month Pay" />
				</div>
				<div class="cb"></div>
				<div class="dataEntryText" style="text-indent: 10px; width: 200px;">1st Half Cash Gift</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="firstHalfCashGift" name="firstHalfCashGift" size="150"   value="" style="width:150px;"  placeholder="1st Half Cash Gift" />
				</div>
				<div class="dataEntryText" style="text-indent: 10px; width: 150px;">2nd Half Cash Gift</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="secondHalfCashGift" name="secondHalfCashGift" size="150"   value="" style="width:150px;"  placeholder="2nd Half Cash Gift" />
				</div>
				<div class="cb"></div>
				<div class="dataEntryText" style="text-indent: 10px; width: 200px;">Total 13th Month and Cash Gift</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="totalYearEndBonusCashGift" name="totalYearEndBonusCashGift" size="150"   value="" style="width:150px;"  placeholder="Total 13th Month and Cash Gift" />
				</div>	
				<div class="dataEntryText" style="text-indent: 10px; width: 150px;">Total Deductions</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="eamcCoopDeduction" name="eamcCoopDeduction" size="150"   value="" style="width:150px;"  placeholder="Total Deductions" />
				</div>					
				<div class="cb"></div>	
				<div class="dataEntryText" style="text-indent: 10px; width: 200px;">Net Take Home Pay</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="netAmountDue" name="netAmountDue" size="150"   value="" style="width:150px;"  placeholder="Net Take Home Pay" />
				</div>	
				<div class="dataEntryText" style="text-indent: 10px; width: 150px;">Year</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="year" name="year" size="150"   value="" style="width:150px;"  placeholder="Year" />
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