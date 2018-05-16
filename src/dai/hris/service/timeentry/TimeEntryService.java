package dai.hris.service.timeentry;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import dai.hris.dao.DAOConstants;
import dai.hris.dao.filemanager.EmployeeDAO;
import dai.hris.dao.timeentry.TimeEntryDAO;
import dai.hris.model.CheckInCheckOut;
import dai.hris.model.DisplayKioskTimeEntry;
import dai.hris.model.Employee;
import dai.hris.model.EmployeeHourlyAttendance;
import dai.hris.model.EmployeeSchedule;
import dai.hris.model.Resolution;
import dai.hris.model.ShiftingSchedule;
import dai.hris.model.TimeEntry;
import dai.hris.model.TimeEntryDispute;
import dao.hris.dao.biometric.CheckInCheckOutDAO;


public class TimeEntryService implements ITimeEntryService {
	static Logger logger = Logger.getLogger(TimeEntryService.class.getClass());
	
	public TimeEntryService(){
		
	}		
	
	public void approveScheduleChangeRequest(int empId, String newStatus, int empScheduleDisputeId) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();		
		dao.approveScheduleChangeRequest(empId, newStatus, empScheduleDisputeId);		
		dao.closeConnection();
	}
	
	public List<Employee> getEmployeeListPerSuperVisor(int id, String filterSection) throws SQLException, Exception{
		EmployeeDAO dao = new EmployeeDAO();
		
		List<Employee> empList = dao.getEmployeeListBySupervisorId(id, filterSection);
		
		dao.closeConnection();
		
		return empList;
	}
	
//	public void resolveTimeEntryUsingScheduledTime(Resolution resolution) throws SQLException, Exception {
//		TimeEntryDAO dao = new TimeEntryDAO();
//		
//		dao.resolveTimeEntryUsingScheduledTime(resolution);
//		
//		dao.closeConnection();
//	}
	
	public void resolveTimeEntryUsingAssignedTime(Resolution resolution) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		
		dao.resolveTimeEntryUsingAssignedTime(resolution);
		
		dao.closeConnection();
	}
	
	public void disputeEmpSchedule(Resolution resolution) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();		
		dao.disputeEmpSchedule(resolution);		
		dao.closeConnection();
	}
	
	public void disputeTimeEntry(Resolution resolution) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();		
		dao.disputeTimeEntry(resolution);		
		dao.closeConnection();
	}
	
	public void disputeTimeEntryBySupervisor(Resolution resolution) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		
		dao.disputeTimeEntryBySupervisor(resolution);
		
		dao.closeConnection();
	}
	
	public void updateTimeEntryDispute(int empId, String newStatus, int timeEntryDisputeId) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		
		dao.updateTimeEntryDispute(empId, newStatus, timeEntryDisputeId);
		
		dao.closeConnection();
	}
	
	public void saveEmployeeSchedule(EmployeeSchedule empSched) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		
		if(dao.checkIfEmployeeHasSchedule(empSched)){
			dao.updateEmployeeSchedule(empSched);
		} else {
			dao.insertEmployeeSchedule(empSched);
		}
		
		dao.closeConnection();
		
	}
	
	
	
	public void saveEmployeeScheduleBulk(EmployeeSchedule empSched, Map<Integer, Integer> weekMap) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		
		String currentDate = empSched.getScheduleDate();
		
		Date thedate = new SimpleDateFormat("yyyy-MM-dd").parse(currentDate);
		GregorianCalendar calendar = new GregorianCalendar();
		
		while (!currentDate.equalsIgnoreCase(incrementDate(empSched.getToDate()))){
			
			calendar.setTime(thedate);			
		    
		    int day = calendar.get(Calendar.DAY_OF_WEEK);
		    
		    if(weekMap.containsKey(day)) {
		    	if(dao.checkIfEmployeeHasSchedule(empSched)){
					dao.updateEmployeeSchedule(empSched);
				} else {
					dao.insertEmployeeSchedule(empSched);
				}
		    }					
			
			currentDate = incrementDate(currentDate);
			thedate = new SimpleDateFormat("yyyy-MM-dd").parse(currentDate);
			
			empSched.setScheduleDate(currentDate);
		}
		
		dao.closeConnection();
		
	}
	
	//TODO
	public void saveHourlyAttendance(EmployeeHourlyAttendance model, Map<Integer, Integer> weekMap) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		
		String currentDate = model.getAttendanceDate();
		
		Date thedate = new SimpleDateFormat("yyyy-MM-dd").parse(currentDate);
		GregorianCalendar calendar = new GregorianCalendar();
		
		while (!currentDate.equalsIgnoreCase(incrementDate(model.getToDate()))){
			
			calendar.setTime(thedate);			
		    
		    int day = calendar.get(Calendar.DAY_OF_WEEK);
		    
		    if(weekMap.containsKey(day)) {
		    	if(dao.checkIfEmployeeHasHourlyAttendance(model)){
					dao.updateHourlyAttendance(model);
				} else {
					dao.insertHourlyAttendance(model);
				}
		    }					
			
			currentDate = incrementDate(currentDate);
			thedate = new SimpleDateFormat("yyyy-MM-dd").parse(currentDate);
			
			model.setAttendanceDate(currentDate);
		}
		
		dao.closeConnection();
		
	}
	
	
	public void saveEmployeeScheduleUpload(EmployeeSchedule empSched) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		
		String currentDate = empSched.getScheduleDate();	
		
		
		while (!currentDate.equalsIgnoreCase(incrementDate(empSched.getToDate()))){			
			
			if(dao.checkIfEmployeeHasSchedule(empSched)){
				dao.updateEmployeeSchedule(empSched);
			} else {
				dao.insertEmployeeSchedule(empSched);
			}			
			
			currentDate = incrementDate(currentDate);		
			
			empSched.setScheduleDate(currentDate);
		}
		
		dao.closeConnection();
		
	}
	
	private String incrementDate(String dateString) throws ParseException {		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		
		c.setTime(sdf.parse(dateString));		
		c.add(Calendar.DATE, 1);  // number of days to add
		
		return sdf.format(c.getTime());  // return the new date
	}
	
	private String decrementDate(String dateString) throws ParseException {	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		
		c.setTime(sdf.parse(dateString));		
		int daysToDecrement = -1;
		c.add(Calendar.DAY_OF_WEEK, daysToDecrement);
		
		return sdf.format(c.getTime());

	}
	
	private String getDatePreviousYearDays(String dateString) throws ParseException {	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		
		c.setTime(sdf.parse(dateString));		
		int daysToDecrement = -365;
		c.add(Calendar.DAY_OF_WEEK, daysToDecrement);
		
		return sdf.format(c.getTime());

	}
	
	private String getDateAdvanceYearDays(String dateString) throws ParseException {	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		
		c.setTime(sdf.parse(dateString));		
		int daysToIncrement = 365;
		c.add(Calendar.DAY_OF_WEEK, daysToIncrement);
		
		return sdf.format(c.getTime());

	}
	
	//TODO
	public void addEmployeeSchedule(EmployeeSchedule empSched) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		
		dao.insertEmployeeSchedule(empSched);
		
		dao.closeConnection();
	}
	
	//TODO
	public void editEmployeeSchedule(EmployeeSchedule empSched) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		
		dao.editEmployeeSchedule(empSched);
		
		dao.closeConnection();
	}
	
	public List<EmployeeSchedule> getAllEmployeeScheduleForTheMonth(int id, String approverType) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		List<EmployeeSchedule> empSchedList  = dao.getAllEmployeeScheduleForTheMonth(id, approverType);
		dao.closeConnection();		
		return empSchedList;
	}
	
	public List<EmployeeSchedule> getAllEmployeeScheduleCalendar(int id, String approverType) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		List<EmployeeSchedule> empSchedList  = dao.getAllEmployeeScheduleCalendar(id, approverType);
		dao.closeConnection();
		
		return empSchedList;
	}
	
	public List<EmployeeSchedule> getEmployeeScheduleCalendarSpecificDate(int id, String approverType, String scheduleDate) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		List<EmployeeSchedule> empSchedList  = dao.getEmployeeScheduleCalendarSpecificDate(id, approverType, scheduleDate);
		dao.closeConnection();
		
		return empSchedList;
	}
	
