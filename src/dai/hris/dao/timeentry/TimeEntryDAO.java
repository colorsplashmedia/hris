package dai.hris.dao.timeentry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import dai.hris.action.filemanager.util.EmployeeUtil;
import dai.hris.dao.DBConstants;
import dai.hris.model.Employee;
import dai.hris.model.EmployeeHourlyAttendance;
import dai.hris.model.EmployeeSchedule;
import dai.hris.model.Resolution;
import dai.hris.model.ShiftingSchedule;
import dai.hris.model.TimeEntry;
import dai.hris.model.TimeEntryDispute;

public class TimeEntryDAO {
	
	Connection conn = null;
	EmployeeUtil util = new EmployeeUtil();
	
	public TimeEntryDAO() {
		try {
    		
    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("TimeEntryDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("TimeEntryDAO :" + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		TimeEntryDAO  dao = new TimeEntryDAO();
		
		try {
			dao.TestClass();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void TestClass() throws SQLException, Exception {	
		StringBuffer sql = new StringBuffer();
		    	
    	sql.append("SELECT ss.shiftingScheduleId, ee.empId, ee.scheduleDate, dateadd(day, 1, ee.scheduleDate) as followingDay, ss.description, ss.timeIn, ss.timeOut ");
    	sql.append("FROM empSchedule ee, shiftingSchedule ss ");
    	sql.append("WHERE ee.shiftingScheduleId = ss.shiftingScheduleId");    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    while (rs.next()) {
	    	if(rs.getInt("shiftingScheduleId") == 1){
	    		StringBuffer sql2 = new StringBuffer();
				
				sql2.append("INSERT INTO empTimeEntry (shiftId, timeIn, timeOut, empId, inputMethodTimeIn, resolutionRemarks, resolvedBy) VALUES (");
				sql2.append(rs.getInt("shiftingScheduleId"));
				sql2.append(",'");
				sql2.append(rs.getString("scheduleDate") + " " + rs.getString("timeIn").substring(0, 10));
				sql2.append("','");
				sql2.append(rs.getString("followingDay") + " " + rs.getString("timeOut").substring(0, 10));
				sql2.append("',");
				sql2.append(rs.getInt("empId"));
				sql2.append(",9999,'");
				sql2.append("");
				sql2.append("',");
				sql2.append(52);
				sql2.append(")");
				
				System.out.print("INSERT SQL: " + sql2.toString());
		  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString());
		  		
		        
		        ps2.executeUpdate();		          
		        
		        conn.commit();
		        
		        ps2.close();
	    	} else {
	    		StringBuffer sql2 = new StringBuffer();
				
				sql2.append("INSERT INTO empTimeEntry (shiftId, timeIn, timeOut, empId, inputMethodTimeIn, resolutionRemarks, resolvedBy) VALUES (");
				sql2.append(rs.getInt("shiftingScheduleId"));
				sql2.append(",'");
				sql2.append(rs.getString("scheduleDate") + " " + rs.getString("timeIn").substring(0, 10));
				sql2.append("','");
				sql2.append(rs.getString("scheduleDate") + " " + rs.getString("timeOut").substring(0, 10));
				sql2.append("',");
				sql2.append(rs.getInt("empId"));
				sql2.append(",9999,'");
				sql2.append("");
				sql2.append("',");
				sql2.append(52);
				sql2.append(")");
				
				System.out.print("INSERT SQL: " + sql2.toString());
		  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString());
		  		
		        
		        ps2.executeUpdate();		          
		        
		        conn.commit();
		        
		        ps2.close();
	    	}
	    }	    
	    ps.close();
	    rs.close();   
	}
	
	
	/*** SCHDULE START ***/
	
	public Map<Integer, List<EmployeeSchedule>> getAllEmployeeScheduleByClockDateMap(String clockDate) throws SQLException, Exception {
 		StringBuffer sql = new StringBuffer();
 		
 		sql.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, ss.description, es.updatedBy ");
 		sql.append("FROM empSchedule es, employee e, shiftingSchedule ss ");
 		sql.append("WHERE es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId ");
 		sql.append("AND es.scheduleDate = '");
 		sql.append(clockDate);
 		sql.append("'");
 		 				
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
  		        
        ResultSet rs = ps.executeQuery();
        
        //List<EmployeeSchedule> empSchedList = new ArrayList<EmployeeSchedule>();
        Map<Integer, List<EmployeeSchedule>> map = new HashMap<Integer, List<EmployeeSchedule>>();
        
        while(rs.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs.getInt("empId"));
        	empSched.setEmpScheduleId(rs.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs.getString("lastname"));
        	empSched.setEmpShift(rs.getString("description"));
        	empSched.setUpdatedBy(rs.getInt("updatedBy"));
        	
        	
        	
        	if(map.containsKey(rs.getInt("empId"))){
	    		 map.get(rs.getInt("empId")).add(empSched);
	    	 } else {
	    		 List<EmployeeSchedule> list = new ArrayList<EmployeeSchedule>();
	    		 list.add(empSched);
	    		 map.put(rs.getInt("empId"), list);
	    	 }
        	
        }
        
        StringBuffer sql2 = new StringBuffer();
 		
 		sql2.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, es.updatedBy ");
 		sql2.append("FROM empSchedule es, employee e ");
 		sql2.append("WHERE es.empId = e.empId AND es.shiftingScheduleId in (2000, 2001) ");
 		sql2.append("AND es.scheduleDate = '");
 		sql2.append(clockDate);
 		sql2.append("'");
 		
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString());
  		
  		        
        ResultSet rs2 = ps2.executeQuery();
        
        
        
        while(rs2.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs2.getInt("empId"));
        	empSched.setEmpScheduleId(rs2.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs2.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs2.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs2.getString("lastname"));
        	
        	if(empSched.getShiftingScheduleId() == 2000){
        		empSched.setEmpShift("Paid - Rest Day");
        	} else if(empSched.getShiftingScheduleId() == 2001){
        		empSched.setEmpShift("Unpaid - Rest Day");
        	}
        	
        	empSched.setUpdatedBy(rs2.getInt("updatedBy"));
        	if(map.containsKey(rs2.getInt("empId"))){
        	
	    		 map.get(rs2.getInt("empId")).add(empSched);
	    	 } else {
	    		 List<EmployeeSchedule> list = new ArrayList<EmployeeSchedule>();
	    		 list.add(empSched);
	    		 map.put(rs2.getInt("empId"), list);
	    	 }
        	
        }
        
