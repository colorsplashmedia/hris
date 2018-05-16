package dai.hris.service.filemanager.department;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.Department;

public interface IDepartmentService {
	
	public boolean isExist(String deptName) throws SQLException, Exception;
	public boolean isExistUpdate(String deptName, int id) throws SQLException, Exception;
	public List<Department> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<Department> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception;
	public int getCountWithFilter(String filter) throws SQLException, Exception;	
	public int getCount() throws SQLException, Exception;	
	public  void add(Department department) throws SQLException, Exception;	
	public  void update(Department department) throws SQLException, Exception;
	public void delete(int id) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;	

	public Department getDepartmentById(int id) throws SQLException, Exception;
}
