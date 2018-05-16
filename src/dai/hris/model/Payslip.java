package dai.hris.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Payslip implements Serializable {

	private static final long serialVersionUID = 5027585243743799098L;
	
	private String positionName;
	private String departmentName;
	
	private int empId;
	private int payrollPeriodId;
	private String payrollType;
	private String payPeriod;
	private String empNo;
	private String name;
	private String taxStatus;
	private BigDecimal basicPay;
	private BigDecimal nightDiff;
	private BigDecimal overtime;
	
	private BigDecimal absences;
	private BigDecimal tardiness;
	private BigDecimal undertime;
	private BigDecimal holidayPay;
	private BigDecimal otherTaxableIncome;
	private BigDecimal gsisEmployeeShare;
	private BigDecimal philhealthEmployeeShare;
	private BigDecimal pagibigEmployeeShare;
	private BigDecimal withholdingTax;
	
	private BigDecimal gsisEmployerShare;
	private BigDecimal philhealthEmployerShare;
	private BigDecimal pagibigEmployerShare;
	
	private BigDecimal totalEarnings;
	private BigDecimal totalDeductions;
	private BigDecimal netPay;
	private BigDecimal netPay2;
	private BigDecimal nontaxableIncome;
	
	
	private BigDecimal cola;
	private BigDecimal foodAllowance;
	private BigDecimal taxShield;
	private BigDecimal tranpoAllowance;
	
	
	private String otherDedName1;
	private String otherDedName2;
	private String otherDedName3;
	private String otherDedName4;
	private String otherDedName5;
	
	private BigDecimal otherDedAmount1;
	private BigDecimal otherDedAmount2;
	private BigDecimal otherDedAmount3;
	private BigDecimal otherDedAmount4;
	private BigDecimal otherDedAmount5;
	
	private String loanName1;
	private String loanName2;
	private String loanName3;
	private String loanName4;
	private String loanName5;
	
	private BigDecimal loanAmount1;
	private BigDecimal loanAmount2;
	private BigDecimal loanAmount3;
	private BigDecimal loanAmount4;
	private BigDecimal loanAmount5;
	
	private BigDecimal grossPay;
	
	
	public BigDecimal getGrossPay() {
		return grossPay;
	}
	public void setGrossPay(BigDecimal grossPay) {
		this.grossPay = grossPay;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getPayrollPeriodId() {
		return payrollPeriodId;
	}
	public void setPayrollPeriodId(int payrollPeriodId) {
		this.payrollPeriodId = payrollPeriodId;
	}
	public BigDecimal getNetPay2() {
		return netPay2;
	}
	public void setNetPay2(BigDecimal netPay2) {
		this.netPay2 = netPay2;
	}
	public String getOtherDedName1() {
		return otherDedName1;
	}
	public void setOtherDedName1(String otherDedName1) {
		this.otherDedName1 = otherDedName1;
	}
	public String getOtherDedName2() {
		return otherDedName2;
	}
	public void setOtherDedName2(String otherDedName2) {
		this.otherDedName2 = otherDedName2;
	}
	public String getOtherDedName3() {
		return otherDedName3;
	}
	public void setOtherDedName3(String otherDedName3) {
		this.otherDedName3 = otherDedName3;
	}
	public String getOtherDedName4() {
		return otherDedName4;
	}
	public void setOtherDedName4(String otherDedName4) {
		this.otherDedName4 = otherDedName4;
	}
	public String getOtherDedName5() {
		return otherDedName5;
	}
	public void setOtherDedName5(String otherDedName5) {
		this.otherDedName5 = otherDedName5;
	}
	public BigDecimal getOtherDedAmount1() {
		return otherDedAmount1;
	}
	public void setOtherDedAmount1(BigDecimal otherDedAmount1) {
		this.otherDedAmount1 = otherDedAmount1;
	}
	public BigDecimal getOtherDedAmount2() {
		return otherDedAmount2;
	}
	public void setOtherDedAmount2(BigDecimal otherDedAmount2) {
		this.otherDedAmount2 = otherDedAmount2;
	}
	public BigDecimal getOtherDedAmount3() {
		return otherDedAmount3;
	}
	public void setOtherDedAmount3(BigDecimal otherDedAmount3) {
		this.otherDedAmount3 = otherDedAmount3;
	}
	public BigDecimal getOtherDedAmount4() {
		return otherDedAmount4;
	}
	public void setOtherDedAmount4(BigDecimal otherDedAmount4) {
		this.otherDedAmount4 = otherDedAmount4;
	}
	public BigDecimal getOtherDedAmount5() {
		return otherDedAmount5;
	}
	public void setOtherDedAmount5(BigDecimal otherDedAmount5) {
		this.otherDedAmount5 = otherDedAmount5;
	}
	public String getLoanName1() {
		return loanName1;
	}
	public void setLoanName1(String loanName1) {
		this.loanName1 = loanName1;
	}
	public String getLoanName2() {
		return loanName2;
	}
	public void setLoanName2(String loanName2) {
		this.loanName2 = loanName2;
	}
	public String getLoanName3() {
		return loanName3;
	}
	public void setLoanName3(String loanName3) {
		this.loanName3 = loanName3;
	}
	public String getLoanName4() {
		return loanName4;
	}
	public void setLoanName4(String loanName4) {
		this.loanName4 = loanName4;
	}
	public String getLoanName5() {
		return loanName5;
	}
	public void setLoanName5(String loanName5) {
		this.loanName5 = loanName5;
	}
	public BigDecimal getLoanAmount1() {
		return loanAmount1;
	}
	public void setLoanAmount1(BigDecimal loanAmount1) {
		this.loanAmount1 = loanAmount1;
	}
	public BigDecimal getLoanAmount2() {
		return loanAmount2;
	}
	public void setLoanAmount2(BigDecimal loanAmount2) {
		this.loanAmount2 = loanAmount2;
	}
	public BigDecimal getLoanAmount3() {
		return loanAmount3;
	}
	public void setLoanAmount3(BigDecimal loanAmount3) {
		this.loanAmount3 = loanAmount3;
	}
	public BigDecimal getLoanAmount4() {
		return loanAmount4;
	}
	public void setLoanAmount4(BigDecimal loanAmount4) {
		this.loanAmount4 = loanAmount4;
	}
	public BigDecimal getLoanAmount5() {
		return loanAmount5;
	}
	public void setLoanAmount5(BigDecimal loanAmount5) {
		this.loanAmount5 = loanAmount5;
	}
	public BigDecimal getCola() {
		return cola;
	}
	public void setCola(BigDecimal cola) {
		this.cola = cola;
	}
	public BigDecimal getFoodAllowance() {
		return foodAllowance;
	}
	public void setFoodAllowance(BigDecimal foodAllowance) {
		this.foodAllowance = foodAllowance;
	}
	public BigDecimal getTaxShield() {
		return taxShield;
	}
	public void setTaxShield(BigDecimal taxShield) {
		this.taxShield = taxShield;
	}
	public BigDecimal getTranpoAllowance() {
		return tranpoAllowance;
	}
	public void setTranpoAllowance(BigDecimal tranpoAllowance) {
		this.tranpoAllowance = tranpoAllowance;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public BigDecimal getNontaxableIncome() {
		return nontaxableIncome;
	}
	public void setNontaxableIncome(BigDecimal nontaxableIncome) {
		this.nontaxableIncome = nontaxableIncome;
	}
	public String getPayrollType() {
		return payrollType;
	}
	public void setPayrollType(String payrollType) {
		this.payrollType = payrollType;
	}
	public String getPayPeriod() {
		return payPeriod;
	}
	public void setPayPeriod(String payPeriod) {
		this.payPeriod = payPeriod;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTaxStatus() {
		return taxStatus;
	}
	public void setTaxStatus(String taxstatus) {
		this.taxStatus = taxstatus;
	}
	public BigDecimal getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(BigDecimal basicPay) {
		this.basicPay = basicPay;
	}
	public BigDecimal getNightDiff() {
		return nightDiff;
	}
	public void setNightDiff(BigDecimal nightDiff) {
		this.nightDiff = nightDiff;
	}
	public BigDecimal getOvertime() {
		return overtime;
	}
	public void setOvertime(BigDecimal overtime) {
		this.overtime = overtime;
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
	public BigDecimal getHolidayPay() {
		return holidayPay;
	}
	public void setHolidayPay(BigDecimal holidayPay) {
		this.holidayPay = holidayPay;
	}
	public BigDecimal getOtherTaxableIncome() {
		return otherTaxableIncome;
	}
	public void setOtherTaxableIncome(BigDecimal otherTaxableIncome) {
		this.otherTaxableIncome = otherTaxableIncome;
	}
	public BigDecimal getGsisEmployeeShare() {
		return gsisEmployeeShare;
	}
	public void setGsisEmployeeShare(BigDecimal gsisEmployeeShare) {
		this.gsisEmployeeShare = gsisEmployeeShare;
	}
	public BigDecimal getPhilhealthEmployeeShare() {
		return philhealthEmployeeShare;
	}
	public void setPhilhealthEmployeeShare(BigDecimal philhealthEmployeeShare) {
		this.philhealthEmployeeShare = philhealthEmployeeShare;
	}
	public BigDecimal getPagibigEmployeeShare() {
		return pagibigEmployeeShare;
	}
	public void setPagibigEmployeeShare(BigDecimal pagibigEmployeeShare) {
		this.pagibigEmployeeShare = pagibigEmployeeShare;
	}
	public BigDecimal getWithholdingTax() {
		return withholdingTax;
	}
	public void setWithholdingTax(BigDecimal withholdingTax) {
		this.withholdingTax = withholdingTax;
	}
	public BigDecimal getGsisEmployerShare() {
		return gsisEmployerShare;
	}
	public void setGsisEmployerShare(BigDecimal gsisEmployerShare) {
		this.gsisEmployerShare = gsisEmployerShare;
	}
	public BigDecimal getPhilhealthEmployerShare() {
		return philhealthEmployerShare;
	}
	public void setPhilhealthEmployerShare(BigDecimal philhealthEmployerShare) {
		this.philhealthEmployerShare = philhealthEmployerShare;
	}
	public BigDecimal getPagibigEmployerShare() {
		return pagibigEmployerShare;
	}
	public void setPagibigEmployerShare(BigDecimal pagibigEmployerShare) {
		this.pagibigEmployerShare = pagibigEmployerShare;
	}
	public BigDecimal getTotalEarnings() {
		return totalEarnings;
	}
	public void setTotalEarnings(BigDecimal totalEarnings) {
		this.totalEarnings = totalEarnings;
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
	
	
	

	
	
}
