package dai.hris.service.payroll.impl;

import java.sql.SQLException;
import java.util.List;

import dai.hris.dao.payroll.LongevityPayDAO;
import dai.hris.model.LongevityPay;
import dai.hris.service.payroll.ILongevityPayService;

public class LongevityPayService implements ILongevityPayService {

	@Override
	public void saveOrUpdate(LongevityPay model) throws SQLException, Exception {
		LongevityPayDAO dao = new LongevityPayDAO();
		dao.saveOrUpdate(model);
		dao.closeConnection();
	}
	
	@Override
	public void add(LongevityPay lp) throws SQLException, Exception {
		LongevityPayDAO dao = new LongevityPayDAO();
		dao.add(lp);
		dao.closeConnection();
	}

	@Override
	public void update(LongevityPay lp) throws SQLException, Exception {
		LongevityPayDAO dao = new LongevityPayDAO();
		dao.update(lp);
		dao.closeConnection();
	}

	@Override
	public void delete(LongevityPay lp) throws SQLException, Exception {
		LongevityPayDAO dao = new LongevityPayDAO();
		dao.delete(lp);
		dao.closeConnection();
	}

	@Override
	public int[] batchUpdate(List<LongevityPay> lpList) throws SQLException, Exception {
		LongevityPayDAO dao = new LongevityPayDAO();
		int[] result = dao.batchUpdate(lpList);
		dao.closeConnection();
		return result;
	}

	@Override
	public int[] batchInsert(List<LongevityPay> lpList) throws SQLException, Exception {
		LongevityPayDAO dao = new LongevityPayDAO();
		int[] result = dao.batchInsert(lpList);
		dao.closeConnection();
		return result;
	}

	@Override
	public List<LongevityPay> getAllByEmployeeId(int empId)	throws SQLException, Exception {
		LongevityPayDAO dao = new LongevityPayDAO();
		List<LongevityPay> result = dao.getAllByEmployeeId(empId);
		dao.closeConnection();
		return result;
	}

	@Override
	public List<LongevityPay> getAllByJobTitleId(int jtId)	throws SQLException, Exception {
		LongevityPayDAO dao = new LongevityPayDAO();
		List<LongevityPay> result = dao.getAllByJobTitleId(jtId);
		dao.closeConnection();
		return result;
	}
	
	@Override
	public int getCount(int empId) throws SQLException, Exception{
		LongevityPayDAO dao = new LongevityPayDAO();
		int result = dao.getCount(empId);
		dao.closeConnection();
		return result;
	}
	
	@Override
    public List<LongevityPay> getLongevityPayListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception{
		LongevityPayDAO dao = new LongevityPayDAO();
		List<LongevityPay> result = dao.getLongevityPayListByEmpId(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return result;
    }
	
	@Override
	public List<LongevityPay> getLongevityPayListByDateRange(String month, String year) throws SQLException, Exception {
		LongevityPayDAO dao = new LongevityPayDAO();
		List<LongevityPay> result = dao.getLongevityPayListByDateRange(month, year);
		dao.closeConnection();
		return result;
	}
	
	@Override
	public void saveLongevityPay(LongevityPay model) throws SQLException, Exception {
		LongevityPayDAO dao = new LongevityPayDAO();
		dao.saveLongevityPay(model);
		dao.closeConnection();
	}
    
}
