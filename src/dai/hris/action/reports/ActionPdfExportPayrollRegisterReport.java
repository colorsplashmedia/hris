package dai.hris.action.reports;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import dai.hris.dao.DBConstants;
import dai.hris.model.Employee;
import dai.hris.model.PayrollRegisterReport;
import dai.hris.model.Payslip;
import dai.hris.model.PayslipZamboanga;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.employee.EmployeeService;
import dai.hris.service.filemanager.employee.IEmployeeService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.reports.IReportService;
import dai.hris.service.reports.ReportService;

/**
 * Servlet implementation class ActionPdfExportFMJobTitleListReport
 */
@WebServlet("/ActionPdfExportPayrollRegisterReport")
public class ActionPdfExportPayrollRegisterReport extends HttpServlet {
	private static final long serialVersionUID = 5676921778006010751L;
	private static Logger logger = Logger.getLogger(ActionPdfExportPayrollRegisterReport.class);
	Gson gson = new Gson();
	
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/pdf");
		
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");

		String reportStreamPath = "dai/hris/reports/PayrollRegister.jasper";
		String exportName = "payrollRegisterListReport";
		
		String payrollPeriodId = request.getParameter("payrollPeriodId");
		
		String signatory1 = request.getParameter("signatory1");
		String signatory2 = request.getParameter("signatory2");
		String signatory3 = request.getParameter("signatory3");
		String signatory4 = request.getParameter("signatory4");
		
		String position1 = request.getParameter("position1");
		String position2 = request.getParameter("position2");
		String position3 = request.getParameter("position3");
		String position4 = request.getParameter("position4");
		
		
		
		InputStream reportStream = getClass().getClassLoader().getResourceAsStream(reportStreamPath);
		if(reportStream == null){
			logger.debug("reportStream is NULL");
		}
								
		try {
					
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("HOSPITAL_NAME", ReportConstant.COMPANY_NAME);
			
			IReportService service = new ReportService();
			String forExport = request.getParameter("forExport");

			
			map.put("signatory1", signatory1);
			map.put("signatory2", signatory2);
			map.put("signatory3", signatory3);
			map.put("signatory4", signatory4);
			map.put("position1", position1);
			map.put("position2", position2);
			map.put("position3", position3);
			map.put("position4", position4);
			
			List<Employee> empIdList = new ArrayList<Employee>();
			
			IEmployeeService empService = new EmployeeService();
			
			empIdList = empService.getAll();
			
			List<PayslipZamboanga> dataList = new ArrayList<PayslipZamboanga>();	
			
			dataList = service.getPayrollRegisterReport(empIdList, Integer.parseInt(payrollPeriodId));	
			
			map.put("noOfEmployees", dataList.size() + "");
			
			if (dataList.isEmpty()) {    	
				PayslipZamboanga model = new PayslipZamboanga();     		    		
	    		
	    		model.setAbsences(BigDecimal.ZERO);
				model.setBasicPay(BigDecimal.ZERO);
				model.setEmpNo("");
				model.setGsisEmployeeShare(BigDecimal.ZERO);
				model.setGsisEmployerShare(BigDecimal.ZERO);
				model.setHolidayPay(BigDecimal.ZERO);
				model.setName("");
				model.setNetPay(BigDecimal.ZERO);
				model.setNightDiff(BigDecimal.ZERO);
				model.setOtherTaxableIncome(BigDecimal.ZERO);
				model.setOvertime(BigDecimal.ZERO);
				model.setPagibigEmployeeShare(BigDecimal.ZERO);
				model.setPagibigEmployerShare(BigDecimal.ZERO);
				model.setPayPeriod("");
				model.setPayrollType("");
				model.setPhilhealthEmployeeShare(BigDecimal.ZERO);
				model.setPhilhealthEmployerShare(BigDecimal.ZERO);
				model.setTardiness(BigDecimal.ZERO);
				model.setTaxStatus("");
				model.setTotalDeductions(BigDecimal.ZERO);
				model.setTotalEarnings(BigDecimal.ZERO);
				model.setUndertime(BigDecimal.ZERO);
				model.setWithholdingTax(BigDecimal.ZERO);
				model.setNontaxableIncome(BigDecimal.ZERO);
				model.setDepartmentName("");
				
				model.setCola(BigDecimal.ZERO);
				model.setTranpoAllowance(BigDecimal.ZERO);
				model.setFoodAllowance(BigDecimal.ZERO);
				model.setOtherDedAmount1(BigDecimal.ZERO);
				model.setOtherDedName1("Other Deductions");
				model.setGrossPay(BigDecimal.ZERO);
				model.setName("");
				model.setPositionName("");
				
				dataList.add(model);
				dataList.add(model);
		    } else {
		    	if(dataList.size() > 0 && dataList.size() % 2 == 0){
		    		//Do Nothing
		    	} else {
		    		PayslipZamboanga model = new PayslipZamboanga();     		    		
		    		
		    		model.setAbsences(BigDecimal.ZERO);
					model.setBasicPay(BigDecimal.ZERO);
					model.setEmpNo("");
					model.setGsisEmployeeShare(BigDecimal.ZERO);
					model.setGsisEmployerShare(BigDecimal.ZERO);
					model.setHolidayPay(BigDecimal.ZERO);
					model.setName("");
					model.setNetPay(BigDecimal.ZERO);
					model.setNightDiff(BigDecimal.ZERO);
					model.setOtherTaxableIncome(BigDecimal.ZERO);
					model.setOvertime(BigDecimal.ZERO);
					model.setPagibigEmployeeShare(BigDecimal.ZERO);
					model.setPagibigEmployerShare(BigDecimal.ZERO);
					model.setPayPeriod("");
					model.setPayrollType("");
					model.setPhilhealthEmployeeShare(BigDecimal.ZERO);
					model.setPhilhealthEmployerShare(BigDecimal.ZERO);
					model.setTardiness(BigDecimal.ZERO);
					model.setTaxStatus("");
					model.setTotalDeductions(BigDecimal.ZERO);
					model.setTotalEarnings(BigDecimal.ZERO);
					model.setUndertime(BigDecimal.ZERO);
					model.setWithholdingTax(BigDecimal.ZERO);
					model.setNontaxableIncome(BigDecimal.ZERO);
					model.setDepartmentName("");
					
					model.setCola(BigDecimal.ZERO);
					model.setTranpoAllowance(BigDecimal.ZERO);
					model.setFoodAllowance(BigDecimal.ZERO);
					model.setOtherDedAmount1(BigDecimal.ZERO);
					model.setOtherDedName1("Other Deductions");
					model.setGrossPay(BigDecimal.ZERO);
					model.setName("");
					model.setPositionName("");
					
					dataList.add(model);
		    	}
		    }
			
//			for(PayrollRegisterReport model : dataList) {
			for(PayslipZamboanga payslip : dataList) {
				map.put("payrollPeriodStr", payslip.getPayPeriod());
				map.put("departmentName", payslip.getDepartmentName());
				if(payslip != null & payslip.getPayPeriod().length() > 0){
					map.put("payrollYear", payslip.getPayPeriod().subSequence(payslip.getPayPeriod().length() - 4, payslip.getPayPeriod().length()));
				} else {
					map.put("payrollYear", "");
				}
				
				
				break;
			}
			
			
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			Connection connection = null;
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			connection = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL); 	
			
