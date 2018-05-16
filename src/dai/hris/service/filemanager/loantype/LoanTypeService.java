package dai.hris.service.filemanager.loantype;

import java.sql.SQLException;
import java.util.List;

import dai.hris.dao.filemanager.LoanTypeDAO;
import dai.hris.model.LoanType;


public class LoanTypeService implements ILoanTypeService {

	public LoanType getLoanType(int loanTypeId) throws SQLException, Exception {
		LoanTypeDAO dao = new LoanTypeDAO();
		LoanType model = dao.getLoanType(loanTypeId);
		dao.closeConnection();
		return model;
	}
	
	@Override
	public boolean isExist(String name) throws SQLException, Exception {
		LoanTypeDAO dao = new LoanTypeDAO();
		boolean isExist = dao.isExist(name);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception {
		LoanTypeDAO dao = new LoanTypeDAO();
		boolean isExist = dao.isExistUpdate(name, id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public List<LoanType> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {		
		LoanTypeDAO dao = new LoanTypeDAO();
		List<LoanType> list = dao.getAll(jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<LoanType> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
		LoanTypeDAO dao = new LoanTypeDAO();
		List<LoanType> list = dao.getAllWithFilter(jtStartIndex, jtPageSize, jtSorting, filter);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
		LoanTypeDAO dao = new LoanTypeDAO();
		int count = dao.getCountWithFilter(filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount() throws SQLException, Exception {
		LoanTypeDAO dao = new LoanTypeDAO();
		int count = dao.getCount();
		dao.closeConnection();
		return count;
	}

	@Override
	public void add(LoanType loanType) throws SQLException, Exception {
		LoanTypeDAO dao = new LoanTypeDAO();
		dao.add(loanType);
		dao.closeConnection();
	}

	@Override
	public void delete(int id) throws SQLException, Exception {
		LoanTypeDAO dao = new LoanTypeDAO();
		dao.delete(id);
		dao.closeConnection();

	}

	@Override
	public void update(LoanType loanType) throws SQLException, Exception {
		LoanTypeDAO dao = new LoanTypeDAO();
		dao.update(loanType);
		dao.closeConnection();
	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		LoanTypeDAO dao = new LoanTypeDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}

}