        ps.close();
        return map;
         
	}
	
	public Map<Integer, List<EmployeeSchedule>> getAllEmployeeScheduleByGroupAndClockDateMap(int id, String approverType, String clockDate) throws SQLException, Exception {
 		StringBuffer sql = new StringBuffer();
 		
 		sql.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, ss.description, es.updatedBy ");
 		sql.append("FROM empSchedule es, employee e, shiftingSchedule ss ");
 		sql.append("WHERE es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId ");
 		sql.append("AND es.scheduleDate = '");
 		sql.append(clockDate);
 		sql.append("'");
 		
 		if("SECTIONADMIN".equals(approverType)) {
			sql.append(" AND e.sectionId = ");
			sql.append(id);
		} else if("UNITADMIN".equals(approverType)) {
			sql.append(" AND e.unitId = ");
			sql.append(id);
		} else if("SUBUNITADMIN".equals(approverType)) {
			sql.append(" AND e.subunitId = ");
			sql.append(id);
		}
 		 				
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
  		        
        ResultSet rs = ps.executeQuery();
        
        //List<EmployeeSchedule> empSchedList = new ArrayList<EmployeeSchedule>();
        Map<Integer, List<EmployeeSchedule>> map = new HashMap<Integer, List<EmployeeSchedule>>();
        
        while(rs.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs.getInt("empId"));
        	empSched.setEmpScheduleId(rs.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs.getString("lastname"));
        	empSched.setEmpShift(rs.getString("description"));
        	empSched.setUpdatedBy(rs.getInt("updatedBy"));
        	
        	
        	
        	if(map.containsKey(rs.getInt("empId"))){
	    		 map.get(rs.getInt("empId")).add(empSched);
	    	 } else {
	    		 List<EmployeeSchedule> list = new ArrayList<EmployeeSchedule>();
	    		 list.add(empSched);
	    		 map.put(rs.getInt("empId"), list);
	    	 }
        	
        }
        
        StringBuffer sql2 = new StringBuffer();
 		
 		sql2.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, es.updatedBy ");
 		sql2.append("FROM empSchedule es, employee e ");
 		sql2.append("WHERE es.empId = e.empId AND es.shiftingScheduleId in (2000, 2001) ");
 		sql2.append("AND es.scheduleDate = '");
 		sql2.append(clockDate);
 		sql2.append("'");
 		
 		if("SECTIONADMIN".equals(approverType)) {
			sql2.append(" AND e.sectionId = ");
			sql2.append(id);
		} else if("UNITADMIN".equals(approverType)) {
			sql2.append(" AND e.unitId = ");
			sql2.append(id);
		} else if("SUBUNITADMIN".equals(approverType)) {
			sql2.append(" AND e.subunitId = ");
			sql2.append(id);
		}
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString());
  		
  		        
        ResultSet rs2 = ps2.executeQuery();
        
        
        
        while(rs2.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs2.getInt("empId"));
        	empSched.setEmpScheduleId(rs2.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs2.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs2.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs2.getString("lastname"));
        	
        	if(empSched.getShiftingScheduleId() == 2000){
        		empSched.setEmpShift("Paid - Rest Day");
        	} else if(empSched.getShiftingScheduleId() == 2001){
        		empSched.setEmpShift("Unpaid - Rest Day");
        	}
        	
        	empSched.setUpdatedBy(rs2.getInt("updatedBy"));
        	if(map.containsKey(rs2.getInt("empId"))){
        	
	    		 map.get(rs2.getInt("empId")).add(empSched);
	    	 } else {
	    		 List<EmployeeSchedule> list = new ArrayList<EmployeeSchedule>();
	    		 list.add(empSched);
	    		 map.put(rs2.getInt("empId"), list);
	    	 }
        	
        }
        
        ps.close();
        return map;
         
	}
	
	public ShiftingSchedule getEmployeeScheduleById(int id) throws SQLException, Exception {	
		String sql = "SELECT es.scheduleDate, es.shiftingScheduleId, sc.shiftType, sc.timeIn, sc.timeOut, sc.description FROM  shiftingSchedule sc, empSchedule es WHERE es.shiftingScheduleId = sc.shiftingScheduleId  AND es.empScheduleId = ? ";		
		PreparedStatement ps = conn.prepareStatement(sql);		
		ps.setInt(1, id);		
		
		ResultSet rs = ps.executeQuery();
	   
	    ShiftingSchedule model = null;
	    
	    if (rs.next()) {
	    	model = new ShiftingSchedule();
	    	model.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
	    	model.setShiftType(rs.getString("shiftType"));	    
	    	model.setTimeIn(util.sqlTimeToString(rs.getTime("timeIn")));
	    	model.setTimeOut(util.sqlTimeToString(rs.getTime("timeOut")));
	    	model.setDescription(rs.getString("description"));	    
	    	model.setTimeInTimeStamp(rs.getTimestamp("timeIn"));
	    	model.settImeOutTimestamp(rs.getTimestamp("timeOut"));
	    	model.setScheduleDate(rs.getDate("scheduleDate"));
	    }	    
	    
	    ps.close();
	    rs.close();  
	    
	    return model;     
	}
	
	
	public ShiftingSchedule getShiftingScheduleById(int id) throws SQLException, Exception {	
		String sql = "SELECT es.scheduleDate, es.shiftingScheduleId, sc.shiftType, sc.timeIn, sc.timeOut, sc.description FROM  shiftingSchedule sc, empSchedule es WHERE es.shiftingScheduleId = sc.shiftingScheduleId  AND es.shiftingScheduleId = ? ";		
		PreparedStatement ps = conn.prepareStatement(sql);		
		ps.setInt(1, id);		
		
		ResultSet rs = ps.executeQuery();
	   
	    ShiftingSchedule model = null;
	    
	    if (rs.next()) {
	    	model = new ShiftingSchedule();
	    	model.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
	    	model.setShiftType(rs.getString("shiftType"));	    
	    	model.setTimeIn(util.sqlTimeToString(rs.getTime("timeIn")));
	    	model.setTimeOut(util.sqlTimeToString(rs.getTime("timeOut")));
	    	model.setDescription(rs.getString("description"));	    
	    	model.setTimeInTimeStamp(rs.getTimestamp("timeIn"));
	    	model.settImeOutTimestamp(rs.getTimestamp("timeOut"));
	    	model.setScheduleDate(rs.getDate("scheduleDate"));
	    }	    
	    
	    ps.close();
	    rs.close();  
	    
	    return model;     
	}
	
	public String getScheduleByDateAndEmpId(int empId, String scheduleDate) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
		String scheduleDesc = "No Schedule";
    	
    	sql.append("SELECT ee.scheduleDate, ss.description ");
    	sql.append("FROM empSchedule ee, shiftingSchedule ss ");
    	sql.append("WHERE ee.shiftingScheduleId = ss.shiftingScheduleId AND ee.empId = ");  
 		sql.append(empId);
 		sql.append(" AND ee.scheduleDate = '");  
 		sql.append(scheduleDate);
    	sql.append("' ORDER BY ee.scheduleDate desc");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    if (rs.next()) {
	    	scheduleDesc = rs.getString("description");
	    }	    
	    ps.close();
	    rs.close();   
	    
	    
	    
	    return scheduleDesc;
	}
	
	public  List<Resolution> getAllEmpScheduleChangeByEmpId(int empId) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.shiftingScheduleId, ee.empScheduleDisputeId, ee.scheduleDate, ee.remarks, ee.approvedBy, ee.empId, ee.approvalStatus, (e.lastname + ',' + e.firstname) as name, ss.description ");
    	sql.append("FROM empScheduleDispute ee, employee e, shiftingSchedule ss ");
    	sql.append("WHERE ee.empId = e.empId AND ee.shiftingScheduleId = ss.shiftingScheduleId AND e.empId = ");  
 		sql.append(empId);
    	sql.append(" ORDER BY ee.scheduleDate desc");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    List<Resolution> list = new ArrayList<Resolution>();
	    
	    while (rs.next()) {
	    	Resolution model = new Resolution();
	    	
	    	model.setClockDate(rs.getString("scheduleDate"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setEmpName(rs.getString("name"));
	    	model.setResolutionRemarks(rs.getString("remarks"));
	    	model.setApprovalStatus(rs.getString("approvalStatus"));
	    	model.setScheduleDesc(rs.getString("description"));
	    	model.setOldScheduleDesc(getScheduleByDateAndEmpId(rs.getInt("empId"), rs.getString("scheduleDate")));
	    	model.setEmpScheduleDisputeId(rs.getInt("empScheduleDisputeId"));
	    	model.setShiftScheduleId(rs.getInt("shiftingScheduleId"));
	    	 
	    	list.add(model);

	    }	    
	    
	    StringBuffer sql2 = new StringBuffer();
	    
	    sql2.append("SELECT ee.shiftingScheduleId, ee.empScheduleDisputeId, ee.scheduleDate, ee.remarks, ee.approvedBy, ee.empId, ee.approvalStatus, (e.lastname + ',' + e.firstname) as name ");
    	sql2.append("FROM empScheduleDispute ee, employee e ");
    	sql2.append("WHERE ee.empId = e.empId AND ee.shiftingScheduleId in (2000, 2001, 2003) AND e.empId = ");  
 		sql2.append(empId);
    	sql2.append(" ORDER BY ee.scheduleDate desc");
 		
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString()); 		
  		        
        ResultSet rs2 = ps2.executeQuery();     
        
        
        while(rs2.next()){
        	Resolution model = new Resolution();
	    	
	    	model.setClockDate(rs2.getString("scheduleDate"));
	    	model.setEmpId(rs2.getInt("empId"));
	    	model.setEmpName(rs2.getString("name"));
	    	model.setResolutionRemarks(rs2.getString("remarks"));
	    	model.setApprovalStatus(rs2.getString("approvalStatus"));	    	
	    	model.setOldScheduleDesc(getScheduleByDateAndEmpId(rs2.getInt("empId"), rs2.getString("scheduleDate")));
	    	model.setEmpScheduleDisputeId(rs2.getInt("empScheduleDisputeId"));
	    	model.setShiftScheduleId(rs2.getInt("shiftingScheduleId"));
        	
        	if(model.getShiftScheduleId() == 2000){
        		model.setScheduleDesc("Paid - Rest Day");
        	} else if(model.getShiftScheduleId() == 2001){
        		model.setScheduleDesc("Unpaid - Rest Day");
        	} else if(model.getShiftScheduleId() == 2003){
        		model.setScheduleDesc("Remove Employee Shift");
        	}       	
        	
        	
        	list.add(model);
        	
        }
	    
	    ps.close();
	    rs.close();      
	    return list;
	}
	
	
	
	public void deleteEmployeeSchedule(int empScheduleId) throws SQLException, Exception {
		String sql = "DELETE FROM  empSchedule WHERE empScheduleId = ?";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1;
  		
  		ps.setInt(index++, empScheduleId);  		
        
        ps.executeUpdate();       
         
        conn.commit();
         
        ps.close();
	}
	
	public void insertEmployeeSchedule(EmployeeSchedule empSched) throws SQLException, Exception {
		String sql = "INSERT INTO empSchedule (shiftingScheduleId, empId, scheduleDate, createdBy, creationDate) VALUES (?,?,?,?,SYSDATETIME())";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1;
  		
        ps.setInt(index++, empSched.getShiftingScheduleId());
        ps.setInt(index++, empSched.getEmpId());
        ps.setString(index++, empSched.getScheduleDate());
        ps.setInt(index++, empSched.getSuperVisorId());
        
        ps.executeUpdate(); 
        
        conn.commit();
        
        ps.close();
        sql = null;
        
	}
	
	/*** SCHDULE END ***/
	
	/*** SCHDULE DISPUTE START ***/
	
	
	
	public Resolution getEmployeeScheduleDisputeById(int id) throws SQLException, Exception {	
		StringBuffer sql = new StringBuffer();       	
    	
		sql.append("SELECT ee.shiftingScheduleId, ee.empScheduleDisputeId, ee.scheduleDate, ee.remarks, ee.approvedBy, ee.empId, ee.approvalStatus, (e.lastname + ',' + e.firstname) as name, ss.description ");
    	sql.append("FROM empScheduleDispute ee, employee e, shiftingSchedule ss ");
    	sql.append("WHERE ee.empId = e.empId AND ee.shiftingScheduleId = ss.shiftingScheduleId AND e.empScheduleDisputeId = ");  
 		sql.append(id);    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    List<Resolution> list = new ArrayList<Resolution>();
	    
	    Resolution model = new Resolution();
	    
	    while (rs.next()) {	    	
	    	
	    	model.setClockDate(rs.getString("scheduleDate"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setEmpName(rs.getString("name"));
	    	model.setResolutionRemarks(rs.getString("remarks"));
	    	model.setApprovalStatus(rs.getString("approvalStatus"));
	    	model.setScheduleDesc(rs.getString("description"));
	    	model.setOldScheduleDesc(getScheduleByDateAndEmpId(rs.getInt("empId"), rs.getString("scheduleDate")));
	    	model.setEmpScheduleDisputeId(rs.getInt("empScheduleDisputeId"));
	    	model.setShiftScheduleId(rs.getInt("shiftingScheduleId"));
	    	 
	    	list.add(model);

	    }	
	    
	    ps.close();
	    rs.close();      
	    return model; 
	}
	
	/*** SCHDULE DISPUTE END ***/
	
	/*** HOURLY ATTENDANCE DISPUTE START ***/
	
	public EmployeeHourlyAttendance getEmployeeHourlyAttendanceById(int id) throws SQLException, Exception {	
		StringBuffer sql = new StringBuffer();       	
    	
    	sql.append("SELECT o.empHourlyAttendanceId, o.empId, o.attendanceDate, o.noOfHours, o.status, o.approvedBy, o.creationDate ");	
    	sql.append(" FROM employee e, empHourlyAttendance o WHERE e.empId = o.empId AND o.empHourlyAttendanceId = ");
    	sql.append(id);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeHourlyAttendance> list = new ArrayList<EmployeeHourlyAttendance>();
	    
	    EmployeeHourlyAttendance model = new EmployeeHourlyAttendance();
	    
	    if (rs.next()) {
	    	
	    	model.setEmpHourlyAttendanceId(rs.getInt("empHourlyAttendanceId"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAttendanceDate(rs.getString("attendanceDate"));
	    	model.setNoOfHours(rs.getInt("noOfHours"));
	    	model.setStatus(rs.getInt("status"));
	    	model.setApprovedBy(rs.getInt("approvedBy"));
	    	model.setCreatedDate(rs.getDate("creationDate"));
	    	list.add(model);
	    	
	    }
	    
	    ps.close();
	    rs.close();      
	    return model;   
	}
	
	public void insertHourlyAttendance(EmployeeHourlyAttendance model) throws SQLException, Exception {
		String sql = "INSERT INTO empHourlyAttendance (noOfHours, empId, attendanceDate, status, creationDate) VALUES (?,?,?,0,SYSDATETIME())";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1;
  		
        ps.setInt(index++, model.getNoOfHours());
        ps.setInt(index++, model.getEmpId());
        ps.setString(index++, model.getAttendanceDate());
        
        ps.executeUpdate(); 
        
        conn.commit();
        
        ps.close();
        sql = null;
        
	}
	
	/*** HOURLY ATTENDANCE DISPUTE END ***/
	
	
	
	
	/*** TIME ENTRY DISPUTE START ***/
	
	//TODO
	public TimeEntryDispute getTimeEntryDisputeById(int id) throws SQLException, Exception {	
		StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.timeEntryDisputeId, ee.approvalStatus, ee.shiftId, ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut, ss.description, ss.shiftType ");
    	sql.append("FROM empTimeEntryDispute ee, employee e, shiftingSchedule ss ");
    	sql.append("WHERE ee.empId = e.empId AND ee.shiftId = ss.shiftingScheduleId AND ee.timeEntryDisputeId = ");    	
    	sql.append(id);
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    TimeEntryDispute model = new TimeEntryDispute();
	    
	    while (rs.next()) {
	    	
	    	 model.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 model.setEmpId(rs.getInt("empId"));
	    	 model.setEmpName(rs.getString("name"));
	    	 model.setShiftScheduleDesc(rs.getString("description"));
	    	 model.setShiftType(rs.getString("shiftType"));	    
	    	 model.setTimeIn(rs.getString("timeIn"));
	    	 model.setTimeOut(rs.getString("timeOut"));
	    	 model.setShiftScheduleId(rs.getInt("shiftId"));
	    	 model.setShiftId(rs.getInt("shiftId"));
	    	 model.setScheduleDate(model.getTimeIn().substring(0, 10));
	    	 
	    	 model.setTimeEntryDisputeId(rs.getInt("timeEntryDisputeId"));
	    	 model.setApprovalStatus(rs.getString("approvalStatus"));
	    	 

	    }	    
	    ps.close();
	    rs.close();      
	    return model;
	}
	
	/*** TIME ENTRY DISPUTE END ***/
	
	
	
	/** Start Ian Code **/
	
	public TimeEntry getLastTimeInAndOutEmpId(int empId) throws SQLException, Exception {	
		
		StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT * FROM empTimeEntry WHERE empId = ");
    	sql.append(empId);
    	sql.append(" ORDER BY timein desc");     	
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());				

		ResultSet rs = ps.executeQuery();
		
		TimeEntry timeEntry = null;
		if (rs.next()) {		
			timeEntry = new TimeEntry();
			
			timeEntry.setTimeEntryId(rs.getInt("timeEntryId"));
			timeEntry.setTimeIn(rs.getString("timeIn"));
			timeEntry.setTimeOut(rs.getString("timeOut"));		
			
			timeEntry.setTimeInTS(rs.getTimestamp("timeIn"));
			timeEntry.setTimeOutTS(rs.getTimestamp("timeOut"));		
		}

		ps.close();
		rs.close();
		
		return timeEntry;
	}
	
	/** End Ian Code **/
	
	
	//TODO
	public void approveScheduleChangeRequest(int empId, String newStatus, int empScheduleDisputeId) throws SQLException, Exception {		
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE  empScheduleDispute set approvalStatus = '");
		sql.append(newStatus);	
		sql.append("' AND resolvedBy = ");
		sql.append(empId);	
		sql.append(" WHERE empScheduleDisputeId = ");
		sql.append(empScheduleDisputeId);
		
		
		System.out.print("UPDATE SQL: " + sql.toString());
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
  		//IF APPROVED
  		if(newStatus.equals("6")){
  			StringBuffer sql2 = new StringBuffer();
  	    	
  	    	sql2.append("SELECT ee.shiftingScheduleId, ee.empScheduleDisputeId, ee.scheduleDate, ee.remarks, ee.approvedBy, ee.empId, ee.approvalStatus ");
  	    	sql2.append("FROM empScheduleDispute ee ");
  	    	sql2.append("WHERE ee.empScheduleDisputeId = ");  
  	 		sql2.append(empScheduleDisputeId);
  	    	
  	    	
  			PreparedStatement ps2 = conn.prepareStatement(sql2.toString());

  		    ResultSet rs2 = ps2.executeQuery();
  		    
  		    
  		    EmployeeSchedule model = new EmployeeSchedule();
  		    
  		    if (rs2.next()) {
  		    	model = new EmployeeSchedule();
  		    	
  		    	model.setScheduleDate(rs2.getString("scheduleDate"));
  		    	model.setEmpId(rs2.getInt("empId"));
  		    	model.setEmpScheduleId(rs2.getInt("shiftingScheduleId"));
  		    	model.setShiftingScheduleId(rs2.getInt("shiftingScheduleId"));
  		    	model.setSuperVisorId(rs2.getInt("approvedBy"));
  		    	model.setUpdatedBy(rs2.getInt("approvedBy"));

  		    }
  			
  			
  			if(checkIfEmployeeHasSchedule(model)){
  				updateEmployeeSchedule(model);
  			} else {
  				insertEmployeeSchedule(model);
  			}
  		}
  		
        
        ps.executeUpdate();		          
       
        conn.commit();
         
        ps.close();
		
	}
	
//	public  List<Resolution> getAllEmpScheduleChangeBySupervisorId(int empId) throws SQLException, Exception {
//		StringBuffer sql = new StringBuffer();
//    	
//    	sql.append("SELECT ee.shiftingScheduleId, ee.empScheduleDisputeId, ee.scheduleDate, ee.remarks, ee.approvedBy, ee.empId, ee.approvalStatus, (e.lastname + ',' + e.firstname) as name, ss.description ");
//    	sql.append("FROM empScheduleDispute ee, employee e, shiftingSchedule ss ");
//    	sql.append("WHERE ee.empId = e.empId AND ee.shiftingScheduleId = ss.shiftingScheduleId AND e.superVisor1Id = ");  
// 		sql.append(empId);
//    	sql.append(" ORDER BY ee.scheduleDate desc");
//    	
//    	
//		PreparedStatement ps = conn.prepareStatement(sql.toString());
//
//	    ResultSet rs = ps.executeQuery();
//	    List<Resolution> list = new ArrayList<Resolution>();
//	    
//	    while (rs.next()) {
//	    	Resolution model = new Resolution();
//	    	
//	    	model.setClockDate(rs.getString("scheduleDate"));
//	    	model.setEmpId(rs.getInt("empId"));
//	    	model.setEmpName(rs.getString("name"));
//	    	model.setResolutionRemarks(rs.getString("remarks"));
//	    	model.setApprovalStatus(rs.getString("approvalStatus"));
//	    	model.setScheduleDesc(rs.getString("description"));
//	    	model.setOldScheduleDesc(getScheduleByDateAndEmpId(rs.getInt("empId"), rs.getString("scheduleDate")));
//	    	model.setEmpScheduleDisputeId(rs.getInt("empScheduleDisputeId"));
//	    	model.setShiftScheduleId(rs.getInt("shiftingScheduleId"));
//	    	
//	    	 
//	    	list.add(model);
//
//	    }	    
//	    
//	    StringBuffer sql2 = new StringBuffer();
//	    
//	    sql2.append("SELECT ee.shiftingScheduleId, ee.empScheduleDisputeId, ee.scheduleDate, ee.remarks, ee.approvedBy, ee.empId, ee.approvalStatus, (e.lastname + ',' + e.firstname) as name ");
//    	sql2.append("FROM empScheduleDispute ee, employee e ");
//    	sql2.append("WHERE ee.empId = e.empId AND ee.shiftingScheduleId in (2000, 2001, 2003) AND e.superVisor1Id = ");  
// 		sql2.append(empId);
//    	sql2.append(" ORDER BY ee.scheduleDate desc");
// 		
// 				
//  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString()); 		
//  		        
//        ResultSet rs2 = ps2.executeQuery();     
//        
//        
//        while(rs2.next()){
//        	Resolution model = new Resolution();
//	    	
//	    	model.setClockDate(rs2.getString("scheduleDate"));
//	    	model.setEmpId(rs2.getInt("empId"));
//	    	model.setEmpName(rs2.getString("name"));
//	    	model.setResolutionRemarks(rs2.getString("remarks"));
//	    	model.setApprovalStatus(rs2.getString("approvalStatus"));	    	
//	    	model.setOldScheduleDesc(getScheduleByDateAndEmpId(rs2.getInt("empId"), rs2.getString("scheduleDate")));
//	    	model.setEmpScheduleDisputeId(rs2.getInt("empScheduleDisputeId"));
//	    	model.setShiftScheduleId(rs2.getInt("shiftingScheduleId"));
//        	
//        	if(model.getShiftScheduleId() == 2000){
//        		model.setScheduleDesc("Paid - Rest Day");
//        	} else if(model.getShiftScheduleId() == 2001){
//        		model.setScheduleDesc("Unpaid - Rest Day");
//        	} else if(model.getShiftScheduleId() == 2003){
//        		model.setScheduleDesc("Remove Employee Shift");
//        	}       	
//        	
//        	
//        	list.add(model);
//        	
//        }
//	    
//	    
//	    ps.close();
//	    rs.close();      
//	    return list;
//	}
	
	
	
	
	
	public void updateEmployeeSchedule(EmployeeSchedule empSched) throws SQLException, Exception {
		
		if(empSched.getShiftingScheduleId() == 2003) {
			//delete action
			String sql = "DELETE FROM  empSchedule WHERE empId =? AND scheduleDate = ?";
	  		PreparedStatement ps  = conn.prepareStatement(sql);
	  		int index = 1;
	  		
	  		ps.setInt(index++, empSched.getEmpId());
	        ps.setString(index++, empSched.getScheduleDate());	
	        
	        ps.executeUpdate();       
	         
	        conn.commit();
	         
	        ps.close();
			
		} else {
			String sql = "UPDATE  empSchedule set shiftingScheduleId = ?, updatedBy = ?, updateDate = SYSDATETIME() where empId =? AND scheduleDate = ?";
	  		PreparedStatement ps  = conn.prepareStatement(sql);
	  		int index = 1;
	  		
	  		ps.setInt(index++, empSched.getShiftingScheduleId());
	  		ps.setInt(index++, empSched.getSuperVisorId());
	        ps.setInt(index++, empSched.getEmpId());
	        ps.setString(index++, empSched.getScheduleDate());		
	        
	        ps.executeUpdate();       
	         
	        conn.commit();
	         
	        ps.close();
		}
		
		
         
	}
	
	public void updateHourlyAttendance(EmployeeHourlyAttendance model) throws SQLException, Exception {
		
		String sql = "UPDATE  empHourlyAttendance set noOfHours = ? where empId =? AND attendanceDate = ?";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1;
  		
  		ps.setInt(index++, model.getNoOfHours());
        ps.setInt(index++, model.getEmpId());
        ps.setString(index++, model.getAttendanceDate());		
        
        ps.executeUpdate();       
         
        conn.commit();
         
        ps.close();	
         
	}
	
	public void editEmployeeSchedule(EmployeeSchedule empSched) throws SQLException, Exception {
		String sql = "UPDATE  empSchedule set scheduleDate = ?, shiftingScheduleId = ?, updatedBy = ?, updateDate = SYSDATETIME() where empScheduleId =?";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1;
  		
  		ps.setString(index++, empSched.getScheduleDate());
  		ps.setInt(index++, empSched.getShiftingScheduleId());
  		ps.setInt(index++, empSched.getSuperVisorId());
        ps.setInt(index++, empSched.getEmpScheduleId());
        
        
        ps.executeUpdate();       
         
        conn.commit();
         
        ps.close();
         
	}
	
	private boolean checkIfHasRequestedScheduleChange(Resolution empSched) throws SQLException, Exception {
		String sql = "SELECT * FROM empScheduleDispute WHERE empId = ? AND scheduleDate = ?";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1;
  		

        ps.setInt(index++, empSched.getEmpId());
        ps.setString(index++, empSched.getClockDate());
       	
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
        	
        	ps.close();
        	
        	return true;
        }        
        
        ps.close();
        return false;
         
	}
	
	public boolean checkIfEmployeeHasSchedule(EmployeeSchedule empSched) throws SQLException, Exception {
		String sql = "SELECT * FROM empSchedule WHERE empId = ? AND scheduleDate = ?";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1;
  		

        ps.setInt(index++, empSched.getEmpId());
        ps.setString(index++, empSched.getScheduleDate());
       	
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
        	
        	ps.close();
        	
        	return true;
        }        
        
        ps.close();
        return false;
         
	}
	
	public boolean checkIfEmployeeHasHourlyAttendance(EmployeeHourlyAttendance model) throws SQLException, Exception {
		String sql = "SELECT * FROM empHourlyAttendance WHERE empId = ? AND attendanceDate = ?";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1;
  		

        ps.setInt(index++, model.getEmpId());
        ps.setString(index++, model.getAttendanceDate());
       	
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
        	
        	ps.close();
        	
        	return true;
        }        
        
        ps.close();
        return false;
         
	}
	
	public boolean checkIfCalendarHasSchedule(EmployeeSchedule empSched) throws SQLException, Exception {
		String sql = "SELECT * FROM empSchedule WHERE createdBy = ? AND scheduleDate = ?";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1;
  		

        ps.setInt(index++, empSched.getSuperVisorId());
        ps.setString(index++, empSched.getScheduleDate());
       	
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
        	
        	ps.close();
        	
        	return true;
        }        
        
        ps.close();
        return false;
         
	}
	
	public Employee getEmployee(int empId) throws SQLException, Exception {
		Employee emp = null;
		String sql = "SELECT * FROM employee where empId = ?";
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, empId);

	    ResultSet rs = ps.executeQuery();
	    
	    if (rs.next()) {
			emp = new Employee();
			emp.setEmpId(rs.getInt("empId"));
			emp.setEmpNo(rs.getString("empNo")); // required field
			emp.setLastname(rs.getString("lastname"));
			emp.setFirstname(rs.getString("firstname"));
			emp.setMiddlename(rs.getString("middlename"));
			emp.setDateOfBirth(StringUtils.isEmpty(rs.getString("dateOfBirth"))? "" : rs.getString("dateOfBirth").substring(0, 10));
			emp.setGender(rs.getString("gender"));
			emp.setCivilStatus(rs.getString("civilStatus"));
			emp.setNationality(rs.getString("nationality"));
			emp.setStreet(rs.getString("street"));
			emp.setCityId(rs.getInt("cityId"));
			emp.setEmail(rs.getString("email"));
			emp.setMobileNo(rs.getString("mobileNo"));
			emp.setTelNo(rs.getString("telNo"));
			emp.setBirthPlace(rs.getString("birthPlace"));
			emp.setProvinceId(rs.getInt("provinceId"));
			emp.setZipCode(rs.getString("zipCode"));
			emp.setJobTitleId(rs.getInt("jobTitleId"));
			emp.setSectionId(rs.getInt("sectionId"));
			emp.setUnitId(rs.getInt("unitId"));
			emp.setSubUnitId(rs.getInt("subUnitId"));
			emp.setPersonnelType(rs.getString("personnelType"));
			emp.setEmployeeTypeId(rs.getInt("employeeTypeId"));
			emp.setEmpStatus(rs.getString("empStatus"));
			emp.setEmploymentDate(StringUtils.isEmpty(rs.getString("employmentDate")) ? "" : rs.getString("employmentDate").substring(0, 10));
			emp.setEndOfContract(StringUtils.isEmpty(rs.getString("endOfContract")) ? "" : rs.getString("endOfContract").substring(0, 10));
			emp.setSss(rs.getString("sss"));
			emp.setGsis(rs.getString("gsis"));
			emp.setHdmf(rs.getString("hdmf"));
			emp.setTin(rs.getString("tin"));
			emp.setPhic(rs.getString("phic"));
			emp.setTaxstatus(rs.getString("taxstatus"));
			emp.setPicLoc(rs.getString("picLoc"));

			emp.setUsername(rs.getString("username"));			
			emp.setPassword(EmployeeUtil.decodePassword(rs.getString("password")));
			emp.setCountryId(rs.getInt("countryId"));
			
//			emp.setJobTitleName(getJobtitleName(rs.getInt("jobTitleId")));
//			emp.setDepartmentName(getDepartmentName(rs.getInt("departmentId")));
//			emp.setDivisionName(getDivisionName(rs.getInt("divisionId")));
//			
//			
//			String superVisorName = getSupervisorName(rs.getInt("superVisor1Id"));
			
//			emp.setSuperVisorName(superVisorName);

		}
	    sql = null;
	    ps.close();
	    rs.close();	 
	    return emp;

	}
	
	public List<Employee> getAllEmployeeList() throws SQLException, Exception {
		String sql = "SELECT * FROM employee";
  		PreparedStatement ps  = conn.prepareStatement(sql);       	
        
        ResultSet rs = ps.executeQuery();
        
        List<Employee> empList = new ArrayList<Employee>();
        
        while(rs.next()){ 
        	Employee emp = new Employee();
			emp.setEmpId(rs.getInt("empId"));
			emp.setEmpNo(rs.getString("empNo")); // required field
			emp.setLastname(rs.getString("lastname"));
			emp.setFirstname(rs.getString("firstname"));
			emp.setMiddlename(rs.getString("middlename"));
			emp.setDateOfBirth(rs.getString("dateOfBirth"));
			emp.setGender(rs.getString("gender"));
			emp.setCivilStatus(rs.getString("civilStatus"));
			emp.setNationality(rs.getString("nationality"));
			emp.setStreet(rs.getString("street"));
			emp.setCityId(rs.getInt("cityId"));
			emp.setEmail(rs.getString("email"));
			emp.setMobileNo(rs.getString("mobileNo"));
			emp.setTelNo(rs.getString("telNo"));
			emp.setBirthPlace(rs.getString("birthPlace"));
			emp.setProvinceId(rs.getInt("provinceId"));
			emp.setZipCode(rs.getString("zipCode"));
			emp.setJobTitleId(rs.getInt("jobTitleId"));
			emp.setSectionId(rs.getInt("sectionId"));
			emp.setUnitId(rs.getInt("unitId"));
			emp.setSubUnitId(rs.getInt("subUnitId"));
			emp.setPersonnelType(rs.getString("personnelType"));
			emp.setEmployeeTypeId(rs.getInt("employeeTypeId"));
			emp.setEmpStatus(rs.getString("empStatus"));
			emp.setEmploymentDate(rs.getString("employmentDate"));
			emp.setEndOfContract(rs.getString("endOfContract"));
			emp.setSss(rs.getString("sss"));
			emp.setGsis(rs.getString("gsis"));
			emp.setHdmf(rs.getString("hdmf"));
			emp.setTin(rs.getString("tin"));
			emp.setPhic(rs.getString("phic"));
			emp.setTaxstatus(rs.getString("taxstatus"));
			emp.setPicLoc(rs.getString("picLoc"));

			emp.setCountryId(rs.getInt("countryId"));

        	empList.add(emp);       	
        }        
        
        ps.close();
        return empList;
         
	}
	
	
	
	public List<Employee> getEmployeeListBySectionId(int sectionId) throws SQLException, Exception {
		String sql = "SELECT * FROM employee WHERE sectionId = ?";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1; 		

        ps.setInt(index++, sectionId);           	
        
        ResultSet rs = ps.executeQuery();
        
        List<Employee> empList = new ArrayList<Employee>();
        
        while(rs.next()){ 
        	Employee emp = new Employee();
			emp.setEmpId(rs.getInt("empId"));
			emp.setEmpNo(rs.getString("empNo")); // required field
			emp.setLastname(rs.getString("lastname"));
			emp.setFirstname(rs.getString("firstname"));
			emp.setMiddlename(rs.getString("middlename"));
			emp.setDateOfBirth(rs.getString("dateOfBirth"));
			emp.setGender(rs.getString("gender"));
			emp.setCivilStatus(rs.getString("civilStatus"));
			emp.setNationality(rs.getString("nationality"));
			emp.setStreet(rs.getString("street"));
			emp.setCityId(rs.getInt("cityId"));
			emp.setEmail(rs.getString("email"));
			emp.setMobileNo(rs.getString("mobileNo"));
			emp.setTelNo(rs.getString("telNo"));
			emp.setBirthPlace(rs.getString("birthPlace"));
			emp.setProvinceId(rs.getInt("provinceId"));
			emp.setZipCode(rs.getString("zipCode"));
			emp.setJobTitleId(rs.getInt("jobTitleId"));
			emp.setSectionId(rs.getInt("sectionId"));
			emp.setUnitId(rs.getInt("unitId"));
			emp.setSubUnitId(rs.getInt("subUnitId"));
			emp.setPersonnelType(rs.getString("personnelType"));
			emp.setEmployeeTypeId(rs.getInt("employeeTypeId"));
			emp.setEmpStatus(rs.getString("empStatus"));
			emp.setEmploymentDate(rs.getString("employmentDate"));
			emp.setEndOfContract(rs.getString("endOfContract"));
			emp.setSss(rs.getString("sss"));
			emp.setGsis(rs.getString("gsis"));
			emp.setHdmf(rs.getString("hdmf"));
			emp.setTin(rs.getString("tin"));
			emp.setPhic(rs.getString("phic"));
			emp.setTaxstatus(rs.getString("taxstatus"));
			emp.setPicLoc(rs.getString("picLoc"));

			emp.setCountryId(rs.getInt("countryId"));

        	empList.add(emp);       	
        }        
        
        ps.close();
        return empList;
         
	}
	
	public List<Employee> getEmployeeListByUnitId(int unitId) throws SQLException, Exception {
		String sql = "SELECT * FROM employee WHERE unitId = ?";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1; 		

        ps.setInt(index++, unitId);           	
        
        ResultSet rs = ps.executeQuery();
        
        List<Employee> empList = new ArrayList<Employee>();
        
        while(rs.next()){ 
        	Employee emp = new Employee();
			emp.setEmpId(rs.getInt("empId"));
			emp.setEmpNo(rs.getString("empNo")); // required field
			emp.setLastname(rs.getString("lastname"));
			emp.setFirstname(rs.getString("firstname"));
			emp.setMiddlename(rs.getString("middlename"));
			emp.setDateOfBirth(rs.getString("dateOfBirth"));
			emp.setGender(rs.getString("gender"));
			emp.setCivilStatus(rs.getString("civilStatus"));
			emp.setNationality(rs.getString("nationality"));
			emp.setStreet(rs.getString("street"));
			emp.setCityId(rs.getInt("cityId"));
			emp.setEmail(rs.getString("email"));
			emp.setMobileNo(rs.getString("mobileNo"));
			emp.setTelNo(rs.getString("telNo"));
			emp.setBirthPlace(rs.getString("birthPlace"));
			emp.setProvinceId(rs.getInt("provinceId"));
			emp.setZipCode(rs.getString("zipCode"));
			emp.setJobTitleId(rs.getInt("jobTitleId"));
			emp.setSectionId(rs.getInt("sectionId"));
			emp.setUnitId(rs.getInt("unitId"));
			emp.setSubUnitId(rs.getInt("subUnitId"));
			emp.setPersonnelType(rs.getString("personnelType"));
			emp.setEmployeeTypeId(rs.getInt("employeeTypeId"));
			emp.setEmpStatus(rs.getString("empStatus"));
			emp.setEmploymentDate(rs.getString("employmentDate"));
			emp.setEndOfContract(rs.getString("endOfContract"));
			emp.setSss(rs.getString("sss"));
			emp.setGsis(rs.getString("gsis"));
			emp.setHdmf(rs.getString("hdmf"));
			emp.setTin(rs.getString("tin"));
			emp.setPhic(rs.getString("phic"));
			emp.setTaxstatus(rs.getString("taxstatus"));
			emp.setPicLoc(rs.getString("picLoc"));

			emp.setCountryId(rs.getInt("countryId"));

        	empList.add(emp);       	
        }        
        
        ps.close();
        return empList;
         
	}
	
	public List<Employee> getEmployeeListBySubUnitId(int subUnitId) throws SQLException, Exception {
		String sql = "SELECT * FROM employee WHERE subUnitId = ?";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1; 		

        ps.setInt(index++, subUnitId);           	
        
        ResultSet rs = ps.executeQuery();
        
        List<Employee> empList = new ArrayList<Employee>();
        
        while(rs.next()){ 
        	Employee emp = new Employee();
			emp.setEmpId(rs.getInt("empId"));
			emp.setEmpNo(rs.getString("empNo")); // required field
			emp.setLastname(rs.getString("lastname"));
			emp.setFirstname(rs.getString("firstname"));
			emp.setMiddlename(rs.getString("middlename"));
			emp.setDateOfBirth(rs.getString("dateOfBirth"));
			emp.setGender(rs.getString("gender"));
			emp.setCivilStatus(rs.getString("civilStatus"));
			emp.setNationality(rs.getString("nationality"));
			emp.setStreet(rs.getString("street"));
			emp.setCityId(rs.getInt("cityId"));
			emp.setEmail(rs.getString("email"));
			emp.setMobileNo(rs.getString("mobileNo"));
			emp.setTelNo(rs.getString("telNo"));
			emp.setBirthPlace(rs.getString("birthPlace"));
			emp.setProvinceId(rs.getInt("provinceId"));
			emp.setZipCode(rs.getString("zipCode"));
			emp.setJobTitleId(rs.getInt("jobTitleId"));
			emp.setSectionId(rs.getInt("sectionId"));
			emp.setUnitId(rs.getInt("unitId"));
			emp.setSubUnitId(rs.getInt("subUnitId"));
			emp.setPersonnelType(rs.getString("personnelType"));
			emp.setEmployeeTypeId(rs.getInt("employeeTypeId"));
			emp.setEmpStatus(rs.getString("empStatus"));
			emp.setEmploymentDate(rs.getString("employmentDate"));
			emp.setEndOfContract(rs.getString("endOfContract"));
			emp.setSss(rs.getString("sss"));
			emp.setGsis(rs.getString("gsis"));
			emp.setHdmf(rs.getString("hdmf"));
			emp.setTin(rs.getString("tin"));
			emp.setPhic(rs.getString("phic"));
			emp.setTaxstatus(rs.getString("taxstatus"));
			emp.setPicLoc(rs.getString("picLoc"));

			emp.setCountryId(rs.getInt("countryId"));

        	empList.add(emp);       	
        }        
        
        ps.close();
        return empList;
         
	}
	
	

	
	//With Filter Start
	
	public List<Employee> getAllEmployeeListByPersonnelType(String personnelType) throws SQLException, Exception {
		String sql = "SELECT * FROM employee WHERE personnelType = '" + personnelType + "'";
  		PreparedStatement ps  = conn.prepareStatement(sql);       	
        
        ResultSet rs = ps.executeQuery();
        
        List<Employee> empList = new ArrayList<Employee>();
        
        while(rs.next()){ 
        	Employee emp = new Employee();
			emp.setEmpId(rs.getInt("empId"));
			emp.setEmpNo(rs.getString("empNo")); // required field
			emp.setLastname(rs.getString("lastname"));
			emp.setFirstname(rs.getString("firstname"));
			emp.setMiddlename(rs.getString("middlename"));
			emp.setDateOfBirth(rs.getString("dateOfBirth"));
			emp.setGender(rs.getString("gender"));
			emp.setCivilStatus(rs.getString("civilStatus"));
			emp.setNationality(rs.getString("nationality"));
			emp.setStreet(rs.getString("street"));
			emp.setCityId(rs.getInt("cityId"));
			emp.setEmail(rs.getString("email"));
			emp.setMobileNo(rs.getString("mobileNo"));
			emp.setTelNo(rs.getString("telNo"));
			emp.setBirthPlace(rs.getString("birthPlace"));
			emp.setProvinceId(rs.getInt("provinceId"));
			emp.setZipCode(rs.getString("zipCode"));
			emp.setJobTitleId(rs.getInt("jobTitleId"));
			emp.setSectionId(rs.getInt("sectionId"));
			emp.setUnitId(rs.getInt("unitId"));
			emp.setSubUnitId(rs.getInt("subUnitId"));
			emp.setPersonnelType(rs.getString("personnelType"));
			emp.setEmployeeTypeId(rs.getInt("employeeTypeId"));
			emp.setEmpStatus(rs.getString("empStatus"));
			emp.setEmploymentDate(rs.getString("employmentDate"));
			emp.setEndOfContract(rs.getString("endOfContract"));
			emp.setSss(rs.getString("sss"));
			emp.setGsis(rs.getString("gsis"));
			emp.setHdmf(rs.getString("hdmf"));
			emp.setTin(rs.getString("tin"));
			emp.setPhic(rs.getString("phic"));
			emp.setTaxstatus(rs.getString("taxstatus"));
			emp.setPicLoc(rs.getString("picLoc"));

			emp.setCountryId(rs.getInt("countryId"));

        	empList.add(emp);       	
        }        
        
        ps.close();
        return empList;
         
	}
	
	public List<Employee> getEmployeeListBySectionIdByPersonnelType(int sectionId, String personnelType) throws SQLException, Exception {
		String sql = "SELECT * FROM employee WHERE sectionId = ? AND personnelType = ?";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1; 		

        ps.setInt(index++, sectionId);   
        ps.setString(index++, personnelType);   
        
        ResultSet rs = ps.executeQuery();
        
        List<Employee> empList = new ArrayList<Employee>();
        
        while(rs.next()){ 
        	Employee emp = new Employee();
			emp.setEmpId(rs.getInt("empId"));
			emp.setEmpNo(rs.getString("empNo")); // required field
			emp.setLastname(rs.getString("lastname"));
			emp.setFirstname(rs.getString("firstname"));
			emp.setMiddlename(rs.getString("middlename"));
			emp.setDateOfBirth(rs.getString("dateOfBirth"));
			emp.setGender(rs.getString("gender"));
			emp.setCivilStatus(rs.getString("civilStatus"));
			emp.setNationality(rs.getString("nationality"));
			emp.setStreet(rs.getString("street"));
			emp.setCityId(rs.getInt("cityId"));
			emp.setEmail(rs.getString("email"));
			emp.setMobileNo(rs.getString("mobileNo"));
			emp.setTelNo(rs.getString("telNo"));
			emp.setBirthPlace(rs.getString("birthPlace"));
			emp.setProvinceId(rs.getInt("provinceId"));
			emp.setZipCode(rs.getString("zipCode"));
			emp.setJobTitleId(rs.getInt("jobTitleId"));
			emp.setSectionId(rs.getInt("sectionId"));
			emp.setUnitId(rs.getInt("unitId"));
			emp.setSubUnitId(rs.getInt("subUnitId"));
			emp.setPersonnelType(rs.getString("personnelType"));
			emp.setEmployeeTypeId(rs.getInt("employeeTypeId"));
			emp.setEmpStatus(rs.getString("empStatus"));
			emp.setEmploymentDate(rs.getString("employmentDate"));
			emp.setEndOfContract(rs.getString("endOfContract"));
			emp.setSss(rs.getString("sss"));
			emp.setGsis(rs.getString("gsis"));
			emp.setHdmf(rs.getString("hdmf"));
			emp.setTin(rs.getString("tin"));
			emp.setPhic(rs.getString("phic"));
			emp.setTaxstatus(rs.getString("taxstatus"));
			emp.setPicLoc(rs.getString("picLoc"));

			emp.setCountryId(rs.getInt("countryId"));

        	empList.add(emp);       	
        }        
        
        ps.close();
        return empList;
         
	}
	
	public List<Employee> getEmployeeListByUnitIdByPersonnelType(int unitId, String personnelType) throws SQLException, Exception {
		String sql = "SELECT * FROM employee WHERE unitId = ? AND personnelType = ?";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1; 		

        ps.setInt(index++, unitId);          
        ps.setString(index++, personnelType);   
        
        ResultSet rs = ps.executeQuery();
        
        List<Employee> empList = new ArrayList<Employee>();
        
        while(rs.next()){ 
        	Employee emp = new Employee();
			emp.setEmpId(rs.getInt("empId"));
			emp.setEmpNo(rs.getString("empNo")); // required field
			emp.setLastname(rs.getString("lastname"));
			emp.setFirstname(rs.getString("firstname"));
			emp.setMiddlename(rs.getString("middlename"));
			emp.setDateOfBirth(rs.getString("dateOfBirth"));
			emp.setGender(rs.getString("gender"));
			emp.setCivilStatus(rs.getString("civilStatus"));
			emp.setNationality(rs.getString("nationality"));
			emp.setStreet(rs.getString("street"));
			emp.setCityId(rs.getInt("cityId"));
			emp.setEmail(rs.getString("email"));
			emp.setMobileNo(rs.getString("mobileNo"));
			emp.setTelNo(rs.getString("telNo"));
			emp.setBirthPlace(rs.getString("birthPlace"));
			emp.setProvinceId(rs.getInt("provinceId"));
			emp.setZipCode(rs.getString("zipCode"));
			emp.setJobTitleId(rs.getInt("jobTitleId"));
			emp.setSectionId(rs.getInt("sectionId"));
			emp.setUnitId(rs.getInt("unitId"));
			emp.setSubUnitId(rs.getInt("subUnitId"));
			emp.setPersonnelType(rs.getString("personnelType"));
			emp.setEmployeeTypeId(rs.getInt("employeeTypeId"));
			emp.setEmpStatus(rs.getString("empStatus"));
			emp.setEmploymentDate(rs.getString("employmentDate"));
			emp.setEndOfContract(rs.getString("endOfContract"));
			emp.setSss(rs.getString("sss"));
			emp.setGsis(rs.getString("gsis"));
			emp.setHdmf(rs.getString("hdmf"));
			emp.setTin(rs.getString("tin"));
			emp.setPhic(rs.getString("phic"));
			emp.setTaxstatus(rs.getString("taxstatus"));
			emp.setPicLoc(rs.getString("picLoc"));

			emp.setCountryId(rs.getInt("countryId"));

        	empList.add(emp);       	
        }        
        
        ps.close();
        return empList;
         
	}
	
	public List<Employee> getEmployeeListBySubUnitIdByPersonnelType(int subUnitId, String personnelType) throws SQLException, Exception {
		String sql = "SELECT * FROM employee WHERE subUnitId = ? AND personnelType = ?";
  		PreparedStatement ps  = conn.prepareStatement(sql);
  		int index = 1; 		

        ps.setInt(index++, subUnitId);   
        ps.setString(index++, personnelType);   
        
        ResultSet rs = ps.executeQuery();
        
        List<Employee> empList = new ArrayList<Employee>();
        
        while(rs.next()){ 
        	Employee emp = new Employee();
			emp.setEmpId(rs.getInt("empId"));
			emp.setEmpNo(rs.getString("empNo")); // required field
			emp.setLastname(rs.getString("lastname"));
			emp.setFirstname(rs.getString("firstname"));
			emp.setMiddlename(rs.getString("middlename"));
			emp.setDateOfBirth(rs.getString("dateOfBirth"));
			emp.setGender(rs.getString("gender"));
			emp.setCivilStatus(rs.getString("civilStatus"));
			emp.setNationality(rs.getString("nationality"));
			emp.setStreet(rs.getString("street"));
			emp.setCityId(rs.getInt("cityId"));
			emp.setEmail(rs.getString("email"));
			emp.setMobileNo(rs.getString("mobileNo"));
			emp.setTelNo(rs.getString("telNo"));
			emp.setBirthPlace(rs.getString("birthPlace"));
			emp.setProvinceId(rs.getInt("provinceId"));
			emp.setZipCode(rs.getString("zipCode"));
			emp.setJobTitleId(rs.getInt("jobTitleId"));
			emp.setSectionId(rs.getInt("sectionId"));
			emp.setUnitId(rs.getInt("unitId"));
			emp.setSubUnitId(rs.getInt("subUnitId"));
			emp.setPersonnelType(rs.getString("personnelType"));
			emp.setEmployeeTypeId(rs.getInt("employeeTypeId"));
			emp.setEmpStatus(rs.getString("empStatus"));
			emp.setEmploymentDate(rs.getString("employmentDate"));
			emp.setEndOfContract(rs.getString("endOfContract"));
			emp.setSss(rs.getString("sss"));
			emp.setGsis(rs.getString("gsis"));
			emp.setHdmf(rs.getString("hdmf"));
			emp.setTin(rs.getString("tin"));
			emp.setPhic(rs.getString("phic"));
			emp.setTaxstatus(rs.getString("taxstatus"));
			emp.setPicLoc(rs.getString("picLoc"));

			emp.setCountryId(rs.getInt("countryId"));

        	empList.add(emp);       	
        }        
        
        ps.close();
        return empList;
         
	}
	
	
	
	//With Filter End	
 	public List<EmployeeSchedule> getAllEmployeeScheduleForTheMonth(int id, String approverType) throws SQLException, Exception {
 		StringBuffer sql = new StringBuffer();
 		
 		sql.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, e.firstname, ss.description, es.updatedBy ");
 		sql.append("FROM empSchedule es, employee e, shiftingSchedule ss ");
 		sql.append("WHERE es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId ");
 		
 		if("SECTIONADMIN".equals(approverType)) {
			sql.append(" AND e.sectionId = ");
			sql.append(id);
		} else if("UNITADMIN".equals(approverType)) {
			sql.append(" AND e.unitId = ");
			sql.append(id);
		} else if("SUBUNITADMIN".equals(approverType)) {
			sql.append(" AND e.subunitId = ");
			sql.append(id);
		}
 		
 		sql.append(" AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ORDER BY es.scheduleDate desc, e.lastname");
 				
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
  		        
        ResultSet rs = ps.executeQuery();
        
        List<EmployeeSchedule> empSchedList = new ArrayList<EmployeeSchedule>();
        
        while(rs.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs.getInt("empId"));
        	empSched.setEmpScheduleId(rs.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs.getString("lastname") + ", " + rs.getString("firstname"));
        	empSched.setEmpShift(rs.getString("description"));
        	empSched.setUpdatedBy(rs.getInt("updatedBy"));
        	
        	empSchedList.add(empSched);
        	
        }
        
        StringBuffer sql2 = new StringBuffer();
 		
 		sql2.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, e.firstname, es.updatedBy ");
 		sql2.append("FROM empSchedule es, employee e ");
 		sql2.append("WHERE es.empId = e.empId AND es.shiftingScheduleId in (2000, 2001) ");
 		
 		if("SECTIONADMIN".equals(approverType)) {
			sql2.append(" AND e.sectionId = ");
			sql2.append(id);
		} else if("UNITADMIN".equals(approverType)) {
			sql2.append(" AND e.unitId = ");
			sql2.append(id);
		} else if("SUBUNITADMIN".equals(approverType)) {
			sql2.append(" AND e.subunitId = ");
			sql2.append(id);
		}
 		
 		sql2.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ORDER BY es.scheduleDate desc, e.lastname");
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString());
  		
  		        
        ResultSet rs2 = ps2.executeQuery();
        
        
        
        while(rs2.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs2.getInt("empId"));
        	empSched.setEmpScheduleId(rs2.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs2.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs2.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs2.getString("lastname") + ", " + rs2.getString("firstname"));
        	
        	if(empSched.getShiftingScheduleId() == 2000){
        		empSched.setEmpShift("Paid - Rest Day");
        	} else if(empSched.getShiftingScheduleId() == 2001){
        		empSched.setEmpShift("Unpaid - Rest Day");
        	}
        	
        	empSched.setUpdatedBy(rs2.getInt("updatedBy"));
        	
        	empSchedList.add(empSched);
        	
        }
        
        ps.close();
        return empSchedList;
         
	}
 	
 	
 	//TODO
 	//Add Leave, Out of Office Display
 	public List<EmployeeSchedule> getAllEmployeeScheduleCalendar(int id, String approverType) throws SQLException, Exception {
 		StringBuffer sql = new StringBuffer();
 		
 		sql.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, e.firstname, ss.description, es.updatedBy, ss.colorAssignment ");
 		sql.append("FROM empSchedule es, employee e, shiftingSchedule ss ");
 		sql.append("WHERE es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId ");
 		
 		if("SECTIONADMIN".equals(approverType)) {
			sql.append(" AND e.sectionId = ");
			sql.append(id);
		} else if("UNITADMIN".equals(approverType)) {
			sql.append(" AND e.unitId = ");
			sql.append(id);
		} else if("SUBUNITADMIN".equals(approverType)) {
			sql.append(" AND e.subunitId = ");
			sql.append(id);
		}
 		
 		sql.append(" ORDER BY es.scheduleDate desc, e.lastname");		
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
  		        
        ResultSet rs = ps.executeQuery();
        
        List<EmployeeSchedule> empSchedList = new ArrayList<EmployeeSchedule>();
        
        while(rs.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs.getInt("empId"));
        	empSched.setEmpScheduleId(rs.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs.getString("lastname") + ", " + rs.getString("firstname"));
        	empSched.setEmpShift(rs.getString("description"));
        	empSched.setUpdatedBy(rs.getInt("updatedBy"));
        	empSched.setColor(rs.getString("colorAssignment"));
        	
        	empSchedList.add(empSched);
        	
        }
        
        //For Rest Day and 
        
        StringBuffer sql2 = new StringBuffer();
 		
 		sql2.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, e.firstname, es.updatedBy ");
 		sql2.append("FROM empSchedule es, employee e ");
 		sql2.append("WHERE es.empId = e.empId AND es.shiftingScheduleId in (2000, 2001) ");
 		
 		if("SECTIONADMIN".equals(approverType)) {
			sql2.append(" AND e.sectionId = ");
			sql2.append(id);
		} else if("UNITADMIN".equals(approverType)) {
			sql2.append(" AND e.unitId = ");
			sql2.append(id);
		} else if("SUBUNITADMIN".equals(approverType)) {
			sql2.append(" AND e.subunitId = ");
			sql2.append(id);
		}
 		
 		sql2.append(" ORDER BY es.scheduleDate desc, e.lastname");
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString());
  		
  		        
        ResultSet rs2 = ps2.executeQuery();
        
        
        
        while(rs2.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs2.getInt("empId"));
        	empSched.setEmpScheduleId(rs2.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs2.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs2.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs2.getString("lastname") + ", " + rs2.getString("firstname"));
        	
        	if(empSched.getShiftingScheduleId() == 2000){
        		empSched.setEmpShift("Paid - Rest Day");
        	} else if(empSched.getShiftingScheduleId() == 2001){
        		empSched.setEmpShift("Unpaid - Rest Day");
        	}
        	
        	empSched.setUpdatedBy(rs2.getInt("updatedBy"));
        	
        	empSchedList.add(empSched);
        	
        }
        
        
        ps.close();
        ps2.close();
        return empSchedList;
         
	}
 	
 	public List<EmployeeSchedule> getEmployeeScheduleCalendarSpecificDate(int id, String approverType, String scheduleDate) throws SQLException, Exception {
 		StringBuffer sql = new StringBuffer();
 		
 		sql.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, e.firstname, ss.description, es.updatedBy ");
 		sql.append("FROM empSchedule es, employee e, shiftingSchedule ss ");
 		sql.append("WHERE es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId ");
 		//sql.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ");
 		sql.append(" AND convert(varchar(26),es.scheduleDate,23) = '");
    	sql.append(scheduleDate);
    	sql.append("'");
    	
    	if("SECTIONADMIN".equals(approverType)) {
			sql.append(" AND e.sectionId = ");
			sql.append(id);
		} else if("UNITADMIN".equals(approverType)) {
			sql.append(" AND e.unitId = ");
			sql.append(id);
		} else if("SUBUNITADMIN".equals(approverType)) {
			sql.append(" AND e.subunitId = ");
			sql.append(id);
		}
    	
 		
 		sql.append(" ORDER BY es.scheduleDate desc, e.lastname");
 				
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
  		        
        ResultSet rs = ps.executeQuery();
        
        List<EmployeeSchedule> empSchedList = new ArrayList<EmployeeSchedule>();
        
        while(rs.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs.getInt("empId"));
        	empSched.setEmpScheduleId(rs.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs.getString("lastname") + ", " + rs.getString("firstname"));
        	empSched.setEmpShift(rs.getString("description"));
        	empSched.setUpdatedBy(rs.getInt("updatedBy"));
        	
        	empSchedList.add(empSched);
        	
        }
        
        StringBuffer sql2 = new StringBuffer();
 		
 		sql2.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, e.firstname, es.updatedBy ");
 		sql2.append("FROM empSchedule es, employee e ");
 		sql2.append("WHERE es.empId = e.empId AND es.shiftingScheduleId in (2000, 2001) ");
 		//sql2.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ");
 		sql2.append(" AND convert(varchar(26),es.scheduleDate,23) = '");
    	sql2.append(scheduleDate);
    	sql2.append("'");
    	
    	if("SECTIONADMIN".equals(approverType)) {
			sql2.append(" AND e.sectionId = ");
			sql2.append(id);
		} else if("UNITADMIN".equals(approverType)) {
			sql2.append(" AND e.unitId = ");
			sql2.append(id);
		} else if("SUBUNITADMIN".equals(approverType)) {
			sql2.append(" AND e.subunitId = ");
			sql2.append(id);
		}
    	    	
 		sql2.append(" ORDER BY es.scheduleDate desc, e.lastname");
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString());
  		
  		        
        ResultSet rs2 = ps2.executeQuery();
        
        
        
        while(rs2.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs2.getInt("empId"));
        	empSched.setEmpScheduleId(rs2.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs2.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs2.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs2.getString("lastname") + ", " + rs2.getString("firstname"));
        	
        	if(empSched.getShiftingScheduleId() == 2000){
        		empSched.setEmpShift("Paid - Rest Day");
        	} else if(empSched.getShiftingScheduleId() == 2001){
        		empSched.setEmpShift("Unpaid - Rest Day");
        	}
        	
        	empSched.setUpdatedBy(rs2.getInt("updatedBy"));
        	
        	empSchedList.add(empSched);
        	
        }
        
        ps.close();
        return empSchedList;
         
	}	
 	
 	
 	public Map<Integer, List<EmployeeSchedule>> getEmployeeScheduleForTheMonthByEmpIdMap(int empId) throws SQLException, Exception {
 		StringBuffer sql = new StringBuffer();
 		
 		sql.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, ss.description, es.updatedBy ");
 		sql.append("FROM empSchedule es, employee e, shiftingSchedule ss ");
 		sql.append("WHERE es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId ");
 		//sql.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ");
 		sql.append("AND e.empId = ");
 		sql.append(empId);
 		sql.append(" ORDER BY es.scheduleDate desc");
 				
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
  		        
        ResultSet rs = ps.executeQuery();
        
        //List<EmployeeSchedule> empSchedList = new ArrayList<EmployeeSchedule>();
        Map<Integer, List<EmployeeSchedule>> map = new HashMap<Integer, List<EmployeeSchedule>>();
        
        while(rs.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs.getInt("empId"));
        	empSched.setEmpScheduleId(rs.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs.getString("lastname"));
        	empSched.setEmpShift(rs.getString("description"));
        	empSched.setUpdatedBy(rs.getInt("updatedBy"));
        	
        	
        	
        	if(map.containsKey(rs.getInt("empId"))){
	    		 map.get(rs.getInt("empId")).add(empSched);
	    	 } else {
	    		 List<EmployeeSchedule> list = new ArrayList<EmployeeSchedule>();
	    		 list.add(empSched);
	    		 map.put(rs.getInt("empId"), list);
	    	 }
        	
        }
        
        StringBuffer sql2 = new StringBuffer();
 		
 		sql2.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, es.updatedBy ");
 		sql2.append("FROM empSchedule es, employee e ");
 		sql2.append("WHERE es.empId = e.empId AND es.shiftingScheduleId in (2000, 2001) ");
 		//sql2.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ");
 		sql2.append("AND e.empId = ");
 		sql2.append(empId);
 		sql2.append(" ORDER BY es.scheduleDate desc");
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString());
  		
  		        
        ResultSet rs2 = ps2.executeQuery();
        
        
        
        while(rs2.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs2.getInt("empId"));
        	empSched.setEmpScheduleId(rs2.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs2.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs2.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs2.getString("lastname"));
        	
        	if(empSched.getShiftingScheduleId() == 2000){
        		empSched.setEmpShift("Paid - Rest Day");
        	} else if(empSched.getShiftingScheduleId() == 2001){
        		empSched.setEmpShift("Unpaid - Rest Day");
        	}
        	
        	empSched.setUpdatedBy(rs2.getInt("updatedBy"));
        	if(map.containsKey(rs2.getInt("empId"))){
        	
	    		 map.get(rs2.getInt("empId")).add(empSched);
	    	 } else {
	    		 List<EmployeeSchedule> list = new ArrayList<EmployeeSchedule>();
	    		 list.add(empSched);
	    		 map.put(rs2.getInt("empId"), list);
	    	 }
        	
        }
        
        ps.close();
        return map;
         
	}
 	
 	public Map<Integer, List<EmployeeSchedule>> getEmployeeScheduleForTheMonthByEmpIdMap(int empId, int month, int year) throws SQLException, Exception {
 		StringBuffer sql = new StringBuffer();
 		
 		sql.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, ss.description, es.updatedBy ");
 		sql.append("FROM empSchedule es, employee e, shiftingSchedule ss ");
 		sql.append("WHERE es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId "); 		
 		sql.append("AND e.empId = ");
 		sql.append(empId);
 		sql.append(" AND MONTH(es.scheduleDate) = ");
    	sql.append(month);
    	sql.append(" AND YEAR(es.scheduleDate) = ");
    	sql.append(year);
 		sql.append(" ORDER BY es.scheduleDate desc");
 				
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
  		System.out.println("getEmployeeScheduleForTheMonthByEmpIdMap SQL: " + sql.toString());
  		
  		        
        ResultSet rs = ps.executeQuery();
        
        //List<EmployeeSchedule> empSchedList = new ArrayList<EmployeeSchedule>();
        Map<Integer, List<EmployeeSchedule>> map = new HashMap<Integer, List<EmployeeSchedule>>();
        
        while(rs.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs.getInt("empId"));
        	empSched.setEmpScheduleId(rs.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs.getString("lastname"));
        	empSched.setEmpShift(rs.getString("description"));
        	empSched.setUpdatedBy(rs.getInt("updatedBy"));
        	
        	
        	
        	if(map.containsKey(rs.getInt("empId"))){
	    		 map.get(rs.getInt("empId")).add(empSched);
	    	 } else {
	    		 List<EmployeeSchedule> list = new ArrayList<EmployeeSchedule>();
	    		 list.add(empSched);
	    		 map.put(rs.getInt("empId"), list);
	    	 }
        	
        }
        
        StringBuffer sql2 = new StringBuffer();
 		
 		sql2.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, es.updatedBy ");
 		sql2.append("FROM empSchedule es, employee e ");
 		sql2.append("WHERE es.empId = e.empId AND es.shiftingScheduleId in (2000, 2001) "); 		
 		sql2.append("AND e.empId = ");
 		sql2.append(empId);
 		sql.append(" AND MONTH(es.scheduleDate) = ");
    	sql.append(month);
    	sql.append(" AND YEAR(es.scheduleDate) = ");
    	sql.append(year);
 		sql2.append(" ORDER BY es.scheduleDate desc");
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString());
  		
  		        
        ResultSet rs2 = ps2.executeQuery();
        
        
        
        while(rs2.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs2.getInt("empId"));
        	empSched.setEmpScheduleId(rs2.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs2.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs2.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs2.getString("lastname"));
        	
        	if(empSched.getShiftingScheduleId() == 2000){
        		empSched.setEmpShift("Paid - Rest Day");
        	} else if(empSched.getShiftingScheduleId() == 2001){
        		empSched.setEmpShift("Unpaid - Rest Day");
        	}
        	
        	empSched.setUpdatedBy(rs2.getInt("updatedBy"));
        	if(map.containsKey(rs2.getInt("empId"))){
        	
	    		 map.get(rs2.getInt("empId")).add(empSched);
	    	 } else {
	    		 List<EmployeeSchedule> list = new ArrayList<EmployeeSchedule>();
	    		 list.add(empSched);
	    		 map.put(rs2.getInt("empId"), list);
	    	 }
        	
        }
        
        ps.close();
        return map;
         
	}
 	
 	
	
	
	
	public void insertTimeIn(TimeEntry timeEntry) throws SQLException, Exception {
		String sql = "INSERT INTO empTimeEntry (shiftId, timeIn, empId, inputMethodTimeIn,  deviceNoTimeIn) VALUES (?,?,?,?,?)";
  		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
  		int index = 1;
  		
        ps.setInt(index++, timeEntry.getShiftId());
        ps.setTimestamp(index++, timeEntry.getTimeInTS());
        ps.setInt(index++, timeEntry.getEmpId());
        ps.setInt(index++, timeEntry.getVerifyCode());
        ps.setString(index++, timeEntry.getDeviceNo());
        
        
        ps.executeUpdate();
          
        ResultSet keyResultSet = ps.getGeneratedKeys();
         int generatedAutoIncrementId = 0;
         if (keyResultSet.next()) {
          	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
          	timeEntry.setTimeEntryId(generatedAutoIncrementId);
          	conn.commit();
          }
        ps.close();
        keyResultSet.close();
	}
	
	public void updateTimeOut(TimeEntry timeEntry) throws SQLException, Exception {
		String sql = "UPDATE  empTimeEntry set timeOut = ?, inputMethodTimeOut = ?, deviceNoTimeOut = ? where timeEntryId =?";
  		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
  		int index = 1;  
  		
  	    ps.setTimestamp(index++, timeEntry.getTimeOutTS());
  	    ps.setInt(index++, timeEntry.getVerifyCode());
  	    ps.setString(index++, timeEntry.getDeviceNo());
        ps.setInt(index++, timeEntry.getTimeEntryId());
        
        ps.executeUpdate();
          
        conn.commit();
        ps.close();
        
	}

	
	
	

	
	
    public  List<TimeEntry> getAllTimeEntry() throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.shiftId, ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut, ss.description, ss.shiftType ");
    	sql.append("FROM empTimeEntry ee, employee e, shiftingSchedule ss ");
    	sql.append("WHERE ee.empId = e.empId AND ee.shiftId = ss.shiftingScheduleId ORDER BY ee.timeIn");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    List<TimeEntry> list = new ArrayList<TimeEntry>();
	    
	    while (rs.next()) {
	    	 TimeEntry model = new TimeEntry();
	    	 model.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 model.setEmpId(rs.getInt("empId"));
	    	 model.setEmpName(rs.getString("name"));
	    	 model.setShiftScheduleDesc(rs.getString("description"));
	    	 model.setShiftType(rs.getString("shiftType"));	    
	    	 model.setTimeIn(rs.getString("timeIn"));
	    	 model.setTimeOut(rs.getString("timeOut"));
	    	 model.setShiftScheduleId(rs.getInt("shiftId"));
	    	 model.setShiftId(rs.getInt("shiftId"));
	    	 
	    	 list.add(model);

	    }	    
	    ps.close();
	    rs.close();      
	    return list;     

	}
    
    
    
    public  Map<Integer, List<TimeEntry>> getAllTimeEntryByEmpIdMap(int empId) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	
