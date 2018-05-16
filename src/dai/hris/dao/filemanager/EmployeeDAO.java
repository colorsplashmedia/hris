package dai.hris.dao.filemanager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import dai.hris.action.filemanager.util.EmployeeUtil;
import dai.hris.dao.DBConstants;
import dai.hris.model.SystemParameters;
import dai.hris.model.Employee;
import dai.hris.model.SearchEmployee;

public class EmployeeDAO {
	Connection conn = null;

	public EmployeeDAO() {

		try {

			/* MS SQL CODE */
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);
			conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("EmployeeDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("EmployeeDAO :" + e.getMessage());
		}
	}
	
	public SystemParameters getSystemSettings() throws SQLException, Exception {
		String sql = "SELECT * FROM sysAdmin";
		PreparedStatement ps = conn.prepareStatement(sql.toString());

		ResultSet rs = ps.executeQuery();
		
		SystemParameters model = new SystemParameters();
		
		if(rs.next()){	
			
			model.setAdminAssistantId(rs.getInt("adminAssistantId"));
			model.setAdminId(rs.getInt("adminId"));
			model.setHrAdminAssistantId(rs.getInt("hrAssistantHeadId"));
			model.setHrAdminId(rs.getInt("hrHeadId"));
			model.setHrAdminLiasonId(rs.getInt("hrLiasonId"));
			model.setIsAdminChecked(rs.getString("isAdminApprover"));
			model.setMinPay(rs.getBigDecimal("minPay"));
			model.setPartimeHrs(rs.getInt("partimeHrs"));
			model.setRegHrs(rs.getInt("regHrs"));
			model.setContractualBreakHrs(rs.getInt("contractualBreakHrs"));
	    	model.setContractualHrs(rs.getInt("contractualHrs"));
	    	model.setIsNightDiffContractual(rs.getString("isNightDiffContractual"));
			
			if(rs.getInt("adminAssistantId") == 0){
				model.setAdminAssistantName("");
			} else {
				Employee emp1 = getEmployee(rs.getInt("adminAssistantId"));
				if(emp1 != null){
					model.setAdminAssistantName(emp1.getLastname() + " " + emp1.getFirstname());
				} else {
					model.setAdminAssistantName("");
				}				
			}
			
			if(rs.getInt("adminId") == 0){
				model.setAdminName("");
			} else {
				Employee emp1 = getEmployee(rs.getInt("adminId"));
				if(emp1 != null){
					model.setAdminName(emp1.getLastname() + " " + emp1.getFirstname());
				} else {
					model.setAdminName("");
				}				
			}
			
			if(rs.getInt("hrAssistantHeadId") == 0){
				model.setHrAdminAssistantName("");
			} else {
				Employee emp1 = getEmployee(rs.getInt("hrAssistantHeadId"));
				if(emp1 != null){
					model.setHrAdminAssistantName(emp1.getLastname() + " " + emp1.getFirstname());
				} else {
					model.setHrAdminAssistantName("");
				}				
			}
			
			if(rs.getInt("hrHeadId") == 0){
				model.setHrAdminName("");
			} else {
				Employee emp1 = getEmployee(rs.getInt("hrHeadId"));
				if(emp1 != null){
					model.setHrAdminName(emp1.getLastname() + " " + emp1.getFirstname());
				} else {
					model.setHrAdminName("");
				}				
			}
			
			if(rs.getInt("hrLiasonId") == 0){
				model.setHrAdminLiasonName("");
			} else {
				Employee emp1 = getEmployee(rs.getInt("hrLiasonId"));
				if(emp1 != null){
					model.setHrAdminLiasonName(emp1.getLastname() + " " + emp1.getFirstname());
				} else {
					model.setHrAdminLiasonName("");
				}				
			}
			
			
				
		}
		
		return model;
		
	}
	
