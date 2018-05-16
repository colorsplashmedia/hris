package dai.hris.service.filemanager.unit;

import java.sql.SQLException;
import java.util.List;
import dai.hris.dao.filemanager.UnitDAO;
import dai.hris.model.Unit;


public class UnitService implements IUnitService {

	@Override
	public Unit getUnit(int unitId) throws SQLException, Exception {
		UnitDAO dao = new UnitDAO();
		Unit model = dao.getUnit(unitId);
		dao.closeConnection();
		return model;
	}
	
	@Override
	public boolean isExist(String name) throws SQLException, Exception {
		UnitDAO dao = new UnitDAO();
		boolean isExist = dao.isExist(name);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception {
		UnitDAO dao = new UnitDAO();
		boolean isExist = dao.isExistUpdate(name, id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public List<Unit> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		
		UnitDAO dao = new UnitDAO();
		List<Unit> list = dao.getAll(jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<Unit> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
		UnitDAO dao = new UnitDAO();
		List<Unit> list = dao.getAllWithFilter(jtStartIndex, jtPageSize, jtSorting, filter);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
		UnitDAO dao = new UnitDAO();
		int count = dao.getCountWithFilter(filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount() throws SQLException, Exception {
		UnitDAO dao = new UnitDAO();
		int count = dao.getCount();
		dao.closeConnection();
		return count;
	}

	@Override
	public void add(Unit model) throws SQLException, Exception {
		UnitDAO dao = new UnitDAO();
		dao.add(model);
		dao.closeConnection();		

	}

	@Override
	public void delete(int id) throws SQLException, Exception {
		UnitDAO dao = new UnitDAO();
		dao.delete(id);
		dao.closeConnection();

	}

	@Override
	public void update(Unit model) throws SQLException, Exception {
		UnitDAO dao = new UnitDAO();
		dao.update(model);
		dao.closeConnection();

	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		UnitDAO dao = new UnitDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}

}
