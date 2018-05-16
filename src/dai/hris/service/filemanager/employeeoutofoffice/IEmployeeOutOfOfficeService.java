package dai.hris.service.filemanager.employeeoutofoffice;

import java.sql.SQLException;
import java.util.List;
import dai.hris.model.EmployeeOutOfOffice;

public interface IEmployeeOutOfOfficeService {
	public EmployeeOutOfOffice getEmployeeOutOfOfficeByEmpOOOId(int empId) throws SQLException, Exception;
	public List<EmployeeOutOfOffice> getEmployeeOutOfOfficeListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public int add(EmployeeOutOfOffice employeeOutOfOffice) throws SQLException, Exception;
	public int update(EmployeeOutOfOffice employeeOutOfOffice) throws SQLException, Exception;
	public int approveOOO(EmployeeOutOfOffice employeeOutOfOffice) throws SQLException, Exception;
	
	public void approveOutOffice(EmployeeOutOfOffice employeeOutOfOffice) throws SQLException, Exception;
	
	public List<EmployeeOutOfOffice> getOutOfOfficeByDateRange(String dateFrom, String dateTo) throws SQLException, Exception;
	
	public  int getCountByEmpId(int empId) throws SQLException, Exception;
//	public  int getCountForSupervisor(int supervisorId) throws SQLException, Exception;
//	public List<EmployeeOutOfOffice> getAllEmployeeOOOForSvApprovalBySuperVisorId(int superVisorId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;

	//NEW
	public  int getAllCount() throws SQLException, Exception;
	public  int getAllCountBySectionId(int sectionId) throws SQLException, Exception;
	public  int getAllCountByUnitId(int unitId) throws SQLException, Exception;
	public  int getAllCountBySubUnitId(int subUnitId) throws SQLException, Exception;
	
	
	public List<EmployeeOutOfOffice> getAllEmployeeOOOForApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeOutOfOffice> getAllEmployeeOOOForHRApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeOutOfOffice> getAllEmployeeOOOForApprovalBySectionId(int empId, int sectionId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeOutOfOffice> getAllEmployeeOOOForApprovalByUnitId(int empId, int unitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeOutOfOffice> getAllEmployeeOOOForApprovalBySubUnitId(int empId, int subUnitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;

}
