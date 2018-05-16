/**
 * 
 */
package dai.hris.action.filemanager.leave.sv.approval;


import java.sql.SQLException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dai.hris.model.Employee;
import dai.hris.model.Leave;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.employee.EmployeeService;
import dai.hris.service.filemanager.employee.IEmployeeService;
import dai.hris.service.filemanager.leave.ILeaveService;
import dai.hris.service.filemanager.leave.LeaveService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;


@WebServlet("/UpdateLeaveHRApprovalAction")
public class UpdateLeaveHRApprovalAction extends HttpServlet {

	private static final long serialVersionUID = 1L;
	ILeaveService leaveService = new LeaveService();
	IEmployeeService employeeService = new EmployeeService();
	Gson gson = new Gson();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
//		HttpSession session = request.getSession(true);   				
		Employee emp = (Employee)request.getSession().getAttribute("employeeLoggedIn");
		//String resolutionCategory = request.getParameter("resolutionCategory");		
		String leaveId = request.getParameter("leaveId");
		String status = request.getParameter("status");
		ILeaveService leaveService = new LeaveService();        
        
		try {
			
			Leave param = new Leave();
			
			param.setApprovedBy(emp.getEmpId());
			param.setLeaveId(Integer.parseInt(leaveId));
			param.setStatus(Integer.parseInt(status));
			
			
			leaveService.updateLeaveStatusHR(param);
			
			Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
			
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("LEAVE");
			sysModel.setProcessDesc("APPROVE LEAVE");
			sysModel.setProcessType("APPROVE");
			sysModel.setUserId(employeeLoggedIn.getEmpId());

			
			sysTrailService.insertSystemTrail(sysModel);
			
	
        	
			String json = gson.toJson("YES");
			System.out.println("json: " + json);
			String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
			response.getWriter().print(jsonData);
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	


}
