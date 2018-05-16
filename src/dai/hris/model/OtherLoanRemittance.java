package dai.hris.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OtherLoanRemittance implements Serializable{

	public OtherLoanRemittance() {
		// TODO Auto-generated constructor stub
	}
	private static final long serialVersionUID = 1L; 
		
	private String empName;
	private BigDecimal amountRemitted;
	private int installmentCounter;
	
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public BigDecimal getAmountRemitted() {
		return amountRemitted;
	}
	public void setAmountRemitted(BigDecimal amountRemitted) {
		this.amountRemitted = amountRemitted;
	}
	public int getInstallmentCounter() {
		return installmentCounter;
	}
	public void setInstallmentCounter(int installmentCounter) {
		this.installmentCounter = installmentCounter;
	}
	
		
}
