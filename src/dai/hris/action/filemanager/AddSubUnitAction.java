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

import dai.hris.model.SystemTrail;
import dai.hris.model.SubUnit;
import dai.hris.service.filemanager.employee.EmployeeService;
import dai.hris.service.filemanager.employee.IEmployeeService;
import dai.hris.service.filemanager.subunit.ISubUnitService;
import dai.hris.service.filemanager.subunit.SubUnitService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.model.Employee;



/**
 * @author Ian
 *
 */
@WebServlet("/AddSubUnitAction")
public class AddSubUnitAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");

		SubUnit model = new SubUnit();
		model.setSubUnitName((request.getParameter("subUnitName")));
		
		String empIdStr = request.getParameter("empId");
		String assistantIdStr = request.getParameter("assistantId");
		String unitIdStr = request.getParameter("unitId");
		
		int empId = 0;
		int assistantId = 0;
		int unitId = 0;
		
		if(empIdStr != null && empIdStr.length() > 0) {
			empId = Integer.parseInt(empIdStr);
		}
		
		if(assistantIdStr != null && assistantIdStr.length() > 0) {
			assistantId = Integer.parseInt(assistantIdStr);
		}
		
		if(unitIdStr != null && unitIdStr.length() > 0) {
			unitId = Integer.parseInt(unitIdStr);
		}
		
		model.setEmpId(empId);
		model.setAssistantId(assistantId);
		model.setUnitId(unitId);
		

		ISubUnitService service = new SubUnitService();

		try {
			if(service.isExist(model.getSubUnitName())) {
				String errorMsg = gson.toJson("Sub Unit already exist.");				
				String jsonData = "{\"Result\":\"ERROR\",\"Message\":" + errorMsg + "}";
				response.getWriter().print(jsonData);
			} else {
				
				IEmployeeService empService = new EmployeeService();
				Employee emp = empService.getEmployee(empId);
				
				ISystemTrailService sysTrailService = new SystemTrailService();
				SystemTrail sysModel = new SystemTrail();
				
				sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
				sysModel.setModuleName("SUB UNIT");
				sysModel.setProcessDesc("Add New Sub Unit: " + model.getSubUnitName() + " | Sub Unit Head: " + emp.getFirstname() + " " + emp.getLastname());
				sysModel.setProcessType("SAVE");
				sysModel.setUserId(employeeLoggedIn.getEmpId());

				
				sysTrailService.insertSystemTrail(sysModel);
				
				service.add(model);
				String json = gson.toJson(model);
				System.out.println("json: " + json);
				String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
				response.getWriter().print(jsonData);
			}			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

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
