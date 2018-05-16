package dai.hris.service.filemanager.employeememo;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.EmployeeMemo;

public interface IEmployeeMemoService {
	public EmployeeMemo getEmployeeMemoByEmployeeMemoId(int employeeMemoId) throws SQLException, Exception;
	public List<EmployeeMemo> getEmployeeMemoListByCreatedByEmpId(int createdByEmpId) throws SQLException, Exception;
	
	public boolean add(EmployeeMemo employeeMemo) throws SQLException, Exception;
	public boolean update(EmployeeMemo employeeMemo) throws SQLException, Exception;
	
	public List<EmployeeMemo> getEmployeeMemoListByToRecipientEmpId(int memoRecipientEmpId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeMemo> getEmployeeMemoListByToRecipientEmpIdWithFilter(int memoRecipientEmpId, int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception;
	public int getCountWithFilter(int memoRecipientEmpId, String filter) throws SQLException, Exception;	
	public int getCount(int memoRecipientEmpId) throws SQLException, Exception;
	
	public void delete(int id) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;	
	
}
