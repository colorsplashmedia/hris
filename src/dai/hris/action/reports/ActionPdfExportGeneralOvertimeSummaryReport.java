package dai.hris.action.reports;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import dai.hris.model.Employee;
import dai.hris.model.EmployeeOvertime;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.overtime.EmployeeOvertimeService;
import dai.hris.service.overtime.IEmployeeOvertimeService;

/**
 * Servlet implementation class ActionPdfExportFMJobTitleListReport
 */
@WebServlet("/ActionPdfExportGeneralOvertimeSummaryReport")
public class ActionPdfExportGeneralOvertimeSummaryReport extends HttpServlet {
	private static final long serialVersionUID = 5676921778006010751L;
	private static Logger logger = Logger.getLogger(ActionPdfExportGeneralOvertimeSummaryReport.class);
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
		
		InputStream reportStream = getClass().getClassLoader().getResourceAsStream( "dai/hris/reports/OvertimeSummary.jasper");
		if(reportStream == null){
			logger.debug("reportStream is NULL");
		}
		
						
		try {
						
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("HOSPITAL_NAME", ReportConstant.COMPANY_NAME);
			map.put("REPORT_TITLE", "OVERTIME SUMMARY REPORT");
			map.put("dateFrom", dateFrom);
			map.put("dateTo", dateTo);
			//map.put("REPORT_SUBTITLE", "WE HEREBY ACKNOWLEDGE to have received from EAMC, the sums therein specified opposite our compensation for our PROFESSIONAL FEE FOR JUNE 8-15, except as noted otherwise in the remaks column.");
			
			
			//map.put("departmentId", Integer.parseInt(departmentId));
			
			IEmployeeOvertimeService service = new EmployeeOvertimeService();
			String forExport = request.getParameter("forExport");
			List<EmployeeOvertime> dataList = new ArrayList<EmployeeOvertime>();	
			
	
			dataList = service.getOvertimeByDateRange(dateFrom, dateTo);
			
			if(dataList.isEmpty()){
	
				
				EmployeeOvertime eo = new EmployeeOvertime();
				
				eo.setEmpOvertimeId(0);
				eo.setEmpId(0);
				eo.setDateRendered("");
				eo.setNoOfHours(0);
				eo.setStatus(10);
				eo.setRemarks("");
				eo.setApprovedBy(0);
				eo.setSecondaryApprover(0);
				eo.setCreatedBy(0);
		    	eo.setCreatedDate(new Date(0));
	    		
				dataList.add(eo);
				
				
				
			}
			
			
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			Connection connection = null;
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			connection = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL); 	
			
			if(forExport.equals("Y")) {
				String printFileName = null;		
				URL sourceFileName = getClass().getResource("/dai/hris/reports/OvertimeSummary.jasper");
				
				printFileName = JasperFillManager.fillReportToFile(sourceFileName.toURI().getPath(),
			            map, beanColDataSource);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	            Calendar calendar = Calendar.getInstance();
				String reportName = "overtimeSummaryReport" + "_" + employeeLoggedIn.getSectionId() 
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
					sysModel.setProcessDesc("EXPORT GENERAL OVERTIME REPORT");
					sysModel.setProcessType("EXPORT");
				} else {
					sysModel.setProcessDesc("VIEW PDF GENERAL OVERTIME REPORT");
					sysModel.setProcessType("VIEW PDF");
				}
				
				sysModel.setModuleName("GENERAL OVERTIME REPORT");
				
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
