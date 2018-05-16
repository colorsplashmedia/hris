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
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.country.CountryService;
import dai.hris.service.filemanager.country.ICountryService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;

/**
 * @author rj
 *
 */
@WebServlet("/DeleteCountryAction")
public class DeleteCountryAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		
		ICountryService service = new CountryService();
		
		try {
			int id = Integer.parseInt(request.getParameter("countryId"));
			
			String jsonData = "{\"Result\":\"OK\"}";
			
			if(service.checkIfRecordHasBeenUsed(id)){
				jsonData = "{\"Result\":\"ERROR\",\"Message\":\"Record already has child data thus cannot be deleted. \"}";		
			} else {
				Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
				
				ISystemTrailService sysTrailService = new SystemTrailService();
				SystemTrail sysModel = new SystemTrail();
				
				sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
				sysModel.setModuleName("COUNTRY FILEMAINTENANCE");
				sysModel.setProcessDesc("Delete Country: " + service.getCountryById(id).getCountryName());
				sysModel.setProcessType("DELETE");
				sysModel.setUserId(employeeLoggedIn.getEmpId());

				
				sysTrailService.insertSystemTrail(sysModel);
				
				service.delete(id);
			}
			
		
			response.getWriter().print(jsonData);
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
