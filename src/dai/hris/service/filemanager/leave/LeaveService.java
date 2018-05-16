package dai.hris.service.filemanager.leave;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import dai.hris.dao.leave.LeaveDAO;
import dai.hris.model.Leave;


public class LeaveService implements ILeaveService {

	@Override
	public List<Leave> getAllLeavesByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		LeaveDAO dao = new LeaveDAO();
		List<Leave> list = dao.getAllLeavesByEmpId(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<Leave> getAllLeavesByEmpIdDateRange(int empId, int jtStartIndex, int jtPageSize, String jtSorting, String dateFrom, String dateTo) throws SQLException, Exception {
		LeaveDAO dao = new LeaveDAO();
		List<Leave> list = dao.getAllLeavesByEmpIdDateRange(empId, jtStartIndex, jtPageSize, jtSorting, dateFrom, dateTo);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountForHRApproval() throws SQLException, Exception {
		LeaveDAO dao = new LeaveDAO();
		int count = dao.getCountForHRApproval();
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCountByEmpId(int empId) throws SQLException, Exception {
		LeaveDAO dao = new LeaveDAO();
		int count = dao.getCountByEmpId(empId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCountByEmpIdDateRange(int empId, String dateFrom, String dateTo) throws SQLException, Exception {
		LeaveDAO dao = new LeaveDAO();
		int count = dao.getCountByEmpIdDateRange(empId, dateFrom, dateTo);
		dao.closeConnection();
		return count;
	}
	
//	@Override
//	public  int getCountForSupervisor(int supervisorId) throws SQLException, Exception {
//		LeaveDAO dao = new LeaveDAO();
//		int count = dao.getCountForSupervisor(supervisorId);
//		dao.closeConnection();
//		return count;
//	}

	@Override
	public int add(Leave leave) throws SQLException, Exception {
		LeaveDAO dao = new LeaveDAO();
		int ctr;
		ctr = dao.add(leave);
		dao.closeConnection();
		return ctr;
	}
	
	@Override
	public int update(Leave leave) throws SQLException, Exception {
		LeaveDAO dao = new LeaveDAO();
		int ctr;
		ctr = dao.update(leave);
		dao.closeConnection();
		return ctr;
	}
	
	@Override
	public int approveLeave(Leave leave) throws SQLException, Exception {
		LeaveDAO dao = new LeaveDAO();
		int ctr;
		ctr = dao.approveLeave(leave);
		dao.closeConnection();
		return ctr;
	}
	
	@Override
	public void updateLeaveStatus(Leave leave) throws SQLException, Exception{
		LeaveDAO dao = new LeaveDAO();
		dao.updateLeaveStatus(leave);
		dao.closeConnection();
	}
	
	@Override
	public void updateLeaveStatusHR(Leave leave) throws SQLException, Exception{
		LeaveDAO dao = new LeaveDAO();
		dao.updateLeaveStatusHR(leave);
		dao.closeConnection();
	}
	
	@Override
	public int approveLeaveHR(Leave leave) throws SQLException, Exception {
		LeaveDAO dao = new LeaveDAO();
		int ctr;
		ctr = dao.approveLeaveHR(leave);
		dao.closeConnection();
		return ctr;
	}
	
	
//	@Override
//	public List<Leave> getAllLeavesForSvApprovalBySuperVisorId(int superVisorId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
//		LeaveDAO dao = new LeaveDAO();
//		List<Leave> list = dao.getAllLeavesForSvApprovalBySuperVisorId(superVisorId, jtStartIndex, jtPageSize, jtSorting);
//		dao.closeConnection();
//		return list;
//	}
	
	
	
	

	//
	/**
	 * get all leaves based from status (new, approved, rejected) from selected time period
	 * status = 2 (APPROVED)
	 * 
	 * returned leave count is in days
	 */
	public Map<String, Object> getAllLeavesCountByEmpId(int empId, Date startDate, Date endDate, int status) throws SQLException, Exception {
		LeaveDAO ld = new LeaveDAO();
		Map<String, Object> map = ld.getAllLeavesByEmpId(empId, startDate, endDate, status);
		

		return map;		
	}
	
	/**
	 * The the number of leaves banked or incurred by employee
	 * returned leave count is in days
	 */
	
	public double getAllEarnedLeavesCountByEmpId(int empId, Date endDate, double multiplyingFactor) throws SQLException, Exception {
		LeaveDAO ld = new LeaveDAO();
		Double allEarnedLeaves = ld.getAllEarnedLeavesByEmpId(empId, endDate, multiplyingFactor);

		return allEarnedLeaves;		
	}


	//NEW
	@Override
	public  int getAllCount() throws SQLException, Exception {
		LeaveDAO dao = new LeaveDAO();
		int count = dao.getAllCount();
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getAllCountBySectionId(int sectionId) throws SQLException, Exception {
		LeaveDAO dao = new LeaveDAO();
		int count = dao.getAllCountBySectionId(sectionId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getAllCountByUnitId(int unitId) throws SQLException, Exception {
		LeaveDAO dao = new LeaveDAO();
		int count = dao.getAllCountByUnitId(unitId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getAllCountBySubUnitId(int subUnitId) throws SQLException, Exception {
		LeaveDAO dao = new LeaveDAO();
		int count = dao.getAllCountBySubUnitId(subUnitId);
		dao.closeConnection();
		return count;
	}
	
	
	@Override
	public List<Leave> getAllLeavesForApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception{
		LeaveDAO dao = new LeaveDAO();
		List<Leave> list = dao.getAllLeavesForApproval(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<Leave> getAllLeavesForHRApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		LeaveDAO dao = new LeaveDAO();
		List<Leave> list = dao.getAllLeavesForHRApproval(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<Leave> getAllLeavesForApprovalBySectionId(int empId, int sectionId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception{
		LeaveDAO dao = new LeaveDAO();
		List<Leave> list = dao.getAllLeavesForApprovalBySectionId(empId, sectionId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<Leave> getAllLeavesForApprovalByUnitId(int empId, int unitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception{
		LeaveDAO dao = new LeaveDAO();
		List<Leave> list = dao.getAllLeavesForApprovalByUnitId(empId, unitId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<Leave> getAllLeavesForApprovalBySubUnitId(int empId, int subUnitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception{
		LeaveDAO dao = new LeaveDAO();
		List<Leave> list = dao.getAllLeavesForApprovalBySubUnitId(empId, subUnitId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	

}
