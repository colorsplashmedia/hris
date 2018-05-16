<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>System Approvers</title>
<%@include file="commonHeader.jsp" %>
<%@include file="commonJtables.jsp" %>
<link rel="stylesheet" type="text/css" href="css/styleTables.css" />
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link rel="stylesheet" href="css/datePickerStyle.css">
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />

<script type="text/javascript" src="js/jquery-ui-1.10.0.min.js"></script>
<script src="js/jquery-1.10.2.js"></script>
<script src="js/common.js"></script>
<script src="js/employee.js"></script>
<script src="js/jquery-ui-1.11.4/jquery-ui.js"></script>
<script type="text/javascript" src="js/moment.min.js"></script>





<script type="text/javascript">
var isSelectedSV1=false;
var isSelectedSV2=false;
var isSelectedSV3=false;
var isSelectedSV4=false;
var isSelectedSV5=false;

$(document).ready(function() {	
	var empId = '${employeeLoggedIn.empId}';	
	
	
	if(empId.length == 0){
		alert("You are not Viewed to view the page. Please login.");
		window.location = "LogoutAction";
		return;
	}
	
	
	
	
	
	$('#selectAllCheckBox').click(function(event) {  //on click 
        if(this.checked) { // check select status
            $('.checkbox1').each(function() { //loop through each checkbox
                this.checked = true;  //select all checkboxes with class "checkbox1"               
            });
        }else{
            $('.checkbox1').each(function() { //loop through each checkbox
                this.checked = false; //deselect all checkboxes with class "checkbox1"                       
            });         
        }
    });
	
	
	$("input[id^=select-sv-button").click(function() {
		$('#searchArea').show();
		
		if ($(this).prop("id") == "select-sv-button-1") {
			isSelectedSV1 = true;
		}
		if ($(this).prop("id") == "select-sv-button-2") {
			isSelectedSV2 = true;
		}
		if ($(this).prop("id") == "select-sv-button-3") {
			isSelectedSV3 = true;
		}	
		if ($(this).prop("id") == "select-sv-button-4") {
			isSelectedSV4 = true;
		}	
		if ($(this).prop("id") == "select-sv-button-5") {
			isSelectedSV5 = true;
		}	
		
		$( "#searchEmployeeDialog" ).dialog({
			show: 'fade',
			hide: 'fade',
			width: 600,
			maxWidth: 600,
			height: 400,
			maxHeight: 400,
			position: { of: "#content", my: "center top", at: "center top+160" },
		    open: function() {
		        // On open, hide the original submit button
		        $( this ).find( "[type=submit]" ).hide();
		    },
		    buttons: [
		        {
		            text: "Close",
		            click: function() {
		                $( this ).dialog( "close" );
		            }
		        }
		    ]
		});
	});
	
	populateForm();
	
	
});	


function clickSearchResult(empid) {		
	if (isSelectedSV1){
		//alert('You chose this supervisor: '+ empSearchMap[empid].firstname + ' ' + empSearchMap[empid].lastname);
		$("#superVisor1FullName").prop("value",  empSearchMap[empid].firstname + ' ' + empSearchMap[empid].lastname);
		$("#superVisor1Id").prop("value", empid);
		isSelectedSV1=false;
	}
	if (isSelectedSV2){
		//alert('You chose this supervisor: '+ empSearchMap[empid].firstname + ' ' + empSearchMap[empid].lastname);
		$("#superVisor2FullName").prop("value",  empSearchMap[empid].firstname + ' ' + empSearchMap[empid].lastname);
		$("#superVisor2Id").prop("value", empid);
		isSelectedSV2=false;
	}
	if (isSelectedSV3){
		//alert('You chose this supervisor: '+ empSearchMap[empid].firstname + ' ' + empSearchMap[empid].lastname);
		$("#superVisor3FullName").prop("value",  empSearchMap[empid].firstname + ' ' + empSearchMap[empid].lastname);
		$("#superVisor3Id").prop("value", empid);
		isSelectedSV3=false;			
	}
	if (isSelectedSV4){
		//alert('You chose this supervisor: '+ empSearchMap[empid].firstname + ' ' + empSearchMap[empid].lastname);
		$("#superVisor4FullName").prop("value",  empSearchMap[empid].firstname + ' ' + empSearchMap[empid].lastname);
		$("#superVisor4Id").prop("value", empid);
		isSelectedSV4=false;			
	}
	if (isSelectedSV5){
		//alert('You chose this supervisor: '+ empSearchMap[empid].firstname + ' ' + empSearchMap[empid].lastname);
		$("#superVisor5FullName").prop("value",  empSearchMap[empid].firstname + ' ' + empSearchMap[empid].lastname);
		$("#superVisor5Id").prop("value", empid);
		isSelectedSV5=false;			
	}
	
	
	
	$( "#searchEmployeeDialog" ).dialog("close");
	
	$('#searchArea').hide();
}

