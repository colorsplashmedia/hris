package dai.hris.services.shiftingSchedule;

import java.sql.SQLException;
import java.util.List;
import dai.hris.dao.shiftingSchedule.ShiftingScheduleDAO;
import dai.hris.model.ShiftingSchedule;

public class ShiftingScheduleService implements IShiftingScheduleService {

	public ShiftingScheduleService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<ShiftingSchedule> getAll() throws SQLException, Exception {		
		ShiftingScheduleDAO dao = new ShiftingScheduleDAO();
		List<ShiftingSchedule> list = dao.getAll();
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<ShiftingSchedule> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {		
		ShiftingScheduleDAO dao = new ShiftingScheduleDAO();
		List<ShiftingSchedule> list = dao.getAll(jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<ShiftingSchedule> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
		ShiftingScheduleDAO dao = new ShiftingScheduleDAO();
		List<ShiftingSchedule> list = dao.getAllWithFilter(jtStartIndex, jtPageSize, jtSorting, filter);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
		ShiftingScheduleDAO dao = new ShiftingScheduleDAO();
		int count = dao.getCountWithFilter(filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount() throws SQLException, Exception {
		ShiftingScheduleDAO dao = new ShiftingScheduleDAO();
		int count = dao.getCount();
		dao.closeConnection();
		return count;
	}
	
	

	@Override
	public void add(dai.hris.model.ShiftingSchedule shiftingSchedule)
			throws SQLException, Exception {
		ShiftingScheduleDAO dao = new ShiftingScheduleDAO();
		dao.add(shiftingSchedule);
		dao.closeConnection();

	}
	
	@Override
	public void update(dai.hris.model.ShiftingSchedule shiftingSchedule)
			throws SQLException, Exception {
		ShiftingScheduleDAO dao = new ShiftingScheduleDAO();
		dao.update(shiftingSchedule);
		dao.closeConnection();

	}
	
	@Override
	public void delete(int id) throws SQLException, Exception {
		ShiftingScheduleDAO dao = new ShiftingScheduleDAO();
		dao.delete(id);
		dao.closeConnection();
	}	
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		ShiftingScheduleDAO dao = new ShiftingScheduleDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}

}
