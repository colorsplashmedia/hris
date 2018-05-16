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
import dai.hris.model.ProfessionalFee;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.payroll.IProfessionalFeeService;
import dai.hris.service.payroll.impl.ProfessionalFeeService;

/**
 * Servlet implementation class SaveDeductionNewAction
 */
@WebServlet("/SaveProfessionalFeeNewAction")
public class SaveProfessionalFeeNewAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IProfessionalFeeService service = new ProfessionalFeeService();
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
		
		String officialReceiptNumber = request.getParameter("officialReceiptNumber");	
		String officialReceiptDate = request.getParameter("officialReceiptDate");	
		String grossAmount = request.getParameter("grossAmount");	
		String withHoldingTax = request.getParameter("withHoldingTax");	
		String finalTax = request.getParameter("finalTax");	
		String netAmountDue = request.getParameter("netAmountDue");	
		String remarks = request.getParameter("remarks");	
		String patientId = request.getParameter("patientId");	
		String patientName = request.getParameter("patientName");	
		
		String empId[] = request.getParameterValues("empId")[0].split(",");		
		
		ProfessionalFee model = new ProfessionalFee();
		
		model.setOfficialReceiptNumber(officialReceiptNumber);
		model.setOfficialReceiptDate(officialReceiptDate);
		model.setGrossAmount(new BigDecimal(grossAmount));
		model.setWithHoldingTax(new BigDecimal(withHoldingTax));
		model.setFinalTax(new BigDecimal(finalTax));
		model.setNetAmountDue(new BigDecimal(netAmountDue));
		model.setRemarks(remarks);
		model.setCreatedBy(employeeLoggedIn.getEmpId());
		model.setPatientId(patientId);
		model.setPatientName(patientName);
		
		try {
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("PROFESSIONAL FEE");
			sysModel.setProcessDesc("Gross Amount: " + model.getGrossAmount()
					+ " | With Holding Tax: " + model.getWithHoldingTax()
					+ " | Final Tax: " + model.getFinalTax()
					+ " | Net Amount Due: " + model.getNetAmountDue()
					+ " | OR Number: " + officialReceiptNumber
					+ " | OR Date: " + officialReceiptDate
					+ " | Patiend ID: " + patientId
					+ " | Patiend Name: " + patientName
					+ " | Employee Id: " 
					);
			
			for(int i=0;i<empId.length;i++) {
				model.setEmpId(Integer.parseInt(empId[i]));
				service.saveProfessionalFee(model);
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
