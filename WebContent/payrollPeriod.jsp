<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Payroll Period</title>
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
$(document).ready(function(){
	var empId = '${employeeLoggedIn.empId}';	
	
	if(empId.length == 0){
		alert("You are not allowed to view the page. Please login.");
		window.location = "LogoutAction";
		return;
	}
	
	var isAllowed = 'NO';
	var addButtonEnabled = 'NO';
	var editButtonEnabled = 'NO';
	var exportButtonEnabled = 'NO';
	var printButtonEnabled = 'NO';
	var actionsJTableVar = {    			 
	};    	
	var toolBarsJTableVar = { 			 
	};
	
	<c:forEach var="fm" items="${sessionScope.moduleAccess.payrollList}">
		<c:if test="${fm == 'py_payroll_period'}">
			isAllowed = 'YES';
		</c:if>
		<c:if test="${fm == 'py_add_payroll_period'}">
			addButtonEnabled = 'YES';
		</c:if>
		
		<c:if test="${fm == 'py_edit_payroll_period'}">
			editButtonEnabled = 'YES';
		</c:if>
		
		<c:if test="${fm == 'py_print_payroll_period'}">
			printButtonEnabled = 'YES';
		</c:if>
		
		<c:if test="${fm == 'py_export_payroll_period'}">
			exportButtonEnabled = 'YES';
		</c:if>
	</c:forEach>
	    	
	
	if(isAllowed == 'NO'){
		alert("You are not Viewed to view the page. Please login.");
		window.location = "LogoutAction";
		return;
	}
	
	if(addButtonEnabled == 'YES' && editButtonEnabled == 'YES'){    		
		actionsJTableVar = {    	    		    	    	
			listAction: '/hris/GetPayrollPeriodAction',                
	        createAction: '/hris/SavePayrollPeriodAction',
			updateAction: '/hris/SavePayrollPeriodAction'    			    		
	    };
	} else if (addButtonEnabled == 'YES' && editButtonEnabled == 'NO') {			
		actionsJTableVar = {	    	   
    		listAction: '/hris/GetPayrollPeriodAction',                
    	    createAction: '/hris/SavePayrollPeriodAction'    			    		
		};
	} else if (addButtonEnabled == 'NO' && editButtonEnabled == 'YES') {			
		actionsJTableVar = {
    		listAction: '/hris/GetPayrollPeriodAction',    	        	
			updateAction: '/hris/SavePayrollPeriodAction'
		};
	} else {			
		actionsJTableVar = {	    	    	
    		listAction: '/hris/GetPayrollPeriodAction'
		};
	}    	
	
	
	if(printButtonEnabled == 'YES' && exportButtonEnabled == 'YES'){    		
		toolBarsJTableVar = {    	    		    	    	
				items: [{
                    tooltip: 'Click here to export this table to excel',
                    icon: '/hris/images/excel.png',
                    text: 'Export to Excel',
                    click: function () {
                    	var oAjaxCall = $.ajax({
                    		type : "POST",
                    		url : "/hris/ActionPdfExportFMPayrollPeriodListReport",	                    		
                    		data: "name="+$('#name').val()+"&forExport=Y",
                    		cache : false,
                    		async : false,
                    		success : function(data) {
                    			window.location.href = "report/" + data;
                    		},
                    		error : function(data) {
                    			alert('error: ' + data);
                    		}

                    	});
                    }
                },{
                    tooltip: 'Click here to print and display via PDF Format',
                    icon: '/hris/images/pdf.png',
                    text: 'Print to PDF',
                    click: function () {
                    	window.open('ActionPdfExportFMPayrollPeriodListReport?name=' + $('#name').val()+"&forExport=N", '_blank');
                    }
                }]   			    		
	    };
	} else if (printButtonEnabled == 'YES' && exportButtonEnabled == 'NO') {			
		toolBarsJTableVar = {	    	   
				items: [{
                    tooltip: 'Click here to print and display via PDF Format',
                    icon: '/hris/images/pdf.png',
                    text: 'Print to PDF',
                    click: function () {
                    	window.open('ActionPdfExportFMPayrollPeriodListReport?name=' + $('#name').val()+"&forExport=N", '_blank');
                    }
                }]	    		
		};
	} else if (printButtonEnabled == 'NO' && exportButtonEnabled == 'YES') {			
		toolBarsJTableVar = {
				items: [{
                    tooltip: 'Click here to export this table to excel',
                    icon: '/hris/images/excel.png',
                    text: 'Export to Excel',
                    click: function () {
                    	var oAjaxCall = $.ajax({
                    		type : "POST",
                    		url : "/hris/ActionPdfExportFMPayrollPeriodListReport",	                    		
                    		data: "name="+$('#name').val()+"&forExport=Y",
                    		cache : false,
                    		async : false,
                    		success : function(data) {
                    			window.location.href = "report/" + data;
                    		},
                    		error : function(data) {
                    			alert('error: ' + data);
                    		}

                    	});
                    }
                }]
		};
	}
	
	$('#containerPayrollPeriod').jtable({
		title: 'Payroll Period',
		paging: true,
        pageSize: 10,
        sorting: true,
        defaultSorting: 'fromDate ASC',
        actions: actionsJTableVar,
        toolbar: toolBarsJTableVar,
		fields: {
			payrollPeriodId: {
				key: true,
				list: false
			},
			payYear: {
				title: 'Pay Year',
				edit : true 
			},
			payMonth: {
				title: 'Pay Month',
				options: {'1':'January','2':'February','3':'March','4':'April','5':'May','6':'June',
					'7':'July','8':'August','9':'September','10':'October','11':'November','12':'December'},
				edit : true 
			},
			payrollType: {
				title: 'Payroll Type',
				options: [{ Value: 'M', DisplayText: 'Monthly' }, { Value: 'S', DisplayText: 'Semi-Monthly' }],
				edit : true 
			},
			fromDate: {
				title: 'From',
				edit : true 
			},
			toDate: {
				title: 'To',
				edit : true 
			},
			payDate: {
			    title: 'Pay Date',
			    edit : true 
			},
			payrollCode: {
				title: 'Payroll Code',
				edit : true 
			},
			numWorkDays: {
				title: '# of Work Days',
				edit : true 
			},
			payPeriod: {
				title: 'Pay Period',
				edit : false,	 
				list: false,
				create: false
				//options: ['13th Month','']
			},
			isContractual: {
				title: 'For Contractual',
				list: false,
				options: {'N':'No','Y':'Yes'}
			},
			
			lockedAt: {
				title: 'Locked Date',
				edit : false,
				create: false,
				list: true
                //display:
                //	function (data) {
                		//alert (data.record.numWorkDays);
                //			if (data.record.lockedAt != undefined){      //need to have a condition for undefined since moment will display default date.
                //    			return moment(data.record.lockedAt).format('YYYY-MM-DD');
                //			}
                //			return 'N/A';
               	//	 }

			}
		},
		formCreated : function(event, data){
			
			$(data.form).addClass("custom_horizontal_form_field");
        	
        	var $dialogDiv = data.form.closest('.ui-dialog');
            $dialogDiv.position({
                my: "center",
                at: "center",
                of: $("#containerPayrollPeriod")
            });
			
			$(data.form).parent().parent().css('top', 200);  //to make the form popup when adding/updating visible
			$(data.form).parent().parent().css('margin-bottom', 100);
       
			data.form.find('input').css('width','180px');
			data.form.find('select').css('width','196px');
			data.form.find('select').css('padding','7.5px');
			
        	//for dates: we need to find the from/to manually
            var $dtfrom = data.form.find ('input[name="fromDate"]');     
            var $dtTo = data.form.find ('input[name="toDate"]');
            var $dtPay = data.form.find ('input[name=payDate]');
            
            //manually activate the datepicker, and supply the params from useDPicker (you need to do this so that when date is selected, the date format is followed.)
            //and you need to add val in order to display the value saved in the DB in the proper format, if not, it will become Mon dd, yyyy which will fail.
			if (data.formType == 'edit'){
                $dtfrom.datepicker({
            		changeMonth : true,
            		changeYear : true,
            		dateFormat : 'yy-mm-dd',
            		yearRange : '1910:2100',
            	    beforeShow: function(input, inst){	            	    	
            	           $(".ui-datepicker").css('font-size', 11);
            	    }
            	}).val(moment(data.record.fromDate).format('YYYY-MM-DD'));		

                $dtTo.datepicker({
            		changeMonth : true,
            		changeYear : true,
            		dateFormat : 'yy-mm-dd',
            		yearRange : '1910:2100',
            	    beforeShow: function(input, inst){	            	    	
            	           $(".ui-datepicker").css('font-size', 11);
            	    }
            	}).val(moment(data.record.toDate).format('YYYY-MM-DD'));
                
                $dtPay.datepicker({
            		changeMonth : true,
            		changeYear : true,
            		dateFormat : 'yy-mm-dd',
            		yearRange : '1910:2100',
            	    beforeShow: function(input, inst){	            	    	
            	           $(".ui-datepicker").css('font-size', 11);
            	    }
            	}).val(moment(data.record.toDate).format('YYYY-MM-DD'));
			}
            
			//call these datepickers below when doing create entry, since we do not need to display any dateFrom or dateTo
            $dtfrom.datepicker({
        		changeMonth : true,
        		changeYear : true,
        		dateFormat : 'yy-mm-dd',
        		yearRange : '1910:2100',
        	    beforeShow: function(input, inst){	            	    	
     	           $(".ui-datepicker").css('font-size', 11);
     	    }
        	});		

            $dtTo.datepicker({
        		changeMonth : true,
        		changeYear : true,
        		dateFormat : 'yy-mm-dd',
        		yearRange : '1910:2100',
        	    beforeShow: function(input, inst){	            	    	
     	           $(".ui-datepicker").css('font-size', 11);
     	    }
        	});				
            
            $dtPay.datepicker({
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
        	data.form.find('input[name="fromDate"]').addClass('validate[required]');
        	data.form.find('input[name="toDate"]').addClass('validate[required]');
        	data.form.find('input[name="paydate"]').addClass('validate[required]');
        	data.form.find('input[name="payrollCode"]').addClass('validate[required]');
        	data.form.find('input[name="numWorkDays"]').addClass('validate[required]');
        	data.form.find('input[name="payPeriod"]').addClass('validate[required]');
        	data.form.find('input[name="payDate"]').addClass('validate[required]');
        	data.form.find('input[name="payYear"]').addClass('validate[required]');
        	
        	var digitsOnly = new RegExp(/^[0-9]+$/);       	
        	
			if(digitsOnly.test(data.form.find('input[name="numWorkDays"]').val())){
				if(data.form.find('input[name="numWorkDays"]').val() == "0"){
					alert("No. of Days should be numberic and should be greater than 0.");
					return false;
				}
        	} else {
        		alert("No. of Days should only be numeric and greater than 0.");
        		return false;
        	}
			
			if(digitsOnly.test(data.form.find('input[name="payYear"]').val())){
				if(data.form.find('input[name="payYear"]').val() == "0"){
					alert("Year should be numberic and should be greater than 0.");
					return false;
				}
        	} else {
        		alert("Year should only be numeric and greater than 0.");
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
	
	$('#searchButton').click(function (e) {
        e.preventDefault();
        $('#containerPayrollPeriod').jtable('load', {
            name: $('#name').val()
        });
    });

    //Load all records when page is first shown
    $('#searchButton').click();
	//$('#containerPayrollPeriod').jtable('load');
});
</script>
</head>
<body>
<div><c:import url="header.jsp" /></div>
<div id="content">
	<div class="cb" style="height: 20px;"></div>
	<div class="filtering" style="width: 1074px;">
    
        Payroll Code: &nbsp;&nbsp;&nbsp;<input type="text" name="name" id="name" size="40" onKeyPress="javascript:enterPressFunc(event);" />        
        
        <div style="cursor: pointer;" id="searchButton">SEARCH</div>
    
	</div>
	<div id="containerPayrollPeriod" class="jTableContainerDaiExtendedLong"></div>		
</div>	
<div><c:import url="footer.jsp" /></div>
</body>
</html>