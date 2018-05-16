package dai.hris.service.filemanager.salarygrade;

import java.sql.SQLException;
import java.util.List;
import dai.hris.dao.filemanager.SalaryGradeDAO;
import dai.hris.model.SalaryGrade;


public class SalaryGradeService implements ISalaryGradeService {

	@Override
	public SalaryGrade getSalaryGrade(int salaryGradeId) throws SQLException, Exception {
		SalaryGradeDAO dao = new SalaryGradeDAO();
		SalaryGrade model = dao.getSalaryGrade(salaryGradeId);
		dao.closeConnection();
		return model;
	}
	
	@Override
	public boolean isExist(String name) throws SQLException, Exception {
		SalaryGradeDAO dao = new SalaryGradeDAO();
		boolean isExist = dao.isExist(name);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception {
		SalaryGradeDAO dao = new SalaryGradeDAO();
		boolean isExist = dao.isExistUpdate(name, id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public List<SalaryGrade> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		
		SalaryGradeDAO dao = new SalaryGradeDAO();
		List<SalaryGrade> list = dao.getAll(jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<SalaryGrade> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
		SalaryGradeDAO dao = new SalaryGradeDAO();
		List<SalaryGrade> list = dao.getAllWithFilter(jtStartIndex, jtPageSize, jtSorting, filter);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
		SalaryGradeDAO dao = new SalaryGradeDAO();
		int count = dao.getCountWithFilter(filter);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getCount() throws SQLException, Exception {
		SalaryGradeDAO dao = new SalaryGradeDAO();
		int count = dao.getCount();
		dao.closeConnection();
		return count;
	}

	@Override
	public void add(SalaryGrade model) throws SQLException, Exception {
		SalaryGradeDAO dao = new SalaryGradeDAO();
		dao.add(model);
		dao.closeConnection();		

	}

	@Override
	public void delete(int id) throws SQLException, Exception {
		SalaryGradeDAO dao = new SalaryGradeDAO();
		dao.delete(id);
		dao.closeConnection();

	}

	@Override
	public void update(SalaryGrade model) throws SQLException, Exception {
		SalaryGradeDAO dao = new SalaryGradeDAO();
		dao.update(model);
		dao.closeConnection();

	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		SalaryGradeDAO dao = new SalaryGradeDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}

}
