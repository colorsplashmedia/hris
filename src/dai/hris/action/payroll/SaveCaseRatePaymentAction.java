package dai.hris.action.payroll;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import com.google.gson.Gson;
import dai.hris.model.CaseRatePayment;
import dai.hris.model.Employee;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.payroll.impl.CaseRatePaymentService;

@WebServlet("/SaveCaseRatePaymentAction")
public class SaveCaseRatePaymentAction extends HttpServlet {
	private static final long serialVersionUID = 5398739944589430743L;

	private CaseRatePaymentService service = new CaseRatePaymentService();
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
			
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		try {
			
			CaseRatePayment model = new CaseRatePayment();
			try {
				BeanUtils.populate(model, request.getParameterMap());
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
					
			
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("CASE RATE");
			sysModel.setProcessDesc("Saved By: " + employeeLoggedIn.getFirstname() + " " + employeeLoggedIn.getLastname() 
					
					);
			sysModel.setProcessType("SAVE");
			sysModel.setUserId(employeeLoggedIn.getEmpId());

			
			sysTrailService.insertSystemTrail(sysModel);

			service.saveOrUpdate(model);
			
			String json = gson.toJson(model);
			System.out.println("json: " + json);
			String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
			response.getWriter().print(jsonData);
			
		} catch (SQLException e) {
			e.printStackTrace();		
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}
