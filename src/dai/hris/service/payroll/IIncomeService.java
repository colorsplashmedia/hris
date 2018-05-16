package dai.hris.service.payroll;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.EmployeeIncome;
import dai.hris.model.Income;

public interface IIncomeService {

	public boolean isExist(String name) throws SQLException, Exception;	
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception;
	public Income getIncomeById(int inId) throws SQLException, Exception;
	public List<Income> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<Income> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception;
	public int getCountWithFilter(String filter) throws SQLException, Exception;	
	public int getCount() throws SQLException, Exception;	
	public List<Income> getAll() throws SQLException, Exception;	
	
	public void add(Income model) throws SQLException, Exception;
	public void update(Income model) throws SQLException, Exception;
	public void delete(int id) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;
	public void saveEmpIncome(EmployeeIncome model) throws SQLException, Exception;
}
