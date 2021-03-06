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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
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
import dai.hris.model.Payslip;
import dai.hris.model.SystemTrail;
import dai.hris.model.TimeEntry;
import dai.hris.service.filemanager.employee.EmployeeService;
import dai.hris.service.filemanager.employee.IEmployeeService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.reports.IReportService;
import dai.hris.service.reports.ReportService;
import dai.hris.service.timeentry.ITimeEntryService;
import dai.hris.service.timeentry.TimeEntryService;

/**
 * Servlet implementation class ActionPdfExportFMJobTitleListReport
 */
@WebServlet("/ActionPdfExportDTRReport")
public class ActionPdfExportDTRReport extends HttpServlet {
	private static final long serialVersionUID = 5676921778006010751L;
	private static Logger logger = Logger.getLogger(ActionPdfExportDTRReport.class);
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
		int month = Integer.parseInt(request.getParameter("month"));
		int year = Integer.parseInt(request.getParameter("year"));
		String verifiedBy = request.getParameter("verifiedBy");
		
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
        
        String empName = employeeLoggedIn.getFirstname() + " " + employeeLoggedIn.getLastname();	
        
        Calendar mycal = new GregorianCalendar(year, (month - 1), 1);

		// Get the number of days in that month
		int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		String totalDays = "" + daysInMonth;
		String totalUnderTime = "0";

				
		
		InputStream reportStream = getClass().getClassLoader().getResourceAsStream( "dai/hris/reports/DTR_Report.jasper");
		if(reportStream == null){
			logger.debug("reportStream is NULL");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("HOSPITAL_NAME", ReportConstant.COMPANY_NAME);
		map.put("REPORT_TITLE", "DAILY TIME RECORD");
		map.put("DTR_MONTH", monthString + " " + year);
		map.put("EMP_NAME", empName);
		map.put("EMP_POSITION", employeeLoggedIn.getJobTitleName());
		map.put("TOTAL_DAYS", totalDays);
		map.put("TOTAL_UNDERTIME", totalUnderTime);
		map.put("SUPERVISOR", verifiedBy);
		map.put("EMP_ID", employeeLoggedIn.getEmpId());
		
		
		IReportService rptService = new ReportService();		
		
		ITimeEntryService service = new TimeEntryService();
		
		
		
		String timeBlank = "";		
		
		for(int day = 1; day <= 31; day++){
			for(int column = 1; column <= 5; column++){
				map.put("TIME" + day + "_" + column, timeBlank);
			}
		}
		
		
		List<Employee> empIdList = new ArrayList<Employee>();
		empIdList.add(employeeLoggedIn);
				
		try {
			
			List<TimeEntry> list = service.getAllTimeEntryByMonthEmpId(employeeLoggedIn.getEmpId(), month, year);
			
			Iterator<TimeEntry> i = list.iterator();
			
			while(i.hasNext()) {
				TimeEntry model = i.next();
				
				//check if time in is blank
				if(model.getTimeIn() != null && model.getTimeIn().length() > 1){
					
					int hrs = Integer.parseInt(model.getTimeIn().substring(11,13));
					int day = Integer.parseInt(model.getTimeIn().substring(8,10));
					
					//CHECK IF AM or PM
					if(hrs >= 0 && hrs < 12){
						if(hrs == 0) {
							map.put("TIME" + day + "_" + "1", "12" + model.getTimeIn().substring(13,16) + " AM");
						} else {
							map.put("TIME" + day + "_" + "1", hrs + model.getTimeIn().substring(13,16) + " AM");
						}						
					} else if (hrs >= 12) {
						
						//Convert Military Time
						if(hrs > 12){
							map.put("TIME" + day + "_" + "3", (hrs - 12) + model.getTimeIn().substring(13,16) + " PM");							
						} else {
							map.put("TIME" + day + "_" + "3", hrs + model.getTimeIn().substring(13,16) + " PM");
						}
					}
				}
				
				if(model.getTimeOut() != null && model.getTimeOut().length() > 1){
					
					int hrs = Integer.parseInt(model.getTimeOut().substring(11,13));
					int day = Integer.parseInt(model.getTimeOut().substring(8,10));
					
					//CHECK IF AM or PM
					if(hrs >= 0 && hrs < 12){
						if(hrs == 0) {
							map.put("TIME" + day + "_" + "2", "12" + model.getTimeOut().substring(13,16) + " AM");
						} else {
							map.put("TIME" + day + "_" + "2", hrs + model.getTimeOut().substring(13,16) + " AM");
						}						
					} else if (hrs >= 12) {
						
						//Convert Military Time
						if(hrs > 12){
							map.put("TIME" + day + "_" + "4", (hrs - 12) + model.getTimeOut().substring(13,16) + " PM");							
						} else {
							map.put("TIME" + day + "_" + "4", hrs + model.getTimeOut().substring(13,16) + " PM");
						}
					}
				}
				
			}
					
			
			
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(empIdList);
			Connection connection = null;
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			connection = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL); 	
			
			if(forExport.equals("Y")) {
				String printFileName = null;		
				URL sourceFileName = getClass().getResource("/dai/hris/reports/DTR_Report.jasper");
				
				printFileName = JasperFillManager.fillReportToFile(sourceFileName.toURI().getPath(),
			            map, beanColDataSource);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	            Calendar calendar = Calendar.getInstance();
				String reportName = "employeeDTR_Report" + "_" + employeeLoggedIn.getSectionId() 
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
					sysModel.setProcessDesc("EXPORT DTR REPORT");
					sysModel.setProcessType("EXPORT");
				} else {
					sysModel.setProcessDesc("VIEW PDF DTR REPORT");
					sysModel.setProcessType("VIEW PDF");
				}
				
				sysModel.setModuleName("DTR REPORT");
				
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
