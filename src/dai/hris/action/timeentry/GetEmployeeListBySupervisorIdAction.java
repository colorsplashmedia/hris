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

import dai.hris.model.Employee;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.timeentry.ITimeEntryService;
import dai.hris.service.timeentry.TimeEntryService;


/**
 * @author rj
 *
 */
@WebServlet("/GetEmployeeListBySupervisorIdAction")
public class GetEmployeeListBySupervisorIdAction extends HttpServlet {
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
		
		String approverType = request.getSession().getAttribute("approverType").toString();
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");	
		
		
		ITimeEntryService service = new TimeEntryService();
		
		try {
			List<Employee> empList = null;
			
			if("ADMIN".equals(approverType)){
				empList = service.getEmployeeListPerSuperVisor(employeeLoggedIn.getEmpId(), "ALL");
			} else if("HRADMIN".equals(approverType)) {
				empList = service.getEmployeeListPerSuperVisor(employeeLoggedIn.getEmpId(), "ALL");
			} else if("SECTIONADMIN".equals(approverType)) {
				empList = service.getEmployeeListPerSuperVisor(employeeLoggedIn.getSectionId(), "SECTION");
			} else if("UNITADMIN".equals(approverType)) {
				empList = service.getEmployeeListPerSuperVisor(employeeLoggedIn.getUnitId(), "SECTION");
			} else if("SUBUNITADMIN".equals(approverType)) {
				empList = service.getEmployeeListPerSuperVisor(employeeLoggedIn.getSubUnitId(), "SECTION");
			}
	
			 ISystemTrailService sysTrailService = new SystemTrailService();
				SystemTrail sysModel = new SystemTrail();
				
				sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
				sysModel.setModuleName("EMPLOYEE LIST");
				sysModel.setProcessDesc("Viewed List By: " + employeeLoggedIn.getFirstname() + " " + employeeLoggedIn.getLastname() 
						
						);
				sysModel.setProcessType("VIEW");
				sysModel.setUserId(employeeLoggedIn.getEmpId());

				
				sysTrailService.insertSystemTrail(sysModel);
			
			
			String json = gson.toJson(empList);
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
