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
	<script type="text/javascript" src="vendor/jquery/jquery.js"></script>

    <title>Dashboard</title>

    <!-- Bootstrap Core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">
	<link href="css/colorsplash.css" rel="stylesheet">
	<!-- DataTables CSS -->
    <link href="vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">
	
	<!-- DataTables Responsive CSS -->
    <link href="vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="vendor/morrisjs/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <script type="text/javascript" src="js/jquery.formatCurrency-1.4.0.js"></script>
    
    
    <!-- 
    
	 -->
    
    <script type="text/javascript">
		

	    $(document).ready(function () {
			var username = '${UserName}';	
			var topAccountNo = '${topAccountNo}';	
	    	
	    	if(username.length == 0){
	    		alert("You are not allowed to view the page. Please login.");
	    		window.location = "LogoutAction";
	    		return;
	    	}
	    	
	    	//getAccounts();
			//getIncomeSummary(topAccountNo);			
			//getRegEntries(topAccountNo);
			//getIncomeDetails(topAccountNo);
	    	
	    	function getRegEntries(accountNo){ 		
	    		
	    		var dataTable = $('#dataTables-newEntries').DataTable({
	    			responsive: true,
	    			"processing" : true,
	    			"serverSide" : true,
	    			"paginate" : true,
	    			"bFilter": false,
	    			"order": [1, 'desc'],
	    			"lengthMenu": [ 7, 10, 25, 50, 75, 100 ],
	    			
	    			"pagingType": "full",
	    			"language" : {
	    				"processing" : "Retrieving Data From Server... Please Wait..."
	    			},
	    			"ajax" : {
	    				url : "GetRegistrationEntriesAction?accountNo="+accountNo,
	    				type : "POST",
	    				dataType : "json",
	    			}
	    		});    		
	    		
	    	}
	    	
	    	function getAccounts(){
				
	    		var oAjaxCall = $.ajax({
	    				type : "POST",
	    				url : "GetAllAccountsAction",
	    				cache : false,
	    				async : false,
	    				dataType : "json",
	    				success : function(data) {
	    					var divs = '';					
	    					
	    					jQuery.each(data.Records, function(i, item) {	    						
	    						divs +="<option style='cursor:pointer; text-align: center; padding: 5px; color: black;'>" + item.accountNo + "</option>";	    													
	    					});
	    												
	    					$('#accountList').html(divs);	    					

	    				},
	    				error : function(data) {
	    					alert('error searchEmployee(): ' + data);
	    				}

	    			});
	    		
	    	}
	    	
	    	
	    	
	    	
	    	
	    	
	    	function getIncomeSummary(accountNo){				
	    			
	    		var oAjaxCall = $.ajax({
	    				type : "POST",
	    				url : "GetAllIncomeAction?accountNo="+accountNo,
	    				cache : false,
	    				async : false,
	    				dataType : "json",
	    				success : function(data) {
	    					$('#totalLeftPerAccount').html(data.IncomeSummary.totalLeft).formatCurrency({symbol: '', roundToDecimalPlace: 0});	
	    					$('#totalRightPerAccount').html(data.IncomeSummary.totalRight).formatCurrency({symbol: '', roundToDecimalPlace: 0});	
	    					$('#totalPairsPerAccount').html(data.IncomeSummary.totalPairs).formatCurrency({symbol: '', roundToDecimalPlace: 0});	
	    					$('#balanceLeftPerAccount').html(data.IncomeSummary.balanceLeft).formatCurrency({symbol: '', roundToDecimalPlace: 0});	
	    					$('#balanceRightPerAccount').html(data.IncomeSummary.balanceRight).formatCurrency({symbol: '', roundToDecimalPlace: 0});	
	    					$('#totalDRIncomePerAccount').html(data.IncomeSummary.totalDirectIncome).formatCurrency({symbol: '&#8369;', roundToDecimalPlace: 0});	
	    					$('#totalPairingIncomePerAccount').html(data.IncomeSummary.totalPairingIncome).formatCurrency({symbol: '&#8369;', roundToDecimalPlace: 0});	
	    					$('#total5thIncomePerAccount').html(data.IncomeSummary.total5thPairIncome/1000).formatCurrency({symbol: '', roundToDecimalPlace: 0});	
	    					$('#totalIncomePerAccount').html(data.IncomeSummary.totalIncome).formatCurrency({symbol: '&#8369;', roundToDecimalPlace: 0});	
	    					$('#totalUnilevelPerAccount').html(data.IncomeSummary.totalUnilevelIncome).formatCurrency({symbol: '&#8369;', roundToDecimalPlace: 0});						
	    					
	    					
	    					$('#totalIncome').html(data.IncomeSummary.totalIncome).formatCurrency({symbol: '&#8369;', roundToDecimalPlace: 0});	
	    					$('#totalIncome2').html(data.IncomeSummary.totalIncome).formatCurrency({symbol: '&#8369;', roundToDecimalPlace: 0});	
	    					$('#totalUnilevelIncome').html(data.IncomeSummary.totalUnilevelIncome).formatCurrency({symbol: '&#8369;', roundToDecimalPlace: 0});	
	    					$('#totalPairingIncome').html(data.IncomeSummary.totalPairingIncome).formatCurrency({symbol: '&#8369;', roundToDecimalPlace: 0});	
	    					$('#totalDRIncome').html(data.IncomeSummary.totalDirectIncome).formatCurrency({symbol: '&#8369;', roundToDecimalPlace: 0});						
	    					
	    					
	    					
	    				},
	    				error : function(data) {
	    					alert('error searchEmployee(): ' + data);
	    				}

	    			});
	    		
	    	}
	    	
	    	function getIncomeDetails(accountNo){
	    		
	    		var dataTable = $('#dataTables-incomeDetails').DataTable({
	    			responsive: true,
	    			"processing" : true,
	    			"serverSide" : true,
	    			"paginate" : true,
	    			"bFilter": false,
	    			"order": [1, 'desc'],
	    			"lengthMenu": [ 10, 25, 50, 75, 100 ],
	    			"pagingType": "full",
	    			"language" : {
	    				"processing" : "Retrieving Data From Server... Please Wait..."
	    			},
	    			"ajax" : {
	    				url : "GetAllPVIncomeAction?accountNo="+accountNo,
	    				type : "POST",
	    				dataType : "json",
	    			}
	    		});	    		
	    		
	    	}
	    	
	    	$('#accountList').change(function(e){
				//alert($(this).val());
				$('#dataTables-newEntries').DataTable().destroy();
				$('#dataTables-incomeDetails').DataTable().destroy();
		    		
				getIncomeSummary($(this).val());
				getIncomeDetails($(this).val());
		    	getRegEntries($(this).val());
	    	});
	    	
	    	
	    	
	    	
	    	
			
			
		
	    }); //end document.ready()
	    
	    
	</script>
   
	
	<script type="text/javascript">
	
	
	
	
	
	
	
	
	
	
	
	
	

	
		
		
		
  	</script>
	
	

