package dai.hris.model;

import java.math.BigDecimal;


public class ObligationRequest {
	
	private int obligationRequestId;
	private int empId;
	private String fundCluster;
	private String obligationDate;
	private String dvNo;
	private String payee;
	private String office;
	private String address;
	private String particulars;
	private String responsibilityCenter;
	private String mfo;
	private BigDecimal amount;
	private BigDecimal totalAmount;
	private String uacsCode;
	private String amountInWords;
	private String signatory1;
	private String position1;
	private String signatory2;
	private String position2;
	
	
	private BigDecimal obligationAmount;
	private BigDecimal paymentAmount;
	private BigDecimal notDueAmount;
	private BigDecimal demandAmount;
	private String jevNo;
	private String refDate;
	private String refParticular;
	
	
	public String getDvNo() {
		return dvNo;
	}
	public void setDvNo(String dvNo) {
		this.dvNo = dvNo;
	}
	public BigDecimal getObligationAmount() {
		return obligationAmount;
	}
	public void setObligationAmount(BigDecimal obligationAmount) {
		this.obligationAmount = obligationAmount;
	}
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public BigDecimal getNotDueAmount() {
		return notDueAmount;
	}
	public void setNotDueAmount(BigDecimal notDueAmount) {
		this.notDueAmount = notDueAmount;
	}
	public BigDecimal getDemandAmount() {
		return demandAmount;
	}
	public void setDemandAmount(BigDecimal demandAmount) {
		this.demandAmount = demandAmount;
	}
	public String getRefDate() {
		return refDate;
	}
	public void setRefDate(String refDate) {
		this.refDate = refDate;
	}
	public String getRefParticular() {
		return refParticular;
	}
	public void setRefParticular(String refParticular) {
		this.refParticular = refParticular;
	}
	public int getObligationRequestId() {
		return obligationRequestId;
	}
	public void setObligationRequestId(int obligationRequestId) {
		this.obligationRequestId = obligationRequestId;
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
	public String getObligationDate() {
		return obligationDate;
	}
	public void setObligationDate(String obligationDate) {
		this.obligationDate = obligationDate;
	}
	public String getPayee() {
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
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
	public String getUacsCode() {
		return uacsCode;
	}
	public void setUacsCode(String uacsCode) {
		this.uacsCode = uacsCode;
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
	public String getJevNo() {
		return jevNo;
	}
	public void setJevNo(String jevNo) {
		this.jevNo = jevNo;
	}
	
	
	
	
		
	
}