//	private CheckInCheckOut getLatestTimeEntryBiometric(String sn) throws SQLException, Exception{
//		
//		CheckInCheckOutDAO checkInCheckOutDAO = new CheckInCheckOutDAO();
//		CheckInCheckOut checkInCheckOut = checkInCheckOutDAO.getCheckInCheckOutLatestBySN(sn);
//		return checkInCheckOut;
//	
//	} 
	
	
	//New from Ian
	public DisplayKioskTimeEntry logTimeInOut(String bioMetricsSerial, DisplayKioskTimeEntry displayKioskTimeEntry) throws Exception {
		
//		SimpleDateFormat sdf_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf_yyyy_MM_dd_HH_mm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		CheckInCheckOutDAO checkInCheckOutDAO = new CheckInCheckOutDAO();
		CheckInCheckOut biometricsTimeEntry = checkInCheckOutDAO.getCheckInCheckOutLatestBySN(bioMetricsSerial);
		String empNo =  biometricsTimeEntry.getBADGENUMBER();
		
		if(empNo == null ) {
			displayKioskTimeEntry.setErrorMessage(DAOConstants.NO_BIOMETRICS_ENTRY);	
			return displayKioskTimeEntry;
		}
		
		//set readflag of biometric to Y
		checkInCheckOutDAO.updateReadFlagToY(biometricsTimeEntry.getCheckInCheckOutID());
		checkInCheckOutDAO.closeConnection();
		
		//get empId from employee
		EmployeeDAO employeeDAO = new EmployeeDAO();
		Employee employee = employeeDAO.getEmployee(empNo);
						
		if(employee == null) {
			displayKioskTimeEntry.setErrorMessage(DAOConstants.NO_EMPLOYEE_FOUND_IN_BIO);
			return displayKioskTimeEntry;
		}
		
		displayKioskTimeEntry.setFirstname(employee.getFirstname());
		displayKioskTimeEntry.setLastname(employee.getLastname());
		displayKioskTimeEntry.setEmpNo(empNo);
		displayKioskTimeEntry.setPicLoc(employee.getPicLoc());
		
		int empId = employee.getEmpId();
				
		TimeEntryDAO timeEntryDAO = new TimeEntryDAO();
		//get last time in and out of employee
		TimeEntry timeEntry = timeEntryDAO.getLastTimeInAndOutEmpId(empId);		
				
		if(timeEntry != null) {
			timeEntry.setEmpId(empId);
			//lastTimeEntry.setShiftId(shiftingSchedule.getShiftingScheduleId());
			timeEntry.setDeviceNo(biometricsTimeEntry.getSn());
			timeEntry.setVerifyCode(biometricsTimeEntry.getVerifyCode());
			
			if(timeEntry.getTimeIn() != null && timeEntry.getTimeIn().length() > 0){
				if(timeEntry.getTimeOut() != null && timeEntry.getTimeOut().length() > 0){
					//Then New Time In
					timeEntry.setTimeInTS(biometricsTimeEntry.getCHECKTIME());				
					timeEntryDAO.insertTimeIn(timeEntry);
				} else {
					//Log as Time Out
					timeEntry.setTimeOutTS(biometricsTimeEntry.getCHECKTIME());			
					timeEntryDAO.updateTimeOut(timeEntry);
				}
			}
		} else {
			timeEntry = new TimeEntry();
			
			timeEntry.setEmpId(empId);
			//lastTimeEntry.setShiftId(shiftingSchedule.getShiftingScheduleId());
			timeEntry.setDeviceNo(biometricsTimeEntry.getSn());
			timeEntry.setVerifyCode(biometricsTimeEntry.getVerifyCode());
			
			//Then New Time In
			timeEntry.setTimeInTS(biometricsTimeEntry.getCHECKTIME());				
			timeEntryDAO.insertTimeIn(timeEntry);
		}	
		
		timeEntryDAO.closeConnection();		
		
		if(timeEntry != null ) {
			if(timeEntry.getTimeInTS() != null) {
				displayKioskTimeEntry.setTimeIn(sdf_yyyy_MM_dd_HH_mm.format(timeEntry.getTimeInTS()));
			}
			if(timeEntry.getTimeOutTS() != null) {
				displayKioskTimeEntry.setTimeOut(sdf_yyyy_MM_dd_HH_mm.format(timeEntry.getTimeOutTS()));
			}
			
		}
		                                  
		return displayKioskTimeEntry;
	}
	
	//original made by Roy
	public DisplayKioskTimeEntry displayKioskTimeEntry(String sn, DisplayKioskTimeEntry displayKioskTimeEntry) throws Exception {
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
		// grace period for timein and timeout in HOURS
		
		
		CheckInCheckOutDAO checkInCheckOutDAO = new CheckInCheckOutDAO();
		
		//get timeentry in biometric
		CheckInCheckOut checkInCheckOut = checkInCheckOutDAO.getCheckInCheckOutLatestBySN(sn);
		String empNo =  checkInCheckOut.getBADGENUMBER();
		if(empNo == null ) {
			displayKioskTimeEntry.setErrorMessage(DAOConstants.NO_BIOMETRICS_ENTRY);	
			return displayKioskTimeEntry;
		}
		//set readflag of biometric to Y
		checkInCheckOutDAO.updateReadFlagToY(checkInCheckOut.getCheckInCheckOutID());
		checkInCheckOutDAO.closeConnection();

		
		//get empId from employee
		EmployeeDAO employeeDAO = new EmployeeDAO();
		Employee employee = employeeDAO.getEmployee(empNo);
		
		
		if(employee == null) {
			displayKioskTimeEntry.setErrorMessage(DAOConstants.NO_EMPLOYEE_FOUND_IN_BIO);
			return displayKioskTimeEntry;
		}
		
		displayKioskTimeEntry.setFirstname(employee.getFirstname());
		displayKioskTimeEntry.setLastname(employee.getLastname());
		displayKioskTimeEntry.setEmpNo(empNo);
		displayKioskTimeEntry.setPicLoc(employee.getPicLoc());
		
		int empId = employee.getEmpId();
		
		
		//get shift timein and timeout
		SimpleDateFormat sdf_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf_yyyy_MM_dd_HH_mm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		//TODO REMOVE CHECKING IF THERE IS SHIFT. SYSTEM SHOULD ALWAYS TAKE THE TIME IN AND OUT
		TimeEntryDAO timeEntryDAO = new TimeEntryDAO();
		ShiftingSchedule shiftingSchedule = timeEntryDAO.getShiftingScheduleByEmpIdAndShiftTimeIn(empId, checkInCheckOut.getCHECKTIME());
		if(shiftingSchedule == null) {
			
			// no shift for today;  try to get the shift of yesterday instead
			shiftingSchedule = getShiftScheduleYesterday(checkInCheckOut,
					empId, timeEntryDAO);

			if(shiftingSchedule  == null) {				
				displayKioskTimeEntry.setErrorMessage(DAOConstants.NO_SHIFT);
				return displayKioskTimeEntry;
			}
		}
		
		Calendar shiftTimeOutCalendar = Calendar.getInstance();
		Calendar shiftTimeInCalendar = Calendar.getInstance();
		
		formatShiftTimeInAndShiftTimeOut(sdf_yyyy_MM_dd,
				sdf_yyyy_MM_dd_HH_mm, shiftingSchedule, shiftTimeOutCalendar,
				shiftTimeInCalendar);
		
		//compare with shifting for timeentry in BIOMETRICS
		Date biometricsCheckinChekout= checkInCheckOut.getCHECKTIME();
		TimeEntry timeEntry = null;
		
		//check if there is in shift; if not used the shift date of yesterday
		if (biometricsCheckinChekout.before(shiftTimeOutCalendar.getTime()) && biometricsCheckinChekout.after(shiftTimeInCalendar.getTime())) {
			//do no thing
		}
		else {
			// use the shift of yesterday
			shiftingSchedule = getShiftScheduleYesterday(checkInCheckOut,
					empId, timeEntryDAO);
			if(shiftingSchedule  == null) {
				displayKioskTimeEntry.setErrorMessage(DAOConstants.NOT_IN_SHIFT);
				return displayKioskTimeEntry;				
			}
			formatShiftTimeInAndShiftTimeOut(sdf_yyyy_MM_dd,
					sdf_yyyy_MM_dd_HH_mm, shiftingSchedule, shiftTimeOutCalendar,
					shiftTimeInCalendar);
			

			
		}
		
		//check if biometrics is in shift 
		if(biometricsCheckinChekout.before(shiftTimeOutCalendar.getTime()) && biometricsCheckinChekout.after(shiftTimeInCalendar.getTime())) {
		
			//check if it has time in
			//timeEntry = timeEntryDAO.getTimeEntryByShiftTimeInDateAndEmpId(empId, shiftTimeInCalendar.getTime());
			String shiftTimeInStr =  sdf_yyyy_MM_dd_HH_mm.format(shiftTimeInCalendar.getTime());
			String shiftTimeOutStr =  sdf_yyyy_MM_dd_HH_mm.format(shiftTimeOutCalendar.getTime());
			
			
			timeEntry = timeEntryDAO.getTimeEntryByShiftTimeInDateAndEmpId(empId, shiftTimeInStr, shiftTimeOutStr);
			
			timeEntry.setEmpId(empId);
			timeEntry.setShiftId(shiftingSchedule.getShiftingScheduleId());
			timeEntry.setDeviceNo(checkInCheckOut.getSn());
			timeEntry.setVerifyCode(checkInCheckOut.getVerifyCode());

			//TODO
			//DITO Mag Insert ng Lunch In and Out
			//Problem is the logic
			if(timeEntry.getTimeInTS() == null) {
				// insert as timeIN
				timeEntry.setTimeInTS(checkInCheckOut.getCHECKTIME());				
				timeEntryDAO.insertTimeIn(timeEntry);
			}
			else{				
				//update as timeOut
				timeEntry.setTimeOutTS(checkInCheckOut.getCHECKTIME());			
				timeEntryDAO.updateTimeOut(timeEntry);
			}
			
		}
		else {
			//no longer in shift +- grace period
			timeEntryDAO.closeConnection();
			displayKioskTimeEntry.setErrorMessage(DAOConstants.NOT_IN_SHIFT);
			return displayKioskTimeEntry;			
		}
					
		timeEntryDAO.closeConnection();	
		if(timeEntry != null ) {
			if(timeEntry.getTimeInTS() != null) {
				displayKioskTimeEntry.setTimeIn(sdf_yyyy_MM_dd_HH_mm.format(timeEntry.getTimeInTS()));
			}
			if(timeEntry.getTimeOutTS() != null) {
				displayKioskTimeEntry.setTimeOut(sdf_yyyy_MM_dd_HH_mm.format(timeEntry.getTimeOutTS()));
			}
			
		}
		                                  
		return displayKioskTimeEntry;
	}

	private ShiftingSchedule getShiftScheduleYesterday(
			CheckInCheckOut checkInCheckOut, int empId,
			TimeEntryDAO timeEntryDAO) throws SQLException, Exception {
		ShiftingSchedule shiftingSchedule;
		Calendar checkinCheckoutCalendar = Calendar.getInstance();
		checkinCheckoutCalendar.setTime(checkInCheckOut.getCHECKTIME());
		checkinCheckoutCalendar.add(Calendar.DAY_OF_MONTH, -1);
		shiftingSchedule = timeEntryDAO.getShiftingScheduleByEmpIdAndShiftTimeIn(empId,checkinCheckoutCalendar.getTime());
		return shiftingSchedule;
	}

	private void formatShiftTimeInAndShiftTimeOut(SimpleDateFormat sdf_yyyy_MM_dd,
			SimpleDateFormat sdf_yyyy_MM_dd_HH_mm,
			ShiftingSchedule shiftingSchedule, Calendar shiftTimeOutCalendar,
			Calendar shiftTimeInCalendar) throws ParseException {
		String shiftTimeInStr = shiftingSchedule.getTimeIn();
		String shiftTimeOutStr = shiftingSchedule.getTimeOut();
		
		shiftTimeInStr = sdf_yyyy_MM_dd.format(shiftingSchedule.getScheduleDate()) + " " + shiftTimeInStr;
		shiftTimeOutStr = sdf_yyyy_MM_dd.format(shiftingSchedule.getScheduleDate()) + " " + shiftTimeOutStr;
		
		Date shiftTimeInDate = sdf_yyyy_MM_dd_HH_mm.parse(shiftTimeInStr);
		
		shiftTimeInCalendar.setTime(shiftTimeInDate);

		Date shiftTimeOutDate = sdf_yyyy_MM_dd_HH_mm.parse(shiftTimeOutStr);
		
		shiftTimeOutCalendar.setTime(shiftTimeOutDate);

		shiftTimeInCalendar.add(Calendar.HOUR, -DAOConstants.GRACE_PERIOD);
		shiftTimeOutCalendar.add(Calendar.HOUR, DAOConstants.GRACE_PERIOD);
		
		
		
		//check if shift is overlapping to another day
		if(shiftTimeOutCalendar.before(shiftTimeInCalendar)) {
			//add one day
			shiftTimeOutCalendar.add(Calendar.DATE, 1);	
		}
	}	
	
	//TODO Fix This (Should Show Emp Time Entry even without Schedule, Fix should get supervisor base on section, unit, subunit
	//Dito Natapos
	public  List<TimeEntry> getAllTimeEntryBySuperVisor(String approverType, int id, int empId) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		List<TimeEntry> listFinal  = new ArrayList<TimeEntry>();
		
