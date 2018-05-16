package dai.hris.service.filemanager.employeeoutofoffice;

import java.sql.SQLException;
import java.util.List;
import dai.hris.dao.filemanager.employeeoutofoffice.EmployeeOutOfOfficeDAO;
import dai.hris.model.EmployeeOutOfOffice;

public class EmployeeOutOfOfficeService implements IEmployeeOutOfOfficeService {

	@Override
	public EmployeeOutOfOffice getEmployeeOutOfOfficeByEmpOOOId(int empOOOId)
			throws SQLException, Exception {
		EmployeeOutOfOffice model = new EmployeeOutOfOffice();
		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();
		model = dao.getEmployeeOutOfOfficeByEmpOOOId(empOOOId);
		dao.closeConnection();
		return model;
	}
	
	@Override
	public void approveOutOffice(EmployeeOutOfOffice employeeOutOfOffice) throws SQLException, Exception {
		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();
		dao.approveOOO(employeeOutOfOffice);
		dao.closeConnection();
	}
	
	@Override
	public  int getCountByEmpId(int empId) throws SQLException, Exception {
		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();
		int count = dao.getCountByEmpId(empId);
		dao.closeConnection();
		return count;
	}
	
//	@Override
//	public  int getCountForSupervisor(int supervisorId) throws SQLException, Exception {
//		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();
//		int count = dao.getCountForSupervisor(supervisorId);
//		dao.closeConnection();
//		return count;
//	}

	@Override
	public List<EmployeeOutOfOffice> getEmployeeOutOfOfficeListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		
		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();		
		List<EmployeeOutOfOffice> list = dao.getEmployeeOutOfOfficeListByEmpId(empId, jtStartIndex, jtPageSize, jtSorting);		
		dao.closeConnection();
		return list;		
	}
	
	@Override
	public List<EmployeeOutOfOffice> getOutOfOfficeByDateRange(String dateFrom, String dateTo) throws SQLException, Exception {
		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();
		List<EmployeeOutOfOffice> result = dao.getOutOfOfficeByDateRange(dateFrom, dateTo);
		dao.closeConnection();
		return result;
		
	}
	
	@Override
	public int add(EmployeeOutOfOffice employeeOutOfOffice) throws SQLException, Exception {
		int status;
		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();
		status = dao.add(employeeOutOfOffice);
		dao.closeConnection();
		return status;
		
	}

	@Override
	public int update(EmployeeOutOfOffice employeeOutOfOffice) throws SQLException, Exception {
		int status;
		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();
		status = dao.update(employeeOutOfOffice);
		dao.closeConnection();
		return status;
	}
	
	@Override
	public int approveOOO(EmployeeOutOfOffice employeeOutOfOffice)	throws SQLException, Exception {
		int status;
		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();
		status = dao.approveOOO(employeeOutOfOffice);
		dao.closeConnection();
		return status;
	}
	
		
//	@Override
//	public List<EmployeeOutOfOffice> getAllEmployeeOOOForSvApprovalBySuperVisorId(int superVisorId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
//		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();
//		List<EmployeeOutOfOffice> list = dao.getAllEmployeeOOOForSvApprovalBySuperVisorId(superVisorId, jtStartIndex, jtPageSize, jtSorting);
//		dao.closeConnection();
//		return list;
//	}
	
	
	//NEW
	@Override
	public  int getAllCount() throws SQLException, Exception {
		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();
		int count = dao.getAllCount();
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getAllCountBySectionId(int sectionId) throws SQLException, Exception {
		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();
		int count = dao.getAllCountBySectionId(sectionId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getAllCountByUnitId(int unitId) throws SQLException, Exception {
		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();
		int count = dao.getAllCountByUnitId(unitId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getAllCountBySubUnitId(int subUnitId) throws SQLException, Exception {
		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();
		int count = dao.getAllCountBySubUnitId(subUnitId);
		dao.closeConnection();
		return count;
	}
	
	
	//NEW
	@Override
	public List<EmployeeOutOfOffice> getAllEmployeeOOOForApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception{
		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();
		List<EmployeeOutOfOffice> list = dao.getAllEmployeeOOOForApproval(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<EmployeeOutOfOffice> getAllEmployeeOOOForHRApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception{
		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();
		List<EmployeeOutOfOffice> list = dao.getAllEmployeeOOOForHRApproval(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<EmployeeOutOfOffice> getAllEmployeeOOOForApprovalBySectionId(int empId, int sectionId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();
		List<EmployeeOutOfOffice> list = dao.getAllEmployeeOOOForApprovalBySectionId(empId, sectionId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<EmployeeOutOfOffice> getAllEmployeeOOOForApprovalByUnitId(int empId, int unitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();
		List<EmployeeOutOfOffice> list = dao.getAllEmployeeOOOForApprovalByUnitId(empId, unitId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<EmployeeOutOfOffice> getAllEmployeeOOOForApprovalBySubUnitId(int empId, int subUnitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		EmployeeOutOfOfficeDAO dao = new EmployeeOutOfOfficeDAO();
		List<EmployeeOutOfOffice> list = dao.getAllEmployeeOOOForApprovalBySubUnitId(empId, subUnitId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	
	
	
	
	
	
	
	
	

}
