package dai.hris.model;

public class EmployeeCBADetails {
	
	private int cbaDetailsId;
	private int cbaId;
	private String majorFinalOutput;
	private String performanceIndicator;
	private String actualAccomplishment;
	private double qRating;
	private double eRating;
	private double tRating;
	private double aveRating;
	private String remarks;
	
	
	public int getCbaDetailsId() {
		return cbaDetailsId;
	}
	public void setCbaDetailsId(int cbaDetailsId) {
		this.cbaDetailsId = cbaDetailsId;
	}
	public int getCbaId() {
		return cbaId;
	}
	public void setCbaId(int cbaId) {
		this.cbaId = cbaId;
	}
	public String getMajorFinalOutput() {
		return majorFinalOutput;
	}
	public void setMajorFinalOutput(String majorFinalOutput) {
		this.majorFinalOutput = majorFinalOutput;
	}
	public String getPerformanceIndicator() {
		return performanceIndicator;
	}
	public void setPerformanceIndicator(String performanceIndicator) {
		this.performanceIndicator = performanceIndicator;
	}
	public String getActualAccomplishment() {
		return actualAccomplishment;
	}
	public void setActualAccomplishment(String actualAccomplishment) {
		this.actualAccomplishment = actualAccomplishment;
	}
	public double getqRating() {
		return qRating;
	}
	public void setqRating(double qRating) {
		this.qRating = qRating;
	}
	public double geteRating() {
		return eRating;
	}
	public void seteRating(double eRating) {
		this.eRating = eRating;
	}
	public double gettRating() {
		return tRating;
	}
	public void settRating(double tRating) {
		this.tRating = tRating;
	}
	public double getAveRating() {
		return aveRating;
	}
	public void setAveRating(double aveRating) {
		this.aveRating = aveRating;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}	
	
	
}
