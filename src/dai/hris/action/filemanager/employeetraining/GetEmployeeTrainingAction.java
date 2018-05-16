package dai.hris.action.filemanager.employeetraining;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dai.hris.model.Employee;
import dai.hris.model.EmployeeOutOfOffice;
import dai.hris.service.filemanager.employeeoutofoffice.EmployeeOutOfOfficeService;
import dai.hris.service.filemanager.employeeoutofoffice.IEmployeeOutOfOfficeService;


@WebServlet("/GetEmployeeTrainingAction")
public class GetEmployeeTrainingAction extends HttpServlet {

	Gson gson = new Gson();

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doGet GetEmployeeTrainingAction");
		doPost(request, response);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		System.out.println("doPost GetEmployeeTrainingAction");
		IEmployeeOutOfOfficeService service = new EmployeeOutOfOfficeService();
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		List<EmployeeOutOfOffice> list = null;
		int empId = employeeLoggedIn.getEmpId();
		
		int count = 0;
		String startIndex = request.getParameter("jtStartIndex");
		String pageSize = request.getParameter("jtPageSize");		
		String sorting = request.getParameter("jtSorting");
		
		try {
			
			count=service.getCountByEmpId(empId);
			
			
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
		
			list = service.getEmployeeOutOfOfficeListByEmpId(empId, startPageIndex,numRecordsPerPage, sorting);
			
			//employeeOutOfOfficeList = trainingOOO.getEmployeeOutOfOfficeListByEmpId(empId);
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
