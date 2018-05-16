//search common

function populateCommonDropDownInLeftSearch() {
	populateSectionDropDown();
	populateUnitDropDown();		
	populatePersonnnelType();
	populatePlantillaDropDown();
}

function populateTable(empId, empNo, empName, sectionName, unitName) {	
		var ctrOfItems = document.getElementById("ctrOfItems").value;
		
		var errorExist = "N";
		
		$("#empListTable").find(":input[name*='empId']").each(function(){			
			if($(this).val() == empId){				
				errorExist = "Y";
			}			
		});
		
		if(errorExist == "Y"){
			return;
		}
		
		ctrOfItems = parseInt(ctrOfItems) + 1;
		
		var divs = '';
		divs +="<tr>";		
		
		divs +="<td>"			  
		divs += empNo;
		divs +="<input type='hidden' id='empId' name='empId[" + ctrOfItems + "]' value='" + empId + "' />";	  
		divs +="</td>"
		divs +="<td>" + empName + "</td>";
		divs +="<td>" + sectionName + "</td>";
		divs +="<td>" + unitName + "</td>";		
		
		divs +="<td>";
		divs +="<img class='removebutton' src='images/delete.png' width='30px' height='30px' />";
		divs +="</td>";		
		
		divs +="</tr>";
		
		$('#empListTable').append(divs);		
		
		$('#ctrOfItems').val(ctrOfItems);
}

function addAllEmployeeCommon() {
	
	if (confirm('Are you sure you want to add all the employees in the list?')) {   	    
		var sectionId = "";
		var unitId = "";
		var personnelTypeId = "";
		var plantillaId = "";
		
		
		var url = '/hris/SearchCommonEmployeeAction?sectionId='+sectionId+'&unitId='+unitId+'&personnelTypeId='+personnelTypeId+'&plantillaId='+plantillaId;		
		
		var oAjaxCall = $.ajax({
				type : "POST",
				url : url,
				cache : false,
				async : false,
				dataType : "json",
				success : function(data) {
					jQuery.each(data.Records, function(i, item) {		
						
						var empName = item.lastname + ', ' + item.firstname;
						
						populateTable(item.empId, item.empNo, empName, item.sectionName, item.unitName);							
						  				  
						
					});
	
				},
				error : function(data) {
					alert('error addAllEmployeeCommon(): ' + data);
				}
	
			});
	}
}

function addEmployeeCommon() {
	
	if (confirm('Are you sure you want to add all the employees under this search criteria in the list?')) {   	    
		var sectionId = document.getElementById("sectionDropDownID").value;
		var unitId = document.getElementById("unitDropDownID").value;
		var personnelTypeId = document.getElementById("personnelTypeDropDownID").value;
		var plantillaId = document.getElementById("plantillaDropDownID").value;
		
		
		var url = '/hris/SearchCommonEmployeeAction?sectionId='+sectionId+'&unitId='+unitId+'&personnelTypeId='+personnelTypeId+'&plantillaId='+plantillaId;		
		
		var oAjaxCall = $.ajax({
				type : "POST",
				url : url,
				cache : false,
				async : false,
				dataType : "json",
				success : function(data) {
					jQuery.each(data.Records, function(i, item) {		
						
						var empName = item.lastname + ', ' + item.firstname;
						
						populateTable(item.empId, item.empNo, empName, item.sectionName, item.unitName);							
						  				  
						
					});
	
				},
				error : function(data) {
					alert('error addEmployeeCommon(): ' + data);
				}
	
			});
	}
}

function removeAll() {
	if (confirm('Are you sure you want to remove all the employees in the list?')) {   	    		
    	$('#empListTable').empty();		
    	var header = '<tr><th width="100">EMP NO.</th><th width="200">NAME</th><th width="200">SECTION</th><th width="200">UNIT</th><th width="30">ACTION</th></tr>';
    	$('#empListTable').html(header);
    }
}

function searchEmployeeCommon (){
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
				divs +="<table id=\"box-table-a1\" width=\"420px;\" style=\"margin: 15px 0px 0px 0px;\"     >";				    
				divs +="<thead><tr>";					
				divs +="<th scope=\"col\">Employee Search Result</th>";
				jQuery.each(data.Records, function(i, item) {
					//empSearchMap[item.empId] = { empid: item.empId, firstname:item.firstname, lastname:item.lastname, empno:item.empNo, sectionName:item.sectionName, unitName:item.unitName};			  
					var empName = item.lastname + ', ' + item.firstname;
					divs +="<tr onclick=\"clickSearchResultCommon("+item.empId +  ", '" + item.empNo + "', '" + empName + "', '" + item.sectionName + "', '" + item.unitName + "');\" style='cursor:pointer;'>";
					//divs +="<tr onclick=\"clickSearchResult("+item.empId+")\" style='cursor:pointer;'>";						
					divs +="<td>" + item.lastname + ', ' + item.firstname + "</td>";						
					divs +="</tr>"  				  
					
				});
				  	
				divs +="</tbody></table>";
					
				$('#searchHolderId').html(divs);

			},
			error : function(data) {
				alert('error searchEmployeeCommon(): ' + data);
			}

		});
	
}

function clickSearchResultCommon(empId, empNo, empName, sectionName, unitName) {	
	var ctrOfItems = document.getElementById("ctrOfItems").value;
	
	var errorExist = "N";
	
	$("#empListTable").find(":input[name*='empId']").each(function(){			
		if($(this).val() == empId){
			alert("Employee already existing in the list.");
			errorExist = "Y";
		}			
	});
	
	if(errorExist == "Y"){
		return;
	}
	
	ctrOfItems = parseInt(ctrOfItems) + 1;
	
	var divs = '';
	divs +="<tr>";		
	
	divs +="<td>"			  
	divs += empNo;
	divs +="<input type='hidden' id='empId' name='empId[" + ctrOfItems + "]' value='" + empId + "' />";	  
	divs +="</td>"
	divs +="<td>" + empName + "</td>";
	divs +="<td>" + sectionName + "</td>";
	divs +="<td>" + unitName + "</td>";		
	
	divs +="<td>";
	divs +="<img class='removebutton' src='images/delete.png' width='30px' height='30px' />";
	divs +="</td>";		
	
	divs +="</tr>";
	
	$('#empListTable').append(divs);		
	
	$('#ctrOfItems').val(ctrOfItems);

}


//dashboard

