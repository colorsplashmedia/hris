package dai.hris.service.payroll.impl;

import java.sql.SQLException;
import java.util.List;

import dai.hris.dao.payroll.DeductionDAO;
import dai.hris.model.Deduction;
import dai.hris.model.EmployeeDeduction;
import dai.hris.service.payroll.IDeductionService;

public class DeductionService implements IDeductionService {

	@Override
	public boolean isExist(String name) throws SQLException, Exception {
		DeductionDAO dao = new DeductionDAO();
		boolean isExist = dao.isExist(name);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception {
		DeductionDAO dao = new DeductionDAO();
		boolean isExist = dao.isExistUpdate(name, id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public List<Deduction> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {		
		DeductionDAO dao = new DeductionDAO();
		List<Deduction> list = dao.getAll(jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<Deduction> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
		DeductionDAO dao = new DeductionDAO();
		List<Deduction> list = dao.getAllWithFilter(jtStartIndex, jtPageSize, jtSorting, filter);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
		DeductionDAO dao = new DeductionDAO();
		int count = dao.getCountWithFilter(filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount() throws SQLException, Exception {
		DeductionDAO dao = new DeductionDAO();
		int count = dao.getCount();
		dao.closeConnection();
		return count;
	}
	
	

	@Override
	public Deduction getDeductionById(int ddId) throws SQLException, Exception {
		DeductionDAO dao = new DeductionDAO();
		Deduction res = dao.getDeductionById(ddId);
		dao.closeConnection();
		return res;
	}

	@Override
	public List<Deduction> getAll() throws SQLException, Exception {
		DeductionDAO dao = new DeductionDAO();
		List<Deduction> res = dao.getAll();
		dao.closeConnection();
		return res;
	}
	
	@Override
	public void add(Deduction model) throws SQLException, Exception {
		DeductionDAO dao = new DeductionDAO();
		dao.add(model);
		dao.closeConnection();
	}
	
	@Override
	public void update(Deduction model) throws SQLException, Exception {
		DeductionDAO dao = new DeductionDAO();
		dao.update(model);
		dao.closeConnection();
	}
	
	@Override
	public void delete(int id) throws SQLException, Exception {
		DeductionDAO dao = new DeductionDAO();
		dao.delete(id);
		dao.closeConnection();
	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		DeductionDAO dao = new DeductionDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public void saveEmpDeduction(EmployeeDeduction model) throws SQLException, Exception {
		DeductionDAO dao = new DeductionDAO();
		dao.saveEmpDeduction(model);
		dao.closeConnection();
	}
	
	@Override
	public void savePayrollExclusion(int empId, int payrollPeriodId, int createdBy) throws SQLException, Exception {
		DeductionDAO dao = new DeductionDAO();
		dao.savePayrollExclusion(empId, payrollPeriodId, createdBy);
		dao.closeConnection();
	}

}