function populateForm(){
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetSystemSettingsAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			console.log(data);
			$('.myCheckbox').attr('checked', false);
			if (data.Record) {
				$("#superVisor1FullName").prop("value",  data.Record.adminName);
				$("#superVisor1Id").prop("value", data.Record.adminId);
				
				$("#superVisor2FullName").prop("value",  data.Record.adminAssistantName);
				$("#superVisor2Id").prop("value", data.Record.adminAssistantId);
				
				$("#superVisor3FullName").prop("value",  data.Record.hrAdminName);
				$("#superVisor3Id").prop("value", data.Record.hrAdminId);
				
				$("#superVisor4FullName").prop("value",  data.Record.hrAdminAssistantName);
				$("#superVisor4Id").prop("value", data.Record.hrAdminAssistantId);
				
				$("#superVisor5FullName").prop("value",  data.Record.hrAdminLiasonName);
				$("#superVisor5Id").prop("value", data.Record.hrAdminLiasonId);
				
				if(data.Record.isAdminChecked == "Y"){
					$('#isAdminChecked').prop('checked', true);
				} else {
					$('#isAdminChecked').prop('checked', false);
				}
				
				
				$('#isNightDiffContractual').val(data.Record.isNightDiffContractual);
				$('#regHrs').val(data.Record.regHrs);
				$('#partimeHrs').val(data.Record.partimeHrs);
				$('#contractualHrs').val(data.Record.contractualHrs);
				$('#contractualBreakHrs').val(data.Record.contractualBreakHrs);
				$('#minPay').val(data.Record.minPay);
			}
		},
		error : function(data) {
			alert('error: systemSettings' + data);
		}
	});
}

function saveModuleAccess() {
	var adminId = document.getElementById("superVisor1Id").value;
	var adminAssistantId = document.getElementById("superVisor2Id").value;
	var hrAdminId = document.getElementById("superVisor3Id").value;
	var hrAdminAssistantId = document.getElementById("superVisor4Id").value;
	var hrAdminLiasonId = document.getElementById("superVisor5Id").value;
	var isNightDiffContractual = document.getElementById("isNightDiffContractual").value;
	
	var isAdminChecked = 'Y';
	
	
	var regHrs = document.getElementById("regHrs").value;
	var partimeHrs = document.getElementById("partimeHrs").value;
	var contractualHrs = document.getElementById("contractualHrs").value;
	var contractualBreakHrs = document.getElementById("contractualBreakHrs").value;
	var minPay = document.getElementById("minPay").value;
	
	var digitsOnly = new RegExp(/^[0-9]+$/);
	var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
	
	if(hrAdminId == "" || hrAdminId == "0"){
		alert("HR Admin is a required approver.");
		return;
	}
	
	if($("#isAdminChecked").is(':checked')){		
		if(adminId == ""  || adminId == "0"){
			alert("Please uncheck checkbox if you want Approvers of Admin blank.");
			return;
		}
	} else {
		isAdminChecked = 'N';
	}
	
	if(isNightDiffContractual == ""){
		alert("Contractual Has Night Differential is a required approver.");
		return;
	}
	
	if(digitsOnly.test(regHrs)){
		if(regHrs == "0"){
			alert("REGULAR EMPLOYEE'S HOURS PER WEEK should be numberic and should be greater than 0.");
			return false;
		}
	} else {
		alert("REGULAR EMPLOYEE'S HOURS PER WEEK should only be numeric and greater than 0.");
		return false;
	}
	
	if(digitsOnly.test(partimeHrs)){
		if(partimeHrs == "0"){
			alert("PART-TIME EMPLOYEE'S HOURS PER WEEK should be numberic and should be greater than 0.");
			return false;
		}
	} else {
		alert("PART-TIME EMPLOYEE'S HOURS PER WEEK should only be numeric and greater than 0.");
		return false;
	}
	
	if(digitsOnly.test(contractualHrs)){
		if(contractualHrs == "0"){
			alert("CONTRACTUAL HOURS PER DAY should be numberic and should be greater than 0.");
			return false;
		}
	} else {
		alert("CONTRACTUAL HOURS PER DAY should only be numeric and greater than 0.");
		return false;
	}
	
	if(digitsOnly.test(contractualBreakHrs)){
		
	} else {
		alert("CONTRACTUAL BREAK HOURS PER DAY should only be numeric and greater than 0.");
		return false;
	}
	
	if(rx.test(minPay)){
		if(minPay == "0"){
			alert("MINIMUM REQUIRED PAY should be numberic and should be greater than 0.");
			return false;
		}
	} else {
		alert("MINIMUM REQUIRED PAY should only be numeric and greater than 0.");
		return false;
	}
	
	
	
	
	var oAjaxCall = $.ajax({
		type : "POST",		
		url : "/hris/SaveApproversAction?adminId="+adminId+"&adminAssistantId="+adminAssistantId+"&hrAdminId="+hrAdminId+"&hrAdminAssistantId="+hrAdminAssistantId+"&hrAdminLiasonId="+hrAdminLiasonId+"&isAdminChecked="+isAdminChecked+"&isNightDiffContractual="+isNightDiffContractual+"&regHrs="+regHrs+"&partimeHrs="+partimeHrs+"&minPay="+minPay+"&contractualHrs="+contractualHrs+"&contractualBreakHrs="+contractualBreakHrs,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {			
			alert('Approvers has been successfully saved.');
		},
		error : function(data) {
			alert('error saveModuleAccess(): ' + data);
		}

	});
}

