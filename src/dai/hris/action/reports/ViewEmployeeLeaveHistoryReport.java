package dai.hris.action.reports;


import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.InputStream;

import org.apache.log4j.Logger;

import dai.hris.dao.DBConstants;
import dai.hris.model.Employee;
import dai.hris.model.Leave;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.leave.ILeaveService;
import dai.hris.service.filemanager.leave.LeaveService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Servlet implementation class Counter
 */
@WebServlet("/ViewEmployeeLeaveHistoryReport")
public class ViewEmployeeLeaveHistoryReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ViewEmployeeLeaveHistoryReport.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewEmployeeLeaveHistoryReport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost( request,  response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		
		
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		
		
		ILeaveService service = new LeaveService();
		String startIndex = request.getParameter("jtStartIndex");
		String pageSize = request.getParameter("jtPageSize");
		String sorting = request.getParameter("jtSorting");		
		int count = 0;
		
		InputStream reportStream = null; 
		
		reportStream = getClass().getClassLoader().getResourceAsStream( "dai/hris/reports/EmpLeaveHistoryReport.jasper");
		Connection connection;
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(reportStream == null){
			logger.debug("reportStream is NULL");
		}
		
		
		map.put("HOSPITAL_NAME", ReportConstant.COMPANY_NAME);
		map.put("empName", employeeLoggedIn.getFirstname() + " " + employeeLoggedIn.getLastname());
		map.put("empPosition", employeeLoggedIn.getJobTitleName());
		map.put("empNo", employeeLoggedIn.getEmpNo());
		map.put("departmentName", employeeLoggedIn.getSectionName());

		
//		map.put("dateFrom", dateFrom + " 00:59:59 AM");
//		map.put("dateTo", dateTo + " 11:59:59 PM");
//		map.put("empId", employeeLoggedIn.getEmpId());
		

		response.setContentType("application/pdf");
		
		
		try {
			
			count=service.getCountByEmpIdDateRange(employeeLoggedIn.getEmpId(), dateFrom, dateTo);
			
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
			
			List<Leave> dataList = service.getAllLeavesByEmpIdDateRange(employeeLoggedIn.getEmpId(), startPageIndex,numRecordsPerPage, sorting, dateFrom, dateTo);
			
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			
			sysModel.setProcessDesc("VIEW PDF EMPLOYEE LEAVE REPORT");
			sysModel.setProcessType("VIEW PDF");			
			sysModel.setModuleName("EMPLOYEE LEAVE REPORT");			
			sysModel.setUserId(employeeLoggedIn.getEmpId());

		
			sysTrailService.insertSystemTrail(sysModel);
			
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			connection = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);
			
				
			JasperRunManager.runReportToPdfStream(reportStream,	response.getOutputStream(), map, beanColDataSource);
			connection.close();
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
