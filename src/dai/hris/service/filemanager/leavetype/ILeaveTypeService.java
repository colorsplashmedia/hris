package dai.hris.service.filemanager.leavetype;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.LeaveType;

public interface ILeaveTypeService {
	
	public boolean isExist(String name) throws SQLException, Exception;
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception;
	public List<LeaveType> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<LeaveType> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception;
	public int getCountWithFilter(String filter) throws SQLException, Exception;	
	public int getCount() throws SQLException, Exception;	
	public  LeaveType getLeaveTypeByLeaveTypeId(int leaveTypeId) throws SQLException, Exception;
	public  void add(LeaveType LeaveType) throws SQLException, Exception;
	public  void delete(int id) throws SQLException, Exception;
	public  void update(LeaveType LeaveType) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;
}
