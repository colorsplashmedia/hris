package dai.hris.dao.filemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dai.hris.dao.DBConstants;
import dai.hris.model.Province;

public class ProvinceDAO {
	
Connection conn = null;
    
    public ProvinceDAO() {
        
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("ProvinceDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("ProvinceDAO :" + e.getMessage());
		}
    	
    }
    
    public boolean isExist(String name) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM province WHERE provinceName = '");
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
    	
    	
    	sql.append("SELECT * FROM province WHERE provinceName = '");
    	sql.append(name);
    	sql.append("' and provinceId <> ");  
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
    	
    	
    	sql.append("SELECT * FROM employee WHERE provinceId = ");   	
    	sql.append(id);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("checkIfRecordHasBeenUsed Province: " + sql.toString());

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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM province WHERE provinceName like '%"); 
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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM province");   	
    	  	
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
    
    public  List<Province> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "provinceName ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT provinceId,  provinceName, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM province ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<Province> list = new ArrayList<Province>();
	    
	    while (rs.next()) {
	    	Province province = new Province();
	    	 province.setProvinceId(rs.getInt("provinceId"));
	    	 province.setProvinceName(rs.getString("provinceName"));	    	 
	    	 list.add(province);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    public List<Province> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
    	
		StringBuffer sql = new StringBuffer(); 
		
		if(jtSorting == null){
    		jtSorting = "provinceName ASC";
    	}
		
		sql.append("WITH OrderedList AS (SELECT provinceId,  provinceName, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM province WHERE provinceName like '%");
    	sql.append(filter);    	
    	sql.append("%') SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());
		
		

	    ResultSet rs = ps.executeQuery();
	    List<Province> list = new ArrayList<Province>();
	    
	    while (rs.next()) {
	    	Province province = new Province();
	    	 province.setProvinceId(rs.getInt("provinceId"));
	    	 province.setProvinceName(rs.getString("provinceName"));	    	 
	    	 list.add(province);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     

	}
    
//    public  ArrayList<Province> getAll() throws SQLException, Exception {
//
//		String sql = "SELECT   provinceId,  provinceName FROM province";		
//		PreparedStatement ps = conn.prepareStatement(sql.toString());
//
//	    ResultSet rs = ps.executeQuery();
//	    ArrayList<Province> list = new ArrayList<Province>();
//	    
//	    while (rs.next()) {
//	    	 Province province = new Province();
//	    	 province.setProvinceId(rs.getInt("provinceId"));
//	    	 province.setProvinceName(rs.getString("provinceName"));	    	 
//	    	 list.add(province);
//
//	    }
//	    
//	    ps.close();
//	    rs.close();      
//	    return list;     
//
//	}
    
    
    public  void add(Province province) throws SQLException, Exception {
  		String sql = "INSERT INTO province (provinceName) VALUES (?)";
  		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, province.getProvinceName());
        
        ps.executeUpdate();
          
        ResultSet keyResultSet = ps.getGeneratedKeys();
         int generatedAutoIncrementId = 0;
         if (keyResultSet.next()) {
          	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
          	province.setProvinceId(generatedAutoIncrementId);
          	conn.commit();
          }

          ps.close();
          keyResultSet.close();

  	}
    
    public  void delete(int id) throws SQLException, Exception {
		String sql = "DELETE from province where provinceId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        conn.commit();
        ps.close();

	}
    
    public  void update(Province province) throws SQLException, Exception {
		
 		String sql = "UPDATE province set provinceName = ? where provinceId = ?";
 		PreparedStatement ps  = conn.prepareStatement(sql);
 		ps.setString(1, province.getProvinceName());
 		
 		ps.setInt(2, province.getProvinceId());
 		
         
         ps.executeUpdate();
         conn.commit();
         ps.close();

 	}
    
    public Province getProvinceById(int id) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	sql.append("SELECT * FROM province WHERE provinceId = "); 
    	sql.append(id);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    Province model = new Province();
	    
	    if (rs.next()) {        	
	    	model.setProvinceId(rs.getInt("provinceId"));
	    	model.setProvinceName(rs.getString("provinceName"));
	    }
	    
	    ps.close();
	    rs.close();      
	    
	    return model;
    }
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}

}
