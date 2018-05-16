package dai.hris.service.payroll;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.Deduction;
import dai.hris.model.EmployeeDeduction;

public interface IDeductionService {

	public boolean isExist(String name) throws SQLException, Exception;
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception;
	public List<Deduction> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<Deduction> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception;
	public int getCountWithFilter(String filter) throws SQLException, Exception;	
	public int getCount() throws SQLException, Exception;		
	public Deduction getDeductionById(int ddId) throws SQLException, Exception;
	public List<Deduction> getAll() throws SQLException, Exception;	
	public void add(Deduction model) throws SQLException, Exception;
	public void update(Deduction model) throws SQLException, Exception;
	public void delete(int id) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;
	public void saveEmpDeduction(EmployeeDeduction model) throws SQLException, Exception;
	
	public void savePayrollExclusion(int empId, int payrollPeriodId, int createdBy) throws SQLException, Exception;
}
