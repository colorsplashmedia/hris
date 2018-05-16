package dai.hris.model;

import java.io.Serializable;

public class AttendanceReport implements Serializable {

	private static final long serialVersionUID = 8653434825156632681L;

	private String empNo;
	private String empName;
	private String shiftTimeIn;
	private String shiftTimeOut;
	private String empTimeIn;
	private String empTimeOut;
	private String remarks;
	private String scheduleDate;
	private String description;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
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
	public String getShiftTimeIn() {
		return shiftTimeIn;
	}
	public void setShiftTimeIn(String shiftTimeIn) {
		this.shiftTimeIn = shiftTimeIn;
	}
	public String getShiftTimeOut() {
		return shiftTimeOut;
	}
	public void setShiftTimeOut(String shiftTimeOut) {
		this.shiftTimeOut = shiftTimeOut;
	}
	public String getEmpTimeIn() {
		return empTimeIn;
	}
	public void setEmpTimeIn(String empTimeIn) {
		this.empTimeIn = empTimeIn;
	}
	public String getEmpTimeOut() {
		return empTimeOut;
	}
	public void setEmpTimeOut(String empTimeOut) {
		this.empTimeOut = empTimeOut;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
				
	
}
