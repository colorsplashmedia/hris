package dai.hris.action.filemanager.employeeworkhistory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dai.hris.model.ServiceRecord;
import dai.hris.service.filemanager.employeeworkhistory.EmployeeWorkHistoryService;
import dai.hris.service.filemanager.employeeworkhistory.IEmployeeWorkHistoryService;

/**
 * Servlet implementation class GetEmployeeWorkHistoryService
 */
@WebServlet("/GetServiceRecordAction")
public class GetServiceRecordAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    IEmployeeWorkHistoryService service = new EmployeeWorkHistoryService();
    Gson gson = new Gson();

    public GetServiceRecordAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet GetServiceRecordAction");
		doPost(request, response);
	}

	//
	//In the server side, we must handle these query string arguments for paging:
	//	jtStartIndex: Start index of records for current page.
	//	jtPageSize: Count of maximum expected records.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet GetServiceRecordAction");
		
		List<ServiceRecord> list = new ArrayList<ServiceRecord>();
		int empId = Integer.parseInt(request.getParameter("empId"));
		try {
			list = service.getServiceRecordListByEmpId(empId);
		} catch (SQLException sqle) {
			// TODO Auto-generated catch block
			sqle.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String json = gson.toJson(list);
	    System.out.println("json: " + json);
 
	    json = "{\"Result\":\"OK\",\"Records\":"+ json + "}";
        response.getWriter().print(json);
        
        
		
	    
		
	}

}
