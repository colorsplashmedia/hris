package dai.hris.service.filemanager.employeepayrollinfo;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.Deduction;
import dai.hris.model.EmployeeDeduction;
import dai.hris.model.EmployeeIncome;
import dai.hris.model.Income;

public interface IEmployeeIncomeDeductionService {
	public List<EmployeeDeduction> getEmployeeDeductionByEmpId(int empId) throws SQLException, Exception;	
	public List<EmployeeIncome> getEmployeeIncomeByEmpId(int empId) throws SQLException, Exception;
//	public int addIncome(EmployeeIncome employeeIncome)	throws SQLException, Exception;	
//	public int addDeduction(EmployeeDeduction employeeDeduction) throws SQLException, Exception;	
//	public int updateIncome(EmployeeIncome employeeIncome) throws SQLException, Exception;	
//	public int updateDeduction(EmployeeDeduction employeeDeduction) throws SQLException, Exception;	
}
