<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Employee Leaves for HR Approval</title>
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
    		listAction: '/hris/GetAllLeavesHRApprovalAction'//,           	    
			//updateAction: '/hris/UpdateLeaveHRApprovalAction?superVisorId=99999' 
    	};    	
    	var toolBarsJTableVar = { 	
    			
    	};  
    	
    	//var superVisorId = '${sessionScope.employeeLoggedIn.empId}'; //this is the employeeid of the supervisor
        $('#containerLeaveReviewAndApproval').jtable({
            title: 'Employee Leaves Requiring My Approval',
            paging: true,
            pageSize: 10,
            sorting: true,
            defaultSorting: 'dateFrom DESC',
            actions: actionsJTableVar,
            toolbar: toolBarsJTableVar,
            formCreated : function(event, data){

            	
            	$(data.form).parent().parent().css("top","155");   				//to make the form popup when adding/updating visible
            	data.form.find('select').css('width','200px');
	            data.form.find('textarea').css('width','200px');
            	
            	//for dates: we need to find the from/to manually
                var $dtfrom = data.form.find ('input[name="dateFrom"]');     
                var $dtTo = data.form.find ('input[name="dateTo"]');
                
            	

                
              //manually activate the datepicker, and supply the params from useDPicker (you need to do this so that when date is selected, the date format is followed.)
              //and you need to add val in order to display the value saved in the DB in the proper format, if not, it will become Mon dd, yyyy which will fail.
				if (data.formType == 'edit'){
	                $dtfrom.datepicker({
	            		changeMonth : true,
	            		changeYear : true,
	            		dateFormat : 'yy-mm-dd',
	            		yearRange : '1910:2100'
	            	}).val(moment(data.record.dateFrom).format('YYYY-MM-DD'));		
					// 

	                $dtTo.datepicker({
	            		changeMonth : true,
	            		changeYear : true,
	            		dateFormat : 'yy-mm-dd',
	            		yearRange : '1910:2100'
	            	}).val(moment(data.record.dateTo).format('YYYY-MM-DD'));
					
					//need to add the 2 below for the hidden attibutes so we can pass back to the servlet the values during update
					var $dtCreatedDate = data.form.find ('input[name="createdDate"]');
					var $dtDateFiled = data.form.find ('input[name="dateFiled"]');    
	                $dtCreatedDate.val(moment(data.record.createdDate).format('YYYY-MM-DD'));	                
	                $dtDateFiled.val(moment(data.record.dateFiled).format('YYYY-MM-DD'));
				}
				
				//call these datepickers below when doing create entry, since we do not need to display any dateFrom or dateTo
                $dtfrom.datepicker({
            		changeMonth : true,
            		changeYear : true,
            		dateFormat : 'yy-mm-dd',
            		yearRange : '1910:2100'
            	});		

                $dtTo.datepicker({
            		changeMonth : true,
            		changeYear : true,
            		dateFormat : 'yy-mm-dd',
            		yearRange : '1910:2100'
            	});				
                
                data.form.validationEngine();

            },
            fields: {
            	leaveId: {
                    key: true,
                    list: false
                },
                
            	empId: {
            		title: 'Employee ID',
                    key: true,
                    list: true,
                    options:  
	                   	function(data) {
			                        if (data.source == 'list') {
			                            return '/hris/GetAllEmployeeAction?displayOption=true';
			                        }
                    }  
                },
                
               //need to pass for update and for beanutils
                dateFiled: {
                	type:'hidden',
                    list:false
                },
               
                
                dateFrom: {
                    title: 'From',
                    //type: 'date',  //comment-out the type date
                    edit: false,
                    display:
                      function (data) {
                    	if (data.record.dateTo != undefined){				//need to have a condition for undefined since moment will display default date.
                          return moment(data.record.dateFrom).format('YYYY-MM-DD');
                    	}
                    	return 'N/A';
                      }
                },
                
                dateTo: {
                    title: 'To',
                    //type: 'date',   //comment-out the type date
                    edit: false,
                    display:
                    	function (data) {
                    			if (data.record.dateTo != undefined){      //need to have a condition for undefined since moment will display default date.
                        			return moment(data.record.dateTo).format('YYYY-MM-DD');
                    			}
                    			return 'N/A';
                   		 }
                },
                
                noDays: {
                    title: '# of Days',
                    edit : false 
                },
                
                
                status: {
                	title: 'Status',
                	list: true,
                	edit: true,
                	options:  [{ Value: '0', DisplayText: 'FOR APPROVAL' }, { Value: '1', DisplayText: 'NOT APPROVED' }, { Value: '2', DisplayText: 'SUPERVISOR APPROVED' }, { Value: '3', DisplayText: 'APPROVED' }]
                },
                
                remarks: {
                    title: 'Remarks',
                    type: 'textarea',
                    //width: '30%',
                    edit : true,
                    list: false
                },
            
                approvedBy: {
                    title: 'Supervisor',
                    edit: false,
                    list: true,
                    options:  
	                   	function(data) {
			                        if (data.source == 'list') {
			                            return '/hris/GetAllEmployeeAction?displayOption=true';
			                        }
			                        //if (data.source == 'create') {
			                        //	return '/hris/GetEmployeeAction?displayOption=true&empId='+superVisorId;
			                            //return '/hris/GetAllEmployeeAction?displayOption=true';
			                        //}
			                        //if (data.source == 'edit') {
			                        //	return '/hris/GetEmployeeAction?displayOption=true&empId='+superVisorId;
			                        	//return '/hris/GetAllEmployeeAction?displayOption=true';
			                    	//}
                    }                   
                
                    
                },need2Approvers: {
                    key: true,
                    list: false,
                    edit: false
                    
                },leaveTypeId: {
                	title: 'Leave Type',
                    edit: false,
                    list: true,
                    options:  
	                   	function(data) {       //Readme in GetLeaveTypeAction
			                        if (data.source == 'list') {
			                            //Return url all options for optimization. 
			                            return '/hris/GetAllLeaveTypeAction?displayOption=true';
			                        }
			                        if (data.source == 'create') {
			                            //Return url all options for optimization. 
			                            return '/hris/GetAllLeaveTypeAction?displayOption=true';
			                        }
			                        if (data.source == 'edit') {
			                            //Return url all options for optimization. 
			                        	//return '/hris/GetLeaveTypeAction?displayOption=true&leaveTypeId='+data.record.leaveTypeId;
			                        	 return '/hris/GetAllLeaveTypeAction?displayOption=true';
			                    	}
                    }
                    
                },Button: {
                	type: 'button',
                	list: true,
                	edit: true,
                	display: function(data) {
                         return '<div style="width: 170px;"><button type="button" style="margin-left: 7px;" onclick="approveLeaveHR(' + data.record.leaveId + ',' + data.record.status +')"> Approve</button><button type="button" style="margin-left: 7px;" onclick="disapproveLeaveHR(' + data.record.leaveId + ',' + data.record.status +')"> Disapprove</button></div> ';
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
            //Validate form when it is being submitted
            formSubmitting: function (event, data) {
            	data.form.find('input[name="dateFrom"]').addClass('validate[required]');
            	data.form.find('input[name="dateTo"]').addClass('validate[required]');
            	data.form.find('input[name="noDays"]').addClass('validate[required]');
            	data.form.find('textarea[name="remarks"]').addClass('validate[required]');
            	
                return data.form.validationEngine('validate');
            },
            //Dispose validation logic when form is closed
            formClosed: function (event, data) {
                data.form.validationEngine('hide');
                data.form.validationEngine('detach');
            }
        });
        $('#containerLeaveReviewAndApproval').jtable('load');
        
    });
    
	    function approveLeaveHR(leaveId, status){
			
			//alert("leaveId: " + leaveId);
			//alert("status: " + status);
			
			if(status == 0 || status == 2){
				if (confirm('Are you sure you want to approve the Leave Entry?')) {
					$.ajax({
						type:"POST",
						url:"/hris/UpdateLeaveHRApprovalAction?leaveId="+leaveId+"&status="+ 3,				
						async: true,
						dataType: 'json',
						success: function (data) {				
							if(data.Record == "YES") {
								alert("Leave Entry has been approved.");
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
	    
		function disapproveLeaveHR(leaveId, status){
			
			//alert("leaveId: " + leaveId);
			//alert("status: " + status);
			
			if(status == 0 || status == 2){
				if (confirm('Are you sure you want to disapprove the Leave Entry?')) {
					$.ajax({
						type:"POST",
						url:"/hris/UpdateLeaveHRApprovalAction?leaveId="+leaveId+"&status="+ 1,				
						async: true,
						dataType: 'json',
						success: function (data) {				
							if(data.Record == "YES") {
								alert("Leave Entry has been disapproved.");
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
	<div id="containerLeaveReviewAndApproval" class="jTableContainerDaiExtendedLong"></div>		
</div>	
<div><c:import url="footer.jsp" /></div>
</body>
</html>