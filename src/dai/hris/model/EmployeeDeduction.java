package dai.hris.model;

import java.math.BigDecimal;

public class EmployeeDeduction {

	// link employee to deduction table
	private int empDeductionId;
	private int empId;
	private int deductionId;
	private int payrollPeriodId;
	private BigDecimal amount;
	private String payrollCycle;
	private String deductionType;
	private String creationDate;
	private int createdBy;
	private String updateDate;
	private int updatedBy;
	private String active; //0 inactive, 1 active	
	private String deductionName;
	
	private String accountingCode;
	
	
	
	public String getAccountingCode() {
		return accountingCode;
	}

	public void setAccountingCode(String accountingCode) {
		this.accountingCode = accountingCode;
	}

	public int getEmpDeductionId() {
		return empDeductionId;
	}

	public void setEmpDeductionId(int empDeductionId) {
		this.empDeductionId = empDeductionId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getDeductionId() {
		return deductionId;
	}

	public void setDeductionId(int deductionId) {
		this.deductionId = deductionId;
	}

	public int getPayrollPeriodId() {
		return payrollPeriodId;
	}

	public void setPayrollPeriodId(int payrollPeriodId) {
		this.payrollPeriodId = payrollPeriodId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	

	public String getPayrollCycle() {
		return payrollCycle;
	}

	public void setPayrollCycle(String payrollCycle) {
		this.payrollCycle = payrollCycle;
	}

	public String getDeductionType() {
		return deductionType;
	}

	public void setDeductionType(String deductionType) {
		this.deductionType = deductionType;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDeductionName() {
		return deductionName;
	}

	public void setDeductionName(String deductionName) {
		this.deductionName = deductionName;
	}

	

}
