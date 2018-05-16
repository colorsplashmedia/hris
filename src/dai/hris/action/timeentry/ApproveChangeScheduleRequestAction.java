package dai.hris.action.timeentry;


import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dai.hris.model.Employee;
import dai.hris.model.Resolution;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.timeentry.ITimeEntryService;
import dai.hris.service.timeentry.TimeEntryService;




/**
 * Servlet implementation class for Servlet: LoginAction
 *
 */
@WebServlet("/ApproveChangeScheduleRequestAction")
 public class ApproveChangeScheduleRequestAction extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	 private static final long serialVersionUID = -6185891323760506163L;	
	 Gson gson = new Gson();
	 /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public ApproveChangeScheduleRequestAction() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doPost(request, response);
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String approverType = request.getSession().getAttribute("approverType").toString();
		String isAdminChecked = request.getSession().getAttribute("isAdminChecked").toString();
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		String empScheduleDisputeId = request.getParameter("empScheduleDisputeId");	
		String newStatus = "";
			
        ITimeEntryService service = new TimeEntryService();        
        
		try {
			//0 - FOR APPROVAL
            //1 - NOT APPROVED
            //2 - FOR UNIT SUPERVISOR APPROVAL
            //3 - FOR SECTION SUPERVISOR APPROVAL
            //4 - FOR HR APPROVAL
            //5 - FOR ADMIN APPROVAL
			//6 - APPROVED
			if("ADMIN".equals(approverType)){
				newStatus = "6";
			} else if("HRADMIN".equals(approverType)) {
				if("Y".equals(isAdminChecked)){
					newStatus = "5";
				} else {
					newStatus = "6";
				}
			} else if("SECTIONADMIN".equals(approverType)) {
				newStatus = "4";
			} else if("UNITADMIN".equals(approverType)) {
				newStatus = "3";
			} else if("SUBUNITADMIN".equals(approverType)) {
				newStatus = "2";
			}
			
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			Resolution model = service.getEmployeeScheduleDisputeById(Integer.parseInt(empScheduleDisputeId));
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("CHANGE SCHEDULE REQUEST");
			sysModel.setProcessDesc("Approved By: " + employeeLoggedIn.getFirstname() + " " + employeeLoggedIn.getLastname() 
					+ " | Approver Type: " + approverType
					+ " | Old Schedule: " + model.getOldScheduleDesc()
					+ " | New Schedule: " + model.getScheduleDesc()
					);
			sysModel.setProcessType("APPROVAL");
			sysModel.setUserId(employeeLoggedIn.getEmpId());

			
			sysTrailService.insertSystemTrail(sysModel);
			
			service.approveScheduleChangeRequest(employeeLoggedIn.getEmpId(), newStatus, Integer.parseInt(empScheduleDisputeId));
			
	
        	
			String json = gson.toJson("YES");
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