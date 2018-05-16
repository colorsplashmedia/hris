<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Employee Loan Entry</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@include file="commonHeader.jsp" %>
<%@include file="commonJtables.jsp" %>
<link rel="stylesheet" type="text/css" href="css/styleTables.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link href="js/validationEngine/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/validationEngine/jquery.validationEngine.js"></script>
<script type="text/javascript" src="js/validationEngine/jquery.validationEngine-en.js"></script>
<style>
.jtable-input-field-container {
    margin-bottom: 5px;
    display: block;
    float: none;
}
#jtable-create-form {
    display: block;
    width: 100%;
    -moz-column-gap:40px;
    /* Firefox */
    -webkit-column-gap:40px;
    /* Safari and Chrome */
    column-gap:40px;
    -moz-column-count:2;
    /* Firefox */
    -webkit-column-count:2;
    /* Safari and Chrome */
    column-count:2;
}
#jtable-edit-form {
    display: block;
    width: 100%;
    -moz-column-gap:40px;
    /* Firefox */
    -webkit-column-gap:40px;
    /* Safari and Chrome */
    column-gap:40px;
    -moz-column-count:2;
    /* Firefox */
    -webkit-column-count:2;
    /* Safari and Chrome */
    column-count:2;
}
</style>

<script type="text/javascript" src="js/common.js"></script>

