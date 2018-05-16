package dai.hris.service.loan;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.LeaveCard;
import dai.hris.model.LoanEntry;

public interface ILoanEntryService {
	public List<LoanEntry> getAllActiveLoanEntry() throws SQLException, Exception;
	public List<LoanEntry> getAllLoanEntryByEmpId(int empId) throws SQLException, Exception;
	public  void add(LoanEntry loanEntry) throws SQLException, Exception;
	public  void update(LoanEntry loanEntry) throws SQLException, Exception;
	
	public List<LoanEntry> getLoanEntryListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	 
	public int getCount(int empId) throws SQLException, Exception;
	
	public List<LeaveCard> getAllLeaveCreditEntryByEmpId(int empId) throws SQLException, Exception;
	public  void addLeaveCredit(LeaveCard model) throws SQLException, Exception;
	public void updateLeaveCredit(LeaveCard model) throws SQLException, Exception;
}
