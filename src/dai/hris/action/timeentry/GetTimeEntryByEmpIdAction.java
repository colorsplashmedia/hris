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
import dai.hris.model.TimeEntry;
import dai.hris.service.timeentry.ITimeEntryService;
import dai.hris.service.timeentry.TimeEntryService;


/**
 * @author rj
 * 
 * THIS CLASS IS NEVER USED
 *
 */
@WebServlet("/GetTimeEntryByEmpIdAction")
public class GetTimeEntryByEmpIdAction extends HttpServlet {
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
		
//		int count = 0;
//		String startIndex = request.getParameter("jtStartIndex");
//		String pageSize = request.getParameter("jtPageSize");		
//		String sorting = request.getParameter("jtSorting");
		
		List<TimeEntry> list = new ArrayList<TimeEntry>();
		
		try {
			
//			count=service.getCount();
//			
//			
//			if(startIndex != null && startIndex.length() > 0) {
//				//do nothing
//			} else {
//				startIndex = "0";
//			}
//			
//			if(pageSize != null && pageSize.length() > 0) {
//				//do nothing
//			} else {
//				pageSize = "" + count;
//			}
//			
//			int startPageIndex = Integer.parseInt(startIndex);
//			int numRecordsPerPage = Integer.parseInt(pageSize);			
			
//			list = service.getAllTimeEntryByEmpIdForDashboard(startPageIndex,numRecordsPerPage, sorting, Integer.parseInt(empId));
			
			
			
			
	
			list = service.getAllTimeEntryByEmpId(Integer.parseInt(empId));		
			
//			String json = gson.toJson(timeEntryList);
//		    System.out.println("json: " + json);
//	 
//		    json = "{\"Result\":\"OK\",\"Records\":"+ json + "}";
//	        response.getWriter().print(json);
			
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String json = gson.toJson(list);
	    System.out.println("json: " + json);
 
	    json = "{\"Result\":\"OK\",\"Records\":"+ json + "}";
        response.getWriter().print(json);

		
	}

}
