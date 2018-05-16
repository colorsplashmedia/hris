function renderTrainings(empId) {
	$('#trainingTable').jtable({
		title : 'Training and Seminars',
		actions : {
			createAction : '/hris/SaveEmployeeTrainingAction',
			listAction : '/hris/GetEmployeeTrainingAction?empId=' + empId,
			updateAction: '/hris/UpdateEducationalTrainingAction'
		},
		formCreated : function(event, data) {
			
			$(data.form).addClass("custom_horizontal_form_field");
        	
        	var $dialogDiv = data.form.closest('.ui-dialog');
            $dialogDiv.position({
                my: "center",
                at: "center",
                of: $("#trainingTable")
            });
            
            $(data.form).parent().parent().css('top', 200);
            
            data.form.find('input').css('width','200px');
			
			var $df = data.form.find ('input[name="dateFrom"]');   
        	
            $df.datepicker({
        		changeMonth : true,
        		changeYear : true,
        		dateFormat : 'yy-mm-dd',
        		yearRange : '1910:2100',
        	    beforeShow: function(input, inst){	            	    	
     	           $(".ui-datepicker").css('font-size', 11);
     	    }
        	});
            
            var $dt = data.form.find ('input[name="dateTo"]');   
        	
            $dt.datepicker({
        		changeMonth : true,
        		changeYear : true,
        		dateFormat : 'yy-mm-dd',
        		yearRange : '1910:2100',
        	    beforeShow: function(input, inst){	            	    	
     	           $(".ui-datepicker").css('font-size', 11);
     	    }
        	});
            
            //data.form.find('input[name="dateFrom"]').addClass('validate[required]');
			//data.form.find('input[name="dateTo"]').addClass('validate[required]');
            //data.form.validationEngine();
			
		},
		fields : {
			empOOOId : {
				key : true,
				list : false
			},
			empId : {
				create : true,
				list : false,
				type : 'hidden',
				defaultValue : empId

			},
			dateFrom : {
				title : 'Date From',
				edit : true
			},
			dateTo : {
				title : 'Date To',
				edit : true
			},
			titleActivity : {
				title : 'Title Activity',
				edit : true
			},
			provider : {
				title : 'Provider',
				edit : true
			},
			remarks : {
				title : 'Remarks',
				edit : true
			}

		}
	});
	$('#trainingTable').jtable('load');
}

