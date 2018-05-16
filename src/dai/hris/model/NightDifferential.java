package dai.hris.model;

import java.math.BigDecimal;

/*
 * 	[absentId] [int] NOT NULL,
 [empNo] [int] NULL,
 [absentDate] [datetime] NULL,
 [superVisorId] [int] NULL,
 [remarks] [varchar](100) NULL,
 */

public class NightDifferential {
	private int empNightDiffId;
	private int empId;
	private String dateRendered;
	private double noOfHours;
	private double holidayRate;
	private double hourlyRate;
	private int payrollPeriodId;
	private BigDecimal nightDiffAmount;
	
	private String empName;
	private String empNo;
	private String payrollPeriod;
	
	
	public String getPayrollPeriod() {
		return payrollPeriod;
	}
	public void setPayrollPeriod(String payrollPeriod) {
		this.payrollPeriod = payrollPeriod;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public int getEmpNightDiffId() {
		return empNightDiffId;
	}
	public void setEmpNightDiffId(int empNightDiffId) {
		this.empNightDiffId = empNightDiffId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getDateRendered() {
		return dateRendered;
	}
	public void setDateRendered(String dateRendered) {
		this.dateRendered = dateRendered;
	}
	public double getNoOfHours() {
		return noOfHours;
	}
	public void setNoOfHours(double noOfHours) {
		this.noOfHours = noOfHours;
	}
	public double getHolidayRate() {
		return holidayRate;
	}
	public void setHolidayRate(double holidayRate) {
		this.holidayRate = holidayRate;
	}
	public double getHourlyRate() {
		return hourlyRate;
	}
	public void setHourlyRate(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	public int getPayrollPeriodId() {
		return payrollPeriodId;
	}
	public void setPayrollPeriodId(int payrollPeriodId) {
		this.payrollPeriodId = payrollPeriodId;
	}
	public BigDecimal getNightDiffAmount() {
		return nightDiffAmount;
	}
	public void setNightDiffAmount(BigDecimal nightDiffAmount) {
		this.nightDiffAmount = nightDiffAmount;
	}

	
}
