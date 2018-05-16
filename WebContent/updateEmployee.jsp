<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Update Employee</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/styleTables.css" />
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link rel="stylesheet" href="css/datePickerStyle.css">

<!-- Tabs -->
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />

<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui-1.11.4/jquery-ui.js"></script>
<script src="js/common.js"></script>
<%@include file="commonJtables.jsp" %>
<script type="text/javascript" src="js/moment.min.js"></script>
<link href="js/validationEngine/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/validationEngine/jquery.validationEngine.js"></script>
<script type="text/javascript" src="js/validationEngine/jquery.validationEngine-en.js"></script>

<script src="js/employee.js"></script>



<style>
input,select { 
	border: 1px solid #52833b;    
    padding: 6.8px;
    background-color: white;
    margin: 5px 0px 0px 0px;
    font: 12px Arial, Helvetica, sans-serif;
	color: black;
}
</style>



<script>
var isSelectedSV1=false;
var isSelectedSV2=false;
var isSelectedSV3=false;

	$(document).ready(function() {
		var empId = document.getElementById("empId").value;		
		initDropDown();
		getEmployeeInfo(empId);		
		getEmployeePayrollInfo(empId);		
		renderEmployeeEducation(empId);
		renderFamilyMember(empId);
		// renderWorkHistory2(empId);
		renderTrainings(empId);
		renderCBA(empId);
		
		displayEmployeeSavedSVNames(empId);

		
	    $("#upload-button").click(function() {
	        var formData = new FormData($("form#uploadFileForm")[0]);
	        $.ajax({
	            url: '/hris/UploadEmployeeImageFileAction?empId='+empId,
	            type: 'POST',
	            data: formData,
	            async: false,
	            success: function(data) {
	                alert('Image uploaded.\nPlease Save Personal Info to confirm profile picture change.');
	                console.log(data.name);
	                $('#picLoc').attr('value', 'empImages/'+data.name);	      
					$('#empImgHolder').attr('src', 'empImages/'+data.name+'?'+Math.random()); //need to add to refresh image
					//use prop rather than attr					          
	                
	            },
	    		error : function(data) {
	    			alert('The image upload failed. Specify a valid image file and try again. ');
	    		},
	            cache: false,
	            contentType: false,
	            processData: false
	        });

	        return false;
	    });
	    
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
		
		$('#serviceRecordTable').jtable({
			title : 'Service Record',			
			actions : {
				createAction : '/hris/SaveServiceRecordAction',
				listAction : '/hris/GetServiceRecordAction?empId=' + empId
			},
			formCreated : function(event, data) {
				
				$(data.form).addClass("custom_horizontal_form_field");
	        	
	        	var $dialogDiv = data.form.closest('.ui-dialog');
	            $dialogDiv.position({
	                my: "center",
	                at: "center",
	                of: $("#serviceRecordTable")
	            });
				
				$(data.form).parent().parent().css("top", 200);
				
				data.form.find('input').css('width','200px');
				data.form.find('select').css('width','200px');
 	            data.form.find('select').css('padding','7.5px');
 	            
 	           var $dtfrom1 = data.form.find ('input[name="dateFrom"]');     
               var $dtTo1 = data.form.find ('input[name="dateTo"]');
               
               if (data.formType == 'edit'){
					
					$dtfrom1.datepicker({
	            		changeMonth : true,
	            		changeYear : true,
	            		dateFormat : 'yy-mm-dd',
	            		yearRange : '1910:2100',
	            	    beforeShow: function(input, inst){	            	    	
	            	           $(".ui-datepicker").css('font-size', 11);
	            	    }
	            	}).val(moment(data.record.dateFrom).format('YYYY-MM-DD'));		

	                $dtTo1.datepicker({
	            		changeMonth : true,
	            		changeYear : true,
	            		dateFormat : 'yy-mm-dd',
	            		yearRange : '1910:2100',
	            	    beforeShow: function(input, inst){	            	    	
	            	           $(".ui-datepicker").css('font-size', 11);
	            	    }
	            	}).val(moment(data.record.dateTo).format('YYYY-MM-DD'));
					
					
				}
				
							
				$dtfrom1.datepicker({
	        		changeMonth : true,
	        		changeYear : true,
	        		dateFormat : 'yy-mm-dd',
	        		yearRange : '1910:2100',
	        	    beforeShow: function(input, inst){	            	    	
	     	           $(".ui-datepicker").css('font-size', 11);
	     	    }
	        	});		
	
	            $dtTo1.datepicker({
	        		changeMonth : true,
	        		changeYear : true,
	        		dateFormat : 'yy-mm-dd',
	        		yearRange : '1910:2100',
	        	    beforeShow: function(input, inst){	            	    	
	     	           $(".ui-datepicker").css('font-size', 11);
	     	    }
	        	});	
				
				data.form.validationEngine();

			},
			formSubmitting: function (event, data) {
				var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
				
				data.form.find('input[name="dateFrom"]').addClass('validate[required]');
                data.form.find('input[name="dateTo"]').addClass('validate[required]');
                data.form.find('input[name="salary"]').addClass('validate[required]');
                data.form.find('input[name="causeRemarks"]').addClass('validate[required]');
	        	
				//alert(data.form.find('input[name="sectionId"]').text());
				
	        	if(rx.test(data.form.find('input[name="salary"]').val())){
	        		if(data.form.find('input[name="salary"]').val() == "0"){
						alert("Salary should be numberic and should be greater than 0.");
						return false;
					}
	        	} else {
	        		alert("Salary should only be numeric and greater than 0.");		
	        		return false;
	        	}	        	
	        	
	            return data.form.validationEngine('validate');
	        },
	        formClosed: function (event, data) {
	            data.form.validationEngine('hide');
	            data.form.validationEngine('detach');
	        },
	        
			fields : {
				serviceRecordId : {
					key : true,
					list : false
				},
				empId : {
					create : true,
					list : false,
					type : 'hidden',
					defaultValue : empId
				},
				jobTitleId : {
					title : 'Designation',
					edit: true,
                    list: true,
                    create:true,
                    options:  
	                   	function(data) {
							if (data.source == 'list') {
			                	return '/hris/GetAllJobAction?forListJTable=true&jobTitleId='+data.record.jobTitleId;
							}
			                if (data.source == 'create') {
			                	return '/hris/GetAllJobAction?displayOption=true&forEdit=false';
			                }
			                if (data.source == 'edit') {
			                	return '/hris/GetAllJobAction?displayOption=true&forEdit=true&jobTitleId='+data.record.jobTitleId;
							}
                    	}
				},
				dateFrom : {
					title : 'Date From',
					edit : true
				},
				dateTo : {
					title : 'Date To',
					edit : true
				},
				salary : {
					title : 'Salary',
					edit : true
				},
				status : {
					title : 'Status',
					edit : true,
					list: false
				},
				placeOfAssignment : {
					title : 'Place of Assignment',
					edit : true
				},
				branch : {
					title : 'Branch',
					edit : true
				},
				wop : {
					title : 'WOP',
					edit : true,
					list: false
				},				
				causeRemarks : {
					title : 'Cause Remarks',
					edit : true
				}

			}
		});
		$('#serviceRecordTable').jtable('load');
		
		
		$('#workHistoryTable').jtable({
			title : 'Work History',
			//paging: true, //Enable paging (need to change action class to impl query change or sublist)
			//pageSize: 3,
			actions : {
				createAction : '/hris/SaveEmployeeWorkHistoryAction',
				listAction : '/hris/GetEmployeeWorkHistoryAction?empId=' + empId
			},
			formCreated : function(event, data) {
				
				$(data.form).addClass("custom_horizontal_form_field");
	        	
	        	var $dialogDiv = data.form.closest('.ui-dialog');
	            $dialogDiv.position({
	                my: "center",
	                at: "center",
	                of: $("#workHistoryTable")
	            });
				
				$(data.form).parent().parent().css("top", 200);
				
				data.form.find('input').css('width','200px');
				
				data.form.validationEngine();

			},
			formSubmitting: function (event, data) {
				var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
				var digitsOnly = new RegExp(/^[0-9]+$/);
	        	
				//alert(data.form.find('input[name="sectionId"]').text());
				
	        	if(rx.test(data.form.find('input[name="salary"]').val())){
	        		if(data.form.find('input[name="salary"]').val() == "0"){
						alert("Salary should be numberic and should be greater than 0.");
						return false;
					}
	        	} else {
	        		alert("Salary should only be numeric and greater than 0.");		
	        		return false;
	        	}
	        	
	        	if(digitsOnly.test(data.form.find('input[name="yrsService"]').val())){
	        		if(data.form.find('input[name="yrsService"]').val() == "0"){
						alert("Years of Service should be numberic and should be greater than 0.");
						return false;
					}
	        	} else {
	        		alert("Years of Service should only be numeric and greater than 0.");
	        		return false;
	        	}
	        	
	            return data.form.validationEngine('validate');
	        },
	        formClosed: function (event, data) {
	            data.form.validationEngine('hide');
	            data.form.validationEngine('detach');
	        },
	        
			fields : {
				empWorkHistoryId : {
					key : true,
					list : false
				},
				empId : {
					create : true,
					list : false,
					type : 'hidden',
					defaultValue : empId
				},
				yrsService : {
					title : 'Years of Service',
					edit : true
				},
				employerName : {
					title : 'Employer Name',
					edit : true
				},

				industry : {
					title : 'Industry',
					edit : true
				},
				address : {
					title : 'Address',
					edit : true
				},
				position : {
					title : 'Position',
					edit : true
				},
				remarks : {
					title : 'Remarks',
					edit : true
				},
				salary : {
					title : 'Salary',
					edit : true
				},
				salaryGrade : {
					title : 'Salary Grade',
					edit : true
				},
				stepIncrement : {
					title : 'Step Increment',
					edit : true
				}

			}
		});
		$('#workHistoryTable').jtable('load');
		
		
		//for payrollinfo deductions section start		
		 $('#containerEmployeeIncomeDeduction').jtable({
			formCreated: function (event, data) {
				
				$(data.form).addClass("custom_horizontal_form_field");
	        	
	        	var $dialogDiv = data.form.closest('.ui-dialog');
	            $dialogDiv.position({
	                my: "center",
	                at: "center",
	                of: $("#containerEmployeeIncomeDeduction")
	            });
	            
	            $(data.form).parent().parent().css("top", 155);
	            
	            data.form.find('select').css('width','200px');
				data.form.find('input').css('width','200px');
				data.form.find('textarea').css('width','200px');
				
				alert('Do not add standard govt deductions here (GSIS, PHIC, HDMF).');
			},
            title: 'Deductions',
            actions: {
                listAction:		'/hris/GetAllEmployeeDeductionAction?empId='+empId
                //	createAction:	'/hris/SaveEmployeeDeductionAction?empId='+empId,
                //updateAction:	'/hris/UpdateEmployeeDeductionAction'
                
            },
            fields: {
            	deductionId: {
                    key: true,
                    list: false
                },
                
            	empId: {
                    key: true,
                    list: false
                },
                deductionName: {
                    title: 'Deduction Name',
                    edit : true 
                },
                amount: {
                    title: 'Amount',
                    edit : true 
                },
                deductionType: {
                    title: 'Deduction Type',                    
                    edit : true,
                    list: true,
                    options: [{ Value: '', DisplayText: 'Select' }, { Value: 'R', DisplayText: 'Recurring' }, { Value: 'O', DisplayText: 'One Time' }]
                },
                
                payrollCycle: {
            		title: 'Payroll Cycle',            		
            		edit: true,
                    list: true,
                    options: [{ Value: '', DisplayText: 'Select' }, { Value: '1', DisplayText: '1st Half' }, { Value: '2', DisplayText: '2nd Half' }, { Value: 'B', DisplayText: 'Both' }]
                }, 
                
            	active: {
            		title: 'Active',
            		create: true,
            		edit: true,
                    list: true,
                    options: [{ Value: 'Y', DisplayText: 'Yes' }, { Value: 'N', DisplayText: 'No' }]
                }
                
                
                             
            }
        });
        $('#containerEmployeeIncomeDeduction').jtable('load');
        
        //for payrollinfo deductions section end
        
        //for payrollinfo income section start
        
		 $('#containerEmployeeIncomeDeduction2').jtable({
			 
			 formCreated: function (event, data) {
					
					$(data.form).addClass("custom_horizontal_form_field");
		        	
		        	var $dialogDiv = data.form.closest('.ui-dialog');
		            $dialogDiv.position({
		                my: "center",
		                at: "center",
		                of: $("#containerEmployeeIncomeDeduction2")
		            });
		            
		            $(data.form).parent().parent().css("top", 155);
		            
		            data.form.find('select').css('width','200px');
					data.form.find('input').css('width','200px');
					data.form.find('textarea').css('width','200px');
					
				},
			 
			 
	            title: 'Income',
	            actions: {
	                listAction:		'/hris/GetAllEmployeeIncomeAction?empId='+empId
	                //createAction:	'/hris/SaveEmployeeIncomeAction?empId='+empId,
	                //updateAction:	'/hris/UpdateEmployeeIncomeAction'
	                
	            },
	            fields: {
	            	incomeId: {
	                    key: true,
	                    list: false
	                },
	                
	            	empId: {
	                    key: true,
	                    list: false
	                },
	                incomeName: {
	                    title: 'Income Name',
	                    edit : true 
	                },
	                amount: {
	                    title: 'Amount',
	                    edit : true 
	                },
	                incomeType: {
	                    title: 'Income Type',                    
	                    edit : true,
	                    list: true,
	                    options: [{ Value: '', DisplayText: 'Select' }, { Value: 'R', DisplayText: 'Recurring' }, { Value: 'O', DisplayText: 'One Time' }]
	                },
	                isTaxable: {
	            		title: 'Taxable',            		
	            		edit: true,
	                    list: true,
	                    options: [{ Value: 'Y', DisplayText: 'Yes' }, { Value: 'N', DisplayText: 'No' }]
	                },
	                payrollCycle: {
	            		title: 'Payroll Cycle',            		
	            		edit: true,
	                    list: true,
	                    options: [{ Value: '', DisplayText: 'Select' }, { Value: '1', DisplayText: '1st Half' }, { Value: '2', DisplayText: '2nd Half' }, { Value: 'B', DisplayText: 'Both' }]
	                }, 	                
	            	active: {
	            		title: 'Active',
	            		create: true,
	            		edit: true,
	                    list: true,
	                    options: [{ Value: 'Y', DisplayText: 'Yes' }, { Value: 'N', DisplayText: 'No' }]
	                }        
	            }
	        });
	        $('#containerEmployeeIncomeDeduction2').jtable('load');
		
		//for payrollinfo income section end
		

		
	});	
	
	function initDropDown() {		
		populateCityDropDown();
		populateJobTitleDropDown();
		populateCountryDropDown();
		//populateDepartmentDropDown();
		//populateDivisionDropDown();
		populateEmployeeTypeDropDown();
		populateProvinceDropDown();		
		populateSectionDropDown();
		populateUnitDropDown();
		populateSubUnitDropDown();
		populatePersonnnelType();
	}
	
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
	
	function renderWorkHistory2(empId) {
		
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

<script type="text/javascript">
$(document).ready(function(){
	$(document).on('change', '#file', function(){
		var name = document.getElementById("file").files[0].name;
		var form_data = new FormData();
		var ext = name.split('.').pop().toLowerCase();
		if(jQuery.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
			alert("Invalid Image File");
		}
		var oFReader = new FileReader();
		oFReader.readAsDataURL(document.getElementById("file").files[0]);
		var f = document.getElementById("file").files[0];
		var fsize = f.size||f.fileSize;
		if(fsize > 2000000) {
			alert("Image File Size is very big");
		} else {
			form_data.append("file", document.getElementById('file').files[0]);
			$.ajax({
				url:"upload.php",
				method:"POST",
				data: form_data,
				contentType: false,
				cache: false,
				processData: false,
				beforeSend:function(){
					$('#uploaded_image').html("<label class='text-success'>Image Uploading...</label>");
				},   
				success:function(data) {
					$('#uploaded_image').html(data);
				}
			});
		}
	});
});
</script>
</head>
<body>
<div><c:import url="header.jsp" /></div>
<div>	
	<input type="hidden" name="empIdLoggedIn" id="empIdLoggedIn" value="${employeeLoggedIn.empId}" />
	<input type="hidden" name="sectionIdLoggedIn" id="sectionIdLoggedIn" value="${employeeLoggedIn.sectionId}" />	
	<div id="content" style="width: 1500px;">
		<!-- Left Side of Dashboard -->
		<div style="width: 400px; float: left; border: 0px solid black">
			
			<!-- Employee Information -->
			<div style="width: 100%">
				<div style="width: 45%; float:left; border: 0px solid black; margin-left: 20px;">
				   <div><img id="empImgHolder" src="images/noimage_2.gif" alt="no image" width="100%" height="auto" /></div>
				</div>
				
				<div style="width: 40%; float:left; border: 0px solid black; margin-left: 10px;">
				   <div class="welcomeHeaderDashboardTable">WELCOME Back!</div>
					<div class="nameDashBoard" id="employeeName"></div>
					<div class="jobTitle" id="jobTitleNameDashboard"></div>
					<div class="departmentDashboard" id="departmentNameDashEmployee"></div>				
					<div class="dashboardTxt">&nbsp;&nbsp;&nbsp;&nbsp;</div>
					<div class="dashboardTxtBig">&nbsp;</div>				
					<div class="dashboardTxt">&nbsp;&nbsp;&nbsp;&nbsp;</div>
					<div class="dashboardTxtBig">&nbsp;</div>
				</div>
				<!-- Picture -->
				
				<!-- Employee Info -->
				
				<div class="cb"></div>
				<div id="dashBoardButtonUploadGroup">
					<form method="POST" id="uploadFileForm" name="uploadFileForm" enctype="multipart/form-data">
						<input type="file" id="upload-file" name="uploadImage" accept="image/*"/>
						<input type="button" id="upload-button" value="Update Image" />
					</form>
				</div>
				
				<div class="dashBoardButton"><a href="employeeLeaveEntry.jsp?empId=${param.empId}">Request Time Off</a></div>
				<div class="dashBoardButton"><a href="empSchedChangeRequest.jsp?empId=${param.empId}">Request Change Shift</a></div>
				<div class="dashBoardButton"><a href="employeeOvertimeEntry.jsp?empId=${param.empId}">File Overtime</a></div>
				<div class="dashBoardButton"><a href="employeeUndertimeEntry.jsp?empId=${param.empId}">File Undertime</a></div>
				<div class="dashBoardButton"><a href="employeeOffSetEntry.jsp?empId=${param.empId}">File OffSet</a></div>				
				<div class="dashBoardButton"><a href="employeeOutOfOfficeEntry.jsp?empId=${param.empId}">File Training &amp; Seminar</a></div>
				<div class="dashBoardButton"><a href="hourlyAttendance.jsp">File Hourly Attendance</a></div>			
				
			</div>
		</div>
		<!-- Right Side of Dashboard -->
		<div style="width: 1050px;  float: left; border: 0px solid black">
			<div id="tabs-nohdr">
			  <ul>
			    <li><a href="#tabs-nohdr-1">Personal Info</a></li>
			    <li class="hiddenField"><a href="#tabs-nohdr-2">Educ Info</a></li>
			    <li class="hiddenField"><a href="#tabs-nohdr-3">Family Info</a></li>
			    <li class="hiddenField"><a href="#tabs-nohdr-4">Payroll Info</a></li>
			    <li class="hiddenField"><a href="#tabs-nohdr-5">Other Income/Deduction</a></li>
			    <li class="hiddenField"><a href="#tabs-nohdr-6">Work History</a></li>
			    <li class="hiddenField"><a href="#tabs-nohdr-7">Trainings/Seminars</a></li>			    
			    <li class="hiddenField"><a href="#tabs-nohdr-8">Competency Assessment</a></li>			  	
			  	<li class="hiddenField"><a href="#tabs-nohdr-9">Service Record</a></li>
			  </ul>
			  <div id="tabs-nohdr-1">			    
			    <form method="POST" id="addEmployeeForm" name="addEmployeeForm" action="AddEmployeeAction">
			    	<input type="hidden" name="empId" id="empId" value="${param.empId}" />		
			    	<input type="hidden" name="picLoc" id="picLoc" value="NONE" />					
				    
				    
				    
				    
				    <div class="floatNext">
				    	<div class="dataEntryText">Employee No</div>
				    	<div class="dataEntryInput"><input type="text" name="empNo" id="empNo" style="width: 250px;" value="${employeeObject.empNo}" placeholder="Employee Number" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Plantilla No</div>
				    	<div class="dataEntryInput"><input type="text" name="plantillaNo" id="plantillaNo" style="width: 250px;" value="${employeeObject.plantillaNo}" placeholder="Plantilla Number" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Firstname</div>			    
				    	<div class="dataEntryInput"><input type="text" name="firstname" id="firstname" style="width: 250px;" value="${employeeObject.firstname}" placeholder="Firstname" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Lastname</div>
				    	<div class="dataEntryInput"><input type="text" name="lastname" id="lastname" style="width: 250px;" value="${employeeObject.lastname}" placeholder="Lastname" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Middlename</div>
				    	<div class="dataEntryInput"><input type="text" name="middlename" id="middlename" style="width: 250px;" value="${employeeObject.middlename}" placeholder="Middlename" /></div>
				    </div>				    
				    <div class="floatNext">
				    	<div class="dataEntryText">Username</div>
				    	<div class="dataEntryInput"><input type="text" name="username" id="username" style="width: 250px;" value="${employeeObject.username}" placeholder="Username" /></div>
				    </div>				    
				    <div class="floatNext">
				    	<div class="dataEntryText">Birthdate</div>
				    	<div class="dataEntryInput"><input type="text" name="dateOfBirth" id="dateOfBirth" style="width: 250px;" value="${employeeObject.dateOfBirth}" class="useDPicker" placeholder="Birthdate" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Email</div>
				    	<div class="dataEntryInput"><input type="text" name="email" id="email" style="width: 250px;" value="${employeeObject.email}" placeholder="Email" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Landline</div>
				    	<div class="dataEntryInput"><input type="text" name="telNo" id="telNo" style="width: 250px;" value="${employeeObject.telNo}" placeholder="Landline" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Mobile No.</div>
				    	<div class="dataEntryInput"><input type="text" name="mobileNo" id="mobileNo" style="width: 250px;" value="${employeeObject.mobileNo}" placeholder="Mobile Number" /></div>
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
				    	<div class="dataEntryInput"><input type="text" name="birthPlace" id="birthPlace" style="width: 250px;"  value="${employeeObject.birthPlace}" placeholder="Place of Birth" /></div>
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
				    	<div class="dataEntryInput"><input type="text" id="nationality" name="nationality" style="width: 250px;" value="${employeeObject.nationality}" placeholder="Nationality" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">Street</div>
				    	<div class="dataEntryInput"><input type="text" id="street" name="street" style="width: 250px;" value="${employeeObject.street}" placeholder="Street" /></div>
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
				    	<div class="dataEntryInput"><input type="text" name="zipCode" id="zipCode" style="width: 250px;" value="${employeeObject.zipCode}" placeholder="Zipcode" /></div>
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
					    	<input type="text" name="employmentDate" id="employmentDate" style="width: 250px;" value="${employeeObject.employmentDate}" class="useDPicker" placeholder="Date Employed" />			    	
					    </div>	
				    </div>
				    <div class="floatNext">
					    <div class="dataEntryText">End of Contract</div>
					    <div class="dataEntryInput">
					    	<input type="text" name="endOfContract" id="endOfContract" style="width: 250px;" value="${employeeObject.endOfContract}" class="useDPicker" placeholder="End of Contract" />	    	
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
				    	<div class="dataEntryInput"><input type="text" name="gsis" id="gsis" style="width: 250px;" value="${employeeObject.gsis}" placeholder="GSIS" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">CRN No.</div>
				    	<div class="dataEntryInput"><input type="text" name="crn" id="crn" style="width: 250px;" value="${param.crn}" placeholder="CRN No." /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">SSS</div>
				    	<div class="dataEntryInput"><input type="text" name="sss" id="sss" style="width: 250px;" value="${employeeObject.sss}" placeholder="SSS" /></div>
				    </div>
				    <div class="floatNext">				    
				    	<div class="dataEntryText">TIN</div>
				    	<div class="dataEntryInput"><input type="text" name="tin" id="tin" style="width: 250px;" value="${employeeObject.tin}" placeholder="TIN" /></div>
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
				    	<div class="dataEntryInput"><input type="text" name="phic" id="phic" style="width: 250px;" value="${employeeObject.phic}" placeholder="PHILHEALTH" /></div>
				    </div>
				    <div class="floatNext">
				    	<div class="dataEntryText">PAGIBIG</div>
				    	<div class="dataEntryInput"><input type="text" name="hdmf" id="hdmf" style="width: 250px;" value="${employeeObject.hdmf}" placeholder="PAGIBIG" /></div>
				    </div>				    
				</form>
			    <div class="cb" style="height: 10px;"></div>
			    <div class="employeeButton" onClick="updateEmployee();">Save</div>
			    <div class="employeeButton">Clear</div>
			    <div class="cb"></div>
			    
			  </div>
			  <div id="tabs-nohdr-2" class="hiddenField">			    
			    <div id="educTable"></div>				  
			  </div>
			  <div id="tabs-nohdr-3" class="hiddenField">			    
			    <div id="familyMemberTable"></div>			    
			  </div>
			  <div id="tabs-nohdr-4" class="hiddenField">
			  	<form method="POST" id="savePayrollInfoForm" name="savePayrollInfoForm" action="SaveEmployeePayrollInfoAction">
				  	<input type="hidden" name="empId" id="empId" value="${param.empId}" />	
				    <div class="dataEntryText">Monthly Rate</div>
				    <div class="dataEntryInput"><input type="text" id="monthlyRate" name="monthlyRate" onkeyup="computeAmounts();" value="${param.monthlyRate}" placeholder="Monthly Rate" /></div>
				    <div class="dataEntryText">Daily Rate</div>
				    <div class="dataEntryInput"><input type="text" id="dailyRate" name="dailyRate" readonly="readonly"  value="${param.dailyRate}" placeholder="Daily Rate" /></div>
				    <div class="cb"></div>
				    <div class="dataEntryText">Hourly Rate</div>
				    <div class="dataEntryInput"><input type="text" id="hourlyRate" name="hourlyRate" readonly="readonly" value="${param.hourlyRate}" placeholder="Hourly Rate" /></div>
				    <div class="dataEntryText">Food Allowance</div>
				    <div class="dataEntryInput"><input type="text" id="foodAllowance" name="foodAllowance"  value="${param.foodAllowance}" placeholder="Food Allowance" /></div>			    
				    <div class="cb"></div>
				    <div class="dataEntryText">Cola</div>
				    <div class="dataEntryInput"><input type="text" id="cola" name="cola"  value="${param.cola}" placeholder="Cola" /></div>
				    <div class="dataEntryText">PERA</div>
				    <div class="dataEntryInput"><input type="text" id="taxShield" name="taxShield"  value="${param.taxShield}" placeholder="PERA" /></div>
				    <div class="cb"></div>
				    <div class="dataEntryText">Transportation Allow.</div>
				    <div class="dataEntryInput"><input type="text" id="transAllowance" name="transAllowance"  value="${param.transAllowance}" placeholder="Transportation Allowance" /></div>
				    <div class="dataEntryText">Payroll Type</div>
				    <div class="dataEntryInput">			    	
				    	<select name="payrollType" id="payrollType" style="width:214px; height:41px; background:#fff; border: 1px solid #52833b; margin-top: 5px; color: black;" >
								<option selected="selected" value="n">Select Payroll Type</option>									
								<option value="M">Monthly</option>
								<option value="S">Semi-Monthly</option>
						</select>
				    </div>
				    <div class="cb"></div>
				    <div class="dataEntryText">Has Holiday Pay</div>
				    <div class="dataEntryInput">
				    	<select name="hasHolidayPay" id="hasHolidayPay" style="width:214px; height:41px; background:#fff; border: 1px solid #52833b; margin-top: 5px; color: black;" >
							<option value="">Select</option>									
							<option value="Y">YES</option>
							<option value="N">NO</option>
						</select>
				    </div>
				    <div class="dataEntryText">Has Night Diff Pay</div>
				    <div class="dataEntryInput">			    	
				    	<select name="hasNightDifferential" id="hasNightDifferential" style="width:214px; height:41px; background:#fff; border: 1px solid #52833b; margin-top: 5px; color: black;" >
							<option value="">Select</option>									
							<option value="Y">YES</option>
							<option value="N">NO</option>
						</select>
				    </div>
				    <div class="cb"></div>
				    <div class="dataEntryText">Pagibig Employee Share</div>
				    <div class="dataEntryInput"><input type="text" id="pagibigEmployeeShare" name="pagibigEmployeeShare" value="${param.pagibigEmployeeShare}" placeholder="Pagibig Employee Share" /></div>				
				    <div class="dataEntryText">Pagibig Employer Share</div>
				    <div class="dataEntryInput"><input type="text" id="pagibigEmployerShare" name="pagibigEmployerShare" value="${param.pagibigEmployerShare}" placeholder="Pagibig Employer Share" /></div>
				    <div class="cb"></div>
				    <div class="dataEntryText">GSIS Employee Share</div>
				    <div class="dataEntryInput"><input type="text" id="gsisEmployeeShare" name="gsisEmployeeShare" value="${param.gsisEmployeeShare}" placeholder="GSIS Employee Share" /></div>				    
				    <div class="dataEntryText">GSIS Employer Share</div>
				    <div class="dataEntryInput"><input type="text" id="gsisEmployerShare" name="gsisEmployerShare" value="${param.pagibigEmployerShare}" placeholder="GSIS Employer Share" /></div>
				    
				    
					<div class="cb" style="height: 10px;"></div>
				    <div class="dataEntryText">Bank Information</div>
				    <div class="cb"></div>	
				    <div class="dataEntryText">Bank Name</div>
				    <div class="dataEntryInput"><input type="text" id="bankNameBan" name="bankNameBan"  value="${param.bankNameBan}" placeholder="Bank Name" /></div>	     
				    <div class="dataEntryText">Bank Account #</div>
				    <div class="dataEntryInput"><input type="text" id="ban" name="ban"  value="${param.ban}" placeholder="Bank Account #" /></div>
					<div class="cb" style="height: 10px;"></div>				    
				    <div class="cb" style="height: 60px;"></div>
				    <div class="employeeButton" onClick="saveEmployeePayrollInfo();">Save</div>
				    <div class="employeeButton">Clear</div>
				    <div class="cb"></div>
			    </form>
			  </div>
			  <div id="tabs-nohdr-5" class="hiddenField">
			    <div>
				<div class="cb" style="height: 10px;"></div>
					<div id="containerEmployeeIncomeDeduction" class="jTableContainerDaiExtended"></div>		
				</div>	
				<div id="content-payroll-info">
				<div class="cb" style="height: 20px;"></div>
					<div id="containerEmployeeIncomeDeduction2" class="jTableContainerDaiExtended"></div>		
				</div>			  			    
			  </div>
			  <div id="tabs-nohdr-6" class="hiddenField">
			  	<div id="workHistoryTable"></div>			    
			  </div>
			  <div id="tabs-nohdr-7" class="hiddenField">
			    <div id="trainingTable"></div>			    
			  </div>
			  <div id="tabs-nohdr-8" class="hiddenField">			    
 				<div id="containerCBAEntry" class="jTableContainerDaiExtended"></div>		
			  </div>
			  <div id="tabs-nohdr-9" class="hiddenField">
			  	<div id="serviceRecordTable"></div>			    
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