function renderWorkHistory(empId) {
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
			
			//data.form.validationEngine();

		},
		formSubmitting: function (event, data) {
			var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
			var digitsOnly = new RegExp(/^[0-9]+$/);
        	
			//alert(data.form.find('input[name="departmentId"]').text());
			
        	if(rx.test(data.form.find('input[name="salary"]').val())){
        		alert("Salary should only be numeric and greater than 0.");		
        		return false;
        	}
        	
        	if(digitsOnly.test(data.form.find('input[name="yrsService"]').val())){
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
}

function renderFamilyMember(empId) {
	$('#familyMemberTable').jtable({
		title : 'Family Information',
		actions : {
			createAction : '/hris/SaveEmployeeFamilyMemberAction',
			listAction : '/hris/GetEmployeeFamilyMemberAction?empId=' + empId,
			updateAction: '/hris/UpdateEmployeeFamilyMemberAction'
		},
		formCreated : function(event, data) {
			
			$(data.form).addClass("custom_horizontal_form_field");
        	
        	var $dialogDiv = data.form.closest('.ui-dialog');
            $dialogDiv.position({
                my: "center",
                at: "center",
                of: $("#familyMemberTable")
            });
            
			$(data.form).parent().parent().css("top", 155);
			
			data.form.find('select').css('width','200px');
			data.form.find('input').css('width','200px');
			
		},
		fields : {
			empFamilyMemberId : {
				key : true,
				list : false
			},
			empId : {
				create : true,
				list : false,
				type : 'hidden',
				defaultValue : empId

			},
			name : {
				title : 'Family Member Name',
				edit : true
			},
			gender : {
				title : 'Gender',
				edit : true,
				options : {
					'M' : 'Male',
					'F' : 'Female'
				}
			},
			relation : {
				title : 'Relationship',
				edit : true
			},
			age : {
				title : 'Age',
				edit : true
			},
			remarks : {
				title : 'Remarks',
				edit : true
			},
			contactNum : {
				title : 'Contact Number',
				edit : true
			}

		}
	});
	$('#familyMemberTable').jtable('load');
}

function renderEmployeeEducation(empId) {
	$('#educTable').jtable({
		title : 'Educational Background',
		actions : {
			createAction : '/hris/SaveEducationalBackgroundAction',
			listAction : '/hris/GetEducationalBackgroundAction?empId=' + empId,
			updateAction: '/hris/UpdateEducationalBackgroundAction'
			
		},
		formCreated : function(event, data) {
			
			$(data.form).addClass("custom_horizontal_form_field");
        	
        	var $dialogDiv = data.form.closest('.ui-dialog');
            $dialogDiv.position({
                my: "center",
                at: "center",
                of: $("#educTable")
            });
            
			$(data.form).parent().parent().css("top", 155);
			
			data.form.find('select').css('width','200px');
			data.form.find('input').css('width','200px');
	
			
		},
		fields : {
			educBkgrndId : {
				key : true,
				list : false
			},
			empId : {
				key : true,
				create : true,
				list : false,
				type : 'hidden',
				defaultValue : empId

			},
			yearAttended : {
				title : 'Year Attended',
				edit : true
			},
			course : {
				title : 'Course/Degree',
				edit : true
			},
			school : {
				title : 'School',
				edit : true
			},
			yearGraduated : {
				title : 'Year Graduated',
				edit : true
			},
			remarks : {
				title : 'Remarks',
				edit : true
			}
		}
	});
	$('#educTable').jtable('load');

}




function getEmployeeInfoDashboard(empId) {
	var oAjaxCall = $.ajax({
		type : "GET",
		url : "/hris/GetEmployeeAction",
		data : "empId=" + empId,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {			
			
			$('#employeeName').html(data.Employee.firstname + " " + data.Employee.lastname);			
			$('#departmentNameDashEmployee').html(data.Employee.departmentName);			
			$('#jobTitleNameDashboard').html(data.Employee.jobTitleName);	
			
			//var imgLocation = "images/noimage_2.gif";
			var imgLocation = data.Employee.picLoc;			
			
			$('img[id$=empImgHolder]').load(imgLocation, function(response, status, xhr) {
			    if (status == "error") 
			        $(this).attr('src', 'images/noimage_2.gif');
			    else
			        $(this).attr('src', imgLocation);
			});
						
			//alert(imgLocation);
			//$('#empImgHolder').attr('src', imgLocation);
			
			//$('#empImgHolder').attr('src', data.Employee.picLoc);
			

		},
		error : function(data) {
			alert('error: ' + data);
			isSuccessful = false;
		}
	});

}




function getEmployeeInfo(empId) {
	var oAjaxCall = $.ajax({
		type : "GET",
		url : "/hris/GetEmployeeAction",
		data : "empId=" + empId,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			
			$('#employeeName').html(data.Employee.firstname + " " + data.Employee.lastname);
			$('#jobTitleName').html(data.Employee.jobTitleId);
			$('#departmentNameEmployee').html(data.Employee.departmentId);
			$('#empNo').val(data.Employee.empNo);
			$('#plantillaNo').val(data.Employee.plantillaNo);
			$('#firstname').val(data.Employee.firstname);
			$('#lastname').val(data.Employee.lastname);
			$('#middlename').val(data.Employee.middlename);
			$('#username').val(data.Employee.username);
			$('#password').val(data.Employee.password);
			$('#crn').val(data.Employee.crn);
			
			if(data.Employee.dateOfBirth == '1900-01-01'){
				$('#dateOfBirth').val("");
			} else {
				$('#dateOfBirth').val(data.Employee.dateOfBirth);
			}
			
			$('#email').val(data.Employee.email);
			$('#telNo').val(data.Employee.telNo);
			$('#mobileNo').val(data.Employee.mobileNo);
			$('#gender').val(data.Employee.gender);
			$('#birthPlace').val(data.Employee.birthPlace);
			$('#civilStatus').val(data.Employee.civilStatus);
			$('#nationality').val(data.Employee.nationality);
			$('#street').val(data.Employee.street);
			$('#zipCode').val(data.Employee.zipCode);
			$('#empStatus').val(data.Employee.empStatus);
			
			
			if(data.Employee.employmentDate == '1900-01-01'){
				$('#employmentDate').val("");
			} else {
				$('#employmentDate').val(data.Employee.employmentDate);
			}
			
			if(data.Employee.endOfContract == '1900-01-01'){
				$('#endOfContract').val("");
			} else {
				$('#endOfContract').val(data.Employee.endOfContract);
			}
			
			
			$('#jobTitleDropDownID').val(data.Employee.jobTitleId);
			$('#cityDropDownID').val(data.Employee.cityId);
			$('#countryDropDownID').val(data.Employee.countryId);
			//$('#departmentDropDownID').val(data.Employee.departmentId);
			//$('#divisionDropDownID').val(data.Employee.divisionId);
			
			$('#sectionDropDownID').val(data.Employee.sectionId);
			$('#unitDropDownID').val(data.Employee.unitId);
			$('#subUnitDropDownID').val(data.Employee.subUnitId);
			$('#personnelTypeDropDownID').val(data.Employee.personnelType);
			
			
			
			$('#employeeTypeDropDownID').val(data.Employee.employeeTypeId);
			$('#provinceDropDownID').val(data.Employee.provinceId);
			
			$('#sss').val(data.Employee.sss);
			$('#gsis').val(data.Employee.gsis);
			$('#hdmf').val(data.Employee.hdmf);
			$('#tin').val(data.Employee.tin);
			$('#phic').val(data.Employee.phic);
			$('#taxstatus').val(data.Employee.taxstatus);
			
			$('#employeeName').html(data.Employee.firstname + " " + data.Employee.lastname);			
			$('#departmentNameDashEmployee').html(data.Employee.departmentName);			
			$('#jobTitleNameDashboard').html(data.Employee.jobTitleName);	
			
			//var imgLocation = "images/noimage_2.gif";			
			var imgLocation = data.Employee.picLoc;
			
			
			$('img[id$=empImgHolder]').load(imgLocation, function(response, status, xhr) {
			    if (status == "error") 
			        $(this).attr('src', 'images/noimage_2.gif');
			    else
			        $(this).attr('src', imgLocation);
			});
			
			
			//if(data.Employee.picLoc != ""){
				
			//	var exists = ImageExists(data.Employee.picLoc);
			//	if(exists) {
			//		imgLocation = data.Employee.picLoc;
			//	}
				
				
			//}
			
			//alert(imgLocation);
			//$("#empImgHolder").attr("src", imgLocation);
			$('#picLoc').val(data.Employee.picLoc);
			

		},
		error : function(data) {
			alert('error: ' + data);
			isSuccessful = false;
		}
	});

}