//		List<Employee> empList = dao.getEmployeeListBySupervisorId(superVisorId);
//		Map<Integer, List<TimeEntry>> timeEntryMap = dao.getAllTimeEntryBySuperVisorMap(superVisorId);
//		Map<Integer, List<EmployeeSchedule>> employeeScheduleMap = dao.getEmployeeScheduleForTheMonthMap(superVisorId);
		//Add SuperVisor in Display
//		Map<Integer, List<EmployeeSchedule>> employeeScheduleMap2 = dao.getEmployeeScheduleForTheMonthByEmpIdMap(superVisorId);
//		if(employeeScheduleMap2 != null && employeeScheduleMap2.containsKey(superVisorId)){
//			List<EmployeeSchedule> superVisorAttendance = employeeScheduleMap2.get(superVisorId);
//		
//			employeeScheduleMap.put(superVisorId, superVisorAttendance);
//		}
		
		List<Employee> empList = new ArrayList<Employee>();
		Map<Integer, List<TimeEntry>> timeEntryMap = new HashMap<Integer, List<TimeEntry>>();
		Map<Integer, List<EmployeeSchedule>> employeeScheduleMap = new HashMap<Integer, List<EmployeeSchedule>>();
		
		if("ADMIN".equals(approverType) || "HRADMIN".equals(approverType)){
			empList = dao.getAllEmployeeList();
			timeEntryMap = dao.getAllTimeEntryMap();
			employeeScheduleMap = dao.getAllEmployeeScheduleForTheMonthMap();
		} else if("SECTIONADMIN".equals(approverType)) {
			empList = dao.getEmployeeListBySectionId(id);
			timeEntryMap = dao.getAllTimeEntryBySectionIdMap(id);
			employeeScheduleMap = dao.getAllEmployeeScheduleForTheMonthMapBySectionId(id);
		} else if("UNITADMIN".equals(approverType)) {
			empList = dao.getEmployeeListByUnitId(id);
			timeEntryMap = dao.getAllTimeEntryByUnitIdMap(id);
			employeeScheduleMap = dao.getAllEmployeeScheduleForTheMonthMapByUnitId(id);
		} else if("SUBUNITADMIN".equals(approverType)) {
			empList = dao.getEmployeeListBySubUnitId(id);
			timeEntryMap = dao.getAllTimeEntryBySubUnitIdMap(id);
			employeeScheduleMap = dao.getAllEmployeeScheduleForTheMonthMapBySubUnitId(id);
		}
		
		for(Employee employee : empList){			
			
			List<TimeEntry> timeEntryList = null;
			List<EmployeeSchedule> empSchedList = null;					
			
			if(employeeScheduleMap.containsKey(employee.getEmpId())){
				
				empSchedList = employeeScheduleMap.get(employee.getEmpId());
				
				for(EmployeeSchedule empSchedule : empSchedList) {
					
					if(timeEntryMap.containsKey(employee.getEmpId())){
						timeEntryList = timeEntryMap.get(employee.getEmpId());
						
						boolean noEntryFound = true;
						
						Map<String, TimeEntry> map2 = new HashMap<String, TimeEntry>();
						
						for(TimeEntry timeEntry : timeEntryList) {
							
							map2.put(StringUtils.isEmpty(timeEntry.getTimeIn()) ? "" : timeEntry.getTimeIn().substring(0, 10), timeEntry);
							
							if(empSchedule.getScheduleDate().equals(StringUtils.isEmpty(timeEntry.getTimeIn()) ? "" : timeEntry.getTimeIn().substring(0, 10))  && empSchedule.getShiftingScheduleId() == timeEntry.getShiftScheduleId()){
								timeEntry.setScheduleDate(timeEntry.getTimeIn().substring(0, 10));
								timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());
								listFinal.add(timeEntry);
								noEntryFound = false;
								break;
							}
						}
						
						if(noEntryFound){
							TimeEntry timeEntry = new TimeEntry();
							timeEntry.setEmpId(employee.getEmpId());
					    	timeEntry.setEmpName(employee.getLastname() + ", " + employee.getFirstname());
					    	timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
					    	timeEntry.setScheduleDate(empSchedule.getScheduleDate());
					    	timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());
					    	//timeEntry.setShiftType(map2.get(empSchedule.getScheduleDate()).getShiftType());	    
					    	//timeEntry.setTimeIn(rs.getString("timeIn"));
					    	//timeEntry.setTimeOut(rs.getString("timeOut"));
							
							listFinal.add(timeEntry);
						}
						
						
						
					} else {
						TimeEntry timeEntry = new TimeEntry();
						timeEntry.setEmpId(employee.getEmpId());
				    	timeEntry.setEmpName(employee.getLastname() + ", " + employee.getFirstname());
				    	timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
				    	timeEntry.setScheduleDate(empSchedule.getScheduleDate());
				    	timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());
				    	//timeEntry.setShiftType(map2.get(empSchedule.getScheduleDate()).getShiftType());	    
				    	//timeEntry.setTimeIn(rs.getString("timeIn"));
				    	//timeEntry.setTimeOut(rs.getString("timeOut"));
						
						listFinal.add(timeEntry);
					}				
					
				}
			}
		}
				
		
		
		dao.closeConnection();
		
		return listFinal;		
	}
	
	
	//TODO
	//TODO Fix	
	//Should Implement New Logic
	//Should compare todays date to timeEntry and Schedule
	//If both Match then log schedule and time entry
	//Else if Only Schedule the log schedule with no time entry
	//Else if only timeentry then log timeentry without schedule
	//Else of both dont match then skip date
	
	
	//? How to get 3 Months from date Today
	
	
	public List<TimeEntry> getAllTimeEntryByEmpId(int empId) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		EmployeeDAO empDAO = new EmployeeDAO();
		List<TimeEntry> listFinal = new ArrayList<TimeEntry>();

		Employee employee = empDAO.getEmployee(empId);
		Map<Integer, List<TimeEntry>> timeEntryMap = dao.getAllTimeEntryByEmpIdMap(empId);
		Map<Integer, List<EmployeeSchedule>> employeeScheduleMap = dao.getEmployeeScheduleForTheMonthByEmpIdMap(empId);

		List<TimeEntry> timeEntryList = null;
		List<EmployeeSchedule> empSchedList = null;
		
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		
		String lastDayToCheck = getDatePreviousYearDays(currentDate);
		String dayToCheck = getDateAdvanceYearDays(currentDate);
		boolean isScheduleFound = false;
		boolean isTimeEntryFound = false;
		
		while(!dayToCheck.equals(lastDayToCheck)){
			
			isScheduleFound = false;
			isTimeEntryFound = false;
			
			if (employeeScheduleMap.containsKey(employee.getEmpId()) && timeEntryMap.containsKey(employee.getEmpId())) {
				empSchedList = employeeScheduleMap.get(employee.getEmpId());
				timeEntryList = timeEntryMap.get(employee.getEmpId());
				
				TimeEntry timeEntry = new TimeEntry();
				
				timeEntry.setShiftScheduleId(0);	
				timeEntry.setShiftScheduleDesc("");
				timeEntry.setTimeIn("");
				timeEntry.setTimeOut("");
				
				for(EmployeeSchedule empSchedule : empSchedList) {
					if (dayToCheck.equals(StringUtils.isEmpty(empSchedule.getScheduleDate()) ? ""	: empSchedule.getScheduleDate())) {
						isScheduleFound = true;
						timeEntry.setScheduleDate(dayToCheck);
						timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());	
						timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
						break;
					}
				}
				
				for(TimeEntry timeEntryLoop : timeEntryList) {
					if (dayToCheck.equals(StringUtils.isEmpty(timeEntryLoop.getTimeIn()) ? ""	: timeEntryLoop.getTimeIn().substring(0, 10))) {
						isTimeEntryFound = true;
						timeEntry.setScheduleDate(dayToCheck);												
						timeEntry.setTimeIn(timeEntryLoop.getTimeIn());
						timeEntry.setTimeOut(timeEntryLoop.getTimeOut());						
						break;
					}
				}
				
				timeEntry.setEmpId(employee.getEmpId());
				timeEntry.setEmpName(employee.getLastname() + ", "	+ employee.getFirstname());
				
				if(isScheduleFound || isTimeEntryFound){
					listFinal.add(timeEntry);
				}
				
				
				
			} else if (employeeScheduleMap.containsKey(employee.getEmpId())) {
				empSchedList = employeeScheduleMap.get(employee.getEmpId());
				
				TimeEntry timeEntry = new TimeEntry();
				
				timeEntry.setShiftScheduleId(0);	
				timeEntry.setShiftScheduleDesc("");
				timeEntry.setTimeIn("");
				timeEntry.setTimeOut("");
				
				for(EmployeeSchedule empSchedule : empSchedList) {
					if (dayToCheck.equals(StringUtils.isEmpty(empSchedule.getScheduleDate()) ? ""	: empSchedule.getScheduleDate())) {
						isScheduleFound = true;
						timeEntry.setScheduleDate(dayToCheck);
						timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());	
						timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
						break;
					}
				}			
				
				timeEntry.setEmpId(employee.getEmpId());
				timeEntry.setEmpName(employee.getLastname() + ", "	+ employee.getFirstname());
				
				if(isScheduleFound){
					listFinal.add(timeEntry);
				}
				
			} else if (timeEntryMap.containsKey(employee.getEmpId())) {
				timeEntryList = timeEntryMap.get(employee.getEmpId());
				
				TimeEntry timeEntry = new TimeEntry();
				
				timeEntry.setShiftScheduleId(0);	
				timeEntry.setShiftScheduleDesc("");
				timeEntry.setTimeIn("");
				timeEntry.setTimeOut("");				
				
				
				for(TimeEntry timeEntryLoop : timeEntryList) {
					if (dayToCheck.equals(StringUtils.isEmpty(timeEntryLoop.getTimeIn()) ? ""	: timeEntryLoop.getTimeIn().substring(0, 10))) {
						isTimeEntryFound = true;
						timeEntry.setScheduleDate(dayToCheck);												
						timeEntry.setTimeIn(timeEntryLoop.getTimeIn());
						timeEntry.setTimeOut(timeEntryLoop.getTimeOut());						
						break;
					}
				}
				
				timeEntry.setEmpId(employee.getEmpId());
				timeEntry.setEmpName(employee.getLastname() + ", "	+ employee.getFirstname());
				
				if(isTimeEntryFound){
					listFinal.add(timeEntry);
				}
			}
			
			dayToCheck = decrementDate(dayToCheck);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**

		if (employeeScheduleMap.containsKey(employee.getEmpId())) {
			empSchedList = employeeScheduleMap.get(employee.getEmpId());

			for(EmployeeSchedule empSchedule : empSchedList) {

				if (timeEntryMap.containsKey(employee.getEmpId())) {
					timeEntryList = timeEntryMap.get(employee.getEmpId());

					boolean noEntryFound = true;

					Map<String, TimeEntry> map2 = new HashMap<String, TimeEntry>();

					for(TimeEntry timeEntry : timeEntryList) {

						map2.put(StringUtils.isEmpty(timeEntry.getTimeIn()) ? "" : timeEntry.getTimeIn().substring(0, 10), timeEntry);

						//if (empSchedule.getScheduleDate().equals(StringUtils.isEmpty(timeEntry.getTimeIn()) ? ""	: timeEntry.getTimeIn().substring(0, 10)) && empSchedule.getShiftingScheduleId() == timeEntry.getShiftScheduleId()) {
						if (empSchedule.getScheduleDate().equals(StringUtils.isEmpty(timeEntry.getTimeIn()) ? ""	: timeEntry.getTimeIn().substring(0, 10))) {
							timeEntry.setScheduleDate(timeEntry.getTimeIn().substring(0, 10));
							timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());
							listFinal.add(timeEntry);
							noEntryFound = false;
							break;
						}
					}

					if (noEntryFound) {
						TimeEntry timeEntry = new TimeEntry();
						timeEntry.setEmpId(employee.getEmpId());
						timeEntry.setEmpName(employee.getLastname() + ", "
								+ employee.getFirstname());
						timeEntry.setShiftScheduleDesc(empSchedule
								.getEmpShift());
						timeEntry
								.setScheduleDate(empSchedule.getScheduleDate());
						timeEntry.setShiftScheduleId(empSchedule
								.getShiftingScheduleId());
						// timeEntry.setShiftType(map2.get(empSchedule.getScheduleDate()).getShiftType());
						// timeEntry.setTimeIn(rs.getString("timeIn"));
						// timeEntry.setTimeOut(rs.getString("timeOut"));

						listFinal.add(timeEntry);
					}

				} else {
					TimeEntry timeEntry = new TimeEntry();
					timeEntry.setEmpId(employee.getEmpId());
					timeEntry.setEmpName(employee.getLastname() + ", "
							+ employee.getFirstname());
					timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
					timeEntry.setScheduleDate(empSchedule.getScheduleDate());
					timeEntry.setShiftScheduleId(empSchedule
							.getShiftingScheduleId());
					// timeEntry.setShiftType(map2.get(empSchedule.getScheduleDate()).getShiftType());
					// timeEntry.setTimeIn(rs.getString("timeIn"));
					// timeEntry.setTimeOut(rs.getString("timeOut"));

					listFinal.add(timeEntry);
				}

			}
		}
		
		*/

		empDAO.closeConnection();
		dao.closeConnection();

		return listFinal;
	}
	
	//TODO Not Working
	public  List<TimeEntry> getAllTimeEntryByMonthEmpId(int empId, int month, int year) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		EmployeeDAO empDAO = new EmployeeDAO();
		List<TimeEntry> listFinal  = new ArrayList<TimeEntry>();
		
		Employee employee = empDAO.getEmployee(empId);
		Map<Integer, List<TimeEntry>> timeEntryMap = dao.getAllTimeEntryByEmpIdMap(empId, month, year);
		Map<Integer, List<EmployeeSchedule>> employeeScheduleMap = dao.getEmployeeScheduleForTheMonthByEmpIdMap(empId, month, year);			
			
		List<TimeEntry> timeEntryList = null;
		List<EmployeeSchedule> empSchedList = null;			
			
		if(employeeScheduleMap.containsKey(employee.getEmpId())){
			empSchedList = employeeScheduleMap.get(employee.getEmpId());
			
			Iterator<EmployeeSchedule> empSchedIterator = empSchedList.iterator();
			while(empSchedIterator.hasNext()){
				EmployeeSchedule empSchedule = empSchedIterator.next();
					
				if(timeEntryMap.containsKey(employee.getEmpId())){
					timeEntryList = timeEntryMap.get(employee.getEmpId());
						
					Iterator<TimeEntry> timeEntryIterator = timeEntryList.iterator();
						
					boolean noEntryFound = true;
						
					Map<String, TimeEntry> map2 = new HashMap<String, TimeEntry>();
						
					while(timeEntryIterator.hasNext()){
						TimeEntry timeEntry = timeEntryIterator.next();
							
						map2.put(StringUtils.isEmpty(timeEntry.getTimeIn()) ? "" : timeEntry.getTimeIn().substring(0, 10), timeEntry);
							
						if(empSchedule.getScheduleDate().equals(StringUtils.isEmpty(timeEntry.getTimeIn()) ? "" : timeEntry.getTimeIn().substring(0, 10))  && empSchedule.getShiftingScheduleId() == timeEntry.getShiftScheduleId()){
							timeEntry.setScheduleDate(timeEntry.getTimeIn().substring(0, 10));
							timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());
							listFinal.add(timeEntry);
							noEntryFound = false;
							break;
						}
					}
						
					if(noEntryFound){
						TimeEntry timeEntry = new TimeEntry();
						timeEntry.setEmpId(employee.getEmpId());
				    	timeEntry.setEmpName(employee.getLastname() + ", " + employee.getFirstname());
				    	timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
				    	timeEntry.setScheduleDate(empSchedule.getScheduleDate());
				    	timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());
				    	//timeEntry.setShiftType(map2.get(empSchedule.getScheduleDate()).getShiftType());	    
				    	//timeEntry.setTimeIn(rs.getString("timeIn"));
				    	//timeEntry.setTimeOut(rs.getString("timeOut"));
							
						listFinal.add(timeEntry);
					}
						
						
						
				} else {
					TimeEntry timeEntry = new TimeEntry();
					timeEntry.setEmpId(employee.getEmpId());
			    	timeEntry.setEmpName(employee.getLastname() + ", " + employee.getFirstname());
			    	timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
			    	timeEntry.setScheduleDate(empSchedule.getScheduleDate());
			    	timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());
			    	//timeEntry.setShiftType(map2.get(empSchedule.getScheduleDate()).getShiftType());	    
			    	//timeEntry.setTimeIn(rs.getString("timeIn"));
			    	//timeEntry.setTimeOut(rs.getString("timeOut"));
						
					listFinal.add(timeEntry);
				}				
					
			}
		}
		
				
		
		empDAO.closeConnection();
		dao.closeConnection();
		
		return listFinal;		
	}
	
	
	
	public  List<TimeEntryDispute> getAllTimeEntryDisputeByEmpId(int empId) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		
		List<TimeEntryDispute> listFinal  = dao.getAllTimeEntryDisputeByEmpId(empId);		
		
		
		dao.closeConnection();
		
		return listFinal;
	}
	
