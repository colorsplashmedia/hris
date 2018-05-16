package dai.hris.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dai.hris.dao.DBConstants;
import dai.hris.model.SystemTrail;



public class SystemTrailDAO {
	
Connection conn = null;
    
    public SystemTrailDAO() {
        
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("SystemTrailDAO :" + sqle.getMessage());
			
		} catch (Exception e) {
			System.out.println("SystemTrailDAO :" + e.getMessage());
		
		}
    	
    }    
    
    public List<String> getProcessTypeList() throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();		
		
		sql.append("SELECT distinct(processType) as processType FROM systemTrail ");	    	
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getProcessTypeList SQL: " + sql.toString());		

	    ResultSet rs = ps.executeQuery();
	    List<String> list = new ArrayList<String>();
	    
	    while (rs.next()) {
	    	list.add(rs.getString("processType"));
	    }
	    
	    return list;
    	
    }
    
    public List<String> getModuleNameList() throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();		
		
		sql.append("SELECT distinct(moduleName) as moduleName FROM systemTrail ");	    	
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getModuleNameList SQL: " + sql.toString());		

	    ResultSet rs = ps.executeQuery();
	    List<String> list = new ArrayList<String>();
	    
	    while (rs.next()) {
	    	list.add(rs.getString("moduleName"));
	    }
	    
	    return list;
    	
    }
    
    
    public List<SystemTrail> getSystemTrailReportDetails(SystemTrail model) throws SQLException, Exception {
    	
		StringBuffer sql = new StringBuffer();		
		
		sql.append("SELECT st.processType, st.moduleName, st.processDesc, st.userId, e.firstname, e.lastname, e.username, st.departmentId, d.departmentName, st.transDate ");	
		sql.append(" FROM systemTrail st, department d, employee e WHERE st.departmentId = d.departmentId AND st.userId = e.empId AND st.departmentId = ");
    	sql.append(model.getDepartmentId());   	
    	
    	if(model.getDateFrom() != null && model.getDateFrom().length() > 0){
    		if(model.getDateFrom().equals(model.getDateTo())){
    			sql.append(" AND CONVERT(DATE, st.transDate) = '");
        		sql.append(model.getDateFrom());
        		sql.append("'");        		
        	} else {
        		sql.append(" AND st.transDate between '");
        		sql.append(model.getDateFrom());
        		sql.append(" 00:00:00' AND '");
        		sql.append(model.getDateTo());
        		sql.append(" 23:59:59'");
        	}    		
    	}
    	
    	if(model.getUserId() > 0){
    		sql.append(" AND st.userId = ");
    		sql.append(model.getUserId());    		
    	}
    	
    	if(model.getModuleName() != null && model.getModuleName().length() > 0){
    		sql.append(" AND st.moduleName = '");
    		sql.append(model.getModuleName());
    		sql.append("'");
    	}
    	
    	if(model.getProcessType() != null && model.getProcessType().length() > 0){
    		sql.append(" AND st.processType = '");
    		sql.append(model.getProcessType());
    		sql.append("'");
    	}
    	
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("getSystemTrailReportDetails SQL: " + sql.toString());
		
		

	    ResultSet rs = ps.executeQuery();
	    List<SystemTrail> list = new ArrayList<SystemTrail>();
	    
	    while (rs.next()) {
	    	 SystemTrail systemTrail = new SystemTrail();
	    	 systemTrail.setProcessType(rs.getString("processType"));
	    	 systemTrail.setModuleName(rs.getString("moduleName"));	
	    	 systemTrail.setProcessDesc(rs.getString("processDesc"));
	    	 systemTrail.setUserId(rs.getInt("userId"));
	    	 systemTrail.setUserName(rs.getString("username"));
	    	 systemTrail.setUserFullName(rs.getString("firstname") + " " + rs.getString("lastname"));
	    	 systemTrail.setDepartmentId(rs.getInt("departmentId"));
	    	 systemTrail.setDepartmentName(rs.getString("departmentName"));
	    	 systemTrail.setTransDate(rs.getString("transDate").substring(0, 19));
	    	 list.add(systemTrail);

	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     

	}
    
   
    public  void insertSystemTrail(SystemTrail model) throws SQLException, Exception {
  		String sql = "INSERT INTO systemTrail (processType, moduleName, processDesc, userId, departmentId, transDate) VALUES (?,?,?,?,?,SYSDATETIME())";
  		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, model.getProcessType());
        ps.setString(2, model.getModuleName());
        ps.setString(3, model.getProcessDesc());
        ps.setInt(4, model.getUserId());
        ps.setInt(5, model.getDepartmentId());
        
        
        ps.executeUpdate();
          
        conn.commit();

        ps.close();
          

  	}    
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}

}
