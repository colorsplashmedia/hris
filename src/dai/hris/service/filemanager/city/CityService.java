package dai.hris.service.filemanager.city;

import java.sql.SQLException;
import java.util.List;

import dai.hris.dao.filemanager.CityDAO;
import dai.hris.model.City;


public class CityService implements ICityService {
	
	@Override
	public City getCityById(int id) throws SQLException, Exception {
		CityDAO dao = new CityDAO();
		City model = dao.getCityById(id);
		dao.closeConnection();
		return model;
	}

	@Override
	public boolean isExist(String name) throws SQLException, Exception {
		CityDAO dao = new CityDAO();
		boolean isExist = dao.isExist(name);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception {
		CityDAO dao = new CityDAO();
		boolean isExist = dao.isExistUpdate(name, id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public List<City> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {		
		CityDAO dao = new CityDAO();
		List<City> list = dao.getAll(jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<City> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
		CityDAO dao = new CityDAO();
		List<City> list = dao.getAllWithFilter(jtStartIndex, jtPageSize, jtSorting, filter);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
		CityDAO dao = new CityDAO();
		int count = dao.getCountWithFilter(filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount() throws SQLException, Exception {
		CityDAO dao = new CityDAO();
		int count = dao.getCount();
		dao.closeConnection();
		return count;
	}

	@Override
	public void add(City city) throws SQLException, Exception {
		CityDAO dao = new CityDAO();
		dao.add(city);
		dao.closeConnection();
	}

	@Override
	public void delete(int id) throws SQLException, Exception {
		CityDAO dao = new CityDAO();
		dao.delete(id);
		dao.closeConnection();

	}

	@Override
	public void update(City city) throws SQLException, Exception {
		CityDAO dao = new CityDAO();
		dao.update(city);
		dao.closeConnection();
	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		CityDAO dao = new CityDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}

}
