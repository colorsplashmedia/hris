/**
 * 
 */
package dai.hris.action.filemanager;

import java.sql.SQLException;
import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dai.hris.model.SystemParameters;
import dai.hris.model.Employee;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.employee.EmployeeService;
import dai.hris.service.filemanager.employee.IEmployeeService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;



/**
 * @author Ian 
 *
 */
@WebServlet("/SaveApproversAction")
public class SaveApproversAction extends HttpServlet {

	private static final long serialVersionUID = 1L;
	IEmployeeService employeeService = new EmployeeService();	
	Gson gson = new Gson();
	

	/**
	 * when accessing URL, this is called
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**called on post
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		System.out.println("doPost SaveEmployeeAction");

		
		String adminIdStr = request.getParameter("adminId");
		String adminAssistantIdStr = request.getParameter("adminAssistantId");
		String hrAdminIdStr = request.getParameter("hrAdminId");
		String hrAdminAssistantIdStr = request.getParameter("hrAdminAssistantId");
		String hrAdminLiasonIdStr = request.getParameter("hrAdminLiasonId");
		String isAdminCheckedStr = request.getParameter("isAdminChecked");
		String isNightDiffContractualStr = request.getParameter("isNightDiffContractual");
		
		String regHrsStr = request.getParameter("regHrs");
		String partimeHrsStr = request.getParameter("partimeHrs");
		String contractualHrsStr = request.getParameter("contractualHrs");
		String contractualBreakHrsStr = request.getParameter("contractualBreakHrs");
		String minPayStr = request.getParameter("minPay");
		
		SystemParameters model = new SystemParameters();
		
		if(regHrsStr != null && regHrsStr.length() > 0) {
			model.setRegHrs(Integer.parseInt(regHrsStr));
		} else {
			model.setRegHrs(0);
		}
		
		if(partimeHrsStr != null && partimeHrsStr.length() > 0) {
			model.setPartimeHrs(Integer.parseInt(partimeHrsStr));
		} else {
			model.setPartimeHrs(0);
		}
		
		if(contractualHrsStr != null && contractualHrsStr.length() > 0) {
			model.setContractualHrs(Integer.parseInt(contractualHrsStr));
		} else {
			model.setContractualHrs(0);
		}
		
		if(contractualBreakHrsStr != null && contractualBreakHrsStr.length() > 0) {
			model.setContractualBreakHrs(Integer.parseInt(contractualBreakHrsStr));
		} else {
			model.setContractualBreakHrs(0);
		}
		
		if(minPayStr != null && minPayStr.length() > 0) {
//			BigDecimal bd = new BigDecimal("");
			model.setMinPay(new BigDecimal(minPayStr));
		} else {
			model.setMinPay(BigDecimal.ZERO);
		}
		
		if(adminIdStr != null && adminIdStr.length() > 0) {
			model.setAdminId(Integer.parseInt(adminIdStr));
		} else {
			model.setAdminId(0);
		}
		
		if(adminAssistantIdStr != null && adminAssistantIdStr.length() > 0) {
			model.setAdminAssistantId(Integer.parseInt(adminAssistantIdStr));
		} else {
			model.setAdminAssistantId(0);
		}
		
		if(hrAdminIdStr != null && hrAdminIdStr.length() > 0) {
			model.setHrAdminId(Integer.parseInt(hrAdminIdStr));
		} else {
			model.setHrAdminId(0);
		}
		
		if(hrAdminAssistantIdStr != null && hrAdminAssistantIdStr.length() > 0) {
			model.setHrAdminAssistantId(Integer.parseInt(hrAdminAssistantIdStr));
		} else {
			model.setHrAdminAssistantId(0);
		}
		
		if(hrAdminLiasonIdStr != null && hrAdminLiasonIdStr.length() > 0) {
			model.setHrAdminLiasonId(Integer.parseInt(hrAdminLiasonIdStr));
		} else {
			model.setHrAdminLiasonId(0);
		}
		
		model.setIsAdminChecked(isAdminCheckedStr);
		model.setIsNightDiffContractual(isNightDiffContractualStr);
       
		try {
			employeeService.saveApprovers(model);		
			
			Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
			
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("APPROVERS");
			sysModel.setProcessDesc("SAVE APPROVERS");
			sysModel.setProcessType("SAVE");
			sysModel.setUserId(employeeLoggedIn.getEmpId());

			
			sysTrailService.insertSystemTrail(sysModel);
			
			String json2 = gson.toJson(model);
			
			response.getWriter().print(json2);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}

	}
	



}
