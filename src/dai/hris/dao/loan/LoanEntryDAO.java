package dai.hris.dao.loan;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dai.hris.action.filemanager.util.EmployeeUtil;
import dai.hris.dao.DBConstants;
import dai.hris.model.LeaveCard;
import dai.hris.model.LoanEntry;

public class LoanEntryDAO {
	
	Connection conn = null;
	EmployeeUtil util = new EmployeeUtil();
    
    public LoanEntryDAO() {    	
    	
    	try {
    		
    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("LoanEntryDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("LoanEntryDAO :" + e.getMessage());
		}
    	
    }
    
    public List<LeaveCard> getAllLeaveCreditEntryByEmpId(int empId) throws SQLException, Exception {
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
    
    public  void addLeaveCredit(LeaveCard model) throws SQLException, Exception {
  		String sql = "INSERT INTO leaveCredits (empId,period,particulars,vEarned,vAbsenceWithPay,vBalanceINCL,vBalanceEXCL,vAbsenceWithOutPay,sEarned,sAbsenceWithPay,sBalanceINCL,sBalanceEXCL,sAbsenceWithOutPay,exVacation,exSick,remarks, createdBy,createdDate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATETIME())";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1;
  		
        ps.setInt(index++, model.getEmpId());
        ps.setString(index++, model.getPeriod());
        ps.setString(index++, model.getParticulars());
        ps.setDouble(index++, model.getvEarned());
        ps.setDouble(index++, model.getvAbsenceWithPay());
        ps.setDouble(index++, model.getvBalanceINCL());
        ps.setDouble(index++, model.getvBalanceEXCL());
        ps.setDouble(index++, model.getvAbsenceWithOutPay());
        ps.setDouble(index++, model.getsEarned());
        ps.setDouble(index++, model.getsAbsenceWithPay());
        ps.setDouble(index++, model.getsBalanceINCL());
        ps.setDouble(index++, model.getsBalanceEXCL());
        ps.setDouble(index++, model.getsAbsenceWithOutPay());
        ps.setDouble(index++, model.getExVacation());
        ps.setDouble(index++, model.getExSick());
        ps.setString(index++, model.getRemarks());
        ps.setInt(index++, model.getCreatedBy());

		ps.executeUpdate();
		
		conn.commit();
		 
		ps.close();      

  	}
    
    public  void updateLeaveCredit(LeaveCard model) throws SQLException, Exception {
    	
  		String sql = "UPDATE leaveCredits set period = ?,particulars = ?,vEarned = ?,vAbsenceWithPay = ?,vBalanceINCL = ?,vBalanceEXCL = ?,vAbsenceWithOutPay = ?,sEarned = ?,sAbsenceWithPay = ?,sBalanceINCL = ?,sBalanceEXCL = ?, sAbsenceWithOutPay = ?, exVacation = ?, exSick = ?, remarks = ? WHERE leaveCreditId = ?";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1;
        ps.setString(index++, model.getPeriod());
        ps.setString(index++, model.getParticulars());
        ps.setDouble(index++, model.getvEarned());
        ps.setDouble(index++, model.getvAbsenceWithPay());
        ps.setDouble(index++, model.getvBalanceINCL());
        ps.setDouble(index++, model.getvBalanceEXCL());
        ps.setDouble(index++, model.getvAbsenceWithOutPay());
        ps.setDouble(index++, model.getsEarned());
        ps.setDouble(index++, model.getsAbsenceWithPay());
        ps.setDouble(index++, model.getsBalanceINCL());
        ps.setDouble(index++, model.getsBalanceEXCL());
        ps.setDouble(index++, model.getsAbsenceWithOutPay());
        ps.setDouble(index++, model.getExVacation());
        ps.setDouble(index++, model.getExSick());
        ps.setString(index++, model.getRemarks());
  		ps.setInt(index++, model.getLeaveCreditId());
  		
  		ps.executeUpdate();
        conn.commit();
        ps.close();

  	}
    
    
    
    public List<LoanEntry> getLoanEntryListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "loanAmount ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT e.empNo, le.loanTypeId, le.dateFile, le.referenceNo, le.loanAmount, le.monthlyAmortization, le.startDateToPay, le.endDateToPay, le.PNNo, le.PNDate, le.periodFrom, le.periodTo, le.remarks, le.loanEntryId, le.noOfMonthToPay, le.deductionFlagActive, le.empId, e.firstname, e.lastname, ROW_NUMBER() OVER(ORDER BY ");		
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM loanEntry le, employee e WHERE le.empid = e.empid ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<LoanEntry> list = new ArrayList<LoanEntry>();
	    
	    while (rs.next()) {
	    	
	    	LoanEntry dao =  new LoanEntry();
	    	
	    	dao.setLoanEntryId(rs.getInt("loanEntryId"));	    	
	    	dao.setEmpId(rs.getInt("empId"));
	    	dao.setDateFile(util.sqlDateToString(rs.getDate("dateFile")));
	    	dao.setLoanTypeId(rs.getInt("loanTypeId"));
	     	dao.setReferenceNo(rs.getString("referenceNo"));
	     	dao.setLoanAmount(rs.getBigDecimal("loanAmount"));
	     	dao.setMonthlyAmortization(rs.getBigDecimal("monthlyAmortization"));
	     	dao.setStartDateToPay(util.sqlDateToString(rs.getDate("startDateToPay")));
	     	dao.setEndDateToPay(util.sqlDateToString(rs.getDate("endDateToPay")));
	     	dao.setPNNo(rs.getString("PNNo"));
	     	dao.setPNDate(rs.getString("PNDate"));
	     	dao.setPeriodFrom(rs.getString("periodFrom"));
	     	dao.setPeriodTo(rs.getString("periodTo"));
	     	dao.setRemarks(rs.getString("remarks"));
	     	dao.setDeductionFlagActive(rs.getString("deductionFlagActive"));
	    	
	    	list.add(dao);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   
	}
    
    public  int getCount(int empId) throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM loanEntry WHERE empId = ");
    	sql.append(empId);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    if (rs.next()) {    	
	    	count = rs.getInt("ctr");
	    }
	    
	    ps.close();
	    rs.close();      
	    
    	
    	return count;
    }
    
