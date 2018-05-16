package dai.hris.service.payroll;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dai.hris.model.CaseRatePayment;

public interface ICaseRatePaymentService {
    public void saveOrUpdate(CaseRatePayment crp) throws SQLException, Exception;
    public List<CaseRatePayment> getAllByEmployeeId(int empId) throws SQLException, Exception;
    public List<CaseRatePayment> getAllByEmpIdAndOrDateRange(int empId, Date from, Date to)  throws SQLException, Exception;    
    public List<CaseRatePayment> getCaseRatePaymentListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
    public List<CaseRatePayment> getCaseRateListByDateRange(String month, String year) throws SQLException, Exception;    
    public int getCount(int empId) throws SQLException, Exception;    
    public void saveCaseRatePayment(CaseRatePayment model) throws SQLException, Exception;
}