function showMemoTable(empId) {	

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetEmployeeMemoByToRecipientAction?toRecipientEmpId=" + empId,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			if (data.Records.length) {
				
				var items = "<div class='headerDashboardTable'>Memo List</div>";
				items += "<table id='box-table-a' summary='Employee Pay Sheet' width='420px;'>";
				items += "<thead>";
				items += "<tr>";
				items += "<th scope='col'>Subject</th>";
				items += "<th scope='col'>From</th>";
				items += "<th scope='col'>Message</th>";
				items += "</tr>";
				items += "</thead>";
				items += "<tbody>";
			
			
				$.each(data.Records, function(i, item) {
					items += "<tr>";
					items += "<td>" + item.subject + "</td>";
					items += "<td>" + item.fromSender + "</td>";
					items += "<td>" + item.message + "</td>";
					items += "</tr>";
				});		
				
				items += "</tbody>";			
				items += "</table>";
				
				$('#memoTable').html(items);
			}
			;
			
			

		},
		error : function(data) {
			alert('error: showMemoTable' + data);
		}

	});
};

function showNotificationTable(empId) {	

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetEmpNotificationByToRecipientAction?toRecipientEmpId=" + empId,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			
			if (data.Records.length) {
				var items = "<div class='headerDashboardTable'>Notification List</div>";
				items += "<table id='box-table-a' summary='Employee Pay Sheet' width='440px;'>";
				items += "<thead>";
				items += "<tr>";
				items += "<th scope='col'>Subject</th>";
				items += "<th scope='col'>From</th>";
				items += "<th scope='col'>Message</th>";
				items += "</tr>";
				items += "</thead>";
				items += "<tbody>";
			
			
				$.each(data.Records, function(i, item) {
					items += "<tr>";
					items += "<td>" + item.subject + "</td>";
					items += "<td>" + item.fromSender + "</td>";
					items += "<td>" + item.message + "</td>";
					items += "</tr>";
				});			
				
				items += "</tbody>";			
				items += "</table>";
				
				$('#notificationTable').html(items);
			}
			;
			
			

		},
		error : function(data) {
			alert('error: showNotificationTable' + data);
		}

	});
};

function showLeaveTable(empId) {	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllLeaveAction?empId=" + empId,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			if (data.Records.length) {
				var items = "<div class='headerDashboardTable'>Filed Leave List</div>";	
				items += "<table id='box-table-a' summary='Employee Pay Sheet' width='890px;'>";
				items += "<thead>";
				items += "<tr>";
				items += "<th scope='col'>Leave Type</th>";
				items += "<th scope='col'>Date From</th>";
				items += "<th scope='col'>Date To</th>";
				items += "<th scope='col'># of Days</th>";			
				items += "<th scope='col'>Status</th>";
				items += "</tr>";
				items += "</thead>";
				items += "<tbody>";
			
			
				$.each(data.Records, function(i, item) {
					items += "<tr>";
					items += "<td>" + item.leaveType + "</td>";
					items += "<td>" + item.dateFrom + "</td>";
					items += "<td>" + item.dateTo + "</td>";
					items += "<td>" + item.noDays + "</td>";
					items += "<td>";
					
					//0 - FOR APPROVAL
	                //1 - NOT APPROVED
	                //2 - FOR UNIT SUPERVISOR APPROVAL
	                //3 - FOR SECTION SUPERVISOR APPROVAL
	                //4 - FOR HR APPROVAL
	                //5 - FOR ADMIN APPROVAL
					//6 - APPROVED
					
					if(item.status == 0){
						items += "FOR APPROVAL"
					}
					
					if(item.status == 1){
						items += "NOT APPROVED"
					}
					
					if(item.status == 2){
						items += "FOR UNIT SUPERVISOR APPROVAL"
					}
					
					if(item.status == 3){
						items += "FOR SECTION SUPERVISOR APPROVAL"
					}
					
					if(item.status == 4){
						items += "FOR HR APPROVAL"
					}
					
					if(item.status == 5){
						items += "FOR ADMIN APPROVAL"
					}
					
					if(item.status == 6){
						items += "APPROVED"
					}
					
					items += "</td>";
					
					items += "</tr>";
				});		
				
				items += "</tbody>";			
				items += "</table>";
				
				$('#leaveTable').html(items);
				
			}
			;
			
			

		},
		error : function(data) {
			alert('error: showLeaveTable' + data);
		}

	});
};


function showOvertimeTable(empId) {	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllOvertimeAction?empId=" + empId,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			if (data.Records.length) {
				var items = "<div class='headerDashboardTable'>Filed Overtime List</div>";	
				items += "<table id='box-table-a' summary='Employee Pay Sheet' width='890px;'>";
				items += "<thead>";
				items += "<tr>";
				items += "<th scope='col'># of Hours</th>";
				items += "<th scope='col'>Rendered Date</th>";
				items += "<th scope='col'>Remarks</th>";		
				items += "<th scope='col'>Status</th>";
				items += "</tr>";
				items += "</thead>";
				items += "<tbody>";
			
			
				$.each(data.Records, function(i, item) {
					items += "<tr>";
					items += "<td>" + item.noOfHours + "</td>";
					items += "<td>" + item.dateRendered + "</td>";
					items += "<td>" + item.remarks + "</td>";
					items += "<td>";
					
					//0 - FOR APPROVAL
	                //1 - NOT APPROVED
	                //2 - FOR UNIT SUPERVISOR APPROVAL
	                //3 - FOR SECTION SUPERVISOR APPROVAL
	                //4 - FOR HR APPROVAL
	                //5 - FOR ADMIN APPROVAL
					//6 - APPROVED
					
					if(item.status == 0){
						items += "FOR APPROVAL"
					}
					
					if(item.status == 1){
						items += "NOT APPROVED"
					}
					
					if(item.status == 2){
						items += "FOR UNIT SUPERVISOR APPROVAL"
					}
					
					if(item.status == 3){
						items += "FOR SECTION SUPERVISOR APPROVAL"
					}
					
					if(item.status == 4){
						items += "FOR HR APPROVAL"
					}
					
					if(item.status == 5){
						items += "FOR ADMIN APPROVAL"
					}
					
					if(item.status == 6){
						items += "APPROVED"
					}
					
					items += "</td>";
					
					items += "</tr>";
				});		
				
				items += "</tbody>";			
				items += "</table>";
				
				$('#overtimeTable').html(items);
				
			}
			;
			
			

		},
		error : function(data) {
			alert('error: showOvertimeTable' + data);
		}

	});
};

