package dai.hris.service.filemanager.employee;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import dai.hris.dao.filemanager.EmployeeDAO;
import dai.hris.model.SystemParameters;
import dai.hris.model.Employee;
import dai.hris.model.SearchEmployee;

public class EmployeeService  implements IEmployeeService {

	public List<Employee> getAll() throws SQLException, Exception {
		EmployeeDAO empDAO = new EmployeeDAO();
		List<Employee> empList = empDAO.getAll();
		empDAO.closeConnection();
		return empList;
	}


	public Employee getEmployee(int empId) throws SQLException, Exception {
		EmployeeDAO empDAO = new EmployeeDAO();
		Employee emp = empDAO.getEmployee(empId);
		empDAO.closeConnection();
		return emp;
	}
	
	public Employee getEmployee(String empNo) throws SQLException, Exception {
		EmployeeDAO empDAO = new EmployeeDAO();
		Employee emp = empDAO.getEmployee(empNo);
		empDAO.closeConnection();
		return emp;
	}
	
	public List<SearchEmployee> selectEmployee(String oSearchText) throws SQLException, Exception {
		EmployeeDAO empDAO = new EmployeeDAO();
		List<SearchEmployee> empList = empDAO.selectEmployee(oSearchText);
		empDAO.closeConnection();
		return empList;
	}
	
	public List<SearchEmployee> selectEmployeeByGroupId(String searchBy, int id) throws SQLException, Exception {
		EmployeeDAO empDAO = new EmployeeDAO();
		List<SearchEmployee> empList = empDAO.selectEmployeeByGroupId(searchBy, id);
		empDAO.closeConnection();
		return empList;
	}
	
	public List<SearchEmployee> getAllEmployeeCommon() throws SQLException, Exception {
		EmployeeDAO empDAO = new EmployeeDAO();
		List<SearchEmployee> empList = empDAO.getAllEmployeeCommon();
		empDAO.closeConnection();
		return empList;
	}
	
	public List<SearchEmployee> searchEmployeeCommon(String sectionId, String unitId, String personnelTypeId, String plantillaId) throws SQLException, Exception {
		EmployeeDAO empDAO = new EmployeeDAO();
		List<SearchEmployee> empList = empDAO.searchEmployeeCommon(sectionId, unitId, personnelTypeId, plantillaId);
		empDAO.closeConnection();
		return empList;
	}
	
	public int checkExistingUserName(String username) throws SQLException, Exception {
		EmployeeDAO empDAO = new EmployeeDAO();
		int empIdExisting = empDAO.checkExistingUserName(username);
		empDAO.closeConnection();
		return empIdExisting;		
	}
	
	public List<Integer> getAllEmpIdsWithEmployeeModuleAccess() throws SQLException, Exception {
		EmployeeDAO empDAO = new EmployeeDAO();
		List<Integer> empListWithEmployeeModuleAccess = empDAO.getAllEmpIdsWithEmployeeModuleAccess();
		empDAO.closeConnection();
		return empListWithEmployeeModuleAccess;		
	}
	
	public HashMap<String, Object> getEmployeeSupervisors(int empId) throws SQLException, Exception {
		EmployeeDAO empDAO = new EmployeeDAO();
		HashMap<String, Object> empHM = empDAO.getEmployeeSupervisors(empId);
		empDAO.closeConnection();
		return empHM;
	}


	public int update(Employee employee) throws SQLException, Exception {
		int status;
		EmployeeDAO empDAO = new EmployeeDAO();
		status = empDAO.update(employee);
		empDAO.closeConnection();
		return status;
		
	}
	
	
	public int updateExcludingPassword(Employee employee) throws SQLException, Exception {
		int status;
		EmployeeDAO empDAO = new EmployeeDAO();
		status = empDAO.updateExcludingPassword(employee);
		empDAO.closeConnection();
		return status;
		
	}

	
	public int add(Employee employee) throws SQLException, Exception {
		int empId;
		EmployeeDAO empDAO = new EmployeeDAO();
		empId = empDAO.add(employee);
		empDAO.closeConnection();
		return empId;		
	}
	
	public boolean updatePassword(String username, String password) throws SQLException, Exception{
		boolean status;
		EmployeeDAO empDAO = new EmployeeDAO();
		status = empDAO.updatePassword(username, password);
		empDAO.closeConnection();
		return status;		
	}
	
	public boolean verifyOldPassword(String empId, String password) throws SQLException, Exception {
		boolean status;
		EmployeeDAO empDAO = new EmployeeDAO();
		status = empDAO.verifyOldPassword(empId, password);
		empDAO.closeConnection();
		return status;	
	}
	
	public void saveApprovers(SystemParameters model) throws SQLException, Exception {
		EmployeeDAO empDAO = new EmployeeDAO();
		empDAO.saveApprovers(model);
		empDAO.closeConnection();
	}
	
	public SystemParameters getSystemSettings() throws SQLException, Exception {
		EmployeeDAO empDAO = new EmployeeDAO();
		SystemParameters model = empDAO.getSystemSettings();
		empDAO.closeConnection();
		return model;
	}

}
