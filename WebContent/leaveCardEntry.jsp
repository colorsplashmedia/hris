<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Employee Leave Credits Entry</title>
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
    			listAction: '/hris/GetAllLeaveCreditByEmpIdAction?empId='+empid,                
    	        createAction: '/hris/AddLeaveCreditEntryAction',
				updateAction: '/hris/UpdateLeaveCreditEntryAction'    			    		
    	    };
		} else if (addButtonEnabled == 'YES' && editButtonEnabled == 'NO') {			
			actionsJTableVar = {	    	   
	    		listAction: '/hris/GetAllLeaveCreditByEmpIdAction?empId='+empid,                
	    	    createAction: '/hris/AddLeaveCreditEntryAction'    			    		
			};
		} else if (addButtonEnabled == 'NO' && editButtonEnabled == 'YES') {			
			actionsJTableVar = {
	    		listAction: '/hris/GetAllLeaveCreditByEmpIdAction?empId='+empid,    	        	
				updateAction: '/hris/UpdateLeaveCreditEntryAction'
			};
		} else {			
			actionsJTableVar = {	    	    	
	    		listAction: '/hris/GetAllLeaveCreditByEmpIdAction?empId='+empid
			};
		}    			
		
		$('#empNo').html(empSearchMap[empid].empno);
		$('#empId').val(empid);
		$('#fullname').html(empSearchMap[empid].lastname + ", " + empSearchMap[empid].firstname);		
		
		$('#containerLeaveCreditEntry').jtable();
		$('#containerLeaveCreditEntry').jtable('destroy');
		
		
		
		  $('#containerLeaveCreditEntry').jtable({
	            title: 'Employee Leave Credits Entry',
	            actions: actionsJTableVar,
	            fields: {
	            	leaveCreditId: {
	                    key: true,
	                    list: false
	                },
	                empId: {
	                	type:'hidden',
	                	defaultValue:empid
	                },	                
	                particulars: {
	                	title: 'Particulars',
	                    type: 'textarea',
	                    list: true
	                },
	                period: {
	                    title: 'Period',                
	                    //type: 'date',
	                    displayFormat: 'yy-mm-dd',
	                    list: true
	                },	                
	                vEarned: {
	                    title: 'Vacation Leave Earned',
	                    list: true
	                },
	                vAbsenceWithPay: {
	                    title: 'Vacation Absence With Pay',
	                    list: false
	                },
	                vBalanceINCL: {
	                    title: 'Vacation Balance INCL',
	                    list: true
	                },
	                vBalanceEXCL: {
	                    title: 'Vacation Balance EXCL',
	                    list: false
	                },
	                vAbsenceWithOutPay: {
	                    title: 'Vacation Absence Without Pay',
	                    list: false
	                },
	                sEarned: {
	                    title: 'Sick Leave Earned',
	                    list: true
	                },
	                sAbsenceWithPay: {
	                    title: 'Sick Absence With Pay',
	                    list: false
	                },
	                sBalanceINCL: {
	                    title: 'Sick Balance INCL',
	                    list: true
	                },
	                sBalanceEXCL: {
	                    title: 'Sick Balance EXCL',
	                    list: false
	                },
	                sAbsenceWithOutPay: {
	                    title: 'Sick Absence Without Pay',
	                    list: false
	                },
	                exVacation: {
	                    title: 'Excess Vacation',
	                    list: false
	                }, 
	                exSick: {
	                    title: 'Excess Sick',
	                    list: false
	                }, 
	                remarks: {
	                    title: 'Remarks',
	                    type: 'textarea',
	                    list: true
	                }
	                
	            },
	            
	          //Initialize validation logic when a form is created
	          formCreated: function (event, data) {
	        	  $(data.form).addClass("custom_horizontal_form_field");
					
					
		        	
		        	var $dialogDiv = data.form.closest('.ui-dialog');
		            $dialogDiv.position({
		                my: "center",
		                at: "center",
		                of: $("#containerLeaveCreditEntry")
		            });
		            
		            $(data.form).parent().parent().css("top","150");
			        
			        data.form.find('input').css('width','200px');
		            data.form.find('textarea').css('width','200px');
		            data.form.find('select').css('width','200px');
		            data.form.find('select').css('padding','7.5px');
		            
		        	
	        	
	           if ((data.formType == 'edit')|| (data.formType == 'create')){
	        	   var $period = data.form.find ('input[name="period"]'); 
	                              
	               $period.datepicker({
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
	            	data.form.find('input[name="period"]').addClass('validate[required]');
		        	data.form.find('input[name="particulars"]').addClass('validate[required]');
	            	
	            	var rx = new RegExp(/^[0-9]\d*(\.\d+)?$/);
	            	      	
	            	
	                return data.form.validationEngine('validate');
	            },
	            //Dispose validation logic when form is closed
	            formClosed: function (event, data) {
	                data.form.validationEngine('hide');
	                data.form.validationEngine('detach');
	            }
	        });
	        $('#containerLeaveCreditEntry').jtable('load');
	
	}
	
	function viewReport (){
		
		var empId = document.getElementById("empId").value;
		
		if(empId == ""){
			alert("Please select and employee before clicking the View Leave Card Button.");
			return;
		}
			    		    	
		url = "/hris/ActionPdfExportLeaveCardReport?forExport=N&empId="+empId;
		swidth = screen.availWidth;
		sheight = screen.availHeight;
		
		openNewPopUpWindow(url, swidth, sheight)
		
	}

	function exportReport (){
		var empId = document.getElementById("empId").value;
		
		if(empId == ""){
			alert("Please select and employee before clicking the Export Leave Card Button.");
			return;
		}
			    		    	
		var oAjaxCall = $.ajax({
			type : "POST",
			url : "/hris/ActionPdfExportLeaveCardReport",
			
			data: "forExport=N&empId="+empId,
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
	
</script>

</head>
<body>
<div><c:import url="header.jsp" /></div>	
<div id="content">
 
	<div id="dashBoardLeftPannel2" style="margin-bottom: 50px;">
		<c:import url="searchEmployee_solr.jsp" />
		<div class="cb"></div>
		<div>
			<div id="searchHolderId" style="margin-bottom: 50px;"></div>	
		</div>
	</div>
	
	<!-- Right Side of Dashboard -->
	<div  id="dashBoardRightPannel2" width="100%">	
		<div style="margin-bottom: 50px; clear: both;">
			<div class="dataEntryText" style="width: 90px; margin-top: 5px;">Employee No:</div>
		    <div class="dataEntryTextBlue" id="empNo" style="width: 60px; margin-top: 5px;"></div>	    
		    <input type="hidden" name="empId" id="empId"  value="" placeholder="Employee" />
		    <div class="dataEntryText" style="width: 110px; margin-top: 5px;">Employee Name:</div>			    
		    <div class="dataEntryTextBlue" id="fullname" style="width: 140px; margin-top: 5px;"></div>	    
		    <div id="buttonPlaceHolderPrint"><div style="margin: 15px 0px 0px 15px; cursor: pointer; float: left;" class="employeeButton" onClick="viewReport();">View Leave Card</div></div>
			<div id="buttonPlaceHolderExport"><div style="margin: 15px 0px 0px 15px; cursor: pointer; float: left;" class="employeeButton" onClick="exportReport();">Export Leave Card</div></div>	  
	  	</div>
	  	<div id="containerLeaveCreditEntry"  class="jTableContainerDaiLeft1020"></div>
	</div>	
</div>
<div style=""><c:import url="footer.jsp" /></div>
</body>
</body>
</html>