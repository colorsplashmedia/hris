package dai.hris.model;

import java.io.Serializable;

public class SubUnit implements Serializable{

	private static final long serialVersionUID = 1L; 
	
	private int subUnitId;
	private String subUnitName;
	private int unitId;
	private String unitName;	
	private int empId;
	private String subUnitHeadName;
	private int assistantId;
	private String assistantSubUnitHeadName;
	public int getSubUnitId() {
		return subUnitId;
	}
	public void setSubUnitId(int subUnitId) {
		this.subUnitId = subUnitId;
	}
	public String getSubUnitName() {
		return subUnitName;
	}
	public void setSubUnitName(String subUnitName) {
		this.subUnitName = subUnitName;
	}
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getSubUnitHeadName() {
		return subUnitHeadName;
	}
	public void setSubUnitHeadName(String subUnitHeadName) {
		this.subUnitHeadName = subUnitHeadName;
	}
	public int getAssistantId() {
		return assistantId;
	}
	public void setAssistantId(int assistantId) {
		this.assistantId = assistantId;
	}
	public String getAssistantSubUnitHeadName() {
		return assistantSubUnitHeadName;
	}
	public void setAssistantSubUnitHeadName(String assistantSubUnitHeadName) {
		this.assistantSubUnitHeadName = assistantSubUnitHeadName;
	}	

}
