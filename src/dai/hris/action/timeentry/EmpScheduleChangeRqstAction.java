package dai.hris.action.timeentry;


import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dai.hris.model.Employee;
import dai.hris.model.Resolution;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.employee.EmployeeService;
import dai.hris.service.filemanager.employee.IEmployeeService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.timeentry.ITimeEntryService;
import dai.hris.service.timeentry.TimeEntryService;




/**
 * Servlet implementation class for Servlet: LoginAction
 *
 */
@WebServlet("/EmpScheduleChangeRqstAction")
 public class EmpScheduleChangeRqstAction extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	 private static final long serialVersionUID = -6185891323760506163L;	 
	 /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public EmpScheduleChangeRqstAction() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doPost(request, response);
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		String clockDate = request.getParameter("clockDate");		
		String empId = request.getParameter("empId");	
		String shiftScheduleId = request.getParameter("shiftScheduleId");
		String resolutionRemarks = request.getParameter("resolutionRemarks");	
		String resolvedBy = request.getParameter("resolvedBy");			
			
        ITimeEntryService timeEntryService = new TimeEntryService();
        
        Resolution resolution = new Resolution();
                
        resolution.setClockDate(clockDate);
        
        if(!empId.isEmpty() && empId.length() > 0){
        	resolution.setEmpId(Integer.parseInt(empId));
        }
        
       
        resolution.setResolutionRemarks(resolutionRemarks);
       
        
        if(!resolvedBy.isEmpty() && resolvedBy.length() > 0){
        	resolution.setResolvedBy(Integer.parseInt(resolvedBy));
        }
        
        if(!shiftScheduleId.isEmpty() && shiftScheduleId.length() > 0){
        	resolution.setShiftScheduleId(Integer.parseInt(shiftScheduleId));
        }       
        
        
		try {
			Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
			
			IEmployeeService empService = new EmployeeService();
			
			Employee emp = empService.getEmployee(empId);
			
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("CHANGE SCHEDULE REQUEST");
			sysModel.setProcessDesc("Employee Name: " + employeeLoggedIn.getFirstname() + " " + employeeLoggedIn.getLastname() 
					+ " | ShiftSchedule Id: " + shiftScheduleId
					+ " | Resolution Remarks: " + resolutionRemarks
					+ " | Resolved By: " + resolvedBy
					);
			sysModel.setProcessType("APPROVAL");
			sysModel.setUserId(employeeLoggedIn.getEmpId());

			
			sysTrailService.insertSystemTrail(sysModel);
			
			timeEntryService.disputeEmpSchedule(resolution);

        	
			RequestDispatcher dispatcher = null;
			dispatcher = getServletContext().getRequestDispatcher("/employeeDashBoard.jsp");			    

			if (dispatcher != null) {
	        	response.setContentType("text/html");
	            dispatcher.include(request, response);
	        }
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}   	 
	
	
}