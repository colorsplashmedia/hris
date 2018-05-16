package dai.hris.service.payroll.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dai.hris.dao.payroll.CaseRatePaymentDAO;
import dai.hris.model.CaseRatePayment;
import dai.hris.service.payroll.ICaseRatePaymentService;

public class CaseRatePaymentService implements ICaseRatePaymentService {
	
	@Override
	public void saveOrUpdate(CaseRatePayment model)  throws SQLException, Exception {
		CaseRatePaymentDAO dao = new CaseRatePaymentDAO();
		dao.saveOrUpdate(model);
		dao.closeConnection();
	}

	@Override
	public List<CaseRatePayment> getAllByEmployeeId(int empId)  throws SQLException, Exception {
		CaseRatePaymentDAO dao = new CaseRatePaymentDAO();
		List<CaseRatePayment> resultList = dao.getAllByEmployeeId(empId);
		dao.closeConnection();
		return resultList;
	}

	@Override
	public List<CaseRatePayment> getAllByEmpIdAndOrDateRange(int empId,	Date from, Date to)  throws SQLException, Exception {
		CaseRatePaymentDAO dao = new CaseRatePaymentDAO();
		List<CaseRatePayment> result = dao.getAllByEmpIdAndOrDateRange(empId, from, to);
		dao.closeConnection();
		return result;
	}

	@Override
	public List<CaseRatePayment> getCaseRatePaymentListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		CaseRatePaymentDAO dao = new CaseRatePaymentDAO();
		List<CaseRatePayment> resultList = dao.getCaseRatePaymentListByEmpId(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return resultList;
	}
	
	@Override
	public List<CaseRatePayment> getCaseRateListByDateRange(String month, String year) throws SQLException, Exception {
		CaseRatePaymentDAO dao = new CaseRatePaymentDAO();
		List<CaseRatePayment> resultList = dao.getCaseRateListByDateRange(month, year);
		dao.closeConnection();
		return resultList;
	}
	
	@Override
	public int getCount(int empId) throws SQLException, Exception {
		CaseRatePaymentDAO dao = new CaseRatePaymentDAO();
		int count = dao.getCount(empId);
		dao.closeConnection();
		return count;		
	}
	
	@Override
	public void saveCaseRatePayment(CaseRatePayment model) throws SQLException, Exception {
		CaseRatePaymentDAO dao = new CaseRatePaymentDAO();
		dao.saveCaseRatePayment(model);
		dao.closeConnection();
	}

}
