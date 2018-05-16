package dai.hris.service.overtime;

import java.sql.SQLException;
import java.util.List;
import dai.hris.dao.overtime.EmployeeUndertimeDAO;
import dai.hris.model.EmployeeUndertime;

public class EmployeeUndertimeService implements IEmployeeUndertimeService {

	@Override
	public EmployeeUndertime getEmployeeUndertimeByEmpUndertimeId(int empId) throws SQLException, Exception {
		EmployeeUndertimeDAO dao = new EmployeeUndertimeDAO();
		EmployeeUndertime employeeOvertime = dao.getEmployeeUndertimeByEmpUndertimeId(empId);
		dao.closeConnection();
		return employeeOvertime;
	}

	@Override
	public List<EmployeeUndertime> getEmployeeUndertimeByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting)	throws SQLException, Exception {
		EmployeeUndertimeDAO dao = new EmployeeUndertimeDAO();
		List<EmployeeUndertime> list = dao.getEmployeeUndertimeByEmpId(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountByEmpId(int empId) throws SQLException, Exception {
		EmployeeUndertimeDAO dao = new EmployeeUndertimeDAO();
		int count = dao.getCountByEmpId(empId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public void approveUndertime(EmployeeUndertime model) throws SQLException, Exception {
		EmployeeUndertimeDAO dao = new EmployeeUndertimeDAO();
		dao.approveUndertime(model);
		dao.closeConnection();
	}
	
//	@Override
//	public  int getCountForSupervisor(int supervisorId) throws SQLException, Exception {
//		EmployeeUndertimeDAO dao = new EmployeeUndertimeDAO();
//		int count = dao.getCountForSupervisor(supervisorId);
//		dao.closeConnection();
//		return count;
//	}
	
	@Override
	public List<EmployeeUndertime> getUndertimeByDateRange(String dateFrom, String dateTo) throws SQLException, Exception {
		EmployeeUndertimeDAO dao = new EmployeeUndertimeDAO();
		List<EmployeeUndertime> result = dao.getUndertimeByDateRange(dateFrom, dateTo);
		dao.closeConnection();
		return result;
		
	}
	
	@Override
	public int add(EmployeeUndertime employeeOvertime) throws SQLException, Exception {
		EmployeeUndertimeDAO dao = new EmployeeUndertimeDAO();
		int ctr;
		ctr = dao.add(employeeOvertime);
		dao.closeConnection();
		return ctr;
	}
	
	@Override
	public int update(EmployeeUndertime employeeOvertime) throws SQLException, Exception {
		EmployeeUndertimeDAO dao = new EmployeeUndertimeDAO();
		int ctr;
		ctr = dao.update(employeeOvertime);
		dao.closeConnection();
		return ctr;
	}


//	@Override
//	public List<EmployeeUndertime> getAllUndertimeForSvApprovalBySuperVisorId(int superVisorId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
//		EmployeeUndertimeDAO dao = new EmployeeUndertimeDAO();
//		List<EmployeeUndertime> list = dao.getAllUndertimeForSvApprovalBySuperVisorId(superVisorId, jtStartIndex, jtPageSize, jtSorting);
//		dao.closeConnection();
//		return list;
//	}
	
	//NEW
	@Override
	public  int getAllCount() throws SQLException, Exception {
		EmployeeUndertimeDAO dao = new EmployeeUndertimeDAO();
		int count = dao.getAllCount();
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getAllCountBySectionId(int sectionId) throws SQLException, Exception {
		EmployeeUndertimeDAO dao = new EmployeeUndertimeDAO();
		int count = dao.getAllCountBySectionId(sectionId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getAllCountByUnitId(int unitId) throws SQLException, Exception {
		EmployeeUndertimeDAO dao = new EmployeeUndertimeDAO();
		int count = dao.getAllCountByUnitId(unitId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getAllCountBySubUnitId(int subUnitId) throws SQLException, Exception {
		EmployeeUndertimeDAO dao = new EmployeeUndertimeDAO();
		int count = dao.getAllCountBySubUnitId(subUnitId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public List<EmployeeUndertime> getAllUndertimeForApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception{
		EmployeeUndertimeDAO dao = new EmployeeUndertimeDAO();
		List<EmployeeUndertime> list = dao.getAllUndertimeForApproval(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<EmployeeUndertime> getAllUndertimeForHRApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception{
		EmployeeUndertimeDAO dao = new EmployeeUndertimeDAO();
		List<EmployeeUndertime> list = dao.getAllUndertimeForApproval(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<EmployeeUndertime> getAllUndertimeForApprovalBySectionId(int empId, int sectionId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		EmployeeUndertimeDAO dao = new EmployeeUndertimeDAO();
		List<EmployeeUndertime> list = dao.getAllUndertimeForApprovalBySectionId(empId, sectionId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<EmployeeUndertime> getAllUndertimeForApprovalByUnitId(int empId, int unitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		EmployeeUndertimeDAO dao = new EmployeeUndertimeDAO();
		List<EmployeeUndertime> list = dao.getAllUndertimeForApprovalByUnitId(empId, unitId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	
	@Override
	public List<EmployeeUndertime> getAllUndertimeForApprovalBySubUnitId(int empId, int subUnitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		EmployeeUndertimeDAO dao = new EmployeeUndertimeDAO();
		List<EmployeeUndertime> list = dao.getAllUndertimeForApprovalBySubUnitId(empId, subUnitId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	

}
