<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<link href="jtables/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jtables/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="jtables/jquery-ui-1.10.0.min.js"></script>
<script type="text/javascript">
//global variable
var empSearchMap = {};


$(document).ready(function () {					
			
});

function searchEmployee (){
	var searchKeyword = document.getElementById("searchBox").value;		
	
	var url = '/hris/SelectEmployeeAction?searchByText=true&oSearchText=';		
		
	var oAjaxCall = $.ajax({
			type : "POST",
			url : url + searchKeyword,
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {
				var divs = '';
				divs +="<table id=\"box-table-a1\" width=\"980px;\" style=\"margin: 15px 0px 0px 15px;\"     >";				    
				divs +="<thead><tr>";					
				divs +="<th scope=\"col\" width=\"70px;\">Emp No</th>";
				divs +="<th scope=\"col\">Name</th>";
				divs +="<th scope=\"col\">Section</th>";
				divs +="<th scope=\"col\">Unit</th></tr></thead><tbody>";
				
				jQuery.each(data.Records, function(i, item) {
					//empSearchMap[item.empId] = { empid: item.empId, firstname:item.firstname, lastname:item.lastname, empno:item.empNo, sectionName:item.sectionName, unitName:item.unitName};			  
											  
					divs +="<tr onclick=\"editEmployee("+item.empId+")\" style='cursor:pointer;'>";	
					divs +="<td>" + item.empNo + "</td>";
					divs +="<td>" + item.lastname + ', ' + item.firstname + "</td>";		
					divs +="<td>" + item.sectionName + "</td>";
					divs +="<td>" + item.unitName + "</td>";
					divs +="</tr>"  				  
					
				});
				  	
				divs +="</tbody></table>";
					
				$('#searchHolderId').html(divs);

			},
			error : function(data) {
				alert('error searchEmployee(): ' + data);
			}

		});
	
}



function addEmployee(){
	window.location = "addEmployee.jsp";
}

function editEmployee(empid){
	window.location = "updateEmployee.jsp?empId=" + empid;
}


</script>
</head>
<body>
	<div style="width: 690px; margin: 0 auto;">
		<div class="searchTxt">Enter First Name or Last Name or Employee number</div>
		<div class="cb"></div>
		<div>			
			<a href=""></a>
			<input id="searchBox" name="searchBox" type="text" size="40" onkeyup="searchEmployee();" placeholder="Type keyword here" />
			<div style="cursor: pointer; float: left;" id="searchButton2" onClick="searchEmployee();">SEARCH</div>
			<div style="cursor: pointer; float: left;" id="searchButton2" onclick="addEmployee();">ADD EMPLOYEE</div>
			
		</div>		 
	</div>	
</body>
</html>
