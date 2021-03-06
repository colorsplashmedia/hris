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
import dai.hris.model.HazardPay;
import dai.hris.model.LongevityPay;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.employee.EmployeeService;
import dai.hris.service.filemanager.employee.IEmployeeService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.payroll.IHazardPayService;
import dai.hris.service.payroll.ILongevityPayService;
import dai.hris.service.payroll.impl.HazardPayService;
import dai.hris.service.payroll.impl.LongevityPayService;

/**
 * Servlet implementation class ActionPdfExportFMJobTitleListReport
 */
@WebServlet("/ActionPdfExportLongevityPayListReport")
public class ActionPdfExportLongevityPayListReport extends HttpServlet {
	private static final long serialVersionUID = 5676921778006010751L;
	private static Logger logger = Logger.getLogger(ActionPdfExportLongevityPayListReport.class);
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

		
		
		int empId = Integer.parseInt(request.getParameter("empId"));
		
		InputStream reportStream = getClass().getClassLoader().getResourceAsStream( "dai/hris/reports/LongevityPayReport.jasper");
		if(reportStream == null){
			logger.debug("reportStream is NULL");
		}
		
		IEmployeeService empService = new EmployeeService();
		Employee emp1 = new Employee();
		
		
			
				
		try {
			
			emp1 = empService.getEmployee(empId);
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("HOSPITAL_NAME", ReportConstant.COMPANY_NAME);
			
			map.put("empName", emp1.getFirstname() + " " + emp1.getLastname());
			map.put("empPosition", emp1.getJobTitleName());
			map.put("empNo", emp1.getEmpNo());
			map.put("departmentName", emp1.getSectionName());
			
			ILongevityPayService service = new LongevityPayService();
			String startIndex = request.getParameter("jtStartIndex");
			String pageSize = request.getParameter("jtPageSize");
			String sorting = request.getParameter("jtSorting");
			String forExport = request.getParameter("forExport");
			int count = 0;		
			
			
			count=service.getCount(empId);
			
			if(startIndex != null && startIndex.length() > 0) {
				//do nothing
			} else {
				startIndex = "0";
			}
			
			if(pageSize != null && pageSize.length() > 0) {
				//do nothing
			} else {
				pageSize = "" + count;
			}
			
			int startPageIndex = Integer.parseInt(startIndex);
			int numRecordsPerPage = Integer.parseInt(pageSize);
			
			List<LongevityPay> dataList = new ArrayList<LongevityPay>();	
						
			dataList = service.getLongevityPayListByEmpId(empId, startPageIndex,numRecordsPerPage, sorting);
			
			if(dataList.isEmpty()){
				
				LongevityPay model = new LongevityPay();
				
				model.setEmpNo("");
				model.setEmpName("");
				model.setLongevityPayId(0);
		    	model.setSalaryGrade(0);
		    	model.setBasicSalary(BigDecimal.ZERO);
		    	model.setNetAmountDue(BigDecimal.ZERO);
		    	model.setYear(0);
		    	model.setMonth(0);
		    	model.setRemarks("");
		    	model.setEmpId(0);
				
				dataList.add(model);
				
			}
			
			
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			Connection connection = null;
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			connection = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL); 	
			
			if(forExport.equals("Y")) {
				String printFileName = null;		
				URL sourceFileName = getClass().getResource("/dai/hris/reports/LongevityPayReport.jasper");
				
				printFileName = JasperFillManager.fillReportToFile(sourceFileName.toURI().getPath(),
			            map, beanColDataSource);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	            Calendar calendar = Calendar.getInstance();
				String reportName = "longevityPayListReport" + "_" + employeeLoggedIn.getSectionId() 
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
					sysModel.setProcessDesc("EXPORT LONGEVITY PAY REPORT");
					sysModel.setProcessType("EXPORT");
				} else {
					sysModel.setProcessDesc("VIEW PDF LONGEVITY PAY REPORT");
					sysModel.setProcessType("VIEW PDF");
				}
				
				sysModel.setModuleName("LONGEVITY PAY REPORT");
				
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
