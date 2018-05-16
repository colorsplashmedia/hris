package dai.hris.dao.payroll;

import java.math.BigDecimal;
import java.sql.Connection;
//import java.sql.Date;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dai.hris.dao.DBConstants;
import dai.hris.model.EmployeeHourlyAttendance;
import dai.hris.model.EmployeeOutOfOffice;
import dai.hris.model.EmployeePayrollRunExt;
import dai.hris.model.NightDifferential;
import dai.hris.model.SystemParameters;
import dai.hris.model.TimeEntry;
import dai.hris.service.payroll.PayrollConstants;


public class PayrollRunDAO {

	private Connection conn = null;

	public PayrollRunDAO() {    	
		try {
			/* MS SQL CODE */    		
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
			conn.setAutoCommit(false);
		} catch (SQLException sqle) {
			System.out.println("PayrollPeriodDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("PayrollPeriodDAO :" + e.getMessage());
		}
	}
	
	
	
	//TODO
	public static void main(String[] args) {
		
		
		try {		
			PayrollRunDAO dao = new PayrollRunDAO();
						
			List<EmployeePayrollRunExt> modelList = dao.getBasicDataForEmployeeListByPayPeriod(1, "M");
			
			for(EmployeePayrollRunExt model : modelList){
				
				List<TimeEntry> scheduleList = dao.getEmployeeShedule(model);
				List<TimeEntry> attendanceList = dao.getEmployeeAttendance(model.getEmpId(), model.getFromDate().toString(), model.getToDate().toString());
				
				Map<String, TimeEntry> timeEntryMap = new HashMap<String, TimeEntry>();
				
				for(TimeEntry attendance: attendanceList) {
					timeEntryMap.put(attendance.getTimeIn().substring(0, 10), attendance);
				}
				
				//dao.getAbsenceAmount(timeEntryMap, model, model.getFromDate(), model.getToDate());
				dao.getNightDiffPay(timeEntryMap, scheduleList, model);
			}
						
			
			
			
		} catch (Exception e) {
			System.out.print("Exception: " + e.getMessage());
		}
	}
	
	private void deleteNightDiffDetails(int empId, int payrollPeriodId)  throws SQLException, Exception {
		String sql = "DELETE FROM empNightDiff WHERE empId = ? AND payrollPeriodId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, empId);
        ps.setInt(2, payrollPeriodId);
        
        ps.executeUpdate();
        conn.commit();
        ps.close();
	}
	
	public void deletePayrollRun(int payrollPeriodId)  throws SQLException, Exception {
		String sql = "DELETE FROM employeePayrollRun WHERE payrollPeriodId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, payrollPeriodId);
        
        ps.executeUpdate();
        conn.commit();
        ps.close();
	}
	
	public List<TimeEntry> getEmployeeShedule(EmployeePayrollRunExt emp)   throws SQLException, Exception {
		List<TimeEntry> empSchedList = new ArrayList<TimeEntry>();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT es.scheduleDate, es.shiftingScheduleId, es.empId, s.timeIn, s.shiftType, s.timeOut FROM empSchedule es, shiftingSchedule s WHERE es.empId = ");
		sql.append(emp.getEmpId());
		sql.append(" AND es.shiftingScheduleId = s.shiftingScheduleId AND CONVERT(DATE,es.scheduleDate) BETWEEN '");
		sql.append(emp.getFromDate());
		sql.append("'");
		sql.append(" AND '");
		sql.append(emp.getToDate());
		sql.append("' ORDER BY es.scheduleDate");
		
		System.out.println("SQL getSchedule - getNightDiffPay(): " + sql.toString());
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {			
			
			TimeEntry empSched = new TimeEntry();
			
			empSched.setEmpId(rs.getInt("empId"));
			empSched.setShiftScheduleId(rs.getInt("shiftingScheduleId"));
			empSched.setScheduleDate(rs.getString("scheduleDate"));
			empSched.setTimeIn(rs.getString("timeIn"));
			empSched.setTimeOut(rs.getString("timeOut"));
			empSched.setShiftType(rs.getString("shiftType"));
			
			empSchedList.add(empSched);
		}
		
		return empSchedList;
		
	}
	
	//TODO - Done just need to verify on the weekend
	public BigDecimal getNightDiffPay(Map<String, TimeEntry> attendanceMap, List<TimeEntry> scheduleList, EmployeePayrollRunExt emp)  throws SQLException, Exception {
		BigDecimal nightDiffPayTotal = BigDecimal.ZERO;
		
		if("N".equals(emp.getHasNightDifferential())){
			return nightDiffPayTotal;
		}
				
		double nightDiff1 = 0;
		double nightDiff2 = 0;
		double totalNightDiff = 0;
		
		deleteNightDiffDetails(emp.getEmpId(), emp.getPayrollPeriodId());	
		
		TimeEntry attendance = null;
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for (TimeEntry empSched: scheduleList) {			
			String scheduleStr = empSched.getScheduleDate();
			
			if(attendanceMap.containsKey(scheduleStr)){
				//Employee is present
				attendance = attendanceMap.get(scheduleStr);
				
				String timeInSched = scheduleStr + " " + empSched.getTimeIn().substring(0,8);
				String timeOutSched = scheduleStr + " " + empSched.getTimeOut().substring(0,8);
				String timeIn = attendance.getTimeIn().substring(0,19);
				String timeOut = attendance.getTimeOut().substring(0,19);
				
				Date timeInSchedDate = sdf.parse(timeInSched);
				Date timeOutSchedDate = sdf.parse(timeOutSched);
				Date timeInDate = sdf.parse(timeIn);
				Date timeOutDate = sdf.parse(timeOut);
				
				if(timeOutSchedDate.after(timeInSchedDate)){
					//Time in schedule if the same date as timeOut do nothing
				} else {
					//Time Out schedule is the following day
					timeOutSched = incrementDate(scheduleStr) + " " + empSched.getTimeOut().substring(0,10);
					timeOutSchedDate = sdf.parse(timeOutSched);
				}
				
				//Check if schedule time in is after actual time in. If yes then use schedule time in otherwise use actual time in
				if(timeInSchedDate.after(timeInDate)){
					//use timein schedule
					timeInDate = sdf.parse(timeInSched);
				}
				
				//Check if actual time out is after schedule time out. If yes then use schedule time out otherwise use actual time out
				if(timeOutDate.after(timeOutSchedDate)){
					//use timein schedule
					timeOutDate = sdf.parse(timeOutSched);
				}
				
				//OLD Code
				//ND for the day and next day
				String ndAM = attendance.getTimeIn().substring(0,10) + " 06:00:00";
				String ndPM = attendance.getTimeIn().substring(0,10) + " 22:00:00";
				String ndNextDayAM = incrementDate(attendance.getTimeIn()) + " 06:00:00";
				String midNightNextDay = incrementDate(attendance.getTimeIn().substring(0,10)) + " 00:00:00";
				
				String day1 = attendance.getTimeIn().substring(0,10);
				String day2 = incrementDate(attendance.getTimeIn().substring(0,10));			
				
				Date ndAMDate = sdf.parse(ndAM);
				Date ndPMDate = sdf.parse(ndPM);
				Date ndNextDayAMDate = sdf.parse(ndNextDayAM);
				
				Date midNightNextDayDate = sdf.parse(midNightNextDay);
				
				if(timeInDate.after(ndAMDate)){
					//time in is after 6AM same date
					if(timeInDate.before(ndPMDate)){
						//time in is before 10PM same date
						if(timeOutDate.after(ndPMDate)){
							//It means there is ND. Time out is after 10PM same date
							if(timeOutDate.after(ndNextDayAMDate)){
								nightDiff1 = 2;
								nightDiff2 = 6;
								totalNightDiff = 8;
							} else {
								//time out is before 6AM the following date
								if(timeOutDate.after(midNightNextDayDate)){
									//time out is after 12MN the following day
									//time out - 10PM previous day
									nightDiff1 = 2;
									long totalDiffSeconds = (timeOutDate.getTime() - ndPMDate.getTime()) / 1000;
									int totalDiffHours = (int) totalDiffSeconds / 3600;    
									totalDiffSeconds = totalDiffSeconds % 3600;
									int mins = (int) totalDiffSeconds / 60;

									double finalHours = 0;
									
									if(mins >= 15){
										if(mins >= 30){
											if(mins >= 45){
												finalHours = totalDiffHours + 0.75;
											} else {
												finalHours = totalDiffHours + 0.5;
											}
										} else {
											finalHours = totalDiffHours + 0.25;
										}
									} else {
										finalHours = totalDiffHours;
									}
									
									totalNightDiff = finalHours; 
									nightDiff2 = totalNightDiff - nightDiff1;
								} else {
									//time out is before or equal 12MN the following day
									
									long totalDiffSeconds = (timeOutDate.getTime() - ndPMDate.getTime()) / 1000;
									int totalDiffHours = (int) totalDiffSeconds / 3600;    
									totalDiffSeconds = totalDiffSeconds % 3600;
									int mins = (int) totalDiffSeconds / 60;

									double finalHours = 0;
									
									if(mins >= 15){
										if(mins >= 30){
											if(mins >= 45){
												finalHours = totalDiffHours + 0.75;
											} else {
												finalHours = totalDiffHours + 0.5;
											}
										} else {
											finalHours = totalDiffHours + 0.25;
										}
									} else {
										finalHours = totalDiffHours;
									}
									
									totalNightDiff = finalHours; 
									nightDiff1 = finalHours;
									nightDiff2 = 0;
								}
								
								
								
							}
						} else {
							//No ND since timeout is before 10PM same date
							nightDiff1 = 0;
							nightDiff2 = 0;
							totalNightDiff = 0;
						}
					} else {					
						//time in is after 10PM then definite ND
						if(timeOutDate.before(ndNextDayAMDate) || timeOutDate.compareTo(ndNextDayAMDate) == 0){
							//time out - time in 
							long ndDiffSeconds1st = (midNightNextDayDate.getTime() - timeInDate.getTime()) / 1000;
							int totalDiffHours1st = (int) ndDiffSeconds1st / 3600;    
							ndDiffSeconds1st = ndDiffSeconds1st % 3600;
							int mins1st = (int) ndDiffSeconds1st / 60;
							
							double finalHours1st = 0;
							
							if(mins1st >= 15){
								if(mins1st >= 30){
									if(mins1st >= 45){
										finalHours1st = totalDiffHours1st + 0.75;
									} else {
										finalHours1st = totalDiffHours1st + 0.5;
									}
								} else {
									finalHours1st = totalDiffHours1st + 0.25;
								}
							} else {
								finalHours1st = totalDiffHours1st;
							}
							
							long ndDiffSeconds2nd = (timeOutDate.getTime() - midNightNextDayDate.getTime()) / 1000;
							int totalDiffHours2nd = (int) ndDiffSeconds2nd / 3600;    
							ndDiffSeconds2nd = ndDiffSeconds2nd % 3600;
							int mins2nd = (int) ndDiffSeconds2nd / 60;
							
							double finalHours2nd = 0;
							
							if(mins2nd >= 15){
								if(mins2nd >= 30){
									if(mins2nd >= 45){
										finalHours2nd = totalDiffHours2nd + 0.75;
									} else {
										finalHours2nd = totalDiffHours2nd + 0.5;
									}
								} else {
									finalHours2nd = totalDiffHours2nd + 0.25;
								}
							} else {
								finalHours2nd = totalDiffHours2nd;
							}
							
							long totalDiffSeconds = (timeOutDate.getTime() - timeInDate.getTime()) / 1000;
							int totalDiffHours = (int) totalDiffSeconds / 3600;    
							
							totalDiffSeconds = totalDiffSeconds % 3600;
							int mins = (int) totalDiffSeconds / 60;
							
							double finalHours = 0;
							
							if(mins >= 15){
								if(mins >= 30){
									if(mins >= 45){
										finalHours = totalDiffHours + 0.75;
									} else {
										finalHours = totalDiffHours + 0.5;
									}
								} else {
									finalHours = totalDiffHours + 0.25;
								}
							} else {
								finalHours = totalDiffHours;
							}
							
							nightDiff1 = finalHours1st;
							nightDiff2 = finalHours2nd;
							totalNightDiff = finalHours;
							
						} else {
							
							long ndDiffSeconds1st = (midNightNextDayDate.getTime() - timeInDate.getTime()) / 1000;
							int totalDiffHours1st = (int) ndDiffSeconds1st / 3600;    
							ndDiffSeconds1st = ndDiffSeconds1st % 3600;
							int mins1st = (int) ndDiffSeconds1st / 60;
							
							double finalHours1st = 0;
							
							if(mins1st >= 15){
								if(mins1st >= 30){
									if(mins1st >= 45){
										finalHours1st = totalDiffHours1st + 0.75;
									} else {
										finalHours1st = totalDiffHours1st + 0.5;
									}
								} else {
									finalHours1st = totalDiffHours1st + 0.25;
								}
							} else {
								finalHours1st = totalDiffHours1st;
							}
							
							//6AM - timeIn
							long totalDiffSeconds = (ndNextDayAMDate.getTime() - timeInDate.getTime()) / 1000;
							int totalDiffHours = (int) totalDiffSeconds / 3600; 
							
							totalDiffSeconds = totalDiffSeconds % 3600;
							int mins = (int) totalDiffSeconds / 60;
							
							double finalHours = 0;
							
							if(mins >= 15){
								if(mins >= 30){
									if(mins >= 45){
										finalHours = totalDiffHours + 0.75;
									} else {
										finalHours = totalDiffHours + 0.5;
									}
								} else {
									finalHours = totalDiffHours + 0.25;
								}
							} else {
								finalHours = totalDiffHours;
							}
							
							nightDiff1 = finalHours1st;
							nightDiff2 = 6;
							totalNightDiff = finalHours;
						}
						
					}
					
				} else {
					//6AM - timeIn
					long totalDiffSeconds = (ndAMDate.getTime() - timeInDate.getTime()) / 1000;
					int totalDiffHours = (int) totalDiffSeconds / 3600;  
					
					totalDiffSeconds = totalDiffSeconds % 3600;
					int mins = (int) totalDiffSeconds / 60;
					
					double finalHours = 0;
					
					if(mins >= 15){
						if(mins >= 30){
							if(mins >= 45){
								finalHours = totalDiffHours + 0.75;
							} else {
								finalHours = totalDiffHours + 0.5;
							}
						} else {
							finalHours = totalDiffHours + 0.25;
						}
					} else {
						finalHours = totalDiffHours;
					}
					
					nightDiff1 = finalHours;
					nightDiff2 = 0;
					totalNightDiff = finalHours;
				}
				
				
				System.out.println("nightDiff1: " + nightDiff1);
				System.out.println("nightDiff2: " + nightDiff2);
				System.out.println("totalNightDiff: " + totalNightDiff);
				System.out.println("======================================");
				
				BigDecimal finalNightDiffPay = BigDecimal.ZERO;
				BigDecimal nightDiffPay1 = BigDecimal.ZERO;
				BigDecimal nightDiffPay2 = BigDecimal.ZERO;
				
				if(nightDiff2 > 0){
					//This means ND if for day1 and day2
					String holidayType1 = isTimeEntryHoliday(day1);
					String holidayType2 = isTimeEntryHoliday(day2);
					
					double holidayRate = 0;
					
					if(holidayType1 != null && holidayType1.length() > 0 && nightDiff1 > 0) {
						if(PayrollConstants.HOLIDAY_REGULAR_INDICATOR.equals(holidayType1)) {
							nightDiffPay1 = new BigDecimal(nightDiff1 * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE * PayrollConstants.NIGHTDIFF_REGULARHOLIDAY_RATE);
							holidayRate = PayrollConstants.NIGHTDIFF_REGULARHOLIDAY_RATE;
						} else if(PayrollConstants.HOLIDAY_SPECIAL_INDICATOR.equals(holidayType1)) {
							nightDiffPay1 = new BigDecimal(nightDiff1 * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE * PayrollConstants.NIGHTDIFF_SPECIALHOLIDAY_RATE);
							holidayRate = PayrollConstants.NIGHTDIFF_SPECIALHOLIDAY_RATE;
						} else if(PayrollConstants.HOLIDAY_DOUBLE_INDICATOR.equals(holidayType1)) {
							nightDiffPay1 = new BigDecimal(nightDiff1 * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE * PayrollConstants.NIGHTDIFF_DOUBLEHOLIDAY_RATE);
							holidayRate = PayrollConstants.NIGHTDIFF_DOUBLEHOLIDAY_RATE;
						}
						
						NightDifferential nightDiff = new NightDifferential();
						
						nightDiff.setDateRendered(day1);
						nightDiff.setEmpId(emp.getEmpId());
						nightDiff.setHolidayRate(holidayRate);
						nightDiff.setHourlyRate(emp.getHourlyRate().doubleValue());
						nightDiff.setNightDiffAmount(nightDiffPay1);
						nightDiff.setNoOfHours(nightDiff1);
						nightDiff.setPayrollPeriodId(emp.getPayrollPeriodId());
						
						//Insert Night Shift Details
						insertNightShiftDetails(nightDiff);
					} else {
						//NOT A HOLIDAY
						if(nightDiff1 > 0) {
							nightDiffPay1 = new BigDecimal(nightDiff1 * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE);
							
							NightDifferential nightDiff = new NightDifferential();
							
							nightDiff.setDateRendered(day1);
							nightDiff.setEmpId(emp.getEmpId());
							nightDiff.setHolidayRate(holidayRate);
							nightDiff.setHourlyRate(emp.getHourlyRate().doubleValue());
							nightDiff.setNightDiffAmount(nightDiffPay1);
							nightDiff.setNoOfHours(nightDiff1);
							nightDiff.setPayrollPeriodId(emp.getPayrollPeriodId());
							
							//Insert Night Shift Details
							insertNightShiftDetails(nightDiff);							
							
						}
					}
					
					nightDiffPayTotal = nightDiffPayTotal.add(nightDiffPay1);
					
					holidayRate = 0;
					
					if(holidayType2 != null && holidayType2.length() > 0 && nightDiff2 > 0) {
						if(PayrollConstants.HOLIDAY_REGULAR_INDICATOR.equals(holidayType2)) {
							nightDiffPay2 = new BigDecimal(nightDiff2 * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE * PayrollConstants.NIGHTDIFF_REGULARHOLIDAY_RATE);
							holidayRate = PayrollConstants.NIGHTDIFF_REGULARHOLIDAY_RATE;
						} else if(PayrollConstants.HOLIDAY_SPECIAL_INDICATOR.equals(holidayType2)) {
							nightDiffPay2 = new BigDecimal(nightDiff2 * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE * PayrollConstants.NIGHTDIFF_SPECIALHOLIDAY_RATE);
							holidayRate = PayrollConstants.NIGHTDIFF_SPECIALHOLIDAY_RATE;
						} else if(PayrollConstants.HOLIDAY_DOUBLE_INDICATOR.equals(holidayType2)) {
							nightDiffPay2 = new BigDecimal(nightDiff2 * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE * PayrollConstants.NIGHTDIFF_DOUBLEHOLIDAY_RATE);
							holidayRate = PayrollConstants.NIGHTDIFF_DOUBLEHOLIDAY_RATE;
						}
						
						NightDifferential nightDiff = new NightDifferential();
						
						nightDiff.setDateRendered(day2);
						nightDiff.setEmpId(emp.getEmpId());
						nightDiff.setHolidayRate(holidayRate);
						nightDiff.setHourlyRate(emp.getHourlyRate().doubleValue());
						nightDiff.setNightDiffAmount(nightDiffPay2);
						nightDiff.setNoOfHours(nightDiff2);
						nightDiff.setPayrollPeriodId(emp.getPayrollPeriodId());
						
						//Insert Night Shift Details
						insertNightShiftDetails(nightDiff);							
						
					} else {
						//NOT A HOLIDAY
						if(nightDiff2 > 0) {
							nightDiffPay2 = new BigDecimal(nightDiff2 * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE);
							
							NightDifferential nightDiff = new NightDifferential();
							
							nightDiff.setDateRendered(day2);
							nightDiff.setEmpId(emp.getEmpId());
							nightDiff.setHolidayRate(holidayRate);
							nightDiff.setHourlyRate(emp.getHourlyRate().doubleValue());
							nightDiff.setNightDiffAmount(nightDiffPay2);
							nightDiff.setNoOfHours(nightDiff2);
							nightDiff.setPayrollPeriodId(emp.getPayrollPeriodId());
							
							//Insert Night Shift Details
							insertNightShiftDetails(nightDiff);
						}
					}
					
					nightDiffPayTotal = nightDiffPayTotal.add(nightDiffPay2);
					
					
					
				} else {
					//ND is for timeIn Date only
					String holidayType1 = isTimeEntryHoliday(day1);
					
					double holidayRate = 0;
					
					if(holidayType1 != null && holidayType1.length() > 0) {
						if(PayrollConstants.HOLIDAY_REGULAR_INDICATOR.equals(holidayType1)) {
							finalNightDiffPay = new BigDecimal(totalNightDiff * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE * PayrollConstants.NIGHTDIFF_REGULARHOLIDAY_RATE);
							holidayRate = PayrollConstants.NIGHTDIFF_REGULARHOLIDAY_RATE;
						} else if(PayrollConstants.HOLIDAY_SPECIAL_INDICATOR.equals(holidayType1)) {
							finalNightDiffPay = new BigDecimal(totalNightDiff * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE * PayrollConstants.NIGHTDIFF_SPECIALHOLIDAY_RATE);
							holidayRate = PayrollConstants.NIGHTDIFF_SPECIALHOLIDAY_RATE;
						} else if(PayrollConstants.HOLIDAY_DOUBLE_INDICATOR.equals(holidayType1)) {
							finalNightDiffPay = new BigDecimal(totalNightDiff * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE * PayrollConstants.NIGHTDIFF_DOUBLEHOLIDAY_RATE);
							holidayRate = PayrollConstants.NIGHTDIFF_DOUBLEHOLIDAY_RATE;
						}
						
						NightDifferential nightDiff = new NightDifferential();
						
						nightDiff.setDateRendered(day1);
						nightDiff.setEmpId(emp.getEmpId());
						nightDiff.setHolidayRate(holidayRate);
						nightDiff.setHourlyRate(emp.getHourlyRate().doubleValue());
						nightDiff.setNightDiffAmount(finalNightDiffPay);
						nightDiff.setNoOfHours(totalNightDiff);
						nightDiff.setPayrollPeriodId(emp.getPayrollPeriodId());
						
						//Insert Night Shift Details
						insertNightShiftDetails(nightDiff);
						
						
					} else {
						//NOT A HOLIDAY
						if(totalNightDiff > 0) {
							finalNightDiffPay = new BigDecimal(totalNightDiff * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE);
							
							NightDifferential nightDiff = new NightDifferential();
							
							nightDiff.setDateRendered(day1);
							nightDiff.setEmpId(emp.getEmpId());
							nightDiff.setHolidayRate(holidayRate);
							nightDiff.setHourlyRate(emp.getHourlyRate().doubleValue());
							nightDiff.setNightDiffAmount(finalNightDiffPay);
							nightDiff.setNoOfHours(totalNightDiff);
							nightDiff.setPayrollPeriodId(emp.getPayrollPeriodId());
							
							//Insert Night Shift Details
							insertNightShiftDetails(nightDiff);
							
						}
					}
					
					
					
					nightDiffPayTotal = nightDiffPayTotal.add(finalNightDiffPay);
				}
				
				
			} else {
				//Employee is probably absent								
				String gracePeriodSchedNextDay = incrementDate(scheduleStr) + " 02:00:00";				
				String nextDayStr = incrementDate(scheduleStr);
				
				if(attendanceMap.containsKey(nextDayStr)){
					TimeEntry attendanceNextDay = attendanceMap.get(incrementDate(scheduleStr));
					
					if(attendanceNextDay != null && attendanceNextDay.getTimeIn() != null){
						Date actualTimeIn = sdf.parse(attendanceNextDay.getTimeIn().substring(0, 21)); 
						Date gracePeriodSchedNextDate = sdf.parse(gracePeriodSchedNextDay);
						
						if(actualTimeIn.before(gracePeriodSchedNextDate)){
							//Employee Present he is only late
							attendance = attendanceMap.get(scheduleStr);
							
							String timeInSched = scheduleStr + empSched.getTimeIn().substring(0,10);
							String timeOutSched = scheduleStr + empSched.getTimeIn().substring(0,10);
							String timeIn = attendance.getTimeIn().substring(0,19);
							String timeOut = attendance.getTimeIn().substring(0,19);
							
							Date timeInSchedDate = sdf.parse(timeInSched);
							Date timeOutSchedDate = sdf.parse(timeOutSched);
							Date timeInDate = sdf.parse(timeIn);
							Date timeOutDate = sdf.parse(timeOut);
							
							if(timeOutSchedDate.after(timeInSchedDate)){
								//Time in schedule if the same date as timeOut do nothing
							} else {
								//Time Out schedule is the following day
								timeOutSched = incrementDate(scheduleStr) + empSched.getTimeIn().substring(0,10);
								timeOutSchedDate = sdf.parse(timeOutSched);
							}
							
							//Check if schedule time in is after actual time in. If yes then use schedule time in otherwise use actual time in
							if(timeInSchedDate.after(timeInDate)){
								//use timein schedule
								timeInDate = sdf.parse(timeInSched);
							}
							
							//Check if actual time out is after schedule time out. If yes then use schedule time out otherwise use actual time out
							if(timeOutDate.after(timeOutSchedDate)){
								//use timein schedule
								timeOutDate = sdf.parse(timeOutSched);
							}
							
							//OLD Code
							//ND for the day and next day
							String ndAM = attendance.getTimeIn().substring(0,10) + " 06:00:00";
							String ndPM = attendance.getTimeIn().substring(0,10) + " 22:00:00";
							String ndNextDayAM = incrementDate(attendance.getTimeIn()) + " 06:00:00";
							String midNightNextDay = incrementDate(attendance.getTimeIn().substring(0,10)) + " 00:00:00";
							
							String day1 = attendance.getTimeIn().substring(0,10);
							String day2 = incrementDate(attendance.getTimeIn().substring(0,10));			
							
							Date ndAMDate = sdf.parse(ndAM);
							Date ndPMDate = sdf.parse(ndPM);
							Date ndNextDayAMDate = sdf.parse(ndNextDayAM);
							
							Date midNightNextDayDate = sdf.parse(midNightNextDay);
							
							if(timeInDate.after(ndAMDate)){
								//time in is after 6AM same date
								if(timeInDate.before(ndPMDate)){
									//time in is before 10PM same date
									if(timeOutDate.after(ndPMDate)){
										//It means there is ND. Time out is after 10PM same date
										if(timeOutDate.after(ndNextDayAMDate)){
											nightDiff1 = 2;
											nightDiff2 = 6;
											totalNightDiff = 8;
										} else {
											//time out is before 6AM the following date
											if(timeOutDate.after(midNightNextDayDate)){
												//time out is after 12MN the following day
												//time out - 10PM previous day
												nightDiff1 = 2;
												long totalDiffSeconds = (timeOutDate.getTime() - ndPMDate.getTime()) / 1000;
												int totalDiffHours = (int) totalDiffSeconds / 3600;    
												totalDiffSeconds = totalDiffSeconds % 3600;
												int mins = (int) totalDiffSeconds / 60;

												double finalHours = 0;
												
												if(mins >= 15){
													if(mins >= 30){
														if(mins >= 45){
															finalHours = totalDiffHours + 0.75;
														} else {
															finalHours = totalDiffHours + 0.5;
														}
													} else {
														finalHours = totalDiffHours + 0.25;
													}
												} else {
													finalHours = totalDiffHours;
												}
												
												totalNightDiff = finalHours; 
												nightDiff2 = totalNightDiff - nightDiff1;
											} else {
												//time out is before or equal 12MN the following day
												
												long totalDiffSeconds = (timeOutDate.getTime() - ndPMDate.getTime()) / 1000;
												int totalDiffHours = (int) totalDiffSeconds / 3600;    
												totalDiffSeconds = totalDiffSeconds % 3600;
												int mins = (int) totalDiffSeconds / 60;

												double finalHours = 0;
												
												if(mins >= 15){
													if(mins >= 30){
														if(mins >= 45){
															finalHours = totalDiffHours + 0.75;
														} else {
															finalHours = totalDiffHours + 0.5;
														}
													} else {
														finalHours = totalDiffHours + 0.25;
													}
												} else {
													finalHours = totalDiffHours;
												}
												
												totalNightDiff = finalHours; 
												nightDiff1 = finalHours;
												nightDiff2 = 0;
											}
											
											
											
										}
									} else {
										//No ND since timeout is before 10PM same date
										nightDiff1 = 0;
										nightDiff2 = 0;
										totalNightDiff = 0;
									}
								} else {					
									//time in is after 10PM then definite ND
									if(timeOutDate.before(ndNextDayAMDate) || timeOutDate.compareTo(ndNextDayAMDate) == 0){
										//time out - time in 
										long ndDiffSeconds1st = (midNightNextDayDate.getTime() - timeInDate.getTime()) / 1000;
										int totalDiffHours1st = (int) ndDiffSeconds1st / 3600;    
										ndDiffSeconds1st = ndDiffSeconds1st % 3600;
										int mins1st = (int) ndDiffSeconds1st / 60;
										
										double finalHours1st = 0;
										
										if(mins1st >= 15){
											if(mins1st >= 30){
												if(mins1st >= 45){
													finalHours1st = totalDiffHours1st + 0.75;
												} else {
													finalHours1st = totalDiffHours1st + 0.5;
												}
											} else {
												finalHours1st = totalDiffHours1st + 0.25;
											}
										} else {
											finalHours1st = totalDiffHours1st;
										}
										
										long ndDiffSeconds2nd = (timeOutDate.getTime() - midNightNextDayDate.getTime()) / 1000;
										int totalDiffHours2nd = (int) ndDiffSeconds2nd / 3600;    
										ndDiffSeconds2nd = ndDiffSeconds2nd % 3600;
										int mins2nd = (int) ndDiffSeconds2nd / 60;
										
										double finalHours2nd = 0;
										
										if(mins2nd >= 15){
											if(mins2nd >= 30){
												if(mins2nd >= 45){
													finalHours2nd = totalDiffHours2nd + 0.75;
												} else {
													finalHours2nd = totalDiffHours2nd + 0.5;
												}
											} else {
												finalHours2nd = totalDiffHours2nd + 0.25;
											}
										} else {
											finalHours2nd = totalDiffHours2nd;
										}
										
										long totalDiffSeconds = (timeOutDate.getTime() - timeInDate.getTime()) / 1000;
										int totalDiffHours = (int) totalDiffSeconds / 3600;    
										
										totalDiffSeconds = totalDiffSeconds % 3600;
										int mins = (int) totalDiffSeconds / 60;
										
										double finalHours = 0;
										
										if(mins >= 15){
											if(mins >= 30){
												if(mins >= 45){
													finalHours = totalDiffHours + 0.75;
												} else {
													finalHours = totalDiffHours + 0.5;
												}
											} else {
												finalHours = totalDiffHours + 0.25;
											}
										} else {
											finalHours = totalDiffHours;
										}
										
										nightDiff1 = finalHours1st;
										nightDiff2 = finalHours2nd;
										totalNightDiff = finalHours;
										
									} else {
										
										long ndDiffSeconds1st = (midNightNextDayDate.getTime() - timeInDate.getTime()) / 1000;
										int totalDiffHours1st = (int) ndDiffSeconds1st / 3600;    
										ndDiffSeconds1st = ndDiffSeconds1st % 3600;
										int mins1st = (int) ndDiffSeconds1st / 60;
										
										double finalHours1st = 0;
										
										if(mins1st >= 15){
											if(mins1st >= 30){
												if(mins1st >= 45){
													finalHours1st = totalDiffHours1st + 0.75;
												} else {
													finalHours1st = totalDiffHours1st + 0.5;
												}
											} else {
												finalHours1st = totalDiffHours1st + 0.25;
											}
										} else {
											finalHours1st = totalDiffHours1st;
										}
										
										//6AM - timeIn
										long totalDiffSeconds = (ndNextDayAMDate.getTime() - timeInDate.getTime()) / 1000;
										int totalDiffHours = (int) totalDiffSeconds / 3600; 
										
										totalDiffSeconds = totalDiffSeconds % 3600;
										int mins = (int) totalDiffSeconds / 60;
										
										double finalHours = 0;
										
										if(mins >= 15){
											if(mins >= 30){
												if(mins >= 45){
													finalHours = totalDiffHours + 0.75;
												} else {
													finalHours = totalDiffHours + 0.5;
												}
											} else {
												finalHours = totalDiffHours + 0.25;
											}
										} else {
											finalHours = totalDiffHours;
										}
										
										nightDiff1 = finalHours1st;
										nightDiff2 = 6;
										totalNightDiff = finalHours;
									}
									
								}
								
							} else {
								//6AM - timeIn
								long totalDiffSeconds = (ndAMDate.getTime() - timeInDate.getTime()) / 1000;
								int totalDiffHours = (int) totalDiffSeconds / 3600;  
								
								totalDiffSeconds = totalDiffSeconds % 3600;
								int mins = (int) totalDiffSeconds / 60;
								
								double finalHours = 0;
								
								if(mins >= 15){
									if(mins >= 30){
										if(mins >= 45){
											finalHours = totalDiffHours + 0.75;
										} else {
											finalHours = totalDiffHours + 0.5;
										}
									} else {
										finalHours = totalDiffHours + 0.25;
									}
								} else {
									finalHours = totalDiffHours;
								}
								
								nightDiff1 = finalHours;
								nightDiff2 = 0;
								totalNightDiff = finalHours;
							}
							
							
							System.out.println("nightDiff1: " + nightDiff1);
							System.out.println("nightDiff2: " + nightDiff2);
							System.out.println("totalNightDiff: " + totalNightDiff);
							System.out.println("======================================");
							
							BigDecimal finalNightDiffPay = BigDecimal.ZERO;
							BigDecimal nightDiffPay1 = BigDecimal.ZERO;
							BigDecimal nightDiffPay2 = BigDecimal.ZERO;
							
							if(nightDiff2 > 0){
								//This means ND if for day1 and day2
								String holidayType1 = isTimeEntryHoliday(day1);
								String holidayType2 = isTimeEntryHoliday(day2);
								
								double holidayRate = 0;
								
								if(holidayType1 != null && holidayType1.length() > 0 && nightDiff1 > 0) {
									if(PayrollConstants.HOLIDAY_REGULAR_INDICATOR.equals(holidayType1)) {
										nightDiffPay1 = new BigDecimal(nightDiff1 * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE * PayrollConstants.NIGHTDIFF_REGULARHOLIDAY_RATE);
										holidayRate = PayrollConstants.NIGHTDIFF_REGULARHOLIDAY_RATE;
									} else if(PayrollConstants.HOLIDAY_SPECIAL_INDICATOR.equals(holidayType1)) {
										nightDiffPay1 = new BigDecimal(nightDiff1 * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE * PayrollConstants.NIGHTDIFF_SPECIALHOLIDAY_RATE);
										holidayRate = PayrollConstants.NIGHTDIFF_SPECIALHOLIDAY_RATE;
									} else if(PayrollConstants.HOLIDAY_DOUBLE_INDICATOR.equals(holidayType1)) {
										nightDiffPay1 = new BigDecimal(nightDiff1 * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE * PayrollConstants.NIGHTDIFF_DOUBLEHOLIDAY_RATE);
										holidayRate = PayrollConstants.NIGHTDIFF_DOUBLEHOLIDAY_RATE;
									}
									
									NightDifferential nightDiff = new NightDifferential();
									
									nightDiff.setDateRendered(day1);
									nightDiff.setEmpId(emp.getEmpId());
									nightDiff.setHolidayRate(holidayRate);
									nightDiff.setHourlyRate(emp.getHourlyRate().doubleValue());
									nightDiff.setNightDiffAmount(nightDiffPay1);
									nightDiff.setNoOfHours(nightDiff1);
									nightDiff.setPayrollPeriodId(emp.getPayrollPeriodId());
									
									//Insert Night Shift Details
									insertNightShiftDetails(nightDiff);
									
								} else {
									//NOT A HOLIDAY
									if(nightDiff1 > 0) {
										nightDiffPay1 = new BigDecimal(nightDiff1 * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE);
										
										NightDifferential nightDiff = new NightDifferential();
										
										nightDiff.setDateRendered(day1);
										nightDiff.setEmpId(emp.getEmpId());
										nightDiff.setHolidayRate(holidayRate);
										nightDiff.setHourlyRate(emp.getHourlyRate().doubleValue());
										nightDiff.setNightDiffAmount(nightDiffPay1);
										nightDiff.setNoOfHours(nightDiff1);
										nightDiff.setPayrollPeriodId(emp.getPayrollPeriodId());
										
										//Insert Night Shift Details
										insertNightShiftDetails(nightDiff);
										
									}
								}
								
								nightDiffPayTotal = nightDiffPayTotal.add(nightDiffPay1);
								
								holidayRate = 0;
								
								if(holidayType2 != null && holidayType2.length() > 0 && nightDiff2 > 0) {
									if(PayrollConstants.HOLIDAY_REGULAR_INDICATOR.equals(holidayType2)) {
										nightDiffPay2 = new BigDecimal(nightDiff2 * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE * PayrollConstants.NIGHTDIFF_REGULARHOLIDAY_RATE);
										holidayRate = PayrollConstants.NIGHTDIFF_REGULARHOLIDAY_RATE;
									} else if(PayrollConstants.HOLIDAY_SPECIAL_INDICATOR.equals(holidayType2)) {
										nightDiffPay2 = new BigDecimal(nightDiff2 * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE * PayrollConstants.NIGHTDIFF_SPECIALHOLIDAY_RATE);
										holidayRate = PayrollConstants.NIGHTDIFF_SPECIALHOLIDAY_RATE;
									} else if(PayrollConstants.HOLIDAY_DOUBLE_INDICATOR.equals(holidayType2)) {
										nightDiffPay2 = new BigDecimal(nightDiff2 * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE * PayrollConstants.NIGHTDIFF_DOUBLEHOLIDAY_RATE);
										holidayRate = PayrollConstants.NIGHTDIFF_DOUBLEHOLIDAY_RATE;
									}
									
									NightDifferential nightDiff = new NightDifferential();
									
									nightDiff.setDateRendered(day2);
									nightDiff.setEmpId(emp.getEmpId());
									nightDiff.setHolidayRate(holidayRate);
									nightDiff.setHourlyRate(emp.getHourlyRate().doubleValue());
									nightDiff.setNightDiffAmount(nightDiffPay2);
									nightDiff.setNoOfHours(nightDiff2);
									nightDiff.setPayrollPeriodId(emp.getPayrollPeriodId());
									
									//Insert Night Shift Details
									insertNightShiftDetails(nightDiff);
									
								} else {
									//NOT A HOLIDAY
									if(nightDiff2 > 0) {
										nightDiffPay2 = new BigDecimal(nightDiff2 * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE);
										
										NightDifferential nightDiff = new NightDifferential();
										
										nightDiff.setDateRendered(day2);
										nightDiff.setEmpId(emp.getEmpId());
										nightDiff.setHolidayRate(holidayRate);
										nightDiff.setHourlyRate(emp.getHourlyRate().doubleValue());
										nightDiff.setNightDiffAmount(nightDiffPay2);
										nightDiff.setNoOfHours(nightDiff2);
										nightDiff.setPayrollPeriodId(emp.getPayrollPeriodId());
										
										//Insert Night Shift Details
										insertNightShiftDetails(nightDiff);
									}
								}
								
								nightDiffPayTotal = nightDiffPayTotal.add(nightDiffPay2);
								
								
								
							} else {
								//ND is for timeIn Date only
								String holidayType1 = isTimeEntryHoliday(day1);
								
								double holidayRate = 0;
								
								if(holidayType1 != null && holidayType1.length() > 0) {
									if(PayrollConstants.HOLIDAY_REGULAR_INDICATOR.equals(holidayType1)) {
										finalNightDiffPay = new BigDecimal(totalNightDiff * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE * PayrollConstants.NIGHTDIFF_REGULARHOLIDAY_RATE);
										holidayRate = PayrollConstants.NIGHTDIFF_REGULARHOLIDAY_RATE;
									} else if(PayrollConstants.HOLIDAY_SPECIAL_INDICATOR.equals(holidayType1)) {
										finalNightDiffPay = new BigDecimal(totalNightDiff * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE * PayrollConstants.NIGHTDIFF_SPECIALHOLIDAY_RATE);
										holidayRate = PayrollConstants.NIGHTDIFF_SPECIALHOLIDAY_RATE;
									} else if(PayrollConstants.HOLIDAY_DOUBLE_INDICATOR.equals(holidayType1)) {
										finalNightDiffPay = new BigDecimal(totalNightDiff * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE * PayrollConstants.NIGHTDIFF_DOUBLEHOLIDAY_RATE);
										holidayRate = PayrollConstants.NIGHTDIFF_DOUBLEHOLIDAY_RATE;
									}
									
									NightDifferential nightDiff = new NightDifferential();
									
									nightDiff.setDateRendered(day1);
									nightDiff.setEmpId(emp.getEmpId());
									nightDiff.setHolidayRate(holidayRate);
									nightDiff.setHourlyRate(emp.getHourlyRate().doubleValue());
									nightDiff.setNightDiffAmount(finalNightDiffPay);
									nightDiff.setNoOfHours(totalNightDiff);
									nightDiff.setPayrollPeriodId(emp.getPayrollPeriodId());
									
									//Insert Night Shift Details
									insertNightShiftDetails(nightDiff);
									
									//Insert Night Shift Details
									
								} else {
									//NOT A HOLIDAY
									if(totalNightDiff > 0) {
										finalNightDiffPay = new BigDecimal(totalNightDiff * emp.getHourlyRate().doubleValue() * PayrollConstants.NIGHTDIFF_RATE);
										
										NightDifferential nightDiff = new NightDifferential();
										
										nightDiff.setDateRendered(day1);
										nightDiff.setEmpId(emp.getEmpId());
										nightDiff.setHolidayRate(holidayRate);
										nightDiff.setHourlyRate(emp.getHourlyRate().doubleValue());
										nightDiff.setNightDiffAmount(finalNightDiffPay);
										nightDiff.setNoOfHours(totalNightDiff);
										nightDiff.setPayrollPeriodId(emp.getPayrollPeriodId());
										
										//Insert Night Shift Details
										insertNightShiftDetails(nightDiff);
										
									}
								}
								
								
								
								nightDiffPayTotal = nightDiffPayTotal.add(finalNightDiffPay);
							}
							
							
						}//if						
					}//if					
				}//if				
			}//else			
		}//for
		
		
		
		
		
		
		
		
		
		//for(TimeEntry attendance: attendanceList) {
		
		//while (rs.next()) {			
			
			
			
			//Determine what time in or out should be used whether from schedule or actual time on
//			String timeIn = attendance.getTimeIn().substring(0,19);
//			String timeOut = attendance.getTimeIn().substring(0,19);						
//			
//			System.out.println("Time In: " + timeIn);
//			System.out.println("Time Out: " + timeOut);
//			
//			Date timeInDate = sdf.parse(timeIn);
//			Date timeOutDate = sdf.parse(timeOut);
			
			
			
			
			
			

			
//		}
		
		return nightDiffPayTotal;
	}
	
	private void insertNightShiftDetails(NightDifferential nightDiff) throws SQLException, Exception {
		
    	StringBuffer sql = new StringBuffer();
    	sql.append("INSERT INTO empNightDiff (empId,dateRendered,noOfHours,holidayRate,hourlyRate,payrollPeriodId, nightDiffAmount) ");
    	sql.append("VALUES (");
    	sql.append(nightDiff.getEmpId());
    	sql.append(", '");
    	sql.append(nightDiff.getDateRendered());
    	sql.append("', ");
    	sql.append(nightDiff.getNoOfHours());
    	sql.append(", ");
    	sql.append(nightDiff.getHolidayRate());
    	sql.append(", ");
    	sql.append(nightDiff.getHourlyRate());
    	sql.append(", ");
    	sql.append(nightDiff.getPayrollPeriodId());
    	sql.append(", ");
    	sql.append(nightDiff.getNightDiffAmount().doubleValue());
    	sql.append(") ");
    	
		
    	PreparedStatement ps = conn.prepareStatement(sql.toString()); 
		
		ps.executeUpdate();
		
		conn.commit();
		 
		ps.close();	
    }
	
//	private String decrementDate(Calendar cal) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		
//		int daysToDecrement = -1;
//		cal.add(Calendar.DAY_OF_WEEK, daysToDecrement);
//		System.out.println("Date after decrement: " + sdf.format(cal.getTime()));
//		
//		return sdf.format(cal.getTime());
//
//	}
	
	private String incrementDate(String dateString) throws ParseException {		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		
		c.setTime(sdf.parse(dateString));		
		c.add(Calendar.DATE, 1);  // number of days to add
		
		return sdf.format(c.getTime());  // return the new date
    }	
	
	
	//get employee payroll run that is not locked.
	public int getEmployeePayrollRunId(int payrollPeriodId, int empId) throws SQLException {
		int index = 1;
		int payrollRunId = 0;
		String sql = "select employeePayrollRunId from employeePayrollRun where payrollPeriodId=? and empId=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(index++, payrollPeriodId);
		ps.setInt(index++, empId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			payrollRunId = rs.getInt("employeePayrollRunId");			
		}
		ps.close();
		rs.close();
		return payrollRunId;

	}

	private Map<Integer,Integer> getExcludedEmployeeId(int payrollPeriodId) throws SQLException, Exception {
		Map<Integer,Integer> empMap = new HashMap<Integer, Integer>();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT * FROM empPayrollExclusion WHERE payrollPeriodId = ");
		sql.append(payrollPeriodId);
		
		System.out.println("SQL getExcludedEmployeeId: " + sql.toString());
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {			
			empMap.put(rs.getInt("empId"), rs.getInt("empId"));
		}
		
		return empMap;
	}
	
	public List<EmployeePayrollRunExt> getBasicDataForEmployeeListByPayPeriod(int payrollPeriodId, String payrollType) throws SQLException, Exception {
		
		List<EmployeePayrollRunExt> resultList = new ArrayList<EmployeePayrollRunExt>();
		Map<Integer,Integer> empMap = getExcludedEmployeeId(payrollPeriodId);
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT e.empId, e.empNo, e.lastname, e.firstname, e.middlename, e.dateOfBirth, e.gender, e.civilStatus, e.email, e.mobileNo, e.telNo,  ");
		sql.append("e.jobTitleId, jt.jobTitle, e.sectionId, d.sectionName, e.unitId, dv.unitName, e.employeeTypeId, et.employeeTypeName,  ");
		sql.append("e.empStatus, e.sss, e.gsis, e.hdmf, e.tin, e.phic, e.taxstatus, e.employmentDate, e.endOfContract, ");		
		sql.append("epi.monthlyRate, epi.dailyRate, epi.hourlyRate, epi.shiftingScheduleId, epi.foodAllowance, ");
		sql.append("epi.cola, epi.taxShield, epi.transAllowance, epi.payrollType, epi.ban, epi.bankNameBan, ");
		sql.append("epi.gsisEmployeeShare, epi.gsisEmployerShare, epi.philhealthEmployeeShare, epi.philhealthEmployerShare, epi.pagibigEmployeeShare, ");
		sql.append("epi.pagibigEmployerShare, epi.hasNightDifferential, epi.hasHolidayPay, pp.payrollPeriodId, pp.payYear, pp.payMonth, ");		 
		sql.append("pp.payrollType, pp.fromDate, pp.toDate, pp.payDate, pp.payrollCode, pp.numWorkDays, pp.payPeriod, pp.deductGovtFlag, pp.isForSecondHalf ");		
		sql.append("FROM employee e, empPayrollInfo epi, payrollPeriod pp, jobTitle jt, section d, unit dv, employeeType et ");
		sql.append("WHERE e.empId = epi.empId ");
		sql.append("AND e.jobTitleId = jt.jobTitleId ");
		sql.append("AND e.sectionId = d.sectionId ");
		sql.append("AND e.unitId = dv.unitId ");
		sql.append("AND e.employeeTypeId = et.employeeTypeId ");
		sql.append("AND pp.payrollType = epi.payrollType ");
		sql.append("AND e.employeeTypeId <> 3 ");
		sql.append("AND lockedAt IS NULL ");
		sql.append("AND pp.payrollPeriodId = ");
		sql.append(payrollPeriodId);
		sql.append(" AND pp.payrollType = '");
		sql.append(payrollType);
		sql.append("'");
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL getBasicDataForEmployeeListByPayPeriod: " + sql.toString());
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			
			//Filter Employees Not included in the payroll run
			if(!empMap.containsKey(rs.getInt("empId"))){
				//do not populate computed fields
				EmployeePayrollRunExt eprE = new EmployeePayrollRunExt();
				eprE.setEmpId(rs.getInt("empId"));
				eprE.setEmpNo(rs.getString("empNo"));
				eprE.setFirstname(rs.getString("firstname"));
				eprE.setMiddlename(rs.getString("middlename"));
				eprE.setLastname(rs.getString("lastname"));
				eprE.setGender(rs.getString("gender"));
				eprE.setEmpStatus(rs.getString("empStatus"));
				eprE.setTaxStatus(rs.getString("taxStatus"));
				eprE.setCivilStatus(rs.getString("civilStatus"));
				eprE.setDateOfBirth(rs.getString("dateOfBirth"));
				eprE.setJobTitleId(rs.getInt("jobTitleId"));
				eprE.setJobTitle(rs.getString("jobTitle"));
				eprE.setSectionId(rs.getInt("sectionId"));
				eprE.setSectionName(rs.getString("sectionName"));
				eprE.setUnitId(rs.getInt("unitId"));
				eprE.setUnitName(rs.getString("unitName"));
				eprE.setEmail(rs.getString("email"));
				eprE.setTelNo(rs.getString("telNo"));
				eprE.setMobileNo(rs.getString("mobileNo"));
				eprE.setEmployeeTypeId(rs.getInt("employeeTypeId"));
				eprE.setEmployeeTypeName(rs.getString("employeeTypeName"));
				eprE.setEmploymentDate(rs.getString("employmentDate"));
				eprE.setEndOfContract(rs.getString("endOfContract"));			
				eprE.setDailyRate(rs.getBigDecimal("dailyRate"));
				eprE.setMonthlyRate(rs.getBigDecimal("monthlyRate"));
				eprE.setHourlyRate(rs.getBigDecimal("hourlyRate"));
				eprE.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
				eprE.setCola(rs.getBigDecimal("cola"));
				eprE.setFoodAllowance(rs.getBigDecimal("foodAllowance"));
				eprE.setTaxShield(rs.getBigDecimal("taxShield"));
				eprE.setTransAllowance(rs.getBigDecimal("transAllowance"));
				eprE.setBan(rs.getString("ban"));
				eprE.setBankNameBan(rs.getString("bankNameBan"));
				eprE.setHasHolidayPay(rs.getString("hasHolidayPay"));
				eprE.setHasNightDifferential(rs.getString("hasNightDifferential"));
				eprE.setPayrollPeriodId(rs.getInt("payrollPeriodId"));
				eprE.setPayDate(rs.getDate("payDate"));
				eprE.setPayPeriod(rs.getString("payPeriod"));
				eprE.setFromDate(rs.getDate("fromDate"));
				eprE.setToDate(rs.getDate("toDate"));
				eprE.setPayrollCode(rs.getString("payrollCode"));
				eprE.setPayrollType(rs.getString("payrollType"));
				eprE.setPayMonth(rs.getInt("payMonth"));
				eprE.setPayYear(rs.getInt("payYear"));
				eprE.setNumWorkDays(rs.getInt("numWorkDays"));
				eprE.setDeductGovtFlag(rs.getString("deductGovtFlag"));
				eprE.setIsForSecondHalf(rs.getString("isForSecondHalf"));			
				eprE.setGsisEmployeeShare(rs.getBigDecimal("gsisEmployeeShare"));
				eprE.setGsisEmployerShare(rs.getBigDecimal("gsisEmployerShare"));
				eprE.setPagibigEmployeeShare(rs.getBigDecimal("pagibigEmployeeShare"));
				eprE.setPagibigEmployerShare(rs.getBigDecimal("pagibigEmployerShare"));
				eprE.setPhilhealthEmployeeShare(rs.getBigDecimal("philhealthEmployeeShare"));
				eprE.setPhilhealthEmployerShare(rs.getBigDecimal("philhealthEmployerShare"));
				
				resultList.add(eprE);			
			}
			
		}
		
		rs.close();
		
		return resultList;
	}
	
	
	
