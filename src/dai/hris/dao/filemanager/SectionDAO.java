package dai.hris.dao.filemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dai.hris.dao.DBConstants;
import dai.hris.model.Section;

public class SectionDAO {
	
Connection conn = null;
    
    public SectionDAO() {
        
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("SectionDAO :" + sqle.getMessage());
			
		} catch (Exception e) {
			System.out.println("SectionDAO :" + e.getMessage());
		
		}
    	
    }
    
    public Section getSection(int sectionId) throws SQLException, Exception {
    	Section model = null;
		String sql = "SELECT * FROM section where sectionId = ?";
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, sectionId);

	    ResultSet rs = ps.executeQuery();
	    
	    if (rs.next()) {
	    	model = new Section();
	    	
	    	model.setSectionId(sectionId);
	    	model.setSectionName(rs.getString("sectionName"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAssistantId(rs.getInt("assistantId"));
			
		}
	    sql = null;
	    ps.close();
	    rs.close();	 
	    return model;
    }
    
    public boolean isExist(String name) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM section WHERE sectionName = '");
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
    	
    	
    	sql.append("SELECT * FROM section WHERE sectionName = '");
    	sql.append(name);
    	sql.append("' and sectionId <> ");  
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
    	
    	
    	sql.append("SELECT * FROM employee WHERE sectionId = ");   	
    	sql.append(id);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("checkIfRecordHasBeenUsed section: " + sql.toString());

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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM section WHERE sectionName like '%"); 
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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM section");   	
    	  	
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
    public List<Section> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "sectionName ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT sectionId,  sectionName, empId, assistantId, ROW_NUMBER() OVER(ORDER BY ");		
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM section ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<Section> list = new ArrayList<Section>();
	    
	    while (rs.next()) {
	    	Section model = new Section();
	    	model.setSectionId(rs.getInt("sectionId"));
	    	model.setSectionName(rs.getString("sectionName"));	    
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAssistantId(rs.getInt("assistantId"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    public List<Section> getAllSectionList() throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();    	
    	
    	
    	sql.append("SELECT sectionId,  sectionName, empId, assistantId FROM section");   	
		
		System.out.println("getAllSectionList SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<Section> list = new ArrayList<Section>();
	    
	    while (rs.next()) {
	    	Section model = new Section();
	    	model.setSectionId(rs.getInt("sectionId"));
	    	model.setSectionName(rs.getString("sectionName"));	    
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAssistantId(rs.getInt("assistantId"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    //TODO Add Head Names and Assistant Names
    public List<Section> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
    	
		StringBuffer sql = new StringBuffer(); 
		
		if(jtSorting == null){
    		jtSorting = "sectionName ASC";
    	}
		
		sql.append("WITH OrderedList AS (SELECT sectionId,  sectionName, empId, assistantId, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM section WHERE sectionName like '%");
    	sql.append(filter);    	
    	sql.append("%') SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());
		
		

	    ResultSet rs = ps.executeQuery();
	    List<Section> list = new ArrayList<Section>();
	    
	    while (rs.next()) {
	    	Section model = new Section();
	    	model.setSectionId(rs.getInt("sectionId"));
	    	model.setSectionName(rs.getString("sectionName"));	    	 
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAssistantId(rs.getInt("assistantId"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     

	}
    

    
    
    public void add(Section model) throws SQLException, Exception {
  		String sql = "INSERT INTO section (sectionName, empId, assistantId) VALUES (?, ?, ?)";
  		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setString(1, model.getSectionName());
        ps.setInt(2, model.getEmpId());        
        ps.setInt(3, model.getAssistantId());        
        ps.executeUpdate();        
        conn.commit();               
        ps.close();
        sql = null;
  	}
    
    public  void delete(int id) throws SQLException, Exception {
		String sql = "DELETE from section where sectionId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        conn.commit();
        ps.close();
        sql = null;
	}
    
    public  void update(Section model) throws SQLException, Exception {		
 		String sql = "UPDATE section set sectionName = ?, empId = ?, assistantId = ? where sectionId = ?";
 		PreparedStatement ps  = conn.prepareStatement(sql);
 		ps.setString(1, model.getSectionName()); 		
 		ps.setInt(2, model.getEmpId());        
        ps.setInt(3, model.getAssistantId());  
 		ps.setInt(4, model.getSectionId());         
        ps.executeUpdate();
        conn.commit();
        ps.close();
        sql = null;
 	}
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}

}
