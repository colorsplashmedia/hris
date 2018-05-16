/**
 * 
 */
package dai.hris.action.timeentry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dai.hris.model.Employee;
import dai.hris.model.TimeEntryDispute;
import dai.hris.service.timeentry.ITimeEntryService;
import dai.hris.service.timeentry.TimeEntryService;

/**
 * @author Ian
 *
 */
@WebServlet("/GetTimeEntryDisputeStaffByEmpIdAction")
public class GetTimeEntryDisputeStaffByEmpIdAction extends HttpServlet {
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
				
		String approverType = request.getSession().getAttribute("approverType").toString();
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		
		
		//String supervisorId = request.getParameter("empId");
		
		ITimeEntryService service = new TimeEntryService();
		
		try {
			List<TimeEntryDispute> timeEntryList = new ArrayList<TimeEntryDispute>();
			
			if("ADMIN".equals(approverType) || "HRADMIN".equals(approverType)){
				//get allTimeEntryDispute
				timeEntryList = service.getAllTimeEntryDispute();
			} else if("SECTIONADMIN".equals(approverType)) {
				//get all time dispute base on sectionId
				timeEntryList = service.getAllTimeEntryDisputeBySectionId(employeeLoggedIn.getSectionId());
			} else if("UNITADMIN".equals(approverType)) {
				//get all time dispute base on unitId
				timeEntryList = service.getAllTimeEntryDisputeByUnitId(employeeLoggedIn.getUnitId());
			} else if("SUBUNITADMIN".equals(approverType)) {
				//get all time dispute base on subUnitId			
				timeEntryList = service.getAllTimeEntryDisputeBySubUnitId(employeeLoggedIn.getSubUnitId());
			}
	
//			List<TimeEntryDispute> timeEntryList = service.getAllTimeEntryDisputeBySupervisorId(Integer.parseInt(supervisorId));		
			
			String json = gson.toJson(timeEntryList);
		    System.out.println("GetTimeEntryDisputeStaffByEmpIdAction json: " + json);
	 
		    json = "{\"Result\":\"OK\",\"Records\":"+ json + "}";
	        response.getWriter().print(json);
			
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		
	}

}
