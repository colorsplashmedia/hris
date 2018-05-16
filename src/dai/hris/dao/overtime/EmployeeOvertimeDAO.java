package dai.hris.dao.overtime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dai.hris.dao.DBConstants;
import dai.hris.model.EmployeeOvertime;

public class EmployeeOvertimeDAO {
	Connection conn = null;
	
	public EmployeeOvertimeDAO() {
		
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("EmployeeOvertimeDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("EmployeeOvertimeDAO :" + e.getMessage());
		}
	}
	
//	public  int getCountForSupervisor(int supervisorId) throws SQLException, Exception {
//    	int count = 0;
//    	
//    	StringBuffer sql = new StringBuffer();
//    	
//    	
//    	sql.append("SELECT count(*) as ctr FROM employee e, empOvertime l WHERE e.empId = l.empId AND e.superVisor1Id = ");   	
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
    
	public List<EmployeeOvertime> getOvertimeByDateRange(String dateFrom, String dateTo) throws SQLException, Exception {
    	List<EmployeeOvertime> resultList = new ArrayList<EmployeeOvertime>();
    	
    	String year = "";
    	String month = "";
    	String day = "";
    	
        Scanner scan = new Scanner(dateFrom);
        
        scan.useDelimiter("-");
        
        month = scan.next();
        day = scan.next();
        year = scan.next();
        
        dateFrom = year + "-" + month + "-" + day;
        // closing the scanner stream
        scan.close();
        
        
        Scanner scan2 = new Scanner(dateTo);
        
        scan2.useDelimiter("-");
        
        month = scan2.next();
        day = scan2.next();
        year = scan2.next();
        
        dateTo = year + "-" + month + "-" + day;
        // closing the scanner stream
        scan2.close();
    	
    	
    	StringBuffer sql = new StringBuffer();       	    	
    	
    	sql.append("SELECT e.empNo, (e.firstname + ' ' + e.lastname) as name, fr.empId, e.empNo, fr.empOvertimeId, fr.dateRendered, fr.noOfHours, fr.remarks, fr.status, fr.approvedBy ");    	
    	sql.append(" FROM empOvertime fr, employee e WHERE fr.empid = e.empid AND CONVERT(VARCHAR(10),fr.dateRendered,110) BETWEEN '");
    	sql.append(dateFrom);
    	sql.append("' AND '");
    	sql.append(dateTo);    	    	
    	sql.append("' ORDER BY name");
    	
		System.out.println("SQL getOvertimeByDateRange: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    
    	while(rs.next()) {
    		EmployeeOvertime model = new EmployeeOvertime();
    		
    		model.setEmpId(rs.getInt("empId"));
    		model.setEmpNo(rs.getString("empNo"));
    		model.setEmpName(rs.getString("name"));
    		model.setDateRendered(rs.getString("dateRendered"));
    		model.setNoOfHours(rs.getInt("noOfHours"));
    		model.setRemarks(rs.getString("remarks"));
    		model.setStatus(rs.getInt("status"));
    		
    		resultList.add(model);
    	}
    	
    	if (resultList.isEmpty()) {    	
    		EmployeeOvertime model = new EmployeeOvertime();
    		
    		model.setEmpId(0);
    		model.setEmpNo("");
    		model.setEmpName("");
    		model.setDateRendered("");
    		model.setNoOfHours(0);
    		model.setRemarks("");
    		model.setStatus(4);
    		
    		resultList.add(model);
    	}
    	
    	
    	rs.close();
    	ps.close();
    	return resultList;
    }
	
    public  int getCountByEmpId(int empId) throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM empOvertime WHERE empId = ");   	
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
	
/**
 * TG
 * @param empOTId
 * @return
 * @throws SQLException
 * @throws Exception
 */
	public EmployeeOvertime getEmployeeOvertimeByEmpOvertimeId(int empOTId) throws SQLException, Exception {			    		
		String sql = "SELECT * FROM empOvertime where empOvertimeId = ?";
		EmployeeOvertime employeeOvertime = null;
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, empOTId);
		ResultSet rs = ps.executeQuery();	    
	    while (rs.next()) {
	    	employeeOvertime = new EmployeeOvertime();
	    	employeeOvertime.setEmpOvertimeId(rs.getInt("empOvertimeId"));
	    	employeeOvertime.setEmpId(rs.getInt("empId"));
	    	employeeOvertime.setDateRendered(rs.getString("dateRendered"));
	    	employeeOvertime.setNoOfHours(rs.getInt("noOfHours"));
	    	employeeOvertime.setStatus(rs.getInt("status"));
	    	employeeOvertime.setRemarks(rs.getString("remarks"));
	    	employeeOvertime.setApprovedBy(rs.getInt("approvedBy"));
	    	employeeOvertime.setSecondaryApprover(rs.getInt("secondaryApprover"));
	    	employeeOvertime.setCreatedBy(rs.getInt("createdBy"));
	    	employeeOvertime.setCreatedDate(rs.getDate("createdDate"));
	    }
	    
	    ps.close();
	    rs.close();      
	    return employeeOvertime;
	}
	

/**
 * TG
 * @param empId
 * @return
 * @throws SQLException
 * @throws Exception
 */
	public List<EmployeeOvertime> getEmployeeOvertimeByEmpId(int empId , int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {			    		
		StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "dateRendered ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT empOvertimeId, empId, dateRendered, noOfHours, status, remarks, approvedBy, secondaryApprover, createdBy, createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM empOvertime WHERE empId = ");
    	sql.append(empId);
    	sql.append(" ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeOvertime> list = new ArrayList<EmployeeOvertime>();
	    
	    while (rs.next()) {	    	
	    	EmployeeOvertime eo = new EmployeeOvertime();
	    	eo.setEmpOvertimeId(rs.getInt("empOvertimeId"));
	    	eo.setEmpId(rs.getInt("empId"));
	    	eo.setDateRendered(rs.getString("dateRendered"));
	    	eo.setNoOfHours(rs.getInt("noOfHours"));
	    	eo.setStatus(rs.getInt("status"));
	    	eo.setRemarks(rs.getString("remarks"));
	    	eo.setApprovedBy(rs.getInt("approvedBy"));
	    	eo.setSecondaryApprover(rs.getInt("secondaryApprover"));
	    	eo.setCreatedBy(rs.getInt("createdBy"));
	    	eo.setCreatedDate(rs.getDate("createdDate"));
	    	
	    	list.add(eo);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		
		
		
		
		
		
//		String sql = "SELECT * FROM empOvertime where empId = ?";
//		EmployeeOvertime employeeOvertime = null;
//		PreparedStatement ps = conn.prepareStatement(sql.toString());
//		ps.setInt(1, empId);
//	
//	    ResultSet rs = ps.executeQuery();
//	    ArrayList<EmployeeOvertime> list = new ArrayList<EmployeeOvertime>();
//	    
//	    while (rs.next()) {	    	
//	    	employeeOvertime = new EmployeeOvertime();
//	    	employeeOvertime.setEmpOvertimeId(rs.getInt("empOvertimeId"));
//	    	employeeOvertime.setEmpId(rs.getInt("empId"));
//	    	employeeOvertime.setDateRendered(rs.getString("dateRendered"));
//	    	employeeOvertime.setNoOfHours(rs.getInt("noOfHours"));
//	    	employeeOvertime.setStatus(rs.getInt("status"));
//	    	employeeOvertime.setRemarks(rs.getString("remarks"));
//	    	employeeOvertime.setApprovedBy(rs.getInt("approvedBy"));
//	    	employeeOvertime.setSecondaryApprover(rs.getInt("secondaryApprover"));
//	    	employeeOvertime.setCreatedBy(rs.getInt("createdBy"));
//	    	employeeOvertime.setCreatedDate(rs.getDate("createdDate"));
//	    	list.add(employeeOvertime);
//	    }
//	    
//	    ps.close();
//	    rs.close();
// 	    list.sort(Comparator.comparing(EmployeeOvertime::getCreatedDate));
//	    return list;		
	}
	    
/**
 * TG
 * @param employeeOvertime
 * @return
 * @throws SQLException
 * @throws Exception
 */
	public int add(EmployeeOvertime employeeOvertime) throws SQLException, Exception {
		String sql = "INSERT INTO empOvertime(empId, dateRendered, noOfHours, remarks, status, approvedBy, secondaryApprover, createdBy, createdDate) "
						+ "VALUES (?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	    ps.setInt(1, employeeOvertime.getEmpId());
	    ps.setString(2, employeeOvertime.getDateRendered());
	    ps.setInt(3, employeeOvertime.getNoOfHours());
	    ps.setString(4, employeeOvertime.getRemarks());
	    ps.setInt(5,employeeOvertime.getStatus());
	    ps.setInt(6, employeeOvertime.getApprovedBy());
	    ps.setInt(7, employeeOvertime.getSecondaryApprover());
	    ps.setInt(8, employeeOvertime.getCreatedBy());
	    ps.setDate(9, employeeOvertime.getCreatedDate());
	    
	    ps.executeUpdate();
	    ResultSet keyResultSet = ps.getGeneratedKeys();
	     int generatedAutoIncrementId = 0;
	     if (keyResultSet.next()) {
	      	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
	      	employeeOvertime.setEmpOvertimeId(generatedAutoIncrementId);
	      	conn.commit();
	      }
		
	     ps.close();
	     keyResultSet.close();

	     return generatedAutoIncrementId;
	}

	    
	/*do not add delete method*/
	
	
/**\
 * TG
 * @param employeeOvertime
 * @return
 * @throws SQLException
 * @throws Exception
 */
    public int update(EmployeeOvertime employeeOvertime) throws SQLException, Exception {		
		String sql = "UPDATE empOvertime SET dateRendered=?, noOfHours=?, remarks=?, status=?, approvedBy=?, secondaryApprover=? "
						+ "WHERE empOvertimeId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
	    ps.setString(1, employeeOvertime.getDateRendered());
	    ps.setInt(2, employeeOvertime.getNoOfHours());
	    ps.setString(3, employeeOvertime.getRemarks());
	    ps.setInt(4, employeeOvertime.getStatus());
	    ps.setInt(5, employeeOvertime.getApprovedBy());
	    ps.setInt(6, employeeOvertime.getSecondaryApprover());
	    ps.setInt(7, employeeOvertime.getEmpOvertimeId());

		int count = ps.executeUpdate();
		conn.commit();
		ps.close();
		
		return count;

 	}
    
    public void approveOvertime(EmployeeOvertime employeeOvertime) throws SQLException, Exception {
    	
    	String sql = "UPDATE empOvertime "
				+ "set approvedBy=?, "
				+ "status=? "
				+ "WHERE empOvertimeId=?";
		PreparedStatement ps = conn.prepareStatement(sql); 
		
		ps.setInt(1, employeeOvertime.getApprovedBy());		
		ps.setInt(2, employeeOvertime.getStatus());
		ps.setInt(3, employeeOvertime.getEmpOvertimeId());
		ps.executeUpdate();
		conn.commit();
		ps.close();
	
    }

//    public List<EmployeeOvertime> getAllOvertimeForSvApprovalBySuperVisorId(int superVisorId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
//    	
//    	StringBuffer sql = new StringBuffer();   
//    	
//    	if(jtSorting == null){
//    		jtSorting = "o.dateRendered ASC";
//    	}
//    	
//    	sql.append("WITH OrderedList AS (SELECT o.empOvertimeId, o.empId, o.dateRendered, o.noOfHours, o.status, o.remarks, o.approvedBy, o.secondaryApprover, o.createdBy, o.createdDate, ROW_NUMBER() OVER(ORDER BY ");	
//    	sql.append(jtSorting);    	
//    	sql.append(") AS RowNumber FROM employee e, empOvertime o WHERE e.empId = o.empId AND o.status = 0 AND e.superVisor1Id = ");
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
//	    List<EmployeeOvertime> list = new ArrayList<EmployeeOvertime>();
//	    
//	    while (rs.next()) {
//	    	EmployeeOvertime employeeOvertime = new EmployeeOvertime();
//	    	employeeOvertime.setEmpOvertimeId(rs.getInt("empOvertimeId"));
//	    	employeeOvertime.setEmpId(rs.getInt("empId"));
//	    	employeeOvertime.setDateRendered(rs.getString("dateRendered"));
//	    	employeeOvertime.setNoOfHours(rs.getInt("noOfHours"));
//	    	employeeOvertime.setStatus(rs.getInt("status"));
//	    	employeeOvertime.setRemarks(rs.getString("remarks"));
//	    	employeeOvertime.setApprovedBy(rs.getInt("approvedBy"));
//	    	employeeOvertime.setSecondaryApprover(rs.getInt("secondaryApprover"));
//	    	employeeOvertime.setCreatedBy(rs.getInt("createdBy"));
//	    	employeeOvertime.setCreatedDate(rs.getDate("createdDate"));
//	    	list.add(employeeOvertime);
//	    }
//	    
//	    ps.close();
//	    rs.close();      
//	    return list;    		
//    	
//    	
////    	EmployeeOvertime employeeOvertime = null;
//// 		ArrayList<EmployeeOvertime> list = new ArrayList<EmployeeOvertime>();
//// 		
//// 		String sql="SELECT o.* FROM employee e, empOvertime o " + 
//// 						"WHERE e.empId = o.empId " +
////						"AND (e.superVisor1Id = ? OR e.superVisor2Id = ? OR e.superVisor3Id= ?) " + 
////						"AND (o.approvedBy = 0  AND o.secondaryApprover = 0)";
//// 		
//// 		PreparedStatement ps = conn.prepareStatement(sql.toString());
//// 		ps.setInt(1, superVisorId);
//// 		ps.setInt(2, superVisorId);
//// 		ps.setInt(3, superVisorId);
//// 		System.out.println(superVisorId);
////
//// 	    ResultSet rs = ps.executeQuery();
//// 	    
//// 	    while (rs.next()) {
//// 	    	employeeOvertime = new EmployeeOvertime();
//// 	    	employeeOvertime.setEmpOvertimeId(rs.getInt("empOvertimeId"));
//// 	    	employeeOvertime.setEmpId(rs.getInt("empId"));
//// 	    	employeeOvertime.setNoOfHours(rs.getInt("noOfHours"));
//// 	    	employeeOvertime.setDateRendered(rs.getString("dateRendered")); 
//// 	    	employeeOvertime.setRemarks(rs.getString("remarks"));
//// 	    	employeeOvertime.setApprovedBy(rs.getInt("approvedBy"));
//// 	    	employeeOvertime.setSecondaryApprover(rs.getInt("secondaryApprover"));
//// 	    	employeeOvertime.setStatus(rs.getInt("status"));
//// 	    	employeeOvertime.setCreatedBy(rs.getInt("createdBy"));
//// 	    	employeeOvertime.setCreatedDate(rs.getDate("createdDate"));
//// 	    	list.add(employeeOvertime);
//// 		}
//// 	    sql = null;
//// 	    ps.close();
//// 	    rs.close();	 
//// 	    list.sort(Comparator.comparing(EmployeeOvertime::getCreatedDate));
//// 	    return list;
//
// 	} 
    
    
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}
    
    
    //NEW
    public  int getAllCount() throws SQLException, Exception {
    	int count = 0;    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, empOvertime l WHERE e.empId = l.empId");   
    	  	
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
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, empOvertime l WHERE e.empId = l.empId AND e.sectionId = ");   	
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
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, empOvertime l WHERE e.empId = l.empId AND e.unitId = ");   	
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
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, empOvertime l WHERE e.empId = l.empId AND e.subUnitId = ");   	
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
    
    public List<EmployeeOvertime> getAllOvertimeForApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "o.dateRendered ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT o.empOvertimeId, o.empId, o.dateRendered, o.noOfHours, o.status, o.remarks, o.approvedBy, o.secondaryApprover, o.createdBy, o.createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empOvertime o WHERE e.empId = o.empId AND o.status in (0, 2, 3, 4, 5) ");
    	sql.append(" AND e.empId <> ");
    	sql.append(empId);
    	sql.append(" ) SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeOvertime> list = new ArrayList<EmployeeOvertime>();
	    
	    while (rs.next()) {
	    	EmployeeOvertime employeeOvertime = new EmployeeOvertime();
	    	employeeOvertime.setEmpOvertimeId(rs.getInt("empOvertimeId"));
	    	employeeOvertime.setEmpId(rs.getInt("empId"));
	    	employeeOvertime.setDateRendered(rs.getString("dateRendered"));
	    	employeeOvertime.setNoOfHours(rs.getInt("noOfHours"));
	    	employeeOvertime.setStatus(rs.getInt("status"));
	    	employeeOvertime.setRemarks(rs.getString("remarks"));
	    	employeeOvertime.setApprovedBy(rs.getInt("approvedBy"));
	    	employeeOvertime.setSecondaryApprover(rs.getInt("secondaryApprover"));
	    	employeeOvertime.setCreatedBy(rs.getInt("createdBy"));
	    	employeeOvertime.setCreatedDate(rs.getDate("createdDate"));
	    	list.add(employeeOvertime);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   

 	} 
    
    public List<EmployeeOvertime> getAllOvertimeForHRApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "o.dateRendered ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT o.empOvertimeId, o.empId, o.dateRendered, o.noOfHours, o.status, o.remarks, o.approvedBy, o.secondaryApprover, o.createdBy, o.createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empOvertime o WHERE e.empId = o.empId AND o.status in (0, 2, 3, 4) ");
    	sql.append(" AND e.empId <> ");
    	sql.append(empId);
    	sql.append(" ) SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeOvertime> list = new ArrayList<EmployeeOvertime>();
	    
	    while (rs.next()) {
	    	EmployeeOvertime employeeOvertime = new EmployeeOvertime();
	    	employeeOvertime.setEmpOvertimeId(rs.getInt("empOvertimeId"));
	    	employeeOvertime.setEmpId(rs.getInt("empId"));
	    	employeeOvertime.setDateRendered(rs.getString("dateRendered"));
	    	employeeOvertime.setNoOfHours(rs.getInt("noOfHours"));
	    	employeeOvertime.setStatus(rs.getInt("status"));
	    	employeeOvertime.setRemarks(rs.getString("remarks"));
	    	employeeOvertime.setApprovedBy(rs.getInt("approvedBy"));
	    	employeeOvertime.setSecondaryApprover(rs.getInt("secondaryApprover"));
	    	employeeOvertime.setCreatedBy(rs.getInt("createdBy"));
	    	employeeOvertime.setCreatedDate(rs.getDate("createdDate"));
	    	list.add(employeeOvertime);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   

 	} 
    
    public List<EmployeeOvertime> getAllOvertimeForApprovalBySectionId(int empId, int sectionId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "o.dateRendered ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT o.empOvertimeId, o.empId, o.dateRendered, o.noOfHours, o.status, o.remarks, o.approvedBy, o.secondaryApprover, o.createdBy, o.createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empOvertime o WHERE e.empId = o.empId AND o.status in (0, 2, 3) AND e.sectionId = ");
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
	    List<EmployeeOvertime> list = new ArrayList<EmployeeOvertime>();
	    
	    while (rs.next()) {
	    	EmployeeOvertime employeeOvertime = new EmployeeOvertime();
	    	employeeOvertime.setEmpOvertimeId(rs.getInt("empOvertimeId"));
	    	employeeOvertime.setEmpId(rs.getInt("empId"));
	    	employeeOvertime.setDateRendered(rs.getString("dateRendered"));
	    	employeeOvertime.setNoOfHours(rs.getInt("noOfHours"));
	    	employeeOvertime.setStatus(rs.getInt("status"));
	    	employeeOvertime.setRemarks(rs.getString("remarks"));
	    	employeeOvertime.setApprovedBy(rs.getInt("approvedBy"));
	    	employeeOvertime.setSecondaryApprover(rs.getInt("secondaryApprover"));
	    	employeeOvertime.setCreatedBy(rs.getInt("createdBy"));
	    	employeeOvertime.setCreatedDate(rs.getDate("createdDate"));
	    	list.add(employeeOvertime);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   

 	} 
    
    public List<EmployeeOvertime> getAllOvertimeForApprovalByUnitId(int empId, int unitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "o.dateRendered ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT o.empOvertimeId, o.empId, o.dateRendered, o.noOfHours, o.status, o.remarks, o.approvedBy, o.secondaryApprover, o.createdBy, o.createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empOvertime o WHERE e.empId = o.empId AND o.status in (0, 2) AND e.unitId = ");
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
	    List<EmployeeOvertime> list = new ArrayList<EmployeeOvertime>();
	    
	    while (rs.next()) {
	    	EmployeeOvertime employeeOvertime = new EmployeeOvertime();
	    	employeeOvertime.setEmpOvertimeId(rs.getInt("empOvertimeId"));
	    	employeeOvertime.setEmpId(rs.getInt("empId"));
	    	employeeOvertime.setDateRendered(rs.getString("dateRendered"));
	    	employeeOvertime.setNoOfHours(rs.getInt("noOfHours"));
	    	employeeOvertime.setStatus(rs.getInt("status"));
	    	employeeOvertime.setRemarks(rs.getString("remarks"));
	    	employeeOvertime.setApprovedBy(rs.getInt("approvedBy"));
	    	employeeOvertime.setSecondaryApprover(rs.getInt("secondaryApprover"));
	    	employeeOvertime.setCreatedBy(rs.getInt("createdBy"));
	    	employeeOvertime.setCreatedDate(rs.getDate("createdDate"));
	    	list.add(employeeOvertime);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   

 	} 


    public List<EmployeeOvertime> getAllOvertimeForApprovalBySubUnitId(int empId, int subUnitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "o.dateRendered ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT o.empOvertimeId, o.empId, o.dateRendered, o.noOfHours, o.status, o.remarks, o.approvedBy, o.secondaryApprover, o.createdBy, o.createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empOvertime o WHERE e.empId = o.empId AND o.status = 0 AND e.subUnitId = ");
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
	    List<EmployeeOvertime> list = new ArrayList<EmployeeOvertime>();
	    
	    while (rs.next()) {
	    	EmployeeOvertime employeeOvertime = new EmployeeOvertime();
	    	employeeOvertime.setEmpOvertimeId(rs.getInt("empOvertimeId"));
	    	employeeOvertime.setEmpId(rs.getInt("empId"));
	    	employeeOvertime.setDateRendered(rs.getString("dateRendered"));
	    	employeeOvertime.setNoOfHours(rs.getInt("noOfHours"));
	    	employeeOvertime.setStatus(rs.getInt("status"));
	    	employeeOvertime.setRemarks(rs.getString("remarks"));
	    	employeeOvertime.setApprovedBy(rs.getInt("approvedBy"));
	    	employeeOvertime.setSecondaryApprover(rs.getInt("secondaryApprover"));
	    	employeeOvertime.setCreatedBy(rs.getInt("createdBy"));
	    	employeeOvertime.setCreatedDate(rs.getDate("createdDate"));
	    	list.add(employeeOvertime);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   

 	}    
    
    
}
