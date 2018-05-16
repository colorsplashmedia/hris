package dai.hris.service.filemanager.holiday;

import java.sql.SQLException;
import java.util.List;

import dai.hris.dao.filemanager.HolidayDAO;
import dai.hris.model.Holiday;


public class HolidayService implements IHolidayService {
	
	@Override
	public Holiday getHolidayById(int id) throws SQLException, Exception {
		HolidayDAO dao = new HolidayDAO();
		Holiday model = dao.getHolidayById(id);
		dao.closeConnection();
		return model;
	}

	@Override
	public boolean isExist(String name, String date) throws SQLException, Exception {
		HolidayDAO dao = new HolidayDAO();
		boolean isExist = dao.isExist(name, date);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public boolean isExistUpdate(String name, int id, String date) throws SQLException, Exception {
		HolidayDAO dao = new HolidayDAO();
		boolean isExist = dao.isExistUpdate(name, id, date);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public List<Holiday> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {		
		HolidayDAO dao = new HolidayDAO();
		List<Holiday> list = dao.getAll(jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<Holiday> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
		HolidayDAO dao = new HolidayDAO();
		List<Holiday> list = dao.getAllWithFilter(jtStartIndex, jtPageSize, jtSorting, filter);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
		HolidayDAO dao = new HolidayDAO();
		int count = dao.getCountWithFilter(filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount() throws SQLException, Exception {
		HolidayDAO dao = new HolidayDAO();
		int count = dao.getCount();
		dao.closeConnection();
		return count;
	}

	@Override
	public int add(Holiday Holiday) throws SQLException, Exception {
		HolidayDAO dao = new HolidayDAO();
		int ctr = dao.add(Holiday);
		dao.closeConnection();		
		return ctr;
	}

	@Override
	public int update(Holiday Holiday) throws SQLException, Exception {
		HolidayDAO dao = new HolidayDAO();
		int ctr = dao.update(Holiday);
		dao.closeConnection();
		return ctr;
	}
	
	@Override
	public void delete(int id) throws SQLException, Exception {
		HolidayDAO dao = new HolidayDAO();
		dao.delete(id);
		dao.closeConnection();
	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		HolidayDAO dao = new HolidayDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}
	
	

}
