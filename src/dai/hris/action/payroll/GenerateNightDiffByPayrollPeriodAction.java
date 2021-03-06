package dai.hris.action.payroll;

import java.io.IOException;

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
import dai.hris.service.payroll.IPayrollPeriodService;
import dai.hris.service.payroll.IPayrollRunService;
import dai.hris.service.payroll.PayrollRunService;
import dai.hris.service.payroll.impl.PayrollPeriodService;

@WebServlet("/GenerateNightDiffByPayrollPeriodAction")
public class GenerateNightDiffByPayrollPeriodAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IPayrollRunService svc = new PayrollRunService();
	Gson gson = new Gson();

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
		Employee loggedInEmployee = (Employee) request.getSession().getAttribute("employeeLoggedIn");	
		int payrollPeriodId = Integer.valueOf(request.getParameter("payrollPeriodId"));
		String payrollType = request.getParameter("payrollType");
		String isContractualRecord = request.getParameter("isContractualRecord");
		
		System.out.println("payrollPeriodId: " + payrollPeriodId);		
		
		try {
			IPayrollPeriodService payService = new PayrollPeriodService();
			IPayrollRunService service = new PayrollRunService();
			
			PayrollPeriod model = payService.getPayrollPeriodById(payrollPeriodId, "");
			
			if(isContractualRecord.equals("Y")) {
				service.generateNightDiffByPayrollPeriodContractual(payrollPeriodId, payrollType, loggedInEmployee.getEmpId());
			} else {
				service.generateNightDiffByPayrollPeriod(payrollPeriodId, payrollType, loggedInEmployee.getEmpId());
			}
			
			Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("GENERATE NIGHT DIFFERENTIAL");
			sysModel.setProcessDesc("Generated By: " + employeeLoggedIn.getFirstname() + " " + employeeLoggedIn.getLastname() 
					+ " | Payroll Period: " + model.getPayPeriod()
					+ " | Payroll Code: " + model.getPayrollCode()
					+ " | Payroll Type: " + model.getPayrollType()
					);
			sysModel.setProcessType("GENERATE");
			sysModel.setUserId(employeeLoggedIn.getEmpId());

			
			sysTrailService.insertSystemTrail(sysModel);
			
			String json = gson.toJson("SUCCESS");
			System.out.println("json: " + json);
			String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
			response.getWriter().print(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
