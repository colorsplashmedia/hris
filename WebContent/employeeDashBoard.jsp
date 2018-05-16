<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Employee Dashboard</title>
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/styleTables.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<script type="text/javascript" src="js/common.js"></script>
<script src="js/employee.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.0.min.js"></script>
<script src="js/jquery-1.10.2.js"></script>
<!-- Pagination -->
<link rel="stylesheet" type="text/css" href="css/easyui.css">
<link rel="stylesheet" type="text/css" href="css/icon.css">
<link rel="stylesheet" href="css/reset.css" />
<link rel="stylesheet" href="css/jqpagination.css" />

<!-- scripts Pagination -->
<script src="js/jquery.jqpagination.js"></script>


<script type="text/javascript">
$(document).ready(function() {
	
	var empId = '${employeeLoggedIn.empId}';	
	
	if(empId.length == 0){
		alert("You are not allowed to view the page. Please login.");
		window.location = "LogoutAction";
		return;
	}
	
	var empId = '${employeeLoggedIn.empId}'; 
	var isApprover = '${isApprover}'; 
	
	showMemoTable(empId);
	showLeaveTable(empId);
	showLeaveBalanceTable(empId);
	showTrainingTable(empId);
	showNotificationTable(empId);
	showAttendanceTable(empId);
	showAttendanceDisputeTable(empId);
	showOvertimeTable(empId);
	showOffSetTable(empId);
	showUndertimeTable(empId);
	getEmployeeInfoDashboard(empId);	
	showScheduleChangeRequestTable(empId);
	
	if(isApprover == "YES") {
		showAttendanceDisputeStaffTable(empId);
		showScheduleChangeRequestOfStaffTable(empId);	
	}	
	
	var items = $(".resultItem");

    var numItems = items.length;
    var perPage = 7;
    
    var numPages = numItems;
    var remainingItems = 0;
    
    if(numItems < perPage){
    	numPages = 1;
    } else {    	
    	remainingItems = numItems % perPage;
    	
    	if(remainingItems > 0){
    		numPages = ((numItems - remainingItems) / perPage) + 1;	
    	} else {
    		numPages = (numItems - remainingItems) / perPage;
    	}
    	
    }    
    
    // only show the first 2 (or "first per_page") items initially
    items.slice(perPage).hide();

    // now setup your pagination
    // you need that .pagination-page div before/after your table
   $('.pagination').jqPagination({        
		max_page	: numPages,
		paged		: function(page) {			
			var showFrom = perPage * (page - 1);
            var showTo = showFrom + perPage;

            items.hide() // first hide everything, then show for the new page
            .slice(showFrom, showTo).show();
		}        
    });
    
    function showMessage(message){
    	alert(message);
    }

});

function approveTimeEntry(timeEntryDisputeId) {
	if (confirm('Are you sure you want to approve the time entry?')) {
		$.ajax({
			type:"POST",
			url:"/hris/UpdateTimeEntryDisputeStatusAction?timeEntryDisputeId="+timeEntryDisputeId,			
			async: true,
			dataType: 'json',
			success: function (data) {				
				if(data.Record == "YES") {
					alert("Time Entry Dispute has been approved.");
					//location.reload();
					window.location = "employeeDashBoard.jsp";
				}							
			},
			error: function (data) {alert('error: '+data)}
		});
	}		
}

function approveChangeSchedule(empScheduleDisputeId) {
	if (confirm('Are you sure you want to approve the Change of Schedule Request?')) {
		$.ajax({
			type:"POST",
			url:"/hris/ApproveChangeScheduleRequestAction?empScheduleDisputeId="+empScheduleDisputeId,			
			async: true,
			dataType: 'json',
			success: function (data) {				
				if(data.Record == "YES") {
					alert("Schedule Change Request has been approved.");
					//location.reload();
					window.location = "employeeDashBoard.jsp";
				}							
			},
			error: function (data) {alert('error: '+data)}
		});
	}		
}



