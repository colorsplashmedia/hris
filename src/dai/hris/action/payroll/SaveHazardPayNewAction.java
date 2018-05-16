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
import dai.hris.model.HazardPay;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.payroll.IHazardPayService;
import dai.hris.service.payroll.impl.HazardPayService;

/**
 * Servlet implementation class SaveDeductionNewAction
 */
@WebServlet("/SaveHazardPayNewAction")
public class SaveHazardPayNewAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IHazardPayService service = new HazardPayService();
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
		
		String hazardPay = request.getParameter("hazardPay");	
		String eamcDeduction = request.getParameter("eamcDeduction");	
		String year = request.getParameter("year");	
		String month = request.getParameter("month");	
		String withHoldingTax = request.getParameter("withHoldingTax");			
		String netAmountDue = request.getParameter("netAmountDue");	
		String remarks = request.getParameter("remarks");	
		String empId[] = request.getParameterValues("empId")[0].split(",");		
		
		HazardPay model = new HazardPay();
		
		model.setHazardPay(new BigDecimal(hazardPay));
		model.setEamcDeduction(new BigDecimal(eamcDeduction));
		model.setWithHoldingTax(new BigDecimal(withHoldingTax));
		model.setNetAmountDue(new BigDecimal(netAmountDue));
		model.setYear(Integer.parseInt(year));
		model.setMonth(Integer.parseInt(month));		
		model.setRemarks(remarks);
		model.setCreatedBy(employeeLoggedIn.getEmpId());			
		
		try {
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("HAZARD PAY");
			sysModel.setProcessDesc("Hazard Pay Amount: " + hazardPay
					+ " | With Holding Tax: " + model.getWithHoldingTax()
					+ " | Deduction: " + eamcDeduction
					+ " | Net Amount Due: " + model.getNetAmountDue()
					+ " | Year: " + model.getYear()
					+ " | Month: " + model.getMonth()
					+ " | Employee Id: " 
					);
			
			for(int i=0;i<empId.length;i++) {
				model.setEmpId(Integer.parseInt(empId[i]));
				service.saveHazardPay(model);
				
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
