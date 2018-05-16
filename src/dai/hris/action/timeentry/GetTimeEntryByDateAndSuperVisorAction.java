/**
 * 
 */
package dai.hris.action.timeentry;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dai.hris.model.Employee;
import dai.hris.model.TimeEntry;
import dai.hris.model.TimeEntryDispute;
import dai.hris.service.timeentry.ITimeEntryService;
import dai.hris.service.timeentry.TimeEntryService;


/**
 * @author rj
 * 
 * This CLASS is NEVER USED
 *
 */
@WebServlet("/GetTimeEntryByDateAndSuperVisorAction")
public class GetTimeEntryByDateAndSuperVisorAction extends HttpServlet {
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
		String approverType = request.getSession().getAttribute("approverType").toString();
		
		String clockDate = request.getParameter("clockDate");
		
		ITimeEntryService service = new TimeEntryService();
		
		int id = 0;
		
		if("SECTIONADMIN".equals(approverType)) {
			id = employeeLoggedIn.getSectionId();
		} else if("UNITADMIN".equals(approverType)) {
			id = employeeLoggedIn.getUnitId();
		} else if("SUBUNITADMIN".equals(approverType)) {
			id = employeeLoggedIn.getSubUnitId();
		}
		
		try {
	
			List<TimeEntry> timeEntryList = service.getTimeEntryByDateAndSuperVisorAction(approverType, id, employeeLoggedIn.getEmpId(), clockDate);		
			List<TimeEntryDispute> timeEntryDisputeList = service.getAllTimeEntryDisputeBySupervisorAndClockDate(employeeLoggedIn.getEmpId(), clockDate);		
			
			if (timeEntryList != null) {
		        request.setAttribute("timeEntryListProblematic", timeEntryList);
		        request.setAttribute("timeEntryDisputeList", timeEntryDisputeList);
		        		        
		        
		        RequestDispatcher dispatcher = null;
		        dispatcher = getServletContext().getRequestDispatcher("/clockEntry.jsp");
		        

		        if (dispatcher != null) {
		        	response.setContentType("text/html");
		            dispatcher.include(request, response);
		        }
		    }
			
			
//			String json = gson.toJson(list);
//		    System.out.println("json: " + json);
//	 
//		    
//	        response.getWriter().print(json);
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		
	}

}
