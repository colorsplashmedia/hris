package dai.hris.service.cba;

import java.sql.SQLException;
import java.util.List;

import dai.hris.dao.cba.CBADAO;
import dai.hris.model.DisbursementVoucher;
import dai.hris.model.EmployeeCBA;
import dai.hris.model.EmployeeCBADetails;
import dai.hris.model.ObligationRequest;
import dai.hris.model.PayrollExclusion;

public class EmployeeCBAService implements IEmployeeCBAService {

	public EmployeeCBA getEmployeeCbaByCbaId(int cbaId) throws SQLException, Exception {		
		CBADAO dao = new CBADAO();
		EmployeeCBA empCBA = dao.getEmployeeCbaByCbaId(cbaId);
		dao.closeConnection();
		return empCBA;
	}

	public List<EmployeeCBA> getEmployeeCBAByEmpId(int empId) throws SQLException, Exception {
		CBADAO dao = new CBADAO();
		List<EmployeeCBA> empCBAList = dao.getEmployeeCBAByEmpId(empId);
		dao.closeConnection();
		return empCBAList;
	}
	
	public List<EmployeeCBADetails> getEmployeeCbaDetailsByCbaId(int cbaId) throws SQLException, Exception {
		CBADAO dao = new CBADAO();
		List<EmployeeCBADetails> empCBAList = dao.getEmployeeCbaDetailsByCbaId(cbaId);
		dao.closeConnection();
		return empCBAList;
	}

	public void add(EmployeeCBA model) throws SQLException, Exception {
		CBADAO dao = new CBADAO();
		dao.add(model);
		dao.closeConnection();
	}

	public void update(EmployeeCBA model) throws SQLException, Exception {
		CBADAO dao = new CBADAO();
		dao.update(model);
		dao.closeConnection();
	}
	
	public void addDetails(EmployeeCBADetails model) throws SQLException, Exception {
		CBADAO dao = new CBADAO();
		dao.addDetails(model);
		dao.closeConnection();
	}

	public void updateDetails(EmployeeCBADetails model) throws SQLException, Exception {
		CBADAO dao = new CBADAO();
		dao.updateDetails(model);
		dao.closeConnection();
	}
	
	public DisbursementVoucher getDisbursementVoucherById(int disbursementVoucherId) throws SQLException, Exception {
		CBADAO dao = new CBADAO();
		DisbursementVoucher model = dao.getDisbursementVoucherById(disbursementVoucherId);
		dao.closeConnection();
		return model;
	}
	
	
	public List<DisbursementVoucher> getAllDisbursementVouchers() throws SQLException, Exception {
		CBADAO dao = new CBADAO();
		List<DisbursementVoucher> list = dao.getAllDisbursementVouchers();
		dao.closeConnection();
		return list;
	}
	
	public void addDisbursementVoucher(DisbursementVoucher model) throws SQLException, Exception {
		CBADAO dao = new CBADAO();
		dao.addDisbursementVoucher(model);
		dao.closeConnection();
	}
	public void updateDisbursementVoucher(DisbursementVoucher model) throws SQLException, Exception {
		CBADAO dao = new CBADAO();
		dao.updateDisbursementVoucher(model);
		dao.closeConnection();
	}
	
	public void addObligationRequest(ObligationRequest model) throws SQLException, Exception {
		CBADAO dao = new CBADAO();
		dao.addObligationRequest(model);
		dao.closeConnection();
	}
	public void updateObligationRequest(ObligationRequest model) throws SQLException, Exception {
		CBADAO dao = new CBADAO();
		dao.updateObligationRequest(model);
		dao.closeConnection();
	}
	
	
	public ObligationRequest getObligationRequestById(int disbursementVoucherId) throws SQLException, Exception {
		CBADAO dao = new CBADAO();
		ObligationRequest model = dao.getObligationRequestById(disbursementVoucherId);
		dao.closeConnection();
		return model;
	}
	
	public List<ObligationRequest> getAllObligationRequest() throws SQLException, Exception {
		CBADAO dao = new CBADAO();
		List<ObligationRequest> list = dao.getAllObligationRequest();
		dao.closeConnection();
		return list;
	}
	
	public List<PayrollExclusion> getAllPayrollExlusion() throws SQLException, Exception {
		CBADAO dao = new CBADAO();
		List<PayrollExclusion> list = dao.getAllPayrollExlusion();
		dao.closeConnection();
		return list;
	}
	
	public void deletePayrollExlusionById(int empPayrollExclusionId) throws SQLException, Exception {
		CBADAO dao = new CBADAO();
		dao.deletePayrollExlusionById(empPayrollExclusionId);
		dao.closeConnection();
	}

}
