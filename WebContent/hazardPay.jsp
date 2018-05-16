<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hazard Pay</title>
<%@include file="commonHeader.jsp" %>
<%@include file="commonJtables.jsp" %>
<link rel="stylesheet" type="text/css" href="css/styleTables.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
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
    	
    	var isAllowed = 'NO';
    	
    	<c:forEach var="fm" items="${sessionScope.moduleAccess.payrollList}">
			<c:if test="${fm == 'py_hazard_pay'}">
				isAllowed = 'YES';
			</c:if>			
		</c:forEach>
		    	
		
		if(isAllowed == 'NO'){
			alert("You are not Viewed to view the page. Please login.");
			window.location = "LogoutAction";
			return;
		}
    	
    });
</script>


<script type="text/javascript">

    function clickSearchResult(empid) {
    	
    	var addButtonEnabled = 'NO';
    	var editButtonEnabled = 'NO';
    	var exportButtonEnabled = 'NO';
    	var printButtonEnabled = 'NO';
    	var actionsJTableVar = {    			 
    	};    	
    	var toolBarsJTableVar = { 			 
    	};
    	
    	<c:forEach var="fm" items="${sessionScope.moduleAccess.payrollList}">
		
			<c:if test="${fm == 'py_add_hazard_pay'}">
				addButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'py_edit_hazard_pay'}">
				editButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'py_print_hazard_pay'}">
				printButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'py_export_hazard_pay'}">
				exportButtonEnabled = 'YES';
			</c:if>
		</c:forEach>
		
		if(addButtonEnabled == 'YES' && editButtonEnabled == 'YES'){    		
    		actionsJTableVar = {    	    		    	    	
    			listAction: '/hris/GetHazardPayAction?toRecipientEmpId='+empid,                
    	        //createAction: '/hris/SaveHazardPayAction',
				updateAction: '/hris/SaveHazardPayAction'    			    		
    	    };
		} else if (addButtonEnabled == 'YES' && editButtonEnabled == 'NO') {			
			actionsJTableVar = {	    	   
	    		listAction: '/hris/GetHazardPayAction?toRecipientEmpId='+empid//,                
	    	    //createAction: '/hris/SaveHazardPayAction'    			    		
			};
		} else if (addButtonEnabled == 'NO' && editButtonEnabled == 'YES') {			
			actionsJTableVar = {
	    		listAction: '/hris/GetHazardPayAction?toRecipientEmpId='+empid,    	        	
				updateAction: '/hris/SaveHazardPayAction'
			};
		} else {			
			actionsJTableVar = {	    	    	
	    		listAction: '/hris/GetHazardPayAction?toRecipientEmpId='+empid
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
	                    		url : "/hris/ActionPdfExportHazardPayListReport",	                    		
	                    		data: "forExport=Y"+"&empId="+empid,
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
                        	window.open('ActionPdfExportHazardPayListReport?forExport=N'+'&empId='+empid, '_blank');
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
	                    	window.open('ActionPdfExportHazardPayListReport?forExport=N'+'&empId='+empid, '_blank');
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
	                    		url : "/hris/ActionPdfExportHazardPayListReport",	                    		
	                    		data: "forExport=Y"+"&empId="+empid,
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
		
		
    	$('#empNo').html(empSearchMap[empid].empno);
		$('#fullname').html(empSearchMap[empid].lastname + ", " + empSearchMap[empid].firstname);
		
		$('#containerHazardPay').jtable();
		$('#containerHazardPay').jtable('destroy');
		$('#containerHazardPay').jtable({
			title: 'Hazard Pay',
			actions: actionsJTableVar,
            toolbar: toolBarsJTableVar,
			fields: {
				hazardPayId: {
					key: true,
					list: false
				},
				empId: {
					type: 'hidden'
				},
				//salaryGrade: {
					//title: 'Salary Grade',
					//list: false
				//},
				//basicSalary: {
					//title: 'Basic Salary',
					//list: false
				//},
				hazardPay: {
					title: 'Hazard Pay'
				},
				withHoldingTax: {
					title: 'Withholding Tax'
				},
				eamcDeduction: {
					title: 'Deduction'
				},
				netAmountDue: {
					title: 'Net Amount Due'
				},
				year: {
					title: 'Year'
				},
				month: {
					title: 'Month',
					options: {'1':'January','2':'February','3':'March','4':'April','5':'May','6':'June',
						'7':'July','8':'August','9':'September','10':'October','11':'November','12':'December'},
				},				
				remarks: {
					title: 'Remarks',
					type: 'textarea',
					list: false
				}
			},
			formCreated : function(event, data){
				
				$(data.form).addClass("custom_horizontal_form_field");
				
				
	        	
	        	var $dialogDiv = data.form.closest('.ui-dialog');
	            $dialogDiv.position({
	                my: "center",
	                at: "center",
	                of: $("#containerHazardPay")
	            });
				
            	$('#Edit-empId').val(empid);
            	$(data.form).parent().parent().css('top', 150);
            	
            	data.form.find('input').css('width','183px');
	            data.form.find('textarea').css('width','194px');
	            data.form.find('select').css('width','200px');
	            data.form.find('select').css('padding','7.5px');
            	
            	
            	
	        	
	        	
	        	
            	data.form.validationEngine();
            },
            //Validate form when it is being submitted
            formSubmitting: function (event, data) {
            	//data.form.find('input[name="salaryGrade"]').addClass('validate[required]');
	        	data.form.find('input[name="hazardPay"]').addClass('validate[required]');
	        	data.form.find('input[name="withHoldingTax"]').addClass('validate[required]');	        	
	        	data.form.find('input[name="netAmountDue"]').addClass('validate[required]');
	        	data.form.find('input[name="year"]').addClass('validate[required]');
	        	data.form.find('input[name="month"]').addClass('validate[required]');
            	
            	var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
				var digitsOnly = new RegExp(/^[0-9]+$/);
	        	
				//if(digitsOnly.test(data.form.find('input[name="salaryGrade"]').val())){
	        	//	if(data.form.find('input[name="salaryGrade"]').val() == "0"){
				//		alert("Salary Grade should be numberic and should be greater than 0.");
				//		return false;
				//	}
	        	//} else {
	        	//	alert("Salary Grade should only be numeric and greater than 0.");
	        	//	return false;
	        	//}
				
	        	//if(rx.test(data.form.find('input[name="basicSalary"]').val())){
	        	//	if(data.form.find('input[name="basicSalary"]').val() == "0"){
				//		alert("Basic Salary should be numberic and should be greater than 0.");
				//		return false;
				//	}
	        	//} else {
	        	//	alert("Basic Salary should only be numeric and greater than 0.");		
	        	//	return false;
	        	//}
	        	
	        	if(rx.test(data.form.find('input[name="hazardPay"]').val())){
	        		if(data.form.find('input[name="hazardPay"]').val() == "0"){
						alert("Hazard Pay should be numberic and should be greater than 0.");
						return false;
					}
	        	} else {
	        		alert("Hazard Pay should only be numeric and greater than 0.");		
	        		return false;
	        	}
	        	
	        	if(rx.test(data.form.find('input[name="withHoldingTax"]').val())){
					if(data.form.find('input[name="withHoldingTax"]').val() == "0"){
						alert("WithHolding Tax should be numberic and should be greater than 0.");
						return false;
					}
	        	} else {
	        		alert("WithHolding Tax should only be numeric and greater than 0.");		
	        		return false;
	        	}
	        	
	        	if(rx.test(data.form.find('input[name="netAmountDue"]').val())){
					if(data.form.find('input[name="netAmountDue"]').val() == "0"){
						alert("Net Amount should be numberic and should be greater than 0.");
						return false;
					}
	        	} else {
	        		alert("Net Amount should only be numeric and greater than 0.");		
	        		return false;
	        	}
	        	
	        	if(rx.test(data.form.find('input[name="eamcDeduction"]').val())){
					
	        	} else {
	        		alert("Deduction should only be numeric.");		
	        		return false;
	        	}
	        	
	        	if(digitsOnly.test(data.form.find('input[name="year"]').val())){
	        		if(data.form.find('input[name="year"]').val() == "0"){
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
		$('#containerHazardPay').jtable('load');
    }
</script>

<script type="text/javascript">

function redirect() {	
	window.location = "hazardPayNew.jsp";		
}

</script>

</head>
<body>
<div><c:import url="header.jsp" /></div>
<div id="content">
    <div id="dashBoardLeftPannel2">
		<c:import url="searchEmployeeCommonPayroll.jsp" />
		<div class="cb"></div>
		<div>
			<div id="searchHolderId" style="margin-bottom: 50px;"></div>	
		</div>
	</div>
	<div id="dashBoardRightPannel2" width="100%">	
		<div class="dataEntryText">Employee No:</div>
	    <div class="dataEntryTextBlue" id="empNo"></div>	    
	    <div class="dataEntryText">Employee Name:</div>			    
	    <div class="dataEntryTextBlue" id="fullname"></div>	    
	    <div class="cb"></div>	    				
	  
	    <!--div class="cb" style="height: 20px;"></div-->
		<div id="containerHazardPay" class="jTableContainerDaiLeft1020"></div>		
	</div>
</div>
<div><c:import url="footer.jsp" /></div>
</body>
</html>