//    	sql.append("SELECT ee.shiftId, ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut, ss.description, ss.shiftType ");
//    	sql.append("FROM empTimeEntry ee, employee e, shiftingSchedule ss ");
//    	sql.append("WHERE ee.empId = e.empId AND ee.shiftId = ss.shiftingScheduleId AND e.empId = ");
//    	sql.append(empId);
//    	sql.append(" ORDER BY ee.timeIn desc");
    	
    	sql.append("SELECT ee.shiftId, ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut ");
    	sql.append("FROM empTimeEntry ee, employee e ");
    	sql.append("WHERE ee.empId = e.empId AND e.empId = ");
    	sql.append(empId);
    	sql.append(" ORDER BY ee.timeIn desc");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    Map<Integer, List<TimeEntry>> map = new HashMap<Integer, List<TimeEntry>>();	    
	    
	    while (rs.next()) {
	    	 TimeEntry timeEntry = new TimeEntry();
	    	 timeEntry.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 timeEntry.setEmpId(rs.getInt("empId"));
	    	 timeEntry.setEmpName(rs.getString("name"));
//	    	 timeEntry.setShiftScheduleDesc(rs.getString("description"));
//	    	 timeEntry.setShiftType(rs.getString("shiftType"));	    
	    	 timeEntry.setTimeIn(rs.getString("timeIn"));
	    	 timeEntry.setTimeOut(rs.getString("timeOut"));
	    	 timeEntry.setShiftScheduleId(rs.getInt("shiftId"));
	    	 timeEntry.setShiftId(rs.getInt("shiftId"));
	    	 
	    	 if(map.containsKey(rs.getInt("empId"))){
	    		 map.get(rs.getInt("empId")).add(timeEntry);
	    	 } else {
	    		 List<TimeEntry> list = new ArrayList<TimeEntry>();
	    		 list.add(timeEntry);
	    		 map.put(rs.getInt("empId"), list);
	    	 }    	 

	    }	    
	    ps.close();
	    rs.close();      
	    return map;     

	}
    
    public  Map<Integer, List<TimeEntry>> getAllTimeEntryByEmpIdMap(int empId, int month, int year) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.shiftId, ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut, ss.description, ss.shiftType ");
    	sql.append("FROM empTimeEntry ee, employee e, shiftingSchedule ss ");
    	sql.append("WHERE ee.empId = e.empId AND ee.shiftId = ss.shiftingScheduleId AND e.empId = ");
    	sql.append(empId);
    	sql.append(" AND MONTH(ee.timeIn) = ");
    	sql.append(month);
    	sql.append(" AND YEAR(ee.timeIn) = ");
    	sql.append(year);
    	sql.append(" ORDER BY ee.timeIn");
    	
    	System.out.println("getAllTimeEntryByEmpIdMap SQL: " + sql.toString());
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    Map<Integer, List<TimeEntry>> map = new HashMap<Integer, List<TimeEntry>>();	    
	    
	    while (rs.next()) {
	    	 TimeEntry timeEntry = new TimeEntry();
	    	 timeEntry.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 timeEntry.setEmpId(rs.getInt("empId"));
	    	 timeEntry.setEmpName(rs.getString("name"));
	    	 timeEntry.setShiftScheduleDesc(rs.getString("description"));
	    	 timeEntry.setShiftType(rs.getString("shiftType"));	    
	    	 timeEntry.setTimeIn(rs.getString("timeIn"));
	    	 timeEntry.setTimeOut(rs.getString("timeOut"));
	    	 timeEntry.setShiftScheduleId(rs.getInt("shiftId"));
	    	 timeEntry.setShiftId(rs.getInt("shiftId"));
	    	 
	    	 if(map.containsKey(rs.getInt("empId"))){
	    		 map.get(rs.getInt("empId")).add(timeEntry);
	    	 } else {
	    		 List<TimeEntry> list = new ArrayList<TimeEntry>();
	    		 list.add(timeEntry);
	    		 map.put(rs.getInt("empId"), list);
	    	 }    	 

	    }	    
	    ps.close();
	    rs.close();      
	    return map;     

	}
    
    
    
