package dai.hris.action.reports;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import java.net.URISyntaxException;
import java.net.URL;

import com.google.gson.Gson;

import dai.hris.model.Employee;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.dao.DBConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;



/**
 * Servlet implementation class Counter
 */
@WebServlet("/ActionPdfExportSystemTrailReport")
public class ActionPdfExportSystemTrailReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ActionPdfExportSystemTrailReport.class);
	Gson gson = new Gson();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActionPdfExportSystemTrailReport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost( request,  response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
//		String userIdStr = request.getParameter("userId");
//		String processType = request.getParameter("processType");
//		String moduleName = request.getParameter("moduleName");
		String forExport = request.getParameter("forExport");	
		
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");

		Map<String, Object> map = new HashMap<String, Object>();
		
		String hospitalLogo = request.getServletContext().getRealPath(File.separator) + ReportConstant.HOSPITAL_LOGO_URL; 
		String dohLogo = request.getServletContext().getRealPath(File.separator) + ReportConstant.DOH_LOGO_URL; 
		map.put("DOH_LOGO", dohLogo);	
		map.put("HOSPITAL_LOGO", hospitalLogo);	
		map.put("HOSPITAL_NAME", ReportConstant.COMPANY_NAME);
		map.put("HOSPITAL_ADD", ReportConstant.HOSPITAL_ADD);			
		map.put("COUNTRY_NAME", ReportConstant.COUNTRY_NAME);	
		map.put("DOH_LABEL", ReportConstant.DOH_LABEL);		
		map.put("REPORT_NAME", "SYSTEM TRAIL REPORT");	
		
		
		InputStream reportStream = getClass().getClassLoader().getResourceAsStream( "dai/hris/reports/SystemTrailReportHRIS.jasper");
		if(reportStream == null){
			logger.debug("reportStream is NULL");
		}
			
		URL sourceFileName = getClass().getResource("/dai/hris/reports/SystemTrailReportHRIS.jasper");		

		String printFileName = null;			
		
		Connection connection;		

		response.setContentType("application/pdf");
		
		
		int userId = 0;
		
		
		try {		
			
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("SYSTEM TRAIL REPORT");
			if(forExport.equals("Y")) {
				sysModel.setProcessDesc("EXPORT SYSTEM TRAIL REPORT");
				sysModel.setProcessType("EXPORT");
			} else {
				sysModel.setProcessDesc("VIEW PDF SYSTEM TRAIL REPORT");
				sysModel.setProcessType("VIEW PDF");
			}
			sysModel.setUserId(employeeLoggedIn.getEmpId());

		
			sysTrailService.insertSystemTrail(sysModel);
			
//			if(userIdStr != null && userIdStr.length() > 0){
//				userId = Integer.parseInt(userIdStr);
//			}
			
			SystemTrail parameter = new SystemTrail();
			
			parameter.setDepartmentId(employeeLoggedIn.getSectionId());
//			parameter.setModuleName(moduleName);			
//			parameter.setProcessType(processType);
			parameter.setUserId(userId);
			parameter.setDateFrom(dateFrom);
			parameter.setDateTo(dateTo);
			
			List<SystemTrail> list = sysTrailService.getSystemTrailReportDetails(parameter);
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
			
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			connection = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL); 			
			
			if(forExport.equals("Y")) {
				printFileName = JasperFillManager.fillReportToFile(sourceFileName.toURI().getPath(),
			            map, beanColDataSource);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	            Calendar calendar = Calendar.getInstance();
				String reportName = "system_trail_report" + "_" + employeeLoggedIn.getSectionId() 
						+ "_" + sdf.format(calendar.getTime()) + ".xls";
				
				if (printFileName != null) {
					JRXlsxExporter exporter = new JRXlsxExporter();

		            exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME,
		                  printFileName);
//		            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
//		                  "C://reports//system_trail_report.xls");
		            
		            ServletContext servletContext = getServletContext();
		    		String contextPath = servletContext.getRealPath(File.separator);	    		
		    		String destinationURL = contextPath + "/report/" + reportName;	            
		            
		            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
		            		destinationURL);

		            exporter.exportReport();
				}
				
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
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
