/**
 * 
 */
package dai.hris.action.timeentry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dai.hris.model.CalendarObject;
import dai.hris.model.Employee;
import dai.hris.model.EmployeeSchedule;
import dai.hris.model.SystemTrail;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.timeentry.ITimeEntryService;
import dai.hris.service.timeentry.TimeEntryService;


/**
 * @author rj
 *
 */
@WebServlet("/GetEmployeeScheduleCalendarAction")
public class GetEmployeeScheduleCalendarAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		String superVisorId = "";
//		if (null != request.getSession().getAttribute("employeeLoggedIn")) {
//			superVisorId = String.valueOf(((Employee)request.getSession().getAttribute("employeeLoggedIn")).getEmpId());
//		} else {
//			superVisorId = request.getParameter("superVisorId");
//		}
		
		String filter = "";
		
		filter = request.getParameter("personnelType");
		
		if(request.getParameter("personnelType") != null && request.getParameter("personnelType").length() > 0){
			filter = request.getParameter("personnelType");
		}
		
		
		String approverType = request.getSession().getAttribute("approverType").toString();
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		
		ITimeEntryService service = new TimeEntryService();
		
		try {
	
			List<EmployeeSchedule> empSchedList = new ArrayList<EmployeeSchedule>();
			
			if("ADMIN".equals(approverType) || "HRADMIN".equals(approverType)){
				//get all Leave
				empSchedList = service.getAllEmployeeScheduleCalendar(filter);
			} else if("SECTIONADMIN".equals(approverType)) {
				//get all Leave base on sectionId
				empSchedList = service.getAllEmployeeScheduleCalendarBySectionId(employeeLoggedIn.getSectionId(), filter);
			} else if("UNITADMIN".equals(approverType)) {
				//get all Leave base on unitId
				empSchedList = service.getAllEmployeeScheduleCalendarByUnitId(employeeLoggedIn.getUnitId(), filter);
			} else if("SUBUNITADMIN".equals(approverType)) {
				//get all Leave base on subUnitId			
				empSchedList = service.getAllEmployeeScheduleCalendarBySubUnitId(employeeLoggedIn.getSubUnitId(), filter);
			}
			
			ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("EMPLOYEE SCHEDULE");
			sysModel.setProcessDesc("Viewed By: " + employeeLoggedIn.getFirstname() + " " + employeeLoggedIn.getLastname() 
					
					);
			sysModel.setProcessType("VIEW");
			sysModel.setUserId(employeeLoggedIn.getEmpId());

			
			sysTrailService.insertSystemTrail(sysModel);
			
			List<CalendarObject> list = new ArrayList<CalendarObject>();
			
			for(EmployeeSchedule empSched : empSchedList) {		
				
				CalendarObject calendarObject = new CalendarObject();
				
				calendarObject.setId(Integer.toString(empSched.getEmpId()));
				calendarObject.setTitle(empSched.getEmpName() + " " + empSched.getEmpShift());
				calendarObject.setStart(empSched.getScheduleDate());	
				if(empSched.getToDate() != null && empSched.getToDate().length() > 0) {
					calendarObject.setEnd(empSched.getToDate());
				}
				calendarObject.setUrl("employeeScheduleDetails.jsp?scheduleDate="+empSched.getScheduleDate());
				
				if(empSched.getShiftingScheduleId() == 2000){
					calendarObject.setColor("#9900FF");
	        	} else if(empSched.getShiftingScheduleId() == 2001){
	        		calendarObject.setColor("#666699");
	        	} else {
//	        		if(empSched.getUpdatedBy() > 0){
//						calendarObject.setColor("blue");
//					} else {
//						calendarObject.setColor("green");
//					}
	        		
	        		calendarObject.setColor(empSched.getColor());
	        	}
				
				
				list.add(calendarObject);
				
			}		
			
			
			String json = gson.toJson(list);
		    System.out.println("json: " + json);
	 
		    
	        response.getWriter().print(json);
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		
	}

}