    public ArrayList<LoanEntry> getAllActiveLoanEntry() throws SQLException, Exception {
    	LoanEntry loanEntry = null;
		ArrayList<LoanEntry> list = new ArrayList<LoanEntry>();
		String sql = "SELECT * FROM loanEntry where deductionFlagActive = 'Y'";
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    while (rs.next()) {
	    	loanEntry = new LoanEntry();
	    	loanEntry.setLoanEntryId(rs.getInt("loanEntryId"));	    	
	    	loanEntry.setEmpId(rs.getInt("empId"));
	    	loanEntry.setDateFile(util.sqlDateToString(rs.getDate("dateFile")));
	     	loanEntry.setLoanTypeId(rs.getInt("loanTypeId"));
	    	loanEntry.setReferenceNo(rs.getString("referenceNo"));
	    	loanEntry.setLoanAmount(rs.getBigDecimal("loanAmount"));
	    	loanEntry.setMonthlyAmortization(rs.getBigDecimal("monthlyAmortization"));
	    	loanEntry.setStartDateToPay(util.sqlDateToString(rs.getDate("startDateToPay")));
	    	loanEntry.setEndDateToPay(util.sqlDateToString(rs.getDate("endDateToPay")));
	    	loanEntry.setPNNo(rs.getString("PNNo"));
	    	loanEntry.setPNDate(rs.getString("PNDate"));
	    	loanEntry.setPeriodFrom(rs.getString("periodFrom"));
	    	loanEntry.setPeriodTo(rs.getString("periodTo"));
	    	loanEntry.setRemarks(rs.getString("remarks"));
	    	loanEntry.setDeductionFlagActive(rs.getString("deductionFlagActive"));
	    	list.add(loanEntry);
		}
	    sql = null;
	    ps.close();
	    rs.close();	 
	    return list;

	}
	
    
    public List<LoanEntry> getAllLoanEntryByEmpId(int empId) throws SQLException, Exception {
    	LoanEntry loanEntry = null;
    	List<LoanEntry> list = new ArrayList<LoanEntry>();
		String sql = "SELECT * FROM loanEntry where empId = ?";
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, empId);

	    ResultSet rs = ps.executeQuery();
	    
