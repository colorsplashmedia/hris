package dai.hris.service.filemanager.unit;

import java.sql.SQLException;
import java.util.List;
import dai.hris.model.Unit;

public interface IUnitService {
	
	public Unit getUnit(int unitId) throws SQLException, Exception;
	public boolean isExist(String name) throws SQLException, Exception;
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception;
	public List<Unit> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<Unit> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception;
	public int getCountWithFilter(String filter) throws SQLException, Exception;	
	public int getCount() throws SQLException, Exception;	
	public void add(Unit model) throws SQLException, Exception;	
	public void update(Unit model) throws SQLException, Exception;
	public void delete(int id) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;	

}
