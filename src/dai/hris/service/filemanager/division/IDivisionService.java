package dai.hris.service.filemanager.division;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.Division;

public interface IDivisionService {
	
	public boolean isExist(String name) throws SQLException, Exception;
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception;
	public List<Division> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<Division> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception;
	public int getCountWithFilter(String filter) throws SQLException, Exception;	
	public int getCount() throws SQLException, Exception;	
	public  void add(Division division) throws SQLException, Exception;	
	public  void update(Division division) throws SQLException, Exception;	
	public void delete(int id) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;	
	
	public Division getDivisionById(int id) throws SQLException, Exception;

}
