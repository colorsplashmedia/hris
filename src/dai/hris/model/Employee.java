package dai.hris.model;

import java.math.BigDecimal;
import java.sql.Date;




public class Employee {
	private int empId;
	private String empNo; //required field
	private String plantillaNo;
	private String lastname;
	private String firstname;
	private String middlename;
	private String username;
	private String password;
	private String dateOfBirth;
	private String gender;
	private String civilStatus;
	private String nationality;
	private String street;
	private int cityId;
	private String email;
	private String mobileNo;
	private String telNo;
	private String birthPlace;
	private int provinceId;
	private int countryId;
	private String zipCode;
	private int jobTitleId;
//	private int departmentId;
//	private int divisionId;
	private int employeeTypeId;
	private String empStatus;
	private String employmentDate;
	private String endOfContract;
	
	
	private String sss;
	private String gsis;
	private String hdmf;
	private String tin;
	private String phic;
	private String taxstatus;
	private String picLoc;
//	private int superVisor1Id;
//	private int superVisor2Id;
//	private int superVisor3Id;
	private Date creationDate;
	private String createdBy;
	
	private String jobTitleName;
//	private String departmentName;
//	private String divisionName;
	
	private String superVisorName;

	//Payroll Info
	private BigDecimal monthlyRate;
	private BigDecimal dailyRate;
	private BigDecimal hourlyRate;	
	private BigDecimal foodAllowance;	
	private BigDecimal cola;
	private BigDecimal taxShield;
	private BigDecimal transAllowance;
	
	
	private BigDecimal gsisEmployeeShare;
	private BigDecimal gsisEmployerShare;
	private BigDecimal philhealthEmployeeShare;
	private BigDecimal philhealthEmployerShare;
	private BigDecimal pagibigEmployeeShare;
	private BigDecimal pagibigEmployerShare;
	
	private String payrollType;
	private String ban;
	private String bankNameBan;
	private int shiftingScheduleId;
	
	
	private int sectionId;
	private int unitId;
	private int subUnitId;
	
	private String sectionName;
	private String unitName;
	private String subUnitName;
	private String personnelType;
	
	private String hasNightDifferential;
	private String hasHolidayPay;
	private String crn;
	
	
	