//	public  List<TimeEntryDispute> getAllTimeEntryDisputeBySupervisorId(int supervisorId) throws SQLException, Exception {
//		TimeEntryDAO dao = new TimeEntryDAO();
//		
//		List<TimeEntryDispute> listFinal  = dao.getAllTimeEntryDisputeBySupervisorId(supervisorId);		
//		
//		
//		dao.closeConnection();
//		
//		return listFinal;
//	}
	
	public  List<TimeEntryDispute> getAllTimeEntryDisputeHR() throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		
		List<TimeEntryDispute> listFinal  = dao.getAllTimeEntryDisputeHR();		
		
		
		dao.closeConnection();
		
		return listFinal;
	}
	
	@Override
	public  List<TimeEntryDispute> getAllTimeEntryDisputeBySupervisorAndClockDate(int resolvedBy, String clockDate) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();		
		List<TimeEntryDispute> listFinal  = dao.getAllTimeEntryDisputeBySupervisorAndClockDate(resolvedBy, clockDate);	
		dao.closeConnection();
		
		return listFinal;
	}
	
	//IAN
	public List<TimeEntry> getTimeEntryByDateAndSuperVisorAction(String approverType, int id, int empId, String clockDate) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		List<TimeEntry> listFinal  = new ArrayList<TimeEntry>();
		List<TimeEntry> noTimeEntryAndIncompleteList  = new ArrayList<TimeEntry>();
		List<TimeEntry> completeList  = new ArrayList<TimeEntry>();
		
