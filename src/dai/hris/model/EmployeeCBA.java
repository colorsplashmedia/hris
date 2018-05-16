package dai.hris.model;


public class EmployeeCBA {
	private int cbaId;
	private int empId;
	private int performanceYear;
	private int monthFrom;
	private int monthTo;
	private String assessmentDate;
	private String comments;
	private int approvedBy;
	private int assessedBy;
	private int finalRatingBy;
	private String approvedByName;
	private String assessedByName;
	private String finalRatingByName;
	private double finalRating;
	
	
	public int getCbaId() {
		return cbaId;
	}
	public void setCbaId(int cbaId) {
		this.cbaId = cbaId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getPerformanceYear() {
		return performanceYear;
	}
	public void setPerformanceYear(int performanceYear) {
		this.performanceYear = performanceYear;
	}
	public int getMonthFrom() {
		return monthFrom;
	}
	public void setMonthFrom(int monthFrom) {
		this.monthFrom = monthFrom;
	}
	public int getMonthTo() {
		return monthTo;
	}
	public void setMonthTo(int monthTo) {
		this.monthTo = monthTo;
	}
	public String getAssessmentDate() {
		return assessmentDate;
	}
	public void setAssessmentDate(String assessmentDate) {
		this.assessmentDate = assessmentDate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(int approvedBy) {
		this.approvedBy = approvedBy;
	}
	public int getAssessedBy() {
		return assessedBy;
	}
	public void setAssessedBy(int assessedBy) {
		this.assessedBy = assessedBy;
	}
	public int getFinalRatingBy() {
		return finalRatingBy;
	}
	public void setFinalRatingBy(int finalRatingBy) {
		this.finalRatingBy = finalRatingBy;
	}
	public String getApprovedByName() {
		return approvedByName;
	}
	public void setApprovedByName(String approvedByName) {
		this.approvedByName = approvedByName;
	}
	public String getAssessedByName() {
		return assessedByName;
	}
	public void setAssessedByName(String assessedByName) {
		this.assessedByName = assessedByName;
	}
	public String getFinalRatingByName() {
		return finalRatingByName;
	}
	public void setFinalRatingByName(String finalRatingByName) {
		this.finalRatingByName = finalRatingByName;
	}
	public double getFinalRating() {
		return finalRating;
	}
	public void setFinalRating(double finalRating) {
		this.finalRating = finalRating;
	}	
		
	
}
