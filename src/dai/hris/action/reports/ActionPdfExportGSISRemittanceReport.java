package dai.hris.action.reports;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
import dai.hris.model.AttendanceReport;
import dai.hris.model.Employee;
import dai.hris.model.GSISRemittance;
import dai.hris.model.LoanType;
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
 * Servlet implementation class ActionPdfExportGSISRemittanceReport
 */
@WebServlet("/ActionPdfExportGSISRemittanceReport")
public class ActionPdfExportGSISRemittanceReport extends HttpServlet {
	private static final long serialVersionUID = 5676921778006010751L;
	private static Logger logger = Logger.getLogger(ActionPdfExportGSISRemittanceReport.class);
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
		
		String reportStreamPath = "dai/hris/reports/GSISRemittanceReport.jasper";
		String exportName = "gsisRemittanceReport";
		
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");	
		
		String forExport = request.getParameter("forExport");
		int month = Integer.parseInt(request.getParameter("month"));
		int year = Integer.parseInt(request.getParameter("year"));
		String signatory1 = request.getParameter("signatory1");		
		String position1 = request.getParameter("position1");
		String officeCode = request.getParameter("officeCode");

				
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
		
		map.put("HOSPITAL_ADD", ReportConstant.HOSPITAL_ADD);			
		map.put("COUNTRY_NAME", ReportConstant.COUNTRY_NAME);	
		map.put("DOH_LABEL", ReportConstant.DOH_LABEL);		
		
		map.put("signatory1", signatory1 != null ? signatory1 : "");
		map.put("position1", position1 != null ? position1 : "");
		map.put("officeCode", officeCode != null ? officeCode : "");
		
		
		IReportService service = new ReportService();	
				
		try {
			map.put("REPORT_NAME", "MONTHLY GSIS REMITTANCE LIST");	
			map.put("PERIOD", month + "/" + year);	
			
			List<GSISRemittance> dataList = new ArrayList<GSISRemittance>();	
			
			int maxLoans = 15;
			
			List<LoanType> loanTypelist = service.getTopGSISLoans(maxLoans);
			
			int ctr = 1;
			for(LoanType loanType : loanTypelist){				
				map.put("loanName" + ctr, loanType.getLoanCode());				
				ctr++;
			}
			
			dataList = service.getGSISRemittanceReport(month, year, loanTypelist);	
			
			if(dataList.isEmpty()){
	
				
				GSISRemittance model = new GSISRemittance();
				
				model.setFirstName("");
		    	model.setLastName("");
		    	model.setMiddleName("");
		    	model.setEmpId("");
		    	model.setBpNo("");	    	
		    	model.setCrn("");
		    	model.setBirthDate("");
		    	model.setEmployeeShare(BigDecimal.ZERO);	    	
		    	model.setEmployerShare(BigDecimal.ZERO);	 
	    		
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
