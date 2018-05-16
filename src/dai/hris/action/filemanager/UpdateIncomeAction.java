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
import dai.hris.model.Income;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.payroll.IIncomeService;
import dai.hris.service.payroll.impl.IncomeService;


/**
 * @author rj
 *
 */
@WebServlet("/UpdateIncomeAction")
public class UpdateIncomeAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		Income model = new Income();
		model.setIncomeName(request.getParameter("incomeName"));
		model.setIsTaxable(request.getParameter("isTaxable"));
		model.setAccountingCode(request.getParameter("accountingCode"));
		model.setCreatedBy(employeeLoggedIn.getEmpId());
		model.setIncomeId(Integer.parseInt(request.getParameter("incomeId")));

		IIncomeService service = new IncomeService();
		
		try {
			
			if(service.isExistUpdate(model.getIncomeName(), model.getIncomeId())) {
				String errorMsg = gson.toJson("Income already exist.");				
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
			sysModel.setModuleName("INCOME FILEMAINTENANCE");
			sysModel.setProcessDesc("Update Income: " + model.getIncomeName() + " | Accounting Code: " + model.getAccountingCode());
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
