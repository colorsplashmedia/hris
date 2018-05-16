package dai.hris.dao.filemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dai.hris.dao.DBConstants;
import dai.hris.model.EmployeeType;

public class EmployeeTypeDAO {
	
Connection conn = null;
    
    public EmployeeTypeDAO() {
        
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("EmployeeTypeDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("EmployeeTypeDAO :" + e.getMessage());
		}
    	
    }
    
    public  int getCountWithFilter(String filter) throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM employeeType WHERE employeeTypeName like '%"); 
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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM employeeType");   	
    	  	
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
    
    public  List<EmployeeType> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "employeeTypeName ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT employeeTypeId,  employeeTypeName, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employeeType ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeType> list = new ArrayList<EmployeeType>();
	    
	    while (rs.next()) {
	    	EmployeeType employeeType = new EmployeeType();
	    	 employeeType.setEmployeeTypeId(rs.getInt("employeeTypeId"));
	    	 employeeType.setEmployeeTypeName(rs.getString("employeeTypeName"));	    	 
	    	 list.add(employeeType);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    public List<EmployeeType> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
    	
		StringBuffer sql = new StringBuffer(); 
		
		if(jtSorting == null){
    		jtSorting = "employeeTypeName ASC";
    	}
		
		sql.append("WITH OrderedList AS (SELECT employeeTypeId,  employeeTypeName, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employeeType WHERE employeeTypeName like '%");
    	sql.append(filter);    	
    	sql.append("%') SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());
		
		

	    ResultSet rs = ps.executeQuery();
	    List<EmployeeType> list = new ArrayList<EmployeeType>();
	    
	    while (rs.next()) {
	    	EmployeeType employeeType = new EmployeeType();
	    	 employeeType.setEmployeeTypeId(rs.getInt("employeeTypeId"));
	    	 employeeType.setEmployeeTypeName(rs.getString("employeeTypeName"));	    	 
	    	 list.add(employeeType);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     

	}
    
//    public  ArrayList<EmployeeType> getAll() throws SQLException, Exception {
//
//		String sql = "SELECT employeeTypeId, employeeTypeName FROM employeeType";		
//		PreparedStatement ps = conn.prepareStatement(sql.toString());
//
//	    ResultSet rs = ps.executeQuery();
//	    ArrayList<EmployeeType> list = new ArrayList<EmployeeType>();
//	    
//	    while (rs.next()) {
//	    	 EmployeeType employeeType = new EmployeeType();
//	    	 employeeType.setEmployeeTypeId(rs.getInt("employeeTypeId"));
//	    	 employeeType.setEmployeeTypeName(rs.getString("employeeTypeName"));	    	 
//	    	 list.add(employeeType);
//
//	    }
//	    
//	    ps.close();
//	    rs.close();      
//	    return list;     
//
//	}
    
    
    public  void add(EmployeeType employeeType) throws SQLException, Exception {
  		String sql = "INSERT INTO employeeType (employeeTypeName) VALUES (?)";
  		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, employeeType.getEmployeeTypeName());
        
        ps.executeUpdate();
          
        ResultSet keyResultSet = ps.getGeneratedKeys();
         int generatedAutoIncrementId = 0;
         if (keyResultSet.next()) {
          	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
          	employeeType.setEmployeeTypeId(generatedAutoIncrementId);
          	conn.commit();
          }

          ps.close();
          keyResultSet.close();

  	}
    
//    
//    public  void delete(EmployeeType employeeType) throws SQLException, Exception {
//		String sql = "DELETE from employeeType where employeeTypeId = ?";
//		PreparedStatement ps  = conn.prepareStatement(sql);
//        ps.setInt(1, employeeType.getEmployeeTypeId());
//        ps.executeUpdate();
//        conn.commit();
//        ps.close();
//
//	}
    
    public  void update(EmployeeType employeeType) throws SQLException, Exception {
		
 		String sql = "UPDATE employeeType set employeeTypeName = ? where employeeTypeId = ?";
 		PreparedStatement ps  = conn.prepareStatement(sql);
 		ps.setString(1, employeeType.getEmployeeTypeName());
 		
 		ps.setInt(2, employeeType.getEmployeeTypeId());
 		
         
         ps.executeUpdate();
         conn.commit();
         ps.close();

 	}
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}

}
