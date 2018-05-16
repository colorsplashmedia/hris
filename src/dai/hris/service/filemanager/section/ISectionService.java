package dai.hris.service.filemanager.section;

import java.sql.SQLException;
import java.util.List;
import dai.hris.model.Section;

public interface ISectionService {
	
	public Section getSection(int sectionId) throws SQLException, Exception;
	public boolean isExist(String name) throws SQLException, Exception;
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception;
	public List<Section> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<Section> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception;
	public int getCountWithFilter(String filter) throws SQLException, Exception;	
	public int getCount() throws SQLException, Exception;	
	public  void add(Section model) throws SQLException, Exception;	
	public  void update(Section model) throws SQLException, Exception;
	public void delete(int id) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;	

}
