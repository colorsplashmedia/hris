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
import dai.hris.model.ObligationRequest;
import dai.hris.model.SystemTrail;
import dai.hris.service.cba.EmployeeCBAService;
import dai.hris.service.cba.IEmployeeCBAService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;



@WebServlet("/GetObligationRequestListAction")
public class GetObligationRequestListAction extends HttpServlet {
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
		
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		IEmployeeCBAService service = new EmployeeCBAService();
		
		try {
	
			List<ObligationRequest> list = service.getAllObligationRequest();
			String json = gson.toJson(list);
		    System.out.println("json: " + json);
		    
		    ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("OBLIGATION REQUEST");
			sysModel.setProcessDesc("Viewed List By: " + employeeLoggedIn.getFirstname() + " " + employeeLoggedIn.getLastname() 
					
					);
			sysModel.setProcessType("VIEW");
			sysModel.setUserId(employeeLoggedIn.getEmpId());

			
			sysTrailService.insertSystemTrail(sysModel);
	 
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
