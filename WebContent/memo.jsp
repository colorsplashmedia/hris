<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Memo</title>
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
    	
    	<c:forEach var="fm" items="${sessionScope.moduleAccess.employeeList}">
			<c:if test="${fm == 'em_memo'}">
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
    	
    	<c:forEach var="fm" items="${sessionScope.moduleAccess.employeeList}">
		
			<c:if test="${fm == 'em_add_memo'}">
				addButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'em_edit_memo'}">
				editButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'em_print_memo'}">
				printButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'em_export_memo'}">
				exportButtonEnabled = 'YES';
			</c:if>
		</c:forEach>
    	    	
    	
    	
    	
    	if(addButtonEnabled == 'YES' && editButtonEnabled == 'YES'){    		
    		actionsJTableVar = {    	    		    	    	
    			listAction: '/hris/GetEmployeeMemoByToRecipientAction?toRecipientEmpId='+empid,                
    	        //createAction: '/hris/AddMemoAction',
				updateAction: '/hris/UpdateMemoAction'    			    		
    	    };
		} else if (addButtonEnabled == 'YES' && editButtonEnabled == 'NO') {			
			actionsJTableVar = {	    	   
	    		listAction: '/hris/GetEmployeeMemoByToRecipientAction?toRecipientEmpId='+empid //,                
	    	    //createAction: '/hris/AddMemoAction'    			    		
			};
		} else if (addButtonEnabled == 'NO' && editButtonEnabled == 'YES') {			
			actionsJTableVar = {
	    		listAction: '/hris/GetEmployeeMemoByToRecipientAction?toRecipientEmpId='+empid,    	        	
				updateAction: '/hris/UpdateMemoAction'
			};
		} else {			
			actionsJTableVar = {	    	    	
	    		listAction: '/hris/GetEmployeeMemoByToRecipientAction?toRecipientEmpId='+empid
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
	                    		url : "/hris/ActionPdfExportFMMemoListReport",	                    		
	                    		data: "forExport=Y"+"&toRecipientEmpId="+empid,
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
                        	window.open('ActionPdfExportFMMemoListReport?forExport=N'+'&toRecipientEmpId='+empid, '_blank');
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
	                    	window.open('ActionPdfExportFMMemoListReport?forExport=N'+'&toRecipientEmpId='+empid, '_blank');
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
	                    		url : "/hris/ActionPdfExportFMNotificationListReport",	                    		
	                    		data: "forExport=Y"+"&toRecipientEmpId="+empid,
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
		
		$('#containerMemo').jtable();
		$('#containerMemo').jtable('destroy');
		
			  $('#containerMemo').jtable({
	            title: 'Memo',
	            paging: true,
	            pageSize: 10,
	            sorting: true,
	            defaultSorting: 'memoFiledDate ASC',
	            actions: actionsJTableVar,
	            toolbar: toolBarsJTableVar,
	            formCreated : function(event, data){
		            
		            $(data.form).parent().parent().css('top',155);
		            
		            data.form.find('input').css('width','180px');
		            data.form.find('textarea').css('width','180px');
		            
	            	
        		
	            	
	            	var $mfd = data.form.find ('input[name="memoFiledDate"]');   
	            	
                    $mfd.datepicker({
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
	            	employeeMemoId: {
	                    key: true,
	                    list: false
	                },
	                toRecipientEmpId: {
	                	type:'hidden',
	                	defaultValue:empid
	                },	               
	                memoFiledDate: {
	                    title: 'Date Filed',                
	                    //type: 'date',
	                    displayFormat: 'yy-mm-dd'
	                },	                
	                ccRecipient: {
	                    title: 'CC'
	                },
	                fromSender: {
	                    title: 'From'
	                },
	                subject: {
	                    title: 'Subject'
	                },
	                message: {
	                    title: 'Message',
	                    type: 'textarea'
	                },

	                remarks: {
	                    title: 'Remarks'
	                }
	            },
	          //Initialize validation logic when a form is created
	          //  formCreated: function (event, data) {

	            //    if (data.formType == "create")
	             //   {

	                   

	                	//$( '#Edit-dateFile' ).datepicker();
	                	//$( '#Edit-startDateToPay' ).datepicker();

	               // }
	           // }	            
	            //Validate form when it is being submitted
	            formSubmitting: function (event, data) {
	            	data.form.find('input[name="memoFiledDate"]').addClass('validate[required]');
	            	data.form.find('input[name="subject"]').addClass('validate[required]');
	            	data.form.find('textarea[name="message"]').addClass('validate[required]');
	            	data.form.find('input[name="remarks"]').addClass('validate[required]');
	            	data.form.find('input[name="fromSender"]').addClass('validate[required]');
	            	
	                return data.form.validationEngine('validate');
	            },
	            //Dispose validation logic when form is closed
	            formClosed: function (event, data) {
	                data.form.validationEngine('hide');
	                data.form.validationEngine('detach');
	            }
	        });
			
			
			  
			$('#containerMemo').jtable('load');
	
	}
	
</script>

<script type="text/javascript">

function redirect() {	
	window.location = "memoNew.jsp";		
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
	
	<!-- Right Side of Dashboard -->
	<div  id="dashBoardRightPannel2" width="100%">	
		<div class="dataEntryText">Employee No:</div>
	    <div class="dataEntryTextBlue" id="empNo"></div>	    
	    <div class="dataEntryText">Employee Name:</div>			    
	    <div class="dataEntryTextBlue" id="fullname"></div>	    
	    <div class="cb"></div>	    
	    				
	  	<div id="containerMemo"  class="jTableContainerDaiLeftLong"></div>
	</div>	
</div>
<div style=""><c:import url="footer.jsp" /></div>
</body>
</body>
</html>