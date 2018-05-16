package dai.hris.model;

import java.sql.Date;

public class EmployeeUndertime {

	private int empUndertimeId;
	private int empId;
	private String dateRendered;
	private int noOfHours;
	private String remarks;
	private int status;
	private int approvedBy;
	private int createdBy;
	private Date createdDate;
	private String statusValue;
	private String empNo;
	private String empName;
	
	
	
	public int getEmpUndertimeId() {
		return empUndertimeId;
	}
	public void setEmpUndertimeId(int empUndertimeId) {
		this.empUndertimeId = empUndertimeId;
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
	public String getStatusValue() {
		if(this.status == 0) {
			this.statusValue = "FOR APPROVAL";
		} else if(this.status == 1) {
			this.statusValue = "NOT APPROVED";
		} else if(this.status == 2) {
			this.statusValue = "FOR UNIT SUPERVISOR APPROVAL";
		} else if(this.status == 3) {
			this.statusValue = "FOR SECTION SUPERVISOR APPROVAL";
		} else if(this.status == 4) {
			this.statusValue = "FOR HR APPROVAL";
		} else if(this.status == 5) {
			this.statusValue = "FOR ADMIN APPROVAL";
		} else if(this.status == 6) {
			this.statusValue = "APPROVED";
		} else {
			this.statusValue = "";
		}
		return statusValue;		
	}
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
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
	public int getNoOfHours() {
		return noOfHours;
	}
	public void setNoOfHours(int noOfHours) {
		this.noOfHours = noOfHours;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(int approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	
	
	
}
