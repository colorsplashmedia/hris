package dai.hris.service.filemanager.employeetype;

import java.sql.SQLException;
import java.util.List;
import dai.hris.model.EmployeeType;

public interface IEmployeeTypeService {
	
	public List<EmployeeType> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeType> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception;
	public int getCountWithFilter(String filter) throws SQLException, Exception;	
	public int getCount() throws SQLException, Exception;	
	public  void add(EmployeeType employeeType) throws SQLException, Exception;
	/*public  void delete(EmployeeType employeeType) throws SQLException, Exception;*/
	public  void update(EmployeeType employeeType) throws SQLException, Exception;

}
