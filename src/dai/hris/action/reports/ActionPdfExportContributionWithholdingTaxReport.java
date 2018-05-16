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
import dai.hris.model.CaseRatePayment;
import dai.hris.model.Employee;
import dai.hris.model.GSISReport;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.reports.IReportService;
import dai.hris.service.reports.ReportService;

/**
 * Servlet implementation class ActionPdfExportFMJobTitleListReport
 */
@WebServlet("/ActionPdfExportContributionWithholdingTaxReport")
public class ActionPdfExportContributionWithholdingTaxReport extends HttpServlet {
	private static final long serialVersionUID = 5676921778006010751L;
	private static Logger logger = Logger.getLogger(ActionPdfExportContributionWithholdingTaxReport.class);
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

		
		
		String forIndividual = request.getParameter("forIndividual");
		String forExport = request.getParameter("forExport");
		
		InputStream reportStream = getClass().getClassLoader().getResourceAsStream( "dai/hris/reports/WithholdingTaxReport.jasper");
		if(reportStream == null){
			logger.debug("reportStream is NULL");
		}
								
		try {
					
			Map<String, Object> map = new HashMap<String, Object>();
			
			String hospitalLogo = request.getServletContext().getRealPath(File.separator) + ReportConstant.HOSPITAL_LOGO_URL; 
			String dohLogo = request.getServletContext().getRealPath(File.separator) + ReportConstant.DOH_LOGO_URL; 
			map.put("DOH_LOGO", dohLogo);	
			map.put("HOSPITAL_LOGO", hospitalLogo);	
			map.put("HOSPITAL_NAME", ReportConstant.COMPANY_NAME);
			map.put("HOSPITAL_ADD", ReportConstant.HOSPITAL_ADD);			
			map.put("COUNTRY_NAME", ReportConstant.COUNTRY_NAME);	
			map.put("DOH_LABEL", ReportConstant.DOH_LABEL);	
			map.put("REPORT_NAME", "WITHHOLDING TAX REPORT");

			
			IReportService service = new ReportService();			
			
			List<GSISReport> dataList = new ArrayList<GSISReport>();	
			
			if(forIndividual.equals("Y")) {
				String dateFrom = request.getParameter("dateFrom");
				String dateTo = request.getParameter("dateTo");
				dataList = service.getWithHoldingTaxReportByDateRangeAndId(employeeLoggedIn.getEmpId(), dateFrom, dateTo);
			} else {
				String payrollPeriodIdStr = request.getParameter("payrollPeriodId");
				dataList = service.getWithHoldingTaxReport(Integer.parseInt(payrollPeriodIdStr));		
			}
			
			int numberOfExtra = 0;
			int maxPerPage = 44;
			if(dataList.size() < maxPerPage) {
				numberOfExtra = maxPerPage - dataList.size();
			} else {
				int remainder = dataList.size() % maxPerPage;				
				numberOfExtra = maxPerPage - remainder;
			}
			
			for(int x = 0; x < numberOfExtra; x++){
				GSISReport model = new GSISReport();
				model.setEmpName("");
				
				dataList.add(model);
			}
			
				
			
			
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			Connection connection = null;
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			connection = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL); 	
			
			if(forExport.equals("Y")) {
				String printFileName = null;		
				URL sourceFileName = getClass().getResource("/dai/hris/reports/WithholdingTaxReport.jasper");
				
				printFileName = JasperFillManager.fillReportToFile(sourceFileName.toURI().getPath(),
			            map, beanColDataSource);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	            Calendar calendar = Calendar.getInstance();
				String reportName = "withholdingTaxReportListReport" + "_" + employeeLoggedIn.getSectionId() 
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
					sysModel.setProcessDesc("EXPORT WITHHOLDING TAX REPORT");
					sysModel.setProcessType("EXPORT");
				} else {
					sysModel.setProcessDesc("VIEW PDF WITHHOLDING TAX REPORT");
					sysModel.setProcessType("VIEW PDF");
				}
				
				sysModel.setModuleName("WITHHOLDING TAX REPORT");
				
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
