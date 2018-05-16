package dai.hris.service.loan;

import java.sql.SQLException;
import java.util.List;

import dai.hris.dao.loan.LoanEntryDAO;
import dai.hris.model.LeaveCard;
import dai.hris.model.LoanEntry;

public class LoanEntryService implements ILoanEntryService {

	public LoanEntryService() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public List<LoanEntry> getAllActiveLoanEntry() throws SQLException, Exception {
		LoanEntryDAO dao = new LoanEntryDAO();
		List<LoanEntry> list = dao.getAllActiveLoanEntry();
		dao.closeConnection();
		return list;
	}

	@Override
	public List<LoanEntry> getAllLoanEntryByEmpId(int empId) throws SQLException, Exception {
		LoanEntryDAO dao = new LoanEntryDAO();
		List<LoanEntry> list = dao.getAllLoanEntryByEmpId(empId);
		dao.closeConnection();
		return list;
	}
	
	public void add(LoanEntry loanEntry) throws SQLException, Exception{
		LoanEntryDAO dao = new LoanEntryDAO();
		dao.add(loanEntry);
		dao.closeConnection();
	}
	
	public void update(LoanEntry loanEntry) throws SQLException, Exception{
		LoanEntryDAO dao = new LoanEntryDAO();
		dao.update(loanEntry);
		dao.closeConnection();
	}
	
	@Override
	public int getCount(int empId) throws SQLException, Exception {
		LoanEntryDAO dao = new LoanEntryDAO();
		int count = dao.getCount(empId);
		return count;
		
	}
	
	@Override
	public List<LoanEntry> getLoanEntryListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		LoanEntryDAO dao = new LoanEntryDAO();
		List<LoanEntry> resultList = dao.getLoanEntryListByEmpId(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return resultList;
	}
	
	@Override
	public List<LeaveCard> getAllLeaveCreditEntryByEmpId(int empId) throws SQLException, Exception {
		LoanEntryDAO dao = new LoanEntryDAO();
		List<LeaveCard> resultList = dao.getAllLeaveCreditEntryByEmpId(empId);
		dao.closeConnection();
		return resultList;
	}
	
	@Override
	public  void addLeaveCredit(LeaveCard model) throws SQLException, Exception {
		LoanEntryDAO dao = new LoanEntryDAO();
		dao.addLeaveCredit(model);
		dao.closeConnection();
	}
	
	@Override
	public void updateLeaveCredit(LeaveCard model) throws SQLException, Exception {
		LoanEntryDAO dao = new LoanEntryDAO();
		dao.updateLeaveCredit(model);
		dao.closeConnection();
	}
}
