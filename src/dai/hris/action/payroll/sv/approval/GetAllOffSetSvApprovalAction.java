package dai.hris.action.payroll.sv.approval;

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
import dai.hris.model.EmployeeOffSet;
import dai.hris.service.overtime.EmployeeOffSetService;
import dai.hris.service.overtime.IEmployeeOffSetService;

@WebServlet("/GetAllOffSetSvApprovalAction")
public class GetAllOffSetSvApprovalAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String approverType = request.getSession().getAttribute("approverType").toString();
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
//		int supervisorId = Integer.parseInt(request.getParameter("superVisorId"));
		IEmployeeOffSetService service = new EmployeeOffSetService();
		List<EmployeeOffSet> list = null;
		
		int count = 0;
		String startIndex = request.getParameter("jtStartIndex");
		String pageSize = request.getParameter("jtPageSize");		
		String sorting = request.getParameter("jtSorting");
		
		try {
//			count=service.getCountForSupervisor(supervisorId);
			
			if("ADMIN".equals(approverType)){
				//get all count
				count = service.getAllCount();
			} else if("HRADMIN".equals(approverType)) {
				//get all count base on sectionId
				count = service.getAllCountBySectionId(employeeLoggedIn.getSectionId());
			} else if("SECTIONADMIN".equals(approverType)) {
				//get all count base on sectionId
				count = service.getAllCountBySectionId(employeeLoggedIn.getSectionId());
			} else if("UNITADMIN".equals(approverType)) {
				//get all count base on unitId
				count = service.getAllCountByUnitId(employeeLoggedIn.getUnitId());
			} else if("SUBUNITADMIN".equals(approverType)) {
				//get all count base on subUnitId			
				count = service.getAllCountBySubUnitId(employeeLoggedIn.getSubUnitId());
			}
			
			
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
		
//			list = service.getAllOvertimeForSvApprovalBySuperVisorId(supervisorId, startPageIndex,numRecordsPerPage, sorting);
			
			if("ADMIN".equals(approverType)){
				//get all Leave
				list = service.getAllOffSetForApproval(employeeLoggedIn.getEmpId(), startPageIndex,numRecordsPerPage, sorting);
			} else if("HRADMIN".equals(approverType)) {
				//get all Leave base on sectionId
				list = service.getAllOffSetForHRApproval(employeeLoggedIn.getEmpId(), startPageIndex,numRecordsPerPage, sorting);
			} else if("SECTIONADMIN".equals(approverType)) {
				//get all Leave base on sectionId
				list = service.getAllOffSetForApprovalBySectionId(employeeLoggedIn.getEmpId(), employeeLoggedIn.getSectionId(), startPageIndex,numRecordsPerPage, sorting);
			} else if("UNITADMIN".equals(approverType)) {
				//get all Leave base on unitId
				list = service.getAllOffSetForApprovalByUnitId(employeeLoggedIn.getEmpId(), employeeLoggedIn.getUnitId(), startPageIndex,numRecordsPerPage, sorting);
			} else if("SUBUNITADMIN".equals(approverType)) {
				//get all Leave base on subUnitId			
				list = service.getAllOffSetForApprovalBySubUnitId(employeeLoggedIn.getEmpId(), employeeLoggedIn.getSubUnitId(), startPageIndex,numRecordsPerPage, sorting);
			}
			
			////list = service.getAllOvertimeForSvApprovalBySuperVisorId(superVisorId);
		}
		catch(Exception e ){
			e.printStackTrace();
		}	

//	    String json = gson.toJson(list);
// 
//	    json = "{\"Result\":\"OK\",\"Records\":"+ json + "}";
//        response.getWriter().print(json);
		
		JsonElement element = gson.toJsonTree(list,new TypeToken<List<EmployeeOffSet>>(){}.getType());
		JsonArray jsonArray = element.getAsJsonArray();
		String listData=jsonArray.toString();           //Return Json in the format required by jTable plugin
		listData = "{\"Result\":\"OK\",\"Records\":"+listData+",\"TotalRecordCount\":"+count+"}";   
		       response.getWriter().print(listData);

	}
	

}
