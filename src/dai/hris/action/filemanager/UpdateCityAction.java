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

import dai.hris.model.City;
import dai.hris.model.Employee;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.city.CityService;
import dai.hris.service.filemanager.city.ICityService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;


/**
 * @author rj
 *
 */
@WebServlet("/UpdateCityAction")
public class UpdateCityAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		City model = new City();
		
		model.setCityId(Integer.parseInt(request.getParameter("cityId")));
		model.setCityName(request.getParameter("cityName"));

		ICityService service = new CityService();
		
		try {
//			service.update(city);
//			response.getWriter().print("{\"Result\":\"OK\"}");
			
			if(service.isExistUpdate(model.getCityName(), model.getCityId())) {
				String errorMsg = gson.toJson("City already exist.");				
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
			sysModel.setModuleName("CITY FILEMAINTENANCE");
			sysModel.setProcessDesc("Update City: " + model.getCityName());
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
