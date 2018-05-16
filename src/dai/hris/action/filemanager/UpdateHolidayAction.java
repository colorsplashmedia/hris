/**
 * 
 */
package dai.hris.action.filemanager;

import java.io.IOException;




import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;

import dai.hris.model.Employee;
import dai.hris.model.Holiday;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.holiday.HolidayService;
import dai.hris.service.filemanager.holiday.IHolidayService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;


@WebServlet("/UpdateHolidayAction")
public class UpdateHolidayAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		Holiday model = new Holiday();
		HashMap<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
		  String name = (String) names.nextElement();
		  map.put(name, request.getParameterValues(name));
		}
		try {
  
			BeanUtils.populate(model, map);
			
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		IHolidayService service = new HolidayService();
		
		try {
//			holidayService.update(holiday);
//			response.getWriter().print("{\"Result\":\"OK\"}");
			
			if(service.isExistUpdate(model.getHolidayName(), model.getHolidayId(), model.getHolidayDate())) {
				String errorMsg = gson.toJson("Holiday already exist.");				
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
			sysModel.setModuleName("HOLIDAY FILEMAINTENANCE");
			sysModel.setProcessDesc("Update Holiday: " + model.getHolidayName() + " | New Holiday Date: " + model.getHolidayDate());
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

}
