<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Generate and Lock Payroll</title>
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
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetAllPayrollPeriodAction?includeLocked=true",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var divs = '';
			  divs +="<table id=\"box-table-a\" summary=\"Payroll Periods\" width=\"1050px;\" style=\"margin: 0px auto 0px auto	;\"     >";				    
			  divs +="<thead><tr>";
			  divs +="<th scope=\"col\">Payroll Type</th>";
			  divs +="<th scope=\"col\">Date From</th>";
			  divs +="<th scope=\"col\">Date To</th>";
			  divs +="<th scope=\"col\">Payroll Code</th>";
			  divs +="<th scope=\"col\">Status</th>";			  
			  divs +="<th scope=\"col\">&nbsp;</th></tr></thead><tbody>";	
			  jQuery.each(data.Records, function(i, item) {				
			  		var payrollType = "";
			  		var status = "";			  		
			  		var isContractualRecord = item.isContractual;
			  		
			  		if(isContractualRecord == 'N'){
				  		if(item.payrollType == 'M'){
				  			payrollType = "Monthly";
				  		}
				  		
				  		if(item.payrollType == 'S'){
				  			payrollType = "Semi-Monthly";
				  		}
				  		
				  		if(item.payrollType == 'W'){
				  			payrollType = "Weekly";
				  		}
				  		
				  		if(item.payrollType == 'D'){
				  			payrollType = "Daily";
				  		}
				  		
				  		if(item.status == 'N'){
				  			status = "New";
				  		}
				  		
				  		if(item.status == 'G'){
				  			status = "Generated";
				  		}
				  		
				  		if(item.status == 'L'){
				  			status = "Locked";
				  		}
				  		
				  			  	  
					  	
					  	divs +="<tr>"
						divs +="<td>" + payrollType + "</td>";
						divs +="<td>" + item.fromDate + "</td>";
						divs +="<td>" + item.toDate + "</td>";
						divs +="<td>" + item.payrollCode + "</td>";
						divs +="<td>" + status + "</td>";
						
						//item.status = "L";
						
						if(item.status == 'N'){
							
							
				  			divs +="<td>"
				  										
				  			divs +=  "<div class='payrollButtonActive' onclick=\"generateNightDiff(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">GENERATE NIGHT DIFF</div>";
				  			divs +=  "<div class='payrollButtonActive' onclick=\"generatePayroll(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">GENERATE PAYROLL</div>";
				  			divs +=  "<div class='payrollButtonDisabled'>LOCK PAYROLL</div>";
				  			divs +=  "<div class='payrollButtonDisabled'>VIEW PAYROLL REGISTER</div>";
				  			divs +=  "<div class='payrollButtonDisabled'>VIEW NIGHT DIFF</div>";
				  										
							divs +="</td>"
				  		}
						
						if(item.status == 'M'){
				  			divs +="<td>"
				  			
				  			divs +=  "<div class='payrollButtonActive' onclick=\"generateNightDiff(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">RE-GENERATE NIGHT DIFF</div>";
				  			divs +=  "<div class='payrollButtonActive' onclick=\"generatePayroll(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">GENERATE PAYROLL</div>";
				  			divs +=  "<div class='payrollButtonDisabled'>LOCK PAYROLL</div>";
				  			divs +=  "<div class='payrollButtonDisabled'>VIEW PAYROLL REGISTER</div>";
				  			divs +=  "<div class='payrollButtonActive' onclick=\"viewNightDiff(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">VIEW NIGHT DIFF</div>";
 										
							divs +="</td>"							
							
				  		}
				  		
				  		if(item.status == 'G'){
				  			divs +="<td>"
				  			
				  			divs +=  "<div class='payrollButtonActive' onclick=\"generateNightDiff(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">RE-GENERATE NIGHT DIFF</div>";
				  			divs +=  "<div class='payrollButtonActive' onclick=\"generatePayroll(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">RE-GENERATE PAYROLL</div>";
				  			divs +=  "<div class='payrollButtonActive' onclick=\"lockPayroll(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">LOCK PAYROLL</div>";
				  			divs +=  "<div class='payrollButtonActive' onclick=\"viewPayrollReg(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">VIEW PAYROLL REGISTER</div>";
				  			divs +=  "<div class='payrollButtonActive' onclick=\"viewNightDiff(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">VIEW NIGHT DIFF</div>";
				  			
							divs +="</td>"
							
							
				  		}
				  		
				  		if(item.status == 'L'){
				  			
				  			divs +="<td>"							
				  			
							divs +=  "<div class='payrollButtonDisabled'>RE-GENERATE NIGHT DIFF</div>";
							divs +=  "<div class='payrollButtonDisabled'>RE-GENERATE PAYROLL</div>";
				  			divs +=  "<div class='payrollButtonDisabled'>LOCK PAYROLL</div>";
				  			divs +=  "<div class='payrollButtonActive' onclick=\"viewPayrollReg(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">VIEW PAYROLL REGISTER</div>";
				  			divs +=  "<div class='payrollButtonActive' onclick=\"viewNightDiff(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">VIEW NIGHT DIFF</div>";
							
							divs +="</td>";
				  		}
						
						
					  	divs +="</tr>";  
			  		}
				
		        });
			  	
			  	divs +="</tbody></table>";
				
				$('#payrollPeriodContainer').html(divs);
				
				
				
				// FOR CONTRACTUAL
				  divs = '';
				  divs +="<table id=\"box-table-a\" summary=\"Payroll Periods\" width=\"1050px;\" style=\"margin: 0px auto 0px auto	;\"     >";				    
				  divs +="<thead><tr>";
				  //divs +="<th scope=\"col\">Pay Year</th>";
				  //divs +="<th scope=\"col\">Pay Month</th>";
				  divs +="<th scope=\"col\">Payroll Type</th>";
				  divs +="<th scope=\"col\">Date From</th>";
				  divs +="<th scope=\"col\">Date To</th>";
				  //divs +="<th scope=\"col\">Pay Date</th>";
				  divs +="<th scope=\"col\">Payroll Code</th>";
				  divs +="<th scope=\"col\">Status</th>";			  
				  divs +="<th scope=\"col\">&nbsp;</th></tr></thead><tbody>";	
				  jQuery.each(data.Records, function(i, item) {				
				  		var payrollType = "";
				  		var status = "";
				  		var details = "No Details";
				  		var isContractualRecord = item.isContractual;
				  		
				  		if(isContractualRecord == 'Y'){
				  		
				  			if(item.payrollType == 'M'){
					  			payrollType = "Monthly";
					  		}
					  		
					  		if(item.payrollType == 'S'){
					  			payrollType = "Semi-Monthly";
					  		}
					  		
					  		if(item.payrollType == 'W'){
					  			payrollType = "Weekly";
					  		}
					  		
					  		if(item.payrollType == 'D'){
					  			payrollType = "Daily";
					  		}
					  		
					  		if(item.status == 'N'){
					  			status = "New";
					  		}
					  		
					  		if(item.status == 'G'){
					  			status = "Generated";
					  		}
					  		
					  		if(item.status == 'L'){
					  			status = "Locked";
					  		}
					  		
					  			  	  
						  	
						  	divs +="<tr>"
							divs +="<td>" + payrollType + "</td>";
							divs +="<td>" + item.fromDate + "</td>";
							divs +="<td>" + item.toDate + "</td>";
							divs +="<td>" + item.payrollCode + "</td>";
							divs +="<td>" + status + "</td>";
							
							//item.status = "L";
							
							if(item.status == 'N'){
								
								
					  			divs +="<td>"
					  											
					  			divs +=  "<div class='payrollButtonActive' onclick=\"generateNightDiff(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">GENERATE NIGHT DIFF</div>";
					  			divs +=  "<div class='payrollButtonActive' onclick=\"generatePayroll(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">GENERATE PAYROLL</div>";
					  			divs +=  "<div class='payrollButtonDisabled'>LOCK PAYROLL</div>";
					  			divs +=  "<div class='payrollButtonDisabled'>VIEW PAYROLL REGISTER</div>";
					  			divs +=  "<div class='payrollButtonDisabled'>VIEW NIGHT DIFF</div>";
					  			
								divs +="</td>"
					  		}
							
							if(item.status == 'M'){
					  			divs +="<td>"
					  			
					  			divs +=  "<div class='payrollButtonActive' onclick=\"generateNightDiff(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">RE-GENERATE NIGHT DIFF</div>";
					  			divs +=  "<div class='payrollButtonActive' onclick=\"generatePayroll(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">GENERATE PAYROLL</div>";
					  			divs +=  "<div class='payrollButtonDisabled'>LOCK PAYROLL</div>";
					  			divs +=  "<div class='payrollButtonDisabled'>VIEW PAYROLL REGISTER</div>";
					  			divs +=  "<div class='payrollButtonActive' onclick=\"viewNightDiff(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">VIEW NIGHT DIFF</div>";
					  											
					  			divs +="</td>"
																
					  		}
					  		
					  		if(item.status == 'G'){
					  			divs +="<td>"
					  			
					  			divs +=  "<div class='payrollButtonActive' onclick=\"generateNightDiff(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">RE-GENERATE NIGHT DIFF</div>";
					  			divs +=  "<div class='payrollButtonActive' onclick=\"generatePayroll(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">RE-GENERATE PAYROLL</div>";
					  			divs +=  "<div class='payrollButtonActive' onclick=\"lockPayroll(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">LOCK PAYROLL</div>";
					  			divs +=  "<div class='payrollButtonActive' onclick=\"viewPayrollReg(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">VIEW PAYROLL REGISTER</div>";
					  			divs +=  "<div class='payrollButtonActive' onclick=\"viewNightDiff(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">VIEW NIGHT DIFF</div>";
					  			
								divs +="</td>"
																
					  		}
					  		
					  		if(item.status == 'L'){
					  			
					  			divs +="<td>"
								
					  			divs +=  "<div class='payrollButtonDisabled'>RE-GENERATE NIGHT DIFF</div>";
								divs +=  "<div class='payrollButtonDisabled'>RE-GENERATE PAYROLL</div>";
					  			divs +=  "<div class='payrollButtonDisabled'>LOCK PAYROLL</div>";
					  			divs +=  "<div class='payrollButtonActive' onclick=\"viewPayrollReg(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">VIEW PAYROLL REGISTER</div>";
					  			divs +=  "<div class='payrollButtonActive' onclick=\"viewNightDiff(" + item.payrollPeriodId +  ", '" + item.payrollType + "', '" + isContractualRecord + "');\">VIEW NIGHT DIFF</div>";
								
								divs +="</td>";
					  		}
							
							
						  	divs +="</tr>"; 
				  		}
					
			        });
				  	
				  	divs +="</tbody></table>";
					
					$('#payrollPeriodContractual').html(divs);
			
			
			

		},
		error : function(data) {
			alert('error: ' + data);
		}

	});
	
	
});

