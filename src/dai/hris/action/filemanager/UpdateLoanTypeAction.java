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
import dai.hris.model.LoanType;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.loantype.LoanTypeService;
import dai.hris.service.filemanager.loantype.ILoanTypeService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;


/**
 * @author rj
 *
 */
@WebServlet("/UpdateLoanTypeAction")
public class UpdateLoanTypeAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		LoanType model = new LoanType();
		
		model.setLoanTypeId(Integer.parseInt(request.getParameter("loanTypeId")));
		model.setLoanTypeName(request.getParameter("loanTypeName"));
		model.setLoanCode(request.getParameter("loanCode"));
		model.setInstitution(request.getParameter("institution"));
		model.setAccountingCode(request.getParameter("accountingCode"));

		ILoanTypeService service = new LoanTypeService();
		
		try {
			
			if(service.isExistUpdate(model.getLoanTypeName(), model.getLoanTypeId())) {
				String errorMsg = gson.toJson("Loan Type already exist.");				
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
			sysModel.setModuleName("LOAN TYPE FILEMAINTENANCE");
			sysModel.setProcessDesc("Update Loan Type: " + model.getLoanTypeName() + " | Accounting Code: " + model.getAccountingCode());
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
