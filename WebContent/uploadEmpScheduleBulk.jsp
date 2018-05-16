<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Upload Employee Schedule</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@include file="commonHeader.jsp" %>
<%@include file="commonJtables.jsp" %>
<link rel="stylesheet" type="text/css" href="css/styleTables.css" />
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/calendarmodal.css" />
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link href="js/validationEngine/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/validationEngine/jquery.validationEngine.js"></script>
<script type="text/javascript" src="js/validationEngine/jquery.validationEngine-en.js"></script>
<script type="text/javascript" src="js/common.js"></script>


<script type="text/javascript">
	$(document).ready(function() {
			
		
	});

	
	
	
	
	
</script>

</head>
<body>
<div><c:import url="header.jsp" /></div>	
<div id="content">
 
	
	<!-- Right Side of Dashboard -->
	<div  style="margin: 0px auto 0 auto; width: 430px;">	
		
	  	
			
			<form action="UploadFile" method="post" enctype="multipart/form-data">
                <input type="submit" id="addEmployeeButton" value="UPLOAD" style="padding-left: 15px; padding-right: 15px;" />
                <input type="file" name="file" required="required" />                
        	</form>
			
			<h4>${requestScope["message"]}</h4>
			<!-- 
						
			<div class="cb" style="height: 20px;"></div>
			<div style="float: left; margin-left: 30px;">
				<input id="addEmployeeButton" value="Submit Schedule" border="0" onclick="javascript:uploadSchedule();"> 
				<input id="addEmployeeButton" value="Clear" border="0" type="reset" style="padding: 0px 30px 0px 30px;">
			</div>
			 -->
			
	</div>	
</div>
<div style=""><c:import url="footer.jsp" /></div>
</body>
</body>
</html>