<script type="text/javascript">

    $(document).ready(function () {
		var empId = '${employeeLoggedIn.empId}';	
    	
    	if(empId.length == 0){
    		alert("You are not allowed to view the page. Please login.");
    		window.location = "LogoutAction";
    		return;
    	}
    	
    	var isAllowed = 'NO';
    	
    	<c:forEach var="fm" items="${sessionScope.moduleAccess.employeesLoan}">
			<c:if test="${fm == 'el_loan_payments'}">
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
    	
    	<c:forEach var="fm" items="${sessionScope.moduleAccess.employeesLoan}">
		
			<c:if test="${fm == 'el_add_loan_payments'}">
				addButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'el_edit_loan_payments'}">
				editButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'el_print_loan_payments'}">
				printButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'el_export_loan_payments'}">
				exportButtonEnabled = 'YES';
			</c:if>
		</c:forEach>
		
		if(addButtonEnabled == 'YES' && editButtonEnabled == 'YES'){    		
    		actionsJTableVar = {    	    		    	    	
    			listAction: '/hris/GetAllLoanEntryByEmpIdAction?empId='+empid,                
    	        createAction: '/hris/AddLoanEntryAction',
				updateAction: '/hris/UpdateLoanEntryAction'    			    		
    	    };
		} else if (addButtonEnabled == 'YES' && editButtonEnabled == 'NO') {			
			actionsJTableVar = {	    	   
	    		listAction: '/hris/GetAllLoanEntryByEmpIdAction?empId='+empid,                
	    	    createAction: '/hris/AddLoanEntryAction'    			    		
			};
		} else if (addButtonEnabled == 'NO' && editButtonEnabled == 'YES') {			
			actionsJTableVar = {
	    		listAction: '/hris/GetAllLoanEntryByEmpIdAction?empId='+empid,    	        	
				updateAction: '/hris/UpdateLoanEntryAction'
			};
		} else {			
			actionsJTableVar = {	    	    	
	    		listAction: '/hris/GetAllLoanEntryByEmpIdAction?empId='+empid
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
	                    		url : "/hris/ActionPdfExportFMLoanEntryListReport",	                    		
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
                        	window.open('ActionPdfExportFMLoanEntryListReport?forExport=N'+'&empId='+empid, '_blank');
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
	                    	window.open('ActionPdfExportFMLoanEntryListReport?forExport=N'+'&empId='+empid, '_blank');
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
	                    		url : "/hris/ActionPdfExportFMLoanEntryListReport",	                    		
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
		
		
		$('#containerEmployeeLoanEntry').jtable();
		$('#containerEmployeeLoanEntry').jtable('destroy');
		
		
		
		  $('#containerEmployeeLoanEntry').jtable({
	            title: 'Loan Entry',
	            actions: actionsJTableVar,
	            toolbar: toolBarsJTableVar,
	            fields: {
	            	loanEntryId: {
	                    key: true,
	                    list: false
	                },
	                empId: {
	                	type:'hidden',
	                	defaultValue:empid
	                },
	                //CHILD TABLE DEFINITION FOR "PAYMENTS "
	                Payments: {
	                    title: '',
	                    width: '5%',
	                    sorting: false,
	                    edit: false,
	                    create: false,
	                    display: function (paymentData) {
	                        //Create an image that will be used to open child table
	                        var $img = $('<img src="/hris/images/list_metro.png" title="List of Payments" />');
	                        //Open child table when user clicks the image
	                        $img.click(function () {
	                            $('#containerEmployeeLoanEntry').jtable('openChildTable',
	                                    $img.closest('tr'),
	                                    {
	                                        title: 'Payment for referenceNo: ' + paymentData.record.referenceNo,
	                                        actions: {
	                                           	 listAction: '/hris/GetAllLoanPaymentsByLoanEntryId?loanEntryId='+paymentData.record.loanEntryId,
	                                             createAction: '/hris/AddLoanPaymentAction',
	                                             updateAction: '/hris/UpdateLoanTypeAction'
	                                        },
	                                        fields: {
	                                        		loanPaymentId: {
	                                        			key: true,
	                            	                    list: false
	                                            	},
	                                            	loanEntryId: {
	                                            		type:'hidden',
	                            	                	defaultValue:paymentData.record.loanEntryId
	                                            	},	   
	                                            	empId: {
	                                            		type:'hidden',
	                            	                	defaultValue:paymentData.record.empId
	                                            	},
	                            	                paidAmount:  {
	                            	                	title: 'Paid Amount'
	                            	                },
	                            	                paymentDate: {
	                            	                    title: 'Payment Date',
	                            	                    type: 'date',
	                            	                    displayFormat: 'yy-mm-dd'
	                            	                },
	                            	                remarks: {
	                            	                    title: 'Remarks',
	                            	                    type: 'textarea'
	                            	                },
	                            	                payrollPeriodId: {
	                            	                	type:'hidden',
	                            	                	defaultValue:0
	                            	                }
	                                            	
	                                            },
	                                            
	                                        }
	                                    , function (data) { //opened handler
	                                        data.childTable.jtable('load');
	                                    });
	                        });
	                        //Return image to show on the person row
	                        return $img;
	                    }
	                },
	                loanTypeId: {
	                    title: 'Loan Type',                
	                    options:  
		                   	function(data) {      
                
							return '/hris/GetAllLoanTypeAction?displayOption=true';
				                        
	                    }
	                },
	                dateFile: {
	                    title: 'Date Filed',                
	                    //type: 'date',
	                    displayFormat: 'yy-mm-dd'
	                },	                
	                referenceNo: {
	                    title: 'Reference No'
	                },
	                loanAmount: {
	                    title: 'Loan Amount'
	                },
	                loanAmountWithInterest: {
	                    title: 'Loan With Interest'
	                },
	                monthlyAmortization: {
	                    title: 'Monthly Amor.'
	                },
	                startDateToPay: {
	                    title: 'Payment Start',
	                    //type: 'date',
	                    displayFormat: 'yy-mm-dd'
	                },
	                endDateToPay: {
	                    title: 'Payment End',
	                    //type: 'date',
	                    displayFormat: 'yy-mm-dd'
	                },   
	                remarks: {
	                    title: 'Remarks',
	                    type: 'textarea',
	                    list: false
	                },
	                PNNo: {
	                    title: 'PNNo',
	                    list: false
	                },
	                PNDate: {
	                    title: 'PN Date',
	                    list: false
	                },
	                payrollCycle: {
	            		title: 'Payroll Cycle',            		
	            		edit: true,
	                    list: false,
	                    options: [{ Value: '', DisplayText: 'Select' }, { Value: '1', DisplayText: '1st Half' }, { Value: '2', DisplayText: '2nd Half' }, { Value: 'B', DisplayText: 'Both' }]
	                }, 
	                deductionFlagActive: {
	                    title: 'Deduct Loan',
	                    list: false,
	                    options: {'N':'No','Y':'Yes'}
	                }
	                
	                //,
	                //periodFrom: {
	                //    title: 'Period From',
	                //    list: false
	                //},
	                //periodTo: {
	                //    title: 'Period To',
	                //    list: false
	                //},
	                
	            },
	            
	          //Initialize validation logic when a form is created
	          formCreated: function (event, data) {
	        	  $(data.form).addClass("custom_horizontal_form_field");
					
					
		        	
		        	var $dialogDiv = data.form.closest('.ui-dialog');
		            $dialogDiv.position({
		                my: "center",
		                at: "center",
		                of: $("#containerEmployeeLoanEntry")
		            });
		            
		            $(data.form).parent().parent().css("top","150");
			        
			        data.form.find('input').css('width','200px');
		            data.form.find('textarea').css('width','200px');
		            data.form.find('select').css('width','200px');
		            data.form.find('select').css('padding','7.5px');
		            
		        	
	        	
	           if ((data.formType == 'edit')|| (data.formType == 'create')){
	        	   var $startDateToPay = data.form.find ('input[name="startDateToPay"]');     
	               var $endDateToPay = data.form.find ('input[name="endDateToPay"]');
	               
	               var $dateFile = data.form.find ('input[name="dateFile"]');
	               var $PNDate = data.form.find ('input[name="PNDate"]');
	               
	               
	               $dateFile.datepicker({
	            		changeMonth : true,
	            		changeYear : true,
	            		dateFormat : 'yy-mm-dd',
	            		yearRange : '1910:2100',
	            	    beforeShow: function(input, inst){	            	    	
	            	           $(".ui-datepicker").css('font-size', 11);
	            	    }
	            	});	
	               
	               $startDateToPay.datepicker({
	            		changeMonth : true,
	            		changeYear : true,
	            		dateFormat : 'yy-mm-dd',
	            		yearRange : '1910:2100',
	            	    beforeShow: function(input, inst){	            	    	
	            	           $(".ui-datepicker").css('font-size', 11);
	            	    }
	            	});	
	               
	               $endDateToPay.datepicker({
	            		changeMonth : true,
	            		changeYear : true,
	            		dateFormat : 'yy-mm-dd',
	            		yearRange : '1910:2100',
	            	    beforeShow: function(input, inst){	            	    	
	            	           $(".ui-datepicker").css('font-size', 11);
	            	    }
	            	});	
	                
	               
	               $PNDate.datepicker({
	            		changeMonth : true,
	            		changeYear : true,
	            		dateFormat : 'yy-mm-dd',
	            		yearRange : '1910:2100',
	            	    beforeShow: function(input, inst){	            	    	
	            	           $(".ui-datepicker").css('font-size', 11);
	            	    }
	            	});	
	        	   
	        	   
	            	
	            }
	           
	           data.form.validationEngine();
	                   

	           },
	            //Validate form when it is being submitted
	            formSubmitting: function (event, data) {
	            	data.form.find('input[name="dateFile"]').addClass('validate[required]');
		        	data.form.find('input[name="referenceNo"]').addClass('validate[required]');
		        	data.form.find('input[name="loanAmount"]').addClass('validate[required]');
		        	data.form.find('input[name="monthlyAmortization"]').addClass('validate[required]');
		        	data.form.find('input[name="startDateToPay"]').addClass('validate[required]');
		        	data.form.find('input[name="endDateToPay"]').addClass('validate[required]');
		        	data.form.find('input[name="remarks"]').addClass('validate[required]');
		        	data.form.find('input[name="PNNo"]').addClass('validate[required]');
		        	data.form.find('input[name="PNDate"]').addClass('validate[required]');
	            	
	            	var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
	            	
	            	if(rx.test(data.form.find('input[name="loanAmount"]').val())){
		        		if(data.form.find('input[name="loanAmount"]').val() == "0"){
							alert("Loan Amount should be numberic and should be greater than 0.");
							return false;
						}
		        	} else {
		        		alert("Loan Amount should only be numeric and greater than 0.");
		        		return false;
		        	}
					
					if(rx.test(data.form.find('input[name="monthlyAmortization"]').val())){
		        		if(data.form.find('input[name="monthlyAmortization"]').val() == "0"){
							alert("Monthly Amortization should be numberic and should be greater than 0.");
							return false;
						}
		        	} else {
		        		alert("Monthly Amortization should only be numeric and greater than 0.");		
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
	        $('#containerEmployeeLoanEntry').jtable('load');
	
	}
	
</script>

</head>
<body>
<div><c:import url="header.jsp" /></div>	
<div id="content">
 
	<div id="dashBoardLeftPannel2">
		<c:import url="searchEmployee_solr.jsp" />
		<div class="cb"></div>
		<div>
			<div id="searchHolderId" style="margin-bottom: 150px;"></div>	
		</div>
	</div>
	
	<!-- Right Side of Dashboard -->
	<div  id="dashBoardRightPannel2" width="100%">	
		<div class="dataEntryText">Employee No:</div>
	    <div class="dataEntryTextBlue" id="empNo"></div>	    
	    <div class="dataEntryText">Employee Name:</div>			    
	    <div class="dataEntryTextBlue" id="fullname"></div>	    
	    <div class="cb"></div>				    				
	  <div id="containerEmployeeLoanEntry"  class="jTableContainerDaiLeft1020"></div>
	</div>	
</div>
<div style=""><c:import url="footer.jsp" /></div>
</body>
</body>
</html>