package dai.hris.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ContributionSummaryReport implements Serializable {

	private static final long serialVersionUID = 8653434825156632681L;

	private String empNo;
	private String empName;
	private String payrollPeriod;
	private BigDecimal gsisShare;
	private BigDecimal hdmfShare;
	private BigDecimal phicShare;
	private BigDecimal withHoldingTax;
	
	
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
	public BigDecimal getGsisShare() {
		return gsisShare;
	}
	public void setGsisShare(BigDecimal gsisShare) {
		this.gsisShare = gsisShare;
	}
	public BigDecimal getHdmfShare() {
		return hdmfShare;
	}
	public void setHdmfShare(BigDecimal hdmfShare) {
		this.hdmfShare = hdmfShare;
	}
	public BigDecimal getPhicShare() {
		return phicShare;
	}
	public void setPhicShare(BigDecimal phicShare) {
		this.phicShare = phicShare;
	}
	public BigDecimal getWithHoldingTax() {
		return withHoldingTax;
	}
	public void setWithHoldingTax(BigDecimal withHoldingTax) {
		this.withHoldingTax = withHoldingTax;
	}
	
	
	
}
