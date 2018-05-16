package dai.hris.dao.filemanager;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dai.hris.dao.DBConstants;
import dai.hris.model.EmployeeDeduction;
import dai.hris.model.EmployeeIncome;

/**
 * get for income and deductions tied to employee!
 * @author playerone
 *
 */
public class EmployeeIncomeDeductionDAO {
	Connection conn = null;
	
	public EmployeeIncomeDeductionDAO() {
		
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("EmployeeIncomeDeductionDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("EmployeeIncomeDeductionDAO :" + e.getMessage());
		}
	}
	
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		return false;
	}
	
	


	public List<EmployeeDeduction> getEmployeeDeductionByEmpId(int empId) throws SQLException, Exception {			    		
		
		String sql = "SELECT ed.deductionId, d.deductionName, ed.amount, ed.empId, ed.payrollPeriodId, ed.payrollCycle, ed.deductionType, d.accountingCode, ed.active "
				+ " FROM empDeductionNew ed, deduction d WHERE ed.deductionId = d.deductionId AND ed.empId = ?";
		List<EmployeeDeduction> list = new ArrayList<EmployeeDeduction>();
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, empId);		
	    
		ResultSet rs = ps.executeQuery();	    
	    
	    while (rs.next()) {
	    	EmployeeDeduction model = new EmployeeDeduction();
	    	model.setActive(rs.getString("active"));
	    	model.setDeductionId(rs.getInt("deductionId"));
	    	model.setDeductionName(rs.getString("deductionName"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAmount(rs.getBigDecimal("amount"));
	    	model.setPayrollPeriodId(rs.getInt("payrollPeriodId"));
	    	model.setPayrollCycle(rs.getString("payrollCycle"));
	    	model.setDeductionType(rs.getString("deductionType"));
	    	model.setAccountingCode(rs.getString("accountingCode"));
	    	model.setActive(rs.getString("active"));
	    	
	    	
//	    	employeeDeduction.setRemarks(rs.getString("remarks"));	    	
//	    	employeeDeduction.setEmpDeductionId(rs.getInt("empDeductionId"));
	    	
	    	list.add(model);
	    }	    
	    ps.close();
	    rs.close();      
	    return list;	
	    
	}
	
	
	public List<EmployeeIncome> getEmployeeIncomeByEmpId(int empId) throws SQLException, Exception {			    		
		
//		String sql = "SELECT * FROM empIncome WHERE empId = ?";
		String sql = "SELECT ed.incomeId, i.incomeName, ed.amount, ed.empId, i.isTaxable, ed.payrollPeriodId, ed.payrollCycle, ed.incomeType, i.accountingCode, "
				+ "ed.active  FROM empIncomeNew ed, income i WHERE ed.incomeId = i.incomeId AND ed.empId = ?";
		List<EmployeeIncome> list = new ArrayList<EmployeeIncome>();
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, empId);		
	    ResultSet rs = ps.executeQuery();	    
	    while (rs.next()) {
	    	EmployeeIncome model = new EmployeeIncome();
	    	
	    	model.setActive(rs.getString("active"));
	    	model.setIncomeId(rs.getInt("incomeId"));
	    	model.setIncomeName(rs.getString("incomeName"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAmount(rs.getBigDecimal("amount"));
	    	model.setPayrollPeriodId(rs.getInt("payrollPeriodId"));
	    	model.setPayrollCycle(rs.getString("payrollCycle"));
	    	model.setIncomeType(rs.getString("incomeType"));
	    	model.setAccountingCode(rs.getString("accountingCode"));
	    	model.setActive(rs.getString("active"));
	    	model.setIsTaxable(rs.getString("isTaxable"));
	    	
	    	
	    	list.add(model);
	    }	    
	    ps.close();
	    rs.close();      
	    return list;		
	}
	
	
    

//	public int addIncome(EmployeeIncome employeeIncome) throws SQLException, Exception {
//		String sql = "INSERT INTO empIncome (empId, incomeId, active, remarks) VALUES(?,?,?,?)";
//		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
//	    
//		int index = 1;		
//		ps.setInt(index++, employeeIncome.getEmpId());
//	    ps.setInt(index++, employeeIncome.getIncomeId());
//	    ps.setInt(index++, employeeIncome.getActive());
//	    ps.setString(index++, employeeIncome.getRemarks());
//   
//	    int count = ps.executeUpdate();
//	    int status = 0;  
//	    ResultSet keyResultSet = ps.getGeneratedKeys();
//	    int generatedAutoIncrementId = 0;
//	    if (keyResultSet.next()) {
//	      	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
//	      	employeeIncome.setEmpIncomeId(generatedAutoIncrementId);
//	      	conn.commit();
//	    }
//		
//	    ps.close();
//	    keyResultSet.close();
//		if (count == 1) {
//			status = generatedAutoIncrementId;
//		}		
//	    return status;
//	}
	
//	public int addDeduction(EmployeeDeduction employeeDeduction) throws SQLException, Exception {
//		String sql = "INSERT INTO empDeduction (empId, deductionId, active, remarks) VALUES(?,?,?,?)";
//		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
//	    
//		int index = 1;		
//		ps.setInt(index++, employeeDeduction.getEmpId());
//	    ps.setInt(index++, employeeDeduction.getDeductionId());
//	    ps.setInt(index++, employeeDeduction.getActive());
//	    ps.setString(index++, employeeDeduction.getRemarks());
//   
//	    int count = ps.executeUpdate();
//	    int status = 0;  
//	    ResultSet keyResultSet = ps.getGeneratedKeys();
//	    int generatedAutoIncrementId = 0;
//	    if (keyResultSet.next()) {
//	      	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
//	      	employeeDeduction.setEmpDeductionId(generatedAutoIncrementId);
//	      	conn.commit();
//	    }
//		
//	    ps.close();
//	    keyResultSet.close();
//		if (count == 1) {
//			status = generatedAutoIncrementId;
//		}		
//	    return status;
//	}

	    
	public void deleteIncome(int id)  throws SQLException, Exception {
		String sql = "DELETE FROM empIncome WHERE empIncomeId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        conn.commit();
        ps.close();
	}
	
	public void deleteDeduction(int id)  throws SQLException, Exception {
		String sql = "DELETE FROM empDeduction WHERE empDeductionId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        conn.commit();
        ps.close();
	}
	
	
//    public int updateIncome(EmployeeIncome employeeIncome) throws SQLException, Exception {		
//		String sql = "UPDATE empIncome SET empId=?, incomeId=?, active=?, remarks=? WHERE empIncomeId=?";
//		PreparedStatement ps  = conn.prepareStatement(sql);
//	    
//		int index = 1;		
//		ps.setInt(index++, employeeIncome.getEmpId());
//	    ps.setInt(index++, employeeIncome.getIncomeId());
//	    ps.setInt(index++, employeeIncome.getActive());
//	    ps.setString(index++, employeeIncome.getRemarks());
//	    ps.setInt(index++, employeeIncome.getEmpIncomeId());
//	    
//		int count = ps.executeUpdate();
//		conn.commit();
//		ps.close();
//		return count;
// 	}
    
//    public int updateDeduction(EmployeeDeduction employeeDeduction) throws SQLException, Exception {		
//		String sql = "UPDATE empDeduction SET empId=?, deductionId=?, active=?, remarks=? WHERE empDeductionId=?";
//		PreparedStatement ps  = conn.prepareStatement(sql);
//	    
//		int index = 1;		
//		ps.setInt(index++, employeeDeduction.getEmpId());
//	    ps.setInt(index++, employeeDeduction.getDeductionId());
//	    ps.setInt(index++, employeeDeduction.getActive());
//	    ps.setString(index++, employeeDeduction.getRemarks());
//	    ps.setInt(index++, employeeDeduction.getEmpDeductionId());
//	    
//		int count = ps.executeUpdate();
//		conn.commit();
//		ps.close();
//		return count;
// 	}
    
//    public BigDecimal getTaxableIncomeTotal(int empId, String isForSecondHalf) throws SQLException {
    public BigDecimal getTaxableIncomeTotal(int empId, String payrollCyle, int payrollPeriodId) throws SQLException, Exception {
    	BigDecimal total = BigDecimal.ZERO;
    	
    	deleteEmpPayIncome(empId, payrollPeriodId, "Y");
    	
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT * FROM empIncomeNew empin, income inc ");
    	sql.append(" WHERE empin.incomeId = inc.incomeId ");
    	sql.append(" AND inc.isTaxable = 'Y' ");
    	if("1".equals(payrollCyle)){
    		sql.append(" AND (empin.payrollCycle = '1' OR  empin.payrollCycle = 'B') ");
    	} else if("2".equals(payrollCyle)){
    		sql.append(" AND (empin.payrollCycle = '2' OR  empin.payrollCycle = 'B') ");
    	}
    	
    	sql.append(" AND empin.empId = ");
    	sql.append(empId);
    			
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
    	
    	//ps.setString(2, isForSecondHalf);
    	ResultSet rs = ps.executeQuery();
    	while(rs.next()) {
    		total = total.add(rs.getBigDecimal("amount"));
    		
    		insertEmpPayIncome(rs.getInt("incomeId"), rs.getBigDecimal("amount"), empId, payrollPeriodId, "Y");
    	}
    	rs.close();
    	ps.close();
    	return total;
    }
    
    
    
    public BigDecimal getNonTaxableIncomeTotal(int empId, String payrollCyle, int payrollPeriodId) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	deleteEmpPayIncome(empId, payrollPeriodId, "N");
    	
    	sql.append("SELECT empin.amount, empin.incomeId FROM empIncomeNew empin, income inc ");
    	sql.append(" WHERE empin.incomeId = inc.incomeId ");
    	sql.append(" AND inc.isTaxable = 'N' ");
    	if("1".equals(payrollCyle)){
    		sql.append(" AND (empin.payrollCycle = '1' OR  empin.payrollCycle = 'B') ");
    	} else if("2".equals(payrollCyle)){
    		sql.append(" AND (empin.payrollCycle = '2' OR  empin.payrollCycle = 'B') ");
    	}
    	
    	sql.append(" AND empin.empId = ");
    	sql.append(empId);
    	
    	BigDecimal total = BigDecimal.ZERO;
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
    	
    	ResultSet rs = ps.executeQuery();
    	while(rs.next()) {
    		total = total.add(rs.getBigDecimal("amount"));
    		
    		insertEmpPayIncome(rs.getInt("incomeId"), rs.getBigDecimal("amount"), empId, payrollPeriodId, "N");
    	}
    	rs.close();
    	ps.close();
    	return total;
    }
    
    public BigDecimal getDeductionTotal(int empId, String payrollCyle, int payrollPeriodId) throws SQLException, Exception {
    	BigDecimal total = BigDecimal.ZERO;
    	
    	deleteEmpPayDeduction(empId, payrollPeriodId);
    	
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT empdd.amount, empdd.deductionId FROM empDeductionNew empdd, deduction dd ");
    	sql.append(" WHERE empdd.deductionId = dd.deductionId ");
    	if("1".equals(payrollCyle)){
    		sql.append(" AND (empdd.payrollCycle = '1' OR  empdd.payrollCycle = 'B') ");
    	} else if("2".equals(payrollCyle)){
    		sql.append(" AND (empdd.payrollCycle = '2' OR  empdd.payrollCycle = 'B') ");
    	}
    	
    	sql.append(" AND empdd.empId = ");
    	sql.append(empId);
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
    	
    	ResultSet rs = ps.executeQuery();
    	while(rs.next()) {
    		total = total.add(rs.getBigDecimal("amount"));
    		
    		insertEmpPayDeduction(rs.getInt("deductionId"), rs.getBigDecimal("amount"), empId, payrollPeriodId);
    	}
    	rs.close();
    	ps.close();
    	return total;
    }
    
    private void deleteEmpPayIncome(int empId, int payrollPeriodId, String taxable) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("DELETE FROM empPayIncome WHERE empId = ");
    	sql.append(empId);
    	sql.append(" AND payrollPeriodId = ");
    	sql.append(payrollPeriodId);    	
    	sql.append(" AND taxable = '");
    	sql.append(taxable);    	
    	sql.append("'");
		
    	PreparedStatement ps = conn.prepareStatement(sql.toString()); 
		
		ps.executeUpdate();
		
		conn.commit();
		 
		ps.close();	
    }
    
    private void insertEmpPayIncome(int incomeId, BigDecimal amount, int empId, int payrollPeriodId, String taxable) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("INSERT INTO empPayIncome (incomeId,amount,empId,payrollPeriodId,taxable,creationDate) ");
    	sql.append("VALUES (");
    	sql.append(incomeId);
    	sql.append(", ");
    	sql.append(amount);
    	sql.append(", ");
    	sql.append(empId);
    	sql.append(", ");
    	sql.append(payrollPeriodId);
    	sql.append(", '");
    	sql.append(taxable);
    	sql.append("', SYSDATETIME())");
    	
		
    	PreparedStatement ps = conn.prepareStatement(sql.toString()); 
		
		ps.executeUpdate();
		
		conn.commit();
		 
		ps.close();	
    }
    
    private void deleteEmpPayDeduction(int empId, int payrollPeriodId) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("DELETE FROM empPayDeduction WHERE empId = ");
    	sql.append(empId);
    	sql.append(" AND payrollPeriodId = ");
    	sql.append(payrollPeriodId);
		
    	PreparedStatement ps = conn.prepareStatement(sql.toString()); 
		
		ps.executeUpdate();
		
		conn.commit();
		 
		ps.close();	
    }
    
    private void insertEmpPayDeduction(int deductionId, BigDecimal amount, int empId, int payrollPeriodId) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("INSERT INTO empPayDeduction (deductionId,amount,empId,payrollPeriodId,creationDate) ");
    	sql.append("VALUES (");
    	sql.append(deductionId);
    	sql.append(", ");
    	sql.append(amount);
    	sql.append(", ");
    	sql.append(empId);
    	sql.append(", ");
    	sql.append(payrollPeriodId);
    	sql.append(", SYSDATETIME())");
    	
		
    	PreparedStatement ps = conn.prepareStatement(sql.toString()); 
		
		ps.executeUpdate();
		
		conn.commit();
		 
		ps.close();	
    }
    
    
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}
}
