package dai.hris.service.filemanager.salarygrade;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.SalaryGrade;

public interface ISalaryGradeService {
	
	public SalaryGrade getSalaryGrade(int salaryGradeId) throws SQLException, Exception;
	public boolean isExist(String name) throws SQLException, Exception;
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception;
	public List<SalaryGrade> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<SalaryGrade> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception;
	public int getCountWithFilter(String filter) throws SQLException, Exception;	
	public int getCount() throws SQLException, Exception;	
	public  void add(SalaryGrade model) throws SQLException, Exception;	
	public  void update(SalaryGrade model) throws SQLException, Exception;
	public void delete(int id) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;	

}
