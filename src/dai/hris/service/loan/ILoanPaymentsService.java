package dai.hris.service.loan;

import java.sql.SQLException;
import java.util.List;
import dai.hris.model.LoanPayments;

public interface ILoanPaymentsService {
	public List<LoanPayments> getAllLoanPaymentsByLoanEntryId(int loanEntryId) throws SQLException, Exception;
	public void add(LoanPayments loanPayments) throws SQLException, Exception;
	public void update(LoanPayments loanPayments) throws SQLException, Exception;
}
