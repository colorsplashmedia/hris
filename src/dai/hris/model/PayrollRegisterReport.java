package dai.hris.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayrollRegisterReport implements Serializable {

	private static final long serialVersionUID = 8653434825156632681L;

	private String empNo;
	private String empName;
	private String taxStatus;
	private String positionName;
	private String payrollCode;
	private String payPeriod;
	private BigDecimal basicPay;
	private BigDecimal absences;
	private BigDecimal tardiness;
	private BigDecimal undertime;
	private BigDecimal overtime;
	private BigDecimal leaveWOPay;
	private BigDecimal nightDiff;
	private BigDecimal holidayPay;
	private BigDecimal nontaxableIncome;
	private BigDecimal grossPay;
	private BigDecimal gsisEmployeeShare;
	private BigDecimal gsisEmployerShare;
	private BigDecimal philhealthEmployeeShare;
	private BigDecimal philhealthEmployerShare;
	private BigDecimal pagibigEmployeeShare;
	private BigDecimal pagibigEmployerShare;
	private BigDecimal loans;
	private BigDecimal otherDeductions;
	private BigDecimal withholdingTax;
	private BigDecimal totalDeductions;
	private BigDecimal netPay;
	private BigDecimal otherTaxableIncome;
	private BigDecimal taxableIncome;
	
	private String employeeTypeName;
	
	
	
	public String getEmployeeTypeName() {
		return employeeTypeName;
	}
	public void setEmployeeTypeName(String employeeTypeName) {
		this.employeeTypeName = employeeTypeName;
	}
	public BigDecimal getTaxableIncome() {
		return taxableIncome;
	}
	public void setTaxableIncome(BigDecimal taxableIncome) {
		this.taxableIncome = taxableIncome;
	}
	public String getPayPeriod() {
		return payPeriod;
	}
	public void setPayPeriod(String payPeriod) {
		this.payPeriod = payPeriod;
	}
	public String getPayrollCode() {
		return payrollCode;
	}
	public void setPayrollCode(String payrollCode) {
		this.payrollCode = payrollCode;
	}
	
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getTaxStatus() {
		return taxStatus;
	}
	public void setTaxStatus(String taxStatus) {
		this.taxStatus = taxStatus;
	}
	public BigDecimal getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(BigDecimal basicPay) {
		this.basicPay = basicPay;
	}
	public BigDecimal getAbsences() {
		return absences;
	}
	public void setAbsences(BigDecimal absences) {
		this.absences = absences;
	}
	public BigDecimal getTardiness() {
		return tardiness;
	}
	public void setTardiness(BigDecimal tardiness) {
		this.tardiness = tardiness;
	}
	public BigDecimal getUndertime() {
		return undertime;
	}
	public void setUndertime(BigDecimal undertime) {
		this.undertime = undertime;
	}
	public BigDecimal getOvertime() {
		return overtime;
	}
	public void setOvertime(BigDecimal overtime) {
		this.overtime = overtime;
	}
	public BigDecimal getLeaveWOPay() {
		return leaveWOPay;
	}
	public void setLeaveWOPay(BigDecimal leaveWOPay) {
		this.leaveWOPay = leaveWOPay;
	}
	public BigDecimal getNightDiff() {
		return nightDiff;
	}
	public void setNightDiff(BigDecimal nightDiff) {
		this.nightDiff = nightDiff;
	}
	public BigDecimal getHolidayPay() {
		return holidayPay;
	}
	public void setHolidayPay(BigDecimal holidayPay) {
		this.holidayPay = holidayPay;
	}
	
	public BigDecimal getNontaxableIncome() {
		return nontaxableIncome;
	}
	public void setNontaxableIncome(BigDecimal nontaxableIncome) {
		this.nontaxableIncome = nontaxableIncome;
	}
	public BigDecimal getGrossPay() {
		return grossPay;
	}
	public void setGrossPay(BigDecimal grossPay) {
		this.grossPay = grossPay;
	}
	public BigDecimal getGsisEmployeeShare() {
		return gsisEmployeeShare;
	}
	public void setGsisEmployeeShare(BigDecimal gsisEmployeeShare) {
		this.gsisEmployeeShare = gsisEmployeeShare;
	}
	public BigDecimal getGsisEmployerShare() {
		return gsisEmployerShare;
	}
	public void setGsisEmployerShare(BigDecimal gsisEmployerShare) {
		this.gsisEmployerShare = gsisEmployerShare;
	}
	public BigDecimal getPhilhealthEmployeeShare() {
		return philhealthEmployeeShare;
	}
	public void setPhilhealthEmployeeShare(BigDecimal philhealthEmployeeShare) {
		this.philhealthEmployeeShare = philhealthEmployeeShare;
	}
	public BigDecimal getPhilhealthEmployerShare() {
		return philhealthEmployerShare;
	}
	public void setPhilhealthEmployerShare(BigDecimal philhealthEmployerShare) {
		this.philhealthEmployerShare = philhealthEmployerShare;
	}
	public BigDecimal getPagibigEmployeeShare() {
		return pagibigEmployeeShare;
	}
	public void setPagibigEmployeeShare(BigDecimal pagibigEmployeeShare) {
		this.pagibigEmployeeShare = pagibigEmployeeShare;
	}
	public BigDecimal getPagibigEmployerShare() {
		return pagibigEmployerShare;
	}
	public void setPagibigEmployerShare(BigDecimal pagibigEmployerShare) {
		this.pagibigEmployerShare = pagibigEmployerShare;
	}
	public BigDecimal getLoans() {
		return loans;
	}
	public void setLoans(BigDecimal loans) {
		this.loans = loans;
	}
	public BigDecimal getOtherDeductions() {
		return otherDeductions;
	}
	public void setOtherDeductions(BigDecimal otherDeductions) {
		this.otherDeductions = otherDeductions;
	}
	public BigDecimal getWithholdingTax() {
		return withholdingTax;
	}
	public void setWithholdingTax(BigDecimal withholdingTax) {
		this.withholdingTax = withholdingTax;
	}
	public BigDecimal getTotalDeductions() {
		return totalDeductions;
	}
	public void setTotalDeductions(BigDecimal totalDeductions) {
		this.totalDeductions = totalDeductions;
	}
	public BigDecimal getNetPay() {
		return netPay;
	}
	public void setNetPay(BigDecimal netPay) {
		this.netPay = netPay;
	}
	public BigDecimal getOtherTaxableIncome() {
		return otherTaxableIncome;
	}
	public void setOtherTaxableIncome(BigDecimal otherTaxableIncome) {
		this.otherTaxableIncome = otherTaxableIncome;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	
	
	
	
	
	
}
