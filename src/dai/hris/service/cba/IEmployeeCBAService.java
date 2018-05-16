package dai.hris.service.cba;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.DisbursementVoucher;
import dai.hris.model.EmployeeCBA;
import dai.hris.model.EmployeeCBADetails;
import dai.hris.model.ObligationRequest;
import dai.hris.model.PayrollExclusion;

public interface IEmployeeCBAService {
	public EmployeeCBA getEmployeeCbaByCbaId(int cbaId) throws SQLException, Exception;
	public List<EmployeeCBA> getEmployeeCBAByEmpId(int empId) throws SQLException, Exception;	
	public List<EmployeeCBADetails> getEmployeeCbaDetailsByCbaId(int cbaId) throws SQLException, Exception;	
	public void add(EmployeeCBA model) throws SQLException, Exception;
	public void update(EmployeeCBA model) throws SQLException, Exception;
	public void addDetails(EmployeeCBADetails model) throws SQLException, Exception;
	public void updateDetails(EmployeeCBADetails model) throws SQLException, Exception;
	
	public DisbursementVoucher getDisbursementVoucherById(int disbursementVoucherId) throws SQLException, Exception;
	public List<DisbursementVoucher> getAllDisbursementVouchers() throws SQLException, Exception;
	public void addDisbursementVoucher(DisbursementVoucher model) throws SQLException, Exception;
	public void updateDisbursementVoucher(DisbursementVoucher model) throws SQLException, Exception;
	
	public void addObligationRequest(ObligationRequest model) throws SQLException, Exception;
	public void updateObligationRequest(ObligationRequest model) throws SQLException, Exception;
	public ObligationRequest getObligationRequestById(int disbursementVoucherId) throws SQLException, Exception;
	public List<ObligationRequest> getAllObligationRequest() throws SQLException, Exception;
	
	public List<PayrollExclusion> getAllPayrollExlusion() throws SQLException, Exception;
	public void deletePayrollExlusionById(int empPayrollExclusionId) throws SQLException, Exception;
	
}