</script>

<script type="text/javascript">

function generatePayroll(payrollPeriodId, payrollType, isContractualRecord){
	if (confirm('Are you sure you want to generate the payroll?')) {
		var oAjaxCall = $.ajax({
			type : "POST",			
			url : "/hris/GeneratePayrollByPayrollPeriodAction?payrollPeriodId="+payrollPeriodId+"&payrollType="+payrollType+"&isContractualRecord="+isContractualRecord,
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {				
				alert('Payroll has been successfully generated.');
				
				window.location = "generateAndLockPayroll.jsp";
			},
			error : function(data) {
				alert('error generatePayroll(): ' + data);
			}

		});
	}	
}

function generateNightDiff(payrollPeriodId, payrollType, isContractualRecord){
	if (confirm('Are you sure you want to generate the night differential?')) {
		var oAjaxCall = $.ajax({
			type : "POST",			
			url : "/hris/GenerateNightDiffByPayrollPeriodAction?payrollPeriodId="+payrollPeriodId+"&payrollType="+payrollType+"&isContractualRecord="+isContractualRecord,
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {				
				alert('Night Differential has been successfully generated.');
				
				window.location = "generateAndLockPayroll.jsp";
			},
			error : function(data) {
				alert('error generatePayroll(): ' + data);
			}

		});
	}
}

