package dai.hris.dao.filemanager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dai.hris.dao.DBConstants;
import dai.hris.model.Holiday;


public class HolidayDAO {
	Connection conn = null;
	public HolidayDAO() {
		  
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("HolidayDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("HolidayDAO :" + e.getMessage());
		}
	}
	
	public boolean isExist(String name, String date) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM holiday WHERE holidayName = '");
    	sql.append(name);
    	sql.append("' AND holidayDate = '");   	
    	sql.append(date);
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
    
    public boolean isExistUpdate(String name, int id, String date) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM holiday WHERE holidayName = '");
    	sql.append(name);
    	sql.append("' AND holidayDate = '");   	
    	sql.append(date);
    	sql.append("' and holidayId <> ");  
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
		//no dependencies
		return false;
	}
	
	public void delete(int id)  throws SQLException, Exception {
		String sql = "DELETE FROM holiday WHERE holidayId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        conn.commit();
        ps.close();
	}
	
	  
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM holiday WHERE holidayName like '%"); 
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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM holiday");   	
    	  	
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
    
    public  List<Holiday> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "holidayDate ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT holidayId, holidayName, holidayDate, holidayType, createdBy, createdDate, ROW_NUMBER() OVER(ORDER BY ");
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM holiday ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<Holiday> list = new ArrayList<Holiday>();
	    
	    while (rs.next()) {
	    	Holiday holiday = new Holiday();
	    	holiday.setHolidayId(rs.getInt("holidayId"));
	    	holiday.setHolidayName(rs.getString("holidayName"));
	    	holiday.setHolidayDate(rs.getString("holidayDate"));  
	    	holiday.setHolidayType(rs.getString("holidayType"));
	    	holiday.setCreatedBy(rs.getInt("createdBy"));
	    	holiday.setCreatedDate(rs.getDate("createdDate"));  	 	
	    	list.add(holiday);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    public List<Holiday> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
    	
		StringBuffer sql = new StringBuffer(); 
		
		if(jtSorting == null){
    		jtSorting = "holidayDate ASC";
    	}
		
		sql.append("WITH OrderedList AS (SELECT holidayId, holidayName, holidayDate, holidayType, createdBy, createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM holiday WHERE holidayName like '%");
    	sql.append(filter);    	
    	sql.append("%') SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());
		
		

	    ResultSet rs = ps.executeQuery();
	    List<Holiday> list = new ArrayList<Holiday>();
	    
	    while (rs.next()) {
	    	Holiday holiday = new Holiday();
	    	holiday.setHolidayId(rs.getInt("holidayId"));
	    	holiday.setHolidayName(rs.getString("holidayName"));
	    	holiday.setHolidayDate(rs.getString("holidayDate"));  
	    	holiday.setHolidayType(rs.getString("holidayType"));
	    	holiday.setCreatedBy(rs.getInt("createdBy"));
	    	holiday.setCreatedDate(rs.getDate("createdDate"));  	 	
	    	list.add(holiday);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     

	}
	
//	public ArrayList<Holiday> getAll() throws SQLException, Exception {
//
//			String sql = "SELECT * FROM holiday";		
//			PreparedStatement ps = conn.prepareStatement(sql.toString());
//
//		    ResultSet rs = ps.executeQuery();
//		    ArrayList<Holiday> list = new ArrayList<Holiday>();
//		    
//		    while (rs.next()) {
//		    	Holiday holiday = new Holiday();
//		    	holiday.setHolidayId(rs.getInt("holidayId"));
//		    	holiday.setHolidayName(rs.getString("holidayName"));
//		    	holiday.setHolidayDate(rs.getString("holidayDate"));  
//		    	holiday.setHolidayType(rs.getString("holidayType"));
//		    	holiday.setCreatedBy(rs.getInt("createdBy"));
//		    	holiday.setCreatedDate(rs.getDate("createdDate"));  	 	
//		    	list.add(holiday);
//		    }
//		    
//		    ps.close();
//		    rs.close();      
//		    return list;     
//
//		}
	    
	    //TODO Fix HolidayDAO convert holidayDate to dateTime
	    public int add(Holiday holiday) throws SQLException, Exception {
	  		String sql = "INSERT INTO holiday (holidayName, holidayDate, holidayType, createdBy, createdDate) VALUES (?,?,?,?,?)";
	  		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	        ps.setString(1, holiday.getHolidayName());
	        ps.setString(2, holiday.getHolidayDate());
	        ps.setString(3, holiday.getHolidayType());
	        ps.setInt(4, holiday.getCreatedBy());
	        ps.setDate(5, holiday.getCreatedDate());
	        
	        
	        ps.executeUpdate();
	          
	        ResultSet keyResultSet = ps.getGeneratedKeys();
	         int generatedAutoIncrementId = 0;
	         if (keyResultSet.next()) {
	          	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
	          	holiday.setHolidayId(generatedAutoIncrementId);
	          	conn.commit();
	          }

	          ps.close();
	          keyResultSet.close();
	          	         
	         return generatedAutoIncrementId;

	  	}
	    
	    public int update(Holiday holiday) throws SQLException, Exception {
			
	 		String sql = "UPDATE holiday set holidayName=?, holidayDate=?, holidayType=? where holidayId = ?";
	 		PreparedStatement ps  = conn.prepareStatement(sql);
	 		ps.setString(1, holiday.getHolidayName());	 		
	 		ps.setString(2, holiday.getHolidayDate());
	 		ps.setString(3, holiday.getHolidayType());	
	 		ps.setInt(4, holiday.getHolidayId());	
	         
	         int count = ps.executeUpdate();
	         conn.commit();
	         ps.close();
	         return count;

	 	}
	    
	    public List<Holiday> getHolidaysByPayPeriodRange(Date dateFrom, Date dateTo) throws SQLException, Exception {
	    	
	    	
	    	StringBuffer sql = new StringBuffer();
	    			
	    	sql.append("SELECT * FROM holiday WHERE holidayDate BETWEEN '");		
	    	sql.append(dateFrom);
	    	sql.append("' AND '");
	    	sql.append(dateTo);
	    	sql.append("'");
	    	
	    	
	    	PreparedStatement ps = conn.prepareStatement(sql.toString());
//			ps.setDate(1, dateFrom);
//			ps.setDate(2, dateTo);

		    ResultSet rs = ps.executeQuery();
		    List<Holiday> list = new ArrayList<Holiday>();
		    
		    while (rs.next()) {
		    	Holiday holiday = new Holiday();
		    	holiday.setHolidayId(rs.getInt("holidayId"));
		    	holiday.setHolidayName(rs.getString("holidayName"));
		    	holiday.setHolidayDate(rs.getString("holidayDate"));  
		    	holiday.setHolidayType(rs.getString("holidayType"));
		    	holiday.setCreatedBy(rs.getInt("createdBy"));
		    	holiday.setCreatedDate(rs.getDate("createdDate"));  	 	
		    	list.add(holiday);
		    }
		    
		    ps.close();
		    rs.close();      
		    return list;
	    	
	    	
	    }
	    
	    public Holiday getHolidayById(int id) throws SQLException, Exception {
	    	
	    	StringBuffer sql = new StringBuffer();    	
	    	
	    	sql.append("SELECT * FROM holiday WHERE holidayId = "); 
	    	sql.append(id);
	    	  	
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			
			System.out.println("SQL: " + sql.toString());

		    ResultSet rs = ps.executeQuery();
		    
		    Holiday holiday = new Holiday();
		    
		    if (rs.next()) {        	
		    	holiday.setHolidayId(rs.getInt("holidayId"));
		    	holiday.setHolidayName(rs.getString("holidayName"));
		    	holiday.setHolidayDate(rs.getString("holidayDate"));  
		    	holiday.setHolidayType(rs.getString("holidayType"));
		    	holiday.setCreatedBy(rs.getInt("createdBy"));
		    	holiday.setCreatedDate(rs.getDate("createdDate"));  
		    }
		    
		    ps.close();
		    rs.close();      
		    
		    return holiday;
	    }
	    
	    public void closeConnection() throws SQLException, Exception {
			conn.close();
		}

}
