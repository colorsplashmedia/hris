package dai.hris.service.filemanager.country;

import java.sql.SQLException;
import java.util.List;
import dai.hris.dao.filemanager.CountryDAO;
import dai.hris.model.Country;

public class CountryService implements ICountryService {
	
	@Override
	public Country getCountryById(int id) throws SQLException, Exception {
		CountryDAO dao = new CountryDAO();
		Country model = dao.getCountryById(id);
		dao.closeConnection();
		return model;
	}

	@Override
	public boolean isExist(String deptName) throws SQLException, Exception {
		CountryDAO dao = new CountryDAO();
		boolean isExist = dao.isExist(deptName);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public boolean isExistUpdate(String deptName, int id) throws SQLException, Exception {
		CountryDAO dao = new CountryDAO();
		boolean isExist = dao.isExistUpdate(deptName, id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public List<Country> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {		
		CountryDAO dao = new CountryDAO();
		List<Country> list = dao.getAll(jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<Country> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
		CountryDAO dao = new CountryDAO();
		List<Country> list = dao.getAllWithFilter(jtStartIndex, jtPageSize, jtSorting, filter);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
		CountryDAO dao = new CountryDAO();
		int count = dao.getCountWithFilter(filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount() throws SQLException, Exception {
		CountryDAO dao = new CountryDAO();
		int count = dao.getCount();
		dao.closeConnection();
		return count;
	}

	@Override
	public void add(Country country) throws SQLException, Exception {
		CountryDAO dao = new CountryDAO();
		dao.add(country);
		dao.closeConnection();
		
		

	}

	@Override
	public void delete(int id) throws SQLException, Exception {
		CountryDAO dao = new CountryDAO();
		dao.delete(id);
		dao.closeConnection();

	}

	@Override
	public void update(Country country) throws SQLException, Exception {
		CountryDAO dao = new CountryDAO();
		dao.update(country);
		dao.closeConnection();

	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		CountryDAO dao = new CountryDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}

}
