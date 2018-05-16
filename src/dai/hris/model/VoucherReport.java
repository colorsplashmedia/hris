package dai.hris.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class VoucherReport implements Serializable{

	public VoucherReport() {
		// TODO Auto-generated constructor stub
	}
	private static final long serialVersionUID = 1L; 
		
	//NOT YER COMPLETE
	private String empName;
	private String modeOfPayment;
	private String payee;
	private String tinNo;
	private String ors;
	private String payeeAddress;
	private String particulars;
	private String responsibilityCenter;
	private String mfo;
	private String pap;	
	private BigDecimal amount;
	private BigDecimal totalAmount;	
	private int supervisorId;
	
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	
		
}
