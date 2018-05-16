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
import dai.hris.model.SystemTrail;
import dai.hris.model.YearEndBonusCashGift;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.payroll.IYearEndBonusCashGiftService;
import dai.hris.service.payroll.impl.YearEndBonusCashGiftService;

/**
 * Servlet implementation class SaveLongevityPayNewAction
 */
@WebServlet("/SaveYearEndBonusCashGiftNewAction")
public class SaveYearEndBonusCashGiftNewAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IYearEndBonusCashGiftService service = new YearEndBonusCashGiftService();
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
		
		
		String firstHalf13thMonth = request.getParameter("firstHalf13thMonth");	
		String secondHalf13thMonth = request.getParameter("secondHalf13thMonth");	
		String firstHalfCashGift = request.getParameter("firstHalfCashGift");		
		String secondHalfCashGift = request.getParameter("secondHalfCashGift");		
		String totalYearEndBonusCashGift = request.getParameter("totalYearEndBonusCashGift");		
		String eamcCoopDeduction = request.getParameter("eamcCoopDeduction");	
		String netAmountDue = request.getParameter("netAmountDue");	
		String year = request.getParameter("year");	
		
		
		
		String empId[] = request.getParameterValues("empId")[0].split(",");		
		
		YearEndBonusCashGift model = new YearEndBonusCashGift();
		
		
		model.setNetAmountDue(new BigDecimal(netAmountDue));
		model.setYear(Integer.parseInt(year));
		model.setFirstHalf13thMonth(new BigDecimal(firstHalf13thMonth));
		model.setSecondHalf13thMonth(new BigDecimal(secondHalf13thMonth));
		model.setFirstHalfCashGift(new BigDecimal(firstHalfCashGift));
		model.setSecondHalfCashGift(new BigDecimal(secondHalfCashGift));
		model.setTotalYearEndBonusCashGift((new BigDecimal(totalYearEndBonusCashGift)));
		model.setEamcCoopDeduction((new BigDecimal(eamcCoopDeduction)));
		
		
		try {
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("YEAR END BONUS AND CASH GIFT");
			sysModel.setProcessDesc("Net Amount Due: " + model.getNetAmountDue()
					+ " | First Half 13th Month: " + firstHalf13thMonth
					+ " | Second Half 13th Month: " + secondHalf13thMonth
					+ " | First Half Cash Gift: " + firstHalfCashGift
					+ " | Second Half Cash Gift: " + secondHalfCashGift
					+ " | Total Year End Bonus Cash Gift: " + totalYearEndBonusCashGift
					+ " | Deduction: " + eamcCoopDeduction
					+ " | Year: " + year
					+ " | Employee Id: " 
					);
			
			for(int i=0;i<empId.length;i++) {
				model.setEmpId(Integer.parseInt(empId[i]));
				service.saveYearEndBonusCashGift(model);
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