</head>

<body>

    <div id="wrapper" >

        <c:import url="leftNav.jsp" />	
        
			
		

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                    	Dashboard                    	
                    </h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-money fa-3x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">34,500.45</div>
                                    <div>Latest Payout</div>
                                    <div>May 6, 2018</div>
                                </div>
                            </div>
                        </div>
                        <a href="#">
                            <div class="panel-footer">
                                <span class="pull-left">View Payslip</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-green">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-money fa-3x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">28,756.15</div>
                                    <div>Previous Payout</div>
                                    <div>April 21, 2018</div>
                                </div>
                            </div>
                        </div>
                        <a href="#">
                            <div class="panel-footer">
                                <span class="pull-left">View Payslip</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-yellow">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-share-alt fa-3x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">10</div>
                                    <div>Filled Approved Leaves</div>
                                    <div>&nbsp;</div>
                                </div>
                            </div>
                        </div>
                        
                            <div class="panel-footer">
                                <span class="pull-left">&nbsp;</span>
                                <span class="pull-right">&nbsp;</span>
                                <div class="clearfix"></div>
                            </div>
                        
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-violet">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-tasks fa-3x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">14</div>
                                    <div>Leave Balance</div>
                                    <div>&nbsp;</div>
                                </div>
                            </div>
                        </div>
                        
                            <div class="panel-footer">
                                <span class="pull-left">&nbsp;</span>
                                <span class="pull-right">&nbsp;</span>
                                <div class="clearfix"></div>
                            </div>
                        
                    </div>
                </div>
            </div>
            <!-- /.row -->
			
			
			
			
			<div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Time and Attendance List
                            
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-incomeDetails">
                                <thead>
                                    <tr>
                                        <th>Date</th>
                                        <th>Schedule</th>
                                        <th>Time In</th>
                                        <th>Time Out</th>
                                        <th>ACTION</th>
                                    </tr>
                                </thead>
                                <tbody id="incomeDetails">
                                    
                                </tbody>
                            </table>
                            <!-- /.table-responsive -->
                            
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-violet">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Leave Request List
                            
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-incomeDetails">
                                <thead>
                                    <tr>
                                        <th>Leave Type</th>
                                        <th>Date From</th>
                                        <th>Date To</th>
                                        <th># of Days</th>
                                        <th>Status</th>
                                    </tr>
                                </thead>
                                <tbody id="incomeDetails">
                                    
                                </tbody>
                            </table>
                            <!-- /.table-responsive -->
                            
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-green">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Overtime Request List
                            
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-incomeDetails">
                                <thead>
                                    <tr>
                                        <th># of Hours</th>
                                        <th>Rendered Date</th>
                                        <th>Remarks</th>
                                        <th>Status</th>
                                        <th>Match Sales Income</th>
                                    </tr>
                                </thead>
                                <tbody id="incomeDetails">
                                    
                                </tbody>
                            </table>
                            <!-- /.table-responsive -->
                            
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
			
			
			
            
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    

    <!-- Bootstrap Core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Morris Charts JavaScript -->
    <script src="vendor/raphael/raphael.min.js"></script>
    <script src="vendor/morrisjs/morris.min.js"></script>
    
    <!--
    <script src="data/morris-data.js"></script>
	-->
	
    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
	
	<!-- DataTables JavaScript -->
    <script src="vendor/datatables/js/jquery.dataTables.min.js"></script>
    <script src="vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
    <script src="vendor/datatables-responsive/dataTables.responsive.js"></script>
	
	 
	
	

</body>

</html>
