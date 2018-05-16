package dai.hris.dao.filemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dai.hris.dao.DBConstants;
import dai.hris.model.Unit;

public class UnitDAO {
	
Connection conn = null;
    
    public UnitDAO() {
        
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("UnitDAO :" + sqle.getMessage());
			
		} catch (Exception e) {
			System.out.println("UnitDAO :" + e.getMessage());
		
		}
    	
    }
    
    public Unit getUnit(int unitId) throws SQLException, Exception {
    	Unit model = null;
		String sql = "SELECT * FROM unit where unitId = ?";
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, unitId);

	    ResultSet rs = ps.executeQuery();
	    
	    if (rs.next()) {
	    	model = new Unit();
	    	
	    	model.setUnitId(unitId);
	    	model.setUnitName(rs.getString("unitName"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAssistantId(rs.getInt("assistantId"));
	    	model.setSectionId(rs.getInt("sectionId"));
			
		}
	    sql = null;
	    ps.close();
	    rs.close();	 
	    return model;
    }
    
    public boolean isExist(String name) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM unit WHERE unitName = '");
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
    	
    	
    	sql.append("SELECT * FROM unit WHERE unitName = '");
    	sql.append(name);
    	sql.append("' and unitId <> ");  
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
    	
    	
    	sql.append("SELECT * FROM employee WHERE unitId = ");   	
    	sql.append(id);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("checkIfRecordHasBeenUsed unit: " + sql.toString());

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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM unit WHERE unitName like '%"); 
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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM unit");   	
    	  	
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
    public List<Unit> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "unitName ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT unitId,  unitName, empId, assistantId, sectionId, ROW_NUMBER() OVER(ORDER BY ");		
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM unit ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<Unit> list = new ArrayList<Unit>();
	    
	    while (rs.next()) {
	    	Unit model = new Unit();
	    	model.setUnitId(rs.getInt("unitId"));
	    	model.setUnitName(rs.getString("unitName"));	    
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAssistantId(rs.getInt("assistantId"));
	    	model.setSectionId(rs.getInt("sectionId"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    public List<Unit> getAllUnitList() throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();    	
    	
    	
    	sql.append("SELECT unitId,  unitName, empId, assistantId, sectionId FROM unit");   	
		
		System.out.println("getAllUnitList SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<Unit> list = new ArrayList<Unit>();
	    
	    while (rs.next()) {
	    	Unit model = new Unit();
	    	model.setUnitId(rs.getInt("unitId"));
	    	model.setUnitName(rs.getString("unitName"));	    
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAssistantId(rs.getInt("assistantId"));
	    	model.setSectionId(rs.getInt("sectionId"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    
    public List<Unit> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
    	
		StringBuffer sql = new StringBuffer(); 
		
		if(jtSorting == null){
    		jtSorting = "unitName ASC";
    	}
		
		sql.append("WITH OrderedList AS (SELECT unitId, unitName, empId, assistantId, sectionId, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM unit WHERE unitName like '%");
    	sql.append(filter);    	
    	sql.append("%') SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());
		
		

	    ResultSet rs = ps.executeQuery();
	    List<Unit> list = new ArrayList<Unit>();
	    
	    while (rs.next()) {
	    	Unit model = new Unit();
	    	model.setUnitId(rs.getInt("unitId"));
	    	model.setUnitName(rs.getString("unitName"));	    	 
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAssistantId(rs.getInt("assistantId"));
	    	model.setSectionId(rs.getInt("sectionId"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     

	}
    

    
    
    public void add(Unit model) throws SQLException, Exception {
  		String sql = "INSERT INTO unit (unitName, empId, assistantId, sectionId) VALUES (?, ?, ?, ?)";
  		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setString(1, model.getUnitName());
        ps.setInt(2, model.getEmpId());        
        ps.setInt(3, model.getAssistantId());     
        ps.setInt(4, model.getSectionId());     
        ps.executeUpdate();        
        conn.commit();               
        ps.close();
        sql = null;
  	}
    
    public  void delete(int id) throws SQLException, Exception {
		String sql = "DELETE from unit where unitId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        conn.commit();
        ps.close();
        sql = null;
	}
    
    public  void update(Unit model) throws SQLException, Exception {		
 		String sql = "UPDATE unit set unitName = ?, empId = ?, assistantId = ?, sectionId = ? where unitId = ?";
 		PreparedStatement ps  = conn.prepareStatement(sql);
 		ps.setString(1, model.getUnitName()); 		
 		ps.setInt(2, model.getEmpId());        
        ps.setInt(3, model.getAssistantId());  
        ps.setInt(4, model.getSectionId());     
 		ps.setInt(5, model.getUnitId());         
        ps.executeUpdate();
        conn.commit();
        ps.close();
        sql = null;
 	}
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}

}