			if(forExport.equals("Y")) {
				String printFileName = null;		
				URL sourceFileName = getClass().getResource(reportStreamPath);
				
				printFileName = JasperFillManager.fillReportToFile(sourceFileName.toURI().getPath(),
			            map, beanColDataSource);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	            Calendar calendar = Calendar.getInstance();
				String reportName = exportName + "_" + employeeLoggedIn.getSectionId() 
						+ "_" + sdf.format(calendar.getTime()) + ".xls";
				
				if (printFileName != null) {
					JRXlsxExporter exporter = new JRXlsxExporter();

		            exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME,
		                  printFileName);

		            
		            ServletContext servletContext = getServletContext();
		    		String contextPath = servletContext.getRealPath(File.separator);	    		
		    		String destinationURL = contextPath + "/report/" + reportName;	            
		            
		            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
		            		destinationURL);

		            exporter.exportReport();
				}
				
				ISystemTrailService sysTrailService = new SystemTrailService();
				SystemTrail sysModel = new SystemTrail();
				
				sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
				
				if(forExport.equals("Y")) {
					sysModel.setProcessDesc("EXPORT PAYROLL REGISTER REPORT");
					sysModel.setProcessType("EXPORT");
				} else {
					sysModel.setProcessDesc("VIEW PDF PAYROLL REGISTER REPORT");
					sysModel.setProcessType("VIEW PDF");
				}
				
				sysModel.setModuleName("PAYROLL REGISTER REPORT");
				
				sysModel.setUserId(employeeLoggedIn.getEmpId());

			
				sysTrailService.insertSystemTrail(sysModel);
				
				connection.close();
				
				ServletOutputStream out = response.getOutputStream();
				response.setContentType("application/json");	
				String jsonObject = gson.toJson(reportName);
				out.print(jsonObject);
				out.close();
			} else {
						
				JasperRunManager.runReportToPdfStream(reportStream,	response.getOutputStream(), map, beanColDataSource);
				connection.close();
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