function displayEmployeeSavedSVNames(empId) {
	var oAjaxCall = $.ajax({
		type : "GET",
		url : "/hris/GetEmployeeSupervisorAction",
		data : "empId=" + empId,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			
			$('#superVisor1FullName').val(data.supervisor1FN);
			$('#superVisor2FullName').val(data.supervisor2FN);
			$('#superVisor3FullName').val(data.supervisor3FN);
			
			$('#superVisor1Id').val(data.supervisor1Id);
			$('#superVisor2Id').val(data.supervisor2Id);
			$('#superVisor3Id').val(data.supervisor3Id);

		},
		error : function(data) {
			alert('error: ' + data);
			isSuccessful = false;
		}
	});

}

function getEmployeePayrollInfo(empId) {
	var oAjaxCall = $.ajax({
		type : "GET",
		url : "/hris/GetEmployeePayrollInfoAction",
		data : "empId=" + empId,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			
			$('#monthlyRate').val(data.EmployeePayrollInfo.monthlyRate);
			$('#dailyRate').val(data.EmployeePayrollInfo.dailyRate);
			$('#hourlyRate').val(data.EmployeePayrollInfo.hourlyRate);			
			$('#foodAllowance').val(data.EmployeePayrollInfo.foodAllowance);			
			$('#cola').val(data.EmployeePayrollInfo.cola);
			$('#taxShield').val(data.EmployeePayrollInfo.taxShield);
			$('#transAllowance').val(data.EmployeePayrollInfo.transAllowance);			
			$('#payrollType').val(data.EmployeePayrollInfo.payrollType);
			$('#ban').val(data.EmployeePayrollInfo.ban);
			$('#bankNameBan').val(data.EmployeePayrollInfo.bankNameBan);
			$('#hasNightDifferential').val(data.EmployeePayrollInfo.hasNightDifferential);
			$('#hasHolidayPay').val(data.EmployeePayrollInfo.hasHolidayPay);
			$('#shiftingScheduleDropDownID').val(data.EmployeePayrollInfo.shiftingScheduleId);
			
			$('#gsisEmployeeShare').val(data.EmployeePayrollInfo.gsisEmployeeShare);
			$('#gsisEmployerShare').val(data.EmployeePayrollInfo.gsisEmployerShare);
			$('#philhealthEmployeeShare').val(data.EmployeePayrollInfo.philhealthEmployeeShare);
			$('#philhealthEmployerShare').val(data.EmployeePayrollInfo.philhealthEmployerShare);
			$('#pagibigEmployeeShare').val(data.EmployeePayrollInfo.pagibigEmployeeShare);
			$('#pagibigEmployerShare').val(data.EmployeePayrollInfo.pagibigEmployerShare);

			
			

		},
		error : function(data) {
			alert('error: ' + data);
			isSuccessful = false;
		}
	});
}

