package dai.hris.service.overtime;

import java.sql.SQLException;
import java.util.List;
import dai.hris.dao.overtime.EmployeeOvertimeDAO;
import dai.hris.model.EmployeeOvertime;

public class EmployeeOvertimeService implements IEmployeeOvertimeService {

	@Override
	public EmployeeOvertime getEmployeeOvertimeByEmpOvertimeId(int empOTId) throws SQLException, Exception {
		EmployeeOvertimeDAO dao = new EmployeeOvertimeDAO();
		EmployeeOvertime employeeOvertime = dao.getEmployeeOvertimeByEmpOvertimeId(empOTId);
		dao.closeConnection();
		return employeeOvertime;
	}

	@Override
	public List<EmployeeOvertime> getEmployeeOvertimeByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting)	throws SQLException, Exception {
		EmployeeOvertimeDAO dao = new EmployeeOvertimeDAO();
		List<EmployeeOvertime> list = dao.getEmployeeOvertimeByEmpId(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountByEmpId(int empId) throws SQLException, Exception {
		EmployeeOvertimeDAO dao = new EmployeeOvertimeDAO();
		int count = dao.getCountByEmpId(empId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public void approveOvertime(EmployeeOvertime employeeOvertime) throws SQLException, Exception {
		EmployeeOvertimeDAO dao = new EmployeeOvertimeDAO();
		dao.approveOvertime(employeeOvertime);
		dao.closeConnection();
	}
	
//	@Override
//	public  int getCountForSupervisor(int supervisorId) throws SQLException, Exception {
//		EmployeeOvertimeDAO dao = new EmployeeOvertimeDAO();
//		int count = dao.getCountForSupervisor(supervisorId);
//		dao.closeConnection();
//		return count;
//	}
	
	@Override
	public List<EmployeeOvertime> getOvertimeByDateRange(String dateFrom, String dateTo) throws SQLException, Exception {
		EmployeeOvertimeDAO dao = new EmployeeOvertimeDAO();
		List<EmployeeOvertime> result = dao.getOvertimeByDateRange(dateFrom, dateTo);
		dao.closeConnection();
		return result;
		
	}
	
	@Override
	public int add(EmployeeOvertime employeeOvertime) throws SQLException, Exception {
		EmployeeOvertimeDAO dao = new EmployeeOvertimeDAO();
		int ctr;
		ctr = dao.add(employeeOvertime);
		dao.closeConnection();
		return ctr;
	}
	
	@Override
	public int update(EmployeeOvertime employeeOvertime) throws SQLException, Exception {
		EmployeeOvertimeDAO dao = new EmployeeOvertimeDAO();
		int ctr;
		ctr = dao.update(employeeOvertime);
		dao.closeConnection();
		return ctr;
	}


//	@Override
//	public List<EmployeeOvertime> getAllOvertimeForSvApprovalBySuperVisorId(int superVisorId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
//		EmployeeOvertimeDAO dao = new EmployeeOvertimeDAO();
//		List<EmployeeOvertime> list = dao.getAllOvertimeForSvApprovalBySuperVisorId(superVisorId, jtStartIndex, jtPageSize, jtSorting);
//		dao.closeConnection();
//		return list;
//	}
	
	//NEW
	@Override
	public  int getAllCount() throws SQLException, Exception {
		EmployeeOvertimeDAO dao = new EmployeeOvertimeDAO();
		int count = dao.getAllCount();
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getAllCountBySectionId(int sectionId) throws SQLException, Exception {
		EmployeeOvertimeDAO dao = new EmployeeOvertimeDAO();
		int count = dao.getAllCountBySectionId(sectionId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getAllCountByUnitId(int unitId) throws SQLException, Exception {
		EmployeeOvertimeDAO dao = new EmployeeOvertimeDAO();
		int count = dao.getAllCountByUnitId(unitId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getAllCountBySubUnitId(int subUnitId) throws SQLException, Exception {
		EmployeeOvertimeDAO dao = new EmployeeOvertimeDAO();
		int count = dao.getAllCountBySubUnitId(subUnitId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public List<EmployeeOvertime> getAllOvertimeForApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception{
		EmployeeOvertimeDAO dao = new EmployeeOvertimeDAO();
		List<EmployeeOvertime> list = dao.getAllOvertimeForApproval(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<EmployeeOvertime> getAllOvertimeForHRApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception{
		EmployeeOvertimeDAO dao = new EmployeeOvertimeDAO();
		List<EmployeeOvertime> list = dao.getAllOvertimeForApproval(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<EmployeeOvertime> getAllOvertimeForApprovalBySectionId(int empId, int sectionId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		EmployeeOvertimeDAO dao = new EmployeeOvertimeDAO();
		List<EmployeeOvertime> list = dao.getAllOvertimeForApprovalBySectionId(empId, sectionId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<EmployeeOvertime> getAllOvertimeForApprovalByUnitId(int empId, int unitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		EmployeeOvertimeDAO dao = new EmployeeOvertimeDAO();
		List<EmployeeOvertime> list = dao.getAllOvertimeForApprovalByUnitId(empId, unitId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	
	@Override
	public List<EmployeeOvertime> getAllOvertimeForApprovalBySubUnitId(int empId, int subUnitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		EmployeeOvertimeDAO dao = new EmployeeOvertimeDAO();
		List<EmployeeOvertime> list = dao.getAllOvertimeForApprovalBySubUnitId(empId, subUnitId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	

}
