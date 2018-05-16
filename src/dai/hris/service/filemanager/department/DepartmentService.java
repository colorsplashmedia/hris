package dai.hris.service.filemanager.department;

import java.sql.SQLException;
import java.util.List;

import dai.hris.dao.filemanager.DepartmentDAO;
import dai.hris.model.Department;


public class DepartmentService implements IDepartmentService {

	@Override
	public Department getDepartmentById(int id) throws SQLException, Exception {
		DepartmentDAO dao = new DepartmentDAO();
		Department model = dao.getDepartmentById(id);
		dao.closeConnection();
		return model;
	}
	
	@Override
	public boolean isExist(String deptName) throws SQLException, Exception {
		DepartmentDAO dao = new DepartmentDAO();
		boolean isExist = dao.isExist(deptName);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public boolean isExistUpdate(String deptName, int id) throws SQLException, Exception {
		DepartmentDAO dao = new DepartmentDAO();
		boolean isExist = dao.isExistUpdate(deptName, id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public List<Department> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		
		DepartmentDAO dao = new DepartmentDAO();
		List<Department> list = dao.getAll(jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<Department> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
		DepartmentDAO dao = new DepartmentDAO();
		List<Department> list = dao.getAllWithFilter(jtStartIndex, jtPageSize, jtSorting, filter);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
		DepartmentDAO dao = new DepartmentDAO();
		int count = dao.getCountWithFilter(filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount() throws SQLException, Exception {
		DepartmentDAO dao = new DepartmentDAO();
		int count = dao.getCount();
		dao.closeConnection();
		return count;
	}

	@Override
	public void add(Department department) throws SQLException, Exception {
		DepartmentDAO dao = new DepartmentDAO();
		dao.add(department);
		dao.closeConnection();		

	}

	@Override
	public void delete(int id) throws SQLException, Exception {
		DepartmentDAO dao = new DepartmentDAO();
		dao.delete(id);
		dao.closeConnection();

	}

	@Override
	public void update(Department department) throws SQLException, Exception {
		DepartmentDAO dao = new DepartmentDAO();
		dao.update(department);
		dao.closeConnection();

	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		DepartmentDAO dao = new DepartmentDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}

}
