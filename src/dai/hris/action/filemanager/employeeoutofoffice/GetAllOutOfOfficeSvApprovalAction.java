package dai.hris.action.filemanager.employeeoutofoffice;

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
//import dai.hris.model.Employee;
import dai.hris.model.EmployeeOutOfOffice;
import dai.hris.service.filemanager.employeeoutofoffice.EmployeeOutOfOfficeService;
import dai.hris.service.filemanager.employeeoutofoffice.IEmployeeOutOfOfficeService;

@WebServlet("/GetAllOutOfOfficeSvApprovalAction")
public class GetAllOutOfOfficeSvApprovalAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost GetAllOutOfOfficeSvApprovalAction");
		
		String approverType = request.getSession().getAttribute("approverType").toString();
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		IEmployeeOutOfOfficeService service = new EmployeeOutOfOfficeService();
//		int superVisorId = Integer.parseInt(request.getParameter("superVisorId"));
//		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		
		List<EmployeeOutOfOffice> list = null;
		int count = 0;
		String startIndex = request.getParameter("jtStartIndex");
		String pageSize = request.getParameter("jtPageSize");		
		String sorting = request.getParameter("jtSorting");
		
		try {
//			count=service.getCountForSupervisor(superVisorId);
			
			if("ADMIN".equals(approverType) || "HRADMIN".equals(approverType)){
				//get all count
				count = service.getAllCount();
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
		
			//list = service.getAllEmployeeOOOForSvApprovalBySuperVisorId(superVisorId, startPageIndex,numRecordsPerPage, sorting);
			
			if("ADMIN".equals(approverType)){
				//get all OOO
				list = service.getAllEmployeeOOOForApproval(employeeLoggedIn.getEmpId(), startPageIndex,numRecordsPerPage, sorting);
			} else if("HRADMIN".equals(approverType)) {
				//get all OOO base on sectionId
				list = service.getAllEmployeeOOOForHRApproval(employeeLoggedIn.getEmpId(), startPageIndex,numRecordsPerPage, sorting);
			} else if("SECTIONADMIN".equals(approverType)) {
				//get all OOO base on sectionId
				list = service.getAllEmployeeOOOForApprovalBySectionId(employeeLoggedIn.getEmpId(), employeeLoggedIn.getSectionId(), startPageIndex,numRecordsPerPage, sorting);
			} else if("UNITADMIN".equals(approverType)) {
				//get all OOO base on unitId
				list = service.getAllEmployeeOOOForApprovalByUnitId(employeeLoggedIn.getEmpId(), employeeLoggedIn.getUnitId(), startPageIndex,numRecordsPerPage, sorting);
			} else if("SUBUNITADMIN".equals(approverType)) {
				//get all OOO base on subUnitId			
				list = service.getAllEmployeeOOOForApprovalBySubUnitId(employeeLoggedIn.getEmpId(), employeeLoggedIn.getSubUnitId(), startPageIndex,numRecordsPerPage, sorting);
			}			
			
			////list = employeeOOOService.getAllEmployeeOOOForSvApprovalBySuperVisorId(superVisorId);
		}
		catch(Exception e ){
			e.printStackTrace();
		}	

//	    String json = gson.toJson(list);
// 
//	    json = "{\"Result\":\"OK\",\"Records\":"+ json + "}";
//        response.getWriter().print(json);
		
		JsonElement element = gson.toJsonTree(list,new TypeToken<List<EmployeeOutOfOffice>>(){}.getType());
		JsonArray jsonArray = element.getAsJsonArray();
		String listData=jsonArray.toString();           //Return Json in the format required by jTable plugin
		listData = "{\"Result\":\"OK\",\"Records\":"+listData+",\"TotalRecordCount\":"+count+"}";   
		       response.getWriter().print(listData);

	}
	

}
