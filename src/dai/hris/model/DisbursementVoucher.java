package dai.hris.model;

import java.math.BigDecimal;


public class DisbursementVoucher {
	
	private int disbursementVoucherId;
	private int empId;
	private String fundCluster;
	private String disbursementDate;
	private String dvNo;
	private String modeOfPayment;
	private String othersDetail;
	private String payee;
	private String tin;
	private String ors;
	private String address;
	private String particulars;
	private String responsibilityCenter;
	private String mfo;
	private BigDecimal amount;
	private BigDecimal totalAmount;
	private String accountingTitle;
	private String uacsCode;
	private BigDecimal debit;
	private BigDecimal credit;
	private String amountInWords;
	private String signatory1;
	private String position1;
	private String signatory2;
	private String position2;
	private String signatory3;
	private String position3;
	private String checkNo;
	private String checkDate;
	private String bankDetails;
	private String orNo;
	private String orDate;
	private String jevNo;
	private String certifiedMethod;
	private String printedName;
	private int payrollPeriodId;
	
	
	
	public int getDisbursementVoucherId() {
		return disbursementVoucherId;
	}
	public void setDisbursementVoucherId(int disbursementVoucherId) {
		this.disbursementVoucherId = disbursementVoucherId;
	}
	public int getPayrollPeriodId() {
		return payrollPeriodId;
	}
	public void setPayrollPeriodId(int payrollPeriodId) {
		this.payrollPeriodId = payrollPeriodId;
	}
	public String getPrintedName() {
		return printedName;
	}
	public void setPrintedName(String printedName) {
		this.printedName = printedName;
	}
	public String getCertifiedMethod() {
		return certifiedMethod;
	}
	public void setCertifiedMethod(String certifiedMethod) {
		this.certifiedMethod = certifiedMethod;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getFundCluster() {
		return fundCluster;
	}
	public void setFundCluster(String fundCluster) {
		this.fundCluster = fundCluster;
	}
	public String getDisbursementDate() {
		return disbursementDate;
	}
	public void setDisbursementDate(String disbursementDate) {
		this.disbursementDate = disbursementDate;
	}
	public String getDvNo() {
		return dvNo;
	}
	public void setDvNo(String dvNo) {
		this.dvNo = dvNo;
	}
	public String getModeOfPayment() {
		return modeOfPayment;
	}
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}
	public String getOthersDetail() {
		return othersDetail;
	}
	public void setOthersDetail(String othersDetail) {
		this.othersDetail = othersDetail;
	}
	public String getPayee() {
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
	}
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}
	public String getOrs() {
		return ors;
	}
	public void setOrs(String ors) {
		this.ors = ors;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getParticulars() {
		return particulars;
	}
	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	public String getResponsibilityCenter() {
		return responsibilityCenter;
	}
	public void setResponsibilityCenter(String responsibilityCenter) {
		this.responsibilityCenter = responsibilityCenter;
	}
	public String getMfo() {
		return mfo;
	}
	public void setMfo(String mfo) {
		this.mfo = mfo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getAccountingTitle() {
		return accountingTitle;
	}
	public void setAccountingTitle(String accountingTitle) {
		this.accountingTitle = accountingTitle;
	}
	public String getUacsCode() {
		return uacsCode;
	}
	public void setUacsCode(String uacsCode) {
		this.uacsCode = uacsCode;
	}
	public BigDecimal getDebit() {
		return debit;
	}
	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}
	public BigDecimal getCredit() {
		return credit;
	}
	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}
	public String getAmountInWords() {
		return amountInWords;
	}
	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}
	public String getSignatory1() {
		return signatory1;
	}
	public void setSignatory1(String signatory1) {
		this.signatory1 = signatory1;
	}
	public String getPosition1() {
		return position1;
	}
	public void setPosition1(String position1) {
		this.position1 = position1;
	}
	public String getSignatory2() {
		return signatory2;
	}
	public void setSignatory2(String signatory2) {
		this.signatory2 = signatory2;
	}
	public String getPosition2() {
		return position2;
	}
	public void setPosition2(String position2) {
		this.position2 = position2;
	}
	public String getSignatory3() {
		return signatory3;
	}
	public void setSignatory3(String signatory3) {
		this.signatory3 = signatory3;
	}
	public String getPosition3() {
		return position3;
	}
	public void setPosition3(String position3) {
		this.position3 = position3;
	}
	public String getCheckNo() {
		return checkNo;
	}
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getBankDetails() {
		return bankDetails;
	}
	public void setBankDetails(String bankDetails) {
		this.bankDetails = bankDetails;
	}
	public String getOrNo() {
		return orNo;
	}
	public void setOrNo(String orNo) {
		this.orNo = orNo;
	}
	public String getOrDate() {
		return orDate;
	}
	public void setOrDate(String orDate) {
		this.orDate = orDate;
	}
	public String getJevNo() {
		return jevNo;
	}
	public void setJevNo(String jevNo) {
		this.jevNo = jevNo;
	}
	
		
	
}
