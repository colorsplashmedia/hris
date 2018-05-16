package dai.hris.dao.filemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dai.hris.dao.DBConstants;
import dai.hris.model.City;


public class CityDAO {
	Connection conn = null;
	public CityDAO() {
		  
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("CityDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("CityDAO :" + e.getMessage());
		}
	}
	
	public boolean isExist(String name) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM city WHERE cityName = '");
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
    	
    	
    	sql.append("SELECT * FROM city WHERE cityName = '");
    	sql.append(name);
    	sql.append("' and cityId <> ");  
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
    	
    	
    	sql.append("SELECT * FROM employee WHERE cityId = ");   	
    	sql.append(id);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("checkIfRecordHasBeenUsed City: " + sql.toString());

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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM city WHERE cityName like '%"); 
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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM city");   	
    	  	
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
    
    public City getCityById(int id) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	sql.append("SELECT * FROM city WHERE cityId = "); 
    	sql.append(id);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    City city = new City();
	    
	    if (rs.next()) {    	    	
	    	city.setCityId(rs.getInt("cityId"));
	    	city.setCityName(rs.getString("cityName"));
	    }
	    
	    ps.close();
	    rs.close();      
	    
    	
    	return city;
    }
    
    public  List<City> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "cityName ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT cityId, cityName, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM city ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<City> list = new ArrayList<City>();
	    
	    while (rs.next()) {
	    	City city = new City();
	    	 city.setCityId(rs.getInt("cityId"));
	    	 city.setCityName(rs.getString("cityName"));	    	 
	    	 list.add(city);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    public List<City> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
    	
		StringBuffer sql = new StringBuffer(); 
		
		if(jtSorting == null){
    		jtSorting = "cityName ASC";
    	}
		
		sql.append("WITH OrderedList AS (SELECT cityId, cityName, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM city WHERE cityName like '%");
    	sql.append(filter);    	
    	sql.append("%') SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());
		
		

	    ResultSet rs = ps.executeQuery();
	    List<City> list = new ArrayList<City>();
	    
	    while (rs.next()) {
	    	City city = new City();
	    	 city.setCityId(rs.getInt("cityId"));
	    	 city.setCityName(rs.getString("cityName"));	    	 
	    	 list.add(city);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     

	}
	
//	  public ArrayList<City> getAll() throws SQLException, Exception {
//
//			String sql = "SELECT * FROM city";		
//			PreparedStatement ps = conn.prepareStatement(sql.toString());
//
//		    ResultSet rs = ps.executeQuery();
//		    ArrayList<City> list = new ArrayList<City>();
//		    
//		    while (rs.next()) {
//		    	 City city = new City();
//		    	 city.setCityId(rs.getInt("cityId"));
//		    	 city.setCityName(rs.getString("cityName"));	    	 
//		    	 list.add(city);
//		    }
//		    
//		    ps.close();
//		    rs.close();
//		    return list;
//		}
	    
	    
	    public  void add(City city) throws SQLException, Exception {
	  		String sql = "INSERT INTO city (cityName) VALUES (?)";
	  		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	        ps.setString(1, city.getCityName());
	        
	        ps.executeUpdate();
	          
	        ResultSet keyResultSet = ps.getGeneratedKeys();
	         int generatedAutoIncrementId = 0;
	         if (keyResultSet.next()) {
	          	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
	          	city.setCityId(generatedAutoIncrementId);
	          	conn.commit();
	          }

	          ps.close();
	          keyResultSet.close();

	  	}
	    
	    public  void delete(int id) throws SQLException, Exception {
			String sql = "DELETE from city where cityId = ?";
			PreparedStatement ps  = conn.prepareStatement(sql);
	        ps.setInt(1, id);
	        ps.executeUpdate();
	        conn.commit();
	        ps.close();

		}
	    
	    public  void update(City city) throws SQLException, Exception {
			
	 		String sql = "UPDATE city set cityName = ? where cityId = ?";
	 		PreparedStatement ps  = conn.prepareStatement(sql);
	 		ps.setString(1, city.getCityName());	 		
	 		ps.setInt(2, city.getCityId());
	 		
	         
	         ps.executeUpdate();
	         conn.commit();
	         ps.close();

	 	}
	    
	    public void closeConnection() throws SQLException, Exception {
			conn.close();
		}

}
