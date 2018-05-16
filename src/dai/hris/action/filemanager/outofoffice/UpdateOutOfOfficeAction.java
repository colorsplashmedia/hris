/**
 * 
 */
package dai.hris.action.filemanager.outofoffice;


import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;

import dai.hris.model.Employee;
import dai.hris.model.EmployeeOutOfOffice;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.employeeoutofoffice.EmployeeOutOfOfficeService;
import dai.hris.service.filemanager.employeeoutofoffice.IEmployeeOutOfOfficeService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;


@WebServlet("/UpdateOutOfOfficeAction")
public class UpdateOutOfOfficeAction extends HttpServlet {

	private static final long serialVersionUID = 1L;
	IEmployeeOutOfOfficeService employeeOOOService = new EmployeeOutOfOfficeService();
	Gson gson = new Gson();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		System.out.println("doPost UpdateOutOfOfficeAction");

		//instead of getting all attributes manually, try to use Beanutils
		EmployeeOutOfOffice employeeOOO = new EmployeeOutOfOffice();
		HashMap<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
		  String name = (String) names.nextElement();
		  map.put(name, request.getParameterValues(name));
		}
		try {
		    
			BeanUtils.populate(employeeOOO, map);
			
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		try {

			employeeOOOService.update(employeeOOO);
			//TODO We need to set the leaveId generated to the Leave object, before returning model object as json data
			//do not set leaveId anymore since it is update
			//leave.setLeaveId(leaveId);
			
			Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
			
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("OUTOFOFFICE");
			sysModel.setProcessDesc("UPDATE OUTOFOFFICE");
			sysModel.setProcessType("UPDATE");
			sysModel.setUserId(employeeLoggedIn.getEmpId());

			
			sysTrailService.insertSystemTrail(sysModel);
			
			String json = gson.toJson(employeeOOO);
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
