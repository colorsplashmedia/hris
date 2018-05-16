package dai.hris.service.payroll;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dai.hris.action.filemanager.util.EmployeeUtil;
import dai.hris.dao.filemanager.EmployeeIncomeDeductionDAO;
import dai.hris.dao.loan.LoanEntryDAO;
import dai.hris.dao.payroll.PayrollRunDAO;
import dai.hris.model.EmployeePayrollRunExt;
import dai.hris.model.PhicContributionTable;
import dai.hris.model.SystemParameters;
import dai.hris.model.TaxTable;
import dai.hris.model.TimeEntry;
import dai.hris.service.payroll.impl.PhicContributionTableService;
import dai.hris.service.payroll.impl.TaxTableService;


public class PayrollRunService implements IPayrollRunService {	
	
	/**
	 * REGULAR EMPLOYEES
	 * @param regularEmpExt 
	 */
	private void generateRegularEmployeePayrollRun(EmployeePayrollRunExt regularEmpExt, PayrollRunDAO payrollRunDAO, int loggedEmpId) {
		ITaxTableService taxTableService = new TaxTableService();	
		
		BigDecimal taxableIncome = BigDecimal.ZERO;
		BigDecimal witholdingTax = BigDecimal.ZERO;
		BigDecimal netPay = BigDecimal.ZERO;
		BigDecimal grossPay =  BigDecimal.ZERO;
		try {
			
			List<TimeEntry> scheduleList = payrollRunDAO.getEmployeeShedule(regularEmpExt);
			List<TimeEntry> attendanceList = payrollRunDAO.getEmployeeAttendance(regularEmpExt.getEmpId(), regularEmpExt.getFromDate().toString(), regularEmpExt.getToDate().toString());
			
			Map<String, TimeEntry> timeEntryMap = new HashMap<String, TimeEntry>();
			
			for(TimeEntry attendance: attendanceList) {
				timeEntryMap.put(attendance.getTimeIn().substring(0,10), attendance);
			}
			
			//GET BASIC RATE, OTHER TAXABLE INCOME, NIGHT DIFF PAY, OVERTIME PAY, HOLIDAY PAY
			regularEmpExt.setBasicPay(getBasicPay(regularEmpExt));	
			regularEmpExt.setOtherTaxableIncome(getOtherTaxableIncome(regularEmpExt));
			
			//PROBLEM With Overtime, NightDiff, Holiday, Tardiness, Absences
			regularEmpExt.setOvertime(getOvertimePay(regularEmpExt));
			regularEmpExt.setNightDiff(getNightDiffPay(timeEntryMap, scheduleList, regularEmpExt));
			regularEmpExt.setHolidayPay(getHolidayPay(timeEntryMap, scheduleList, regularEmpExt));	
			
			regularEmpExt.setAbsences(getAbsences(timeEntryMap, scheduleList, regularEmpExt));	
			regularEmpExt.setTardiness(getTardiness(timeEntryMap, scheduleList, regularEmpExt));	
//			regularEmpExt.setUndertime(undertime);
//			regularEmpExt.setLeaveWOPay(leaveWOPay);

			
			//Ian Note: Gov Deduction. Get overide contribution if less that computed. Use computed else use overide
			regularEmpExt.setPagibigEmployeeShare(getPagibigEmployeeShare(regularEmpExt));
			regularEmpExt.setPagibigEmployerShare(getPagibigEmployerShare(regularEmpExt));
			regularEmpExt.setGsisEmployeeShare(getGSISEmployeeShare(regularEmpExt));
			regularEmpExt.setGsisEmployerShare(getGSISEmployerShare(regularEmpExt));
			regularEmpExt.setPhilhealthEmployeeShare(getPhilHealthShare(regularEmpExt));
			regularEmpExt.setPhilhealthEmployerShare(getPhilHealthShare(regularEmpExt));			
			
			
			//Gross = Basic + Other taxable income + OT + HP + ND - Tardiness - Undertime - Pagibig - PHIC - GSIS
			if(regularEmpExt.getBasicPay() != null){
				grossPay = grossPay.add(regularEmpExt.getBasicPay());
			}
			
			if(regularEmpExt.getOtherTaxableIncome() != null){
				grossPay = grossPay.add(regularEmpExt.getOtherTaxableIncome());
			}			
			
			if(regularEmpExt.getOvertime() != null){
				grossPay = grossPay.add(regularEmpExt.getOvertime());
			}
			
			if(regularEmpExt.getHolidayPay() != null){
				grossPay = grossPay.add(regularEmpExt.getHolidayPay());
			}
			
			if(regularEmpExt.getNightDiff() != null){
				grossPay = grossPay.add(regularEmpExt.getNightDiff());
			}
			
			if(regularEmpExt.getTransAllowance() != null){
				grossPay = grossPay.add(regularEmpExt.getTransAllowance());
			}
			
			if(regularEmpExt.getFoodAllowance() != null){
				grossPay = grossPay.add(regularEmpExt.getFoodAllowance());
			}
			
			if(regularEmpExt.getCola() != null){
				grossPay = grossPay.add(regularEmpExt.getCola());
			}
			
			if(regularEmpExt.getTardiness() != null){
				grossPay = grossPay.subtract(regularEmpExt.getTardiness());
			}
			
			if(regularEmpExt.getAbsences() != null){
				grossPay = grossPay.subtract(regularEmpExt.getAbsences());
			}
			
//			if(regularEmpExt.getUndertime() != null){
//				grossPay = grossPay.subtract(regularEmpExt.getUndertime());
//			}
			
			if(regularEmpExt.getPagibigEmployeeShare() != null){
				grossPay = grossPay.subtract(regularEmpExt.getPagibigEmployeeShare());
			}
			
			if(regularEmpExt.getGsisEmployeeShare() != null){
				grossPay = grossPay.subtract(regularEmpExt.getGsisEmployeeShare());
			}
			
			if(regularEmpExt.getPhilhealthEmployeeShare() != null){
				grossPay = grossPay.subtract(regularEmpExt.getPhilhealthEmployeeShare());
			}
			
			regularEmpExt.setGrossPay(grossPay);
			
			
			TaxTable employeeTaxTable = null;

			
			employeeTaxTable = taxTableService.getTaxTableBySalaryAndTaxStatusAndPayrollType(regularEmpExt.getGrossPay(), regularEmpExt.getTaxStatus(), regularEmpExt.getPayrollType());
			//Tax =	1,875 + [(24,006.20-17,917) X .25]
			if(employeeTaxTable != null){
				//Sample Gross Pay is 15,000
				//Tax = 15000 - 10000 * Rate[0.2] + Exemption[708.33]
				BigDecimal rate = new BigDecimal(employeeTaxTable.getTaxRate());
				rate = rate.divide(new BigDecimal(100));
				witholdingTax = regularEmpExt.getGrossPay().subtract(employeeTaxTable.getSalaryBase());
				witholdingTax = witholdingTax.multiply(rate);
				witholdingTax = witholdingTax.add(employeeTaxTable.getTaxAmount());
				
				//witholdingTax = employeeTaxTable.getTaxAmount().add((regularEmpExt.getGrossPay().subtract(employeeTaxTable.getSalaryBase()).multiply(new BigDecimal(employeeTaxTable.getTaxPercentage()))));
			}
			
			
			regularEmpExt.setWithholdingTax(witholdingTax);
			
			
			regularEmpExt.setNontaxableIncome(getNonTaxableIncome(regularEmpExt));
			regularEmpExt.setOtherDeductions(getOtherDeductions(regularEmpExt));
			regularEmpExt.setLoans(getEmployeeLoans(regularEmpExt));
			
			//netPay = taxable income + non taxable income - witholding tax - other deductions - loans
			
			if(regularEmpExt.getNontaxableIncome() != null){
				netPay = regularEmpExt.getGrossPay().add(regularEmpExt.getNontaxableIncome());
				netPay = netPay.subtract(witholdingTax);
			} else {
				netPay = regularEmpExt.getGrossPay().subtract(witholdingTax);
			}			
			
			if(regularEmpExt.getOtherDeductions() != null){
				netPay = netPay.subtract(regularEmpExt.getOtherDeductions());
			}		
			
			if(regularEmpExt.getLoans() != null){		
				netPay = netPay.subtract(regularEmpExt.getLoans());
			}			
			
			regularEmpExt.setNetPay(netPay);
			
			System.out.println("Employee ID			: " + regularEmpExt.getEmpId());		
			System.out.println("Employee is			: " + regularEmpExt.getEmployeeTypeName());
			System.out.println("Payroll Type		: "	+ regularEmpExt.getPayrollType());
			System.out.println("Basic Pay			: " + getBasicPay(regularEmpExt));
			System.out.println("Holiday				: "	+ regularEmpExt.getHolidayPay());
			System.out.println("Night Diff			: "	+ regularEmpExt.getNightDiff());
			System.out.println("Overtime			: "	+ regularEmpExt.getOvertime());
			System.out.println("Other Taxable Income: "	+ regularEmpExt.getOtherTaxableIncome());
			System.out.println("Non-Taxable Income	: "	+ regularEmpExt.getNontaxableIncome());
			System.out.println("Taxable income		: " + taxableIncome.toPlainString());
			System.out.println(" ");
			System.out.println("Tardiness			: "	+ regularEmpExt.getTardiness());
			System.out.println("Absences			: "	+ regularEmpExt.getAbsences());
			System.out.println("Other Deductions	: "	+ regularEmpExt.getOtherDeductions());
			System.out.println("Witholding Tax		: " + regularEmpExt.getWithholdingTax());
			System.out.println("Loans				: " + regularEmpExt.getLoans());		
			System.out.println(" ");
			System.out.println(" ");
			System.out.println("Gross Pay			: " + regularEmpExt.getGrossPay().toPlainString());
			System.out.println("Net Pay				: " + regularEmpExt.getNetPay().toPlainString());
			System.out.println("====================================================");
			
			
			regularEmpExt.setCreateDate(EmployeeUtil.getCurrentSystemDateSqlFormat());
			regularEmpExt.setCreatedBy(Integer.toString(loggedEmpId)); 
				
			int employeePayrollRunId = payrollRunDAO.getEmployeePayrollRunId(regularEmpExt.getPayrollPeriodId(), regularEmpExt.getEmpId());
			if (employeePayrollRunId > 0) {
				regularEmpExt.setEmployeePayrollRunId(employeePayrollRunId);
				payrollRunDAO.update(regularEmpExt);
			} else {
				payrollRunDAO.add(regularEmpExt);
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 
	 * @param contractualEmpExt
	 */
	private void generateContractualEmployeePayrollRun(EmployeePayrollRunExt modelEmp, PayrollRunDAO payrollRunDAO, int loggedEmpId) {
		BigDecimal deductions = BigDecimal.ZERO;
		BigDecimal basicIncome = BigDecimal.ZERO;
		BigDecimal taxableIncome = BigDecimal.ZERO;
		BigDecimal grossPay =  BigDecimal.ZERO;		
		
		try {
			/*
			 * For Contractual No work no Pay. So to compute payroll need to get the the list of attendance or the dates the employee was present
			 * Check if Employee has holiday pay and night differential
			 * Check if there are approved overtime
			 * For Contractuals I believe Govt Deductions are not applicable
			 * For Contractual get nonTaxable Income, Deductions, Loan, Holiday Pay if allowed, NightDiff if allowed, Overtime
			 * 
			 * modelEmp.setOtherTaxableIncome(getOtherTaxableIncome(modelEmp));
			 * Not applicable for contractual: gsis, pag ibig, philhealth
			 * 
			 */
			
			SystemParameters sysParam = payrollRunDAO.getSystemParameters();
			List<TimeEntry> scheduleList = payrollRunDAO.getEmployeeShedule(modelEmp);
			List<TimeEntry> attendanceList = payrollRunDAO.getEmployeeAttendance(modelEmp.getEmpId(), modelEmp.getFromDate().toString(), modelEmp.getToDate().toString());
			
			Map<String, TimeEntry> timeEntryMap = new HashMap<String, TimeEntry>();
			
			for(TimeEntry attendance : attendanceList) {
				int noOfHrs = attendance.getTotalHoursPerShift() - sysParam.getContractualBreakHrs();
				if(noOfHrs >= sysParam.getContractualHrs()){
					//Daily Rate
					basicIncome = basicIncome.add(modelEmp.getDailyRate());					
					
				} else {
					//Hourly Rate
					BigDecimal hourlyPay = BigDecimal.ZERO;
					hourlyPay = modelEmp.getHourlyRate().multiply(new BigDecimal(noOfHrs));
					
					basicIncome = basicIncome.add(hourlyPay);
					
				}
				
				timeEntryMap.put(attendance.getTimeIn(), attendance);
			}
			
			
			
			modelEmp.setNontaxableIncome(getNonTaxableIncome(modelEmp));			
			modelEmp.setOtherDeductions(getOtherDeductions(modelEmp));		
			
			modelEmp.setOvertime(getOvertimePay(modelEmp));
			modelEmp.setNightDiff(getNightDiffPay(timeEntryMap, scheduleList, modelEmp));
			modelEmp.setHolidayPay(getHolidayPay(timeEntryMap, scheduleList, modelEmp));		
			
			if(modelEmp.getOvertime() != null){
				basicIncome = basicIncome.add(modelEmp.getOvertime());
			}
			
			if(modelEmp.getHolidayPay() != null){
				basicIncome = basicIncome.add(modelEmp.getHolidayPay());
			}
			
			if(modelEmp.getNightDiff() != null){
				basicIncome = basicIncome.add(modelEmp.getNightDiff());
			}
			
			
			
			deductions = getTardinessAndAbsences(modelEmp);
	
	
			/**
			 * TODO need to check how to compute for total deductions
			 */
			modelEmp.setTotalDeductions(deductions);
			
			
			//Taxable income = basicIncome - (SSS/PhilHealth/Pag-IBIG deductions + Tardiness + Absences)
			//TODO Error changing code. Other taxble income is not applicable to contractors
			
			taxableIncome = basicIncome.subtract(deductions);
			modelEmp.setTaxableIncome(taxableIncome);
			
			System.out.println("Start for empid: " 					+ modelEmp.getEmpId());
			System.out.println("     Employee is " 					+ modelEmp.getEmployeeTypeName());
			System.out.println("     Payroll Type:   " 				+ modelEmp.getPayrollType());
			System.out.println("      Basic Income (basic pay, overtime,  holiday,  night differential):   " + basicIncome.toPlainString());	
			System.out.println("        Basic Pay:   " 				+ getBasicPay(modelEmp));
			System.out.println("        Holiday:   " 					+ modelEmp.getHolidayPay());
			System.out.println("        Night Diff:   " 				+ modelEmp.getNightDiff());
			System.out.println("        Overtime:   " 					+ modelEmp.getOvertime());
			System.out.println("      Other Taxable Income:   " 		+ modelEmp.getOtherTaxableIncome());
			System.out.println("      Non-Taxable Income:   " 		+ modelEmp.getNontaxableIncome());
			System.out.println("      Deduct Govt Deductions? " 		+ modelEmp.getDeductGovtFlag());
			System.out.println("      Standard Govt Deductions: " 	+ getStandardGovtDeductions(modelEmp));
			System.out.println("     Taxable income: " 				+ taxableIncome.toPlainString());
			System.out.println(" ");
			System.out.println("     Tardiness:   " 				+ modelEmp.getTardiness());
			System.out.println("     Absences:   " 					+ modelEmp.getAbsences());
			System.out.println("     Other Deductions:   " 					+ modelEmp.getOtherDeductions());
			System.out.println("     Deductions (govt, tardiness, absences):     " + deductions.toPlainString());
	
			modelEmp.setWithholdingTax(BigDecimal.ZERO);
			modelEmp.setNetPay(taxableIncome);
			
			//gross pay is basicIncome + non-taxable income
			grossPay =  basicIncome.add(modelEmp.getNontaxableIncome());
			modelEmp.setGrossPay(grossPay);
			System.out.println(" ");
			System.out.println(" ");
			System.out.println("     Gross Pay: " + modelEmp.getGrossPay().toPlainString());
			System.out.println("     Net Pay: " + modelEmp.getNetPay().toPlainString());
			System.out.println("End for empid: " + modelEmp.getEmpId());
			modelEmp.setCreateDate(EmployeeUtil.getCurrentSystemDateSqlFormat());
			modelEmp.setCreatedBy(Integer.toString(loggedEmpId)); 
			
			int employeePayrollRunId = payrollRunDAO.getEmployeePayrollRunId(modelEmp.getPayrollPeriodId(), modelEmp.getEmpId());
			if (employeePayrollRunId > 0) {
				modelEmp.setEmployeePayrollRunId(employeePayrollRunId);
				payrollRunDAO.update(modelEmp);
			} else {
				payrollRunDAO.add(modelEmp);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * PROBATIONARY EMPLOYEE
	 * @param probationaryEmpExt
	 */
	private void generateProbationaryEmployeePayrollRun(EmployeePayrollRunExt probationaryEmpExt, PayrollRunDAO payrollRunDAO, int loggedEmpId) {
		ITaxTableService taxTableService = new TaxTableService();	
		BigDecimal deductions = BigDecimal.ZERO;
		BigDecimal basicIncome = BigDecimal.ZERO;
		BigDecimal taxableIncome = BigDecimal.ZERO;
		BigDecimal witholdingTax = BigDecimal.ZERO;
		BigDecimal netPay = BigDecimal.ZERO;
		BigDecimal grossPay =  BigDecimal.ZERO;
		try {
			/*
			 *
			 * as per discussion with team, comment out overtime, since hospital uses offsetting
			 * probationaryEmpExt.setOvertime(getOvertimePay(probationaryEmpExt.getEmpId(), probationaryEmpExt.getFromDate(), probationaryEmpExt.getToDate()));
			 *
			 * comment out holiday
			 * probationaryEmpExt.setHolidayPay(getHolidayPay(probationaryEmpExt, probationaryEmpExt.getFromDate(), probationaryEmpExt.getToDate()));	
			 */

		
			//probationaryEmpExt.setNightDiff(getNightDiffPay(probationaryEmpExt, probationaryEmpExt.getFromDate(), probationaryEmpExt.getToDate()));
//			probationaryEmpExt.setTardiness(getTardiness(probationaryEmpExt));
//			probationaryEmpExt.setAbsences(getAbsences(probationaryEmpExt));
			
			probationaryEmpExt.setPagibigEmployeeShare(getPagibigEmployeeShare(probationaryEmpExt));
			probationaryEmpExt.setPagibigEmployerShare(getPagibigEmployerShare(probationaryEmpExt));
			probationaryEmpExt.setGsisEmployeeShare(getGSISEmployeeShare(probationaryEmpExt));
			probationaryEmpExt.setGsisEmployerShare(getGSISEmployerShare(probationaryEmpExt));			
			probationaryEmpExt.setPhilhealthEmployeeShare(getPhilHealthShare(probationaryEmpExt));
			probationaryEmpExt.setPhilhealthEmployerShare(getPhilHealthShare(probationaryEmpExt));
			probationaryEmpExt.setOtherTaxableIncome(getOtherTaxableIncome(probationaryEmpExt));
			probationaryEmpExt.setNontaxableIncome(getNonTaxableIncome(probationaryEmpExt));
			probationaryEmpExt.setOtherDeductions(getOtherDeductions(probationaryEmpExt));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//basicIncome = basicPay + OT + HP + ND 		
		basicIncome = basicIncome.add(getBasicPay(probationaryEmpExt));
		
		if(probationaryEmpExt.getOvertime() != null){
			basicIncome = basicIncome.add(probationaryEmpExt.getOvertime());
		}
		
		if(probationaryEmpExt.getHolidayPay() != null){
			basicIncome = basicIncome.add(probationaryEmpExt.getHolidayPay());
		}
		
		if(probationaryEmpExt.getNightDiff() != null){
			basicIncome = basicIncome.add(probationaryEmpExt.getNightDiff());
		}
		
		if (PayrollConstants.YES.equals(probationaryEmpExt.getDeductGovtFlag())) {  //deduct govt deductions?
			deductions = getStandardGovtDeductions(probationaryEmpExt).add(getTardinessAndAbsences(probationaryEmpExt));
		} else {
			deductions = getTardinessAndAbsences(probationaryEmpExt);
		}

		/**
		 * TODO need to check how to compute for total deductions
		 */
		probationaryEmpExt.setTotalDeductions(deductions);
		
		
		//Taxable income = basicIncome - (SSS/PhilHealth/Pag-IBIG deductions + Tardiness + Absences)
		if(probationaryEmpExt.getOtherTaxableIncome() != null){
			taxableIncome = basicIncome.add(probationaryEmpExt.getOtherTaxableIncome()).subtract(deductions);
		} else {
			taxableIncome = basicIncome.subtract(deductions);
		}
		
		
		
		probationaryEmpExt.setTaxableIncome(taxableIncome);
		
		System.out.println("Start for empid: " 					+ probationaryEmpExt.getEmpId());
		System.out.println("     Employee is " 					+ probationaryEmpExt.getEmployeeTypeName());
		System.out.println("     Payroll Type:   " 				+ probationaryEmpExt.getPayrollType());
		System.out.println("      Basic Income (basic pay, overtime,  holiday,  night differential):   " + basicIncome.toPlainString());	
		System.out.println("        Basic Pay:   " 				+ getBasicPay(probationaryEmpExt));
		System.out.println("        Holiday:   " 					+ probationaryEmpExt.getHolidayPay());
		System.out.println("        Night Diff:   " 				+ probationaryEmpExt.getNightDiff());
		System.out.println("        Overtime:   " 					+ probationaryEmpExt.getOvertime());
		System.out.println("      Other Taxable Income:   " 		+ probationaryEmpExt.getOtherTaxableIncome());
		System.out.println("      Non-Taxable Income:   " 		+ probationaryEmpExt.getNontaxableIncome());
		System.out.println("      Deduct Govt Deductions? " 		+ probationaryEmpExt.getDeductGovtFlag());
		System.out.println("      Standard Govt Deductions: " 	+ getStandardGovtDeductions(probationaryEmpExt));
		System.out.println("     Taxable income: " 				+ taxableIncome.toPlainString());
		System.out.println(" ");
		System.out.println("     Tardiness:   " 				+ probationaryEmpExt.getTardiness());
		System.out.println("     Absences:   " 					+ probationaryEmpExt.getAbsences());
		System.out.println("     Other Deductions:   " 					+ probationaryEmpExt.getOtherDeductions());
		System.out.println("     Deductions (govt, tardiness, absences):     " + deductions.toPlainString());

		
		/**
		 * TODO:
		 *need to determine kelan second cutoff (dun bawas sss) - this is impt for semi monthly
		 *when is the crediting of allowances?
		 *do we need to factor in tax shield?
		 *
		 *need to factor in empIncome and empDeduction
		 */
		
		
		TaxTable employeeTaxTable = null;

		try {
			employeeTaxTable = taxTableService.getTaxTableBySalaryAndTaxStatusAndPayrollType(taxableIncome, probationaryEmpExt.getTaxStatus(), probationaryEmpExt.getPayrollType());
			//Tax =	1,875 + [(24,006.20-17,917) X .25]
			witholdingTax = employeeTaxTable.getTaxAmount().add((taxableIncome.subtract(employeeTaxTable.getSalaryBase()).multiply(new BigDecimal(employeeTaxTable.getTaxPercentage()))));
			System.out.println("     Witholding Tax: " +  witholdingTax.toPlainString());
			probationaryEmpExt.setWithholdingTax(witholdingTax);
			//netPay = taxable income + non taxable income - witholding tax - other deductions
			netPay = taxableIncome.add(probationaryEmpExt.getNontaxableIncome()).subtract(witholdingTax).subtract(probationaryEmpExt.getOtherDeductions());
			probationaryEmpExt.setNetPay(netPay);
			
		} catch (SQLException sqle) {
			// TODO Auto-generated catch block
			sqle.printStackTrace();
		} catch (Exception e)	{
			e.printStackTrace();
		}

		
		//TODO This is wrong
		//gross pay is basicIncome + non-taxable income
		grossPay =  basicIncome.add(probationaryEmpExt.getNontaxableIncome());
		probationaryEmpExt.setGrossPay(grossPay);
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("     Gross Pay: " + probationaryEmpExt.getGrossPay().toPlainString());
		System.out.println("     Net Pay: " + probationaryEmpExt.getNetPay().toPlainString());
		System.out.println("End for empid: " + probationaryEmpExt.getEmpId());
		probationaryEmpExt.setCreateDate(EmployeeUtil.getCurrentSystemDateSqlFormat());
		probationaryEmpExt.setCreatedBy(Integer.toString(loggedEmpId)); 
		
		try {
			int employeePayrollRunId = payrollRunDAO.getEmployeePayrollRunId(probationaryEmpExt.getPayrollPeriodId(), probationaryEmpExt.getEmpId());
			if (employeePayrollRunId > 0) {
				probationaryEmpExt.setEmployeePayrollRunId(employeePayrollRunId);
				payrollRunDAO.update(probationaryEmpExt);
			} else {
				payrollRunDAO.add(probationaryEmpExt);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	
	
	private void generateNightDifferentialPayrollRun(EmployeePayrollRunExt regularEmpExt, PayrollRunDAO payrollRunDAO, int loggedEmpId) throws SQLException, Exception {
		List<TimeEntry> attendanceList = payrollRunDAO.getEmployeeAttendance(regularEmpExt.getEmpId(), regularEmpExt.getFromDate().toString(), regularEmpExt.getToDate().toString());
		List<TimeEntry> scheduleList = payrollRunDAO.getEmployeeShedule(regularEmpExt);
		
		Map<String, TimeEntry> timeEntryMap = new HashMap<String, TimeEntry>();
		
		for(TimeEntry attendance: attendanceList) {
			timeEntryMap.put(attendance.getTimeIn(), attendance);
		}
		
		getNightDiffPay(timeEntryMap, scheduleList, regularEmpExt);
	}
	
	
	/**
	 * pass payrollPeriodId to generate payroll run
	 */
	public List<EmployeePayrollRunExt> generatePayrollByPayrollPeriod (int payrollPeriodId, String payrollType, int loggedEmpId) throws SQLException, Exception {
		PayrollRunDAO payrollRunDAO = new PayrollRunDAO();
		List<EmployeePayrollRunExt> employeePayrollRunEmpList = null;
		try {
			employeePayrollRunEmpList = payrollRunDAO.getBasicDataForEmployeeListByPayPeriod(payrollPeriodId, payrollType);
			
			payrollRunDAO.deletePayrollRun(payrollPeriodId);	
			
			for(EmployeePayrollRunExt eprE: employeePayrollRunEmpList) {
				if (PayrollConstants.REGULAR.equalsIgnoreCase(eprE.getEmployeeTypeName())) {
					generateRegularEmployeePayrollRun(eprE, payrollRunDAO, loggedEmpId);
				} else if (PayrollConstants.PROBATIONARY.equalsIgnoreCase(eprE.getEmployeeTypeName())) {
					generateProbationaryEmployeePayrollRun(eprE, payrollRunDAO, loggedEmpId);
				}			
			}	
		
			payrollRunDAO.updatePayrollPeriodStatus(payrollPeriodId, "G", loggedEmpId); //update to Generated status	
			System.out.println("update to (G)enerated done for payrollPeriodId: " + payrollPeriodId);
		} catch (Exception e) {
			e.printStackTrace();
			payrollRunDAO.updatePayrollPeriodStatus(payrollPeriodId, "N", 0);
			System.err.println("Cannot change payroll period status to (G)enerated. Set back value to (N)ew.");
		}
		payrollRunDAO.closeConnection();
		return employeePayrollRunEmpList;		
	}
	
	public List<EmployeePayrollRunExt> generatePayrollByPayrollPeriodContractual (int payrollPeriodId, String payrollType, int loggedEmpId) throws SQLException, Exception {
		PayrollRunDAO payrollRunDAO = new PayrollRunDAO();
		List<EmployeePayrollRunExt> employeePayrollRunEmpList = null;
		try {
			employeePayrollRunEmpList = payrollRunDAO.getBasicDataForEmpListByPayPeriodContractual(payrollPeriodId, payrollType);
			for(EmployeePayrollRunExt eprE: employeePayrollRunEmpList) {
				if (PayrollConstants.CONTRACTUAL.equalsIgnoreCase(eprE.getEmployeeTypeName())) {
					generateContractualEmployeePayrollRun(eprE, payrollRunDAO, loggedEmpId);
				}			
			}	
		
			payrollRunDAO.updatePayrollPeriodStatus(payrollPeriodId, "G", loggedEmpId); //update to Generated status	
			System.out.println("update to (G)enerated done for payrollPeriodId: " + payrollPeriodId);
		} catch (Exception e) {
			e.printStackTrace();
			payrollRunDAO.updatePayrollPeriodStatus(payrollPeriodId, "N", 0);
			System.err.println("Cannot change payroll period status to (G)enerated. Set back value to (N)ew.");
		}
		payrollRunDAO.closeConnection();
		return employeePayrollRunEmpList;		
	}
	
	/**
	 * Get Basic PAY (This is for Regular and Probasionary Employees Only
	 * @param employeePayrollRunExt
	 * @return
	 */
	private BigDecimal getBasicPay(EmployeePayrollRunExt employeePayrollRunExt) {
		BigDecimal basicPay = BigDecimal.ZERO;
		
		if (employeePayrollRunExt.getPayrollType().equals(PayrollConstants.SEMI_MONTHLY)) {
			basicPay =  employeePayrollRunExt.getMonthlyRate().divide(new BigDecimal(2), BigDecimal.ROUND_HALF_UP);
		} else if (employeePayrollRunExt.getPayrollType().equals(PayrollConstants.MONTHLY)) {
			basicPay =  employeePayrollRunExt.getMonthlyRate();
		}
		
		employeePayrollRunExt.setBasicPay(basicPay);
		return basicPay;
	}
	
	
	/**
	 * get the Employee share for GSIS, PHIC, HDMF
	 * @param employeePayrollRunExt
	 * @return
	 */
	private BigDecimal getStandardGovtDeductions(EmployeePayrollRunExt employeePayrollRunExt) {
		BigDecimal standardGovtDeductions = BigDecimal.ZERO;
		
		if(employeePayrollRunExt.getGsisEmployeeShare() != null){
			standardGovtDeductions = standardGovtDeductions.add(employeePayrollRunExt.getGsisEmployeeShare());
		}
		
		if(employeePayrollRunExt.getPhilhealthEmployeeShare() != null){
			standardGovtDeductions = standardGovtDeductions.add(employeePayrollRunExt.getPhilhealthEmployeeShare());
		}
		
		if(employeePayrollRunExt.getPagibigEmployeeShare() != null){
			standardGovtDeductions = standardGovtDeductions.add(employeePayrollRunExt.getPagibigEmployeeShare());
		}
		
		
		return standardGovtDeductions;
	}
	
	
	//TODO This is wrong
	private BigDecimal getTardinessAndAbsences(EmployeePayrollRunExt employeePayrollRunExt) {
		BigDecimal tardinessAndAbsences = BigDecimal.ZERO;
		
		if(employeePayrollRunExt.getTardiness() != null){
			tardinessAndAbsences = tardinessAndAbsences.add(employeePayrollRunExt.getTardiness());
		}
		
		if(employeePayrollRunExt.getAbsences() != null){
			tardinessAndAbsences = tardinessAndAbsences.add(employeePayrollRunExt.getAbsences());
		}
		
		return tardinessAndAbsences;
	}
	
	
	
	
	
	//TODO Not Working Yet
	private BigDecimal getEmployeeLoans(EmployeePayrollRunExt emp) throws SQLException, Exception {
		LoanEntryDAO dao = new LoanEntryDAO();
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String startDate = df.format(emp.getFromDate());
		
		String payrollCycle = determinePayrollCycle(startDate);
		
		BigDecimal result = dao.getLoanTotal(emp.getEmpId(), payrollCycle, emp.getPayrollPeriodId());
		dao.closeConnection();
		return result != null ? result : new BigDecimal(0);
		
		
		
		
//		BigDecimal loanAmount = BigDecimal.ZERO;
//		LoanEntryDAO LoanEntryDAO = new LoanEntryDAO();
//		LoanPaymentsDAO loanPaymentsDAO = new LoanPaymentsDAO();
//		
//		List<LoanEntry> loanEntryList = LoanEntryDAO.getAllLoanEntryByEmpId(emp.getEmpId());
//		
//		for (LoanEntry loanEntry : loanEntryList) {
//			if("Y".equals(loanEntry.getDeductionFlagActive())){
//
//				BigDecimal loanPaymentsSaved = loanPaymentsDAO.getTotalLoanPaymentsByLoanEntryId(loanEntry.getLoanEntryId());
//				BigDecimal tmpBalance = BigDecimal.ZERO;
//				BigDecimal quotient = BigDecimal.ZERO;
//				BigDecimal remainder = BigDecimal.ZERO;
//				LoanPayments loanPayment = null;
//				int compareResult = loanPaymentsSaved.compareTo(loanEntry.getLoanAmount());	
//				//System.out.println("comparison result is : " + compareResult);
//				//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
//								
//				//subtracted by system or manually (by loan payments)
//				
//				if(compareResult == 0){  			//equals
//					System.out.println("loan completed for loanEntryId: " + loanEntry.getLoanEntryId());
//					loanEntry.setDeductionFlagActive("N");
//					loanEntry.setRemarks("Loan completed.");
//					LoanEntryDAO.update(loanEntry);
//					continue;
//				}
//				
//				if(compareResult == -1){    //loanEntry.getLoanAmount() is greater than total loanPayments
//					System.out.println("loan still in progress for loanEntryId: " + loanEntry.getLoanEntryId());
//					System.out.println("loanPaymentsSaved: " + loanPaymentsSaved.toString());
//
//					tmpBalance = loanEntry.getLoanAmount().subtract(loanPaymentsSaved);
//					quotient = tmpBalance.divide(loanEntry.getMonthlyAmortization());
//					remainder = tmpBalance.remainder(loanEntry.getMonthlyAmortization());
//					
//					System.out.println("tmpBalance: " + tmpBalance.toString());
//					System.out.println("quotient: " + quotient.toString());
//					System.out.println("remainder: " + remainder.toString());
//					
//					if (quotient.compareTo(new BigDecimal(1)) == -1) { //quotient is less than one
//						//then the remainder is the final loan payment that will be generated by system
//						System.out.println("final payment");
//						loanPayment = new LoanPayments();
//						loanPayment.setLoanEntryId(loanEntry.getLoanEntryId());
//						loanPayment.setPaidAmount(remainder);
//						loanPayment.setPaymentDate(new java.sql.Date((new java.util.Date()).getTime()).toString());
//						loanPayment.setRemarks("SYSTEM GENERATED PAYMENT FINAL");
//						loanPaymentsDAO.add(loanPayment);
//						
//						//then update the flag indicator to N since this is the remainder only
//						loanEntry.setDeductionFlagActive("N");
//						loanEntry.setRemarks("Loan completed.");
//						LoanEntryDAO.update(loanEntry);
//						loanAmount = loanAmount.add(remainder);
//					} else if (quotient.compareTo(new BigDecimal(1)) == 1) { //quotient is greater than one
//						//then use the normal payment amortization will be generated by system
//						System.out.println("not yet final payment");
//						loanPayment = new LoanPayments();
//						loanPayment.setLoanEntryId(loanEntry.getLoanEntryId());
//						loanPayment.setPaidAmount(loanEntry.getMonthlyAmortization());
//						loanPayment.setPaymentDate(new java.sql.Date((new java.util.Date()).getTime()).toString());
//						loanPayment.setRemarks("SYSTEM GENERATED PAYMENT");
//						loanPaymentsDAO.add(loanPayment);
//						loanAmount = loanAmount.add(loanEntry.getMonthlyAmortization());
//					}  else if (quotient.compareTo(new BigDecimal(1)) == 0 & remainder.compareTo(new BigDecimal(0)) == 0 ) { 
//						//final quotient (isa na lang) at wala nang remainder 
//						//so final payment na
//						//then the amortization is the final loan payment that will be generated by system
//						System.out.println("final payment exact");
//						loanPayment = new LoanPayments();
//						loanPayment.setLoanEntryId(loanEntry.getLoanEntryId());
//						loanPayment.setPaidAmount(loanEntry.getMonthlyAmortization());
//						loanPayment.setPaymentDate(new java.sql.Date((new java.util.Date()).getTime()).toString());
//						loanPayment.setRemarks("SYSTEM GENERATED PAYMENT FINAL - Exact");
//						loanPaymentsDAO.add(loanPayment);
//						
//						//then update the flag indicator to N since this is the remainder only
//						loanEntry.setDeductionFlagActive("N");
//						loanEntry.setRemarks("Loan completed.");
//						LoanEntryDAO.update(loanEntry);
//						loanAmount = loanAmount.add(loanEntry.getMonthlyAmortization());			
//					}
//					continue;
//				}
//			}			
//		}		
//		
//		return loanAmount;
	}
	
	
	
	
	

	
	
	
	
	
	
	public boolean lockPayrollByPayrollPeriod (int payrollPeriodId) {
		//should generate first before locking
		return true;
	}
	
	
	private BigDecimal getAbsences(Map<String, TimeEntry> map, List<TimeEntry> scheduleList, EmployeePayrollRunExt regularEmpExt) throws SQLException, Exception {
		PayrollRunDAO dao = new PayrollRunDAO();
		BigDecimal absenceAmount = dao.getAbsenceAmount(map, scheduleList, regularEmpExt);
		dao.closeConnection();
		return absenceAmount;
	}	
	
	private BigDecimal getTardiness(Map<String, TimeEntry> map, List<TimeEntry> scheduleList, EmployeePayrollRunExt regularEmpExt) throws SQLException, Exception {
		PayrollRunDAO dao = new PayrollRunDAO();
		BigDecimal absenceAmount = dao.getTardiness(map, scheduleList, regularEmpExt);
		dao.closeConnection();
		return absenceAmount;
	}	
	
//	//Roy
//	private BigDecimal getTardiness(EmployeePayrollRunExt emp)  throws SQLException, Exception {
//			int empId = emp.getEmpId();
//			Date dateFrom = emp.getFromDate();
//			Date dateTo = emp.getToDate();
//			
//			TardinessDAO dao = new TardinessDAO();
//			int sumOfTardinessInMins = dao.getTotalNumberOfTardiness(empId, dateFrom, dateTo);
//			BigDecimal totalTardinessMinsDeduction = emp.getHolidayPay().divide(new BigDecimal(60)).multiply(new BigDecimal(sumOfTardinessInMins));
//			
//			return totalTardinessMinsDeduction != null ? totalTardinessMinsDeduction : new BigDecimal(0);
//			
//	}
	
	private BigDecimal getOvertimePay(EmployeePayrollRunExt regularEmpExt) throws SQLException, Exception {
		PayrollRunDAO dao = new PayrollRunDAO();
		BigDecimal overtimePay = dao.getOvertimePay(regularEmpExt);
		dao.closeConnection();
		return overtimePay;
	}
	
	//TODO Not Working
	private BigDecimal getHolidayPay(Map<String, TimeEntry> attendanceMap, List<TimeEntry> scheduleList, EmployeePayrollRunExt regularEmpExt) throws SQLException, Exception {
		PayrollRunDAO dao = new PayrollRunDAO();
		BigDecimal holidayPay = dao.getHolidayPay(attendanceMap, scheduleList, regularEmpExt);
		dao.closeConnection();
		return holidayPay;
	}
	
	
	private BigDecimal getNightDiffPay(Map<String, TimeEntry> attendanceMap, List<TimeEntry> scheduleList, EmployeePayrollRunExt regularEmpExt)  throws SQLException, Exception {
		PayrollRunDAO dao = new PayrollRunDAO();
		BigDecimal holidayPay = dao.getNightDiffPay(attendanceMap, scheduleList, regularEmpExt);
		dao.closeConnection();
		return holidayPay;
	}
	
	
	
	
	
	/*** start GSIS ****/
	
	private BigDecimal getGSISEmployeeShare(EmployeePayrollRunExt emp){
		BigDecimal gsisEmployeeShare = BigDecimal.ZERO;
		gsisEmployeeShare = emp.getBasicPay().multiply(new BigDecimal(0.09));
		
		if(emp.getGsisEmployeeShare() != null && emp.getGsisEmployeeShare().compareTo(gsisEmployeeShare) > 0) {
			gsisEmployeeShare = emp.getGsisEmployeeShare();
		}
		
		return gsisEmployeeShare != null ? gsisEmployeeShare : new BigDecimal(0);
	}
	
	private BigDecimal getGSISEmployerShare(EmployeePayrollRunExt emp){
		BigDecimal gsisEmployerShare = BigDecimal.ZERO;
		gsisEmployerShare = emp.getBasicPay().multiply(new BigDecimal(0.12));
		
		if(emp.getGsisEmployerShare() != null && emp.getGsisEmployerShare().compareTo(gsisEmployerShare) > 0) {
			gsisEmployerShare = emp.getGsisEmployerShare();
		}		
		
		return gsisEmployerShare != null ? gsisEmployerShare : new BigDecimal(0);
	}
	
	/*** end GSIS ****/
	
	/*** start Pag Ibig ****/
	//Computation is 1% for 1500 and below
	//Computation is 2% greater than 1500 but should only be computed with max of 5000. So if your salary is 1500 and above the employee and employer share is always 100
	private BigDecimal getPagibigEmployeeShare(EmployeePayrollRunExt emp){
		BigDecimal pagibigShare = new BigDecimal(PayrollConstants.MIN_PAGIBIG_SHARE);
		
		if(emp.getPagibigEmployeeShare() != null && emp.getPagibigEmployeeShare().compareTo(pagibigShare) > 0) {
			pagibigShare = emp.getPagibigEmployeeShare();
		}		
		
		if (emp.getPayrollType().equals(PayrollConstants.SEMI_MONTHLY)) {
			pagibigShare =  pagibigShare.divide(new BigDecimal(2), BigDecimal.ROUND_HALF_UP);
		}		
		
		return pagibigShare;
	}
	
	private BigDecimal getPagibigEmployerShare(EmployeePayrollRunExt emp){
		BigDecimal pagibigShare = new BigDecimal(PayrollConstants.MIN_PAGIBIG_SHARE);
		
		if(emp.getPagibigEmployerShare() != null && emp.getPagibigEmployerShare().compareTo(pagibigShare) > 0) {
			pagibigShare = emp.getPagibigEmployerShare();
		}		
		
		if (emp.getPayrollType().equals(PayrollConstants.SEMI_MONTHLY)) {
			pagibigShare =  pagibigShare.divide(new BigDecimal(2), BigDecimal.ROUND_HALF_UP);
		}
		
		return pagibigShare;
	}
	
	/*** end Pag Ibig ****/
	
	/*** start PHIC ****/
	//employee and employer share are the same
	private BigDecimal getPhilHealthShare(EmployeePayrollRunExt emp)  throws SQLException, Exception {
		PhicContributionTableService phicService= new PhicContributionTableService();
		PhicContributionTable phicTable = phicService.getPhicContributionBySalary(emp.getMonthlyRate());
		BigDecimal philhealth = BigDecimal.ZERO;
		
		if(phicTable != null){
			if (emp.getPayrollType().equals(PayrollConstants.SEMI_MONTHLY)) {
				philhealth =  phicTable.getEmployeeShare().divide(new BigDecimal(2), BigDecimal.ROUND_HALF_UP);
			} else {
				philhealth = phicTable.getEmployeeShare();
			}
		}
		
		return philhealth;		
	}	
	/*** end PHIC ****/
	
	public String determinePayrollCycle(String startDate){		
		if(startDate != null && startDate.length() > 2){
			int startDateInt = Integer.parseInt(startDate.substring(startDate.length() - 2)); 
			if(startDateInt >= 1){
				if(startDateInt >= 16){
					return "2";
				} else {
					return "1";
				}
			}
		}
		
		return "";
	}
	
	private BigDecimal getOtherTaxableIncome(EmployeePayrollRunExt emp)  throws SQLException, Exception {
		EmployeeIncomeDeductionDAO dao = new EmployeeIncomeDeductionDAO();
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		String startDate = df.format(emp.getFromDate());
		
		String payrollCycle = determinePayrollCycle(startDate);
		BigDecimal result = dao.getTaxableIncomeTotal(emp.getEmpId(), payrollCycle, emp.getPayrollPeriodId());
		dao.closeConnection();
		return result != null ? result : new BigDecimal(0);
	}
	
	
	private BigDecimal getNonTaxableIncome(EmployeePayrollRunExt emp)  throws SQLException, Exception {
		EmployeeIncomeDeductionDAO dao = new EmployeeIncomeDeductionDAO();
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		String startDate = df.format(emp.getFromDate());
		
		String payrollCycle = determinePayrollCycle(startDate);
		
		BigDecimal result = dao.getNonTaxableIncomeTotal(emp.getEmpId(), payrollCycle, emp.getPayrollPeriodId());
		dao.closeConnection();
		return result != null ? result : new BigDecimal(0);
	}
	
	
	private BigDecimal getOtherDeductions(EmployeePayrollRunExt emp)  throws SQLException, Exception {
		EmployeeIncomeDeductionDAO dao = new EmployeeIncomeDeductionDAO();
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String startDate = df.format(emp.getFromDate());
		
		String payrollCycle = determinePayrollCycle(startDate);
		
		BigDecimal result = dao.getDeductionTotal(emp.getEmpId(), payrollCycle, emp.getPayrollPeriodId());
		dao.closeConnection();
		return result != null ? result : new BigDecimal(0);
	}

	@Override
	public void updatePayrollPeriodStatusToLocked(int payrollPeriodId, int loggedEmpId)	throws SQLException, Exception {
		PayrollRunDAO payrollRunDAO = new PayrollRunDAO();
		
		payrollRunDAO.updatePayrollPeriodStatus(payrollPeriodId, "L", loggedEmpId);
		
		payrollRunDAO.closeConnection();
		
	}
	
	@Override
	public List<EmployeePayrollRunExt> generateNightDiffByPayrollPeriodContractual (int payrollPeriodId, String payrollType, int loggedEmpId) throws SQLException, Exception{
		PayrollRunDAO payrollRunDAO = new PayrollRunDAO();
		List<EmployeePayrollRunExt> employeePayrollRunEmpList = null;
		try {
			employeePayrollRunEmpList = payrollRunDAO.getBasicDataForEmpListByPayPeriodContractual(payrollPeriodId, payrollType);
			for(EmployeePayrollRunExt eprE: employeePayrollRunEmpList) {
				if (PayrollConstants.CONTRACTUAL.equalsIgnoreCase(eprE.getEmployeeTypeName())) {
					generateContractualEmployeePayrollRun(eprE, payrollRunDAO, loggedEmpId);
				}			
			}	
		
			payrollRunDAO.updatePayrollPeriodStatus(payrollPeriodId, "G", loggedEmpId); //update to Generated status	
			System.out.println("update to (G)enerated done for payrollPeriodId: " + payrollPeriodId);
		} catch (Exception e) {
			e.printStackTrace();
			payrollRunDAO.updatePayrollPeriodStatus(payrollPeriodId, "N", 0);
			System.err.println("Cannot change payroll period status to (G)enerated. Set back value to (N)ew.");
		}
		payrollRunDAO.closeConnection();
		return employeePayrollRunEmpList;	
	}
	
	@Override
	public List<EmployeePayrollRunExt> generateNightDiffByPayrollPeriod (int payrollPeriodId, String payrollType, int loggedEmpId) throws SQLException, Exception {
		PayrollRunDAO payrollRunDAO = new PayrollRunDAO();
		List<EmployeePayrollRunExt> employeePayrollRunEmpList = null;
		try {
			employeePayrollRunEmpList = payrollRunDAO.getBasicDataForEmployeeListByPayPeriod(payrollPeriodId, payrollType);
			for(EmployeePayrollRunExt eprE: employeePayrollRunEmpList) {
				generateNightDifferentialPayrollRun(eprE, payrollRunDAO, loggedEmpId);			
			}	
		
			payrollRunDAO.updatePayrollPeriodStatus(payrollPeriodId, "M", loggedEmpId); //update to Generated status	
			System.out.println("update to (NG)Night Diff Generated done for payrollPeriodId: " + payrollPeriodId);
		} catch (Exception e) {
			e.printStackTrace();
			payrollRunDAO.updatePayrollPeriodStatus(payrollPeriodId, "M", 0);
			System.err.println("Cannot change payroll period status to (G)enerated. Set back value to (N)ew.");
		}
		payrollRunDAO.closeConnection();
		return employeePayrollRunEmpList;	
	}
	
	
}



