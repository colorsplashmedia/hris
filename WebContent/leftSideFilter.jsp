<div style="width: 420px; border: 1px solid black; height: 260px; margin: 0px 0px 0px 15px;">
	<div style="background-color: black; color: white; padding: 10px;">SEARCH FILTER EMPLOYEE</div>
	<div class="searchTxt" style="width: 120px; border: 0px solid black;">Section</div>
	<div style="float: left; margin: 5px 0px 0px 0px;" >
	   	<select name="sectionId" id="sectionDropDownID" style="width: 250px; padding: 6.5px;" >										
							
		</select>			    	
	</div>
	<div class="cb"></div>
	<div class="searchTxt" style="width: 120px; border: 0px solid black;">Unit</div>
	<div style="float: left; margin: 5px 0px 0px 0px;" >
	   	<select name="unitId" id="unitDropDownID" style="width: 250px; padding: 6.5px;" >										
					
		</select>			    	
	</div>
	<div class="cb"></div>
	<div class="searchTxt" style="width: 120px; border: 0px solid black;">Personnel Type</div>
	<div style="float: left; margin: 5px 0px 0px 0px;" >
	   	<select name="personnelTypeId" id="personnelTypeDropDownID" style="width: 250px; padding: 6.5px;" >										
					
		</select>			    	
	</div>
	<div class="cb"></div>
	<div class="searchTxt" style="width: 120px; border: 0px solid black;">Plantilla</div>
	<div style="float: left; margin: 5px 0px 0px 0px;" >
	   	<select name="plantillaId" id="plantillaDropDownID" style="width: 250px; padding: 6.5px;" >										
					
		</select>			    	
	</div>	
	<div class="cb"></div>
	<div style="cursor: pointer;" id="addSearchButton" onClick="addEmployeeCommon();">ADD EMPLOYEES</div>
</div>	
<div class="cb" style="height: 20px;"></div>		
<div style="width: 420px; border: 1px solid black; height: 120px; margin: 0px 0px 0px 15px;">
	<div style="background-color: black; color: white; padding: 10px;">SEARCH FILTER EMPLOYEE</div>
	<div id="itemSearchContainer">
		<div class="searchTxt">Enter Employee Name you wish to search</div>
		<div class="cb"></div>
		<div>			
			<input id="searchBox" name="searchBox" onkeyup="searchEmployee();" type="text" size="35" placeholder="Type Employee Name here" />
			<div style="cursor: pointer;" id="searchButton" onClick="searchEmployeeCommon();">SEARCH</div>
		</div>		 
	</div>			
	<div class="cb" style="height: 30px;"></div>
	<div>
		<div id="searchHolderId" style="margin-bottom: 50px;"></div>	
	</div>
	<div class="cb" style="height: 300px;"></div>
</div>