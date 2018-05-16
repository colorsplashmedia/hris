package dai.hris.service.filemanager.empnotification;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.EmployeeNotification;

public interface IEmpNotificationService {
	public EmployeeNotification getEmployeeNotificationByEmployeeNotificationId(int empNotificationId) throws SQLException, Exception;
	public List<EmployeeNotification> getEmployeeNotificationListByCreatedByEmpId(int createdByEmpId) throws SQLException, Exception;
	public List<EmployeeNotification> getEmployeeNotificationListByToRecipientEmpId(int memoRecipientEmpId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public boolean add(EmployeeNotification employeeNotification) throws SQLException, Exception;
	public boolean update(EmployeeNotification employeeNotification) throws SQLException, Exception;
	public int getCount(int memoRecipientEmpId) throws SQLException, Exception;
	
	public void delete(int id) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;	
}
