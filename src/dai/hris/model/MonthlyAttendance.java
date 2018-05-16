package dai.hris.model;

import java.io.Serializable;

public class MonthlyAttendance implements Serializable{

	public MonthlyAttendance() {
		// TODO Auto-generated constructor stub
	}
	private static final long serialVersionUID = 1L; 
		
	private String empName;
	private int sickDays;
	private int vacationDays;
	private int hours;
	private int mins;
	private int undertime;
	private int dayEquivalent;
	private String datesAbsence;
	private String remarks;
	
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public int getSickDays() {
		return sickDays;
	}
	public void setSickDays(int sickDays) {
		this.sickDays = sickDays;
	}
	public int getVacationDays() {
		return vacationDays;
	}
	public void setVacationDays(int vacationDays) {
		this.vacationDays = vacationDays;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public int getMins() {
		return mins;
	}
	public void setMins(int mins) {
		this.mins = mins;
	}
	public int getUndertime() {
		return undertime;
	}
	public void setUndertime(int undertime) {
		this.undertime = undertime;
	}
	public int getDayEquivalent() {
		return dayEquivalent;
	}
	public void setDayEquivalent(int dayEquivalent) {
		this.dayEquivalent = dayEquivalent;
	}
	public String getDatesAbsence() {
		return datesAbsence;
	}
	public void setDatesAbsence(String datesAbsence) {
		this.datesAbsence = datesAbsence;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
		
}
