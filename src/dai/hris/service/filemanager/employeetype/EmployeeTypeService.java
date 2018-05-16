package dai.hris.service.filemanager.employeetype;

import java.sql.SQLException;
import java.util.List;
import dai.hris.dao.filemanager.EmployeeTypeDAO;
import dai.hris.model.EmployeeType;


public class EmployeeTypeService implements IEmployeeTypeService {

	@Override
	public List<EmployeeType> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		
		EmployeeTypeDAO dao = new EmployeeTypeDAO();
		List<EmployeeType> list = dao.getAll(jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<EmployeeType> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
		EmployeeTypeDAO dao = new EmployeeTypeDAO();
		List<EmployeeType> list = dao.getAllWithFilter(jtStartIndex, jtPageSize, jtSorting, filter);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
		EmployeeTypeDAO dao = new EmployeeTypeDAO();
		int count = dao.getCountWithFilter(filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount() throws SQLException, Exception {
		EmployeeTypeDAO dao = new EmployeeTypeDAO();
		int count = dao.getCount();
		dao.closeConnection();
		return count;
	}

	@Override
	public void add(EmployeeType employeeType) throws SQLException, Exception {
		EmployeeTypeDAO dao = new EmployeeTypeDAO();
		dao.add(employeeType);
		dao.closeConnection();
	}

	/*
	@Override
	public void delete(EmployeeType employeeType) throws SQLException, Exception {
		EmployeeTypeDAO dao = new EmployeeTypeDAO();
		dao.delete(employeeType);
		dao.closeConnection();

	}
	*/

	@Override
	public void update(EmployeeType employeeType) throws SQLException, Exception {
		EmployeeTypeDAO dao = new EmployeeTypeDAO();
		dao.update(employeeType);
		dao.closeConnection();

	}

}
