package dai.hris.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class LongevityPay implements Serializable {

	private static final long serialVersionUID = 2724890765748441547L;
	private int longevityPayId;
	private int salaryGrade;
	private BigDecimal basicSalary;
	private BigDecimal netAmountDue;
	private int year;
	private int month;
	private String remarks;
	private int empId;
	
	private String empNo;
    private String empName;
    private int createdBy;
    
    
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
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
	public int getLongevityPayId() {
		return longevityPayId;
	}
	public void setLongevityPayId(int id) {
		this.longevityPayId = id;
	}
	public int getSalaryGrade() {
		return salaryGrade;
	}
	public void setSalaryGrade(int salaryGrade) {
		this.salaryGrade = salaryGrade;
	}
	public BigDecimal getBasicSalary() {
		return basicSalary;
	}
	public void setBasicSalary(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
	}
	public BigDecimal getNetAmountDue() {
		return netAmountDue;
	}
	public void setNetAmountDue(BigDecimal netAmountDue) {
		this.netAmountDue = netAmountDue;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int employeeId) {
		this.empId = employeeId;
	}
}
