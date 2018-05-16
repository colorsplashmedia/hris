package dai.hris.action.filemanager.leave.sv.approval;

import java.io.IOException;
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

import dai.hris.model.Employee;
import dai.hris.model.Leave;
import dai.hris.service.filemanager.leave.ILeaveService;
import dai.hris.service.filemanager.leave.LeaveService;

@WebServlet("/GetAllLeavesHRApprovalAction")
public class GetAllLeavesHRApprovalAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		ILeaveService service = new LeaveService();
		List<Leave> list = null;
		int count = 0;
		String startIndex = request.getParameter("jtStartIndex");
		String pageSize = request.getParameter("jtPageSize");		
		String sorting = request.getParameter("jtSorting");
		
		try {
			count=service.getCountForHRApproval();
			
			
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
			
			list = service.getAllLeavesForHRApproval(employeeLoggedIn.getEmpId(), startPageIndex,numRecordsPerPage, sorting);
		}
		catch(Exception e ){
			e.printStackTrace();
		}	

//	    String json = gson.toJson(list);
// 
//	    json = "{\"Result\":\"OK\",\"Records\":"+ json + "}";
//        response.getWriter().print(json);
		
		JsonElement element = gson.toJsonTree(list,new TypeToken<List<Leave>>(){}.getType());
		JsonArray jsonArray = element.getAsJsonArray();
		String listData=jsonArray.toString();           //Return Json in the format required by jTable plugin
		listData = "{\"Result\":\"OK\",\"Records\":"+listData+",\"TotalRecordCount\":"+count+"}";   
		       response.getWriter().print(listData);

	}
	

}
