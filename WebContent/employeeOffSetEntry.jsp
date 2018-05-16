<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Employee Off Set Entry</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@include file="commonHeader.jsp" %>
<%@include file="commonJtables.jsp" %>

<link rel="stylesheet" type="text/css" href="css/navstyle.css" />
<link rel="stylesheet" type="text/css" href="css/dai.css" />
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
    		listAction: '/hris/GetAllOffSetAction?empId='+empId,                
    	    createAction: '/hris/SaveOffSetAction?empId='+empId,
			updateAction: '/hris/UpdateOffSetAction' 
    	};    	
    	var toolBarsJTableVar = { 	
    			
    	};  
    	
    	
    	//var empId = '${sessionScope.employeeLoggedIn.empId}'; //this is the employeeid filing the OT
    	
        $('#containerEmployeeOffSetEntry').jtable({
            title: 'My OffSet Entries',
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
	                of: $("#containerEmployeeOffSetEntry")
	            });
            	
	            data.form.find('input').css('width','200px');
	            data.form.find('textarea').css('width','200px');
            	$(data.form).parent().parent().css("top","155");        	
            	
            	
            	//for dates: we need to find the from/to manually
                var $dtfrom = data.form.find ('input[name="dateRendered"]');           	
            	
            					
				$dtfrom.datepicker({
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
            fields: {
            	empOffSetId: {
                    key: true,
                    list: false
                },
                
            	empId: {
                    key: true,
                    list: false
                },
                
                noOfHours: {
                    title: '# of Hours',
                    create: true,
                    edit : true 
                },
                
                dateRendered: {
                    title: 'Rendered Date',
                    //type: 'date',  //comment-out the type date
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
            	data.form.find('input[name="dateRendered"]').addClass('validate[required]');
            	data.form.find('input[name="noOfHours"]').addClass('validate[required]');
            	data.form.find('textarea[name="remarks"]').addClass('validate[required]');
            	
            	var digitsOnly = new RegExp(/^[0-9]+$/);
            	
            	
            	
				if(digitsOnly.test(data.form.find('input[name="noOfHours"]').val())){
					if(data.form.find('input[name="noOfHours"]').val() == "0"){
						alert("No. of Hours should be numberic and should be greater than 0.");
						return false;
					}
	        	} else {
	        		alert("No. of Hours should only be numeric and greater than 0.");
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
        $('#containerEmployeeOffSetEntry').jtable('load');
        
    });

</script>

</head>
<body>
<div><c:import url="header.jsp" /></div>
<div id="content">
<div class="cb" style="height: 20px;"></div>
	<div id="containerEmployeeOffSetEntry" class="jTableContainerDaiExtended"></div>		
</div>	
<div><c:import url="footer.jsp" /></div>
</body>
</html>