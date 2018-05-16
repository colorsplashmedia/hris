package dai.hris.service.filemanager.employeeworkhistory;

import java.sql.SQLException;
import java.util.List;

import dai.hris.dao.filemanager.EmployeeWorkHistoryDAO;
import dai.hris.model.EmployeeWorkHistory;
import dai.hris.model.ServiceRecord;

/**
 * 
 * @author danielpadilla
 *
 */
public class EmployeeWorkHistoryService implements IEmployeeWorkHistoryService  {

	@Override
	public boolean isExist(String name) throws SQLException, Exception {
		EmployeeWorkHistoryDAO dao = new EmployeeWorkHistoryDAO();
		boolean isExist = dao.isExist(name);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception {
		EmployeeWorkHistoryDAO dao = new EmployeeWorkHistoryDAO();
		boolean isExist = dao.isExistUpdate(name, id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public EmployeeWorkHistory getEmployeeWorkHistoryByEmpWorkHistoryId(int empWorkHistoryId) throws SQLException, Exception {
		EmployeeWorkHistory eWH = new EmployeeWorkHistory();
		EmployeeWorkHistoryDAO eWHDAO = new EmployeeWorkHistoryDAO();
		eWH = eWHDAO.getEmployeeWorkHistoryByEmpWorkHistoryId(empWorkHistoryId);
		eWHDAO.closeConnection();
		return eWH;		
	}

	@Override
	public List<EmployeeWorkHistory> getEmployeeWorkHistoryListByEmpId(int empId) throws SQLException, Exception {		
		EmployeeWorkHistoryDAO eWHDAO = new EmployeeWorkHistoryDAO();
		List<EmployeeWorkHistory> eWHList = eWHDAO.getEmployeeWorkHistoryListByEmpId(empId);
		eWHDAO.closeConnection();
		return eWHList;
	}

	@Override
	public void add(EmployeeWorkHistory employeeWorkHistory) throws SQLException, Exception {		
		EmployeeWorkHistoryDAO dao = new EmployeeWorkHistoryDAO();
		dao.add(employeeWorkHistory);
		dao.closeConnection();
	}

	@Override
	public void update(EmployeeWorkHistory employeeWorkHistory) throws SQLException, Exception {		
		EmployeeWorkHistoryDAO dao = new EmployeeWorkHistoryDAO();
		dao.update(employeeWorkHistory);
		dao.closeConnection();
	}
	
	@Override
	public void delete(int id) throws SQLException, Exception {
		EmployeeWorkHistoryDAO dao = new EmployeeWorkHistoryDAO();
		dao.delete(id);
		dao.closeConnection();
	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		EmployeeWorkHistoryDAO dao = new EmployeeWorkHistoryDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public List<ServiceRecord> getServiceRecordListByEmpId(int empId) throws SQLException, Exception {
		EmployeeWorkHistoryDAO dao = new EmployeeWorkHistoryDAO();
		List<ServiceRecord> list = dao.getServiceRecordListByEmpId(empId);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public void saveServiceRecord(ServiceRecord model) throws SQLException, Exception {
		EmployeeWorkHistoryDAO dao = new EmployeeWorkHistoryDAO();
		dao.saveServiceRecord(model);
		dao.closeConnection();
	}

}
