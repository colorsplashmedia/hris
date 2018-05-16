package dai.hris.dao.filemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dai.hris.dao.DBConstants;
import dai.hris.model.LoanType;

public class LoanTypeDAO {
	
Connection conn = null;
    
    public LoanTypeDAO() {
        
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("LoanTypeDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("LoanTypeDAO :" + e.getMessage());
		}
    	
    }
    
    public LoanType getLoanType(int loanTypeId) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM loanType WHERE loanTypeId = ");
    	sql.append(loanTypeId);
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getLoanType SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
	    LoanType model = null;
	    
	    if (rs.next()) {    	
	    	model = new LoanType();
	    	
	    	model.setAccountingCode(rs.getString("accountingCode"));
	    	model.setInstitution(rs.getString("institution"));
	    	model.setLoanCode(rs.getString("loanCode"));
	    	model.setLoanTypeId(loanTypeId);
	    	model.setLoanTypeName(rs.getString("loanTypeName"));
	    }
	    
	    ps.close();
	    rs.close();      
	    
    	
    	return model;
    }
    
    public boolean isExist(String name) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM loanType WHERE loanTypeName = '");
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
    	
    	
    	sql.append("SELECT * FROM loanType WHERE loanTypeName = '");
    	sql.append(name);
    	sql.append("' and loanTypeId <> ");  
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
    	
    	
    	sql.append("SELECT * FROM loanEntry WHERE loanTypeId = ");   	
    	sql.append(id);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("checkIfRecordHasBeenUsed LoanType: " + sql.toString());

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
    
    public  int getCountWithFilter(String filter) throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM loanType WHERE loanTypeName like '%"); 
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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM loanType");   	
    	  	
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
    
    public  List<LoanType> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "loanTypeName ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT loanTypeId,  loanTypeName, loanCode, institution, accountingCode, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM loanType ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<LoanType> list = new ArrayList<LoanType>();
	    
	    while (rs.next()) {
	    	LoanType model = new LoanType();
	    	model.setLoanTypeId(rs.getInt("loanTypeId"));
	    	model.setLoanTypeName(rs.getString("loanTypeName"));
	    	model.setLoanCode(rs.getString("loanCode"));
	    	model.setInstitution(rs.getString("institution"));
	    	model.setAccountingCode(rs.getString("accountingCode"));
	    	 
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    public List<LoanType> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
    	
		StringBuffer sql = new StringBuffer(); 
		
		if(jtSorting == null){
    		jtSorting = "loanTypeName ASC";
    	}
		
		sql.append("WITH OrderedList AS (SELECT loanTypeId,  loanTypeName, loanCode, institution, accountingCode, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM loanType WHERE loanTypeName like '%");
    	sql.append(filter);    	
    	sql.append("%') SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());
		
		

	    ResultSet rs = ps.executeQuery();
	    List<LoanType> list = new ArrayList<LoanType>();
	    
	    while (rs.next()) {
	    	LoanType model = new LoanType();
	    	model.setLoanTypeId(rs.getInt("loanTypeId"));
	    	model.setLoanTypeName(rs.getString("loanTypeName"));	    	
	    	model.setLoanCode(rs.getString("loanCode"));
		    model.setInstitution(rs.getString("institution"));
		    model.setAccountingCode(rs.getString("accountingCode"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     

	}
    
//    public  ArrayList<LoanType> getAll() throws SQLException, Exception {
//
//		String sql = "SELECT   loanTypeId,  loanTypeName FROM loanType";		
//		PreparedStatement ps = conn.prepareStatement(sql.toString());
//
//	    ResultSet rs = ps.executeQuery();
//	    ArrayList<LoanType> list = new ArrayList<LoanType>();
//	    
//	    while (rs.next()) {
//	    	 LoanType loanType = new LoanType();
//	    	 loanType.setLoanTypeId(rs.getInt("loanTypeId"));
//	    	 loanType.setLoanTypeName(rs.getString("loanTypeName"));	    	 
//	    	 list.add(loanType);
//
//	    }
//	    
//	    ps.close();
//	    rs.close();      
//	    return list;     
//
//	}
//    
    
    public void add(LoanType model) throws SQLException, Exception {
    	
  		String sql = "INSERT INTO loanType (loanTypeName, loanCode, institution, accountingCode, createdBy, creationDate) VALUES (?, ?, ?, ?, ?, SYSDATETIME())";
  		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, model.getLoanTypeName());
        ps.setString(2, model.getLoanCode());
        ps.setString(3, model.getInstitution());
        ps.setString(4, model.getAccountingCode());
        ps.setInt(5, model.getCreatedBy());
        
        ps.executeUpdate();
        
        conn.commit(); 
    	
    	sql = null;
		ps.close();

  	}
    
    public  void delete(int id) throws SQLException, Exception {
		String sql = "DELETE from loanType where loanTypeId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        conn.commit();
        ps.close();

	}
    
    public  void update(LoanType model) throws SQLException, Exception {
		
 		String sql = "UPDATE loanType set loanTypeName = ?, loanCode = ?, institution = ?, accountingCode = ?, updatedBy = ?, updateDate = SYSDATETIME() WHERE loanTypeId = ?";
 		PreparedStatement ps  = conn.prepareStatement(sql);
 		ps.setString(1, model.getLoanTypeName());
 		ps.setString(2, model.getLoanCode());
        ps.setString(3, model.getInstitution());
        ps.setString(4, model.getAccountingCode());
        ps.setInt(5, model.getCreatedBy());
 		ps.setInt(6, model.getLoanTypeId());
 		
         
         ps.executeUpdate();
         conn.commit();
         ps.close();

 	}
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}

}
