package dai.hris.action.filemanager.employeememo;

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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import dai.hris.model.EmployeeNotification;
import dai.hris.service.filemanager.empnotification.EmpNotificationService;
import dai.hris.service.filemanager.empnotification.IEmpNotificationService;

/**
 * Servlet implementation class GetEmployeeMemoAction
 */

//get all of the employee's received memos (employee is the recipient)
@WebServlet("/GetEmpNotificationByToRecipientAction")
public class GetEmpNotificationByToRecipientAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Gson gson = new Gson();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetEmpNotificationByToRecipientAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet GetEmpNotificationByToRecipientAction");
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost GetEmpNotificationByToRecipientAction");
		List<EmployeeNotification> list = new ArrayList<EmployeeNotification>();
		int toRecipientEmpId = Integer.parseInt(request.getParameter("toRecipientEmpId"));
		IEmpNotificationService service = new EmpNotificationService();
		
		
		int count = 0;
		String startIndex = request.getParameter("jtStartIndex");
		String pageSize = request.getParameter("jtPageSize");
//		String name = request.getParameter("name");
		String sorting = request.getParameter("jtSorting");
		
		
		try {
			
//			if(name != null && !name.isEmpty()){
	//			count=service.getCountWithFilter(toRecipientEmpId, name);
	//		} else {
	//			count=service.getCount(toRecipientEmpId);
	//		}
			
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
			
	//		if(name != null && !name.isEmpty()){
	//			list = service.getEmployeeMemoListByToRecipientEmpIdWithFilter(toRecipientEmpId, startPageIndex,numRecordsPerPage, sorting, name);				
	//		} else {
	//			list = service.getEmployeeMemoListByToRecipientEmpId(toRecipientEmpId, startPageIndex,numRecordsPerPage, sorting);
	//		}
			
			list = service.getEmployeeNotificationListByToRecipientEmpId(toRecipientEmpId, startPageIndex,numRecordsPerPage, sorting);
			
			
		} catch (SQLException sqle) {
			// TODO Auto-generated catch block
			sqle.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//	    String json = gson.toJson(list);
//	    System.out.println("json: " + json);
// 
//	    json = "{\"Result\":\"OK\",\"Records\":"+ json + "}";
//        response.getWriter().print(json);
		
		JsonElement element = gson.toJsonTree(list,new TypeToken<List<EmployeeNotification>>(){}.getType());
		JsonArray jsonArray = element.getAsJsonArray();
		String listData=jsonArray.toString();           //Return Json in the format required by jTable plugin
		listData = "{\"Result\":\"OK\",\"Records\":"+listData+",\"TotalRecordCount\":"+count+"}";   
		       response.getWriter().print(listData);
        
       
		
	}

}
