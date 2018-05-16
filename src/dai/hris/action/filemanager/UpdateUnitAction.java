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
import dai.hris.model.Unit;
import dai.hris.service.filemanager.unit.IUnitService;
import dai.hris.service.filemanager.unit.UnitService;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;


/**
 * @author Ian
 *
 */
@WebServlet("/UpdateUnitAction")
public class UpdateUnitAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		Unit model = new Unit();
		
		model.setUnitId(Integer.parseInt(request.getParameter("unitId")));
		model.setUnitName((request.getParameter("unitName")));
		
		String empIdStr = request.getParameter("empId");
		String assistantIdStr = request.getParameter("assistantId");
		String sectionIdStr = request.getParameter("sectionId");
		
		int empId = 0;
		int assistantId = 0;
		int sectionId = 0;
		
		if(empIdStr != null && empIdStr.length() > 0) {
			empId = Integer.parseInt(empIdStr);
		}
		
		if(assistantIdStr != null && assistantIdStr.length() > 0) {
			assistantId = Integer.parseInt(assistantIdStr);
		}		
		
		if(sectionIdStr != null && sectionIdStr.length() > 0) {
			sectionId = Integer.parseInt(sectionIdStr);
		}
		
		model.setEmpId(empId);
		model.setAssistantId(assistantId);
		model.setSectionId(sectionId);
		
		
		IUnitService service = new UnitService();
		
		try {
			
			if(service.isExistUpdate(model.getUnitName(), model.getUnitId())) {
				String errorMsg = gson.toJson("Unit already exist.");				
				String jsonData = "{\"Result\":\"ERROR\",\"Message\":" + errorMsg + "}";
				response.getWriter().print(jsonData);
			} else {
				service.update(model);
				String json = gson.toJson(model);
				System.out.println("json: " + json);
				String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
				response.getWriter().print(jsonData);
			}		
			
			
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("UNIT FILEMAINTENANCE");
			sysModel.setProcessDesc("Update Unit: " + model.getUnitName());
			sysModel.setProcessType("EDIT");
			sysModel.setUserId(employeeLoggedIn.getEmpId());

			
			sysTrailService.insertSystemTrail(sysModel);
		}
		catch(Exception e ){
			e.printStackTrace();
			String error = "{\"Result\":\"ERROR\"}";
			 response.getWriter().print(error);
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
