package dai.hris.model;

import java.io.Serializable;
import java.util.List;

public class Unit implements Serializable{

	private static final long serialVersionUID = 1L; 
	
	private int unitId;
	private String unitName;
	private int sectionId;
	private String sectionName;
	private int empId;
	private String unitHeadName;
	private int assistantId;
	private String assistantUnitHeadName;
	
	private List<SubUnit> subUnitList;

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

	public int getSectionId() {
		return sectionId;
	}

	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getUnitHeadName() {
		return unitHeadName;
	}

	public void setUnitHeadName(String unitHeadName) {
		this.unitHeadName = unitHeadName;
	}

	public int getAssistantId() {
		return assistantId;
	}

	public void setAssistantId(int assistantId) {
		this.assistantId = assistantId;
	}

	public String getAssistantUnitHeadName() {
		return assistantUnitHeadName;
	}

	public void setAssistantUnitHeadName(String assistantUnitHeadName) {
		this.assistantUnitHeadName = assistantUnitHeadName;
	}

	public List<SubUnit> getSubUnitList() {
		return subUnitList;
	}

	public void setSubUnitList(List<SubUnit> subUnitList) {
		this.subUnitList = subUnitList;
	} 
	
	
	
	
	

}
