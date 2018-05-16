package dai.hris.model;

import java.io.Serializable;

public class JobTitle  implements Serializable {
	private static final long serialVersionUID = 1L; 
	private int jobTitleId;
	private String jobTitle;
	private int salaryGradeId;
	
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
	public int getSalaryGradeId() {
		return salaryGradeId;
	}
	public void setSalaryGradeId(int salaryGradeId) {
		this.salaryGradeId = salaryGradeId;
	}
	
	
	
	

}
