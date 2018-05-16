package dai.hris.dao.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import dai.hris.action.filemanager.util.EmployeeUtil;
import dai.hris.dao.DBConstants;
import dai.hris.dao.filemanager.SectionDAO;
import dai.hris.dao.filemanager.SubUnitDAO;
import dai.hris.dao.filemanager.UnitDAO;
import dai.hris.model.SystemParameters;
import dai.hris.model.Employee;
import dai.hris.model.Section;
import dai.hris.model.SubUnit;
import dai.hris.model.Unit;

public class LoginDAO {
	
	Connection conn = null;
    
    public LoginDAO() {    	
    	
    	try {
    		
    		/* MYSQL CODE */
//			MysqlDataSource ds = null;
//			ds = new MysqlDataSource();
//			ds.setUrl(DBConstants.DB_URL);
//			ds.setUser(DBConstants.DB_USERNAME);
//			ds.setPassword(DBConstants.DB_PASSWORD);
//			conn = ds.getConnection();
    		
    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("LoginDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("LoginDAO :" + e.getMessage());
		}
    	
    }
    
    public SystemParameters getApprovers() throws SQLException, Exception {
    	SystemParameters model = new SystemParameters();
    	String sql = "SELECT * FROM sysAdmin";
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    if (rs.next()) {	    	
	    	model.setAdminId(rs.getInt("adminId"));
	    	model.setAdminAssistantId(rs.getInt("adminAssistantId"));
	    	model.setHrAdminId(rs.getInt("hrHeadId"));
	    	model.setHrAdminAssistantId(rs.getInt("hrAssistantHeadId"));
	    	model.setHrAdminLiasonId(rs.getInt("hrLiasonId"));	    	
	    	model.setIsAdminChecked(rs.getString("isAdminApprover"));
	    	
	    }
	    
	    SectionDAO sectionDAO = new SectionDAO();
    	
    	List<Section> sectionList = sectionDAO.getAllSectionList();
    	
    	model.setSectionHeadList(sectionList);
    	
    	UnitDAO unitDAO = new UnitDAO();
    	
    	List<Unit> unitList = unitDAO.getAllUnitList();
    	
    	model.setUnitHeadList(unitList);
    	
    	SubUnitDAO subUnitDAO = new SubUnitDAO();
    	
    	List<SubUnit> subUnitList = subUnitDAO.getAllSubUnitList();
    	
    	model.setSubUnitHeadList(subUnitList);
	    
	    return model;
    }
	
    public Employee getEmployee(String username) throws SQLException, Exception {
		Employee emp = null;
		String sql = "SELECT * FROM employee where username = ?";
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setString(1, username);

	    ResultSet rs = ps.executeQuery();
	    
	    if (rs.next()) {
	    	emp = new Employee();
			emp.setEmpId(rs.getInt("empId"));
			emp.setEmpNo(rs.getString("empNo")); // required field
			emp.setPlantillaNo(rs.getString("plantillaNo"));
			emp.setLastname(rs.getString("lastname"));
			emp.setFirstname(rs.getString("firstname"));
			emp.setMiddlename(rs.getString("middlename"));
			emp.setDateOfBirth(StringUtils.isEmpty(rs.getString("dateOfBirth"))? "" : rs.getString("dateOfBirth").substring(0, 10));
			emp.setGender(rs.getString("gender"));
			emp.setCivilStatus(rs.getString("civilStatus"));
			emp.setNationality(rs.getString("nationality"));
			emp.setStreet(rs.getString("street"));
			emp.setCityId(rs.getInt("cityId"));
			emp.setEmail(rs.getString("email"));
			emp.setMobileNo(rs.getString("mobileNo"));
			emp.setTelNo(rs.getString("telNo"));
			emp.setBirthPlace(rs.getString("birthPlace"));
			emp.setProvinceId(rs.getInt("provinceId"));
			emp.setZipCode(rs.getString("zipCode"));
			emp.setJobTitleId(rs.getInt("jobTitleId"));
//			emp.setDepartmentId(rs.getInt("departmentId"));
//			emp.setDivisionId(rs.getInt("divisionId"));
			
			emp.setSectionId(rs.getInt("sectionId"));
			emp.setUnitId(rs.getInt("unitId"));
			emp.setSubUnitId(rs.getInt("subUnitId"));
			
			emp.setEmployeeTypeId(rs.getInt("employeeTypeId"));
			emp.setEmpStatus(rs.getString("empStatus"));
			emp.setEmploymentDate(StringUtils.isEmpty(rs.getString("employmentDate")) ? "" : rs.getString("employmentDate").substring(0, 10));
			emp.setEndOfContract(StringUtils.isEmpty(rs.getString("endOfContract")) ? "" : rs.getString("endOfContract").substring(0, 10));
			emp.setSss(rs.getString("sss"));
			emp.setGsis(rs.getString("gsis"));
			emp.setHdmf(rs.getString("hdmf"));
			emp.setTin(rs.getString("tin"));
			emp.setPhic(rs.getString("phic"));
			emp.setTaxstatus(rs.getString("taxstatus"));
			emp.setPicLoc(rs.getString("picLoc"));

			emp.setUsername(rs.getString("username"));			
			emp.setPassword(EmployeeUtil.decodePassword(rs.getString("password")));
			emp.setCountryId(rs.getInt("countryId"));
			
			emp.setJobTitleName(getJobtitleName(rs.getInt("jobTitleId")));
			emp.setSectionName(getSectionName(rs.getInt("sectionId")));
			emp.setUnitName(getUnitName(rs.getInt("unitId")));
			emp.setSubUnitName(getSubUnitName(rs.getInt("subUnitId")));
			emp.setPersonnelType(rs.getString("personnelType"));
			
			
//			String superVisorName = getSupervisorName(rs.getInt("superVisor1Id"));
//			
//			emp.setSuperVisorName(superVisorName);
		}
	    sql = null;
	    ps.close();
	    rs.close();	 
	    return emp;

	}
    
//    private String getSupervisorName(int empId) throws SQLException, Exception {
//		
//		String sql = "SELECT firstname, lastname FROM employee where empId = ?";
//		PreparedStatement ps = conn.prepareStatement(sql.toString());
//		ps.setInt(1, empId);
//
//	    ResultSet rs = ps.executeQuery();
//	    
//	    String superVisorName = "";
//	    
//	    if (rs.next()) {			
//			superVisorName = rs.getString("firstname") + " " + rs.getString("lastname");
//		}
//	    sql = null;
//	    ps.close();
//	    rs.close();	 
//	    return superVisorName;
//
//	}
    
    private String getJobtitleName (int jobTitleId)  throws SQLException, Exception {
		String sql = "SELECT   jobTitleId,  jobTitle FROM jobTitle where jobTitleId = ?";		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, jobTitleId);
		
	    ResultSet rs = ps.executeQuery();
	   
	    
	    if (rs.next()) {	    	
	    	return rs.getString("jobTitle");	    	 
	    }
	    
	    ps.close();
	    rs.close();      
	    return "";     
	}
    
