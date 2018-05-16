package dai.hris.service.payroll.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dai.hris.dao.payroll.PayrollPeriodDAO;
import dai.hris.dao.payroll.ProfessionalFeeDAO;
import dai.hris.model.PayrollPeriod;
import dai.hris.model.ProfessionalFee;
import dai.hris.service.payroll.IPayrollPeriodService;

public class PayrollPeriodService implements IPayrollPeriodService {

	public PayrollPeriod getLatestPayrollPeriodByEmpId(int empId) throws SQLException {
		PayrollPeriodDAO dao = new PayrollPeriodDAO();
		PayrollPeriod result = dao.getPayrollPeriodById(empId, "");
		dao.closeConnection();
		return result;
	}
	
	@Override
	public List<PayrollPeriod> getAll(int jtStartIndex, int jtPageSize, String jtSorting, String payrollPeriodStatus) throws SQLException, Exception {		
		PayrollPeriodDAO dao = new PayrollPeriodDAO();
		List<PayrollPeriod> list = dao.getAll(jtStartIndex, jtPageSize, jtSorting, payrollPeriodStatus);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<PayrollPeriod> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter, String payrollPeriodStatus) throws SQLException, Exception {
		PayrollPeriodDAO dao = new PayrollPeriodDAO();
		List<PayrollPeriod> list = dao.getAllWithFilter(jtStartIndex, jtPageSize, jtSorting, filter, payrollPeriodStatus);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
		PayrollPeriodDAO dao = new PayrollPeriodDAO();
		int count = dao.getCountWithFilter(filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount() throws SQLException, Exception {
		PayrollPeriodDAO dao = new PayrollPeriodDAO();
		int count = dao.getCount();
		dao.closeConnection();
		return count;
	}
	
	@Override
	public List<PayrollPeriod> getPayrollRegisterByDateRange(String dateFrom, String dateTo) throws SQLException {
		PayrollPeriodDAO dao = new PayrollPeriodDAO();
		List<PayrollPeriod> result = dao.getPayrollRegisterByDateRange(dateFrom, dateTo);
		dao.closeConnection();
		return result;
		
	}
	
	@Override
	public int saveOrUpdate(PayrollPeriod payrollPeriod) throws SQLException {
		PayrollPeriodDAO dao = new PayrollPeriodDAO();
		int res = dao.saveOrUpdate(payrollPeriod);
		dao.closeConnection();
		return res;
	}

	@Override
	public PayrollPeriod getPayrollPeriodById(int id, String payrollPeriodStatus) throws SQLException {
		PayrollPeriodDAO dao = new PayrollPeriodDAO();
		PayrollPeriod result = dao.getPayrollPeriodById(id, payrollPeriodStatus);
		dao.closeConnection();
		return result;
	}

	@Override
	public List<PayrollPeriod> getAll() throws SQLException {
		PayrollPeriodDAO dao = new PayrollPeriodDAO();
		List<PayrollPeriod> list = dao.getAll();
		dao.closeConnection();
		return list;
	}
	
	@Override
	public int[] batchUpdate(List<PayrollPeriod> ppList) throws SQLException {
		PayrollPeriodDAO dao = new PayrollPeriodDAO();
		int[] result = dao.batchUpdate(ppList);
		dao.closeConnection();
		return result;
	}

	@Override
	public int[] batchInsert(List<PayrollPeriod> ppList) throws SQLException {
		PayrollPeriodDAO dao = new PayrollPeriodDAO();
		int[] result = dao.batchInsert(ppList);
		dao.closeConnection();
		return result;
	}

	@Override
	public List<PayrollPeriod> getAll(boolean includeLocked)
			throws SQLException {
		PayrollPeriodDAO dao = new PayrollPeriodDAO();
		ArrayList<PayrollPeriod> list = dao.getAll(includeLocked);
		dao.closeConnection();
		return list;
	}
	
	public ArrayList<PayrollPeriod> getAllGenerated()
			throws SQLException {
		PayrollPeriodDAO dao = new PayrollPeriodDAO();
		ArrayList<PayrollPeriod> list = dao.getAllGenerated();
		dao.closeConnection();
		return list;
	}
	
	

	/*@Override
	public List<PayrollPeriod> getAllByEmployeeId(int employeeId) throws SQLException {
		PayrollPeriodDAO dao = new PayrollPeriodDAO();
		List<PayrollPeriod> result = dao.getAllByEmployeeId(employeeId);
		dao.closeConnection();
		return result;
	}

	@Override
	public List<PayrollPeriod> getAllByJobTitleId(int jtId) throws SQLException {
		PayrollPeriodDAO dao = new PayrollPeriodDAO();
		List<PayrollPeriod> result = dao.getAllByJobTitleId(jtId);
		dao.closeConnection();
		return result;
	}*/

}
