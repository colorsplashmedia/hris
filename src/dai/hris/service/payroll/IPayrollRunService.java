package dai.hris.service.payroll;

import java.sql.SQLException;
import java.util.List;
import dai.hris.model.EmployeePayrollRunExt;

public interface IPayrollRunService {
	public List<EmployeePayrollRunExt> generatePayrollByPayrollPeriod (int payrollPeriodId, String payrollType, int loggedEmpId) throws SQLException, Exception;
	public List<EmployeePayrollRunExt> generatePayrollByPayrollPeriodContractual (int payrollPeriodId, String payrollType, int loggedEmpId) throws SQLException, Exception;
	public boolean lockPayrollByPayrollPeriod (int payrollPeriodId);
	public void updatePayrollPeriodStatusToLocked(int payrollPeriodId, int loggedEmpId) throws SQLException, Exception;	
	public List<EmployeePayrollRunExt> generateNightDiffByPayrollPeriodContractual (int payrollPeriodId, String payrollType, int loggedEmpId) throws SQLException, Exception;
	public List<EmployeePayrollRunExt> generateNightDiffByPayrollPeriod (int payrollPeriodId, String payrollType, int loggedEmpId) throws SQLException, Exception;
}
