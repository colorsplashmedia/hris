package dai.hris.services.shiftingSchedule;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.ShiftingSchedule;

public interface IShiftingScheduleService {

	public List<ShiftingSchedule> getAll() throws SQLException, Exception;
	public List<ShiftingSchedule> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<ShiftingSchedule> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception;
	public int getCountWithFilter(String filter) throws SQLException, Exception;	
	public int getCount() throws SQLException, Exception;	
	public  void add(ShiftingSchedule shiftingSchedule) throws SQLException, Exception;
	public  void update(ShiftingSchedule shiftingSchedule) throws SQLException, Exception;
	
	public void delete(int id) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;	
	
}
