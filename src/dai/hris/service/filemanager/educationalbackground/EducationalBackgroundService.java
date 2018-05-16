package dai.hris.service.filemanager.educationalbackground;

import java.sql.SQLException;
import java.util.List;

import dai.hris.dao.filemanager.EducationalBackgroundDAO;
import dai.hris.model.EducationalBackground;

/**
 * 
 * @author danielpadilla
 *
 */
public class EducationalBackgroundService implements IEducationalBackgroundService {

	@Override
	public boolean isExist(String name, String course) throws SQLException, Exception {
		EducationalBackgroundDAO dao = new EducationalBackgroundDAO();
		boolean isExist = dao.isExist(name, course);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public boolean isExistUpdate(String name, int id, String course) throws SQLException, Exception {
		EducationalBackgroundDAO dao = new EducationalBackgroundDAO();
		boolean isExist = dao.isExistUpdate(name, id, course);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public EducationalBackground getEmployeeEducationalBackgroundByEducBkgrndId(int educBkgrndId) throws SQLException, Exception {
		EducationalBackgroundDAO eBackDAO = new EducationalBackgroundDAO();
		EducationalBackground employeeEducBackground = eBackDAO.getEmployeeEducationalBackgroundByEducBkgrndId(educBkgrndId);
		eBackDAO.closeConnection();
		return employeeEducBackground;
	}

	@Override
	public List<EducationalBackground> getEmployeeEducationalBackgroundListByEmpId(int empId) throws SQLException, Exception {
		EducationalBackgroundDAO eBackDAO = new EducationalBackgroundDAO();
		List<EducationalBackground> employeeEducBackgroundList = eBackDAO.getEmployeeEducationalBackgroundListByEmpId(empId);
		eBackDAO.closeConnection();
		return employeeEducBackgroundList;
	}

	@Override
	public int add(EducationalBackground educationalBackground)	throws SQLException, Exception {
		int status;
		EducationalBackgroundDAO eBackDAO = new EducationalBackgroundDAO();
		status = eBackDAO.add(educationalBackground);
		eBackDAO.closeConnection();
		return status;		
	}

	@Override
	public int update(EducationalBackground educationalBackground) throws SQLException, Exception {
		int status;
		EducationalBackgroundDAO eBackDAO = new EducationalBackgroundDAO();
		status = eBackDAO.update(educationalBackground);
		eBackDAO.closeConnection();
		return status;
	}
	
	@Override
	public void delete(int id) throws SQLException, Exception {
		EducationalBackgroundDAO dao = new EducationalBackgroundDAO();
		dao.delete(id);
		dao.closeConnection();
	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		EducationalBackgroundDAO dao = new EducationalBackgroundDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}

}
