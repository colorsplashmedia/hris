package dai.hris.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class AlphaListReport implements Serializable {

	private static final long serialVersionUID = 8653434825156632681L;

	private String empNo;
	private String empName;
	private String tinNo;
	private BigDecimal gross;
	private BigDecimal nonTax13thMonth;
	private BigDecimal govtDeductions;
	private BigDecimal diminis;
	private BigDecimal taxable13thMonth;
	private BigDecimal otherTaxableIncome;
	private BigDecimal netCompensation;
	private BigDecimal exemptions;
	private BigDecimal netTaxableCompensation;
	private BigDecimal incomeTaxDue;
	private BigDecimal incomeTaxWithHeld;
	private BigDecimal incomeTaxPayable;
	private BigDecimal incomeTaxRefund;
	
	
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
	public String getTinNo() {
		return tinNo;
	}
	public void setTinNo(String tinNo) {
		this.tinNo = tinNo;
	}
	public BigDecimal getGross() {
		return gross;
	}
	public void setGross(BigDecimal gross) {
		this.gross = gross;
	}
	public BigDecimal getNonTax13thMonth() {
		return nonTax13thMonth;
	}
	public void setNonTax13thMonth(BigDecimal nonTax13thMonth) {
		this.nonTax13thMonth = nonTax13thMonth;
	}
	public BigDecimal getGovtDeductions() {
		return govtDeductions;
	}
	public void setGovtDeductions(BigDecimal govtDeductions) {
		this.govtDeductions = govtDeductions;
	}
	public BigDecimal getDiminis() {
		return diminis;
	}
	public void setDiminis(BigDecimal diminis) {
		this.diminis = diminis;
	}
	public BigDecimal getTaxable13thMonth() {
		return taxable13thMonth;
	}
	public void setTaxable13thMonth(BigDecimal taxable13thMonth) {
		this.taxable13thMonth = taxable13thMonth;
	}
	public BigDecimal getOtherTaxableIncome() {
		return otherTaxableIncome;
	}
	public void setOtherTaxableIncome(BigDecimal otherTaxableIncome) {
		this.otherTaxableIncome = otherTaxableIncome;
	}
	public BigDecimal getNetCompensation() {
		return netCompensation;
	}
	public void setNetCompensation(BigDecimal netCompensation) {
		this.netCompensation = netCompensation;
	}
	public BigDecimal getExemptions() {
		return exemptions;
	}
	public void setExemptions(BigDecimal exemptions) {
		this.exemptions = exemptions;
	}
	public BigDecimal getNetTaxableCompensation() {
		return netTaxableCompensation;
	}
	public void setNetTaxableCompensation(BigDecimal netTaxableCompensation) {
		this.netTaxableCompensation = netTaxableCompensation;
	}
	public BigDecimal getIncomeTaxDue() {
		return incomeTaxDue;
	}
	public void setIncomeTaxDue(BigDecimal incomeTaxDue) {
		this.incomeTaxDue = incomeTaxDue;
	}
	public BigDecimal getIncomeTaxWithHeld() {
		return incomeTaxWithHeld;
	}
	public void setIncomeTaxWithHeld(BigDecimal incomeTaxWithHeld) {
		this.incomeTaxWithHeld = incomeTaxWithHeld;
	}
	public BigDecimal getIncomeTaxPayable() {
		return incomeTaxPayable;
	}
	public void setIncomeTaxPayable(BigDecimal incomeTaxPayable) {
		this.incomeTaxPayable = incomeTaxPayable;
	}
	public BigDecimal getIncomeTaxRefund() {
		return incomeTaxRefund;
	}
	public void setIncomeTaxRefund(BigDecimal incomeTaxRefund) {
		this.incomeTaxRefund = incomeTaxRefund;
	}
				
	
}
