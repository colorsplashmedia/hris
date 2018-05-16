package dai.hris.service.filemanager.leave;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import dai.hris.model.Leave;

public interface ILeaveService {	
	public List<Leave> getAllLeavesByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<Leave> getAllLeavesByEmpIdDateRange(int empId, int jtStartIndex, int jtPageSize, String jtSorting, String dateFrom, String dateTo) throws SQLException, Exception;
	public int getCountForHRApproval() throws SQLException, Exception;	
	public  int getCountByEmpId(int empId) throws SQLException, Exception;
	public  int getCountByEmpIdDateRange(int empId, String dateFrom, String dateTo) throws SQLException, Exception;
//	public  int getCountForSupervisor(int supervisorId) throws SQLException, Exception;
	public int add(Leave leave) throws SQLException, Exception;
	public int update(Leave leave) throws SQLException, Exception;
	
	public void updateLeaveStatus(Leave leave) throws SQLException, Exception;
	public void updateLeaveStatusHR(Leave leave) throws SQLException, Exception;
	
	public int approveLeave(Leave leave) throws SQLException, Exception;
	public int approveLeaveHR(Leave leave) throws SQLException, Exception;
	
//	public List<Leave> getAllLeavesForSvApprovalBySuperVisorId(int superVisorId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
//	public List<Leave> getAllLeavesForHRApproval(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;

	public Map<String, Object> getAllLeavesCountByEmpId(int empId, Date startDate, Date endDate, int status) throws SQLException, Exception;
	public double getAllEarnedLeavesCountByEmpId(int empId, Date endDate, double multiplyingFactor) throws SQLException, Exception;
	
	//NEW
	public  int getAllCount() throws SQLException, Exception;
	public  int getAllCountBySectionId(int sectionId) throws SQLException, Exception;
	public  int getAllCountByUnitId(int unitId) throws SQLException, Exception;
	public  int getAllCountBySubUnitId(int subUnitId) throws SQLException, Exception;
	
	public List<Leave> getAllLeavesForApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<Leave> getAllLeavesForHRApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<Leave> getAllLeavesForApprovalBySectionId(int empId, int sectionId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<Leave> getAllLeavesForApprovalByUnitId(int empId, int unitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<Leave> getAllLeavesForApprovalBySubUnitId(int empId, int subUnitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	
}
