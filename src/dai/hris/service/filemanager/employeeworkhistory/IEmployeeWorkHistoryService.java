package dai.hris.service.filemanager.employeeworkhistory;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.EmployeeWorkHistory;
import dai.hris.model.ServiceRecord;

/**
 * 
 * @author danielpadilla
 *
 */
public interface IEmployeeWorkHistoryService {
	
	public boolean isExist(String name) throws SQLException, Exception;
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception;
	public EmployeeWorkHistory getEmployeeWorkHistoryByEmpWorkHistoryId(int empWorkHistoryId) throws SQLException, Exception;
	public List<EmployeeWorkHistory> getEmployeeWorkHistoryListByEmpId(int empId) throws SQLException, Exception;
	public void add(EmployeeWorkHistory employeeWorkHistory) throws SQLException, Exception;
	public void update(EmployeeWorkHistory employeeWorkHistory) throws SQLException, Exception;	
	public void delete(int id) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;	
	
	
	
	public List<ServiceRecord> getServiceRecordListByEmpId(int empId) throws SQLException, Exception;
	public void saveServiceRecord(ServiceRecord model) throws SQLException, Exception;
}
