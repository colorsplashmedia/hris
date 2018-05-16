package dai.hris.action.filemanager.employeefamilymember;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dai.hris.model.Employee;
import dai.hris.model.EmployeeFamilyMember;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.employeefamilymember.EmployeeFamilyMemberService;
import dai.hris.service.filemanager.employeefamilymember.IEmployeeFamilyMemberService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;

/**
 * Servlet implementation class SaveEmployeeFamilyMember
 */
@WebServlet("/SaveEmployeeFamilyMemberAction")
public class SaveEmployeeFamilyMemberAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IEmployeeFamilyMemberService service = new EmployeeFamilyMemberService();
	Gson gson = new Gson();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveEmployeeFamilyMemberAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		EmployeeFamilyMember efm = new EmployeeFamilyMember();
		efm.setEmpId(Integer.parseInt(request.getParameter("empId")));
		efm.setContactNum(request.getParameter("contactNum"));
		efm.setAge(Integer.parseInt(request.getParameter("age")));
		efm.setGender(request.getParameter("gender"));
		efm.setName(request.getParameter("name"));
		efm.setRelation(request.getParameter("relation"));
		efm.setRemarks(request.getParameter("remarks"));	

		try {
			
			if(service.isExist(efm.getName())) {
				String errorMsg = gson.toJson("Family Member already exist.");				
				String jsonData = "{\"Result\":\"ERROR\",\"Message\":" + errorMsg + "}";
				response.getWriter().print(jsonData);
			} else {
				ISystemTrailService sysTrailService = new SystemTrailService();
				SystemTrail sysModel = new SystemTrail();
				
				sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
				sysModel.setModuleName("FAMILY MEMBER");
				sysModel.setProcessDesc("SAVE FAMILY MEMBER");
				sysModel.setProcessType("SAVE");
				sysModel.setUserId(employeeLoggedIn.getEmpId());

				
				sysTrailService.insertSystemTrail(sysModel);
				
				service.add(efm);
				String json = gson.toJson(efm);
				System.out.println("json: " + json);
				String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
				response.getWriter().print(jsonData);
			}
			
//			employeeFamilyMemberService.add(efm);
//			
//			String json = gson.toJson(efm);
//			System.out.println("json: " + json);
//			String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
//			response.getWriter().print(jsonData);	
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}

}
