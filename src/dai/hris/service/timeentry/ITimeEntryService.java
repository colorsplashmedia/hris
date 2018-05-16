package dai.hris.service.timeentry;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import dai.hris.model.DisplayKioskTimeEntry;
import dai.hris.model.Employee;
import dai.hris.model.EmployeeHourlyAttendance;
import dai.hris.model.EmployeeSchedule;
import dai.hris.model.Resolution;
import dai.hris.model.ShiftingSchedule;
import dai.hris.model.TimeEntry;
import dai.hris.model.TimeEntryDispute;

public interface ITimeEntryService {

//	public List<TimeEntryDispute> getAllTimeEntryDisputeBySupervisorId(int empId) throws SQLException, Exception;
//	public  List<Resolution> getAllEmpScheduleChangeBySupervisorId(int empId) throws SQLException, Exception;	
//	public void resolveTimeEntryUsingScheduledTime(Resolution resolution) throws SQLException, Exception;
	
	
	//START SCHEDULE
	public List<EmployeeSchedule> getAllEmployeeScheduleForTheMonth(int id, String approverType) throws SQLException, Exception;
	public List<EmployeeSchedule> getAllEmployeeScheduleCalendar(int id, String approverType) throws SQLException, Exception;
	public List<EmployeeSchedule> getEmployeeScheduleCalendarSpecificDate(int id, String approverType, String scheduleDate) throws SQLException, Exception;
	public void disputeEmpSchedule(Resolution resolution) throws SQLException, Exception;
	public void saveEmployeeSchedule(EmployeeSchedule empSched) throws SQLException, Exception;
	public void saveEmployeeScheduleBulk(EmployeeSchedule empSched, Map<Integer, Integer> weekMap) throws SQLException, Exception;
	public void saveEmployeeScheduleUpload(EmployeeSchedule empSched) throws SQLException, Exception;
	public void addEmployeeSchedule(EmployeeSchedule empSched) throws SQLException, Exception;
	public void editEmployeeSchedule(EmployeeSchedule empSched) throws SQLException, Exception;
	public void deleteEmployeeSchedule(int empScheduleId) throws SQLException, Exception;
	public boolean checkIfCalendarHasSchedule(EmployeeSchedule empSched) throws SQLException, Exception;
	public  List<Resolution> getAllEmpScheduleChangeByEmpId(int empId) throws SQLException, Exception;
	public void approveScheduleChangeRequest(int empId, String newStatus, int empScheduleDisputeId) throws SQLException, Exception;
	public  List<Resolution> getAllEmpScheduleChange() throws SQLException, Exception;
	public  List<Resolution> getAllEmpScheduleChangeBySectionId(int sectionId) throws SQLException, Exception;
	public  List<Resolution> getAllEmpScheduleChangeByUnitId(int unitId) throws SQLException, Exception;
	public  List<Resolution> getAllEmpScheduleChangeBySubUnitId(int subUnitId) throws SQLException, Exception;
	public List<EmployeeSchedule> getAllEmployeeScheduleCalendar(String filter) throws SQLException, Exception;
	public List<EmployeeSchedule> getAllEmployeeScheduleCalendarBySectionId(int sectionId, String filter) throws SQLException, Exception;
	public List<EmployeeSchedule> getAllEmployeeScheduleCalendarByUnitId(int unitId, String filter) throws SQLException, Exception;
	public List<EmployeeSchedule> getAllEmployeeScheduleCalendarBySubUnitId(int subUnitId, String filter) throws SQLException, Exception;
	public Resolution getEmployeeScheduleDisputeById(int id) throws SQLException, Exception;	
	public ShiftingSchedule getShiftingScheduleById(int id) throws SQLException, Exception;	
	public ShiftingSchedule getEmployeeScheduleById(int id) throws SQLException, Exception;
	//END SCHEDULE
	
