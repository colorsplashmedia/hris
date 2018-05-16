package dai.hris.dao.filemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dai.hris.dao.DBConstants;
import dai.hris.model.Department;

public class DepartmentDAO {
	
Connection conn = null;
    
    public DepartmentDAO() {
        
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("DepartmentDAO :" + sqle.getMessage());
			
		} catch (Exception e) {
			System.out.println("DepartmentDAO :" + e.getMessage());
		
		}
    	
    }
    
    public Department getDepartmentById(int id) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	sql.append("SELECT * FROM department WHERE departmentId = "); 
    	sql.append(id);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    Department model = new Department();
	    
	    if (rs.next()) {        	
	    	model.setDepartmentId(rs.getInt("departmentId"));
	    	model.setDepartmentName(rs.getString("departmentName"));
	    }
	    
	    ps.close();
	    rs.close();      
	    
    	
    	return model;
    }
    
    public boolean isExist(String name) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM department WHERE departmentname = '");
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
    	
    	
    	sql.append("SELECT * FROM department WHERE departmentname = '");
    	sql.append(name);
    	sql.append("' and departmentId <> ");  
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
    	
    	
    	sql.append("SELECT * FROM employee WHERE departmentId = ");   	
    	sql.append(id);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("checkIfRecordHasBeenUsed Department: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    if (rs.next()) {  
	    	ps.close();
		    rs.close(); 
		    
	    	return true;
	    } else if(id == 1 || id == 2) {
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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM department WHERE departmentName like '%"); 
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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM department");   	
    	  	
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
    
    public  List<Department> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "departmentName ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT departmentId,  departmentName, ROW_NUMBER() OVER(ORDER BY ");		
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM department ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<Department> list = new ArrayList<Department>();
	    
	    while (rs.next()) {
	    	Department Department = new Department();
	    	 Department.setDepartmentId(rs.getInt("departmentId"));
	    	 Department.setDepartmentName(rs.getString("departmentName"));	    	 
	    	 list.add(Department);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    public List<Department> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
    	
		StringBuffer sql = new StringBuffer(); 
		
		if(jtSorting == null){
    		jtSorting = "departmentName ASC";
    	}
		
		sql.append("WITH OrderedList AS (SELECT departmentId,  departmentName, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM department WHERE departmentName like '%");
    	sql.append(filter);    	
    	sql.append("%') SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());
		
		

	    ResultSet rs = ps.executeQuery();
	    List<Department> list = new ArrayList<Department>();
	    
	    while (rs.next()) {
	    	Department Department = new Department();
	    	 Department.setDepartmentId(rs.getInt("departmentId"));
	    	 Department.setDepartmentName(rs.getString("departmentName"));	    	 
	    	 list.add(Department);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     

	}
    
//    public  ArrayList<Department> getAll() throws SQLException, Exception {
//
//		String sql = "SELECT   departmentId,  departmentName FROM Department";		
//		PreparedStatement ps = conn.prepareStatement(sql.toString());
//
//	    ResultSet rs = ps.executeQuery();
//	    ArrayList<Department> list = new ArrayList<Department>();
//	    
//	    while (rs.next()) {
//	    	 Department Department = new Department();
//	    	 Department.setDepartmentId(rs.getInt("departmentId"));
//	    	 Department.setDepartmentName(rs.getString("departmentName"));	    	 
//	    	 list.add(Department);
//
//	    }
//	    
//	    ps.close();
//	    rs.close();      
//	    return list;     
//
//	}
    
    
    public  void add(Department Department) throws SQLException, Exception {
  		String sql = "INSERT INTO Department (departmentName) VALUES (?)";
  		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, Department.getDepartmentName());
        
        ps.executeUpdate();
          
        ResultSet keyResultSet = ps.getGeneratedKeys();
         int generatedAutoIncrementId = 0;
         if (keyResultSet.next()) {
          	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
          	Department.setDepartmentId(generatedAutoIncrementId);
          	conn.commit();
          }

          ps.close();
          keyResultSet.close();

  	}
    
    public  void delete(int id) throws SQLException, Exception {
		String sql = "DELETE from Department where departmentId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        conn.commit();
        ps.close();

	}
    
    public  void update(Department Department) throws SQLException, Exception {
		
 		String sql = "UPDATE Department set departmentName = ? where departmentId = ?";
 		PreparedStatement ps  = conn.prepareStatement(sql);
 		ps.setString(1, Department.getDepartmentName()); 		
 		ps.setInt(2, Department.getDepartmentId());
 		
         
         ps.executeUpdate();
         conn.commit();
         ps.close();

 	}
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}

}