function saveEmployeePayrollInfo() {
	
	var monthlyRate = document.getElementById("monthlyRate").value;
	var dailyRate = document.getElementById("dailyRate").value;
	var hourlyRate = document.getElementById("hourlyRate").value;
	var payrollType = document.getElementById("payrollType").value;
	
	var pagibigEmployeeShare = document.getElementById("pagibigEmployeeShare").value;
	var pagibigEmployerShare = document.getElementById("pagibigEmployerShare").value;
	var gsisEmployeeShare = document.getElementById("gsisEmployeeShare").value;
	var gsisEmployerShare = document.getElementById("gsisEmployerShare").value;
	
	dailyRate = dailyRate.replace(/,/g, '');
	hourlyRate = hourlyRate.replace(/,/g, '');
	
	var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
	
	if(monthlyRate == ""){
		alert("Monthly Rate is a required field");
		return;
	}
	
	if(rx.test(monthlyRate)){
		//alert("Pass");
	} else {
		alert("Monthly Rate should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
	
	if(dailyRate == ""){
		alert("Daily Rate is a required field");
		return;
	}
	
	if(rx.test(dailyRate)){
		//alert("Pass");
	} else {
		alert("Daily Rate should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
	
	if(hourlyRate == ""){
		alert("Hourly Rate is a required field");
		return;
	}
	
	if(rx.test(hourlyRate)){
		//alert("Pass");
	} else {
		alert("Hourly Rate should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
	
	if(payrollType == ""){
		alert("Payroll Type is a required field");
		return;
	}
	
	if(pagibigEmployeeShare == ""){
		
	} else {
		if(rx.test(pagibigEmployeeShare)){
			//alert("Pass");
		} else {
			alert("Pagibig Employee Share should be numberic and should be greater than 0.");
			errorExist = "Y";
		}
	}
	
	if(pagibigEmployerShare == ""){
		
	} else {
		if(rx.test(pagibigEmployerShare)){
			//alert("Pass");
		} else {
			alert("Pagibig Employer Share should be numberic and should be greater than 0.");
			errorExist = "Y";
		}
	}
	
	if(gsisEmployeeShare == ""){
		
	} else {
		if(rx.test(gsisEmployeeShare)){
			//alert("Pass");
		} else {
			alert("GSIS Employee Share should be numberic and should be greater than 0.");
			errorExist = "Y";
		}
	}
	
	
	if(gsisEmployerShare == ""){
		
	} else {
		if(rx.test(gsisEmployerShare)){
			//alert("Pass");
		} else {
			alert("GSIS Employer Share should be numberic and should be greater than 0.");
			errorExist = "Y";
		}
	}
	
	
	

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/SaveEmployeePayrollInfoAction",
		data : JSON.stringify($("#savePayrollInfoForm").serializeObject()),
		cache : false,
		async : true,
		dataType : "json",
		success : function(data) {		
			alert("Employee Payroll Information Successfully Saved.");
		},
		error : function(data) {
			obj = JSON.parse(data);
			alert (obj.count);
			alert('error: ' + data);
			//isSuccessful = false;
		}
	});

	//return isSuccessful;
}

function updateEmployee() {
	//var departmentId = document.getElementById("departmentId").value;
	var empId = document.getElementById("empId").value;
	var empNo = document.getElementById("empNo").value;
	var plantillaNo = document.getElementById("plantillaNo").value;
	//var departmentId = document.getElementById("departmentDropDownID").value;
	//var divisionId = document.getElementById("divisionDropDownID").value;
	
	var sectionId = document.getElementById("sectionDropDownID").value;
	var unitId = document.getElementById("unitDropDownID").value;
	var personnelType = document.getElementById("personnelTypeDropDownID").value;
	//var subUnitId = document.getElementById("subUnitDropDownID").value;
	var firstname = document.getElementById("firstname").value;
	var lastname = document.getElementById("lastname").value;
	var username = document.getElementById("username").value;
	var dateOfBirth = document.getElementById("dateOfBirth").value;	
	var civilStatus = document.getElementById("civilStatus").value;
	var jobTitle = document.getElementById("jobTitleDropDownID").value;
	var tin = document.getElementById("tin").value;
	var taxstatus = document.getElementById("taxstatus").value;
	var employeeType = document.getElementById("employeeTypeDropDownID").value;
	
	var employmentDate = document.getElementById("employmentDate").value;
	
	var gsis = document.getElementById("gsis").value;
	var phic = document.getElementById("phic").value;
	var hdmf = document.getElementById("hdmf").value;
	
	if(empNo == ""){
		alert("Employee Number is a required field");
		return;
	}
	
	if(plantillaNo == ""){
		alert("Plantilla Number is a required field");
		return;
	}
	
	if(firstname == ""){
		alert("Firstname is a required field");
		return;
	}
	
	if(lastname == ""){
		alert("Lastname is a required field");
		return;
	}
	
	if(username == ""){
		alert("Username is a required field");
		return;
	}
	
	if(dateOfBirth == ""){
		alert("Birthdate is a required field");
		return;
	}
	
	if(civilStatus == ""){
		alert("Civil Status is a required field");
		return;
	}
	
	if(jobTitle == ""){
		alert("Job Title is a required field");
		return;
	}
	
	if(employmentDate == ""){
		alert("Employment Date is a required field");
		return;
	}
	
	if(tin == ""){
		alert("TIN is a required field");
		return;
	}
	
	if(taxstatus == ""){
		alert("Tax Status is a required field");
		return;
	}
	
	if(employeeType == ""){
		alert("Employee Type is a required field");
		return;
	}
	
	if(sectionId == ""){
		alert("Section is a required field");
		return;
	}
	
	if(unitId == ""){
		alert("Unit is a required field");
		return;
	}
	
	if(personnelType == ""){
		alert("Personnel Type is a required field");
		return;
	}
	
	if(gsis == ""){
		alert("GSIS is a required field");
		return;
	}
	
	if(phic == ""){
		alert("Philhealth No. is a required field");
		return;
	}
	
	if(hdmf == ""){
		alert("Pagibig is a required field");
		return;
	}
	
	
	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/UpdateEmployeeAction",
		data : JSON.stringify($("#addEmployeeForm").serializeObject()),
		cache : false,
		async : true,
		dataType : "json",
		success : function(data) {
			
			if(data == 0) {
				alert("Username already exist. Please use a different username.");
			} else {
				//indexEmployee();
				alert("Employee Personal Information Successfully Saved.");				
			}
			
			

			// if(saveEmployeePayrollInfo()){
			
			// } else {
			// alert("Employee Payroll Info has invalid values please check.");
			// }

		},
		error : function(data) {
			//console.log(data);  //use this in order to find out [Object Object]
			alert('error: ' + data.responseText); //please see UpdateEmployeeAction for usage
		}
	});
}

function saveEmployee() {
	//var departmentId = document.getElementById("departmentId").value;
	//alert("saveEmployee");
	//var empId = document.getElementById("empId").value;
	
	var empNo = document.getElementById("empNo").value;
	var plantillaNo = document.getElementById("plantillaNo").value;
	//var departmentId = document.getElementById("departmentDropDownID").value;
	//var divisionId = document.getElementById("divisionDropDownID").value;
	
	var sectionId = document.getElementById("sectionDropDownID").value;
	var unitId = document.getElementById("unitDropDownID").value;
	var personnelType = document.getElementById("personnelTypeDropDownID").value;
	//var subUnitId = document.getElementById("subUnitDropDownID").value;
	var firstname = document.getElementById("firstname").value;
	var lastname = document.getElementById("lastname").value;
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	var dateOfBirth = document.getElementById("dateOfBirth").value;	
	var civilStatus = document.getElementById("civilStatus").value;
	var jobTitle = document.getElementById("jobTitleDropDownID").value;
	var tin = document.getElementById("tin").value;
	var taxstatus = document.getElementById("taxstatus").value;
	var employeeType = document.getElementById("employeeTypeDropDownID").value;
	
	var employmentDate = document.getElementById("employmentDate").value;
	
	var gsis = document.getElementById("gsis").value;
	var phic = document.getElementById("phic").value;
	var hdmf = document.getElementById("hdmf").value;
	var crn = document.getElementById("crn").value;
	
	var monthlyRate = document.getElementById("monthlyRate").value;
	var dailyRate = document.getElementById("dailyRate").value;
	var hourlyRate = document.getElementById("hourlyRate").value;
	var payrollType = document.getElementById("payrollType").value;
	
	var pagibigEmployeeShare = document.getElementById("pagibigEmployeeShare").value;
	var pagibigEmployerShare = document.getElementById("pagibigEmployerShare").value;
	var gsisEmployeeShare = document.getElementById("gsisEmployeeShare").value;
	var gsisEmployerShare = document.getElementById("gsisEmployerShare").value;
	
	var hasNightDifferential = document.getElementById("hasNightDifferential").value;
	var hasHolidayPay = document.getElementById("hasHolidayPay").value;
	
	dailyRate = dailyRate.replace(/,/g, '');
	hourlyRate = hourlyRate.replace(/,/g, '');
	
	var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
	
	if(monthlyRate == ""){
		alert("Monthly Rate is a required field");
		return;
	}
	
	if(rx.test(monthlyRate)){
		//alert("Pass");
	} else {
		alert("Monthly Rate should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
	
	if(dailyRate == ""){
		alert("Daily Rate is a required field");
		return;
	}
	
	if(rx.test(dailyRate)){
		//alert("Pass");
	} else {
		alert("Daily Rate should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
	
	if(hourlyRate == ""){
		alert("Hourly Rate is a required field");
		return;
	}
	
	if(rx.test(hourlyRate)){
		//alert("Pass");
	} else {
		alert("Hourly Rate should be numberic and should be greater than 0.");
		errorExist = "Y";
	}
	
	if(payrollType == ""){
		alert("Payroll Type is a required field");
		return;
	}
	
	if(empNo == ""){
		alert("Employee Number is a required field");
		return;
	}
	
	if(plantillaNo == ""){
		alert("Plantilla Number is a required field");
		return;
	}
	
	
	
	if(firstname == ""){
		alert("Firstname is a required field");
		return;
	}
	
	if(lastname == ""){
		alert("Lastname is a required field");
		return;
	}
	
	if(username == ""){
		alert("Username is a required field");
		return;
	}
	
	if(password == ""){
		alert("Password is a required field");
		return;
	}
	
	if(dateOfBirth == ""){
		alert("Birthdate is a required field");
		return;
	}
	
	if(civilStatus == ""){
		alert("Civil Status is a required field");
		return;
	}
	
	if(jobTitle == ""){
		alert("Job Title is a required field");
		return;
	}
	
	if(employmentDate == ""){
		alert("Employment Date is a required field");
		return;
	}
	
	if(tin == ""){
		alert("TIN is a required field");
		return;
	}
	
	if(taxstatus == ""){
		alert("Tax Status is a required field");
		return;
	}
	
	if(employeeType == ""){
		alert("Employee Type is a required field");
		return;
	}
	
	if(sectionId == ""){
		alert("Section is a required field");
		return;
	}
	
	if(unitId == ""){
		alert("Unit is a required field");
		return;
	}
	
	if(personnelType == ""){
		alert("Personnel Type is a required field");
		return;
	}
	
	if(gsis == ""){
		alert("GSIS is a required field");
		return;
	}
	
	if(phic == ""){
		alert("Philhealth No. is a required field");
		return;
	}
	
	if(hdmf == ""){
		alert("Pagibig is a required field");
		return;
	}
	
	if(hasHolidayPay == ""){
		alert("Has Holiday Pay is a required field");
		return;
	}
	
	if(hasNightDifferential == ""){
		alert("Has Night Differential Pay is a required field");
		return;
	}
	
	if(pagibigEmployeeShare == ""){
		
	} else {
		if(rx.test(pagibigEmployeeShare)){
			//alert("Pass");
		} else {
			alert("Pagibig Employee Share should be numberic and should be greater than 0.");
			errorExist = "Y";
		}
	}
	
	if(pagibigEmployerShare == ""){
		
	} else {
		if(rx.test(pagibigEmployerShare)){
			//alert("Pass");
		} else {
			alert("Pagibig Employer Share should be numberic and should be greater than 0.");
			errorExist = "Y";
		}
	}
	
	if(gsisEmployeeShare == ""){
		
	} else {
		if(rx.test(gsisEmployeeShare)){
			//alert("Pass");
		} else {
			alert("GSIS Employee Share should be numberic and should be greater than 0.");
			errorExist = "Y";
		}
	}
	
	
	if(gsisEmployerShare == ""){
		
	} else {
		if(rx.test(gsisEmployerShare)){
			//alert("Pass");
		} else {
			alert("GSIS Employer Share should be numberic and should be greater than 0.");
			errorExist = "Y";
		}
	}
	
	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/SaveEmployeeAction",
		data : JSON.stringify($("#addEmployeeForm").serializeObject()),
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			// alert(JSON.stringify($("#addEmployeeForm").serializeObject()));
			if(data == 0) {
				alert("Username already exist. Please use a different username.");
			} else {
				//indexEmployee();
				alert("Employee Information Successfully Saved.");
				$(location).attr('href', '/hris/updateEmployee.jsp?empId=' + data);
			}
			
		},
		error : function(data) {
			alert('Error: ' + data);

		}
	});	


}

function saveEmployeeCashGift() {
	//var departmentId = document.getElementById("departmentId").value;
	//alert("saveEmployee");
	//var empId = document.getElementById("empId").value;
	
	var empNo = document.getElementById("empNo").value;
	var departmentId = document.getElementById("departmentDropDownID").value;
	var divisionId = document.getElementById("divisionDropDownID").value;
	var firstname = document.getElementById("firstname").value;
	var lastname = document.getElementById("lastname").value;
	var username = document.getElementById("username").value;
	var employmentDate = document.getElementById("employmentDate").value;
	var tin = document.getElementById("tin").value;
	var taxstatus = document.getElementById("taxstatus").value;
	var employeeType = document.getElementById("employeeTypeDropDownID").value;
	
	
	if(empNo == ""){
		alert("Employee Number is a required field");
		return;
	}
	
	if(departmentId == ""){
		alert("Department is a required field");
		return;
	}
	
	if(divisionId == ""){
		alert("Division is a required field");
		return;
	}
	
	if(firstname == ""){
		alert("Firstname is a required field");
		return;
	}
	
	if(lastname == ""){
		alert("Lastname is a required field");
		return;
	}
	
	if(username == ""){
		alert("Username is a required field");
		return;
	}
	
	if(employmentDate == ""){
		alert("Employment Date is a required field");
		return;
	}
	
	if(tin == ""){
		alert("TIN is a required field");
		return;
	}
	
	if(taxstatus == ""){
		alert("Tax Status is a required field");
		return;
	}
	
	if(employeeType == ""){
		alert("Employee Type is a required field");
		return;
	}
	
	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/SaveEmployeeAction",
		data : JSON.stringify($("#addEmployeeForm").serializeObject()),
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			// alert(JSON.stringify($("#addEmployeeForm").serializeObject()));
			if(data == 0) {
				alert("Username already exist. Please use a different username.");
			} else {
				//indexEmployee();
				alert("Employee Information Successfully Saved.");
				$(location).attr('href', '/hris/updateEmployee.jsp?empId=' + data);
			}
			
		},
		error : function(data) {
			alert('Error: ' + data);

		}
	});	


}