function showOffSetTable(empId) {	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllOffSetAction?empId=" + empId,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			if (data.Records.length) {
				var items = "<div class='headerDashboardTable'>Filed OffSet List</div>";	
				items += "<table id='box-table-a' summary='Employee Pay Sheet' width='890px;'>";
				items += "<thead>";
				items += "<tr>";
				items += "<th scope='col'># of Hours</th>";
				items += "<th scope='col'>Rendered Date</th>";
				items += "<th scope='col'>Remarks</th>";		
				items += "<th scope='col'>Status</th>";
				items += "</tr>";
				items += "</thead>";
				items += "<tbody>";
			
			
				$.each(data.Records, function(i, item) {
					items += "<tr>";
					items += "<td>" + item.noOfHours + "</td>";
					items += "<td>" + item.dateRendered + "</td>";
					items += "<td>" + item.remarks + "</td>";
					items += "<td>";
					
					//0 - FOR APPROVAL
	                //1 - NOT APPROVED
	                //2 - FOR UNIT SUPERVISOR APPROVAL
	                //3 - FOR SECTION SUPERVISOR APPROVAL
	                //4 - FOR HR APPROVAL
	                //5 - FOR ADMIN APPROVAL
					//6 - APPROVED
					
					if(item.status == 0){
						items += "FOR APPROVAL"
					}
					
					if(item.status == 1){
						items += "NOT APPROVED"
					}
					
					if(item.status == 2){
						items += "FOR UNIT SUPERVISOR APPROVAL"
					}
					
					if(item.status == 3){
						items += "FOR SECTION SUPERVISOR APPROVAL"
					}
					
					if(item.status == 4){
						items += "FOR HR APPROVAL"
					}
					
					if(item.status == 5){
						items += "FOR ADMIN APPROVAL"
					}
					
					if(item.status == 6){
						items += "APPROVED"
					}
					
					items += "</td>";
					
					items += "</tr>";
				});		
				
				items += "</tbody>";			
				items += "</table>";
				
				$('#offSetTable').html(items);
				
			}
			;
			
			

		},
		error : function(data) {
			alert('error: showOffSetTable' + data);
		}

	});
};

function showUndertimeTable(empId) {	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllUndertimeAction?empId=" + empId,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			if (data.Records.length) {
				var items = "<div class='headerDashboardTable'>Filed Undertime List</div>";	
				items += "<table id='box-table-a' summary='Employee Pay Sheet' width='890px;'>";
				items += "<thead>";
				items += "<tr>";
				items += "<th scope='col'># of Hours</th>";
				items += "<th scope='col'>Rendered Date</th>";
				items += "<th scope='col'>Remarks</th>";		
				items += "<th scope='col'>Status</th>";
				items += "</tr>";
				items += "</thead>";
				items += "<tbody>";
			
			
				$.each(data.Records, function(i, item) {
					items += "<tr>";
					items += "<td>" + item.noOfHours + "</td>";
					items += "<td>" + item.dateRendered + "</td>";
					items += "<td>" + item.remarks + "</td>";
					items += "<td>";
					
					//0 - FOR APPROVAL
	                //1 - NOT APPROVED
	                //2 - FOR UNIT SUPERVISOR APPROVAL
	                //3 - FOR SECTION SUPERVISOR APPROVAL
	                //4 - FOR HR APPROVAL
	                //5 - FOR ADMIN APPROVAL
					//6 - APPROVED
					
					if(item.status == 0){
						items += "FOR APPROVAL"
					}
					
					if(item.status == 1){
						items += "NOT APPROVED"
					}
					
					if(item.status == 2){
						items += "FOR UNIT SUPERVISOR APPROVAL"
					}
					
					if(item.status == 3){
						items += "FOR SECTION SUPERVISOR APPROVAL"
					}
					
					if(item.status == 4){
						items += "FOR HR APPROVAL"
					}
					
					if(item.status == 5){
						items += "FOR ADMIN APPROVAL"
					}
					
					if(item.status == 6){
						items += "APPROVED"
					}
					
					items += "</td>";
					
					items += "</tr>";
				});		
				
				items += "</tbody>";			
				items += "</table>";
				
				$('#undertimeTable').html(items);
				
			}
			;
			
			

		},
		error : function(data) {
			alert('error: showUndertimeTable' + data);
		}

	});
};


function showLeaveBalanceTable(empId) {	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllLeaveBalanceAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = "<table id='box-table-a' summary='' width='890px;'>";
			items += "<thead>";
			items += "<tr>";
			items += "<th scope='col'>Vacation Leave Balance</th>";
			items += "<th scope='col'>Sick Leave Balance</th>";
			//items += "<th scope='col'>&nbsp</th>";
			items += "</tr>";
			items += "</thead>";
			items += "<tbody>";
			
			//if (data.Records.length) {
			//	$.each(data.Records, function(i, item) {
			//		items += "<tr>";
			//		items += "<td>" + item.category + "</td>";
			//		items += "<td>" + item.count + "</td>";
			//		items += "<td>";				

					
			//		items += "</td>";
					
			//		items += "</tr>";
			//	});				
			//};
			
			items += "<tr>";
			items += "<td>" + data.Records.vBalanceINCL + "</td>";
			items += "<td>" + data.Records.sBalanceINCL + "</td>";
			items += "</tr>";
			
			items += "</tbody>";			
			items += "</table>";
			
			$('#leaveBalanceTable').html(items);

		},
		error : function(data) {
			alert('error: showLeaveBalanceTable' + data);
		}

	});
};

function showTrainingTable(empId) {	

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetEmployeeTrainingAction?empId=" + empId,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			if (data.Records.length) {
				var items = "<div class='headerDashboardTable'>Trainings &amp; Seminars</div>";
				items += "<table id='box-table-a' summary='Employee Pay Sheet' width='890px;'>";
				items += "<thead>";
				items += "<tr>";
				items += "<th scope='col'>Name of Event</th>";
				items += "<th scope='col'>Date From</th>";
				items += "<th scope='col'>Date To</th>";						
				items += "<th scope='col'>Provider</th>";
				items += "<th scope='col'>Status</th>";
				items += "</tr>";
				items += "</thead>";
				items += "<tbody>";
			
			
				$.each(data.Records, function(i, item) {
					items += "<tr>";
					items += "<td>" + item.titleActivity + "</td>";
					items += "<td>" + item.fromDate + "</td>";
					items += "<td>" + item.toDate + "</td>";
					items += "<td>" + item.provider + "</td>";	
															
					items += "<td>";  	
					
					if (item.status == "0") {							
						items +=  "FOR APPROVAL";	
					} else if (item.status == "1") {							
						items +=  "NOT APPROVED";	
					} else if (item.status == "2") {							
						items +=  "FOR UNIT SUPERVISOR APPROVAL";	
					} else if (item.status == "3") {							
						items +=  "FOR SECTION SUPERVISOR APPROVAL";	
					} else if (item.status == "4") {							
						items +=  "FOR HR APPROVAL";	
					} else if (item.status == "5") {							
						items +=  "FOR ADMIN APPROVAL";	
					} else {
						items +=  "APPROVED";	
					}		
					
					items += "</td>";
					
					
					items += "</tr>";
				});		
				
				items += "</tbody>";			
				items += "</table>";
				
				$('#trainingTable').html(items);
			}
			;
			
			

		},
		error : function(data) {
			alert('error: showTrainingTable' + data);
		}

	});
};

