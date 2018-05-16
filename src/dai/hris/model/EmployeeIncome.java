package dai.hris.model;

import java.math.BigDecimal;

public class EmployeeIncome {

	// link employee to income table
	private int empIncomeId;
	private int empId;
	private int incomeId;	
	private int payrollPeriodId;
	private BigDecimal amount;
	private String payrollCycle;
	private String incomeType;
	private String creationDate;
	private int createdBy;
	private String updateDate;
	private int updatedBy;
	private String active; //0 inactive, 1 active	
	private String incomeName;
	private String accountingCode;
	private String isTaxable;
	
	
	public String getAccountingCode() {
		return accountingCode;
	}
	public void setAccountingCode(String accountingCode) {
		this.accountingCode = accountingCode;
	}
	public String getIsTaxable() {
		return isTaxable;
	}
	public void setIsTaxable(String isTaxable) {
		this.isTaxable = isTaxable;
	}
	public int getEmpIncomeId() {
		return empIncomeId;
	}
	public void setEmpIncomeId(int empIncomeId) {
		this.empIncomeId = empIncomeId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getIncomeId() {
		return incomeId;
	}
	public void setIncomeId(int incomeId) {
		this.incomeId = incomeId;
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
	public String getIncomeType() {
		return incomeType;
	}
	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
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
	public String getIncomeName() {
		return incomeName;
	}
	public void setIncomeName(String incomeName) {
		this.incomeName = incomeName;
	}

	

}