</script>

<script>

function searchEmployee (){
	var searchKeyword = document.getElementById("searchBox").value;		
	
	var url = '/hris/SelectEmployeeAction?searchByText=true&oSearchText=';		
		
	var oAjaxCall = $.ajax({
			type : "POST",
			url : url + searchKeyword,
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {
				var divs = '';
				divs +="<table id=\"box-table-a\" width=\"520px;\" style=\"margin: 15px auto 0px 15px;\"     >";				    
				divs +="<thead><tr>";					
				divs +="<th scope=\"col\" width=\"70px;\">Emp No</th>";
				divs +="<th scope=\"col\">Name</th>";
				divs +="<th scope=\"col\">Section</th>";
				divs +="<th scope=\"col\">Unit</th></tr></thead><tbody>";
				
				jQuery.each(data.Records, function(i, item) {
					//empSearchMap[item.empId] = { empid: item.empId, firstname:item.firstname, lastname:item.lastname, empno:item.empNo, sectionName:item.sectionName, unitName:item.unitName};			  
											  
					divs +="<tr onclick=\"clickSearchResult("+item.empId+")\" style='cursor:pointer;'>";	
					divs +="<td>" + item.empNo + "</td>";
					divs +="<td>" + item.lastname + ', ' + item.firstname + "</td>";		
					divs +="<td>" + item.sectionName + "</td>";
					divs +="<td>" + item.unitName + "</td>";
					divs +="</tr>"  				  
					
				});
				  	
				divs +="</tbody></table>";
					
				$('#searchHolderId').html(divs);

			},
			error : function(data) {
				alert('error searchEmployee(): ' + data);
			}

		});
	
}





</script>

