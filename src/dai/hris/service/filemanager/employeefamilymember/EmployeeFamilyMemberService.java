package dai.hris.service.filemanager.employeefamilymember;

import java.sql.SQLException;
import java.util.List;
import dai.hris.dao.filemanager.EmployeeFamilyMemberDAO;
import dai.hris.model.EmployeeFamilyMember;

/**
 * 
 * @author danielpadilla
 *
 */
public class EmployeeFamilyMemberService implements IEmployeeFamilyMemberService {

	@Override
	public boolean isExist(String name) throws SQLException, Exception {
		EmployeeFamilyMemberDAO dao = new EmployeeFamilyMemberDAO();
		boolean isExist = dao.isExist(name);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public boolean isExistUpdate(String name, int id) throws SQLException, Exception {
		EmployeeFamilyMemberDAO dao = new EmployeeFamilyMemberDAO();
		boolean isExist = dao.isExistUpdate(name, id);
		dao.closeConnection();
		return isExist;
	}
	
	@Override
	public List<EmployeeFamilyMember> getEmployeeFamilyMemberListByEmpId(int empId) throws SQLException, Exception {
		EmployeeFamilyMemberDAO eFMDAO = new EmployeeFamilyMemberDAO();
		List<EmployeeFamilyMember> eFMList = eFMDAO.getEmployeeFamilyMemberListByEmpId(empId);
		eFMDAO.closeConnection();
		return eFMList;
	}

	@Override
	public EmployeeFamilyMember getEmployeeFamilyMemberByEmpFamilyMemberId(int empFamilyMemberId) throws SQLException, Exception {
		EmployeeFamilyMemberDAO eFMDAO = new EmployeeFamilyMemberDAO();
		EmployeeFamilyMember eFM = eFMDAO.getEmployeeFamilyMemberByEmpFamilyMemberId(empFamilyMemberId);
		eFMDAO.closeConnection();
		return eFM;
	}

	@Override
	public int add(EmployeeFamilyMember empFamilyMember) throws SQLException, Exception {
		int status;
		EmployeeFamilyMemberDAO eFMDAO = new EmployeeFamilyMemberDAO();
		status = eFMDAO.add(empFamilyMember);
		eFMDAO.closeConnection();
		return status;
	}
	
	@Override
	public void delete(int id) throws SQLException, Exception {
		EmployeeFamilyMemberDAO dao = new EmployeeFamilyMemberDAO();
		dao.delete(id);
		dao.closeConnection();
	}

	@Override
	public int update(EmployeeFamilyMember empFamilyMember) throws SQLException, Exception {
		int status;
		EmployeeFamilyMemberDAO eFMDAO = new EmployeeFamilyMemberDAO();
		status = eFMDAO.update(empFamilyMember);
		eFMDAO.closeConnection();
		return status;
	}
	
	@Override
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		EmployeeFamilyMemberDAO dao = new EmployeeFamilyMemberDAO();
		boolean isExist = dao.checkIfRecordHasBeenUsed(id);
		dao.closeConnection();
		return isExist;
	}

}
