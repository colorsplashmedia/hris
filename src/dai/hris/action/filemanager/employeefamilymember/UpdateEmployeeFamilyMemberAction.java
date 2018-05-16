package dai.hris.action.filemanager.employeefamilymember;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;

import dai.hris.model.Employee;
import dai.hris.model.EmployeeFamilyMember;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.employeefamilymember.EmployeeFamilyMemberService;
import dai.hris.service.filemanager.employeefamilymember.IEmployeeFamilyMemberService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;

@WebServlet("/UpdateEmployeeFamilyMemberAction")
public class UpdateEmployeeFamilyMemberAction extends HttpServlet {

	IEmployeeFamilyMemberService service = new EmployeeFamilyMemberService();
	Gson gson = new Gson();

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost UpdateEmployeeFamilyMemberAction");
		EmployeeFamilyMember model = new EmployeeFamilyMember();
		
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
		  String name = (String) names.nextElement();
		  map.put(name, request.getParameterValues(name));
		}
		try {
			    
			BeanUtils.populate(model, map);
	
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		try {
//			int eMemId = service.update(eMem);
//			eMem.setEmpFamilyMemberId(eMemId);
//			String json = gson.toJson(eMem);
//			String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
//			System.out.println(jsonData);
//			response.getWriter().print(jsonData);	
			
			if(service.isExistUpdate(model.getName(), model.getEmpFamilyMemberId())) {
				String errorMsg = gson.toJson("Family Member already exist.");				
				String jsonData = "{\"Result\":\"ERROR\",\"Message\":" + errorMsg + "}";
				response.getWriter().print(jsonData);
			} else {
				service.update(model);
				String json = gson.toJson(model);
				System.out.println("json: " + json);
				String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
				response.getWriter().print(jsonData);
			}		
			
			
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("FAMILY MEMBER ");
			sysModel.setProcessDesc("EDIT FAMILY MEMBER");
			sysModel.setProcessType("EDIT");
			sysModel.setUserId(employeeLoggedIn.getEmpId());

			
			sysTrailService.insertSystemTrail(sysModel);
			
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
