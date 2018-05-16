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
import dai.hris.model.EmployeeUndertime;

public class EmployeeUndertimeDAO {
	Connection conn = null;
	
	public EmployeeUndertimeDAO() {
		
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("EmployeeUndertimeDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("EmployeeUndertimeDAO :" + e.getMessage());
		}
	}
	
//	public  int getCountForSupervisor(int supervisorId) throws SQLException, Exception {
//    	int count = 0;
//    	
//    	StringBuffer sql = new StringBuffer();
//    	
//    	
//    	sql.append("SELECT count(*) as ctr FROM employee e, empUndertime l WHERE e.empId = l.empId AND e.superVisor1Id = ");   	
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
    
	public List<EmployeeUndertime> getUndertimeByDateRange(String dateFrom, String dateTo) throws SQLException, Exception {
    	List<EmployeeUndertime> resultList = new ArrayList<EmployeeUndertime>();
    	
    	String year = "";
    	String month = "";
    	String day = "";
    	
        Scanner scan = new Scanner(dateFrom);
        
        scan.useDelimiter("/");
        
        month = scan.next();
        day = scan.next();
        year = scan.next();
        
        dateFrom = year + "-" + month + "-" + day;
        // closing the scanner stream
        scan.close();
        
        
        Scanner scan2 = new Scanner(dateTo);
        
        scan2.useDelimiter("/");
        
        month = scan2.next();
        day = scan2.next();
        year = scan2.next();
        
        dateTo = year + "-" + month + "-" + day;
        // closing the scanner stream
        scan2.close();
    	
    	
    	StringBuffer sql = new StringBuffer();       	    	
    	
    	sql.append("SELECT e.empNo, (e.firstname + ' ' + e.lastname) as name, fr.empId, e.empNo, fr.empUndertimeId, fr.dateRendered, fr.noOfHours, fr.remarks, fr.status, fr.approvedBy ");    	
    	sql.append(" FROM empUndertime fr, employee e WHERE fr.empid = e.empid AND CONVERT(VARCHAR(10),fr.dateRendered,110) BETWEEN '");
    	sql.append(dateFrom);
    	sql.append("' AND '");
    	sql.append(dateTo);    	    	
    	sql.append("' ORDER BY name");
    	
		System.out.println("SQL getUndertimeByDateRange: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    
    	while(rs.next()) {
    		EmployeeUndertime model = new EmployeeUndertime();
    		
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
    		EmployeeUndertime model = new EmployeeUndertime();
    		
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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM empUndertime WHERE empId = ");   	
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
	

	public EmployeeUndertime getEmployeeUndertimeByEmpUndertimeId(int empOTId) throws SQLException, Exception {			    		
		String sql = "SELECT * FROM empUndertime where empUndertimeId = ?";
		EmployeeUndertime model = null;
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, empOTId);
		ResultSet rs = ps.executeQuery();	    
	    while (rs.next()) {
	    	model = new EmployeeUndertime();
	    	model.setEmpUndertimeId(rs.getInt("empUndertimeId"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setDateRendered(rs.getString("dateRendered"));
	    	model.setNoOfHours(rs.getInt("noOfHours"));
	    	model.setStatus(rs.getInt("status"));
	    	model.setRemarks(rs.getString("remarks"));
	    	model.setApprovedBy(rs.getInt("approvedBy"));	    	
	    	model.setCreatedBy(rs.getInt("createdBy"));
	    	model.setCreatedDate(rs.getDate("createdDate"));
	    }
	    
	    ps.close();
	    rs.close();      
	    return model;
	}
	


	public List<EmployeeUndertime> getEmployeeUndertimeByEmpId(int empId , int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {			    		
		StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "dateRendered ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT empUndertimeId, empId, dateRendered, noOfHours, status, remarks, approvedBy, createdBy, createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM empUndertime WHERE empId = ");
    	sql.append(empId);
    	sql.append(" ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeUndertime> list = new ArrayList<EmployeeUndertime>();
	    
	    while (rs.next()) {	    	
	    	EmployeeUndertime model = new EmployeeUndertime();
	    	model.setEmpUndertimeId(rs.getInt("empUndertimeId"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setDateRendered(rs.getString("dateRendered"));
	    	model.setNoOfHours(rs.getInt("noOfHours"));
	    	model.setStatus(rs.getInt("status"));
	    	model.setRemarks(rs.getString("remarks"));
	    	model.setApprovedBy(rs.getInt("approvedBy"));	    	
	    	model.setCreatedBy(rs.getInt("createdBy"));
	    	model.setCreatedDate(rs.getDate("createdDate"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    				
		
	}
	    

	public int add(EmployeeUndertime model) throws SQLException, Exception {
		String sql = "INSERT INTO empUndertime(empId, dateRendered, noOfHours, remarks, status, approvedBy, createdBy, createdDate) "
						+ "VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	    ps.setInt(1, model.getEmpId());
	    ps.setString(2, model.getDateRendered());
	    ps.setInt(3, model.getNoOfHours());
	    ps.setString(4, model.getRemarks());
	    ps.setInt(5,model.getStatus());
	    ps.setInt(6, model.getApprovedBy());
	    ps.setInt(7, model.getCreatedBy());
	    ps.setDate(8, model.getCreatedDate());
	    
	    ps.executeUpdate();
	    ResultSet keyResultSet = ps.getGeneratedKeys();
	     int generatedAutoIncrementId = 0;
	     if (keyResultSet.next()) {
	      	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
	      	model.setEmpUndertimeId(generatedAutoIncrementId);
	      	conn.commit();
	      }
		
	     ps.close();
	     keyResultSet.close();

	     return generatedAutoIncrementId;
	}

	    
	/*do not add delete method*/
	
	

    public int update(EmployeeUndertime model) throws SQLException, Exception {		
		String sql = "UPDATE empUndertime SET dateRendered=?, noOfHours=?, remarks=?, status=?, approvedBy=? "
						+ "WHERE empUndertimeId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
	    ps.setString(1, model.getDateRendered());
	    ps.setInt(2, model.getNoOfHours());
	    ps.setString(3, model.getRemarks());
	    ps.setInt(4, model.getStatus());
	    ps.setInt(5, model.getApprovedBy());
	    ps.setInt(6, model.getEmpUndertimeId());

		int count = ps.executeUpdate();
		conn.commit();
		ps.close();
		
		return count;

 	}
    
    public void approveUndertime(EmployeeUndertime model) throws SQLException, Exception {
    	
    	String sql = "UPDATE empUndertime "
				+ "set approvedBy=?, "
				+ "status=? "
				+ "WHERE empUndertimeId=?";
		PreparedStatement ps = conn.prepareStatement(sql); 
		
		ps.setInt(1, model.getApprovedBy());		
		ps.setInt(2, model.getStatus());
		ps.setInt(3, model.getEmpUndertimeId());
		ps.executeUpdate();
		conn.commit();
		ps.close();
	
    }

//    public List<EmployeeUndertime> getAllUndertimeForSvApprovalBySuperVisorId(int superVisorId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
//    	
//    	StringBuffer sql = new StringBuffer();   
//    	
//    	if(jtSorting == null){
//    		jtSorting = "o.dateRendered ASC";
//    	}
//    	
//    	sql.append("WITH OrderedList AS (SELECT o.empUndertimeId, o.empId, o.dateRendered, o.noOfHours, o.status, o.remarks, o.approvedBy, o.createdBy, o.createdDate, ROW_NUMBER() OVER(ORDER BY ");	
//    	sql.append(jtSorting);    	
//    	sql.append(") AS RowNumber FROM employee e, empUndertime o WHERE e.empId = o.empId AND o.status = 0 AND e.superVisor1Id = ");
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
//	    List<EmployeeUndertime> list = new ArrayList<EmployeeUndertime>();
//	    
//	    while (rs.next()) {
//	    	EmployeeUndertime model = new EmployeeUndertime();
//	    	model.setEmpUndertimeId(rs.getInt("empUndertimeId"));
//	    	model.setEmpId(rs.getInt("empId"));
//	    	model.setDateRendered(rs.getString("dateRendered"));
//	    	model.setNoOfHours(rs.getInt("noOfHours"));
//	    	model.setStatus(rs.getInt("status"));
//	    	model.setRemarks(rs.getString("remarks"));
//	    	model.setApprovedBy(rs.getInt("approvedBy"));	    	
//	    	model.setCreatedBy(rs.getInt("createdBy"));
//	    	model.setCreatedDate(rs.getDate("createdDate"));
//	    	list.add(model);
//	    }
//	    
//	    ps.close();
//	    rs.close();      
//	    return list;    	
//
//
// 	} 
    
    
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}
    
    
    //NEW
    public  int getAllCount() throws SQLException, Exception {
    	int count = 0;    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, empUndertime l WHERE e.empId = l.empId");   
    	  	
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
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, empUndertime l WHERE e.empId = l.empId AND e.sectionId = ");   	
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
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, empUndertime l WHERE e.empId = l.empId AND e.unitId = ");   	
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
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, empUndertime l WHERE e.empId = l.empId AND e.subUnitId = ");   	
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
    
    public List<EmployeeUndertime> getAllUndertimeForApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "o.dateRendered ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT o.empUndertimeId, o.empId, o.dateRendered, o.noOfHours, o.status, o.remarks, o.approvedBy, o.createdBy, o.createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empUndertime o WHERE e.empId = o.empId AND o.status in (0, 2, 3, 4, 5) ");
    	sql.append(" AND e.empId <> ");
    	sql.append(empId);
    	sql.append(" ) SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeUndertime> list = new ArrayList<EmployeeUndertime>();
	    
	    while (rs.next()) {
	    	EmployeeUndertime model = new EmployeeUndertime();
	    	model.setEmpUndertimeId(rs.getInt("empUndertimeId"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setDateRendered(rs.getString("dateRendered"));
	    	model.setNoOfHours(rs.getInt("noOfHours"));
	    	model.setStatus(rs.getInt("status"));
	    	model.setRemarks(rs.getString("remarks"));
	    	model.setApprovedBy(rs.getInt("approvedBy"));	    	
	    	model.setCreatedBy(rs.getInt("createdBy"));
	    	model.setCreatedDate(rs.getDate("createdDate"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   

 	} 
    
    public List<EmployeeUndertime> getAllUndertimeForHRApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "o.dateRendered ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT o.empUndertimeId, o.empId, o.dateRendered, o.noOfHours, o.status, o.remarks, o.approvedBy, o.createdBy, o.createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empUndertime o WHERE e.empId = o.empId AND o.status in (0, 2, 3, 4) ");
    	sql.append(" AND e.empId <> ");
    	sql.append(empId);
    	sql.append(" ) SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeUndertime> list = new ArrayList<EmployeeUndertime>();
	    
	    while (rs.next()) {
	    	EmployeeUndertime model = new EmployeeUndertime();
	    	model.setEmpUndertimeId(rs.getInt("empUndertimeId"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setDateRendered(rs.getString("dateRendered"));
	    	model.setNoOfHours(rs.getInt("noOfHours"));
	    	model.setStatus(rs.getInt("status"));
	    	model.setRemarks(rs.getString("remarks"));
	    	model.setApprovedBy(rs.getInt("approvedBy"));	    	
	    	model.setCreatedBy(rs.getInt("createdBy"));
	    	model.setCreatedDate(rs.getDate("createdDate"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   

 	} 
    
    public List<EmployeeUndertime> getAllUndertimeForApprovalBySectionId(int empId, int sectionId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "o.dateRendered ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT o.empUndertimeId, o.empId, o.dateRendered, o.noOfHours, o.status, o.remarks, o.approvedBy, o.createdBy, o.createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empUndertime o WHERE e.empId = o.empId AND o.status in (0, 2, 3) AND e.sectionId = ");
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
	    List<EmployeeUndertime> list = new ArrayList<EmployeeUndertime>();
	    
	    while (rs.next()) {
	    	EmployeeUndertime model = new EmployeeUndertime();
	    	model.setEmpUndertimeId(rs.getInt("empUndertimeId"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setDateRendered(rs.getString("dateRendered"));
	    	model.setNoOfHours(rs.getInt("noOfHours"));
	    	model.setStatus(rs.getInt("status"));
	    	model.setRemarks(rs.getString("remarks"));
	    	model.setApprovedBy(rs.getInt("approvedBy"));	    	
	    	model.setCreatedBy(rs.getInt("createdBy"));
	    	model.setCreatedDate(rs.getDate("createdDate"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   

 	} 
    
    public List<EmployeeUndertime> getAllUndertimeForApprovalByUnitId(int empId, int unitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "o.dateRendered ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT o.empUndertimeId, o.empId, o.dateRendered, o.noOfHours, o.status, o.remarks, o.approvedBy, o.createdBy, o.createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empUndertime o WHERE e.empId = o.empId AND o.status in (0, 2) AND e.unitId = ");
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
	    List<EmployeeUndertime> list = new ArrayList<EmployeeUndertime>();
	    
	    while (rs.next()) {
	    	EmployeeUndertime model = new EmployeeUndertime();
	    	model.setEmpUndertimeId(rs.getInt("empUndertimeId"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setDateRendered(rs.getString("dateRendered"));
	    	model.setNoOfHours(rs.getInt("noOfHours"));
	    	model.setStatus(rs.getInt("status"));
	    	model.setRemarks(rs.getString("remarks"));
	    	model.setApprovedBy(rs.getInt("approvedBy"));	    	
	    	model.setCreatedBy(rs.getInt("createdBy"));
	    	model.setCreatedDate(rs.getDate("createdDate"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   

 	} 


    public List<EmployeeUndertime> getAllUndertimeForApprovalBySubUnitId(int empId, int subUnitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "o.dateRendered ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT o.empUndertimeId, o.empId, o.dateRendered, o.noOfHours, o.status, o.remarks, o.approvedBy, o.createdBy, o.createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empUndertime o WHERE e.empId = o.empId AND o.status = 0 AND e.subUnitId = ");
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
	    List<EmployeeUndertime> list = new ArrayList<EmployeeUndertime>();
	    
	    while (rs.next()) {
	    	EmployeeUndertime model = new EmployeeUndertime();
	    	model.setEmpUndertimeId(rs.getInt("empUndertimeId"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setDateRendered(rs.getString("dateRendered"));
	    	model.setNoOfHours(rs.getInt("noOfHours"));
	    	model.setStatus(rs.getInt("status"));
	    	model.setRemarks(rs.getString("remarks"));
	    	model.setApprovedBy(rs.getInt("approvedBy"));	    	
	    	model.setCreatedBy(rs.getInt("createdBy"));
	    	model.setCreatedDate(rs.getDate("createdDate"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   

 	} 

	
}
