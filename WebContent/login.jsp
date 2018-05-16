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

    <title>Login</title>
    
    <script type="text/javascript" src="vendor/jquery/jquery.js"></script>

    <!-- Bootstrap Core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    

    <!-- MetisMenu CSS -->
    <link href="vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">

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
    
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script type="text/javascript" src="js/dpx-common.js"></script>
    
    <script>
		$(document).ready(function() {	
			
			var isExpired = '${param.sessionExpired}';	
	    	
	    	if(isExpired == '1'){
	    		alert("You have been logged out because your session has expired.");	    		
	    	}
			
		});	
	</script>
    
    
    <script type="text/javascript">
		
		
		$(document).keypress(function(e) {
		    if(e.which == 13) {
		    	login();
		    }
		});
	</script>
	
	<script type="text/javascript">	
		function login(){
			
			if (document.loginFrm.username.value == null ||	document.loginFrm.username.value.length == 0 ) {
				alert('Username is a mandatory field.');
				document.loginFrm.username.focus();
				return;
			}
			if (document.loginFrm.password.value == null || document.loginFrm.password.value.length == 0 ) {
				alert('Password is a mandtory field.');
				document.loginFrm.password.focus();
				return;
			}
			
			document.loginFrm.submit();		
		}
		
		function clearUsername() {
			var username = document.getElementById("username").value;
			
			if(username == "Username"){
				$('#username').val("");
			}		
		}
	
	</script>
	
	

</head>

<body>	
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title text-center">&nbsp;</h3>
                    </div>
                    <div class="panel-body" style="text-align: center; font-extra-bold; margin-top: 0px;">
						<div style="margin: 0px 0px 20px 0px;">
							<a href="http://health2wellnessphil.com/">
								<img src="images/ColorSplashLogo.jpg" alt="logo" width="300" height="300">
							</a>
						</div>
                        <form method="POST" name="loginFrm" id="loginFrm" action="LoginAction">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Username" id="username" name="username" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="">
                                </div>
                                <!--
                                <div class="checkbox">
                                    <label>
                                        <input name="remember" type="checkbox" value="Remember Me">Remember Me
                                    </label>
                                </div>
                                 Change this to a button or input when using this as a form 
                                <a href="index.html" class="btn btn-lg btn-success btn-block">Login</a>
                                -->
                                <button class="btn btn-success btn-lg btn-block" onClick="login();">LOGIN</button>
                            </fieldset>
                        </form>
                    </div>
					<div class="panel-footer" style="text-align: center; font-extra-bold; margin-top: 0px; background-color: #337AB7; color: white;">
                        <div style="font-size: 12px;">Copyright &copy; 2017, DPX</div>						
                    </div>
                    
                    <c:if test="${param.isExist == 0}">							
						<div class="alert alert-danger alert-dismissable" style="text-align: center;">
                        	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        	Invalid Credentials. Please try again.
                    	</div>
					</c:if>
                    
                </div>
            </div>
        </div>
    </div>

    <!-- jQuery -->
    <script src="vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>

</body>

</html>
