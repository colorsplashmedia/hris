package dai.hris.service.filemanager.province;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.Province;

public interface IProvinceService {
	
	public boolean isExist(String name) throws SQLException, Exception;
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception;
	public List<Province> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<Province> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception;
	public int getCountWithFilter(String filter) throws SQLException, Exception;	
	public int getCount() throws SQLException, Exception;	
	public  void add(Province province) throws SQLException, Exception;	
	public  void update(Province province) throws SQLException, Exception;	
	public void delete(int id) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;	
	
	public Province getProvinceById(int id) throws SQLException, Exception;

}