//    public  ArrayList<TimeEntry> getAllTimeEntryByMonthAndSuperVisor(int superVisorId) throws SQLException, Exception {
//    	StringBuffer sql = new StringBuffer();
//    	
//    	sql.append("SELECT ee.shiftId, ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut, ss.description, ss.shiftType ");
//    	sql.append("FROM empTimeEntry ee, employee e, shiftingSchedule ss ");
////    	sql.append("WHERE ee.empId = e.empId AND ee.shiftId = ss.shiftingScheduleId AND MONTH(ee.timeIn) = MONTH(SYSDATETIME()) AND e.superVisor1Id = ");
//    	sql.append("WHERE ee.empId = e.empId AND ee.shiftId = ss.shiftingScheduleId AND e.superVisor1Id = ");
//    	sql.append(superVisorId);
//    	
//    	
//		PreparedStatement ps = conn.prepareStatement(sql.toString());
//
//	    ResultSet rs = ps.executeQuery();
//	    ArrayList<TimeEntry> list = new ArrayList<TimeEntry>();
//	    
//	    while (rs.next()) {
//	    	 TimeEntry model = new TimeEntry();
//	    	 model.setTimeEntryId(rs.getInt("timeEntryId"));
//	    	 model.setEmpId(rs.getInt("empId"));
//	    	 model.setEmpName(rs.getString("name"));
//	    	 model.setShiftScheduleDesc(rs.getString("description"));
//	    	 model.setShiftType(rs.getString("shiftType"));	    
//	    	 model.setTimeIn(rs.getString("timeIn"));
//	    	 model.setTimeOut(rs.getString("timeOut"));
//	    	 model.setShiftScheduleId(rs.getInt("shiftId"));
//	    	 model.setShiftId(rs.getInt("shiftId"));
//	    	
//	    	 list.add(model);
//
//	    }	    
//	    ps.close();
//	    rs.close();      
//	    return list;     
//
//	}


    public boolean isEmployeePresentInTheHoliday(int empId, String holidayDate) throws SQLException, Exception {    	
    	
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT * FROM empTimeEntry WHERE empId = ");
    	sql.append(empId);
    	sql.append(" AND CONVERT(DATE,timeIn) = '");
    	sql.append(holidayDate);
    	sql.append("'");    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();	    
	    
	    if (rs.next()) {
	    	ps.close();
		    rs.close();
	    	return true;
	    }	    
	    
	    
	    ps.close();
	    rs.close();      
	    return false;     

	}
    
    public boolean isTimeEntryRestDay(int empId, String timeEntryDate) throws SQLException, Exception {    	
    	
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT * FROM empSchedule WHERE empId = ");
    	sql.append(empId);
    	sql.append(" AND CONVERT(DATE,scheduleDate) = '");
    	sql.append(timeEntryDate);
    	sql.append("' AND shiftingScheduleId = 2000");    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();	    
	    
	    if (rs.next()) {
	    	ps.close();
		    rs.close();
	    	return true;
	    }	    
	    
	    
	    ps.close();
	    rs.close();      
	    return false;     

	}
    
    public boolean isTimeEntryHoliday(int empId, String holidayDate) throws SQLException, Exception {    	
    	
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT * FROM holiday WHERE CONVERT(DATE,holidayDate) = '");
    	sql.append(holidayDate);
    	sql.append("'");    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();	    
	    
	    if (rs.next()) {
	    	ps.close();
		    rs.close();
	    	return true;
	    }	    
	    
	    
	    ps.close();
	    rs.close();      
	    return false;     

	}
    
    public  ShiftingSchedule getShiftingScheduleByEmpIdAndShiftTimeIn(int empId, Date shiftTimeIn ) throws SQLException, Exception {
    	
		String sql = "SELECT es.scheduleDate, es.shiftingScheduleId, sc.shiftType, sc.timeIn, sc.timeOut, sc.description FROM  shiftingSchedule sc, empSchedule es WHERE es.shiftingScheduleId = sc.shiftingScheduleId  AND es.empId = ?   AND convert(date, es.scheduleDate, 101)  = ?";		
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setInt(1, empId);
		
		
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY");
		String empLoginDate = df.format(shiftTimeIn);
		ps.setString(2, empLoginDate);
		
		

	    ResultSet rs = ps.executeQuery();
	   
	    ShiftingSchedule model = null;
	    
	    if (rs.next()) {
	    	model = new ShiftingSchedule();
	    	 model.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
	    	 model.setShiftType(rs.getString("shiftType"));	    
	    	 model.setTimeIn(util.sqlTimeToString(rs.getTime("timeIn")));
	    	 model.setTimeOut(util.sqlTimeToString(rs.getTime("timeOut")));
	    	 model.setDescription(rs.getString("description"));	    
	    	 model.setTimeInTimeStamp(rs.getTimestamp("timeIn"));
	    	 model.settImeOutTimestamp(rs.getTimestamp("timeOut"));
	    	 model.setScheduleDate(rs.getDate("scheduleDate"));
	    }	    
	    ps.close();
	    rs.close();      
	    return model;     

	}
    
    public  void add(ShiftingSchedule shiftingSchedule) throws SQLException, Exception {
  		String sql = "INSERT INTO shiftingSchedule (shiftType,timeIn,timeOut, description) VALUES (?,?,?,?)";
  		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
  		int index = 1;
  		
        ps.setString(index++, shiftingSchedule.getShiftType());
        ps.setTime(index++, util.convertToSqlTime(shiftingSchedule.getTimeIn()));
        ps.setTime(index++, util.convertToSqlTime(shiftingSchedule.getTimeOut()));
        ps.setString(index++, shiftingSchedule.getDescription());

        ps.executeUpdate();
          
        ResultSet keyResultSet = ps.getGeneratedKeys();
         int generatedAutoIncrementId = 0;
         if (keyResultSet.next()) {
          	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
          	shiftingSchedule.setShiftingScheduleId(generatedAutoIncrementId);
          	conn.commit();
          }
        ps.close();
          keyResultSet.close();

  	}
    
    public  void update(ShiftingSchedule shiftingSchedule) throws SQLException, Exception {
    	
    	
  		String sql = "UPDATE  shiftingSchedule set shiftType = ? ,timeIn = ?,timeOut = ? , description = ? where shiftingScheduleId =?";
  		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
  		int index = 1;
  		
        ps.setString(index++, shiftingSchedule.getShiftType());
        ps.setTime(index++, util.convertToSqlTime(shiftingSchedule.getTimeIn()));
        ps.setTime(index++, util.convertToSqlTime(shiftingSchedule.getTimeOut()));
        ps.setString(index++, shiftingSchedule.getDescription());
        ps.setInt(index++, shiftingSchedule.getShiftingScheduleId());
        
        ps.executeUpdate();
          
        ResultSet keyResultSet = ps.getGeneratedKeys();
         int generatedAutoIncrementId = 0;
         if (keyResultSet.next()) {
          	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
          	shiftingSchedule.setShiftingScheduleId(generatedAutoIncrementId);
          	conn.commit();
          }
        ps.close();
          keyResultSet.close();

  	}
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}
    
	public TimeEntry getTimeEntryByShiftTimeInDateAndEmpId(int empId, String shiftTimeInStr, String shiftTimeOutStr) throws SQLException, Exception {	
		
		String sql = "select * from empTimeEntry where empId = " + empId + " and timein " + " between '" + shiftTimeInStr + "' and  '" + shiftTimeOutStr +"'";
		PreparedStatement ps = conn.prepareStatement(sql);				

		ResultSet rs = ps.executeQuery();
		TimeEntry timeEntry = new TimeEntry();
		if (rs.next()) {			
			timeEntry.setTimeEntryId(rs.getInt("timeEntryId"));
			timeEntry.setTimeInTS(rs.getTimestamp("timeIn"));
			timeEntry.setTimeOutTS(rs.getTimestamp("timeOut"));				
		}

		ps.close();
		rs.close();
		return timeEntry;

	}
	
//	public void resolveTimeEntryUsingScheduledTime(Resolution resolution) throws SQLException, Exception {
//		if("S".equals(resolution.getResolutionType())){
//			//get schedule time in and out from schedule table based on date and empId
//			String sqlSelect = "SELECT timeIn, timeOut FROM shiftingSchedule WHERE shiftingScheduleId = " + resolution.getShiftScheduleId();
//			PreparedStatement psSelect = conn.prepareStatement(sqlSelect);				
//
//			ResultSet rsSelect = psSelect.executeQuery();
//			
//			if (rsSelect.next()) {	
//				if("timeIn".equals(resolution.getResolutionCategory())){
//					resolution.setTimeEntryAssigned(resolution.getClockDate() + " " + rsSelect.getString("timeIn").substring(0, 8));
//				} else {
//					resolution.setTimeEntryAssigned(resolution.getClockDate() + " " + rsSelect.getString("timeOut").substring(0, 8));
//				}
//							
//			}
//			
//			
//			//timeIn
//			if("timeIn".equals(resolution.getResolutionCategory())){
//				StringBuffer sql = new StringBuffer();
//				
//				sql.append("INSERT INTO empTimeEntry (shiftId, timeIn, empId, inputMethodTimeIn, resolutionRemarks, resolvedBy) VALUES (");
//				sql.append(resolution.getShiftScheduleId());
//				sql.append(",'");
//				sql.append(resolution.getTimeEntryAssigned());
//				sql.append("',");
//				sql.append(resolution.getEmpId());
//				sql.append(",9999,'");
//				sql.append(resolution.getResolutionRemarks());
//				sql.append("',");
//				sql.append(resolution.getResolvedBy());
//				sql.append(")");
//				
//				System.out.print("INSERT SQL: " + sql.toString());
//		  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
//		  		
//		        
//		        ps.executeUpdate();		          
//		        
//		        conn.commit();
//		        
//		        ps.close();
//		        
//			} else {
//				
//				//Error need to get timeEntryId from UI if TimeOut
//				StringBuffer sql = new StringBuffer();
//				
//				sql.append("UPDATE  empTimeEntry set timeOut = '");
//				sql.append(resolution.getTimeEntryAssigned());
//				sql.append("', inputMethodTimeOut = 9999, resolutionRemarks = '");
//				sql.append(resolution.getResolutionRemarks());
//				sql.append("', resolvedBy = ");
//				sql.append(resolution.getResolvedBy());
//				sql.append(" WHERE timeEntryId = ");
//				sql.append(resolution.getTimeEntryId());
//				
//				
//				System.out.print("UPDATE SQL: " + sql.toString());
//		  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
//		  		
//		        
//		        ps.executeUpdate();		          
//		       
//		        conn.commit();
//		         
//		        ps.close();
//		         
//			}
//		}
//		
//		
//	}
	
	public  List<TimeEntryDispute> getAllTimeEntryDisputeHR() throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.timeEntryDisputeId, ee.approvalStatus, ee.shiftId, ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut, ss.description, ss.shiftType ");
    	sql.append("FROM empTimeEntryDispute ee, employee e, shiftingSchedule ss ");
    	sql.append("WHERE ee.empId = e.empId AND ee.shiftId = ss.shiftingScheduleId AND ee.approvalStatus <> 'HA' ");    	
    	sql.append(" ORDER BY ee.timeIn");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    List<TimeEntryDispute> list = new ArrayList<TimeEntryDispute>();
	    
	    while (rs.next()) {
	    	TimeEntryDispute model = new TimeEntryDispute();
	    	 model.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 model.setEmpId(rs.getInt("empId"));
	    	 model.setEmpName(rs.getString("name"));
	    	 model.setShiftScheduleDesc(rs.getString("description"));
	    	 model.setShiftType(rs.getString("shiftType"));	    
	    	 model.setTimeIn(rs.getString("timeIn"));
	    	 model.setTimeOut(rs.getString("timeOut"));
	    	 model.setShiftScheduleId(rs.getInt("shiftId"));
	    	 model.setShiftId(rs.getInt("shiftId"));
	    	 model.setScheduleDate(model.getTimeIn().substring(0, 10));
	    	 
	    	 model.setTimeEntryDisputeId(rs.getInt("timeEntryDisputeId"));
	    	 model.setApprovalStatus(rs.getString("approvalStatus"));
	    	 
	    	 list.add(model);

	    }	    
	    ps.close();
	    rs.close();      
	    return list;
	}
	
	