function resetForm() {
	document.getElementById("addEmployeeForm").reset();
}


function renderCBA(empid) {

		//alert("empId: " + empid) ;
		var addButtonEnabled = 'YES';
    	var editButtonEnabled = 'YES';
    	var exportButtonEnabled = 'YES';
    	var printButtonEnabled = 'YES';
    	var actionsJTableVar = {    			 
    	};    	
    	var toolBarsJTableVar = { 			 
    	};    	
    	
		
		if(addButtonEnabled == 'YES' && editButtonEnabled == 'YES'){    		
    		actionsJTableVar = {    	    		    	    	
    			listAction: '/hris/GetAllEmployeeCBAAction?empId='+empid,                
    	        createAction: '/hris/SaveEmployeeCBAAction',
				updateAction: '/hris/UpdateEmployeeCBAAction'    			    		
    	    };
		} else if (addButtonEnabled == 'YES' && editButtonEnabled == 'NO') {			
			actionsJTableVar = {	    	   
	    		listAction: '/hris/GetAllEmployeeCBAAction?empId='+empid,                
	    	    createAction: '/hris/SaveEmployeeCBAAction'    			    		
			};
		} else if (addButtonEnabled == 'NO' && editButtonEnabled == 'YES') {			
			actionsJTableVar = {
	    		listAction: '/hris/GetAllEmployeeCBAAction?empId='+empid,    	        	
				updateAction: '/hris/UpdateEmployeeCBAAction'
			};
		} else {			
			actionsJTableVar = {	    	    	
	    		listAction: '/hris/GetAllEmployeeCBAAction?empId='+empid
			};
		}		
			
		
		$('#containerCBAEntry').jtable();
		$('#containerCBAEntry').jtable('destroy');
		
				
		$('#containerCBAEntry').jtable({
	            title: 'Competency Base Assesstment',
	            actions: actionsJTableVar,
	            toolbar: toolBarsJTableVar,
	            fields: {
	            	cbaId: {
	                    key: true,
	                    list: false
	                },
	                empId: {
	                	type:'hidden',
	                	defaultValue:empid
	                },
	                //CHILD TABLE DEFINITION FOR "PAYMENTS "
	                Payments: {
	                    title: '',
	                    width: '5%',
	                    sorting: false,
	                    edit: false,
	                    create: false,
	                    display: function (cbaData) {
	                        //Create an image that will be used to open child table
	                        var $img = $('<img src="/hris/images/list_metro.png" title="List of Payments" />');
	                        //Open child table when user clicks the image
	                        $img.click(function () {
	                            $('#containerCBAEntry').jtable('openChildTable',
	                                    $img.closest('tr'),
	                                    {
	                                        title: 'CBA Details For Performance Year: ' + cbaData.record.performanceYear,
	                                        actions: {
	                                           	 listAction: '/hris/GetAllEmployeeCBADetailsAction?cbaId='+cbaData.record.cbaId,
	                                             createAction: '/hris/SaveEmployeeCBADetailsAction',
	                                             updateAction: '/hris/UpdateEmployeeCBADetailsAction'
	                                        },
	                                        fields: {
	                                        		cbaDetailsId: {
	                                        			key: true,
	                            	                    list: false
	                                            	},
	                                            	cbaId: {
	                                            		type:'hidden',
	                            	                	defaultValue:cbaData.record.cbaId
	                                            	},
	                                            	majorFinalOutput:  {
	                            	                	title: 'Major Final Output',
	                            	                    type: 'textarea'
	                            	                },
	                            	                performanceIndicator:  {
	                            	                	title: 'Performance Indicator',
	                            	                    type: 'textarea'
	                            	                },	                            	                
	                            	                actualAccomplishment:  {
	                            	                	title: 'ActualcAccomplishments',
	                            	                    type: 'textarea'
	                            	                },
	                            	                qRating: {
	                            	                    title: 'Q(1)'
	                            	                },
	                            	                eRating: {
	                            	                    title: 'E(2)'
	                            	                },
	                            	                tRating: {
	                            	                    title: 'T(3)'
	                            	                },
	                            	                aveRating: {
	                            	                    title: 'A(4)'
	                            	                },
	                            	                remarks: {
	                            	                    title: 'Remarks',
	                            	                    type: 'textarea'
	                            	                }
	                                            	
	                                            },
	                                            formCreated: function (event, data) {
	                              	        	  $(data.form).addClass("custom_horizontal_form_field");
	                              	        	  
	                              					
	                              		        	
	                              		        	var $dialogDiv = data.form.closest('.ui-dialog');
	                              		            $dialogDiv.position({
	                              		                my: "center",
	                              		                at: "center",
	                              		                of: $("#containerCBAEntry")
	                              		            });
	                              		            
	                              		            $(data.form).parent().parent().css("top","150");
	                              		            $(data.form).parent().parent().css("margin-bottom","100");
	                              			        
	                              			        data.form.find('input').css('width','400px');
	                              		            data.form.find('textarea').css('width','400px');
	                              		            data.form.find('textarea').css('height','200px');
	                              		            data.form.find('select').css('width','400px');
	                              		            data.form.find('select').css('padding','7.5px');                              	        	
	                              	          
	                              		            data.form.validationEngine();
	                              	                   

	                              	           },
	                              	            //Validate form when it is being submitted
	                              	            formSubmitting: function (event, data) {
	                              	            	data.form.find('input[name="majorFinalOutput"]').addClass('validate[required]');
	                              		        	data.form.find('input[name="performanceIndicator"]').addClass('validate[required]');
	                              	            	                             	            	
	                              	                return data.form.validationEngine('validate');
	                              	            },
	                              	            //Dispose validation logic when form is closed
	                              	            formClosed: function (event, data) {
	                              	                data.form.validationEngine('hide');
	                              	                data.form.validationEngine('detach');
	                              	            }
	                                            
	                                        }
	                                    , function (data) { //opened handler
	                                        data.childTable.jtable('load');
	                                    });
	                        });
	                        //Return image to show on the person row
	                        return $img;
	                    }
	                },
	                monthFrom: {
	                    title: 'Month From',
						options: {1:'January',2:'February',3:'March',4:'April',5:'May',6:'June',
							7:'July',8:'August',9:'September',10:'October',11:'November',12:'December'}
	                },
	                monthTo: {
	                    title: 'Month To',
						options: {'1':'January','2':'February','3':'March','4':'April','5':'May','6':'June',
							'7':'July','8':'August','9':'September','10':'October','11':'November','12':'December'}
	                },
	                performanceYear: {
	                    title: 'Performance Year'
	                },
	                approvedBy: {
	                	title: 'Approved By',
	                    edit: false,
	                    list: true,
	                    options:  
	                    	function(data) {
								if (data.source == 'list') {
				                	return '/hris/GetAllEmployeeAction?forListJTable=true&empId='+data.record.approvedBy;
									//return '/hris/GetEmployeeAction?displayOption=true&empId='+data.record.empId;
								}
				                if (data.source == 'create') {
				                	return '/hris/GetAllEmployeeAction?displayOption=true&forEdit=false';
				                }
				                if (data.source == 'edit') {
				                	return '/hris/GetAllEmployeeAction?displayOption=true&forEdit=true&empId='+data.record.approvedBy;
								}
		                	},                    	
	                    create:false
	                },
	                assessedBy: {
	                	title: 'Assessed By',
	                    edit: false,
	                    list: false,
	                    options:  
	                    	function(data) {
								if (data.source == 'list') {
				                	return '/hris/GetAllEmployeeAction?forListJTable=true&empId='+data.record.assessedBy;
									//return '/hris/GetEmployeeAction?displayOption=true&empId='+data.record.empId;
								}
				                if (data.source == 'create') {
				                	return '/hris/GetAllEmployeeAction?displayOption=true&forEdit=false';
				                }
				                if (data.source == 'edit') {
				                	return '/hris/GetAllEmployeeAction?displayOption=true&forEdit=true&empId='+data.record.assessedBy;
								}
		                	},                    	
	                    create:false
	                },
	                finalRatingBy: {
	                	title: 'Final Rating By',
	                    edit: false,
	                    list: false,
	                    options:  
	                    	function(data) {
								if (data.source == 'list') {
				                	return '/hris/GetAllEmployeeAction?forListJTable=true&empId='+data.record.finalRatingBy;
									//return '/hris/GetEmployeeAction?displayOption=true&empId='+data.record.empId;
								}
				                if (data.source == 'create') {
				                	return '/hris/GetAllEmployeeAction?displayOption=true&forEdit=false';
				                }
				                if (data.source == 'edit') {
				                	return '/hris/GetAllEmployeeAction?displayOption=true&forEdit=true&empId='+data.record.finalRatingBy;
								}
		                	},                    	
	                    create:false
	                },
	                assessmentDate: {
	                    title: 'Assessment Date',                
	                    //type: 'date',
	                    displayFormat: 'yy-mm-dd',
	                    list: false
	                },               
	                finalRating: {
	                    title: 'Final Rating',
	                    list: false
	                },
	                comments: {
	                    title: 'Comments and Recommendation',
	                    type: 'textarea',
	                    list: false
	                }
	                
	            },
	            
	          //Initialize validation logic when a form is created
	          formCreated: function (event, data) {
	        	  $(data.form).addClass("custom_horizontal_form_field");
	        	  
					
		        	
		        	var $dialogDiv = data.form.closest('.ui-dialog');
		            $dialogDiv.position({
		                my: "center",
		                at: "center",
		                of: $("#containerCBAEntry")
		            });
		            
		            $(data.form).parent().parent().css("top","150");
		            $(data.form).parent().parent().css("margin-bottom","100");
		            
			        data.form.find('input').css('width','400px');
		            data.form.find('textarea').css('width','400px');
		            data.form.find('textarea').css('height','200px');
		            data.form.find('select').css('width','400px');
		            data.form.find('select').css('padding','7.5px');
		           
		            
		        	
	        	
	           if ((data.formType == 'edit')|| (data.formType == 'create')){
	        	   var $assessmentDate = data.form.find ('input[name="assessmentDate"]');   
	               
	               
	               $assessmentDate.datepicker({
	            		changeMonth : true,
	            		changeYear : true,
	            		dateFormat : 'yy-mm-dd',
	            		yearRange : '1910:2100',
	            	    beforeShow: function(input, inst){	            	    	
	            	           $(".ui-datepicker").css('font-size', 11);
	            	    }
	            	});	  	   
	        	   
	            	
	            }
	           
	           data.form.validationEngine();
	                   

	           },
	            //Validate form when it is being submitted
	            formSubmitting: function (event, data) {
	            	data.form.find('input[name="monthFrom"]').addClass('validate[required]');
		        	data.form.find('input[name="monthTo"]').addClass('validate[required]');
		        	data.form.find('input[name="performanceYear"]').addClass('validate[required]');
	            	
	            	
	            	var digitsOnly = new RegExp(/^[0-9]+$/);
	            	var test  = data.form.find('input[name="performanceYear"]').val();
	            	
	            	
	            	
	            	if(digitsOnly.test(data.form.find('input[name="performanceYear"]').val())){
		        		if(data.form.find('input[name="performanceYear"]').val() == "0"){
							alert("Performance Year should be numberic and should be greater than 0.");
							return false;
						}
		        	} else {
		        		alert("Performance Year should only be numeric and greater than 0.");
		        		return false;
		        	}
	            	
	            	
	            	
	                return data.form.validationEngine('validate');
	            },
	            //Dispose validation logic when form is closed
	            formClosed: function (event, data) {
	                data.form.validationEngine('hide');
	                data.form.validationEngine('detach');
	            }
	        });
	        $('#containerCBAEntry').jtable('load');
	
		
}

