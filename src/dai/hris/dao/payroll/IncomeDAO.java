package dai.hris.dao.payroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dai.hris.dao.DBConstants;
import dai.hris.model.EmployeeIncome;
import dai.hris.model.Income;

public class IncomeDAO {
	private Connection conn;

	public IncomeDAO() {
		try {
			/* MS SQL CODE */    		
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
			conn.setAutoCommit(false);
		} catch (SQLException sqle) {
			System.out.println("IncomeDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("IncomeDAO :" + e.getMessage());
		}
	}
	
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM income WHERE incomeName like '%"); 
    	sql.append(filter);  
    	sql.append("%'");  
    	
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
    
    public  int getCount() throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM income");   	
    	  	
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
    
    public  List<Income> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "incomeName ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT incomeId, incomeName, isTaxable, accountingCode, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM income ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<Income> list = new ArrayList<Income>();
	    
	    while (rs.next()) {
	    	Income model = new Income();
	    	model.setIncomeId(rs.getInt("incomeId"));
			model.setIncomeName(rs.getString("incomeName"));
			model.setIsTaxable(rs.getString("isTaxable"));
			model.setAccountingCode(rs.getString("accountingCode"));
			list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    public List<Income> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
    	
		StringBuffer sql = new StringBuffer(); 
		
		if(jtSorting == null){
    		jtSorting = "incomeName ASC";
    	}
		
		sql.append("WITH OrderedList AS (SELECT incomeId, incomeName, isTaxable, accountingCode, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM income WHERE incomeName like '%");
    	sql.append(filter);    	
    	sql.append("%') SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());
		
		

	    ResultSet rs = ps.executeQuery();
	    List<Income> list = new ArrayList<Income>();
	    
	    while (rs.next()) {
	    	Income model = new Income();
	    	model.setIncomeId(rs.getInt("incomeId"));
			model.setIncomeName(rs.getString("incomeName"));
			model.setIsTaxable(rs.getString("isTaxable"));
			model.setAccountingCode(rs.getString("accountingCode"));
			list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     

	}
	
	
	
	public Income getIncomeById(int inId) throws SQLException, Exception {
		Income model = null;
		String sql  = "SELECT * FROM income where incomeId = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, inId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			model = new Income();
			model.setIncomeId(rs.getInt("incomeId"));
			model.setIncomeName(rs.getString("incomeName"));
			model.setIsTaxable(rs.getString("isTaxable"));
			model.setAccountingCode(rs.getString("accountingCode"));
		}
		rs.close();
		return model;
	}
	
	public List<Income> getAll() throws SQLException, Exception {
		List<Income> resultList = new ArrayList<Income>();
		String sql  = "SELECT * FROM income";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Income model = new Income();
			model.setIncomeId(rs.getInt("incomeId"));
			model.setIncomeName(rs.getString("incomeName"));
			model.setIsTaxable(rs.getString("isTaxable"));
			model.setAccountingCode(rs.getString("accountingCode"));			
			resultList.add(model);
		}
		rs.close();
		return resultList;
	}
	
	public void saveEmpIncome(EmployeeIncome model) throws SQLException, Exception {
    	
    	String sql = "INSERT INTO empIncomeNew (incomeId,amount,empId,payrollPeriodId,incomeType,payrollCycle,createdBy,creationDate) VALUES (?,?,?,?,?,?,?,SYSDATETIME())";		
    	PreparedStatement ps = conn.prepareStatement(sql);
    	
		int index = 1;
		ps.setInt(index++, model.getIncomeId());
		ps.setBigDecimal(index++, model.getAmount());
		ps.setInt(index++, model.getEmpId());
		ps.setInt(index++, model.getPayrollPeriodId());
		ps.setString(index++, model.getIncomeType());		
		ps.setString(index++, model.getPayrollCycle());
		ps.setInt(index++, model.getCreatedBy());
		
		
    	ps.executeUpdate();
		
    	conn.commit(); 
    	
    	sql = null;
		ps.close();
		
    }

	public boolean isExist(String name) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM income WHERE incomeName = '");
    	sql.append(name);
    	sql.append("'");   	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    if (rs.next()) {    	
	    	return true;
	    }
	    
	    ps.close();
	    rs.close();      
	    
    	
    	return false;
    }
    
    public boolean isExistUpdate(String name, int id) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM income WHERE incomeName = '");
    	sql.append(name);
    	sql.append("' and incomeId <> ");  
    	sql.append(id);
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    if (rs.next()) {    	
	    	return true;
	    }
	    
	    ps.close();
	    rs.close();      
	    
    	
    	return false;
    }
    
    public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM empIncomeNew WHERE incomeId = ");   	
    	sql.append(id);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("checkIfRecordHasBeenUsed Income: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    if (rs.next()) {  
	    	ps.close();
		    rs.close(); 
		    
	    	return true;
	    }
	    
	    ps.close();
	    rs.close();   
		
		return false;
	}    
    
    public void add(Income model) throws SQLException, Exception {
    	
  		String sql = "INSERT INTO income (incomeName, isTaxable, accountingCode, createdBy, creationDate) VALUES (?, ?, ?, ?, SYSDATETIME())";
  		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
  		ps.setString(1, model.getIncomeName());
 		ps.setString(2, model.getIsTaxable());
        ps.setString(3, model.getAccountingCode());
        ps.setInt(4, model.getCreatedBy());
        
        ps.executeUpdate();
        
        conn.commit(); 
    	
    	sql = null;
		ps.close();

  	}
    
    public  void delete(int id) throws SQLException, Exception {
		String sql = "DELETE from income where incomeId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        conn.commit();
        ps.close();

	}
    
    public  void update(Income model) throws SQLException, Exception {
		
 		String sql = "UPDATE income set incomeName = ?, isTaxable = ?, accountingCode = ?, updatedBy = ?, updateDate = SYSDATETIME() WHERE incomeId = ?";
 		PreparedStatement ps  = conn.prepareStatement(sql);
 		ps.setString(1, model.getIncomeName());
 		ps.setString(2, model.getIsTaxable());
        ps.setString(3, model.getAccountingCode());
        ps.setInt(4, model.getUpdatedBy());
 		ps.setInt(5, model.getIncomeId());
 		
         
         ps.executeUpdate();
         conn.commit();
         ps.close();

 	}
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}
}