	public List<EmployeePayrollRunExt> getBasicDataForEmpListByPayPeriodContractual(int payPeriod, String payrollType) throws SQLException {
		ArrayList<EmployeePayrollRunExt> resultList = new ArrayList<EmployeePayrollRunExt>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT e.empId, e.empNo, e.lastname, e.firstname, e.middlename, e.dateOfBirth, e.gender, e.civilStatus, e.email, e.mobileNo, e.telNo,  ");
		sql.append("e.jobTitleId, jt.jobTitle, e.sectionId, d.sectionName, e.unitId, dv.unitName, e.employeeTypeId, et.employeeTypeName,  ");
		sql.append("e.empStatus, e.sss, e.gsis, e.hdmf, e.tin, e.phic, e.taxstatus, e.employmentDate, e.endOfContract, ");		
		sql.append("epi.monthlyRate, epi.dailyRate, epi.hourlyRate, epi.shiftingScheduleId, epi.foodAllowance, ");
		sql.append("epi.cola, epi.taxShield, epi.transAllowance, epi.payrollType, epi.ban, epi.bankNameBan, ");
		sql.append("epi.gsisEmployeeShare, epi.gsisEmployerShare, epi.philhealthEmployeeShare, epi.philhealthEmployerShare, ");
		sql.append("epi.pagibigEmployeeShare, epi.pagibigEmployerShare, epi.hasNightDifferential, epi.hasHolidayPay, pp.payrollPeriodId, pp.payYear, pp.payMonth, ");		 
		sql.append("pp.payrollType, pp.fromDate, pp.toDate, pp.payDate, pp.payrollCode, pp.numWorkDays, pp.payPeriod, pp.deductGovtFlag, pp.isForSecondHalf ");		
		sql.append("FROM employee e, empPayrollInfo epi, payrollPeriod pp, jobTitle jt, section d, unit dv, employeeType et ");
		sql.append("WHERE e.empId = epi.empId ");
		sql.append("AND e.jobTitleId = jt.jobTitleId ");
		sql.append("AND e.sectionId = d.sectionId ");
		sql.append("AND e.unitId = dv.unitId ");
		sql.append("AND e.employeeTypeId = et.employeeTypeId ");
		sql.append("AND pp.payrollType = epi.payrollType ");
		sql.append("AND e.employeeTypeId = 3 ");
		sql.append("AND lockedAt IS NULL ");
		sql.append("AND pp.payrollPeriodId = ? ");
		sql.append("AND pp.payrollType = ? ");
		
		System.out.print("SQL getBasicDataForEmpListByPayPeriodContractual: " + sql.toString());
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, payPeriod);
		ps.setString(2, payrollType);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			//do not populate computed fields
			EmployeePayrollRunExt eprE = new EmployeePayrollRunExt();
			eprE.setEmpId(rs.getInt("empId"));
			eprE.setEmpNo(rs.getString("empNo"));
			eprE.setFirstname(rs.getString("firstname"));
			eprE.setMiddlename(rs.getString("middlename"));
			eprE.setLastname(rs.getString("lastname"));
			eprE.setGender(rs.getString("gender"));
			eprE.setEmpStatus(rs.getString("empStatus"));
			eprE.setTaxStatus(rs.getString("taxStatus"));
			eprE.setCivilStatus(rs.getString("civilStatus"));
			eprE.setDateOfBirth(rs.getString("dateOfBirth"));
			eprE.setJobTitleId(rs.getInt("jobTitleId"));
			eprE.setJobTitle(rs.getString("jobTitle"));
			eprE.setSectionId(rs.getInt("sectionId"));
			eprE.setSectionName(rs.getString("sectionName"));
			eprE.setUnitId(rs.getInt("unitId"));
			eprE.setUnitName(rs.getString("unitName"));
			eprE.setEmail(rs.getString("email"));
			eprE.setTelNo(rs.getString("telNo"));
			eprE.setMobileNo(rs.getString("mobileNo"));
			eprE.setEmployeeTypeId(rs.getInt("employeeTypeId"));
			eprE.setEmployeeTypeName(rs.getString("employeeTypeName"));
			eprE.setEmploymentDate(rs.getString("employmentDate"));
			eprE.setEndOfContract(rs.getString("endOfContract"));			
			eprE.setDailyRate(rs.getBigDecimal("dailyRate"));
			eprE.setMonthlyRate(rs.getBigDecimal("monthlyRate"));
			eprE.setHourlyRate(rs.getBigDecimal("hourlyRate"));
			eprE.setShiftingScheduleId(rs.getInt("shiftingScheduleId"));
			eprE.setCola(rs.getBigDecimal("cola"));
			eprE.setFoodAllowance(rs.getBigDecimal("foodAllowance"));
			eprE.setTaxShield(rs.getBigDecimal("taxShield"));
			eprE.setTransAllowance(rs.getBigDecimal("transAllowance"));
			eprE.setBan(rs.getString("ban"));
			eprE.setBankNameBan(rs.getString("bankNameBan"));
			eprE.setHasHolidayPay(rs.getString("hasHolidayPay"));
			eprE.setHasNightDifferential(rs.getString("hasNightDifferential"));
			eprE.setPayrollPeriodId(rs.getInt("payrollPeriodId"));
			eprE.setPayDate(rs.getDate("payDate"));
			eprE.setPayPeriod(rs.getString("payPeriod"));
			eprE.setFromDate(rs.getDate("fromDate"));
			eprE.setToDate(rs.getDate("toDate"));
			eprE.setPayrollCode(rs.getString("payrollCode"));
			eprE.setPayrollType(rs.getString("payrollType"));
			eprE.setPayMonth(rs.getInt("payMonth"));
			eprE.setPayYear(rs.getInt("payYear"));
			eprE.setNumWorkDays(rs.getInt("numWorkDays"));
			eprE.setDeductGovtFlag(rs.getString("deductGovtFlag"));
			eprE.setIsForSecondHalf(rs.getString("isForSecondHalf"));			
			eprE.setGsisEmployeeShare(rs.getBigDecimal("gsisEmployeeShare"));
			eprE.setGsisEmployerShare(rs.getBigDecimal("gsisEmployerShare"));
			eprE.setPagibigEmployeeShare(rs.getBigDecimal("pagibigEmployeeShare"));
			eprE.setPagibigEmployerShare(rs.getBigDecimal("pagibigEmployerShare"));
			eprE.setPhilhealthEmployeeShare(rs.getBigDecimal("philhealthEmployeeShare"));
			eprE.setPhilhealthEmployerShare(rs.getBigDecimal("philhealthEmployerShare"));
			
