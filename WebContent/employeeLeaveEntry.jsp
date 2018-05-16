<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Leave Entry</title>
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
		var empId = '${employeeLoggedIn.empId}';	
    	
    	if(empId.length == 0){
    		alert("You are not allowed to view the page. Please login.");
    		window.location = "LogoutAction";
    		return;
    	}
    	
    	
    	var addButtonEnabled = 'YES';
    	var editButtonEnabled = 'YES';
    	var exportButtonEnabled = 'YES';
    	var printButtonEnabled = 'YES';
    	var actionsJTableVar = {    			
    		listAction: '/hris/GetAllLeaveAction?empId='+empId,                
    	    createAction: '/hris/SaveLeaveAction?empId='+empId,
			updateAction: '/hris/UpdateLeaveAction' 
    	};    	
    	var toolBarsJTableVar = { 	
    			
    	};  	
    	
    	
    	
    	
        $('#containerEmployeeLeaveEntry').jtable({
            title: 'My Filed Leaves',
            paging: true,
            pageSize: 10,
            sorting: true,
            defaultSorting: 'dateFrom DESC',
            actions: actionsJTableVar,
            toolbar: toolBarsJTableVar,
            fields: {
            	leaveId: {
                    key: true,
                    list: false
                },                
            	empId: {
                    key: true,
                    list: false
                },                
               //need to pass for update and for beanutils
                dateFiled: {
                	type:'hidden',
                    list:false
                }, 
                
                noDays: {
                    title: '# of Days',
                    create: true,
                    edit : true 
                },  
                dateFrom: {
                    title: 'From',                    
                    edit: true                         
                },         
                dateTo: {
                    title: 'To',
                    edit: true                   
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
                	create: false,
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
                    	function(data) {
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
                },need2Approvers: {
                    key: true,
                    list: false,
                    create: false,
                    edit: false
                    
                },leaveTypeId: {
                	title: 'Leave Type',
                    edit: true,
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
                    },                    	
                    create:true
                    
                },createdBy: {
                    key: true,
                    list: false 
                
                //need to pass for update and for beanutils
                },createdDate: {
                	type:'hidden',
                    list: false
                }                
            },
            formCreated : function(event, data){
            	
				$(data.form).addClass("custom_horizontal_form_field");
				
				//for dates: we need to find the from/to manually
                
                
                var $dtfrom1 = data.form.find ('input[name="dateFrom"]');     
                var $dtTo1 = data.form.find ('input[name="dateTo"]');
	        	
	        	var $dialogDiv = data.form.closest('.ui-dialog');
	            $dialogDiv.position({
	                my: "center",
	                at: "center",
	                of: $("#containerEmployeeLeaveEntry")
	            });
            	
	            $(data.form).parent().parent().css("top","150");
	            
	            
	            data.form.find('input').css('width','200px');
	            data.form.find('textarea').css('width','200px');
	            data.form.find('select').css('width','200px');
	            data.form.find('select').css('padding','7.5px');
            	
                
                
            	
            	
            	
                

                
              //manually activate the datepicker, and supply the params from useDPicker (you need to do this so that when date is selected, the date format is followed.)
              //and you need to add val in order to display the value saved in the DB in the proper format, if not, it will become Mon dd, yyyy which will fail.
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
					
					//need to add the 2 below for the hidden attibutes so we can pass back to the servlet the values during update
					//var $dtCreatedDate = data.form.find ('input[name="createdDate"]');
					//var $dtDateFiled = data.form.find ('input[name="dateFiled"]');    
	                //$dtCreatedDate.val(moment(data.record.createdDate).format('YYYY-MM-DD'));	                
	                //$dtDateFiled.val(moment(data.record.dateFiled).format('YYYY-MM-DD'));
				}
				
				//call these datepickers below when doing create entry, since we do not need to display any dateFrom or dateTo
                
				
				
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
            
            //Validate form when it is being submitted
            formSubmitting: function (event, data) {
            	
            	data.form.find('input[name="dateFrom"]').addClass('validate[required]');
                data.form.find('input[name="dateTo"]').addClass('validate[required]');
                data.form.find('input[name="noDays"]').addClass('validate[required]');
                data.form.find('textarea[name="remarks"]').addClass('validate[required]');
                data.form.find('input[name="status"]').addClass('validate[required]');
            	
				var digitsOnly = new RegExp(/^[0-9]+$/);
            	
            	
            	
				if(digitsOnly.test(data.form.find('input[name="noDays"]').val())){
					if(data.form.find('input[name="noDays"]').val() == "0"){
						alert("No. of Days should be numberic and should be greater than 0.");
						return false;
					}
	        	} else {
	        		alert("No. of Days should only be numeric and greater than 0.");
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
        $('#containerEmployeeLeaveEntry').jtable('load');
        
    });

</script>

</head>
<body>
<div><c:import url="header.jsp" /></div>
<div id="content">
<div class="cb" style="height: 20px;"></div>
	<div id="containerEmployeeLeaveEntry" class="jTableContainerDaiExtendedLong"></div>		
</div>	
<div><c:import url="footer.jsp" /></div>
</body>
</html>