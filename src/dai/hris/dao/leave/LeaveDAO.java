package dai.hris.dao.leave;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import dai.hris.dao.DBConstants;
import dai.hris.model.Leave;

public class LeaveDAO {
	
	Connection conn = null;
    
    public LeaveDAO() {    	
    	
    	try {
    		
    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("LeaveDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("LeaveDAO :" + e.getMessage());
		}
    	
    }
    
    public void updateLeaveStatus(Leave leave) throws SQLException, Exception {
    	
    	String sql = "UPDATE leave "
				+ "set approvedBy=?, "
				+ "status=? "
				+ "WHERE leaveId=?";
		PreparedStatement ps = conn.prepareStatement(sql); 
		
		ps.setInt(1, leave.getApprovedBy());		
		ps.setInt(2, leave.getStatus());
		ps.setInt(3, leave.getLeaveId());
		ps.executeUpdate();
		conn.commit();
		ps.close();
    	
    }
    
    public void updateLeaveStatusHR(Leave leave) throws SQLException, Exception {
    	
    	String sql = "UPDATE leave "
    			+ "set approvedBy=?, "
				+ "status=? "
				+ "WHERE leaveId=?";
		PreparedStatement ps = conn.prepareStatement(sql); 
		
		ps.setInt(1, leave.getApprovedBy());
		ps.setInt(2, leave.getStatus());
		ps.setInt(3, leave.getLeaveId());
	

		ps.executeUpdate();
		conn.commit();
		ps.close();
    	
    }
    
    public  int getCountForHRApproval() throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM leave WHERE status <> 3 ");   	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    if (rs.next()) {    	
	    	count = rs.getInt("ctr");
	    }
	    
	    ps.close();
	    rs.close();      
	    
    	
    	return count;
    }
    
    
