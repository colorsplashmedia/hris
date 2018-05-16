<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Add Employee</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/styleTables.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link rel="stylesheet" href="css/datePickerStyle.css">
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />

<!-- Tabs -->
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />
<script type="text/javascript" src="js/jquery-ui-1.10.0.min.js"></script>
<script src="js/jquery-1.10.2.js"></script>
<script src="js/common.js"></script>
<script src="js/employee.js"></script>
<script src="js/jquery-ui-1.11.4/jquery-ui.js"></script>

<style>
input,select { 
	border: 1px solid #52833b;    
    padding: 6.5px;
    background-color: white;
    margin: 5px 0px 0px 0px;
    font: 12px Arial, Helvetica, sans-serif;
}
</style>


<script>
var isSelectedSV1=false;
var isSelectedSV2=false;
var isSelectedSV3=false;

	$(document).ready(function() {	
		populateCityDropDown();
		populateJobTitleDropDown();
		populateCountryDropDown();
		//populateDepartmentDropDown();
		//populateDivisionDropDown();
		populatePersonnnelType();
		
		populateEmployeeTypeDropDown();
		populateProvinceDropDown();
		
		populateSectionDropDown();
		populateUnitDropDown();
		populateSubUnitDropDown();
		
		/*
	    $("#upload-button").click(function() {
	        var formData = new FormData($("form#uploadFileForm")[0]);

	        $.ajax({
	            url: '/hris/UploadEmployeeImageFileAction',
	            type: 'POST',
	            data: formData,
	            async: false,
	            success: function(data) {
	                alert('The image upload is successful! ');
	                $('#picLoc').prop('value', 'hrisImages/'+data.name);	      
					$('#empImgHolder').prop('src', 'hrisImages/'+data.name);
					//use prop rather than attr
					          
	                
	            },
	    		error : function(data) {
	    			alert('The image upload failed! ');
	    		},
	            cache: false,
	            contentType: false,
	            processData: false
	        });

	        return false;
	    });
		*/
	    
	    
	    
	    
		//$("#select-sv-button-1").click(function() {
		$("input[id^=select-sv-button").click(function() {
			if ($(this).prop("id") == "select-sv-button-1") {
				isSelectedSV1 = true;
			}
			if ($(this).prop("id") == "select-sv-button-2") {
				isSelectedSV2 = true;
			}
			if ($(this).prop("id") == "select-sv-button-3") {
				isSelectedSV3 = true;
			}			
			
			$( "#searchEmployeeDialog" ).dialog({
				show: 'fade',
				hide: 'fade',
				width: 600,
				maxWidth: 600,
				height: 400,
				maxHeight: 400,
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
		
	});	
	
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};
	


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
		$( "#searchEmployeeDialog" ).dialog("close");
}

function computeAmounts() {		
	
	var monthlySal = $("#monthlyRate").val();
	
	var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
	
	if(rx.test(monthlySal)){
		//alert("Pass");
	} else {
		alert("Monthy Salary should be numberic and should be greater than 0.");
		$("#dailyRate").val(0);
		$("#hourlyRate").val(0);
		return;
	}
	
	
	var dailyRate = parseFloat(monthlySal) / 22;
	var hourlyRate = parseFloat(dailyRate) / 8;
	
	$("#dailyRate").val(dailyRate.toFixed(2));
	$("#hourlyRate").val(hourlyRate.toFixed(2));
	
}
	  
</script>

</head>
<body>
<div><c:import url="header.jsp" /></div>
<div>	
	<input type="hidden" name="empIdLoggedIn" id="empIdLoggedIn" value="${employeeLoggedIn.empId}" />
	<input type="hidden" name="sectionIdLoggedIn" id="sectionIdLoggedIn" value="${employeeLoggedIn.sectionId}" />	
	<div id="content">		
		<!-- Right Side of Dashboard -->
		<div id="dashBoardRightPannel" style="margin-left: 50px;">
			<div id="tabs-nohdr">
			  <ul>
			    <li><a href="#tabs-nohdr-1">Personal and Payroll Info</a></li>			    
			  </ul>
			  <div id="tabs-nohdr-1">			    
			    <form method="POST" id="addEmployeeForm" name="addEmployeeForm" action="AddEmployeeAction">
			    	<input type="hidden" name="createdBy" id="createdBy" value="${employeeLoggedIn.empId}" />
			    	<input type="hidden" name="picLoc" id="picLoc" value="NONE" />			
			    	<div class="cb" style="height: 10px;"></div>
				    <div style="color: white; font-size: 16px; width: 100%; background-color: black; padding: 10px 0px 10px 10px;">PERSONAL INFORMATION</div>	
				    <div class="floatNext">
				    	<div class="dataEntryText">Employee No</div>
				    	<div class="dataEntryInput"><input type="text" name="empNo" id="empNo" style="width: 250px;" value="${param.empNo}" placeholder="Employee Number" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Plantilla No</div>
				    	<div class="dataEntryInput"><input type="text" name="plantillaNo" id="plantillaNo" style="width: 250px;" value="${param.plantillaNo}" placeholder="Plantilla Number" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Firstname</div>			    
				    	<div class="dataEntryInput"><input type="text" name="firstname" id="firstname" style="width: 250px;" value="${param.firstname}" placeholder="Firstname" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Lastname</div>
				    	<div class="dataEntryInput"><input type="text" name="lastname" id="lastname" style="width: 250px;" value="${param.lastname}" placeholder="Lastname" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Middlename</div>
				    	<div class="dataEntryInput"><input type="text" name="middlename" id="middlename" style="width: 250px;" value="${param.middlename}" placeholder="Middlename" /></div>
				    </div>				    
				    <div class="floatNext">
				    	<div class="dataEntryText">Username</div>
				    	<div class="dataEntryInput"><input type="text" name="username" id="username" style="width: 250px;" value="${param.username}" placeholder="Username" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Password</div>
				    	<div class="dataEntryInput"><input type="password" name="password" id="password" style="width: 250px;" value="${param.password}" placeholder="Password" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Birthdate</div>
				    	<div class="dataEntryInput"><input type="text" name="dateOfBirth" id="dateOfBirth" style="width: 250px;" value="${param.dateOfBirth}" class="useDPicker" placeholder="Birthdate" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Email</div>
				    	<div class="dataEntryInput"><input type="text" name="email" id="email" style="width: 250px;" value="${param.email}" placeholder="Email" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Landline</div>
				    	<div class="dataEntryInput"><input type="text" name="telNo" id="telNo" style="width: 250px;" value="${param.telNo}" placeholder="Landline" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Mobile No.</div>
				    	<div class="dataEntryInput"><input type="text" name="mobileNo" id="mobileNo" style="width: 250px;" value="${param.mobileNo}" placeholder="Mobile Number" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Gender</div>
					    <div class="dataEntryInput">			    	
					    	<select name="gender" id="gender" style="width: 250px;" >
								<option selected="selected" value="n">Select Gender</option>									
								<option value="M">Male</option>
								<option value="F">Female</option>
							</select>
					    </div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Place of Birth</div>
				    	<div class="dataEntryInput"><input type="text" name="birthPlace" id="birthPlace" style="width: 250px;"  value="${param.birthPlace}" placeholder="Place of Birth" /></div>
				    </div>
				    <div class="floatNext">
					    <div class="dataEntryText">Civil Status</div>
					    <div class="dataEntryInput">			    	
					    	<select name="civilStatus" id="civilStatus" style="width: 250px;" >
								<option selected="selected" value="n">Select Status</option>									
								<option value="S">Single</option>
								<option value="M">Married</option>
								<option value="D">Divorced</option>
								<option value="W">Widowed</option>
							</select>
					    </div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Nationality</div>
				    	<div class="dataEntryInput"><input type="text" id="nationality" name="nationality" style="width: 250px;" value="${param.nationality}" placeholder="Nationality" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Street</div>
				    	<div class="dataEntryInput"><input type="text" id="street" name="street" style="width: 250px;" value="${param.street}" placeholder="Street" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">City</div>
					    <div class="dataEntryInput">
					    	<select name="cityId" id="cityDropDownID" style="width: 250px;" >			    													
								
								
							</select>			    		    	
					    </div>
				    </div>
				    <div class="floatNext">
					    <div class="dataEntryText">Province</div>
					    <div class="dataEntryInput">
					    	<select name="provinceId" id="provinceDropDownID" style="width: 250px;" >			    													
								
							</select>			    	
					    </div>
				    </div>
				    <div class="floatNext">
					    <div class="dataEntryText">Country</div>
					    <div class="dataEntryInput">
					    	<select name="countryId" id="countryDropDownID" style="width: 250px;" >			    													
								
							</select>			    	
					    </div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Zip Code</div>
				    	<div class="dataEntryInput"><input type="text" name="zipCode" id="zipCode" style="width: 250px;" value="${param.zipCode}" placeholder="Zipcode" /></div>
				    </div>
				    
				    <div class="floatNext">
				    	<div class="dataEntryText">Job Title</div>
					    <div class="dataEntryInput">
					    	<select name="jobTitleId" id="jobTitleDropDownID" style="width: 250px;" >			    													
								
							</select>			    	
					    </div>	
				    </div>
				    <div class="floatNext">
					    <div class="dataEntryText">Employee Status</div>
					    <div class="dataEntryInput">
					    	<select name="empStatus" id="empStatus" style="width: 250px;" >			    													
								<option value="A">ACTIVE</option>						
								<option value="NA">NOT ACTIVE</option>
							</select>			    	
					    </div>
					</div>
				    <!-- 
				    <div class="dataEntryText">Department</div>
				    <div class="dataEntryInput">
				    	<select name="sectionId" id="departmentDropDownID" style="width:200px;" >			    													
							
						</select>			    	
				    </div>	
				    </div><div class="floatNext">
				    <div class="dataEntryText">Division</div>
				    <div class="dataEntryInput">
				    	<select name="divisionId" id="divisionDropDownID" style="width: 250px;" >			    													
							
						</select>			    	
				    </div>
				     -->
				    
				    <div class="floatNext">
					    <div class="dataEntryText">Section</div>
					    <div class="dataEntryInput">
					    	<select name="sectionId" id="sectionDropDownID" style="width: 250px;" >			    													
								
							</select>			    	
					    </div>	
				    </div>
				    <div class="floatNext">
					    <div class="dataEntryText">Unit</div>
					    <div class="dataEntryInput">
					    	<select name="unitId" id="unitDropDownID" style="width: 250px;" >			    													
								
							</select>			    	
					    </div>
				    </div>
				    <div class="floatNext">
					    <div class="dataEntryText">Sub Unit</div>
					    <div class="dataEntryInput">
					    	<select name="subUnitId" id="subUnitDropDownID" style="width: 250px;" >			    													
								
							</select>			    	
					    </div>		
				    </div>
				    <div class="floatNext">
					    <div class="dataEntryText">Date Employed</div>
					    <div class="dataEntryInput">
					    	<input type="text" name="employmentDate" id="employmentDate" style="width: 250px;" value="${param.employmentDate}" class="useDPicker" placeholder="Date Employed" />			    	
					    </div>	
				    </div>
				    <div class="floatNext">
					    <div class="dataEntryText">End of Contract</div>
					    <div class="dataEntryInput">
					    	<input type="text" name="endOfContract" id="endOfContract" style="width: 250px;" value="${param.endOfContract}" class="useDPicker" placeholder="End of Contract" />	    	
					    </div>				    
				    </div>
				    <div class="floatNext">
					    <div class="dataEntryText">Employee Type</div>
					    <div class="dataEntryInput">
					    	<select name="employeeTypeId" id="employeeTypeDropDownID" style="width: 250px;" >			    													
								
							</select>			    	
					    </div>				    
				    </div>
				    <div class="floatNext"> 
					    <div class="dataEntryText">Personnel Type</div>
					    <div class="dataEntryInput">				    	
							<select name="personnelType" id="personnelTypeDropDownID" style="width: 250px;" >		
								
							</select>			    	
					    </div> 
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">GSIS</div>
				    	<div class="dataEntryInput"><input type="text" name="gsis" id="gsis" style="width: 250px;" value="${param.gsis}" placeholder="GSIS" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">CRN No.</div>
				    	<div class="dataEntryInput"><input type="text" name="crn" id="crn" style="width: 250px;" value="${param.crn}" placeholder="CRN No." /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">SSS</div>
				    	<div class="dataEntryInput"><input type="text" name="sss" id="sss" style="width: 250px;" value="${param.sss}" placeholder="SSS" /></div>
				    </div>
				    <div class="floatNext">				    
				    	<div class="dataEntryText">TIN</div>
				    	<div class="dataEntryInput"><input type="text" name="tin" id="tin" style="width: 250px;" value="${param.tin}" placeholder="TIN" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">TAX STATUS</div>
					    <div class="dataEntryInput">
					    	<select name="taxstatus" id="taxstatus" style="width: 250px;" >
					    		<option value="">Select Tax Status</option>															
								<option value="Z">Z</option>						
								<option value="S">S</option>
								<option value="S1">S1</option>
								<option value="S2">S2</option>
								<option value="S3">S3</option>
								<option value="S4">S4</option>
								<option value="ME">ME</option>
								<option value="ME1">ME1</option>
								<option value="ME2">ME2</option>
								<option value="ME3">ME3</option>
								<option value="ME4">ME4</option>
							</select>
					    </div>
				    </div>
				    <div class="floatNext">				    
				    	<div class="dataEntryText">PHILHEALTH</div>
				    	<div class="dataEntryInput"><input type="text" name="phic" id="phic" style="width: 250px;" value="${param.phic}" placeholder="PHILHEALTH" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">PAGIBIG</div>
				    	<div class="dataEntryInput"><input type="text" name="hdmf" id="hdmf" style="width: 250px;" value="${param.hdmf}" placeholder="PAGIBIG" /></div>
				    </div>
				    
				    <!-- Start Payroll Info -->
				    
				    <div class="cb" style="height: 10px;"></div>
				    <div style="color: white; font-size: 16px; width: 100%; background-color: black; padding: 10px 0px 10px 10px;">PAYROLL INFORMATION</div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Monthly Rate</div>
				    	<div class="dataEntryInput"><input type="text" id="monthlyRate" name="monthlyRate" style="width: 250px;" onkeyup="computeAmounts();" value="${param.monthlyRate}" placeholder="Monthly Rate" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Daily Rate</div>
				    	<div class="dataEntryInput"><input type="text" id="dailyRate" name="dailyRate" style="width: 250px;" readonly="readonly"  value="${param.dailyRate}" placeholder="Daily Rate" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Hourly Rate</div>
				    	<div class="dataEntryInput"><input type="text" id="hourlyRate" name="hourlyRate" style="width: 250px;" readonly="readonly" value="${param.hourlyRate}" placeholder="Hourly Rate" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Food Allowance</div>
				    	<div class="dataEntryInput"><input type="text" id="foodAllowance" name="foodAllowance" style="width: 250px;"  value="${param.foodAllowance}" placeholder="Food Allowance" /></div>			    
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Cola</div>
				    	<div class="dataEntryInput"><input type="text" id="cola" name="cola" style="width: 250px;" value="${param.cola}" placeholder="Cola" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">PERA</div>
				    	<div class="dataEntryInput"><input type="text" id="taxShield" name="taxShield" style="width: 250px;" value="${param.taxShield}" placeholder="PERA" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Transportation Allow.</div>
				    	<div class="dataEntryInput"><input type="text" id="transAllowance" name="transAllowance" style="width: 250px;" value="${param.transAllowance}" placeholder="Transportation Allowance" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Payroll Type</div>
					    <div class="dataEntryInput">			    	
					    	<select name="payrollType" id="payrollType" style="width: 250px; height:41px; background:#fff; border: 1px solid #52833b; margin-top: 5px; color: black;" >
									<option selected="selected" value="n">Select Payroll Type</option>									
									<option value="M">Monthly</option>
									<option value="S">Semi-Monthly</option>
							</select>
					    </div>				    
				    </div>
				    <div class="floatNext">
					    <div class="dataEntryText">Has Holiday Pay</div>
					    <div class="dataEntryInput">
					    	<select name="hasHolidayPay" id="hasHolidayPay" style="width:250px; height:41px; background:#fff; border: 1px solid #52833b; margin-top: 5px; color: black;" >
								<option value="">Select</option>									
								<option value="Y">YES</option>
								<option value="N">NO</option>
							</select>
					    </div>
				    </div>
				    <div class="floatNext">
					    <div class="dataEntryText">Has Night Diff Pay</div>
					    <div class="dataEntryInput">			    	
					    	<select name="hasNightDifferential" id="hasNightDifferential" style="width:250px; height:41px; background:#fff; border: 1px solid #52833b; margin-top: 5px; color: black;" >
								<option value="">Select</option>									
								<option value="Y">YES</option>
								<option value="N">NO</option>
							</select>
					    </div>
					</div>
					<div class="floatNext">
				    	<div class="dataEntryText">Pagibig Employee Share</div>
				    	<div class="dataEntryInput"><input type="text" id="pagibigEmployeeShare" name="pagibigEmployeeShare" style="width: 250px;" value="${param.pagibigEmployeeShare}" placeholder="Pagibig Employee Share" /></div>
				    </div>
					<div class="floatNext">
				    	<div class="dataEntryText">Pagibig Employer Share</div>
				    	<div class="dataEntryInput"><input type="text" id="pagibigEmployerShare" name="pagibigEmployerShare" style="width: 250px;" value="${param.pagibigEmployerShare}" placeholder="Pagibig Employer Share" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">GSIS Employee Share</div>
				    	<div class="dataEntryInput"><input type="text" id="gsisEmployeeShare" name="gsisEmployeeShare" style="width: 250px;" value="${param.gsisEmployeeShare}" placeholder="GSIS Employee Share" /></div>
				    </div>
					<div class="floatNext">
				    	<div class="dataEntryText">GSIS Employer Share</div>
				    	<div class="dataEntryInput"><input type="text" id="gsisEmployerShare" name="gsisEmployerShare" style="width: 250px;" value="${param.pagibigEmployerShare}" placeholder="GSIS Employer Share" /></div>
				    </div>
					
					<div class="cb" style="height: 10px;"></div>
				    <div style="color: white; font-size: 16px; width: 100%; background-color: black; padding: 10px 0px 10px 10px;">BANK INFORMATION</div>
				    <div class="floatNext">	
				    	<div class="dataEntryText">Bank Name</div>
				    	<div class="dataEntryInput"><input type="text" id="bankNameBan" name="bankNameBan" style="width: 250px;"  value="${param.bankNameBan}" placeholder="Bank Name" /></div>	     
				    </div>
				    <div class="floatNext">	
				    	<div class="dataEntryText">Bank Account #</div> 
				    	<div class="dataEntryInput"><input type="text" id="ban" name="ban"  style="width: 250px;" value="${param.ban}" placeholder="Bank Account #" /></div>					
				    </div>
				    <div class="cb" style="height: 10px;"></div>
				    <!-- End Payroll Info -->
				    
				    
				    
				    
				    
				    <!-- 
				    <div class="dataEntryText">Supervisor 1</div>
				    <div class="dataEntryInput">
				    	<input type="text" name="superVisor1FullName"  id="superVisor1FullName" value="" disabled/>
				    	<input type="hidden" name="superVisor1Id" id="superVisor1Id" value="0" />			
				 		<input type="button" id="select-sv-button-1" value="Choose..">
				    </div>

					<div class="cb"></div>
				    <div class="dataEntryText">Supervisor 2</div>
				    <div class="dataEntryInput">
				    	<input type="text" name="superVisor2FullName" id="superVisor2FullName"  value="" disabled/>
				    	<input type="hidden" name="superVisor2Id" id="superVisor2Id" value="0" />	
				    	<input type="button" id="select-sv-button-2" value="Choose.."> 	
				    </div>
				    
				    
				    <div class="cb"></div>
				    <div class="dataEntryText">Supervisor 3</div>
				    <div class="dataEntryInput">
				    	<input type="text" name="superVisor3FullName" id="superVisor3FullName"  value="" disabled/>
				    	<input type="hidden" name="superVisor3Id" id="superVisor3Id" value="0" />	
				    	<input type="button" id="select-sv-button-3" value="Choose..">    	
				    </div>
					 -->
					 
					 <div class="cb" style="height: 10px;"></div>
			    <div class="employeeButton" onClick="saveEmployee();">Save</div>
			    <div class="employeeButton" onClick="resetForm();">Clear</div>
			    <div class="cb"></div>
					
				</form>
			    
			    
		    
			  </div>
			  
			  
			</div>
		</div>
		<div class="cb" style="height: 50px;"></div>
	</div>
</div>	

<div style=""><c:import url="footer.jsp" /></div>

<!-- 
<div id="searchEmployeeDialog" title="Search Employee" class="ui-front">
<c:import url="searchEmployee_solr.jsp" />
	<div class="cb"></div>
	<div>
		<div id="searchHolderId" style="margin-bottom: 50px;"></div>	
	</div>
</div>
 -->
</body>
</html>