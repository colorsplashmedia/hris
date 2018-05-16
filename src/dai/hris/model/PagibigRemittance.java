package dai.hris.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PagibigRemittance implements Serializable{

	public PagibigRemittance() {
		// TODO Auto-generated constructor stub
	}
	private static final long serialVersionUID = 1L; 
		
	private String firstName;
	private String lastName;
	private String middleName;
	private String hireDate;
	private String pagibigId;
	private String empId;
	private String tin;
	private BigDecimal employeeShare;
	private BigDecimal employerShare;
	private BigDecimal shortTermLoan;
	private BigDecimal calamityLoan;
	private String birthDate;
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getHireDate() {
		return hireDate;
	}
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}
	public String getPagibigId() {
		return pagibigId;
	}
	public void setPagibigId(String pagibigId) {
		this.pagibigId = pagibigId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
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
	public BigDecimal getShortTermLoan() {
		return shortTermLoan;
	}
	public void setShortTermLoan(BigDecimal shortTermLoan) {
		this.shortTermLoan = shortTermLoan;
	}
	public BigDecimal getCalamityLoan() {
		return calamityLoan;
	}
	public void setCalamityLoan(BigDecimal calamityLoan) {
		this.calamityLoan = calamityLoan;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	
		
}
