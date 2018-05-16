/**
 * 
 */
package dai.hris.action.timeentry;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dai.hris.model.TimeEntryDispute;
import dai.hris.service.timeentry.ITimeEntryService;
import dai.hris.service.timeentry.TimeEntryService;


/**
 * @author rj
 * 
 * This class is never used
 *
 */
@WebServlet("/GetTimeEntryDisputeByEmpIdAction")
public class GetTimeEntryDisputeByEmpIdAction extends HttpServlet {
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
		
		
		String empId = request.getParameter("empId");
		
		ITimeEntryService service = new TimeEntryService();
		
		try {
	
			List<TimeEntryDispute> timeEntryList = service.getAllTimeEntryDisputeByEmpId(Integer.parseInt(empId));		
			
			String json = gson.toJson(timeEntryList);
		    System.out.println("json: " + json);
	 
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
