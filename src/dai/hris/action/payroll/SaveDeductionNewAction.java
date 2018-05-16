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
import dai.hris.model.EmployeeDeduction;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.payroll.IDeductionService;
import dai.hris.service.payroll.impl.DeductionService;

/**
 * Servlet implementation class SaveDeductionNewAction
 */
@WebServlet("/SaveDeductionNewAction")
public class SaveDeductionNewAction extends HttpServlet {
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
		
		String deductionId = request.getParameter("deductionId");	
		String amount = request.getParameter("amount");	
		String payrollPeriodId = request.getParameter("payrollPeriodId");	
		String payrollCycle = request.getParameter("payrollCycle");	
		String deductionType = request.getParameter("deductionType");
		String empId[] = request.getParameterValues("empId")[0].split(",");		
		
		EmployeeDeduction model = new EmployeeDeduction();
		
		model.setDeductionId(Integer.parseInt(deductionId));
		model.setAmount(new BigDecimal(amount));
		model.setDeductionType(deductionType);
		model.setCreatedBy(employeeLoggedIn.getEmpId());
		
		if(payrollPeriodId != null && payrollPeriodId.length() > 0 && "O".equals(deductionType)){
			model.setPayrollPeriodId(Integer.parseInt(payrollPeriodId));
		}
		
		model.setPayrollCycle(payrollCycle);
		
		ISystemTrailService sysTrailService = new SystemTrailService();
		SystemTrail sysModel = new SystemTrail();
		
		sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
		sysModel.setModuleName("DEDUCTION");
		sysModel.setProcessDesc("Deduction Amount: " + amount
				+ " | Deduction Type: " + deductionType		
				+ " | Employee Id: " 
				);
		
		try {
			for(int i=0;i<empId.length;i++) {
				model.setEmpId(Integer.parseInt(empId[i]));
				service.saveEmpDeduction(model);
				
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
