/**
 * 
 */
package dai.hris.action.filemanager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dai.hris.model.Employee;
import dai.hris.model.EmployeeType;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.employeetype.EmployeeTypeService;
import dai.hris.service.filemanager.employeetype.IEmployeeTypeService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;


/**
 * @author rj
 *
 */
@WebServlet("/AddEmployeeTypeAction")
public class AddEmployeeTypeAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		EmployeeType model = new EmployeeType();
		model.setEmployeeTypeName((request.getParameter("employeeTypeName")));
		
		IEmployeeTypeService service = new EmployeeTypeService();
	
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		try {
			service.add(model);
			
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("EMPLOYEE TYPE FILEMAINTENANCE");
			sysModel.setProcessDesc("ADD EMPLOYEE TYPE NAME: " + model.getEmployeeTypeName());
			sysModel.setProcessType("SAVE");
			sysModel.setUserId(employeeLoggedIn.getEmpId());
			
			sysTrailService.insertSystemTrail(sysModel);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		String json = gson.toJson(model);
		System.out.println("json: " + json);
		String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
		response.getWriter().print(jsonData);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doGet(request, response);
	}

}
