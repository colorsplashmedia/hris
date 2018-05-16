package dai.hris.service.filemanager.subunit;

import java.sql.SQLException;
import java.util.List;
import dai.hris.model.SubUnit;

public interface ISubUnitService {
	
	public SubUnit getSubUnit(int unitId) throws SQLException, Exception;
	public boolean isExist(String name) throws SQLException, Exception;
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception;
	public List<SubUnit> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<SubUnit> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception;
	public int getCountWithFilter(String filter) throws SQLException, Exception;	
	public int getCount() throws SQLException, Exception;	
	public void add(SubUnit model) throws SQLException, Exception;	
	public void update(SubUnit model) throws SQLException, Exception;
	public void delete(int id) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;	

}
