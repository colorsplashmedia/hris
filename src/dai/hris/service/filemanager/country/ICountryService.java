package dai.hris.service.filemanager.country;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.Country;


public interface ICountryService {
	
	public boolean isExist(String deptName) throws SQLException, Exception;
	public boolean isExistUpdate(String deptName, int id) throws SQLException, Exception;
	public List<Country> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<Country> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception;
	public int getCountWithFilter(String filter) throws SQLException, Exception;	
	public int getCount() throws SQLException, Exception;	
	public  void add(Country country) throws SQLException, Exception;	
	public  void update(Country country) throws SQLException, Exception;
	public void delete(int id) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;	

	public Country getCountryById(int id) throws SQLException, Exception;
}
