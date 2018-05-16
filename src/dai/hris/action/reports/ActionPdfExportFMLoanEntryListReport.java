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
import dai.hris.model.CaseRatePayment;
import dai.hris.model.City;
import dai.hris.model.Employee;
import dai.hris.model.EmployeeMemo;
import dai.hris.model.LoanEntry;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.city.CityService;
import dai.hris.service.filemanager.city.ICityService;
import dai.hris.service.filemanager.employee.EmployeeService;
import dai.hris.service.filemanager.employee.IEmployeeService;
import dai.hris.service.filemanager.employeememo.EmployeeMemoService;
import dai.hris.service.filemanager.employeememo.IEmployeeMemoService;
import dai.hris.service.loan.ILoanEntryService;
import dai.hris.service.loan.LoanEntryService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.payroll.ICaseRatePaymentService;
import dai.hris.service.payroll.impl.CaseRatePaymentService;

/**
 * Servlet implementation class ActionPdfExportFMJobTitleListReport
 */
@WebServlet("/ActionPdfExportFMLoanEntryListReport")
public class ActionPdfExportFMLoanEntryListReport extends HttpServlet {
	private static final long serialVersionUID = 5676921778006010751L;
	private static Logger logger = Logger.getLogger(ActionPdfExportFMLoanEntryListReport.class);
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
		
		InputStream reportStream = getClass().getClassLoader().getResourceAsStream( "dai/hris/reports/LoanEntryReport.jasper");
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
			//map.put("departmentId", Integer.parseInt(departmentId));
			
			ILoanEntryService service = new LoanEntryService();
			String startIndex = request.getParameter("jtStartIndex");
			String pageSize = request.getParameter("jtPageSize");
			String sorting = request.getParameter("jtSorting");
//			String name = request.getParameter("name");
			String forExport = request.getParameter("forExport");
			int count = 0;		
			
//			if(name != null && !name.isEmpty()){
//				count=service.getCountWithFilter(toRecipientEmpId, name);
//			} else {
//				count=service.getCount(toRecipientEmpId);
//			}
			
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
			
			List<LoanEntry> dataList = new ArrayList<LoanEntry>();	
			
//			if(name != null && !name.isEmpty()){
//				dataList = service.getEmployeeMemoListByToRecipientEmpIdWithFilter(toRecipientEmpId, startPageIndex,numRecordsPerPage, sorting, name);				
//			} else {
//				dataList = service.getEmployeeMemoListByToRecipientEmpId(toRecipientEmpId, startPageIndex,numRecordsPerPage, sorting);
//			}
			
			dataList = service.getLoanEntryListByEmpId(empId, startPageIndex,numRecordsPerPage, sorting);
			
			if(dataList.isEmpty()){
				
				LoanEntry model = new LoanEntry();
				
				model.setDateFile("");
				model.setDeductionFlagActive("");
				model.setEmpId(0);
				model.setEndDateToPay("");
				model.setEndDateToPay("");
				model.setLoanAmount(BigDecimal.ZERO);
				model.setLoanEntryId(0);
				model.setLoanTypeId(0);
				model.setMonthlyAmortization(BigDecimal.ZERO);
				model.setNoOfMonthToPay(0);
				model.setPeriodFrom("");
				model.setPeriodTo("");
				model.setPNDate("");
				model.setPNNo("");
				model.setReferenceNo("");
				model.setRemarks("");
				model.setStartDateToPay("");
				
				dataList.add(model);
				
			}
			
			
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			Connection connection = null;
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			connection = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL); 	
			
			if(forExport.equals("Y")) {
				String printFileName = null;		
				URL sourceFileName = getClass().getResource("/dai/hris/reports/LoanEntryReport.jasper");
				
				printFileName = JasperFillManager.fillReportToFile(sourceFileName.toURI().getPath(),
			            map, beanColDataSource);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	            Calendar calendar = Calendar.getInstance();
				String reportName = "LoanEntryReport" + "_" + employeeLoggedIn.getSectionId() 
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
					sysModel.setProcessDesc("EXPORT MEMO REPORT");
					sysModel.setProcessType("EXPORT");
				} else {
					sysModel.setProcessDesc("VIEW PDF MEMO REPORT");
					sysModel.setProcessType("VIEW PDF");
				}
				
				sysModel.setModuleName("MEMO REPORT");
				
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
