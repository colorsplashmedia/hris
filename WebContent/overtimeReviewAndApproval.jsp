<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Employee Overtime Approval</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@include file="commonHeader.jsp" %>
<%@include file="commonJtables.jsp" %>

<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />
<link href="js/validationEngine/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/validationEngine/jquery.validationEngine.js"></script>
<script type="text/javascript" src="js/validationEngine/jquery.validationEngine-en.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/moment.min.js"></script>	

<script type="text/javascript">

    $(document).ready(function () {
    	
		var superVisorId = '${employeeLoggedIn.empId}';	
    	
    	if(superVisorId.length == 0){
    		alert("You are not allowed to view the page. Please login.");
    		window.location = "LogoutAction";
    		return;
    	}
    	
    	
    	var addButtonEnabled = 'YES';
    	var editButtonEnabled = 'YES';
    	var exportButtonEnabled = 'YES';
    	var printButtonEnabled = 'YES';
    	var actionsJTableVar = {    			
    		listAction: '/hris/GetAllOvertimeSvApprovalAction?superVisorId='+superVisorId//,                
    		//updateAction: '/hris/UpdateOvertimeSvApprovalAction?superVisorId='+superVisorId
    	};    	
    	var toolBarsJTableVar = { 	
    			
    	};  
    	
    	
    	//var superVisorId = '${sessionScope.employeeLoggedIn.empId}'; //this is the employeeid of the supervisor

        $('#containerEmployeeOvertimeEntry').jtable({
            title: 'Employee Overtime Approval',
            paging: true,
            pageSize: 10,
            sorting: true,
            defaultSorting: 'dateRendered DESC',
            actions: actionsJTableVar,
            toolbar: toolBarsJTableVar,
            formCreated : function(event, data){
            	
				$(data.form).addClass("custom_horizontal_form_field");
				
				
	        	
	        	var $dialogDiv = data.form.closest('.ui-dialog');
	            $dialogDiv.position({
	                my: "center",
	                at: "center",
	                of: $("#containerEmployeeOvertimeEntry")
	            });
            	
            	
				$(data.form).parent().parent().css("top","155");   				//to make the form popup when adding/updating visible
            	
				 data.form.find('input').css('width','200px');
		         data.form.find('textarea').css('width','200px');
		         data.form.find('select').css('width','200px');
		            
            	//for dates: we need to find the from/to manually
                var $dtfrom = data.form.find ('input[name="dateRendered"]');
            	
            	//hack since twitter bootstrap conflicts with jtable
            	//but still have some issues with the size of the save and cancel buttons
                var $dtButton = $(data.form).parent().parent().find ('button[type="button"]');
                $dtButton.each(function(index) {
                	if (index==0){
                		$(this).addClass("ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close");
                	} else {
                		$(this).addClass("ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
                	}
                })
				//$dtButton.first().addClass("ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close");
				//$dtButton.next().addClass("ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
              //manually activate the datepicker, and supply the params from useDPicker (you need to do this so that when date is selected, the date format is followed.)
              //and you need to add val in order to display the value saved in the DB in the proper format, if not, it will become Mon dd, yyyy which will fail.
				if (data.formType == 'edit'){
	                $dtfrom.val(moment(data.record.dateFrom).format('YYYY-MM-DD HH:MM'));		
										
					//need to add the 2 below for the hidden attibutes so we can pass back to the servlet the values during update
					var $dtCreatedDate = data.form.find ('input[name="createdDate"]');
	                $dtCreatedDate.val(moment(data.record.createdDate).format('YYYY-MM-DD HH:MM'));	                

				}
				
				//call these datepickers below when doing create entry, since we do not need to display any dateFrom or dateTo
                // $dtfrom.datepicker({
            	//	changeMonth : true,
            	//	changeYear : true,
            	//	dateFormat : 'yy-mm-dd',
            	//	yearRange : '1910:2100',
            	//    beforeShow: function(input, inst){	            	    	
            	//           $(".ui-datepicker").css('font-size', 11);
            	//    }
            	//});	

				
				$dtfrom.parent().addClass("input-append date");
				$dtfrom.parent().append("<span class='add-on'><i data-time-icon='icon-time' data-date-icon='icon-calendar'></i></span>");
				$dtfrom.parent().datetimepicker({
			        format: 'yyyy-MM-dd hh:mm',
					pick12HourFormat: false,
			        language: 'en'
				});
				
            	


               
				data.form.validationEngine();

            },
            fields: {
            	empOvertimeId: {
                    key: true,
                    list: false
                },
                
            	empId: {
            		title: 'Employee Name',
                    edit: false,
                    list: true,
                    options:  
	                   	function(data) {       //Readme in GetLeaveTypeAction
	                    	if (data.source == 'list') {
			                	return '/hris/GetAllEmployeeAction?forListJTable=true&empId='+data.record.empId;
								//return '/hris/GetEmployeeAction?displayOption=true&empId='+data.record.empId;
							}
			                if (data.source == 'create') {
			                	return '/hris/GetAllEmployeeAction?displayOption=true&forEdit=false';
			                }
			                if (data.source == 'edit') {
			                	return '/hris/GetAllEmployeeAction?displayOption=true&forEdit=true&empId='+data.record.empId;
							}
                    	},                    	
                    create:false
                },
                
         
                
                dateRendered: {
                    title: 'Rendered Date',
                    //type: 'date',  //comment-out the type date
                    edit: true,
                    create: true,
                    //can comment display if data type for date used is varchar
                    display:
                      function (data) {
                    	if (data.record.dateRendered != undefined){				//need to have a condition for undefined since moment will display default date.
                         return moment(data.record.dateRendered).format('YYYY-MM-DD HH:mm');
                    	}
                    	return 'N/A';
                      }
                },
                
               
                noOfHours: {
                    title: '# of Hours',
                    create: true,
                    edit : true 
                },
                
                remarks: {
                    title: 'Remarks',
                    type: 'textarea',
                    //width: '30%',
                    create: true,
                    edit : true 
                },

                
                //0 - FOR APPROVAL
                //1 - NOT APPROVED
                //2 - FOR UNIT SUPERVISOR APPROVAL
                //3 - FOR SECTION SUPERVISOR APPROVAL
                //4 - FOR HR APPROVAL
                //5 - FOR ADMIN APPROVAL
				//6 - APPROVED
                status: {
                	title: 'Status',
                	list: true,
                	edit: false,
                	options:  [{ Value: '0', DisplayText: 'FOR APPROVAL' }, 
                	           { Value: '1', DisplayText: 'NOT APPROVED' }, 
                	           { Value: '2', DisplayText: 'FOR UNIT SUPERVISOR APPROVAL' }, 
                	           { Value: '3', DisplayText: 'FOR SECTION SUPERVISOR APPROVAL' }, 
                	           { Value: '4', DisplayText: 'FOR HR APPROVAL' }, 
                	           { Value: '5', DisplayText: 'FOR ADMIN APPROVAL' },
                	           { Value: '6', DisplayText: 'APPROVED' } ]
                },    
            
                approvedBy: {
                    title: 'Approved By',
                    edit: false,
                    list: true,
                    options:  
	                   	function(data) {       //Readme in GetLeaveTypeAction
	                    	if (data.source == 'list') {
			                	return '/hris/GetAllEmployeeAction?forListJTable=true&empId='+data.record.empId;
								//return '/hris/GetEmployeeAction?displayOption=true&empId='+data.record.empId;
							}
			                if (data.source == 'create') {
			                	return '/hris/GetAllEmployeeAction?displayOption=true&forEdit=false';
			                }
			                if (data.source == 'edit') {
			                	return '/hris/GetAllEmployeeAction?displayOption=true&forEdit=true&empId='+data.record.empId;
							}
                    	},                    	
                    create:false                    
                },Button: {
                	type: 'button',
                	list: true,
                	edit: true,
                	display: function(data) {
                         return '<div style="width: 170px;"><button type="button" style="margin-left: 7px;" onclick="approveOvertime(' + data.record.empOvertimeId +')"> Approve</button> <button type="button" style="margin-left: 7px;" onclick="disapproveOvertime(' + data.record.empOvertimeId + ')"> Disapprove</button></div> ';
                    } 
                    
                    
                },createdBy: {
                    key: true,
                    list: false 
                
                //need to pass for update and for beanutils
                },createdDate: {
                	type:'hidden',
                    list: false
                }                
            },
            formSubmitting: function (event, data) {
            	data.form.find('input[name="dateRendered"]').addClass('validate[required]');
            	data.form.find('input[name="noOfHours"]').addClass('validate[required]');
            	data.form.find('textarea[name="remarks"]').addClass('validate[required]');
            	
                return data.form.validationEngine('validate');
            },
            //Dispose validation logic when form is closed
            formClosed: function (event, data) {
                data.form.validationEngine('hide');
                data.form.validationEngine('detach');
            }
        });
        $('#containerEmployeeOvertimeEntry').jtable('load');
        
    });
    
		 function approveOvertime(empOvertimeId){
				
				//alert("leaveId: " + leaveId);
				//alert("status: " + status);
				
				if(status == 0){
					
					if (confirm('Are you sure you want to approve the Overtime Entry?')) {
						$.ajax({
							type:"POST",
							url:"/hris/UpdateOvertimeSvApprovalAction?empOvertimeId="+empOvertimeId+"&approveFlag=Y",			
							async: true,
							dataType: 'json',
							success: function (data) {				
								if(data.Record == "YES") {
									alert("Overtime Entry has been approved.");
									location.reload();
								}							
							},
							error: function (data) {alert('error: '+data)}
						});
					}	
				}else {
					
					alert ("This transaction is not allowed.");
				}
				
				
		  }
		 
		 function disapproveOvertime(empOvertimeId){
				
				//alert("leaveId: " + leaveId);
				//alert("status: " + status);
				
				if(status == 0){
					
					if (confirm('Are you sure you want to disapprove the Overtime Entry?')) {
						$.ajax({
							type:"POST",
							url:"/hris/UpdateOvertimeSvApprovalAction?empOvertimeId="+empOvertimeId+"&approveFlag=N",			
							async: true,
							dataType: 'json',
							success: function (data) {				
								if(data.Record == "YES") {
									alert("Overtime Entry has been disapproved.");
									location.reload();
								}							
							},
							error: function (data) {alert('error: '+data)}
						});
					}	
				}else {
					
					alert ("This transaction is not allowed.");
				}
				
				
		  }

</script>

</head>
<body>
<div><c:import url="header.jsp" /></div>
<div id="content">
<div class="cb" style="height: 20px;"></div>
	<div id="containerEmployeeOvertimeEntry" class="jTableContainerDaiExtended"></div>		
</div>	
<div><c:import url="footer.jsp" /></div>
</body>
</html>