	//START TIMEENTRY
	public List<TimeEntryDispute> getAllTimeEntryDisputeBySupervisorAndClockDate(int resolvedBy, String clockDate) throws SQLException, Exception;
	public void resolveTimeEntryUsingAssignedTime(Resolution resolution) throws SQLException, Exception;
	public void disputeTimeEntry(Resolution resolution) throws SQLException, Exception;
	public void disputeTimeEntryBySupervisor(Resolution resolution) throws SQLException, Exception;
	public void updateTimeEntryDispute(int empId, String newStatus, int timeEntryDisputeId) throws SQLException, Exception;	
	public List<TimeEntry> getAllTimeEntryBySuperVisor(String approverType, int id, int empId) throws SQLException, Exception;
	public List<TimeEntry> getAllTimeEntryByEmpId(int empId) throws SQLException, Exception;
	public List<TimeEntry> getAllTimeEntryByMonthEmpId(int empId, int month, int year) throws SQLException, Exception;
	public List<TimeEntryDispute> getAllTimeEntryDisputeByEmpId(int empId) throws SQLException, Exception;
	public List<TimeEntryDispute> getAllTimeEntryDisputeHR() throws SQLException, Exception;	
	public List<TimeEntry> getTimeEntryByDateAndSuperVisorAction(String approverType, int id, int empId, String clockDate) throws SQLException, Exception;
	public DisplayKioskTimeEntry displayKioskTimeEntry(String sn, DisplayKioskTimeEntry display) throws Exception;
	public List<TimeEntry> getAllTimeEntryByEmpIdForDashboard(int jtStartIndex, int jtPageSize, String jtSorting, int empId) throws SQLException, Exception;
	public DisplayKioskTimeEntry logTimeInOut(String sn, DisplayKioskTimeEntry display) throws Exception;
	public List<TimeEntryDispute> getAllTimeEntryDispute() throws SQLException, Exception;
	public List<TimeEntryDispute> getAllTimeEntryDisputeBySectionId(int sectionId) throws SQLException, Exception;
	public List<TimeEntryDispute> getAllTimeEntryDisputeByUnitId(int unidId) throws SQLException, Exception;
	public List<TimeEntryDispute> getAllTimeEntryDisputeBySubUnitId(int subUnitId) throws SQLException, Exception;
	public List<TimeEntry> getAllTimeEntry(String filter) throws SQLException, Exception;
	public List<TimeEntry> getAllTimeEntryBySectionId(int sectionId, String filter) throws SQLException, Exception;
	public List<TimeEntry> getAllTimeEntryByUnitId(int unitId, String filter) throws SQLException, Exception;
	public List<TimeEntry> getAllTimeEntryBySubUnitId(int subUnitId, String filter) throws SQLException, Exception;
	public TimeEntry getTimeEntryById(int id) throws SQLException, Exception;
	public void generateTimeInAndOutForTesting ()  throws SQLException, Exception;
	//END TIMEENTRY
	
	//START EMPLOYEE LIST
	public List<Employee> getEmployeeListPerSuperVisor(int id, String filterSection) throws SQLException, Exception;
	//END EMPLOYEE LIST
			
	
	
	//START HOURLY ATTENDANCE	
	public void saveHourlyAttendance(EmployeeHourlyAttendance model, Map<Integer, Integer> weekMap) throws SQLException, Exception;
	public List<EmployeeHourlyAttendance> getAllHourlyAttendanceForApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeHourlyAttendance> getAllHourlyAttendanceForHRApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeHourlyAttendance> getAllHourlyAttendanceForApprovalBySectionId(int empId, int sectionId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeHourlyAttendance> getAllHourlyAttendanceForApprovalByUnitId(int empId, int unitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public List<EmployeeHourlyAttendance> getAllHourlyAttendanceForApprovalBySubUnitId(int empId, int subUnitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception;
	public void approveHourlyAttendance(EmployeeHourlyAttendance model) throws SQLException, Exception;
	public EmployeeHourlyAttendance getEmployeeHourlyAttendanceById(int id) throws SQLException, Exception;
	//END HOURLY ATTENDANCE
	
	public String converTimeToMilitaryTime(String timeStr);	
	public int getCount() throws SQLException, Exception;	
	public int getAllCount() throws SQLException, Exception;
	public int getAllCountBySectionId(int sectionId) throws SQLException, Exception;
	public int getAllCountByUnitId(int unitId) throws SQLException, Exception;
	public int getAllCountBySubUnitId(int subUnitId) throws SQLException, Exception;
	
	

		
	
	
	
}
