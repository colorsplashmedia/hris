package dai.hris.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class MedicareShareAllowance implements Serializable {

	private static final long serialVersionUID = 6480148950279501365L;
    private int medicareShareAllowanceId;
    private int numDays;
    private BigDecimal ratePerDay;
    private BigDecimal netAmountDue;
    private String date;
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
	public int getMedicareShareAllowanceId() {
		return medicareShareAllowanceId;
	}
	public void setMedicareShareAllowanceId(int id) {
		this.medicareShareAllowanceId = id;
	}
	public int getNumDays() {
		return numDays;
	}
	public void setNumDays(int numDays) {
		this.numDays = numDays;
	}
	public BigDecimal getRatePerDay() {
		return ratePerDay;
	}
	public void setRatePerDay(BigDecimal ratePerDay) {
		this.ratePerDay = ratePerDay;
	}
	public BigDecimal getNetAmountDue() {
		return netAmountDue;
	}
	public void setNetAmountDue(BigDecimal netAmountDue) {
		this.netAmountDue = netAmountDue;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
