package dai.hris.action.reports;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
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
import dai.hris.model.CaseRatePayment;
import dai.hris.model.City;
import dai.hris.model.Employee;
import dai.hris.model.EmployeeMemo;
import dai.hris.model.EmployeeOutOfOffice;
import dai.hris.model.EmployeeOvertime;
import dai.hris.model.ProfessionalFee;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.city.CityService;
import dai.hris.service.filemanager.city.ICityService;
import dai.hris.service.filemanager.employee.EmployeeService;
import dai.hris.service.filemanager.employee.IEmployeeService;
import dai.hris.service.filemanager.employeememo.EmployeeMemoService;
import dai.hris.service.filemanager.employeememo.IEmployeeMemoService;
import dai.hris.service.filemanager.employeeoutofoffice.EmployeeOutOfOfficeService;
import dai.hris.service.filemanager.employeeoutofoffice.IEmployeeOutOfOfficeService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.overtime.EmployeeOvertimeService;
import dai.hris.service.overtime.IEmployeeOvertimeService;
import dai.hris.service.payroll.ICaseRatePaymentService;
import dai.hris.service.payroll.IProfessionalFeeService;
import dai.hris.service.payroll.impl.CaseRatePaymentService;
import dai.hris.service.payroll.impl.ProfessionalFeeService;

/**
 * Servlet implementation class ActionPdfExportFMJobTitleListReport
 */
@WebServlet("/ActionPdfExportOutOfOfficeSummaryReport")
public class ActionPdfExportOutOfOfficeSummaryReport extends HttpServlet {
	private static final long serialVersionUID = 5676921778006010751L;
	private static Logger logger = Logger.getLogger(ActionPdfExportOutOfOfficeSummaryReport.class);
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

		
		
//		int empId = Integer.parseInt(request.getParameter("empId"));
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		
		InputStream reportStream = getClass().getClassLoader().getResourceAsStream( "dai/hris/reports/OutOfOfficeSummaryReport.jasper");
		if(reportStream == null){
			logger.debug("reportStream is NULL");
		}
		
						
		try {
						
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("HOSPITAL_NAME", ReportConstant.COMPANY_NAME);
			map.put("REPORT_TITLE", "OUT OF OFFICE SUMMARY REPORT");
			map.put("empName", employeeLoggedIn.getFirstname() + " " + employeeLoggedIn.getLastname());
			map.put("empPosition", employeeLoggedIn.getJobTitleName());
			map.put("empNo", employeeLoggedIn.getEmpNo());
			map.put("departmentName", employeeLoggedIn.getSectionName());
			//map.put("REPORT_SUBTITLE", "WE HEREBY ACKNOWLEDGE to have received from EAMC, the sums therein specified opposite our compensation for our PROFESSIONAL FEE FOR JUNE 8-15, except as noted otherwise in the remaks column.");
			
			
			//map.put("departmentId", Integer.parseInt(departmentId));
			
			IEmployeeOutOfOfficeService service = new EmployeeOutOfOfficeService();
			String forExport = request.getParameter("forExport");
			List<EmployeeOutOfOffice> dataList = new ArrayList<EmployeeOutOfOffice>();	
			
	
			dataList = service.getOutOfOfficeByDateRange(dateFrom, dateTo);
			
			if(dataList.isEmpty()){
	
		    	
		    	EmployeeOutOfOffice eooo = new EmployeeOutOfOffice();
	    		
	    		eooo.setApprovedBy(0);
	    		eooo.setCreatedBy(0);
	    		eooo.setCreatedDate(new Date(0));
	    		eooo.setDateFrom("");
	    		eooo.setDateTo("");
	    		eooo.setEmpId(0);
	    		eooo.setEmpOOOId(0);
	    		eooo.setNoOfHours(0);
	    		eooo.setProvider("");
	    		eooo.setRemarks("");
	    		eooo.setSecondaryApprover(0);
	    		eooo.setStatus(10);
	    		eooo.setTitleActivity("");
	 	    	
	    		
				dataList.add(eooo);
				
				
				
			}
			
			
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			Connection connection = null;
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			connection = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL); 	
			
			if(forExport.equals("Y")) {
				String printFileName = null;		
				URL sourceFileName = getClass().getResource("/dai/hris/reports/OutOfOfficeSummaryReport.jasper");
				
				printFileName = JasperFillManager.fillReportToFile(sourceFileName.toURI().getPath(),
			            map, beanColDataSource);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	            Calendar calendar = Calendar.getInstance();
				String reportName = "OutOfOfficeSummaryReport" + "_" + employeeLoggedIn.getSectionId() 
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
					sysModel.setProcessDesc("EXPORT GENERAL PAYROLL CASE RATE REPORT");
					sysModel.setProcessType("EXPORT");
				} else {
					sysModel.setProcessDesc("VIEW PDF GENERAL PAYROLL CASE RATE REPORT");
					sysModel.setProcessType("VIEW PDF");
				}
				
				sysModel.setModuleName("GENERAL PAYROLL CASE RATE REPORT");
				
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
