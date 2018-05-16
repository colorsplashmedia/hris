package dai.hris.service.payroll;

import java.sql.SQLException;
import java.util.List;
import dai.hris.model.HazardPay;

public interface IHazardPayService {
	public void saveOrUpdate(HazardPay model) throws SQLException, Exception;
	public void add(HazardPay model) throws SQLException, Exception;
    public void update(HazardPay model) throws SQLException, Exception;
    public void delete(HazardPay model) throws SQLException, Exception;
    public int[] batchUpdate(List<HazardPay> list) throws SQLException, Exception;
    public int[] batchInsert(List<HazardPay> list) throws SQLException, Exception;
    public List<HazardPay> getAllByEmployeeId(int empId) throws SQLException, Exception;
    public List<HazardPay> getAllByJobTitleId(int jtId) throws SQLException, Exception;
    
    public int getCount(int empId) throws SQLException, Exception;
    public List<HazardPay> getHazardPayListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
    public List<HazardPay> getHazardPayListByDateRange(String month, String year) throws SQLException, Exception;
    
    public void saveHazardPay(HazardPay model) throws SQLException, Exception;
}
