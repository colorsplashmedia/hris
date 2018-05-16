/**
 * 
 */
package dai.hris.action.filemanager;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dai.hris.model.Employee;
import dai.hris.model.SalaryGrade;
import dai.hris.service.filemanager.salarygrade.ISalaryGradeService;
import dai.hris.service.filemanager.salarygrade.SalaryGradeService;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;


/**
 * @author Ian
 *
 */
@WebServlet("/UpdateSalaryGradeAction")
public class UpdateSalaryGradeAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		SalaryGrade model = new SalaryGrade();
		
		model.setSalaryGradeId(Integer.parseInt(request.getParameter("salaryGradeId")));
		model.setSalaryGradeName((request.getParameter("salaryGradeName")));
		
		model.setStep(Integer.parseInt(request.getParameter("step")));
		model.setBasicSalary(new BigDecimal(request.getParameter("basicSalary")));
		


		ISalaryGradeService service = new SalaryGradeService();
		
		try {
			
			if(service.isExistUpdate(model.getSalaryGradeName(), model.getSalaryGradeId())) {
				String errorMsg = gson.toJson("Salary Grade already exist.");				
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
			sysModel.setModuleName("SALARY GRADE FILEMAINTENANCE");
			sysModel.setProcessDesc("Update Salary Grade: " + model.getSalaryGradeName() + " | New Salary: " + model.getBasicSalary());
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