//	public  List<TimeEntryDispute> getAllTimeEntryDisputeBySupervisorId(int supervisorId) throws SQLException, Exception {
//		StringBuffer sql = new StringBuffer();
//    	
//    	sql.append("SELECT ee.timeEntryDisputeId, ee.approvalStatus, ee.shiftId, ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut, ss.description, ss.shiftType ");
//    	sql.append("FROM empTimeEntryDispute ee, employee e, shiftingSchedule ss ");
//    	sql.append("WHERE ee.empId = e.empId AND ee.shiftId = ss.shiftingScheduleId AND e.superVisor1Id = ");
//    	sql.append(supervisorId);    	
//    	sql.append(" ORDER BY ee.timeIn");
//    	
//    	
//		PreparedStatement ps = conn.prepareStatement(sql.toString());
//
//	    ResultSet rs = ps.executeQuery();
//	    List<TimeEntryDispute> list = new ArrayList<TimeEntryDispute>();
//	    
//	    while (rs.next()) {
//	    	TimeEntryDispute model = new TimeEntryDispute();
//	    	 model.setTimeEntryId(rs.getInt("timeEntryId"));
//	    	 model.setEmpId(rs.getInt("empId"));
//	    	 model.setEmpName(rs.getString("name"));
//	    	 model.setShiftScheduleDesc(rs.getString("description"));
//	    	 model.setShiftType(rs.getString("shiftType"));	    
//	    	 model.setTimeIn(rs.getString("timeIn"));
//	    	 model.setTimeOut(rs.getString("timeOut"));
//	    	 model.setShiftScheduleId(rs.getInt("shiftId"));
//	    	 model.setShiftId(rs.getInt("shiftId"));
//	    	 model.setScheduleDate(model.getTimeIn().substring(0, 10));
//	    	 
//	    	 model.setTimeEntryDisputeId(rs.getInt("timeEntryDisputeId"));
//	    	 model.setApprovalStatus(rs.getString("approvalStatus"));
//	    	 
//	    	 list.add(model);
//
//	    }	    
//	    ps.close();
//	    rs.close();      
//	    return list;
//	}
	
	public  List<TimeEntryDispute> getAllTimeEntryDisputeBySupervisorAndClockDate(int resolvedBy, String clockDate) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.timeEntryDisputeId, ee.approvalStatus, ee.shiftId, ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut, ss.description, ss.shiftType ");
    	sql.append("FROM empTimeEntryDispute ee, employee e, shiftingSchedule ss ");
    	sql.append("WHERE ee.empId = e.empId AND ee.shiftId = ss.shiftingScheduleId AND ee.resolvedBy = ");
    	sql.append(resolvedBy);
    	sql.append(" AND convert(varchar(26),ee.timeIn,23) = '");
    	sql.append(clockDate);
    	sql.append("' ORDER BY ee.timeIn");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    List<TimeEntryDispute> list = new ArrayList<TimeEntryDispute>();
	    
	    while (rs.next()) {
	    	TimeEntryDispute model = new TimeEntryDispute();
	    	 model.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 model.setEmpId(rs.getInt("empId"));
	    	 model.setEmpName(rs.getString("name"));
	    	 model.setShiftScheduleDesc(rs.getString("description"));
	    	 model.setShiftType(rs.getString("shiftType"));	    
	    	 model.setTimeIn(rs.getString("timeIn"));
	    	 model.setTimeOut(rs.getString("timeOut"));
	    	 model.setShiftScheduleId(rs.getInt("shiftId"));
	    	 model.setShiftId(rs.getInt("shiftId"));
	    	 model.setScheduleDate(model.getTimeIn().substring(0, 10));
	    	 
	    	 model.setTimeEntryDisputeId(rs.getInt("timeEntryDisputeId"));
	    	 model.setApprovalStatus(rs.getString("approvalStatus"));
	    	 
	    	 list.add(model);

	    }	    
	    ps.close();
	    rs.close();      
	    return list;
	}
	
	public  List<TimeEntryDispute> getAllTimeEntryDisputeByEmpId(int empId) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.timeEntryDisputeId, ee.approvalStatus, ee.shiftId, ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut, ss.description, ss.shiftType ");
    	sql.append("FROM empTimeEntryDispute ee, employee e, shiftingSchedule ss ");
    	sql.append("WHERE ee.empId = e.empId AND ee.shiftId = ss.shiftingScheduleId AND ee.empId = ");
    	sql.append(empId);
    	sql.append(" ORDER BY ee.timeIn");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    List<TimeEntryDispute> list = new ArrayList<TimeEntryDispute>();
	    
	    while (rs.next()) {
	    	TimeEntryDispute model = new TimeEntryDispute();
	    	 model.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 model.setEmpId(rs.getInt("empId"));
	    	 model.setEmpName(rs.getString("name"));
	    	 model.setShiftScheduleDesc(rs.getString("description"));
	    	 model.setShiftType(rs.getString("shiftType"));	    
	    	 model.setTimeIn(rs.getString("timeIn"));
	    	 model.setTimeOut(rs.getString("timeOut"));
	    	 model.setShiftScheduleId(rs.getInt("shiftId"));
	    	 model.setShiftId(rs.getInt("shiftId"));
	    	 model.setScheduleDate(model.getTimeIn().substring(0, 10));
	    	 
	    	 model.setTimeEntryDisputeId(rs.getInt("timeEntryDisputeId"));
	    	 model.setApprovalStatus(rs.getString("approvalStatus"));
	    	 
	    	 list.add(model);

	    }	    
	    ps.close();
	    rs.close();      
	    return list;   
	}
	
	//TODO
	/**
	 * PEN - PENDING STATUS
	 * PUA - PENDING UNIT HEAD APPROVAL
	 * PSA - PENDING SECTION HEAD APPROVAL
	 * PHR - PENDING HR APPROVAL
	 * AAA - APPROVED
	 * 
	 */
	//0 - FOR APPROVAL
    //1 - NOT APPROVED
    //2 - FOR UNIT SUPERVISOR APPROVAL
    //3 - FOR SECTION SUPERVISOR APPROVAL
    //4 - FOR HR APPROVAL
    //5 - FOR ADMIN APPROVAL
	//6 - APPROVED
	
	public void updateTimeEntryDispute(int empId, String newStatus, int timeEntryDisputeId) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE  empTimeEntryDispute set approvalStatus = '");
		sql.append(newStatus);	
		sql.append("', resolvedBy = ");
		sql.append(empId);	
		sql.append(" WHERE timeEntryDisputeId = ");
		sql.append(timeEntryDisputeId);
		
		
		System.out.println("UPDATE SQL: " + sql.toString());
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
  		//IF APPROVED
  		if(newStatus.equals("6")){
  			TimeEntryDispute timeEntryDispute = getTimeEntryDispute(timeEntryDisputeId);
  			
  			Resolution resolution = new Resolution();
  			
  			resolution.setTimeEntryId(timeEntryDispute.getTimeEntryId());
  			resolution.setShiftScheduleId(timeEntryDispute.getShiftScheduleId());
  			resolution.setTimeInHrs(timeEntryDispute.getTimeIn());
  			resolution.setTimeOutHrs(timeEntryDispute.getTimeOut());
  			resolution.setEmpId(timeEntryDispute.getEmpId());
  			resolution.setResolutionRemarks(timeEntryDispute.getResolutionRemarks());
  			resolution.setResolvedBy(timeEntryDispute.getResolvedBy());
  			
  			
  			
  			resolveTimeEntryUsingAssignedTimeFromDispute(resolution);
  		}
  		
        
        ps.executeUpdate();		          
       
        conn.commit();
         
        ps.close();
	}
	
	
	
	
	private TimeEntryDispute getTimeEntryDispute(int timeEntryDisputeId)  throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT * ");
    	sql.append("FROM empTimeEntryDispute ");
    	sql.append("WHERE timeEntryDisputeId = ");
    	sql.append(timeEntryDisputeId);    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    List<TimeEntryDispute> list = new ArrayList<TimeEntryDispute>();
	    TimeEntryDispute model = new TimeEntryDispute();
	    
	    if (rs.next()) {
	    	
	    	 model.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 model.setEmpId(rs.getInt("empId"));
	    	 
	    	 //model.setShiftScheduleDesc(rs.getString("description"));
	    	 //model.setShiftType(rs.getString("shiftType"));	    
	    	 model.setTimeIn(rs.getString("timeIn"));
	    	 model.setTimeOut(rs.getString("timeOut"));
	    	 model.setShiftScheduleId(rs.getInt("shiftId"));
	    	 model.setShiftId(rs.getInt("shiftId"));
	    	 model.setResolutionRemarks(rs.getString("resolutionRemarks"));
	    	 model.setResolvedBy(rs.getInt("resolvedBy"));
	    	 model.setTimeEntryDisputeId(rs.getInt("timeEntryDisputeId"));
	    	 model.setApprovalStatus(rs.getString("approvalStatus"));
	    	 model.setScheduleDate(model.getTimeIn().substring(0, 10));
	    	 
	    	 list.add(model);

	    }	    
	    ps.close();
	    rs.close();      
	    return model; 
	}
	
	public void disputeTimeEntryBySupervisor(Resolution resolution) throws SQLException, Exception {
		resolution.setTimeInHrs(resolution.getClockDate() + " " + resolution.getTimeInHrs());
		resolution.setTimeOutHrs(resolution.getClockDate() + " " + resolution.getTimeOutHrs());
		
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO empTimeEntryDispute (timeEntryId, shiftId, timeIn, timeOut, empId, approvalStatus, resolutionRemarks, resolvedBy) VALUES (");
		sql.append(resolution.getTimeEntryId());
		sql.append(",");
		sql.append(resolution.getShiftScheduleId());
		sql.append(",'");
		sql.append(resolution.getTimeInHrs());
		sql.append("','");
		sql.append(resolution.getTimeOutHrs());
		sql.append("',");
		sql.append(resolution.getEmpId());
		sql.append(",'0','");
		sql.append(resolution.getResolutionRemarks());
		sql.append("',");
		sql.append(resolution.getResolvedBy());
		sql.append(")");
		
		System.out.print("INSERT SQL: " + sql.toString());
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
        
        ps.executeUpdate();		          
        
        conn.commit();
        
        ps.close();
	}
	//TODO
	public void disputeEmpSchedule(Resolution resolution) throws SQLException, Exception {
		
		if(checkIfHasRequestedScheduleChange(resolution)){
			//UPDATE
			String sql = "UPDATE  empScheduleDispute set shiftingScheduleId = ? WHERE empId =? AND scheduleDate = ?";
	  		PreparedStatement ps  = conn.prepareStatement(sql);
	  		int index = 1;
	  		
	  		ps.setInt(index++, resolution.getShiftScheduleId());
	  		ps.setInt(index++, resolution.getEmpId());
	        ps.setString(index++, resolution.getClockDate());		
	        
	        ps.executeUpdate();       
	         
	        conn.commit();
	         
	        ps.close();
			
		} else {
			StringBuffer sql = new StringBuffer();
			
			sql.append("INSERT INTO empScheduleDispute (shiftingScheduleId, scheduleDate, remarks, empId, approvalStatus, approvedBy) VALUES (");
			sql.append(resolution.getShiftScheduleId());
			sql.append(",'");
			sql.append(resolution.getClockDate());
			sql.append("','");
			sql.append(resolution.getResolutionRemarks());
			sql.append("',");
			sql.append(resolution.getEmpId());
			sql.append(",'0',");
			sql.append(resolution.getResolvedBy());		
			sql.append(")");
			
			System.out.print("INSERT SQL disputeEmpSchedule: " + sql.toString());
	  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
	  		
	        
	        ps.executeUpdate();		          
	        
	        conn.commit();
	        
	        ps.close();
		}
				
		
		
        
        
	}
	
	public void disputeTimeEntry(Resolution resolution) throws SQLException, Exception {
		resolution.setTimeInHrs(resolution.getClockDate() + " " + resolution.getTimeInHrs());
		resolution.setTimeOutHrs(resolution.getClockDate() + " " + resolution.getTimeOutHrs());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date1 = sdf.parse(resolution.getTimeInHrs());
        Date date2 = sdf.parse(resolution.getTimeOutHrs());    

        if(date1.after(date2)){           
            
            Calendar cal = Calendar.getInstance();    
            cal.setTime(date2);    
            cal.add(Calendar.DATE, 1 );           
            
            resolution.setTimeOutHrs(sdf.format(cal.getTime()));
        }
		
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO empTimeEntryDispute (timeEntryId, shiftId, timeIn, timeOut, empId, approvalStatus, resolutionRemarks, resolvedBy) VALUES (");
		sql.append(resolution.getTimeEntryId());
		sql.append(",");
		sql.append(resolution.getShiftScheduleId());
		sql.append(",'");
		sql.append(resolution.getTimeInHrs());
		sql.append("','");
		sql.append(resolution.getTimeOutHrs());
		sql.append("',");
		sql.append(resolution.getEmpId());
		sql.append(",'0','");
		sql.append(resolution.getResolutionRemarks());
		sql.append("',");
		sql.append(resolution.getResolvedBy());
		sql.append(")");
		
		System.out.print("INSERT SQL disputeTimeEntry: " + sql.toString());
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
        
        ps.executeUpdate();		          
        
        conn.commit();
        
        ps.close();
	}
	
	public void resolveTimeEntryUsingAssignedTime(Resolution resolution) throws SQLException, Exception {
//		if("L".equals(resolution.getResolutionType())){
			//combine getTimeEntryAssigned to scheduled date
			resolution.setTimeInHrs(resolution.getClockDate() + " " + resolution.getTimeInHrs());
			resolution.setTimeOutHrs(resolution.getClockDate() + " " + resolution.getTimeOutHrs());
			
			
			//timeIn
			if(resolution.getTimeEntryId() == 0){
				StringBuffer sql = new StringBuffer();
				
				sql.append("INSERT INTO empTimeEntry (shiftId, timeIn, timeOut, empId, inputMethodTimeIn, resolutionRemarks, resolvedBy) VALUES (");
				sql.append(resolution.getShiftScheduleId());
				sql.append(",'");
				sql.append(resolution.getTimeInHrs());
				sql.append("','");
				sql.append(resolution.getTimeOutHrs());
				sql.append("',");
				sql.append(resolution.getEmpId());
				sql.append(",9999,'");
				sql.append(resolution.getResolutionRemarks());
				sql.append("',");
				sql.append(resolution.getResolvedBy());
				sql.append(")");
				
				System.out.print("INSERT SQL: " + sql.toString());
		  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
		  		
		        
		        ps.executeUpdate();		          
		        
		        conn.commit();
		        
		        ps.close();
		        
			} else {
				
				//Error need to get timeEntryId from UI if TimeOut
				StringBuffer sql = new StringBuffer();
				
				sql.append("UPDATE  empTimeEntry set timeIn = '");
				sql.append(resolution.getTimeInHrs());
				sql.append("', timeOut = '");
				sql.append(resolution.getTimeOutHrs());
				sql.append("', inputMethodTimeOut = 9999, resolutionRemarks = '");
				sql.append(resolution.getResolutionRemarks());
				sql.append("', resolvedBy = ");
				sql.append(resolution.getResolvedBy());
				sql.append(" WHERE timeEntryId = ");
				sql.append(resolution.getTimeEntryId());
				
				
				System.out.print("UPDATE SQL: " + sql.toString());
		  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
		  		
		        
		        ps.executeUpdate();		          
		       
		        conn.commit();
		         
		        ps.close();
		         
			}
//		}
	}
	
	private void resolveTimeEntryUsingAssignedTimeFromDispute(Resolution resolution) throws SQLException, Exception {

			
			
			//timeIn
			if(resolution.getTimeEntryId() == 0){
				StringBuffer sql = new StringBuffer();
				
				sql.append("INSERT INTO empTimeEntry (shiftId, timeIn, timeOut, empId, inputMethodTimeIn, resolutionRemarks, resolvedBy) VALUES (");
				sql.append(resolution.getShiftScheduleId());
				sql.append(",'");
				sql.append(resolution.getTimeInHrs());
				sql.append("','");
				sql.append(resolution.getTimeOutHrs());
				sql.append("',");
				sql.append(resolution.getEmpId());
				sql.append(",9999,'");
				sql.append(resolution.getResolutionRemarks());
				sql.append("',");
				sql.append(resolution.getResolvedBy());
				sql.append(")");
				
				System.out.println("INSERT SQL: " + sql.toString());
		  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
		  		
		        
		        ps.executeUpdate();		          
		        
		        conn.commit();
		        
		        ps.close();
		        
			} else {
				
				//Error need to get timeEntryId from UI if TimeOut
				StringBuffer sql = new StringBuffer();
				
				sql.append("UPDATE  empTimeEntry set timeIn = '");
				sql.append(resolution.getTimeInHrs());
				sql.append("', timeOut = '");
				sql.append(resolution.getTimeOutHrs());
				sql.append("', inputMethodTimeOut = 9999, resolutionRemarks = '");
				sql.append(resolution.getResolutionRemarks());
				sql.append("', resolvedBy = ");
				sql.append(resolution.getResolvedBy());
				sql.append(" WHERE timeEntryId = ");
				sql.append(resolution.getTimeEntryId());
				
				
				System.out.print("UPDATE SQL: " + sql.toString());
		  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
		  		
		        
		        ps.executeUpdate();		          
		       
		        conn.commit();
		         
		        ps.close();
		         
			}

	}

	public int getCount() throws SQLException, Exception {
		
		return 0;
	}
	
	
	public List<TimeEntry> getAllTimeEntryByEmpIdForDashboard(int jtStartIndex, int jtPageSize, String jtSorting, int empId) throws SQLException, Exception {
		return null;
	}
	
	
	//NEW FOR APPROVAL PROCESS
	
	public  List<TimeEntryDispute> getAllTimeEntryDispute() throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.timeEntryDisputeId, ee.approvalStatus, ee.shiftId, ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut, ss.description, ss.shiftType ");
    	sql.append("FROM empTimeEntryDispute ee, employee e, shiftingSchedule ss ");
    	sql.append("WHERE ee.empId = e.empId AND ee.shiftId = ss.shiftingScheduleId ORDER BY ee.timeIn");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    List<TimeEntryDispute> list = new ArrayList<TimeEntryDispute>();
	    
	    while (rs.next()) {
	    	TimeEntryDispute model = new TimeEntryDispute();
	    	 model.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 model.setEmpId(rs.getInt("empId"));
	    	 model.setEmpName(rs.getString("name"));
	    	 model.setShiftScheduleDesc(rs.getString("description"));
	    	 model.setShiftType(rs.getString("shiftType"));	    
	    	 model.setTimeIn(rs.getString("timeIn"));
	    	 model.setTimeOut(rs.getString("timeOut"));
	    	 model.setShiftScheduleId(rs.getInt("shiftId"));
	    	 model.setShiftId(rs.getInt("shiftId"));
	    	 model.setScheduleDate(model.getTimeIn().substring(0, 10));
	    	 
	    	 model.setTimeEntryDisputeId(rs.getInt("timeEntryDisputeId"));
	    	 model.setApprovalStatus(rs.getString("approvalStatus"));
	    	 
	    	 list.add(model);

	    }	    
	    ps.close();
	    rs.close();      
	    return list;
	}
	
	public  List<TimeEntryDispute> getAllTimeEntryDisputeBySectionId(int sectionId) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.timeEntryDisputeId, ee.approvalStatus, ee.shiftId, ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut, ss.description, ss.shiftType ");
    	sql.append("FROM empTimeEntryDispute ee, employee e, shiftingSchedule ss ");
    	sql.append("WHERE ee.empId = e.empId AND ee.shiftId = ss.shiftingScheduleId AND e.sectionId = ");
    	sql.append(sectionId);    	
    	sql.append(" ORDER BY ee.timeIn");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    List<TimeEntryDispute> list = new ArrayList<TimeEntryDispute>();
	    
	    while (rs.next()) {
	    	TimeEntryDispute model = new TimeEntryDispute();
	    	 model.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 model.setEmpId(rs.getInt("empId"));
	    	 model.setEmpName(rs.getString("name"));
	    	 model.setShiftScheduleDesc(rs.getString("description"));
	    	 model.setShiftType(rs.getString("shiftType"));	    
	    	 model.setTimeIn(rs.getString("timeIn"));
	    	 model.setTimeOut(rs.getString("timeOut"));
	    	 model.setShiftScheduleId(rs.getInt("shiftId"));
	    	 model.setShiftId(rs.getInt("shiftId"));
	    	 model.setScheduleDate(model.getTimeIn().substring(0, 10));
	    	 
	    	 model.setTimeEntryDisputeId(rs.getInt("timeEntryDisputeId"));
	    	 model.setApprovalStatus(rs.getString("approvalStatus"));
	    	 
	    	 list.add(model);

	    }	    
	    ps.close();
	    rs.close();      
	    return list;
	}
	
	public  List<TimeEntryDispute> getAllTimeEntryDisputeByUnitId(int unitId) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.timeEntryDisputeId, ee.approvalStatus, ee.shiftId, ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut, ss.description, ss.shiftType ");
    	sql.append("FROM empTimeEntryDispute ee, employee e, shiftingSchedule ss ");
    	sql.append("WHERE ee.empId = e.empId AND ee.shiftId = ss.shiftingScheduleId AND e.unitId = ");
    	sql.append(unitId);    	
    	sql.append(" ORDER BY ee.timeIn");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    List<TimeEntryDispute> list = new ArrayList<TimeEntryDispute>();
	    
	    while (rs.next()) {
	    	TimeEntryDispute model = new TimeEntryDispute();
	    	 model.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 model.setEmpId(rs.getInt("empId"));
	    	 model.setEmpName(rs.getString("name"));
	    	 model.setShiftScheduleDesc(rs.getString("description"));
	    	 model.setShiftType(rs.getString("shiftType"));	    
	    	 model.setTimeIn(rs.getString("timeIn"));
	    	 model.setTimeOut(rs.getString("timeOut"));
	    	 model.setShiftScheduleId(rs.getInt("shiftId"));
	    	 model.setShiftId(rs.getInt("shiftId"));
	    	 model.setScheduleDate(model.getTimeIn().substring(0, 10));
	    	 
	    	 model.setTimeEntryDisputeId(rs.getInt("timeEntryDisputeId"));
	    	 model.setApprovalStatus(rs.getString("approvalStatus"));
	    	 
	    	 list.add(model);

	    }	    
	    ps.close();
	    rs.close();      
	    return list;
	}
	
	public  List<TimeEntryDispute> getAllTimeEntryDisputeBySubUnitId(int subUnitId) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.timeEntryDisputeId, ee.approvalStatus, ee.shiftId, ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut, ss.description, ss.shiftType ");
    	sql.append("FROM empTimeEntryDispute ee, employee e, shiftingSchedule ss ");
    	sql.append("WHERE ee.empId = e.empId AND ee.shiftId = ss.shiftingScheduleId AND e.subUnitId = ");
    	sql.append(subUnitId);    	
    	sql.append(" ORDER BY ee.timeIn");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    List<TimeEntryDispute> list = new ArrayList<TimeEntryDispute>();
	    
	    while (rs.next()) {
	    	TimeEntryDispute model = new TimeEntryDispute();
	    	 model.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 model.setEmpId(rs.getInt("empId"));
	    	 model.setEmpName(rs.getString("name"));
	    	 model.setShiftScheduleDesc(rs.getString("description"));
	    	 model.setShiftType(rs.getString("shiftType"));	    
	    	 model.setTimeIn(rs.getString("timeIn"));
	    	 model.setTimeOut(rs.getString("timeOut"));
	    	 model.setShiftScheduleId(rs.getInt("shiftId"));
	    	 model.setShiftId(rs.getInt("shiftId"));
	    	 model.setScheduleDate(model.getTimeIn().substring(0, 10));
	    	 
	    	 model.setTimeEntryDisputeId(rs.getInt("timeEntryDisputeId"));
	    	 model.setApprovalStatus(rs.getString("approvalStatus"));
	    	 
	    	 list.add(model);

	    }	    
	    ps.close();
	    rs.close();      
	    return list;
	}
	
	public  List<Resolution> getAllEmpScheduleChange() throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.shiftingScheduleId, ee.empScheduleDisputeId, ee.scheduleDate, ee.remarks, ee.approvedBy, ee.empId, ee.approvalStatus, (e.lastname + ',' + e.firstname) as name, ss.description ");
    	sql.append("FROM empScheduleDispute ee, employee e, shiftingSchedule ss ");
    	sql.append("WHERE ee.empId = e.empId AND ee.shiftingScheduleId = ss.shiftingScheduleId ORDER BY ee.scheduleDate desc");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    List<Resolution> list = new ArrayList<Resolution>();
	    
	    while (rs.next()) {
	    	Resolution model = new Resolution();
	    	
	    	model.setClockDate(rs.getString("scheduleDate"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setEmpName(rs.getString("name"));
	    	model.setResolutionRemarks(rs.getString("remarks"));
	    	model.setApprovalStatus(rs.getString("approvalStatus"));
	    	model.setScheduleDesc(rs.getString("description"));
	    	model.setOldScheduleDesc(getScheduleByDateAndEmpId(rs.getInt("empId"), rs.getString("scheduleDate")));
	    	model.setEmpScheduleDisputeId(rs.getInt("empScheduleDisputeId"));
	    	model.setShiftScheduleId(rs.getInt("shiftingScheduleId"));
	    	
	    	 
	    	list.add(model);

	    }	    
	    
	    StringBuffer sql2 = new StringBuffer();
	    
	    sql2.append("SELECT ee.shiftingScheduleId, ee.empScheduleDisputeId, ee.scheduleDate, ee.remarks, ee.approvedBy, ee.empId, ee.approvalStatus, (e.lastname + ',' + e.firstname) as name ");
    	sql2.append("FROM empScheduleDispute ee, employee e ");
    	sql2.append("WHERE ee.empId = e.empId AND ee.shiftingScheduleId in (2000, 2001, 2003) ORDER BY ee.scheduleDate desc");
 		
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString()); 		
  		        
        ResultSet rs2 = ps2.executeQuery();     
        
        
        while(rs2.next()){
        	Resolution model = new Resolution();
	    	
	    	model.setClockDate(rs2.getString("scheduleDate"));
	    	model.setEmpId(rs2.getInt("empId"));
	    	model.setEmpName(rs2.getString("name"));
	    	model.setResolutionRemarks(rs2.getString("remarks"));
	    	model.setApprovalStatus(rs2.getString("approvalStatus"));	    	
	    	model.setOldScheduleDesc(getScheduleByDateAndEmpId(rs2.getInt("empId"), rs2.getString("scheduleDate")));
	    	model.setEmpScheduleDisputeId(rs2.getInt("empScheduleDisputeId"));
	    	model.setShiftScheduleId(rs2.getInt("shiftingScheduleId"));
        	
        	if(model.getShiftScheduleId() == 2000){
        		model.setScheduleDesc("Paid - Rest Day");
        	} else if(model.getShiftScheduleId() == 2001){
        		model.setScheduleDesc("Unpaid - Rest Day");
        	} else if(model.getShiftScheduleId() == 2003){
        		model.setScheduleDesc("Remove Employee Shift");
        	}       	
        	
        	
        	list.add(model);
        	
        }
	    
	    
	    ps.close();
	    rs.close();      
	    return list;
	}
	
	public  List<Resolution> getAllEmpScheduleChangeBySectionId(int sectionId) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.shiftingScheduleId, ee.empScheduleDisputeId, ee.scheduleDate, ee.remarks, ee.approvedBy, ee.empId, ee.approvalStatus, (e.lastname + ',' + e.firstname) as name, ss.description ");
    	sql.append("FROM empScheduleDispute ee, employee e, shiftingSchedule ss ");
    	sql.append("WHERE ee.empId = e.empId AND ee.shiftingScheduleId = ss.shiftingScheduleId AND e.sectionId = ");  
 		sql.append(sectionId);
    	sql.append(" ORDER BY ee.scheduleDate desc");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    List<Resolution> list = new ArrayList<Resolution>();
	    
	    while (rs.next()) {
	    	Resolution model = new Resolution();
	    	
	    	model.setClockDate(rs.getString("scheduleDate"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setEmpName(rs.getString("name"));
	    	model.setResolutionRemarks(rs.getString("remarks"));
	    	model.setApprovalStatus(rs.getString("approvalStatus"));
	    	model.setScheduleDesc(rs.getString("description"));
	    	model.setOldScheduleDesc(getScheduleByDateAndEmpId(rs.getInt("empId"), rs.getString("scheduleDate")));
	    	model.setEmpScheduleDisputeId(rs.getInt("empScheduleDisputeId"));
	    	model.setShiftScheduleId(rs.getInt("shiftingScheduleId"));
	    	
	    	 
	    	list.add(model);

	    }	    
	    
	    StringBuffer sql2 = new StringBuffer();
	    
	    sql2.append("SELECT ee.shiftingScheduleId, ee.empScheduleDisputeId, ee.scheduleDate, ee.remarks, ee.approvedBy, ee.empId, ee.approvalStatus, (e.lastname + ',' + e.firstname) as name ");
    	sql2.append("FROM empScheduleDispute ee, employee e ");
    	sql2.append("WHERE ee.empId = e.empId AND ee.shiftingScheduleId in (2000, 2001, 2003) AND e.sectionId = ");  
 		sql2.append(sectionId);
    	sql2.append(" ORDER BY ee.scheduleDate desc");
 		
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString()); 		
  		        
        ResultSet rs2 = ps2.executeQuery();     
        
        
        while(rs2.next()){
        	Resolution model = new Resolution();
	    	
	    	model.setClockDate(rs2.getString("scheduleDate"));
	    	model.setEmpId(rs2.getInt("empId"));
	    	model.setEmpName(rs2.getString("name"));
	    	model.setResolutionRemarks(rs2.getString("remarks"));
	    	model.setApprovalStatus(rs2.getString("approvalStatus"));	    	
	    	model.setOldScheduleDesc(getScheduleByDateAndEmpId(rs2.getInt("empId"), rs2.getString("scheduleDate")));
	    	model.setEmpScheduleDisputeId(rs2.getInt("empScheduleDisputeId"));
	    	model.setShiftScheduleId(rs2.getInt("shiftingScheduleId"));
        	
        	if(model.getShiftScheduleId() == 2000){
        		model.setScheduleDesc("Paid - Rest Day");
        	} else if(model.getShiftScheduleId() == 2001){
        		model.setScheduleDesc("Unpaid - Rest Day");
        	} else if(model.getShiftScheduleId() == 2003){
        		model.setScheduleDesc("Remove Employee Shift");
        	}       	
        	
        	
        	list.add(model);
        	
        }
	    
	    
	    ps.close();
	    rs.close();      
	    return list;
	}
	
	public  List<Resolution> getAllEmpScheduleChangeByUnitId(int unitId) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.shiftingScheduleId, ee.empScheduleDisputeId, ee.scheduleDate, ee.remarks, ee.approvedBy, ee.empId, ee.approvalStatus, (e.lastname + ',' + e.firstname) as name, ss.description ");
    	sql.append("FROM empScheduleDispute ee, employee e, shiftingSchedule ss ");
    	sql.append("WHERE ee.empId = e.empId AND ee.shiftingScheduleId = ss.shiftingScheduleId AND e.unitId = ");  
 		sql.append(unitId);
    	sql.append(" ORDER BY ee.scheduleDate desc");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    List<Resolution> list = new ArrayList<Resolution>();
	    
	    while (rs.next()) {
	    	Resolution model = new Resolution();
	    	
	    	model.setClockDate(rs.getString("scheduleDate"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setEmpName(rs.getString("name"));
	    	model.setResolutionRemarks(rs.getString("remarks"));
	    	model.setApprovalStatus(rs.getString("approvalStatus"));
	    	model.setScheduleDesc(rs.getString("description"));
	    	model.setOldScheduleDesc(getScheduleByDateAndEmpId(rs.getInt("empId"), rs.getString("scheduleDate")));
	    	model.setEmpScheduleDisputeId(rs.getInt("empScheduleDisputeId"));
	    	model.setShiftScheduleId(rs.getInt("shiftingScheduleId"));
	    	
	    	 
	    	list.add(model);

	    }	    
	    
	    StringBuffer sql2 = new StringBuffer();
	    
	    sql2.append("SELECT ee.shiftingScheduleId, ee.empScheduleDisputeId, ee.scheduleDate, ee.remarks, ee.approvedBy, ee.empId, ee.approvalStatus, (e.lastname + ',' + e.firstname) as name ");
    	sql2.append("FROM empScheduleDispute ee, employee e ");
    	sql2.append("WHERE ee.empId = e.empId AND ee.shiftingScheduleId in (2000, 2001, 2003) AND e.unitId = ");  
 		sql2.append(unitId);
    	sql2.append(" ORDER BY ee.scheduleDate desc");
 		
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString()); 		
  		        
        ResultSet rs2 = ps2.executeQuery();     
        
        
        while(rs2.next()){
        	Resolution model = new Resolution();
	    	
	    	model.setClockDate(rs2.getString("scheduleDate"));
	    	model.setEmpId(rs2.getInt("empId"));
	    	model.setEmpName(rs2.getString("name"));
	    	model.setResolutionRemarks(rs2.getString("remarks"));
	    	model.setApprovalStatus(rs2.getString("approvalStatus"));	    	
	    	model.setOldScheduleDesc(getScheduleByDateAndEmpId(rs2.getInt("empId"), rs2.getString("scheduleDate")));
	    	model.setEmpScheduleDisputeId(rs2.getInt("empScheduleDisputeId"));
	    	model.setShiftScheduleId(rs2.getInt("shiftingScheduleId"));
        	
        	if(model.getShiftScheduleId() == 2000){
        		model.setScheduleDesc("Paid - Rest Day");
        	} else if(model.getShiftScheduleId() == 2001){
        		model.setScheduleDesc("Unpaid - Rest Day");
        	} else if(model.getShiftScheduleId() == 2003){
        		model.setScheduleDesc("Remove Employee Shift");
        	}       	
        	
        	
        	list.add(model);
        	
        }
	    
	    
	    ps.close();
	    rs.close();      
	    return list;
	}
	
	
	public  List<Resolution> getAllEmpScheduleChangeBySubUnitId(int subUnitId) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.shiftingScheduleId, ee.empScheduleDisputeId, ee.scheduleDate, ee.remarks, ee.approvedBy, ee.empId, ee.approvalStatus, (e.lastname + ',' + e.firstname) as name, ss.description ");
    	sql.append("FROM empScheduleDispute ee, employee e, shiftingSchedule ss ");
    	sql.append("WHERE ee.empId = e.empId AND ee.shiftingScheduleId = ss.shiftingScheduleId AND e.subUnitId = ");  
 		sql.append(subUnitId);
    	sql.append(" ORDER BY ee.scheduleDate desc");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    List<Resolution> list = new ArrayList<Resolution>();
	    
	    while (rs.next()) {
	    	Resolution model = new Resolution();
	    	
	    	model.setClockDate(rs.getString("scheduleDate"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setEmpName(rs.getString("name"));
	    	model.setResolutionRemarks(rs.getString("remarks"));
	    	model.setApprovalStatus(rs.getString("approvalStatus"));
	    	model.setScheduleDesc(rs.getString("description"));
	    	model.setOldScheduleDesc(getScheduleByDateAndEmpId(rs.getInt("empId"), rs.getString("scheduleDate")));
	    	model.setEmpScheduleDisputeId(rs.getInt("empScheduleDisputeId"));
	    	model.setShiftScheduleId(rs.getInt("shiftingScheduleId"));
	    	
	    	 
	    	list.add(model);

	    }	    
	    
	    StringBuffer sql2 = new StringBuffer();
	    
	    sql2.append("SELECT ee.shiftingScheduleId, ee.empScheduleDisputeId, ee.scheduleDate, ee.remarks, ee.approvedBy, ee.empId, ee.approvalStatus, (e.lastname + ',' + e.firstname) as name ");
    	sql2.append("FROM empScheduleDispute ee, employee e ");
    	sql2.append("WHERE ee.empId = e.empId AND ee.shiftingScheduleId in (2000, 2001, 2003) AND e.subUnitId = ");  
 		sql2.append(subUnitId);
    	sql2.append(" ORDER BY ee.scheduleDate desc");
 		
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString()); 		
  		        
        ResultSet rs2 = ps2.executeQuery();     
        
        
        while(rs2.next()){
        	Resolution model = new Resolution();
	    	
	    	model.setClockDate(rs2.getString("scheduleDate"));
	    	model.setEmpId(rs2.getInt("empId"));
	    	model.setEmpName(rs2.getString("name"));
	    	model.setResolutionRemarks(rs2.getString("remarks"));
	    	model.setApprovalStatus(rs2.getString("approvalStatus"));	    	
	    	model.setOldScheduleDesc(getScheduleByDateAndEmpId(rs2.getInt("empId"), rs2.getString("scheduleDate")));
	    	model.setEmpScheduleDisputeId(rs2.getInt("empScheduleDisputeId"));
	    	model.setShiftScheduleId(rs2.getInt("shiftingScheduleId"));
        	
        	if(model.getShiftScheduleId() == 2000){
        		model.setScheduleDesc("Paid - Rest Day");
        	} else if(model.getShiftScheduleId() == 2001){
        		model.setScheduleDesc("Unpaid - Rest Day");
        	} else if(model.getShiftScheduleId() == 2003){
        		model.setScheduleDesc("Remove Employee Shift");
        	}       	
        	
        	
        	list.add(model);
        	
        }
	    
	    
	    ps.close();
	    rs.close();      
	    return list;
	}
	
