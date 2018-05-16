package dai.hris.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayrollProofList implements Serializable{

	public PayrollProofList() {
		// TODO Auto-generated constructor stub
	}
	private static final long serialVersionUID = 1L; 
		
	private String empName;
	private String accountNo;
	private BigDecimal amount;
	
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
		
}
