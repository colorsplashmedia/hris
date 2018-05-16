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

import dai.hris.model.Employee;
import dai.hris.model.Resolution;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.timeentry.ITimeEntryService;
import dai.hris.service.timeentry.TimeEntryService;


/**
 * @author rj
 *
 */
@WebServlet("/GetChangeRequestScheduleBySupervisorIdAction")
public class GetChangeRequestScheduleBySupervisorIdAction extends HttpServlet {
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
		
//		String supervisorId = request.getParameter("empId");
		
		ITimeEntryService service = new TimeEntryService();
		
		try {
			
			List<Resolution> list = new ArrayList<Resolution>();		
			
			if("ADMIN".equals(approverType) || "HRADMIN".equals(approverType)){
				//get all Change Schedule
				list = service.getAllEmpScheduleChange();
			} else if("SECTIONADMIN".equals(approverType)) {
				//get all Change Schedule base on sectionId
				list = service.getAllEmpScheduleChangeBySectionId(employeeLoggedIn.getSectionId());
			} else if("UNITADMIN".equals(approverType)) {
				//get all Change Schedule base on unitId
				list = service.getAllEmpScheduleChangeByUnitId(employeeLoggedIn.getUnitId());
			} else if("SUBUNITADMIN".equals(approverType)) {
				//get all Change Schedule base on subUnitId			
				list = service.getAllEmpScheduleChangeBySubUnitId(employeeLoggedIn.getSubUnitId());
			}
			
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("CHANGE SCHEDULE REQUEST");
			sysModel.setProcessDesc("Viewed By: " + employeeLoggedIn.getFirstname() + " " + employeeLoggedIn.getLastname() 
					+ " | Viewer Type: " + approverType
					);
			sysModel.setProcessType("VIEW");
			sysModel.setUserId(employeeLoggedIn.getEmpId());

			
			sysTrailService.insertSystemTrail(sysModel);
	
//			List<Resolution> list = service.getAllEmpScheduleChangeBySupervisorId(Integer.parseInt(supervisorId));		
			
			String json = gson.toJson(list);
		    System.out.println("GetChangeRequestScheduleBySupervisorIdAction json: " + json);
	 
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
