package dai.hris.action.timeentry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import dai.hris.model.EmployeeHourlyAttendance;
import dai.hris.model.Resolution;
import dai.hris.model.SystemTrail;
import dai.hris.service.filemanager.employee.EmployeeService;
import dai.hris.service.filemanager.employee.IEmployeeService;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.timeentry.ITimeEntryService;
import dai.hris.service.timeentry.TimeEntryService;
import dai.hris.model.Employee;

@WebServlet("/SaveHourlyAttendanceAction")
public class SaveHourlyAttendanceAction extends HttpServlet {
	private static final long serialVersionUID = 5398739944589430743L;

	
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
		
		ITimeEntryService service = new TimeEntryService();
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json_param = "";
        if(br != null){
            json_param = br.readLine();
        }
       
        System.out.println("json_param: " + json_param);
        
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> param = mapper.readValue(json_param, Map.class);
        String weekDay = null;        
		
		try {
			int noOfHours = Integer.parseInt(param.get("noOfHours").toString());
			int employeeId = employeeLoggedIn.getEmpId();
			String scheduleDateFrom = param.get("scheduleDateFrom").toString();
			String scheduleDateTo = param.get("scheduleDateTo").toString();
			
			ArrayList<String> weekDayList = null;
			
			Map<Integer, Integer> weekDayMap = new HashMap<Integer, Integer>();
			
			if(param.get("weekday") instanceof String){
				weekDayList = new ArrayList<String>();
				weekDay = (String)param.get("weekday");
	        	weekDayList.add(weekDay);
	        } else {
	        	weekDayList = (ArrayList<String>)param.get("weekday");
	        }
			
			for (String intDay : weekDayList) {
				weekDayMap.put(Integer.parseInt(intDay), Integer.parseInt(intDay));
			}
			
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			EmployeeHourlyAttendance model = new EmployeeHourlyAttendance();
			model.setEmpId(employeeId);
			model.setNoOfHours(noOfHours);
			model.setAttendanceDate(scheduleDateFrom);
			model.setToDate(scheduleDateTo);
			
			IEmployeeService empService = new EmployeeService();
			
			Employee emp = empService.getEmployee(employeeId);
			
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();	
			
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("EMPLOYEE HOURLY ATTENDANCE");
			sysModel.setProcessDesc("Saved By: " + employeeLoggedIn.getFirstname() + " " + employeeLoggedIn.getLastname() 
					+ " | Employee: " + emp.getFirstname() + " " + emp.getLastname() 
					+ " | No. Of Hours: " + noOfHours
					+ " | Attendance Date: " + model.getAttendanceDate()
					);
			sysModel.setProcessType("SAVE");
			sysModel.setUserId(employeeLoggedIn.getEmpId());

			
			sysTrailService.insertSystemTrail(sysModel);
			
			service.saveHourlyAttendance(model, weekDayMap);				
			
			String json = "";
			json = gson.toJson("success");			 
			json = "{\"Employee\":"+json+"}";
		    response.getWriter().print(json);
			
		} catch (SQLException e) {
			System.out.println("SaveEmployeeScheduleAction: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	
}
