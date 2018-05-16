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
import dai.hris.model.PagibigRemittance;
import dai.hris.model.PayrollProofList;
//import dai.hris.model.Payslip;
import dai.hris.model.PayslipZamboanga;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.employee.EmployeeService;
import dai.hris.service.filemanager.employee.IEmployeeService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.reports.IReportService;
import dai.hris.service.reports.ReportService;

/**
 * Servlet implementation class ActionPdfExportOvertimeAuthorizationReport
 */
@WebServlet("/ActionPdfExportOvertimeAuthorizationReport")
public class ActionPdfExportOvertimeAuthorizationReport extends HttpServlet {
	private static final long serialVersionUID = 5676921778006010751L;
	private static Logger logger = Logger.getLogger(ActionPdfExportOvertimeAuthorizationReport.class);
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
		
		String reportStreamPath = "dai/hris/reports/OvertimeAuthority.jasper";
		String exportName = "overtimeAuthorityReport";
		
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");	
		
		String forExport = request.getParameter("forExport");
		String position1 = request.getParameter("position1");
		String position2 = request.getParameter("position2");
		String position3 = request.getParameter("position3");
		String signatory1 = request.getParameter("signatory1");	
		String signatory2 = request.getParameter("signatory2");	
		String signatory3 = request.getParameter("signatory3");	
		
		String divisionHead = request.getParameter("divisionHead");	
		String divisionHeadPosition = request.getParameter("divisionHeadPosition");	
		String purposeOfOvertime = request.getParameter("purposeOfOvertime");	
		String activities1 = request.getParameter("activities1");	
		String estQty1 = request.getParameter("estQty1");	
		String estMH1 = request.getParameter("estMH1");	
		String period1 = request.getParameter("period1");	
		String time1 = request.getParameter("time1");	
		

				
		InputStream reportStream = getClass().getClassLoader().getResourceAsStream(reportStreamPath);
		if(reportStream == null){
			logger.debug("reportStream is NULL");
		}		
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String hospitalLogo = request.getServletContext().getRealPath(File.separator) + ReportConstant.HOSPITAL_LOGO_URL; 
		String dohLogo = request.getServletContext().getRealPath(File.separator) + ReportConstant.DOH_LOGO_URL; 
		map.put("DOH_LOGO", dohLogo);	
		map.put("HOSPITAL_LOGO", hospitalLogo);	
		map.put("HOSPITAL_NAME", ReportConstant.COMPANY_NAME);
		map.put("REGION", ReportConstant.REGION);		
		map.put("HOSPITAL_ADD", ReportConstant.HOSPITAL_ADD);			
		map.put("COUNTRY_NAME", ReportConstant.COUNTRY_NAME);	
		map.put("DOH_LABEL", ReportConstant.DOH_LABEL);		
		map.put("REPORT_NAME", "OVERTIME AUTHORITY");		
		
		map.put("signatory1", signatory1);		
		map.put("signatory2", signatory2);		
		map.put("signatory3", signatory3);		
		map.put("position1", position1);		
		map.put("position2", position2);		
		map.put("position3", position3);		
		
		map.put("divisionHead", divisionHead);		
		
		map.put("divisionHeadPosition", divisionHeadPosition != null ? divisionHeadPosition : "");
		map.put("purposeOfOvertime", purposeOfOvertime != null ? purposeOfOvertime : "");
		map.put("activities1", activities1 != null ? activities1 : "");
		map.put("estQty1", estQty1 != null ? estQty1 : "");
		map.put("estMH1", estMH1 != null ? estMH1 : "");
		map.put("period1", period1 != null ? period1 : "");
		map.put("time1", time1 != null ? time1 : "");
		
		String empId[] = request.getParameterValues("empId")[0].split(",");		
		
		
				
		try {
			IEmployeeService empService = new EmployeeService();
			
			StringBuffer empNames = new StringBuffer();
			
			for(int i=0;i<empId.length;i++) {
				Employee emp = empService.getEmployee(Integer.parseInt(empId[i]));
				
				empNames.append(emp.getFirstname() + " " + emp.getLastname() + ", ");
				
			}
			
			map.put("empNames", empNames.toString());		
						
			List<PagibigRemittance> dataList = new ArrayList<PagibigRemittance>();		
			
			PagibigRemittance model = new PagibigRemittance();
			
			model.setFirstName("");
			
			dataList.add(model);
			
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
					sysModel.setProcessDesc("EXPORT PAG-IBIG REMITTANCE REPORT");
					sysModel.setProcessType("EXPORT");
				} else {
					sysModel.setProcessDesc("VIEW PDF PAG-IBIG REMITTANCE REPORT");
					sysModel.setProcessType("VIEW PDF");
				}
				
				sysModel.setModuleName("PAG-IBIG REMITTANCE REPORT");
				
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
