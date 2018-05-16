package dai.hris.model;

public class Resolution {
	private int timeEntryId;
	private String resolutionCategory;
	private String clockDate;
	private int empId;
	private String resolutionType;
	private int shiftScheduleId;
	private String timeInHrs;
	private String timeOutHrs;
	private String resolutionRemarks;
	private int resolvedBy;
	private String empName;
	private String approvedBy;	
	private String approvalStatus;
	private String scheduleDesc;
	private String oldScheduleDesc;
	private int empScheduleDisputeId;
	
	
	public int getEmpScheduleDisputeId() {
		return empScheduleDisputeId;
	}
	public void setEmpScheduleDisputeId(int empScheduleDisputeId) {
		this.empScheduleDisputeId = empScheduleDisputeId;
	}
	public String getOldScheduleDesc() {
		return oldScheduleDesc;
	}
	public void setOldScheduleDesc(String oldScheduleDesc) {
		this.oldScheduleDesc = oldScheduleDesc;
	}
	public String getScheduleDesc() {
		return scheduleDesc;
	}
	public void setScheduleDesc(String scheduleDesc) {
		this.scheduleDesc = scheduleDesc;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public int getTimeEntryId() {
		return timeEntryId;
	}
	public void setTimeEntryId(int timeEntryId) {
		this.timeEntryId = timeEntryId;
	}
	public String getResolutionCategory() {
		return resolutionCategory;
	}
	public void setResolutionCategory(String resolutionCategory) {
		this.resolutionCategory = resolutionCategory;
	}
	public String getClockDate() {
		return clockDate;
	}
	public void setClockDate(String clockDate) {
		this.clockDate = clockDate;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getResolutionType() {
		return resolutionType;
	}
	public void setResolutionType(String resolutionType) {
		this.resolutionType = resolutionType;
	}
	public int getShiftScheduleId() {
		return shiftScheduleId;
	}
	public void setShiftScheduleId(int shiftScheduleId) {
		this.shiftScheduleId = shiftScheduleId;
	}
	
	public String getTimeInHrs() {
		return timeInHrs;
	}
	public void setTimeInHrs(String timeInHrs) {
		this.timeInHrs = timeInHrs;
	}
	public String getTimeOutHrs() {
		return timeOutHrs;
	}
	public void setTimeOutHrs(String timeOutHrs) {
		this.timeOutHrs = timeOutHrs;
	}
	public String getResolutionRemarks() {
		return resolutionRemarks;
	}
	public void setResolutionRemarks(String resolutionRemarks) {
		this.resolutionRemarks = resolutionRemarks;
	}
	public int getResolvedBy() {
		return resolvedBy;
	}
	public void setResolvedBy(int resolvedBy) {
		this.resolvedBy = resolvedBy;
	}
	
	
}
