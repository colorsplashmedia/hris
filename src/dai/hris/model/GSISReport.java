package dai.hris.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class GSISReport implements Serializable {

	private static final long serialVersionUID = 8653434825156632681L;

	private String empNo;
	private String empName;
	private String gsisNo;
	private String payrollPeriod;
	private BigDecimal employeeShare;
	private BigDecimal employerShare;
	private BigDecimal totalContribution;
	
	
	public String getPayrollPeriod() {
		return payrollPeriod;
	}
	public void setPayrollPeriod(String payrollPeriod) {
		this.payrollPeriod = payrollPeriod;
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
	public String getGsisNo() {
		return gsisNo;
	}
	public void setGsisNo(String gsisNo) {
		this.gsisNo = gsisNo;
	}
	public BigDecimal getEmployeeShare() {
		return employeeShare;
	}
	public void setEmployeeShare(BigDecimal employeeShare) {
		this.employeeShare = employeeShare;
	}
	public BigDecimal getEmployerShare() {
		return employerShare;
	}
	public void setEmployerShare(BigDecimal employerShare) {
		this.employerShare = employerShare;
	}
	public BigDecimal getTotalContribution() {
		return totalContribution;
	}
	public void setTotalContribution(BigDecimal totalContribution) {
		this.totalContribution = totalContribution;
	}
	
	
}
