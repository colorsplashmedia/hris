package dai.hris.service.filemanager.employeepayrollinfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dai.hris.dao.filemanager.EmployeeIncomeDeductionDAO;
import dai.hris.model.Deduction;
import dai.hris.model.EmployeeDeduction;
import dai.hris.model.EmployeeIncome;
import dai.hris.model.Income;

public class EmployeeIncomeDeductionService implements IEmployeeIncomeDeductionService {

	public List<EmployeeDeduction> getEmployeeDeductionByEmpId(int empId) throws SQLException, Exception {
		EmployeeIncomeDeductionDAO eIDDAO = new EmployeeIncomeDeductionDAO();
		List<EmployeeDeduction> list = new ArrayList<EmployeeDeduction>();
		list = eIDDAO.getEmployeeDeductionByEmpId(empId);
		eIDDAO.closeConnection();
		return list;	
	}
	
	
	public List<EmployeeIncome> getEmployeeIncomeByEmpId(int empId) throws SQLException, Exception {
		EmployeeIncomeDeductionDAO dao = new EmployeeIncomeDeductionDAO();
		List<EmployeeIncome> list = new ArrayList<EmployeeIncome>();
		list = dao.getEmployeeIncomeByEmpId(empId);
		dao.closeConnection();
		return list;	
	}
	
	
//	public int addIncome(EmployeeIncome employeeIncome)	throws SQLException, Exception {
//		int status;
//		EmployeeIncomeDeductionDAO eIDDAO = new EmployeeIncomeDeductionDAO();
//		status = eIDDAO.addIncome(employeeIncome);
//		eIDDAO.closeConnection();
//		return status;
//	}
//	
//	public int addDeduction(EmployeeDeduction employeeDeduction) throws SQLException, Exception {
//		int status;
//		EmployeeIncomeDeductionDAO eIDDAO = new EmployeeIncomeDeductionDAO();
//		status = eIDDAO.addDeduction(employeeDeduction);
//		eIDDAO.closeConnection();
//		return status;
//	}
	
	
//	public int updateIncome(EmployeeIncome employeeIncome) throws SQLException, Exception {
//		int status;
//		EmployeeIncomeDeductionDAO eIDDAO = new EmployeeIncomeDeductionDAO();
//		status = eIDDAO.updateIncome(employeeIncome);
//		eIDDAO.closeConnection();
//		return status;
//	}
//	
//	public int updateDeduction(EmployeeDeduction employeeDeduction) throws SQLException, Exception {
//		int status;
//		EmployeeIncomeDeductionDAO eIDDAO = new EmployeeIncomeDeductionDAO();
//		status = eIDDAO.updateDeduction(employeeDeduction);
//		eIDDAO.closeConnection();
//		return status;
//	}


}
