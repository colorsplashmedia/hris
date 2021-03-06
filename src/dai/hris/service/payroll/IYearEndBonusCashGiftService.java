package dai.hris.service.payroll;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.YearEndBonusCashGift;

public interface IYearEndBonusCashGiftService {
    public void add(YearEndBonusCashGift bonus) throws SQLException, Exception;
    public void update(YearEndBonusCashGift bonus) throws SQLException, Exception;
    public void delete(YearEndBonusCashGift bonus) throws SQLException, Exception;
    public int[] batchUpdate(List<YearEndBonusCashGift> yebList) throws SQLException, Exception;
    public int[] batchInsert(List<YearEndBonusCashGift> yebList) throws SQLException, Exception;
    public List<YearEndBonusCashGift> getAllByEmployeeId(int empId) throws SQLException, Exception;
    public List<YearEndBonusCashGift> getAllByJobTitleId(int jtId) throws SQLException, Exception;
    
    public void saveOrUpdate(YearEndBonusCashGift model) throws SQLException, Exception;
    public int getCount(int empId) throws SQLException, Exception;
    public List<YearEndBonusCashGift> getYearEndBonusCashGiftListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
    public List<YearEndBonusCashGift> getYearEndBonusCashGiftListByDateRange(String year) throws SQLException, Exception;
    
    public void saveYearEndBonusCashGift(YearEndBonusCashGift model) throws SQLException, Exception;
}
