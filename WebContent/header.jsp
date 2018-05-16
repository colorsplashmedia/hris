<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- DIV Header -->
<div id="headerContainer">
	<div id="headerLogo">
		<div id="companyLogo"><img src="images/dai-logo2.png" width="250px" height="79px" style="padding:10px 0px 0px 10px; margin-left: auto; margin-right: 10px; float: left;" /></div>
		<div id="clock">HUMAN RESOURCE INFORMATION SYSTEM 1.0</div>				
	</div>
	<div class="cb"></div>
	<nav>
		<ul>
			<li>							
				<a href="#">My Account</a>
				<ul>
					<li><a href="employeeDashBoard.jsp">Dashboard</a></li>
					<li><a href="changePassword.jsp">Change Password</a></li>
					<!-- 
					<li><a href="employeeReports.jsp">Employee Reports</a></li>
					 -->
					<li>
						<a href="#">Employee Reports &raquo;</a>
						<ul>
							<li><a href="payslipReport.jsp?singlePayslip=Y">Payslip</a></li>	
							<li>
							<a href="#">Contribution Reports &raquo;</a>	
							<ul>
								<li><a href="gsisReport.jsp">GSIS Contribution Report</a></li>		
								<li><a href="taxReport.jsp">Tax Contribution Report</a></li>
								<li><a href="phicReport.jsp">PHIC Contribution Report</a></li>
								<li><a href="pagibigReport.jsp">HDMF Contribution Report</a></li>
								<li><a href="contributionReport.jsp">Contribution Summary Report</a></li>	
							</ul>	
							</li>						
							<li><a href="dtrReport.jsp">Daily Time Record</a></li>							
							<li><a href="leaveReport.jsp">Leave Report</a></li>							
							<li><a href="overtimeReport.jsp">Overtime Report</a></li>		
							<li><a href="outOfOfficeReport.jsp">Out of Office Report</a></li>		
							<li><a href="attendanceReport.jsp?forIndividual=Y">Attendance Report</a></li>		
							<li><a href="scheduleReport.jsp">Schedule Report</a></li>		
							<li><a href="specialLeaveReport.jsp">Application For Special Privilege Leave</a></li>		
							<li><a href="applicationForLeaveReport.jsp">Application For Leave</a></li>																						
						</ul>
					</li>					
				</ul>
			</li>
			<c:forEach var="stl" items="${sessionScope.moduleAccess.systemTabList}">
				<c:if test="${stl == 'st_view_fileManagementTab'}">
				<li>
					<a href="#">File Management</a>
					<ul>							
						<c:forEach var="fm" items="${sessionScope.moduleAccess.fileManagementList}">								
							<c:if test="${fm == 'fm_holidays'}">
								<li><a href="fileMaintenanceHoliday.jsp">Holidays</a></li>
							</c:if>							
							<c:if test="${fm == 'fm_view_module_access'}">
								<li><a href="moduleAccess.jsp">Module Access</a></li>
							</c:if>								
							<c:if test="${fm == 'fm_view_payrollSettings'}">
								<li>
								<a href="#">Payroll Settings &raquo;</a>
								<ul>
									<c:forEach var="varPayrollSettingsHeaderList" items="${payrollSettingsHeaderList}">
										<c:if test="${varPayrollSettingsHeaderList == 'fm_loan_type'}">
											<li><a href="fileMaintenanceLoanType.jsp">Loan Type</a></li>
										</c:if>
										<c:if test="${varPayrollSettingsHeaderList == 'fm_leave_type'}">
											<li><a href="fileMaintenanceLeaveType.jsp">Leave Type</a></li>
										</c:if>
										<c:if test="${varPayrollSettingsHeaderList == 'fm_income'}">
											<li><a href="fm_Income.jsp">Income &amp; Benefits Type</a></li>
										</c:if>	
										<c:if test="${varPayrollSettingsHeaderList == 'fm_deduction'}">
											<li><a href="fm_Deduction.jsp">Deduction Type</a></li>
										</c:if>																	
									</c:forEach>								
								</ul>
								</li>
							</c:if>
							
							
							<c:if test="${fm == 'fm_view_employeeSettings'}">
								<li>
								<a href="#">Employee Settings &raquo;</a>
								<ul>
									<c:forEach var="varEmployeeSettingsHeaderList" items="${employeeSettingsHeaderList}">
										<c:if test="${varEmployeeSettingsHeaderList == 'fm_section'}">
											<li><a href="fm_section.jsp">Section</a></li>
										</c:if>
										<c:if test="${varEmployeeSettingsHeaderList == 'fm_unit'}">
											<li><a href="fm_unit.jsp">Unit</a></li>
										</c:if>
										<c:if test="${varEmployeeSettingsHeaderList == 'fm_subUnit'}">
											<li><a href="fm_subUnit.jsp">Sub Unit</a></li>
										</c:if>	
										<c:if test="${varEmployeeSettingsHeaderList == 'fm_system_approvers'}">
											<li><a href="systemSettings.jsp">System Settings</a></li>
										</c:if>
										<c:if test="${varEmployeeSettingsHeaderList == 'fm_job_title'}">
											<li><a href="fileMaintenanceJobTitle.jsp">Job Title</a></li>
										</c:if>
										<c:if test="${varEmployeeSettingsHeaderList == 'fm_salary_grade'}">
											<li><a href="fm_salary_grade.jsp">Salary Grade</a></li>
										</c:if>										
									</c:forEach>								
								</ul>
								</li>
							</c:if>
							
							<c:if test="${fm == 'fm_view_locationSettings'}">
								<li>
								<a href="#">Location Settings &raquo;</a>
								<ul>
									<c:forEach var="varLocationSettingsHeaderList" items="${locationSettingsHeaderList}">
										<c:if test="${varLocationSettingsHeaderList == 'fm_city'}">
											<li><a href="fileMaintenanceCity.jsp">City</a></li>
										</c:if>
										<c:if test="${varLocationSettingsHeaderList == 'fm_province'}">
											<li><a href="fileMaintenanceProvince.jsp">Province</a></li>
										</c:if>
										<c:if test="${varLocationSettingsHeaderList == 'fm_country'}">
											<li><a href="fileMaintenanceCountry.jsp">Country</a></li>
										</c:if>										
									</c:forEach>								
								</ul>
								</li>
							</c:if>
											
						</c:forEach>
								
								
					</ul>
				</li>
				</c:if>
				<c:if test="${stl == 'st_view_employeeTab'}">
				<li>										
					<a href="#">Employee</a>
					<ul>
						
						<c:forEach var="em" items="${sessionScope.moduleAccess.employeeList}">
						
							<c:if test="${em == 'em_view_empManagement'}">
								<li>
								<a href="#">Employee Management &raquo;</a>
								<ul>
									<c:forEach var="varEmpManagementHeaderList" items="${empManagementHeaderList}">
										<c:if test="${varEmpManagementHeaderList == 'em_employee'}">
											<li><a href="employeeSearch.jsp">Add/Edit Employee</a></li>
										</c:if>
										<c:if test="${varEmpManagementHeaderList == 'em_memo'}">
											<li><a href="memo.jsp">Memo</a></li>
										</c:if>
										<c:if test="${varEmpManagementHeaderList == 'em_notification'}">
											<li><a href="notification.jsp">Notification</a></li>
										</c:if>										
									</c:forEach>								
								</ul>
								</li>
							</c:if>
							
							<c:if test="${em == 'em_view_empEntries'}">
								<li>
								<a href="#">Employee Entries &raquo;</a>
								<ul>
									<c:forEach var="varEmpEntriesHeaderList" items="${empEntriesHeaderList}">
										<c:if test="${varEmpEntriesHeaderList == 'em_file_leave'}">
											<li><a href="employeeLeaveEntry.jsp">File Leave</a></li>
										</c:if>
										<c:if test="${varEmpEntriesHeaderList == 'em_file_ooo'}">
											<li><a href="employeeOutOfOfficeEntry.jsp">File Out of Office</a></li>
										</c:if>
										<c:if test="${varEmpEntriesHeaderList == 'em_file_changeshift'}">
											<li><a href="empSchedChangeRequest.jsp">Request Change Shift</a></li>
										</c:if>
										<c:if test="${varEmpEntriesHeaderList == 'em_file_ot'}">
											<li><a href="employeeOvertimeEntry.jsp">File Overtime</a></li>
										</c:if>	
										<c:if test="${varEmpEntriesHeaderList == 'em_file_offset'}">
											<li><a href="employeeOffSetEntry.jsp">File OffSet</a></li>
										</c:if>	
										<c:if test="${varEmpEntriesHeaderList == 'em_file_undertime'}">
											<li><a href="employeeUndertimeEntry.jsp">File Undertime</a></li>
										</c:if>	
										<c:if test="${varEmpEntriesHeaderList == 'em_file_hourly_attendance'}">
											<li><a href="hourlyAttendance.jsp">File Hourly Attendance</a></li>
										</c:if>									
									</c:forEach>								
								</ul>
								</li>
							</c:if>
							
							<c:if test="${em == 'em_view_empApprovals'}">
								<li>
								<a href="#">Approvals &raquo;</a>
								<ul>
									<c:forEach var="varEmpApprovalsHeaderList" items="${empApprovalsHeaderList}">
										<c:if test="${varEmpApprovalsHeaderList == 'em_leave_approval'}">
											<li><a href="leaveReviewAndApproval.jsp">Leave Approval</a></li>
										</c:if>
										<c:if test="${varEmpApprovalsHeaderList == 'em_ooo_approval'}">
											<li><a href="oooReviewAndApproval.jsp">Out of Office Approval</a></li>
										</c:if>
										<c:if test="${varEmpApprovalsHeaderList == 'em_ot_approval'}">
											<li><a href="overtimeReviewAndApproval.jsp">Overtime Approval</a></li>
										</c:if>	
										<c:if test="${varEmpApprovalsHeaderList == 'em_offset_approval'}">
											<li><a href="offSetReviewAndApproval.jsp">OffSet Approval</a></li>
										</c:if>	
										<c:if test="${varEmpApprovalsHeaderList == 'em_undertime_approval'}">
											<li><a href="undertimeReviewAndApproval.jsp">Undertime Approval</a></li>
										</c:if>	
										<c:if test="${varEmpApprovalsHeaderList == 'em_hourly_attendance_approval'}">
											<li><a href="hourlyAttendanceApproval.jsp">Hourly Attendance Approval</a></li>
										</c:if>									
									</c:forEach>								
								</ul>
								</li>
							</c:if>
							
							<!-- 
							<c:if test="${em == 'em_employee'}">
								<li><a href="employeeSearch.jsp">Employee</a></li>
							</c:if>
							<c:if test="${em == 'em_memo'}">
								<li><a href="memo.jsp">Memo</a></li>
							</c:if>							
							<c:if test="${em == 'em_notification'}">
								<li><a href="notification.jsp">Notification</a></li>		
							</c:if>	
							<c:if test="${em == 'em_file_leave'}">
								<li><a href="employeeLeaveEntry.jsp">File Leave</a></li>		
							</c:if>	
							<c:if test="${em == 'em_file_ooo'}">
								<li><a href="employeeOutOfOfficeEntry.jsp">File Out of Office</a></li>
							</c:if>	
							<c:if test="${em == 'em_file_ot'}">
								<li><a href="employeeOvertimeEntry.jsp">File Overtime</a></li>
							</c:if>	
							<c:if test="${em == 'em_file_offset'}">
								<li><a href="employeeOffSetEntry.jsp">File OffSet</a></li>
							</c:if>	
							<c:if test="${em == 'em_file_undertime'}">
								<li><a href="employeeUndertimeEntry.jsp">File Undertime</a></li>
							</c:if>	
							
							<c:if test="${em == 'em_leave_approval'}">
								<li><a href="leaveReviewAndApproval.jsp">Leave Approval</a></li>
							</c:if>	
							
							<c:if test="${em == 'em_ooo_approval'}">
								<li><a href="oooReviewAndApproval.jsp">Out of Office Approval</a></li>
							</c:if>	
							
							<c:if test="${em == 'em_ot_approval'}">
								<li><a href="overtimeReviewAndApproval.jsp">Overtime Approval</a></li>
							</c:if>	
							
							<c:if test="${em == 'em_offset_approval'}">
								<li><a href="offSetReviewAndApproval.jsp">OffSet Approval</a></li>
							</c:if>	
							
							<c:if test="${em == 'em_undertime_approval'}">
								<li><a href="undertimeReviewAndApproval.jsp">Undertime Approval</a></li>
							</c:if>	
							
							 -->
						</c:forEach>						
					</ul>
				</li>
				</c:if>
				<c:if test="${stl == 'st_view_employeeTimeMgtTab'}">
				<li>
					<a href="#">Time Management</a>
					<ul>
						<li><a href="leaveCardEntry.jsp">Leave Card</a></li>
						<c:forEach var="tm" items="${sessionScope.moduleAccess.timeManagementList}">						
							<c:if test="${tm == 'tm_create_employee_shift'}">
								<li><a href="shiftingSchedule.jsp">Create Employee Shift</a></li>
							</c:if>
							<c:if test="${tm == 'tm_attendance_monitoring'}">
								<li><a href="timeEntryCalendar.jsp">Attendance Monitoring</a></li>
							</c:if>
							<c:if test="${tm == 'tm_employee_schedule'}">
								<li><a href="employeeScheduleCalendar.jsp">Employee Schedule</a></li>
							</c:if>
							<c:if test="${tm == 'tm_time_dispute_hr_approval'}">
								<c:if test="${employeeLoggedIn.sectionId == '1'}">
									<li><a href="timeEntryDisputeApproval.jsp">Time Dispute HR Approval</a></li>
								</c:if>							
							</c:if>						
						</c:forEach>											
					</ul>
				</li>	
				</c:if>
				<c:if test="${stl == 'st_view_payrollTab'}">				
				<li>
					<a href="#">Payroll</a>
					<ul>
						<li>
							<a href="#">Goverment Tables &raquo;</a>	
							<ul>
								<li><a href="../hris/pdf/philhealth.pdf" target="blank">PHIC Contribution Table</a></li>
								<li><a href="../hris/pdf/birTaxTable.pdf" target="blank">Tax Table</a></li>
							</ul>
						</li>
						<li>
							<a href="#">Payroll Transactions &raquo;</a>	
							<ul>
								<li><a href="viewObligationsList.jsp">Obligation Request</a></li>
								<li><a href="viewVouchersList.jsp">Disbursement Voucher</a></li>
								<li><a href="generateAndLockPayroll.jsp">Generate/Lock Payroll</a></li>
								<li><a href="viewPayrollExclusionList.jsp">Exclude From Payroll</a></li>
								<li><a href="payrollPeriod.jsp">Payroll Period</a></li>	
							</ul>
						</li>
						
						<c:forEach var="py" items="${sessionScope.moduleAccess.payrollList}">								
							<c:if test="${py == 'py_income_benefits'}">
								<li><a href="addIncomeNew.jsp">Income and Benefits</a></li>
							</c:if>
							<c:if test="${py == 'py_deductions'}">
								<li><a href="addDeductionNew.jsp">Deductions</a></li>
							</c:if>
							<c:if test="${py == 'py_case_rate'}">
								<li><a href="caseRatePayment.jsp">Case Rate</a></li>
							</c:if>
							<c:if test="${py == 'py_professional_fee'}">
								<li><a href="professionalFee.jsp">Professional Fee</a></li>
							</c:if>
							<c:if test="${py == 'py_hazard_pay'}">
								<li><a href="hazardPay.jsp">Hazard Pay</a></li>
							</c:if>
							<c:if test="${py == 'py_longevity_pay'}">
								<li><a href="longevityPay.jsp">Longevity Pay</a></li>
							</c:if>
							<c:if test="${py == 'py_medicare_share'}">
								<li><a href="medicareShare.jsp">Medicare Share</a></li>
							</c:if>
							<c:if test="${py == 'py_yearend_bonus'}">
								<li><a href="yearEndBonus.jsp">Year End Bonus and Cash Gift</a></li>
							</c:if>
							<!-- 
							<c:if test="${py == 'py_phic_contribution'}">
								<li><a href="../hris/pdf/philhealth.pdf" target="blank">PHIC Contribution Table</a></li>
							</c:if>
							<c:if test="${py == 'py_tax_table'}">
								<li><a href="../hris/pdf/birTaxTable.pdf" target="blank">Tax Table</a></li>
							</c:if>	
							 -->					
						</c:forEach>				
							
					</ul>
				</li>
				</c:if>
				<c:if test="${stl == 'st_view_employeeLoansTab'}">	
				<li>
					<a href="#">Employee's Loan</a>
					<ul>				
						<c:forEach var="el" items="${sessionScope.moduleAccess.employeesLoanList}">
							<c:if test="${el == 'el_loan_payments'}">
								<li><a href="employeeLoanEntry.jsp">Loan and Payments</a></li>
							</c:if>							
						</c:forEach>										
					</ul>
				</li>
				</c:if>
				<!-- For Future Area for new Reports headers -->
				<!-- 
				<c:if test="${stl == 'st_view_payrollReportsTab'}">	
				<li>
					<a href="reports.jsp">Payroll Reports</a>
					<ul>				
						<c:forEach var="el" items="${sessionScope.moduleAccess.payrollReportsList}">
							<c:if test="${el == 'el_loan_payments'}">
								<li><a href="employeeLoanEntry.jsp">Loan and Payments</a></li>
							</c:if>							
						</c:forEach>										
					</ul>
				</li>
				</c:if>
				 -->
			</c:forEach>
			<li>
				<a href="#">Payroll Reports</a>
				<ul>
					<li><a href="payslipReport.jsp?singlePayslip=N">Payslip Report</a></li>														
					<li>
						<a href="#">Payroll Reports &raquo;</a>	
						<ul>
							<li><a href="payrollProofList.jsp">Payroll Proof List Report</a></li>			
							<li><a href="payrollRegistryReport.jsp">General Payroll Report</a></li>		
							<li><a href="payrollRegistryYTDReport.jsp">YTD Payroll Register Report</a></li>
							<li><a href="payrollRegistry13thReport.jsp">Payroll Register 13th Month Report</a></li>
							<li><a href="withholdingTax.jsp">Withholding Tax Report</a></li>
							<li><a href="birAlphaListReport.jsp">BIR Alphalist Report</a></li>							
						</ul>	
					</li>
					<li>
						<a href="#">Other Payroll Reports &raquo;</a>	
						<ul>							
							<li><a href="caseRateReport.jsp?singlePayslip=Y">Case Rate Payment Report</a></li>
							<li><a href="professionalFeeReport.jsp?singlePayslip=Y">Professional Fee Report</a></li>							
							<li><a href="hazardPayReport.jsp">Hazard Pay Report</a></li>							
							<li><a href="longevityReport.jsp">Longevity Report</a></li>							
							<li><a href="medicareReport.jsp">Medicare Share Report</a></li>		
							<li><a href="yearEndBonusReport.jsp">Year End Bonus and Cash Gift Report</a></li>
							
						</ul>	
					</li>
					<li>
						<a href="#">Employee Reports &raquo;</a>	
						<ul>
							<!-- 
							<li><a href="attendanceReport.jsp?forIndividual=Y">Attendance Report</a></li>
							 -->
							
							<li><a href="applicationForLeaveReport.jsp">Application For Leave</a></li>								
							<li><a href="overtimeHRReport.jsp">Overtime Summary Report</a></li>							
							<li><a href="overtimeAuthority.jsp">Overtime Authority</a></li>
							<li><a href="monthlyAttendanceReport.jsp">Monthy Attendance Report</a></li>
							<li><a href="offDutyReport.jsp">Off Duty Monthly Schedule Report</a></li>
							<li><a href="serviceRecordReport.jsp">Service Record</a></li>
							<li><a href="certificateOfEmploymentReport.jsp">Certificate of Employment</a></li>
						</ul>	
					</li>	
					<li>
						<a href="#">Contributions Reports &raquo;</a>	
						<ul>
							<li><a href="gsisPayrollReport.jsp">GSIS Contribution Report</a></li>		
							<li><a href="phicPayrollReport.jsp">PHIC Contribution Report</a></li>
							<li><a href="pagibigPayrollReport.jsp">PAGIBIG Contribution Report</a></li>
							<li><a href="taxPayrollReport.jsp">Tax Contribution Report</a></li>
						</ul>	
					</li>	
					<li>
						<a href="#">Remittance Reports &raquo;</a>	
						<ul>
							<li><a href="otherLoanRemittanceReport.jsp">Other Loan Monthly Remittance Report</a></li>	
							<li><a href="withholdingTaxRemittanceReport.jsp">Withholding Tax Monthly Remittance Report</a></li>								
							<li><a href="phicRemittanceReport.jsp">PHIC Monthly Remittance Report</a></li>
							<li><a href="gsisRemittanceReport.jsp">GSIS Monthly Remittance Report</a></li>	
							<li><a href="pagibigRemittanceReport.jsp">PAGIBIG Monthly Remittance Report</a></li>
						</ul>	
					</li>			
					
					
					
					<li><a href="systemTrailReport.jsp">System Trail Report</a></li>																						
				</ul>
			</li>
			<li>
				<a href="LogoutAction">Logout</a>
			</li>
		</ul>
	</nav>
</div>
<!-- DIV Header -->