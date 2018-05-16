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
import dai.hris.service.filemanager.salarygrade.ISalaryGradeService;
import dai.hris.service.filemanager.salarygrade.SalaryGradeService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;


/**
 * @author Ian
 *
 */
@WebServlet("/DeleteSalaryGradeAction")
public class DeleteSalaryGradeAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		
		ISalaryGradeService service = new SalaryGradeService();
		
		try {
			int id = Integer.parseInt(request.getParameter("salaryGradeId"));
			
			String jsonData = "{\"Result\":\"OK\"}";
			
			if(service.checkIfRecordHasBeenUsed(id)){
				jsonData = "{\"Result\":\"ERROR\",\"Message\":\"Record already has child data thus cannot be deleted. \"}";		
			} else {
				Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
				
				ISystemTrailService sysTrailService = new SystemTrailService();
				SystemTrail sysModel = new SystemTrail();
				
				sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
				sysModel.setModuleName("SALARY GRADE FILEMAINTENANCE");
				sysModel.setProcessDesc("Delete Salary Grade: " + service.getSalaryGrade(id).getSalaryGradeName());
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
