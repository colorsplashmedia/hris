package dai.hris.service.filemanager.leavetype;

import java.sql.SQLException;
import java.util.List;
import dai.hris.dao.filemanager.LeaveTypeDAO;
import dai.hris.model.LeaveType;


public class LeaveTypeService implements ILeaveTypeService {

	@Override
	public boolean isExist(String name) throws SQLException, Exception {
		LeaveTypeDAO dao = new LeaveTypeDAO();
		boolean isExist = dao.isExist(name);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception {
		LeaveTypeDAO dao = new LeaveTypeDAO();
		boolean isExist = dao.isExistUpdate(name, id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public List<LeaveType> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {		
		LeaveTypeDAO dao = new LeaveTypeDAO();
		List<LeaveType> list = dao.getAll(jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<LeaveType> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
		LeaveTypeDAO dao = new LeaveTypeDAO();
		List<LeaveType> list = dao.getAllWithFilter(jtStartIndex, jtPageSize, jtSorting, filter);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
		LeaveTypeDAO dao = new LeaveTypeDAO();
		int count = dao.getCountWithFilter(filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount() throws SQLException, Exception {
		LeaveTypeDAO dao = new LeaveTypeDAO();
		int count = dao.getCount();
		dao.closeConnection();
		return count;
	}
	
	
	@Override
	public LeaveType getLeaveTypeByLeaveTypeId(int leaveTypeId) throws SQLException, Exception {

		LeaveTypeDAO dao = new LeaveTypeDAO();
		LeaveType leaveType = dao.getLeaveTypeByLeaveTypeId(leaveTypeId);
		dao.closeConnection();
		return leaveType;
	}
	

	@Override
	public void add(LeaveType leaveType) throws SQLException, Exception {
		LeaveTypeDAO dao = new LeaveTypeDAO();
		dao.add(leaveType);
		dao.closeConnection();
	}

	@Override
	public void delete(int id) throws SQLException, Exception {
		LeaveTypeDAO dao = new LeaveTypeDAO();
		dao.delete(id);
		dao.closeConnection();

	}

	@Override
	public void update(LeaveType leaveType) throws SQLException, Exception {
		LeaveTypeDAO dao = new LeaveTypeDAO();
		dao.update(leaveType);
		dao.closeConnection();
	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		LeaveTypeDAO dao = new LeaveTypeDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}



}