function showAttendanceTable(empId) {	

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetTimeEntryByEmpIdAction?empId=" + empId,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = "<table id='box-table-a' summary='Employee Pay Sheet' width='890px;'>";
			items += "<thead>";
			items += "<tr>";
			items += "<th scope='col'>Date</th>";
			items += "<th scope='col'>Schedule</th>";
			items += "<th scope='col'>Time In</th>";
			items += "<th scope='col'>Time Out</th>";			
			items += "<th scope='col'>&nbsp;</th>";
			items += "</tr>";
			items += "</thead>";
			items += "<tbody>";
			
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<tr class='resultItem'>";
					items += "<td>" + item.scheduleDate + "</td>";
					
					//alert(item.shiftScheduleDesc);
					if(item.shiftScheduleDesc){						
						items += "<td>" + item.shiftScheduleDesc + "</td>";
					} else {
						items += "<td>NO SCHEDULE</td>";
					}
					
					items += "<td>";
					
					if(item.shiftScheduleId == 2000){
						items +=  "Paid - Rest Day";
						items += "</td>";  
						items += "<td>";  		
						items +=  "Paid - Rest Day";
						items += "</td>";  
						items += "<td>"; 
						items += "&nbsp;";
					} else if(item.shiftScheduleId == 2001){
						items +=  "Unpaid - Rest Day";
						items += "</td>";  
						items += "<td>";  		
						items +=  "Unpaid - Rest Day";
						items += "</td>";  
						items += "<td>"; 
						items += "&nbsp;";
					} else {
						if (item.timeIn) {
							items += item.timeIn;						
						} else {
							items +=  "No Time In";
						}
						items += "</td>";  
						items += "<td>";  					
						if (item.timeOut) {
							items += item.timeOut;
						} else {					
							items +=  "No Time Out";
						}
						items += "</td>";  
						items += "<td>";  		
						
						if(item.shiftScheduleDesc){						
							if (item.timeIn) {
								if (item.timeOut) {		
									items += "&nbsp;";
								} else {
									items += "<a class='resolveButton' href='/hris/timeEntryDispute.jsp?empId=" + item.empId + "&shiftScheduleId=" + item.shiftScheduleId + "&timeEntryId=" + item.timeEntryId + "&clockDate=" + item.scheduleDate + "'>Dispute</a>";								
								}							
							} else {
								items += "<a class='resolveButton' href='/hris/timeEntryDispute.jsp?empId=" + item.empId + "&shiftScheduleId=" + item.shiftScheduleId + "&timeEntryId=" + item.timeEntryId + "&clockDate=" + item.scheduleDate + "'>Dispute</a>";
								
							}
						} else {
							//FOR NO SCHEDULE
							items += "<a class='resolveButton' href='/hris/empSchedChangeRequest.jsp?empId=" + item.empId + "&clockDate=" + item.scheduleDate + "'>Request Schedule</a>";
						}
						
						
					}
					
					
					items += "</td>";
					
					
					items += "</tr>";
				});				
			}
			;
			
			items += "</tbody>";			
			items += "</table>";
			
			//items += "<div class='pagination'>";
			//items += "<a href='#' class='first' data-action='first'>&laquo;</a>";
			//items += "<a href='#' class='previous' data-action='previous'>&lsaquo;</a>";
			//items += "<input type='text' readonly='readonly' />";
			//items += "<a href='#' class='next' data-action='next'>&rsaquo;</a>";
			//items += "<a href='#' class='last' data-action='last'>&raquo;</a>";
			//items += "</div>";
			
			$('#attendanceTable').html(items);

		},
		error : function(data) {
			alert('error: showAttendanceTable' + data);
		}

	});
};

function showAttendanceDisputeTable(empId) {	

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetTimeEntryDisputeByEmpIdAction?empId=" + empId,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			if (data.Records.length) {
				var items = "<div class='headerDashboardTable'>Attendance Dispute List</div>";
				items += "<table id='box-table-a' summary='Employee Pay Sheet' width='890px;'>";
				items += "<thead>";
				items += "<tr>";
				items += "<th scope='col'>Date</th>";
				items += "<th scope='col'>Schedule</th>";
				items += "<th scope='col'>Time In</th>";
				items += "<th scope='col'>Time Out</th>";			
				items += "<th scope='col'>&nbsp;</th>";
				items += "</tr>";
				items += "</thead>";
				items += "<tbody>";
			
			
				$.each(data.Records, function(i, item) {
					items += "<tr>";
					items += "<td>" + item.scheduleDate + "</td>";
					items += "<td>" + item.shiftScheduleDesc + "</td>";
					items += "<td>";
					
					if(item.shiftScheduleId == 2000){
						items +=  "Paid - Rest Day";
						items += "</td>";  
						items += "<td>";  		
						items +=  "Paid - Rest Day";
						items += "</td>";  
						items += "<td>"; 
						items += "&nbsp;";
					} else if(item.shiftScheduleId == 2001){
						items +=  "Unpaid - Rest Day";
						items += "</td>";  
						items += "<td>";  		
						items +=  "Unpaid - Rest Day";
						items += "</td>";  
						items += "<td>"; 
						items += "&nbsp;";
					} else {
						if (item.timeIn) {
							items += item.timeIn;						
						} else {
							items +=  "-- No Time In --";
						}
						items += "</td>";  
						items += "<td>";  					
						if (item.timeOut) {
							items += item.timeOut;
						} else {					
							items +=  "-- No Time Out --";
						}
						items += "</td>";  
						items += "<td>";  					
						
						
						//0 - FOR APPROVAL
		                //1 - NOT APPROVED
		                //2 - FOR UNIT SUPERVISOR APPROVAL
		                //3 - FOR SECTION SUPERVISOR APPROVAL
		                //4 - FOR HR APPROVAL
		                //5 - FOR ADMIN APPROVAL
						//6 - APPROVED
						
						if(item.approvalStatus == "0"){
							items += "FOR APPROVAL"
						}
						
						if(item.approvalStatus == "1"){
							items += "NOT APPROVED"
						}
						
						if(item.approvalStatus == "2"){
							items += "FOR UNIT SUPERVISOR APPROVAL"
						}
						
						if(item.approvalStatus == "3"){
							items += "FOR SECTION SUPERVISOR APPROVAL"
						}
						
						if(item.approvalStatus == "4"){
							items += "FOR HR APPROVAL"
						}
						
						if(item.approvalStatus == "5"){
							items += "FOR ADMIN APPROVAL"
						}
						
						if(item.approvalStatus == "6"){
							items += "APPROVED"
						}
						
						
					}
					
					
					items += "</td>";
					
					
					items += "</tr>";
				});		
				
				items += "</tbody>";			
				items += "</table>";
				
				$('#attendanceDisputeTable').html(items);
				
			}
			;
			
			

		},
		error : function(data) {
			alert('error: showAttendanceDisputeTable' + data);
		}

	});
};

