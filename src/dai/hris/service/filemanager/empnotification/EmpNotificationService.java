package dai.hris.service.filemanager.empnotification;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dai.hris.dao.filemanager.EmpNotificationDAO;
import dai.hris.model.EmployeeNotification;

public class EmpNotificationService implements IEmpNotificationService {

	@Override
	public EmployeeNotification getEmployeeNotificationByEmployeeNotificationId(int employeeNotificationId) throws SQLException, Exception {
		EmpNotificationDAO eMDAO = new EmpNotificationDAO();
		EmployeeNotification empMemo = new EmployeeNotification();
		empMemo = eMDAO.getEmployeeNotificationByEmployeeNotificationId(employeeNotificationId);
		eMDAO.closeConnection();
		return empMemo;
		
	}

	@Override
	public List<EmployeeNotification> getEmployeeNotificationListByCreatedByEmpId(int createdByEmpId) throws SQLException, Exception {
		EmpNotificationDAO eMDAO = new EmpNotificationDAO();
		List<EmployeeNotification> empMemList = new ArrayList<EmployeeNotification>();
		empMemList = eMDAO.getEmployeeNotificationListByCreatedByEmpId(createdByEmpId);
		eMDAO.closeConnection();
		return empMemList;
	}

	@Override
	public List<EmployeeNotification> getEmployeeNotificationListByToRecipientEmpId(int memoRecipientEmpId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		EmpNotificationDAO eMDAO = new EmpNotificationDAO();
		List<EmployeeNotification> empMemList = new ArrayList<EmployeeNotification>();
		empMemList = eMDAO.getEmployeeNotificationListByToRecipientEmpId(memoRecipientEmpId, jtStartIndex, jtPageSize, jtSorting);
		eMDAO.closeConnection();
		return empMemList;
	}
	
	@Override
	public  int getCount(int memoRecipientEmpId) throws SQLException, Exception {
		EmpNotificationDAO dao = new EmpNotificationDAO();
		int count = dao.getCount(memoRecipientEmpId);
		dao.closeConnection();
		return count;
	}

	@Override
	public boolean add(EmployeeNotification employeeNotification) throws SQLException, Exception {
		boolean status;
		EmpNotificationDAO eMDAO = new EmpNotificationDAO();
		status = eMDAO.add(employeeNotification);
		eMDAO.closeConnection();
		return status;
	}

	@Override
	public boolean update(EmployeeNotification employeeNotification) throws SQLException, Exception {
		boolean status;
		EmpNotificationDAO eMDAO = new EmpNotificationDAO();
		status = eMDAO.update(employeeNotification);
		eMDAO.closeConnection();
		return status;
	}
	
	@Override
	public void delete(int id) throws SQLException, Exception {
		EmpNotificationDAO dao = new EmpNotificationDAO();
		dao.delete(id);
		dao.closeConnection();
	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		EmpNotificationDAO dao = new EmpNotificationDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}

}
