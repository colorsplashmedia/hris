package dai.hris.service.filemanager.section;

import java.sql.SQLException;
import java.util.List;
import dai.hris.dao.filemanager.SectionDAO;
import dai.hris.model.Section;


public class SectionService implements ISectionService {

	@Override
	public Section getSection(int sectionId) throws SQLException, Exception {
		SectionDAO dao = new SectionDAO();
		Section model = dao.getSection(sectionId);
		dao.closeConnection();
		return model;
	}
	
	@Override
	public boolean isExist(String name) throws SQLException, Exception {
		SectionDAO dao = new SectionDAO();
		boolean isExist = dao.isExist(name);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception {
		SectionDAO dao = new SectionDAO();
		boolean isExist = dao.isExistUpdate(name, id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public List<Section> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		
		SectionDAO dao = new SectionDAO();
		List<Section> list = dao.getAll(jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<Section> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
		SectionDAO dao = new SectionDAO();
		List<Section> list = dao.getAllWithFilter(jtStartIndex, jtPageSize, jtSorting, filter);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
		SectionDAO dao = new SectionDAO();
		int count = dao.getCountWithFilter(filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount() throws SQLException, Exception {
		SectionDAO dao = new SectionDAO();
		int count = dao.getCount();
		dao.closeConnection();
		return count;
	}

	@Override
	public void add(Section model) throws SQLException, Exception {
		SectionDAO dao = new SectionDAO();
		dao.add(model);
		dao.closeConnection();		

	}

	@Override
	public void delete(int id) throws SQLException, Exception {
		SectionDAO dao = new SectionDAO();
		dao.delete(id);
		dao.closeConnection();

	}

	@Override
	public void update(Section model) throws SQLException, Exception {
		SectionDAO dao = new SectionDAO();
		dao.update(model);
		dao.closeConnection();

	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		SectionDAO dao = new SectionDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}

}
