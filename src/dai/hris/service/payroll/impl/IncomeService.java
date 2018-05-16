package dai.hris.service.payroll.impl;

import java.sql.SQLException;
import java.util.List;

import dai.hris.dao.payroll.IncomeDAO;
import dai.hris.model.EmployeeIncome;
import dai.hris.model.Income;
import dai.hris.service.payroll.IIncomeService;

public class IncomeService implements IIncomeService {

	@Override
	public boolean isExist(String name) throws SQLException, Exception {
		IncomeDAO dao = new IncomeDAO();
		boolean isExist = dao.isExist(name);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception {
		IncomeDAO dao = new IncomeDAO();
		boolean isExist = dao.isExistUpdate(name, id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public List<Income> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {		
		IncomeDAO dao = new IncomeDAO();
		List<Income> list = dao.getAll(jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<Income> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
		IncomeDAO dao = new IncomeDAO();
		List<Income> list = dao.getAllWithFilter(jtStartIndex, jtPageSize, jtSorting, filter);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
		IncomeDAO dao = new IncomeDAO();
		int count = dao.getCountWithFilter(filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount() throws SQLException, Exception {
		IncomeDAO dao = new IncomeDAO();
		int count = dao.getCount();
		dao.closeConnection();
		return count;
	}
	
	

	@Override
	public Income getIncomeById(int inId) throws SQLException, Exception {
		IncomeDAO dao = new IncomeDAO();
		Income res = dao.getIncomeById(inId);
		dao.closeConnection();
		return res;
	}

	@Override
	public List<Income> getAll() throws SQLException, Exception {
		IncomeDAO dao = new IncomeDAO();
		List<Income> res = dao.getAll();
		dao.closeConnection();
		return res;
	}
	
	
	@Override
	public void add(Income model) throws SQLException, Exception {
		IncomeDAO dao = new IncomeDAO();
		dao.add(model);
		dao.closeConnection();
	}
	
	@Override
	public void update(Income model) throws SQLException, Exception {
		IncomeDAO dao = new IncomeDAO();
		dao.update(model);
		dao.closeConnection();
	}
	
	@Override
	public void delete(int id) throws SQLException, Exception {
		IncomeDAO dao = new IncomeDAO();
		dao.delete(id);
		dao.closeConnection();

	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		IncomeDAO dao = new IncomeDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public void saveEmpIncome(EmployeeIncome model) throws SQLException, Exception {
		IncomeDAO dao = new IncomeDAO();
		dao.saveEmpIncome(model);
		dao.closeConnection();
	}

}
