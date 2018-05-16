package dai.hris.service.payroll;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.PayrollPeriod;
import dai.hris.model.ProfessionalFee;

public interface IPayrollPeriodService {
    public int saveOrUpdate(PayrollPeriod payrollPeriod) throws SQLException;
    public PayrollPeriod getPayrollPeriodById(int id, String payrollPeriodStatus) throws SQLException;
    public List<PayrollPeriod> getAll() throws SQLException;
    public int[] batchUpdate(List<PayrollPeriod> ppList) throws SQLException;
    public int[] batchInsert(List<PayrollPeriod> ppList) throws SQLException;
    /*public List<PayrollPeriod> getAllByEmployeeId(int employeeId) throws SQLException;
    public List<PayrollPeriod> getAllByJobTitleId(int jtId) throws SQLException;*/
    
    public List<PayrollPeriod> getPayrollRegisterByDateRange(String dateFrom, String dateTo) throws SQLException;
    
	public List<PayrollPeriod> getAll(int jtStartIndex, int jtPageSize, String jtSorting, String payrollPeriodStatus) throws SQLException, Exception;
	public List<PayrollPeriod> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter, String payrollPeriodStatus) throws SQLException, Exception;
	public int getCountWithFilter(String filter) throws SQLException, Exception;
	public int getCount() throws SQLException, Exception;	
	public List<PayrollPeriod> getAll(boolean includeLocked) throws SQLException;
    public List<PayrollPeriod> getAllGenerated() throws SQLException;
    public PayrollPeriod getLatestPayrollPeriodByEmpId(int empId) throws SQLException;
    
}
