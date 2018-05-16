package dai.hris.dao.filemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dai.hris.dao.DBConstants;
import dai.hris.model.SubUnit;
import dai.hris.model.Unit;

public class SubUnitDAO {
	
Connection conn = null;
    
    public SubUnitDAO() {
        
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("SubUnitDAO :" + sqle.getMessage());
			
		} catch (Exception e) {
			System.out.println("SubUnitDAO :" + e.getMessage());
		
		}
    	
    }
    
    public List<SubUnit> getAllSubUnitList() throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();    	
    	
    	
    	sql.append("SELECT subUnitId,  subUnitName, empId, assistantId, unitId FROM subUnit");   	
		
		System.out.println("getAllUnitList SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<SubUnit> list = new ArrayList<SubUnit>();
	    
	    while (rs.next()) {
	    	SubUnit model = new SubUnit();
	    	model.setUnitId(rs.getInt("unitId"));
	    	model.setSubUnitId(rs.getInt("subUnitId"));
	    	model.setSubUnitName(rs.getString("subUnitName"));	    
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAssistantId(rs.getInt("assistantId"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    public SubUnit getSubUnit(int subUnitId) throws SQLException, Exception {
    	SubUnit model = null;
		String sql = "SELECT * FROM subUnit where subUnitId = ?";
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, subUnitId);

	    ResultSet rs = ps.executeQuery();
	    
	    if (rs.next()) {
	    	model = new SubUnit();
	    	
	    	model.setSubUnitId(subUnitId);
	    	model.setSubUnitName(rs.getString("subUnitName"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAssistantId(rs.getInt("assistantId"));
	    	model.setUnitId(rs.getInt("unitId"));
			
		}
	    sql = null;
	    ps.close();
	    rs.close();	 
	    return model;
    }
    
    public boolean isExist(String name) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM subUnit WHERE subUnitName = '");
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
    	
    	
    	sql.append("SELECT * FROM subUnit WHERE subUnitName = '");
    	sql.append(name);
    	sql.append("' and subUnitId <> ");  
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
    	
    	
    	sql.append("SELECT * FROM employee WHERE subUnitId = ");   	
    	sql.append(id);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("checkIfRecordHasBeenUsed subUnit: " + sql.toString());

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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM subUnit WHERE subUnitName like '%"); 
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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM subUnit");   	
    	  	
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
    
    //TODO Add Head Names and Assistant Names
    public List<SubUnit> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "subUnitName ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT subUnitId,  subUnitName, empId, assistantId, unitId, ROW_NUMBER() OVER(ORDER BY ");		
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM subUnit ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<SubUnit> list = new ArrayList<SubUnit>();
	    
	    while (rs.next()) {
	    	SubUnit model = new SubUnit();
	    	model.setSubUnitId(rs.getInt("subUnitId"));
	    	model.setSubUnitName(rs.getString("subUnitName"));	    
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAssistantId(rs.getInt("assistantId"));
	    	model.setUnitId(rs.getInt("unitId"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    
    public List<SubUnit> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
    	
		StringBuffer sql = new StringBuffer(); 
		
		if(jtSorting == null){
    		jtSorting = "subUnitName ASC";
    	}
		
		sql.append("WITH OrderedList AS (SELECT subUnitId, subUnitName, empId, assistantId, unitId ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM subUnit WHERE subUnitName like '%");
    	sql.append(filter);    	
    	sql.append("%') SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());
		
		

	    ResultSet rs = ps.executeQuery();
	    List<SubUnit> list = new ArrayList<SubUnit>();
	    
	    while (rs.next()) {
	    	SubUnit model = new SubUnit();
	    	model.setSubUnitId(rs.getInt("subUnitId"));
	    	model.setSubUnitName(rs.getString("subUnitName"));	    	 
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAssistantId(rs.getInt("assistantId"));
	    	model.setUnitId(rs.getInt("unitId"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     

	}
    

    
    
    public void add(SubUnit model) throws SQLException, Exception {
  		String sql = "INSERT INTO subUnit (subUnitName, empId, assistantId, unitId) VALUES (?, ?, ?, ?)";
  		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setString(1, model.getSubUnitName());
        ps.setInt(2, model.getEmpId());        
        ps.setInt(3, model.getAssistantId());        
        ps.setInt(4, model.getUnitId());        
        ps.executeUpdate();        
        conn.commit();               
        ps.close();
        sql = null;
  	}
    
    public  void delete(int id) throws SQLException, Exception {
		String sql = "DELETE from subUnit where subUnitId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        conn.commit();
        ps.close();
        sql = null;
	}
    
    public  void update(SubUnit model) throws SQLException, Exception {		
 		String sql = "UPDATE subUnit set subUnitName = ?, empId = ?, assistantId = ?, unitId = ? where subUnitId = ?";
 		PreparedStatement ps  = conn.prepareStatement(sql);
 		ps.setString(1, model.getSubUnitName()); 		
 		ps.setInt(2, model.getEmpId());        
        ps.setInt(3, model.getAssistantId());  
        ps.setInt(4, model.getUnitId());        
 		ps.setInt(5, model.getSubUnitId());         
        ps.executeUpdate();
        conn.commit();
        ps.close();
        sql = null;
 	}
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}

}
