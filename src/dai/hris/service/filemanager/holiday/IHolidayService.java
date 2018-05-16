package dai.hris.service.filemanager.holiday;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.Holiday;

public interface IHolidayService {
	
	public boolean isExist(String name, String date) throws SQLException, Exception;
	public boolean isExistUpdate(String name, int id, String date) throws SQLException, Exception;
	public List<Holiday> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<Holiday> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception;
	public int getCountWithFilter(String filter) throws SQLException, Exception;	
	public int getCount() throws SQLException, Exception;	
	public int add(Holiday holiday) throws SQLException, Exception;
	public int update(Holiday holiday) throws SQLException, Exception;
	public void delete(int id) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;	
	
	public Holiday getHolidayById(int id) throws SQLException, Exception;
}
