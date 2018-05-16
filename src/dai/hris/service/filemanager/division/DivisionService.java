package dai.hris.service.filemanager.division;

import java.sql.SQLException;
import java.util.List;

import dai.hris.dao.filemanager.DivisionDAO;
import dai.hris.model.Division;

public class DivisionService implements IDivisionService {
	
	@Override
	public Division getDivisionById(int id) throws SQLException, Exception {
		DivisionDAO dao = new DivisionDAO();
		Division model = dao.getDivisionById(id);
		dao.closeConnection();
		return model;
	}

	@Override
	public boolean isExist(String name) throws SQLException, Exception {
		DivisionDAO dao = new DivisionDAO();
		boolean isExist = dao.isExist(name);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception {
		DivisionDAO dao = new DivisionDAO();
		boolean isExist = dao.isExistUpdate(name, id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public List<Division> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {		
		DivisionDAO dao = new DivisionDAO();
		List<Division> list = dao.getAll(jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<Division> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
		DivisionDAO dao = new DivisionDAO();
		List<Division> list = dao.getAllWithFilter(jtStartIndex, jtPageSize, jtSorting, filter);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
		DivisionDAO dao = new DivisionDAO();
		int count = dao.getCountWithFilter(filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount() throws SQLException, Exception {
		DivisionDAO dao = new DivisionDAO();
		int count = dao.getCount();
		dao.closeConnection();
		return count;
	}

	@Override
	public void add(Division division) throws SQLException, Exception {
		DivisionDAO dao = new DivisionDAO();
		dao.add(division);
		dao.closeConnection();
	}

	@Override
	public void delete(int id) throws SQLException, Exception {
		DivisionDAO dao = new DivisionDAO();
		dao.delete(id);
		dao.closeConnection();

	}

	@Override
	public void update(Division division) throws SQLException, Exception {
		DivisionDAO dao = new DivisionDAO();
		dao.update(division);
		dao.closeConnection();
	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		DivisionDAO dao = new DivisionDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}

}
