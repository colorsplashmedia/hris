package dai.hris.service.filemanager.jobtitle;

import java.sql.SQLException;
import java.util.List;

import dai.hris.dao.filemanager.JobTitleDAO;
import dai.hris.model.JobTitle;

public class JobTitleService implements IJobTitleService {

	@Override
	public JobTitle getJobTitle(int jobTitleId) throws SQLException, Exception {
		JobTitleDAO dao = new JobTitleDAO();
		JobTitle model = dao.getJobTitle(jobTitleId);
		dao.closeConnection();
		return model;
	}
	
	@Override
	public boolean isExist(String name) throws SQLException, Exception {
		JobTitleDAO dao = new JobTitleDAO();
		boolean isExist = dao.isExist(name);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception {
		JobTitleDAO dao = new JobTitleDAO();
		boolean isExist = dao.isExistUpdate(name, id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public List<JobTitle> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {		
		JobTitleDAO dao = new JobTitleDAO();
		List<JobTitle> list = dao.getAll(jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<JobTitle> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
		JobTitleDAO dao = new JobTitleDAO();
		List<JobTitle> list = dao.getAllWithFilter(jtStartIndex, jtPageSize, jtSorting, filter);
		dao.closeConnection();
		return list;
	}

	@Override
	public void add(JobTitle jobTitle) throws SQLException, Exception {
		JobTitleDAO dao = new JobTitleDAO();
		dao.add(jobTitle);
		dao.closeConnection();

	}

	@Override
	public void delete(int id) throws SQLException, Exception {
		JobTitleDAO dao = new JobTitleDAO();
		dao.delete(id);
		dao.closeConnection();

	}

	@Override
	public void update(JobTitle jobTitle) throws SQLException, Exception {
		JobTitleDAO dao = new JobTitleDAO();
		dao.update(jobTitle);
		dao.closeConnection();

	}
	
	@Override
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
		JobTitleDAO dao = new JobTitleDAO();
		int count = dao.getCountWithFilter(filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount() throws SQLException, Exception {
		JobTitleDAO dao = new JobTitleDAO();
		int count = dao.getCount();
		dao.closeConnection();
		return count;
	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		JobTitleDAO dao = new JobTitleDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}

}
