<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Sub Unit</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@include file="commonHeader.jsp" %>
<%@include file="commonJtables.jsp" %>
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />
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
			<c:if test="${fm == 'fm_subUnit'}">
				isAllowed = 'YES';
			</c:if>
			<c:if test="${fm == 'fm_add_subUnit'}">
				addButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'fm_edit_subUnit'}">
				editButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'fm_delete_subUnit'}">
				deleteButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'fm_print_subUnit'}">
				printButtonEnabled = 'YES';
			</c:if>
			
			<c:if test="${fm == 'fm_export_subUnit'}">
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
    			listAction: '/hris/GetAllSubUnitAction',                
    	        createAction: '/hris/AddSubUnitAction',
				updateAction: '/hris/UpdateSubUnitAction',
				deleteAction: '/hris/DeleteSubUnitAction'
    	    };
		} else if (addButtonEnabled == 'YES' && editButtonEnabled == 'YES' && deleteButtonEnabled == 'NO') {			
			actionsJTableVar = {	    	   
	    		listAction: '/hris/GetAllSubUnitAction',                
	    	    createAction: '/hris/AddSubUnitAction',
	    	    updateAction: '/hris/UpdateSubUnitAction'
			};
		} else if (addButtonEnabled == 'YES' && editButtonEnabled == 'NO' && deleteButtonEnabled == 'NO') {			
			actionsJTableVar = {
	    		listAction: '/hris/GetAllSubUnitAction',  
	    		createAction: '/hris/AddSubUnitAction'
			};
		} else if (addButtonEnabled == 'YES' && editButtonEnabled == 'NO' && deleteButtonEnabled == 'YES') {			
			actionsJTableVar = {
	    		listAction: '/hris/GetAllSubUnitAction',  
	    		createAction: '/hris/AddSubUnitAction',
	    		deleteAction: '/hris/DeleteSubUnitAction'
			};
		} else if (addButtonEnabled == 'NO' && editButtonEnabled == 'YES' && deleteButtonEnabled == 'YES') {			
			actionsJTableVar = {
	    		listAction: '/hris/GetAllSubUnitAction',  
	    		updateAction: '/hris/UpdateSubUnitAction',
	    		deleteAction: '/hris/DeleteSubUnitAction'
			};
		} else if (addButtonEnabled == 'NO' && editButtonEnabled == 'NO' && deleteButtonEnabled == 'YES') {			
			actionsJTableVar = {
	    		listAction: '/hris/GetAllSubUnitAction',  
	    		deleteAction: '/hris/DeleteSubUnitAction'
			};
		} else {			
			actionsJTableVar = {	    	    	
	    		listAction: '/hris/GetAllSubUnitAction'
			};
		}
    	
    	 	
    	
    	//TODO
    	if(printButtonEnabled == 'YES' && exportButtonEnabled == 'YES'){    		
    		toolBarsJTableVar = {    	    		    	    	
    				items: [{
                        tooltip: 'Click here to export this table to excel',
                        icon: '/hris/images/excel.png',
                        text: 'Export to Excel',
                        click: function () {
                        	var oAjaxCall = $.ajax({
	                    		type : "POST",
	                    		url : "/hris/ActionPdfExportFMDepartmentListReport",	                    		
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
                        	window.open('ActionPdfExportFMDepartmentListReport?name=' + $('#name').val()+"&forExport=N", '_blank');
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
	                    	window.open('ActionPdfExportFMDepartmentListReport?name=' + $('#name').val()+"&forExport=N", '_blank');
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
	                    		url : "/hris/ActionPdfExportFMDepartmentListReport",	                    		
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
    	
        $('#containerSubUnit').jtable({
            title: 'List of Sub Units',
            paging: true,
            pageSize: 10,
            sorting: true,
            defaultSorting: 'subUnitName ASC',
            actions: actionsJTableVar,
            toolbar: toolBarsJTableVar,            
            fields: {
            	subUnitId: {
                    key: true,
                    list: false
                },
                subUnitName: {
                    title: 'Sub Unit Name',
                    edit : true 
                },
                unitId: {
                    title: 'Unit',
                    edit: true,
                    list: true,
                    create:true,
                    options:  
	                   	function(data) {
							if (data.source == 'list') {
			                	return '/hris/GetAllUnitAction?forListJTable=true&unitId='+data.record.unitId;
							}
			                if (data.source == 'create') {
			                	return '/hris/GetAllUnitAction?displayOption=true&forEdit=false';
			                }
			                if (data.source == 'edit') {
			                	return '/hris/GetAllUnitAction?displayOption=true&forEdit=true&unitId='+data.record.unitId;
							}
                    	}                                    
                },
                empId: {
                    title: 'Sub Unit Head',
                    edit: true,
                    list: true,
                    create:true,
                    options:  
	                   	function(data) {
							if (data.source == 'list') {
			                	return '/hris/GetAllEmployeeAction?forListJTable=true&empId='+data.record.empId;
								//return '/hris/GetEmployeeAction?displayOption=true&empId='+data.record.empId;
							}
			                if (data.source == 'create') {
			                	return '/hris/GetAllEmployeeAction?displayOption=true&forEdit=false';
			                }
			                if (data.source == 'edit') {
			                	return '/hris/GetAllEmployeeAction?displayOption=true&forEdit=true&empId='+data.record.empId;
							}
                    	}                                    
                },
                assistantId: {
                    title: 'Assnt Sub Unit Head',
                    edit: true,
                    list: true,
                    create:true,
                    options:  
	                   	function(data) {
							if (data.source == 'list') {
								return '/hris/GetAllEmployeeAction?forListJTable=true&empId='+data.record.assistantId;
								//return '/hris/GetEmployeeAction?displayOption=true&empId='+data.record.assistantId;
							}
			                if (data.source == 'create') {
			                	return '/hris/GetAllEmployeeAction?displayOption=true&forEdit=false';
			                }
			                if (data.source == 'edit') {
			                	return '/hris/GetAllEmployeeAction?displayOption=true&forEdit=true&empId='+data.record.assistantId;
							}
                    	}                                    
                }
                
                
            },
            formCreated: function(event, data){
            	
            	data.form.find('input').css('width','200px');
            	data.form.find('select').css('width','200px');
	            data.form.find('select').css('padding','7.5px');
            	
            	data.form.find('input[name="unitName"]').addClass('validate[required]');
            	data.form.validationEngine();
            },
            //Validate form when it is being submitted
            formSubmitting: function (event, data) {
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
            $('#containerSubUnit').jtable('load', {
                name: $('#name').val()
            });
        });
 
        //Load all records when page is first shown
        $('#searchButton').click();
        
        //$('#containerSection').jtable('load');
    });
</script>

</head>
<body>
<div><c:import url="header.jsp" /></div>
<div id="content">
	<div class="cb" style="height: 20px;"></div>
	<div class="filtering" style="width: 574px;">
    
        Sub Unit Name: &nbsp;&nbsp;&nbsp;<input type="text" name="name" id="name" size="40" onKeyPress="javascript:enterPressFunc(event);" />        
        
        <div style="cursor: pointer;" id="searchButton">SEARCH</div>
    
	</div>
	<div id="containerSubUnit"  class="jTableContainerDai"></div>
</div>	
<div><c:import url="footer.jsp" /></div>
</body>
</html>