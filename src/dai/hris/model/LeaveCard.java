package dai.hris.model;

import java.io.Serializable;

public class LeaveCard implements Serializable{

	public LeaveCard() {
		// TODO Auto-generated constructor stub
	}
	private static final long serialVersionUID = 1L; 
		
	private int leaveCreditId;
	private int empId;
	private String period;
	private String particulars;
	private double vEarned;
	private double vAbsenceWithPay;
	private double vBalanceINCL;
	private double vBalanceEXCL;
	private double vAbsenceWithOutPay;
	private double sEarned;
	private double sAbsenceWithPay;
	private double sBalanceINCL;
	private double sBalanceEXCL;
	private double sAbsenceWithOutPay;
	private double exVacation;
	private double exSick;
	private String remarks;
	private int createdBy;
	
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public int getLeaveCreditId() {
		return leaveCreditId;
	}
	public void setLeaveCreditId(int leaveCreditId) {
		this.leaveCreditId = leaveCreditId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getParticulars() {
		return particulars;
	}
	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	public double getvEarned() {
		return vEarned;
	}
	public void setvEarned(double vEarned) {
		this.vEarned = vEarned;
	}
	public double getvAbsenceWithPay() {
		return vAbsenceWithPay;
	}
	public void setvAbsenceWithPay(double vAbsenceWithPay) {
		this.vAbsenceWithPay = vAbsenceWithPay;
	}
	public double getvBalanceINCL() {
		return vBalanceINCL;
	}
	public void setvBalanceINCL(double vBalanceINCL) {
		this.vBalanceINCL = vBalanceINCL;
	}
	public double getvBalanceEXCL() {
		return vBalanceEXCL;
	}
	public void setvBalanceEXCL(double vBalanceEXCL) {
		this.vBalanceEXCL = vBalanceEXCL;
	}
	public double getvAbsenceWithOutPay() {
		return vAbsenceWithOutPay;
	}
	public void setvAbsenceWithOutPay(double vAbsenceWithOutPay) {
		this.vAbsenceWithOutPay = vAbsenceWithOutPay;
	}
	public double getsEarned() {
		return sEarned;
	}
	public void setsEarned(double sEarned) {
		this.sEarned = sEarned;
	}
	public double getsAbsenceWithPay() {
		return sAbsenceWithPay;
	}
	public void setsAbsenceWithPay(double sAbsenceWithPay) {
		this.sAbsenceWithPay = sAbsenceWithPay;
	}
	public double getsBalanceINCL() {
		return sBalanceINCL;
	}
	public void setsBalanceINCL(double sBalanceINCL) {
		this.sBalanceINCL = sBalanceINCL;
	}
	public double getsBalanceEXCL() {
		return sBalanceEXCL;
	}
	public void setsBalanceEXCL(double sBalanceEXCL) {
		this.sBalanceEXCL = sBalanceEXCL;
	}
	public double getsAbsenceWithOutPay() {
		return sAbsenceWithOutPay;
	}
	public void setsAbsenceWithOutPay(double sAbsenceWithOutPay) {
		this.sAbsenceWithOutPay = sAbsenceWithOutPay;
	}
	public double getExVacation() {
		return exVacation;
	}
	public void setExVacation(double exVacation) {
		this.exVacation = exVacation;
	}
	public double getExSick() {
		return exSick;
	}
	public void setExSick(double exSick) {
		this.exSick = exSick;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}	
	
		
}
