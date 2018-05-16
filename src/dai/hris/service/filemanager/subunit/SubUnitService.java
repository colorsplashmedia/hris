package dai.hris.service.filemanager.subunit;

import java.sql.SQLException;
import java.util.List;
import dai.hris.dao.filemanager.SubUnitDAO;
import dai.hris.model.SubUnit;


public class SubUnitService implements ISubUnitService {

	@Override
	public SubUnit getSubUnit(int subUnitId) throws SQLException, Exception {
		SubUnitDAO dao = new SubUnitDAO();
		SubUnit model = dao.getSubUnit(subUnitId);
		dao.closeConnection();
		return model;
	}
	
	@Override
	public boolean isExist(String name) throws SQLException, Exception {
		SubUnitDAO dao = new SubUnitDAO();
		boolean isExist = dao.isExist(name);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception {
		SubUnitDAO dao = new SubUnitDAO();
		boolean isExist = dao.isExistUpdate(name, id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public List<SubUnit> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		
		SubUnitDAO dao = new SubUnitDAO();
		List<SubUnit> list = dao.getAll(jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<SubUnit> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
		SubUnitDAO dao = new SubUnitDAO();
		List<SubUnit> list = dao.getAllWithFilter(jtStartIndex, jtPageSize, jtSorting, filter);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
		SubUnitDAO dao = new SubUnitDAO();
		int count = dao.getCountWithFilter(filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount() throws SQLException, Exception {
		SubUnitDAO dao = new SubUnitDAO();
		int count = dao.getCount();
		dao.closeConnection();
		return count;
	}

	@Override
	public void add(SubUnit model) throws SQLException, Exception {
		SubUnitDAO dao = new SubUnitDAO();
		dao.add(model);
		dao.closeConnection();		

	}

	@Override
	public void delete(int id) throws SQLException, Exception {
		SubUnitDAO dao = new SubUnitDAO();
		dao.delete(id);
		dao.closeConnection();

	}

	@Override
	public void update(SubUnit model) throws SQLException, Exception {
		SubUnitDAO dao = new SubUnitDAO();
		dao.update(model);
		dao.closeConnection();

	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		SubUnitDAO dao = new SubUnitDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}

}
