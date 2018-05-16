package dai.hris.model;


public class PayrollExclusion {
	
	private int empPayrollExclusionId;
	private int empId;
	private int payrollPeriodId;	
	private String empNo;
	private String empName;
	private String payrollPeriod;
	
	
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public int getEmpPayrollExclusionId() {
		return empPayrollExclusionId;
	}
	public void setEmpPayrollExclusionId(int empPayrollExclusionId) {
		this.empPayrollExclusionId = empPayrollExclusionId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getPayrollPeriodId() {
		return payrollPeriodId;
	}
	public void setPayrollPeriodId(int payrollPeriodId) {
		this.payrollPeriodId = payrollPeriodId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getPayrollPeriod() {
		return payrollPeriod;
	}
	public void setPayrollPeriod(String payrollPeriod) {
		this.payrollPeriod = payrollPeriod;
	}
		
	
}
