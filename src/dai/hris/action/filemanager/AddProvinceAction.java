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
import dai.hris.model.Province;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.province.ProvinceService;
import dai.hris.service.filemanager.province.IProvinceService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;


/**
 * @author rj
 *
 */
@WebServlet("/AddProvinceAction")
public class AddProvinceAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		Province model = new Province();
		model.setProvinceName(request.getParameter("provinceName"));

		IProvinceService service = new ProvinceService();

		try {
//			service.add(Province);
			
			if(service.isExist(model.getProvinceName())) {
				String errorMsg = gson.toJson("Province already exist.");				
				String jsonData = "{\"Result\":\"ERROR\",\"Message\":" + errorMsg + "}";
				response.getWriter().print(jsonData);
			} else {
				ISystemTrailService sysTrailService = new SystemTrailService();
				SystemTrail sysModel = new SystemTrail();
				
				sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
				sysModel.setModuleName("PROVINCE FILE MAINTENANCE");
				sysModel.setProcessDesc("ADD PROVINCE: " + model.getProvinceName());
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

//		String json = gson.toJson(Province);
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
