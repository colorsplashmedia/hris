package dai.hris.service.filemanager.educationalbackground;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.EducationalBackground;

public interface IEducationalBackgroundService {
	
	public boolean isExist(String name, String course) throws SQLException, Exception;
	public boolean isExistUpdate(String name, int id, String course) throws SQLException, Exception;
	public EducationalBackground getEmployeeEducationalBackgroundByEducBkgrndId(int educBkgrndId)throws SQLException, Exception;
	public List<EducationalBackground> getEmployeeEducationalBackgroundListByEmpId(int empId) throws SQLException, Exception;
	public int add(EducationalBackground educationalBackground)	throws SQLException, Exception;
	public int update(EducationalBackground educationalBackground) throws SQLException, Exception;	
	public void delete(int id) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;	
}
