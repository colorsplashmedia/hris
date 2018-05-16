package dai.hris.service.overtime;

import java.sql.SQLException;
import java.util.List;
import dai.hris.dao.overtime.EmployeeOffSetDAO;
import dai.hris.model.EmployeeOffSet;

public class EmployeeOffSetService implements IEmployeeOffSetService {

	@Override
	public EmployeeOffSet getEmployeeOffSetByEmpOffSetId(int empId) throws SQLException, Exception {
		EmployeeOffSetDAO dao = new EmployeeOffSetDAO();
		EmployeeOffSet employeeOvertime = dao.getEmployeeOffSetByEmpOffSetId(empId);
		dao.closeConnection();
		return employeeOvertime;
	}

	@Override
	public List<EmployeeOffSet> getEmployeeOffSetByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting)	throws SQLException, Exception {
		EmployeeOffSetDAO dao = new EmployeeOffSetDAO();
		List<EmployeeOffSet> list = dao.getEmployeeOffSetByEmpId(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountByEmpId(int empId) throws SQLException, Exception {
		EmployeeOffSetDAO dao = new EmployeeOffSetDAO();
		int count = dao.getCountByEmpId(empId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public void approveOffSet(EmployeeOffSet model) throws SQLException, Exception {
		EmployeeOffSetDAO dao = new EmployeeOffSetDAO();
		dao.approveOffSet(model);
		dao.closeConnection();
	}
	
//	@Override
//	public  int getCountForSupervisor(int supervisorId) throws SQLException, Exception {
//		EmployeeOffSetDAO dao = new EmployeeOffSetDAO();
//		int count = dao.getCountForSupervisor(supervisorId);
//		dao.closeConnection();
//		return count;
//	}
	
	@Override
	public List<EmployeeOffSet> getOffSetByDateRange(String dateFrom, String dateTo) throws SQLException, Exception {
		EmployeeOffSetDAO dao = new EmployeeOffSetDAO();
		List<EmployeeOffSet> result = dao.getOffSetByDateRange(dateFrom, dateTo);
		dao.closeConnection();
		return result;
		
	}
	
	@Override
	public int add(EmployeeOffSet employeeOvertime) throws SQLException, Exception {
		EmployeeOffSetDAO dao = new EmployeeOffSetDAO();
		int ctr;
		ctr = dao.add(employeeOvertime);
		dao.closeConnection();
		return ctr;
	}
	
	@Override
	public int update(EmployeeOffSet employeeOvertime) throws SQLException, Exception {
		EmployeeOffSetDAO dao = new EmployeeOffSetDAO();
		int ctr;
		ctr = dao.update(employeeOvertime);
		dao.closeConnection();
		return ctr;
	}


//	@Override
//	public List<EmployeeOffSet> getAllOffSetForSvApprovalBySuperVisorId(int superVisorId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
//		EmployeeOffSetDAO dao = new EmployeeOffSetDAO();
//		List<EmployeeOffSet> list = dao.getAllOffSetForSvApprovalBySuperVisorId(superVisorId, jtStartIndex, jtPageSize, jtSorting);
//		dao.closeConnection();
//		return list;
//	}
	
	//NEW
	@Override
	public  int getAllCount() throws SQLException, Exception {
		EmployeeOffSetDAO dao = new EmployeeOffSetDAO();
		int count = dao.getAllCount();
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getAllCountBySectionId(int sectionId) throws SQLException, Exception {
		EmployeeOffSetDAO dao = new EmployeeOffSetDAO();
		int count = dao.getAllCountBySectionId(sectionId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getAllCountByUnitId(int unitId) throws SQLException, Exception {
		EmployeeOffSetDAO dao = new EmployeeOffSetDAO();
		int count = dao.getAllCountByUnitId(unitId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getAllCountBySubUnitId(int subUnitId) throws SQLException, Exception {
		EmployeeOffSetDAO dao = new EmployeeOffSetDAO();
		int count = dao.getAllCountBySubUnitId(subUnitId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public List<EmployeeOffSet> getAllOffSetForApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception{
		EmployeeOffSetDAO dao = new EmployeeOffSetDAO();
		List<EmployeeOffSet> list = dao.getAllOffSetForApproval(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<EmployeeOffSet> getAllOffSetForHRApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception{
		EmployeeOffSetDAO dao = new EmployeeOffSetDAO();
		List<EmployeeOffSet> list = dao.getAllOffSetForApproval(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<EmployeeOffSet> getAllOffSetForApprovalBySectionId(int empId, int sectionId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		EmployeeOffSetDAO dao = new EmployeeOffSetDAO();
		List<EmployeeOffSet> list = dao.getAllOffSetForApprovalBySectionId(empId, sectionId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<EmployeeOffSet> getAllOffSetForApprovalByUnitId(int empId, int unitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		EmployeeOffSetDAO dao = new EmployeeOffSetDAO();
		List<EmployeeOffSet> list = dao.getAllOffSetForApprovalByUnitId(empId, unitId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	
	@Override
	public List<EmployeeOffSet> getAllOffSetForApprovalBySubUnitId(int empId, int subUnitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		EmployeeOffSetDAO dao = new EmployeeOffSetDAO();
		List<EmployeeOffSet> list = dao.getAllOffSetForApprovalBySubUnitId(empId, subUnitId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	

}
