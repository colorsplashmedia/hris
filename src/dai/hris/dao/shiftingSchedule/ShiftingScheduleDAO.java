package dai.hris.dao.shiftingSchedule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dai.hris.action.filemanager.util.EmployeeUtil;
import dai.hris.dao.DBConstants;
import dai.hris.model.ShiftingSchedule;

public class ShiftingScheduleDAO {
	
	Connection conn = null;
	EmployeeUtil util = new EmployeeUtil();
	
	public ShiftingScheduleDAO() {
		try {
    		
    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("ShiftingScheduleDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("ShiftingScheduleDAO :" + e.getMessage());
		}
	}
	
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		return false;
	}
	
	public  void delete(int id) throws SQLException, Exception {
		String sql = "DELETE from shiftingSchedule where shiftingScheduleId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        conn.commit();
        ps.close();

	}
	
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM shiftingSchedule WHERE description like '%"); 
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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM shiftingSchedule");   	
    	  	
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
    
    public  List<ShiftingSchedule> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "description ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT shiftingScheduleId, shiftType, timeIn, timeOut, description, noOfHours, colorAssignment, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM shiftingSchedule ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<ShiftingSchedule> list = new ArrayList<ShiftingSchedule>();
	    
	    while (rs.next()) {
	    	ShiftingSchedule model = new ShiftingSchedule();
	    	 model.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
	    	 model.setShiftType(rs.getString("shiftType"));	    
	    	 model.setTimeIn(util.sqlTimeToString(rs.getTime("timeIn")));
	    	 model.setTimeOut(util.sqlTimeToString(rs.getTime("timeOut")));
	    	 model.setDescription(rs.getString("description"));	    
	    	 model.setNoOfHours(rs.getInt("noOfHours"));
	    	 model.setColorAssignment(rs.getString("colorAssignment"));
	    	 list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    public List<ShiftingSchedule> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
    	
		StringBuffer sql = new StringBuffer(); 
		
		if(jtSorting == null){
    		jtSorting = "description ASC";
    	}
		
		sql.append("WITH OrderedList AS (SELECT shiftingScheduleId, shiftType, timeIn, timeOut, description, noOfHours, colorAssignment, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM shiftingSchedule WHERE description like '%");
    	sql.append(filter);    	
    	sql.append("%') SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());
		
		

	    ResultSet rs = ps.executeQuery();
	    List<ShiftingSchedule> list = new ArrayList<ShiftingSchedule>();
	    
	    while (rs.next()) {
	    	ShiftingSchedule model = new ShiftingSchedule();
	    	 model.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
	    	 model.setShiftType(rs.getString("shiftType"));	    
	    	 model.setTimeIn(util.sqlTimeToString(rs.getTime("timeIn")));
	    	 model.setTimeOut(util.sqlTimeToString(rs.getTime("timeOut")));
	    	 model.setDescription(rs.getString("description"));	    
	    	 model.setNoOfHours(rs.getInt("noOfHours"));
	    	 model.setColorAssignment(rs.getString("colorAssignment"));
	    	 list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     

	}
	
    public  List<ShiftingSchedule> getAll() throws SQLException, Exception {

		String sql = "SELECT   *  FROM shiftingSchedule";		
		PreparedStatement ps = conn.prepareStatement(sql);

	    ResultSet rs = ps.executeQuery();
	    ArrayList<ShiftingSchedule> list = new ArrayList<ShiftingSchedule>();
	    
	    while (rs.next()) {
	    	 ShiftingSchedule model = new ShiftingSchedule();
	    	 model.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
	    	 model.setShiftType(rs.getString("shiftType"));	    
	    	 model.setTimeIn(util.sqlTimeToString(rs.getTime("timeIn")));
	    	 model.setTimeOut(util.sqlTimeToString(rs.getTime("timeOut")));
	    	 model.setDescription(rs.getString("description"));	    
	    	 model.setNoOfHours(rs.getInt("noOfHours"));
	    	 model.setColorAssignment(rs.getString("colorAssignment"));
	    	 list.add(model);

	    }	    
	    ps.close();
	    rs.close();      
	    return list;     

	}
    
    public  void add(ShiftingSchedule shiftingSchedule) throws SQLException, Exception {
  		String sql = "INSERT INTO shiftingSchedule (shiftType,timeIn,timeOut, description, noOfHours, colorAssignment) VALUES (?,?,?,?,?,?)";
  		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
  		int index = 1;
  		
        ps.setString(index++, shiftingSchedule.getShiftType());
        ps.setTime(index++, util.convertToSqlTime(shiftingSchedule.getTimeIn()));
        ps.setTime(index++, util.convertToSqlTime(shiftingSchedule.getTimeOut()));
        ps.setString(index++, shiftingSchedule.getDescription());
        ps.setInt(index++, shiftingSchedule.getNoOfHours());
        ps.setString(index++, shiftingSchedule.getColorAssignment());
        
        ps.executeUpdate();
          
        ResultSet keyResultSet = ps.getGeneratedKeys();
         int generatedAutoIncrementId = 0;
         if (keyResultSet.next()) {
          	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
          	shiftingSchedule.setShiftingScheduleId(generatedAutoIncrementId);
          	conn.commit();
          }
        ps.close();
          keyResultSet.close();

  	}
    
    public  void update(ShiftingSchedule shiftingSchedule) throws SQLException, Exception {
    	
    	
  		String sql = "UPDATE  shiftingSchedule set shiftType = ? ,timeIn = ?,timeOut = ? , description = ?, noOfHours = ?, colorAssignment = ?  where shiftingScheduleId =?";
  		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
  		int index = 1;
  		
        ps.setString(index++, shiftingSchedule.getShiftType());
        ps.setTime(index++, util.convertToSqlTime(shiftingSchedule.getTimeIn()));
        ps.setTime(index++, util.convertToSqlTime(shiftingSchedule.getTimeOut()));
        ps.setString(index++, shiftingSchedule.getDescription());
        ps.setInt(index++, shiftingSchedule.getNoOfHours());
        ps.setString(index++, shiftingSchedule.getColorAssignment());
        ps.setInt(index++, shiftingSchedule.getShiftingScheduleId());
        
        
        ps.executeUpdate();
          
        ResultSet keyResultSet = ps.getGeneratedKeys();
         int generatedAutoIncrementId = 0;
         if (keyResultSet.next()) {
          	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
          	shiftingSchedule.setShiftingScheduleId(generatedAutoIncrementId);
          	conn.commit();
          }
        ps.close();
          keyResultSet.close();

  	}
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}

	

}
