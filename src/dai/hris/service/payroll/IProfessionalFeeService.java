package dai.hris.service.payroll;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import dai.hris.model.ProfessionalFee;

public interface IProfessionalFeeService {
    public void saveOrUpdate(ProfessionalFee pf) throws SQLException;
    public List<ProfessionalFee> getAllByEmployeeId(int empId) throws SQLException;
    public List<ProfessionalFee> getAllByEmpIdAndOrDateRange(int empId, Date from, Date to) throws SQLException;
    public List<ProfessionalFee> getProfessionalFeeListByDateRange(String dateFrom, String dateTo) throws SQLException;
    public List<ProfessionalFee> getProfessionalFeeListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
    
    public int getCount(int empId) throws SQLException, Exception;
    
    public void saveProfessionalFee(ProfessionalFee model) throws SQLException, Exception;
}
