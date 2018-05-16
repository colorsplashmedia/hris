package dai.hris.service.overtime;

import java.sql.SQLException;
import java.util.List;
import dai.hris.model.EmployeeOffSet;

public interface IEmployeeOffSetService {
	public EmployeeOffSet getEmployeeOffSetByEmpOffSetId(int empId) throws SQLException, Exception;
	public List<EmployeeOffSet> getEmployeeOffSetByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public int add(EmployeeOffSet model) throws SQLException, Exception;
	public int update(EmployeeOffSet model) throws SQLException, Exception;	
	public void approveOffSet(EmployeeOffSet model) throws SQLException, Exception;	
    public List<EmployeeOffSet> getOffSetByDateRange(String dateFrom, String dateTo) throws SQLException, Exception;	
	public  int getCountByEmpId(int empId) throws SQLException, Exception;
//	public  int getCountForSupervisor(int supervisorId) throws SQLException, Exception;
//	public List<EmployeeOffSet> getAllOffSetForSvApprovalBySuperVisorId(int superVisorId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;

	//NEW
	public  int getAllCount() throws SQLException, Exception;
	public  int getAllCountBySectionId(int sectionId) throws SQLException, Exception;
	public  int getAllCountByUnitId(int unitId) throws SQLException, Exception;
	public  int getAllCountBySubUnitId(int subUnitId) throws SQLException, Exception;
	
	public List<EmployeeOffSet> getAllOffSetForApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeOffSet> getAllOffSetForHRApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeOffSet> getAllOffSetForApprovalBySectionId(int empId, int sectionId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeOffSet> getAllOffSetForApprovalByUnitId(int empId, int unitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeOffSet> getAllOffSetForApprovalBySubUnitId(int empId, int subUnitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;


}
