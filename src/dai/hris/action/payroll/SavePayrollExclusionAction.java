package dai.hris.action.payroll;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dai.hris.model.Employee;
import dai.hris.model.PayrollPeriod;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.payroll.IDeductionService;
import dai.hris.service.payroll.IPayrollPeriodService;
import dai.hris.service.payroll.impl.DeductionService;
import dai.hris.service.payroll.impl.PayrollPeriodService;

/**
 * Servlet implementation class SavePayrollExclusionAction
 */
@WebServlet("/SavePayrollExclusionAction")
public class SavePayrollExclusionAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IDeductionService service = new DeductionService();
	private Gson gson = new Gson();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		
		String payrollPeriodId = request.getParameter("payrollPeriodId");
		String empId[] = request.getParameterValues("empId")[0].split(",");		
			
		
		try {
			IPayrollPeriodService payrollService = new PayrollPeriodService();
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			PayrollPeriod model = payrollService.getPayrollPeriodById(Integer.parseInt(payrollPeriodId), "");
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("PAYROLL EXCLUSION");
			sysModel.setProcessDesc("Payroll Period: " + model.getPayPeriod()
					+ " | Payroll Code: " + model.getPayrollCode()
					+ " | Employee Id: " 
					);
			
			for(int i=0;i<empId.length;i++) {
				service.savePayrollExclusion(Integer.parseInt(empId[i]), Integer.parseInt(payrollPeriodId), employeeLoggedIn.getEmpId());
				sysModel.setProcessDesc(sysModel.getProcessDesc() + empId[i] + ", ");
			}
			
			sysModel.setProcessType("SAVE");
			sysModel.setUserId(employeeLoggedIn.getEmpId());

			
			sysTrailService.insertSystemTrail(sysModel);
			
			String json = gson.toJson("SUCCESS");
			String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
			response.getWriter().print(jsonData);			
			
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
