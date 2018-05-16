<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Medicare Share Allowance</title>
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
			<c:if test="${fm == 'py_medicare_share'}">
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
		
			<c:if test="${fm == 'py_add_medicare_share'}">
				addButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'py_edit_medicare_share'}">
				editButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'py_print_medicare_share'}">
				printButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'py_export_medicare_share'}">
				exportButtonEnabled = 'YES';
			</c:if>
		</c:forEach>
		
		if(addButtonEnabled == 'YES' && editButtonEnabled == 'YES'){    		
    		actionsJTableVar = {    	    		    	    	
    			listAction: '/hris/GetMedicareShareAllowanceAction?toRecipientEmpId='+empid,                
    	        //createAction: '/hris/SaveMedicareShareAllowanceAction',
				updateAction: '/hris/SaveMedicareShareAllowanceAction'    			    		
    	    };
		} else if (addButtonEnabled == 'YES' && editButtonEnabled == 'NO') {			
			actionsJTableVar = {	    	   
	    		listAction: '/hris/GetMedicareShareAllowanceAction?toRecipientEmpId='+empid//,                
	    	    //createAction: '/hris/SaveMedicareShareAllowanceAction'    			    		
			};
		} else if (addButtonEnabled == 'NO' && editButtonEnabled == 'YES') {			
			actionsJTableVar = {
	    		listAction: '/hris/GetMedicareShareAllowanceAction?toRecipientEmpId='+empid,    	        	
				updateAction: '/hris/SaveMedicareShareAllowanceAction'
			};
		} else {			
			actionsJTableVar = {	    	    	
	    		listAction: '/hris/GetMedicareShareAllowanceAction?toRecipientEmpId='+empid
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
	                    		url : "/hris/ActionPdfExportMedicareShareListReport",	                    		
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
                        	window.open('ActionPdfExportMedicareShareListReport?forExport=N'+'&empId='+empid, '_blank');
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
	                    	window.open('ActionPdfExportMedicareShareListReport?forExport=N'+'&empId='+empid, '_blank');
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
	                    		url : "/hris/ActionPdfExportMedicareShareListReport",	                    		
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
		
		$('#containerMedicareShare').jtable();
		$('#containerMedicareShare').jtable('destroy');
		$('#containerMedicareShare').jtable({
			title: 'Medicare Share Allowance',
			actions: actionsJTableVar,
            toolbar: toolBarsJTableVar,
			fields: {
				medicareShareAllowanceId: {
					key: true,
					list: false
				},
				empId: {
					type: 'hidden'
				},
				numDays: {
					title: 'Number of Days'
				},
				ratePerDay: {
					title: 'Rate Per Day'
				},				
				netAmountDue: {
					title: 'Net Amount Due'
				},
				date: {
					title: 'Date'					
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
	                of: $("#containerMedicareShare")
	            });
				
            	$('#Edit-empId').val(empid);
            	$(data.form).parent().parent().css('top', 150);
            	
            	data.form.find('input').css('width','183px');
	            data.form.find('textarea').css('width','194px');
	            data.form.find('select').css('width','200px');
	            data.form.find('select').css('padding','7.5px');
	            
	            var $dtOR = data.form.find ('input[name="date"]');     
                //manually activate the datepicker, and supply the params from useDPicker (you need to do this so that when date is selected, the date format is followed.)
                //and you need to add val in order to display the value saved in the DB in the proper format, if not, it will become Mon dd, yyyy which will fail.
    			if (data.formType == 'edit'){
                    $dtOR.datepicker({
                		changeMonth : true,
                		changeYear : true,
                		dateFormat : 'yy-mm-dd',
                		yearRange : '1910:2100',
                	    beforeShow: function(input, inst){	            	    	
                	           $(".ui-datepicker").css('font-size', 11);
                	    }
                	}).val(moment(data.record.fromDate).format('YYYY-MM-DD'));		
    			}
    			$dtOR.datepicker({
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
            	data.form.find('input[name="salaryGrade"]').addClass('validate[required]');	        	
	        	data.form.find('input[name="netAmountDue"]').addClass('validate[required]');
	        	data.form.find('input[name="date"]').addClass('validate[required]');
	        	
            	var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
				var digitsOnly = new RegExp(/^[0-9]+$/);
            	
            	if(digitsOnly.test(data.form.find('input[name="numDays"]').val())){
	        		if(data.form.find('input[name="numDays"]').val() == "0"){
						alert("Year should be numberic and should be greater than 0.");
						return false;
					}
	        	} else {
	        		alert("Year should only be numeric and greater than 0.");
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
            	
            	if(rx.test(data.form.find('input[name="netAmountDue"]').val())){
					if(data.form.find('input[name="netAmountDue"]').val() == "0"){
						alert("Net Amount should be numberic and should be greater than 0.");
						return false;
					}
	        	} else {
	        		alert("Net Amount should only be numeric and greater than 0.");		
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
		$('#containerMedicareShare').jtable('load');
    }
</script>


<script type="text/javascript">

function redirect() {	
	window.location = "medicareShareNew.jsp";		
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
		<div id="containerMedicareShare" class="jTableContainerDaiLeft1020"></div>		
	</div>
</div>
<div><c:import url="footer.jsp" /></div>
</body>
</html>