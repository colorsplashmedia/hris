package dai.hris.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ServiceRecord implements Serializable{

	public ServiceRecord() {
		// TODO Auto-generated constructor stub
	}
	private static final long serialVersionUID = 1L; 
	
	
	private int serviceRecordId;
	private int empId;
	private String dateFrom;
	private String dateTo;
	private int jobTitleId;
	private String jobTitle;
	private String status;
	private BigDecimal salary;
	private String placeOfAssignment;
	private String branch;
	private String wop;
	private String causeRemarks;	
	private String creationDate;
	private int createdBy;
	
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getWop() {
		return wop;
	}
	public void setWop(String wop) {
		this.wop = wop;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public int getServiceRecordId() {
		return serviceRecordId;
	}
	public void setServiceRecordId(int serviceRecordId) {
		this.serviceRecordId = serviceRecordId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public int getJobTitleId() {
		return jobTitleId;
	}
	public void setJobTitleId(int jobTitleId) {
		this.jobTitleId = jobTitleId;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	public String getPlaceOfAssignment() {
		return placeOfAssignment;
	}
	public void setPlaceOfAssignment(String placeOfAssignment) {
		this.placeOfAssignment = placeOfAssignment;
	}
	public String getCauseRemarks() {
		return causeRemarks;
	}
	public void setCauseRemarks(String causeRemarks) {
		this.causeRemarks = causeRemarks;
	}
	
	
	
	
	
	
	
}