	    while (rs.next()) {
	    	loanEntry = new LoanEntry();
	    	loanEntry.setLoanEntryId(rs.getInt("loanEntryId"));	    	
	    	loanEntry.setEmpId(rs.getInt("empId"));
	    	loanEntry.setDateFile(util.sqlDateToString(rs.getDate("dateFile")));
	     	loanEntry.setLoanTypeId(rs.getInt("loanTypeId"));
	    	loanEntry.setReferenceNo(rs.getString("referenceNo"));
	    	loanEntry.setLoanAmount(rs.getBigDecimal("loanAmount"));
	    	loanEntry.setMonthlyAmortization(rs.getBigDecimal("monthlyAmortization"));
	    	loanEntry.setStartDateToPay(util.sqlDateToString(rs.getDate("startDateToPay")));
	    	loanEntry.setEndDateToPay(util.sqlDateToString(rs.getDate("endDateToPay")));
	    	loanEntry.setPNNo(rs.getString("PNNo"));
	    	loanEntry.setPNDate(rs.getString("PNDate"));
	    	loanEntry.setPeriodFrom(rs.getString("periodFrom"));
	    	loanEntry.setPeriodTo(rs.getString("periodTo"));
	    	loanEntry.setRemarks(rs.getString("remarks"));
	    	loanEntry.setDeductionFlagActive(rs.getString("deductionFlagActive"));
	    	loanEntry.setPayrollCycle(rs.getString("payrollCycle"));
	    	loanEntry.setLoanAmountWithInterest(rs.getBigDecimal("loanAmountWithInterest"));
	    	list.add(loanEntry);
		}
	    sql = null;
	    ps.close();
	    rs.close();	 
	    return list;

	}
    
    public  void add(LoanEntry loanEntry) throws SQLException, Exception {
  		String sql = "INSERT INTO loanEntry (empId,dateFile,loanTypeId,referenceNo,loanAmount,monthlyAmortization,startDateToPay,endDateToPay,PNNo,PNDate,periodFrom,periodTo,remarks,deductionFlagActive,payrollCycle,loanAmountWithInterest) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1;
  		
        ps.setInt(index++, loanEntry.getEmpId());
        ps.setDate(index++, util.convertToSqlDate(loanEntry.getDateFile()));
        ps.setInt(index++, loanEntry.getLoanTypeId());
        ps.setString(index++, loanEntry.getReferenceNo());
        ps.setBigDecimal(index++, loanEntry.getLoanAmount());
        ps.setBigDecimal(index++, loanEntry.getMonthlyAmortization());
        ps.setDate(index++, util.convertToSqlDate(loanEntry.getStartDateToPay()));
        ps.setDate(index++, util.convertToSqlDate(loanEntry.getEndDateToPay()));
        ps.setString(index++, loanEntry.getPNNo());
        ps.setString(index++, loanEntry.getPNDate());
        ps.setString(index++, "NA");
        ps.setString(index++, "NA");
        ps.setString(index++, loanEntry.getRemarks());
        ps.setString(index++, loanEntry.getDeductionFlagActive());
        ps.setString(index++, loanEntry.getPayrollCycle());
        ps.setBigDecimal(index++, loanEntry.getLoanAmountWithInterest());

		ps.executeUpdate();
		
		conn.commit();
		 
		ps.close();      

  	}
    
    public  void update(LoanEntry loanEntry) throws SQLException, Exception {
    	
  		String sql = "UPDATE loanEntry set dateFile = ?,loanTypeId = ?,referenceNo = ?,loanAmount = ?,monthlyAmortization = ?,startDateToPay = ?,endDateToPay = ?,PNNo = ?,PNDate = ?,periodFrom = ?,periodTo = ?, remarks = ?, deductionFlagActive = ?, payrollCycle = ?, loanAmountWithInterest = ? where loanEntryId = ?";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1;
  		ps.setDate(index++, util.convertToSqlDate(loanEntry.getDateFile()));
        ps.setInt(index++, loanEntry.getLoanTypeId());
        ps.setString(index++, loanEntry.getReferenceNo());
        ps.setBigDecimal(index++, loanEntry.getLoanAmount());
        ps.setBigDecimal(index++, loanEntry.getMonthlyAmortization());
        ps.setDate(index++, util.convertToSqlDate(loanEntry.getStartDateToPay()));
        ps.setDate(index++, util.convertToSqlDate(loanEntry.getEndDateToPay()));
        ps.setString(index++, loanEntry.getPNNo());
        ps.setString(index++, loanEntry.getPNDate());
        ps.setString(index++, "NA");
        ps.setString(index++, "NA");
        ps.setString(index++, loanEntry.getRemarks());
        ps.setString(index++, loanEntry.getDeductionFlagActive());
        ps.setString(index++, loanEntry.getPayrollCycle());
        ps.setBigDecimal(index++, loanEntry.getLoanAmountWithInterest());
  		ps.setInt(index++, loanEntry.getLoanEntryId());
  		
  		ps.executeUpdate();
        conn.commit();
        ps.close();

  	}
    
    public BigDecimal getLoanTotal(int empId, String payrollCyle, int payrollPeriodId) throws SQLException, Exception {
    	BigDecimal totalPaidLoan = BigDecimal.ZERO;
    	BigDecimal monthlyAmortization = BigDecimal.ZERO;
    	BigDecimal loanAmountWithInterest = BigDecimal.ZERO;
    	int loanEntryId = 0;
    	
    	deleteEmpPayLoan(empId, payrollPeriodId);
    	
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT le.monthlyAmortization, le.loanEntryId, le.loanAmountWithInterest, sum(lp.paidAmount) as paidAmount FROM loanEntry le, loanPayments lp ");
    	sql.append(" WHERE lp.loanEntryId = le.loanEntryId ");
    	if("1".equals(payrollCyle)){
    		sql.append(" AND (le.payrollCycle = '1' OR  le.payrollCycle = 'B') ");
    	} else if("2".equals(payrollCyle)){
    		sql.append(" AND (le.payrollCycle = '2' OR  le.payrollCycle = 'B') ");
    	}
    	
    	sql.append(" AND le.empId = ");
    	sql.append(empId);
    	
    	sql.append(" GROUP BY le.monthlyAmortization, le.loanEntryId, le.loanAmountWithInterest");
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
    	
    	ResultSet rs = ps.executeQuery();
    	if(rs.next()) {
    		totalPaidLoan = rs.getBigDecimal("paidAmount");	
    		monthlyAmortization = rs.getBigDecimal("monthlyAmortization");
    		loanAmountWithInterest = rs.getBigDecimal("monthlyAmortization");
    		loanEntryId = rs.getInt("loanEntryId");
    		
    		if(loanAmountWithInterest.compareTo(totalPaidLoan) == 1){
    			BigDecimal remainingAmount = loanAmountWithInterest.subtract(totalPaidLoan);
    			
    			if(monthlyAmortization.compareTo(remainingAmount) == 1){
    				monthlyAmortization = remainingAmount;
    			}
        		
        	}
    		
    		insertEmpPayLoan(loanEntryId, monthlyAmortization, empId, payrollPeriodId);
    	}    	
    	
    	rs.close();
    	ps.close();
    	return monthlyAmortization;
    }
    
    private void deleteEmpPayLoan(int empId, int payrollPeriodId) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("DELETE FROM loanPayments WHERE empId = ");
    	sql.append(empId);
    	sql.append(" AND payrollPeriodId = ");
    	sql.append(payrollPeriodId);
		
    	PreparedStatement ps = conn.prepareStatement(sql.toString()); 
		
		ps.executeUpdate();
		
		conn.commit();
		 
		ps.close();	
    }
    
    private void insertEmpPayLoan(int loanEntryId, BigDecimal paidAmount, int empId, int payrollPeriodId) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("INSERT INTO loanPayments (loanEntryId,paidAmount,empId,payrollPeriodId,remarks,paymentDate) ");
    	sql.append("VALUES (");
    	sql.append(loanEntryId);
    	sql.append(", ");
    	sql.append(paidAmount);
    	sql.append(", ");
    	sql.append(empId);
    	sql.append(", ");
    	sql.append(payrollPeriodId);
    	sql.append(", 'Payroll Deduction', SYSDATETIME())");
    	
		
    	PreparedStatement ps = conn.prepareStatement(sql.toString()); 
		
		ps.executeUpdate();
		
		conn.commit();
		 
		ps.close();	
    }
    
 
   
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}
	
	
	

}