    private String getSectionName (int sectionId)  throws SQLException, Exception {
		String sql = "SELECT sectionId, sectionName FROM section WHERE sectionId = ?";		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, sectionId);
		
	    ResultSet rs = ps.executeQuery();
	   
	    
	    if (rs.next()) {	    	
	    	return rs.getString("sectionName");	    	 
	    }
	    
	    ps.close();
	    rs.close();      
	    return "";     
	}
	
	private String getUnitName (int unitId)  throws SQLException, Exception {
		String sql = "SELECT unitId, unitName FROM unit WHERE unitId = ?";		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, unitId);
		
	    ResultSet rs = ps.executeQuery();
	   
	    
	    if (rs.next()) {	    	
	    	return rs.getString("unitName");	    	 
	    }
	    
	    ps.close();
	    rs.close();      
	    return "";     
	}
	
	private String getSubUnitName (int subUnitId)  throws SQLException, Exception {
		String sql = "SELECT subUnitId, subUnitName FROM subUnit WHERE subUnitId = ?";		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, subUnitId);
		
	    ResultSet rs = ps.executeQuery();
	   
	    
	    if (rs.next()) {	    	
	    	return rs.getString("subUnitName");	    	 
	    }
	    
	    ps.close();
	    rs.close();      
	    return "";     
	}
    
    public boolean checkLoginCredentials(String username, String password) throws SQLException, Exception {
    	
    	String sql = "SELECT * FROM employee where username = '" + username + "' and password = '" + password + "'";    	
    	
    	PreparedStatement ps = conn.prepareStatement(sql);            

    	ResultSet rs = ps.executeQuery();            

            
    	if (rs.next()) {
    		sql = null;
	        ps.close();
	        rs.close();
    		return true;
    	}
    	sql = null;
        ps.close();
        rs.close();
    	return false;    	
    }
    
    
  //TODO Check this out as dependent in Department Id
    public void updateLoginActivity(int userId, int departmentId, String transType)  throws SQLException, Exception {
    	
    	if("LOGOUT".equals(transType)){
    		String sql = "UPDATE loginactivity SET logout = GETDATE() WHERE userId = " + userId + " AND logout IS NULL AND login = (SELECT MAX(a.login) FROM loginactivity a WHERE loginactivity.userId = a.userid)";        	
    		
    		PreparedStatement ps = conn.prepareStatement(sql);
    		    		    		
        	ps.executeUpdate();
        	conn.commit();  	    	
            ps.close();
            
            
    	} else {
    		String sql = "INSERT INTO loginactivity(userId,login,departmentId) VALUES(?,GETDATE(),?)";
    		PreparedStatement ps = conn.prepareStatement(sql);

    		ps.setInt(1, userId);    		
    		ps.setInt(2, departmentId);    		
        	ps.executeUpdate();
        	conn.commit();  	    	
            ps.close();     
    	}
    	
		
		
    }
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}
	
	
	

}
