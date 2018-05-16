package dai.hris.service.overtime;

import java.sql.SQLException;
import java.util.List;
import dai.hris.model.EmployeeUndertime;

public interface IEmployeeUndertimeService {
	public EmployeeUndertime getEmployeeUndertimeByEmpUndertimeId(int empId) throws SQLException, Exception;
	public List<EmployeeUndertime> getEmployeeUndertimeByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public int add(EmployeeUndertime model) throws SQLException, Exception;
	public int update(EmployeeUndertime model) throws SQLException, Exception;	
	public void approveUndertime(EmployeeUndertime model) throws SQLException, Exception;	
    public List<EmployeeUndertime> getUndertimeByDateRange(String dateFrom, String dateTo) throws SQLException, Exception;	
	public  int getCountByEmpId(int empId) throws SQLException, Exception;
//	public  int getCountForSupervisor(int supervisorId) throws SQLException, Exception;
//	public List<EmployeeUndertime> getAllUndertimeForSvApprovalBySuperVisorId(int superVisorId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;

	//NEW
	public  int getAllCount() throws SQLException, Exception;
	public  int getAllCountBySectionId(int sectionId) throws SQLException, Exception;
	public  int getAllCountByUnitId(int unitId) throws SQLException, Exception;
	public  int getAllCountBySubUnitId(int subUnitId) throws SQLException, Exception;
	
	public List<EmployeeUndertime> getAllUndertimeForApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeUndertime> getAllUndertimeForHRApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeUndertime> getAllUndertimeForApprovalBySectionId(int empId, int sectionId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeUndertime> getAllUndertimeForApprovalByUnitId(int empId, int unitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeUndertime> getAllUndertimeForApprovalBySubUnitId(int empId, int subUnitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;


}
