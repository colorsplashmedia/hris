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
import dai.hris.model.Deduction;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.payroll.IDeductionService;
import dai.hris.service.payroll.impl.DeductionService;


/**
 * @author rj
 *
 */
@WebServlet("/UpdateDeductionAction")
public class UpdateDeductionAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		Deduction model = new Deduction();
		model.setDeductionName(request.getParameter("deductionName"));
		model.setInstitution(request.getParameter("institution"));
		model.setAccountingCode(request.getParameter("accountingCode"));
		model.setDeductionId(Integer.parseInt(request.getParameter("deductionId")));
		model.setCreatedBy(employeeLoggedIn.getEmpId());

		IDeductionService service = new DeductionService();
		
		try {
			
			if(service.isExistUpdate(model.getDeductionName(), model.getDeductionId())) {
				String errorMsg = gson.toJson("Deduction already exist.");				
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
			sysModel.setModuleName("DEDUCTION FILEMAINTENANCE");
			sysModel.setProcessDesc("Update Deduction: " + model.getDeductionName() + " | Accounting Code: " + model.getAccountingCode());
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
