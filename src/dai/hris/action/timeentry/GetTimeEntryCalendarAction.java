/**
 * 
 */
package dai.hris.action.timeentry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;

import dai.hris.model.CalendarObject;
import dai.hris.model.Employee;
import dai.hris.model.SystemTrail;
import dai.hris.model.TimeEntry;
import dai.hris.service.login.ISystemTrailService;
import dai.hris.service.login.SystemTrailService;
import dai.hris.service.timeentry.ITimeEntryService;
import dai.hris.service.timeentry.TimeEntryService;


/**
 * @author Ian
 *
 */
@WebServlet("/GetTimeEntryCalendarAction")
public class GetTimeEntryCalendarAction extends HttpServlet {
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
			
			List<TimeEntry> timeEntryList = new ArrayList<TimeEntry>();
	
//			List<TimeEntry> timeEntryList = service.getAllTimeEntryBySuperVisor(Integer.parseInt(superVisorId));
			
			if("ADMIN".equals(approverType) || "HRADMIN".equals(approverType)){
				//get all Leave
				timeEntryList = service.getAllTimeEntry(filter);
			} else if("SECTIONADMIN".equals(approverType)) {
				//get all Leave base on sectionId
				timeEntryList = service.getAllTimeEntryBySectionId(employeeLoggedIn.getSectionId(), filter);
			} else if("UNITADMIN".equals(approverType)) {
				//get all Leave base on unitId
				timeEntryList = service.getAllTimeEntryByUnitId(employeeLoggedIn.getUnitId(), filter);
			} else if("SUBUNITADMIN".equals(approverType)) {
				//get all Leave base on subUnitId			
				timeEntryList = service.getAllTimeEntryBySubUnitId(employeeLoggedIn.getSubUnitId(), filter);
			}
			
			
		    ISystemTrailService sysTrailService = new SystemTrailService();
			SystemTrail sysModel = new SystemTrail();
			
			sysModel.setDepartmentId(employeeLoggedIn.getSectionId());
			sysModel.setModuleName("TIME ENTRY");
			sysModel.setProcessDesc("Time Entry Calendar List By: " + employeeLoggedIn.getFirstname() + " " + employeeLoggedIn.getLastname() 
					
					);
			sysModel.setProcessType("VIEW");
			sysModel.setUserId(employeeLoggedIn.getEmpId());

			
			sysTrailService.insertSystemTrail(sysModel);
			
			
			List<CalendarObject> list = new ArrayList<CalendarObject>();
			Iterator<TimeEntry> i = timeEntryList.iterator(); 
			
			while(i.hasNext()){
				TimeEntry timeEntry = i.next();
				CalendarObject calendarObject = new CalendarObject();
				
				calendarObject.setId(Integer.toString(timeEntry.getEmpId()));
				calendarObject.setTitle(timeEntry.getEmpName());
				calendarObject.setStart(timeEntry.getScheduleDate());			
				calendarObject.setUrl("GetTimeEntryByDateAndSuperVisorAction" + "?clockDate=" + timeEntry.getScheduleDate());
				
				if(timeEntry.getShiftScheduleId() == 2000){
					calendarObject.setColor("#838389");
					calendarObject.setTextColor("white");
					calendarObject.setSequence("4");
	        	} else if(timeEntry.getShiftScheduleId() == 2001){
	        		calendarObject.setColor("#000000");
	        		calendarObject.setTextColor("white");
	        		calendarObject.setSequence("5");
	        	} else if(StringUtils.isEmpty(timeEntry.getTimeIn()) && StringUtils.isEmpty(timeEntry.getTimeOut())){
					calendarObject.setColor("red");
					calendarObject.setTextColor("white");
					calendarObject.setSequence("1");
				} else if(StringUtils.isEmpty(timeEntry.getTimeIn()) || StringUtils.isEmpty(timeEntry.getTimeOut())){
					calendarObject.setColor("#9900FF");
					calendarObject.setTextColor("white");
					calendarObject.setSequence("2");
				} else if(StringUtils.isEmpty(timeEntry.getShiftScheduleDesc())){
					calendarObject.setColor("#1874CD");
					calendarObject.setTextColor("white");
					calendarObject.setSequence("3");
				} else {
					calendarObject.setColor("green");
					calendarObject.setTextColor("white");
					calendarObject.setSequence("6");
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
