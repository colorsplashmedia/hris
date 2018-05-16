package dai.hris.dao.filemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dai.hris.dao.DBConstants;
import dai.hris.model.EmployeeWorkHistory;
import dai.hris.model.ServiceRecord;

/**
 * 
 * @author danielpadilla
 *
 */
public class EmployeeWorkHistoryDAO {
	Connection conn = null;
	
	public EmployeeWorkHistoryDAO() {
		
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("EmployeeWorkHistoryDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("EmployeeWorkHistoryDAO :" + e.getMessage());
		}
	}
	
	public void saveServiceRecord(ServiceRecord model) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
    	sql.append("INSERT INTO serviceRecord (empId,dateFrom,dateTo,jobTitleId,status,placeOfAssignment,branch,wop,causeRemarks,salary,creationDate,createdBy) ");
    	sql.append("VALUES (");
    	sql.append(model.getEmpId());
    	sql.append(", '");
    	sql.append(model.getDateFrom());
    	sql.append("', '");
    	sql.append(model.getDateTo());
    	sql.append("', ");
    	sql.append(model.getJobTitleId());
    	sql.append(", '");
    	sql.append(model.getStatus());
    	sql.append("', '");
    	sql.append(model.getPlaceOfAssignment());
    	sql.append("', '");
    	sql.append(model.getBranch());
    	sql.append("', '");
    	sql.append(model.getWop());
    	sql.append("', '");
    	sql.append(model.getCauseRemarks());
    	sql.append("', ");
    	sql.append(model.getSalary());
    	sql.append(", SYSDATETIME(),");
    	sql.append(model.getCreatedBy());
    	sql.append(") ");
    	
		
    	PreparedStatement ps = conn.prepareStatement(sql.toString()); 
		
		ps.executeUpdate();
		
		conn.commit();
		 
