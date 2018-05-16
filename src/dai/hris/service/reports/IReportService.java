package dai.hris.service.reports;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.AlphaListReport;
import dai.hris.model.AttendanceReport;
import dai.hris.model.ContributionSummaryReport;
import dai.hris.model.Employee;
import dai.hris.model.GSISRemittance;
import dai.hris.model.GSISReport;
import dai.hris.model.Leave;
import dai.hris.model.LeaveCard;
import dai.hris.model.LoanType;
import dai.hris.model.MonthlyAttendanceReport;
import dai.hris.model.NightDifferential;
import dai.hris.model.OffDutyReport;
import dai.hris.model.OtherLoanRemittance;
import dai.hris.model.PagibigRemittance;
import dai.hris.model.PayrollProofList;
import dai.hris.model.PayrollRegisterReport;
import dai.hris.model.PayslipZamboanga;
import dai.hris.model.ServiceRecord;
import dai.hris.model.YearEndBonusCashGift;


public interface IReportService {
	
	public List<ContributionSummaryReport> getContributionSummaryReportByDateRangeAndId (int empId, String dateFrom, String dateTo) throws SQLException, Exception;
	public List<PayslipZamboanga> getPaylipByEmpId (List<Employee> empIdList, int payrollPeriodId) throws SQLException, Exception;
	public List<Leave> getAllLeavesByDateRange(List<Employee> empIdList, String dateFrom, String dateTo) throws SQLException, Exception;
	public List<GSISReport> getGSISReportByDateRangeAndId (int empId, String dateFrom, String dateTo) throws SQLException, Exception;
	public List<GSISReport> getGSISReport (int payrollPeriodId) throws SQLException, Exception;
	public List<GSISReport> getPHICReport (int payrollPeriodId) throws SQLException, Exception;
	public List<GSISReport> getPHICReportByDateRangeAndId (int empId, String dateFrom, String dateTo) throws SQLException, Exception;
	public List<GSISReport> getHDMFReport (int payrollPeriodId) throws SQLException, Exception;
	public List<GSISReport> getHDMFReportByDateRangeAndId (int empId, String dateFrom, String dateTo) throws SQLException, Exception;
	public List<GSISReport> getWithHoldingTaxReport (int payrollPeriodId) throws SQLException, Exception;
	public List<GSISReport> getWithHoldingTaxReportByDateRangeAndId (int empId, String dateFrom, String dateTo) throws SQLException, Exception;
//	public List<PayrollRegisterReport> getPayrollRegisterReport (int payrollPeriodId) throws SQLException, Exception;
	public List<PayslipZamboanga> getPayrollRegisterReport (List<Employee> empIdList, int payrollPeriodId) throws SQLException, Exception;
	public List<PayrollRegisterReport> getPayrollRegisterReportYTD (int year) throws SQLException, Exception;
	public List<YearEndBonusCashGift> getPayrollRegisterReport13thMonth (int year) throws SQLException, Exception;
	public List<AlphaListReport> getBIRAlphaListReport (int year) throws SQLException, Exception;
	public List<AttendanceReport> getAttendanceReportListReport (String dateFrom, String dateTo) throws SQLException, Exception;
	public List<AttendanceReport> getAttendanceReportListByDateAndIdReport (int empId, String dateFrom, String dateTo) throws SQLException, Exception;
	public List<AttendanceReport> getScheduleReportListByDateAndIdReport (int empId, String dateFrom, String dateTo) throws SQLException, Exception;
	
	
	public List<ServiceRecord> getServiceRecordReport (int empId) throws SQLException, Exception;
	public List<MonthlyAttendanceReport> getMonthlyAttendanceReport(List<Employee> empIdList, int month, int year) throws SQLException, Exception;
	public List<GSISRemittance> getGSISRemittanceReport(int month, int year, List<LoanType> loanTypelist) throws SQLException, Exception;
	public List<PagibigRemittance> getPagibigRemittanceReport(int month, int year) throws SQLException, Exception;
	public List<OtherLoanRemittance> getOtherLoanRemittanceReport(int loanId, int month, int year) throws SQLException, Exception;
	public List<PayrollProofList> getPayrollProofListReport(int month, int year) throws SQLException, Exception;
	public List<OffDutyReport> getOffDutyReport(int unitId, int month, int year) throws SQLException, Exception;
	public List<GSISReport> getPHICMonthlyRemittanceReport(int month, int year) throws SQLException, Exception;
	public List<LeaveCard> getLeaveCardByEmpId(int empId) throws SQLException, Exception;
	public List<OtherLoanRemittance> getWithholdingTaxRemittanceReport(int month, int year) throws SQLException, Exception;
	
	public List<LoanType> getTopGSISLoans(int maxLoans) throws SQLException, Exception;
	
	public List<NightDifferential> getNightDifferentialReport (int payrollPeriodId) throws SQLException, Exception;
}
