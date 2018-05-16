<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Obligation Request Entry</title>
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
<style>
.jtable-input-field-container {
    margin-bottom: 5px;
    display: block;
    float: none;
}
#jtable-create-form {
    display: block;
    width: 100%;
    -moz-column-gap:40px;
    /* Firefox */
    -webkit-column-gap:40px;
    /* Safari and Chrome */
    column-gap:40px;
    -moz-column-count:2;
    /* Firefox */
    -webkit-column-count:2;
    /* Safari and Chrome */
    column-count:2;
}
#jtable-edit-form {
    display: block;
    width: 100%;
    -moz-column-gap:40px;
    /* Firefox */
    -webkit-column-gap:40px;
    /* Safari and Chrome */
    column-gap:40px;
    -moz-column-count:2;
    /* Firefox */
    -webkit-column-count:2;
    /* Safari and Chrome */
    column-count:2;
}
</style>

<script type="text/javascript" src="js/common.js"></script>

<script type="text/javascript">

    $(document).ready(function () {
		var empId = '${employeeLoggedIn.empId}';	
    	
    	if(empId.length == 0){
    		alert("You are not allowed to view the page. Please login.");
    		window.location = "LogoutAction";
    		return;
    	}
    	
    	var obligationRequestId = document.getElementById("obligationRequestId").value;	
    	
    	var oAjaxCall = $.ajax({
    		type : "POST",
    		url : "GetObligationRequestAction?obligationRequestId=" + obligationRequestId,
    		cache : false,
    		async : false,
    		dataType : "json",
    		success : function(data) {
    			
    			$('#obligationDate').val(data.Records.obligationDate);
    			$('#fundCluster').val(data.Records.fundCluster);
    			$('#dvNo').val(data.Records.dvNo);
    			$('#payee').val(data.Records.payee);
    			$('#address').val(data.Records.address);
    			$('#particulars').val(data.Records.particulars);
    			$('#responsibilityCenter').val(data.Records.responsibilityCenter);
    			$('#mfo').val(data.Records.mfo);
    			$('#amount').val(data.Records.amount);
    			$('#uacsCode').val(data.Records.uacsCode);
    			$('#amountInWords').val(data.Records.amountInWords);
    			$('#signatory1').val(data.Records.signatory1);
    			$('#signatory2').val(data.Records.signatory2);
    			$('#position1').val(data.Records.position1);
    			$('#position2').val(data.Records.position2);
    			$('#obligationAmount').val(data.Records.obligationAmount);
    			$('#paymentAmount').val(data.Records.paymentAmount);
    			$('#notDueAmount').val(data.Records.notDueAmount);
    			$('#demandAmount').val(data.Records.demandAmount);
    			$('#jevNo').val(data.Records.jevNo);
    			$('#refDate').val(data.Records.refDate);
    			$('#refParticular').val(data.Records.refParticular);

    		},
    		error : function(data) {
    			alert('error: showMemoTable' + data);
    		}

    	});
    	
    	
    	
    });
</script>

<style>
input,select { 
	border: 1px solid #52833b;    
    padding: 6.8px;
    background-color: white;
    margin: 5px 0px 0px 0px;
    font: 12px Arial, Helvetica, sans-serif;
	color: black;
}

textarea {
	margin: 5px 0px 0px 0px;
	width: 1070px;
	height: 150px;
} 
</style>


<script type="text/javascript">

