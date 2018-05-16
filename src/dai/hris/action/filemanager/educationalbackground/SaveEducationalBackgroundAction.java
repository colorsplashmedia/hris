package dai.hris.action.filemanager.educationalbackground;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dai.hris.model.EducationalBackground;
import dai.hris.model.Employee;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.educationalbackground.EducationalBackgroundService;
import dai.hris.service.filemanager.educationalbackground.IEducationalBackgroundService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;

@WebServlet("/SaveEducationalBackgroundAction")
public class SaveEducationalBackgroundAction extends HttpServlet {

	IEducationalBackgroundService service = new EducationalBackgroundService();
	Gson gson = new Gson();
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		EducationalBackground model = new EducationalBackground();
		
		model.setEmpId(Integer.parseInt(request.getParameter("empId")));
		model.setCourse(request.getParameter("course"));
		model.setRemarks(request.getParameter("remarks"));
		model.setSchool(request.getParameter("school"));
		model.setYearAttended(request.getParameter("yearAttended"));
		model.setYearGraduated(request.getParameter("yearGraduated"));

		

		try {
//			educationalBacgroundService.add(eb);		
//			
//			String json = gson.toJson(eb);
//			System.out.println("json: " + json);
//			String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
//			response.getWriter().print(jsonData);		
			
			if(service.isExist(model.getSchool(), model.getCourse())) {
				String errorMsg = gson.toJson("Education Background already exist.");				
				String jsonData = "{\"Result\":\"ERROR\",\"Message\":" + errorMsg + "}";
				response.getWriter().print(jsonData);
			} else {
				ISystemTrailService sysTrailService = new SystemTrailService();
				SystemTrail sysModel = new SystemTrail();
				
				sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
				sysModel.setModuleName("EDUCATIONAL BACKGROUND");
				sysModel.setProcessDesc("SAVE EDUCATIONAL BACKGROUND");
				sysModel.setProcessType("SAVE");
				sysModel.setUserId(employeeLoggedIn.getEmpId());

				
				sysTrailService.insertSystemTrail(sysModel);
				
				service.add(model);
				String json = gson.toJson(model);
				System.out.println("json: " + json);
				String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
				response.getWriter().print(jsonData);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}

	

}
