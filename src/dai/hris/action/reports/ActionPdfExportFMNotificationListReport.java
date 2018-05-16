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
import dai.hris.model.City;
import dai.hris.model.Employee;
import dai.hris.model.EmployeeMemo;
import dai.hris.model.EmployeeNotification;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.city.CityService;
import dai.hris.service.filemanager.city.ICityService;
import dai.hris.service.filemanager.employee.EmployeeService;
import dai.hris.service.filemanager.employee.IEmployeeService;
import dai.hris.service.filemanager.employeememo.EmployeeMemoService;
import dai.hris.service.filemanager.employeememo.IEmployeeMemoService;
import dai.hris.service.filemanager.empnotification.EmpNotificationService;
import dai.hris.service.filemanager.empnotification.IEmpNotificationService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;

/**
 * Servlet implementation class ActionPdfExportFMJobTitleListReport
 */
@WebServlet("/ActionPdfExportFMNotificationListReport")
public class ActionPdfExportFMNotificationListReport extends HttpServlet {
	private static final long serialVersionUID = 5676921778006010751L;
	private static Logger logger = Logger.getLogger(ActionPdfExportFMNotificationListReport.class);
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

		
		
		int toRecipientEmpId = Integer.parseInt(request.getParameter("toRecipientEmpId"));
		
		InputStream reportStream = getClass().getClassLoader().getResourceAsStream( "dai/hris/reports/NotificationReport.jasper");
		if(reportStream == null){
			logger.debug("reportStream is NULL");
		}
		
		IEmployeeService empService = new EmployeeService();
		Employee emp1 = new Employee();
		
		
			
				
		try {
			
			emp1 = empService.getEmployee(toRecipientEmpId);
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("HOSPITAL_NAME", ReportConstant.COMPANY_NAME);
			
			map.put("empName", emp1.getFirstname() + " " + emp1.getLastname());
			map.put("empPosition", emp1.getJobTitleName());
			map.put("empNo", emp1.getEmpNo());
			map.put("departmentName", emp1.getSectionName());
			//map.put("departmentId", Integer.parseInt(departmentId));
			
			IEmpNotificationService service = new EmpNotificationService();
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
			
			count=service.getCount(toRecipientEmpId);
			
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
			
			List<EmployeeNotification> dataList = new ArrayList<EmployeeNotification>();	
			
//			if(name != null && !name.isEmpty()){
//				dataList = service.getEmployeeMemoListByToRecipientEmpIdWithFilter(toRecipientEmpId, startPageIndex,numRecordsPerPage, sorting, name);				
//			} else {
//				dataList = service.getEmployeeMemoListByToRecipientEmpId(toRecipientEmpId, startPageIndex,numRecordsPerPage, sorting);
//			}
			
			dataList = service.getEmployeeNotificationListByToRecipientEmpId(toRecipientEmpId, startPageIndex,numRecordsPerPage, sorting);
			
			if(dataList.isEmpty()){
				
				EmployeeNotification model = new EmployeeNotification();
				
				model.setFiledDate("");
				model.setCcRecipient("");
				model.setFromSender("");
				model.setSubject("");
				model.setMessage("");
				model.setRemarks("");
				
				dataList.add(model);
				
			}
			
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			Connection connection = null;
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			connection = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL); 	
			
			if(forExport.equals("Y")) {
				String printFileName = null;		
				URL sourceFileName = getClass().getResource("/dai/hris/reports/NotificationReport.jasper");
				
				printFileName = JasperFillManager.fillReportToFile(sourceFileName.toURI().getPath(),
			            map, beanColDataSource);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	            Calendar calendar = Calendar.getInstance();
				String reportName = "empNotificationListReport" + "_" + employeeLoggedIn.getSectionId() 
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
					sysModel.setProcessDesc("EXPORT NOTIFICATION REPORT");
					sysModel.setProcessType("EXPORT");
				} else {
					sysModel.setProcessDesc("VIEW PDF NOTIFICATION REPORT");
					sysModel.setProcessType("VIEW PDF");
				}
				
				sysModel.setModuleName("NOTIFICATION REPORT");
				
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