		ps.close();	
	}	
	
	public List<ServiceRecord> getServiceRecordListByEmpId(int empId) throws SQLException, Exception {
		String sql = "SELECT * FROM serviceRecord where empId = ?";		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, empId);
	
	    ResultSet rs = ps.executeQuery();
	    ArrayList<ServiceRecord> list = new ArrayList<ServiceRecord>();
	    
	    while (rs.next()) {
	    	ServiceRecord model = new ServiceRecord();
	    	model.setServiceRecordId(rs.getInt("serviceRecordId"));
	    	model.setJobTitleId(rs.getInt("jobTitleId"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setDateFrom(rs.getString("dateFrom"));
	    	model.setDateTo(rs.getString("dateTo"));
	    	model.setStatus(rs.getString("status"));
	    	model.setPlaceOfAssignment(rs.getString("placeOfAssignment"));
	    	model.setCauseRemarks(rs.getString("causeRemarks"));
	    	model.setBranch(rs.getString("branch"));
	    	model.setWop(rs.getString("wop"));
	    	model.setSalary(rs.getBigDecimal("salary"));
	    	
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;		
	}
	
	public boolean isExist(String name) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM empWorkHistory WHERE employerName = '");
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
    	
    	
    	sql.append("SELECT * FROM empWorkHistory WHERE employerName = '");
    	sql.append(name);
    	sql.append("' and empWorkHistoryId <> ");  
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
	

	/**
	 * tested 062315 TG
	 * @param empWorkHistoryId
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public EmployeeWorkHistory getEmployeeWorkHistoryByEmpWorkHistoryId(int empWorkHistoryId) throws SQLException, Exception {			    		
		String sql = "SELECT empWorkHistoryId, empId, yrsService, employerName, address, countryId, industry, position, remarks, salary, salaryGrade, stepIncrement FROM empWorkHistory where empWorkHistoryId = ?";
		EmployeeWorkHistory employeeWorkHistory = null;
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, empWorkHistoryId);
		
	    ResultSet rs = ps.executeQuery();
	    
	    while (rs.next()) {
	    	employeeWorkHistory = new EmployeeWorkHistory();
	    	employeeWorkHistory.setAddress(rs.getString("address"));
	    	employeeWorkHistory.setCountryId(rs.getInt("countryId"));
	    	employeeWorkHistory.setEmpId(rs.getInt("empId"));
	    	employeeWorkHistory.setEmployerName(rs.getString("employerName"));
	    	employeeWorkHistory.setEmpWorkHistoryId(rs.getInt("empWorkHistoryId"));
	    	employeeWorkHistory.setIndustry(rs.getString("industry"));
	    	employeeWorkHistory.setPosition(rs.getString("position"));
	    	employeeWorkHistory.setRemarks(rs.getString("remarks"));
	    	employeeWorkHistory.setYrsService(rs.getInt("yrsService"));
	    	employeeWorkHistory.setSalary(rs.getBigDecimal("salary"));
	    	employeeWorkHistory.setSalaryGrade(rs.getString("salaryGrade"));
	    	employeeWorkHistory.setStepIncrement(rs.getString("stepIncrement"));
	    }
	    
	    ps.close();
	    rs.close();      
	    return employeeWorkHistory;		
	}
	

	/**
	 * tested 062315 TG
	 * @param empId
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<EmployeeWorkHistory> getEmployeeWorkHistoryListByEmpId(int empId) throws SQLException, Exception {			    		
		String sql = "SELECT empWorkHistoryId, empId, yrsService, employerName, address, countryId, industry, position, remarks, salary, salaryGrade, stepIncrement FROM empWorkHistory where empId = ?";		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, empId);
	
	    ResultSet rs = ps.executeQuery();
	    ArrayList<EmployeeWorkHistory> list = new ArrayList<EmployeeWorkHistory>();
	    
	    while (rs.next()) {
	    	EmployeeWorkHistory employeeWorkHistory = new EmployeeWorkHistory();
	    	employeeWorkHistory.setAddress(rs.getString("address"));
	    	employeeWorkHistory.setCountryId(rs.getInt("countryId"));
	    	employeeWorkHistory.setEmpId(rs.getInt("empId"));
	    	employeeWorkHistory.setEmployerName(rs.getString("employerName"));
	    	employeeWorkHistory.setEmpWorkHistoryId(rs.getInt("empWorkHistoryId"));
	    	employeeWorkHistory.setIndustry(rs.getString("industry"));
	    	employeeWorkHistory.setPosition(rs.getString("position"));
	    	employeeWorkHistory.setRemarks(rs.getString("remarks"));
	    	employeeWorkHistory.setYrsService(rs.getInt("yrsService"));
	    	employeeWorkHistory.setSalary(rs.getBigDecimal("salary"));
	    	employeeWorkHistory.setSalaryGrade(rs.getString("salaryGrade"));
	    	employeeWorkHistory.setStepIncrement(rs.getString("stepIncrement"));
	    	list.add(employeeWorkHistory);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;		
	}
	    
	/**
	 * tested ok 062315 TG
	 * @param employeeOutOfOffice
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public int add(EmployeeWorkHistory employeeWorkHistory) throws SQLException, Exception {
		String sql = "INSERT INTO empWorkHistory (empId, yrsService, employerName, address, countryId, industry, position, remarks, salary, salaryGrade, stepIncrement) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	    ps.setInt(1, employeeWorkHistory.getEmpId());
	    ps.setInt(2, employeeWorkHistory.getYrsService());
	    ps.setString(3, employeeWorkHistory.getEmployerName());
	    ps.setString(4, employeeWorkHistory.getAddress());
	    ps.setInt(5, employeeWorkHistory.getCountryId());
	    ps.setString(6, employeeWorkHistory.getIndustry());
	    ps.setString(7, employeeWorkHistory.getPosition());
	    ps.setString(8, employeeWorkHistory.getRemarks());
	    ps.setBigDecimal(9, employeeWorkHistory.getSalary());
	    ps.setString(10, employeeWorkHistory.getSalaryGrade());
	    ps.setString(11, employeeWorkHistory.getStepIncrement());
	    
	    int count = ps.executeUpdate();
	    int status = 0;  
	    ResultSet keyResultSet = ps.getGeneratedKeys();
	     int generatedAutoIncrementId = 0;
	     if (keyResultSet.next()) {
	      	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
	      	employeeWorkHistory.setEmpWorkHistoryId(generatedAutoIncrementId);
	      	conn.commit();
	      }
		
	     ps.close();
	     keyResultSet.close();
		 if (count == 1) {
			 status = generatedAutoIncrementId;
		 }		
	    return status;
	}

	    
	public void delete(int id)  throws SQLException, Exception {
		String sql = "DELETE FROM empWorkHistory WHERE empWorkHistoryId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        conn.commit();
        ps.close();
	}
	
	

    public void update(EmployeeWorkHistory employeeWorkHistory) throws SQLException, Exception {		
		String sql = "UPDATE empWorkHistory SET empId=?, yrsService=?, employerName=?, address=?, countryId=?, industry=?, position=?, remarks=?, salary=?, salaryGrade=?, stepIncrement=? WHERE empWorkHistoryId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
	    ps.setInt(1, employeeWorkHistory.getEmpId());
	    ps.setInt(2, employeeWorkHistory.getYrsService());
	    ps.setString(3, employeeWorkHistory.getEmployerName());
	    ps.setString(4, employeeWorkHistory.getAddress());
	    ps.setInt(5, employeeWorkHistory.getCountryId());
	    ps.setString(6, employeeWorkHistory.getIndustry());
	    ps.setString(7, employeeWorkHistory.getPosition());
	    ps.setString(8, employeeWorkHistory.getRemarks());
	    ps.setBigDecimal(9, employeeWorkHistory.getSalary());
	    ps.setString(10, employeeWorkHistory.getSalaryGrade());
	    ps.setString(11, employeeWorkHistory.getStepIncrement());
	    ps.setInt(9, employeeWorkHistory.getEmpWorkHistoryId());
		
	    ps.executeUpdate();
		conn.commit();
		
		ps.close();

 	}
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}
}