//    public  int getCountForSupervisor(int supervisorId) throws SQLException, Exception {
//    	int count = 0;
//    	
//    	StringBuffer sql = new StringBuffer();
//    	
//    	
//    	sql.append("SELECT count(*) as ctr FROM employee e, leave l WHERE e.empId = l.empId AND e.superVisor1Id = ");   	
//    	sql.append(supervisorId);
//    	  	
//		PreparedStatement ps = conn.prepareStatement(sql.toString());
//		
//		System.out.println("SQL: " + sql.toString());
//
//	    ResultSet rs = ps.executeQuery();
//	    
//	    
//	    if (rs.next()) {    	
//	    	count = rs.getInt("ctr");
//	    }
//	    
//	    ps.close();
//	    rs.close();      
//	    
//    	
//    	return count;
//    }
    
    public  int getCountByEmpId(int empId) throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM leave WHERE empId = ");   	
    	sql.append(empId);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    if (rs.next()) {    	
	    	count = rs.getInt("ctr");
	    }
	    
	    ps.close();
	    rs.close();      
	    
    	
    	return count;
    }
    
    public  int getCountByEmpIdDateRange(int empId, String dateFrom, String dateTo) throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM leave WHERE empId = ");   	
    	sql.append(empId);
    	sql.append(" AND ((dateFrom between '");
    	sql.append(dateFrom);
    	sql.append("' AND '");
    	sql.append(dateTo);
    	sql.append("') OR (dateTo between '");
    	sql.append(dateFrom);
    	sql.append("' AND '");
    	sql.append(dateTo);
    	sql.append("'))");
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    if (rs.next()) {    	
	    	count = rs.getInt("ctr");
	    }
	    
	    ps.close();
	    rs.close();      
	    
    	
    	return count;
    }
	
    public List<Leave> getAllLeavesByEmpIdDateRange(int empId, int jtStartIndex, int jtPageSize, String jtSorting, String dateFrom, String dateTo) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "l.dateFrom ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT l.leaveId, l.empId, l.dateFiled, l.dateFrom, l.dateTo, l.noDays, l.remarks, l.status, l.approvedBy, l.secondaryApprover, l.need2Approvers, l.leaveTypeId, l.createdBy, l.createdDate, lt.leaveTypeName, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM leave l, leaveType lt WHERE l.leaveTypeId = lt.leaveTypeId AND l.empId = ");
    	sql.append(empId);
    	sql.append(" AND ((dateFrom between '");
    	sql.append(dateFrom);
    	sql.append("' AND '");
    	sql.append(dateTo);
    	sql.append("') OR (dateTo between '");
    	sql.append(dateFrom);
    	sql.append("' AND '");
    	sql.append(dateTo);
    	sql.append("'))");
    	sql.append(" ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<Leave> list = new ArrayList<Leave>();
	    
	    while (rs.next()) {
	    	Leave leave = new Leave();
	    	leave.setLeaveId(rs.getInt("leaveId"));
	    	leave.setEmpId(rs.getInt("empId"));
	    	leave.setDateFiled(rs.getDate("dateFiled"));
	    	leave.setDateFrom(rs.getDate("dateFrom"));
	    	leave.setDateTo(rs.getDate("dateTo"));
	    	leave.setNoDays(rs.getInt("noDays"));
	    	leave.setRemarks(rs.getString("remarks"));
	    	leave.setStatus(rs.getInt("status"));
	    	leave.setApprovedBy(rs.getInt("approvedBy"));
	    	leave.setSecondaryApprover(rs.getInt("secondaryApprover"));
	    	leave.setNeed2Approvers(rs.getString("need2Approvers"));
	    	leave.setLeaveTypeId(rs.getInt("leaveTypeId"));
	    	leave.setCreatedBy(rs.getInt("createdBy"));
	    	leave.setCreatedDate(rs.getDate("createdDate"));
	    	leave.setLeaveType(rs.getString("leaveTypeName"));
	    	
	    	list.add(leave);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;
    }
    
    public List<Leave> getAllLeavesByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "l.dateFrom ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT l.leaveId, l.empId, l.dateFiled, l.dateFrom, l.dateTo, l.noDays, l.remarks, l.status, l.approvedBy, l.secondaryApprover, l.need2Approvers, l.leaveTypeId, l.createdBy, l.createdDate, lt.leaveTypeName, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM leave l, leaveType lt WHERE l.leaveTypeId = lt.leaveTypeId AND l.empId = ");
    	sql.append(empId);
    	sql.append(" ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<Leave> list = new ArrayList<Leave>();
	    
	    while (rs.next()) {
	    	Leave leave = new Leave();
	    	leave.setLeaveId(rs.getInt("leaveId"));
	    	leave.setEmpId(rs.getInt("empId"));
	    	leave.setDateFiled(rs.getDate("dateFiled"));
	    	leave.setDateFrom(rs.getDate("dateFrom"));
	    	leave.setDateTo(rs.getDate("dateTo"));
	    	leave.setNoDays(rs.getInt("noDays"));
	    	leave.setRemarks(rs.getString("remarks"));
	    	leave.setStatus(rs.getInt("status"));
	    	leave.setApprovedBy(rs.getInt("approvedBy"));
	    	leave.setSecondaryApprover(rs.getInt("secondaryApprover"));
	    	leave.setNeed2Approvers(rs.getString("need2Approvers"));
	    	leave.setLeaveTypeId(rs.getInt("leaveTypeId"));
	    	leave.setCreatedBy(rs.getInt("createdBy"));
	    	leave.setCreatedDate(rs.getDate("createdDate"));
	    	leave.setLeaveType(rs.getString("leaveTypeName"));
	    	
	    	list.add(leave);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
//    	Leave leave = null;
//		ArrayList<Leave> leaveList = new ArrayList<Leave>();
//		StringBuffer sql = new StringBuffer();
//				
//		sql.append("SELECT l.leaveId, l.empId, l.dateFiled, l.dateFrom, l.dateTo, l.noDays, l.remarks, l.status, l.approvedBy, ");
//		sql.append("l.secondaryApprover, l.need2Approvers, l.leaveTypeId, l.createdBy, l.createdDate, lt.leaveTypeName ");
//		sql.append("FROM leave l, leaveType lt WHERE l.leaveTypeId = lt.leaveTypeId AND  empId = ");
//		sql.append(empId);
//		
//		PreparedStatement ps = conn.prepareStatement(sql.toString());
//		
//
//	    ResultSet rs = ps.executeQuery();
//	    
//	    while (rs.next()) {
//	    	leave = new Leave();
//	    	leave.setLeaveId(rs.getInt("leaveId"));
//	    	leave.setEmpId(rs.getInt("empId"));
//	    	leave.setDateFiled(rs.getDate("dateFiled"));
//	    	leave.setDateFrom(rs.getDate("dateFrom"));
//	    	leave.setDateTo(rs.getDate("dateTo"));
//	    	leave.setNoDays(rs.getInt("noDays"));
//	    	leave.setRemarks(rs.getString("remarks"));
//	    	leave.setStatus(rs.getInt("status"));
//	    	leave.setApprovedBy(rs.getInt("approvedBy"));
//	    	leave.setSecondaryApprover(rs.getInt("secondaryApprover"));
//	    	leave.setNeed2Approvers(rs.getString("need2Approvers"));
//	    	leave.setLeaveTypeId(rs.getInt("leaveTypeId"));
//	    	leave.setCreatedBy(rs.getInt("createdBy"));
//	    	leave.setCreatedDate(rs.getDate("createdDate"));
//	    	leave.setLeaveType(rs.getString("leaveTypeName"));
//	    	
//	    	leaveList.add(leave);
//		}
//	    sql = null;
//	    ps.close();
//	    rs.close();	 
//	    return leaveList;

	}
    
    //getAllLeavesForSvApprovalBySuperVisorId
//    public List<Leave> getAllLeavesForSvApprovalBySuperVisorId(int superVisorId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
//    	StringBuffer sql = new StringBuffer();   
//    	
//    	if(jtSorting == null){
//    		jtSorting = "l.dateFrom DESC";
//    	}
//    	
//    	sql.append("WITH OrderedList AS (SELECT l.leaveId, l.empId, l.dateFiled, l.dateFrom, l.dateTo, l.noDays, l.remarks, l.status, l.approvedBy, l.secondaryApprover, l.need2Approvers, l.leaveTypeId, l.createdBy, l.createdDate, ROW_NUMBER() OVER(ORDER BY ");	
//    	sql.append(jtSorting);    	
//    	sql.append(") AS RowNumber FROM employee e, leave l WHERE e.empId = l.empId AND l.status = 0 AND e.superVisor1Id = ");
//    	sql.append(superVisorId);
//    	sql.append(" ) ");
//    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
//    	sql.append(jtStartIndex);
//    	sql.append(" AND ");
//    	sql.append(jtStartIndex + jtPageSize);
//		
//		System.out.println("SQL: " + sql.toString());   	
//    		
//		PreparedStatement ps = conn.prepareStatement(sql.toString());		
//		
//	    ResultSet rs = ps.executeQuery();
//	    List<Leave> list = new ArrayList<Leave>();
//	    
//	    while (rs.next()) {
//	    	Leave leave = new Leave();
//	    	leave.setLeaveId(rs.getInt("leaveId"));
//	    	leave.setEmpId(rs.getInt("empId"));
//	    	leave.setDateFiled(rs.getDate("dateFiled"));
//	    	leave.setDateFrom(rs.getDate("dateFrom"));
//	    	leave.setDateTo(rs.getDate("dateTo"));
//	    	leave.setNoDays(rs.getInt("noDays"));
//	    	leave.setRemarks(rs.getString("remarks"));
//	    	leave.setStatus(rs.getInt("status"));
//	    	leave.setApprovedBy(rs.getInt("approvedBy"));
//	    	leave.setSecondaryApprover(rs.getInt("secondaryApprover"));
//	    	leave.setNeed2Approvers(rs.getString("need2Approvers"));
//	    	leave.setLeaveTypeId(rs.getInt("leaveTypeId"));
//	    	leave.setCreatedBy(rs.getInt("createdBy"));
//	    	leave.setCreatedDate(rs.getDate("createdDate"));
//	    	//leave.setLeaveType(rs.getString("leaveTypeName"));
//	    	
//	    	list.add(leave);
//	    }
//	    
//	    ps.close();
//	    rs.close();      
//	    return list;    		
//    	
//    	
//    	
//// 		Leave leave = null;
//// 		ArrayList<Leave> leaveList = new ArrayList<Leave>();
//// 		String sql = "SELECT l.* FROM employee e, leave l "+
//// 						"WHERE e.empId = l.empId " +
////						"AND (e.superVisor1Id = ? OR e.superVisor2Id = ? OR e.superVisor3Id=?) " +
////						"AND (l.approvedBy = 0  AND l.secondaryApprover = 0)" +
//// 						"AND l.status = 0";
//// 		PreparedStatement ps = conn.prepareStatement(sql.toString());
//// 		ps.setInt(1, superVisorId);
//// 		ps.setInt(2, superVisorId);
//// 		ps.setInt(3, superVisorId);
//// 		System.out.println(superVisorId);
////
//// 	    ResultSet rs = ps.executeQuery();
//// 	    
//// 	    while (rs.next()) {
//// 	    	leave = new Leave();
//// 	    	leave.setLeaveId(rs.getInt("leaveId"));
//// 	    	leave.setEmpId(rs.getInt("empId"));
//// 	    	leave.setDateFiled(rs.getDate("dateFiled"));
//// 	    	leave.setDateFrom(rs.getDate("dateFrom"));
//// 	    	leave.setDateTo(rs.getDate("dateTo"));
//// 	    	leave.setNoDays(rs.getInt("noDays"));
//// 	    	leave.setRemarks(rs.getString("remarks"));
//// 	    	leave.setStatus(rs.getInt("status"));
//// 	    	leave.setApprovedBy(rs.getInt("approvedBy"));
//// 	    	leave.setSecondaryApprover(rs.getInt("secondaryApprover"));
//// 	    	leave.setNeed2Approvers(rs.getString("need2Approvers"));
//// 	    	leave.setLeaveTypeId(rs.getInt("leaveTypeId"));
//// 	    	leave.setCreatedBy(rs.getInt("createdBy"));
//// 	    	leave.setCreatedDate(rs.getDate("createdDate"));
//// 	    	leaveList.add(leave);
//// 		}
//// 	    sql = null;
//// 	    ps.close();
//// 	    rs.close();	 
//// 	    return leaveList;
//
// 	}   
    
    
    
    
 
    /**
     * 	[leaveId] [int] IDENTITY(1,1) NOT NULL,
	[empId] [int] NULL,
	[dateFiled] [datetime] NULL,
	[dateFrom] [datetime] NULL,
	[dateTo] [datetime] NULL,
	[noDays] [int] NULL,
	[remarks] [varchar](50) NULL,
	[approvedBy] [int] NULL,
	[secondaryApprover] [int] NULL,
	[need2Approvers] [varchar](1) NULL,
	[leaveTypeId] [int] NULL,
	[createdBy] [int] NULL,
	[createdDate] [datetime] NULL,
	

     * @param employee
     * @return
     * @throws SQLException
     * @throws Exception
     */
  
    
    public int add(Leave leave) throws SQLException, Exception {
    		
    		String sql = "INSERT INTO leave ("
    				+ "empId, dateFiled, dateFrom, dateTo,	noDays,	remarks, approvedBy, secondaryApprover, "
    				+ "need2Approvers, leaveTypeId, createdBy, createdDate, status"
    				+ ") "+
    					"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    		 
    		PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); 
    		
    		ps.setInt(1, leave.getEmpId());
    		ps.setDate(2, leave.getDateFiled());
    		ps.setDate(3, leave.getDateFrom());
    		ps.setDate(4, leave.getDateTo());
    		ps.setInt(5, leave.getNoDays());
    		ps.setString(6, leave.getRemarks());
    		ps.setInt(7, leave.getApprovedBy());
    		ps.setInt(8, leave.getSecondaryApprover());
    		ps.setString(9, leave.getNeed2Approvers());
    		ps.setInt(10, leave.getLeaveTypeId());
    		ps.setInt(11, leave.getCreatedBy());
    		ps.setDate(12, leave.getCreatedDate());
    		ps.setInt(13, leave.getStatus());
 
    	 
    		ps.executeUpdate();
    		 ResultSet keyResultSet = ps.getGeneratedKeys(); 
    		 int generatedAutoIncrementId = 0; 
    		 	if (keyResultSet.next()) {
    		 		generatedAutoIncrementId = (int) keyResultSet.getInt(1);
    		 		leave.setLeaveId(generatedAutoIncrementId);
    		 		conn.commit(); 
    		 	}
    		 
    		 ps.close();
    		 keyResultSet.close();

    		 
    		 return generatedAutoIncrementId;

    	
    }
    
    
    public int update(Leave leave) throws SQLException, Exception {
		
		String sql = "UPDATE leave "
				+ "set empId=?, dateFiled=?, dateFrom=?, dateTo=?,	noDays=?, remarks=?, approvedBy=?, secondaryApprover=?, "
				+ "need2Approvers=?, leaveTypeId=?, createdBy=?, createdDate=?, status=? "
				+ "WHERE leaveId=? ";
		PreparedStatement ps = conn.prepareStatement(sql); 
		System.out.println("the empid is " + leave.getEmpId());
		System.out.println("the leaveId is " + leave.getLeaveId());
		ps.setInt(1, leave.getEmpId());
		ps.setDate(2, leave.getDateFiled());
		ps.setDate(3, leave.getDateFrom());
		ps.setDate(4, leave.getDateTo());
		ps.setInt(5, leave.getNoDays());
		ps.setString(6, leave.getRemarks());
		ps.setInt(7, leave.getApprovedBy());
		ps.setInt(8, leave.getSecondaryApprover());
		ps.setString(9, leave.getNeed2Approvers());
		ps.setInt(10, leave.getLeaveTypeId());
		ps.setInt(11, leave.getCreatedBy());
		ps.setDate(12, leave.getCreatedDate());
		ps.setInt(13, leave.getStatus());
		ps.setInt(14, leave.getLeaveId());
		int count = ps.executeUpdate();
		conn.commit();
		ps.close();		
		return count;

	
    }
    
    public int approveLeave(Leave leave) throws SQLException, Exception {
		
		String sql = "UPDATE leave "
				+ "set remarks=?, approvedBy=?, "
				+ "status=? "
				+ "WHERE leaveId=?";
		PreparedStatement ps = conn.prepareStatement(sql); 
		
		ps.setString(1, leave.getRemarks());
		ps.setInt(2, leave.getApprovedBy());		
		ps.setInt(3, leave.getStatus());
		ps.setInt(4, leave.getLeaveId());
		int count = ps.executeUpdate();
		conn.commit();
		ps.close();		
		return count;

	
    }
    
    public int approveLeaveHR(Leave leave) throws SQLException, Exception {
		
		String sql = "UPDATE leave "
				+ "set remarks=?, secondaryApprover=?, "
				+ "status=? "
				+ "WHERE leaveId=? ";
		PreparedStatement ps = conn.prepareStatement(sql); 
		
		ps.setString(1, leave.getRemarks());
		ps.setInt(2, leave.getApprovedBy());		
		ps.setInt(3, leave.getStatus());
		ps.setInt(4, leave.getLeaveId());
		int count = ps.executeUpdate();
		conn.commit();
		ps.close();		
		return count;

	
    }
    
    public Map<String, Object> getAllLeavesByEmpId(int empId, Date startDate, Date endDate, int status) throws SQLException, Exception {
    	StringBuffer sql = 	new StringBuffer();
    	Map<String, Object> map = new TreeMap<String, Object>();
    	sql.append("select distinct lt.leaveTypeName, ");
    	sql.append("  sum(l.noDays) over (partition by lt.leaveTypeName) as totalLeaveCount from leave l, leaveType lt where ");
    	sql.append("  l.leaveTypeId = lt.leaveTypeId ");
    	sql.append("  and l.status = ? ");
    	sql.append("  and l.empId = ? ");
    	sql.append("  and dateFrom >= ? and dateTo <= ?");
    	PreparedStatement ps = conn.prepareStatement(sql.toString()); 
    	ps.setInt(1, status);
    	ps.setInt(2, empId);
    	ps.setDate(3, startDate);
    	ps.setDate(4, endDate);
    	
    	ResultSet rs = ps.executeQuery();
    	String tmpFieldName = "";
    	while (rs.next()) {
    		 tmpFieldName = "";
    		if (status == 3) {//approved
    			 tmpFieldName = "Approved ";
    		} 
    		map.put(tmpFieldName + rs.getString("leaveTypeName"), rs.getInt("totalLeaveCount"));
    	}
    	
	    sql = null;
	    ps.close();
	    rs.close();	
	    return map;    	
    }
    
    /**
     * in days
     * @param empId
     * @param endDate
     * @param multiplyingFactor
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public Double getAllEarnedLeavesByEmpId(int empId, Date endDate, double multiplyingFactor) throws SQLException, Exception {
    	StringBuffer sql = 	new StringBuffer();
    	Double allEarnedLeaves = 0d;
    	sql.append("SELECT DATEDIFF(month, employmentDate, ?) * ? AS totalEarnedLeaves from employee where empId=?");
    	PreparedStatement ps = conn.prepareStatement(sql.toString()); 
    	ps.setDate(1, endDate);
    	ps.setDouble(2, multiplyingFactor);
    	ps.setInt(3, empId);
    	
    	ResultSet rs = ps.executeQuery();    	
    	if (rs.next()) {
    		allEarnedLeaves = rs.getDouble("totalEarnedLeaves");
    	}
    	
	    sql = null;
	    ps.close();
	    rs.close();	
	    return allEarnedLeaves;    	
    }
    
    
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}
	
	
    
    
    
    
    
    //NEW
    public  int getAllCount() throws SQLException, Exception {
    	int count = 0;    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, leave l WHERE e.empId = l.empId");   
    	  	
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
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, leave l WHERE e.empId = l.empId AND e.sectionId = ");   	
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
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, leave l WHERE e.empId = l.empId AND e.unitId = ");   	
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
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, leave l WHERE e.empId = l.empId AND e.subUnitId = ");   	
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
    
    
    //0 - FOR APPROVAL
    //1 - NOT APPROVED
    //2 - FOR UNIT SUPERVISOR APPROVAL
    //3 - FOR SECTION SUPERVISOR APPROVAL
    //4 - FOR HR APPROVAL
    //5 - FOR ADMIN APPROVAL
	//6 - APPROVED
    public List<Leave> getAllLeavesForHRApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "l.dateFrom DESC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT l.leaveId, l.empId, l.dateFiled, l.dateFrom, l.dateTo, l.noDays, l.remarks, l.status, l.approvedBy, l.secondaryApprover, l.need2Approvers, l.leaveTypeId, l.createdBy, l.createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, leave l WHERE e.empId = l.empId AND l.status in (0, 2, 3, 4) ");
    	sql.append(" AND e.empId <> ");
    	sql.append(empId);
    	sql.append(" ) SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<Leave> list = new ArrayList<Leave>();
	    
	    while (rs.next()) {
	    	Leave leave = new Leave();
	    	leave.setLeaveId(rs.getInt("leaveId"));
	    	leave.setEmpId(rs.getInt("empId"));
	    	leave.setDateFiled(rs.getDate("dateFiled"));
	    	leave.setDateFrom(rs.getDate("dateFrom"));
	    	leave.setDateTo(rs.getDate("dateTo"));
	    	leave.setNoDays(rs.getInt("noDays"));
	    	leave.setRemarks(rs.getString("remarks"));
	    	leave.setStatus(rs.getInt("status"));
	    	leave.setApprovedBy(rs.getInt("approvedBy"));
	    	leave.setSecondaryApprover(rs.getInt("secondaryApprover"));
	    	leave.setNeed2Approvers(rs.getString("need2Approvers"));
	    	leave.setLeaveTypeId(rs.getInt("leaveTypeId"));
	    	leave.setCreatedBy(rs.getInt("createdBy"));
	    	leave.setCreatedDate(rs.getDate("createdDate"));
	    	//leave.setLeaveType(rs.getString("leaveTypeName"));
	    	
	    	list.add(leave);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     	
 	}   
    
    public List<Leave> getAllLeavesForApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "l.dateFrom DESC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT l.leaveId, l.empId, l.dateFiled, l.dateFrom, l.dateTo, l.noDays, l.remarks, l.status, l.approvedBy, l.secondaryApprover, l.need2Approvers, l.leaveTypeId, l.createdBy, l.createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, leave l WHERE e.empId = l.empId AND l.status in (0, 2, 3, 4, 5) ");
    	sql.append(" AND e.empId <> ");
    	sql.append(empId);
    	sql.append(" ) SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<Leave> list = new ArrayList<Leave>();
	    
	    while (rs.next()) {
	    	Leave leave = new Leave();
	    	leave.setLeaveId(rs.getInt("leaveId"));
	    	leave.setEmpId(rs.getInt("empId"));
	    	leave.setDateFiled(rs.getDate("dateFiled"));
	    	leave.setDateFrom(rs.getDate("dateFrom"));
	    	leave.setDateTo(rs.getDate("dateTo"));
	    	leave.setNoDays(rs.getInt("noDays"));
	    	leave.setRemarks(rs.getString("remarks"));
	    	leave.setStatus(rs.getInt("status"));
	    	leave.setApprovedBy(rs.getInt("approvedBy"));
	    	leave.setSecondaryApprover(rs.getInt("secondaryApprover"));
	    	leave.setNeed2Approvers(rs.getString("need2Approvers"));
	    	leave.setLeaveTypeId(rs.getInt("leaveTypeId"));
	    	leave.setCreatedBy(rs.getInt("createdBy"));
	    	leave.setCreatedDate(rs.getDate("createdDate"));
	    	//leave.setLeaveType(rs.getString("leaveTypeName"));
	    	
	    	list.add(leave);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     	


 	}   
    
    public List<Leave> getAllLeavesForApprovalBySectionId(int empId, int sectionId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "l.dateFrom DESC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT l.leaveId, l.empId, l.dateFiled, l.dateFrom, l.dateTo, l.noDays, l.remarks, l.status, l.approvedBy, l.secondaryApprover, l.need2Approvers, l.leaveTypeId, l.createdBy, l.createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, leave l WHERE e.empId = l.empId AND l.status in (0, 2, 3) AND e.sectionId = ");
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
	    List<Leave> list = new ArrayList<Leave>();
	    
	    while (rs.next()) {
	    	Leave leave = new Leave();
	    	leave.setLeaveId(rs.getInt("leaveId"));
	    	leave.setEmpId(rs.getInt("empId"));
	    	leave.setDateFiled(rs.getDate("dateFiled"));
	    	leave.setDateFrom(rs.getDate("dateFrom"));
	    	leave.setDateTo(rs.getDate("dateTo"));
	    	leave.setNoDays(rs.getInt("noDays"));
	    	leave.setRemarks(rs.getString("remarks"));
	    	leave.setStatus(rs.getInt("status"));
	    	leave.setApprovedBy(rs.getInt("approvedBy"));
	    	leave.setSecondaryApprover(rs.getInt("secondaryApprover"));
	    	leave.setNeed2Approvers(rs.getString("need2Approvers"));
	    	leave.setLeaveTypeId(rs.getInt("leaveTypeId"));
	    	leave.setCreatedBy(rs.getInt("createdBy"));
	    	leave.setCreatedDate(rs.getDate("createdDate"));
	    	//leave.setLeaveType(rs.getString("leaveTypeName"));
	    	
	    	list.add(leave);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		
 	}   
    
    public List<Leave> getAllLeavesForApprovalByUnitId(int empId, int unitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "l.dateFrom DESC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT l.leaveId, l.empId, l.dateFiled, l.dateFrom, l.dateTo, l.noDays, l.remarks, l.status, l.approvedBy, l.secondaryApprover, l.need2Approvers, l.leaveTypeId, l.createdBy, l.createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, leave l WHERE e.empId = l.empId AND l.status in (0, 2) AND e.unitId = ");
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
	    List<Leave> list = new ArrayList<Leave>();
	    
	    while (rs.next()) {
	    	Leave leave = new Leave();
	    	leave.setLeaveId(rs.getInt("leaveId"));
	    	leave.setEmpId(rs.getInt("empId"));
	    	leave.setDateFiled(rs.getDate("dateFiled"));
	    	leave.setDateFrom(rs.getDate("dateFrom"));
	    	leave.setDateTo(rs.getDate("dateTo"));
	    	leave.setNoDays(rs.getInt("noDays"));
	    	leave.setRemarks(rs.getString("remarks"));
	    	leave.setStatus(rs.getInt("status"));
	    	leave.setApprovedBy(rs.getInt("approvedBy"));
	    	leave.setSecondaryApprover(rs.getInt("secondaryApprover"));
	    	leave.setNeed2Approvers(rs.getString("need2Approvers"));
	    	leave.setLeaveTypeId(rs.getInt("leaveTypeId"));
	    	leave.setCreatedBy(rs.getInt("createdBy"));
	    	leave.setCreatedDate(rs.getDate("createdDate"));
	    	//leave.setLeaveType(rs.getString("leaveTypeName"));
	    	
	    	list.add(leave);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		
 	}   
    
    public List<Leave> getAllLeavesForApprovalBySubUnitId(int empId, int subUnitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "l.dateFrom DESC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT l.leaveId, l.empId, l.dateFiled, l.dateFrom, l.dateTo, l.noDays, l.remarks, l.status, l.approvedBy, l.secondaryApprover, l.need2Approvers, l.leaveTypeId, l.createdBy, l.createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, leave l WHERE e.empId = l.empId AND l.status = 0 AND e.subUnitId = ");
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
	    List<Leave> list = new ArrayList<Leave>();
	    
	    while (rs.next()) {
	    	Leave leave = new Leave();
	    	leave.setLeaveId(rs.getInt("leaveId"));
	    	leave.setEmpId(rs.getInt("empId"));
	    	leave.setDateFiled(rs.getDate("dateFiled"));
	    	leave.setDateFrom(rs.getDate("dateFrom"));
	    	leave.setDateTo(rs.getDate("dateTo"));
	    	leave.setNoDays(rs.getInt("noDays"));
	    	leave.setRemarks(rs.getString("remarks"));
	    	leave.setStatus(rs.getInt("status"));
	    	leave.setApprovedBy(rs.getInt("approvedBy"));
	    	leave.setSecondaryApprover(rs.getInt("secondaryApprover"));
	    	leave.setNeed2Approvers(rs.getString("need2Approvers"));
	    	leave.setLeaveTypeId(rs.getInt("leaveTypeId"));
	    	leave.setCreatedBy(rs.getInt("createdBy"));
	    	leave.setCreatedDate(rs.getDate("createdDate"));
	    	//leave.setLeaveType(rs.getString("leaveTypeName"));
	    	
	    	list.add(leave);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		
 	}   
	

}
