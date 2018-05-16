<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-store, no-cache, must-revalidate" />
<meta http-equiv="Pragma" content="no-store, no-cache" />
<meta http-equiv="Expires" content="0" />
<title>TIME ENTRY SCREEN</title>


<link rel="stylesheet" type="text/css" href="css/navstyle.css" />
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.11.4/jquery-ui.js"></script>


<link rel="stylesheet" href="js/jquery-ui-1.11.4/jquery-ui.css">
	
<script type="text/javascript">

$(document).ready(function() {
    var serialNo1 = '1111';
    var serialNo2 = '2222';
	$( "#dialog" ).dialog();
	
	
	 $( "input[type=submit], a, button" )
     .button()
     .click(function( event ) {
       	event.preventDefault();      	
      	serialNo1 = $( "#bioSerialNo1" ).val();    
      	serialNo2 = $( "#bioSerialNo2" ).val();    
       	
      	setInterval(function() { fnGetCheckinCheckoutBySN(serialNo1, 'window1'); },2000);
      	setInterval(function() { fnGetCheckinCheckoutBySN(serialNo2, 'window2'); },2000);
       	
      	$( "#dialog" ).dialog('close');
     });
	 
	


});

function clickButtonToGetTimeEntrySample() {
	var serialNo = '6796153600003';
	fnGetCheckinCheckoutBySN(serialNo);
}

function clearMessages(window) {
	if(window == 'window1'){
		$('#errorMessageId').html("");	
		$('#nameId').html("");	
		$('#empNoId').html("");	
		$('#timeInId').html("");	
		$('#timeOutId').html("");	
		$('#imgId').attr('src',"");
	} else if(window == 'window2'){
		$('#errorMessageId2').html("");	
		$('#nameId2').html("");	
		$('#empNoId2').html("");	
		$('#timeInId2').html("");	
		$('#timeOutId2').html("");	
		$('#imgId2').attr('src',"");
	}
}
function fnGetCheckinCheckoutBySN(oSN, window) {
	
	//alert("232312");
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "/hris/DisplayKioskTimeEntryAction",
		
		data: "sn=" + oSN,
		cache : false,
		async : false,
		success : function(data) {

			//var imgLocation = "images/noimage_2.gif";
			
			var imgLocation = data.picLoc;
			var msgText = data.errorMessage;
			var name = data.firstname + " " + data.lastname;
			var empNo = data.empNo;
			
			//msgText = "NOTINSHIFT";
			//name = "Ian Alfred Orozco";
			//empNo = "123456";
			
			 
			if(msgText == 'NOLATESTBIOENTRY') {
				// DO NOTHING
				
			} else if(msgText == 'NOMATCHEMP'){
				
				clearMessages(window);		
				
				if(window == 'window1'){
					$('#errorMessageId').html('No matching employee id in DB, please check with ADMIN');	
					$('#nameId').html("");	
					$('#empNoId').html("");	
					$('#timeInId').html("");	
					$('#timeOutId').html("");	
					$('#imgId').attr('src',"");
				} else if(window == 'window2'){
					$('#errorMessageId2').html('No matching employee id in DB, please check with ADMIN');	
					$('#nameId2').html("");	
					$('#empNoId2').html("");	
					$('#timeInId2').html("");	
					$('#timeOutId2').html("");	
					$('#imgId2').attr('src',"");
				}				

			} else if (data.firstname.length > 0 )  {				
				
				clearMessages(window);
				
				if(window == 'window1'){
					$('#nameId').html(data.firstname + " " + data.lastname);	
					$('#empNoId').html("Employee Number: " + data.empNo);	
					$('#timeInId').html("Time In: " + data.timeIn);	
					$('#timeOutId').html("Time Out: " +data.timeOut);	
					$('#imgId').attr('src',imgLocation);	
				} else if(window == 'window2'){
					$('#nameId2').html(data.firstname + " " + data.lastname);	
					$('#empNoId2').html("Employee Number: " + data.empNo);	
					$('#timeInId2').html("Time In: " + data.timeIn);	
					$('#timeOutId2').html("Time Out: " +data.timeOut);	
					$('#imgId2').attr('src',imgLocation);	
				}		
						
				
			}

			
			
		},
		error : function(data) {
			//alert('error: ' + data);
		}

	});
};

function changeImage(id) {
	//alert(id);
	var imgLocation = "images/noimage_2.gif";
	$('#'+id).attr('src',imgLocation);
	//$('#imgId2').attr('src',imgLocation);
}

</script>




</head>
<body>
<div style="width: 100%; margin: 0px auto;">
	<div id="timeEnryContentDual">
		<div id="timeEntryLeftPannelDual">
			<div style="width: 330px; border: 0px solid green; margin: 0 auto 0 auto;">
				<!-- Employee Information -->
				<div style="border: 0px solid black;">
					<img src="" id="imgId" width="330px" height="430px" onerror="changeImage('imgId');" style="margin: 0 auto 0 auto;" />
				</div>	
				<!-- Employee Info -->
				<div style="border: 3px solid white; text-indent: 10px; height: 130px; margin-top: 0px;">					
					<div id="errorMessageId"></div>					
					<div class="employeeNameDetailsTimeAreaDual" id="nameId">Name:</div>
					<div class="employeeDetailsTimeAreaDual" id="empNoId">Emp No:</div>
					<div class="employeeDetailsTimeAreaDual" id="timeInId">Time In:</div>
					<div class="employeeDetailsTimeAreaDual" id="timeOutId">Time Out:</div>
				</div>
			</div>
		</div>
		<!-- Right Side of Dashboard -->
		<div id="timeEntryRightPannelDual">
			<div style="width: 330px; border: 0px solid green; margin: 0 auto 0 auto;">
				<!-- Employee Information -->
				<div style="border: 0px solid black;">
					<img src="" id="imgId2" width="330px" height="430px" onerror="changeImage('imgId2');" style="margin: 0 auto 0 auto;" />
				</div>	
				<!-- Employee Info -->
				<div style="border: 3px solid white; text-indent: 10px; height: 130px; margin-top: 0px;">					
					<div id="errorMessageId2"></div>					
					<div class="employeeNameDetailsTimeAreaDual" id="nameId2">Name:</div>
					<div class="employeeDetailsTimeAreaDual" id="empNoId2">Emp No:</div>
					<div class="employeeDetailsTimeAreaDual" id="timeInId2">Time In:</div>
					<div class="employeeDetailsTimeAreaDual" id="timeOutId2">Time Out:</div>
				</div>
			</div>
		</div>
		<div class="cb"></div>		
	</div>
</div>
<!--
<input type="button" value="OK" onclick="clickButtonToGetTimeEntrySample();">
-->


<div id="dialog" title="">
	<label for="name">Biometrics Device Serial Numbers</label>
	<input type="text"  id="bioSerialNo1" class="text ui-widget-content ui-corner-all">
	<input type="text"  id="bioSerialNo2" class="text ui-widget-content ui-corner-all">
	<input type="submit" value="OK">  
</div>

<!--
<div id="dialog2" title="">
	<label for="name">2nd Biometrics serial number</label>
	<input type="text"  id="bioSerialNo2" class="text ui-widget-content ui-corner-all">
	<input type="submit" value="OK">  
</div>
-->
</body>
</html>