function showScheduleChangeRequestTable(empId) {	

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetChangeRequestScheduleByEmpIdAction?empId=" + empId,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			if (data.Records.length) {
				var items = "<div class='headerDashboardTable'>Change Schedule Request List</div>";
				items += "<table id='box-table-a' summary='Employee Pay Sheet' width='890px;'>";
				items += "<thead>";
				items += "<tr>";				
				items += "<th scope='col'>Date</th>";
				items += "<th scope='col'>Current Schedule</th>";
				items += "<th scope='col'>Requested Schedule</th>";
				items += "<th scope='col'>Status</th>";
				items += "</tr>";
				items += "</thead>";
				items += "<tbody>";
			
			
				$.each(data.Records, function(i, item) {
					items += "<tr>";					
					items += "<td>" + item.clockDate + "</td>";
					items += "<td>" + item.oldScheduleDesc + "</td>";
					items += "<td>" + item.scheduleDesc + "</td>";
					
					items += "<td>";  	
					
					//0 - FOR APPROVAL
	                //1 - NOT APPROVED
	                //2 - FOR UNIT SUPERVISOR APPROVAL
	                //3 - FOR SECTION SUPERVISOR APPROVAL
	                //4 - FOR HR APPROVAL
	                //5 - FOR ADMIN APPROVAL
					//6 - APPROVED
					
					if(item.approvalStatus == "0"){
						items += "FOR APPROVAL"
					}
					
					if(item.approvalStatus == "1"){
						items += "NOT APPROVED"
					}
					
					if(item.approvalStatus == "2"){
						items += "FOR UNIT SUPERVISOR APPROVAL"
					}
					
					if(item.approvalStatus == "3"){
						items += "FOR SECTION SUPERVISOR APPROVAL"
					}
					
					if(item.approvalStatus == "4"){
						items += "FOR HR APPROVAL"
					}
					
					if(item.approvalStatus == "5"){
						items += "FOR ADMIN APPROVAL"
					}
					
					if(item.approvalStatus == "6"){
						items += "APPROVED"
					}
					
					items += "</td>";					
					
					
					
					items += "</tr>";
				});			
				
				items += "</tbody>";			
				items += "</table>";
				
				$('#changeScheduleEmpTable').html(items);
				
			}
			;
			
			

		},
		error : function(data) {
			alert('error showScheduleChangeRequestTable: ' + data);
		}

	});
};

function showScheduleChangeRequestOfStaffTable(empId) {	

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetChangeRequestScheduleBySupervisorIdAction?empId=" + empId,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			if (data.Records.length) {
				var items = "<div class='headerDashboardTable'>My Staff Change Schedule Request List</div>";
				items += "<table id='box-table-a' summary='Employee Pay Sheet' width='890px;'>";
				items += "<thead>";
				items += "<tr>";			
				items += "<th scope='col'>Name</th>";
				items += "<th scope='col'>Date</th>";
				items += "<th scope='col'>Current Schedule</th>";
				items += "<th scope='col'>Requested Schedule</th>";
				items += "<th scope='col'>Status</th>";
				items += "<th scope='col'>&nbsp;</th>";
				items += "</tr>";
				items += "</thead>";
				items += "<tbody>";
			
			
				$.each(data.Records, function(i, item) {
					items += "<tr>";			
					items += "<td>" + item.empName + "</td>";
					items += "<td>" + item.clockDate + "</td>";
					items += "<td>" + item.oldScheduleDesc + "</td>";
					items += "<td>" + item.scheduleDesc + "</td>";
					
					//0 - FOR APPROVAL
	                //1 - NOT APPROVED
	                //2 - FOR UNIT SUPERVISOR APPROVAL
	                //3 - FOR SECTION SUPERVISOR APPROVAL
	                //4 - FOR HR APPROVAL
	                //5 - FOR ADMIN APPROVAL
					//6 - APPROVED
					
					items += "<td>";  	
					
					if (item.approvalStatus == "0") {							
						items +=  "FOR APPROVAL";	
					} else if (item.approvalStatus == "1") {							
						items +=  "NOT APPROVED";	
					} else if (item.approvalStatus == "2") {							
						items +=  "FOR UNIT SUPERVISOR APPROVAL";	
					} else if (item.approvalStatus == "3") {							
						items +=  "FOR SECTION SUPERVISOR APPROVAL";	
					} else if (item.approvalStatus == "4") {							
						items +=  "FOR HR APPROVAL";	
					} else if (item.approvalStatus == "5") {							
						items +=  "FOR ADMIN APPROVAL";	
					} else {
						items +=  "APPROVED";	
					}		
					
					items += "</td>";		
					
					items += "<td>";  				
					if (item.approvalStatus != "6" && item.approvalStatus != "1") {										
						items +=  "<div class='resolveButton' onClick='approveChangeSchedule(" + item.empScheduleDisputeId + ");'>APPROVE REQUEST</div>";
					} else {
						items +=  "&nbsp;";	
					}					
					items += "</td>";		
					
					
					
					items += "</tr>";
				});			
				
				items += "</tbody>";			
				items += "</table>";
				
				$('#changeScheduleEmpTableSupervisor').html(items);
				
			}
			;
			
			

		},
		error : function(data) {
			alert('error showScheduleChangeRequestTable: ' + data);
		}

	});
};