//		List<Employee> empList = dao.getEmployeeListBySupervisorId(superVisorId);
//		Map<Integer, List<TimeEntry>> timeEntryMap = dao.getAllTimeEntryBySuperVisorAndClockDateMap(superVisorId, clockDate);
//		Map<Integer, List<EmployeeSchedule>> employeeScheduleMap = dao.getEmployeeScheduleBySuperVisorAndClockDateMap(superVisorId, clockDate);
		
		List<Employee> empList = new ArrayList<Employee>();
		Map<Integer, List<TimeEntry>> timeEntryMap = new HashMap<Integer, List<TimeEntry>>();
		Map<Integer, List<EmployeeSchedule>> employeeScheduleMap = new HashMap<Integer, List<EmployeeSchedule>>();
		
		if("ADMIN".equals(approverType) || "HRADMIN".equals(approverType)){
			empList = dao.getAllEmployeeList();
			timeEntryMap = dao.getAllTimeEntryByClockDateMap(clockDate);
			employeeScheduleMap = dao.getAllEmployeeScheduleByClockDateMap(clockDate);
		} else if("SECTIONADMIN".equals(approverType)) {		
			empList = dao.getEmployeeListBySectionId(id);
			timeEntryMap = dao.getAllTimeEntryBySectionIdAndClockDateMap(id, clockDate);
			employeeScheduleMap = dao.getAllEmployeeScheduleByGroupAndClockDateMap(id, approverType, clockDate);
		} else if("UNITADMIN".equals(approverType)) {			
			empList = dao.getEmployeeListByUnitId(id);
			timeEntryMap = dao.getAllTimeEntryByUnitIdAndClockDateMap(id, clockDate);
			employeeScheduleMap = dao.getAllEmployeeScheduleByGroupAndClockDateMap(id, approverType, clockDate);
		} else if("SUBUNITADMIN".equals(approverType)) {			
			empList = dao.getEmployeeListBySubUnitId(id);
			timeEntryMap = dao.getAllTimeEntryBySubUnitIdAndClockDateMap(id, clockDate);
			employeeScheduleMap = dao.getAllEmployeeScheduleByGroupAndClockDateMap(id, approverType, clockDate);
		}
		
		
		Iterator<Employee> empIterator = empList.iterator();
		
		while(empIterator.hasNext()){
			Employee employee = empIterator.next();
			
			List<TimeEntry> timeEntryList = null;
			List<EmployeeSchedule> empSchedList = null;		
			
			
			if(employeeScheduleMap.containsKey(employee.getEmpId())){
				empSchedList = employeeScheduleMap.get(employee.getEmpId());
				
				Iterator<EmployeeSchedule> empSchedIterator = empSchedList.iterator();
				
				while(empSchedIterator.hasNext()){
					EmployeeSchedule empSchedule = empSchedIterator.next();
					
					//TODO
					
					if(empSchedule.getShiftingScheduleId() == 2000){
						
						TimeEntry timeEntry = new TimeEntry();
						timeEntry.setEmpId(employee.getEmpId());
				    	timeEntry.setEmpName(employee.getLastname() + ", " + employee.getFirstname());
				    	timeEntry.setShiftScheduleDesc("Paid - Rest Day");
				    	timeEntry.setScheduleDate(empSchedule.getScheduleDate());
				    	timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());
				    		    
				    	timeEntry.setTimeIn("Paid - Rest Day");
				    	timeEntry.setTimeOut("Paid - Rest Day");
						
				    	completeList.add(timeEntry);
						
		        	} else if(empSchedule.getShiftingScheduleId() == 2001){
		        		
		        		TimeEntry timeEntry = new TimeEntry();
						timeEntry.setEmpId(employee.getEmpId());
				    	timeEntry.setEmpName(employee.getLastname() + ", " + employee.getFirstname());
				    	timeEntry.setShiftScheduleDesc("Unpaid - Rest Day");
				    	timeEntry.setScheduleDate(empSchedule.getScheduleDate());
				    	timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());
				    		    
				    	timeEntry.setTimeIn("Unpaid - Rest Day");
				    	timeEntry.setTimeOut("Unpaid - Rest Day");
				    	
				    	completeList.add(timeEntry);
		        	} else {
					
						if(timeEntryMap.containsKey(employee.getEmpId())){
							timeEntryList = timeEntryMap.get(employee.getEmpId());
							
							Iterator<TimeEntry> timeEntryIterator = timeEntryList.iterator();
							
							boolean noEntryFound = true;
							
							Map<String, TimeEntry> map2 = new HashMap<String, TimeEntry>();
							
							while(timeEntryIterator.hasNext()){
								TimeEntry timeEntry = timeEntryIterator.next();
								
								map2.put(StringUtils.isEmpty(timeEntry.getTimeIn()) ? "" : timeEntry.getTimeIn().substring(0, 10), timeEntry);
								
								if(empSchedule.getScheduleDate().equals(StringUtils.isEmpty(timeEntry.getTimeIn()) ? "" : timeEntry.getTimeIn().substring(0, 10)) && empSchedule.getShiftingScheduleId() == timeEntry.getShiftScheduleId() ){
									
									if(StringUtils.isEmpty(timeEntry.getTimeOut())){
										timeEntry.setScheduleDate(timeEntry.getTimeIn().substring(0, 10));
										timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());
										timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
										noTimeEntryAndIncompleteList.add(timeEntry);
										//listFinal.add(timeEntry);
										noEntryFound = false;
										break;
									} else {
										timeEntry.setScheduleDate(timeEntry.getTimeIn().substring(0, 10));
										timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());
										timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
										completeList.add(timeEntry);
										noEntryFound = false;
										break;
									}
									
									
								}
							}
							
							if(noEntryFound){
								TimeEntry timeEntry = new TimeEntry();
								timeEntry.setEmpId(employee.getEmpId());
						    	timeEntry.setEmpName(employee.getLastname() + ", " + employee.getFirstname());
						    	timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
						    	timeEntry.setScheduleDate(empSchedule.getScheduleDate());
						    	timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());
						    	//timeEntry.setShiftType(map2.get(empSchedule.getScheduleDate()).getShiftType());	    
						    	//timeEntry.setTimeIn(rs.getString("timeIn"));
						    	//timeEntry.setTimeOut(rs.getString("timeOut"));
								
//								listFinal.add(timeEntry);
						    	noTimeEntryAndIncompleteList.add(timeEntry);
							}
							
							
							
						} else {
							TimeEntry timeEntry = new TimeEntry();
							timeEntry.setEmpId(employee.getEmpId());
					    	timeEntry.setEmpName(employee.getLastname() + ", " + employee.getFirstname());
					    	timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
					    	timeEntry.setScheduleDate(empSchedule.getScheduleDate());
					    	timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());
					    	//timeEntry.setShiftType(map2.get(empSchedule.getScheduleDate()).getShiftType());	    
					    	//timeEntry.setTimeIn(rs.getString("timeIn"));
					    	//timeEntry.setTimeOut(rs.getString("timeOut"));
							
					    	noTimeEntryAndIncompleteList.add(timeEntry);
						}	
		        	}
					
				}
			}
		}
				
		listFinal.addAll(noTimeEntryAndIncompleteList);
		listFinal.addAll(completeList);
		
		dao.closeConnection();
		
		return listFinal;
	}
	
	public void generateTimeInAndOutForTesting ()  throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
//		dao.generateTimeInAndOutForTesting();
		dao.closeConnection();
	}
	
	public String converTimeToMilitaryTime(String timeStr){
		String newTime = "";
		
		if(timeStr.equals("12:00am")){
			newTime = "00:00";
		} else if(timeStr.equals("12:30am")){
			newTime = "00:30";
		} else if(timeStr.equals("1:00am")){
			newTime = "01:00";
		} else if(timeStr.equals("1:30am")){
			newTime = "01:30";
		} else if(timeStr.equals("2:00am")){
			newTime = "02:00";
		} else if(timeStr.equals("2:30am")){
			newTime = "02:30";
		} else if(timeStr.equals("3:00am")){
			newTime = "03:00";
		} else if(timeStr.equals("3:30am")){
			newTime = "03:30";
		} else if(timeStr.equals("4:00am")){
			newTime = "04:00";
		} else if(timeStr.equals("4:30am")){
			newTime = "04:30";
		} else if(timeStr.equals("5:00am")){
			newTime = "05:00";
		} else if(timeStr.equals("5:30am")){
			newTime = "05:30";
		} else if(timeStr.equals("6:00am")){
			newTime = "06:00";
		} else if(timeStr.equals("6:30am")){
			newTime = "06:30";
		} else if(timeStr.equals("7:00am")){
			newTime = "07:00";
		} else if(timeStr.equals("7:30am")){
			newTime = "07:30";
		} else if(timeStr.equals("8:00am")){
			newTime = "08:00";
		} else if(timeStr.equals("8:30am")){
			newTime = "08:30";
		} else if(timeStr.equals("9:00am")){
			newTime = "09:00";
		} else if(timeStr.equals("9:30am")){
			newTime = "09:30";
		} else if(timeStr.equals("10:00am")){
			newTime = "10:00";
		} else if(timeStr.equals("10:30am")){
			newTime = "10:30";
		} else if(timeStr.equals("11:00am")){
			newTime = "11:00";
		} else if(timeStr.equals("11:30am")){
			newTime = "11:30";
		} else if(timeStr.equals("12:00pm")){
			newTime = "12:00";
		} else if(timeStr.equals("12:30pm")){
			newTime = "12:30";
		} else if(timeStr.equals("1:00pm")){
			newTime = "13:00";
		} else if(timeStr.equals("1:30pm")){
			newTime = "13:30";
		} else if(timeStr.equals("2:00pm")){
			newTime = "14:00";
		} else if(timeStr.equals("2:30pm")){
			newTime = "14:30";
		} else if(timeStr.equals("3:00pm")){
			newTime = "15:00";
		} else if(timeStr.equals("3:30pm")){
			newTime = "15:30";
		} else if(timeStr.equals("4:00pm")){
			newTime = "16:00";
		} else if(timeStr.equals("4:30pm")){
			newTime = "16:30";
		} else if(timeStr.equals("5:00pm")){
			newTime = "17:00";
		} else if(timeStr.equals("5:30pm")){
			newTime = "17:30";
		} else if(timeStr.equals("6:00pm")){
			newTime = "18:00";
		} else if(timeStr.equals("6:30pm")){
			newTime = "18:30";
		} else if(timeStr.equals("7:00pm")){
			newTime = "19:00";
		} else if(timeStr.equals("7:30pm")){
			newTime = "19:30";
		} else if(timeStr.equals("8:00pm")){
			newTime = "20:00";
		} else if(timeStr.equals("8:30pm")){
			newTime = "20:30";
		} else if(timeStr.equals("9:00pm")){
			newTime = "21:00";
		} else if(timeStr.equals("9:30pm")){
			newTime = "21:30";
		} else if(timeStr.equals("10:00pm")){
			newTime = "22:00";
		} else if(timeStr.equals("10:30pm")){
			newTime = "22:30";
		} else if(timeStr.equals("11:00pm")){
			newTime = "23:00";
		} else if(timeStr.equals("11:30pm")){
			newTime = "23:30";
		} 
		
		return newTime;
	}
	
	public void deleteEmployeeSchedule(int empScheduleId) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		dao.deleteEmployeeSchedule(empScheduleId);
		dao.closeConnection();		
	}
	
	public boolean checkIfCalendarHasSchedule(EmployeeSchedule empSched) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		boolean returnValue = dao.checkIfCalendarHasSchedule(empSched);
		dao.closeConnection();	
		return returnValue;
	}
	
	public int getCount() throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		int count = dao.getCount();
		dao.closeConnection();	
		return count;
	}
	
	public List<TimeEntry> getAllTimeEntryByEmpIdForDashboard(int jtStartIndex, int jtPageSize, String jtSorting, int empId) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		List<TimeEntry> list = dao.getAllTimeEntryByEmpIdForDashboard(jtStartIndex, jtPageSize, jtSorting, empId);
		dao.closeConnection();	
		return list;
	}
	
