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
import dai.hris.model.PayrollPeriod;
import dai.hris.model.PayslipZamboanga;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.employee.EmployeeService;
import dai.hris.service.filemanager.employee.IEmployeeService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.payroll.IPayrollPeriodService;
import dai.hris.service.payroll.impl.PayrollPeriodService;
import dai.hris.service.reports.IReportService;
import dai.hris.service.reports.ReportService;

/**
 * Servlet implementation class ActionPdfExportCertificateOfEmploymentReport
 */
@WebServlet("/ActionPdfExportCertificateOfEmploymentReport")
public class ActionPdfExportCertificateOfEmploymentReport extends HttpServlet {
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
		
		//Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");		
		
		String signatory1 = request.getParameter("signatory1");
		String position1 = request.getParameter("position1");
		String purpose = request.getParameter("purpose");
		String empId = request.getParameter("empId");
		
		String forExport = request.getParameter("forExport");
		
		InputStream reportStream = getClass().getClassLoader().getResourceAsStream( "dai/hris/reports/EmploymentCertReport.jasper");
		if(reportStream == null){
			logger.debug("reportStream is NULL");
		}
								
		try {
			IReportService service = new ReportService();
			IEmployeeService empService = new EmployeeService();
			Employee modelEmp = empService.getEmployee(Integer.parseInt(empId));
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			String hospitalLogo = request.getServletContext().getRealPath(File.separator) + ReportConstant.HOSPITAL_LOGO_URL; 
			String dohLogo = request.getServletContext().getRealPath(File.separator) + ReportConstant.DOH_LOGO_URL; 
			map.put("DOH_LOGO", dohLogo);	
			map.put("HOSPITAL_LOGO", hospitalLogo);	
			map.put("HOSPITAL_NAME", ReportConstant.COMPANY_NAME);
			map.put("HOSPITAL_ADD", ReportConstant.HOSPITAL_ADD);			
			map.put("COUNTRY_NAME", ReportConstant.COUNTRY_NAME);	
			map.put("DOH_LABEL", ReportConstant.DOH_LABEL);	
			map.put("REPORT_NAME", "CERTIFICATION");
			if("M".equals(modelEmp.getGender())){
				map.put("empName", "Mr. " + modelEmp.getFirstname() + " "+ modelEmp.getMiddlename() + " " + modelEmp.getLastname());
				map.put("empFirstName", "Mr. " + modelEmp.getLastname());
			} else {
				map.put("empName", "Ms. " + modelEmp.getFirstname() + " "+ modelEmp.getMiddlename() + " " + modelEmp.getLastname());
				map.put("empFirstName", "Ms. " + modelEmp.getLastname());
			}
			
			map.put("jobTitle", modelEmp.getJobTitleName());
			map.put("signatory1", signatory1);
			map.put("position1", position1);
			map.put("purpose", purpose);
			
			List<Employee> empIdList = new ArrayList<Employee>();
			
			//IEmployeeService empService = new EmployeeService();
			
			empIdList = empService.getAll();
			
			List<PayslipZamboanga> dataList = new ArrayList<PayslipZamboanga>();	
			
			dataList = service.getPayrollRegisterReport(empIdList, 1);	
			
			for(PayslipZamboanga payslip : dataList){
				map.put("salary", payslip.getBasicPay());
				
				BigDecimal otherIncome = BigDecimal.ZERO;
				otherIncome = payslip.getOtherTaxableIncome().add(payslip.getNontaxableIncome());
				
				map.put("otherIncome", otherIncome);
				map.put("totalDeductions", payslip.getTotalDeductions());
				map.put("netIncome", payslip.getNetPay());
				
				break;
			}
			
			
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
		    }						
			
						
			
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			Connection connection = null;
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			connection = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL); 	
			
			if(forExport.equals("Y")) {
				String printFileName = null;		
				URL sourceFileName = getClass().getResource("/dai/hris/reports/PayrollRegister.jasper");
				
				printFileName = JasperFillManager.fillReportToFile(sourceFileName.toURI().getPath(),
			            map, beanColDataSource);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	            Calendar calendar = Calendar.getInstance();
				String reportName = "payrollRegisterListReport" + "_" + modelEmp.getSectionId() 
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
				
				sysModel.setDepartmentId(modelEmp.getSectionId());
				
				if(forExport.equals("Y")) {
					sysModel.setProcessDesc("EXPORT PAYROLL REGISTER REPORT");
					sysModel.setProcessType("EXPORT");
				} else {
					sysModel.setProcessDesc("VIEW PDF PAYROLL REGISTER REPORT");
					sysModel.setProcessType("VIEW PDF");
				}
				
				sysModel.setModuleName("PAYROLL REGISTER REPORT");
				
				sysModel.setUserId(modelEmp.getEmpId());

			
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
