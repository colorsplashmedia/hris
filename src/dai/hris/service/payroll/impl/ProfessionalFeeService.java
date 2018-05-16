package dai.hris.service.payroll.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dai.hris.dao.payroll.ProfessionalFeeDAO;
import dai.hris.model.ProfessionalFee;
import dai.hris.service.payroll.IProfessionalFeeService;

public class ProfessionalFeeService implements IProfessionalFeeService {

	@Override
	public void saveOrUpdate(ProfessionalFee pf) throws SQLException {
		ProfessionalFeeDAO dao = new ProfessionalFeeDAO();
		dao.saveOrUpdate(pf);
		dao.closeConnection();
	}

	@Override
	public List<ProfessionalFee> getAllByEmployeeId(int empId) throws SQLException {
		ProfessionalFeeDAO dao = new ProfessionalFeeDAO();
		List<ProfessionalFee> result = dao.getAllByEmployeeId(empId);
		dao.closeConnection();
		return result;
	}

	@Override
	public List<ProfessionalFee> getAllByEmpIdAndOrDateRange(int empId, Date from, Date to)	throws SQLException {
		ProfessionalFeeDAO dao = new ProfessionalFeeDAO();
		List<ProfessionalFee> result = dao.getAllByEmpIdAndOrDateRange(empId, from, to);
		dao.closeConnection();
		return result;
	}
	
	@Override
	public List<ProfessionalFee> getProfessionalFeeListByDateRange(String dateFrom, String dateTo) throws SQLException {
		ProfessionalFeeDAO dao = new ProfessionalFeeDAO();
		List<ProfessionalFee> result = dao.getProfessionalFeeListByDateRange(dateFrom, dateTo);
		dao.closeConnection();
		return result;		
	}

	@Override
	public List<ProfessionalFee> getProfessionalFeeListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		ProfessionalFeeDAO dao = new ProfessionalFeeDAO();
		List<ProfessionalFee> resultList = dao.getProfessionalFeeListByEmpId(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return resultList;
	}
	
	@Override
	public int getCount(int empId) throws SQLException, Exception {
		ProfessionalFeeDAO dao = new ProfessionalFeeDAO();
		int count = dao.getCount(empId);
		dao.closeConnection();
		return count;		
	}
	
	@Override
	public void saveProfessionalFee(ProfessionalFee model) throws SQLException, Exception {
		ProfessionalFeeDAO dao = new ProfessionalFeeDAO();
		dao.saveProfessionalFee(model);
		dao.closeConnection();
	}
}
