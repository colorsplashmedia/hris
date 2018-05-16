<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Shifting Schedule</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">




<%@include file="commonHeader.jsp" %>
<%@include file="commonJtables.jsp" %>



<script type="text/javascript" src="colorpicker/jquery.simplecolorpicker.js"></script>
<link rel="stylesheet" href="colorpicker/jquery.simplecolorpicker-glyphicons.css">
<link rel="stylesheet" href="colorpicker/jquery.simplecolorpicker.css">
<link rel="stylesheet" href="colorpicker/jquery.simplecolorpicker-regularfont.css">  
<link rel="stylesheet" href="colorpicker/jquery.simplecolorpicker-fontawesome.css">
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />

<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link href="js/validationEngine/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/validationEngine/jquery.validationEngine.js"></script>
<script type="text/javascript" src="js/validationEngine/jquery.validationEngine-en.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery.timepicker.css" />
<script type="text/javascript" src="js/jquery.timepicker.js"></script>




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
    	var exportButtonEnabled = 'NO';
    	var printButtonEnabled = 'NO';
    	var actionsJTableVar = {    			 
    	};    	
    	var toolBarsJTableVar = { 			 
    	};
    	
    	<c:forEach var="fm" items="${sessionScope.moduleAccess.timeManagementList}">
			<c:if test="${fm == 'tm_create_employee_shift'}">
				isAllowed = 'YES';
			</c:if>
			<c:if test="${fm == 'tm_add_create_employee_shift'}">
				addButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'tm_edit_create_employee_shift'}">
				editButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'tm_print_create_employee_shift'}">
				printButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'tm_export_create_employee_shift'}">
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
    			listAction: '/hris/GetAllShiftingSchedule',                
    	        createAction: '/hris/AddShifitingSchedule',
				updateAction: '/hris/UpdateShifitingSchedule'    			    		
    	    };
		} else if (addButtonEnabled == 'YES' && editButtonEnabled == 'NO') {			
			actionsJTableVar = {	    	   
	    		listAction: '/hris/GetAllShiftingSchedule',                
	    	    createAction: '/hris/AddShifitingSchedule'    			    		
			};
		} else if (addButtonEnabled == 'NO' && editButtonEnabled == 'YES') {			
			actionsJTableVar = {
	    		listAction: '/hris/GetAllShiftingSchedule',    	        	
				updateAction: '/hris/UpdateShifitingSchedule'
			};
		} else {			
			actionsJTableVar = {	    	    	
	    		listAction: '/hris/GetAllShiftingSchedule'
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
	                    		url : "/hris/ActionPdfExportFMShiftingScheduleListReport",	                    		
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
                        	window.open('ActionPdfExportFMShiftingScheduleListReport?name=' + $('#name').val()+"&forExport=N", '_blank');
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
	                    	window.open('ActionPdfExportFMShiftingScheduleListReport?name=' + $('#name').val()+"&forExport=N", '_blank');
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
	                    		url : "/hris/ActionPdfExportFMShiftingScheduleListReport",	                    		
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
    	
        $('#containerShiftingSchedule').jtable({
            title: 'Shifting Schedule',
            paging: true,
            pageSize: 10,
            sorting: true,
            defaultSorting: 'description ASC',
            actions: actionsJTableVar,
            toolbar: toolBarsJTableVar,
            formCreated : function(event, data){

            	$(data.form).addClass("custom_horizontal_form_field");
				
				
	        	
	        	var $dialogDiv = data.form.closest('.ui-dialog');
	            $dialogDiv.position({
	                my: "center",
	                at: "center",
	                of: $("#containerShiftingSchedule")
	            });
            	
	            $(data.form).parent().parent().css("margin","50");
            	$(data.form).parent().parent().css("top","120");
            	
            	data.form.find('input').css('width','200px');
	            data.form.find('select').css('width','200px');
	            data.form.find('select').css('padding','7.5px');
            	
            	
    			data.form.find('select[name="colorAssignment"]').simplecolorpicker({theme: 'regularfont'});
    			
            	//to make the form popup when adding/updating visible
            	  var timeIn = data.form.find ('input[name="timeIn"]');
            	  timeIn.timepicker({ 'timeFormat': 'H:i' });
            	  var timeOut = data.form.find ('input[name="timeOut"]');
            	  timeOut.timepicker({ 'timeFormat': 'H:i' });
            	  
  	        	
	        	
	        	data.form.validationEngine();
            },
            fields: {
            	shiftingScheduleId: {
                    key: true,
                    list: false
                },
                shiftType: {
                    title: 'Shift Type',
                    edit : true ,
                    options:  [{ Value: 'F', DisplayText: 'Fixed' }, { Value: 'G', DisplayText: 'Gliding' }, { Value: 'S', DisplayText: 'Shifting' }]
                },
                timeIn: {
                    title: 'Time In',
                    edit : true 
                },
                timeOut: {
                    title: 'Time Out',
                    edit : true 
                },
                description: {
                    title: 'Description',
                    edit : true 
                },
                noOfHours: {
                    title: '# of Hours',
                    edit : true 
                },
                colorAssignment: {
                	title: 'Color',
                	edit: true,
                	options: { '#7bd148': 'Green', '#5484ed': 'Bold blue', '#000088': 'Dark Blue', '#46d6db': 'Turquoise', '#51b749' : 'Bold green', '#FFFF00' : 'Yellow', '#FF8C00' : 'Dark Orange', '#ff887c' : 'Red', '#dc2127' : 'Bold red', '#880088' : 'Dark Magenta', '#dbadff' : 'Purple', '#FF00FF' : 'Magenta', '#708090' : 'Slate Gray', '#C0C0C0' : 'Silver' }
                }
                
                
                
            },
            //Validate form when it is being submitted
            formSubmitting: function (event, data) {
            	data.form.find('input[name="timeIn"]').addClass('validate[required]');
	        	data.form.find('input[name="timeOut"]').addClass('validate[required]');
	        	data.form.find('input[name="description"]').addClass('validate[required]');
	        	data.form.find('input[name="colorAssignment"]').addClass('validate[required]');
	        	
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
        
        $('#searchButton').click(function (e) {
            e.preventDefault();
            $('#containerShiftingSchedule').jtable('load', {
                name: $('#name').val()
            });
        });
 
        //Load all records when page is first shown
        $('#searchButton').click();
        //$('#containerShiftingSchedule').jtable('load');
    });
    
    
</script>

</head>
<body>
<div><c:import url="header.jsp" /></div>
<div id="content">
	<div class="cb" style="height: 20px;"></div>
	<div class="filtering" style="width: 774px;">
    
        Shift Description: &nbsp;&nbsp;&nbsp;<input type="text" name="name" id="name" size="40" onKeyPress="javascript:enterPressFunc(event);" />        
        
        <div style="cursor: pointer;" id="searchButton">SEARCH</div>
    
	</div>
	<div id="containerShiftingSchedule"  class="jTableContainerDaiExtended"></div>
</div>	
<div><c:import url="footer.jsp" /></div>
</body>
</html>