function showAttendanceDisputeStaffTable(empId) {	

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetTimeEntryDisputeStaffByEmpIdAction?empId=" + empId,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			if (data.Records.length) {
				var items = "<div class='headerDashboardTable'>My Staff Time Entry Dispute List</div>";
				items += "<table id='box-table-a' summary='Employee Pay Sheet' width='890px;'>";
				items += "<thead>";
				items += "<tr>";
				items += "<th scope='col'>Name</th>";
				items += "<th scope='col'>Date</th>";
				items += "<th scope='col'>Schedule</th>";
				items += "<th scope='col'>Status</th>";
				items += "<th scope='col'>Time In</th>";
				items += "<th scope='col'>Time Out</th>";			
				items += "<th scope='col'>&nbsp;</th>";
				items += "</tr>";
				items += "</thead>";
				items += "<tbody>";
			
			
				$.each(data.Records, function(i, item) {
					items += "<tr>";
					items += "<td>" + item.empName + "</td>";
					items += "<td>" + item.scheduleDate + "</td>";
					items += "<td>" + item.shiftScheduleDesc + "</td>";
					
					//0 - FOR APPROVAL
	                //1 - NOT APPROVED
	                //2 - FOR UNIT SUPERVISOR APPROVAL
	                //3 - FOR SECTION SUPERVISOR APPROVAL
	                //4 - FOR HR APPROVAL
	                //5 - FOR ADMIN APPROVAL
					//6 - APPROVED
					
					items += "<td>";  	
					
					if (item.approvalStatus == "0") {							
						items +=  "FOR APPROVAL";	
					} else if (item.approvalStatus == "1") {							
						items +=  "NOT APPROVED";	
					} else if (item.approvalStatus == "2") {							
						items +=  "FOR UNIT SUPERVISOR APPROVAL";	
					} else if (item.approvalStatus == "3") {							
						items +=  "FOR SECTION SUPERVISOR APPROVAL";	
					} else if (item.approvalStatus == "4") {							
						items +=  "FOR HR APPROVAL";	
					} else if (item.approvalStatus == "5") {							
						items +=  "FOR ADMIN APPROVAL";	
					} else {
						items +=  "APPROVED";	
					}		
					
					items += "</td>";
					
					items += "<td>";
					
					if(item.shiftScheduleId == 2000){
						items +=  "Paid - Rest Day";
						items += "</td>";  
						items += "<td>";  		
						items +=  "Paid - Rest Day";
						items += "</td>";  
						items += "<td>"; 
						items += "&nbsp;";
					} else if(item.shiftScheduleId == 2001){
						items +=  "Unpaid - Rest Day";
						items += "</td>";  
						items += "<td>";  		
						items +=  "Unpaid - Rest Day";
						items += "</td>";  
						items += "<td>"; 
						items += "&nbsp;";
					} else {
						if (item.timeIn) {
							items += item.timeIn;						
						} else {
							items +=  "-- No Time In --";
						}
						items += "</td>";  
						items += "<td>";  					
						if (item.timeOut) {
							items += item.timeOut;
						} else {					
							items +=  "-- No Time Out --";
						}
						items += "</td>";  
						items += "<td>";  
						
						
						
						if (item.approvalStatus != "6" && item.approvalStatus != "1") {
							items +=  "<div class='resolveButton' onClick='approveTimeEntry(" + item.timeEntryDisputeId + ");'>APPROVE REQUEST</div>";							
						} else {
							items += "&nbsp;";								
						}
					}
					
					
					items += "</td>";
					
					
					items += "</tr>";
				});			
				
				items += "</tbody>";			
				items += "</table>";
				
				$('#attendanceDisputeStaffTable').html(items);
				
			}
			;
			
			

		},
		error : function(data) {
			alert('error showAttendanceDisputeStaffTable: ' + data);
		}

	});
};

function showAttendanceDisputeStaffForHRTable(empId) {	

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetTimeEntryDisputeHRAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = "<table id='box-table-a' summary='Employee Pay Sheet' width='890px;'>";
			items += "<thead>";
			items += "<tr>";
			items += "<th scope='col'>Name</th>";
			items += "<th scope='col'>Date</th>";
			items += "<th scope='col'>Schedule</th>";
			items += "<th scope='col'>Time In</th>";
			items += "<th scope='col'>Time Out</th>";			
			items += "<th scope='col'>&nbsp;</th>";
			items += "</tr>";
			items += "</thead>";
			items += "<tbody>";
			
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<tr>";
					items += "<td>" + item.empName + "</td>";
					items += "<td>" + item.scheduleDate + "</td>";
					items += "<td>" + item.shiftScheduleDesc + "</td>";
					items += "<td>";
					
					if(item.shiftScheduleId == 2000){
						items +=  "Paid - Rest Day-";
						items += "</td>";  
						items += "<td>";  		
						items +=  "Paid - Rest Day";
						items += "</td>";  
						items += "<td>"; 
						items += "&nbsp;";
					} else if(item.shiftScheduleId == 2001){
						items +=  "Unpaid - Rest Day";
						items += "</td>";  
						items += "<td>";  		
						items +=  "Unpaid - Rest Day";
						items += "</td>";  
						items += "<td>"; 
						items += "&nbsp;";
					} else {
						if (item.timeIn) {
							items += item.timeIn;						
						} else {
							items +=  "-- No Time In --";
						}
						items += "</td>";  
						items += "<td>";  					
						if (item.timeOut) {
							items += item.timeOut;
						} else {					
							items +=  "-- No Time Out --";
						}
						items += "</td>";  
						items += "<td>";  					
						if (item.approvalStatus == "P") {
							items +=  "<div class='resolveButton' onClick='approveTimeEntry(" + item.timeEntryDisputeId + ");'>APPROVE REQUEST</div>";							
						} else if (item.approvalStatus == "SA") {
							items +=  "<div class='resolveButton' onClick='approveTimeEntry(" + item.timeEntryDisputeId + ");'>APPROVE REQUEST</div>";						
						} else {
							items +=  "-- APPROVED --";	
						}
					}
					
					
					items += "</td>";
					
					
					items += "</tr>";
				});				
			}
			;
			
			items += "</tbody>";			
			items += "</table>";
			
			$('#attendanceDisputeStaffTable').html(items);

		},
		error : function(data) {
			alert('error showAttendanceDisputeStaffForHRTable: ' + data);
		}

	});
};


//common dropdown
function populatePersonnnelType() {
	var items = '<option value="">Select Personnel Type</option>';
	items += '<option value="RS">REGULAR STAFF</option>';
	items += '<option value="AS">ALLIED MEDICAL STAFF</option>';
	items += '<option value="DC">DOCTOR</option>';
	items += '<option value="NR">NURSE</option>';
	items += '<option value="OT">OTHERS</option>';
	$('#personnelTypeDropDownID').html(items);
};

