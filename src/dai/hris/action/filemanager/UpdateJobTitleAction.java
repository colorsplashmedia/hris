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
import dai.hris.model.JobTitle;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.jobtitle.JobTitleService;
import dai.hris.service.filemanager.jobtitle.IJobTitleService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;


/**
 * @author rj
 *
 */
@WebServlet("/UpdateJobTitleAction")
public class UpdateJobTitleAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		JobTitle model = new JobTitle();
		
		model.setJobTitleId(Integer.parseInt(request.getParameter("jobTitleId")));
		model.setJobTitle(request.getParameter("jobTitle"));
		
		String salaryGradeIdStr = request.getParameter("salaryGradeId");
		
		int salaryGradeId = 0;
		
		if(salaryGradeIdStr != null && salaryGradeIdStr.length() > 0) {
			salaryGradeId = Integer.parseInt(salaryGradeIdStr);
		}
		
		model.setSalaryGradeId(salaryGradeId);

		IJobTitleService service = new JobTitleService();
		
		try {
//			service.update(jobTitle);
//			response.getWriter().print("{\"Result\":\"OK\"}");
			
			if(service.isExistUpdate(model.getJobTitle(), model.getJobTitleId())) {
				String errorMsg = gson.toJson("Job Title already exist.");				
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
			sysModel.setModuleName("JOB TITLE FILEMAINTENANCE");
			sysModel.setProcessDesc("Update Job Title: " + model.getJobTitle());
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
