package dai.hris.service.filemanager.employee;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import dai.hris.model.SystemParameters;
import dai.hris.model.Employee;
import dai.hris.model.SearchEmployee;

public interface IEmployeeService {
	public List<Employee> getAll() throws SQLException, Exception;
	public Employee getEmployee(int empId) throws SQLException, Exception;
	public Employee getEmployee(String empNo) throws SQLException, Exception;
	public List<SearchEmployee> selectEmployee(String oSearchText) throws SQLException, Exception;
	public int checkExistingUserName(String username) throws SQLException, Exception;
	public List<Integer> getAllEmpIdsWithEmployeeModuleAccess() throws SQLException, Exception;
	public HashMap<String, Object> getEmployeeSupervisors(int empId) throws SQLException, Exception;
	public int add(Employee employee) throws SQLException, Exception;
	public int update(Employee employee) throws SQLException, Exception;
	public int updateExcludingPassword(Employee employee) throws SQLException, Exception;
	public boolean updatePassword(String username, String password) throws SQLException, Exception;
	public boolean verifyOldPassword(String empId, String password) throws SQLException, Exception;
	
	//New
	public void saveApprovers(SystemParameters model) throws SQLException, Exception;
	public SystemParameters getSystemSettings() throws SQLException, Exception;
	
	public List<SearchEmployee> selectEmployeeByGroupId(String searchBy, int id) throws SQLException, Exception;
	public List<SearchEmployee> searchEmployeeCommon(String sectionId, String unitId, String personnelTypeId, String plantillaId) throws SQLException, Exception;
	public List<SearchEmployee> getAllEmployeeCommon() throws SQLException, Exception;
}