</script>
</head>
<body>
<div><c:import url="header.jsp" /></div>
<div>		
	<div id="content">
		<!-- Left Side of Dashboard -->
		<div id="dashBoardLeftPannel">
			<div style="width: 100%">
				<div style="width: 45%; float:left; border: 0px solid black; margin-left: 20px;">
				   <div><img id="empImgHolder" src="images/noimage_2.gif" alt="no image" onerror="changeImage();" width="100%" height="auto" /></div>
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
							
				<div class="cb" style="height: 20px;"></div>
							
				<div class="dashBoardButton"><a href="employeeLeaveEntry.jsp?empId=${employeeLoggedIn.empId}">Request Time Off</a></div>
				<div class="cb"></div>
				<div class="dashBoardButton"><a href="empSchedChangeRequest.jsp?empId=${employeeLoggedIn.empId}">Request Change Shift</a></div>
				<div class="cb"></div>
				<div class="dashBoardButton"><a href="employeeOvertimeEntry.jsp?empId=${employeeLoggedIn.empId}">File Overtime</a></div>
				<div class="cb"></div>
				<div class="dashBoardButton"><a href="employeeUndertimeEntry.jsp?empId=${employeeLoggedIn.empId}">File Undertime</a></div>
				<div class="cb"></div>
				<div class="dashBoardButton"><a href="employeeOffSetEntry.jsp?empId=${employeeLoggedIn.empId}">File OffSet</a></div>		
				<div class="cb"></div>		
				<div class="dashBoardButton"><a href="employeeOutOfOfficeEntry.jsp?empId=${employeeLoggedIn.empId}">File Training &amp; Seminar</a></div>
				<div class="cb"></div>
				<div class="dashBoardButton"><a href="hourlyAttendance.jsp">File Hourly Attendance</a></div>				
			</div>
			
		</div>
		<!-- Right Side of Dashboard -->
		<div id="dashBoardRightPannel">
			<!-- Notifications -->
			<div>
				
				<div style="float: left;">						
					<div id="leaveTable"></div>							
				</div>
				
				<div class="cb"></div>
				<div style="float: left;">
					<div class="headerDashboardTable" style="float: left;">Employee Attendance List</div>	
					<div class="pagination" style="float: left; margin-top: 20px; margin-left: 30px;">
			    		<a href="#" class="first" data-action="first">&laquo;</a>
			    		<a href="#" class="previous" data-action="previous">&lsaquo;</a>
			    		<input type="text" readonly="readonly" />
			    		<a href="#" class="next" data-action="next">&rsaquo;</a>
			    		<a href="#" class="last" data-action="last">&raquo;</a>
					</div>	
					<div id="attendanceTable"></div>		
					
										
				</div>
				
				<div class="cb"></div>
				<div style="float: left;">
					<div class="headerDashboardTable">Leave Balance</div>		
					<div id="leaveBalanceTable"></div>							
				</div>
				
				<div class="cb"></div>
				<div style="float: left;">							
					<div id="attendanceDisputeTable"></div>							
				</div>
				<div class="cb"></div>
				<div style="float: left;">						
					<div id="attendanceDisputeStaffTable"></div>							
				</div>
				<div class="cb"></div>
				<div style="float: left;">						
					<div id="changeScheduleEmpTable"></div>							
				</div>
				<div class="cb"></div>
				<div style="float: left;">						
					<div id="changeScheduleEmpTableSupervisor"></div>							
				</div>
				<div class="cb"></div>
				<div style="float: left;">					
					<div id="trainingTable"></div>						
				</div>
				<div class="cb"></div>
				<div style="float: left;">							
					<div id="overtimeTable"></div>							
				</div>
				<div class="cb"></div>
				<div style="float: left;">							
					<div id="offSetTable"></div>							
				</div>
				<div class="cb"></div>
				<div style="float: left;">							
					<div id="undertimeTable"></div>							
				</div>
				<div class="cb"></div>
				<div style="float: left;">						
					<div id="memoTable"></div>					
				</div>
				<div class="cb"></div>
				<div style="float: left;">					
					<div id="notificationTable"></div>					
				</div>
				<div class="cb"></div>
				
				
			</div>
		</div>
		<div class="cb" style="height: 50px;"></div>
	</div>
</div>	

<div style=""><c:import url="footer.jsp" /></div>
</body>
</html>
