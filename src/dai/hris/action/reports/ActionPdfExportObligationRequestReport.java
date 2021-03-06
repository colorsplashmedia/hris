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
import dai.hris.model.LeaveCard;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;

/**
 * Servlet implementation class ActionPdfExportObligationRequestReport
 */
@WebServlet("/ActionPdfExportObligationRequestReport")
public class ActionPdfExportObligationRequestReport extends HttpServlet {
	private static final long serialVersionUID = 5676921778006010751L;
	private static Logger logger = Logger.getLogger(ActionPdfExportObligationRequestReport.class);
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
		
		String reportStreamPath = "dai/hris/reports/ObligationRequestReport.jasper";
		String exportName = "obligationRequestReport";
		String REPORT_NAME = "OBLIGATION REQUEST AND STATUS";
		
		String forExport = request.getParameter("forExport");		
				
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
		
		map.put("REPORT_NAME", REPORT_NAME);
		
		map.put("obligationDate", request.getParameter("obligationDate"));
		map.put("fundCluster", request.getParameter("fundCluster"));
		map.put("dvNo", request.getParameter("dvNo"));
		map.put("payee", request.getParameter("payee"));
		map.put("address", request.getParameter("address"));
		map.put("particulars", request.getParameter("particulars"));
		map.put("responsibilityCenter", request.getParameter("responsibilityCenter"));
		map.put("mfo", request.getParameter("mfo"));
		map.put("amount", request.getParameter("amount") != null ? new BigDecimal(request.getParameter("amount")) : BigDecimal.ZERO);
		map.put("totalAmount", request.getParameter("amount") != null ? new BigDecimal(request.getParameter("amount")) : BigDecimal.ZERO);
		map.put("uacsCode", request.getParameter("uacsCode"));
		map.put("amountInWords", request.getParameter("amountInWords"));
		map.put("signatory1", request.getParameter("signatory1"));
		map.put("signatory2", request.getParameter("signatory2"));
		map.put("position1", request.getParameter("position1"));
		map.put("position2", request.getParameter("position2"));
		map.put("obligationAmount", request.getParameter("obligationAmount") != null ? new BigDecimal(request.getParameter("obligationAmount")) : BigDecimal.ZERO);
		map.put("paymentAmount", request.getParameter("paymentAmount") != null ? new BigDecimal(request.getParameter("paymentAmount")) : BigDecimal.ZERO);
		map.put("notDueAmount", request.getParameter("notDueAmount") != null ? new BigDecimal(request.getParameter("notDueAmount")) : BigDecimal.ZERO);
		map.put("demandAmount", request.getParameter("demandAmount") != null ? new BigDecimal(request.getParameter("demandAmount")) : BigDecimal.ZERO);
		
		map.put("jevNo", request.getParameter("jevNo"));
		map.put("refDate", request.getParameter("refDate"));
		map.put("refParticular", request.getParameter("refParticular"));
		
		
		
		try {
			
			List<LeaveCard> dataList = new ArrayList<LeaveCard>();	
			LeaveCard leaveCard = new LeaveCard();
			leaveCard.setPeriod("");
			
			dataList.add(leaveCard);
			
			
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
