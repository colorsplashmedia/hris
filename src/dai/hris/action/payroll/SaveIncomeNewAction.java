package dai.hris.action.payroll;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dai.hris.model.Employee;
import dai.hris.model.EmployeeIncome;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.payroll.IIncomeService;
import dai.hris.service.payroll.impl.IncomeService;

/**
 * Servlet implementation class SaveDeductionNewAction
 */
@WebServlet("/SaveIncomeNewAction")
public class SaveIncomeNewAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IIncomeService service = new IncomeService();
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
		
		String incomeId = request.getParameter("incomeId");	
		String amount = request.getParameter("amount");	
		String payrollPeriodId = request.getParameter("payrollPeriodId");	
		String payrollCycle = request.getParameter("payrollCycle");	
		String incomeType = request.getParameter("incomeType");	
		String empId[] = request.getParameterValues("empId")[0].split(",");		
		
		EmployeeIncome model = new EmployeeIncome();
		
		model.setIncomeId(Integer.parseInt(incomeId));
		model.setAmount(new BigDecimal(amount));
		model.setIncomeType(incomeType);
		model.setCreatedBy(employeeLoggedIn.getEmpId());
		
		if(payrollPeriodId != null && payrollPeriodId.length() > 0 && "O".equals(incomeType)){
			model.setPayrollPeriodId(Integer.parseInt(payrollPeriodId));
		}
		
		model.setPayrollCycle(payrollCycle);
		
		try {
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("INCOME");
			sysModel.setProcessDesc("Deduction Amount: " + amount
					+ " | Income Type: " + incomeType
					+ " | Employee Id: " 
					);
			
			for(int i=0;i<empId.length;i++) {
				model.setEmpId(Integer.parseInt(empId[i]));
				service.saveEmpIncome(model);
				
				sysModel.setProcessDesc(sysModel.getProcessDesc() + empId[i] + ", ");
			}
			
			sysModel.setProcessType("SAVE");
			sysModel.setUserId(employeeLoggedIn.getEmpId());
			
			sysTrailService.insertSystemTrail(sysModel);
//			svc.saveOrUpdate(dd);
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
