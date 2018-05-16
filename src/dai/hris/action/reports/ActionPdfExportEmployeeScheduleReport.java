package dai.hris.action.reports;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import dai.hris.model.AttendanceReport;
import dai.hris.model.Employee;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.reports.IReportService;
import dai.hris.service.reports.ReportService;

/**
 * Servlet implementation class ActionPdfExportFMJobTitleListReport
 */
@WebServlet("/ActionPdfExportEmployeeScheduleReport")
public class ActionPdfExportEmployeeScheduleReport extends HttpServlet {
	private static final long serialVersionUID = 5676921778006010751L;
	private static Logger logger = Logger.getLogger(ActionPdfExportEmployeeScheduleReport.class);
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
		
		InputStream reportStream = getClass().getClassLoader().getResourceAsStream( "dai/hris/reports/ScheduleSummaryReport.jasper");
		if(reportStream == null){
			logger.debug("reportStream is NULL");
		}
		
						
		try {
						
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("HOSPITAL_NAME", ReportConstant.COMPANY_NAME);
			map.put("REPORT_TITLE", "EMPLOYEE SCHEDULE SUMMARY REPORT");
			map.put("empName", employeeLoggedIn.getFirstname() + " " + employeeLoggedIn.getLastname());
			map.put("empPosition", employeeLoggedIn.getJobTitleName());
			map.put("empNo", employeeLoggedIn.getEmpNo());
			map.put("departmentName", employeeLoggedIn.getSectionName());
			//map.put("REPORT_SUBTITLE", "WE HEREBY ACKNOWLEDGE to have received from EAMC, the sums therein specified opposite our compensation for our PROFESSIONAL FEE FOR JUNE 8-15, except as noted otherwise in the remaks column.");
			
			
			//map.put("departmentId", Integer.parseInt(departmentId));
			
			IReportService service = new ReportService();
			String forExport = request.getParameter("forExport");
			List<AttendanceReport> dataList = new ArrayList<AttendanceReport>();	
			
			dataList = service.getScheduleReportListByDateAndIdReport(employeeLoggedIn.getEmpId(), dateFrom, dateTo);
			
			
			if(dataList.isEmpty()){
	
				
				AttendanceReport model = new AttendanceReport();
				
				model.setEmpName("");
				model.setEmpNo("");
				model.setDescription("");
	    		model.setScheduleDate("");
	    		
				dataList.add(model);		
				
			}
			
			
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			Connection connection = null;
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			connection = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL); 	
			
			if(forExport.equals("Y")) {
				String printFileName = null;		
				URL sourceFileName = getClass().getResource("/dai/hris/reports/ScheduleSummaryReport.jasper");
				
				printFileName = JasperFillManager.fillReportToFile(sourceFileName.toURI().getPath(),
			            map, beanColDataSource);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	            Calendar calendar = Calendar.getInstance();
				String reportName = "scheduleSummaryReport" + "_" + employeeLoggedIn.getSectionId() 
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
					sysModel.setProcessDesc("EXPORT EMPLOYEE SCHEDULE SUMMARY REPORT");
					sysModel.setProcessType("EXPORT");
				} else {
					sysModel.setProcessDesc("VIEW PDF EMPLOYEE SCHEDULE SUMMARY REPORT");
					sysModel.setProcessType("VIEW PDF");
				}
				
				sysModel.setModuleName("EMPLOYEE SCHEDULE SUMMARY REPORT");
				
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