	public String getCrn() {
		return crn;
	}
	public void setCrn(String crn) {
		this.crn = crn;
	}
	public String getHasNightDifferential() {
		return hasNightDifferential;
	}
	public void setHasNightDifferential(String hasNightDifferential) {
		this.hasNightDifferential = hasNightDifferential;
	}
	public String getHasHolidayPay() {
		return hasHolidayPay;
	}
	public void setHasHolidayPay(String hasHolidayPay) {
		this.hasHolidayPay = hasHolidayPay;
	}
	public String getPlantillaNo() {
		return plantillaNo;
	}
	public void setPlantillaNo(String plantillaNo) {
		this.plantillaNo = plantillaNo;
	}
	public String getPersonnelType() {
		return personnelType;
	}
	public void setPersonnelType(String personnelType) {
		this.personnelType = personnelType;
	}
	public int getSectionId() {
		return sectionId;
	}
	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	public int getSubUnitId() {
		return subUnitId;
	}
	public void setSubUnitId(int subUnitId) {
		this.subUnitId = subUnitId;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getSubUnitName() {
		return subUnitName;
	}
	public void setSubUnitName(String subUnitName) {
		this.subUnitName = subUnitName;
	}
	public BigDecimal getMonthlyRate() {
		return monthlyRate;
	}
	public void setMonthlyRate(BigDecimal monthlyRate) {
		this.monthlyRate = monthlyRate;
	}
	public BigDecimal getDailyRate() {
		return dailyRate;
	}
	public void setDailyRate(BigDecimal dailyRate) {
		this.dailyRate = dailyRate;
	}
	public BigDecimal getHourlyRate() {
		return hourlyRate;
	}
	public void setHourlyRate(BigDecimal hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	public BigDecimal getFoodAllowance() {
		return foodAllowance;
	}
	public void setFoodAllowance(BigDecimal foodAllowance) {
		this.foodAllowance = foodAllowance;
	}
	public BigDecimal getCola() {
		return cola;
	}
	public void setCola(BigDecimal cola) {
		this.cola = cola;
	}
	public BigDecimal getTaxShield() {
		return taxShield;
	}
	public void setTaxShield(BigDecimal taxShield) {
		this.taxShield = taxShield;
	}
	public BigDecimal getTransAllowance() {
		return transAllowance;
	}
	public void setTransAllowance(BigDecimal transAllowance) {
		this.transAllowance = transAllowance;
	}
	public BigDecimal getGsisEmployeeShare() {
		return gsisEmployeeShare;
	}
	public void setGsisEmployeeShare(BigDecimal gsisEmployeeShare) {
		this.gsisEmployeeShare = gsisEmployeeShare;
	}
	public BigDecimal getGsisEmployerShare() {
		return gsisEmployerShare;
	}
	public void setGsisEmployerShare(BigDecimal gsisEmployerShare) {
		this.gsisEmployerShare = gsisEmployerShare;
	}
	public BigDecimal getPhilhealthEmployeeShare() {
		return philhealthEmployeeShare;
	}
	public void setPhilhealthEmployeeShare(BigDecimal philhealthEmployeeShare) {
		this.philhealthEmployeeShare = philhealthEmployeeShare;
	}
	public BigDecimal getPhilhealthEmployerShare() {
		return philhealthEmployerShare;
	}
	public void setPhilhealthEmployerShare(BigDecimal philhealthEmployerShare) {
		this.philhealthEmployerShare = philhealthEmployerShare;
	}
	public BigDecimal getPagibigEmployeeShare() {
		return pagibigEmployeeShare;
	}
	public void setPagibigEmployeeShare(BigDecimal pagibigEmployeeShare) {
		this.pagibigEmployeeShare = pagibigEmployeeShare;
	}
	public BigDecimal getPagibigEmployerShare() {
		return pagibigEmployerShare;
	}
	public void setPagibigEmployerShare(BigDecimal pagibigEmployerShare) {
		this.pagibigEmployerShare = pagibigEmployerShare;
	}
	public String getPayrollType() {
		return payrollType;
	}
	public void setPayrollType(String payrollType) {
		this.payrollType = payrollType;
	}
	public String getBan() {
		return ban;
	}
	public void setBan(String ban) {
		this.ban = ban;
	}
	public String getBankNameBan() {
		return bankNameBan;
	}
	public void setBankNameBan(String bankNameBan) {
		this.bankNameBan = bankNameBan;
	}
	public int getShiftingScheduleId() {
		return shiftingScheduleId;
	}
	public void setShiftingScheduleId(int shiftingScheduleId) {
		this.shiftingScheduleId = shiftingScheduleId;
	}
	public String getSuperVisorName() {
		return superVisorName;
	}
	public void setSuperVisorName(String superVisorName) {
		this.superVisorName = superVisorName;
	}
	public String getJobTitleName() {
		return jobTitleName;
	}
	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}
//	public String getDepartmentName() {
//		return departmentName;
//	}
//	public void setDepartmentName(String departmentName) {
//		this.departmentName = departmentName;
//	}
//	public String getDivisionName() {
//		return divisionName;
//	}
//	public void setDivisionName(String divisionName) {
//		this.divisionName = divisionName;
//	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCivilStatus() {
		return civilStatus;
	}
	public void setCivilStatus(String civilStatus) {
		this.civilStatus = civilStatus;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getBirthPlace() {
		return birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public int getJobTitleId() {
		return jobTitleId;
	}
	public void setJobTitleId(int jobTitleId) {
		this.jobTitleId = jobTitleId;
	}
	
//	public int getDepartmentId() {
//		return departmentId;
//	}
//	public void setDepartmentId(int departmentId) {
//		this.departmentId = departmentId;
//	}
//	public int getDivisionId() {
//		return divisionId;
//	}
//	public void setDivisionId(int divisionId) {
//		this.divisionId = divisionId;
//	}
	
	public int getEmployeeTypeId() {
		return employeeTypeId;
	}
	public void setEmployeeTypeId(int employeeTypeId) {
		this.employeeTypeId = employeeTypeId;
	}
	public String getEmpStatus() {
		return empStatus;
	}
	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}
	public String getEmploymentDate() {
		return employmentDate;
	}
	public void setEmploymentDate(String employmentDate) {
		this.employmentDate = employmentDate;
	}
	public String getEndOfContract() {
		return endOfContract;
	}
	public void setEndOfContract(String endOfContract) {
		this.endOfContract = endOfContract;
	}
	public String getSss() {
		return sss;
	}
	public void setSss(String sss) {
		this.sss = sss;
	}
	public String getGsis() {
		return gsis;
	}
	public void setGsis(String gsis) {
		this.gsis = gsis;
	}
	public String getHdmf() {
		return hdmf;
	}
	public void setHdmf(String hdmf) {
		this.hdmf = hdmf;
	}
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}
	public String getPhic() {
		return phic;
	}
	public void setPhic(String phic) {
		this.phic = phic;
	}
	public String getTaxstatus() {
		return taxstatus;
	}
	public void setTaxstatus(String taxstatus) {
		this.taxstatus = taxstatus;
	}
	public String getPicLoc() {
		return picLoc;
	}
	public void setPicLoc(String picLoc) {
		this.picLoc = picLoc;
	}
//	public int getSuperVisor1Id() {
//		return superVisor1Id;
//	}
//	public void setSuperVisor1Id(int superVisor1Id) {
//		this.superVisor1Id = superVisor1Id;
//	}
//	public int getSuperVisor2Id() {
//		return superVisor2Id;
//	}
//	public void setSuperVisor2Id(int superVisor2Id) {
//		this.superVisor2Id = superVisor2Id;
//	}
//	public int getSuperVisor3Id() {
//		return superVisor3Id;
//	}
//	public void setSuperVisor3Id(int superVisor3Id) {
//		this.superVisor3Id = superVisor3Id;
//	}
	
