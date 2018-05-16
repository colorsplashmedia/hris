package dai.hris.service.filemanager.jobtitle;

import java.sql.SQLException;
import java.util.List;
import dai.hris.model.JobTitle;

public interface IJobTitleService {
	
	public JobTitle getJobTitle(int jobTitleId) throws SQLException, Exception;
	public boolean isExist(String name) throws SQLException, Exception;
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception;
	public List<JobTitle> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<JobTitle> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception;
	public void add(JobTitle jobTitle) throws SQLException, Exception;
	public void delete(int id) throws SQLException, Exception;
	public void update(JobTitle jobTitle) throws SQLException, Exception;
	
	public int getCountWithFilter(String filter) throws SQLException, Exception;	
	public int getCount() throws SQLException, Exception;	
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;	

}
