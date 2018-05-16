package dai.hris.model;

import java.io.Serializable;
import java.util.List;

public class Section implements Serializable{

	private static final long serialVersionUID = 1L; 
	private int sectionId;
	private String sectionName;
	private int empId;
	private String sectionHeadName;
	private int assistantId;
	private String assistantSectionHeadName;
	
	private List<Unit> unitList;
	
	

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

	public String getSectionHeadName() {
		return sectionHeadName;
	}

	public void setSectionHeadName(String sectionHeadName) {
		this.sectionHeadName = sectionHeadName;
	}

	public int getAssistantId() {
		return assistantId;
	}

	public void setAssistantId(int assistantId) {
		this.assistantId = assistantId;
	}

	public String getAssistantSectionHeadName() {
		return assistantSectionHeadName;
	}

	public void setAssistantSectionHeadName(String assistantSectionHeadName) {
		this.assistantSectionHeadName = assistantSectionHeadName;
	}

	public List<Unit> getUnitList() {
		return unitList;
	}

	public void setUnitList(List<Unit> unitList) {
		this.unitList = unitList;
	} 
	
	
	

}
