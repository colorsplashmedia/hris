package dai.hris.service.overtime;

import java.sql.SQLException;
import java.util.List;
import dai.hris.model.EmployeeOvertime;

public interface IEmployeeOvertimeService {
	public EmployeeOvertime getEmployeeOvertimeByEmpOvertimeId(int empId) throws SQLException, Exception;
	public List<EmployeeOvertime> getEmployeeOvertimeByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public int add(EmployeeOvertime employeeOvertime) throws SQLException, Exception;
	public int update(EmployeeOvertime employeeOvertime) throws SQLException, Exception;
	
	public void approveOvertime(EmployeeOvertime employeeOvertime) throws SQLException, Exception;
	
    public List<EmployeeOvertime> getOvertimeByDateRange(String dateFrom, String dateTo) throws SQLException, Exception;
	
	public  int getCountByEmpId(int empId) throws SQLException, Exception;
//	public  int getCountForSupervisor(int supervisorId) throws SQLException, Exception;
//	public List<EmployeeOvertime> getAllOvertimeForSvApprovalBySuperVisorId(int superVisorId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;

	//NEW
	public  int getAllCount() throws SQLException, Exception;
	public  int getAllCountBySectionId(int sectionId) throws SQLException, Exception;
	public  int getAllCountByUnitId(int unitId) throws SQLException, Exception;
	public  int getAllCountBySubUnitId(int subUnitId) throws SQLException, Exception;
	
	public List<EmployeeOvertime> getAllOvertimeForApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeOvertime> getAllOvertimeForHRApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeOvertime> getAllOvertimeForApprovalBySectionId(int empId, int sectionId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeOvertime> getAllOvertimeForApprovalByUnitId(int empId, int unitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeOvertime> getAllOvertimeForApprovalBySubUnitId(int empId, int subUnitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;


}
