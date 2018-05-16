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

import dai.hris.model.SalaryGrade;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.salarygrade.ISalaryGradeService;
import dai.hris.service.filemanager.salarygrade.SalaryGradeService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.model.Employee;



/**
 * @author Ian
 *
 */
@WebServlet("/AddSalaryGradeAction")
public class AddSalaryGradeAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");

		SalaryGrade model = new SalaryGrade();
		model.setSalaryGradeName((request.getParameter("salaryGradeName")));
		model.setStep(Integer.parseInt(request.getParameter("step")));
		model.setBasicSalary(new BigDecimal(request.getParameter("basicSalary")));

		ISalaryGradeService service = new SalaryGradeService();

		try {
			if(service.isExist(model.getSalaryGradeName())) {
				String errorMsg = gson.toJson("Salary Grade already exist.");				
				String jsonData = "{\"Result\":\"ERROR\",\"Message\":" + errorMsg + "}";
				response.getWriter().print(jsonData);
			} else {
				ISystemTrailService sysTrailService = new SystemTrailService();
				SystemTrail sysModel = new SystemTrail();
				
				sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
				sysModel.setModuleName("SALARY GRADE");
				sysModel.setProcessDesc("Add Salary Grade: " + model.getSalaryGradeName() + " | Basic Salary: " + model.getBasicSalary());
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
