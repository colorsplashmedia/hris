package dai.hris.service.filemanager.employeememo;

import java.sql.SQLException;
import java.util.List;
import dai.hris.dao.filemanager.EmployeeMemoDAO;
import dai.hris.model.EmployeeMemo;

public class EmployeeMemoService implements IEmployeeMemoService {

	@Override
	public EmployeeMemo getEmployeeMemoByEmployeeMemoId(int employeeMemoId)	throws SQLException, Exception {
		EmployeeMemoDAO eMDAO = new EmployeeMemoDAO();
		EmployeeMemo empMemo = new EmployeeMemo();
		empMemo = eMDAO.getEmployeeMemoByEmployeeMemoId(employeeMemoId);
		eMDAO.closeConnection();
		return empMemo;
		
	}

	@Override
	public List<EmployeeMemo> getEmployeeMemoListByCreatedByEmpId(int createdByEmpId) throws SQLException, Exception {
		EmployeeMemoDAO eMDAO = new EmployeeMemoDAO();
		List<EmployeeMemo> empMemList = eMDAO.getEmployeeMemoListByCreatedByEmpId(createdByEmpId);
		eMDAO.closeConnection();
		return empMemList;
	}

	@Override
	public List<EmployeeMemo> getEmployeeMemoListByToRecipientEmpId(int memoRecipientEmpId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		EmployeeMemoDAO eMDAO = new EmployeeMemoDAO();
		List<EmployeeMemo> empMemList = eMDAO.getEmployeeMemoListByToRecipientEmpId(memoRecipientEmpId, jtStartIndex, jtPageSize, jtSorting);		
		eMDAO.closeConnection();
		return empMemList;
	}
	
	@Override
	public List<EmployeeMemo> getEmployeeMemoListByToRecipientEmpIdWithFilter(int memoRecipientEmpId, int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
		EmployeeMemoDAO eMDAO = new EmployeeMemoDAO();
		List<EmployeeMemo> empMemList = eMDAO.getEmployeeMemoListByToRecipientEmpIdWithFilter(memoRecipientEmpId, jtStartIndex, jtPageSize, jtSorting, filter);
		eMDAO.closeConnection();
		return empMemList;
	}
	
	@Override
	public  int getCountWithFilter(int memoRecipientEmpId, String filter) throws SQLException, Exception {
		EmployeeMemoDAO dao = new EmployeeMemoDAO();
		int count = dao.getCountWithFilter(memoRecipientEmpId, filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount(int memoRecipientEmpId) throws SQLException, Exception {
		EmployeeMemoDAO dao = new EmployeeMemoDAO();
		int count = dao.getCount(memoRecipientEmpId);
		dao.closeConnection();
		return count;
	}

	@Override
	public boolean add(EmployeeMemo employeeMemo) throws SQLException, Exception {
		boolean status;
		EmployeeMemoDAO eMDAO = new EmployeeMemoDAO();
		status = eMDAO.add(employeeMemo);
		eMDAO.closeConnection();
		return status;
	}

	@Override
	public boolean update(EmployeeMemo employeeMemo) throws SQLException, Exception {
		boolean status;
		EmployeeMemoDAO eMDAO = new EmployeeMemoDAO();
		status = eMDAO.update(employeeMemo);
		eMDAO.closeConnection();
		return status;
	}
	
	@Override
	public void delete(int id) throws SQLException, Exception {
		EmployeeMemoDAO dao = new EmployeeMemoDAO();
		dao.delete(id);
		dao.closeConnection();
	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		EmployeeMemoDAO dao = new EmployeeMemoDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}

}
