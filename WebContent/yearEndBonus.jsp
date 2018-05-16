<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Year End Bonus and Cash Gift</title>
<%@include file="commonHeader.jsp" %>
<%@include file="commonJtables.jsp" %>
<link rel="stylesheet" type="text/css" href="css/styleTables.css" />
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
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
    	
    	var isAllowed = 'NO';
    	
    	<c:forEach var="fm" items="${sessionScope.moduleAccess.payrollList}">
			<c:if test="${fm == 'py_yearend_bonus'}">
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
		
			<c:if test="${fm == 'py_add_yearend_bonus'}">
				addButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'py_edit_yearend_bonus'}">
				editButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'py_print_yearend_bonus'}">
				printButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'py_export_yearend_bonus'}">
				exportButtonEnabled = 'YES';
			</c:if>
		</c:forEach>
		
		if(addButtonEnabled == 'YES' && editButtonEnabled == 'YES'){    		
    		actionsJTableVar = {    	    		    	    	
    			listAction: '/hris/GetYearEndBonusCashGiftAction?toRecipientEmpId='+empid,                
    	        //createAction: '/hris/SaveYearEndBonusCashGiftAction',
				updateAction: '/hris/SaveYearEndBonusCashGiftAction'    			    		
    	    };
		} else if (addButtonEnabled == 'YES' && editButtonEnabled == 'NO') {			
			actionsJTableVar = {	    	   
	    		listAction: '/hris/GetYearEndBonusCashGiftAction?toRecipientEmpId='+empid//,                
	    	    //createAction: '/hris/SaveYearEndBonusCashGiftAction'    			    		
			};
		} else if (addButtonEnabled == 'NO' && editButtonEnabled == 'YES') {			
			actionsJTableVar = {
	    		listAction: '/hris/GetYearEndBonusCashGiftAction?toRecipientEmpId='+empid,    	        	
				updateAction: '/hris/SaveYearEndBonusCashGiftAction'
			};
		} else {			
			actionsJTableVar = {	    	    	
	    		listAction: '/hris/GetYearEndBonusCashGiftAction?toRecipientEmpId='+empid
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
	                    		url : "/hris/ActionPdfExportYearEndBonusListReport",	                    		
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
                        	window.open('ActionPdfExportYearEndBonusListReport?forExport=N'+'&empId='+empid, '_blank');
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
	                    	window.open('ActionPdfExportYearEndBonusListReport?forExport=N'+'&empId='+empid, '_blank');
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
	                    		url : "/hris/ActionPdfExportYearEndBonusListReport",	                    		
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
		
		$('#containerYearEndBonus').jtable();
		$('#containerYearEndBonus').jtable('destroy');
		$('#containerYearEndBonus').jtable({
			title: 'Year End Bonus and Cash Gift',
			actions: actionsJTableVar,
            toolbar: toolBarsJTableVar,
			fields: {
				yearEndBonusId: {
					key: true,
					list: false
				},
				empId: {
					type: 'hidden'
				},
				//salaryGrade: {
				//	title: 'Salary Grade',
				//	list: false
				//},
				//STEP: {
				//	title: 'STEP',
				//	list: false
				//},
				//basicSalary: {
				//	title: 'Basic Salary',
				//	list: false
				//},				
				//cashGift: {
				//	title: 'Cash Gift',
				//	list: false
				//},
				//total: {
				//	title: 'Total',
				//	list: false					
				//},						
				//basicSalaryOct31: {
				//	title: 'Basic Salary as of Oct 31',
				//	list: false
				//},
				//cashGift1: {
				//	title: 'Cash Gift I',
				//	list: false					
				//},
				firstHalf13thMonth: {
					title: '1st Half 13th Month'
				},
				firstHalfCashGift: {
					title: '1st Half Cash Gift'
				},
				secondHalf13thMonth: {
					title: '2nd Half 13th Month'
				},
				secondHalfCashGift: {
					title: '2nd Half Cash Gift'
				},
				totalYearEndBonusCashGift: {
					title: 'Total Bonus and Cash Gift',
					list: false			
				},
				eamcCoopDeduction: {
					title: 'Deduction',
					list: false					
				},
				netAmountDue: {
					title: 'NET Amount Due'					
				},
				year: {
					title: 'Year'					
				}
			},
			formCreated : function(event, data){
				
				$(data.form).addClass("custom_horizontal_form_field");
				
				
	        	
	        	var $dialogDiv = data.form.closest('.ui-dialog');
	            $dialogDiv.position({
	                my: "center",
	                at: "center",
	                of: $("#containerYearEndBonus")
	            });
				
            	$('#Edit-empId').val(empid);
            	$(data.form).parent().parent().css('top', 150);
            	
            	data.form.find('input').css('width','183px');
	            data.form.find('textarea').css('width','194px');
	            data.form.find('select').css('width','200px');
	            
	            
            	
            	
            	
	        	//data.form.find('input[name="salaryGrade"]').addClass('validate[required]');	        	
	        	//data.form.find('input[name="netAmountDue"]').addClass('validate[required]');
	        	//data.form.find('input[name="date"]').addClass('validate[required]');
	        	
	        	
            	data.form.validationEngine();
            },
            //Validate form when it is being submitted
            formSubmitting: function (event, data) {
            	var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
				var digitsOnly = new RegExp(/^[0-9]+$/);
	        	
				
	        	
	        	if(rx.test(data.form.find('input[name="firstHalf13thMonth"]').val())){
					
	        	} else {
	        		alert("First Half 13thMonth should only be numeric.");		
	        		return false;
	        	}
	        	
				if(rx.test(data.form.find('input[name="firstHalfCashGift"]').val())){
									
				} else {
					alert("First Half Cash Gift should only be numeric.");		
					return false;
				}
					        	
				if(rx.test(data.form.find('input[name="secondHalf13thMonth"]').val())){
					
				} else {
					alert("First Half 13thMonth should only be numeric.");		
					return false;
				}
				
				if(rx.test(data.form.find('input[name="secondHalfCashGift"]').val())){
					
				} else {
					alert("First Half Cash Gift should only be numeric.");		
					return false;
				}
				
				if(rx.test(data.form.find('input[name="totalYearEndBonusCashGift"]').val())){
					
				} else {
					alert("Total YearEndBonus Cash Gift Cash Gift should only be numeric.");		
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
	        	
	        	if(rx.test(data.form.find('input[name="eamcCoopDeduction"]').val())){
					
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
		$('#containerYearEndBonus').jtable('load');
    }
</script>

<script type="text/javascript">

function redirect() {	
	window.location = "yearEndBonusNew.jsp";		
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
		<div id="containerYearEndBonus" class="jTableContainerDaiLeft1020"></div>		
	</div>
</div>
<div><c:import url="footer.jsp" /></div>
</body>
</html>