function lockPayroll(payrollPeriodId, payrollType, isContractualRecord){
	if (confirm('Are you sure you want to lock the payroll?')) {
		var oAjaxCall = $.ajax({
			type : "POST",			
			url : "/hris/LockPayrollPeriodAction?payrollPeriodId="+payrollPeriodId,
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {				
				alert('Payroll has been successfully locked.');
				
				window.location = "generateAndLockPayroll.jsp";
			},
			error : function(data) {
				alert('error generatePayroll(): ' + data);
			}

		});
	}
}

function viewPayrollReg(payrollPeriodId, payrollType, isContractualRecord){
	url = "/hris/ActionPdfExportPayrollRegisterReport?forExport=N&payrollPeriodId="+payrollPeriodId;
	swidth = screen.availWidth;
	sheight = screen.availHeight;		
	openNewPopUpWindow(url, swidth, sheight)
}

function viewNightDiff(payrollPeriodId, payrollType, isContractualRecord){
	
	//alert("This feature is still on development. This message will be removed once the module is completed.");
	
	//return;
	
	url = "/hris/ActionPdfExportNightDiffReport?forExport=N&payrollPeriodId="+payrollPeriodId;
	swidth = screen.availWidth;
	sheight = screen.availHeight;		
	openNewPopUpWindow(url, swidth, sheight)
	
	
}

</script>
</head>
<body>
<div><c:import url="header.jsp" /></div>
<div id="content">
	<div style="width: 1050px; margin: auto; text-align: left; border: 0px solid black;">	
		<div class="payrollPeriodHeader">FOR REGULAR AND PROBATIONARY</div>
	    <div id="payrollPeriodContainer" style="margin-bottom: 30px;"></div>		
	    <div class="payrollPeriodHeader">FOR CONTRACTUALS</div>
	    <div id="payrollPeriodContractual"></div>	
	</div>		
</div>	

<div><c:import url="footer.jsp" /></div>
</body>
</html>