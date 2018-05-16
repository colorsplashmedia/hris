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

import dai.hris.model.Country;
import dai.hris.model.Employee;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.country.CountryService;
import dai.hris.service.filemanager.country.ICountryService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;


/**
 * @author rj
 *
 */
@WebServlet("/AddCountryAction")
public class AddCountryAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		Country model = new Country();
		model.setCountryName(request.getParameter("countryName"));

		ICountryService service = new CountryService();

		try {
//			service.add(country);
			
			if(service.isExist(model.getCountryName())) {
				String errorMsg = gson.toJson("City already exist.");				
				String jsonData = "{\"Result\":\"ERROR\",\"Message\":" + errorMsg + "}";
				response.getWriter().print(jsonData);
			} else {
				ISystemTrailService sysTrailService = new SystemTrailService();
				SystemTrail sysModel = new SystemTrail();
				
				sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
				sysModel.setModuleName("COUNTRY FILE MAINTENANCE");
				sysModel.setProcessDesc("ADD NEW COUNTRY: " + model.getCountryName());
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

//		String json = gson.toJson(country);
//		System.out.println("json: " + json);
//		String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
//		response.getWriter().print(jsonData);

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
