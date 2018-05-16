<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Holidays</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@include file="commonHeader.jsp" %>
<%@include file="commonJtables.jsp" %>
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />
<script type="text/javascript" src="js/common.js"></script>
<link href="js/validationEngine/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/validationEngine/jquery.validationEngine.js"></script>
<script type="text/javascript" src="js/validationEngine/jquery.validationEngine-en.js"></script>
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
    	var addButtonEnabled = 'NO';
    	var editButtonEnabled = 'NO';
    	var deleteButtonEnabled = 'NO';
    	var exportButtonEnabled = 'NO';
    	var printButtonEnabled = 'NO';
    	var actionsJTableVar = {    			 
    	};    	
    	var toolBarsJTableVar = { 			 
    	};
    	
    	<c:forEach var="fm" items="${sessionScope.moduleAccess.fileManagementList}">
			<c:if test="${fm == 'fm_holidays'}">
				isAllowed = 'YES';
			</c:if>
			<c:if test="${fm == 'fm_add_holidays'}">
				addButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'fm_edit_holidays'}">
				editButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'fm_delete_holidays'}">
				deleteButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'fm_print_holidays'}">
				printButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'fm_export_holidays'}">
				exportButtonEnabled = 'YES';
			</c:if>
		</c:forEach>
    	    	
    	
    	if(isAllowed == 'NO'){
    		alert("You are not Viewed to view the page. Please login.");
    		window.location = "LogoutAction";
    		return;
    	}
    	
    	
    	if(addButtonEnabled == 'YES' && editButtonEnabled == 'YES' && deleteButtonEnabled == 'YES'){    		
    		actionsJTableVar = {    	    		    	    	
    			listAction: '/hris/GetAllHolidayAction',                
    	        createAction: '/hris/SaveHolidayAction',
				updateAction: '/hris/UpdateHolidayAction',
				deleteAction: '/hris/DeleteHolidaysAction'
    	    };
		} else if (addButtonEnabled == 'YES' && editButtonEnabled == 'YES' && deleteButtonEnabled == 'NO') {			
			actionsJTableVar = {	    	   
	    		listAction: '/hris/GetAllHolidayAction',                
	    	    createAction: '/hris/SaveHolidayAction',
	    	    updateAction: '/hris/UpdateHolidayAction'
			};
		} else if (addButtonEnabled == 'YES' && editButtonEnabled == 'NO' && deleteButtonEnabled == 'NO') {			
			actionsJTableVar = {
	    		listAction: '/hris/GetAllHolidayAction',  
	    		createAction: '/hris/SaveHolidayAction'
			};
		} else if (addButtonEnabled == 'YES' && editButtonEnabled == 'NO' && deleteButtonEnabled == 'YES') {			
			actionsJTableVar = {
	    		listAction: '/hris/GetAllHolidayAction',  
	    		createAction: '/hris/SaveHolidayAction',
	    		deleteAction: '/hris/DeleteHolidaysAction'
			};
		} else if (addButtonEnabled == 'NO' && editButtonEnabled == 'YES' && deleteButtonEnabled == 'YES') {			
			actionsJTableVar = {
	    		listAction: '/hris/GetAllHolidayAction',  
	    		updateAction: '/hris/UpdateHolidayAction',
	    		deleteAction: '/hris/DeleteHolidaysAction'
			};
		} else if (addButtonEnabled == 'NO' && editButtonEnabled == 'NO' && deleteButtonEnabled == 'YES') {			
			actionsJTableVar = {
	    		listAction: '/hris/GetAllHolidayAction',  
	    		deleteAction: '/hris/DeleteHolidaysAction'
			};
		} else {			
			actionsJTableVar = {	    	    	
	    		listAction: '/hris/GetAllHolidayAction'
			};
		}    	
    	
    	
    	//if(addButtonEnabled == 'YES' && editButtonEnabled == 'YES'){    		
    	//	actionsJTableVar = {    	    		    	    	
    	//		listAction: '/hris/GetAllHolidayAction',                
    	//        createAction: '/hris/SaveHolidayAction',
		//		updateAction: '/hris/UpdateHolidayAction'    			    		
    	//    };
		//} else if (addButtonEnabled == 'YES' && editButtonEnabled == 'NO') {			
		//	actionsJTableVar = {	    	   
	   // 		listAction: '/hris/GetAllHolidayAction',                
	    //	    createAction: '/hris/SaveHolidayAction'    			    		
		//	};
		//} else if (addButtonEnabled == 'NO' && editButtonEnabled == 'YES') {			
		//	actionsJTableVar = {
	    //		listAction: '/hris/GetAllHolidayAction',    	        	
		//		updateAction: '/hris/UpdateHolidayAction'
		//	};
		//} else {			
		//	actionsJTableVar = {	    	    	
	    //		listAction: '/hris/GetAllHolidayAction'
		//	};
		//}    	
    	
    	
    	if(printButtonEnabled == 'YES' && exportButtonEnabled == 'YES'){    		
    		toolBarsJTableVar = {    	    		    	    	
    				items: [{
                        tooltip: 'Click here to export this table to excel',
                        icon: '/hris/images/excel.png',
                        text: 'Export to Excel',
                        click: function () {
                        	var oAjaxCall = $.ajax({
	                    		type : "POST",
	                    		url : "/hris/ActionPdfExportFMHolidayListReport",	                    		
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
                        	window.open('ActionPdfExportFMHolidayListReport?name=' + $('#name').val()+"&forExport=N", '_blank');
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
	                    	window.open('ActionPdfExportFMHolidayListReport?name=' + $('#name').val()+"&forExport=N", '_blank');
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
	                    		url : "/hris/ActionPdfExportFMHolidayListReport",	                    		
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
    	
        $('#containerHoliday').jtable({
            title: 'List of Holidays',
            paging: true,
            pageSize: 10,
            sorting: true,
            defaultSorting: 'holidayDate ASC',
            actions: actionsJTableVar,
            toolbar: toolBarsJTableVar,
            fields: {
            	holidayId: {
                    key: true,
                    list: false
                },
                holidayName: {
                    title: 'Name',
                    edit : true 
                },
                holidayDate: {
                    title: 'Date of Holiday',
                    edit : true 
                },
                holidayType: {
                    title: 'Type',
                    edit : true,
                    options:  [{ Value: 'R', DisplayText: 'Regular Holiday' }, { Value: 'S', DisplayText: 'Special Non Working' }, { Value: 'D', DisplayText: 'Double Holiday' }]
                }                
                
            },
            formCreated: function(event, data){
            	
            	 $(data.form).parent().parent().css("top","150");
            	 
            	data.form.find('input').css('width','200px');
             	data.form.find('select').css('width','200px');
 	            data.form.find('select').css('padding','7.5px');
		         
            	var $dtHoliday = data.form.find ('input[name="holidayDate"]');     
                //manually activate the datepicker, and supply the params from useDPicker (you need to do this so that when date is selected, the date format is followed.)
                //and you need to add val in order to display the value saved in the DB in the proper format, if not, it will become Mon dd, yyyy which will fail.
    			
                /*
                if (data.formType == 'edit'){
                    $dtHoliday.datepicker({
                		changeMonth : true,
                		changeYear : true,
                		dateFormat : 'yy-mm-dd',
                		yearRange : '1910:2100',
                	    beforeShow: function(input, inst){	            	    	
                	           $(".ui-datepicker").css('font-size', 11);
                	    }
                	}).val(moment(data.record.fromDate).format('YYYY-MM-DD'));	
    				
    			}
    			$dtHoliday.datepicker({
            		changeMonth : true,
            		changeYear : true,
            		dateFormat : 'yy-mm-dd',
            		yearRange : '1910:2100',
            	    beforeShow: function(input, inst){	            	    	
         	           $(".ui-datepicker").css('font-size', 11);
         	        }
            	});
    			*/
    			
            	if (data.formType == 'edit'){
            		$dtHoliday.datepicker({
                		changeMonth : true,
                		changeYear : true,
                		dateFormat : 'yy-mm-dd',
                		yearRange : '1910:2100',
                	    beforeShow: function(input, inst){	            	    	
                	           $(".ui-datepicker").css('font-size', 11);
                	    }
                	}).val(moment(data.record.fromDate).format('YYYY-MM-DD'));		
    			}
    			$dtHoliday.datepicker({
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
            	data.form.find('input[name="holidayName"]').addClass('validate[required]');
            	data.form.find('input[name="holidayDate"]').addClass('validate[required]');
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
            $('#containerHoliday').jtable('load', {
                name: $('#name').val()
            });
        });
 
        //Load all records when page is first shown
        $('#searchButton').click();
        
        //$('#containerHoliday').jtable('load');
    });
</script>

</head>
<body>
<div><c:import url="header.jsp" /></div>
<div id="content">
	<div class="cb" style="height: 20px;"></div>
	<div class="filtering" style="width: 774px;">
    
        Holiday Name: &nbsp;&nbsp;&nbsp;<input type="text" name="name" id="name" size="40" onKeyPress="javascript:enterPressFunc(event);" />        
        
        <div style="cursor: pointer;" id="searchButton">SEARCH</div>
    
	</div>
	<div id="containerHoliday" class="jTableContainerDaiExtended"></div>		
</div>	
<div><c:import url="footer.jsp" /></div>
</body>
</html>