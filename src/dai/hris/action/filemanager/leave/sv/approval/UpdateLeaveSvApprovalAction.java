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
import dai.hris.service.filemanager.leave.ILeaveService;
import dai.hris.service.filemanager.leave.LeaveService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;

@WebServlet("/UpdateLeaveSvApprovalAction")
public class UpdateLeaveSvApprovalAction extends HttpServlet {

	private static final long serialVersionUID = 1L;
	ILeaveService leaveService = new LeaveService();
	
	Gson gson = new Gson();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
					

		
		String approverType = request.getSession().getAttribute("approverType").toString();
		String isAdminChecked = request.getSession().getAttribute("isAdminChecked").toString();
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		
		//String resolutionCategory = request.getParameter("resolutionCategory");		
		String leaveId = request.getParameter("leaveId");
		ILeaveService leaveService = new LeaveService();        
        
		try {
			
			Leave param = new Leave();
			
			param.setApprovedBy(employeeLoggedIn.getEmpId());
			param.setLeaveId(Integer.parseInt(leaveId));
			
			String approveFlag = request.getParameter("approveFlag");     
			//0 - FOR APPROVAL
            //1 - NOT APPROVED
            //2 - FOR UNIT SUPERVISOR APPROVAL
            //3 - FOR SECTION SUPERVISOR APPROVAL
            //4 - FOR HR APPROVAL
            //5 - FOR ADMIN APPROVAL
			//6 - APPROVED
			if("N".equals(approveFlag)){
				param.setStatus(1);
			} else {
				if("ADMIN".equals(approverType)){
					param.setStatus(6);
				} else if("HRADMIN".equals(approverType)) {
					if("Y".equals(isAdminChecked)){
						param.setStatus(5);
					} else {
						param.setStatus(6);
					}
				} else if("SECTIONADMIN".equals(approverType)) {
					param.setStatus(4);
				} else if("UNITADMIN".equals(approverType)) {
					param.setStatus(3);
				} else if("SUBUNITADMIN".equals(approverType)) {
					param.setStatus(2);
				}
			}
			
			
			leaveService.updateLeaveStatus(param);
			
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
