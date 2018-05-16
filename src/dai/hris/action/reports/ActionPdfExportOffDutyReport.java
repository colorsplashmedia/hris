package dai.hris.action.reports;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
//import java.math.BigDecimal;
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
import dai.hris.model.OffDutyReport;
//import dai.hris.model.Payslip;
import dai.hris.model.PayslipZamboanga;
import dai.hris.model.SystemTrail;
import dai.hris.model.Unit;
import dai.hris.service.filemanager.employee.EmployeeService;
import dai.hris.service.filemanager.employee.IEmployeeService;
import dai.hris.service.filemanager.unit.IUnitService;
import dai.hris.service.filemanager.unit.UnitService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.reports.IReportService;
import dai.hris.service.reports.ReportService;

/**
 * Servlet implementation class ActionPdfExportOffDutyReport
 */
@WebServlet("/ActionPdfExportOffDutyReport")
public class ActionPdfExportOffDutyReport extends HttpServlet {
	private static final long serialVersionUID = 5676921778006010751L;
	private static Logger logger = Logger.getLogger(ActionPdfExportOffDutyReport.class);
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
		
		String reportStreamPath = "dai/hris/reports/OffDutySchedule.jasper";
		String exportName = "offDutyReport";
		
		String forExport = request.getParameter("forExport");
		int month = Integer.parseInt(request.getParameter("month"));
		int year = Integer.parseInt(request.getParameter("year"));
		String signatory1 = request.getParameter("signatory1");
		String signatory2 = request.getParameter("signatory2");
		String signatory3 = request.getParameter("signatory3");
		String supervisingOfficer = request.getParameter("supervisingOfficer");
		String unitId = request.getParameter("unitId");
		
		String position1 = request.getParameter("position1");
		String position2 = request.getParameter("position2");
		String position3 = request.getParameter("position3");
				
		InputStream reportStream = getClass().getClassLoader().getResourceAsStream(reportStreamPath);
		if(reportStream == null){
			logger.debug("reportStream is NULL");
		}
		
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
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String hospitalLogo = request.getServletContext().getRealPath(File.separator) + ReportConstant.HOSPITAL_LOGO_URL; 
		String dohLogo = request.getServletContext().getRealPath(File.separator) + ReportConstant.DOH_LOGO_URL; 
		map.put("DOH_LOGO", dohLogo);	
		map.put("HOSPITAL_LOGO", hospitalLogo);	
		map.put("HOSPITAL_NAME", ReportConstant.COMPANY_NAME);
		
		map.put("HOSPITAL_ADD", ReportConstant.HOSPITAL_ADD);			
		map.put("COUNTRY_NAME", ReportConstant.COUNTRY_NAME);	
		map.put("DOH_LABEL", ReportConstant.DOH_LABEL);	
		
		map.put("REPORT_NAME", "OFF DUTY SCHEDULE");	
		map.put("REPORT_SUB_NAME", "For the month of " + monthString + " " + year);	
		
		map.put("signatory1", signatory1 != null ? signatory1 : "");
		map.put("signatory2", signatory2 != null ? signatory2 : "");
		map.put("signatory3", signatory3 != null ? signatory3 : "");
		map.put("supervisingNurse", supervisingOfficer != null ? supervisingOfficer : "");
		map.put("position1", position1 != null ? position1 : "");
		map.put("position2", position2 != null ? position2 : "");
		map.put("position3", position3 != null ? position3 : "");
		
		IUnitService uservice = new UnitService();
		
		
		
		IReportService service = new ReportService();	
		
		try {
						
			Unit unit = uservice.getUnit(Integer.parseInt(unitId));
			
			map.put("departmentName", unit.getUnitName());
			
			List<OffDutyReport> dataList = new ArrayList<OffDutyReport>();	
			
			dataList = service.getOffDutyReport(Integer.parseInt(unitId), month, year);
			int numberOfExtra = 0;
			if(dataList.size() < 16) {
				numberOfExtra = 16 - dataList.size();
			} else {
				int remainder = dataList.size() % 16;				
				numberOfExtra = 16 - remainder;
			}
			
			for(int x = 0; x < numberOfExtra; x++){
				OffDutyReport model = new OffDutyReport();
				model.setEmpName("");
				model.setDay1("W");
				model.setDay2("W");
				model.setDay3("W");
				model.setDay4("W");
				model.setDay5("W");
				model.setDay6("W");
				model.setDay7("W");
				model.setDay8("W");
				model.setDay9("W");
				model.setDay10("W");
				model.setDay11("W");
				model.setDay12("W");
				model.setDay13("W");
				model.setDay14("W");
				model.setDay15("W");
				model.setDay16("W");
				model.setDay17("W");
				model.setDay18("W");
				model.setDay19("W");
				model.setDay20("W");
				model.setDay21("W");
				model.setDay22("W");
				model.setDay23("W");
				model.setDay24("W");
				model.setDay25("W");
				model.setDay26("W");
				model.setDay27("W");
				model.setDay28("W");
				model.setDay29("W");
				model.setDay30("W");
				model.setDay31("W");
				
				dataList.add(model);
			}
			
			
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			Connection connection = null;
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			connection = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL); 	
			
			if(forExport.equals("Y")) {
				String printFileName = null;		
				URL sourceFileName = getClass().getResource(reportStreamPath);
				
				printFileName = JasperFillManager.fillReportToFile(sourceFileName.toURI().getPath(), map, beanColDataSource);
				
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
					sysModel.setProcessDesc("EXPORT OFF DUTY REPORT");
					sysModel.setProcessType("EXPORT");
				} else {
					sysModel.setProcessDesc("VIEW PDF OFF DUTY REPORT");
					sysModel.setProcessType("VIEW PDF");
				}
				
				sysModel.setModuleName("OFF DUTY REPORT");
				
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
