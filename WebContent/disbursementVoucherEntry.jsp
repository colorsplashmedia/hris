<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Disbursement Voucher Entry</title>
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

function viewVouchers () {
	window.location = "viewVouchersList.jsp";
}

function submitTransaction(){
	var errorExist = "N";
	
	var disbursementDate = document.getElementById("disbursementDate").value;	
	var fundCluster = document.getElementById("fundCluster").value;	
	var dvNo = document.getElementById("dvNo").value;	
	var modeOfPayment = document.getElementById("modeOfPayment").value;	
	var othersDetail = document.getElementById("othersDetail").value;
	var payee = document.getElementById("payee").value;
	var tin = document.getElementById("tin").value;
	var ors = document.getElementById("ors").value;
	var address = document.getElementById("address").value;
	var responsibilityCenter = document.getElementById("responsibilityCenter").value;
	var mfo = document.getElementById("mfo").value;
	var particulars = document.getElementById("particulars").value;
	var amount = document.getElementById("amount").value;
	var signatory1 = document.getElementById("signatory1").value;
	var signatory2 = document.getElementById("signatory2").value;
	var signatory3 = document.getElementById("signatory3").value;
	var position1 = document.getElementById("position1").value;
	var position2 = document.getElementById("position2").value;
	var position3 = document.getElementById("position3").value;
	var accountingTitle = document.getElementById("accountingTitle").value;
	
	var uacsCode = document.getElementById("uacsCode").value;
	var debit = document.getElementById("debit").value;
	var credit = document.getElementById("credit").value;
	var amountInWords = document.getElementById("amountInWords").value;
	var checkNo = document.getElementById("checkNo").value;
	var checkDate = document.getElementById("checkDate").value;
	var bankDetails = document.getElementById("bankDetails").value;
	var orNo = document.getElementById("orNo").value;
	var orDate = document.getElementById("orDate").value;
	var jevNo = document.getElementById("jevNo").value;
	var certifiedMethod = document.getElementById("certifiedMethod").value;
	var printedName = document.getElementById("printedName").value;
	
	var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
	var digitsOnly = new RegExp(/^[0-9]+$/);
	
	if(rx.test(amount)){
		//alert("Pass");
	} else {
		alert("Amount should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
	
	if(modeOfPayment == ""){
		alert("Mode of Payment is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(modeOfPayment == "OTH"){
		if(othersDetail == ""){
			alert("Other Payment Method is a required field.");
			errorExist = "Y";
			return;
		}		
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
	
	if(signatory3 == ""){
		alert("Approved By is a required field.");
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
	
	if(position3 == ""){
		alert("Position of Approved By is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(errorExist == "Y"){
		return;
	} else {
		var oAjaxCall = $.ajax({
			type : "POST",			
			url : "/hris/SaveDisbursementVoucherAction?forEdit=N&fundCluster="+fundCluster+"&disbursementDate="+disbursementDate+"&dvNo="+dvNo+"&modeOfPayment="+modeOfPayment+"&othersDetail="+othersDetail+"&payee="+payee+"&tin="+tin+"&ors="+ors+"&address="+address+"&particulars="+particulars+"&responsibilityCenter="+responsibilityCenter+"&mfo="+mfo+"&amount="+amount+"&accountingTitle="+accountingTitle+"&uacsCode="+uacsCode+"&debit="+debit+"&credit="+credit+"&amountInWords="+amountInWords+"&signatory1="+signatory1+"&signatory2="+signatory2+"&signatory3="+signatory3+"&position1="+position1+"&position2="+position2+"&position3="+position3+"&checkNo="+checkNo+"&checkDate="+checkDate+"&bankDetails="+bankDetails+"&orNo="+orNo+"&orDate="+orDate+"&jevNo="+jevNo+"&certifiedMethod="+certifiedMethod+"&printedName="+printedName,
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {
				
				alert('Disbursement Voucher has been successfully saved.');
				
				//window.location = "caseRatePaymentNew.jsp";
			},
			error : function(data) {
				alert('error submitTransaction Case Rate Payment(): ' + data);
			}

		});
	}
	
	
}

function viewVoucherReport (){
	var errorExist = "N";
	
	var disbursementVoucherId = document.getElementById("disbursementVoucherId").value;	
	var disbursementDate = document.getElementById("disbursementDate").value;	
	var fundCluster = document.getElementById("fundCluster").value;	
	var dvNo = document.getElementById("dvNo").value;	
	var modeOfPayment = document.getElementById("modeOfPayment").value;	
	var othersDetail = document.getElementById("othersDetail").value;
	var payee = document.getElementById("payee").value;
	var tin = document.getElementById("tin").value;
	var ors = document.getElementById("ors").value;
	var address = document.getElementById("address").value;
	var responsibilityCenter = document.getElementById("responsibilityCenter").value;
	var mfo = document.getElementById("mfo").value;
	var particulars = document.getElementById("particulars").value;
	var amount = document.getElementById("amount").value;
	var signatory1 = document.getElementById("signatory1").value;
	var signatory2 = document.getElementById("signatory2").value;
	var signatory3 = document.getElementById("signatory3").value;
	var position1 = document.getElementById("position1").value;
	var position2 = document.getElementById("position2").value;
	var position3 = document.getElementById("position3").value;
	var accountingTitle = document.getElementById("accountingTitle").value;
	
	var uacsCode = document.getElementById("uacsCode").value;
	var debit = document.getElementById("debit").value;
	var credit = document.getElementById("credit").value;
	var amountInWords = document.getElementById("amountInWords").value;
	var checkNo = document.getElementById("checkNo").value;
	var checkDate = document.getElementById("checkDate").value;
	var bankDetails = document.getElementById("bankDetails").value;
	var orNo = document.getElementById("orNo").value;
	var orDate = document.getElementById("orDate").value;
	var jevNo = document.getElementById("jevNo").value;
	var certifiedMethod = document.getElementById("certifiedMethod").value;
	var printedName = document.getElementById("printedName").value;
	
	var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
	var digitsOnly = new RegExp(/^[0-9]+$/);
	
	if(rx.test(amount)){
		//alert("Pass");
	} else {
		alert("Amount should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
	
	if(modeOfPayment == ""){
		alert("Mode of Payment is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(modeOfPayment == "OTH"){
		if(othersDetail == ""){
			alert("Other Payment Method is a required field.");
			errorExist = "Y";
			return;
		}		
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
	
	if(signatory3 == ""){
		alert("Approved By is a required field.");
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
	
	if(position3 == ""){
		alert("Position of Approved By is a required field.");
		errorExist = "Y";
		return;
	}
	
	if(errorExist == "Y"){
		return;
	}
	
	url = "/hris/ActionPdfExportDisbursementVoucherReport?forExport=N&fundCluster="+fundCluster+"&disbursementVoucherId="+disbursementVoucherId+"&disbursementDate="+disbursementDate+"&dvNo="+dvNo+"&modeOfPayment="+modeOfPayment+"&othersDetail="+othersDetail+"&payee="+payee+"&tin="+tin+"&ors="+ors+"&address="+address+"&particulars="+particulars+"&responsibilityCenter="+responsibilityCenter+"&mfo="+mfo+"&amount="+amount+"&accountingTitle="+accountingTitle+"&uacsCode="+uacsCode+"&debit="+debit+"&credit="+credit+"&amountInWords="+amountInWords+"&signatory1="+signatory1+"&signatory2="+signatory2+"&signatory3="+signatory3+"&position1="+position1+"&position2="+position2+"&position3="+position3+"&checkNo="+checkNo+"&checkDate="+checkDate+"&bankDetails="+bankDetails+"&orNo="+orNo+"&orDate="+orDate+"&jevNo="+jevNo+"&certifiedMethod="+certifiedMethod+"&printedName="+printedName;
	swidth = screen.availWidth;
	sheight = screen.availHeight;
	
	openNewPopUpWindow(url, swidth, sheight)
}
	
	
</script>

</head>
<body>
<div><c:import url="header.jsp" /></div>	
<div id="content">
<div style="width: 1230px; border: 1px solid black; height: 800px; margin: 0px 0px 0px 15px; padding: 0px 0px 0px 0px">
	<div style="background-color: black; color: white; padding: 10px;">CREATE DISBURSEMENT VOUCHER</div>
	<div style="width: 1230px; border: 0px solid red; height: 420px; margin: 0px 0px 0px 15px; padding: 0px 0px 0px 0px">
	<div class="dataEntryText">Mode of Payment</div>
	<div class="dataEntryInput">			    	
		<select name="modeOfPayment" id="modeOfPayment" style="width: 250px;" >
			<option selected="selected" value="">Select Mode of Payment</option>									
			<option value="MDS">MDS Check</option>
			<option value="COM">Commercial Check</option>
			<option value="ADA">ADA</option>
			<option value="OTH">Others</option>
		</select>
	</div>
	<div class="dataEntryInput"><input type="text" name="othersDetail" id="othersDetail" style="width: 250px;" value="" placeholder="Other Payment Method" /></div>
	<div class="cb"></div>
	<div class="dataEntryTextNew">Fund Cluster</div>		
	<div class="dataEntryInput"><input type="text" name="fundCluster" id="fundCluster" style="width: 250px;" value="" placeholder="Fund Cluster" /></div>
	<div class="dataEntryTextNew">Disbursement Date</div>
	<div class="dataEntryInput"><input type="text" name="disbursementDate" id="disbursementDate" class="useDPicker" style="width: 250px;" value="" placeholder="Disbursement Date" /></div>
	<div class="dataEntryTextNew">DV No.</div>
	<div class="dataEntryInput"><input type="text" name="dvNo" id="dvNo" style="width: 250px;" value="" placeholder="DV No." /></div>
	<div class="cb"></div>	
	<div class="dataEntryText">Payee</div>
	<div class="dataEntryInput"><input type="text" name="payee" id="payee" style="width: 250px;" value="" placeholder="Payee" /></div>
	<div class="dataEntryText">TIN/Employee No.</div>
	<div class="dataEntryInput"><input type="text" name="tin" id="tin" style="width: 250px;" value="" placeholder="TIN/Employee No." /></div>
	<div class="dataEntryText">ORS/BURS No.</div>
	<div class="dataEntryInput"><input type="text" name="ors" id="ors" style="width: 250px;" value="" placeholder="ORS/BURS No." /></div>
	<div class="cb"></div>
	<div class="dataEntryText">Address</div>
	<div class="dataEntryInput"><input type="text" name="address" id="address" style="width: 1070px;" value="" placeholder="Address" /></div>
	<div class="cb"></div>
	<div class="dataEntryText">Particulars</div>
	<div class="dataEntryInput"><textarea placeholder="Particulars" name="particulars" id="particulars"></textarea></div>
	<div class="cb"></div>
	<div class="dataEntryText">Resp. Center</div>
	<div class="dataEntryInput"><input type="text" name="responsibilityCenter" id="responsibilityCenter" style="width: 250px;" value="" placeholder="Responsibility Center" /></div>
	<div class="dataEntryText">MFO/PAP</div>
	<div class="dataEntryInput"><input type="text" name="mfo" id="mfo" style="width: 250px;" value="" placeholder="MFO/PAP" /></div>
	<div class="dataEntryText">Amount</div>
	<div class="dataEntryInput"><input type="text" name="amount" id="amount" style="width: 250px;" value="" placeholder="Amount" /></div>
	<div class="cb"></div>	
	<div class="dataEntryText">Certified By</div>
	<div class="dataEntryInput"><input type="text" name="signatory1" id="signatory1" style="width: 250px;" value="" placeholder="Certified By" /></div>
	<div class="dataEntryText">Position</div>
	<div class="dataEntryInput"><input type="text" name="position1" id="position1" style="width: 250px;" value="" placeholder="Position" /></div>
	<div class="cb"></div>
	<div class="dataEntryText">Account Title</div>
	<div class="dataEntryInput"><input type="text" name="accountingTitle" id="accountingTitle" style="width: 250px;" value="" placeholder="Account Title" /></div>
	<div class="dataEntryText">UACS Code</div>
	<div class="dataEntryInput"><input type="text" name="uacsCode" id="uacsCode" style="width: 150px;" value="" placeholder="UACS Code" /></div>
	<div class="dataEntryText" style="width: 60px;">Debit</div>
	<div class="dataEntryInput"><input type="text" name="debit" id="debit" style="width: 150px;" value="" placeholder="Debit" /></div>
	<div class="dataEntryText" style="width: 70px;">Credit</div>
	<div class="dataEntryInput"><input type="text" name="credit" id="credit" style="width: 150px;" value="" placeholder="Credit" /></div>
	<div class="cb"></div>	
	<div class="dataEntryText">Approved Payment</div>
	<div class="dataEntryInput"><textarea placeholder="Amount in Words" name="amountInWords" id="amountInWords"></textarea></div>
	<div class="cb"></div>	
	<div class="dataEntryText">Certified Method</div>
	<div class="dataEntryInput">				    	
		<select name="certifiedMethod" id="certifiedMethod" style="width: 250px;" >
			<option selected="selected" value="">Select Certified Method</option>									
			<option value="CA">Cash Available</option>
			<option value="DB">Subject to Authority to Debit Account</option>
			<option value="SD">Supporting documents complete and amount claimed proper</option>
		</select>	
	</div>
	<div class="dataEntryText">2nd Certified By</div>
	<div class="dataEntryInput"><input type="text" name="signatory2" id="signatory2" style="width: 250px;" value="" placeholder="Certified By" /></div>
	<div class="dataEntryText">Position</div>
	<div class="dataEntryInput"><input type="text" name="position2" id="position2" style="width: 250px;" value="" placeholder="Position" /></div>
	<div class="cb"></div>
	<div class="dataEntryText">Approved By</div>
	<div class="dataEntryInput"><input type="text" name="signatory3" id="signatory3" style="width: 250px;" value="" placeholder="Approved By" /></div>
	<div class="dataEntryText">Position</div>
	<div class="dataEntryInput"><input type="text" name="position3" id="position3" style="width: 250px;" value="" placeholder="Position" /></div>
	<div class="dataEntryText">JEV No</div>
	<div class="dataEntryInput"><input type="text" name="jevNo" id="jevNo" style="width: 250px;" value="" placeholder="JEV No" /></div>
	<div class="cb"></div>
	<div class="dataEntryText">Check/ADA No.</div>
	<div class="dataEntryInput"><input type="text" name="checkNo" id="checkNo" style="width: 250px;" value="" placeholder="Check/ADA No." /></div>
	<div class="dataEntryText">Check Date</div>
	<div class="dataEntryInput"><input type="text" name="checkDate" id="checkDate" class="useDPicker" style="width: 250px;" value="" placeholder="Check Date" /></div>
	<div class="dataEntryText">Bank Details</div>
	<div class="dataEntryInput"><input type="text" name="bankDetails" id="bankDetails" style="width: 250px;" value="" placeholder="Bank Name/Account Number" /></div>
	<div class="cb"></div>	
	<div class="dataEntryText">Printed Name</div>
	<div class="dataEntryInput"><input type="text" name="printedName" id="printedName" style="width: 250px;" value="" placeholder="Printed Name" /></div>
	<div class="dataEntryText">OR No.</div>
	<div class="dataEntryInput"><input type="text" name="orNo" id="orNo" style="width: 250px;" value="" placeholder="OR No." /></div>
	<div class="dataEntryText">OR Date</div>
	<div class="dataEntryInput"><input type="text" name="orDate" id="orDate" class="useDPicker" style="width: 250px;" value="" placeholder="OR Date" /></div>
	<div class="cb"></div>
	<div id="buttonPlaceHolder" style="margin: 20px 0px 0px 0px;">
	<div style="margin: 0px 0px 0px 15px; cursor: pointer;" class="employeeButton" onClick="submitTransaction();">Submit Request</div>
	<div style="cursor: pointer; float:left; margin: 0px 0px 0px 15px" class="employeeButton" onclick="cancelProcessing();">Cancel</div>
	<div style="margin: 0px 0px 0px 15px; cursor: pointer;" class="employeeButton" onClick="viewVouchers();">View Disbursement Voucher List</div>
	<div style="margin: 0px 0px 0px 15px; cursor: pointer;" class="employeeButton" onClick="viewVoucherReport();">View Disbursement Voucher Report</div>
	</div>

	<div class="cb" style="height: 100px;"></div>	
	</div>
</div>	
</div>
<div style=""><c:import url="footer.jsp" /></div>
</body>
</body>
</html>