	@Override
	public String toString() {
		return "Employee [empId = " + empId + ", Lastname = "
				+ lastname + ", Firstname = "
				+ firstname + ", Middlename = " + middlename + ", dateOfBirth = "
				+ dateOfBirth + ", mobileNo = " + mobileNo + ", employmentDate = "
				+ employmentDate + ", sss = " + sss + ", gsis = "
				+ gsis + ", hdmf = " + hdmf + ", tin = "
				+ tin + ", phic = " + phic + ", taxstatus = "
				+ taxstatus + ", username = " + username + ", civilStatus = "
				+ civilStatus + "]";
	}
	
	/**
	From dbo.employee table

		[empId] [int] IDENTITY(1,1) NOT NULL,
		[empNo] [varchar](50) NOT NULL,
		[lastname] [varchar](50) NULL,
		[firstname] [varchar](50) NULL,
		[middlename] [varchar](50) NULL,
		[dateOfBirth] [datetime] NULL,
		[gender] [varchar](1) NULL,
		[civilStatus] [varchar](50) NULL,
		[nationality] [varchar](50) NULL,
		[street] [varchar](150) NULL,
		[cityId] [int] NULL,
		[email] [varchar](150) NULL,
		[mobileNo] [varchar](50) NULL,
		[telNo] [varchar](50) NULL,
		[birthPlace] [varchar](50) NULL,
		[provId] [int] NULL,
		[zipCode] [varchar](50) NULL,
		[jtId] [int] NULL,
		[deptId] [int] NULL,
		[divisionId] [int] NULL,
		[empTypeId] [int] NULL,
		[empStatus] [varchar](50) NULL,
		[employmentDate] [datetime] NULL,
		[endOfContract] [datetime] NULL,
		[sss] [varchar](50) NULL,
		[gsis] [varchar](50) NULL,
		[hdmf] [varchar](50) NULL,
		[tin] [varchar](50) NULL,
		[phic] [varchar](50) NULL,
		[taxstatus] [varchar](50) NULL,
		[picLoc] [varchar](50) NULL,
		[superVisor1Id] [int] NULL,
		[superVisor2Id] [int] NULL,
		[superVisor3Id] [int] NULL,
		[username] [varchar](50) NULL,
		[password] [varchar](50) NULL,
		[createdBy] [varchar](50) NULL,
		[creationDate] [datetime] NULL,

	*/

}
