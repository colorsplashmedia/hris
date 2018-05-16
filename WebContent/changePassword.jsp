<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
	
    <title>Change Password</title>
    
    <script type="text/javascript" src="vendor/jquery/jquery.js"></script>

    <!-- Bootstrap Core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="css/colorsplash.css" rel="stylesheet">
    <!-- MetisMenu CSS -->
    <link href="vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">
	<link href="css/colorsplash.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <script type="text/javascript">
		

	    $(document).ready(function () {
			var username = '${UserName}';	
	    	
	    	if(username.length == 0){
	    		alert("You are not allowed to view the page. Please login.");
	    		window.location = "LogoutAction";
	    		return;
	    	}
		
	    }); //end document.ready()
	    
	    
	</script>
    
    <script type="text/javascript">
	

        function updatePassword(){

        	var oldPassword = document.getElementById("oldPassword").value;
    		var empId = document.getElementById("empId").value;
    		var newPassword1 = document.getElementById("newPassword1").value;
    		var newPassword2 = document.getElementById("newPassword2").value;		
    		
    		
    		if(newPassword1 != newPassword2){
    			alert("New Password does not match.");
    			return;
    		}
    		
    		var oAjaxCall = $.ajax({
    			type : "GET",
    			url : "UpdatePasswordAction",			
    			data: "oldPassword=" + oldPassword + "&empId=" + empId,
    			cache : false,
    			async : false,
    			dataType:"json",
    			success : function(data) {	
    				if(data == 'failed'){
    					alert("Your Old Password is incorrect.");		
    					return;
    				} else {
    					updatePasswordSubmit();
    				}			
    			},
    			error : function(data) {
    				alert('error: ' + data);
    			}
    		});

        }
        
        function updatePasswordSubmit() {
    		var newPassword1 = document.getElementById("newPassword1").value;
    		var empId = document.getElementById("empId").value;
    		
    		var oAjaxCall = $.ajax({
    			type : "POST",
    			url : "UpdatePasswordAction",			
    			data: "newPassword1=" + newPassword1 + "&empId=" + empId,
    			cache : false,
    			async : false,
    			dataType:"json",
    			success : function(data) {				
    				alert("Your Password was successfully changed.");				
    			},
    			error : function(data) {
    				alert('error: ' + data);
    			}
    		});
    	}

	
</script>

</head>

<body>

    <div id="wrapper" >

        <c:import url="leftNav.jsp" />
        
        <input type="hidden" name="empId" id="empId" value="${employeeLoggedIn.empId}" />	
		<input type="hidden" name="sectionId" id="sectionId" value="${employee.sectionId}" />

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid" >
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Change Password</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
				<form class="form form-vertical" method="POST" name="updatePassForm" action="UpdatePasswordAction">
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-primary">
							<div class="panel-heading">
								Enter New Password
							</div>
							<!-- /.panel-heading -->
							<div class="panel-body">
							
								
								
									<div class="row">
										
										<div class="col-sm-12">
											<div class="row">
												<div class="col-sm-12">
												  <div class="form-group">
													<label for="oldPassword">Old Password<span class="kv-reqd">*</span></label>
													<input type="text" class="form-control" name="oldPassword" id="oldPassword" required>
												  </div>
												</div>
												
											</div>
											<div class="row">
												<div class="col-sm-12">
												  <div class="form-group">
													<label for="newPassword1">New Password</label>
													<input type="text" class="form-control" name="newPassword1" id="newPassword1" required>
												  </div>
												</div>
												
											</div>
											<div class="row">
												<div class="col-sm-12">
												  <div class="form-group">
													<label for="newPassword2">Confirm New Password</label>
													<input type="text" class="form-control" name="newPassword2" id="newPassword2" required>
												  </div>
												</div>
												
											</div>
											
											<div class="form-group">												
												<div class="text-right" style="margin-bottom: 20px;"> 
													<button type="button" class="btn btn-primary btn-lg btn-block" onClick="updatePassword();">Submit</button>
												</div>
												
												<c:if test="${param.errorCode == 1 or noPassword}">
													<div class="alert alert-danger alert-dismissable" style="text-align: center;">
							                        	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
							                        	Old password is required.
							                    	</div>								
												</c:if>
												<c:if test="${param.errorCode == 2 or mismatch}">								
													<div class="alert alert-danger alert-dismissable" style="text-align: center;">
							                        	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
							                        	New password do not match.
							                    	</div>						
												</c:if>
												<c:if test="${param.errorCode == 3}">								
													<div class="alert alert-danger alert-dismissable" style="text-align: center;">
							                        	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
							                        	Change password failed.
							                    	</div>				
												</c:if>
												<c:if test="${noPassword2}">								
													<div class="alert alert-danger alert-dismissable" style="text-align: center;">
							                        	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
							                        	Please enter new password.
							                    	</div>					
												</c:if>
												<c:if test="${noPassword3}">								
													<div class="alert alert-danger alert-dismissable" style="text-align: center;">
							                        	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
							                        	Please Confirm new password.
							                    	</div>					
												</c:if>
												<c:if test="${param.errorCode == 4}">								
													<div class="alert alert-danger alert-dismissable" style="text-align: center;">
							                        	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
							                        	Old Password Incorrect. Please enter correct password.
							                    	</div>						
												</c:if>
												
												<c:if test="${param.success == 1}">
													<div class="alert alert alert-success" style="text-align: center;">
							                        	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
							                        	Password has been successfully changed.
							                    	</div>									
												</c:if>
											</div>
										</div>
									</div>
								
								<!-- /.form -->
							</div>
							
							<!-- /.panel-body -->
						</div>
						<!-- /.panel -->
					</div>
					<!-- /.col-lg -->
				</div>
				<!-- /.row -->
				</form>
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
  

    <!-- Bootstrap Core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
	
	

</body>

</html>