function populatePlantillaDropDown() {
	var items = '<option value="">Select</option>';
	items += '<option value="P">IN PLANTILLA</option>';
	items += '<option value="NP">NOT IN PLANTILLA</option>';
	$('#plantillaDropDownID').html(items);
};

function populateDeductionDropDown() {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetAllDeductionAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="">Select Deduction</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.deductionId + "'>"
							+ item.deductionName + "</option>";
				});
				$('#deductionDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populateDeductionDropDown' + data);
		}

	});
};

function populateIncomeDropDown() {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetAllIncomeAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="">Select Income</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.incomeId + "'>"
							+ item.incomeName + "</option>";
				});
				$('#incomeDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populateIncomeDropDown' + data);
		}

	});
};


function populatePayrollPeriod() {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetPayrollPeriodAction?payrollPeriodStatus=G",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="">Select</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.payrollPeriodId + "'>"
							+ item.fromDate + " to " + item.toDate + "</option>";
				});
				$('#payrollPeriodDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populatePayrollPeriod' + data);
		}

	});
};

function populatePayrollPeriodLocked() {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetPayrollPeriodAction?payrollPeriodStatus=L",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="">Select</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.payrollPeriodId + "'>"
							+ item.fromDate + " to " + item.toDate + "</option>";
				});
				$('#payrollPeriodDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populatePayrollPeriod' + data);
		}

	});
};

function populateEmployeeCheckBoxes(superVisorId) {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetEmployeeListBySupervisorIdAction?superVisorId=" + superVisorId,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = "";
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<input type='checkbox' class='checkbox1' name='empId' value='" + item.empId + "'>"	+ item.firstname + ", " + item.lastname + "<br>"					
				});
				$('#scroller').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populateEmployeeCheckBoxes' + data);
		}

	});
};

function populateJobTitleDropDown() {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetAllJobAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="">Select Job Title</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.jobTitleId + "'>"
							+ item.jobTitle + "</option>";
				});
				$('#jobTitleDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populateJobTitleDropDown' + data);
		}

	});
};

function populateCityDropDown() {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetAllCityAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="">Select City</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.cityId + "'>"
							+ item.cityName + "</option>";
				});
				$('#cityDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populateCityDropDown' + data);
		}

	});
};

function populateShiftingScheduleDropDown() {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetAllShiftingSchedule",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="">Select Shift</option>';
			items += '<option value="2000">Paid - Rest Day</option>';
			//items += '<option value="2001">Unpaid - Rest Day</option>';
			items += '<option value="2003">Remove Employee Shift</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.shiftingScheduleId + "'>"
							+ item.description + "</option>";
				});
				
				$('#shiftingScheduleDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populateShiftingScheduleDropDown' + data);
		}

	});
};



function populateCountryDropDown() {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetAllCountryAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="">Select Country</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.countryId + "'>"
							+ item.countryName + "</option>";
				});
				$('#countryDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populateCountryDropDown' + data);
		}

	});
};

function populateSectionDropDown() {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetAllSectionAction?forListJTable=false&displayOption=false",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="">Select Section</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.sectionId + "'>"
							+ item.sectionName + "</option>";
				});
				$('#sectionDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populateSectionDropDown' + data);
		}

	});
};

function populateUnitDropDown() {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetAllUnitAction?forListJTable=false&displayOption=false",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="">Select Unit</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.unitId + "'>"
							+ item.unitName + "</option>";
				});
				$('#unitDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populateUnitDropDown' + data);
		}

	});
};

function populateSubUnitDropDown() {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetAllSubUnitAction?forListJTable=false&displayOption=false",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="">Select Sub Unit</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.subUnitId + "'>"
							+ item.subUnitName + "</option>";
				});
				$('#subUnitDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populateSubUnitDropDown' + data);
		}

	});
};


function populateDepartmentDropDown() {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetAllDepartmentAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="">Select Department</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.departmentId + "'>"
							+ item.departmentName + "</option>";
				});
				$('#departmentDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populateDepartmentDropDown' + data);
		}

	});
};

function populateDivisionDropDown() {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetAllDivisionAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="">Select Division</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.divisionId + "'>"
							+ item.divisionName + "</option>";
				});
				$('#divisionDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populateDivisionDropDown' + data);
		}

	});
};

function populateEmployeeTypeDropDown() {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetAllEmployeeTypeAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="">Select Employee Type</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.employeeTypeId + "'>"
							+ item.employeeTypeName + "</option>";
				});
				$('#employeeTypeDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populateEmployeeTypeDropDown' + data);
		}

	});
};

function populateEmployeeDropDown() {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetAllEmployeeAction?forListJTable=false&forDropDown=true",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="">Select Employee</option>';
			if (data.Options.length) {
				$.each(data.Options, function(i, item) {
					items += "<option value='" + item.Value + "'>"
							+ item.DisplayText + "</option>";
				});
				$('#employeeDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populateEmployeeDropDown' + data);
		}

	});
};

function populateLeaveTypeDropDown() {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetAllLeaveTypeAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="">Select Leave Type</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.leaveTypeId + "'>"
							+ item.leaveTypeId + "</option>";
				});
				$('#leaveTypeDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populateLeaveTypeDropDown' + data);
		}

	});
};

function populateLoanTypeDropDown() {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetAllLoanTypeAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="">Select Loan Type</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.loanTypeId + "'>"
							+ item.loanTypeName + "</option>";
				});
				$('#loanTypeDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populateLoanTypeDropDown' + data);
		}

	});
};

function populateProvinceDropDown() {

	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/GetAllProvinceAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="">Select Province</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.provinceId + "'>"
							+ item.provinceName + "</option>";
				});
				$('#provinceDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: populateProvinceDropDown' + data);
		}

	});
};



$(function() {
	$(".useDPicker").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : 'yy-mm-dd',
		yearRange : '1910:2100',
		beforeShow: function(input, inst){	            	    	
		   $(".ui-datepicker").css('font-size', 11);
		}
	});

});

$(function() {
	$("#tabs-nohdr").tabs();
});



/*
Copyright 2011, Colorsplash Media
JavaScript Name: common.js
Develop By: Ian Orozco
Date: May 31, 2011
*/

