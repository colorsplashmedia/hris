package dai.hris.service.filemanager.employeefamilymember;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.EmployeeFamilyMember;

/**
 * 
 * @author danielpadilla
 *
 */
public interface IEmployeeFamilyMemberService {
	
	public boolean isExist(String name) throws SQLException, Exception;
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception;
	public List<EmployeeFamilyMember> getEmployeeFamilyMemberListByEmpId(int empId) throws SQLException, Exception;
	public EmployeeFamilyMember getEmployeeFamilyMemberByEmpFamilyMemberId(int empFamilyMemberId) throws SQLException, Exception;
	public int add(EmployeeFamilyMember empFamilyMember) throws SQLException, Exception;
	public int update(EmployeeFamilyMember empFamilyMember) throws SQLException, Exception;	
	
	public void delete(int id) throws SQLException, Exception;
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception;	
}
