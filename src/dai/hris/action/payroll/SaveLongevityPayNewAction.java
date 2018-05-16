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
import dai.hris.model.LongevityPay;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.payroll.ILongevityPayService;
import dai.hris.service.payroll.impl.LongevityPayService;

/**
 * Servlet implementation class SaveLongevityPayNewAction
 */
@WebServlet("/SaveLongevityPayNewAction")
public class SaveLongevityPayNewAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ILongevityPayService service = new LongevityPayService();
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
		
		
		String netAmountDue = request.getParameter("netAmountDue");	
		String year = request.getParameter("year");	
		String month = request.getParameter("month");			
		String remarks = request.getParameter("remarks");	
		String empId[] = request.getParameterValues("empId")[0].split(",");		
		
		LongevityPay model = new LongevityPay();
		
		
		model.setNetAmountDue(new BigDecimal(netAmountDue));
		model.setYear(Integer.parseInt(year));
		model.setMonth(Integer.parseInt(month));		
		model.setRemarks(remarks);
		model.setCreatedBy(employeeLoggedIn.getEmpId());			
		
		try {
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("LONGEVITY PAY");
			sysModel.setProcessDesc("Net Amount Due: " + model.getNetAmountDue()
					+ " | Year: " + model.getYear()
					+ " | Month: " + model.getMonth()
					+ " | Remarks: " + remarks
					+ " | Employee Id: " 
					);
			
			for(int i=0;i<empId.length;i++) {
				model.setEmpId(Integer.parseInt(empId[i]));
				service.saveLongevityPay(model);
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
