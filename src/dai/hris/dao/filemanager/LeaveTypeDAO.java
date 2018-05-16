package dai.hris.dao.filemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dai.hris.dao.DBConstants;
import dai.hris.model.LeaveType;

public class LeaveTypeDAO {
	
Connection conn = null;
    
    public LeaveTypeDAO() {
        
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("LeaveTypeDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("LeaveTypeDAO :" + e.getMessage());
		}
    	
    }
    
    public boolean isExist(String name) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM leaveType WHERE leaveTypeName = '");
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
    	
    	
    	sql.append("SELECT * FROM leaveType WHERE leaveTypeName = '");
    	sql.append(name);
    	sql.append("' and leaveTypeId <> ");  
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
    	
    	
    	sql.append("SELECT * FROM leave WHERE leaveTypeId = ");   	
    	sql.append(id);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("checkIfRecordHasBeenUsed LeaveType: " + sql.toString());

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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM leaveType WHERE leaveTypeName like '%"); 
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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM leaveType");   	
    	  	
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
    
    public  List<LeaveType> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "leaveTypeName ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT leaveTypeId,  leaveTypeName, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM leaveType ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<LeaveType> list = new ArrayList<LeaveType>();
	    
	    while (rs.next()) {
	    	LeaveType leaveType = new LeaveType();
	    	 leaveType.setLeaveTypeId(rs.getInt("leaveTypeId"));
	    	 leaveType.setLeaveTypeName(rs.getString("leaveTypeName"));	    	 
	    	 list.add(leaveType);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    public List<LeaveType> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
    	
		StringBuffer sql = new StringBuffer(); 
		
		if(jtSorting == null){
    		jtSorting = "leaveTypeName ASC";
    	}
		
		sql.append("WITH OrderedList AS (SELECT leaveTypeId,  leaveTypeName, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM leaveType WHERE leaveTypeName like '%");
    	sql.append(filter);    	
    	sql.append("%') SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());
		
		

	    ResultSet rs = ps.executeQuery();
	    List<LeaveType> list = new ArrayList<LeaveType>();
	    
	    while (rs.next()) {
	    	LeaveType leaveType = new LeaveType();
	    	 leaveType.setLeaveTypeId(rs.getInt("leaveTypeId"));
	    	 leaveType.setLeaveTypeName(rs.getString("leaveTypeName"));	    	 
	    	 list.add(leaveType);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     

	}
    
//    public  ArrayList<LeaveType> getAll() throws SQLException, Exception {
//
//		String sql = "SELECT   leaveTypeId,  leaveTypeName FROM leaveType";		
//		PreparedStatement ps = conn.prepareStatement(sql.toString());
//
//	    ResultSet rs = ps.executeQuery();
//	    ArrayList<LeaveType> list = new ArrayList<LeaveType>();
//	    
//	    while (rs.next()) {
//	    	 LeaveType leaveType = new LeaveType();
//	    	 leaveType.setLeaveTypeId(rs.getInt("leaveTypeId"));
//	    	 leaveType.setLeaveTypeName(rs.getString("leaveTypeName"));	    	 
//	    	 list.add(leaveType);
//
//	    }
//	    
//	    ps.close();
//	    rs.close();      
//	    return list;     
//
//	}
    
    
    public LeaveType getLeaveTypeByLeaveTypeId(int leaveTypeId) throws SQLException, Exception {

		String sql = "SELECT   leaveTypeId,  leaveTypeName FROM leaveType where leaveTypeId=?";		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, leaveTypeId);

	    ResultSet rs = ps.executeQuery();
	    LeaveType leaveType = null;
	    
	    while (rs.next()) {
	    	 leaveType = new LeaveType();
	    	 leaveType.setLeaveTypeId(rs.getInt("leaveTypeId"));
	    	 leaveType.setLeaveTypeName(rs.getString("leaveTypeName"));	    	 
	    	 

	    }
	    
	    ps.close();
	    rs.close();
	    
	    return leaveType;	    

	}
    
    
    public  void add(LeaveType leaveType) throws SQLException, Exception {
  		String sql = "INSERT INTO leaveType (leaveTypeName) VALUES (?)";
  		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, leaveType.getLeaveTypeName());
        
        ps.executeUpdate();
          
        ResultSet keyResultSet = ps.getGeneratedKeys();
         int generatedAutoIncrementId = 0;
         if (keyResultSet.next()) {
          	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
          	leaveType.setLeaveTypeId(generatedAutoIncrementId);
          	conn.commit();
          }

          ps.close();
          keyResultSet.close();

  	}
    
    public  void delete(int id) throws SQLException, Exception {
		String sql = "DELETE from leaveType where leaveTypeId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        conn.commit();
        ps.close();

	}
    
    public  void update(LeaveType leaveType) throws SQLException, Exception {
		
 		String sql = "UPDATE leaveType set leaveTypeName = ? where leaveTypeId = ?";
 		PreparedStatement ps  = conn.prepareStatement(sql);
 		ps.setString(1, leaveType.getLeaveTypeName());
 		
 		ps.setInt(2, leaveType.getLeaveTypeId());
 		
         
         ps.executeUpdate();
         conn.commit();
         ps.close();

 	}
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}

}
