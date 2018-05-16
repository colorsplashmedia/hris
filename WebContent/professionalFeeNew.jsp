<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Professional Fee</title>
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
			<c:if test="${fm == 'py_professional_fee'}">
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
	var officialReceiptNumber = document.getElementById("officialReceiptNumber").value;
	var officialReceiptDate = document.getElementById("officialReceiptDate").value;
	var grossAmount = document.getElementById("grossAmount").value;
	var withHoldingTax = document.getElementById("withHoldingTax").value;
	var finalTax = document.getElementById("finalTax").value;
	var netAmountDue = document.getElementById("netAmountDue").value;
	var remarks = document.getElementById("remarks").value;
	var patientId = document.getElementById("patientId").value;
	var patientName = document.getElementById("patientName").value;
	
	var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
	
	if(officialReceiptNumber == ""){
		alert("Official Receipt Number is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(officialReceiptDate == ""){
		alert("Official Receipt Date is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(grossAmount == ""){
		alert("Gross Amount is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(rx.test(grossAmount)){
		//alert("Pass");
	} else {
		alert("Gross Amount should be numberic and should be greater than 0.");
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
	
	if(withHoldingTax != ""){
		if(rx.test(withHoldingTax)){
			//alert("Pass");
		} else {
			alert("With Holding Tax should be numberic.");
			errorExist = "Y";
		}
	}
	
	if(finalTax != ""){
		if(rx.test(finalTax)){
			//alert("Pass");
		} else {
			alert("Final Tax should be numberic.");
			errorExist = "Y";
		}
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
			url : "/hris/SaveProfessionalFeeNewAction?empId="+empId+"&officialReceiptNumber="+officialReceiptNumber+"&patientId="+patientId+"&patientName="+patientName+"&officialReceiptDate="+officialReceiptDate+"&grossAmount="+grossAmount+"&withHoldingTax="+withHoldingTax+"&finalTax="+finalTax+"&netAmountDue="+netAmountDue+"&remarks="+remarks,
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {
				
				alert('Professional Fee has been successfully saved to the selected employees.');
				
				window.location = "professionalFeeNew.jsp";
			},
			error : function(data) {
				alert('error submitTransaction Professional Fee(): ' + data);
			}

		});
	}
	
	
}

</script>

<script type="text/javascript">

function cancelProcessing() {	
	if (confirm('Are you sure you want to cancel the transaction?')) {
		window.location = "professionalFee.jsp";		
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
			<form method="POST" id="saveMemoForm" name="saveMemoForm" action="SaveProfessionalFeeNewAction">
			<div style="width: 730px; border: 1px solid black; height: 280px; margin: 0px 0px 0px 15px;">
				<div style="background-color: black; color: white; padding: 10px;">SAVE PROFESSIONAL FEE</div>
				<div class="dataEntryText" style="text-indent: 20px;">OR No.</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="officialReceiptNumber" name="officialReceiptNumber" size="150"  value="" style="width:200px;"  placeholder="OR No." />
				</div>
				<div class="dataEntryText" style="text-indent: 0px;">OR Date</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="officialReceiptDate" name="officialReceiptDate" size="150"  value="" class="useDPicker" style="width:200px;"  placeholder="OR Date" />
				</div>
				<div class="dataEntryText" style="text-indent: 20px;">Gross Amount</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="grossAmount" name="grossAmount" size="150"   value="" style="width:200px;"  placeholder="Gross Amount" />
				</div>
				<div class="dataEntryText" style="text-indent: 0px;">WithHolding Tax</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="withHoldingTax" name="withHoldingTax" size="150"  value="" style="width:200px;"  placeholder="WithHolding Tax" />
				</div>								
				<div class="dataEntryText" style="text-indent: 20px;">Final Tax</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="finalTax" name="finalTax" size="150"  value="" style="width:200px;"  placeholder="Final Tax" />
				</div>
				<div class="dataEntryText" style="text-indent: 0px;">Net Amount Due</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="netAmountDue" name="netAmountDue" size="150"  value="" style="width:200px;"  placeholder="Net Amount Due" />
				</div>
				<div class="dataEntryText" style="text-indent: 20px;">Patient ID</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="patientId" name="patientId" size="150"  value="" style="width:200px;"  placeholder="Patient ID" />
				</div>
				<div class="dataEntryText" style="text-indent: 0px;">Patient Name</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="patientName" name="patientName" size="150"  value="" style="width:200px;"  placeholder="Patient Name" />
				</div>				
				<div class="cb"></div>
				<div class="dataEntryText" style="text-indent: 20px;">Remarks</div>
				<div class="dataEntryInputRequisition">
					<textarea rows="4" cols="61" id="remarks" name="remarks" placeholder="Enter your remarks here..." style="margin: 10px 0px 0px 0px; width:560px;"></textarea>
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