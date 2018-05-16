package dai.hris.model;

import java.io.Serializable;

/**
 * [binId] [int] IDENTITY(1,1) NOT NULL,
	[binCode] [varchar](50) NULL,
	[binName] [varchar](50) NULL,
	[createdBy] [int] NULL,
	[creationDate] [datetime] NULL, 
 * 
 * 
 * @author Ian
 *
 */

public class SystemTrail implements Serializable{

	private static final long serialVersionUID = 1L; 
	private int systemTrailId;
	private String processType;
	private String moduleName;
	private String processDesc;
	private String transDate;
	private int userId; 
	private String userName; 
	private int departmentId; 
	private String departmentName;
	private String userFullName; 
	private String dateFrom;
	private String dateTo;
	
	
	public String getUserFullName() {
		return userFullName;
	}
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
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
	public int getSystemTrailId() {
		return systemTrailId;
	}
	public void setSystemTrailId(int systemTrailId) {
		this.systemTrailId = systemTrailId;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getProcessDesc() {
		return processDesc;
	}
	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	
	

}