			resultList.add(eprE);			
			
		}
		rs.close();
		return resultList;
	}
	
	public String isTimeEntryHoliday(String holidayDate) throws SQLException, Exception {    	
    	String holidayType = "";
		
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("SELECT * FROM holiday WHERE CONVERT(DATE,holidayDate) = '");
    	sql.append(holidayDate);
    	sql.append("'");    	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();	    
	    
	    if (rs.next()) {
	    	holidayType = rs.getString("holidayType");
	    	ps.close();
		    rs.close();		    
	    }	    
	    
	    
	    ps.close();
	    rs.close();      
	    return holidayType;     

	}
	
	//TODO - Done just need to verify on the weekend
	public BigDecimal getHolidayPay (Map<String, TimeEntry> attendanceMap, List<TimeEntry> scheduleList, EmployeePayrollRunExt regularEmpExt) throws SQLException, Exception {
		BigDecimal holidayPayTotal = BigDecimal.ZERO;
		
		TimeEntry attendance = null;
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		double day1Hrs = 0;
		double day2Hrs = 0;
		
		for (TimeEntry empSched: scheduleList) {			
			String scheduleStr = empSched.getScheduleDate();
			
			if(attendanceMap.containsKey(scheduleStr)){
				//Employee is present
				attendance = attendanceMap.get(scheduleStr);
				
				String timeInSched = scheduleStr + " " + empSched.getTimeIn().substring(0,8);
				String timeOutSched = scheduleStr + " " + empSched.getTimeOut().substring(0,8);
				String timeIn = attendance.getTimeIn().substring(0,19);
				String timeOut = attendance.getTimeOut().substring(0,19);
				
				Date timeInSchedDate = sdf.parse(timeInSched);
				Date timeOutSchedDate = sdf.parse(timeOutSched);
				Date timeInDate = sdf.parse(timeIn);
				Date timeOutDate = sdf.parse(timeOut);
				
				if(timeOutSchedDate.after(timeInSchedDate)){
					//Time in schedule if the same date as timeOut do nothing
				} else {
					//Time Out schedule is the following day
					timeOutSched = incrementDate(scheduleStr) + " " + empSched.getTimeOut().substring(0,10);
					timeOutSchedDate = sdf.parse(timeOutSched);
				}
				
				//Check if schedule time in is after actual time in. If yes then use schedule time in otherwise use actual time in
				if(timeInSchedDate.after(timeInDate)){
					//use timein schedule
					timeInDate = sdf.parse(timeInSched);
				}
				
				//Check if actual time out is after schedule time out. If yes then use schedule time out otherwise use actual time out
				if(timeOutDate.after(timeOutSchedDate)){
					//use timein schedule
					timeOutDate = sdf.parse(timeOutSched);
				}
				
				//OLD Code
				String midNightNextDay = incrementDate(attendance.getTimeIn().substring(0,10)) + " 00:00:00";
				
				String day1 = attendance.getTimeIn().substring(0,10);
				String day2 = incrementDate(attendance.getTimeIn().substring(0,10));				
					
				Date midNightNextDayDate = sdf.parse(midNightNextDay);
				
				if(timeOutDate.after(midNightNextDayDate)){
					//day1Hrs = midNightNextDayDate - timeInDate
					//day2Hrs = timeOutDate - midNightNextDayDate
					long ndDiffSeconds1st = (midNightNextDayDate.getTime() - timeInDate.getTime()) / 1000;
					int totalDiffHours1st = (int) ndDiffSeconds1st / 3600;    
					ndDiffSeconds1st = ndDiffSeconds1st % 3600;
					int mins1st = (int) ndDiffSeconds1st / 60;
					
					double finalHours1st = 0;
					
					if(mins1st >= 15){
						if(mins1st >= 30){
							if(mins1st >= 45){
								finalHours1st = totalDiffHours1st + 0.75;
							} else {
								finalHours1st = totalDiffHours1st + 0.5;
							}
						} else {
							finalHours1st = totalDiffHours1st + 0.25;
						}
					} else {
						finalHours1st = totalDiffHours1st;
					}
					
					long ndDiffSeconds2nd = (timeOutDate.getTime() - midNightNextDayDate.getTime()) / 1000;
					int totalDiffHours2nd = (int) ndDiffSeconds2nd / 3600;    
					ndDiffSeconds2nd = ndDiffSeconds2nd % 3600;
					int mins2nd = (int) ndDiffSeconds2nd / 60;
					
					double finalHours2nd = 0;
					
					if(mins2nd >= 15){
						if(mins2nd >= 30){
							if(mins2nd >= 45){
								finalHours2nd = totalDiffHours2nd + 0.75;
							} else {
								finalHours2nd = totalDiffHours2nd + 0.5;
							}
						} else {
							finalHours2nd = totalDiffHours2nd + 0.25;
						}
					} else {
						finalHours2nd = totalDiffHours2nd;
					}
					
					
					
					day1Hrs = finalHours1st;
					day2Hrs = finalHours2nd;
					
					
				} else {
					//time out is before or equal 12MN the following day
									
					long totalDiffSeconds = (timeOutDate.getTime() - timeInDate.getTime()) / 1000;
					int totalDiffHours = (int) totalDiffSeconds / 3600;    
					totalDiffSeconds = totalDiffSeconds % 3600;
					int mins = (int) totalDiffSeconds / 60;

					double finalHours = 0;
								
					if(mins >= 15){
						if(mins >= 30){
							if(mins >= 45){
								finalHours = totalDiffHours + 0.75;
							} else {
								finalHours = totalDiffHours + 0.5;
							}
						} else {
							finalHours = totalDiffHours + 0.25;
						}
					} else {
						finalHours = totalDiffHours;
					}
									
					day1Hrs = finalHours; 
				}
				
				
				System.out.println("day1Hrs: " + day1Hrs);
				System.out.println("day2Hrs: " + day2Hrs);
				System.out.println("======================================");
				
				BigDecimal holidayPay = BigDecimal.ZERO;
				BigDecimal holidayPay1 = BigDecimal.ZERO;
				BigDecimal holidayPay2 = BigDecimal.ZERO;
				
				if(day2Hrs > 0){
					//This means ND if for day1 and day2
					String holidayType1 = isTimeEntryHoliday(day1);
					String holidayType2 = isTimeEntryHoliday(day2);
					
					double totalHrs = 0;
					
					if(holidayType1 != null && holidayType1.length() > 0 && day1Hrs > 0) {
						totalHrs = day1Hrs;
						
						if(PayrollConstants.HOLIDAY_REGULAR_INDICATOR.equals(holidayType1)) {
							holidayPay1 = new BigDecimal(day1Hrs * regularEmpExt.getHourlyRate().doubleValue() * PayrollConstants.HOLIDAY_REGULAR_RATE);
						} else if(PayrollConstants.HOLIDAY_SPECIAL_INDICATOR.equals(holidayType1)) {
							holidayPay1 = new BigDecimal(day1Hrs * regularEmpExt.getHourlyRate().doubleValue() * PayrollConstants.HOLIDAY_SPECIAL_RATE);
						} else if(PayrollConstants.HOLIDAY_DOUBLE_INDICATOR.equals(holidayType1)) {
							holidayPay1 = new BigDecimal(day1Hrs * regularEmpExt.getHourlyRate().doubleValue() * PayrollConstants.HOLIDAY_DOUBLEHOLIDAYRESTDATE_RATE);
						}
						
					}
					
					holidayPayTotal = holidayPayTotal.add(holidayPay1);
					
					if(holidayType2 != null && holidayType2.length() > 0 && day2Hrs > 0) {
						totalHrs = totalHrs + day2Hrs;
						
						// day2Hrs = 8 - 6;
						if(totalHrs > 8){
							day2Hrs = 8 - day1Hrs;
						}
						
						if(PayrollConstants.HOLIDAY_REGULAR_INDICATOR.equals(holidayType2)) {
							holidayPay2 = new BigDecimal(day2Hrs * regularEmpExt.getHourlyRate().doubleValue() * PayrollConstants.HOLIDAY_REGULAR_RATE);
						} else if(PayrollConstants.HOLIDAY_SPECIAL_INDICATOR.equals(holidayType2)) {
							holidayPay2 = new BigDecimal(day2Hrs * regularEmpExt.getHourlyRate().doubleValue() * PayrollConstants.HOLIDAY_SPECIAL_RATE);
						} else if(PayrollConstants.HOLIDAY_DOUBLE_INDICATOR.equals(holidayType2)) {
							holidayPay2 = new BigDecimal(day2Hrs * regularEmpExt.getHourlyRate().doubleValue() * PayrollConstants.HOLIDAY_DOUBLEHOLIDAYRESTDATE_RATE);
						}
					}
					
					holidayPayTotal = holidayPayTotal.add(holidayPay2);
					
					
					
				} else {
					//Holiday Pay is for timeIn Date only
					String holidayType1 = isTimeEntryHoliday(day1);
					
					if(day1Hrs > 8){
						day1Hrs = 8;
					}
					
					if(holidayType1 != null && holidayType1.length() > 0) {
						if(PayrollConstants.HOLIDAY_REGULAR_INDICATOR.equals(holidayType1)) {
							holidayPay = new BigDecimal(day1Hrs * regularEmpExt.getHourlyRate().doubleValue() * PayrollConstants.HOLIDAY_REGULAR_RATE);
						} else if(PayrollConstants.HOLIDAY_SPECIAL_INDICATOR.equals(holidayType1)) {
							holidayPay = new BigDecimal(day1Hrs * regularEmpExt.getHourlyRate().doubleValue() * PayrollConstants.HOLIDAY_SPECIAL_RATE);
						} else if(PayrollConstants.HOLIDAY_DOUBLE_INDICATOR.equals(holidayType1)) {
							holidayPay = new BigDecimal(day1Hrs * regularEmpExt.getHourlyRate().doubleValue() * PayrollConstants.HOLIDAY_DOUBLEHOLIDAYRESTDATE_RATE);
						}
					}	
					
					holidayPayTotal = holidayPayTotal.add(holidayPay);
				}
				
			} else {
				String gracePeriodSchedNextDay = incrementDate(scheduleStr) + " 02:00:00";				
				String nextDayStr = incrementDate(scheduleStr);
				
				if(attendanceMap.containsKey(nextDayStr)){
					TimeEntry attendanceNextDay = attendanceMap.get(incrementDate(scheduleStr));
					
					if(attendanceNextDay != null && attendanceNextDay.getTimeIn() != null){
						Date actualTimeIn = sdf.parse(attendanceNextDay.getTimeIn().substring(0, 21)); 
						Date gracePeriodSchedNextDate = sdf.parse(gracePeriodSchedNextDay);
						
						if(actualTimeIn.before(gracePeriodSchedNextDate)){
							//Employee Present he is only late
							attendance = attendanceMap.get(scheduleStr);
							
							String timeInSched = scheduleStr + " " + empSched.getTimeIn().substring(0,8);
							String timeOutSched = scheduleStr + " " + empSched.getTimeOut().substring(0,8);
							String timeIn = attendance.getTimeIn().substring(0,19);
							String timeOut = attendance.getTimeOut().substring(0,19);
							
							Date timeInSchedDate = sdf.parse(timeInSched);
							Date timeOutSchedDate = sdf.parse(timeOutSched);
							Date timeInDate = sdf.parse(timeIn);
							Date timeOutDate = sdf.parse(timeOut);
							
							if(timeOutSchedDate.after(timeInSchedDate)){
								//Time in schedule if the same date as timeOut do nothing
							} else {
								//Time Out schedule is the following day
								timeOutSched = incrementDate(scheduleStr) + " " + empSched.getTimeOut().substring(0,10);
								timeOutSchedDate = sdf.parse(timeOutSched);
							}
							
							//Check if schedule time in is after actual time in. If yes then use schedule time in otherwise use actual time in
							if(timeInSchedDate.after(timeInDate)){
								//use timein schedule
								timeInDate = sdf.parse(timeInSched);
							}
							
							//Check if actual time out is after schedule time out. If yes then use schedule time out otherwise use actual time out
							if(timeOutDate.after(timeOutSchedDate)){
								//use timein schedule
								timeOutDate = sdf.parse(timeOutSched);
							}
							
							//OLD Code
							String midNightNextDay = incrementDate(attendance.getTimeIn().substring(0,10)) + " 00:00:00";
							
							String day1 = attendance.getTimeIn().substring(0,10);
							String day2 = incrementDate(attendance.getTimeIn().substring(0,10));				
								
							Date midNightNextDayDate = sdf.parse(midNightNextDay);
							
							if(timeOutDate.after(midNightNextDayDate)){
								//day1Hrs = midNightNextDayDate - timeInDate
								//day2Hrs = timeOutDate - midNightNextDayDate
								long ndDiffSeconds1st = (midNightNextDayDate.getTime() - timeInDate.getTime()) / 1000;
								int totalDiffHours1st = (int) ndDiffSeconds1st / 3600;    
								ndDiffSeconds1st = ndDiffSeconds1st % 3600;
								int mins1st = (int) ndDiffSeconds1st / 60;
								
								double finalHours1st = 0;
								
								if(mins1st >= 15){
									if(mins1st >= 30){
										if(mins1st >= 45){
											finalHours1st = totalDiffHours1st + 0.75;
										} else {
											finalHours1st = totalDiffHours1st + 0.5;
										}
									} else {
										finalHours1st = totalDiffHours1st + 0.25;
									}
								} else {
									finalHours1st = totalDiffHours1st;
								}
								
								long ndDiffSeconds2nd = (timeOutDate.getTime() - midNightNextDayDate.getTime()) / 1000;
								int totalDiffHours2nd = (int) ndDiffSeconds2nd / 3600;    
								ndDiffSeconds2nd = ndDiffSeconds2nd % 3600;
								int mins2nd = (int) ndDiffSeconds2nd / 60;
								
								double finalHours2nd = 0;
								
								if(mins2nd >= 15){
									if(mins2nd >= 30){
										if(mins2nd >= 45){
											finalHours2nd = totalDiffHours2nd + 0.75;
										} else {
											finalHours2nd = totalDiffHours2nd + 0.5;
										}
									} else {
										finalHours2nd = totalDiffHours2nd + 0.25;
									}
								} else {
									finalHours2nd = totalDiffHours2nd;
								}
								
								
								
								day1Hrs = finalHours1st;
								day2Hrs = finalHours2nd;
								
								
							} else {
								//time out is before or equal 12MN the following day
												
								long totalDiffSeconds = (timeOutDate.getTime() - timeInDate.getTime()) / 1000;
								int totalDiffHours = (int) totalDiffSeconds / 3600;    
								totalDiffSeconds = totalDiffSeconds % 3600;
								int mins = (int) totalDiffSeconds / 60;

								double finalHours = 0;
											
								if(mins >= 15){
									if(mins >= 30){
										if(mins >= 45){
											finalHours = totalDiffHours + 0.75;
										} else {
											finalHours = totalDiffHours + 0.5;
										}
									} else {
										finalHours = totalDiffHours + 0.25;
									}
								} else {
									finalHours = totalDiffHours;
								}
												
								day1Hrs = finalHours; 
							}
							
							
							System.out.println("day1Hrs: " + day1Hrs);
							System.out.println("day2Hrs: " + day2Hrs);
							System.out.println("======================================");
							
							BigDecimal holidayPay = BigDecimal.ZERO;
							BigDecimal holidayPay1 = BigDecimal.ZERO;
							BigDecimal holidayPay2 = BigDecimal.ZERO;
							
							if(day2Hrs > 0){
								//This means ND if for day1 and day2
								String holidayType1 = isTimeEntryHoliday(day1);
								String holidayType2 = isTimeEntryHoliday(day2);
								
								double totalHrs = 0;
								
								if(holidayType1 != null && holidayType1.length() > 0 && day1Hrs > 0) {
									totalHrs = day1Hrs;
									
									if(PayrollConstants.HOLIDAY_REGULAR_INDICATOR.equals(holidayType1)) {
										holidayPay1 = new BigDecimal(day1Hrs * regularEmpExt.getHourlyRate().doubleValue() * PayrollConstants.HOLIDAY_REGULAR_RATE);
									} else if(PayrollConstants.HOLIDAY_SPECIAL_INDICATOR.equals(holidayType1)) {
										holidayPay1 = new BigDecimal(day1Hrs * regularEmpExt.getHourlyRate().doubleValue() * PayrollConstants.HOLIDAY_SPECIAL_RATE);
									} else if(PayrollConstants.HOLIDAY_DOUBLE_INDICATOR.equals(holidayType1)) {
										holidayPay1 = new BigDecimal(day1Hrs * regularEmpExt.getHourlyRate().doubleValue() * PayrollConstants.HOLIDAY_DOUBLEHOLIDAYRESTDATE_RATE);
									}
									
								}
								
								holidayPayTotal = holidayPayTotal.add(holidayPay1);
								
								if(holidayType2 != null && holidayType2.length() > 0 && day2Hrs > 0) {
									totalHrs = totalHrs + day2Hrs;
									
									// day2Hrs = 8 - 6;
									if(totalHrs > 8){
										day2Hrs = 8 - day1Hrs;
									}
									
									if(PayrollConstants.HOLIDAY_REGULAR_INDICATOR.equals(holidayType2)) {
										holidayPay2 = new BigDecimal(day2Hrs * regularEmpExt.getHourlyRate().doubleValue() * PayrollConstants.HOLIDAY_REGULAR_RATE);
									} else if(PayrollConstants.HOLIDAY_SPECIAL_INDICATOR.equals(holidayType2)) {
										holidayPay2 = new BigDecimal(day2Hrs * regularEmpExt.getHourlyRate().doubleValue() * PayrollConstants.HOLIDAY_SPECIAL_RATE);
									} else if(PayrollConstants.HOLIDAY_DOUBLE_INDICATOR.equals(holidayType2)) {
										holidayPay2 = new BigDecimal(day2Hrs * regularEmpExt.getHourlyRate().doubleValue() * PayrollConstants.HOLIDAY_DOUBLEHOLIDAYRESTDATE_RATE);
									}
								}
								
								holidayPayTotal = holidayPayTotal.add(holidayPay2);
								
								
								
							} else {
								//Holiday Pay is for timeIn Date only
								String holidayType1 = isTimeEntryHoliday(day1);
								
								if(day1Hrs > 8){
									day1Hrs = 8;
								}
								
								if(holidayType1 != null && holidayType1.length() > 0) {
									if(PayrollConstants.HOLIDAY_REGULAR_INDICATOR.equals(holidayType1)) {
										holidayPay = new BigDecimal(day1Hrs * regularEmpExt.getHourlyRate().doubleValue() * PayrollConstants.HOLIDAY_REGULAR_RATE);
									} else if(PayrollConstants.HOLIDAY_SPECIAL_INDICATOR.equals(holidayType1)) {
										holidayPay = new BigDecimal(day1Hrs * regularEmpExt.getHourlyRate().doubleValue() * PayrollConstants.HOLIDAY_SPECIAL_RATE);
									} else if(PayrollConstants.HOLIDAY_DOUBLE_INDICATOR.equals(holidayType1)) {
										holidayPay = new BigDecimal(day1Hrs * regularEmpExt.getHourlyRate().doubleValue() * PayrollConstants.HOLIDAY_DOUBLEHOLIDAYRESTDATE_RATE);
									}
								}	
								
								holidayPayTotal = holidayPayTotal.add(holidayPay);
							}
							
						}//if
					}//if
				}//if				
			}//else
		}//For
		
		
		return holidayPayTotal;		
		

	}
	
	//TODO - Need to Test
	public BigDecimal getTardiness (Map<String, TimeEntry> attendanceMap, List<TimeEntry> scheduleList, EmployeePayrollRunExt regularEmpExt) throws SQLException, Exception {
		BigDecimal tardinessAmount = BigDecimal.ZERO;
				
		double hourlyRate = (regularEmpExt != null && regularEmpExt.getHourlyRate() != null) ? regularEmpExt.getHourlyRate().doubleValue() : 0;
		double minRate = hourlyRate / 60;
		
		TimeEntry attendance = null;
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for (TimeEntry empSched: scheduleList) {			
			String scheduleStr = empSched.getScheduleDate();
			
			if(attendanceMap.containsKey(scheduleStr)){
				//Employee is present
				attendance = attendanceMap.get(scheduleStr);
				
				String timeInSched = scheduleStr + " " + empSched.getTimeIn().substring(0,8);
				String timeOutSched = scheduleStr + " " + empSched.getTimeOut().substring(0,8);
				String timeIn = attendance.getTimeIn().substring(0,19);
				String timeOut = attendance.getTimeOut().substring(0,19);
				
				Date timeInSchedDate = sdf.parse(timeInSched);
				Date timeOutSchedDate = sdf.parse(timeOutSched);
				Date timeInDate = sdf.parse(timeIn);
				Date timeOutDate = sdf.parse(timeOut);
				
				if(timeOutSchedDate.after(timeInSchedDate)){
					//Time in schedule if the same date as timeOut do nothing
				} else {
					//Time Out schedule is the following day
					timeOutSched = incrementDate(scheduleStr) + " " + empSched.getTimeOut().substring(0,10);
					timeOutSchedDate = sdf.parse(timeOutSched);
				}
				
				//Check if schedule time in is after actual time in. If yes then use schedule time in otherwise use actual time in
				if(timeInSchedDate.after(timeInDate)){
					//use timein schedule
					//Employee is not late
					
				} else {
					//Emp is late
					long totalDiffSeconds = (timeInDate.getTime() - timeInSchedDate.getTime()) / 1000;
					int totalDiffHours = (int) totalDiffSeconds / 3600;    
					totalDiffSeconds = totalDiffSeconds % 3600;
					int mins = (int) totalDiffSeconds / 60;

					
					tardinessAmount = tardinessAmount.add(new BigDecimal((totalDiffHours * hourlyRate) + (mins * minRate))); 
				}
				
				//Check if actual time out is after schedule time out. If yes then use schedule time out otherwise use actual time out
				if(timeOutDate.after(timeOutSchedDate)){
					//use timein schedule
					//Employee has no undertime
				} else {
					//Employee has undertime
					
					long totalDiffSeconds = (timeOutSchedDate.getTime() - timeOutDate.getTime()) / 1000;
					int totalDiffHours = (int) totalDiffSeconds / 3600;    
					totalDiffSeconds = totalDiffSeconds % 3600;
					int mins = (int) totalDiffSeconds / 60;

					
					tardinessAmount = tardinessAmount.add(new BigDecimal((totalDiffHours * hourlyRate) + (mins * minRate))); 
				}
				
			} else {
				String gracePeriodSchedNextDay = incrementDate(scheduleStr) + " 02:00:00";				
				String nextDayStr = incrementDate(scheduleStr);
				
				if(attendanceMap.containsKey(nextDayStr)){
					TimeEntry attendanceNextDay = attendanceMap.get(incrementDate(scheduleStr));
					
					if(attendanceNextDay != null && attendanceNextDay.getTimeIn() != null){
						Date actualTimeIn = sdf.parse(attendanceNextDay.getTimeIn().substring(0, 21)); 
						Date gracePeriodSchedNextDate = sdf.parse(gracePeriodSchedNextDay);
						
						if(actualTimeIn.before(gracePeriodSchedNextDate)){
							//Employee Present he is only late
							attendance = attendanceMap.get(scheduleStr);
							
							String timeInSched = scheduleStr + " " + empSched.getTimeIn().substring(0,8);
							String timeOutSched = scheduleStr + " " + empSched.getTimeOut().substring(0,8);
							String timeIn = attendance.getTimeIn().substring(0,19);
							String timeOut = attendance.getTimeOut().substring(0,19);
							
							Date timeInSchedDate = sdf.parse(timeInSched);
							Date timeOutSchedDate = sdf.parse(timeOutSched);
							Date timeInDate = sdf.parse(timeIn);
							Date timeOutDate = sdf.parse(timeOut);
							
							if(timeOutSchedDate.after(timeInSchedDate)){
								//Time in schedule if the same date as timeOut do nothing
							} else {
								//Time Out schedule is the following day
								timeOutSched = incrementDate(scheduleStr) + " " + empSched.getTimeOut().substring(0,10);
								timeOutSchedDate = sdf.parse(timeOutSched);
							}
							
							//Check if schedule time in is after actual time in. If yes then use schedule time in otherwise use actual time in
							if(timeInSchedDate.after(timeInDate)){
								//Not Late
								
							} else {
								//Emp is late
								long totalDiffSeconds = (timeInDate.getTime() - timeInSchedDate.getTime()) / 1000;
								int totalDiffHours = (int) totalDiffSeconds / 3600;    
								totalDiffSeconds = totalDiffSeconds % 3600;
								int mins = (int) totalDiffSeconds / 60;

								
								tardinessAmount = tardinessAmount.add(new BigDecimal((totalDiffHours * hourlyRate) + (mins * minRate))); 
								
							}
							
							//Check if actual time out is after schedule time out. If yes then use schedule time out otherwise use actual time out
							if(timeOutDate.after(timeOutSchedDate)){
								//use timein schedule
								//emp has no undertime
							} else {
								//Employee has undertime
								
								long totalDiffSeconds = (timeOutSchedDate.getTime() - timeOutDate.getTime()) / 1000;
								int totalDiffHours = (int) totalDiffSeconds / 3600;    
								totalDiffSeconds = totalDiffSeconds % 3600;
								int mins = (int) totalDiffSeconds / 60;

								
								tardinessAmount = tardinessAmount.add(new BigDecimal((totalDiffHours * hourlyRate) + (mins * minRate))); 
							}
							
						}//if
					}//if
				}//if				
			}//else
		}//For
		
		
		return tardinessAmount;
	}
	
	private int isOnOffSet(int empId, String dateStr)  throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
		int hours = 0;
		
		sql.append("SELECT noOfHours FROM empOffSet e WHERE e.empId = ");
		sql.append(empId);
		sql.append(" AND CONVERT(DATE,e.dateRendered) = '");
		sql.append(dateStr);
		sql.append("' AND e.status = 6");
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {				
			hours = rs.getInt("noOfHours");
		}
		
		return hours;
	}
	
	private boolean isOnLeave(int empId, String dateStr)  throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
		boolean isOnLeave = false;
		
		sql.append("SELECT * FROM leave e WHERE e.empId = ");
		sql.append(empId);
		sql.append(" AND CONVERT(DATE,e.dateFrom) >= '");
		sql.append(dateStr);
		sql.append("'");
		sql.append(" AND CONVERT(DATE,e.dateTo) <= '");
		sql.append(dateStr);
		sql.append("' AND e.status = 6");
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			isOnLeave = true;
			break;
		}
		
		return isOnLeave;
	}
	
	private boolean isOutOfOffice(int empId, String dateStr)  throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
		boolean isOnLeave = false;
		
		sql.append("SELECT * FROM empOutOfOffice e WHERE e.empId = ");
		sql.append(empId);
		sql.append(" AND CONVERT(DATE,e.dateFrom) >= '");
		sql.append(dateStr);
		sql.append("'");
		sql.append(" AND CONVERT(DATE,e.dateTo) <= '");
		sql.append(dateStr);
		sql.append("' AND e.status = 6");
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			isOnLeave = true;
			break;
		}
		
		return isOnLeave;
	}
	
	
	
	//TODO - Done Except for Leave Credits - Need to test
	public BigDecimal getAbsenceAmount (Map<String, TimeEntry> attendanceMap, List<TimeEntry> scheduleList, EmployeePayrollRunExt regularEmpExt) throws SQLException, Exception {
		BigDecimal absenceAmount = BigDecimal.ZERO;
				
		double dailyRate = (regularEmpExt != null && regularEmpExt.getDailyRate() != null) ? regularEmpExt.getDailyRate().doubleValue() : 0;
		
		for (TimeEntry attendance: scheduleList) {
			
			BigDecimal absent = BigDecimal.ZERO;
			
			String scheduleStr = attendance.getScheduleDate();
			
			if(attendanceMap.containsKey(scheduleStr)){
				//Employee is present
				
			} else {
				//Employee is probably absent
				
				//Check if OffSet, Check if OOO Check if on Leave 
				//TODO Check if has leave credits
				if(isOnOffSet(regularEmpExt.getEmpId(), scheduleStr) > 0){
					// On Off Set
					absent = new BigDecimal(0);
				} else if (isOnLeave(regularEmpExt.getEmpId(), scheduleStr)) {
					// On Leave
					absent = new BigDecimal(0);
				} else if (isOutOfOffice(regularEmpExt.getEmpId(), scheduleStr)) {
					absent = new BigDecimal(0);
//				} else if (hasLeaveCredits(empId)) {
//					absent = new BigDecimal(0);
				} else {
					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					String gracePeriodSchedNextDay = incrementDate(scheduleStr) + " 02:00:00";
					
					String nextDayStr = incrementDate(scheduleStr);
					
					if(attendanceMap.containsKey(nextDayStr)){
						TimeEntry attendanceNextDay = attendanceMap.get(incrementDate(scheduleStr));
						
						if(attendanceNextDay != null && attendanceNextDay.getTimeIn() != null){
							Date actualTimeIn = sdf.parse(attendanceNextDay.getTimeIn().substring(0, 21)); 
							Date gracePeriodSchedNextDate = sdf.parse(gracePeriodSchedNextDay);
							
							if(actualTimeIn.after(gracePeriodSchedNextDate)){
								//Employee is really absent
								absent = new BigDecimal(dailyRate);						
								
							} else {
								//Employee Present he is only late
								absent = new BigDecimal(0);
							}//else						
						}//if					
						
					} else {
						//Employee is really absent
						absent = new BigDecimal(dailyRate);
					}//else
				}				
								
			}//else					
			
			absenceAmount = absenceAmount.add(absent);
		}//for
		
		
		return absenceAmount;
	}
	
	public BigDecimal getOvertimePay (EmployeePayrollRunExt regularEmpExt) throws SQLException, Exception {
		BigDecimal overtimePayTotal = BigDecimal.ZERO;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT dateRendered, noOfHours, empId FROM empOvertime WHERE empId = ");
		sql.append(regularEmpExt.getEmpId());
		sql.append(" AND status = 6 AND CONVERT(DATE,dateRendered) BETWEEN '");
		sql.append(regularEmpExt.getFromDate());
		sql.append("'");
		sql.append(" AND '");
		sql.append(regularEmpExt.getToDate());
		sql.append("'");
		
		System.out.println("SQL getOvertimePay: " + sql.toString());
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		ResultSet rs = ps.executeQuery();
		
		double hourlyRate = (regularEmpExt != null && regularEmpExt.getHourlyRate() != null) ? regularEmpExt.getHourlyRate().doubleValue() : 0;
		
		while (rs.next()) {
			String holidayType = isTimeEntryHoliday(rs.getString("dateRendered"));
			BigDecimal overtimePay = BigDecimal.ZERO;
			
			if(holidayType != null && holidayType.length() > 0) {
				if(PayrollConstants.HOLIDAY_REGULAR_INDICATOR.equals(holidayType)) {
					overtimePay = new BigDecimal(rs.getInt("noOfHours") * hourlyRate * PayrollConstants.OVERTIME_REGULARHOLIDAY_RATE);
				} else if(PayrollConstants.HOLIDAY_SPECIAL_INDICATOR.equals(holidayType)) {
					overtimePay = new BigDecimal(rs.getInt("noOfHours") * hourlyRate * PayrollConstants.OVERTIME_SPECIALHOLIDAY_RATE);
				} else if(PayrollConstants.HOLIDAY_DOUBLE_INDICATOR.equals(holidayType)) {
					overtimePay = new BigDecimal(rs.getInt("noOfHours") * hourlyRate * PayrollConstants.OVERTIME_DOUBLEHOLIDAY_RATE);
				}
			} else {
				//NOT A HOLIDAY
				overtimePay = new BigDecimal(rs.getInt("noOfHours") * hourlyRate * PayrollConstants.OVERTIME_ORDINARY_RATE);
			}			
			
			overtimePayTotal = overtimePayTotal.add(overtimePay);
		}
		
		return overtimePayTotal;
	}
	
	public SystemParameters getSystemParameters() throws SQLException, Exception {
		
		SystemParameters model = new SystemParameters();
    	String sql = "SELECT * FROM sysAdmin";
		PreparedStatement ps = conn.prepareStatement(sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    if (rs.next()) {	    	
	    	model.setAdminId(rs.getInt("adminId"));
	    	model.setAdminAssistantId(rs.getInt("adminAssistantId"));
	    	model.setHrAdminId(rs.getInt("hrHeadId"));
	    	model.setHrAdminAssistantId(rs.getInt("hrAssistantHeadId"));
	    	model.setHrAdminLiasonId(rs.getInt("hrLiasonId"));	    	
	    	model.setIsAdminChecked(rs.getString("isAdminApprover"));
	    	model.setContractualBreakHrs(rs.getInt("contractualBreakHrs"));
	    	model.setContractualHrs(rs.getInt("contractualHrs"));
	    	model.setMinPay(rs.getBigDecimal("minPay"));
	    	model.setPartimeHrs(rs.getInt("partimeHrs"));
	    	model.setRegHrs(rs.getInt("regHrs"));
	    	model.setIsNightDiffContractual(rs.getString("isNightDiffContractual"));	    	
	    }
	    
	    return model;
		
	}
	
	public List<Integer> getOffSet (int empId, String startDate, String endDate) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
		List<Integer> list = new ArrayList<Integer>();
		
		sql.append("SELECT noOfHours FROM empOffSet e WHERE e.empId = ");
		sql.append(empId);
		sql.append(" AND CONVERT(DATE,e.dateRendered) BETWEEN '");
		sql.append(startDate);
		sql.append("' AND '");
		sql.append(endDate);
		sql.append("' AND e.status = 6");
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {				
			list.add(rs.getInt("noOfHours"));
		}
		
		return list;
	}
	
	public List<Integer> getOverTimeHours (int empId, String startDate, String endDate) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
		List<Integer> list = new ArrayList<Integer>();
		
		sql.append("SELECT noOfHours FROM empOvertime e WHERE e.empId = ");
		sql.append(empId);
		sql.append(" AND CONVERT(DATE,e.dateRendered) BETWEEN '");
		sql.append(startDate);
		sql.append("' AND '");
		sql.append(endDate);
		sql.append("' AND e.status = 6");
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {				
			list.add(rs.getInt("noOfHours"));
		}
		
		return list;
	}
	
	
	
	
	
	public List<Integer> getUnderTimeHours (int empId, String startDate, String endDate) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
		List<Integer> list = new ArrayList<Integer>();
		
		sql.append("SELECT noOfHours FROM empUndertime e WHERE e.empId = ");
		sql.append(empId);
		sql.append(" AND CONVERT(DATE,e.dateRendered) BETWEEN '");
		sql.append(startDate);
		sql.append("' AND '");
		sql.append(endDate);
		sql.append("' AND e.status = 6");
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {				
			list.add(rs.getInt("noOfHours"));
		}
		
		return list;
	}
	
	
	
	public List<TimeEntry> getApprovedLeaves (int empId, String startDate, String endDate) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
		List<TimeEntry> list = new ArrayList<TimeEntry>();
		
		sql.append("SELECT *, DATEDIFF(day,e.dateFrom, e.dateTo) totalDays FROM leave e WHERE e.empId = ");
		sql.append(empId);
		sql.append(" AND CONVERT(DATE,e.dateFrom) >= '");
		sql.append(startDate);
		sql.append("'");
		sql.append(" AND CONVERT(DATE,e.dateTo) <= '");
		sql.append(endDate);
		sql.append("' AND e.status = 6");
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			TimeEntry model = new TimeEntry();
			
			
			model.setEmpId(empId);
			model.setTimeIn(rs.getString("dateFrom"));
			model.setTimeOut(rs.getString("dateTo"));
			model.setTotalHoursPerShift(rs.getInt("totalDays"));
			
			list.add(model);
		}
		
		return list;
	}
	
	
	public List<EmployeeOutOfOffice> getOutOfOffice (int empId, String startDate, String endDate) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
		List<EmployeeOutOfOffice> list = new ArrayList<EmployeeOutOfOffice>();
		
		sql.append("SELECT *, DATEDIFF(day,e.dateFrom, e.dateTo) totalDays FROM empOutOfOffice e WHERE e.empId = ");
		sql.append(empId);
		sql.append(" AND CONVERT(DATE,e.dateFrom) >= '");
		sql.append(startDate);
		sql.append("'");
		sql.append(" AND CONVERT(DATE,e.dateTo) <= '");
		sql.append(endDate);
		sql.append("' AND e.status = 6");
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			EmployeeOutOfOffice model = new EmployeeOutOfOffice();
			
			
			model.setEmpId(empId);
			model.setDateFrom(rs.getString("dateFrom"));
			model.setDateTo(rs.getString("dateTo"));
			model.setNoOfHours(rs.getInt("noOfHours"));
			model.setTotalDays(rs.getInt("totalDays"));
			
			list.add(model);
		}
		
		return list;
	}
	
	public List<TimeEntry> getEmployeeAttendance(int empId, String startDate, String endDate) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
		List<TimeEntry> list = new ArrayList<TimeEntry>();
		
		sql.append("SELECT *, DATEDIFF(Hour,e.timeIn, e.timeOut) totalHours FROM empTimeEntry e WHERE e.empId = ");
		sql.append(empId);
		sql.append(" AND CONVERT(DATE,e.timeIn) >= '");
		sql.append(startDate);
		sql.append("'");
		sql.append(" AND CONVERT(DATE,e.timeOut) <= '");
		sql.append(endDate);
		sql.append("' ORDER BY e.timeIn");
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			TimeEntry model = new TimeEntry();			
			
			model.setEmpId(empId);
			model.setTimeIn(rs.getString("timeIn"));
			model.setTimeOut(rs.getString("timeOut"));
			model.setTotalHoursPerShift(rs.getInt("totalHours"));
			
			list.add(model);
		}
		
		return list;
		
	}
	
	public List<EmployeeHourlyAttendance> getDoctorHourlyAttendance(int empId, String startDate, String endDate) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
		List<EmployeeHourlyAttendance> list = new ArrayList<EmployeeHourlyAttendance>();
		
		sql.append("SELECT * FROM empHourlyAttendance e WHERE e.empId = ");
		sql.append(empId);
		sql.append(" AND CONVERT(DATE,e.attendanceDate) BETWEEN '");
		sql.append(startDate);
		sql.append("' AND '");
		sql.append(endDate);
		sql.append("' AND e.status = 6");
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			EmployeeHourlyAttendance model = new EmployeeHourlyAttendance();			
			
			model.setEmpId(empId);
			model.setAttendanceDate(rs.getString("attendanceDate"));			
			model.setNoOfHours(rs.getInt("noOfHours"));
			
			list.add(model);
		}
		
		return list;
		
	} 
	
	
    public int add(EmployeePayrollRunExt eprE) throws SQLException, Exception {
		
    	StringBuffer sql = new StringBuffer();
    	sql.append("INSERT INTO employeePayrollRun (empId,taxStatus,basicPay,absences,tardiness,undertime,overtime, ");
    	sql.append("leaveWOPay,nightDiff,holidayPay,taxableIncome,nontaxableIncome,grossPay, gsisEmployeeShare, gsisEmployerShare, ");
    	sql.append("philhealthEmployeeShare,philhealthEmployerShare,pagibigEmployeeShare,pagibigEmployerShare, ");
    	sql.append("loans,otherDeductions,withholdingTax,totalDeductions,netPay,payrollCode,payrollPeriodId,payrollRunStatus, ");
    	sql.append("createdBy,createDate,updatedBy,updatedDate,lockedBy,lockedDate,otherTaxableIncome,cola,foodAllowance,taxShield,tranpoAllowance) ");
    	sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		PreparedStatement ps = conn.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS); 
		int index = 1;
		ps.setInt(index++, eprE.getEmpId());
		ps.setString(index++, eprE.getTaxStatus());
		ps.setDouble(index++, eprE.getBasicPay() != null ? eprE.getBasicPay().doubleValue() : 0);  //populate this - value either from monthly, semi monthly, or daily rate
		ps.setDouble(index++, eprE.getAbsences() != null ? eprE.getAbsences().doubleValue() : 0);
		ps.setDouble(index++, eprE.getTardiness() != null ? eprE.getTardiness().doubleValue() : 0);
		ps.setDouble(index++, eprE.getUndertime() != null ? eprE.getUndertime().doubleValue() : 0);
		ps.setDouble(index++, eprE.getOvertime() != null ? eprE.getOvertime().doubleValue() : 0);		
		ps.setDouble(index++, 0);
		ps.setDouble(index++, eprE.getNightDiff() != null ? eprE.getNightDiff().doubleValue() : 0);
		ps.setDouble(index++, eprE.getHolidayPay() != null ? eprE.getHolidayPay().doubleValue() : 0);		
		ps.setDouble(index++, eprE.getTaxableIncome() != null ? eprE.getTaxableIncome().doubleValue() : 0);		
		ps.setDouble(index++, eprE.getNontaxableIncome() != null ? eprE.getNontaxableIncome().doubleValue() : 0);		
		ps.setDouble(index++, eprE.getGrossPay() != null ? eprE.getGrossPay().doubleValue() : 0);
		ps.setDouble(index++, eprE.getGsisEmployeeShare() != null ? eprE.getGsisEmployeeShare().doubleValue() : 0);
		ps.setDouble(index++, eprE.getGsisEmployerShare() != null ? eprE.getGsisEmployerShare().doubleValue() : 0);				
		ps.setDouble(index++, eprE.getPhilhealthEmployeeShare() != null ? eprE.getPhilhealthEmployeeShare().doubleValue() : 0);
		ps.setDouble(index++, eprE.getPhilhealthEmployerShare() != null ? eprE.getPhilhealthEmployerShare().doubleValue() : 0);		
		ps.setDouble(index++, eprE.getPagibigEmployeeShare() != null ? eprE.getPagibigEmployeeShare().doubleValue() : 0);
		ps.setDouble(index++, eprE.getPagibigEmployerShare() != null ? eprE.getPagibigEmployerShare().doubleValue() : 0);		
		ps.setDouble(index++, eprE.getLoans() != null ? eprE.getLoans().doubleValue() : 0);		
		ps.setDouble(index++, eprE.getOtherDeductions() != null ? eprE.getOtherDeductions().doubleValue() : 0);		
		ps.setDouble(index++, eprE.getWithholdingTax() != null ? eprE.getWithholdingTax().doubleValue() : 0);
		ps.setDouble(index++, eprE.getTotalDeductions() != null ? eprE.getTotalDeductions().doubleValue() : 0);
		ps.setDouble(index++, eprE.getNetPay() != null ? eprE.getNetPay().doubleValue() : 0);
		ps.setString(index++, eprE.getPayrollCode());
		ps.setInt(index++, eprE.getPayrollPeriodId());
		ps.setString(index++, eprE.getPayrollRunStatus());		
		ps.setString(index++, eprE.getCreatedBy());
		ps.setDate(index++, eprE.getCreateDate());
		ps.setString(index++, eprE.getUpdatedBy());
		ps.setDate(index++, eprE.getUpdatedDate());
		ps.setString(index++, eprE.getLockedBy());
		ps.setDate(index++, eprE.getLockedDate());
		ps.setDouble(index++, eprE.getOtherTaxableIncome() != null ? eprE.getOtherTaxableIncome().doubleValue() : 0);
		ps.setDouble(index++, eprE.getCola() != null ? eprE.getCola().doubleValue() : 0);
		ps.setDouble(index++, eprE.getFoodAllowance() != null ? eprE.getFoodAllowance().doubleValue() : 0);
		ps.setDouble(index++, eprE.getTaxShield() != null ? eprE.getTaxShield().doubleValue() : 0);
		ps.setDouble(index++, eprE.getTransAllowance() != null ? eprE.getTransAllowance().doubleValue() : 0);
		
		ps.executeUpdate();
		ResultSet keyResultSet = ps.getGeneratedKeys(); 
		int generatedAutoIncrementId = 0; 
		if (keyResultSet.next()) {
			generatedAutoIncrementId = (int) keyResultSet.getInt(1);
			eprE.setEmployeePayrollRunId(generatedAutoIncrementId);
			conn.commit(); 
		}
		 
		ps.close();
		keyResultSet.close();
		return generatedAutoIncrementId;


	
    }
	
	
	
    public int update(EmployeePayrollRunExt eprE) throws SQLException, Exception {
		
    	StringBuffer sql = new StringBuffer();
    	sql.append("UPDATE employeePayrollRun SET empId=?, taxStatus=?, basicPay=?, absences=?, tardiness=?, undertime=?, overtime=?, ");
    	sql.append("leaveWOPay=?, nightDiff=?, holidayPay=?, taxableIncome=?, nontaxableIncome=?, grossPay=?, gsisEmployeeShare=?, gsisEmployerShare=?, ");
    	sql.append("philhealthEmployeeShare=?, philhealthEmployerShare=?, pagibigEmployeeShare=?, pagibigEmployerShare=?, ");
    	sql.append("loans=?, otherDeductions=?, withholdingTax=?, totalDeductions=?, netPay=?, payrollCode=?, payrollPeriodId=?, payrollRunStatus=?, ");
    	sql.append("createdBy=?, createDate=?, updatedBy=?, updatedDate=?, lockedBy=?, lockedDate=?, otherTaxableIncome=?, cola=?, foodAllowance=?, taxShield=?, tranpoAllowance=? ");
    	sql.append("WHERE employeePayrollRunId = ? ");
		PreparedStatement ps = conn.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS); 
		int index = 1;
		ps.setInt(index++, eprE.getEmpId());
		ps.setString(index++, eprE.getTaxStatus());
		ps.setDouble(index++, eprE.getBasicPay() != null ? eprE.getBasicPay().doubleValue() : 0);  //populate this - value either from monthly, semi monthly, or daily rate
		ps.setDouble(index++, eprE.getAbsences() != null ? eprE.getAbsences().doubleValue() : 0);
		ps.setDouble(index++, eprE.getTardiness() != null ? eprE.getTardiness().doubleValue() : 0);
		ps.setDouble(index++, eprE.getUndertime() != null ? eprE.getUndertime().doubleValue() : 0);
		ps.setDouble(index++, eprE.getOvertime() != null ? eprE.getOvertime().doubleValue() : 0);
		
		ps.setDouble(index++, 0);
		ps.setDouble(index++, eprE.getNightDiff() != null ? eprE.getNightDiff().doubleValue() : 0);
		ps.setDouble(index++, eprE.getHolidayPay() != null ? eprE.getHolidayPay().doubleValue() : 0);		
		ps.setDouble(index++, eprE.getTaxableIncome() != null ? eprE.getTaxableIncome().doubleValue() : 0);		
		ps.setDouble(index++, eprE.getNontaxableIncome() != null ? eprE.getNontaxableIncome().doubleValue() : 0);
		
		ps.setDouble(index++, eprE.getGrossPay() != null ? eprE.getGrossPay().doubleValue() : 0);
		ps.setDouble(index++, eprE.getGsisEmployeeShare() != null ? eprE.getGsisEmployeeShare().doubleValue() : 0);
		ps.setDouble(index++, eprE.getGsisEmployerShare() != null ? eprE.getGsisEmployerShare().doubleValue() : 0);
				
		ps.setDouble(index++, eprE.getPhilhealthEmployeeShare() != null ? eprE.getPhilhealthEmployeeShare().doubleValue() : 0);
		ps.setDouble(index++, eprE.getPhilhealthEmployerShare() != null ? eprE.getPhilhealthEmployerShare().doubleValue() : 0);		
		ps.setDouble(index++, eprE.getPagibigEmployeeShare() != null ? eprE.getPagibigEmployeeShare().doubleValue() : 0);
		ps.setDouble(index++, eprE.getPagibigEmployerShare() != null ? eprE.getPagibigEmployerShare().doubleValue() : 0);
		
		ps.setDouble(index++, eprE.getLoans() != null ? eprE.getLoans().doubleValue() : 0);
		ps.setDouble(index++, eprE.getOtherDeductions() != null ? eprE.getOtherDeductions().doubleValue() : 0);
		
		//ps.setDouble(index++, 0);
		//ps.setDouble(index++, 0);
		
		ps.setDouble(index++, eprE.getWithholdingTax() != null ? eprE.getWithholdingTax().doubleValue() : 0);
		ps.setDouble(index++, eprE.getTotalDeductions() != null ? eprE.getTotalDeductions().doubleValue() : 0);
		ps.setDouble(index++, eprE.getNetPay() != null ? eprE.getNetPay().doubleValue() : 0);
		ps.setString(index++, eprE.getPayrollCode());
		ps.setInt(index++, eprE.getPayrollPeriodId());
		ps.setString(index++, eprE.getPayrollRunStatus());
		
		ps.setString(index++, eprE.getCreatedBy());
		ps.setDate(index++, eprE.getCreateDate());
		ps.setString(index++, eprE.getUpdatedBy());
		ps.setDate(index++, eprE.getUpdatedDate());
		ps.setString(index++, eprE.getLockedBy());
		ps.setDate(index++, eprE.getLockedDate());
		ps.setDouble(index++, eprE.getOtherTaxableIncome() != null ? eprE.getOtherTaxableIncome().doubleValue() : 0);
		ps.setDouble(index++, eprE.getCola() != null ? eprE.getCola().doubleValue() : 0);
		ps.setDouble(index++, eprE.getFoodAllowance() != null ? eprE.getFoodAllowance().doubleValue() : 0);
		ps.setDouble(index++, eprE.getTaxShield() != null ? eprE.getTaxShield().doubleValue() : 0);
		ps.setDouble(index++, eprE.getTransAllowance() != null ? eprE.getTransAllowance().doubleValue() : 0);
		
		
		ps.setInt(index++, eprE.getEmployeePayrollRunId());
		
		
		ps.executeUpdate();
		 ResultSet keyResultSet = ps.getGeneratedKeys(); 
		 int generatedAutoIncrementId = 0; 
		 	if (keyResultSet.next()) {
		 		generatedAutoIncrementId = (int) keyResultSet.getInt(1);
		 		eprE.setEmployeePayrollRunId(generatedAutoIncrementId);
		 		conn.commit(); 
		 	}
		 
		 ps.close();
		 keyResultSet.close();
		 return generatedAutoIncrementId;


	
}	
    
    
    public void updatePayrollPeriodStatus(int payrollPeriodId, String status, int empId) throws SQLException, Exception {
		
    	StringBuffer sql = new StringBuffer();
    	
    	if(status.equals("L")) {
    		sql.append("UPDATE payrollPeriod SET status = '"); 
    		sql.append(status);
    		sql.append("', updatedAt = GETDATE(), lockedAt = GETDATE()  WHERE payrollPeriodId = ");
    		sql.append(payrollPeriodId);
    	} else {
    		sql.append("UPDATE payrollPeriod SET status = '");    	
    		sql.append(status);
    		sql.append("', updatedAt = GETDATE()  WHERE payrollPeriodId = ");
    		sql.append(payrollPeriodId);
    	}
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString()); 
//		int index = 1;
//		ps.setString(index++, status);
//		ps.setInt(index++, payrollPeriodId);		
    	
		ps.executeUpdate();	 

		
		StringBuffer sql2 = new StringBuffer();
    	sql2.append("UPDATE employeePayrollRun SET payrollRunStatus = '");
    	sql2.append(status);
		sql2.append("', updatedDate = GETDATE(), updatedBy = ");
		sql2.append(empId);
		sql2.append(" WHERE payrollPeriodId = ");
		sql2.append(payrollPeriodId);
		PreparedStatement ps2 = conn.prepareStatement(sql2.toString()); 
//		index = 1;
//		ps2.setString(index++, status);
//		ps2.setInt(index++, empId);
//		ps2.setInt(index++, payrollPeriodId);		
		ps2.executeUpdate();	
		
		conn.commit();
		 
		ps.close();		
		ps2.close();		
	
    }	
    

	
	
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}

	
	
}