</head>
<body>
<div><c:import url="header.jsp" /></div>
<div id="content">
    
	<div id="dashBoardRightPannel2" style="margin-left: 50px; margin-top: 0px;">	
		<div id="fixedButtonPlaceHolder"><div id="FixedSubmitButton2" onclick="javascript:saveModuleAccess();">SAVE SETTINGS</div></div>	    
	    <div>
			<div style="height:730px;">			    
			  <form method="POST" id="moduleAccessForm" name="moduleAccessForm" action="SaveModuleAccessAction">
				  	<input type="hidden" name="createdBy" id="createdBy" value="${employeeLoggedIn.empId}" />
				   	<input type="hidden" name="empId" id="empId" value="" />
				   	<input type="hidden" name="moduleAccessId" id="moduleAccessId" value="" />			   	
				   	
				   	<div>
			  			<div class="moduleAccessGroupHeader">ADMIN<div class="floatLeftCheckBoxFirst"><input type="checkbox" class="checkbox1" name="fileManagementModule" id="isAdminChecked" value="Y"></div></div>
			  			<div class="moduleAccessDetailContanier" style="height: 90px;">
			  				<div class="dataEntryText" style="width: 220px;">ADMIN OFFICER</div>
							<div class="dataEntryInput">
								<input type="text" name="superVisor1FullName"  id="superVisor1FullName" value="" disabled/>
								<input type="hidden" name="superVisor1Id" id="superVisor1Id" value="0" />			
								<input type="button" style="padding: 5px 15px 8px 15px;" id="select-sv-button-1" value="SELECT EMPLOYEE">
							</div>
							<div class="cb" style="height: 0px;"></div>
							<div class="dataEntryText" style="width: 220px;">ADMIN OFFICER ASSISTANT</div>
							<div class="dataEntryInput">
								<input type="text" name="superVisor2FullName"  id="superVisor2FullName" value="" disabled/>
								<input type="hidden" name="superVisor2Id" id="superVisor2Id" value="0" />			
								<input type="button" style="padding: 5px 15px 8px 15px;" id="select-sv-button-2" value="SELECT EMPLOYEE">
							</div>			  				
			  			</div>
			  		</div>
			  		
			  		<div>
			  			<div class="moduleAccessGroupHeader">HR ADMIN</div>
			  			<div class="moduleAccessDetailContanier" style="height: 130px;">
			  				<div class="dataEntryText" style="width: 170px;">HR ADMIN</div>
							<div class="dataEntryInput">
								<input type="text" name="superVisor3FullName"  id="superVisor3FullName" value="" disabled/>
								<input type="hidden" name="superVisor3Id" id="superVisor3Id" value="0" />			
								<input type="button" style="padding: 5px 15px 8px 15px;" id="select-sv-button-3" value="SELECT EMPLOYEE">
							</div>
							<div class="cb" style="height: 0px;"></div>
							<div class="dataEntryText" style="width: 170px;">HR ADMIN ASSISTANT</div>
							<div class="dataEntryInput">
								<input type="text" name="superVisor4FullName"  id="superVisor4FullName" value="" disabled/>
								<input type="hidden" name="superVisor4Id" id="superVisor4Id" value="0" />			
								<input type="button" style="padding: 5px 15px 8px 15px;" id="select-sv-button-4" value="SELECT EMPLOYEE">
							</div>
							<div class="cb" style="height: 0px;"></div>
							<div class="dataEntryText" style="width: 170px;">HR ADMIN LIASON</div>
							<div class="dataEntryInput">
								<input type="text" name="superVisor5FullName"  id="superVisor5FullName" value="" disabled/>
								<input type="hidden" name="superVisor5Id" id="superVisor5Id" value="0" />			
								<input type="button" style="padding: 5px 15px 8px 15px;" id="select-sv-button-5" value="SELECT EMPLOYEE">
							</div>
			  			</div>
			  		</div>
			  		
			  		<div>
			  			<div class="moduleAccessGroupHeader">SYSTEM SETTINGS</div>
			  			<div class="moduleAccessDetailContanier" style="height: 240px;">
			  				<div class="dataEntryText" style="width: 370px;">REGULAR EMPLOYEE'S HOURS PER WEEK</div>
							<div class="dataEntryInput">
								<input type="text" name="regHrs"  id="regHrs" value="" />
							</div>
							<div class="cb" style="height: 0px;"></div>
							<div class="dataEntryText" style="width: 370px;">PART-TIME EMPLOYEE'S HOURS PER WEEK</div>
							<div class="dataEntryInput">
								<input type="text" name="partimeHrs"  id="partimeHrs" value="" />
							</div>
							<div class="cb" style="height: 0px;"></div>
							<div class="dataEntryText" style="width: 370px;">CONTRACTUAL TOTAL HOURS</div>
							<div class="dataEntryInput">
								<input type="text" name="contractualHrs"  id="contractualHrs" value="" />
							</div>
							<div class="cb" style="height: 0px;"></div>
							<div class="dataEntryText" style="width: 370px;">CONTRACTUAL BREAK HOURS</div>
							<div class="dataEntryInput">
								<input type="text" name="contractualBreakHrs"  id="contractualBreakHrs" value="" />
							</div>
							<div class="cb" style="height: 0px;"></div>
							<div class="dataEntryText" style="width: 370px;">CONTRACTUAL HAS NIGHT DIFF</div>
							<div class="dataEntryInput">
								<select name="isNightDiffContractual" id="isNightDiffContractual" style="width: 163px; padding: 6.5px; float: left;" >										
									<option value="">Select</option>
									<option value="Y">YES</option>
									<option value="N">NO</option>
								</select>
							</div>
							<div class="cb" style="height: 0px;"></div>
							<div class="dataEntryText" style="width: 370px;">MINIMUM REQUIRED PAY</div>
							<div class="dataEntryInput">
								<input type="text" name="minPay"  id="minPay" value=""/>
							</div>
			  			</div>
			  		</div>
			  		
			  		
				    <div class="cb"></div>				    		    
			   <div class="cb" style="height: 10px;"></div>
			  
			  <div class="cb"></div>
			</form>
			</div>
		</div>		
	</div>
</div>
<div><c:import url="footer.jsp" /></div>
<div id="searchEmployeeDialog" title="Search Employee" class="ui-front">
	<div id="searchArea" style="display:none" style="z-index: 3000;">
		<div class="searchTxt">Enter First Name or Last Name or Employee number</div>
		<div class="cb"></div>
		<div>			
			<a href=""></a>
			<input id="searchBox" name="searchBox" style="width: 180px;" type="text" onkeyup="searchEmployee();" size="40" placeholder="Type keyword here" />
			<div id="searchButton2" style="padding:12px 10px 12px 10px; cursor: pointer;" onClick="searchEmployee();">SEARCH</div>			
		</div>		 
	</div>
	<div class="cb"></div>
	<div>
		<div id="searchHolderId" style="margin-bottom: 50px;"></div>	
	</div>
</div>
</body>
</html>