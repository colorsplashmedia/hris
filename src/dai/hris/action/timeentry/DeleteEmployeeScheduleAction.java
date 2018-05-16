/**
 * 
 */
package dai.hris.action.timeentry;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dai.hris.model.Employee;
import dai.hris.model.ShiftingSchedule;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
//import dai.hris.model.Employee;
import dai.hris.service.timeentry.ITimeEntryService;
import dai.hris.service.timeentry.TimeEntryService;


/**
 * @author rj
 *
 */
@WebServlet("/DeleteEmployeeScheduleAction")
public class DeleteEmployeeScheduleAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		String superVisorId = "";
//		if (null != request.getSession().getAttribute("employeeLoggedIn")) {
//			superVisorId = String.valueOf(((Employee)request.getSession().getAttribute("employeeLoggedIn")).getEmpId());
//		} else {
//			superVisorId = request.getParameter("superVisorId");
//		}
		
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
//		
		int empScheduleId = Integer.parseInt(request.getParameter("empScheduleId"));
		
		ITimeEntryService service = new TimeEntryService();
		
		try {
			
			ShiftingSchedule model = service.getEmployeeScheduleById(empScheduleId);
			
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("EMPLOYEE SCHEDULE");
			sysModel.setProcessDesc("Deleted By: " + employeeLoggedIn.getFirstname() + " " 
					+ employeeLoggedIn.getLastname() 
					+ " | Schedule: " + model.getDescription() 
					+ " | Schedule Date: " + model.getScheduleDate());
			sysModel.setProcessType("DELETE");
			sysModel.setUserId(employeeLoggedIn.getEmpId());

			
			sysTrailService.insertSystemTrail(sysModel);
	
			service.deleteEmployeeSchedule(empScheduleId);

			RequestDispatcher dispatcher = null;
	        dispatcher = getServletContext().getRequestDispatcher("/employeeScheduleDetails.jsp");
	        

	        if (dispatcher != null) {
	        	response.setContentType("text/html");
	            dispatcher.include(request, response);
	        }
		    
	        
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		
	}

}
