<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Overtime Authority Report</title>
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
			<c:if test="${fm == 'py_case_rate'}">
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


function submitTransaction (){
	
	var signatory1 = document.getElementById("signatory1").value;
	var signatory2 = document.getElementById("signatory2").value;	
	var signatory3 = document.getElementById("signatory3").value;	
	var position1 = document.getElementById("position1").value;
	var position2 = document.getElementById("position2").value;	
	var position3 = document.getElementById("position3").value;	
	var divisionHead = document.getElementById("divisionHead").value;	
	var divisionHeadPosition = document.getElementById("divisionHeadPosition").value;
	var purposeOfOvertime = document.getElementById("purposeOfOvertime").value;
	
	var activities1 = document.getElementById("activities1").value;
	var estQty1 = document.getElementById("estQty1").value;
	var estMH1 = document.getElementById("estMH1").value;
	var period1 = document.getElementById("period1").value;
	var time1 = document.getElementById("time1").value;
	
	if(divisionHead == ""){
		alert("Officer is a mandatory field.");
		return;
	}
	
	if(divisionHeadPosition == ""){
		alert("Officer Position is a mandatory field.");
		return;
	}
	
	if(purposeOfOvertime == ""){
		alert("Purpose Of Overtime is a mandatory field.");
		return;
	}
	
	if(signatory1 == ""){
		alert("Requested By is a mandatory field.");
		return;
	}
	
	if(signatory2 == ""){
		alert("Recommended by is a mandatory field.");
		return;
	} 	 	
	
	
	if(signatory3 == ""){
		alert("Approved By is a mandatory field.");
		return;
	}
	
	if(position1 == ""){
		alert("Requested By Position is a mandatory field.");
		return;
	}
	
	if(position2 == ""){
		alert("Recommended by Position is a mandatory field.");
		return;
	}
	
	if(position3 == ""){
		alert("Approved by Position is a mandatory field.");
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
	
		    		    	
	url = "/hris/ActionPdfExportOvertimeAuthorizationReport?empId="+empId+"&signatory1="+signatory1+"&signatory2="+signatory2+"&signatory3="+signatory3+"&position1="+position1+"&position2="+position2+"&forExport=N&position3="+position3+"&divisionHead="+divisionHead+"&divisionHeadPosition="+divisionHeadPosition+"&purposeOfOvertime="+purposeOfOvertime+"&activities1="+activities1+"&estQty1="+estQty1+"&estMH1="+estMH1+"&period1="+period1+"&time1="+time1;
	swidth = screen.availWidth;
	sheight = screen.availHeight;
	
	openNewPopUpWindow(url, swidth, sheight)
	
}

function exportReport (){
	
	var signatory1 = document.getElementById("signatory1").value;
	var signatory2 = document.getElementById("signatory2").value;	
	var leaveType = document.getElementById("leaveType").value;	
	var dateFrom = document.getElementById("datepicker1").value;
	var dateTo = document.getElementById("datepicker2").value;
	
	if(dateFrom == ""){
		alert("Date From is a mandatory field.");
		return;
	}
	
	if(dateTo == ""){
		alert("Date To is a mandatory field.");
		return;
	} 	 	
	
	
	if(leaveType == ""){
		alert("Leave Type is a mandatory field.");
		return;
	}
	
	if(signatory1 == ""){
		alert("Section Head is a mandatory field.");
		return;
	}
	
	if(signatory2 == ""){
		alert("Division Head is a mandatory field.");
		return;
	}
	
	  	
		    		    	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/ActionPdfExportSpecialLeaveReport",
		
		data: "signatory1="+signatory1+"&signatory2="+signatory2+"&dateFrom="+dateFrom+"&dateTo="+dateTo+"&forExport=Y&leaveType="+leaveType,
		cache : false,
		async : false,
		success : function(data) {
			window.location.href = "report/" + data;
		},
		error : function(data) {
			alert('error: ' + data);
		}

	});  
	
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
			<form method="POST" id="saveMemoForm" name="saveMemoForm" action="SaveCaseRatePaymentNewAction">
			<div style="width: 730px; border: 1px solid black; height: 580px; margin: 0px 0px 0px 15px;">
				<div style="background-color: black; color: white; padding: 10px;">OVERTIME AUTHORITY</div>
				<div class="dataEntryText" style="width: 200px; text-indent: 20px;">Officer Name</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="divisionHead" name="divisionHead" size="150"  value="" style="width:400px;"  placeholder="Officer Name" />
				</div>
				<div class="cb"></div>		
				<div class="dataEntryText" style="width: 200px; text-indent: 20px;">Office Position</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="divisionHeadPosition" name="divisionHeadPosition" size="150"  value="" style="width:400px;"  placeholder="Office Position" />
				</div>
				<div class="cb"></div>		
				<div class="dataEntryText" style="width: 200px; text-indent: 20px;">Purpose of Overtime</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="purposeOfOvertime" name="purposeOfOvertime" size="150"   value="" style="width:400px;"  placeholder="Purpose of Overtime" />
				</div>
				<div class="cb"></div>		
				<div class="dataEntryText" style="width: 200px; text-indent: 20px;">Activity to be accomplished</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="activities1" name="activities1" size="150"  value="" style="width:400px;"  placeholder="Activity to be accomplished" />
				</div>
				<div class="cb"></div>				
				<div class="dataEntryText" style="width: 200px; text-indent: 20px;">Estimated Qty</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="estQty1" name="estQty1" size="150"  value="" style="width:400px;"  placeholder="Estimated Qty" />
				</div>
				<div class="cb"></div>
				<div class="dataEntryText" style="width: 200px; text-indent: 20px;">Estimated MH</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="estMH1" name="estMH1" size="150"  value="" style="width:400px;"  placeholder="Estimated MH" />
				</div>
				<div class="cb"></div>
				<div class="dataEntryText" style="width: 200px; text-indent: 20px;">Period Covered</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="period1" name="period1" size="150" class="useDPicker" value="" style="width:400px;"  placeholder="Period Covered" />
				</div>
				<div class="cb"></div>
				<div class="dataEntryText" style="width: 200px; text-indent: 20px;">Requested By</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="signatory1" name="signatory1" size="150"  value="" style="width:400px;"  placeholder="Requested By" />
				</div>
				<div class="cb"></div>
				<div class="dataEntryText" style="width: 200px; text-indent: 20px;">Position</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="position1" name="position1" size="150"  value="" style="width:400px;"  placeholder="Position" />
				</div>					
				<div class="cb"></div>	
				<div class="dataEntryText" style="width: 200px; text-indent: 20px;">Recommended By</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="signatory2" name="signatory2" size="150"  value="" style="width:400px;"  placeholder="Recommended By" />
				</div>					
				<div class="cb"></div>	
				<div class="dataEntryText" style="width: 200px; text-indent: 20px;">Position</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="position2" name="position2" size="150"  value="" style="width:400px;"  placeholder="Position" />
				</div>					
				<div class="cb"></div>	
				<div class="dataEntryText" style="width: 200px; text-indent: 20px;">Approved By</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="signatory3" name="signatory3" size="150"  value="" style="width:400px;"  placeholder="Approved By" />
				</div>					
				<div class="cb"></div>	
				<div class="dataEntryText" style="width: 200px; text-indent: 20px;">Position</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="position3" name="position3" size="150"  value="" style="width:400px;"  placeholder="Position" />
				</div>					
				<div class="cb"></div>	
				
				<div class="dataEntryText" style="width: 200px; text-indent: 20px;">Time</div>
				<div class="dataEntryInputRequisition">
					<input type="text" id="time1" name="time1" size="150"  value="" style="width:400px;"  placeholder="Time" />
				</div>					
				<div class="cb"></div>	
						
			</div>			
		    <div class="cb" style="height: 20px;"></div>    	
		    <c:import url="empSearchForReport.jsp" />			
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