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
import dai.hris.model.MedicareShareAllowance;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.payroll.IMedicareShareAllowanceService;
import dai.hris.service.payroll.impl.MedicareShareAllowanceService;

/**
 * Servlet implementation class SaveLongevityPayNewAction
 */
@WebServlet("/SaveMedicareShareNewAction")
public class SaveMedicareShareNewAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IMedicareShareAllowanceService service = new MedicareShareAllowanceService();
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
		String numDays = request.getParameter("numDays");	
		String ratePerDay = request.getParameter("ratePerDay");		
		String date = request.getParameter("date");		
		String remarks = request.getParameter("remarks");	
		String empId[] = request.getParameterValues("empId")[0].split(",");		
		
		MedicareShareAllowance model = new MedicareShareAllowance();
		
		
		model.setNetAmountDue(new BigDecimal(netAmountDue));
		model.setNumDays(Integer.parseInt(numDays));
		model.setRatePerDay(new BigDecimal(ratePerDay));
		model.setDate(date);
		model.setRemarks(remarks);
		model.setCreatedBy(employeeLoggedIn.getEmpId());			
		
		try {
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("MEDICARE SHARE");
			sysModel.setProcessDesc("Net Amount Due: " + model.getNetAmountDue()
					+ " | No. of Days: " + numDays
					+ " | Rate per Day: " + ratePerDay
					+ " | Date: " + date
					+ " | Remarks: " + remarks
					+ " | Employee Id: " 
					);
			
			for(int i=0;i<empId.length;i++) {
				model.setEmpId(Integer.parseInt(empId[i]));
				service.saveMedicareShare(model);
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