//	public  List<Resolution> getAllEmpScheduleChangeBySupervisorId(int empId) throws SQLException, Exception {
//		TimeEntryDAO dao = new TimeEntryDAO();
//		List<Resolution> list = dao.getAllEmpScheduleChangeBySupervisorId(empId);
//		dao.closeConnection();
//		return list;
//	}
	
	public  List<Resolution> getAllEmpScheduleChangeByEmpId(int empId) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		List<Resolution> list = dao.getAllEmpScheduleChangeByEmpId(empId);
		dao.closeConnection();
		return list;
	}
	
	public List<TimeEntryDispute> getAllTimeEntryDispute() throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();		
		List<TimeEntryDispute> list  = dao.getAllTimeEntryDispute();			
		dao.closeConnection();		
		return list;
	}
	public List<TimeEntryDispute> getAllTimeEntryDisputeBySectionId(int sectionId) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();		
		List<TimeEntryDispute> list  = dao.getAllTimeEntryDisputeBySectionId(sectionId);			
		dao.closeConnection();		
		return list;
	}
	public List<TimeEntryDispute> getAllTimeEntryDisputeByUnitId(int unidId) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();		
		List<TimeEntryDispute> list  = dao.getAllTimeEntryDisputeByUnitId(unidId);			
		dao.closeConnection();		
		return list;
	}
	public List<TimeEntryDispute> getAllTimeEntryDisputeBySubUnitId(int subUnitId) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();		
		List<TimeEntryDispute> list  = dao.getAllTimeEntryDisputeBySubUnitId(subUnitId);			
		dao.closeConnection();		
		return list;
	}
	
	public  List<Resolution> getAllEmpScheduleChange() throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		List<Resolution> list = dao.getAllEmpScheduleChange();
		dao.closeConnection();
		return list;
	}
	public  List<Resolution> getAllEmpScheduleChangeBySectionId(int sectionId) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		List<Resolution> list = dao.getAllEmpScheduleChangeBySectionId(sectionId);
		dao.closeConnection();
		return list;
	}
	public  List<Resolution> getAllEmpScheduleChangeByUnitId(int unitId) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		List<Resolution> list = dao.getAllEmpScheduleChangeByUnitId(unitId);
		dao.closeConnection();
		return list;
	}
	public  List<Resolution> getAllEmpScheduleChangeBySubUnitId(int subUnitId) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		List<Resolution> list = dao.getAllEmpScheduleChangeBySubUnitId(subUnitId);
		dao.closeConnection();
		return list;
	}
	
	
	//THIS IS FOR ADMIN AND HRADMIN
	public List<TimeEntry> getAllTimeEntry(String filter) throws SQLException, Exception{
		TimeEntryDAO dao = new TimeEntryDAO();
		List<TimeEntry> listFinal  = new ArrayList<TimeEntry>();
		
		List<Employee> empList = new ArrayList<Employee>();
		
		if(filter != null && filter.length() > 0){
			empList = dao.getAllEmployeeListByPersonnelType(filter);
		} else {
			empList = dao.getAllEmployeeList();
		}		
		
		Map<Integer, List<TimeEntry>> timeEntryMap = dao.getAllTimeEntryMap();
		Map<Integer, List<EmployeeSchedule>> employeeScheduleMap = dao.getAllEmployeeScheduleForTheMonthMap();

		for(Employee employee : empList){
			List<TimeEntry> timeEntryList = null;
			List<EmployeeSchedule> empSchedList = null;
			
			String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			
			String lastDayToCheck = getDatePreviousYearDays(currentDate);
			String dayToCheck = getDateAdvanceYearDays(currentDate);
			boolean isScheduleFound = false;
			boolean isTimeEntryFound = false;
			
			while(!dayToCheck.equals(lastDayToCheck)){
				
				isScheduleFound = false;
				isTimeEntryFound = false;
				
				if (employeeScheduleMap.containsKey(employee.getEmpId()) && timeEntryMap.containsKey(employee.getEmpId())) {
					empSchedList = employeeScheduleMap.get(employee.getEmpId());
					timeEntryList = timeEntryMap.get(employee.getEmpId());
					
					TimeEntry timeEntry = new TimeEntry();
					
					timeEntry.setShiftScheduleId(0);	
					timeEntry.setShiftScheduleDesc("");
					timeEntry.setTimeIn("");
					timeEntry.setTimeOut("");
					
					for(EmployeeSchedule empSchedule : empSchedList) {
						if (dayToCheck.equals(StringUtils.isEmpty(empSchedule.getScheduleDate()) ? ""	: empSchedule.getScheduleDate())) {
							isScheduleFound = true;
							timeEntry.setScheduleDate(dayToCheck);
							timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());	
							timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
							break;
						}
					}
					
					for(TimeEntry timeEntryLoop : timeEntryList) {
						if (dayToCheck.equals(StringUtils.isEmpty(timeEntryLoop.getTimeIn()) ? ""	: timeEntryLoop.getTimeIn().substring(0, 10))) {
							isTimeEntryFound = true;
							timeEntry.setScheduleDate(dayToCheck);												
							timeEntry.setTimeIn(timeEntryLoop.getTimeIn());
							timeEntry.setTimeOut(timeEntryLoop.getTimeOut());						
							break;
						}
					}
					
					timeEntry.setEmpId(employee.getEmpId());
					timeEntry.setEmpName(employee.getLastname() + ", "	+ employee.getFirstname());
					
					if(isScheduleFound || isTimeEntryFound){
						listFinal.add(timeEntry);
					}
					
					
					
				} else if (employeeScheduleMap.containsKey(employee.getEmpId())) {
					empSchedList = employeeScheduleMap.get(employee.getEmpId());
					
					TimeEntry timeEntry = new TimeEntry();
					
					timeEntry.setShiftScheduleId(0);	
					timeEntry.setShiftScheduleDesc("");
					timeEntry.setTimeIn("");
					timeEntry.setTimeOut("");
					
					for(EmployeeSchedule empSchedule : empSchedList) {
						if (dayToCheck.equals(StringUtils.isEmpty(empSchedule.getScheduleDate()) ? ""	: empSchedule.getScheduleDate())) {
							isScheduleFound = true;
							timeEntry.setScheduleDate(dayToCheck);
							timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());	
							timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
							break;
						}
					}			
					
					timeEntry.setEmpId(employee.getEmpId());
					timeEntry.setEmpName(employee.getLastname() + ", "	+ employee.getFirstname());
					
					if(isScheduleFound){
						listFinal.add(timeEntry);
					}
					
				} else if (timeEntryMap.containsKey(employee.getEmpId())) {
					timeEntryList = timeEntryMap.get(employee.getEmpId());
					
					TimeEntry timeEntry = new TimeEntry();
					
					timeEntry.setShiftScheduleId(0);	
					timeEntry.setShiftScheduleDesc("");
					timeEntry.setTimeIn("");
					timeEntry.setTimeOut("");				
					
					
					for(TimeEntry timeEntryLoop : timeEntryList) {
						if (dayToCheck.equals(StringUtils.isEmpty(timeEntryLoop.getTimeIn()) ? ""	: timeEntryLoop.getTimeIn().substring(0, 10))) {
							isTimeEntryFound = true;
							timeEntry.setScheduleDate(dayToCheck);												
							timeEntry.setTimeIn(timeEntryLoop.getTimeIn());
							timeEntry.setTimeOut(timeEntryLoop.getTimeOut());						
							break;
						}
					}
					
					timeEntry.setEmpId(employee.getEmpId());
					timeEntry.setEmpName(employee.getLastname() + ", "	+ employee.getFirstname());
					
					if(isTimeEntryFound){
						listFinal.add(timeEntry);
					}
				}
				
				dayToCheck = decrementDate(dayToCheck);
			}//while
		}			
		
		
		dao.closeConnection();
		
		return listFinal;
	}
	public List<TimeEntry> getAllTimeEntryBySectionId(int sectionId, String filter) throws SQLException, Exception{
		TimeEntryDAO dao = new TimeEntryDAO();
		List<TimeEntry> listFinal  = new ArrayList<TimeEntry>();
		
		List<Employee> empList = new ArrayList<Employee>();
		if(filter != null && filter.length() > 0){
			empList = dao.getEmployeeListBySectionIdByPersonnelType(sectionId, filter);
		} else {
			empList = dao.getEmployeeListBySectionId(sectionId);
		}
		
		Map<Integer, List<TimeEntry>> timeEntryMap = dao.getAllTimeEntryBySectionIdMap(sectionId);
		Map<Integer, List<EmployeeSchedule>> employeeScheduleMap = dao.getAllEmployeeScheduleForTheMonthMapBySectionId(sectionId);
		
		
		for(Employee employee : empList){
			List<TimeEntry> timeEntryList = null;
			List<EmployeeSchedule> empSchedList = null;
			
			String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			
			String lastDayToCheck = getDatePreviousYearDays(currentDate);
			String dayToCheck = getDateAdvanceYearDays(currentDate);
			boolean isScheduleFound = false;
			boolean isTimeEntryFound = false;
			
			while(!dayToCheck.equals(lastDayToCheck)){
				
				isScheduleFound = false;
				isTimeEntryFound = false;
				
				if (employeeScheduleMap.containsKey(employee.getEmpId()) && timeEntryMap.containsKey(employee.getEmpId())) {
					empSchedList = employeeScheduleMap.get(employee.getEmpId());
					timeEntryList = timeEntryMap.get(employee.getEmpId());
					
					TimeEntry timeEntry = new TimeEntry();
					
					timeEntry.setShiftScheduleId(0);	
					timeEntry.setShiftScheduleDesc("");
					timeEntry.setTimeIn("");
					timeEntry.setTimeOut("");
					
					for(EmployeeSchedule empSchedule : empSchedList) {
						if (dayToCheck.equals(StringUtils.isEmpty(empSchedule.getScheduleDate()) ? ""	: empSchedule.getScheduleDate())) {
							isScheduleFound = true;
							timeEntry.setScheduleDate(dayToCheck);
							timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());	
							timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
							break;
						}
					}
					
					for(TimeEntry timeEntryLoop : timeEntryList) {
						if (dayToCheck.equals(StringUtils.isEmpty(timeEntryLoop.getTimeIn()) ? ""	: timeEntryLoop.getTimeIn().substring(0, 10))) {
							isTimeEntryFound = true;
							timeEntry.setScheduleDate(dayToCheck);												
							timeEntry.setTimeIn(timeEntryLoop.getTimeIn());
							timeEntry.setTimeOut(timeEntryLoop.getTimeOut());						
							break;
						}
					}
					
					timeEntry.setEmpId(employee.getEmpId());
					timeEntry.setEmpName(employee.getLastname() + ", "	+ employee.getFirstname());
					
					if(isScheduleFound || isTimeEntryFound){
						listFinal.add(timeEntry);
					}
					
					
					
				} else if (employeeScheduleMap.containsKey(employee.getEmpId())) {
					empSchedList = employeeScheduleMap.get(employee.getEmpId());
					
					TimeEntry timeEntry = new TimeEntry();
					
					timeEntry.setShiftScheduleId(0);	
					timeEntry.setShiftScheduleDesc("");
					timeEntry.setTimeIn("");
					timeEntry.setTimeOut("");
					
					for(EmployeeSchedule empSchedule : empSchedList) {
						if (dayToCheck.equals(StringUtils.isEmpty(empSchedule.getScheduleDate()) ? ""	: empSchedule.getScheduleDate())) {
							isScheduleFound = true;
							timeEntry.setScheduleDate(dayToCheck);
							timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());	
							timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
							break;
						}
					}			
					
					timeEntry.setEmpId(employee.getEmpId());
					timeEntry.setEmpName(employee.getLastname() + ", "	+ employee.getFirstname());
					
					if(isScheduleFound){
						listFinal.add(timeEntry);
					}
					
				} else if (timeEntryMap.containsKey(employee.getEmpId())) {
					timeEntryList = timeEntryMap.get(employee.getEmpId());
					
					TimeEntry timeEntry = new TimeEntry();
					
					timeEntry.setShiftScheduleId(0);	
					timeEntry.setShiftScheduleDesc("");
					timeEntry.setTimeIn("");
					timeEntry.setTimeOut("");				
					
					
					for(TimeEntry timeEntryLoop : timeEntryList) {
						if (dayToCheck.equals(StringUtils.isEmpty(timeEntryLoop.getTimeIn()) ? ""	: timeEntryLoop.getTimeIn().substring(0, 10))) {
							isTimeEntryFound = true;
							timeEntry.setScheduleDate(dayToCheck);												
							timeEntry.setTimeIn(timeEntryLoop.getTimeIn());
							timeEntry.setTimeOut(timeEntryLoop.getTimeOut());						
							break;
						}
					}
					
					timeEntry.setEmpId(employee.getEmpId());
					timeEntry.setEmpName(employee.getLastname() + ", "	+ employee.getFirstname());
					
					if(isTimeEntryFound){
						listFinal.add(timeEntry);
					}
				}
				
				dayToCheck = decrementDate(dayToCheck);
			}//while
		}	
		
		
		dao.closeConnection();
		
		return listFinal;
	}
	public List<TimeEntry> getAllTimeEntryByUnitId(int unitId, String filter) throws SQLException, Exception{
		TimeEntryDAO dao = new TimeEntryDAO();
		List<TimeEntry> listFinal  = new ArrayList<TimeEntry>();
		
		
		List<Employee> empList = new ArrayList<Employee>();
		if(filter != null && filter.length() > 0){
			empList = dao.getEmployeeListByUnitIdByPersonnelType(unitId, filter);
		} else {
			empList = dao.getEmployeeListByUnitId(unitId);
		}
		
		Map<Integer, List<TimeEntry>> timeEntryMap = dao.getAllTimeEntryByUnitIdMap(unitId);
		Map<Integer, List<EmployeeSchedule>> employeeScheduleMap = dao.getAllEmployeeScheduleForTheMonthMapByUnitId(unitId);
		
		
		for(Employee employee : empList){
			List<TimeEntry> timeEntryList = null;
			List<EmployeeSchedule> empSchedList = null;
			
			String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			
			String lastDayToCheck = getDatePreviousYearDays(currentDate);
			String dayToCheck = getDateAdvanceYearDays(currentDate);
			boolean isScheduleFound = false;
			boolean isTimeEntryFound = false;
			
			while(!dayToCheck.equals(lastDayToCheck)){
				
				isScheduleFound = false;
				isTimeEntryFound = false;
				
				if (employeeScheduleMap.containsKey(employee.getEmpId()) && timeEntryMap.containsKey(employee.getEmpId())) {
					empSchedList = employeeScheduleMap.get(employee.getEmpId());
					timeEntryList = timeEntryMap.get(employee.getEmpId());
					
					TimeEntry timeEntry = new TimeEntry();
					
					timeEntry.setShiftScheduleId(0);	
					timeEntry.setShiftScheduleDesc("");
					timeEntry.setTimeIn("");
					timeEntry.setTimeOut("");
					
					for(EmployeeSchedule empSchedule : empSchedList) {
						if (dayToCheck.equals(StringUtils.isEmpty(empSchedule.getScheduleDate()) ? ""	: empSchedule.getScheduleDate())) {
							isScheduleFound = true;
							timeEntry.setScheduleDate(dayToCheck);
							timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());	
							timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
							break;
						}
					}
					
					for(TimeEntry timeEntryLoop : timeEntryList) {
						if (dayToCheck.equals(StringUtils.isEmpty(timeEntryLoop.getTimeIn()) ? ""	: timeEntryLoop.getTimeIn().substring(0, 10))) {
							isTimeEntryFound = true;
							timeEntry.setScheduleDate(dayToCheck);												
							timeEntry.setTimeIn(timeEntryLoop.getTimeIn());
							timeEntry.setTimeOut(timeEntryLoop.getTimeOut());						
							break;
						}
					}
					
					timeEntry.setEmpId(employee.getEmpId());
					timeEntry.setEmpName(employee.getLastname() + ", "	+ employee.getFirstname());
					
					if(isScheduleFound || isTimeEntryFound){
						listFinal.add(timeEntry);
					}
					
					
					
				} else if (employeeScheduleMap.containsKey(employee.getEmpId())) {
					empSchedList = employeeScheduleMap.get(employee.getEmpId());
					
					TimeEntry timeEntry = new TimeEntry();
					
					timeEntry.setShiftScheduleId(0);	
					timeEntry.setShiftScheduleDesc("");
					timeEntry.setTimeIn("");
					timeEntry.setTimeOut("");
					
					for(EmployeeSchedule empSchedule : empSchedList) {
						if (dayToCheck.equals(StringUtils.isEmpty(empSchedule.getScheduleDate()) ? ""	: empSchedule.getScheduleDate())) {
							isScheduleFound = true;
							timeEntry.setScheduleDate(dayToCheck);
							timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());	
							timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
							break;
						}
					}			
					
					timeEntry.setEmpId(employee.getEmpId());
					timeEntry.setEmpName(employee.getLastname() + ", "	+ employee.getFirstname());
					
					if(isScheduleFound){
						listFinal.add(timeEntry);
					}
					
				} else if (timeEntryMap.containsKey(employee.getEmpId())) {
					timeEntryList = timeEntryMap.get(employee.getEmpId());
					
					TimeEntry timeEntry = new TimeEntry();
					
					timeEntry.setShiftScheduleId(0);	
					timeEntry.setShiftScheduleDesc("");
					timeEntry.setTimeIn("");
					timeEntry.setTimeOut("");				
					
					
					for(TimeEntry timeEntryLoop : timeEntryList) {
						if (dayToCheck.equals(StringUtils.isEmpty(timeEntryLoop.getTimeIn()) ? ""	: timeEntryLoop.getTimeIn().substring(0, 10))) {
							isTimeEntryFound = true;
							timeEntry.setScheduleDate(dayToCheck);												
							timeEntry.setTimeIn(timeEntryLoop.getTimeIn());
							timeEntry.setTimeOut(timeEntryLoop.getTimeOut());						
							break;
						}
					}
					
					timeEntry.setEmpId(employee.getEmpId());
					timeEntry.setEmpName(employee.getLastname() + ", "	+ employee.getFirstname());
					
					if(isTimeEntryFound){
						listFinal.add(timeEntry);
					}
				}
				
				dayToCheck = decrementDate(dayToCheck);
			}
		}
		
		
		
		dao.closeConnection();
		
		return listFinal;
	}
	public List<TimeEntry> getAllTimeEntryBySubUnitId(int subUnitId, String filter) throws SQLException, Exception{
		TimeEntryDAO dao = new TimeEntryDAO();
		List<TimeEntry> listFinal  = new ArrayList<TimeEntry>();
				
		List<Employee> empList = new ArrayList<Employee>();
		if(filter != null && filter.length() > 0){
			empList = dao.getEmployeeListBySubUnitIdByPersonnelType(subUnitId, filter);
		} else {
			empList = dao.getEmployeeListBySubUnitId(subUnitId);
		}
		
		Map<Integer, List<TimeEntry>> timeEntryMap = dao.getAllTimeEntryBySubUnitIdMap(subUnitId);
		Map<Integer, List<EmployeeSchedule>> employeeScheduleMap = dao.getAllEmployeeScheduleForTheMonthMapBySubUnitId(subUnitId);
		
		for(Employee employee : empList){
			List<TimeEntry> timeEntryList = null;
			List<EmployeeSchedule> empSchedList = null;
			
			String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			
			String lastDayToCheck = getDatePreviousYearDays(currentDate);
			String dayToCheck = getDateAdvanceYearDays(currentDate);
			boolean isScheduleFound = false;
			boolean isTimeEntryFound = false;
			
			while(!dayToCheck.equals(lastDayToCheck)){
				
				isScheduleFound = false;
				isTimeEntryFound = false;
				
				if (employeeScheduleMap.containsKey(employee.getEmpId()) && timeEntryMap.containsKey(employee.getEmpId())) {
					empSchedList = employeeScheduleMap.get(employee.getEmpId());
					timeEntryList = timeEntryMap.get(employee.getEmpId());
					
					TimeEntry timeEntry = new TimeEntry();
					
					timeEntry.setShiftScheduleId(0);	
					timeEntry.setShiftScheduleDesc("");
					timeEntry.setTimeIn("");
					timeEntry.setTimeOut("");
					
					for(EmployeeSchedule empSchedule : empSchedList) {
						if (dayToCheck.equals(StringUtils.isEmpty(empSchedule.getScheduleDate()) ? ""	: empSchedule.getScheduleDate())) {
							isScheduleFound = true;
							timeEntry.setScheduleDate(dayToCheck);
							timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());	
							timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
							break;
						}
					}
					
					for(TimeEntry timeEntryLoop : timeEntryList) {
						if (dayToCheck.equals(StringUtils.isEmpty(timeEntryLoop.getTimeIn()) ? ""	: timeEntryLoop.getTimeIn().substring(0, 10))) {
							isTimeEntryFound = true;
							timeEntry.setScheduleDate(dayToCheck);												
							timeEntry.setTimeIn(timeEntryLoop.getTimeIn());
							timeEntry.setTimeOut(timeEntryLoop.getTimeOut());						
							break;
						}
					}
					
					timeEntry.setEmpId(employee.getEmpId());
					timeEntry.setEmpName(employee.getLastname() + ", "	+ employee.getFirstname());
					
					if(isScheduleFound || isTimeEntryFound){
						listFinal.add(timeEntry);
					}
					
					
					
				} else if (employeeScheduleMap.containsKey(employee.getEmpId())) {
					empSchedList = employeeScheduleMap.get(employee.getEmpId());
					
					TimeEntry timeEntry = new TimeEntry();
					
					timeEntry.setShiftScheduleId(0);	
					timeEntry.setShiftScheduleDesc("");
					timeEntry.setTimeIn("");
					timeEntry.setTimeOut("");
					
					for(EmployeeSchedule empSchedule : empSchedList) {
						if (dayToCheck.equals(StringUtils.isEmpty(empSchedule.getScheduleDate()) ? ""	: empSchedule.getScheduleDate())) {
							isScheduleFound = true;
							timeEntry.setScheduleDate(dayToCheck);
							timeEntry.setShiftScheduleId(empSchedule.getShiftingScheduleId());	
							timeEntry.setShiftScheduleDesc(empSchedule.getEmpShift());
							break;
						}
					}			
					
					timeEntry.setEmpId(employee.getEmpId());
					timeEntry.setEmpName(employee.getLastname() + ", "	+ employee.getFirstname());
					
					if(isScheduleFound){
						listFinal.add(timeEntry);
					}
					
				} else if (timeEntryMap.containsKey(employee.getEmpId())) {
					timeEntryList = timeEntryMap.get(employee.getEmpId());
					
					TimeEntry timeEntry = new TimeEntry();
					
					timeEntry.setShiftScheduleId(0);	
					timeEntry.setShiftScheduleDesc("");
					timeEntry.setTimeIn("");
					timeEntry.setTimeOut("");				
					
					
					for(TimeEntry timeEntryLoop : timeEntryList) {
						if (dayToCheck.equals(StringUtils.isEmpty(timeEntryLoop.getTimeIn()) ? ""	: timeEntryLoop.getTimeIn().substring(0, 10))) {
							isTimeEntryFound = true;
							timeEntry.setScheduleDate(dayToCheck);												
							timeEntry.setTimeIn(timeEntryLoop.getTimeIn());
							timeEntry.setTimeOut(timeEntryLoop.getTimeOut());						
							break;
						}
					}
					
					timeEntry.setEmpId(employee.getEmpId());
					timeEntry.setEmpName(employee.getLastname() + ", "	+ employee.getFirstname());
					
					if(isTimeEntryFound){
						listFinal.add(timeEntry);
					}
				}
				
				dayToCheck = decrementDate(dayToCheck);
			}
		}
		
		
		
		dao.closeConnection();
		
		return listFinal;
	}
	
	public List<EmployeeSchedule> getAllEmployeeScheduleCalendar(String filter) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		List<EmployeeSchedule> empSchedList  = dao.getAllEmployeeScheduleCalendar(filter);
		dao.closeConnection();		
		return empSchedList;
	}
	public List<EmployeeSchedule> getAllEmployeeScheduleCalendarBySectionId(int sectionId, String filter) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		List<EmployeeSchedule> empSchedList  = dao.getAllEmployeeScheduleCalendarBySectionId(sectionId, filter);
		dao.closeConnection();		
		return empSchedList;
	}
	public List<EmployeeSchedule> getAllEmployeeScheduleCalendarByUnitId(int unitId, String filter) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		List<EmployeeSchedule> empSchedList  = dao.getAllEmployeeScheduleCalendarByUnitId(unitId, filter);
		dao.closeConnection();		
		return empSchedList;
	}
	public List<EmployeeSchedule> getAllEmployeeScheduleCalendarBySubUnitId(int subUnitId, String filter) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		List<EmployeeSchedule> empSchedList  = dao.getAllEmployeeScheduleCalendarBySubUnitId(subUnitId, filter);
		dao.closeConnection();		
		return empSchedList;
	}
	
	//NEW
	
	@Override
	public  int getAllCount() throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		int count = dao.getAllCount();
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getAllCountBySectionId(int sectionId) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		int count = dao.getAllCountBySectionId(sectionId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getAllCountByUnitId(int unitId) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		int count = dao.getAllCountByUnitId(unitId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public  int getAllCountBySubUnitId(int subUnitId) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		int count = dao.getAllCountBySubUnitId(subUnitId);
		dao.closeConnection();
		return count;
	}
	
	@Override
	public List<EmployeeHourlyAttendance> getAllHourlyAttendanceForApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception{
		TimeEntryDAO dao = new TimeEntryDAO();
		List<EmployeeHourlyAttendance> list = dao.getAllHourlyAttendanceForApproval(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<EmployeeHourlyAttendance> getAllHourlyAttendanceForHRApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception{
		TimeEntryDAO dao = new TimeEntryDAO();
		List<EmployeeHourlyAttendance> list = dao.getAllHourlyAttendanceForHRApproval(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<EmployeeHourlyAttendance> getAllHourlyAttendanceForApprovalBySectionId(int empId, int sectionId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		List<EmployeeHourlyAttendance> list = dao.getAllHourlyAttendanceForApprovalBySectionId(empId, sectionId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<EmployeeHourlyAttendance> getAllHourlyAttendanceForApprovalByUnitId(int empId, int unitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		List<EmployeeHourlyAttendance> list = dao.getAllHourlyAttendanceForApprovalByUnitId(empId, unitId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	
	@Override
	public List<EmployeeHourlyAttendance> getAllHourlyAttendanceForApprovalBySubUnitId(int empId, int subUnitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		List<EmployeeHourlyAttendance> list = dao.getAllHourlyAttendanceForApprovalBySubUnitId(empId, subUnitId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public void approveHourlyAttendance(EmployeeHourlyAttendance model) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		dao.approveHourlyAttendance(model);
		dao.closeConnection();
	}
	
	public Resolution getEmployeeScheduleDisputeById(int id) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		Resolution model = dao.getEmployeeScheduleDisputeById(id);
		dao.closeConnection();
		return model;
	}
	public EmployeeHourlyAttendance getEmployeeHourlyAttendanceById(int id) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		EmployeeHourlyAttendance model = dao.getEmployeeHourlyAttendanceById(id);
		dao.closeConnection();
		return model;
	}
	
	public ShiftingSchedule getShiftingScheduleById(int id) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		ShiftingSchedule model = dao.getShiftingScheduleById(id);
		dao.closeConnection();
		return model;
	}
	
	public TimeEntry getTimeEntryById(int id) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		TimeEntry model = dao.getTimeEntryById(id);
		dao.closeConnection();
		return model;
	}
	
	public ShiftingSchedule getEmployeeScheduleById(int id) throws SQLException, Exception {
		TimeEntryDAO dao = new TimeEntryDAO();
		ShiftingSchedule model = dao.getEmployeeScheduleById(id);
		dao.closeConnection();
		return model;
	}
	
	
}

