package dai.hris.model;

import java.math.BigDecimal;
import java.util.List;

public class SystemParameters {
	
	private int adminId;
	private int adminAssistantId;
	private int hrAdminId;
	private int hrAdminAssistantId;
	private int hrAdminLiasonId;
	private String isAdminChecked;
	
	private String adminName;
	private String adminAssistantName;
	private String hrAdminName;
	private String hrAdminAssistantName;
	private String hrAdminLiasonName;
	
	private int regHrs;
	private int partimeHrs;
	private int contractualHrs;
	private int contractualBreakHrs;
	private String isNightDiffContractual;
	private BigDecimal minPay;
	
	private List<Section> sectionHeadList;
	private List<Unit> unitHeadList;
	private List<SubUnit> subUnitHeadList;
	
	
	public String getIsNightDiffContractual() {
		return isNightDiffContractual;
	}
	public void setIsNightDiffContractual(String isNightDiffContractual) {
		this.isNightDiffContractual = isNightDiffContractual;
	}
	public int getContractualHrs() {
		return contractualHrs;
	}
	public void setContractualHrs(int contractualHrs) {
		this.contractualHrs = contractualHrs;
	}
	public int getContractualBreakHrs() {
		return contractualBreakHrs;
	}
	public void setContractualBreakHrs(int contractualBreakHrs) {
		this.contractualBreakHrs = contractualBreakHrs;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminAssistantName() {
		return adminAssistantName;
	}
	public void setAdminAssistantName(String adminAssistantName) {
		this.adminAssistantName = adminAssistantName;
	}
	public String getHrAdminName() {
		return hrAdminName;
	}
	public void setHrAdminName(String hrAdminName) {
		this.hrAdminName = hrAdminName;
	}
	public String getHrAdminAssistantName() {
		return hrAdminAssistantName;
	}
	public void setHrAdminAssistantName(String hrAdminAssistantName) {
		this.hrAdminAssistantName = hrAdminAssistantName;
	}
	public String getHrAdminLiasonName() {
		return hrAdminLiasonName;
	}
	public void setHrAdminLiasonName(String hrAdminLiasonName) {
		this.hrAdminLiasonName = hrAdminLiasonName;
	}
	public int getRegHrs() {
		return regHrs;
	}
	public void setRegHrs(int regHrs) {
		this.regHrs = regHrs;
	}
	public int getPartimeHrs() {
		return partimeHrs;
	}
	public void setPartimeHrs(int partimeHrs) {
		this.partimeHrs = partimeHrs;
	}
	public BigDecimal getMinPay() {
		return minPay;
	}
	public void setMinPay(BigDecimal minPay) {
		this.minPay = minPay;
	}
	public String getIsAdminChecked() {
		return isAdminChecked;
	}
	public void setIsAdminChecked(String isAdminChecked) {
		this.isAdminChecked = isAdminChecked;
	}
	public int getAdminAssistantId() {
		return adminAssistantId;
	}
	public void setAdminAssistantId(int adminAssistantId) {
		this.adminAssistantId = adminAssistantId;
	}
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public int getHrAdminId() {
		return hrAdminId;
	}
	public void setHrAdminId(int hrAdminId) {
		this.hrAdminId = hrAdminId;
	}
	public int getHrAdminAssistantId() {
		return hrAdminAssistantId;
	}
	public void setHrAdminAssistantId(int hrAdminAssistantId) {
		this.hrAdminAssistantId = hrAdminAssistantId;
	}
	public int getHrAdminLiasonId() {
		return hrAdminLiasonId;
	}
	public void setHrAdminLiasonId(int hrAdminLiasonId) {
		this.hrAdminLiasonId = hrAdminLiasonId;
	}
	public List<Section> getSectionHeadList() {
		return sectionHeadList;
	}
	public void setSectionHeadList(List<Section> sectionHeadList) {
		this.sectionHeadList = sectionHeadList;
	}
	public List<Unit> getUnitHeadList() {
		return unitHeadList;
	}
	public void setUnitHeadList(List<Unit> unitHeadList) {
		this.unitHeadList = unitHeadList;
	}
	public List<SubUnit> getSubUnitHeadList() {
		return subUnitHeadList;
	}
	public void setSubUnitHeadList(List<SubUnit> subUnitHeadList) {
		this.subUnitHeadList = subUnitHeadList;
	}	

}
