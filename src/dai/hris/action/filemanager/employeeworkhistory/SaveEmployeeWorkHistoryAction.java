package dai.hris.action.filemanager.employeeworkhistory;


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
import dai.hris.model.EmployeeWorkHistory;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.employeeworkhistory.EmployeeWorkHistoryService;
import dai.hris.service.filemanager.employeeworkhistory.IEmployeeWorkHistoryService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;

/**
 * Servlet implementation class SaveEmployeeWorkHistoryAction
 */
@WebServlet("/SaveEmployeeWorkHistoryAction")
public class SaveEmployeeWorkHistoryAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    IEmployeeWorkHistoryService service = new EmployeeWorkHistoryService();
    Gson gson = new Gson();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveEmployeeWorkHistoryAction() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		
		EmployeeWorkHistory ewh = new EmployeeWorkHistory();
		
		ewh.setEmpId(Integer.parseInt(request.getParameter("empId")));
		ewh.setEmployerName(request.getParameter("employerName"));
		ewh.setYrsService(Integer.parseInt(request.getParameter("yrsService")));
		ewh.setAddress(request.getParameter("address"));
		//ewh.setCountryId(Integer.parseInt(request.getParameter("countryId")));
		ewh.setIndustry(request.getParameter("industry"));
		ewh.setPosition(request.getParameter("position"));
		ewh.setRemarks(request.getParameter("remarks"));		
		String salary = request.getParameter("salary");
		if(salary != null){
			ewh.setSalary(new BigDecimal(salary));
		} else {
			ewh.setSalary(new BigDecimal(0));
		}
		ewh.setSalaryGrade(request.getParameter("salaryGrade"));
		ewh.setStepIncrement(request.getParameter("stepIncrement"));

		try {
//			employeeWorkHistoryService.add(ewh);		
//			
//			String json = gson.toJson(ewh);
//			System.out.println("json: " + json);
//			String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
//			response.getWriter().print(jsonData);		
			
			if(service.isExist(ewh.getEmployerName())) {
				String errorMsg = gson.toJson("Work History already exist.");				
				String jsonData = "{\"Result\":\"ERROR\",\"Message\":" + errorMsg + "}";
				response.getWriter().print(jsonData);
			} else {
				ISystemTrailService sysTrailService = new SystemTrailService();
				SystemTrail sysModel = new SystemTrail();
				
				sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
				sysModel.setModuleName("WORK HISTORY");
				sysModel.setProcessDesc("SAVE WORK HISTORY");
				sysModel.setProcessType("SAVE");
				sysModel.setUserId(employeeLoggedIn.getEmpId());

				
				sysTrailService.insertSystemTrail(sysModel);
				
				service.add(ewh);
				String json = gson.toJson(ewh);
				System.out.println("json: " + json);
				String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
				response.getWriter().print(jsonData);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
		}
	}

}
