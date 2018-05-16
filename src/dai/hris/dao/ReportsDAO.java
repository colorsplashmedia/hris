package dai.hris.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dai.hris.action.reports.ReportConstant;
import dai.hris.dao.DBConstants;
import dai.hris.model.AlphaListReport;
import dai.hris.model.AttendanceReport;
import dai.hris.model.ContributionSummaryReport;
import dai.hris.model.Employee;
import dai.hris.model.GSISRemittance;
import dai.hris.model.GSISReport;
import dai.hris.model.Leave;
import dai.hris.model.LeaveCard;
import dai.hris.model.LoanType;
import dai.hris.model.MonthlyAttendanceReport;
import dai.hris.model.NightDifferential;
import dai.hris.model.OffDutyReport;
import dai.hris.model.OtherLoanRemittance;
import dai.hris.model.PagibigRemittance;
import dai.hris.model.PayrollProofList;
import dai.hris.model.PayrollRegisterReport;
//import dai.hris.model.Payslip;
import dai.hris.model.PayslipZamboanga;
import dai.hris.model.ServiceRecord;
import dai.hris.model.YearEndBonusCashGift;

public class ReportsDAO {
	
Connection conn = null;
    
    public ReportsDAO() {
        
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("ReportsDAO :" + sqle.getMessage());
			
		} catch (Exception e) {
			System.out.println("ReportsDAO :" + e.getMessage());
		
		}
    	
    }
    
    public List<NightDifferential> getNightDifferentialReport(int payrollPeriodId) throws SQLException, Exception {
    	NightDifferential model = null;
    	List<NightDifferential> list = new ArrayList<NightDifferential>();
    	StringBuffer sql = new StringBuffer();  
    	
		sql.append("SELECT nd.empId, nd.dateRendered, SUM(nd.noOfHours) as noOfHours, nd.hourlyRate, nd.holidayRate, SUM(nd.nightDiffAmount) as nightDiffAmount, nd.payrollPeriodId, e.empNo, ");
		sql.append("(e.firstname + ' ' + e.lastname) as name, (SUBSTRING(CONVERT(VARCHAR,pp.fromDate), 1, 11) + ' To ' + SUBSTRING(CONVERT(VARCHAR,pp.toDate), 1, 11)) as payPeriod ");
		sql.append(" FROM empNightDiff nd, employee e, payrollPeriod pp WHERE nd.empId = e.empId AND nd.payrollPeriodId = pp.payrollPeriodId AND nd.payrollPeriodId = ");
		sql.append(payrollPeriodId);
		sql.append(" GROUP BY nd.empId, nd.dateRendered, nd.hourlyRate, nd.holidayRate, nd.payrollPeriodId, e.empNo, ");
		sql.append("(e.firstname + ' ' + e.lastname), (SUBSTRING(CONVERT(VARCHAR,pp.fromDate), 1, 11) + ' To ' + SUBSTRING(CONVERT(VARCHAR,pp.toDate), 1, 11))  ORDER BY name, nd.dateRendered ");
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL : " + sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    while (rs.next()) {
	    	
	    	if(rs.getDouble("noOfHours") > 0){
	    		model = new NightDifferential();
		    	//model.setEmpNightDiffId(rs.getInt("empNightDiffId"));	    	
		    	model.setEmpId(rs.getInt("empId"));
		    	model.setDateRendered(rs.getString("dateRendered"));
		    	model.setNoOfHours(rs.getDouble("noOfHours"));
		    	model.setHourlyRate(rs.getDouble("hourlyRate"));
		    	model.setHolidayRate(rs.getDouble("holidayRate"));
		    	model.setNightDiffAmount(rs.getBigDecimal("nightDiffAmount"));
		    	model.setPayrollPeriodId(rs.getInt("payrollPeriodId"));
		    	model.setEmpName(rs.getString("name"));
		    	model.setEmpNo(rs.getString("empNo"));
		    	model.setPayrollPeriod(rs.getString("payPeriod"));
		    	
		    	list.add(model);
	    	}
	    	
	    	
		}
	    sql = null;
	    ps.close();
	    rs.close();	 
	    return list;
    }
    
    public List<LeaveCard> getLeaveCardByEmpId(int empId) throws SQLException, Exception {
    	LeaveCard model = null;
    	List<LeaveCard> list = new ArrayList<LeaveCard>();
		String sql = "SELECT * FROM leaveCredits WHERE empId = ?";
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, empId);

	    ResultSet rs = ps.executeQuery();
	    
	    while (rs.next()) {
	    	model = new LeaveCard();
	    	model.setLeaveCreditId(rs.getInt("leaveCreditId"));	    	
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setPeriod(rs.getString("period"));
	    	model.setParticulars(rs.getString("particulars"));
	    	model.setvEarned(rs.getDouble("vEarned"));
	    	model.setvAbsenceWithPay(rs.getDouble("vAbsenceWithPay"));
	    	model.setvBalanceINCL(rs.getDouble("vBalanceINCL"));
	    	model.setvBalanceEXCL(rs.getDouble("vBalanceEXCL"));
	    	model.setvAbsenceWithOutPay(rs.getDouble("vAbsenceWithOutPay"));
	    	model.setsEarned(rs.getDouble("sEarned"));
	    	model.setsAbsenceWithPay(rs.getDouble("sAbsenceWithPay"));
	    	model.setsBalanceINCL(rs.getDouble("sBalanceINCL"));
	    	model.setsBalanceEXCL(rs.getDouble("sBalanceEXCL"));
	    	model.setsAbsenceWithOutPay(rs.getDouble("sAbsenceWithOutPay"));
	    	model.setExVacation(rs.getDouble("exVacation"));
	    	model.setExSick(rs.getDouble("exSick"));
	    	model.setRemarks(rs.getString("remarks"));
	    	
	    	list.add(model);
		}
	    sql = null;
	    ps.close();
	    rs.close();	 
	    return list;
    }
    
    //TODO Should think of overlapping months
    public List<MonthlyAttendanceReport> getMonthlyAttendanceReport(List<Employee> empIdList, int month, int year) throws SQLException, Exception {
    	 
    	
    	int numCtr = 1;
    	List<MonthlyAttendanceReport> list = new ArrayList<MonthlyAttendanceReport>();
    	
    	for(Employee model : empIdList) {
    		MonthlyAttendanceReport modelReport = new MonthlyAttendanceReport();
    		
    		modelReport.setNumCtr(numCtr);
    		modelReport.setEmpName(model.getLastname() + ", " + model.getFirstname());
    		modelReport.setRemarks("FULL-TIME (etd: " + model.getEmploymentDate().substring(0, 10) + ")");
    		
    		//SQL FOR SL
    		StringBuffer sql = new StringBuffer();  
        	sql.append("SELECT noDays, DAY(dateFrom) as dayNo1, DAY(dateTo) as dayNo2  ");    	
        	sql.append("FROM leave WHERE leaveTypeId = 1 AND status = 6 AND MONTH(dateFrom) = ");
     		sql.append(month);
     		sql.append(" AND YEAR(dateFrom) = ");
     		sql.append(year);
        	sql.append(" AND empId = ");
        	sql.append(model.getEmpId()); 
        	sql.append(" ORDER BY dateFrom");
    		
    		System.out.println("SQL FOR SL getMonthlyAttendanceReport: " + sql.toString());   	
    		
    		PreparedStatement ps = conn.prepareStatement(sql.toString());		
    		
    	    ResultSet rs = ps.executeQuery();
    	    
    	    
    	    while (rs.next()) {	
    	    	
    	    	if(rs.getInt("dayNo1") == rs.getInt("dayNo2")) {
    	    		modelReport.setTransDate(rs.getInt("dayNo1") + "");
    	    	} else {
    	    		modelReport.setTransDate(rs.getInt("dayNo1") + "-" + rs.getInt("dayNo2"));
    	    	}
    	    	
    	    	
    	    	modelReport.setSick(rs.getInt("noDays") + "");  	    	
    	    	list.add(modelReport);
    	    	modelReport = new MonthlyAttendanceReport();
    	    }
    	    
    	    //SQL FOR VL
    		StringBuffer sqlVL = new StringBuffer();  
    		sqlVL.append("SELECT noDays, DAY(dateFrom) as dayNo1, DAY(dateTo) as dayNo2  ");    	
    		sqlVL.append("FROM leave WHERE leaveTypeId = 2 AND status = 6 AND MONTH(dateFrom) = ");
    		sqlVL.append(month);
    		sqlVL.append(" AND YEAR(dateFrom) = ");
    		sqlVL.append(year);
    		sqlVL.append(" AND empId = ");
    		sqlVL.append(model.getEmpId()); 
    		sqlVL.append(" ORDER BY dateFrom");
    		
    		System.out.println("SQL FOR VL getMonthlyAttendanceReport: " + sqlVL.toString());   	
    		
    		PreparedStatement psVL = conn.prepareStatement(sqlVL.toString());		
    		
    	    ResultSet rsVL = psVL.executeQuery();   
    	    
    	    modelReport = new MonthlyAttendanceReport();
    	    
    	    while (rsVL.next()) {	    	   	    	    	    	
    	    	if(rsVL.getInt("dayNo1") == rsVL.getInt("dayNo2")) {
    	    		modelReport.setTransDate(rsVL.getInt("dayNo1") + "");
    	    	} else {
    	    		modelReport.setTransDate(rsVL.getInt("dayNo1") + "-" + rsVL.getInt("dayNo2"));
    	    	}
    	    	modelReport.setVacation(rsVL.getInt("noDays") + "");  	    	
    	    	list.add(modelReport);
    	    	modelReport = new MonthlyAttendanceReport();
    	    }
    	    

	    	
	    	
	    	
    	    
    	    numCtr ++;
    		
    		
    	}
    	

		
	    return list;   
    	
    }
    
    
    
    public List<GSISRemittance> getGSISRemittanceReport(int month, int year, List<LoanType> loanTypeList) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();   
    	
    	
    	sql.append("SELECT e.firstName, e.lastName, e.middleName, e.empNo, e.empId, e.dateOfBirth, e.gsis, e.crn, epr.gsisEmployeeShare, epr.gsisEmployerShare  ");    	
    	sql.append("FROM employee e, employeePayrollRun epr, payrollPeriod pp ");    	
    	sql.append("WHERE epr.empId = e.empId AND epr.payrollPeriodId = pp.payrollPeriodId ");
    	sql.append("AND MONTH(pp.fromDate) = ");
 		sql.append(month);
 		sql.append(" AND YEAR(pp.fromDate) = ");
 		sql.append(year);

		
		System.out.println("SQL getPagibigRemittanceReport: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<GSISRemittance> list = new ArrayList<GSISRemittance>();
	    
	    while (rs.next()) {	    	
	    	GSISRemittance model = new GSISRemittance();
	    	
	    	model.setFirstName(rs.getString("firstName"));
	    	model.setLastName(rs.getString("lastName"));
	    	model.setMiddleName(rs.getString("middleName"));
	    	model.setEmpId(rs.getString("empNo"));
	    	model.setBpNo(rs.getString("gsis"));	    	
	    	model.setCrn(rs.getString("crn"));
	    	model.setBirthDate(rs.getString("dateOfBirth").substring(0, 10));
	    	model.setEmployeeShare(rs.getBigDecimal("gsisEmployeeShare"));	    	
	    	model.setEmployerShare(rs.getBigDecimal("gsisEmployerShare"));	 
	    	
	    	//1 - Calamity Loan
	    	//2 - Short Term Loan
	    	
	    	int ctr = 1;
	    	
	    	for(LoanType loanType : loanTypeList){
	    		
	    		switch (ctr) {
					case 1:	model.setLoan1(getLoanPaymentsPerMonthGSIS(month, year, rs.getInt("empId"), loanType.getLoanTypeId()));	 
							break;
					case 2: model.setLoan2(getLoanPaymentsPerMonthGSIS(month, year, rs.getInt("empId"), loanType.getLoanTypeId()));	 
							break;
					case 3: model.setLoan3(getLoanPaymentsPerMonthGSIS(month, year, rs.getInt("empId"), loanType.getLoanTypeId()));	 
							break;
					case 4: model.setLoan4(getLoanPaymentsPerMonthGSIS(month, year, rs.getInt("empId"), loanType.getLoanTypeId()));	 
							break;
					case 5: model.setLoan5(getLoanPaymentsPerMonthGSIS(month, year, rs.getInt("empId"), loanType.getLoanTypeId()));	 
							break;
					case 6: model.setLoan6(getLoanPaymentsPerMonthGSIS(month, year, rs.getInt("empId"), loanType.getLoanTypeId()));	 
							break;
					case 7: model.setLoan7(getLoanPaymentsPerMonthGSIS(month, year, rs.getInt("empId"), loanType.getLoanTypeId()));	 
							break;
					case 8: model.setLoan8(getLoanPaymentsPerMonthGSIS(month, year, rs.getInt("empId"), loanType.getLoanTypeId()));	 
							break;
					case 9: model.setLoan8(getLoanPaymentsPerMonthGSIS(month, year, rs.getInt("empId"), loanType.getLoanTypeId()));	 
							break;
					case 10: model.setLoan8(getLoanPaymentsPerMonthGSIS(month, year, rs.getInt("empId"), loanType.getLoanTypeId()));	 
							break;
					case 11: model.setLoan8(getLoanPaymentsPerMonthGSIS(month, year, rs.getInt("empId"), loanType.getLoanTypeId()));	 
							break;
					case 12: model.setLoan8(getLoanPaymentsPerMonthGSIS(month, year, rs.getInt("empId"), loanType.getLoanTypeId()));	 
							break;
					case 13: model.setLoan8(getLoanPaymentsPerMonthGSIS(month, year, rs.getInt("empId"), loanType.getLoanTypeId()));	 
							break;
					case 14: model.setLoan8(getLoanPaymentsPerMonthGSIS(month, year, rs.getInt("empId"), loanType.getLoanTypeId()));	 
							break;
					case 15: model.setLoan8(getLoanPaymentsPerMonthGSIS(month, year, rs.getInt("empId"), loanType.getLoanTypeId()));	 
							break;
					
					default: break;
		    	}
	    		
	    		ctr++;
	    	}
	    	
	    	
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   
    }
    
    public List<LoanType> getTopGSISLoans(int maxLoans) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM loanType WHERE institution = 'GSIS'");
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getTopGSISLoans SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
	    LoanType model = null;
	    List<LoanType> loanTypeList = new ArrayList<LoanType>();
	    
	    int ctr = 0;
	    while (rs.next()) {  
	    	if(ctr == maxLoans) {
	    		break;
	    	}
	    	model = new LoanType();
	    	
	    	model.setAccountingCode(rs.getString("accountingCode"));
	    	model.setInstitution(rs.getString("institution"));
	    	model.setLoanCode(rs.getString("loanCode"));
	    	model.setLoanTypeId(rs.getInt("loanTypeId"));
	    	model.setLoanTypeName(rs.getString("loanTypeName"));
	    	
	    	loanTypeList.add(model);
	    	
	    	ctr++;
	    }
	    
	    ps.close();
	    rs.close();      
	    
    	
    	return loanTypeList;
    }
    
    private BigDecimal getLoanPaymentsPerMonthGSIS(int month, int year, int empId, int loanTypeId) throws SQLException, Exception {    	
    	
    	StringBuffer sql = new StringBuffer();       	
    	
    	sql.append("SELECT sum(paidAmount) as paidAmount ");    	
    	sql.append("FROM loanEntry le, loanPayments lp, payrollPeriod pp ");    	
    	sql.append("WHERE lp.empId = le.empId AND lp.loanEntryId = le.loanEntryId AND lp.payrollPeriodId = pp.payrollPeriodId ");
    	sql.append("AND MONTH(pp.fromDate) = ");
 		sql.append(month);
 		sql.append(" AND YEAR(pp.fromDate) = ");
 		sql.append(year);
 		sql.append(" AND le.loanTypeId = ");
 		sql.append(loanTypeId);
 		sql.append(" AND le.empId = ");
 		sql.append(empId);

		
		System.out.println("SQL getLoanPaymentsPerMonthPagibig: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    
	    if (rs.next()) {	    	
	    	return rs.getBigDecimal("paidAmount") != null ? rs.getBigDecimal("paidAmount") : BigDecimal.ZERO;
	    }
    	
    	return BigDecimal.ZERO;
    }
    
    //TODO Calamity and Short Term
    public List<PagibigRemittance> getPagibigRemittanceReport(int month, int year) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();   
    	
    	
    	sql.append("SELECT e.firstName, e.lastName, e.middleName, e.empNo, e.empId, e.employmentDate, e.dateOfBirth, e.hdmf, e.tin, epr.pagibigEmployeeShare, epr.pagibigEmployerShare  ");    	
    	sql.append("FROM employee e, employeePayrollRun epr, payrollPeriod pp ");    	
    	sql.append("WHERE epr.empId = e.empId AND epr.payrollPeriodId = pp.payrollPeriodId ");
    	sql.append("AND MONTH(pp.fromDate) = ");
 		sql.append(month);
 		sql.append(" AND YEAR(pp.fromDate) = ");
 		sql.append(year);

		
		System.out.println("SQL getPagibigRemittanceReport: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<PagibigRemittance> list = new ArrayList<PagibigRemittance>();
	    
	    while (rs.next()) {	    	
	    	PagibigRemittance model = new PagibigRemittance();
	    	
	    	model.setFirstName(rs.getString("firstName"));
	    	model.setLastName(rs.getString("lastName"));
	    	model.setMiddleName(rs.getString("middleName"));
	    	model.setEmpId(rs.getString("empNo"));
	    	model.setHireDate(rs.getString("employmentDate").substring(0, 10));
	    	model.setPagibigId(rs.getString("hdmf"));	    	
	    	model.setTin(rs.getString("tin"));
	    	model.setBirthDate(rs.getString("dateOfBirth").substring(0, 10));
	    	model.setEmployeeShare(rs.getBigDecimal("pagibigEmployeeShare"));	    	
	    	model.setEmployerShare(rs.getBigDecimal("pagibigEmployerShare"));	 
	    	
	    	//1 - Calamity Loan
	    	//2 - Short Term Loan
	    	model.setCalamityLoan(getLoanPaymentsPerMonthPagibig(month, year, rs.getInt("empId"), 1));	    	
	    	model.setShortTermLoan(getLoanPaymentsPerMonthPagibig(month, year, rs.getInt("empId"), 2));	    	
	    	
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   
    }
    
    private BigDecimal getLoanPaymentsPerMonthPagibig(int month, int year, int empId, int loanTypeId) throws SQLException, Exception {    	
    	
    	StringBuffer sql = new StringBuffer();       	
    	
    	sql.append("SELECT sum(paidAmount) as paidAmount ");    	
    	sql.append("FROM loanEntry le, loanPayments lp, payrollPeriod pp ");    	
    	sql.append("WHERE lp.empId = le.empId AND lp.loanEntryId = le.loanEntryId AND lp.payrollPeriodId = pp.payrollPeriodId ");
    	sql.append("AND MONTH(pp.fromDate) = ");
 		sql.append(month);
 		sql.append(" AND YEAR(pp.fromDate) = ");
 		sql.append(year);
 		sql.append(" AND le.loanTypeId = ");
 		sql.append(loanTypeId);
 		sql.append(" AND le.empId = ");
 		sql.append(empId);

		
		System.out.println("SQL getLoanPaymentsPerMonthPagibig: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    
	    if (rs.next()) {	    	
	    	return rs.getBigDecimal("paidAmount") != null ? rs.getBigDecimal("paidAmount") : BigDecimal.ZERO;
	    }
    	
    	return BigDecimal.ZERO;
    }
    
    public List<OtherLoanRemittance> getWithholdingTaxRemittanceReport(int month, int year) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();   
    	
    	
    	sql.append("SELECT (e.firstname + ' ' + e.lastname) as name, epr.withholdingTax FROM employee e, employeePayrollRun epr, payrollPeriod pp ");    	
    	sql.append("WHERE epr.empId = e.empId AND epr.payrollPeriodId = pp.payrollPeriodId ");
    	sql.append("AND MONTH(pp.fromDate) = ");
 		sql.append(month);
 		sql.append(" AND YEAR(pp.fromDate) = ");
 		sql.append(year);

		
		System.out.println("SQL getWithholdingTaxRemittanceReport: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<OtherLoanRemittance> list = new ArrayList<OtherLoanRemittance>();
	    
	    while (rs.next()) {	    	
	    	OtherLoanRemittance model = new OtherLoanRemittance();
	    	
	    	model.setEmpName(rs.getString("name"));
	    	model.setAmountRemitted(rs.getBigDecimal("withholdingTax"));	    	

	    	list.add(model);

	    }
	    
	    ps.close();
	    rs.close();      
	    return list;
    }
    
    
    //TODO Installent Counter
    public List<OtherLoanRemittance> getOtherLoanRemittanceReport(int loanId, int month, int year) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();   
    	
    	
    	sql.append("SELECT (e.firstname + ' ' + e.lastname) as name, lp.paidAmount, le.noOfMonthToPay FROM employee e, loanPayments lp, loanEntry le, loanType lt ");    	
    	sql.append("WHERE lp.empId = e.empId AND le.empId = e.empId AND le.loanTypeId = lt.loanTypeId AND lp.loanEntryId = le.loanEntryId ");
    	sql.append("AND MONTH(lp.paymentDate) = ");
 		sql.append(month);
 		sql.append(" AND YEAR(lp.paymentDate) = ");
 		sql.append(year);

		
		System.out.println("SQL getOtherLoanRemittanceReport: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<OtherLoanRemittance> list = new ArrayList<OtherLoanRemittance>();
	    
	    while (rs.next()) {	    	
	    	OtherLoanRemittance model = new OtherLoanRemittance();
	    	
	    	model.setEmpName(rs.getString("name"));
	    	model.setAmountRemitted(rs.getBigDecimal("paidAmount"));
	    	model.setInstallmentCounter(rs.getInt("noOfMonthToPay"));
	    	
//	    	for(int x = 0; x < 50; x++) {	    	
	    		list.add(model);
//	    	}
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;
    }
    
    //TODO
    private int getNumOfPayments(int month, int year, int empId, int loanTypeId) throws SQLException, Exception {
    	int noPayments = 0;
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	
    	sql.append("SELECT count(lp.paidAmount) as ctr FROM loanPayments lp, loanEntry le, loanType lt ");    	
    	sql.append("WHERE le.empId = lp.empId AND le.loanTypeId = lt.loanTypeId AND lp.loanEntryId = le.loanEntryId ");
    	sql.append("AND MONTH(lp.paymentDate) = ");
 		sql.append(month);
 		sql.append(" AND YEAR(lp.paymentDate) = ");
 		sql.append(year);

		
		System.out.println("SQL getOtherLoanRemittanceReport: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<OtherLoanRemittance> list = new ArrayList<OtherLoanRemittance>();
	    
	    while (rs.next()) {	    	
	    	OtherLoanRemittance model = new OtherLoanRemittance();
	    	
	    	model.setEmpName(rs.getString("name"));
	    	model.setAmountRemitted(rs.getBigDecimal("paidAmount"));
	    	model.setInstallmentCounter(0);
	    	
	    	//for(int x = 0; x < 50; x++) {	    	
	    		list.add(model);
	    	//}
	    }
	    
	    ps.close();
	    rs.close();   
    	
    	return noPayments;
    }
    
    public List<Object> getVoucerReport(int empId) throws SQLException, Exception {
    	
    	return null;
    }
    
    public List<Object> getObligationReport(int empId) throws SQLException, Exception {
    	return null;
    }
        
    public List<PayrollProofList> getPayrollProofListReport(int month, int year) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();   
    	
    	
    	sql.append("SELECT (e.firstname + ' ' + e.lastname) as name, ep.ban, epr.netPay FROM employee e, empPayrollInfo ep, employeePayrollRun epr, payrollPeriod pp ");    	
    	sql.append("WHERE ep.empId = e.empId AND epr.empId = e.empId AND epr.payrollPeriodId = pp.payrollPeriodId ");
    	sql.append("AND MONTH(pp.fromDate) = ");
 		sql.append(month);
 		sql.append(" AND YEAR(pp.fromDate) = ");
 		sql.append(year);

		
		System.out.println("SQL getPayrollProofListReport: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<PayrollProofList> list = new ArrayList<PayrollProofList>();
	    
	    while (rs.next()) {	    	
	    	PayrollProofList model = new PayrollProofList();
	    	
	    	model.setEmpName(rs.getString("name"));
	    	model.setAccountNo(rs.getString("ban"));
	    	model.setAmount(rs.getBigDecimal("netPay"));
	    	
	    	for(int x = 0; x < 50; x++) {	    	
	    		list.add(model);
	    	}
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   
    }
    
    public List<OffDutyReport> getOffDutyReport(int unitId, int month, int year) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
 		
 		sql.append("SELECT DAY(es.scheduleDate) as dayNo, (e.lastname + ', ' + e.firstname) as name, ss.description ");
 		sql.append("FROM empSchedule es, employee e, shiftingSchedule ss ");
 		sql.append("WHERE es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId ");
 		sql.append("AND MONTH(es.scheduleDate) = ");
 		sql.append(month);
 		sql.append(" AND YEAR(es.scheduleDate) = ");
 		sql.append(year);
 		sql.append(" AND e.unitId = ");
 		sql.append(unitId);
 		
// 		if(personnelType != null && personnelType.length() > 0){
// 			sql.append(" AND e.personnelType = '");
// 	 		sql.append(personnelType);
// 	 		sql.append("'");
// 		}
 		
 		sql.append(" ORDER BY e.lastname, es.scheduleDate");
 				
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		System.out.println("SQL getOffDutyReport: " + sql.toString());   	
  		        
        ResultSet rs = ps.executeQuery();
        
        List<OffDutyReport> list = new ArrayList<OffDutyReport>();
        String empName = "";
        String empNameOld = "";
        OffDutyReport model = new OffDutyReport();
        
        while(rs.next()){       	
        	
        	empName = rs.getString("name"); 
        	
        	if(empNameOld.equals("")){        		
	    		model.setEmpName(empName);
	    		empNameOld = empName;		 
	    	} else {
	    		if(!empNameOld.equals(empName)){
	    			list.add(model);
	    			model = new OffDutyReport();
		    		model.setEmpName(empName);
		    		empNameOld = empName;		    		
		    	}    
	    	}
        	       	
        	
        	int day = rs.getInt("dayNo");
        	
	    	switch (day) {
				case 1:	model.setDay1("W");
						break;
				case 2: model.setDay2("W");
						break;
				case 3: model.setDay3("W");
						break;
				case 4: model.setDay4("W");
						break;
				case 5: model.setDay5("W");
						break;
				case 6: model.setDay6("W");
						break;
				case 7: model.setDay7("W");
						break;
				case 8: model.setDay8("W");
						break;
				case 9: model.setDay9("W");
						break;
				case 10: model.setDay10("W");
						break;
				case 11: model.setDay11("W");
						break;
				case 12: model.setDay12("W");
						break;
				case 13: model.setDay13("W");
						break;
				case 14: model.setDay14("W");
						break;
				case 15: model.setDay15("W");
						break;
				case 16: model.setDay16("W");
						break;
				case 17: model.setDay17("W");
						break;
				case 18: model.setDay18("W");
						break;
				case 19: model.setDay19("W");
						break;
				case 20: model.setDay20("W");
						break;
				case 21: model.setDay21("W");
						break;
				case 22: model.setDay22("W");
						break;
				case 23: model.setDay23("W");
						break;
				case 24: model.setDay24("W");
						break;
				case 25: model.setDay25("W");
						break;
				case 26: model.setDay26("W");
						break;
				case 27: model.setDay27("W");
						break;
				case 28: model.setDay28("W");
						break;
				case 29: model.setDay29("W");
						break;
				case 30: model.setDay30("W");
						break;
				case 31: model.setDay31("W");
						break;
				default: break;
	    	}		
        	
        }    
        
        if(model != null && model.getEmpName() != null){
        	list.add(model);
        }       
        
	    ps.close();
	    rs.close();      
	    return list;   
    }
    
    public List<ServiceRecord> getServiceRecordReport (int empId) throws SQLException, Exception {
    	String sql = "SELECT sr.serviceRecordId, sr.jobTitleId, jt.jobTitle, sr.empId, sr.dateFrom, sr.dateTo, sr.status, sr.placeOfAssignment, sr.causeRemarks, sr.branch, sr.wop, sr.salary  FROM serviceRecord sr, jobTitle jt WHERE sr.jobTitleId = jt.jobTitleId ANd sr.empId = ?";		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, empId);
	
	    ResultSet rs = ps.executeQuery();
	    ArrayList<ServiceRecord> list = new ArrayList<ServiceRecord>();
	    
	    while (rs.next()) {
	    
	    	
	    	ServiceRecord model = new ServiceRecord();
	    	model.setServiceRecordId(rs.getInt("serviceRecordId"));
	    	model.setJobTitleId(rs.getInt("jobTitleId"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setDateFrom(rs.getString("dateFrom"));
	    	model.setDateTo(rs.getString("dateTo"));
	    	model.setStatus(rs.getString("status"));
	    	model.setPlaceOfAssignment(rs.getString("placeOfAssignment"));
	    	model.setCauseRemarks(rs.getString("causeRemarks"));
	    	model.setBranch(rs.getString("branch"));
	    	model.setWop(rs.getString("wop"));
	    	model.setSalary(rs.getBigDecimal("salary"));
	    	model.setJobTitle(rs.getString("jobTitle"));
	    	
	    	list.add(model);
	    }
	    
	    int listSize = 0;
    	int loopCtr = 0;
    	
    	listSize = list.size();
    	
    	int maxSize = 16;
    	int minSize = 12;
    	
    	
    	if (listSize <= minSize) {   
    		loopCtr = minSize - listSize;
    		
    		for (int i = 0; i < loopCtr; i++) {
    			ServiceRecord model = new ServiceRecord();
    	    	model.setServiceRecordId(0);
    	    	model.setJobTitleId(0);
    	    	model.setEmpId(0);
    	    	model.setDateFrom("");
    	    	model.setDateTo("");
    	    	model.setStatus("");
    	    	model.setPlaceOfAssignment("");
    	    	model.setCauseRemarks("");
    	    	model.setBranch("");
    	    	model.setWop("");
    	    	model.setSalary(BigDecimal.ZERO);
    	    	model.setJobTitle("");
    	    	
    	    	list.add(model);
    		}
    	} else {
    		int remainder = listSize % maxSize;
	    	
	    	if(remainder == 0) {
	    		loopCtr = minSize;
	    	} else if(remainder >= minSize){
	    		loopCtr = minSize + (maxSize-remainder);
	    	} else {
	    		loopCtr = minSize - remainder;
	    	}
	    	
	    	for (int i = 0; i < loopCtr; i++) {
	    		ServiceRecord model = new ServiceRecord();
    	    	model.setServiceRecordId(0);
    	    	model.setJobTitleId(0);
    	    	model.setEmpId(0);
    	    	model.setDateFrom("");
    	    	model.setDateTo("");
    	    	model.setStatus("");
    	    	model.setPlaceOfAssignment("");
    	    	model.setCauseRemarks("");
    	    	model.setBranch("");
    	    	model.setWop("");
    	    	model.setSalary(BigDecimal.ZERO);
    	    	model.setJobTitle("");
    	    	
    	    	list.add(model);
			}
    	}
	    
	    ps.close();
	    rs.close();      
	    return list;	
    }
    
    public String constructCondition(List<String> accountList){
		StringBuffer condition = new StringBuffer();
		
		Iterator<String> i = accountList.iterator();
		condition.append("'");
		while(i.hasNext()){
			condition.append(i.next());
			condition.append("'");
			if(i.hasNext()){
				condition.append(", '");
			}			
		}
		return condition.toString();
	}
    
    public String constructConditionIntValue(List<Employee> empIdList){
		StringBuffer condition = new StringBuffer();
		
		Iterator<Employee> i = empIdList.iterator();
		//condition.append("'");
		while(i.hasNext()){
			condition.append(i.next().getEmpId());
			//condition.append("'");
			if(i.hasNext()){
				condition.append(", ");
			}			
		}
		return condition.toString();
	}
    
    public List<AttendanceReport> getScheduleReportListByDateAndIdReport (int empId, String dateFrom, String dateTo) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();   
    	
    	
    	sql.append("SELECT (e.firstname + ' ' + e.lastname) as name, e.empNo, ss.description, es.scheduleDate  ");    	
    	sql.append("FROM employee e, empSchedule es, shiftingSchedule ss WHERE es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId AND es.scheduleDate between '");
    	sql.append(dateFrom);  
    	sql.append("' AND '");
    	sql.append(dateTo);  
    	sql.append("' AND e.empId = ");
    	sql.append(empId); 
    	sql.append(" ORDER BY scheduleDate");
		
		System.out.println("SQL getScheduleReportListByDateAndIdReport: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<AttendanceReport> list = new ArrayList<AttendanceReport>();
	    
	    while (rs.next()) {	    	
	    	AttendanceReport model = new AttendanceReport();
	    	
	    	model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name"));	
			model.setDescription(rs.getString("description"));
    		model.setScheduleDate(rs.getString("scheduleDate"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   
    }
    
    //TODO Not Working
    public List<AttendanceReport> getAttendanceReportListByDateAndIdReport (int empId, String dateFrom, String dateTo) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();   
    	
    	
    	sql.append("SELECT (e.firstname + ' ' + e.lastname) as name, e.empNo, SUBSTRING(CONVERT(VARCHAR,ss.timeIn),1, 5) as timeIn, SUBSTRING(CONVERT(VARCHAR,ss.timeOut),1, 5) as timeOut,  ");
    	sql.append("ISNULL(ete.timeIn,'') as empTimeIn, ISNULL(ete.timeOut,'') as empTimeOut, ISNULL(ete.resolutionRemarks, '') as resolutionRemarks ");
    	sql.append("FROM empTimeEntry ete, employee e, empSchedule es, shiftingSchedule ss WHERE ete.empId = e.empId AND es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId AND ete.shiftId = ss.shiftingScheduleId AND CONVERT(VARCHAR(10),ete.timeIn,110) = CONVERT(VARCHAR(10),es.scheduleDate,110) AND ete.timeIn between '");
    	sql.append(dateFrom);  
    	sql.append("' AND '");
    	sql.append(dateTo);  
    	sql.append("' AND e.empId = ");
    	sql.append(empId); 
    	sql.append(" ORDER BY name, empTimeIn");
		
		System.out.println("SQL getAttendanceReportListByDateAndIdReport: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<AttendanceReport> list = new ArrayList<AttendanceReport>();
	    
	    while (rs.next()) {	    	
	    	AttendanceReport model = new AttendanceReport();
	    	
	    	model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name"));	    	
			model.setEmpTimeIn(rs.getString("empTimeIn"));
			model.setEmpTimeOut(rs.getString("empTimeOut"));
			model.setShiftTimeIn(rs.getString("timeIn"));
			model.setShiftTimeOut(rs.getString("timeOut"));
			model.setRemarks(rs.getString("resolutionRemarks"));
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   
    }
    
    //TODO Not Working
    public List<AttendanceReport> getAttendanceReportListReport (String dateFrom, String dateTo) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();   
    	
    	
    	sql.append("SELECT (e.firstname + ' ' + e.lastname) as name, e.empNo, SUBSTRING(CONVERT(VARCHAR,ss.timeIn),1, 5) as timeIn, SUBSTRING(CONVERT(VARCHAR,ss.timeOut),1, 5) as timeOut,  ");
    	sql.append("ISNULL(ete.timeIn,'') as empTimeIn, ISNULL(ete.timeOut,'') as empTimeOut, ISNULL(ete.resolutionRemarks, '') as resolutionRemarks ");
    	sql.append("FROM empTimeEntry ete, employee e, empSchedule es, shiftingSchedule ss WHERE ete.empId = e.empId AND es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId AND ete.shiftId = ss.shiftingScheduleId AND CONVERT(VARCHAR(10),ete.timeIn,110) = CONVERT(VARCHAR(10),es.scheduleDate,110) AND ete.timeIn between '");
    	sql.append(dateFrom);  
    	sql.append("' AND '");
    	sql.append(dateTo);  
    	sql.append("' ORDER BY name, empTimeIn");
		
		System.out.println("SQL getAttendanceReportListReport: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<AttendanceReport> list = new ArrayList<AttendanceReport>();
	    
	    while (rs.next()) {	    	
	    	AttendanceReport model = new AttendanceReport();
	    	
	    	model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name"));	    	
			model.setEmpTimeIn(rs.getString("empTimeIn"));
			model.setEmpTimeOut(rs.getString("empTimeOut"));
			model.setShiftTimeIn(rs.getString("timeIn"));
			model.setShiftTimeOut(rs.getString("timeOut"));
			model.setRemarks(rs.getString("resolutionRemarks"));
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   
    }
    
    public List<AlphaListReport> getBIRAlphaListReport (int year) throws SQLException, Exception {
    	List<AlphaListReport> list = new ArrayList<AlphaListReport>();
    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	
    	sql.append("SELECT (e.firstname + ' ' + e.lastname) as name, e.empId, e.taxstatus, e.tin, e.empNo, sum(epr.grossPay) as grossPay, (sum(philhealthEmployeeShare) + sum(pagibigEmployeeShare) + sum(gsisEmployeeShare)) as govtDeductions, ");
    	sql.append(" sum(epr.taxableIncome) as taxableIncome, sum(epr.nontaxableIncome) as nontaxableIncome, sum(epr.withholdingTax) as withholdingTax ");
    	//sql.append(" epr.philhealthEmployeeShare, epr.philhealthEmployerShare, epr.pagibigEmployeeShare, epr.pagibigEmployerShare, epr.loans, epr.otherDeductions, epr.withholdingTax, epr.totalDeductions, ");
    	//sql.append(" d.departmentName, epr.netPay, epr.payrollCode, epr.payrollPeriodId, epr.payrollRunStatus, epr.createdBy, epr.createDate, epr.updatedBy, epr.updatedDate, epr.lockedBy, epr.lockedDate, epr.otherTaxableIncome ");    	    	
    	sql.append(" FROM employeePayrollRun epr, employee e, payrollPeriod pp "); 
    	sql.append(" WHERE epr.empId = e.empId and epr.payrollPeriodId = pp.payrollPeriodId AND pp.payYear = ");    
    	sql.append(year);
    	sql.append(" GROUP BY (e.firstname + ' ' + e.lastname), e.empId, e.taxstatus, e.tin, e.empNo ");   	    	
    	sql.append(" ORDER BY name, pp.fromDate desc ");   	
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getPaylipByEmpId SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
    	
    	while (rs.next()) {    	
    		
    		AlphaListReport model = new AlphaListReport();     		   		
    		
    		
			model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name") + " - " + rs.getString("empNo"));		
			model.setTinNo(rs.getString("tin"));
			model.setGross(rs.getBigDecimal("grossPay"));	
			
			//Get From YearEndBonus Table
			YearEndBonusCashGift yearEndBonus = getYearEndBonusCashGiftByEmpIdAndYear(rs.getInt("empId"), year);
			
			BigDecimal total13thMonth = BigDecimal.ZERO;
			
			total13thMonth = yearEndBonus.getNetAmountDue();
			
			if (total13thMonth == null) {
				model.setNonTax13thMonth(BigDecimal.ZERO);
				model.setTaxable13thMonth(BigDecimal.ZERO);
			} else if(total13thMonth.intValue() <= ReportConstant.EXEMPTION_13THMONTH) {
				model.setNonTax13thMonth(total13thMonth.setScale(2, RoundingMode.CEILING));
				model.setTaxable13thMonth(BigDecimal.ZERO);
			} else {
				int taxable13thMonth = ReportConstant.EXEMPTION_13THMONTH - total13thMonth.intValue();
				model.setNonTax13thMonth(new BigDecimal(ReportConstant.EXEMPTION_13THMONTH).setScale(2, RoundingMode.CEILING));
				model.setTaxable13thMonth(new BigDecimal(taxable13thMonth).setScale(2, RoundingMode.CEILING));
			}
			
			
			
			model.setGovtDeductions(rs.getBigDecimal("govtDeductions").setScale(2, RoundingMode.CEILING));
			model.setDiminis(rs.getBigDecimal("nontaxableIncome").setScale(2, RoundingMode.CEILING));			
			
			model.setOtherTaxableIncome(rs.getBigDecimal("taxableIncome").setScale(2, RoundingMode.CEILING));
			
			//netCompensation = Gross Pay - govtDeductions - diminis(nontaxableIncome) - Non Taxble 13th Month + Taxable 13th Month + other Taxable Income
			BigDecimal netCompensation = BigDecimal.ZERO;
			
			netCompensation = model.getGross().subtract(model.getGovtDeductions());
			netCompensation = netCompensation.subtract(model.getDiminis());
			netCompensation = netCompensation.subtract(model.getNonTax13thMonth());
			netCompensation = netCompensation.add(model.getTaxable13thMonth());
			netCompensation = netCompensation.add(model.getOtherTaxableIncome());
			
			model.setNetCompensation(netCompensation.setScale(2, RoundingMode.CEILING));
			
			//Get TaxStatus to Determine Exemption
			
			if("Z".equals(rs.getString("taxstatus")) || "S".equals(rs.getString("taxstatus")) || "ME".equals(rs.getString("taxstatus"))){
				model.setExemptions(new BigDecimal(ReportConstant.PERSONAL_EXEMPTION).setScale(2, RoundingMode.CEILING));
			}
			
			if("ME1".equals(rs.getString("taxstatus")) || "S1".equals(rs.getString("taxstatus"))) {
				model.setExemptions(new BigDecimal(ReportConstant.PERSONAL_EXEMPTION + ReportConstant.DEPENDENT_EXEMPTION).setScale(2, RoundingMode.CEILING));
			}
			
			if("ME2".equals(rs.getString("taxstatus")) || "S2".equals(rs.getString("taxstatus"))) {
				model.setExemptions(new BigDecimal(ReportConstant.PERSONAL_EXEMPTION + (ReportConstant.DEPENDENT_EXEMPTION * 2)).setScale(2, RoundingMode.CEILING));
			}
			
			if("ME3".equals(rs.getString("taxstatus")) || "S3".equals(rs.getString("taxstatus"))) {
				model.setExemptions(new BigDecimal(ReportConstant.PERSONAL_EXEMPTION + (ReportConstant.DEPENDENT_EXEMPTION * 3)).setScale(2, RoundingMode.CEILING));
			}
			
			if("ME4".equals(rs.getString("taxstatus")) || "S4".equals(rs.getString("taxstatus"))) {
				model.setExemptions(new BigDecimal(ReportConstant.PERSONAL_EXEMPTION + (ReportConstant.DEPENDENT_EXEMPTION * 4)).setScale(2, RoundingMode.CEILING));
			}
			
			
			//netTaxableCompensation = netCompensation - exemptions
			BigDecimal netTaxableCompensation = BigDecimal.ZERO;
			
			netTaxableCompensation = model.getNetCompensation().subtract(model.getExemptions());
			model.setNetTaxableCompensation(netTaxableCompensation.setScale(2, RoundingMode.CEILING));
			
			
			//incomeTaxDue (Apply FORMULA)
			BigDecimal incomeTaxDue = BigDecimal.ZERO;
			
			if(netTaxableCompensation.intValue()<=0) {
				model.setIncomeTaxDue(BigDecimal.ZERO);
			} else {
				if(netTaxableCompensation.intValue()>500000) {
					incomeTaxDue = netTaxableCompensation.subtract(new BigDecimal(500000));
					incomeTaxDue = incomeTaxDue.multiply(new BigDecimal(0.32));
					incomeTaxDue = incomeTaxDue.add(new BigDecimal(125000));
					
					
					
					//((netTaxableCompensation.intValue()-500000)*0.32)+125000
				} else {
					if(netTaxableCompensation.intValue()>250000) {
						incomeTaxDue = netTaxableCompensation.subtract(new BigDecimal(250000));
						incomeTaxDue = incomeTaxDue.multiply(new BigDecimal(0.3));
						incomeTaxDue = incomeTaxDue.add(new BigDecimal(50000));
						
						
						
						//((netTaxableCompensation.intValue()-250000)*0.3)+50000
					} else {
						if(netTaxableCompensation.intValue()>140000) {
							incomeTaxDue = netTaxableCompensation.subtract(new BigDecimal(140000));
							incomeTaxDue = incomeTaxDue.multiply(new BigDecimal(0.25));
							incomeTaxDue = incomeTaxDue.add(new BigDecimal(22500));
							
							
							
							//((netTaxableCompensation.intValue()-140000)*0.25)+22500
						} else {
							if(netTaxableCompensation.intValue()>70000) {
								incomeTaxDue = netTaxableCompensation.subtract(new BigDecimal(70000));
								incomeTaxDue = incomeTaxDue.multiply(new BigDecimal(0.2));
								incomeTaxDue = incomeTaxDue.add(new BigDecimal(8500));
								
													
								
								//((netTaxableCompensation.intValue()-70000)*0.2)+8500
							} else {
								if(netTaxableCompensation.intValue()>30000) {
									incomeTaxDue = netTaxableCompensation.subtract(new BigDecimal(30000));
									incomeTaxDue = incomeTaxDue.multiply(new BigDecimal(0.15));
									incomeTaxDue = incomeTaxDue.add(new BigDecimal(2500));
									
															
									
									//((netTaxableCompensation.intValue()-30000)*0.15)+2500
								} else {
									if(netTaxableCompensation.intValue()>10000) {
										incomeTaxDue = netTaxableCompensation.subtract(new BigDecimal(10000));
										incomeTaxDue = incomeTaxDue.multiply(new BigDecimal(0.1));
										incomeTaxDue = incomeTaxDue.add(new BigDecimal(500));
										
										
										
										//((netTaxableCompensation.intValue()-10000)*0.1)+500
									} else {
										
										incomeTaxDue = incomeTaxDue.multiply(new BigDecimal(0.05));
										
										
										
										
										//netTaxableCompensation.intValue()*0.05
									}
								}
							}
						}
					}
				}
			}
			
			
			model.setIncomeTaxDue(incomeTaxDue.setScale(2, RoundingMode.CEILING));
			
			
			model.setIncomeTaxWithHeld(rs.getBigDecimal("withholdingTax").setScale(2, RoundingMode.CEILING));
			
			BigDecimal incomeTaxRefund = BigDecimal.ZERO;
			BigDecimal incomeTaxPayable = BigDecimal.ZERO;
			
			if(model.getIncomeTaxWithHeld().compareTo(model.getIncomeTaxDue()) >= 0) {
				incomeTaxRefund = model.getIncomeTaxWithHeld().subtract(incomeTaxDue);
			} else {
				incomeTaxPayable = incomeTaxDue.subtract(model.getIncomeTaxWithHeld());
			}
			
			
			model.setIncomeTaxPayable(incomeTaxPayable.setScale(2, RoundingMode.CEILING));
			model.setIncomeTaxRefund(incomeTaxRefund.setScale(2, RoundingMode.CEILING));
    		
	    	list.add(model);
	    }
    	
    	if (list.isEmpty()) {    	
    		AlphaListReport model = new AlphaListReport();    		
    		
			model.setEmpNo("");
			model.setEmpName("");
			model.setTinNo("");
			model.setGross(BigDecimal.ZERO);	
			model.setNonTax13thMonth(BigDecimal.ZERO);
			model.setGovtDeductions(BigDecimal.ZERO);
			model.setDiminis(BigDecimal.ZERO);			
			model.setTaxable13thMonth(BigDecimal.ZERO);
			model.setOtherTaxableIncome(BigDecimal.ZERO);
			model.setNetCompensation(BigDecimal.ZERO);
			model.setExemptions(BigDecimal.ZERO);
			model.setNetTaxableCompensation(BigDecimal.ZERO);
			model.setIncomeTaxDue(BigDecimal.ZERO);
			model.setIncomeTaxWithHeld(BigDecimal.ZERO);
			model.setIncomeTaxPayable(BigDecimal.ZERO);
			model.setIncomeTaxRefund(BigDecimal.ZERO);		
			
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();    
    	
    	return list;
    }
    
    public YearEndBonusCashGift getYearEndBonusCashGiftByEmpIdAndYear(int empId, int year) throws SQLException, Exception {
    	YearEndBonusCashGift yeb = new YearEndBonusCashGift();
    	String sql = "select * from yearEndBonusCashGift where empId = ? and year = ?";
    	PreparedStatement ps = conn.prepareStatement(sql);
    	ps.setInt(1, empId);
    	ps.setInt(2, year);
    	
    	ResultSet rs = ps.executeQuery();
    	while (rs.next()) {
    		yeb.setYearEndBonusId(rs.getInt("yearEndBonusCashGiftId"));
    		yeb.setSalaryGrade(rs.getInt("salaryGrade"));
    		yeb.setSTEP(rs.getBigDecimal("STEP"));
    		yeb.setBasicSalary(rs.getBigDecimal("basicSalary"));
    		yeb.setCashGift(rs.getBigDecimal("cashGift"));
    		yeb.setTotal(rs.getBigDecimal("total")); 
    		yeb.setBasicSalaryOct31(rs.getBigDecimal("basicSalaryOct31"));
    		yeb.setCashGift1(rs.getBigDecimal("cashGift1"));
    		yeb.setFirstHalf13thMonth(rs.getBigDecimal("firstHalf13thMonth"));
    		yeb.setFirstHalfCashGift(rs.getBigDecimal("firstHalfCashGift"));
    		yeb.setSecondHalf13thMonth(rs.getBigDecimal("secondHalf13thMonth"));      
    		yeb.setSecondHalfCashGift(rs.getBigDecimal("secondHalfCashGift"));       
    		yeb.setTotalYearEndBonusCashGift(rs.getBigDecimal("totalYearEndBonusCashGift"));
    		yeb.setEamcCoopDeduction(rs.getBigDecimal("eamcCoopDeduction"));
    		yeb.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
    		yeb.setYear(rs.getInt("year"));
    		yeb.setEmpId(rs.getInt("empId"));
    		
    	}
    	rs.close();
    	ps.close();
    	return yeb;
    }
    
    public List<YearEndBonusCashGift> getPayrollRegisterReport13thMonth (int year) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();   
    	
    	
    	sql.append("SELECT e.empNo, (e.firstname + ' ' + e.lastname) as name, hp.netAmountDue, (hp.firstHalf13thMonth + hp.secondHalf13thMonth) as firstHalf13thMonth, ");	
    	sql.append("(hp.firstHalfCashGift + hp.secondHalfCashGift) as firstHalfCashGift, hp.eamcCoopDeduction, hp.empId ");		
    	sql.append("FROM yearEndBonusCashGift hp, employee e WHERE hp.empid = e.empid AND hp.year = ");
    	sql.append(year);  
    	sql.append(" ORDER BY name");
		
		System.out.println("SQL getLongevityPayListByDateRange: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<YearEndBonusCashGift> list = new ArrayList<YearEndBonusCashGift>();
	    
	    while (rs.next()) {	    	
	    	YearEndBonusCashGift model = new YearEndBonusCashGift();
	    	
	    	model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name"));	    	
	    	model.setFirstHalf13thMonth(rs.getBigDecimal("firstHalf13thMonth"));
	    	model.setFirstHalfCashGift(rs.getBigDecimal("firstHalfCashGift"));
    		model.setEamcCoopDeduction(rs.getBigDecimal("eamcCoopDeduction"));
    		model.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
    		
	    	list.add(model);
	    }
	    
	    if (list.isEmpty()) {    	
	    	YearEndBonusCashGift model = new YearEndBonusCashGift();
	    	
	    	model.setEmpNo("");
			model.setEmpName("");	    	
	    	model.setFirstHalf13thMonth(BigDecimal.ZERO);
	    	model.setFirstHalfCashGift(BigDecimal.ZERO);
    		model.setEamcCoopDeduction(BigDecimal.ZERO);
    		model.setNetAmountDue(BigDecimal.ZERO);
    		
    		list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   
    }
    
    //Not Working
    public List<PayrollRegisterReport> getPayrollRegisterReportYTD (int year) throws SQLException, Exception {
    	List<PayrollRegisterReport> list = new ArrayList<PayrollRegisterReport>();
    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	
    	sql.append("SELECT epr.empId, epr.taxStatus, epr.basicPay, epr.absences, epr.tardiness, epr.undertime, epr.overtime, epr.leaveWOPay, ");  
    	sql.append("epr.nightDiff, epr.holidayPay, epr.taxableIncome, epr.nontaxableIncome, epr.grossPay, epr.gsisEmployeeShare, ");    	  
    	sql.append("epr.gsisEmployerShare, epr.philhealthEmployeeShare, epr.philhealthEmployerShare, epr.pagibigEmployeeShare, epr.pagibigEmployerShare, ");    	  
    	sql.append("epr.loans, epr.otherDeductions, epr.withholdingTax, epr.totalDeductions, epr.netPay, epr.otherTaxableIncome, e.empNo, (e.firstname + ' ' + e.lastname) as name, j.jobTitle ");    	  
    	sql.append(" FROM payrollPeriod pp, employeePayrollRun epr, employee e, jobTitle j "); 
    	sql.append(" WHERE e.empId = epr.empId AND e.jobTitleId = j.jobTitleId AND pp.payrollPeriodId = epr.payrollPeriodId AND pp.payYear = ");    
    	sql.append(year); 
    	sql.append(" GROUP BY epr.empId, epr.taxStatus, epr.basicPay, epr.absences, epr.tardiness, epr.undertime, epr.overtime, epr.leaveWOPay, ");   
    	sql.append("epr.nightDiff, epr.holidayPay, epr.taxableIncome, epr.nontaxableIncome, epr.grossPay, epr.gsisEmployeeShare, ");    	  
    	sql.append("epr.gsisEmployerShare, epr.philhealthEmployeeShare, epr.philhealthEmployerShare, epr.pagibigEmployeeShare, epr.pagibigEmployerShare, ");    	  
    	sql.append("epr.loans, epr.otherDeductions, epr.withholdingTax, epr.totalDeductions, epr.netPay, epr.otherTaxableIncome, e.empNo, (e.firstname + ' ' + e.lastname), j.jobTitle ");
    	sql.append(" ORDER BY name ");   	
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getGSISReport SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
    	
    	while (rs.next()) {    	
    		PayrollRegisterReport model = new PayrollRegisterReport();     		    		
    		
    		model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name"));
			model.setAbsences(rs.getBigDecimal("absences"));
			model.setBasicPay(rs.getBigDecimal("basicPay"));
			model.setPositionName(rs.getString("jobTitle"));
			model.setGrossPay(rs.getBigDecimal("grossPay"));
			model.setGsisEmployeeShare(rs.getBigDecimal("gsisEmployeeShare"));
			model.setGsisEmployerShare(rs.getBigDecimal("gsisEmployerShare"));
			model.setHolidayPay(rs.getBigDecimal("holidayPay"));
			model.setLeaveWOPay(rs.getBigDecimal("leaveWOPay"));
			model.setLoans(rs.getBigDecimal("loans"));
			model.setNetPay(rs.getBigDecimal("netPay"));
			model.setNightDiff(rs.getBigDecimal("nightDiff"));
			model.setNontaxableIncome(rs.getBigDecimal("nontaxableIncome"));
			model.setOtherDeductions(rs.getBigDecimal("otherDeductions"));
			model.setOtherTaxableIncome(rs.getBigDecimal("otherTaxableIncome"));
			model.setOvertime(rs.getBigDecimal("overtime"));
			model.setPagibigEmployeeShare(rs.getBigDecimal("pagibigEmployeeShare"));
			model.setPagibigEmployerShare(rs.getBigDecimal("pagibigEmployerShare"));
			model.setPhilhealthEmployeeShare(rs.getBigDecimal("philhealthEmployeeShare"));
			model.setPhilhealthEmployerShare(rs.getBigDecimal("philhealthEmployerShare"));
			model.setTardiness(rs.getBigDecimal("tardiness"));
			model.setTaxStatus(rs.getString("taxStatus"));
			model.setTotalDeductions(rs.getBigDecimal("totalDeductions"));
			model.setUndertime(rs.getBigDecimal("undertime"));
			model.setWithholdingTax(rs.getBigDecimal("withholdingTax"));
    		
	    	list.add(model);
	    }
    	
    	if (list.isEmpty()) {    	
    		PayrollRegisterReport model = new PayrollRegisterReport();
			
			model.setEmpNo("");
			model.setEmpName("");
			model.setAbsences(BigDecimal.ZERO);
			model.setBasicPay(BigDecimal.ZERO);
			model.setPositionName("");
			model.setGrossPay(BigDecimal.ZERO);
			model.setGsisEmployeeShare(BigDecimal.ZERO);
			model.setGsisEmployerShare(BigDecimal.ZERO);
			model.setHolidayPay(BigDecimal.ZERO);
			model.setLeaveWOPay(BigDecimal.ZERO);
			model.setLoans(BigDecimal.ZERO);
			model.setNetPay(BigDecimal.ZERO);
			model.setNightDiff(BigDecimal.ZERO);
			model.setNontaxableIncome(BigDecimal.ZERO);
			model.setOtherDeductions(BigDecimal.ZERO);
			model.setOtherTaxableIncome(BigDecimal.ZERO);
			model.setOvertime(BigDecimal.ZERO);
			model.setPagibigEmployeeShare(BigDecimal.ZERO);
			model.setPagibigEmployerShare(BigDecimal.ZERO);
			model.setPhilhealthEmployeeShare(BigDecimal.ZERO);
			model.setPhilhealthEmployerShare(BigDecimal.ZERO);
			model.setTardiness(BigDecimal.ZERO);
			model.setTaxStatus("");
			model.setTotalDeductions(BigDecimal.ZERO);
			model.setUndertime(BigDecimal.ZERO);
			model.setWithholdingTax(BigDecimal.ZERO);
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();    
    	
    	return list;
    }
    
//    public List<PayrollRegisterReport> getPayrollRegisterReport (int payrollPeriodId) throws SQLException, Exception {
//    	List<PayrollRegisterReport> list = new ArrayList<PayrollRegisterReport>();
//    	
//    	StringBuffer sql = new StringBuffer();    	
//    	
//    	
//    	sql.append("SELECT distinct epr.*, e.empNo, e.lastname + ', ' + e.firstname as name, pp.fromDate, pp.toDate, pp.payrollCode, j.jobTitle ");    	    	    	
//    	sql.append(" FROM payrollPeriod pp, employeePayrollRun epr, employee e, jobTitle j "); 
//    	sql.append(" WHERE e.empId = epr.empId AND e.jobTitleId = j.jobTitleId AND pp.payrollPeriodId = epr.payrollPeriodId AND pp.payrollPeriodId = ");    
//    	sql.append(payrollPeriodId);       	
//    	sql.append(" ORDER BY name ");   	
//    	
//    	PreparedStatement ps = conn.prepareStatement(sql.toString());
//		
//		System.out.println("getGSISReport SQL: " + sql.toString());
//
//	    ResultSet rs = ps.executeQuery();
//    	
//    	while (rs.next()) {    	
//    		PayrollRegisterReport model = new PayrollRegisterReport();     		    		
//    		
//    		model.setEmpNo(rs.getString("empNo"));
//			model.setEmpName(rs.getString("name"));
//			model.setAbsences(rs.getBigDecimal("absences"));
//			model.setBasicPay(rs.getBigDecimal("basicPay"));
//			model.setPositionName(rs.getString("jobTitle"));
//			model.setGrossPay(rs.getBigDecimal("grossPay"));
//			model.setGsisEmployeeShare(rs.getBigDecimal("gsisEmployeeShare"));
//			model.setGsisEmployerShare(rs.getBigDecimal("gsisEmployerShare"));
//			model.setHolidayPay(rs.getBigDecimal("holidayPay"));
//			model.setLeaveWOPay(rs.getBigDecimal("leaveWOPay"));
//			model.setLoans(rs.getBigDecimal("loans"));
//			model.setNetPay(rs.getBigDecimal("netPay"));
//			model.setNightDiff(rs.getBigDecimal("nightDiff"));
//			model.setNontaxableIncome(rs.getBigDecimal("nontaxableIncome"));
//			model.setOtherDeductions(rs.getBigDecimal("otherDeductions"));
//			model.setOtherTaxableIncome(rs.getBigDecimal("otherTaxableIncome"));
//			model.setOvertime(rs.getBigDecimal("overtime"));
//			model.setPagibigEmployeeShare(rs.getBigDecimal("pagibigEmployeeShare"));
//			model.setPagibigEmployerShare(rs.getBigDecimal("pagibigEmployerShare"));
//			model.setPhilhealthEmployeeShare(rs.getBigDecimal("philhealthEmployeeShare"));
//			model.setPhilhealthEmployerShare(rs.getBigDecimal("philhealthEmployerShare"));
//			model.setTardiness(rs.getBigDecimal("tardiness"));
//			model.setTaxStatus(rs.getString("taxStatus"));
//			model.setTotalDeductions(rs.getBigDecimal("totalDeductions"));
//			model.setUndertime(rs.getBigDecimal("undertime"));
//			model.setWithholdingTax(rs.getBigDecimal("withholdingTax"));
//			model.setPayrollCode(rs.getString("payrollCode"));
//    		
//	    	list.add(model);
//	    }
//    	
//    	if (list.isEmpty()) {    	
//    		PayrollRegisterReport model = new PayrollRegisterReport();
//			
//			model.setEmpNo("");
//			model.setEmpName("");
//			model.setAbsences(BigDecimal.ZERO);
//			model.setBasicPay(BigDecimal.ZERO);
//			model.setPositionName("");
//			model.setGrossPay(BigDecimal.ZERO);
//			model.setGsisEmployeeShare(BigDecimal.ZERO);
//			model.setGsisEmployerShare(BigDecimal.ZERO);
//			model.setHolidayPay(BigDecimal.ZERO);
//			model.setLeaveWOPay(BigDecimal.ZERO);
//			model.setLoans(BigDecimal.ZERO);
//			model.setNetPay(BigDecimal.ZERO);
//			model.setNightDiff(BigDecimal.ZERO);
//			model.setNontaxableIncome(BigDecimal.ZERO);
//			model.setOtherDeductions(BigDecimal.ZERO);
//			model.setOtherTaxableIncome(BigDecimal.ZERO);
//			model.setOvertime(BigDecimal.ZERO);
//			model.setPagibigEmployeeShare(BigDecimal.ZERO);
//			model.setPagibigEmployerShare(BigDecimal.ZERO);
//			model.setPhilhealthEmployeeShare(BigDecimal.ZERO);
//			model.setPhilhealthEmployerShare(BigDecimal.ZERO);
//			model.setTardiness(BigDecimal.ZERO);
//			model.setTaxStatus("");
//			model.setTotalDeductions(BigDecimal.ZERO);
//			model.setUndertime(BigDecimal.ZERO);
//			model.setWithholdingTax(BigDecimal.ZERO);
//			model.setPayrollCode("");
//    		
//	    	list.add(model);
//	    }
//	    
//	    ps.close();
//	    rs.close();    
//    	
//    	return list;
//    }
    
//    public List<Payslip> getPayrollRegisterReport (List<Employee> empIdList, int payrollPeriodId) throws SQLException, Exception {
    //TODO
    public List<PayslipZamboanga> getPayrollRegisterReport (List<Employee> empIdList, int payrollPeriodId) throws SQLException, Exception {
    	List<PayslipZamboanga> list = new ArrayList<PayslipZamboanga>();
    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	
    	sql.append("SELECT e.empId, pp.payrollPeriodId, (e.firstname + ' ' + e.lastname) as name, (SUBSTRING(CONVERT(VARCHAR,pp.fromDate), 1, 11) + ' To ' + SUBSTRING(CONVERT(VARCHAR,pp.toDate), 1, 11)) as payPeriod, ");    	  
    	sql.append(" e.taxstatus, pp.payrollType, e.empNo, epr.employeePayrollRunId, epr.empId, epr.taxStatus, epr.basicPay, epr.absences, epr.tardiness, epr.undertime, ");
    	sql.append(" epr.overtime, epr.leaveWOPay, epr.nightDiff, epr.holidayPay, epr.taxableIncome, epr.nontaxableIncome, epr.grossPay, epr.gsisEmployeeShare, epr.gsisEmployerShare, ");
    	sql.append(" epr.philhealthEmployeeShare, epr.philhealthEmployerShare, epr.pagibigEmployeeShare, epr.pagibigEmployerShare, epr.loans, epr.otherDeductions, epr.withholdingTax, epr.totalDeductions, ");
    	sql.append(" d.sectionName, epr.netPay, epr.payrollCode, epr.payrollPeriodId, epr.payrollRunStatus, epr.createdBy, epr.createDate, epr.updatedBy, epr.updatedDate,  ");    	    	
    	sql.append(" epr.lockedBy, epr.lockedDate, epr.otherTaxableIncome, epr.cola, epr.foodAllowance, epr.tranpoAllowance, epr.taxShield, j.jobTitle ");
    	sql.append(" FROM employeePayrollRun epr, employee e, payrollPeriod pp, section d, jobTitle j "); 
    	sql.append(" WHERE e.jobTitleId = j.jobTitleId AND e.sectionId = d.sectionId AND epr.empId = e.empId and epr.payrollPeriodId = pp.payrollPeriodId AND pp.payrollPeriodId = ");    
    	sql.append(payrollPeriodId);  
    	sql.append(" AND e.empId in (");    	
    	sql.append(constructConditionIntValue(empIdList));  
    	sql.append(")");
    	
    	
    	sql.append(" ORDER BY name ");   	
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getPaylipByEmpId SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
    	
    	while (rs.next()) {    	
    		PayslipZamboanga model = new PayslipZamboanga();     	
    		
    		//Employee Payroll Details
    		model.setEmpId(rs.getInt("empId"));
    		model.setPayrollPeriodId(rs.getInt("payrollPeriodId"));
			model.setName(rs.getString("name"));
			model.setPositionName(rs.getString("jobTitle"));
			model.setDepartmentName(rs.getString("sectionName"));
			model.setPayPeriod(rs.getString("payPeriod"));
			model.setPayrollType(rs.getString("payrollType"));
			model.setTaxStatus(rs.getString("taxstatus"));				
			
			//Income
			model.setBasicPay(rs.getBigDecimal("basicPay"));
			model.setEmpNo(rs.getString("empNo"));
			model.setHolidayPay(rs.getBigDecimal("holidayPay"));
			model.setNightDiff(rs.getBigDecimal("nightDiff"));
			model.setCola(rs.getBigDecimal("cola"));
			model.setTranpoAllowance(rs.getBigDecimal("tranpoAllowance"));
			model.setFoodAllowance(rs.getBigDecimal("foodAllowance"));
			model.setOvertime(rs.getBigDecimal("overtime"));
			
			if(rs.getBigDecimal("otherTaxableIncome") != null){
				model.setOtherTaxableIncome(rs.getBigDecimal("otherTaxableIncome"));
			} else {
				model.setOtherTaxableIncome(BigDecimal.ZERO);
			}
			model.setNontaxableIncome(rs.getBigDecimal("nontaxableIncome"));
			
			BigDecimal totalEarnings = new BigDecimal(0);
			
			totalEarnings = totalEarnings.add(model.getBasicPay() != null ? model.getBasicPay() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.add(model.getNightDiff() != null ? model.getNightDiff() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.add(model.getOvertime() != null ? model.getOvertime() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.add(model.getHolidayPay() != null ? model.getHolidayPay() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.add(model.getOtherTaxableIncome() != null ? model.getOtherTaxableIncome() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.add(model.getNontaxableIncome() != null ? model.getNontaxableIncome() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.add(model.getCola() != null ? model.getCola() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.add(model.getFoodAllowance() != null ? model.getFoodAllowance() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.add(model.getTranpoAllowance() != null ? model.getTranpoAllowance() : BigDecimal.ZERO);
			
			totalEarnings = totalEarnings.subtract(model.getAbsences() != null ? model.getAbsences() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.subtract(model.getTardiness() != null ? model.getTardiness() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.subtract(model.getUndertime() != null ? model.getUndertime() : BigDecimal.ZERO);
			
			System.out.println("GROSS: " + rs.getBigDecimal("grossPay").doubleValue());
			System.out.println("TOTAL EARNINGS: " + totalEarnings.doubleValue());
			
			model.setTotalEarnings(totalEarnings);
			model.setGrossPay(rs.getBigDecimal("grossPay"));
			
			//Deductions
			model.setGsisEmployeeShare(rs.getBigDecimal("gsisEmployeeShare"));
			model.setGsisEmployerShare(rs.getBigDecimal("gsisEmployerShare"));
			model.setPhilhealthEmployeeShare(rs.getBigDecimal("philhealthEmployeeShare"));
			model.setPhilhealthEmployerShare(rs.getBigDecimal("philhealthEmployerShare"));
			model.setPagibigEmployeeShare(rs.getBigDecimal("pagibigEmployeeShare"));
			model.setPagibigEmployerShare(rs.getBigDecimal("pagibigEmployerShare"));
			model.setAbsences(rs.getBigDecimal("absences"));
			model.setTardiness(rs.getBigDecimal("tardiness"));			
			model.setUndertime(rs.getBigDecimal("undertime"));
			model.setWithholdingTax(rs.getBigDecimal("withholdingTax"));
			
			BigDecimal finalDeduction = BigDecimal.ZERO;
			
			finalDeduction = finalDeduction.add(rs.getBigDecimal("withholdingTax"));
			
			int counter = 0;
			
			StringBuffer sql2 = new StringBuffer();   
	    	
	    	
	    	sql2.append("SELECT ed.amount as amount, d.deductionName as name FROM empPayDeduction ed, deduction d WHERE d.deductionId = ed.deductionId AND empId = ");	
	    	sql2.append(model.getEmpId());      	
	    	sql2.append(" AND payrollPeriodId = ");
	    	sql2.append(model.getPayrollPeriodId());
	    	sql2.append(" UNION SELECT 0 as amount, deductionName as name FROM deduction a WHERE NOT EXISTS (SELECT 'x' FROM empPayDeduction b WHERE a.deductionId = b.deductionId)");
	    	sql2.append(" UNION SELECT lp.paidAmount as amount, lt.loanTypeName as name FROM loanPayments lp, loanEntry le, loanType lt WHERE le.loanTypeId = lt.loanTypeId ");
	    	sql2.append("AND lp.loanEntryId = le.loanEntryId AND le.empId = ");
	    	sql2.append(model.getEmpId());      	
	    	sql2.append(" AND lp.payrollPeriodId = ");
	    	sql2.append(model.getPayrollPeriodId());
	    	sql2.append(" UNION SELECT 0 as amount, loanTypeName as name FROM loanType a WHERE NOT EXISTS (SELECT 'x' FROM loanPayments b, loanEntry c WHERE a.loanTypeId = c.loanTypeId and c.loanEntryId = b.loanEntryId)");
	    	
			
			System.out.println("SQL getDeductionsAndLoans: " + sql2.toString());   	
	    		
			PreparedStatement ps2 = conn.prepareStatement(sql2.toString());		
			
		    ResultSet rs2 = ps2.executeQuery();
		    
		    while (rs2.next()) {
		    	
		    	setPayslipDeductions(counter, model, rs2.getBigDecimal("amount"), rs2.getString("name"), "", "");
		    	finalDeduction = finalDeduction.add(rs2.getBigDecimal("amount"));	   
		    	
		    	System.out.println("counter: " + counter);   	
		    	System.out.println("deductionName: " + rs2.getString("name"));   	
		    	System.out.println("paidAmount: " + rs2.getBigDecimal("amount").doubleValue());   	
		    	System.out.println("finalDeduction: " + finalDeduction.doubleValue());   	
		    	
		    	counter++;	    	
		    	
		    }
			
			model.setTotalDeductions(finalDeduction);	
			
			if("M".equals(model.getPayrollType())){
				BigDecimal netPay = rs.getBigDecimal("netPay").divide(new BigDecimal(2));
				model.setNetPay(rs.getBigDecimal("netPay"));
				
				double netPay1 = netPay.doubleValue();
				double fractionalPart = netPay1 % 1;
				netPay1 = netPay1 - fractionalPart;			
				
				double netPay2 = netPay.doubleValue() + fractionalPart;
				
				model.setNetPay1(new BigDecimal(netPay1));
				model.setNetPay2(new BigDecimal(netPay2));
			} else {
				model.setNetPay(rs.getBigDecimal("netPay"));
			}
			
			
    		
	    	list.add(model);
	    	
	    }
    	
    	
    	
    	
	    
	    ps.close();
	    rs.close();    
    	
    	return list;
    }
    
    public List<ContributionSummaryReport> getContributionSummaryReportByDateRangeAndId (int empId, String dateFrom, String dateTo) throws SQLException, Exception {
    	List<ContributionSummaryReport> list = new ArrayList<ContributionSummaryReport>();
    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	
    	sql.append("SELECT (e.firstname + ' ' + e.lastname) as name, e.empNo, epr.withholdingTax, (epr.philhealthEmployeeShare +  epr.philhealthEmployerShare) as phicShare,  ");    	    	    	
    	sql.append("(epr.pagibigEmployeeShare +  epr.pagibigEmployerShare) as hdmfShare, (epr.gsisEmployeeShare +  epr.gsisEmployerShare) as gsisShare, "); 
    	sql.append(" CONVERT(VARCHAR(12),pp.fromDate,107) + ' to ' + CONVERT(VARCHAR(12),pp.toDate,107) as payPeriod FROM employeePayrollRun epr, employee e, payrollPeriod pp ");    
    	sql.append(" WHERE epr.empId = e.empId and epr.payrollPeriodId = pp.payrollPeriodId AND pp.fromDate BETWEEN '");    
    	sql.append(dateFrom);       	
    	sql.append("' AND '");   
    	sql.append(dateTo);  
    	sql.append("' AND e.empId = ");   
    	sql.append(empId);  
    	sql.append(" ORDER BY pp.fromDate desc ");   	
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getGSISReport SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
    	
    	while (rs.next()) {    	
    		ContributionSummaryReport model = new ContributionSummaryReport();     		    		
    		
    		model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name"));
			model.setPayrollPeriod(rs.getString("payPeriod"));
			model.setGsisShare(rs.getBigDecimal("gsisShare"));
			model.setHdmfShare(rs.getBigDecimal("hdmfShare"));
			model.setPhicShare(rs.getBigDecimal("phicShare"));
			model.setWithHoldingTax(rs.getBigDecimal("withholdingTax"));	
    		
	    	list.add(model);
	    }
    	
    	if (list.isEmpty()) {    	
    		ContributionSummaryReport model = new ContributionSummaryReport();
			
			model.setEmpNo("");
			model.setEmpName("");
			model.setPayrollPeriod("");
			model.setGsisShare(BigDecimal.ZERO);
			model.setHdmfShare(BigDecimal.ZERO);
			model.setPhicShare(BigDecimal.ZERO);
			model.setWithHoldingTax(BigDecimal.ZERO);	
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();    
    	
    	return list;
    }
    
    public List<GSISReport> getWithHoldingTaxReport (int payrollPeriodId) throws SQLException, Exception {
    	List<GSISReport> list = new ArrayList<GSISReport>();
    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	
    	sql.append("SELECT (e.firstname + ' ' + e.lastname) as name, e.empNo, epr.withholdingTax, e.tin, ");    	    	    	
    	sql.append(" CONVERT(VARCHAR(12),pp.fromDate,107) + ' to ' + CONVERT(VARCHAR(12),pp.toDate,107) as payPeriod FROM employeePayrollRun epr, employee e, payrollPeriod pp "); 
    	sql.append(" WHERE epr.empId = e.empId and epr.payrollPeriodId = pp.payrollPeriodId AND pp.payrollPeriodId = ");    
    	sql.append(payrollPeriodId);       	
    	sql.append(" ORDER BY name, pp.fromDate desc ");   	
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getWithHoldingTaxReport SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
    	
    	while (rs.next()) {    	
    		GSISReport model = new GSISReport();     		    		
    		
    		model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name"));
			model.setEmployeeShare(rs.getBigDecimal("withholdingTax"));
			model.setGsisNo(rs.getString("tin"));
			model.setPayrollPeriod(rs.getString("payPeriod"));
	    	list.add(model);
	    }
    	
    	if (list.isEmpty()) {    	
    		GSISReport model = new GSISReport();
			
			model.setEmpNo("");
			model.setEmpName("");
			model.setEmployeeShare(BigDecimal.ZERO);
			model.setEmployerShare(BigDecimal.ZERO);
			model.setGsisNo("");
			model.setPayrollPeriod("");
			model.setTotalContribution(BigDecimal.ZERO);		
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();    
    	
    	return list;
    }
    
    public List<GSISReport> getWithHoldingTaxReportByDateRangeAndId (int empId, String dateFrom, String dateTo) throws SQLException, Exception {
    	List<GSISReport> list = new ArrayList<GSISReport>();
    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	
    	sql.append("SELECT (e.firstname + ' ' + e.lastname) as name, e.empNo, epr.withholdingTax, e.tin, ");    	    	    	
    	sql.append(" CONVERT(VARCHAR(12),pp.fromDate,107) + ' to ' + CONVERT(VARCHAR(12),pp.toDate,107) as payPeriod FROM employeePayrollRun epr, employee e, payrollPeriod pp "); 
    	sql.append(" WHERE epr.empId = e.empId and epr.payrollPeriodId = pp.payrollPeriodId AND pp.fromDate BETWEEN '");    
    	sql.append(dateFrom);       	
    	sql.append("' AND '");   
    	sql.append(dateTo);  
    	sql.append("' AND e.empId = ");   
    	sql.append(empId);  
    	sql.append(" ORDER BY pp.fromDate desc ");   	
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getWithHoldingTaxReportByDateRangeAndId SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
    	
    	while (rs.next()) {    	
    		GSISReport model = new GSISReport();     		    		
    		
    		model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name"));
			model.setEmployeeShare(rs.getBigDecimal("withholdingTax"));
			model.setGsisNo(rs.getString("tin"));
			model.setPayrollPeriod(rs.getString("payPeriod"));
			
	    	list.add(model);
	    }
    	
    	if (list.isEmpty()) {    	
    		GSISReport model = new GSISReport();
			
			model.setEmpNo("");
			model.setEmpName("");
			model.setEmployeeShare(BigDecimal.ZERO);
			model.setEmployerShare(BigDecimal.ZERO);
			model.setGsisNo("");
			model.setPayrollPeriod("");
			model.setTotalContribution(BigDecimal.ZERO);		
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();    
    	
    	return list;
    }
    
    public List<GSISReport> getPHICReport (int payrollPeriodId) throws SQLException, Exception {
    	List<GSISReport> list = new ArrayList<GSISReport>();
    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	
    	sql.append("SELECT (e.firstname + ' ' + e.lastname) as name, e.empNo, epr.philhealthEmployeeShare, epr.philhealthEmployerShare, (epr.philhealthEmployeeShare +  epr.philhealthEmployerShare) as totalContribution, e.phic, ");    	    	    	
    	sql.append(" CONVERT(VARCHAR(12),pp.fromDate,107) + ' to ' + CONVERT(VARCHAR(12),pp.toDate,107) as payPeriod   FROM employeePayrollRun epr, employee e, payrollPeriod pp "); 
    	sql.append(" WHERE epr.empId = e.empId and epr.payrollPeriodId = pp.payrollPeriodId AND pp.payrollPeriodId = ");    
    	sql.append(payrollPeriodId);       	
    	sql.append(" ORDER BY name, pp.fromDate ");   	
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getPHICReport SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
    	
    	while (rs.next()) {    	
    		GSISReport model = new GSISReport();     		    		
    		
    		model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name"));
			model.setEmployeeShare(rs.getBigDecimal("philhealthEmployeeShare"));
			model.setEmployerShare(rs.getBigDecimal("philhealthEmployerShare"));
			model.setGsisNo(rs.getString("phic"));
			model.setPayrollPeriod(rs.getString("payPeriod"));
			model.setTotalContribution(rs.getBigDecimal("totalContribution"));
    		
	    	list.add(model);
	    }
    	
    	if (list.isEmpty()) {    	
    		GSISReport model = new GSISReport();
			
			model.setEmpNo("");
			model.setEmpName("");
			model.setEmployeeShare(BigDecimal.ZERO);
			model.setEmployerShare(BigDecimal.ZERO);
			model.setGsisNo("");
			model.setPayrollPeriod("");
			model.setTotalContribution(BigDecimal.ZERO);		
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();    
    	
    	return list;
    }
    
    public List<GSISReport> getPHICMonthlyRemittanceReport (int month, int year) throws SQLException, Exception {
    	List<GSISReport> list = new ArrayList<GSISReport>();
    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	
    	sql.append("SELECT (e.firstname + ' ' + e.lastname) as name, e.empNo, epr.philhealthEmployeeShare, epr.philhealthEmployerShare, (epr.philhealthEmployeeShare +  epr.philhealthEmployerShare) as totalContribution, e.phic, ");    	    	    	
    	sql.append(" CONVERT(VARCHAR(12),pp.fromDate,107) + ' to ' + CONVERT(VARCHAR(12),pp.toDate,107) as payPeriod  FROM employeePayrollRun epr, employee e, payrollPeriod pp "); 
    	sql.append(" WHERE epr.empId = e.empId and epr.payrollPeriodId = pp.payrollPeriodId AND MONTH(pp.fromDate) = ");    
    	sql.append(month);
 		sql.append(" AND YEAR(pp.fromDate) = ");
 		sql.append(year);
    	sql.append(" ORDER BY name, pp.fromDate desc ");   	
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getPHICMonthlyRemittanceReport SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
    	
    	while (rs.next()) {    	
    		GSISReport model = new GSISReport();     		    		
    		
    		model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name"));
			model.setEmployeeShare(rs.getBigDecimal("philhealthEmployeeShare"));
			model.setEmployerShare(rs.getBigDecimal("philhealthEmployerShare"));
			model.setGsisNo(rs.getString("phic"));
			model.setPayrollPeriod(rs.getString("payPeriod"));
			model.setTotalContribution(rs.getBigDecimal("totalContribution"));
    		
	    	list.add(model);
	    }
    	
    	if (list.isEmpty()) {    	
    		GSISReport model = new GSISReport();
			
			model.setEmpNo("");
			model.setEmpName("");
			model.setEmployeeShare(BigDecimal.ZERO);
			model.setEmployerShare(BigDecimal.ZERO);
			model.setGsisNo("");
			model.setPayrollPeriod("");
			model.setTotalContribution(BigDecimal.ZERO);		
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();    
    	
    	return list;
    }
    
    public List<GSISReport> getPHICReportByDateRangeAndId (int empId, String dateFrom, String dateTo) throws SQLException, Exception {
    	List<GSISReport> list = new ArrayList<GSISReport>();
    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	
    	sql.append("SELECT (e.firstname + ' ' + e.lastname) as name, e.empNo, epr.philhealthEmployeeShare, epr.philhealthEmployerShare, (epr.philhealthEmployeeShare +  epr.philhealthEmployerShare) as totalContribution, e.phic, ");    	    	    	
    	sql.append(" CONVERT(VARCHAR(12),pp.fromDate,107) + ' to ' + CONVERT(VARCHAR(12),pp.toDate,107) as payPeriod   FROM employeePayrollRun epr, employee e, payrollPeriod pp "); 
    	sql.append(" WHERE epr.empId = e.empId and epr.payrollPeriodId = pp.payrollPeriodId AND pp.fromDate BETWEEN '");    
    	sql.append(dateFrom);       	
    	sql.append("' AND '");   
    	sql.append(dateTo);  
    	sql.append("' AND e.empId = ");   
    	sql.append(empId);  
    	sql.append(" ORDER BY pp.fromDate desc ");   	
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getGSISReport SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
    	
    	while (rs.next()) {    	
    		GSISReport model = new GSISReport();     		    		
    		
    		model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name"));
			model.setEmployeeShare(rs.getBigDecimal("philhealthEmployeeShare"));
			model.setEmployerShare(rs.getBigDecimal("philhealthEmployerShare"));
			model.setGsisNo(rs.getString("phic"));
			model.setPayrollPeriod(rs.getString("payPeriod"));
			model.setTotalContribution(rs.getBigDecimal("totalContribution"));
    		
	    	list.add(model);
	    }
    	
    	if (list.isEmpty()) {    	
    		GSISReport model = new GSISReport();
			
			model.setEmpNo("");
			model.setEmpName("");
			model.setEmployeeShare(BigDecimal.ZERO);
			model.setEmployerShare(BigDecimal.ZERO);
			model.setGsisNo("");
			model.setPayrollPeriod("");
			model.setTotalContribution(BigDecimal.ZERO);		
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();    
    	
    	return list;
    }
    
    public List<GSISReport> getHDMFReport (int payrollPeriodId) throws SQLException, Exception {
    	List<GSISReport> list = new ArrayList<GSISReport>();
    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	
    	sql.append("SELECT (e.firstname + ' ' + e.lastname) as name, e.empNo, epr.pagibigEmployeeShare, epr.pagibigEmployerShare, (epr.pagibigEmployeeShare +  epr.pagibigEmployerShare) as totalContribution, e.hdmf, ");    	    	    	
    	sql.append(" CONVERT(VARCHAR(12),pp.fromDate,107) + ' to ' + CONVERT(VARCHAR(12),pp.toDate,107) as payPeriod  FROM employeePayrollRun epr, employee e, payrollPeriod pp "); 
    	sql.append(" WHERE epr.empId = e.empId and epr.payrollPeriodId = pp.payrollPeriodId AND pp.payrollPeriodId = ");    
    	sql.append(payrollPeriodId);       	
    	sql.append(" ORDER BY name, pp.fromDate desc ");   	
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getGSISReport SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
    	
    	while (rs.next()) {    	
    		GSISReport model = new GSISReport();     		    		
    		
    		model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name"));
			model.setEmployeeShare(rs.getBigDecimal("pagibigEmployeeShare"));
			model.setEmployerShare(rs.getBigDecimal("pagibigEmployerShare"));
			model.setGsisNo(rs.getString("hdmf"));
			model.setPayrollPeriod(rs.getString("payPeriod"));
			model.setTotalContribution(rs.getBigDecimal("totalContribution"));
    		
	    	list.add(model);
	    }
    	
    	if (list.isEmpty()) {    	
    		GSISReport model = new GSISReport();
			
			model.setEmpNo("");
			model.setEmpName("");
			model.setEmployeeShare(BigDecimal.ZERO);
			model.setEmployerShare(BigDecimal.ZERO);
			model.setGsisNo("");
			model.setPayrollPeriod("");
			model.setTotalContribution(BigDecimal.ZERO);		
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();    
    	
    	return list;
    }
    
    public List<GSISReport> getHDMFReportByDateRangeAndId (int empId, String dateFrom, String dateTo) throws SQLException, Exception {
    	List<GSISReport> list = new ArrayList<GSISReport>();
    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	
    	sql.append("SELECT (e.firstname + ' ' + e.lastname) as name, e.empNo, epr.pagibigEmployeeShare, epr.pagibigEmployerShare, (epr.pagibigEmployeeShare +  epr.pagibigEmployerShare) as totalContribution, e.hdmf, ");    	    	    	
    	sql.append(" CONVERT(VARCHAR(12),pp.fromDate,107) + ' to ' + CONVERT(VARCHAR(12),pp.toDate,107) as payPeriod  FROM employeePayrollRun epr, employee e, payrollPeriod pp "); 
    	sql.append(" WHERE epr.empId = e.empId and epr.payrollPeriodId = pp.payrollPeriodId AND pp.fromDate BETWEEN '");    
    	sql.append(dateFrom);       	
    	sql.append("' AND '");   
    	sql.append(dateTo);  
    	sql.append("' AND e.empId = ");   
    	sql.append(empId);  
    	sql.append(" ORDER BY pp.fromDate desc ");   	
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getGSISReport SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
    	
    	while (rs.next()) {    	
    		GSISReport model = new GSISReport();     		    		
    		
    		model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name"));
			model.setEmployeeShare(rs.getBigDecimal("pagibigEmployeeShare"));
			model.setEmployerShare(rs.getBigDecimal("pagibigEmployerShare"));
			model.setGsisNo(rs.getString("hdmf"));
			model.setPayrollPeriod(rs.getString("payPeriod"));
			model.setTotalContribution(rs.getBigDecimal("totalContribution"));
    		
	    	list.add(model);
	    }
    	
    	if (list.isEmpty()) {    	
    		GSISReport model = new GSISReport();
			
			model.setEmpNo("");
			model.setEmpName("");
			model.setEmployeeShare(BigDecimal.ZERO);
			model.setEmployerShare(BigDecimal.ZERO);
			model.setGsisNo("");
			model.setPayrollPeriod("");
			model.setTotalContribution(BigDecimal.ZERO);		
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();    
    	
    	return list;
    }
    
    public List<GSISReport> getGSISReportByDateRangeAndId (int empId, String dateFrom, String dateTo) throws SQLException, Exception {
    	List<GSISReport> list = new ArrayList<GSISReport>();
    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	
    	sql.append("SELECT (e.firstname + ' ' + e.lastname) as name, e.empNo, epr.gsisEmployeeShare, epr.gsisEmployerShare, (epr.gsisEmployeeShare +  epr.gsisEmployerShare) as totalContribution, e.gsis, ");    	    	    	
    	sql.append(" CONVERT(VARCHAR(12),pp.fromDate,107) + ' to ' + CONVERT(VARCHAR(12),pp.toDate,107) as payPeriod FROM employeePayrollRun epr, employee e, payrollPeriod pp "); 
    	sql.append(" WHERE epr.empId = e.empId and epr.payrollPeriodId = pp.payrollPeriodId AND pp.fromDate BETWEEN '");    
    	sql.append(dateFrom);       	
    	sql.append("' AND '");   
    	sql.append(dateTo);  
    	sql.append("' AND e.empId = ");   
    	sql.append(empId);  
    	sql.append(" ORDER BY pp.fromDate desc ");   	
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getGSISReportByDateRangeAndId SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
    	
    	while (rs.next()) {    	
    		GSISReport model = new GSISReport();     		    		
    		
    		model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name"));
			model.setEmployeeShare(rs.getBigDecimal("gsisEmployeeShare"));
			model.setEmployerShare(rs.getBigDecimal("gsisEmployerShare"));
			model.setGsisNo(rs.getString("gsis"));
			model.setPayrollPeriod(rs.getString("payPeriod"));
			model.setTotalContribution(rs.getBigDecimal("totalContribution"));
    		
	    	list.add(model);
	    }
    	
    	if (list.isEmpty()) {    	
    		GSISReport model = new GSISReport();
			
			model.setEmpNo("");
			model.setEmpName("");
			model.setEmployeeShare(BigDecimal.ZERO);
			model.setEmployerShare(BigDecimal.ZERO);
			model.setGsisNo("");
			model.setPayrollPeriod("");
			model.setTotalContribution(BigDecimal.ZERO);		
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();    
    	
    	return list;
    }
    
    public List<GSISReport> getGSISReport (int payrollPeriodId) throws SQLException, Exception {
    	List<GSISReport> list = new ArrayList<GSISReport>();
    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	
    	sql.append("SELECT (e.firstname + ' ' + e.lastname) as name, e.empNo, epr.gsisEmployeeShare, epr.gsisEmployerShare, (epr.gsisEmployeeShare +  epr.gsisEmployerShare) as totalContribution, e.gsis, ");    	    	    	
    	sql.append(" CONVERT(VARCHAR(12),pp.fromDate,107) + ' to ' + CONVERT(VARCHAR(12),pp.toDate,107) as payPeriod FROM employeePayrollRun epr, employee e, payrollPeriod pp "); 
    	sql.append(" WHERE epr.empId = e.empId and epr.payrollPeriodId = pp.payrollPeriodId AND pp.payrollPeriodId = ");    
    	sql.append(payrollPeriodId);       	
    	sql.append(" ORDER BY name, pp.fromDate desc ");   	
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getGSISReport SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
    	
    	while (rs.next()) {    	
    		GSISReport model = new GSISReport();     		    		
    		
    		model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name"));
			model.setEmployeeShare(rs.getBigDecimal("gsisEmployeeShare"));
			model.setEmployerShare(rs.getBigDecimal("gsisEmployerShare"));
			model.setGsisNo(rs.getString("gsis"));
			model.setPayrollPeriod(rs.getString("payPeriod"));
			model.setTotalContribution(rs.getBigDecimal("totalContribution"));
    		
	    	list.add(model);
	    }
    	
    	if (list.isEmpty()) {    	
    		GSISReport model = new GSISReport();
			
			model.setEmpNo("");
			model.setEmpName("");
			model.setEmployeeShare(BigDecimal.ZERO);
			model.setEmployerShare(BigDecimal.ZERO);
			model.setGsisNo("");
			model.setPayrollPeriod("");
			model.setTotalContribution(BigDecimal.ZERO);		
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();    
    	
    	return list;
    }
    
    //TODO Should Get Other Deductions and Loans
//    public List<Payslip> getPaylipByEmpId (List<Employee> empIdList, int payrollPeriodId) throws SQLException, Exception {
//    	List<Payslip> list = new ArrayList<Payslip>();
//    	
//    	StringBuffer sql = new StringBuffer();    	
//    	
//    	
//    	sql.append("SELECT e.empId, pp.payrollPeriodId, (e.firstname + ' ' + e.lastname) as name, (SUBSTRING(CONVERT(VARCHAR,pp.fromDate), 1, 11) + ' To ' + SUBSTRING(CONVERT(VARCHAR,pp.toDate), 1, 11)) as payPeriod, ");    	  
//    	sql.append(" e.taxstatus, pp.payrollType, e.empNo, epr.employeePayrollRunId, epr.empId, epr.taxStatus, epr.basicPay, epr.absences, epr.tardiness, epr.undertime, ");
//    	sql.append(" epr.overtime, epr.leaveWOPay, epr.nightDiff, epr.holidayPay, epr.taxableIncome, epr.nontaxableIncome, epr.grossPay, epr.gsisEmployeeShare, epr.gsisEmployerShare, ");
//    	sql.append(" epr.philhealthEmployeeShare, epr.philhealthEmployerShare, epr.pagibigEmployeeShare, epr.pagibigEmployerShare, epr.loans, epr.otherDeductions, epr.withholdingTax, epr.totalDeductions, ");
//    	sql.append(" d.sectionName, epr.netPay, epr.payrollCode, epr.payrollPeriodId, epr.payrollRunStatus, epr.createdBy, epr.createDate, epr.updatedBy, epr.updatedDate,  ");    	    	
//    	sql.append(" epr.lockedBy, epr.lockedDate, epr.otherTaxableIncome, epr.cola, epr.foodAllowance, epr.tranpoAllowance, epr.taxShield, j.jobTitle ");
//    	sql.append(" FROM employeePayrollRun epr, employee e, payrollPeriod pp, section d, jobTitle j "); 
//    	sql.append(" WHERE e.jobTitleId = j.jobTitleId AND e.sectionId = d.sectionId AND epr.empId = e.empId and epr.payrollPeriodId = pp.payrollPeriodId AND pp.payrollPeriodId = ");    
//    	sql.append(payrollPeriodId);  
//    	sql.append(" AND e.empId in (");    	
//    	sql.append(constructConditionIntValue(empIdList));  
//    	sql.append(")");
//    	
//    	
//    	sql.append(" ORDER BY name ");   	
//    	
//    	PreparedStatement ps = conn.prepareStatement(sql.toString());
//		
//		System.out.println("getPaylipByEmpId SQL: " + sql.toString());
//
//	    ResultSet rs = ps.executeQuery();
//    	
//    	while (rs.next()) {    	
//    		Payslip model = new Payslip();     	
//    		
//    		//Employee Payroll Details
//    		model.setEmpId(rs.getInt("empId"));
//    		model.setPayrollPeriodId(rs.getInt("payrollPeriodId"));
//			model.setName(rs.getString("name"));
//			model.setPositionName(rs.getString("jobTitle"));
//			model.setDepartmentName(rs.getString("sectionName"));
//			model.setPayPeriod(rs.getString("payPeriod"));
//			model.setPayrollType(rs.getString("payrollType"));
//			model.setTaxStatus(rs.getString("taxstatus"));				
//			model.setTaxShield(rs.getBigDecimal("taxShield"));
//			
//			//Income
//			model.setBasicPay(rs.getBigDecimal("basicPay"));
//			model.setEmpNo(rs.getString("empNo"));
//			model.setHolidayPay(rs.getBigDecimal("holidayPay"));
//			model.setNightDiff(rs.getBigDecimal("nightDiff"));
//			model.setCola(rs.getBigDecimal("cola"));
//			model.setTranpoAllowance(rs.getBigDecimal("tranpoAllowance"));
//			model.setFoodAllowance(rs.getBigDecimal("foodAllowance"));
//			model.setOvertime(rs.getBigDecimal("overtime"));
//			
//			if(rs.getBigDecimal("otherTaxableIncome") != null){
//				model.setOtherTaxableIncome(rs.getBigDecimal("otherTaxableIncome"));
//			} else {
//				model.setOtherTaxableIncome(BigDecimal.ZERO);
//			}
//			model.setNontaxableIncome(rs.getBigDecimal("nontaxableIncome"));
//			
//			BigDecimal totalEarnings = new BigDecimal(0);
//			
//			totalEarnings = totalEarnings.add(model.getBasicPay() != null ? model.getBasicPay() : BigDecimal.ZERO);
//			totalEarnings = totalEarnings.add(model.getNightDiff() != null ? model.getNightDiff() : BigDecimal.ZERO);
//			totalEarnings = totalEarnings.add(model.getOvertime() != null ? model.getOvertime() : BigDecimal.ZERO);
//			totalEarnings = totalEarnings.add(model.getHolidayPay() != null ? model.getHolidayPay() : BigDecimal.ZERO);
//			totalEarnings = totalEarnings.add(model.getOtherTaxableIncome() != null ? model.getOtherTaxableIncome() : BigDecimal.ZERO);
//			totalEarnings = totalEarnings.add(model.getNontaxableIncome() != null ? model.getNontaxableIncome() : BigDecimal.ZERO);
//			totalEarnings = totalEarnings.add(model.getCola() != null ? model.getCola() : BigDecimal.ZERO);
//			totalEarnings = totalEarnings.add(model.getFoodAllowance() != null ? model.getFoodAllowance() : BigDecimal.ZERO);
//			totalEarnings = totalEarnings.add(model.getTranpoAllowance() != null ? model.getTranpoAllowance() : BigDecimal.ZERO);
//			
//			totalEarnings = totalEarnings.subtract(model.getAbsences() != null ? model.getAbsences() : BigDecimal.ZERO);
//			totalEarnings = totalEarnings.subtract(model.getTardiness() != null ? model.getTardiness() : BigDecimal.ZERO);
//			totalEarnings = totalEarnings.subtract(model.getUndertime() != null ? model.getUndertime() : BigDecimal.ZERO);
//			
//			System.out.println("GROSS: " + rs.getBigDecimal("grossPay").doubleValue());
//			System.out.println("TOTAL EARNINGS: " + totalEarnings.doubleValue());
//			
//			model.setTotalEarnings(totalEarnings);
//			model.setGrossPay(rs.getBigDecimal("grossPay"));
//			
//			//Deductions
//			model.setGsisEmployeeShare(rs.getBigDecimal("gsisEmployeeShare"));
//			model.setGsisEmployerShare(rs.getBigDecimal("gsisEmployerShare"));
//			model.setPhilhealthEmployeeShare(rs.getBigDecimal("philhealthEmployeeShare"));
//			model.setPhilhealthEmployerShare(rs.getBigDecimal("philhealthEmployerShare"));
//			model.setPagibigEmployeeShare(rs.getBigDecimal("pagibigEmployeeShare"));
//			model.setPagibigEmployerShare(rs.getBigDecimal("pagibigEmployerShare"));
//			model.setAbsences(rs.getBigDecimal("absences"));
//			model.setTardiness(rs.getBigDecimal("tardiness"));			
//			model.setUndertime(rs.getBigDecimal("undertime"));
//			model.setWithholdingTax(rs.getBigDecimal("withholdingTax"));
//			
//			BigDecimal finalDeduction = BigDecimal.ZERO;
//			
//			finalDeduction = finalDeduction.add(rs.getBigDecimal("withholdingTax"));
//			
//			
//			
//			
//			int counter = 0;
//			BigDecimal otherDeduction = BigDecimal.ZERO;
//			
//			List<BigDecimal> deductionList = getOtherDeductionsForPayslip(model, counter, otherDeduction);
//			List<BigDecimal> loanList = getLoansForPayslip(model, counter, otherDeduction);
//			
//			for(BigDecimal deduction : deductionList){
//				finalDeduction = finalDeduction.add(deduction);
//			}
//			
//			for(BigDecimal loan : loanList){
//				finalDeduction = finalDeduction.add(loan);
//			}
//			
//			model.setTotalDeductions(finalDeduction);	
//			
//			if("M".equals(model.getPayrollType())){
//				BigDecimal netPay = rs.getBigDecimal("netPay").divide(new BigDecimal(2));
//				model.setNetPay(netPay);
//				model.setNetPay2(netPay);
//			} else {
//				model.setNetPay(rs.getBigDecimal("netPay"));
//			}
//			
//			
//    		
//	    	list.add(model);
//	    	list.add(model);
//	    	list.add(model);
//	    	list.add(model);
//	    }
//    	
//    	if (list.isEmpty()) {    	
//    		Payslip model = new Payslip();     		    		
//    		
//    		model.setAbsences(BigDecimal.ZERO);
//			model.setBasicPay(BigDecimal.ZERO);
//			model.setEmpNo("");
//			model.setGsisEmployeeShare(BigDecimal.ZERO);
//			model.setGsisEmployerShare(BigDecimal.ZERO);
//			model.setHolidayPay(BigDecimal.ZERO);
//			model.setName("");
//			model.setNetPay(BigDecimal.ZERO);
//			model.setNightDiff(BigDecimal.ZERO);
//			model.setOtherTaxableIncome(BigDecimal.ZERO);
//			model.setOvertime(BigDecimal.ZERO);
//			model.setPagibigEmployeeShare(BigDecimal.ZERO);
//			model.setPagibigEmployerShare(BigDecimal.ZERO);
//			model.setPayPeriod("");
//			model.setPayrollType("");
//			model.setPhilhealthEmployeeShare(BigDecimal.ZERO);
//			model.setPhilhealthEmployerShare(BigDecimal.ZERO);
//			model.setTardiness(BigDecimal.ZERO);
//			model.setTaxStatus("");
//			model.setTotalDeductions(BigDecimal.ZERO);
//			model.setTotalEarnings(BigDecimal.ZERO);
//			model.setUndertime(BigDecimal.ZERO);
//			model.setWithholdingTax(BigDecimal.ZERO);
//			model.setNontaxableIncome(BigDecimal.ZERO);
//			model.setDepartmentName("");
//			
//			model.setCola(BigDecimal.ZERO);
//			model.setTranpoAllowance(BigDecimal.ZERO);
//			model.setFoodAllowance(BigDecimal.ZERO);
//			model.setOtherDedAmount1(BigDecimal.ZERO);
//			model.setOtherDedName1("Other Deductions");
//			model.setLoanAmount1(BigDecimal.ZERO);
//			model.setLoanName1("Other Loans");
//			model.setGrossPay(BigDecimal.ZERO);
//			model.setName("");
//			model.setPositionName("");
//			
//	    	list.add(model);
//	    	list.add(model);
//	    	list.add(model);
//	    	list.add(model);
//	    }
//	    
//	    ps.close();
//	    rs.close();    
//    	
//    	return list;
//    }
    
    
    //TODO
    public List<PayslipZamboanga> getPaylipInfoZamboanga (List<Employee> empIdList, int payrollPeriodId) throws SQLException, Exception {
    	List<PayslipZamboanga> list = new ArrayList<PayslipZamboanga>();
    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	
    	sql.append("SELECT e.empId, pp.payrollPeriodId, (e.firstname + ' ' + e.lastname) as name, CONVERT(VARCHAR(12),fromDate,107) + ' to ' + CONVERT(VARCHAR(12),toDate,107) as payPeriod, ");    	  
    	sql.append(" e.taxstatus, pp.payrollType, e.empNo, epr.employeePayrollRunId, epr.empId, epr.taxStatus, epr.basicPay, epr.absences, epr.tardiness, epr.undertime, ");
    	sql.append(" epr.overtime, epr.leaveWOPay, epr.nightDiff, epr.holidayPay, epr.taxableIncome, epr.nontaxableIncome, epr.grossPay, epr.gsisEmployeeShare, epr.gsisEmployerShare, ");
    	sql.append(" epr.philhealthEmployeeShare, epr.philhealthEmployerShare, epr.pagibigEmployeeShare, epr.pagibigEmployerShare, epr.loans, epr.otherDeductions, epr.withholdingTax, epr.totalDeductions, ");
    	sql.append(" d.sectionName, epr.netPay, epr.payrollCode, epr.payrollPeriodId, epr.payrollRunStatus, epr.createdBy, epr.createDate, epr.updatedBy, epr.updatedDate,  ");    	    	
    	sql.append(" epr.lockedBy, epr.lockedDate, epr.otherTaxableIncome, epr.cola, epr.foodAllowance, epr.tranpoAllowance, epr.taxShield, j.jobTitle ");
    	sql.append(" FROM employeePayrollRun epr, employee e, payrollPeriod pp, section d, jobTitle j "); 
    	sql.append(" WHERE e.jobTitleId = j.jobTitleId AND e.sectionId = d.sectionId AND epr.empId = e.empId and epr.payrollPeriodId = pp.payrollPeriodId AND pp.payrollPeriodId = ");    
    	sql.append(payrollPeriodId);  
    	sql.append(" AND e.empId in (");    	
    	sql.append(constructConditionIntValue(empIdList));  
    	sql.append(")");
    	
    	
    	sql.append(" ORDER BY name ");   	
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getPaylipByEmpId SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
    	
    	while (rs.next()) {    	
    		PayslipZamboanga model = new PayslipZamboanga();     	
    		
    		//Employee Payroll Details
    		model.setEmpId(rs.getInt("empId"));
    		model.setPayrollPeriodId(rs.getInt("payrollPeriodId"));
			model.setName(rs.getString("name"));
			model.setPositionName(rs.getString("jobTitle"));
			model.setDepartmentName(rs.getString("sectionName"));
			model.setPayPeriod(rs.getString("payPeriod"));
			model.setPayrollType(rs.getString("payrollType"));
			model.setTaxStatus(rs.getString("taxstatus"));				
			model.setTaxShield(rs.getBigDecimal("taxShield"));
			
			//Income
			model.setBasicPay(rs.getBigDecimal("basicPay"));
			model.setEmpNo(rs.getString("empNo"));
			model.setHolidayPay(rs.getBigDecimal("holidayPay"));
			model.setNightDiff(rs.getBigDecimal("nightDiff"));
			model.setCola(rs.getBigDecimal("cola"));
			model.setTranpoAllowance(rs.getBigDecimal("tranpoAllowance"));
			model.setFoodAllowance(rs.getBigDecimal("foodAllowance"));
			model.setOvertime(rs.getBigDecimal("overtime"));
			
			if(rs.getBigDecimal("otherTaxableIncome") != null){
				model.setOtherTaxableIncome(rs.getBigDecimal("otherTaxableIncome"));
			} else {
				model.setOtherTaxableIncome(BigDecimal.ZERO);
			}
			model.setNontaxableIncome(rs.getBigDecimal("nontaxableIncome"));
			
			BigDecimal totalEarnings = new BigDecimal(0);
			
			totalEarnings = totalEarnings.add(model.getBasicPay() != null ? model.getBasicPay() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.add(model.getNightDiff() != null ? model.getNightDiff() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.add(model.getOvertime() != null ? model.getOvertime() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.add(model.getHolidayPay() != null ? model.getHolidayPay() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.add(model.getOtherTaxableIncome() != null ? model.getOtherTaxableIncome() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.add(model.getNontaxableIncome() != null ? model.getNontaxableIncome() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.add(model.getCola() != null ? model.getCola() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.add(model.getFoodAllowance() != null ? model.getFoodAllowance() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.add(model.getTranpoAllowance() != null ? model.getTranpoAllowance() : BigDecimal.ZERO);
			
			totalEarnings = totalEarnings.subtract(model.getAbsences() != null ? model.getAbsences() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.subtract(model.getTardiness() != null ? model.getTardiness() : BigDecimal.ZERO);
			totalEarnings = totalEarnings.subtract(model.getUndertime() != null ? model.getUndertime() : BigDecimal.ZERO);
			
			System.out.println("GROSS: " + rs.getBigDecimal("grossPay").doubleValue());
			System.out.println("TOTAL EARNINGS: " + totalEarnings.doubleValue());
			
			model.setTotalEarnings(totalEarnings);
			model.setGrossPay(rs.getBigDecimal("grossPay"));
			
			//Deductions
			model.setGsisEmployeeShare(rs.getBigDecimal("gsisEmployeeShare"));
			model.setGsisEmployerShare(rs.getBigDecimal("gsisEmployerShare"));
			model.setPhilhealthEmployeeShare(rs.getBigDecimal("philhealthEmployeeShare"));
			model.setPhilhealthEmployerShare(rs.getBigDecimal("philhealthEmployerShare"));
			model.setPagibigEmployeeShare(rs.getBigDecimal("pagibigEmployeeShare"));
			model.setPagibigEmployerShare(rs.getBigDecimal("pagibigEmployerShare"));
			model.setAbsences(rs.getBigDecimal("absences"));
			model.setTardiness(rs.getBigDecimal("tardiness"));			
			model.setUndertime(rs.getBigDecimal("undertime"));
			model.setWithholdingTax(rs.getBigDecimal("withholdingTax"));
			
			BigDecimal finalDeduction = BigDecimal.ZERO;
			
			finalDeduction = finalDeduction.add(rs.getBigDecimal("withholdingTax"));			
			
			//GET INCOME DISPLAY
			int counter2 = 1;
			
			StringBuffer sql3 = new StringBuffer();   
	    	
	    	
	    	sql3.append("SELECT ei.amount, i.incomeName FROM empIncomeNew ei, income i WHERE ei.incomeId = i.incomeId AND ei.incomeType = 'R' AND ei.empId = ");	
	    	sql3.append(model.getEmpId());      	
	    	sql3.append(" UNION SELECT ei2.amount, i2.incomeName FROM empIncomeNew ei2, income i2 WHERE ei2.incomeId = i2.incomeId AND ei2.incomeType = 'O' AND ei2.payrollPeriodId = ");
	    	sql3.append(model.getPayrollPeriodId());
	    	sql3.append(" AND ei2.empId = ");	
	    	sql3.append(model.getEmpId());      	
	    	sql3.append(" UNION SELECT 0 as amount, incomeName FROM income a WHERE NOT EXISTS (SELECT 'x' FROM empIncomeNew b WHERE a.incomeId = b.incomeId) ORDER BY amount desc");
	    	
	    	
			
			System.out.println("SQL getIncome: " + sql3.toString());   	
	    		
			PreparedStatement ps3 = conn.prepareStatement(sql3.toString());		
			
		    ResultSet rs3 = ps3.executeQuery();
		    
		    while (rs3.next()) {
		    	
		    	setPayslipIncome(counter2, model, rs3.getBigDecimal("amount"), rs3.getString("incomeName"));
		    	
		    	System.out.println("counter: " + counter2);   	
		    	System.out.println("incomeName: " + rs3.getString("incomeName"));   	
		    	System.out.println("amount: " + rs3.getBigDecimal("amount").doubleValue());   
		    	
		    	counter2++;	    	
		    	
		    }
			
			
			//GET DEDUCTIONS AND LOANS
			
			int counter = 1;
			
			StringBuffer sql2 = new StringBuffer();   
	    	
	    	
			sql2.append("SELECT ed.amount as amount, d.deductionName as name FROM empPayDeduction ed, deduction d WHERE d.deductionId = ed.deductionId AND empId = ");	
	    	sql2.append(model.getEmpId());      	
	    	sql2.append(" AND payrollPeriodId = ");
	    	sql2.append(model.getPayrollPeriodId());
	    	sql2.append(" UNION SELECT 0 as amount, deductionName as name FROM deduction a WHERE NOT EXISTS (SELECT 'x' FROM empPayDeduction b WHERE a.deductionId = b.deductionId)");
	    	sql2.append(" UNION SELECT lp.paidAmount as amount, lt.loanTypeName as name FROM loanPayments lp, loanEntry le, loanType lt WHERE le.loanTypeId = lt.loanTypeId ");
	    	sql2.append("AND lp.loanEntryId = le.loanEntryId AND le.empId = ");
	    	sql2.append(model.getEmpId());      	
	    	sql2.append(" AND lp.payrollPeriodId = ");
	    	sql2.append(model.getPayrollPeriodId());
	    	sql2.append(" UNION SELECT 0 as amount, loanTypeName as name FROM loanType a WHERE NOT EXISTS (SELECT 'x' FROM loanPayments b, loanEntry c WHERE a.loanTypeId = c.loanTypeId and c.loanEntryId = b.loanEntryId)");
	    	
			
			System.out.println("SQL getDeductionsAndLoans: " + sql2.toString());   	
	    		
			PreparedStatement ps2 = conn.prepareStatement(sql2.toString());		
			
		    ResultSet rs2 = ps2.executeQuery();
		    
		    while (rs2.next()) {
		    	
		    	setPayslipDeductions(counter, model, rs2.getBigDecimal("amount"), rs2.getString("name"), "", "");
		    	finalDeduction = finalDeduction.add(rs2.getBigDecimal("amount"));	   
		    	
		    	System.out.println("counter: " + counter);   	
		    	System.out.println("deductionName: " + rs2.getString("name"));   	
		    	System.out.println("paidAmount: " + rs2.getBigDecimal("amount").doubleValue());   	
		    	System.out.println("finalDeduction: " + finalDeduction.doubleValue());   	
		    	
		    	counter++;	    	
		    	
		    }
			
			
			model.setTotalDeductions(finalDeduction);	
			
			if("M".equals(model.getPayrollType())){
				BigDecimal netPay = rs.getBigDecimal("netPay").divide(new BigDecimal(2));
				model.setNetPay(rs.getBigDecimal("netPay"));
				
				double netPay1 = netPay.doubleValue();
				double fractionalPart = netPay1 % 1;
				netPay1 = netPay1 - fractionalPart;			
				
				double netPay2 = netPay.doubleValue() + fractionalPart;
				
				model.setNetPay1(new BigDecimal(netPay1));
				model.setNetPay2(new BigDecimal(netPay2));
			} else {
				model.setNetPay(rs.getBigDecimal("netPay"));
			}
			
			
    		
	    	list.add(model);
	    	list.add(model);
	    	list.add(model);
	    	list.add(model);
	    }
    	
    	if (list.isEmpty()) {    	
    		PayslipZamboanga model = new PayslipZamboanga();     		    		
    		
    		model.setAbsences(BigDecimal.ZERO);
			model.setBasicPay(BigDecimal.ZERO);
			model.setEmpNo("");
			model.setGsisEmployeeShare(BigDecimal.ZERO);
			model.setGsisEmployerShare(BigDecimal.ZERO);
			model.setHolidayPay(BigDecimal.ZERO);
			model.setName("");
			model.setNetPay(BigDecimal.ZERO);
			model.setNightDiff(BigDecimal.ZERO);
			model.setOtherTaxableIncome(BigDecimal.ZERO);
			model.setOvertime(BigDecimal.ZERO);
			model.setPagibigEmployeeShare(BigDecimal.ZERO);
			model.setPagibigEmployerShare(BigDecimal.ZERO);
			model.setPayPeriod("");
			model.setPayrollType("");
			model.setPhilhealthEmployeeShare(BigDecimal.ZERO);
			model.setPhilhealthEmployerShare(BigDecimal.ZERO);
			model.setTardiness(BigDecimal.ZERO);
			model.setTaxStatus("");
			model.setTotalDeductions(BigDecimal.ZERO);
			model.setTotalEarnings(BigDecimal.ZERO);
			model.setUndertime(BigDecimal.ZERO);
			model.setWithholdingTax(BigDecimal.ZERO);
			model.setNontaxableIncome(BigDecimal.ZERO);
			model.setDepartmentName("");
			
			model.setCola(BigDecimal.ZERO);
			model.setTranpoAllowance(BigDecimal.ZERO);
			model.setFoodAllowance(BigDecimal.ZERO);
			model.setOtherDedAmount1(BigDecimal.ZERO);
			model.setOtherDedName1("Other Deductions");
			model.setGrossPay(BigDecimal.ZERO);
			model.setName("");
			model.setPositionName("");
			
	    	list.add(model);
	    	list.add(model);
	    	list.add(model);
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();    
    	
    	return list;
    }
    
//    private void getOtherDeductionsForPayslip (PayslipZamboanga payslip, int counter, BigDecimal totalDeductions)  throws SQLException, Exception {
//    	StringBuffer sql = new StringBuffer();   
//    	
//    	
//    	sql.append("SELECT ed.amount, d.deductionName FROM empPayDeduction ed, deduction d WHERE d.deductionId = ed.deductionId AND empId = ");	
//    	sql.append(payslip.getEmpId());      	
//    	sql.append(" AND payrollPeriodId = ");
//    	sql.append(payslip.getPayrollPeriodId());
//    	sql.append(" UNION SELECT 0, deductionName FROM deduction a WHERE NOT EXISTS (SELECT 'x' FROM empPayDeduction b WHERE a.deductionId = b.deductionId)");
//    	
//		
//		System.out.println("SQL getOtherDeductionsForPayslip: " + sql.toString());   	
//    		
//		PreparedStatement ps = conn.prepareStatement(sql.toString());		
//		
//	    ResultSet rs = ps.executeQuery();
//	    
//	   
//	    
//	    while (rs.next()) {
//	    	
//	    	setPayslipDeductions(counter, payslip, rs.getBigDecimal("amount"), rs.getString("deductionName"), totalDeductions);	   
//	    	
//	    	System.out.println("counter: " + counter);   	
//	    	System.out.println("deductionName: " + rs.getString("deductionName"));   	
//	    	System.out.println("paidAmount: " + rs.getBigDecimal("amount").doubleValue());   	
//	    	System.out.println("totalDeductions: " + totalDeductions.doubleValue());   	
//	    	
//	    	counter++;	    	
//	    	
//	    }
//	    
//	   
//	    ps.close();
//	    rs.close();
//    }
//    
//    private void getLoansForPayslip (PayslipZamboanga payslip, int counter, BigDecimal totalDeductions)  throws SQLException, Exception {
//    	StringBuffer sql = new StringBuffer();   
//    	
//    	
//    	sql.append("SELECT lt.loanTypeName, lp.paidAmount FROM loanPayments lp, loanEntry le, loanType lt WHERE le.loanTypeId = lt.loanTypeId ");
//    	sql.append("AND lp.loanEntryId = le.loanEntryId AND le.empId = ");
//    	sql.append(payslip.getEmpId());      	
//    	sql.append(" AND lp.payrollPeriodId = ");
//    	sql.append(payslip.getPayrollPeriodId());
//    	sql.append(" UNION SELECT loanTypeName, 0 FROM loanType a WHERE NOT EXISTS (SELECT 'x' FROM loanPayments b, loanEntry c WHERE a.loanTypeId = c.loanTypeId and c.loanEntryId = b.loanEntryId)");
//    	
//		
//		System.out.println("SQL getLoansForPayslip: " + sql.toString());   	
//    		
//		PreparedStatement ps = conn.prepareStatement(sql.toString());		
//		
//	    ResultSet rs = ps.executeQuery();
//	    
//	    while (rs.next()) {
//	    	
//	    	setPayslipDeductions(counter, payslip, rs.getBigDecimal("paidAmount"), rs.getString("loanTypeName"), totalDeductions);
//	    	
//	    	System.out.println("counter: " + counter);   	
//	    	System.out.println("deductionName: " + rs.getString("loanTypeName"));   
//	    	System.out.println("paidAmount: " + rs.getBigDecimal("paidAmount").doubleValue());   	
//	    	System.out.println("totalDeductions: " + totalDeductions.doubleValue());   	
//	    	
//	    	counter++;	
//	    	
//	    }
//	    
//	   
//	    ps.close();
//	    rs.close();
//    }
    
    private void setPayslipDeductions(int counter, PayslipZamboanga payslip, BigDecimal amount, String deductionName, String deductionType, String cod){
    	
    	
    	switch (counter) {
			case 1:	payslip.setOtherDedAmount1(amount);
					payslip.setOtherDedName1(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
					break;
			case 2: payslip.setOtherDedAmount2(amount);
					payslip.setOtherDedName2(deductionName);
					payslip.setCod2(cod);
					payslip.setDeductionType2(deductionType);
					break;
			case 3: payslip.setOtherDedAmount3(amount);
					payslip.setOtherDedName3(deductionName);
					payslip.setCod3(cod);
					payslip.setDeductionType3(deductionType);
					break;
			case 4: payslip.setOtherDedAmount4(amount);
					payslip.setOtherDedName4(deductionName);
					payslip.setCod4(cod);
					payslip.setDeductionType4(deductionType);
					break;
			case 5: payslip.setOtherDedAmount5(amount);
					payslip.setOtherDedName5(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 6: payslip.setOtherDedAmount6(amount);
					payslip.setOtherDedName6(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 7: payslip.setOtherDedAmount7(amount);
					payslip.setOtherDedName7(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 8: payslip.setOtherDedAmount8(amount);
					payslip.setOtherDedName8(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 9: payslip.setOtherDedAmount9(amount);
					payslip.setOtherDedName9(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 10: payslip.setOtherDedAmount10(amount);
					payslip.setOtherDedName10(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 11: payslip.setOtherDedAmount11(amount);
					payslip.setOtherDedName11(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 12: payslip.setOtherDedAmount12(amount);
					payslip.setOtherDedName12(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 13: payslip.setOtherDedAmount13(amount);
					payslip.setOtherDedName13(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 14: payslip.setOtherDedAmount14(amount);
					payslip.setOtherDedName14(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 15: payslip.setOtherDedAmount15(amount);
					payslip.setOtherDedName15(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 16: payslip.setOtherDedAmount16(amount);
					payslip.setOtherDedName16(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 17: payslip.setOtherDedAmount17(amount);
					payslip.setOtherDedName17(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 18: payslip.setOtherDedAmount18(amount);
					payslip.setOtherDedName18(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 19: payslip.setOtherDedAmount19(amount);
					payslip.setOtherDedName19(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 20: payslip.setOtherDedAmount20(amount);
					payslip.setOtherDedName20(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 21: payslip.setOtherDedAmount21(amount);
					payslip.setOtherDedName21(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 22: payslip.setOtherDedAmount22(amount);
					payslip.setOtherDedName22(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 23: payslip.setOtherDedAmount23(amount);
					payslip.setOtherDedName23(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 24: payslip.setOtherDedAmount24(amount);
					payslip.setOtherDedName24(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 25: payslip.setOtherDedAmount25(amount);
					payslip.setOtherDedName25(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 26: payslip.setOtherDedAmount26(amount);
					payslip.setOtherDedName26(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 27: payslip.setOtherDedAmount27(amount);
					payslip.setOtherDedName27(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 28: payslip.setOtherDedAmount28(amount);
					payslip.setOtherDedName28(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 29: payslip.setOtherDedAmount29(amount);
					payslip.setOtherDedName29(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 30: payslip.setOtherDedAmount30(amount);
					payslip.setOtherDedName30(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 31: payslip.setOtherDedAmount31(amount);
					payslip.setOtherDedName31(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 32: payslip.setOtherDedAmount32(amount);
					payslip.setOtherDedName32(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 33: payslip.setOtherDedAmount33(amount);
					payslip.setOtherDedName33(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 34: payslip.setOtherDedAmount34(amount);
					payslip.setOtherDedName34(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 35: payslip.setOtherDedAmount35(amount);
					payslip.setOtherDedName35(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 36: payslip.setOtherDedAmount36(amount);
					payslip.setOtherDedName36(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 37: payslip.setOtherDedAmount37(amount);
					payslip.setOtherDedName37(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 38: payslip.setOtherDedAmount38(amount);
					payslip.setOtherDedName38(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 39: payslip.setOtherDedAmount39(amount);
					payslip.setOtherDedName39(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 40: payslip.setOtherDedAmount40(amount);
					payslip.setOtherDedName40(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 41: payslip.setOtherDedAmount41(amount);
					payslip.setOtherDedName41(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 42: payslip.setOtherDedAmount42(amount);
					payslip.setOtherDedName42(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 43: payslip.setOtherDedAmount43(amount);
					payslip.setOtherDedName43(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 44: payslip.setOtherDedAmount44(amount);
					payslip.setOtherDedName44(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;
			case 45: payslip.setOtherDedAmount45(amount);
					payslip.setOtherDedName45(deductionName);
					payslip.setCod1(cod);
					payslip.setDeductionType1(deductionType);
//					otherDeduction = otherDeduction.add(amount);
					break;			
			default:
//					otherDeduction = otherDeduction.add(amount);
					payslip.setOtherDedAmount45(amount);
					payslip.setOtherDedName45("Other Deductions");
					break;
		}
    	
//    	return otherDeduction;
    }
    
    private void setPayslipIncome(int counter, PayslipZamboanga payslip, BigDecimal amount, String deductionName){
    	
    	
    	switch (counter) {
			case 1:	payslip.setOtherIncomeAmount1(amount);
					payslip.setOtherIncomeName1(deductionName);
					break;
			case 2: payslip.setOtherIncomeAmount2(amount);
					payslip.setOtherIncomeName2(deductionName);
					break;
			case 3: payslip.setOtherIncomeAmount3(amount);
					payslip.setOtherIncomeName3(deductionName);
					break;
			case 4: payslip.setOtherIncomeAmount4(amount);
					payslip.setOtherIncomeName4(deductionName);
					break;
			case 5: payslip.setOtherIncomeAmount5(amount);
					payslip.setOtherIncomeName5(deductionName);
					break;
			case 6: payslip.setOtherIncomeAmount6(amount);
					payslip.setOtherIncomeName6(deductionName);
					break;
			case 7: payslip.setOtherIncomeAmount7(amount);
					payslip.setOtherIncomeName7(deductionName);
					break;			
			default:
					payslip.setOtherIncomeAmount7(amount);
					payslip.setOtherIncomeName7("Other Income");
					break;
		}
    	

    }
    
    public List<Leave> getAllLeavesByDateRange(List<Employee> empIdList, String dateFrom, String dateTo) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();   
    	
    	
    	sql.append("SELECT l.leaveId, l.empId, l.dateFiled, l.dateFrom, l.dateTo, l.noDays, l.remarks, l.status, l.approvedBy, l.secondaryApprover, l.need2Approvers, l.leaveTypeId, l.createdBy, l.createdDate, lt.leaveTypeName ");	
    	
    	sql.append(" FROM leave l, leaveType lt WHERE l.leaveTypeId = lt.leaveTypeId ");
    	sql.append(" AND l.empId in (");    	
    	sql.append(constructConditionIntValue(empIdList));  
    	sql.append(")");
    	sql.append(" AND ((dateFrom between '");
    	sql.append(dateFrom);
    	sql.append("' AND '");
    	sql.append(dateTo);
    	sql.append("') OR (dateTo between '");
    	sql.append(dateFrom);
    	sql.append("' AND '");
    	sql.append(dateTo);
    	sql.append("'))");
    	
		
		System.out.println("SQL getAllLeavesByDateRange: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<Leave> list = new ArrayList<Leave>();
	    
	    while (rs.next()) {
	    	Leave leave = new Leave();
	    	leave.setLeaveId(rs.getInt("leaveId"));
	    	leave.setEmpId(rs.getInt("empId"));
	    	leave.setDateFiled(rs.getDate("dateFiled"));
	    	leave.setDateFrom(rs.getDate("dateFrom"));
	    	leave.setDateTo(rs.getDate("dateTo"));
	    	leave.setNoDays(rs.getInt("noDays"));
	    	leave.setRemarks(rs.getString("remarks"));
	    	leave.setStatus(rs.getInt("status"));
	    	leave.setApprovedBy(rs.getInt("approvedBy"));
	    	leave.setSecondaryApprover(rs.getInt("secondaryApprover"));
	    	leave.setNeed2Approvers(rs.getString("need2Approvers"));
	    	leave.setLeaveTypeId(rs.getInt("leaveTypeId"));
	    	leave.setCreatedBy(rs.getInt("createdBy"));
	    	leave.setCreatedDate(rs.getDate("createdDate"));
	    	leave.setLeaveType(rs.getString("leaveTypeName"));
	    	
	    	list.add(leave);
	    }
	    
	    if (list.isEmpty()) {    	
	    	Leave leave = new Leave();
	    	leave.setLeaveId(0);
	    	leave.setEmpId(0);
	    	leave.setDateFiled(new Date(0));
	    	leave.setDateFrom(new Date(0));
	    	leave.setDateTo(new Date(0));
	    	leave.setNoDays(0);
	    	leave.setRemarks("");
	    	leave.setStatus(4);
	    	leave.setApprovedBy(0);
	    	leave.setSecondaryApprover(0);
	    	leave.setNeed2Approvers("");
	    	leave.setLeaveTypeId(0);
	    	leave.setCreatedBy(0);
	    	leave.setCreatedDate(new Date(0));
	    	leave.setLeaveType("");
	    	
	    	list.add(leave);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;
    }
   
	    
    
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}
    
    

}
