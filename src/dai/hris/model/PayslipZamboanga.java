package dai.hris.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayslipZamboanga implements Serializable {

	private static final long serialVersionUID = 5027585243743799098L;
	
	private String positionName;
	private String departmentName;
	
	private int empId;
	private int payrollPeriodId;
	private String payrollType;
	private String payPeriod;
	private String empNo;
	private String name;
	private String taxStatus;
	private BigDecimal basicPay;
	private BigDecimal nightDiff;
	private BigDecimal overtime;
	
	private BigDecimal absences;
	private BigDecimal tardiness;
	private BigDecimal undertime;
	private BigDecimal holidayPay;
	
	private BigDecimal gsisEmployeeShare;
	private BigDecimal philhealthEmployeeShare;
	private BigDecimal pagibigEmployeeShare;
	private BigDecimal withholdingTax;
	
	private BigDecimal gsisEmployerShare;
	private BigDecimal philhealthEmployerShare;
	private BigDecimal pagibigEmployerShare;
	
	private BigDecimal totalEarnings;
	private BigDecimal totalDeductions;
	private BigDecimal netPay;
	private BigDecimal netPay1;
	private BigDecimal netPay2;
	private BigDecimal nontaxableIncome;
	private BigDecimal otherTaxableIncome;
	
	private BigDecimal cola;
	private BigDecimal foodAllowance;
	private BigDecimal taxShield;
	private BigDecimal tranpoAllowance;
	private BigDecimal grossPay;
	
	private String otherIncomeName1;
	private String otherIncomeName2;
	private String otherIncomeName3;
	private String otherIncomeName4;
	private String otherIncomeName5;
	private String otherIncomeName6;
	private String otherIncomeName7;
	
	private BigDecimal otherIncomeAmount1;
	private BigDecimal otherIncomeAmount2;
	private BigDecimal otherIncomeAmount3;
	private BigDecimal otherIncomeAmount4;
	private BigDecimal otherIncomeAmount5;
	private BigDecimal otherIncomeAmount6;
	private BigDecimal otherIncomeAmount7;
	
	
	private String otherDedName1;
	private String otherDedName2;
	private String otherDedName3;
	private String otherDedName4;
	private String otherDedName5;
	private String otherDedName6;
	private String otherDedName7;
	private String otherDedName8;
	private String otherDedName9;
	private String otherDedName10;
	private String otherDedName11;
	private String otherDedName12;
	private String otherDedName13;
	private String otherDedName14;
	private String otherDedName15;
	private String otherDedName16;
	private String otherDedName17;
	private String otherDedName18;
	private String otherDedName19;
	private String otherDedName20;
	private String otherDedName21;
	private String otherDedName22;
	private String otherDedName23;
	private String otherDedName24;
	private String otherDedName25;
	private String otherDedName26;
	private String otherDedName27;
	private String otherDedName28;
	private String otherDedName29;
	private String otherDedName30;
	private String otherDedName31;
	private String otherDedName32;
	private String otherDedName33;
	private String otherDedName34;
	private String otherDedName35;
	private String otherDedName36;
	private String otherDedName37;
	private String otherDedName38;
	private String otherDedName39;
	private String otherDedName40;
	private String otherDedName41;
	private String otherDedName42;
	private String otherDedName43;
	private String otherDedName44;
	private String otherDedName45;
	private String otherDedName46;
	private String otherDedName47;
	private String otherDedName48;
	
	private BigDecimal otherDedAmount1;
	private BigDecimal otherDedAmount2;
	private BigDecimal otherDedAmount3;
	private BigDecimal otherDedAmount4;
	private BigDecimal otherDedAmount5;
	private BigDecimal otherDedAmount6;
	private BigDecimal otherDedAmount7;
	private BigDecimal otherDedAmount8;
	private BigDecimal otherDedAmount9;
	private BigDecimal otherDedAmount10;
	private BigDecimal otherDedAmount11;
	private BigDecimal otherDedAmount12;
	private BigDecimal otherDedAmount13;
	private BigDecimal otherDedAmount14;
	private BigDecimal otherDedAmount15;
	private BigDecimal otherDedAmount16;
	private BigDecimal otherDedAmount17;
	private BigDecimal otherDedAmount18;
	private BigDecimal otherDedAmount19;
	private BigDecimal otherDedAmount20;
	private BigDecimal otherDedAmount21;
	private BigDecimal otherDedAmount22;
	private BigDecimal otherDedAmount23;
	private BigDecimal otherDedAmount24;
	private BigDecimal otherDedAmount25;
	private BigDecimal otherDedAmount26;
	private BigDecimal otherDedAmount27;
	private BigDecimal otherDedAmount28;
	private BigDecimal otherDedAmount29;
	private BigDecimal otherDedAmount30;
	private BigDecimal otherDedAmount31;
	private BigDecimal otherDedAmount32;
	private BigDecimal otherDedAmount33;
	private BigDecimal otherDedAmount34;
	private BigDecimal otherDedAmount35;
	private BigDecimal otherDedAmount36;
	private BigDecimal otherDedAmount37;
	private BigDecimal otherDedAmount38;
	private BigDecimal otherDedAmount39;
	private BigDecimal otherDedAmount40;
	private BigDecimal otherDedAmount41;
	private BigDecimal otherDedAmount42;
	private BigDecimal otherDedAmount43;
	private BigDecimal otherDedAmount44;
	private BigDecimal otherDedAmount45;
	private BigDecimal otherDedAmount46;
	private BigDecimal otherDedAmount47;
	private BigDecimal otherDedAmount48;
	
	private String cod1;
	private String cod2;
	private String cod3;
	private String cod4;
	private String cod5;
	private String cod6;
	private String cod7;
	private String cod8;
	private String cod9;
	private String cod10;
	private String cod11;
	private String cod12;
	private String cod13;
	private String cod14;
	private String cod15;
	private String cod16;
	private String cod17;
	private String cod18;
	private String cod19;
	private String cod20;
	private String cod21;
	private String cod22;
	private String cod23;
	private String cod24;
	private String cod25;
	private String cod26;
	private String cod27;
	private String cod28;
	private String cod29;
	private String cod30;
	private String cod31;
	private String cod32;
	private String cod33;
	private String cod34;
	private String cod35;
	private String cod36;
	private String cod37;
	private String cod38;
	private String cod39;
	private String cod40;
	private String cod41;
	private String cod42;
	private String cod43;
	private String cod44;
	private String cod45;
	private String cod46;
	private String cod47;
	private String cod48;
	
	private String deductionType1;
	private String deductionType2;
	private String deductionType3;
	private String deductionType4;
	private String deductionType5;
	private String deductionType6;
	private String deductionType7;
	private String deductionType8;
	private String deductionType9;
	private String deductionType10;
	private String deductionType11;
	private String deductionType12;
	private String deductionType13;
	private String deductionType14;
	private String deductionType15;
	private String deductionType16;
	private String deductionType17;
	private String deductionType18;
	private String deductionType19;
	private String deductionType20;
	private String deductionType21;
	private String deductionType22;
	private String deductionType23;
	private String deductionType24;
	private String deductionType25;
	private String deductionType26;
	private String deductionType27;
	private String deductionType28;
	private String deductionType29;
	private String deductionType30;
	private String deductionType31;
	private String deductionType32;
	private String deductionType33;
	private String deductionType34;
	private String deductionType35;
	private String deductionType36;
	private String deductionType37;
	private String deductionType38;
	private String deductionType39;
	private String deductionType40;
	private String deductionType41;
	private String deductionType42;
	private String deductionType43;
	private String deductionType44;
	private String deductionType45;
	private String deductionType46;
	private String deductionType47;
	private String deductionType48;

	
	public String getCod1() {
		return cod1;
	}
	public void setCod1(String cod1) {
		this.cod1 = cod1;
	}
	public String getCod2() {
		return cod2;
	}
	public void setCod2(String cod2) {
		this.cod2 = cod2;
	}
	public String getCod3() {
		return cod3;
	}
	public void setCod3(String cod3) {
		this.cod3 = cod3;
	}
	public String getCod4() {
		return cod4;
	}
	public void setCod4(String cod4) {
		this.cod4 = cod4;
	}
	public String getCod5() {
		return cod5;
	}
	public void setCod5(String cod5) {
		this.cod5 = cod5;
	}
	public String getCod6() {
		return cod6;
	}
	public void setCod6(String cod6) {
		this.cod6 = cod6;
	}
	public String getCod7() {
		return cod7;
	}
	public void setCod7(String cod7) {
		this.cod7 = cod7;
	}
	public String getCod8() {
		return cod8;
	}
	public void setCod8(String cod8) {
		this.cod8 = cod8;
	}
	public String getCod9() {
		return cod9;
	}
	public void setCod9(String cod9) {
		this.cod9 = cod9;
	}
	public String getCod10() {
		return cod10;
	}
	public void setCod10(String cod10) {
		this.cod10 = cod10;
	}
	public String getCod11() {
		return cod11;
	}
	public void setCod11(String cod11) {
		this.cod11 = cod11;
	}
	public String getCod12() {
		return cod12;
	}
	public void setCod12(String cod12) {
		this.cod12 = cod12;
	}
	public String getCod13() {
		return cod13;
	}
	public void setCod13(String cod13) {
		this.cod13 = cod13;
	}
	public String getCod14() {
		return cod14;
	}
	public void setCod14(String cod14) {
		this.cod14 = cod14;
	}
	public String getCod15() {
		return cod15;
	}
	public void setCod15(String cod15) {
		this.cod15 = cod15;
	}
	public String getCod16() {
		return cod16;
	}
	public void setCod16(String cod16) {
		this.cod16 = cod16;
	}
	public String getCod17() {
		return cod17;
	}
	public void setCod17(String cod17) {
		this.cod17 = cod17;
	}
	public String getCod18() {
		return cod18;
	}
	public void setCod18(String cod18) {
		this.cod18 = cod18;
	}
	public String getCod19() {
		return cod19;
	}
	public void setCod19(String cod19) {
		this.cod19 = cod19;
	}
	public String getCod20() {
		return cod20;
	}
	public void setCod20(String cod20) {
		this.cod20 = cod20;
	}
	public String getCod21() {
		return cod21;
	}
	public void setCod21(String cod21) {
		this.cod21 = cod21;
	}
	public String getCod22() {
		return cod22;
	}
	public void setCod22(String cod22) {
		this.cod22 = cod22;
	}
	public String getCod23() {
		return cod23;
	}
	public void setCod23(String cod23) {
		this.cod23 = cod23;
	}
	public String getCod24() {
		return cod24;
	}
	public void setCod24(String cod24) {
		this.cod24 = cod24;
	}
	public String getCod25() {
		return cod25;
	}
	public void setCod25(String cod25) {
		this.cod25 = cod25;
	}
	public String getCod26() {
		return cod26;
	}
	public void setCod26(String cod26) {
		this.cod26 = cod26;
	}
	public String getCod27() {
		return cod27;
	}
	public void setCod27(String cod27) {
		this.cod27 = cod27;
	}
	public String getCod28() {
		return cod28;
	}
	public void setCod28(String cod28) {
		this.cod28 = cod28;
	}
	public String getCod29() {
		return cod29;
	}
	public void setCod29(String cod29) {
		this.cod29 = cod29;
	}
	public String getCod30() {
		return cod30;
	}
	public void setCod30(String cod30) {
		this.cod30 = cod30;
	}
	public String getCod31() {
		return cod31;
	}
	public void setCod31(String cod31) {
		this.cod31 = cod31;
	}
	public String getCod32() {
		return cod32;
	}
	public void setCod32(String cod32) {
		this.cod32 = cod32;
	}
	public String getCod33() {
		return cod33;
	}
	public void setCod33(String cod33) {
		this.cod33 = cod33;
	}
	public String getCod34() {
		return cod34;
	}
	public void setCod34(String cod34) {
		this.cod34 = cod34;
	}
	public String getCod35() {
		return cod35;
	}
	public void setCod35(String cod35) {
		this.cod35 = cod35;
	}
	public String getCod36() {
		return cod36;
	}
	public void setCod36(String cod36) {
		this.cod36 = cod36;
	}
	public String getCod37() {
		return cod37;
	}
	public void setCod37(String cod37) {
		this.cod37 = cod37;
	}
	public String getCod38() {
		return cod38;
	}
	public void setCod38(String cod38) {
		this.cod38 = cod38;
	}
	public String getCod39() {
		return cod39;
	}
	public void setCod39(String cod39) {
		this.cod39 = cod39;
	}
	public String getCod40() {
		return cod40;
	}
	public void setCod40(String cod40) {
		this.cod40 = cod40;
	}
	public String getCod41() {
		return cod41;
	}
	public void setCod41(String cod41) {
		this.cod41 = cod41;
	}
	public String getCod42() {
		return cod42;
	}
	public void setCod42(String cod42) {
		this.cod42 = cod42;
	}
	public String getCod43() {
		return cod43;
	}
	public void setCod43(String cod43) {
		this.cod43 = cod43;
	}
	public String getCod44() {
		return cod44;
	}
	public void setCod44(String cod44) {
		this.cod44 = cod44;
	}
	public String getCod45() {
		return cod45;
	}
	public void setCod45(String cod45) {
		this.cod45 = cod45;
	}
	public String getCod46() {
		return cod46;
	}
	public void setCod46(String cod46) {
		this.cod46 = cod46;
	}
	public String getCod47() {
		return cod47;
	}
	public void setCod47(String cod47) {
		this.cod47 = cod47;
	}
	public String getCod48() {
		return cod48;
	}
	public void setCod48(String cod48) {
		this.cod48 = cod48;
	}
	public String getDeductionType1() {
		return deductionType1;
	}
	public void setDeductionType1(String deductionType1) {
		this.deductionType1 = deductionType1;
	}
	public String getDeductionType2() {
		return deductionType2;
	}
	public void setDeductionType2(String deductionType2) {
		this.deductionType2 = deductionType2;
	}
	public String getDeductionType3() {
		return deductionType3;
	}
	public void setDeductionType3(String deductionType3) {
		this.deductionType3 = deductionType3;
	}
	public String getDeductionType4() {
		return deductionType4;
	}
	public void setDeductionType4(String deductionType4) {
		this.deductionType4 = deductionType4;
	}
	public String getDeductionType5() {
		return deductionType5;
	}
	public void setDeductionType5(String deductionType5) {
		this.deductionType5 = deductionType5;
	}
	public String getDeductionType6() {
		return deductionType6;
	}
	public void setDeductionType6(String deductionType6) {
		this.deductionType6 = deductionType6;
	}
	public String getDeductionType7() {
		return deductionType7;
	}
	public void setDeductionType7(String deductionType7) {
		this.deductionType7 = deductionType7;
	}
	public String getDeductionType8() {
		return deductionType8;
	}
	public void setDeductionType8(String deductionType8) {
		this.deductionType8 = deductionType8;
	}
	public String getDeductionType9() {
		return deductionType9;
	}
	public void setDeductionType9(String deductionType9) {
		this.deductionType9 = deductionType9;
	}
	public String getDeductionType10() {
		return deductionType10;
	}
	public void setDeductionType10(String deductionType10) {
		this.deductionType10 = deductionType10;
	}
	public String getDeductionType11() {
		return deductionType11;
	}
	public void setDeductionType11(String deductionType11) {
		this.deductionType11 = deductionType11;
	}
	public String getDeductionType12() {
		return deductionType12;
	}
	public void setDeductionType12(String deductionType12) {
		this.deductionType12 = deductionType12;
	}
	public String getDeductionType13() {
		return deductionType13;
	}
	public void setDeductionType13(String deductionType13) {
		this.deductionType13 = deductionType13;
	}
	public String getDeductionType14() {
		return deductionType14;
	}
	public void setDeductionType14(String deductionType14) {
		this.deductionType14 = deductionType14;
	}
	public String getDeductionType15() {
		return deductionType15;
	}
	public void setDeductionType15(String deductionType15) {
		this.deductionType15 = deductionType15;
	}
	public String getDeductionType16() {
		return deductionType16;
	}
	public void setDeductionType16(String deductionType16) {
		this.deductionType16 = deductionType16;
	}
	public String getDeductionType17() {
		return deductionType17;
	}
	public void setDeductionType17(String deductionType17) {
		this.deductionType17 = deductionType17;
	}
	public String getDeductionType18() {
		return deductionType18;
	}
	public void setDeductionType18(String deductionType18) {
		this.deductionType18 = deductionType18;
	}
	public String getDeductionType19() {
		return deductionType19;
	}
	public void setDeductionType19(String deductionType19) {
		this.deductionType19 = deductionType19;
	}
	public String getDeductionType20() {
		return deductionType20;
	}
	public void setDeductionType20(String deductionType20) {
		this.deductionType20 = deductionType20;
	}
	public String getDeductionType21() {
		return deductionType21;
	}
	public void setDeductionType21(String deductionType21) {
		this.deductionType21 = deductionType21;
	}
	public String getDeductionType22() {
		return deductionType22;
	}
	public void setDeductionType22(String deductionType22) {
		this.deductionType22 = deductionType22;
	}
	public String getDeductionType23() {
		return deductionType23;
	}
	public void setDeductionType23(String deductionType23) {
		this.deductionType23 = deductionType23;
	}
	public String getDeductionType24() {
		return deductionType24;
	}
	public void setDeductionType24(String deductionType24) {
		this.deductionType24 = deductionType24;
	}
	public String getDeductionType25() {
		return deductionType25;
	}
	public void setDeductionType25(String deductionType25) {
		this.deductionType25 = deductionType25;
	}
	public String getDeductionType26() {
		return deductionType26;
	}
	public void setDeductionType26(String deductionType26) {
		this.deductionType26 = deductionType26;
	}
	public String getDeductionType27() {
		return deductionType27;
	}
	public void setDeductionType27(String deductionType27) {
		this.deductionType27 = deductionType27;
	}
	public String getDeductionType28() {
		return deductionType28;
	}
	public void setDeductionType28(String deductionType28) {
		this.deductionType28 = deductionType28;
	}
	public String getDeductionType29() {
		return deductionType29;
	}
	public void setDeductionType29(String deductionType29) {
		this.deductionType29 = deductionType29;
	}
	public String getDeductionType30() {
		return deductionType30;
	}
	public void setDeductionType30(String deductionType30) {
		this.deductionType30 = deductionType30;
	}
	public String getDeductionType31() {
		return deductionType31;
	}
	public void setDeductionType31(String deductionType31) {
		this.deductionType31 = deductionType31;
	}
	public String getDeductionType32() {
		return deductionType32;
	}
	public void setDeductionType32(String deductionType32) {
		this.deductionType32 = deductionType32;
	}
	public String getDeductionType33() {
		return deductionType33;
	}
	public void setDeductionType33(String deductionType33) {
		this.deductionType33 = deductionType33;
	}
	public String getDeductionType34() {
		return deductionType34;
	}
	public void setDeductionType34(String deductionType34) {
		this.deductionType34 = deductionType34;
	}
	public String getDeductionType35() {
		return deductionType35;
	}
	public void setDeductionType35(String deductionType35) {
		this.deductionType35 = deductionType35;
	}
	public String getDeductionType36() {
		return deductionType36;
	}
	public void setDeductionType36(String deductionType36) {
		this.deductionType36 = deductionType36;
	}
	public String getDeductionType37() {
		return deductionType37;
	}
	public void setDeductionType37(String deductionType37) {
		this.deductionType37 = deductionType37;
	}
	public String getDeductionType38() {
		return deductionType38;
	}
	public void setDeductionType38(String deductionType38) {
		this.deductionType38 = deductionType38;
	}
	public String getDeductionType39() {
		return deductionType39;
	}
	public void setDeductionType39(String deductionType39) {
		this.deductionType39 = deductionType39;
	}
	public String getDeductionType40() {
		return deductionType40;
	}
	public void setDeductionType40(String deductionType40) {
		this.deductionType40 = deductionType40;
	}
	public String getDeductionType41() {
		return deductionType41;
	}
	public void setDeductionType41(String deductionType41) {
		this.deductionType41 = deductionType41;
	}
	public String getDeductionType42() {
		return deductionType42;
	}
	public void setDeductionType42(String deductionType42) {
		this.deductionType42 = deductionType42;
	}
	public String getDeductionType43() {
		return deductionType43;
	}
	public void setDeductionType43(String deductionType43) {
		this.deductionType43 = deductionType43;
	}
	public String getDeductionType44() {
		return deductionType44;
	}
	public void setDeductionType44(String deductionType44) {
		this.deductionType44 = deductionType44;
	}
	public String getDeductionType45() {
		return deductionType45;
	}
	public void setDeductionType45(String deductionType45) {
		this.deductionType45 = deductionType45;
	}
	public String getDeductionType46() {
		return deductionType46;
	}
	public void setDeductionType46(String deductionType46) {
		this.deductionType46 = deductionType46;
	}
	public String getDeductionType47() {
		return deductionType47;
	}
	public void setDeductionType47(String deductionType47) {
		this.deductionType47 = deductionType47;
	}
	public String getDeductionType48() {
		return deductionType48;
	}
	public void setDeductionType48(String deductionType48) {
		this.deductionType48 = deductionType48;
	}
	
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getPayrollPeriodId() {
		return payrollPeriodId;
	}
	public void setPayrollPeriodId(int payrollPeriodId) {
		this.payrollPeriodId = payrollPeriodId;
	}
	public String getPayrollType() {
		return payrollType;
	}
	public void setPayrollType(String payrollType) {
		this.payrollType = payrollType;
	}
	public String getPayPeriod() {
		return payPeriod;
	}
	public void setPayPeriod(String payPeriod) {
		this.payPeriod = payPeriod;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTaxStatus() {
		return taxStatus;
	}
	public void setTaxStatus(String taxStatus) {
		this.taxStatus = taxStatus;
	}
	public BigDecimal getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(BigDecimal basicPay) {
		this.basicPay = basicPay;
	}
	public BigDecimal getNightDiff() {
		return nightDiff;
	}
	public void setNightDiff(BigDecimal nightDiff) {
		this.nightDiff = nightDiff;
	}
	public BigDecimal getOvertime() {
		return overtime;
	}
	public void setOvertime(BigDecimal overtime) {
		this.overtime = overtime;
	}
	public BigDecimal getAbsences() {
		return absences;
	}
	public void setAbsences(BigDecimal absences) {
		this.absences = absences;
	}
	public BigDecimal getTardiness() {
		return tardiness;
	}
	public void setTardiness(BigDecimal tardiness) {
		this.tardiness = tardiness;
	}
	public BigDecimal getUndertime() {
		return undertime;
	}
	public void setUndertime(BigDecimal undertime) {
		this.undertime = undertime;
	}
	public BigDecimal getHolidayPay() {
		return holidayPay;
	}
	public void setHolidayPay(BigDecimal holidayPay) {
		this.holidayPay = holidayPay;
	}
	public BigDecimal getGsisEmployeeShare() {
		return gsisEmployeeShare;
	}
	public void setGsisEmployeeShare(BigDecimal gsisEmployeeShare) {
		this.gsisEmployeeShare = gsisEmployeeShare;
	}
	public BigDecimal getPhilhealthEmployeeShare() {
		return philhealthEmployeeShare;
	}
	public void setPhilhealthEmployeeShare(BigDecimal philhealthEmployeeShare) {
		this.philhealthEmployeeShare = philhealthEmployeeShare;
	}
	public BigDecimal getPagibigEmployeeShare() {
		return pagibigEmployeeShare;
	}
	public void setPagibigEmployeeShare(BigDecimal pagibigEmployeeShare) {
		this.pagibigEmployeeShare = pagibigEmployeeShare;
	}
	public BigDecimal getWithholdingTax() {
		return withholdingTax;
	}
	public void setWithholdingTax(BigDecimal withholdingTax) {
		this.withholdingTax = withholdingTax;
	}
	public BigDecimal getGsisEmployerShare() {
		return gsisEmployerShare;
	}
	public void setGsisEmployerShare(BigDecimal gsisEmployerShare) {
		this.gsisEmployerShare = gsisEmployerShare;
	}
	public BigDecimal getPhilhealthEmployerShare() {
		return philhealthEmployerShare;
	}
	public void setPhilhealthEmployerShare(BigDecimal philhealthEmployerShare) {
		this.philhealthEmployerShare = philhealthEmployerShare;
	}
	public BigDecimal getPagibigEmployerShare() {
		return pagibigEmployerShare;
	}
	public void setPagibigEmployerShare(BigDecimal pagibigEmployerShare) {
		this.pagibigEmployerShare = pagibigEmployerShare;
	}
	public BigDecimal getTotalEarnings() {
		return totalEarnings;
	}
	public void setTotalEarnings(BigDecimal totalEarnings) {
		this.totalEarnings = totalEarnings;
	}
	public BigDecimal getTotalDeductions() {
		return totalDeductions;
	}
	public void setTotalDeductions(BigDecimal totalDeductions) {
		this.totalDeductions = totalDeductions;
	}
	public BigDecimal getNetPay() {
		return netPay;
	}
	public void setNetPay(BigDecimal netPay) {
		this.netPay = netPay;
	}
	public BigDecimal getNetPay1() {
		return netPay1;
	}
	public void setNetPay1(BigDecimal netPay1) {
		this.netPay1 = netPay1;
	}
	public BigDecimal getNetPay2() {
		return netPay2;
	}
	public void setNetPay2(BigDecimal netPay2) {
		this.netPay2 = netPay2;
	}
	public BigDecimal getNontaxableIncome() {
		return nontaxableIncome;
	}
	public void setNontaxableIncome(BigDecimal nontaxableIncome) {
		this.nontaxableIncome = nontaxableIncome;
	}
	public BigDecimal getOtherTaxableIncome() {
		return otherTaxableIncome;
	}
	public void setOtherTaxableIncome(BigDecimal otherTaxableIncome) {
		this.otherTaxableIncome = otherTaxableIncome;
	}
	public BigDecimal getCola() {
		return cola;
	}
	public void setCola(BigDecimal cola) {
		this.cola = cola;
	}
	public BigDecimal getFoodAllowance() {
		return foodAllowance;
	}
	public void setFoodAllowance(BigDecimal foodAllowance) {
		this.foodAllowance = foodAllowance;
	}
	public BigDecimal getTaxShield() {
		return taxShield;
	}
	public void setTaxShield(BigDecimal taxShield) {
		this.taxShield = taxShield;
	}
	public BigDecimal getTranpoAllowance() {
		return tranpoAllowance;
	}
	public void setTranpoAllowance(BigDecimal tranpoAllowance) {
		this.tranpoAllowance = tranpoAllowance;
	}
	public BigDecimal getGrossPay() {
		return grossPay;
	}
	public void setGrossPay(BigDecimal grossPay) {
		this.grossPay = grossPay;
	}
	public String getOtherIncomeName1() {
		return otherIncomeName1;
	}
	public void setOtherIncomeName1(String otherIncomeName1) {
		this.otherIncomeName1 = otherIncomeName1;
	}
	public String getOtherIncomeName2() {
		return otherIncomeName2;
	}
	public void setOtherIncomeName2(String otherIncomeName2) {
		this.otherIncomeName2 = otherIncomeName2;
	}
	public String getOtherIncomeName3() {
		return otherIncomeName3;
	}
	public void setOtherIncomeName3(String otherIncomeName3) {
		this.otherIncomeName3 = otherIncomeName3;
	}
	public String getOtherIncomeName4() {
		return otherIncomeName4;
	}
	public void setOtherIncomeName4(String otherIncomeName4) {
		this.otherIncomeName4 = otherIncomeName4;
	}
	public String getOtherIncomeName5() {
		return otherIncomeName5;
	}
	public void setOtherIncomeName5(String otherIncomeName5) {
		this.otherIncomeName5 = otherIncomeName5;
	}
	public String getOtherIncomeName6() {
		return otherIncomeName6;
	}
	public void setOtherIncomeName6(String otherIncomeName6) {
		this.otherIncomeName6 = otherIncomeName6;
	}
	public String getOtherIncomeName7() {
		return otherIncomeName7;
	}
	public void setOtherIncomeName7(String otherIncomeName7) {
		this.otherIncomeName7 = otherIncomeName7;
	}
	public BigDecimal getOtherIncomeAmount1() {
		return otherIncomeAmount1;
	}
	public void setOtherIncomeAmount1(BigDecimal otherIncomeAmount1) {
		this.otherIncomeAmount1 = otherIncomeAmount1;
	}
	public BigDecimal getOtherIncomeAmount2() {
		return otherIncomeAmount2;
	}
	public void setOtherIncomeAmount2(BigDecimal otherIncomeAmount2) {
		this.otherIncomeAmount2 = otherIncomeAmount2;
	}
	public BigDecimal getOtherIncomeAmount3() {
		return otherIncomeAmount3;
	}
	public void setOtherIncomeAmount3(BigDecimal otherIncomeAmount3) {
		this.otherIncomeAmount3 = otherIncomeAmount3;
	}
	public BigDecimal getOtherIncomeAmount4() {
		return otherIncomeAmount4;
	}
	public void setOtherIncomeAmount4(BigDecimal otherIncomeAmount4) {
		this.otherIncomeAmount4 = otherIncomeAmount4;
	}
	public BigDecimal getOtherIncomeAmount5() {
		return otherIncomeAmount5;
	}
	public void setOtherIncomeAmount5(BigDecimal otherIncomeAmount5) {
		this.otherIncomeAmount5 = otherIncomeAmount5;
	}
	public BigDecimal getOtherIncomeAmount6() {
		return otherIncomeAmount6;
	}
	public void setOtherIncomeAmount6(BigDecimal otherIncomeAmount6) {
		this.otherIncomeAmount6 = otherIncomeAmount6;
	}
	public BigDecimal getOtherIncomeAmount7() {
		return otherIncomeAmount7;
	}
	public void setOtherIncomeAmount7(BigDecimal otherIncomeAmount7) {
		this.otherIncomeAmount7 = otherIncomeAmount7;
	}
	public String getOtherDedName1() {
		return otherDedName1;
	}
	public void setOtherDedName1(String otherDedName1) {
		this.otherDedName1 = otherDedName1;
	}
	public String getOtherDedName2() {
		return otherDedName2;
	}
	public void setOtherDedName2(String otherDedName2) {
		this.otherDedName2 = otherDedName2;
	}
	public String getOtherDedName3() {
		return otherDedName3;
	}
	public void setOtherDedName3(String otherDedName3) {
		this.otherDedName3 = otherDedName3;
	}
	public String getOtherDedName4() {
		return otherDedName4;
	}
	public void setOtherDedName4(String otherDedName4) {
		this.otherDedName4 = otherDedName4;
	}
	public String getOtherDedName5() {
		return otherDedName5;
	}
	public void setOtherDedName5(String otherDedName5) {
		this.otherDedName5 = otherDedName5;
	}
	public String getOtherDedName6() {
		return otherDedName6;
	}
	public void setOtherDedName6(String otherDedName6) {
		this.otherDedName6 = otherDedName6;
	}
	public String getOtherDedName7() {
		return otherDedName7;
	}
	public void setOtherDedName7(String otherDedName7) {
		this.otherDedName7 = otherDedName7;
	}
	public String getOtherDedName8() {
		return otherDedName8;
	}
	public void setOtherDedName8(String otherDedName8) {
		this.otherDedName8 = otherDedName8;
	}
	public String getOtherDedName9() {
		return otherDedName9;
	}
	public void setOtherDedName9(String otherDedName9) {
		this.otherDedName9 = otherDedName9;
	}
	public String getOtherDedName10() {
		return otherDedName10;
	}
	public void setOtherDedName10(String otherDedName10) {
		this.otherDedName10 = otherDedName10;
	}
	public String getOtherDedName11() {
		return otherDedName11;
	}
	public void setOtherDedName11(String otherDedName11) {
		this.otherDedName11 = otherDedName11;
	}
	public String getOtherDedName12() {
		return otherDedName12;
	}
	public void setOtherDedName12(String otherDedName12) {
		this.otherDedName12 = otherDedName12;
	}
	public String getOtherDedName13() {
		return otherDedName13;
	}
	public void setOtherDedName13(String otherDedName13) {
		this.otherDedName13 = otherDedName13;
	}
	public String getOtherDedName14() {
		return otherDedName14;
	}
	public void setOtherDedName14(String otherDedName14) {
		this.otherDedName14 = otherDedName14;
	}
	public String getOtherDedName15() {
		return otherDedName15;
	}
	public void setOtherDedName15(String otherDedName15) {
		this.otherDedName15 = otherDedName15;
	}
	public String getOtherDedName16() {
		return otherDedName16;
	}
	public void setOtherDedName16(String otherDedName16) {
		this.otherDedName16 = otherDedName16;
	}
	public String getOtherDedName17() {
		return otherDedName17;
	}
	public void setOtherDedName17(String otherDedName17) {
		this.otherDedName17 = otherDedName17;
	}
	public String getOtherDedName18() {
		return otherDedName18;
	}
	public void setOtherDedName18(String otherDedName18) {
		this.otherDedName18 = otherDedName18;
	}
	public String getOtherDedName19() {
		return otherDedName19;
	}
	public void setOtherDedName19(String otherDedName19) {
		this.otherDedName19 = otherDedName19;
	}
	public String getOtherDedName20() {
		return otherDedName20;
	}
	public void setOtherDedName20(String otherDedName20) {
		this.otherDedName20 = otherDedName20;
	}
	public String getOtherDedName21() {
		return otherDedName21;
	}
	public void setOtherDedName21(String otherDedName21) {
		this.otherDedName21 = otherDedName21;
	}
	public String getOtherDedName22() {
		return otherDedName22;
	}
	public void setOtherDedName22(String otherDedName22) {
		this.otherDedName22 = otherDedName22;
	}
	public String getOtherDedName23() {
		return otherDedName23;
	}
	public void setOtherDedName23(String otherDedName23) {
		this.otherDedName23 = otherDedName23;
	}
	public String getOtherDedName24() {
		return otherDedName24;
	}
	public void setOtherDedName24(String otherDedName24) {
		this.otherDedName24 = otherDedName24;
	}
	public String getOtherDedName25() {
		return otherDedName25;
	}
	public void setOtherDedName25(String otherDedName25) {
		this.otherDedName25 = otherDedName25;
	}
	public String getOtherDedName26() {
		return otherDedName26;
	}
	public void setOtherDedName26(String otherDedName26) {
		this.otherDedName26 = otherDedName26;
	}
	public String getOtherDedName27() {
		return otherDedName27;
	}
	public void setOtherDedName27(String otherDedName27) {
		this.otherDedName27 = otherDedName27;
	}
	public String getOtherDedName28() {
		return otherDedName28;
	}
	public void setOtherDedName28(String otherDedName28) {
		this.otherDedName28 = otherDedName28;
	}
	public String getOtherDedName29() {
		return otherDedName29;
	}
	public void setOtherDedName29(String otherDedName29) {
		this.otherDedName29 = otherDedName29;
	}
	public String getOtherDedName30() {
		return otherDedName30;
	}
	public void setOtherDedName30(String otherDedName30) {
		this.otherDedName30 = otherDedName30;
	}
	public String getOtherDedName31() {
		return otherDedName31;
	}
	public void setOtherDedName31(String otherDedName31) {
		this.otherDedName31 = otherDedName31;
	}
	public String getOtherDedName32() {
		return otherDedName32;
	}
	public void setOtherDedName32(String otherDedName32) {
		this.otherDedName32 = otherDedName32;
	}
	public String getOtherDedName33() {
		return otherDedName33;
	}
	public void setOtherDedName33(String otherDedName33) {
		this.otherDedName33 = otherDedName33;
	}
	public String getOtherDedName34() {
		return otherDedName34;
	}
	public void setOtherDedName34(String otherDedName34) {
		this.otherDedName34 = otherDedName34;
	}
	public String getOtherDedName35() {
		return otherDedName35;
	}
	public void setOtherDedName35(String otherDedName35) {
		this.otherDedName35 = otherDedName35;
	}
	public String getOtherDedName36() {
		return otherDedName36;
	}
	public void setOtherDedName36(String otherDedName36) {
		this.otherDedName36 = otherDedName36;
	}
	public String getOtherDedName37() {
		return otherDedName37;
	}
	public void setOtherDedName37(String otherDedName37) {
		this.otherDedName37 = otherDedName37;
	}
	public String getOtherDedName38() {
		return otherDedName38;
	}
	public void setOtherDedName38(String otherDedName38) {
		this.otherDedName38 = otherDedName38;
	}
	public String getOtherDedName39() {
		return otherDedName39;
	}
	public void setOtherDedName39(String otherDedName39) {
		this.otherDedName39 = otherDedName39;
	}
	public String getOtherDedName40() {
		return otherDedName40;
	}
	public void setOtherDedName40(String otherDedName40) {
		this.otherDedName40 = otherDedName40;
	}
	public String getOtherDedName41() {
		return otherDedName41;
	}
	public void setOtherDedName41(String otherDedName41) {
		this.otherDedName41 = otherDedName41;
	}
	public String getOtherDedName42() {
		return otherDedName42;
	}
	public void setOtherDedName42(String otherDedName42) {
		this.otherDedName42 = otherDedName42;
	}
	public String getOtherDedName43() {
		return otherDedName43;
	}
	public void setOtherDedName43(String otherDedName43) {
		this.otherDedName43 = otherDedName43;
	}
	public String getOtherDedName44() {
		return otherDedName44;
	}
	public void setOtherDedName44(String otherDedName44) {
		this.otherDedName44 = otherDedName44;
	}
	public String getOtherDedName45() {
		return otherDedName45;
	}
	public void setOtherDedName45(String otherDedName45) {
		this.otherDedName45 = otherDedName45;
	}
	public String getOtherDedName46() {
		return otherDedName46;
	}
	public void setOtherDedName46(String otherDedName46) {
		this.otherDedName46 = otherDedName46;
	}
	public String getOtherDedName47() {
		return otherDedName47;
	}
	public void setOtherDedName47(String otherDedName47) {
		this.otherDedName47 = otherDedName47;
	}
	public String getOtherDedName48() {
		return otherDedName48;
	}
	public void setOtherDedName48(String otherDedName48) {
		this.otherDedName48 = otherDedName48;
	}
	public BigDecimal getOtherDedAmount1() {
		return otherDedAmount1;
	}
	public void setOtherDedAmount1(BigDecimal otherDedAmount1) {
		this.otherDedAmount1 = otherDedAmount1;
	}
	public BigDecimal getOtherDedAmount2() {
		return otherDedAmount2;
	}
	public void setOtherDedAmount2(BigDecimal otherDedAmount2) {
		this.otherDedAmount2 = otherDedAmount2;
	}
	public BigDecimal getOtherDedAmount3() {
		return otherDedAmount3;
	}
	public void setOtherDedAmount3(BigDecimal otherDedAmount3) {
		this.otherDedAmount3 = otherDedAmount3;
	}
	public BigDecimal getOtherDedAmount4() {
		return otherDedAmount4;
	}
	public void setOtherDedAmount4(BigDecimal otherDedAmount4) {
		this.otherDedAmount4 = otherDedAmount4;
	}
	public BigDecimal getOtherDedAmount5() {
		return otherDedAmount5;
	}
	public void setOtherDedAmount5(BigDecimal otherDedAmount5) {
		this.otherDedAmount5 = otherDedAmount5;
	}
	public BigDecimal getOtherDedAmount6() {
		return otherDedAmount6;
	}
	public void setOtherDedAmount6(BigDecimal otherDedAmount6) {
		this.otherDedAmount6 = otherDedAmount6;
	}
	public BigDecimal getOtherDedAmount7() {
		return otherDedAmount7;
	}
	public void setOtherDedAmount7(BigDecimal otherDedAmount7) {
		this.otherDedAmount7 = otherDedAmount7;
	}
	public BigDecimal getOtherDedAmount8() {
		return otherDedAmount8;
	}
	public void setOtherDedAmount8(BigDecimal otherDedAmount8) {
		this.otherDedAmount8 = otherDedAmount8;
	}
	public BigDecimal getOtherDedAmount9() {
		return otherDedAmount9;
	}
	public void setOtherDedAmount9(BigDecimal otherDedAmount9) {
		this.otherDedAmount9 = otherDedAmount9;
	}
	public BigDecimal getOtherDedAmount10() {
		return otherDedAmount10;
	}
	public void setOtherDedAmount10(BigDecimal otherDedAmount10) {
		this.otherDedAmount10 = otherDedAmount10;
	}
	public BigDecimal getOtherDedAmount11() {
		return otherDedAmount11;
	}
	public void setOtherDedAmount11(BigDecimal otherDedAmount11) {
		this.otherDedAmount11 = otherDedAmount11;
	}
	public BigDecimal getOtherDedAmount12() {
		return otherDedAmount12;
	}
	public void setOtherDedAmount12(BigDecimal otherDedAmount12) {
		this.otherDedAmount12 = otherDedAmount12;
	}
	public BigDecimal getOtherDedAmount13() {
		return otherDedAmount13;
	}
	public void setOtherDedAmount13(BigDecimal otherDedAmount13) {
		this.otherDedAmount13 = otherDedAmount13;
	}
	public BigDecimal getOtherDedAmount14() {
		return otherDedAmount14;
	}
	public void setOtherDedAmount14(BigDecimal otherDedAmount14) {
		this.otherDedAmount14 = otherDedAmount14;
	}
	public BigDecimal getOtherDedAmount15() {
		return otherDedAmount15;
	}
	public void setOtherDedAmount15(BigDecimal otherDedAmount15) {
		this.otherDedAmount15 = otherDedAmount15;
	}
	public BigDecimal getOtherDedAmount16() {
		return otherDedAmount16;
	}
	public void setOtherDedAmount16(BigDecimal otherDedAmount16) {
		this.otherDedAmount16 = otherDedAmount16;
	}
	public BigDecimal getOtherDedAmount17() {
		return otherDedAmount17;
	}
	public void setOtherDedAmount17(BigDecimal otherDedAmount17) {
		this.otherDedAmount17 = otherDedAmount17;
	}
	public BigDecimal getOtherDedAmount18() {
		return otherDedAmount18;
	}
	public void setOtherDedAmount18(BigDecimal otherDedAmount18) {
		this.otherDedAmount18 = otherDedAmount18;
	}
	public BigDecimal getOtherDedAmount19() {
		return otherDedAmount19;
	}
	public void setOtherDedAmount19(BigDecimal otherDedAmount19) {
		this.otherDedAmount19 = otherDedAmount19;
	}
	public BigDecimal getOtherDedAmount20() {
		return otherDedAmount20;
	}
	public void setOtherDedAmount20(BigDecimal otherDedAmount20) {
		this.otherDedAmount20 = otherDedAmount20;
	}
	public BigDecimal getOtherDedAmount21() {
		return otherDedAmount21;
	}
	public void setOtherDedAmount21(BigDecimal otherDedAmount21) {
		this.otherDedAmount21 = otherDedAmount21;
	}
	public BigDecimal getOtherDedAmount22() {
		return otherDedAmount22;
	}
	public void setOtherDedAmount22(BigDecimal otherDedAmount22) {
		this.otherDedAmount22 = otherDedAmount22;
	}
	public BigDecimal getOtherDedAmount23() {
		return otherDedAmount23;
	}
	public void setOtherDedAmount23(BigDecimal otherDedAmount23) {
		this.otherDedAmount23 = otherDedAmount23;
	}
	public BigDecimal getOtherDedAmount24() {
		return otherDedAmount24;
	}
	public void setOtherDedAmount24(BigDecimal otherDedAmount24) {
		this.otherDedAmount24 = otherDedAmount24;
	}
	public BigDecimal getOtherDedAmount25() {
		return otherDedAmount25;
	}
	public void setOtherDedAmount25(BigDecimal otherDedAmount25) {
		this.otherDedAmount25 = otherDedAmount25;
	}
	public BigDecimal getOtherDedAmount26() {
		return otherDedAmount26;
	}
	public void setOtherDedAmount26(BigDecimal otherDedAmount26) {
		this.otherDedAmount26 = otherDedAmount26;
	}
	public BigDecimal getOtherDedAmount27() {
		return otherDedAmount27;
	}
	public void setOtherDedAmount27(BigDecimal otherDedAmount27) {
		this.otherDedAmount27 = otherDedAmount27;
	}
	public BigDecimal getOtherDedAmount28() {
		return otherDedAmount28;
	}
	public void setOtherDedAmount28(BigDecimal otherDedAmount28) {
		this.otherDedAmount28 = otherDedAmount28;
	}
	public BigDecimal getOtherDedAmount29() {
		return otherDedAmount29;
	}
	public void setOtherDedAmount29(BigDecimal otherDedAmount29) {
		this.otherDedAmount29 = otherDedAmount29;
	}
	public BigDecimal getOtherDedAmount30() {
		return otherDedAmount30;
	}
	public void setOtherDedAmount30(BigDecimal otherDedAmount30) {
		this.otherDedAmount30 = otherDedAmount30;
	}
	public BigDecimal getOtherDedAmount31() {
		return otherDedAmount31;
	}
	public void setOtherDedAmount31(BigDecimal otherDedAmount31) {
		this.otherDedAmount31 = otherDedAmount31;
	}
	public BigDecimal getOtherDedAmount32() {
		return otherDedAmount32;
	}
	public void setOtherDedAmount32(BigDecimal otherDedAmount32) {
		this.otherDedAmount32 = otherDedAmount32;
	}
	public BigDecimal getOtherDedAmount33() {
		return otherDedAmount33;
	}
	public void setOtherDedAmount33(BigDecimal otherDedAmount33) {
		this.otherDedAmount33 = otherDedAmount33;
	}
	public BigDecimal getOtherDedAmount34() {
		return otherDedAmount34;
	}
	public void setOtherDedAmount34(BigDecimal otherDedAmount34) {
		this.otherDedAmount34 = otherDedAmount34;
	}
	public BigDecimal getOtherDedAmount35() {
		return otherDedAmount35;
	}
	public void setOtherDedAmount35(BigDecimal otherDedAmount35) {
		this.otherDedAmount35 = otherDedAmount35;
	}
	public BigDecimal getOtherDedAmount36() {
		return otherDedAmount36;
	}
	public void setOtherDedAmount36(BigDecimal otherDedAmount36) {
		this.otherDedAmount36 = otherDedAmount36;
	}
	public BigDecimal getOtherDedAmount37() {
		return otherDedAmount37;
	}
	public void setOtherDedAmount37(BigDecimal otherDedAmount37) {
		this.otherDedAmount37 = otherDedAmount37;
	}
	public BigDecimal getOtherDedAmount38() {
		return otherDedAmount38;
	}
	public void setOtherDedAmount38(BigDecimal otherDedAmount38) {
		this.otherDedAmount38 = otherDedAmount38;
	}
	public BigDecimal getOtherDedAmount39() {
		return otherDedAmount39;
	}
	public void setOtherDedAmount39(BigDecimal otherDedAmount39) {
		this.otherDedAmount39 = otherDedAmount39;
	}
	public BigDecimal getOtherDedAmount40() {
		return otherDedAmount40;
	}
	public void setOtherDedAmount40(BigDecimal otherDedAmount40) {
		this.otherDedAmount40 = otherDedAmount40;
	}
	public BigDecimal getOtherDedAmount41() {
		return otherDedAmount41;
	}
	public void setOtherDedAmount41(BigDecimal otherDedAmount41) {
		this.otherDedAmount41 = otherDedAmount41;
	}
	public BigDecimal getOtherDedAmount42() {
		return otherDedAmount42;
	}
	public void setOtherDedAmount42(BigDecimal otherDedAmount42) {
		this.otherDedAmount42 = otherDedAmount42;
	}
	public BigDecimal getOtherDedAmount43() {
		return otherDedAmount43;
	}
	public void setOtherDedAmount43(BigDecimal otherDedAmount43) {
		this.otherDedAmount43 = otherDedAmount43;
	}
	public BigDecimal getOtherDedAmount44() {
		return otherDedAmount44;
	}
	public void setOtherDedAmount44(BigDecimal otherDedAmount44) {
		this.otherDedAmount44 = otherDedAmount44;
	}
	public BigDecimal getOtherDedAmount45() {
		return otherDedAmount45;
	}
	public void setOtherDedAmount45(BigDecimal otherDedAmount45) {
		this.otherDedAmount45 = otherDedAmount45;
	}
	public BigDecimal getOtherDedAmount46() {
		return otherDedAmount46;
	}
	public void setOtherDedAmount46(BigDecimal otherDedAmount46) {
		this.otherDedAmount46 = otherDedAmount46;
	}
	public BigDecimal getOtherDedAmount47() {
		return otherDedAmount47;
	}
	public void setOtherDedAmount47(BigDecimal otherDedAmount47) {
		this.otherDedAmount47 = otherDedAmount47;
	}
	public BigDecimal getOtherDedAmount48() {
		return otherDedAmount48;
	}
	public void setOtherDedAmount48(BigDecimal otherDedAmount48) {
		this.otherDedAmount48 = otherDedAmount48;
	}
		
		
	
}
