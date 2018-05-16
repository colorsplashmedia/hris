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
import dai.hris.model.NightDifferential;
import dai.hris.model.SystemTrail;
import dai.hris.model.YearEndBonusCashGift;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.payroll.IYearEndBonusCashGiftService;
import dai.hris.service.payroll.impl.YearEndBonusCashGiftService;

/**
 * Servlet implementation class ActionPdfExportFMJobTitleListReport
 */
@WebServlet("/ActionPdfExportGeneralYearEndBonusReport")
public class ActionPdfExportGeneralYearEndBonusReport extends HttpServlet {
	private static final long serialVersionUID = 5676921778006010751L;
	private static Logger logger = Logger.getLogger(ActionPdfExportGeneralYearEndBonusReport.class);
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

		String reportStreamPath = "dai/hris/reports/YearEndBonusReport.jasper";
		String exportName = "yearEndBonusListReport";
		
//		int empId = Integer.parseInt(request.getParameter("empId"));
		String year = request.getParameter("year");
		
		InputStream reportStream = getClass().getClassLoader().getResourceAsStream(reportStreamPath);
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
			
			map.put("REPORT_NAME", "YEAR END BONUS AND CASH GIFT REPORT");		
			
			
			//map.put("departmentId", Integer.parseInt(departmentId));
			
			IYearEndBonusCashGiftService service = new YearEndBonusCashGiftService();
			String forExport = request.getParameter("forExport");
			List<YearEndBonusCashGift> dataList = new ArrayList<YearEndBonusCashGift>();	
			
	
			dataList = service.getYearEndBonusCashGiftListByDateRange(year);
			
			int numberOfExtra = 0;
			int maxPerPage = 21;
			if(dataList.size() < maxPerPage) {
				numberOfExtra = maxPerPage - dataList.size();
			} else {
				int remainder = dataList.size() % maxPerPage;				
				numberOfExtra = maxPerPage - remainder;
			}
			
			for(int x = 0; x < numberOfExtra; x++){
				YearEndBonusCashGift model = new YearEndBonusCashGift();
				model.setEmpName("");
				
				dataList.add(model);
			}
			
//			if(dataList.isEmpty()){
//				
//				YearEndBonusCashGift model = new YearEndBonusCashGift();
//		    	
//				model.setEmpNo("");
//				model.setEmpName("");
//		    	model.setYearEndBonusId(0);
//		    	model.setSalaryGrade(0);
//		    	model.setSTEP(BigDecimal.ZERO);
//		    	model.setBasicSalary(BigDecimal.ZERO);
//		    	model.setCashGift(BigDecimal.ZERO);
//		    	model.setTotal(BigDecimal.ZERO); 
//		    	model.setBasicSalaryOct31(BigDecimal.ZERO);
//		    	model.setCashGift1(BigDecimal.ZERO);
//		    	model.setFirstHalf13thMonth(BigDecimal.ZERO);
//		    	model.setFirstHalfCashGift(BigDecimal.ZERO);
//		    	model.setSecondHalf13thMonth(BigDecimal.ZERO);      
//	    		model.setSecondHalfCashGift(BigDecimal.ZERO);       
//	    		model.setTotalYearEndBonusCashGift(BigDecimal.ZERO);
//	    		model.setEamcCoopDeduction(BigDecimal.ZERO);
//	    		model.setNetAmountDue(BigDecimal.ZERO);
//	    		model.setYear(0);
//	    		model.setEmpId(0);
//				
//				dataList.add(model);
//				
//			}
			
			
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			Connection connection = null;
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			connection = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL); 	
			
			if(forExport.equals("Y")) {
				String printFileName = null;		
				URL sourceFileName = getClass().getResource(reportStreamPath);
				
				printFileName = JasperFillManager.fillReportToFile(sourceFileName.toURI().getPath(),
			            map, beanColDataSource);
				
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
					sysModel.setProcessDesc("EXPORT YEAR END BONUS REPORT");
					sysModel.setProcessType("EXPORT");
				} else {
					sysModel.setProcessDesc("VIEW PDF YEAR END BONUS REPORT");
					sysModel.setProcessType("VIEW PDF");
				}
				
				sysModel.setModuleName("YEAR END BONUS REPORT");
				
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