// ----------------------------------------------------------------- 
// Function Name      : initLoginPage 
// Function Purpose   : Removes the menus, toolbar, address bar,  and status bar from 
//						the login page and fits it to specified size
// Passed Parameters  : <none> 
// Retuned Parameters : <none> 
// Author             : Ian Orozco
// ----------------------------------------------------------------- 
function initLoginPage(x, y) {
	
	if(!window.legallyOpened)
	{
		var pageURL = "../lps/login.jsp";
		var w = x;
		var h = y;
		var left = (screen.width/2)-(w/2);
		var top = (screen.height/2)-(h/2);
		var winParameters = "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no,width="+w+",height="+h+",top="+top+",left="+left;
		
		// open new login window as popup
		var o = window.open(pageURL, "_blank", winParameters);
		o.legallyOpened = true;
		//dock(o);

		var ver = getInternetExplorerVersion();	
		
		// Close old login window
		if ( ver >= 7.0 )    {			
			window.open('', '_self', '');
			window.close();
		} else {			
			window.opener = top;
			window.close();
		}		
		
	}
}

// ----------------------------------------------------------------- 
// Function Name      : openNewPopUpWindowCloseParentWithScrollBar 
// Function Purpose   : Removes the menus, toolbar, address bar,  and status bar from 
//						the login page and fits it to specified size
// Passed Parameters  : URL, SCREEN WIDTH, SCREEN HEIGHT 
// Retuned Parameters : <none> 
// Author             : Ian Orozco
// ----------------------------------------------------------------
function openNewPopUpWindowCloseParentWithScrollBar(url, swidth, sheight){		
		
	var w = swidth;
	var h = sheight;
	var left = (screen.width/2)-(w/2);
	var top = (screen.height/2)-(h/2);		
		
	var winParameters = "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no,width="+w+",height="+h+",top="+top+",left="+left;
	var o = window.open(url, "_blank", winParameters);
	o.legallyOpened = true;
	
	var ver = getInternetExplorerVersion();	
	if ( ver >= 7.0 )    {			
		window.open('', '_self', '');
		window.close();
	} else {			
		window.opener = top;
		window.close();			
	}
}

// ----------------------------------------------------------------- 
// Function Name      : openNewPopUpWindowWithScrollBar 
// Function Purpose   : Removes the menus, toolbar, address bar,  and status bar from 
//						the login page and fits it to specified size
// Passed Parameters  : URL, SCREEN WIDTH, SCREEN HEIGHT 
// Retuned Parameters : <none> 
// Author             : Ian Orozco
// ----------------------------------------------------------------
function openNewPopUpWindowWithScrollBar(url, swidth, sheight){		
		
	var w = swidth;
	var h = sheight;
	var left = (screen.width/2)-(w/2);
	var top = (screen.height/2)-(h/2);		
		
	var winParameters = "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no,width="+w+",height="+h+",top="+top+",left="+left;
	var o = window.open(url, "_blank", winParameters);
	o.legallyOpened = true;
	
	var ver = getInternetExplorerVersion();	
	if ( ver >= 7.0 )    {			
		window.open('', '_self', '');
	} else {			
		window.opener = top;
	}
}

// ----------------------------------------------------------------- 
// Function Name      : openNewPopUpWindowCloseParent 
// Function Purpose   : Removes the menus, toolbar, address bar,  and status bar from 
//						the login page and fits it to specified size
// Passed Parameters  : URL, SCREEN WIDTH, SCREEN HEIGHT 
// Retuned Parameters : <none> 
// Author             : Ian Orozco
// ----------------------------------------------------------------	
function openNewPopUpWindowCloseParent(url, swidth, sheight){		
		
	var w = swidth;
	var h = sheight;
	var left = (screen.width/2)-(w/2);
	var top = (screen.height/2)-(h/2);		
		
	var winParameters = "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no,width="+w+",height="+h+",top="+top+",left="+left;
	var o = window.open(url, "_blank", winParameters);
	o.legallyOpened = true;
	
	var ver = getInternetExplorerVersion();	
	if ( ver >= 7.0 )    {			
		window.open('', '_self', '');
		window.close();		
	} else {			
		window.opener = top;
		window.close();					
	}

}

// ----------------------------------------------------------------- 
// Function Name      : openNewPopUpWindow 
// Function Purpose   : Removes the menus, toolbar, address bar,  and status bar from 
//						the login page and fits it to specified size
// Passed Parameters  : URL, SCREEN WIDTH, SCREEN HEIGHT 
// Retuned Parameters : <none> 
// Author             : Ian Orozco
// ----------------------------------------------------------------	
function openNewPopUpWindow(url, swidth, sheight){		
		
	var w = swidth;
	var h = sheight;
	var left = (screen.width/2)-(w/2);
	var top = (screen.height/2)-(h/2);		
		
	var winParameters = "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no,fullscreen=true,width="+w+",height="+h+",top="+top+",left="+left;
	var o = window.open(url, "_blank", winParameters);
	o.legallyOpened = true;
	
	
	var ver = getInternetExplorerVersion();	
	if ( ver >= 7.0 )    {			
		window.open('', '_self', '');
	} else {			
		window.opener = top;			
	}

}

//----------------------------------------------------------------- 
//Function Name      : openNewPopUpWindow 
//Function Purpose   : Removes the menus, toolbar, address bar,  and status bar from 
//						the login page and fits it to specified size
//Passed Parameters  : URL, SCREEN WIDTH, SCREEN HEIGHT 
//Retuned Parameters : <none> 
//Author             : Ian Orozco
//----------------------------------------------------------------	
function openNewPopUpWindowSelf(url, swidth, sheight){		
		
	var w = swidth;
	var h = sheight;
	var left = (screen.width/2)-(w/2);
	var top = (screen.height/2)-(h/2);		
		
	var winParameters = "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no,fullscreen=true,width="+w+",height="+h+",top="+top+",left="+left;
	var o = window.open(url, "_self", winParameters);
	o.legallyOpened = true;
	
	
	var ver = getInternetExplorerVersion();	
	if ( ver >= 7.0 )    {			
		window.open('', '_self', '');
	} else {			
		window.opener = top;			
	}

}

//-----------------------------------------------------------------
//Function Name      : getInternetExplorerVersion
//Function Purpose   : Returns the version of Internet Explorer or a -1
//                   (indicating the use of another browser).
//Passed Parameters  : The Select element id, the option id, and the input id
//Retuned Parameters : <none> 
//Author             : Ian Orozco
//-----------------------------------------------------------------
function getInternetExplorerVersion()

{
	var rv = -1; // Return value assumes failure.
	if (navigator.appName == 'Microsoft Internet Explorer')
	{
		var ua = navigator.userAgent;
		var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
		if (re.exec(ua) != null)
			rv = parseFloat( RegExp.$1 );
	}
	return rv;
}