	public void saveApprovers(SystemParameters model) throws SQLException, Exception {
		String sql = "SELECT * FROM sysAdmin";
		PreparedStatement ps = conn.prepareStatement(sql.toString());

		ResultSet rs = ps.executeQuery();
		
		if(rs.next()){
			//UPDATE
			StringBuffer sql2 = new StringBuffer();
			
			sql2.append("UPDATE sysAdmin set adminId = ");
			sql2.append(model.getAdminId());
			sql2.append(", hrHeadId = ");
			sql2.append(model.getHrAdminId());
			sql2.append(", hrAssistantHeadId = ");	
			sql2.append(model.getHrAdminAssistantId());
			sql2.append(", hrLiasonId = ");	
			sql2.append(model.getHrAdminLiasonId());
			sql2.append(", adminAssistantId = ");	
			sql2.append(model.getAdminAssistantId());			
			sql2.append(", regHrs = ");	
			sql2.append(model.getRegHrs());			
			sql2.append(", partimeHrs = ");	
			sql2.append(model.getPartimeHrs());	
			sql2.append(", contractualHrs = ");	
			sql2.append(model.getContractualHrs());		
			sql2.append(", contractualBreakHrs = ");	
			sql2.append(model.getContractualBreakHrs());		
			sql2.append(", minPay = ");	
			sql2.append(model.getMinPay());	
			sql2.append(", isNightDiffContractual = '");	
			sql2.append(model.getIsNightDiffContractual());
			sql2.append("'");
			sql2.append(", isAdminApprover = '");	
			sql2.append(model.getIsAdminChecked());
			sql2.append("'");
			
			PreparedStatement ps2 = conn.prepareStatement(sql2.toString());
			
			ps2.executeUpdate();
			conn.commit();
			ps2.close();
		} else {			
			//INSERT
			StringBuffer sql2 = new StringBuffer();
			
			sql2.append("INSERT INTO sysAdmin(adminId, hrHeadId, hrAssistantHeadId, hrLiasonId, adminAssistantId, regHrs, partimeHrs, contractualHrs, contractualBreakHrs, minPay, isNightDiffContractual, isAdminApprover) VALUES (");
			sql2.append(model.getAdminId());
			sql2.append(", ");
			sql2.append(model.getHrAdminId());
			sql2.append(", ");	
			sql2.append(model.getHrAdminAssistantId());
			sql2.append(", ");	
			sql2.append(model.getHrAdminLiasonId());
			sql2.append(", ");	
			sql2.append(model.getAdminAssistantId());
			sql2.append(", ");	
			sql2.append(model.getRegHrs());
			sql2.append(", ");	
			sql2.append(model.getPartimeHrs());
			sql2.append(", ");	
			sql2.append(model.getContractualHrs());
			sql2.append(", ");	
			sql2.append(model.getContractualBreakHrs());
			sql2.append(", ");	
			sql2.append(model.getMinPay());
			sql2.append(", '");
			sql2.append(model.getIsNightDiffContractual());
			sql2.append(", '");
			sql2.append(model.getIsAdminChecked());
			sql2.append("')");
			
			PreparedStatement ps2 = conn.prepareStatement(sql2.toString());
			
			ps2.executeUpdate();
			conn.commit();
			ps2.close();
		}
		
		ps.close();
		rs.close();
	}

	
	/**
	 * get all employees from employee table
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<Employee> getAll() throws SQLException, Exception {

		String sql = "SELECT * FROM employee";
		PreparedStatement ps = conn.prepareStatement(sql.toString());

		ResultSet rs = ps.executeQuery();
		List<Employee> list = new ArrayList<Employee>();

		while (rs.next()) {
			Employee emp = new Employee();
			emp.setEmpId(rs.getInt("empId"));
			emp.setEmpNo(rs.getString("empNo")); // required field
			emp.setPlantillaNo(rs.getString("plantillaNo"));
			emp.setLastname(rs.getString("lastname"));
			emp.setFirstname(rs.getString("firstname"));
			emp.setMiddlename(rs.getString("middlename"));
			emp.setDateOfBirth(rs.getString("dateOfBirth"));
			emp.setGender(rs.getString("gender"));
			emp.setCivilStatus(rs.getString("civilStatus"));
			emp.setNationality(rs.getString("nationality"));
			emp.setStreet(rs.getString("street"));
			emp.setCityId(rs.getInt("cityId"));
			emp.setCrn(rs.getString("crn"));
			emp.setEmail(rs.getString("email"));
			emp.setMobileNo(rs.getString("mobileNo"));
			emp.setTelNo(rs.getString("telNo"));
			emp.setBirthPlace(rs.getString("birthPlace"));
			emp.setProvinceId(rs.getInt("provinceId"));
			emp.setZipCode(rs.getString("zipCode"));
			emp.setJobTitleId(rs.getInt("jobTitleId"));
			emp.setSectionId(rs.getInt("sectionId"));
			emp.setUnitId(rs.getInt("unitId"));
			emp.setSubUnitId(rs.getInt("subUnitId"));
			emp.setPersonnelType(rs.getString("personnelType"));
			emp.setEmployeeTypeId(rs.getInt("employeeTypeId"));
			emp.setEmpStatus(rs.getString("empStatus"));
			emp.setEmploymentDate(rs.getString("employmentDate"));
			emp.setEndOfContract(rs.getString("endOfContract"));
			emp.setSss(rs.getString("sss"));
			emp.setGsis(rs.getString("gsis"));
			emp.setHdmf(rs.getString("hdmf"));
			emp.setTin(rs.getString("tin"));
			emp.setPhic(rs.getString("phic"));
			emp.setTaxstatus(rs.getString("taxstatus"));
			emp.setPicLoc(rs.getString("picLoc"));			

			emp.setCountryId(rs.getInt("countryId"));
			list.add(emp);

		}

		ps.close();
		rs.close();
		return list;

	}
	
	public ArrayList<Employee> getEmployeeListBySupervisorId(int id, String filterSection) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT * FROM employee ");
		
		if("SECTION".equals(filterSection)) {
			sql.append(" WHERE sectionId = ");
			sql.append(id);
		} else if ("UNIT".equals(filterSection)) {
			sql.append(" WHERE unitId = ");
			sql.append(id);
		} else if ("SUBUNIT".equals(filterSection)) {
			sql.append("WHERE subunitId = ");
			sql.append(id);
		}		
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		

		ResultSet rs = ps.executeQuery();
		ArrayList<Employee> list = new ArrayList<Employee>();
		

		while (rs.next()) {
			Employee emp = new Employee();
			emp.setEmpId(rs.getInt("empId"));
			emp.setEmpNo(rs.getString("empNo")); // required field
			emp.setPlantillaNo(rs.getString("plantillaNo"));
			emp.setLastname(rs.getString("lastname"));
			emp.setFirstname(rs.getString("firstname"));
			emp.setMiddlename(rs.getString("middlename"));
			emp.setDateOfBirth(rs.getString("dateOfBirth"));
			emp.setGender(rs.getString("gender"));
			emp.setCivilStatus(rs.getString("civilStatus"));
			emp.setNationality(rs.getString("nationality"));
			emp.setStreet(rs.getString("street"));
			emp.setCityId(rs.getInt("cityId"));
			emp.setCrn(rs.getString("crn"));
			emp.setEmail(rs.getString("email"));
			emp.setMobileNo(rs.getString("mobileNo"));
			emp.setTelNo(rs.getString("telNo"));
			emp.setBirthPlace(rs.getString("birthPlace"));
			emp.setProvinceId(rs.getInt("provinceId"));
			emp.setZipCode(rs.getString("zipCode"));
			emp.setJobTitleId(rs.getInt("jobTitleId"));
			emp.setSectionId(rs.getInt("sectionId"));
			emp.setUnitId(rs.getInt("unitId"));
			emp.setSubUnitId(rs.getInt("subUnitId"));
			emp.setPersonnelType(rs.getString("personnelType"));
			emp.setEmployeeTypeId(rs.getInt("employeeTypeId"));
			emp.setEmpStatus(rs.getString("empStatus"));
			emp.setEmploymentDate(rs.getString("employmentDate"));
			emp.setEndOfContract(rs.getString("endOfContract"));
			emp.setSss(rs.getString("sss"));
			emp.setGsis(rs.getString("gsis"));
			emp.setHdmf(rs.getString("hdmf"));
			emp.setTin(rs.getString("tin"));
			emp.setPhic(rs.getString("phic"));
			emp.setTaxstatus(rs.getString("taxstatus"));
			emp.setPicLoc(rs.getString("picLoc"));

			emp.setCountryId(rs.getInt("countryId"));
			list.add(emp);

		}

		ps.close();
		rs.close();
		return list;

	}
	
	/**
	 * 
	 * @param empId
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Employee getEmployee(int empId) throws SQLException, Exception {
		Employee emp = null;
		String sql = "SELECT * FROM employee where empId = ?";
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, empId);

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
			emp.setCrn(rs.getString("crn"));
			emp.setEmail(rs.getString("email"));
			emp.setMobileNo(rs.getString("mobileNo"));
			emp.setTelNo(rs.getString("telNo"));
			emp.setBirthPlace(rs.getString("birthPlace"));
			emp.setProvinceId(rs.getInt("provinceId"));
			emp.setZipCode(rs.getString("zipCode"));
			emp.setJobTitleId(rs.getInt("jobTitleId"));
			emp.setSectionId(rs.getInt("sectionId"));
			emp.setUnitId(rs.getInt("unitId"));
			emp.setSubUnitId(rs.getInt("subUnitId"));
			emp.setPersonnelType(rs.getString("personnelType"));
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
			
			
			String superVisorName = getSupervisorName(rs.getInt("superVisor1Id"));
			
			emp.setSuperVisorName(superVisorName);

		}
	    sql = null;
	    ps.close();
	    rs.close();	 
	    return emp;

	}
	
	public String getSupervisorName(int empId) throws SQLException, Exception {
		
		String sql = "SELECT firstname, lastname FROM employee where empId = ?";
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, empId);

	    ResultSet rs = ps.executeQuery();
	    
	    String superVisorName = "";
	    
	    if (rs.next()) {			
			superVisorName = rs.getString("firstname") + " " + rs.getString("lastname");
		}
	    sql = null;
	    ps.close();
	    rs.close();	 
	    return superVisorName;

	}
	
	public List<SearchEmployee> getAllEmployeeCommon() throws SQLException, Exception {
		List<SearchEmployee> list = new ArrayList<SearchEmployee>();
		StringBuffer sql = new StringBuffer("");
		
		sql.append("SELECT e.empid, e.empNo, e.firstname, e.lastname, ");
		sql.append("s.sectionName, u.unitName FROM employee e, section s, unit u ");
		sql.append("WHERE e.sectionId = s.sectionId ");
		sql.append("AND e.unitId = u.unitId ");			
		
		
		System.out.println("getAllEmployeeCommon SQL: " + sql.toString());
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    while (rs.next()) {   	
	    	SearchEmployee model = new SearchEmployee();
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setEmpNo(rs.getString("empNo"));
	    	model.setLastname(rs.getString("lastname"));
	    	model.setFirstname(rs.getString("firstname"));

	    	model.setSectionName(rs.getString("sectionName"));
	    	model.setUnitName(rs.getString("unitName"));
	    	
			list.add(model);
	    }
		
	    
	    sql = null;
	    ps.close();
	    rs.close();	 
		return list;
	}
	
	public List<SearchEmployee> searchEmployeeCommon(String sectionId, String unitId, String personnelTypeId, String plantillaId) throws SQLException, Exception {
		List<SearchEmployee> list = new ArrayList<SearchEmployee>();
		StringBuffer sql = new StringBuffer("");
		
		sql.append("SELECT e.empid, e.empNo, e.firstname, e.lastname, ");
		sql.append("s.sectionName, u.unitName FROM employee e, section s, unit u ");
		sql.append("WHERE e.sectionId = s.sectionId ");
		sql.append("AND e.unitId = u.unitId ");			
		
		if(sectionId != null && !sectionId.isEmpty()){
			sql.append(" AND s.sectionId = ");
			sql.append(sectionId);
		}
		
		if(unitId != null && !unitId.isEmpty()){
			sql.append(" AND u.unitId = ");
			sql.append(unitId);
		}
		
		if(personnelTypeId != null && !personnelTypeId.isEmpty()){
			sql.append(" AND e.personnelType = '");
			sql.append(personnelTypeId);
			sql.append("'");
		}
		
		if(plantillaId != null && !plantillaId.isEmpty()){
			if("P".equals(plantillaId)){				
				sql.append(" AND (plantillaNo IS NOT NULL AND plantillaNo <> '')");
			} else if("NP".equals(plantillaId)){
				sql.append(" AND (plantillaNo IS NULL OR plantillaNo = '')");
			}			
		}
		
		
		
		System.out.println("searchEmployeeCommon SQL: " + sql.toString());
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    while (rs.next()) {   	
	    	SearchEmployee model = new SearchEmployee();
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setEmpNo(rs.getString("empNo"));
	    	model.setLastname(rs.getString("lastname"));
	    	model.setFirstname(rs.getString("firstname"));

	    	model.setSectionName(rs.getString("sectionName"));
	    	model.setUnitName(rs.getString("unitName"));
	    	
			list.add(model);
	    }
		
	    
	    sql = null;
	    ps.close();
	    rs.close();	 
		return list;
	}
	
	public List<SearchEmployee> selectEmployeeByGroupId(String searchBy, int id) throws SQLException, Exception {
		
		SearchEmployee semp = null;
		List<SearchEmployee> sempList = new ArrayList<>();
		StringBuffer dynSql = new StringBuffer("");
		PreparedStatement ps = null;
		
		//System.out.println("here at else");
		dynSql.append("SELECT e.empid, e.empNo, e.firstname, e.lastname, ");
		dynSql.append("s.sectionName, u.unitName FROM employee e, section s, unit u ");
		dynSql.append("WHERE e.sectionId=s.sectionId ");
		dynSql.append("AND e.unitId = u.unitId ");			
		dynSql.append("AND ");
		dynSql.append(searchBy);
		dynSql.append(" = ");
		dynSql.append(id);
		
		System.out.println("SQL: " + dynSql.toString());
		
		ps = conn.prepareStatement(dynSql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    while (rs.next()) {
			semp = new SearchEmployee();
			semp.setEmpId(rs.getInt("empId"));
			semp.setEmpNo(rs.getString("empNo")); // required field
			semp.setLastname(rs.getString("lastname"));
			semp.setFirstname(rs.getString("firstname"));

			semp.setSectionName(rs.getString("sectionName"));
			semp.setUnitName(rs.getString("unitName"));
			sempList.add(semp);
		}
	    dynSql = null;
	    ps.close();
	    rs.close();	 
	    return sempList;
	}
	
	
	public List<SearchEmployee> selectEmployee(String oSearchText) throws SQLException, Exception {
		SearchEmployee semp = null;
		List<SearchEmployee> sempList = new ArrayList<>();
		StringBuffer dynSql = new StringBuffer("");
		PreparedStatement ps = null;
		if (StringUtils.isEmpty(oSearchText)) {
			//System.out.println("here at if");
			dynSql.append("SELECT e.empid, e.empNo, e.firstname, e.lastname, ");
			dynSql.append("d.sectionName, unitName FROM employee e, section d, unit dv ");
			dynSql.append("WHERE e.sectionId=d.sectionId ");
			dynSql.append(" AND e.unitId = dv.unitId ");
			ps = conn.prepareStatement(dynSql.toString());
		} else{
			//System.out.println("here at else");
			dynSql.append("SELECT e.empid, e.empNo, e.firstname, e.lastname, ");
			dynSql.append("d.sectionName, unitName FROM employee e, section d, unit dv ");
			dynSql.append("WHERE e.sectionId=d.sectionId ");
			dynSql.append("AND e.unitId = dv.unitId ");			
			dynSql.append("AND (lower(e.firstname) like '%");
			dynSql.append(oSearchText.toLowerCase());
			dynSql.append("%' OR lower(e.lastname) like '%");
			dynSql.append(oSearchText.toLowerCase());
			dynSql.append("%' OR lower(e.empNo) like '%");
			dynSql.append(oSearchText.toLowerCase());
			dynSql.append("%')");
			
			System.out.println("SQL: " + dynSql.toString());
			
			ps = conn.prepareStatement(dynSql.toString());
			
			
		}

	    ResultSet rs = ps.executeQuery();
	    
	    while (rs.next()) {
			semp = new SearchEmployee();
			semp.setEmpId(rs.getInt("empId"));
			semp.setEmpNo(rs.getString("empNo")); // required field
			semp.setLastname(rs.getString("lastname"));
			semp.setFirstname(rs.getString("firstname"));

			semp.setSectionName(rs.getString("sectionName"));
			semp.setUnitName(rs.getString("unitName"));
			sempList.add(semp);
		}
	    dynSql = null;
	    ps.close();
	    rs.close();	 
	    return sempList;
	}
	
	
	public int checkExistingUserName(String username) throws SQLException {		
		String sql = "select empId from employee where lower(username) = ?";
		int empIdExisting = 0;
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setString(1, username);
		
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {	    	
	    	empIdExisting = rs.getInt("empId");	    	 
	    }

	    
	    ps.close();
	    rs.close();      
	    return empIdExisting;     
	}
	
	
	public List<Integer> getAllEmpIdsWithEmployeeModuleAccess() throws SQLException {		
		String sql = "select empid from moduleaccess where employee like '%em_employee%'";
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		List<Integer> empListWithEmployeeModuleAccess = new ArrayList<Integer>();
		
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {	    	
	    	empListWithEmployeeModuleAccess.add(rs.getInt("empId"));	    	 
	    }

	    
	    ps.close();
	    rs.close();      
	    return empListWithEmployeeModuleAccess;     
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
	/**
	 * 
	 * @param empId
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Employee getEmployee(String empNo) throws SQLException, Exception {
		Employee emp = null;
		String sql = "SELECT * FROM employee where empNo = ?";
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setString(1, empNo);

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
			emp.setCrn(rs.getString("crn"));
			emp.setEmail(rs.getString("email"));
			emp.setMobileNo(rs.getString("mobileNo"));
			emp.setTelNo(rs.getString("telNo"));
			emp.setBirthPlace(rs.getString("birthPlace"));
			emp.setProvinceId(rs.getInt("provinceId"));
			emp.setZipCode(rs.getString("zipCode"));
			emp.setJobTitleId(rs.getInt("jobTitleId"));
			emp.setSectionId(rs.getInt("sectionId"));
			emp.setUnitId(rs.getInt("unitId"));
			emp.setSubUnitId(rs.getInt("subUnitId"));
			emp.setPersonnelType(rs.getString("personnelType"));
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

		}
	    sql = null;
	    ps.close();
	    rs.close();	 
	    return emp;

	}
	
	public HashMap<String, Object> getEmployeeSupervisors(int empId) throws SQLException, Exception {
	    
	    StringBuffer dynSql = new StringBuffer("");
	    dynSql.append("SELECT e1.empId AS employeeID, e1.superVisor1Id AS employeeSV1, e1.superVisor2Id AS employeeSV2, e1.superVisor3Id AS employeeSV3, ");
	    dynSql.append(" e2.empId as employeeSVID, e2.firstname AS SVFName, e2.lastname SVLName ");
	    dynSql.append(" FROM employee e1, employee e2 ");
	    dynSql.append(" WHERE "); 
	    dynSql.append("  ((e1.superVisor1Id = e2.empId) OR (e1.superVisor2Id = e2.empId) OR (e1.superVisor3Id = e2.empId)) ");
	    //dynSql.append("  AND ((e1.superVisor1Id = ?) OR (e1.superVisor2Id = ?) OR (e1.superVisor3Id = ?)) ");
	    dynSql.append("  AND  e1.empId = ?");
	    
	    PreparedStatement ps2 = conn.prepareStatement(dynSql.toString());
	    ps2.setInt(1, empId);
	    ResultSet rs2 = ps2.executeQuery();
	    HashMap<String, Object> map = new HashMap<String, Object>();
	    int tmpSuperVisor1Id;
	    int tmpSuperVisor2Id;
	    int tmpSuperVisor3Id;
	    int tmpEmployeeSVId;
		while (rs2.next()) {
			tmpSuperVisor1Id = rs2.getInt("employeeSV1");
			tmpSuperVisor2Id = rs2.getInt("employeeSV2");
			tmpSuperVisor3Id = rs2.getInt("employeeSV3");
			tmpEmployeeSVId = rs2.getInt("employeeSVID");
			if (tmpEmployeeSVId == tmpSuperVisor1Id) {
				map.put("supervisor1Id", tmpSuperVisor1Id);
				map.put("supervisor1FN", rs2.getString("SVFName") + " " + rs2.getString("SVLName"));
			}
			if (tmpEmployeeSVId == tmpSuperVisor2Id) {
				map.put("supervisor2Id", tmpSuperVisor2Id);
				map.put("supervisor2FN", rs2.getString("SVFName") + " " + rs2.getString("SVLName"));
			}
			if (tmpEmployeeSVId == tmpSuperVisor3Id) {
				map.put("supervisor3Id", tmpSuperVisor3Id);
				map.put("supervisor3FN", rs2.getString("SVFName") + " " + rs2.getString("SVLName"));
			}

		}

	    dynSql = null;
	    ps2.close();
	    rs2.close();	
	    return map;
	}
	


	/**
	 * 
	 * @param emp
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public int add(Employee emp) throws SQLException, Exception {
		
		int ctr = 1;
		
		String sql = "INSERT INTO employee (empNo, plantillaNo, lastname, firstname, middlename, dateOfBirth, gender, civilStatus, nationality, street, cityId, crn, " +
						"email, mobileNo, telNo, birthPlace, provinceId, zipCode, jobTitleId, sectionId, unitId, employeeTypeId, empStatus, employmentDate, endOfContract, " +
						"sss, gsis, hdmf, tin, phic, taxstatus, picLoc, username, password, createdBy, creationDate, subUnitId, personnelType) "+
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		 
		PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); 
		
		ps.setString(ctr++, emp.getEmpNo());
		ps.setString(ctr++, emp.getPlantillaNo());
		ps.setString(ctr++, emp.getLastname());
		ps.setString(ctr++, emp.getFirstname());
		ps.setString(ctr++, emp.getMiddlename());
		ps.setString(ctr++, emp.getDateOfBirth());
		ps.setString(ctr++, emp.getGender());
		ps.setString(ctr++, emp.getCivilStatus());
		ps.setString(ctr++, emp.getNationality());
		ps.setString(ctr++, emp.getStreet());
		ps.setInt(ctr++,emp.getCityId());
		ps.setString(ctr++,emp.getCrn());
		ps.setString(ctr++, emp.getEmail());
		ps.setString(ctr++, emp.getMobileNo());
		ps.setString(ctr++, emp.getTelNo());
		ps.setString(ctr++, emp.getBirthPlace());
		ps.setInt(ctr++, emp.getProvinceId());
		ps.setString(ctr++, emp.getZipCode());
		ps.setInt(ctr++, emp.getJobTitleId());
		ps.setInt(ctr++, emp.getSectionId());
		ps.setInt(ctr++, emp.getUnitId());
		ps.setInt(ctr++, emp.getEmployeeTypeId());
		ps.setString(ctr++, emp.getEmpStatus());
		ps.setString(ctr++, emp.getEmploymentDate());
		ps.setString(ctr++, emp.getEndOfContract());
		ps.setString(ctr++, emp.getSss());
		ps.setString(ctr++, emp.getGsis());
		ps.setString(ctr++, emp.getHdmf());
		ps.setString(ctr++, emp.getTin());
		ps.setString(ctr++, emp.getPhic());
		ps.setString(ctr++, emp.getTaxstatus());
		ps.setString(ctr++, emp.getPicLoc());
//		ps.setInt(ctr++, emp.getSuperVisor1Id());
//		ps.setInt(ctr++, emp.getSuperVisor2Id());
//		ps.setInt(ctr++, emp.getSuperVisor3Id());
		ps.setString(ctr++, emp.getUsername());		
		ps.setString(ctr++, EmployeeUtil.encodePassword(emp.getPassword()));
		ps.setString(ctr++, emp.getCreatedBy());
		ps.setDate(ctr++, emp.getCreationDate());
		ps.setInt(ctr++, emp.getSubUnitId());
		ps.setString(ctr++, emp.getPersonnelType());
	 
		//39 items
		
		
		ps.executeUpdate();

		 ResultSet keyResultSet = ps.getGeneratedKeys(); 
		 int generatedAutoIncrementId = 0; 
		 	if (keyResultSet.next()) {
		 		generatedAutoIncrementId = (int) keyResultSet.getInt(1);
		 		emp.setEmpId(generatedAutoIncrementId);
		 		conn.commit(); 
		 	}
		 
		 ps.close();
		 keyResultSet.close();
		 
		 if(emp.getMonthlyRate().compareTo(BigDecimal.ZERO) == 1){
			 String sql2 = "INSERT INTO empPayrollInfo (empId, monthlyRate, dailyRate, hourlyRate, shiftingScheduleId, foodAllowance, "
						+ "	cola, taxShield, transAllowance, payrollType, ban, bankNameBan, gsisEmployeeShare, gsisEmployerShare, pagibigEmployeeShare, pagibigEmployerShare, "
						+ " philhealthEmployeeShare, philhealthEmployerShare, hasNightDifferential, hasHolidayPay) VALUES (?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps2  = conn.prepareStatement(sql2);
			    
				int index = 1;
				
				ps2.setInt(index++, emp.getEmpId());
				ps2.setBigDecimal(index++, emp.getMonthlyRate());
				ps2.setBigDecimal(index++, emp.getDailyRate());
				ps2.setBigDecimal(index++, emp.getHourlyRate());
			    ps2.setInt(index++, emp.getShiftingScheduleId());
			    ps2.setBigDecimal(index++, emp.getFoodAllowance());	    
			    ps2.setBigDecimal(index++, emp.getCola());
			    ps2.setBigDecimal(index++, emp.getTaxShield());
			    ps2.setBigDecimal(index++, emp.getTransAllowance());	    
			    ps2.setString(index++, emp.getPayrollType());
			    ps2.setString(index++, emp.getBan());
			    ps2.setString(index++, emp.getBankNameBan());
			    ps2.setBigDecimal(index++, emp.getGsisEmployeeShare());
				ps2.setBigDecimal(index++, emp.getGsisEmployerShare());
				ps2.setBigDecimal(index++, emp.getPagibigEmployeeShare());
				ps2.setBigDecimal(index++, emp.getPagibigEmployerShare());
				ps2.setBigDecimal(index++, emp.getPhilhealthEmployeeShare());
				ps2.setBigDecimal(index++, emp.getPhilhealthEmployerShare());
				ps2.setString(index++, emp.getHasNightDifferential());
			    ps2.setString(index++, emp.getHasHolidayPay());
			    
				ps2.executeUpdate();
		        
		        conn.commit();
		               
		        ps.close();
					
			    
		 }
	 
		 
		 return generatedAutoIncrementId;

	}
	
	public boolean updatePassword(String empId, String password) throws SQLException, Exception {		
		StringBuffer sql = new StringBuffer();
		
		password = EmployeeUtil.encodePassword(password);
		
		sql.append("UPDATE employee set password = '");
		sql.append(password);
		sql.append("' where empId = '");
		sql.append(empId);
		sql.append("'");		
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		int count = ps.executeUpdate();
		boolean status = false;
		conn.commit();
		ps.close();
		 if (count == 1) {
			 status = true;
		 }		 
		 
		 return status;
	}
	
	public boolean verifyOldPassword(String empId, String password) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
		
		password = EmployeeUtil.encodePassword(password);
		
		
		sql.append("SELECT * FROM employee where empId = ");
		sql.append(empId);
		sql.append(" and password = '");
		sql.append(password);
		sql.append("'");
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		

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
	


	/**
	 * 
	 * @param emp
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public int update(Employee emp) throws SQLException, Exception {		
		String sql = "UPDATE employee set empNo=?, plantillaNo=?, lastname=?, firstname=?, middlename=?, dateOfBirth=?, gender=?, civilStatus=?, nationality=?, street=?, cityId=?, crn=?, " +
						"email=?, mobileNo=?, telNo=?, birthPlace=?, provinceId=?, zipCode=?, jobTitleId=?, sectionId=?, unitId=?, employeeTypeId=?, empStatus=?, employmentDate=?, endOfContract=?, " +
						"sss=?, gsis=?, hdmf=?, tin=?, phic=?, taxstatus=?, picLoc=?, username=?, password=?, createdBy=?, creationDate=?, subUnitId=?, personnelType=? where empId=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		int idx = 1;
		 
		ps.setString(idx++, emp.getEmpNo());
		ps.setString(idx++, emp.getPlantillaNo());
		ps.setString(idx++, emp.getLastname());
		ps.setString(idx++, emp.getFirstname());
		ps.setString(idx++, emp.getMiddlename());
		ps.setString(idx++, emp.getDateOfBirth());
		ps.setString(idx++, emp.getGender());
		ps.setString(idx++, emp.getCivilStatus());
		ps.setString(idx++, emp.getNationality());
		ps.setString(idx++, emp.getStreet());
		ps.setInt(idx++,emp.getCityId());
		ps.setString(idx++,emp.getCrn());
		ps.setString(idx++, emp.getEmail());
		ps.setString(idx++, emp.getMobileNo());
		ps.setString(idx++, emp.getTelNo());
		ps.setString(idx++, emp.getBirthPlace());
		ps.setInt(idx++, emp.getProvinceId());
		ps.setString(idx++, emp.getZipCode());
		ps.setInt(idx++, emp.getJobTitleId());
		ps.setInt(idx++, emp.getSectionId());
		ps.setInt(idx++, emp.getUnitId());
		ps.setInt(idx++, emp.getEmployeeTypeId());
		ps.setString(idx++, emp.getEmpStatus());
		ps.setString(idx++,emp.getEmploymentDate());
		ps.setString(idx++, emp.getEndOfContract());
		ps.setString(idx++, emp.getSss());
		ps.setString(idx++, emp.getGsis());
		ps.setString(idx++, emp.getHdmf());
		ps.setString(idx++, emp.getTin());
		ps.setString(idx++, emp.getPhic());
		ps.setString(idx++, emp.getTaxstatus());
		ps.setString(idx++, emp.getPicLoc());
//		ps.setInt(idx++, emp.getSuperVisor1Id());
//		ps.setInt(idx++, emp.getSuperVisor2Id());
//		ps.setInt(idx++, emp.getSuperVisor3Id());
		ps.setString(idx++, emp.getUsername());
		ps.setString(idx++, EmployeeUtil.encodePassword(emp.getPassword()));
		ps.setString(idx++, emp.getCreatedBy());
		ps.setDate(idx++, emp.getCreationDate());
		
		ps.setInt(idx++, emp.getSubUnitId());
		ps.setString(idx++, emp.getPersonnelType());
		
		ps.setInt(idx++, emp.getEmpId());
		
		int count = ps.executeUpdate();

		conn.commit();
		ps.close();
		
		return count;

	}
	
	
	/**
	 * 
	 * @param emp
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public int updateExcludingPassword(Employee emp) throws SQLException, Exception {		
		String sql = "UPDATE employee set empNo=?, plantillaNo=?, lastname=?, firstname=?, middlename=?, dateOfBirth=?, gender=?, civilStatus=?, nationality=?, street=?, cityId=?, crn=?, " +
						"email=?, mobileNo=?, telNo=?, birthPlace=?, provinceId=?, zipCode=?, jobTitleId=?, sectionId=?, unitId=?, employeeTypeId=?, empStatus=?, employmentDate=?, endOfContract=?, " +
						"sss=?, gsis=?, hdmf=?, tin=?, phic=?, taxstatus=?, picLoc=?, username=?, createdBy=?, creationDate=?, subUnitId=?, personnelType=? WHERE empId=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		 
		int idx = 1;
		
		ps.setString(idx++, emp.getEmpNo());
		ps.setString(idx++, emp.getPlantillaNo());
		ps.setString(idx++, emp.getLastname());
		ps.setString(idx++, emp.getFirstname());
		ps.setString(idx++, emp.getMiddlename());
		ps.setString(idx++, emp.getDateOfBirth());
		ps.setString(idx++, emp.getGender());
		ps.setString(idx++, emp.getCivilStatus());
		ps.setString(idx++, emp.getNationality());
		ps.setString(idx++, emp.getStreet());
		ps.setInt(idx++,emp.getCityId());
		ps.setString(idx++,emp.getCrn());
		ps.setString(idx++, emp.getEmail());
		ps.setString(idx++, emp.getMobileNo());
		ps.setString(idx++, emp.getTelNo());
		ps.setString(idx++, emp.getBirthPlace());
		ps.setInt(idx++, emp.getProvinceId());
		ps.setString(idx++, emp.getZipCode());
		ps.setInt(idx++, emp.getJobTitleId());
		ps.setInt(idx++, emp.getSectionId());
		ps.setInt(idx++, emp.getUnitId());
		ps.setInt(idx++, emp.getEmployeeTypeId());
		ps.setString(idx++, emp.getEmpStatus());
		ps.setString(idx++,emp.getEmploymentDate());
		ps.setString(idx++, emp.getEndOfContract());
		ps.setString(idx++, emp.getSss());
		ps.setString(idx++, emp.getGsis());
		ps.setString(idx++, emp.getHdmf());
		ps.setString(idx++, emp.getTin());
		ps.setString(idx++, emp.getPhic());
		ps.setString(idx++, emp.getTaxstatus());
		ps.setString(idx++, emp.getPicLoc());
//		ps.setInt(idx++, emp.getSuperVisor1Id());
//		ps.setInt(idx++, emp.getSuperVisor2Id());
//		ps.setInt(idx++, emp.getSuperVisor3Id());
		ps.setString(idx++, emp.getUsername());
		ps.setString(idx++, emp.getCreatedBy());
		ps.setDate(idx++, emp.getCreationDate());		
		ps.setInt(idx++, emp.getSubUnitId());
		ps.setString(idx++, emp.getPersonnelType());		
		ps.setInt(idx++, emp.getEmpId());
		
		int count = ps.executeUpdate();

		conn.commit();
		ps.close();
		
		return count;

	}
	

	public void closeConnection() throws SQLException, Exception {
		conn.close();
	}

}
