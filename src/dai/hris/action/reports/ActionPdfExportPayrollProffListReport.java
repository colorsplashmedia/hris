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
import dai.hris.model.Employee;
import dai.hris.model.PayrollProofList;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.employee.EmployeeService;
import dai.hris.service.filemanager.employee.IEmployeeService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.reports.IReportService;
import dai.hris.service.reports.ReportService;

/**
 * Servlet implementation class ActionPdfExportPayrollProffListReport
 */
@WebServlet("/ActionPdfExportPayrollProffListReport")
public class ActionPdfExportPayrollProffListReport extends HttpServlet {
	private static final long serialVersionUID = 5676921778006010751L;
	private static Logger logger = Logger.getLogger(ActionPdfExportPayrollProffListReport.class);
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
		
		String forExport = request.getParameter("forExport");
		String monthStr = request.getParameter("month");
		int month = Integer.parseInt(request.getParameter("month"));
		int year = Integer.parseInt(request.getParameter("year"));
		String signatory1 = request.getParameter("signatory1");
		String signatory2 = request.getParameter("signatory2");
		String signatory3 = request.getParameter("signatory3");
		String signatory4 = request.getParameter("signatory4");
		String signatory5 = request.getParameter("signatory5");
		
		String position1 = request.getParameter("position1");
		String position2 = request.getParameter("position2");
		String position3 = request.getParameter("position3");
		String position4 = request.getParameter("position4");
		String position5 = request.getParameter("position5");

				
		InputStream reportStream = getClass().getClassLoader().getResourceAsStream( "dai/hris/reports/PayrollProofListReport.jasper");
		if(reportStream == null){
			logger.debug("reportStream is NULL");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String monthString;
		switch (month) {
	        case 1:  monthString = "January";
	                 break;
	        case 2:  monthString = "February";
	                 break;
	        case 3:  monthString = "March";
	                 break;
	        case 4:  monthString = "April";
	                 break;
	        case 5:  monthString = "May";
	                 break;
	        case 6:  monthString = "June";
	                 break;
	        case 7:  monthString = "July";
	                 break;
	        case 8:  monthString = "August";
	                 break;
	        case 9:  monthString = "September";
	                 break;
	        case 10: monthString = "October";
	                 break;
	        case 11: monthString = "November";
	                 break;
	        case 12: monthString = "December";
	                 break;
	        default: monthString = "Invalid month";
	                 break;
	    }
		
		map.put("HOSPITAL_NAME", ReportConstant.COMPANY_NAME);
		map.put("REPORT_NAME", "Payroll Prooflist for " + monthString + " " + year);	
		
		map.put("signatory1", signatory1 != null ? signatory1 : "");
		map.put("signatory2", signatory2 != null ? signatory2 : "");
		map.put("signatory3", signatory3 != null ? signatory3 : "");
		map.put("signatory4", signatory4 != null ? signatory4 : "");
		map.put("signatory5", signatory5 != null ? signatory5 : "");
		
		map.put("position1", position1 != null ? position1 : "");
		map.put("position2", position2 != null ? position2 : "");
		map.put("position3", position3 != null ? position3 : "");
		map.put("position4", position4 != null ? position4 : "");
		map.put("position5", position5 != null ? position5 : "");
		
		
		IReportService service = new ReportService();		
						
		try {
			
			
			List<PayrollProofList> dataList = new ArrayList<PayrollProofList>();	
						
			dataList = service.getPayrollProofListReport(month, year);					
			
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			Connection connection = null;
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			connection = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL); 	
			
			if(forExport.equals("Y")) {
				String printFileName = null;		
				URL sourceFileName = getClass().getResource("/dai/hris/reports/PayrollProofListReport./.jasper");
				
				printFileName = JasperFillManager.fillReportToFile(sourceFileName.toURI().getPath(), map, beanColDataSource);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	            Calendar calendar = Calendar.getInstance();
				String reportName = "payslipReport" + "_" + employeeLoggedIn.getSectionId() 
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
					sysModel.setProcessDesc("EXPORT PAYSLIP REPORT");
					sysModel.setProcessType("EXPORT");
				} else {
					sysModel.setProcessDesc("VIEW PDF PAYSLIP REPORT");
					sysModel.setProcessType("VIEW PDF");
				}
				
				sysModel.setModuleName("PAYSLIP REPORT");
				
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