//	public  Map<Integer, List<TimeEntry>> getAllLeaveEntryMap(String fetchType, int id) throws SQLException, Exception {
//		StringBuffer sql = new StringBuffer();
//    	
//    	sql.append("SELECT ee.leaveId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.dateFrom, ee.dateTo, DATEDIFF(day,e.dateFrom, e.dateTo) totalDays ");
//    	sql.append("FROM leave ee, employee e ");
//    	sql.append("WHERE ee.empId = e.empId AND ee.status = 6 ");
//    	
//    	if("SECTION".equals(fetchType)){
//    		sql.append(" AND e.sectionId = ");
//    		sql.append(id);
//    	}
//    	
//    	if("UNIT".equals(fetchType)){
//    		sql.append(" AND e.unitId = ");
//    		sql.append(id);
//    	}
//    	
//    	if("SUBUNIT".equals(fetchType)){
//    		sql.append(" AND e.subUnitId = ");
//    		sql.append(id);
//    	}
//    	
//    	sql.append(" ORDER BY ee.dateFrom");
//    	
//    	
//		PreparedStatement ps = conn.prepareStatement(sql.toString());
//
//	    ResultSet rs = ps.executeQuery();
//	    
//	    
//	    Map<Integer, List<TimeEntry>> map = new HashMap<Integer, List<TimeEntry>>();	    
//	    
//	    while (rs.next()) {
//	    	
//	    	//TODO
//	    	if(rs.getInt("totalDays") > 0){
//	    		
//	    		String currentDate = rs.getString("dateFrom");
//	    		
//	    		
//	    		TimeEntry timeEntry = new TimeEntry();
//		    	 timeEntry.setTimeEntryId(rs.getInt("leaveId"));
//		    	 timeEntry.setEmpId(rs.getInt("empId"));
//		    	 timeEntry.setEmpName(rs.getString("name"));	    
//		    	 timeEntry.setTimeIn(rs.getString("dateFrom"));
//		    	 timeEntry.setTimeOut(rs.getString("dateTo"));
//		    	 
//		    	 if(map.containsKey(rs.getInt("empId"))){
//		    		 map.get(rs.getInt("empId")).add(timeEntry);
//		    	 } else {
//		    		 List<TimeEntry> list = new ArrayList<TimeEntry>();
//		    		 list.add(timeEntry);
//		    		 map.put(rs.getInt("empId"), list);
//		    	 }
//	    	} else {
//	    		TimeEntry timeEntry = new TimeEntry();
//		    	 timeEntry.setTimeEntryId(rs.getInt("leaveId"));
//		    	 timeEntry.setEmpId(rs.getInt("empId"));
//		    	 timeEntry.setEmpName(rs.getString("name"));	    
//		    	 timeEntry.setTimeIn(rs.getString("dateFrom"));
//		    	 timeEntry.setTimeOut(rs.getString("dateTo"));
//		    	 
//		    	 if(map.containsKey(rs.getInt("empId"))){
//		    		 map.get(rs.getInt("empId")).add(timeEntry);
//		    	 } else {
//		    		 List<TimeEntry> list = new ArrayList<TimeEntry>();
//		    		 list.add(timeEntry);
//		    		 map.put(rs.getInt("empId"), list);
//		    	 } 
//	    	}  	
//	    	    	 
//
//	    }		    
//
//	    
//	    ps.close();
//	    rs.close();  
//
//	    return map;     
//	}
	
	

	
    public  Map<Integer, List<TimeEntry>> getAllTimeEntryMap() throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut ");
    	sql.append("FROM empTimeEntry ee, employee e ");
    	sql.append("WHERE ee.empId = e.empId ORDER BY ee.timeIn");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    Map<Integer, List<TimeEntry>> map = new HashMap<Integer, List<TimeEntry>>();	    
	    
	    while (rs.next()) {
	    	 TimeEntry timeEntry = new TimeEntry();
	    	 timeEntry.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 timeEntry.setEmpId(rs.getInt("empId"));
	    	 timeEntry.setEmpName(rs.getString("name"));	    
	    	 timeEntry.setTimeIn(rs.getString("timeIn"));
	    	 timeEntry.setTimeOut(rs.getString("timeOut"));
	    	 
	    	 if(map.containsKey(rs.getInt("empId"))){
	    		 map.get(rs.getInt("empId")).add(timeEntry);
	    	 } else {
	    		 List<TimeEntry> list = new ArrayList<TimeEntry>();
	    		 list.add(timeEntry);
	    		 map.put(rs.getInt("empId"), list);
	    	 }    	 

	    }		    

	    
	    ps.close();
	    rs.close();  

	    return map;     

	}
    
    public  Map<Integer, List<TimeEntry>> getAllTimeEntryBySectionIdMap(int sectionId) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut ");
    	sql.append("FROM empTimeEntry ee, employee e ");
    	sql.append("WHERE ee.empId = e.empId AND e.sectionId = ");
    	sql.append(sectionId);
    	sql.append(" ORDER BY ee.timeIn");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    Map<Integer, List<TimeEntry>> map = new HashMap<Integer, List<TimeEntry>>();	    
	    
	    while (rs.next()) {
	    	 TimeEntry timeEntry = new TimeEntry();
	    	 timeEntry.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 timeEntry.setEmpId(rs.getInt("empId"));
	    	 timeEntry.setEmpName(rs.getString("name"));	    
	    	 timeEntry.setTimeIn(rs.getString("timeIn"));
	    	 timeEntry.setTimeOut(rs.getString("timeOut"));
	    	 
	    	 if(map.containsKey(rs.getInt("empId"))){
	    		 map.get(rs.getInt("empId")).add(timeEntry);
	    	 } else {
	    		 List<TimeEntry> list = new ArrayList<TimeEntry>();
	    		 list.add(timeEntry);
	    		 map.put(rs.getInt("empId"), list);
	    	 }    	 

	    }		    

	    
	    ps.close();
	    rs.close();  

	    return map;     

	}
    
    public  Map<Integer, List<TimeEntry>> getAllTimeEntryByUnitIdMap(int unitId) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut ");
    	sql.append("FROM empTimeEntry ee, employee e ");
    	sql.append("WHERE ee.empId = e.empId AND e.unitId = ");
    	sql.append(unitId);
    	sql.append(" ORDER BY ee.timeIn");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    Map<Integer, List<TimeEntry>> map = new HashMap<Integer, List<TimeEntry>>();	    
	    
	    while (rs.next()) {
	    	 TimeEntry timeEntry = new TimeEntry();
	    	 timeEntry.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 timeEntry.setEmpId(rs.getInt("empId"));
	    	 timeEntry.setEmpName(rs.getString("name"));	    
	    	 timeEntry.setTimeIn(rs.getString("timeIn"));
	    	 timeEntry.setTimeOut(rs.getString("timeOut"));
	    	 
	    	 if(map.containsKey(rs.getInt("empId"))){
	    		 map.get(rs.getInt("empId")).add(timeEntry);
	    	 } else {
	    		 List<TimeEntry> list = new ArrayList<TimeEntry>();
	    		 list.add(timeEntry);
	    		 map.put(rs.getInt("empId"), list);
	    	 }    	 

	    }		    

	    
	    ps.close();
	    rs.close();  

	    return map;     

	}
    
    public  Map<Integer, List<TimeEntry>> getAllTimeEntryBySubUnitIdMap(int subUnitId) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut ");
    	sql.append("FROM empTimeEntry ee, employee e ");
    	sql.append("WHERE ee.empId = e.empId AND e.subUnitId = ");
    	sql.append(subUnitId);
    	sql.append(" ORDER BY ee.timeIn");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    Map<Integer, List<TimeEntry>> map = new HashMap<Integer, List<TimeEntry>>();	    
	    
	    while (rs.next()) {
	    	 TimeEntry timeEntry = new TimeEntry();
	    	 timeEntry.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 timeEntry.setEmpId(rs.getInt("empId"));
	    	 timeEntry.setEmpName(rs.getString("name"));	    
	    	 timeEntry.setTimeIn(rs.getString("timeIn"));
	    	 timeEntry.setTimeOut(rs.getString("timeOut"));
	    	 
	    	 if(map.containsKey(rs.getInt("empId"))){
	    		 map.get(rs.getInt("empId")).add(timeEntry);
	    	 } else {
	    		 List<TimeEntry> list = new ArrayList<TimeEntry>();
	    		 list.add(timeEntry);
	    		 map.put(rs.getInt("empId"), list);
	    	 }    	 

	    }		    

	    
	    ps.close();
	    rs.close();  

	    return map;     

	}
    
    public Map<Integer, List<EmployeeSchedule>> getAllEmployeeScheduleForTheMonthMap() throws SQLException, Exception {
 		StringBuffer sql = new StringBuffer();
 		
 		sql.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, ss.description, es.updatedBy ");
 		sql.append("FROM empSchedule es, employee e, shiftingSchedule ss ");
 		sql.append("WHERE es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId ");
 		sql.append(" ORDER BY es.scheduleDate desc");
 				
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
  		        
        ResultSet rs = ps.executeQuery();
        
        //List<EmployeeSchedule> empSchedList = new ArrayList<EmployeeSchedule>();
        Map<Integer, List<EmployeeSchedule>> map = new HashMap<Integer, List<EmployeeSchedule>>();
        
        while(rs.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs.getInt("empId"));
        	empSched.setEmpScheduleId(rs.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs.getString("lastname"));
        	empSched.setEmpShift(rs.getString("description"));
        	empSched.setUpdatedBy(rs.getInt("updatedBy"));
        	
        	
        	
        	if(map.containsKey(rs.getInt("empId"))){
	    		 map.get(rs.getInt("empId")).add(empSched);
	    	 } else {
	    		 List<EmployeeSchedule> list = new ArrayList<EmployeeSchedule>();
	    		 list.add(empSched);
	    		 map.put(rs.getInt("empId"), list);
	    	 }
        	
        }
        
        StringBuffer sql2 = new StringBuffer();
 		
 		sql2.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, es.updatedBy ");
 		sql2.append("FROM empSchedule es, employee e ");
 		sql2.append("WHERE es.empId = e.empId AND es.shiftingScheduleId in (2000, 2001) ");
 		sql2.append(" ORDER BY es.scheduleDate desc");
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString());
  		
  		        
        ResultSet rs2 = ps2.executeQuery();
        
        
        
        while(rs2.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs2.getInt("empId"));
        	empSched.setEmpScheduleId(rs2.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs2.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs2.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs2.getString("lastname"));
        	
        	if(empSched.getShiftingScheduleId() == 2000){
        		empSched.setEmpShift("Paid - Rest Day");
        	} else if(empSched.getShiftingScheduleId() == 2001){
        		empSched.setEmpShift("Unpaid - Rest Day");
        	}
        	
        	empSched.setUpdatedBy(rs2.getInt("updatedBy"));
        	if(map.containsKey(rs2.getInt("empId"))){
        	
	    		 map.get(rs2.getInt("empId")).add(empSched);
	    	 } else {
	    		 List<EmployeeSchedule> list = new ArrayList<EmployeeSchedule>();
	    		 list.add(empSched);
	    		 map.put(rs2.getInt("empId"), list);
	    	 }
        	
        }
        
        ps.close();
        return map;
         
	}
    
    public Map<Integer, List<EmployeeSchedule>> getAllEmployeeScheduleForTheMonthMapBySectionId(int sectionId) throws SQLException, Exception {
 		StringBuffer sql = new StringBuffer();
 		
 		sql.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, ss.description, es.updatedBy ");
 		sql.append("FROM empSchedule es, employee e, shiftingSchedule ss ");
 		sql.append("WHERE es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId ");
 		//sql.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ");
 		sql.append("AND e.sectionId = ");
 		sql.append(sectionId);
 		sql.append(" ORDER BY es.scheduleDate desc");
 				
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
  		        
        ResultSet rs = ps.executeQuery();
        
        //List<EmployeeSchedule> empSchedList = new ArrayList<EmployeeSchedule>();
        Map<Integer, List<EmployeeSchedule>> map = new HashMap<Integer, List<EmployeeSchedule>>();
        
        while(rs.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs.getInt("empId"));
        	empSched.setEmpScheduleId(rs.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs.getString("lastname"));
        	empSched.setEmpShift(rs.getString("description"));
        	empSched.setUpdatedBy(rs.getInt("updatedBy"));
        	
        	
        	
        	if(map.containsKey(rs.getInt("empId"))){
	    		 map.get(rs.getInt("empId")).add(empSched);
	    	 } else {
	    		 List<EmployeeSchedule> list = new ArrayList<EmployeeSchedule>();
	    		 list.add(empSched);
	    		 map.put(rs.getInt("empId"), list);
	    	 }
        	
        }
        
        StringBuffer sql2 = new StringBuffer();
 		
 		sql2.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, es.updatedBy ");
 		sql2.append("FROM empSchedule es, employee e ");
 		sql2.append("WHERE es.empId = e.empId AND es.shiftingScheduleId in (2000, 2001) ");
 		//sql2.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ");
 		sql2.append("AND e.sectionId = ");
 		sql2.append(sectionId);
 		sql2.append(" ORDER BY es.scheduleDate desc");
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString());
  		
  		        
        ResultSet rs2 = ps2.executeQuery();
        
        
        
        while(rs2.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs2.getInt("empId"));
        	empSched.setEmpScheduleId(rs2.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs2.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs2.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs2.getString("lastname"));
        	
        	if(empSched.getShiftingScheduleId() == 2000){
        		empSched.setEmpShift("Paid - Rest Day");
        	} else if(empSched.getShiftingScheduleId() == 2001){
        		empSched.setEmpShift("Unpaid - Rest Day");
        	}
        	
        	empSched.setUpdatedBy(rs2.getInt("updatedBy"));
        	if(map.containsKey(rs2.getInt("empId"))){
        	
	    		 map.get(rs2.getInt("empId")).add(empSched);
	    	 } else {
	    		 List<EmployeeSchedule> list = new ArrayList<EmployeeSchedule>();
	    		 list.add(empSched);
	    		 map.put(rs2.getInt("empId"), list);
	    	 }
        	
        }
        
        ps.close();
        return map;
         
	}
    
    public Map<Integer, List<EmployeeSchedule>> getAllEmployeeScheduleForTheMonthMapByUnitId(int unitId) throws SQLException, Exception {
 		StringBuffer sql = new StringBuffer();
 		
 		sql.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, ss.description, es.updatedBy ");
 		sql.append("FROM empSchedule es, employee e, shiftingSchedule ss ");
 		sql.append("WHERE es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId ");
 		//sql.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ");
 		sql.append("AND e.unitId = ");
 		sql.append(unitId);
 		sql.append(" ORDER BY es.scheduleDate desc");
 				
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
  		        
        ResultSet rs = ps.executeQuery();
        
        //List<EmployeeSchedule> empSchedList = new ArrayList<EmployeeSchedule>();
        Map<Integer, List<EmployeeSchedule>> map = new HashMap<Integer, List<EmployeeSchedule>>();
        
        while(rs.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs.getInt("empId"));
        	empSched.setEmpScheduleId(rs.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs.getString("lastname"));
        	empSched.setEmpShift(rs.getString("description"));
        	empSched.setUpdatedBy(rs.getInt("updatedBy"));
        	
        	
        	
        	if(map.containsKey(rs.getInt("empId"))){
	    		 map.get(rs.getInt("empId")).add(empSched);
	    	 } else {
	    		 List<EmployeeSchedule> list = new ArrayList<EmployeeSchedule>();
	    		 list.add(empSched);
	    		 map.put(rs.getInt("empId"), list);
	    	 }
        	
        }
        
        StringBuffer sql2 = new StringBuffer();
 		
 		sql2.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, es.updatedBy ");
 		sql2.append("FROM empSchedule es, employee e ");
 		sql2.append("WHERE es.empId = e.empId AND es.shiftingScheduleId in (2000, 2001) ");
 		//sql2.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ");
 		sql2.append("AND e.unitId = ");
 		sql2.append(unitId);
 		sql2.append(" ORDER BY es.scheduleDate desc");
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString());
  		
  		        
        ResultSet rs2 = ps2.executeQuery();
        
        
        
        while(rs2.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs2.getInt("empId"));
        	empSched.setEmpScheduleId(rs2.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs2.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs2.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs2.getString("lastname"));
        	
        	if(empSched.getShiftingScheduleId() == 2000){
        		empSched.setEmpShift("Paid - Rest Day");
        	} else if(empSched.getShiftingScheduleId() == 2001){
        		empSched.setEmpShift("Unpaid - Rest Day");
        	}
        	
        	empSched.setUpdatedBy(rs2.getInt("updatedBy"));
        	if(map.containsKey(rs2.getInt("empId"))){
        	
	    		 map.get(rs2.getInt("empId")).add(empSched);
	    	 } else {
	    		 List<EmployeeSchedule> list = new ArrayList<EmployeeSchedule>();
	    		 list.add(empSched);
	    		 map.put(rs2.getInt("empId"), list);
	    	 }
        	
        }
        
        ps.close();
        return map;
         
	}
    
    public Map<Integer, List<EmployeeSchedule>> getAllEmployeeScheduleForTheMonthMapBySubUnitId(int subUnitId) throws SQLException, Exception {
 		StringBuffer sql = new StringBuffer();
 		
 		sql.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, ss.description, es.updatedBy ");
 		sql.append("FROM empSchedule es, employee e, shiftingSchedule ss ");
 		sql.append("WHERE es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId ");
 		//sql.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ");
 		sql.append("AND e.subUnitId = ");
 		sql.append(subUnitId);
 		sql.append(" ORDER BY es.scheduleDate desc");
 				
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
  		        
        ResultSet rs = ps.executeQuery();
        
        //List<EmployeeSchedule> empSchedList = new ArrayList<EmployeeSchedule>();
        Map<Integer, List<EmployeeSchedule>> map = new HashMap<Integer, List<EmployeeSchedule>>();
        
        while(rs.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs.getInt("empId"));
        	empSched.setEmpScheduleId(rs.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs.getString("lastname"));
        	empSched.setEmpShift(rs.getString("description"));
        	empSched.setUpdatedBy(rs.getInt("updatedBy"));
        	
        	
        	
        	if(map.containsKey(rs.getInt("empId"))){
	    		 map.get(rs.getInt("empId")).add(empSched);
	    	 } else {
	    		 List<EmployeeSchedule> list = new ArrayList<EmployeeSchedule>();
	    		 list.add(empSched);
	    		 map.put(rs.getInt("empId"), list);
	    	 }
        	
        }
        
        StringBuffer sql2 = new StringBuffer();
 		
 		sql2.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, es.updatedBy ");
 		sql2.append("FROM empSchedule es, employee e ");
 		sql2.append("WHERE es.empId = e.empId AND es.shiftingScheduleId in (2000, 2001) ");
 		//sql2.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ");
 		sql2.append("AND e.subUnitId = ");
 		sql2.append(subUnitId);
 		sql2.append(" ORDER BY es.scheduleDate desc");
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString());
  		
  		        
        ResultSet rs2 = ps2.executeQuery();
        
        
        
        while(rs2.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs2.getInt("empId"));
        	empSched.setEmpScheduleId(rs2.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs2.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs2.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs2.getString("lastname"));
        	
        	if(empSched.getShiftingScheduleId() == 2000){
        		empSched.setEmpShift("Paid - Rest Day");
        	} else if(empSched.getShiftingScheduleId() == 2001){
        		empSched.setEmpShift("Unpaid - Rest Day");
        	}
        	
        	empSched.setUpdatedBy(rs2.getInt("updatedBy"));
        	if(map.containsKey(rs2.getInt("empId"))){
        	
	    		 map.get(rs2.getInt("empId")).add(empSched);
	    	 } else {
	    		 List<EmployeeSchedule> list = new ArrayList<EmployeeSchedule>();
	    		 list.add(empSched);
	    		 map.put(rs2.getInt("empId"), list);
	    	 }
        	
        }
        
        ps.close();
        return map;
         
	}
    
    public List<EmployeeSchedule> getAllEmployeeScheduleCalendar(String personnelType) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
 		
 		sql.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, e.firstname, ss.description, es.updatedBy, ss.colorAssignment ");
 		sql.append("FROM empSchedule es, employee e, shiftingSchedule ss ");
 		sql.append("WHERE es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId ");
 		//sql.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ");
 		
 		if(personnelType != null && personnelType.length() > 0){
 			sql.append(" AND e.personnelType = '");
 	 		sql.append(personnelType);
 	 		sql.append("'");
 		}
 		
 		sql.append(" ORDER BY es.scheduleDate desc, es.empScheduleId, e.lastname");
 				
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
  		        
        ResultSet rs = ps.executeQuery();
        
        List<EmployeeSchedule> empSchedList = new ArrayList<EmployeeSchedule>();
        
        while(rs.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs.getInt("empId"));
        	empSched.setEmpScheduleId(rs.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs.getString("lastname") + ", " + rs.getString("firstname"));
        	empSched.setEmpShift(rs.getString("description"));
        	empSched.setUpdatedBy(rs.getInt("updatedBy"));
        	empSched.setColor(rs.getString("colorAssignment"));
        	
        	empSchedList.add(empSched);
        	
        }
        
        //For Rest Day and 
        
        StringBuffer sql2 = new StringBuffer();
 		
 		sql2.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, e.firstname, es.updatedBy ");
 		sql2.append("FROM empSchedule es, employee e ");
 		sql2.append("WHERE es.empId = e.empId AND es.shiftingScheduleId in (2000, 2001) ");
 		//sql2.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ");
 		
 		if(personnelType != null && personnelType.length() > 0){
 			sql.append(" AND e.personnelType = '");
 	 		sql.append(personnelType);
 	 		sql.append("'");
 		}
 		
 		sql2.append(" ORDER BY es.scheduleDate desc, es.empScheduleId, e.lastname");
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString());
  		
  		        
        ResultSet rs2 = ps2.executeQuery();
        
        
        
        while(rs2.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs2.getInt("empId"));
        	empSched.setEmpScheduleId(rs2.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs2.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs2.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs2.getString("lastname") + ", " + rs2.getString("firstname"));
        	
        	if(empSched.getShiftingScheduleId() == 2000){
        		empSched.setEmpShift("Paid - Rest Day");
        	} else if(empSched.getShiftingScheduleId() == 2001){
        		empSched.setEmpShift("Unpaid - Rest Day");
        	}
        	
        	empSched.setUpdatedBy(rs2.getInt("updatedBy"));
        	
        	empSchedList.add(empSched);
        	
        }
        
        
        
        /*
        
        //For Leave
        StringBuffer sql3 = new StringBuffer();
 		
        sql3.append("SELECT l.empId, l.leaveId, l.dateFrom, l.dateTo, e.lastname, e.firstname ");
        sql3.append("FROM leave l, employee e ");
 		sql3.append("WHERE l.empId = e.empId "); 		
 		sql3.append("AND e.superVisor1Id = ");
 		sql3.append(supervisorId);
 		sql3.append(" ORDER BY l.dateFrom, e.lastname");
 				
  		PreparedStatement ps3  = conn.prepareStatement(sql3.toString());
  		
  		        
        ResultSet rs3 = ps3.executeQuery();
        
        
        
        while(rs3.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs3.getInt("empId"));        	
        	empSched.setScheduleDate(rs3.getString("dateFrom"));
        	empSched.setToDate(rs3.getString("dateTo"));        	
        	empSched.setEmpName(rs3.getString("lastname") + ", " + rs3.getString("firstname"));        	
        	empSched.setEmpShift("On Leave");        	      	
        	empSchedList.add(empSched);        	
        }
        
        //For Out of Office
        StringBuffer sql4 = new StringBuffer();
 		
        sql4.append("SELECT l.empId, l.empOOOId, l.dateFrom, l.dateTo, e.lastname, e.firstname ");
        sql4.append("FROM empOutOfOffice l, employee e ");
        sql4.append("WHERE l.empId = e.empId "); 		
        sql4.append("AND e.superVisor1Id = ");
        sql4.append(supervisorId);
        sql4.append(" ORDER BY l.dateFrom, e.lastname");
 				
  		PreparedStatement ps4  = conn.prepareStatement(sql4.toString());
  		
  		        
        ResultSet rs4 = ps4.executeQuery();
        
        
        
        while(rs4.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs4.getInt("empId"));        	
        	empSched.setScheduleDate(rs4.getString("dateFrom"));
        	empSched.setToDate(rs4.getString("dateTo"));        	
        	empSched.setEmpName(rs4.getString("lastname") + ", " + rs4.getString("firstname"));        	
        	empSched.setEmpShift("On Training and Seminar");        	      	
        	empSchedList.add(empSched);        	
        }
        
        
        
        ps3.close();
        ps4.close();
        
        */
        ps.close();
        ps2.close();
        return empSchedList;
	}
	public List<EmployeeSchedule> getAllEmployeeScheduleCalendarBySectionId(int sectionId, String personnelType) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
 		
 		sql.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, e.firstname, ss.description, es.updatedBy, ss.colorAssignment ");
 		sql.append("FROM empSchedule es, employee e, shiftingSchedule ss ");
 		sql.append("WHERE es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId ");
 		//sql.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ");
 		
 		sql.append("AND e.sectionId = ");
	 	sql.append(sectionId);
 		if(personnelType != null && personnelType.length() > 0){
 			sql.append(" AND e.personnelType = '");
 	 		sql.append(personnelType);
 	 		sql.append("'");
 		}
 		
 		sql.append(" ORDER BY es.empScheduleId, es.scheduleDate desc, e.lastname");
 				
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
  		        
        ResultSet rs = ps.executeQuery();
        
        List<EmployeeSchedule> empSchedList = new ArrayList<EmployeeSchedule>();
        
        while(rs.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs.getInt("empId"));
        	empSched.setEmpScheduleId(rs.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs.getString("lastname") + ", " + rs.getString("firstname"));
        	empSched.setEmpShift(rs.getString("description"));
        	empSched.setUpdatedBy(rs.getInt("updatedBy"));
        	empSched.setColor(rs.getString("colorAssignment"));
        	
        	empSchedList.add(empSched);
        	
        }
        
        //For Rest Day and 
        
        StringBuffer sql2 = new StringBuffer();
 		
 		sql2.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, e.firstname, es.updatedBy ");
 		sql2.append("FROM empSchedule es, employee e ");
 		sql2.append("WHERE es.empId = e.empId AND es.shiftingScheduleId in (2000, 2001) ");
 		//sql2.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ");
 		
 		sql.append("AND e.sectionId = ");
	 	sql.append(sectionId);
	 	if(personnelType != null && personnelType.length() > 0){
 			sql.append(" AND e.personnelType = '");
 	 		sql.append(personnelType);
 	 		sql.append("'");
 		}
 		
 		sql2.append(" ORDER BY es.empScheduleId, es.scheduleDate desc, e.lastname");
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString());
  		
  		        
        ResultSet rs2 = ps2.executeQuery();
        
        
        
        while(rs2.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs2.getInt("empId"));
        	empSched.setEmpScheduleId(rs2.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs2.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs2.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs2.getString("lastname") + ", " + rs2.getString("firstname"));
        	
        	if(empSched.getShiftingScheduleId() == 2000){
        		empSched.setEmpShift("Paid - Rest Day");
        	} else if(empSched.getShiftingScheduleId() == 2001){
        		empSched.setEmpShift("Unpaid - Rest Day");
        	}
        	
        	empSched.setUpdatedBy(rs2.getInt("updatedBy"));
        	
        	empSchedList.add(empSched);
        	
        }
        
        ps.close();
        ps2.close();
        return empSchedList;
	}
	public List<EmployeeSchedule> getAllEmployeeScheduleCalendarByUnitId(int unitId, String personnelType) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
 		
 		sql.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, e.firstname, ss.description, es.updatedBy, ss.colorAssignment ");
 		sql.append("FROM empSchedule es, employee e, shiftingSchedule ss ");
 		sql.append("WHERE es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId ");
 		//sql.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ");
 		
 		sql.append("AND e.unitId = ");
	 	sql.append(unitId);
	 	if(personnelType != null && personnelType.length() > 0){
 			sql.append(" AND e.personnelType = '");
 	 		sql.append(personnelType);
 	 		sql.append("'");
 		}
 		
 		sql.append(" ORDER BY es.empScheduleId, es.scheduleDate desc, e.lastname");
 				
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
  		        
        ResultSet rs = ps.executeQuery();
        
        List<EmployeeSchedule> empSchedList = new ArrayList<EmployeeSchedule>();
        
        while(rs.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs.getInt("empId"));
        	empSched.setEmpScheduleId(rs.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs.getString("lastname") + ", " + rs.getString("firstname"));
        	empSched.setEmpShift(rs.getString("description"));
        	empSched.setUpdatedBy(rs.getInt("updatedBy"));
        	empSched.setColor(rs.getString("colorAssignment"));
        	
        	empSchedList.add(empSched);
        	
        }
        
        //For Rest Day and 
        
        StringBuffer sql2 = new StringBuffer();
 		
 		sql2.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, e.firstname, es.updatedBy ");
 		sql2.append("FROM empSchedule es, employee e ");
 		sql2.append("WHERE es.empId = e.empId AND es.shiftingScheduleId in (2000, 2001) ");
 		//sql2.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ");
 		
 		sql.append("AND e.unitId = ");
	 	sql.append(unitId);
	 	if(personnelType != null && personnelType.length() > 0){
 			sql.append(" AND e.personnelType = '");
 	 		sql.append(personnelType);
 	 		sql.append("'");
 		}
 		
 		sql2.append(" ORDER BY es.empScheduleId, es.scheduleDate desc, e.lastname");
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString());
  		
  		        
        ResultSet rs2 = ps2.executeQuery();
        
        
        
        while(rs2.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs2.getInt("empId"));
        	empSched.setEmpScheduleId(rs2.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs2.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs2.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs2.getString("lastname") + ", " + rs2.getString("firstname"));
        	
        	if(empSched.getShiftingScheduleId() == 2000){
        		empSched.setEmpShift("Paid - Rest Day");
        	} else if(empSched.getShiftingScheduleId() == 2001){
        		empSched.setEmpShift("Unpaid - Rest Day");
        	}
        	
        	empSched.setUpdatedBy(rs2.getInt("updatedBy"));
        	
        	empSchedList.add(empSched);
        	
        }
        
        ps.close();
        ps2.close();
        return empSchedList;
	}
	public List<EmployeeSchedule> getAllEmployeeScheduleCalendarBySubUnitId(int subUnitId, String personnelType) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
 		
 		sql.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, e.firstname, ss.description, es.updatedBy, ss.colorAssignment ");
 		sql.append("FROM empSchedule es, employee e, shiftingSchedule ss ");
 		sql.append("WHERE es.empId = e.empId AND es.shiftingScheduleId = ss.shiftingScheduleId ");
 		//sql.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ");
 		
 		sql.append("AND e.subUnitId = ");
	 	sql.append(subUnitId);
	 	if(personnelType != null && personnelType.length() > 0){
 			sql.append(" AND e.personnelType = '");
 	 		sql.append(personnelType);
 	 		sql.append("'");
 		}
 		
 		sql.append(" ORDER BY es.empScheduleId, es.scheduleDate desc, e.lastname");
 				
  		PreparedStatement ps  = conn.prepareStatement(sql.toString());
  		
  		        
        ResultSet rs = ps.executeQuery();
        
        List<EmployeeSchedule> empSchedList = new ArrayList<EmployeeSchedule>();
        
        while(rs.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs.getInt("empId"));
        	empSched.setEmpScheduleId(rs.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs.getString("lastname") + ", " + rs.getString("firstname"));
        	empSched.setEmpShift(rs.getString("description"));
        	empSched.setUpdatedBy(rs.getInt("updatedBy"));
        	empSched.setColor(rs.getString("colorAssignment"));
        	
        	empSchedList.add(empSched);
        	
        }
        
        //For Rest Day and 
        
        StringBuffer sql2 = new StringBuffer();
 		
 		sql2.append("SELECT es.empId, es.empScheduleId, es.scheduleDate, es.shiftingScheduleId, e.lastname, e.firstname, es.updatedBy ");
 		sql2.append("FROM empSchedule es, employee e ");
 		sql2.append("WHERE es.empId = e.empId AND es.shiftingScheduleId in (2000, 2001) ");
 		//sql2.append("AND MONTH(es.scheduleDate) = MONTH(SYSDATETIME()) ");
 		
 		sql.append("AND e.subUnitId = ");
	 	sql.append(subUnitId);
	 	if(personnelType != null && personnelType.length() > 0){
 			sql.append(" AND e.personnelType = '");
 	 		sql.append(personnelType);
 	 		sql.append("'");
 		}
 		
 		sql2.append(" ORDER BY es.empScheduleId, es.scheduleDate desc, e.lastname");
 				
  		PreparedStatement ps2  = conn.prepareStatement(sql2.toString());
  		
  		        
        ResultSet rs2 = ps2.executeQuery();
        
        
        
        while(rs2.next()){
        	EmployeeSchedule empSched = new EmployeeSchedule();
        	empSched.setEmpId(rs2.getInt("empId"));
        	empSched.setEmpScheduleId(rs2.getInt("empScheduleId"));
        	empSched.setScheduleDate(rs2.getString("scheduleDate"));
        	empSched.setShiftingScheduleId(rs2.getInt("shiftingScheduleId"));
        	empSched.setEmpName(rs2.getString("lastname") + ", " + rs2.getString("firstname"));
        	
        	if(empSched.getShiftingScheduleId() == 2000){
        		empSched.setEmpShift("Paid - Rest Day");
        	} else if(empSched.getShiftingScheduleId() == 2001){
        		empSched.setEmpShift("Unpaid - Rest Day");
        	}
        	
        	empSched.setUpdatedBy(rs2.getInt("updatedBy"));
        	
        	empSchedList.add(empSched);
        	
        }
        
        ps.close();
        ps2.close();
        return empSchedList;
	}
	
	
	
	//NEW
	public  int getAllCount() throws SQLException, Exception {
    	int count = 0;    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, empHourlyAttendance l WHERE e.empId = l.empId");   
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();	    
	    
	    if (rs.next()) {    	
	    	count = rs.getInt("ctr");
	    }
	    
	    ps.close();
	    rs.close();      	    
    	
    	return count;
    }
    
    public  int getAllCountBySectionId(int sectionId) throws SQLException, Exception {
    	int count = 0;    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, empHourlyAttendance l WHERE e.empId = l.empId AND e.sectionId = ");   	
    	sql.append(sectionId);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();	    
	    
	    if (rs.next()) {    	
	    	count = rs.getInt("ctr");
	    }
	    
	    ps.close();
	    rs.close();      	    
    	
    	return count;
    }
    
    public  int getAllCountByUnitId(int unitId) throws SQLException, Exception {
    	int count = 0;    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, empHourlyAttendance l WHERE e.empId = l.empId AND e.unitId = ");   	
    	sql.append(unitId);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();	    
	    
	    if (rs.next()) {    	
	    	count = rs.getInt("ctr");
	    }
	    
	    ps.close();
	    rs.close();      	    
    	
    	return count;
    }
    
    public  int getAllCountBySubUnitId(int subUnitId) throws SQLException, Exception {
    	int count = 0;    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, empHourlyAttendance l WHERE e.empId = l.empId AND e.subUnitId = ");   	
    	sql.append(subUnitId);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();	    
	    
	    if (rs.next()) {    	
	    	count = rs.getInt("ctr");
	    }
	    
	    ps.close();
	    rs.close();      	    
    	
    	return count;
    }
	
	public List<EmployeeHourlyAttendance> getAllHourlyAttendanceForApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "o.attendanceDate ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT o.empHourlyAttendanceId, o.empId, o.attendanceDate, o.noOfHours, o.status, o.approvedBy, o.creationDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empHourlyAttendance o WHERE e.empId = o.empId AND o.status in (0, 2, 3, 4, 5) ");
    	sql.append(" AND e.empId <> ");
    	sql.append(empId);
    	sql.append(" ) SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeHourlyAttendance> list = new ArrayList<EmployeeHourlyAttendance>();
	    
	    while (rs.next()) {
	    	EmployeeHourlyAttendance model = new EmployeeHourlyAttendance();
	    	model.setEmpHourlyAttendanceId(rs.getInt("empHourlyAttendanceId"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAttendanceDate(rs.getString("attendanceDate"));
	    	model.setNoOfHours(rs.getInt("noOfHours"));
	    	model.setStatus(rs.getInt("status"));
	    	model.setApprovedBy(rs.getInt("approvedBy"));
	    	model.setCreatedDate(rs.getDate("creationDate"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   

 	} 
    
    public List<EmployeeHourlyAttendance> getAllHourlyAttendanceForHRApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "o.attendanceDate ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT o.empHourlyAttendanceId, o.empId, o.attendanceDate, o.noOfHours, o.status, o.approvedBy, o.creationDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empHourlyAttendance o WHERE e.empId = o.empId AND o.status in (0, 2, 3, 4) ");
    	sql.append(" AND e.empId <> ");
    	sql.append(empId);
    	sql.append(" ) SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeHourlyAttendance> list = new ArrayList<EmployeeHourlyAttendance>();
	    
	    while (rs.next()) {
	    	EmployeeHourlyAttendance model = new EmployeeHourlyAttendance();
	    	model.setEmpHourlyAttendanceId(rs.getInt("empHourlyAttendanceId"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAttendanceDate(rs.getString("attendanceDate"));
	    	model.setNoOfHours(rs.getInt("noOfHours"));
	    	model.setStatus(rs.getInt("status"));
	    	model.setApprovedBy(rs.getInt("approvedBy"));
	    	model.setCreatedDate(rs.getDate("creationDate"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   

 	} 
    
    public List<EmployeeHourlyAttendance> getAllHourlyAttendanceForApprovalBySectionId(int empId, int sectionId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "o.attendanceDate ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT o.empHourlyAttendanceId, o.empId, o.attendanceDate, o.noOfHours, o.status, o.approvedBy, o.creationDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empHourlyAttendance o WHERE e.empId = o.empId AND o.status in (0, 2, 3) AND e.sectionId = ");
    	sql.append(sectionId);
    	sql.append(" AND e.empId <> ");
    	sql.append(empId);
    	sql.append(" ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeHourlyAttendance> list = new ArrayList<EmployeeHourlyAttendance>();
	    
	    while (rs.next()) {
	    	EmployeeHourlyAttendance model = new EmployeeHourlyAttendance();
	    	model.setEmpHourlyAttendanceId(rs.getInt("empHourlyAttendanceId"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAttendanceDate(rs.getString("attendanceDate"));
	    	model.setNoOfHours(rs.getInt("noOfHours"));
	    	model.setStatus(rs.getInt("status"));
	    	model.setApprovedBy(rs.getInt("approvedBy"));
	    	model.setCreatedDate(rs.getDate("creationDate"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   

 	} 
    
    public List<EmployeeHourlyAttendance> getAllHourlyAttendanceForApprovalByUnitId(int empId, int unitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "o.attendanceDate ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT o.empHourlyAttendanceId, o.empId, o.attendanceDate, o.noOfHours, o.status, o.approvedBy, o.creationDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empHourlyAttendance o WHERE e.empId = o.empId AND o.status in (0, 2) AND e.unitId = ");
    	sql.append(unitId);
    	sql.append(" AND e.empId <> ");
    	sql.append(empId);
    	sql.append(" ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeHourlyAttendance> list = new ArrayList<EmployeeHourlyAttendance>();
	    
	    while (rs.next()) {
	    	EmployeeHourlyAttendance model = new EmployeeHourlyAttendance();
	    	model.setEmpHourlyAttendanceId(rs.getInt("empHourlyAttendanceId"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAttendanceDate(rs.getString("attendanceDate"));
	    	model.setNoOfHours(rs.getInt("noOfHours"));
	    	model.setStatus(rs.getInt("status"));
	    	model.setApprovedBy(rs.getInt("approvedBy"));
	    	model.setCreatedDate(rs.getDate("creationDate"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   

 	} 


    public List<EmployeeHourlyAttendance> getAllHourlyAttendanceForApprovalBySubUnitId(int empId, int subUnitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "o.attendanceDate ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT o.empHourlyAttendanceId, o.empId, o.attendanceDate, o.noOfHours, o.status, o.approvedBy, o.creationDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empHourlyAttendance o WHERE e.empId = o.empId AND o.status = 0 AND e.subUnitId = ");
    	sql.append(subUnitId);
    	sql.append(" AND e.empId <> ");
    	sql.append(empId);
    	sql.append(" ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeHourlyAttendance> list = new ArrayList<EmployeeHourlyAttendance>();
	    
	    while (rs.next()) {
	    	EmployeeHourlyAttendance model = new EmployeeHourlyAttendance();
	    	model.setEmpHourlyAttendanceId(rs.getInt("empHourlyAttendanceId"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setAttendanceDate(rs.getString("attendanceDate"));
	    	model.setNoOfHours(rs.getInt("noOfHours"));
	    	model.setStatus(rs.getInt("status"));
	    	model.setApprovedBy(rs.getInt("approvedBy"));
	    	model.setCreatedDate(rs.getDate("creationDate"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   

 	} 
    
    public void approveHourlyAttendance(EmployeeHourlyAttendance model) throws SQLException, Exception {
    	
    	String sql = "UPDATE empHourlyAttendance "
				+ "set approvedBy=?, "
				+ "status=? "
				+ "WHERE empHourlyAttendanceId=?";
		PreparedStatement ps = conn.prepareStatement(sql); 
		
		ps.setInt(1, model.getApprovedBy());		
		ps.setInt(2, model.getStatus());
		ps.setInt(3, model.getEmpHourlyAttendanceId());
		ps.executeUpdate();
		conn.commit();
		ps.close();
	
    }
    
    
    /* START TIME ENTRY */
    
    public TimeEntry getTimeEntryById(int id) throws SQLException, Exception {	
		StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.shiftId, ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut, ss.description, ss.shiftType ");
    	sql.append("FROM empTimeEntry ee, employee e, shiftingSchedule ss ");
    	sql.append("WHERE ee.empId = e.empId AND ee.shiftId = ss.shiftingScheduleId AND e.timeEntryId = ");
    	sql.append(id);    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    	    
	    TimeEntry model = new TimeEntry();
	    
	    if (rs.next()) {	    	 
	    	 model.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 model.setEmpId(rs.getInt("empId"));
	    	 model.setEmpName(rs.getString("name"));
	    	 model.setShiftScheduleDesc(rs.getString("description"));
	    	 model.setShiftType(rs.getString("shiftType"));	    
	    	 model.setTimeIn(rs.getString("timeIn"));
	    	 model.setTimeOut(rs.getString("timeOut"));
	    	 model.setShiftScheduleId(rs.getInt("shiftId"));
	    	 model.setShiftId(rs.getInt("shiftId"));
	    }	    
	    
	    ps.close();
	    rs.close();      
	    return model; 
	}
    
//    public void generateTimeInAndOutForTesting ()  throws SQLException, Exception {
//    	StringBuffer sql = new StringBuffer();
//    	
//    	sql.append("SELECT * FROM empSchedule");    	
//    	
//		PreparedStatement ps = conn.prepareStatement(sql.toString());
//
//	    ResultSet rs = ps.executeQuery();
//	    
//	    while (rs.next()) {
//	    	 if()
//	    	 
//	    }	    
//	    ps.close();
//	    rs.close();      
//
//    }
//    
//    private Map<Integer, String> getTimeEntryList () throws SQLException, Exception {
//    	StringBuffer sql = new StringBuffer();
//    	
//    	Map<Integer, String> timeEntryMap = new HashMap<Integer, String>();
//    	
//    	sql.append("SELECT * FROM empTimeEntry");    	
//    	
//		PreparedStatement ps = conn.prepareStatement(sql.toString());
//
//	    ResultSet rs = ps.executeQuery();
//	    
//	    while (rs.next()) {
//	    	 	    	 
//	    }	    
//	    ps.close();
//	    rs.close();      
//
//    }
    
    public  Map<Integer, List<TimeEntry>> getAllTimeEntryByClockDateMap(String clockDate) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.shiftId, ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut, ss.description, ss.shiftType ");
    	sql.append("FROM empTimeEntry ee, employee e, shiftingSchedule ss ");
    	sql.append("WHERE ee.empId = e.empId AND ee.shiftId = ss.shiftingScheduleId AND convert(varchar(26),ee.timeIn,23) = '");
    	sql.append(clockDate);
    	sql.append("' ORDER BY ee.timeIn");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    Map<Integer, List<TimeEntry>> map = new HashMap<Integer, List<TimeEntry>>();	    
	    
	    while (rs.next()) {
	    	 TimeEntry timeEntry = new TimeEntry();
	    	 
	    	 timeEntry.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 timeEntry.setEmpId(rs.getInt("empId"));
	    	 timeEntry.setEmpName(rs.getString("name"));
	    	 timeEntry.setShiftScheduleDesc(rs.getString("description"));
	    	 timeEntry.setShiftType(rs.getString("shiftType"));	    
	    	 timeEntry.setTimeIn(rs.getString("timeIn"));
	    	 timeEntry.setTimeOut(rs.getString("timeOut"));
	    	 timeEntry.setShiftScheduleId(rs.getInt("shiftId"));
	    	 timeEntry.setShiftId(rs.getInt("shiftId"));
	    	 
	    	 if(rs.getString("timeIn") != null && rs.getString("timeIn").length() > 0){
	    		 String timeInHrsText = rs.getString("timeIn").substring(11, 16);
	    		 
	    		 timeEntry.setTimeInHrsText(timeInHrsText);
	    	 } else {
	    		 timeEntry.setTimeInHrsText("00:00");
	    	 }
	    	 
	    	 if(rs.getString("timeOut") != null && rs.getString("timeOut").length() > 0){
	    		 String timeOutHrsText = rs.getString("timeIn").substring(11, 16);
	    		 
	    		 timeEntry.setTimeOutHrsText(timeOutHrsText);	    		 
	    	 } else {
	    		 timeEntry.setTimeOutHrsText("00:00");	  
	    	 }
	    	 
	    	 
	    	 
	    	 if(map.containsKey(rs.getInt("empId"))){
	    		 map.get(rs.getInt("empId")).add(timeEntry);
	    	 } else {
	    		 List<TimeEntry> list = new ArrayList<TimeEntry>();
	    		 list.add(timeEntry);
	    		 map.put(rs.getInt("empId"), list);
	    	 }    	 

	    }	    
	    ps.close();
	    rs.close();      
	    return map;     

	}
    
    public  Map<Integer, List<TimeEntry>> getAllTimeEntryBySectionIdAndClockDateMap(int sectionId, String clockDate) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut ");
    	sql.append("FROM empTimeEntry ee, employee e ");
    	sql.append("WHERE ee.empId = e.empId AND e.sectionId = ");
    	sql.append(sectionId);
    	sql.append(" AND convert(varchar(26),ee.timeIn,23) = '");
    	sql.append(clockDate);
    	sql.append(" ORDER BY ee.timeIn");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    Map<Integer, List<TimeEntry>> map = new HashMap<Integer, List<TimeEntry>>();	    
	    
	    while (rs.next()) {
	    	 TimeEntry timeEntry = new TimeEntry();
	    	 timeEntry.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 timeEntry.setEmpId(rs.getInt("empId"));
	    	 timeEntry.setEmpName(rs.getString("name"));	    
	    	 timeEntry.setTimeIn(rs.getString("timeIn"));
	    	 timeEntry.setTimeOut(rs.getString("timeOut"));
	    	 
	    	 if(map.containsKey(rs.getInt("empId"))){
	    		 map.get(rs.getInt("empId")).add(timeEntry);
	    	 } else {
	    		 List<TimeEntry> list = new ArrayList<TimeEntry>();
	    		 list.add(timeEntry);
	    		 map.put(rs.getInt("empId"), list);
	    	 }    	 

	    }		    

	    
	    ps.close();
	    rs.close();  

	    return map;     

	}
    
    public  Map<Integer, List<TimeEntry>> getAllTimeEntryByUnitIdAndClockDateMap(int unitId, String clockDate) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut ");
    	sql.append("FROM empTimeEntry ee, employee e ");
    	sql.append("WHERE ee.empId = e.empId AND e.unitId = ");
    	sql.append(unitId);
    	sql.append(" AND convert(varchar(26),ee.timeIn,23) = '");
    	sql.append(clockDate);
    	sql.append(" ORDER BY ee.timeIn");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    Map<Integer, List<TimeEntry>> map = new HashMap<Integer, List<TimeEntry>>();	    
	    
	    while (rs.next()) {
	    	 TimeEntry timeEntry = new TimeEntry();
	    	 timeEntry.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 timeEntry.setEmpId(rs.getInt("empId"));
	    	 timeEntry.setEmpName(rs.getString("name"));	    
	    	 timeEntry.setTimeIn(rs.getString("timeIn"));
	    	 timeEntry.setTimeOut(rs.getString("timeOut"));
	    	 
	    	 if(map.containsKey(rs.getInt("empId"))){
	    		 map.get(rs.getInt("empId")).add(timeEntry);
	    	 } else {
	    		 List<TimeEntry> list = new ArrayList<TimeEntry>();
	    		 list.add(timeEntry);
	    		 map.put(rs.getInt("empId"), list);
	    	 }    	 

	    }		    

	    
	    ps.close();
	    rs.close();  

	    return map;     

	}
    
    public  Map<Integer, List<TimeEntry>> getAllTimeEntryBySubUnitIdAndClockDateMap(int subUnitId, String clockDate) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT ee.timeEntryId, e.empId, (e.lastname + ',' + e.firstname) as name, ee.timeIn, ee.timeOut ");
    	sql.append("FROM empTimeEntry ee, employee e ");
    	sql.append("WHERE ee.empId = e.empId AND e.subUnitId = ");
    	sql.append(subUnitId);
    	sql.append(" AND convert(varchar(26),ee.timeIn,23) = '");
    	sql.append(clockDate);    	
    	sql.append("' ORDER BY ee.timeIn");
    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    Map<Integer, List<TimeEntry>> map = new HashMap<Integer, List<TimeEntry>>();	    
	    
	    while (rs.next()) {
	    	 TimeEntry timeEntry = new TimeEntry();
	    	 timeEntry.setTimeEntryId(rs.getInt("timeEntryId"));
	    	 timeEntry.setEmpId(rs.getInt("empId"));
	    	 timeEntry.setEmpName(rs.getString("name"));	    
	    	 timeEntry.setTimeIn(rs.getString("timeIn"));
	    	 timeEntry.setTimeOut(rs.getString("timeOut"));
	    	 
	    	 if(map.containsKey(rs.getInt("empId"))){
	    		 map.get(rs.getInt("empId")).add(timeEntry);
	    	 } else {
	    		 List<TimeEntry> list = new ArrayList<TimeEntry>();
	    		 list.add(timeEntry);
	    		 map.put(rs.getInt("empId"), list);
	    	 }    	 

	    }		    

	    
	    ps.close();
	    rs.close();  

	    return map;     

	}
	
    
    /* END TIME ENTRY */
	

}
