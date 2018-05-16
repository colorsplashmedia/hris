package dai.hris.action.filemanager.employeeworkhistory;


import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dai.hris.model.Employee;
import dai.hris.model.ServiceRecord;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.employeeworkhistory.EmployeeWorkHistoryService;
import dai.hris.service.filemanager.employeeworkhistory.IEmployeeWorkHistoryService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;

/**
 * Servlet implementation class SaveEmployeeWorkHistoryAction
 */
@WebServlet("/SaveServiceRecordAction")
public class SaveServiceRecordAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    IEmployeeWorkHistoryService service = new EmployeeWorkHistoryService();
    Gson gson = new Gson();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveServiceRecordAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		ServiceRecord model = new ServiceRecord();
		
		String jobTitleIdStr = request.getParameter("jobTitleId");
		String empIdStr = request.getParameter("empId");
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		String status = request.getParameter("status");
		String placeOfAssignment = request.getParameter("placeOfAssignment");
		String causeRemarks = request.getParameter("causeRemarks");
		String branch = request.getParameter("branch");
		String wop = request.getParameter("wop");
		String salary = request.getParameter("salary");
		
		
//		model.setServiceRecordId(Integer.parseInt(request.getParameter("serviceRecordId")));
		
		if(jobTitleIdStr != null && jobTitleIdStr.length() > 0){
			model.setJobTitleId(Integer.parseInt(jobTitleIdStr));
		}
		
		if(empIdStr != null && empIdStr.length() > 0){
			model.setEmpId(Integer.parseInt(empIdStr));
		}
		
		if(status != null && status.length() > 0){
			model.setStatus(status);
		} else {
			model.setStatus("");
		}
		
		if(placeOfAssignment != null && placeOfAssignment.length() > 0){
			model.setPlaceOfAssignment(placeOfAssignment);
		} else {
			model.setPlaceOfAssignment("");
		}
		
		if(branch != null && branch.length() > 0){
			model.setBranch(branch);
		} else {
			model.setBranch("");
		}
		
		if(wop != null && wop.length() > 0){
			model.setWop(wop);
		} else {
			model.setWop("");
		}
    	
    	
    	model.setDateFrom(dateFrom);
    	model.setDateTo(dateTo);
    	
    	
    	model.setCauseRemarks(causeRemarks);
    	model.setSalary(new BigDecimal(salary));
		
		
		try {
//			employeeWorkHistoryService.add(model);		
//			
//			String json = gson.toJson(model);
//			System.out.println("json: " + json);
//			String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
//			response.getWriter().print(jsonData);		
			
//			if(service.isExist(model.getEmployerName())) {
//				String errorMsg = gson.toJson("City already exist.");				
//				String jsonData = "{\"Result\":\"ERROR\",\"Message\":" + errorMsg + "}";
//				response.getWriter().print(jsonData);
//			} else {
//				
//			}
			
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("SERVICE RECORD");
			sysModel.setProcessDesc("SAVE SERVICE RECORD");
			sysModel.setProcessType("SAVE");
			sysModel.setUserId(employeeLoggedIn.getEmpId());

			
			sysTrailService.insertSystemTrail(sysModel);
			
			service.saveServiceRecord(model);
			String json = gson.toJson(model);
			System.out.println("json: " + json);
			String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
			response.getWriter().print(jsonData);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
		}
	}

}
