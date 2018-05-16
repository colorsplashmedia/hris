package dai.hris.dao.payroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dai.hris.dao.DBConstants;
import dai.hris.model.Deduction;
import dai.hris.model.EmployeeDeduction;

public class DeductionDAO {
	private Connection conn;

	public DeductionDAO() {
		try {
			/* MS SQL CODE */    		
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
			conn.setAutoCommit(false);
		} catch (SQLException sqle) {
			System.out.println("DeductionDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("DeductionDAO :" + e.getMessage());
		}
	}
	
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM deduction WHERE deductionName like '%"); 
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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM deduction");   	
    	  	
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
    
    public  List<Deduction> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "deductionName ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT deductionId, deductionName, institution, accountingCode, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM deduction ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<Deduction> list = new ArrayList<Deduction>();
	    
	    while (rs.next()) {
	    	Deduction model = new Deduction();
	    	model.setDeductionId(rs.getInt("deductionId"));
			model.setDeductionName(rs.getString("deductionName"));
			model.setAccountingCode(rs.getString("accountingCode"));
			model.setInstitution(rs.getString("institution"));
			list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    public List<Deduction> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
    	
		StringBuffer sql = new StringBuffer(); 
		
		if(jtSorting == null){
    		jtSorting = "deductionName ASC";
    	}
		
		sql.append("WITH OrderedList AS (SELECT deductionId, deductionName, institution, accountingCode, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM deduction WHERE deductionName like '%");
    	sql.append(filter);    	
    	sql.append("%') SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());
		
		

	    ResultSet rs = ps.executeQuery();
	    List<Deduction> list = new ArrayList<Deduction>();
	    
	    while (rs.next()) {
	    	Deduction model = new Deduction();
	    	model.setDeductionId(rs.getInt("deductionId"));
			model.setDeductionName(rs.getString("deductionName"));
			model.setAccountingCode(rs.getString("accountingCode"));
			model.setInstitution(rs.getString("institution"));
			list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     

	}
    
    public void saveEmpDeduction(EmployeeDeduction model) throws SQLException, Exception {
    	
    	String sql = "INSERT INTO empDeductionNew (deductionId,amount,empId,payrollPeriodId,deductionType,payrollCycle,createdBy,creationDate) "
				+ " VALUES (?,?,?,?,?,?,?,SYSDATETIME())";		
    	PreparedStatement ps = conn.prepareStatement(sql);
    	
		int index = 1;
		ps.setInt(index++, model.getDeductionId());
		ps.setBigDecimal(index++, model.getAmount());
		ps.setInt(index++, model.getEmpId());
		ps.setInt(index++, model.getPayrollPeriodId());
		ps.setString(index++, model.getDeductionType());
		ps.setString(index++, model.getPayrollCycle());
		ps.setInt(index++, model.getCreatedBy());
		
		
    	ps.executeUpdate();
		
    	conn.commit(); 
    	
    	sql = null;
		ps.close();
		
    }
    
	public void savePayrollExclusion(int empId, int payrollPeriodId, int createdBy) throws SQLException, Exception {
    	
    	String sql = "INSERT INTO empPayrollExclusion (empId,payrollPeriodId,createdBy,creationDate) "
				+ " VALUES (?,?,?,SYSDATETIME())";		
    	PreparedStatement ps = conn.prepareStatement(sql);
    	
		int index = 1;
		ps.setInt(index++, empId);
		ps.setInt(index++, payrollPeriodId);
		ps.setInt(index++, createdBy);
		
		
    	ps.executeUpdate();
		
    	conn.commit(); 
    	
    	sql = null;
		ps.close();
		
    }
	
	public Deduction getDeductionById(int ddId) throws SQLException {
		Deduction model = null;
		String sql = "select * from Deduction where deductionId=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, ddId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			model = new Deduction();
		    model.setDeductionId(rs.getInt("deductionId"));
			model.setDeductionName(rs.getString("deductionName"));
			model.setAccountingCode(rs.getString("accountingCode"));
			model.setInstitution(rs.getString("institution"));
		}
		rs.close();
		return model;
	}
	
	public List<Deduction> getAll() throws SQLException {
		List<Deduction> resultList = new ArrayList<Deduction>();
		String sql = "SELECT * FROM deduction";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Deduction model = new Deduction();
			model.setDeductionId(rs.getInt("deductionId"));
			model.setDeductionName(rs.getString("deductionName"));
			model.setAccountingCode(rs.getString("accountingCode"));
			model.setInstitution(rs.getString("institution"));
			
			resultList.add(model);
		}
		rs.close();
		return resultList;
	}
	
	
	
	///////
	
	public boolean isExist(String name) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM deduction WHERE deductionName = '");
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
    	
    	
    	sql.append("SELECT * FROM deduction WHERE deductionName = '");
    	sql.append(name);
    	sql.append("' and deductionId <> ");  
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
    	
    	
    	sql.append("SELECT * FROM empDeductionNew WHERE deductionId = ");   	
    	sql.append(id);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("checkIfRecordHasBeenUsed Deduction: " + sql.toString());

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
    
        
    
    
    
    
    public void add(Deduction model) throws SQLException, Exception {
    	
  		String sql = "INSERT INTO deduction (deductionName, institution, accountingCode, createdBy, creationDate) VALUES (?, ?, ?, ?, SYSDATETIME())";
  		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, model.getDeductionName());
        ps.setString(2, model.getInstitution());
        ps.setString(3, model.getAccountingCode());
        ps.setInt(4, model.getCreatedBy());
        
        ps.executeUpdate();
        
        conn.commit(); 
    	
    	sql = null;
		ps.close();

  	}
    
    public  void delete(int id) throws SQLException, Exception {
		String sql = "DELETE from deduction where deductionId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        conn.commit();
        ps.close();

	}
    
    public  void update(Deduction model) throws SQLException, Exception {
		
 		String sql = "UPDATE deduction set deductionName = ?, institution = ?, accountingCode = ?, updatedBy = ?, updateDate = SYSDATETIME() WHERE deductionId = ?";
 		PreparedStatement ps  = conn.prepareStatement(sql);
 		ps.setString(1, model.getDeductionName());
 		ps.setString(2, model.getInstitution());
        ps.setString(3, model.getAccountingCode());
        ps.setInt(4, model.getCreatedBy());
 		ps.setInt(5, model.getDeductionId());
 		
         
         ps.executeUpdate();
         conn.commit();
         ps.close();

 	}
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}
}
