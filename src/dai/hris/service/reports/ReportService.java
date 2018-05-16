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
//import dai.hris.model.Payslip;
import dai.hris.model.PayslipZamboanga;
import dai.hris.model.ServiceRecord;
import dai.hris.model.YearEndBonusCashGift;
import dai.hris.dao.ReportsDAO;

public class ReportService implements IReportService {
	
	@Override
	public List<ContributionSummaryReport> getContributionSummaryReportByDateRangeAndId (int empId, String dateFrom, String dateTo) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<ContributionSummaryReport> list = dao.getContributionSummaryReportByDateRangeAndId(empId, dateFrom, dateTo);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<PayslipZamboanga> getPaylipByEmpId (List<Employee> empIdList, int payrollPeriodId) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<PayslipZamboanga> list = dao.getPaylipInfoZamboanga(empIdList, payrollPeriodId);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<Leave> getAllLeavesByDateRange(List<Employee> empIdList, String dateFrom, String dateTo) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<Leave> list = dao.getAllLeavesByDateRange(empIdList, dateFrom, dateTo);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<GSISReport> getGSISReportByDateRangeAndId (int empId, String dateFrom, String dateTo) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<GSISReport> list = dao.getGSISReportByDateRangeAndId(empId, dateFrom, dateTo);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<GSISReport> getGSISReport (int payrollPeriodId) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<GSISReport> list = dao.getGSISReport(payrollPeriodId);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<GSISReport> getPHICReport (int payrollPeriodId) throws SQLException, Exception{
		ReportsDAO dao = new ReportsDAO();
		List<GSISReport> list = dao.getPHICReport(payrollPeriodId);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<GSISReport> getPHICMonthlyRemittanceReport(int month, int year) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<GSISReport> list = dao.getPHICMonthlyRemittanceReport(month, year);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<GSISReport> getPHICReportByDateRangeAndId (int empId, String dateFrom, String dateTo) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<GSISReport> list = dao.getPHICReportByDateRangeAndId(empId, dateFrom, dateTo);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<GSISReport> getHDMFReport (int payrollPeriodId) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<GSISReport> list = dao.getHDMFReport(payrollPeriodId);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<GSISReport> getHDMFReportByDateRangeAndId (int empId, String dateFrom, String dateTo) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<GSISReport> list = dao.getHDMFReportByDateRangeAndId(empId, dateFrom, dateTo);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<GSISReport> getWithHoldingTaxReport (int payrollPeriodId) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<GSISReport> list = dao.getWithHoldingTaxReport(payrollPeriodId);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<GSISReport> getWithHoldingTaxReportByDateRangeAndId (int empId, String dateFrom, String dateTo) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<GSISReport> list = dao.getWithHoldingTaxReportByDateRangeAndId(empId, dateFrom, dateTo);
		dao.closeConnection();
		return list;
	}
	
//	@Override
//	public List<PayrollRegisterReport> getPayrollRegisterReport (int payrollPeriodId) throws SQLException, Exception {
//		ReportsDAO dao = new ReportsDAO();
//		List<PayrollRegisterReport> list = dao.getPayrollRegisterReport(payrollPeriodId);
//		dao.closeConnection();
//		return list;
//	}
	
	@Override
	public List<PayslipZamboanga> getPayrollRegisterReport (List<Employee> empIdList, int payrollPeriodId) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<PayslipZamboanga> list = dao.getPayrollRegisterReport(empIdList, payrollPeriodId);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<PayrollRegisterReport> getPayrollRegisterReportYTD (int year) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<PayrollRegisterReport> list = dao.getPayrollRegisterReportYTD(year);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<YearEndBonusCashGift> getPayrollRegisterReport13thMonth (int year) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<YearEndBonusCashGift> list = dao.getPayrollRegisterReport13thMonth(year);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<AlphaListReport> getBIRAlphaListReport (int year) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<AlphaListReport> list = dao.getBIRAlphaListReport(year);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<AttendanceReport> getAttendanceReportListReport (String dateFrom, String dateTo) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<AttendanceReport> list = dao.getAttendanceReportListReport(dateFrom, dateTo);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<AttendanceReport> getAttendanceReportListByDateAndIdReport (int empId, String dateFrom, String dateTo) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<AttendanceReport> list = dao.getAttendanceReportListByDateAndIdReport(empId, dateFrom, dateTo);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<AttendanceReport> getScheduleReportListByDateAndIdReport (int empId, String dateFrom, String dateTo) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<AttendanceReport> list = dao.getScheduleReportListByDateAndIdReport(empId, dateFrom, dateTo);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<ServiceRecord> getServiceRecordReport (int empId) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<ServiceRecord> list = dao.getServiceRecordReport(empId);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<MonthlyAttendanceReport> getMonthlyAttendanceReport(List<Employee> empIdList, int month, int year) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<MonthlyAttendanceReport> list = dao.getMonthlyAttendanceReport(empIdList, month, year);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<GSISRemittance> getGSISRemittanceReport(int month, int year, List<LoanType> loanTypelist) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<GSISRemittance> list = dao.getGSISRemittanceReport(month, year, loanTypelist);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<PagibigRemittance> getPagibigRemittanceReport(int month, int year) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<PagibigRemittance> list = dao.getPagibigRemittanceReport(month, year);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<OtherLoanRemittance> getOtherLoanRemittanceReport(int loanId, int month, int year) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<OtherLoanRemittance> list = dao.getOtherLoanRemittanceReport(loanId, month, year);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<PayrollProofList> getPayrollProofListReport(int month, int year) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<PayrollProofList> list = dao.getPayrollProofListReport(month, year);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<OffDutyReport> getOffDutyReport(int unitId, int month, int year) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<OffDutyReport> list = dao.getOffDutyReport(unitId, month, year);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<LeaveCard> getLeaveCardByEmpId(int empId) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<LeaveCard> list = dao.getLeaveCardByEmpId(empId);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<OtherLoanRemittance> getWithholdingTaxRemittanceReport(int month, int year) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<OtherLoanRemittance> list = dao.getWithholdingTaxRemittanceReport(month, year);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<LoanType> getTopGSISLoans(int maxLoans) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<LoanType> list = dao.getTopGSISLoans(maxLoans);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<NightDifferential> getNightDifferentialReport (int payrollPeriodId) throws SQLException, Exception {
		ReportsDAO dao = new ReportsDAO();
		List<NightDifferential> list = dao.getNightDifferentialReport(payrollPeriodId);
		dao.closeConnection();
		return list;
	}
	
}