function submitTransaction(){
	var errorExist = "N";
	
	var obligationRequestId = document.getElementById("obligationRequestId").value;	
	var obligationDate = document.getElementById("obligationDate").value;	
	var fundCluster = document.getElementById("fundCluster").value;	
	var dvNo = document.getElementById("dvNo").value;
	var payee = document.getElementById("payee").value;
	var address = document.getElementById("address").value;
	var particulars = document.getElementById("particulars").value;
	var responsibilityCenter = document.getElementById("responsibilityCenter").value;
	var mfo = document.getElementById("mfo").value;
	var amount = document.getElementById("amount").value;
	var uacsCode = document.getElementById("uacsCode").value;
	var amountInWords = document.getElementById("amountInWords").value;	
	var signatory1 = document.getElementById("signatory1").value;
	var position1 = document.getElementById("position1").value;
	var signatory2 = document.getElementById("signatory2").value;
	var position2 = document.getElementById("position2").value;
	var obligationAmount = document.getElementById("obligationAmount").value;	
	var paymentAmount = document.getElementById("paymentAmount").value;
	var notDueAmount = document.getElementById("notDueAmount").value;
	var demandAmount = document.getElementById("demandAmount").value;
	var jevNo = document.getElementById("jevNo").value;
	var refDate = document.getElementById("refDate").value;
	var refParticular = document.getElementById("refParticular").value;
	
	var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
	var digitsOnly = new RegExp(/^[0-9]+$/);
	
	if(rx.test(amount)){
		//alert("Pass");
	} else {
		alert("Amount should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
	
		
	if(payee == ""){
		alert("Payee is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(particulars == ""){
		alert("Particulars is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(signatory1 == ""){
		alert("Certified By is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(signatory2 == ""){
		alert("2nd Certified By is a required field.");
		errorExist = "Y";
		return;
	}
	
	
	
	if(position1 == ""){
		alert("Position of Certified By is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(position2 == ""){
		alert("Position of 2nd Certified By is a required field.");
		errorExist = "Y";
		return;
	}
	
	
	
	if(errorExist == "Y"){
		return;
	} else {
		var oAjaxCall = $.ajax({
			type : "POST",			
			url : "/hris/SaveObligationRequestAction?forEdit=Y&obligationDate="+obligationDate+"&obligationRequestId="+obligationRequestId+"&fundCluster="+fundCluster+"&dvNo="+dvNo+"&payee="+payee+"&address="+address+"&particulars="+particulars+"&responsibilityCenter="+responsibilityCenter+"&mfo="+mfo+"&amount="+amount+"&uacsCode="+uacsCode+"&amountInWords="+amountInWords+"&signatory1="+signatory1+"&signatory2="+signatory2+"&position1="+position1+"&position2="+position2+"&obligationAmount="+obligationAmount+"&paymentAmount="+paymentAmount+"&notDueAmount="+notDueAmount+"&demandAmount="+demandAmount+"&jevNo="+jevNo+"&refDate="+refDate+"&refParticular="+refParticular,
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {
				
				alert('Obligation Request has been successfully saved.');
				
				//window.location = "caseRatePaymentNew.jsp";
			},
			error : function(data) {
				alert('error submitTransaction Obligation Request(): ' + data);
			}

		});
	}
	
	
}

function viewObligationReport (){
	var errorExist = "N";
	
	var obligationDate = document.getElementById("obligationDate").value;	
	var fundCluster = document.getElementById("fundCluster").value;	
	var dvNo = document.getElementById("dvNo").value;
	var payee = document.getElementById("payee").value;
	var address = document.getElementById("address").value;
	var particulars = document.getElementById("particulars").value;
	var responsibilityCenter = document.getElementById("responsibilityCenter").value;
	var mfo = document.getElementById("mfo").value;
	var amount = document.getElementById("amount").value;
	var uacsCode = document.getElementById("uacsCode").value;
	var amountInWords = document.getElementById("amountInWords").value;	
	var signatory1 = document.getElementById("signatory1").value;
	var position1 = document.getElementById("position1").value;
	var signatory2 = document.getElementById("signatory2").value;
	var position2 = document.getElementById("position2").value;
	var obligationAmount = document.getElementById("obligationAmount").value;	
	var paymentAmount = document.getElementById("paymentAmount").value;
	var notDueAmount = document.getElementById("notDueAmount").value;
	var demandAmount = document.getElementById("demandAmount").value;
	var jevNo = document.getElementById("jevNo").value;
	var refDate = document.getElementById("refDate").value;
	var refParticular = document.getElementById("refParticular").value;
	
	var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
	var digitsOnly = new RegExp(/^[0-9]+$/);
	
	if(rx.test(amount)){
		//alert("Pass");
	} else {
		alert("Amount should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
	
		
	if(payee == ""){
		alert("Payee is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(particulars == ""){
		alert("Particulars is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(signatory1 == ""){
		alert("Certified By is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(signatory2 == ""){
		alert("2nd Certified By is a required field.");
		errorExist = "Y";
		return;
	}
	
	
	
	if(position1 == ""){
		alert("Position of Certified By is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(position2 == ""){
		alert("Position of 2nd Certified By is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(errorExist == "Y"){
		return;
	}
	
	url = "/hris/ActionPdfExportObligationRequestReport?forExport=N&obligationDate="+obligationDate+"&fundCluster="+fundCluster+"&dvNo="+dvNo+"&payee="+payee+"&address="+address+"&particulars="+particulars+"&responsibilityCenter="+responsibilityCenter+"&mfo="+mfo+"&amount="+amount+"&uacsCode="+uacsCode+"&amountInWords="+amountInWords+"&signatory1="+signatory1+"&signatory2="+signatory2+"&position1="+position1+"&position2="+position2+"&obligationAmount="+obligationAmount+"&paymentAmount="+paymentAmount+"&notDueAmount="+notDueAmount+"&demandAmount="+demandAmount+"&jevNo="+jevNo+"&refDate="+refDate+"&refParticular="+refParticular;
	swidth = screen.availWidth;
	sheight = screen.availHeight;
	
	openNewPopUpWindow(url, swidth, sheight)
}

function viewObligations () {
	window.location = "viewObligationsList.jsp";
}
	
	
</script>

</head>
<body>
<div><c:import url="header.jsp" /></div>	
<div id="content">
<div style="width: 1230px; border: 1px solid black; height: 630px; margin: 0px 0px 0px 15px; padding: 0px 0px 0px 0px">
	<div style="background-color: black; color: white; padding: 10px;">CREATE OBLIGATION REQUEST</div>
	<div style="width: 1230px; border: 0px solid red; height: 420px; margin: 0px 0px 0px 15px; padding: 0px 0px 0px 0px">
	<input type="hidden" id="obligationRequestId" name="obligationRequestId"  value="${param.obligationRequestId}" />
	<div class="dataEntryTextNew">Fund Cluster</div>		
	<div class="dataEntryInput"><input type="text" name="fundCluster" id="fundCluster" style="width: 250px;" value="" placeholder="Fund Cluster" /></div>
	<div class="dataEntryTextNew">Obligation Date</div>
	<div class="dataEntryInput"><input type="text" name="obligationDate" id="obligationDate" class="useDPicker" style="width: 250px;" value="" placeholder="Obligation Date" /></div>
	<div class="dataEntryTextNew">No.</div>
	<div class="dataEntryInput"><input type="text" name="dvNo" id="dvNo" style="width: 250px;" value="" placeholder="No." /></div>
	<div class="cb"></div>	
	<div class="dataEntryText">Payee</div>
	<div class="dataEntryInput"><input type="text" name="payee" id="payee" style="width: 250px;" value="" placeholder="Payee" /></div>	
	<div class="dataEntryText">Address</div>
	<div class="dataEntryInput"><input type="text" name="address" id="address" style="width: 660px;" value="" placeholder="Address" /></div>
	<div class="cb"></div>
	<div class="dataEntryText">Particulars</div>
	<div class="dataEntryInput"><textarea placeholder="Particulars" name="particulars" id="particulars"></textarea></div>
	<div class="cb"></div>
	<div class="dataEntryText">Resp. Center</div>
	<div class="dataEntryInput"><input type="text" name="responsibilityCenter" id="responsibilityCenter" style="width: 150px;" value="" placeholder="Responsibility Center" /></div>
	<div class="dataEntryText" style="width: 120px;">UACS Code</div>
	<div class="dataEntryInput"><input type="text" name="uacsCode" id="uacsCode" style="width: 150px;" value="" placeholder="UACS Code" /></div>
	<div class="dataEntryText" style="width: 90px;">MFO/PAP</div>
	<div class="dataEntryInput"><input type="text" name="mfo" id="mfo" style="width: 150px;" value="" placeholder="MFO/PAP" /></div>
	<div class="dataEntryText" style="width: 140px;">Amount</div>
	<div class="dataEntryInput"><input type="text" name="amount" id="amount" style="width: 150px;" value="" placeholder="Amount" /></div>
	<div class="cb"></div>
	<div class="dataEntryText">Amount In Words</div>
	<div class="dataEntryInput"><textarea placeholder="Amount in Words" name="amountInWords" id="amountInWords"></textarea></div>
	<div class="cb"></div>	
	<div class="dataEntryText">Certified By</div>
	<div class="dataEntryInput"><input type="text" name="signatory1" id="signatory1" style="width: 150px;" value="" placeholder="Certified By" /></div>
	<div class="dataEntryText" style="width: 120px;">Position</div>
	<div class="dataEntryInput"><input type="text" name="position1" id="position1" style="width: 150px;" value="" placeholder="Position" /></div>
	<div class="dataEntryText" style="width: 90px;">Certified By</div>
	<div class="dataEntryInput"><input type="text" name="signatory2" id="signatory2" style="width: 150px;" value="" placeholder="2nd Certified By" /></div>
	<div class="dataEntryText" style="width: 140px;">Position</div>
	<div class="dataEntryInput"><input type="text" name="position2" id="position2" style="width: 150px;" value="" placeholder="Position" /></div>	
	<div class="cb"></div>
	<div class="dataEntryText">Obligation Amount</div>
	<div class="dataEntryInput"><input type="text" name="obligationAmount" id="obligationAmount" style="width: 150px;" value="" placeholder="Obligation Amount" /></div>
	<div class="dataEntryText" style="width: 120px;">Payment Amount</div>
	<div class="dataEntryInput"><input type="text" name="paymentAmount" id="paymentAmount" style="width: 150px;" value="" placeholder="Payment Amount" /></div>
	<div class="dataEntryText" style="width: 90px;">Not Yet Due</div>
	<div class="dataEntryInput"><input type="text" name="notDueAmount" id="notDueAmount" style="width: 150px;" value="" placeholder="Not Yet Due" /></div>
	<div class="dataEntryText" style="width: 140px;">Demandable Amount</div>
	<div class="dataEntryInput"><input type="text" name="demandAmount" id="demandAmount" style="width: 150px;" value="" placeholder="Demandable Amount" /></div>	
	<div class="cb"></div>
	<div class="dataEntryText">Reference Date</div>
	<div class="dataEntryInput"><input type="text" name="refDate" id="refDate" class="useDPicker" style="width: 150px;" value="" placeholder="Reference Date" /></div>
	<div class="dataEntryText">Ref Particulars</div>
	<div class="dataEntryInput"><input type="text" name="refParticular" id="refParticular" style="width: 430px;" value="" placeholder="Reference Particulars" /></div>
	<div class="dataEntryText" style="width: 140px;">ORS/JEV/RCI No</div>
	<div class="dataEntryInput"><input type="text" name="jevNo" id="jevNo" style="width: 150px;" value="" placeholder="ORS/JEV/RCI/RADAI No." /></div>
	
	<div class="cb"></div>
	<div id="buttonPlaceHolder" style="margin: 20px 0px 0px 0px;">
	<div style="margin: 0px 0px 0px 15px; cursor: pointer;" class="employeeButton" onClick="submitTransaction();">Submit Request</div>
	<div style="cursor: pointer; float:left; margin: 0px 0px 0px 15px" class="employeeButton" onclick="cancelProcessing();">Cancel</div>
	<div style="margin: 0px 0px 0px 15px; cursor: pointer;" class="employeeButton" onClick="viewObligations();">View Obligation Request List</div>
	<div style="margin: 0px 0px 0px 15px; cursor: pointer;" class="employeeButton" onClick="viewObligationReport();">View Obligation Request Report</div>
	</div>

	<div class="cb" style="height: 100px;"></div>	
	</div>
</div>	
</div>
<div style=""><c:import url="footer.jsp" /></div>
</body>
</body>
</html>