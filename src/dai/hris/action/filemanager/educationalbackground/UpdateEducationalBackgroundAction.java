package dai.hris.action.filemanager.educationalbackground;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;

import dai.hris.model.EducationalBackground;
import dai.hris.model.Employee;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.educationalbackground.EducationalBackgroundService;
import dai.hris.service.filemanager.educationalbackground.IEducationalBackgroundService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;

@WebServlet("/UpdateEducationalBackgroundAction")
public class UpdateEducationalBackgroundAction extends HttpServlet {

	IEducationalBackgroundService service = new EducationalBackgroundService();
	Gson gson = new Gson();

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost UpdateEducationalBackgroundAction");
		EducationalBackground model = new EducationalBackground();
		
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
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
		

		try {
			
			if(service.isExistUpdate(model.getSchool(), model.getEducBkgrndId(), model.getCourse())) {
				String errorMsg = gson.toJson("Education Background already exist.");				
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
			sysModel.setModuleName("EDUCATIONAL BACKGROUND");
			sysModel.setProcessDesc("EDIT EDUCATIONAL BACKGROUND");
			sysModel.setProcessType("EDIT");
			sysModel.setUserId(employeeLoggedIn.getEmpId());

			
			sysTrailService.insertSystemTrail(sysModel);
			
			
//			int eBackId = service.update(model);
//			model.setEducBkgrndId(eBackId);
//			String json = gson.toJson(model);
//			String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
//			System.out.println(jsonData);
//			response.getWriter().print(jsonData);		
			
		} catch (SQLException e) {
			e.printStackTrace();
			String error = "{\"Result\":\"ERROR\"}";
			 response.getWriter().print(error);
		} catch (Exception e) {
			e.printStackTrace();
			String error = "{\"Result\":\"ERROR\"}";
			 response.getWriter().print(error);
		}
	}
	

	

}
