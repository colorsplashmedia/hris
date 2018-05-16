package dai.hris.service.payroll;

import java.sql.SQLException;
import java.util.List;
import dai.hris.model.LongevityPay;

public interface ILongevityPayService {
	public void add(LongevityPay lp) throws SQLException, Exception;
    public void update(LongevityPay lp) throws SQLException, Exception;
    public void delete(LongevityPay lp) throws SQLException, Exception;
    public int[] batchUpdate(List<LongevityPay> lpList) throws SQLException, Exception;
    public int[] batchInsert(List<LongevityPay> lpList) throws SQLException, Exception;
    public List<LongevityPay> getAllByEmployeeId(int empId) throws SQLException, Exception;
    public List<LongevityPay> getAllByJobTitleId(int jtId) throws SQLException, Exception;
    
    public void saveOrUpdate(LongevityPay model) throws SQLException, Exception;
    public int getCount(int empId) throws SQLException, Exception;
    public List<LongevityPay> getLongevityPayListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
    public List<LongevityPay> getLongevityPayListByDateRange(String month, String year) throws SQLException, Exception;
    
    public void saveLongevityPay(LongevityPay model) throws SQLException, Exception;
}
