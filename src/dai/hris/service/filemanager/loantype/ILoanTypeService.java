package dai.hris.service.filemanager.loantype;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.LoanType;

public interface ILoanTypeService {
	
	public boolean isExist(String name) throws SQLException, Exception;
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception;
	public List<LoanType> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<LoanType> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception;
	public int getCountWithFilter(String filter) throws SQLException, Exception;	
	public int getCount() throws SQLException, Exception;	
	public  void add(LoanType LoanType) throws SQLException, Exception;
	
	public  void update(LoanType LoanType) throws SQLException, Exception;
	
	public void delete(int id) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;	
	
	public LoanType getLoanType(int loanTypeId) throws SQLException, Exception;

}
