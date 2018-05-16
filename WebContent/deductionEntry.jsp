<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Deductions List</title>
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
			<c:if test="${fm == 'py_deductions'}">
				isAllowed = 'YES';
			</c:if>
			<c:if test="${fm == 'py_add_deductions'}">
				addButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'py_edit_deductions'}">
				editButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'py_print_deductions'}">
				printButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'py_export_deductions'}">
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
    			listAction: '/hris/GetDeductionAction',                
    	        createAction: '/hris/SaveDeductionAction',
				updateAction: '/hris/SaveDeductionAction'    			    		
    	    };
		} else if (addButtonEnabled == 'YES' && editButtonEnabled == 'NO') {			
			actionsJTableVar = {	    	   
	    		listAction: '/hris/GetDeductionAction',                
	    	    createAction: '/hris/SaveDeductionAction'    			    		
			};
		} else if (addButtonEnabled == 'NO' && editButtonEnabled == 'YES') {			
			actionsJTableVar = {
	    		listAction: '/hris/GetDeductionAction',    	        	
				updateAction: '/hris/SaveDeductionAction'
			};
		} else {			
			actionsJTableVar = {	    	    	
	    		listAction: '/hris/GetDeductionAction'
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
	                    		url : "/hris/ActionPdfExportFMDeductionListReport",	                    		
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
                        	window.open('ActionPdfExportFMDeductionListReport?name=' + $('#name').val()+"&forExport=N", '_blank');
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
	                    	window.open('ActionPdfExportFMDeductionListReport?name=' + $('#name').val()+"&forExport=N", '_blank');
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
	                    		url : "/hris/ActionPdfExportFMDeductionListReport",	                    		
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
		
		
		$('#containerDeductionEntry').jtable({
			title: 'Deductions',
			paging: true,
            pageSize: 10,
            sorting: true,
            defaultSorting: 'deductionName ASC',
            actions: actionsJTableVar,
            toolbar: toolBarsJTableVar,
			fields: {
				deductionId: {
					key: true,
					list: false
				},
				deductionName: {
					title: 'Name',
				},
				amount: {
					title: 'Amount'
				},
				employeeType: {
					title: 'Employee Type',
					options: ['All', 'Probationary','Regular','Contractual']
				},
				payrollType: {
					title: 'Payroll Type',
					options: ['Semi-monthly','Monthly','Weekly']
				},
				isForSecondHalf: {
					title: 'For 2nd Half',
					options: {'N':'No','Y':'Yes'}
				}
			},
			formCreated : function(event, data){
				
				$(data.form).addClass("custom_horizontal_form_field");
				
				
	        	
	        	var $dialogDiv = data.form.closest('.ui-dialog');
	            $dialogDiv.position({
	                my: "center",
	                at: "center",
	                of: $("#containerDeductionEntry")
	            });
	            
	            
            	$(data.form).parent().parent().css('top',150);
            	
            	data.form.find('input').css('width','183px');
	            data.form.find('select').css('width','200px');
	            data.form.find('select').css('padding','7.5px');

	        	
            	data.form.validationEngine();
            },
            //Validate form when it is being submitted
            formSubmitting: function (event, data) {
            	data.form.find('input[name="deductionName"]').addClass('validate[required]');
	        	data.form.find('input[name="amount"]').addClass('validate[required]');
				var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
            	
				if(rx.test(data.form.find('input[name="amount"]').val())){
	        		
	        	} else {
	        		alert("Amount should only be numeric and greater than 0.");		
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
            $('#containerDeductionEntry').jtable('load', {
                name: $('#name').val()
            });
        });
 
        //Load all records when page is first shown
        $('#searchButton').click();
		
		//$('#containerDeductionEntry').jtable('load');
	});
</script>
</head>
<body>
<div><c:import url="header.jsp" /></div>
<div id="content">
	<div class="cb" style="height: 20px;"></div>
	<div class="filtering" style="width: 774px;">
    
        Deduction Name: &nbsp;&nbsp;&nbsp;<input type="text" name="name" id="name" size="40" onKeyPress="javascript:enterPressFunc(event);" />        
        
        <div style="cursor: pointer;" id="searchButton">SEARCH</div>
    
	</div>
	<div id="containerDeductionEntry" class="jTableContainerDaiExtended"></div>		
</div>	
<div><c:import url="footer.jsp" /></div>
</body>
</html>