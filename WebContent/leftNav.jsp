		<!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0;" >
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <!--
                <img src="images/goldLogo.png" class="img-circle m-b" width="45" height="45" alt="logo" style="padding: 5px; margin-left: 10px;">
                 -->
                <a class="navbar-brand" href="index.html">Human Resource Information System v2.0</a>
                 <!--
                <a class="navbar-brand" href="dashboard.jsp">H2W Goldpack System</a>
                 -->
            </div>
            <!-- /.navbar-header -->

            
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation" >
                <div class="sidebar-nav navbar-collapse iancustom">
					<div style="text-align: center; font-extra-bold; padding: 30px 0px 30px 0px; border-bottom: 1px solid #dedede; ">
						
						<img src="images/ColorSplashLogo.jpg" width="85" height="85" alt="logo">
						

						<div style="text-align: center; margin-top: 10px;">
							<span id="memberNameLeftNav" >${MemberName}</span>

							<div id="userNameLeftNav">
								${empName}								
							</div>


							<div id="sparkline1" class="small-chart m-t-sm"></div>
							<!-- 
							<div>
								<h4 style="font-size: 18px; font-weight: bold; color: #5CB85C;" id="totalIncome2">
									&#8369; 0
								</h4>
								<small class="text-muted">&nbsp;</small>
							</div>
							 -->
						</div>
					</div>
                    <ul class="nav" id="side-menu">         
						
                        <li>
                            <a class="leftNav" href="#"><i class="fa fa-user fa-fw"></i> Employee Module<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a class="leftNav" href="dashboard.jsp">Employee Dashboard</a>
                                </li>
                                <li>
                                    <a class="leftNav" href="changePassword.jsp">Change Password</a>
                                </li>
								<li>
                                    <a class="leftNav" href="employeeLeaveEntry.jsp?empId=${employeeLoggedIn.empId}">Request Leave</a>
                                </li>
                                <li>
                                    <a class="leftNav" href="employeeOvertimeEntry.jsp?empId=${employeeLoggedIn.empId}">File Overtime</a>
                                </li>
                                <li>
                                    <a class="leftNav" href="employeeOffSetEntry.jsp?empId=${employeeLoggedIn.empId}">File Offset</a>
                                </li>
                                <li>
                                    <a class="leftNav" href="employeeOutOfOfficeEntry.jsp?empId=${employeeLoggedIn.empId}">File Out of Office</a>
                                </li>
                                <li>
                                    <a href="#">Employee Reports <span class="fa arrow"></span></a>
                                    <ul class="nav nav-third-level">
                                        <li>
                                            <a href="#">Payslip</a>
                                        </li>
                                        <li>
                                            <a href="#">Contribution Report</a>
                                        </li>
                                        <li>
                                            <a href="#">Daily Time Record</a>
                                        </li>
                                        <li>
                                            <a href="#">Leave and OffSet Report</a>
                                        </li>
                                        <li>
                                            <a href="#">Schedule Report</a>
                                        </li>
                                        <li>
                                            <a href="#">Leave Application</a>
                                        </li>
                                    </ul>
                                    <!-- /.nav-third-level -->
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        
                        <li>
                            <a class="leftNav" href="#"><i class="fa fa-check-circle fa-fw"></i> Supervisor Module<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a class="leftNav" href="#">Approvals</a>
                                </li> 
                                <li>
                                    <a href="#">Time Management <span class="fa arrow"></span></a>
                                    <ul class="nav nav-third-level">
                                        <li>
                                            <a href="#">Attendance Monitoring</a>
                                        </li>
                                        <li>
                                            <a href="#">Employee Schedule</a>
                                        </li>                                        
                                    </ul>
                                    <!-- /.nav-third-level -->
                                </li>
                                                               
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
						<li>
                            <a class="leftNav" href="#"><i class="fa fa-th-list fa-fw"></i> File Maintenance Module<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a class="leftNav" href="#">City File Maintenance</a>
                                </li> 
                                <li>
                                    <a class="leftNav" href="#">Job Title File Maintenance</a>
                                </li> 
                                <li>
                                    <a class="leftNav" href="#">Department File Maintenance</a>
                                </li>   
                                <li>
                                    <a class="leftNav" href="#">Section File Maintenance</a>
                                </li>
								<li>
                                    <a class="leftNav" href="#">Unit File Maintenance</a>
                                </li>                     
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a class="leftNav" href="#"><i class="fa fa-money fa-fw"></i> Payroll Module<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="#">Payroll Settings <span class="fa arrow"></span></a>
                                    <ul class="nav nav-third-level">
                                        <li>
                                            <a href="#">Payroll Variables</a>
                                        </li>
                                        <li>
                                            <a href="#">Loan Types</a>
                                        </li>
                                        <li>
                                            <a href="#">Leave Types</a>
                                        </li>
                                        <li>
                                            <a href="#">Income and Benefits Types</a>
                                        </li>  
                                        <li>
                                            <a href="#">Deduction Types</a>
                                        </li>   
                                        <li>
                                            <a href="#">Shift Management</a>
                                        </li>                                   
                                    </ul>
                                    <!-- /.nav-third-level -->
                                </li>         
                                <li>
                                    <a href="#">Employee Management <span class="fa arrow"></span></a>
                                    <ul class="nav nav-third-level">
                                        <li>
                                            <a href="#">Employee Master List</a>
                                        </li>
                                        <li>
                                            <a href="#">Memo/Notification Management</a>
                                        </li>
                                        <li>
                                            <a href="#">Add Income and Benefits</a>
                                        </li>
                                        <li>
                                            <a href="#">Add Deductions</a>
                                        </li>  
                                        <li>
                                            <a href="#">Loan Management</a>
                                        </li>   
                                        <li>
                                            <a href="#">Attendance Monitoring</a>
                                        </li>
                                        <li>
                                            <a href="#">Employee Schedule</a>
                                        </li>      
                                        <li>
                                            <a href="#">Approvals</a>
                                        </li>                               
                                    </ul>
                                    <!-- /.nav-third-level -->
                                </li>    
                                <li>
                                    <a href="#">Payroll Management <span class="fa arrow"></span></a>
                                    <ul class="nav nav-third-level">
                                        <li>
                                            <a href="#">Payroll Period</a>
                                        </li>
                                        <li>
                                            <a href="#">Generate/Lock Payroll</a>
                                        </li>                                                                           
                                    </ul>
                                    <!-- /.nav-third-level -->
                                </li> 
                                <li>
                                    <a href="#">Payroll Reports <span class="fa arrow"></span></a>
                                    <ul class="nav nav-third-level">
                                        <li>
                                            <a href="#">Print Payslip</a>
                                        </li>
                                        <li>
                                            <a href="#">Certificate of Employment</a>
                                        </li>
                                        <li>
                                            <a href="#">Contribution List Report</a>
                                        </li>
                                        <li>
                                            <a href="#">Payroll Register 13th Month Report</a>
                                        </li>  
                                        <li>
                                            <a href="#">Withholding Tax Report</a>
                                        </li>   
                                        <li>
                                            <a href="#">BIR Alphalist Report</a>
                                        </li>                                                                   
                                    </ul>
                                    <!-- /.nav-third-level -->
                                </li>                     
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>						
						<li>
                            <a class="leftNav" href="LogoutAction"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                        
                        
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>