package dai.hris.service.filemanager.province;

import java.sql.SQLException;
import java.util.List;

import dai.hris.dao.filemanager.ProvinceDAO;
import dai.hris.model.Province;


public class ProvinceService implements IProvinceService {
	
	@Override
	public Province getProvinceById(int id) throws SQLException, Exception {
		ProvinceDAO dao = new ProvinceDAO();
		Province model = dao.getProvinceById(id);
		dao.closeConnection();
		return model;
	}

	@Override
	public boolean isExist(String name) throws SQLException, Exception {
		ProvinceDAO dao = new ProvinceDAO();
		boolean isExist = dao.isExist(name);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception {
		ProvinceDAO dao = new ProvinceDAO();
		boolean isExist = dao.isExistUpdate(name, id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public List<Province> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {		
		ProvinceDAO dao = new ProvinceDAO();
		List<Province> list = dao.getAll(jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<Province> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
		ProvinceDAO dao = new ProvinceDAO();
		List<Province> list = dao.getAllWithFilter(jtStartIndex, jtPageSize, jtSorting, filter);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
		ProvinceDAO dao = new ProvinceDAO();
		int count = dao.getCountWithFilter(filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount() throws SQLException, Exception {
		ProvinceDAO dao = new ProvinceDAO();
		int count = dao.getCount();
		dao.closeConnection();
		return count;
	}

	@Override
	public void add(Province province) throws SQLException, Exception {
		ProvinceDAO dao = new ProvinceDAO();
		dao.add(province);
		dao.closeConnection();
		
		

	}

	@Override
	public void delete(int id) throws SQLException, Exception {
		ProvinceDAO dao = new ProvinceDAO();
		dao.delete(id);
		dao.closeConnection();

	}

	@Override
	public void update(Province province) throws SQLException, Exception {
		ProvinceDAO dao = new ProvinceDAO();
		dao.update(province);
		dao.closeConnection();
	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		ProvinceDAO dao = new ProvinceDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}

}
