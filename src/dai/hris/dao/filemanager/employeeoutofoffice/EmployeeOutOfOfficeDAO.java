package dai.hris.dao.filemanager.employeeoutofoffice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dai.hris.dao.DBConstants;
import dai.hris.model.EmployeeOutOfOffice;

public class EmployeeOutOfOfficeDAO {
	Connection conn = null;
	
	public EmployeeOutOfOfficeDAO() {
		
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("EmployeeOutOfOfficeDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("EmployeeOutOfOfficeDAO :" + e.getMessage());
		}
	}
	

	/**
	 * tested ok 062315 TG
	 * @param empOOOId
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public EmployeeOutOfOffice getEmployeeOutOfOfficeByEmpOOOId(int empOOOId) throws SQLException, Exception {			    		
		String sql = "SELECT * FROM empOutOfOffice where empOOOId = ?";
		EmployeeOutOfOffice employeeOutOfOffice = null;
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, empOOOId);
		
	    ResultSet rs = ps.executeQuery();
	    
	    while (rs.next()) {
	    	employeeOutOfOffice = new EmployeeOutOfOffice();
	    	employeeOutOfOffice.setEmpOOOId(rs.getInt("empOOOId"));
	    	employeeOutOfOffice.setEmpId(rs.getInt("empId"));
	    	employeeOutOfOffice.setDateFrom(rs.getString("dateFrom"));
	    	employeeOutOfOffice.setDateTo(rs.getString("dateTo"));	    	
	    	employeeOutOfOffice.setProvider(rs.getString("provider"));
	    	employeeOutOfOffice.setRemarks(rs.getString("remarks"));
	    	employeeOutOfOffice.setNoOfHours(rs.getInt("noOfHours"));
	    	employeeOutOfOffice.setTitleActivity(rs.getString("titleActivity"));	    	
	    	employeeOutOfOffice.setStatus(rs.getInt("status"));
	    	employeeOutOfOffice.setApprovedBy(rs.getInt("approvedBy"));
	    	employeeOutOfOffice.setSecondaryApprover(rs.getInt("secondaryApprover"));
	    	employeeOutOfOffice.setCreatedBy(rs.getInt("createdBy"));
	    	employeeOutOfOffice.setCreatedDate(rs.getDate("createdDate"));
	    }
	    
	    ps.close();
	    rs.close();      
	    return employeeOutOfOffice;		
	}
	
//	public  int getAllEmployeeOOOForSvApprovalBySuperVisorId(int supervisorId) throws SQLException, Exception {
//    	int count = 0;
//    	
//    	StringBuffer sql = new StringBuffer();
//    	
//    	
//    	sql.append("SELECT count(*) as ctr FROM employee e, empOutOfOffice l WHERE e.empId = l.empId AND e.superVisor1Id = ");   	
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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM empOutOfOffice WHERE empId = ");   	
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
	 * tested ok 062315 TG
	 * @param empId
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<EmployeeOutOfOffice> getEmployeeOutOfOfficeListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {			    		
		
		StringBuffer sql = new StringBuffer();   
    	
    	//if(jtSorting == null){
    		jtSorting = "dateFrom DESC";
    	//}
    	
    	sql.append("WITH OrderedList AS (SELECT empOOOId, empId, dateFrom, dateTo, provider, remarks, noOfHours, titleActivity, status, approvedBy, secondaryApprover, createdBy, createdDate, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM empOutOfOffice WHERE empId = ");
    	sql.append(empId);
    	sql.append(" ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeOutOfOffice> list = new ArrayList<EmployeeOutOfOffice>();
	    
	    EmployeeOutOfOffice employeeOutOfOffice = null;
	    
	    while (rs.next()) {
	    	employeeOutOfOffice = new EmployeeOutOfOffice();
	    	employeeOutOfOffice.setEmpOOOId(rs.getInt("empOOOId"));
	    	employeeOutOfOffice.setEmpId(rs.getInt("empId"));
	    	employeeOutOfOffice.setDateFrom(rs.getString("dateFrom"));
	    	employeeOutOfOffice.setDateTo(rs.getString("dateTo"));	    	
	    	employeeOutOfOffice.setProvider(rs.getString("provider"));
	    	employeeOutOfOffice.setRemarks(rs.getString("remarks"));
	    	employeeOutOfOffice.setNoOfHours(rs.getInt("noOfHours"));
	    	employeeOutOfOffice.setTitleActivity(rs.getString("titleActivity"));	    	
	    	employeeOutOfOffice.setStatus(rs.getInt("status"));
	    	employeeOutOfOffice.setApprovedBy(rs.getInt("approvedBy"));
	    	employeeOutOfOffice.setSecondaryApprover(rs.getInt("secondaryApprover"));
	    	employeeOutOfOffice.setCreatedBy(rs.getInt("createdBy"));
	    	employeeOutOfOffice.setCreatedDate(rs.getDate("createdDate"));
	    	list.add(employeeOutOfOffice);
	    	
	    	
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		
		
		
//		String sql = "SELECT * FROM empOutOfOffice where empId = ?";
//		EmployeeOutOfOffice employeeOutOfOffice = null;
//		PreparedStatement ps = conn.prepareStatement(sql.toString());
//		ps.setInt(1, empId);
//	
//	    ResultSet rs = ps.executeQuery();
//	    ArrayList<EmployeeOutOfOffice> list = new ArrayList<EmployeeOutOfOffice>();
//	    
//	    while (rs.next()) {	    	
//	    	employeeOutOfOffice = new EmployeeOutOfOffice();
//	    	employeeOutOfOffice.setEmpOOOId(rs.getInt("empOOOId"));
//	    	employeeOutOfOffice.setEmpId(rs.getInt("empId"));
//	    	employeeOutOfOffice.setDateFrom(rs.getString("dateFrom"));
//	    	employeeOutOfOffice.setDateTo(rs.getString("dateTo"));	    	
//	    	employeeOutOfOffice.setProvider(rs.getString("provider"));
//	    	employeeOutOfOffice.setRemarks(rs.getString("remarks"));
//	    	employeeOutOfOffice.setNoOfHours(rs.getInt("noOfHours"));
//	    	employeeOutOfOffice.setTitleActivity(rs.getString("titleActivity"));	    	
//	    	employeeOutOfOffice.setStatus(rs.getInt("status"));
//	    	employeeOutOfOffice.setApprovedBy(rs.getInt("approvedBy"));
//	    	employeeOutOfOffice.setSecondaryApprover(rs.getInt("secondaryApprover"));
//	    	employeeOutOfOffice.setCreatedBy(rs.getInt("createdBy"));
//	    	employeeOutOfOffice.setCreatedDate(rs.getDate("createdDate"));
//	    	list.add(employeeOutOfOffice);
//	    }
//	    
//	    ps.close();
//	    rs.close();      
//	    return list;		
	}
	    
	/**
	 * tested ok 062315 TG
	 * @param employeeOutOfOffice
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public int add(EmployeeOutOfOffice employeeOutOfOffice) throws SQLException, Exception {
		String sql = "INSERT INTO empOutOfOffice (empId, dateFrom, dateTo, titleActivity, provider, remarks, status, approvedBy, secondaryApprover, "
						+ "createdBy, createdDate, noOfHours) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	    ps.setInt(1, employeeOutOfOffice.getEmpId());
	    ps.setString(2, employeeOutOfOffice.getDateFrom());
	    ps.setString(3, employeeOutOfOffice.getDateTo());
	    ps.setString(4, employeeOutOfOffice.getTitleActivity());
	    ps.setString(5, employeeOutOfOffice.getProvider());
	    ps.setString(6, employeeOutOfOffice.getRemarks());
	    ps.setInt(7, employeeOutOfOffice.getStatus());
	    ps.setInt(8, employeeOutOfOffice.getApprovedBy());
	    ps.setInt(9, employeeOutOfOffice.getSecondaryApprover());
	    ps.setInt(10, employeeOutOfOffice.getCreatedBy());
	    ps.setDate(11, employeeOutOfOffice.getCreatedDate());
	    ps.setInt(12, employeeOutOfOffice.getNoOfHours());
	    
	    ps.executeUpdate();
	    ResultSet keyResultSet = ps.getGeneratedKeys();
	     int generatedAutoIncrementId = 0;
	     if (keyResultSet.next()) {
	      	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
	      	employeeOutOfOffice.setEmpOOOId(generatedAutoIncrementId);
	      	conn.commit();
	      }
		
	     ps.close();
	     keyResultSet.close();

	     return generatedAutoIncrementId;
	}

	    
	/*do not add delete method*/
	
	
	/**
	 * tested ok 062315 TG
	 * @param employeeOutOfOffice
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
    public int update(EmployeeOutOfOffice employeeOutOfOffice) throws SQLException, Exception {		
		String sql = "UPDATE empOutOfOffice SET empId=?, dateFrom=?, dateTo=?, titleActivity=?, provider=?, remarks=?, approvedBy=?, secondaryApprover=?, "
						+ "status=?, noOfHours=? WHERE empOOOId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
	    ps.setInt(1, employeeOutOfOffice.getEmpId());
	    ps.setString(2, employeeOutOfOffice.getDateFrom());
	    ps.setString(3, employeeOutOfOffice.getDateTo());
	    ps.setString(4, employeeOutOfOffice.getTitleActivity());
	    ps.setString(5, employeeOutOfOffice.getProvider());
	    ps.setString(6, employeeOutOfOffice.getRemarks());
	    ps.setInt(7, employeeOutOfOffice.getApprovedBy());
	    ps.setInt(8, employeeOutOfOffice.getSecondaryApprover());
	    ps.setInt(9, employeeOutOfOffice.getStatus());
	    ps.setInt(10, employeeOutOfOffice.getNoOfHours());
	    ps.setInt(11, employeeOutOfOffice.getEmpOOOId());
		int count = ps.executeUpdate();
		conn.commit();
		ps.close();
		
		return count;

 	}
    
    /**
	 * tested ok 062315 TG
	 * @param employeeOutOfOffice
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
    public int approveOOO(EmployeeOutOfOffice employeeOutOfOffice) throws SQLException, Exception {		
		String sql = "UPDATE empOutOfOffice SET remarks=?, approvedBy=?, "
						+ "status=? WHERE empOOOId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
	    
	    ps.setString(1, employeeOutOfOffice.getRemarks());
	    ps.setInt(2, employeeOutOfOffice.getApprovedBy());	    
	    ps.setInt(3, employeeOutOfOffice.getStatus());	    
	    ps.setInt(4, employeeOutOfOffice.getEmpOOOId());
		int count = ps.executeUpdate();
		conn.commit();
		ps.close();
		
		return count;

 	}
    
    public List<EmployeeOutOfOffice> getOutOfOfficeByDateRange(String dateFrom, String dateTo) throws SQLException {
    	List<EmployeeOutOfOffice> resultList = new ArrayList<EmployeeOutOfOffice>();
    	StringBuffer sql = new StringBuffer();       	    	
    	
    	sql.append("SELECT e.empNo, (e.firstname + ' ' + e.lastname) as name, e.empNo, eOOO.approvedBy, eOOO.createdBy, eOOO.createdDate, eOOO.dateFrom, eOOO.dateTo, eOOO.noOfHours, eOOO.provider, eOOO.remarks, eOOO.secondaryApprover, eOOO.status, eOOO.titleActivity ");    	
    	sql.append(" FROM empOutOfOffice eOOO, employee e WHERE eOOO.empid = e.empid AND CONVERT(VARCHAR(10),eOOO.dateFrom,110) BETWEEN '");
    	sql.append(dateFrom);
    	sql.append("' AND '");
    	sql.append(dateTo);    	    	
    	sql.append("' ORDER BY name");
    	
		System.out.println("SQL getOutOfOfficeByDateRange: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    
    	while(rs.next()) {
    		EmployeeOutOfOffice eooo = new EmployeeOutOfOffice();
    		
    		eooo.setApprovedBy(rs.getInt("approvedBy"));
    		eooo.setCreatedBy(rs.getInt("createdBy"));
    		eooo.setCreatedDate(rs.getDate("createdDate"));
    		eooo.setDateFrom(rs.getString("dateFrom"));
    		eooo.setDateTo(rs.getString("dateTo"));
    		eooo.setNoOfHours(rs.getInt("noOfHours"));
    		eooo.setProvider(rs.getString("provider"));
    		eooo.setRemarks(rs.getString("remarks"));
    		eooo.setSecondaryApprover(rs.getInt("secondaryApprover"));
    		eooo.setStatus(rs.getInt("status"));
    		eooo.setTitleActivity(rs.getString("titleActivity"));
 	    	
 	    	resultList.add(eooo);
    	}
    	
    	
    	rs.close();
    	ps.close();
    	return resultList;
    }
    
//    public List<EmployeeOutOfOffice> getAllEmployeeOOOForSvApprovalBySuperVisorId(int superVisorId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
//    	
//    	StringBuffer sql = new StringBuffer();   
//    	
//    	//if(jtSorting == null){
//    		jtSorting = "eOOO.dateFrom DESC";
//    	//}
//    	
//    	sql.append("WITH OrderedList AS (SELECT eOOO.approvedBy, eOOO.createdBy, eOOO.createdDate, eOOO.dateFrom, eOOO.dateTo, eOOO.empId, eOOO.empOOOId, eOOO.noOfHours, eOOO.provider, eOOO.remarks, eOOO.secondaryApprover, eOOO.status, eOOO.titleActivity, ROW_NUMBER() OVER(ORDER BY ");	
//    	sql.append(jtSorting);    	
//    	sql.append(") AS RowNumber FROM employee e, empOutOfOffice eOOO WHERE e.empId = eOOO.empId AND eOOO.status = 0 AND e.superVisor1Id = ");
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
//	    List<EmployeeOutOfOffice> list = new ArrayList<EmployeeOutOfOffice>();
//	    
//	    EmployeeOutOfOffice employeeOOO = null;
//	    
//	    while (rs.next()) {
//	    	employeeOOO = new EmployeeOutOfOffice();
// 	    	employeeOOO.setApprovedBy(rs.getInt("approvedBy"));
// 	    	employeeOOO.setCreatedBy(rs.getInt("createdBy"));
// 	    	employeeOOO.setCreatedDate(rs.getDate("createdDate"));
// 	    	employeeOOO.setDateFrom(rs.getString("dateFrom"));
// 	    	employeeOOO.setDateTo(rs.getString("dateTo"));
// 	    	employeeOOO.setEmpId(rs.getInt("empId"));
// 	    	employeeOOO.setEmpOOOId(rs.getInt("empOOOId"));
// 	    	employeeOOO.setNoOfHours(rs.getInt("noOfHours"));
// 	    	employeeOOO.setProvider(rs.getString("provider"));
// 	    	employeeOOO.setRemarks(rs.getString("remarks"));
// 	    	employeeOOO.setSecondaryApprover(rs.getInt("secondaryApprover"));
// 	    	employeeOOO.setStatus(rs.getInt("status"));
// 	    	employeeOOO.setTitleActivity(rs.getString("titleActivity"));
// 	    	
//	    	list.add(employeeOOO);
//	    	
//	    	
//	    }
//	    
//	    ps.close();
//	    rs.close();      
//	    return list; 
//    	
//    	
//    	
//    	
////    	EmployeeOutOfOffice employeeOOO = null;
////    	List<EmployeeOutOfOffice> employeeOOOList = new ArrayList<EmployeeOutOfOffice>();
//// 		String sql = "SELECT eOOO.* FROM employee e, empOutOfOffice eOOO "+
//// 						"WHERE e.empId = eOOO.empId " +
////						"AND (e.superVisor1Id = ? OR e.superVisor2Id = ? OR e.superVisor3Id=?) " +
////						"AND (eOOO.approvedBy = 0  AND eOOO.secondaryApprover = 0)";
//// 		PreparedStatement ps = conn.prepareStatement(sql.toString());
//// 		ps.setInt(1, superVisorId);
//// 		ps.setInt(2, superVisorId);
//// 		ps.setInt(3, superVisorId);
//// 		System.out.println(superVisorId);
////
//// 	    ResultSet rs = ps.executeQuery();
//// 	    
//// 	    while (rs.next()) {
//// 	    	employeeOOO = new EmployeeOutOfOffice();
//// 	    	employeeOOO.setApprovedBy(rs.getInt("approvedBy"));
//// 	    	employeeOOO.setCreatedBy(rs.getInt("createdBy"));
//// 	    	employeeOOO.setCreatedDate(rs.getDate("createdDate"));
//// 	    	employeeOOO.setDateFrom(rs.getString("dateFrom"));
//// 	    	employeeOOO.setDateTo(rs.getString("dateTo"));
//// 	    	employeeOOO.setEmpId(rs.getInt("empId"));
//// 	    	employeeOOO.setEmpOOOId(rs.getInt("empOOOId"));
//// 	    	employeeOOO.setNoOfHours(rs.getInt("noOfHours"));
//// 	    	employeeOOO.setProvider(rs.getString("provider"));
//// 	    	employeeOOO.setRemarks(rs.getString("remarks"));
//// 	    	employeeOOO.setSecondaryApprover(rs.getInt("secondaryApprover"));
//// 	    	employeeOOO.setStatus(rs.getInt("status"));
//// 	    	employeeOOO.setTitleActivity(rs.getString("titleActivity"));
//// 	    	
//// 	    	employeeOOOList.add(employeeOOO);
//// 		}
//// 	    sql = null;
//// 	    ps.close();
//// 	    rs.close();	 
//// 	    return employeeOOOList;
//    }
    
    
    
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}
    
    
    //NEW
    public  int getAllCount() throws SQLException, Exception {
    	int count = 0;    	
    	StringBuffer sql = new StringBuffer();    	
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, empOutOfOffice l WHERE e.empId = l.empId");   
    	  	
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
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, empOutOfOffice l WHERE e.empId = l.empId AND e.sectionId = ");   	
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
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, empOutOfOffice l WHERE e.empId = l.empId AND e.unitId = ");   	
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
    	
    	sql.append("SELECT count(*) as ctr FROM employee e, empOutOfOffice l WHERE e.empId = l.empId AND e.subUnitId = ");   	
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
    
    //NEW
    
    
    //0 - FOR APPROVAL
    //1 - NOT APPROVED
    //2 - FOR UNIT SUPERVISOR APPROVAL
    //3 - FOR SECTION SUPERVISOR APPROVAL
    //4 - FOR HR APPROVAL
    //5 - FOR ADMIN APPROVAL
	//6 - APPROVED
    public List<EmployeeOutOfOffice> getAllEmployeeOOOForApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	//if(jtSorting == null){
    		jtSorting = "eOOO.dateFrom DESC";
    	//}
    	
    	sql.append("WITH OrderedList AS (SELECT eOOO.approvedBy, eOOO.createdBy, eOOO.createdDate, eOOO.dateFrom, eOOO.dateTo, eOOO.empId, eOOO.empOOOId, eOOO.noOfHours, eOOO.provider, eOOO.remarks, eOOO.secondaryApprover, eOOO.status, eOOO.titleActivity, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empOutOfOffice eOOO WHERE e.empId = eOOO.empId AND eOOO.status in (0, 2, 3, 4, 5) ");
    	sql.append(" AND e.empId <> ");
    	sql.append(empId);
    	sql.append(" ) SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeOutOfOffice> list = new ArrayList<EmployeeOutOfOffice>();
	    
	    EmployeeOutOfOffice employeeOOO = null;
	    
	    while (rs.next()) {
	    	employeeOOO = new EmployeeOutOfOffice();
 	    	employeeOOO.setApprovedBy(rs.getInt("approvedBy"));
 	    	employeeOOO.setCreatedBy(rs.getInt("createdBy"));
 	    	employeeOOO.setCreatedDate(rs.getDate("createdDate"));
 	    	employeeOOO.setDateFrom(rs.getString("dateFrom"));
 	    	employeeOOO.setDateTo(rs.getString("dateTo"));
 	    	employeeOOO.setEmpId(rs.getInt("empId"));
 	    	employeeOOO.setEmpOOOId(rs.getInt("empOOOId"));
 	    	employeeOOO.setNoOfHours(rs.getInt("noOfHours"));
 	    	employeeOOO.setProvider(rs.getString("provider"));
 	    	employeeOOO.setRemarks(rs.getString("remarks"));
 	    	employeeOOO.setSecondaryApprover(rs.getInt("secondaryApprover"));
 	    	employeeOOO.setStatus(rs.getInt("status"));
 	    	employeeOOO.setTitleActivity(rs.getString("titleActivity"));
 	    	
	    	list.add(employeeOOO);
	    	
	    	
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;  	
    	

    }
    
    public List<EmployeeOutOfOffice> getAllEmployeeOOOForHRApproval(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	//if(jtSorting == null){
    		jtSorting = "eOOO.dateFrom DESC";
    	//}
    	
    	sql.append("WITH OrderedList AS (SELECT eOOO.approvedBy, eOOO.createdBy, eOOO.createdDate, eOOO.dateFrom, eOOO.dateTo, eOOO.empId, eOOO.empOOOId, eOOO.noOfHours, eOOO.provider, eOOO.remarks, eOOO.secondaryApprover, eOOO.status, eOOO.titleActivity, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empOutOfOffice eOOO WHERE e.empId = eOOO.empId AND eOOO.status in (0, 2, 3, 4) ");
    	sql.append(" AND e.empId <> ");
    	sql.append(empId);
    	sql.append(" ) SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeOutOfOffice> list = new ArrayList<EmployeeOutOfOffice>();
	    
	    EmployeeOutOfOffice employeeOOO = null;
	    
	    while (rs.next()) {
	    	employeeOOO = new EmployeeOutOfOffice();
 	    	employeeOOO.setApprovedBy(rs.getInt("approvedBy"));
 	    	employeeOOO.setCreatedBy(rs.getInt("createdBy"));
 	    	employeeOOO.setCreatedDate(rs.getDate("createdDate"));
 	    	employeeOOO.setDateFrom(rs.getString("dateFrom"));
 	    	employeeOOO.setDateTo(rs.getString("dateTo"));
 	    	employeeOOO.setEmpId(rs.getInt("empId"));
 	    	employeeOOO.setEmpOOOId(rs.getInt("empOOOId"));
 	    	employeeOOO.setNoOfHours(rs.getInt("noOfHours"));
 	    	employeeOOO.setProvider(rs.getString("provider"));
 	    	employeeOOO.setRemarks(rs.getString("remarks"));
 	    	employeeOOO.setSecondaryApprover(rs.getInt("secondaryApprover"));
 	    	employeeOOO.setStatus(rs.getInt("status"));
 	    	employeeOOO.setTitleActivity(rs.getString("titleActivity"));
 	    	
	    	list.add(employeeOOO);
	    	
	    	
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;  	
    	

    }
    
    public List<EmployeeOutOfOffice> getAllEmployeeOOOForApprovalBySectionId(int empId, int sectionId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	//if(jtSorting == null){
    		jtSorting = "eOOO.dateFrom DESC";
    	//}
    	
    	sql.append("WITH OrderedList AS (SELECT eOOO.approvedBy, eOOO.createdBy, eOOO.createdDate, eOOO.dateFrom, eOOO.dateTo, eOOO.empId, eOOO.empOOOId, eOOO.noOfHours, eOOO.provider, eOOO.remarks, eOOO.secondaryApprover, eOOO.status, eOOO.titleActivity, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empOutOfOffice eOOO WHERE e.empId = eOOO.empId AND eOOO.status in (0, 2, 3) AND e.sectionId = ");
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
	    List<EmployeeOutOfOffice> list = new ArrayList<EmployeeOutOfOffice>();
	    
	    EmployeeOutOfOffice employeeOOO = null;
	    
	    while (rs.next()) {
	    	employeeOOO = new EmployeeOutOfOffice();
 	    	employeeOOO.setApprovedBy(rs.getInt("approvedBy"));
 	    	employeeOOO.setCreatedBy(rs.getInt("createdBy"));
 	    	employeeOOO.setCreatedDate(rs.getDate("createdDate"));
 	    	employeeOOO.setDateFrom(rs.getString("dateFrom"));
 	    	employeeOOO.setDateTo(rs.getString("dateTo"));
 	    	employeeOOO.setEmpId(rs.getInt("empId"));
 	    	employeeOOO.setEmpOOOId(rs.getInt("empOOOId"));
 	    	employeeOOO.setNoOfHours(rs.getInt("noOfHours"));
 	    	employeeOOO.setProvider(rs.getString("provider"));
 	    	employeeOOO.setRemarks(rs.getString("remarks"));
 	    	employeeOOO.setSecondaryApprover(rs.getInt("secondaryApprover"));
 	    	employeeOOO.setStatus(rs.getInt("status"));
 	    	employeeOOO.setTitleActivity(rs.getString("titleActivity"));
 	    	
	    	list.add(employeeOOO);
	    	
	    	
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;  	
    	

    }
    
    public List<EmployeeOutOfOffice> getAllEmployeeOOOForApprovalByUnitId(int empId, int unitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	//if(jtSorting == null){
    		jtSorting = "eOOO.dateFrom DESC";
    	//}
    	
    	sql.append("WITH OrderedList AS (SELECT eOOO.approvedBy, eOOO.createdBy, eOOO.createdDate, eOOO.dateFrom, eOOO.dateTo, eOOO.empId, eOOO.empOOOId, eOOO.noOfHours, eOOO.provider, eOOO.remarks, eOOO.secondaryApprover, eOOO.status, eOOO.titleActivity, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empOutOfOffice eOOO WHERE e.empId = eOOO.empId AND eOOO.status in (0, 2) AND e.unitId = ");
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
	    List<EmployeeOutOfOffice> list = new ArrayList<EmployeeOutOfOffice>();
	    
	    EmployeeOutOfOffice employeeOOO = null;
	    
	    while (rs.next()) {
	    	employeeOOO = new EmployeeOutOfOffice();
 	    	employeeOOO.setApprovedBy(rs.getInt("approvedBy"));
 	    	employeeOOO.setCreatedBy(rs.getInt("createdBy"));
 	    	employeeOOO.setCreatedDate(rs.getDate("createdDate"));
 	    	employeeOOO.setDateFrom(rs.getString("dateFrom"));
 	    	employeeOOO.setDateTo(rs.getString("dateTo"));
 	    	employeeOOO.setEmpId(rs.getInt("empId"));
 	    	employeeOOO.setEmpOOOId(rs.getInt("empOOOId"));
 	    	employeeOOO.setNoOfHours(rs.getInt("noOfHours"));
 	    	employeeOOO.setProvider(rs.getString("provider"));
 	    	employeeOOO.setRemarks(rs.getString("remarks"));
 	    	employeeOOO.setSecondaryApprover(rs.getInt("secondaryApprover"));
 	    	employeeOOO.setStatus(rs.getInt("status"));
 	    	employeeOOO.setTitleActivity(rs.getString("titleActivity"));
 	    	
	    	list.add(employeeOOO);
	    	
	    	
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;  	
    	

    }
    
    public List<EmployeeOutOfOffice> getAllEmployeeOOOForApprovalBySubUnitId(int empId, int subUnitId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();   
    	
    	//if(jtSorting == null){
    		jtSorting = "eOOO.dateFrom DESC";
    	//}
    	
    	sql.append("WITH OrderedList AS (SELECT eOOO.approvedBy, eOOO.createdBy, eOOO.createdDate, eOOO.dateFrom, eOOO.dateTo, eOOO.empId, eOOO.empOOOId, eOOO.noOfHours, eOOO.provider, eOOO.remarks, eOOO.secondaryApprover, eOOO.status, eOOO.titleActivity, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employee e, empOutOfOffice eOOO WHERE e.empId = eOOO.empId AND eOOO.status = 0 AND e.subUnitId = ");
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
	    List<EmployeeOutOfOffice> list = new ArrayList<EmployeeOutOfOffice>();
	    
	    EmployeeOutOfOffice employeeOOO = null;
	    
	    while (rs.next()) {
	    	employeeOOO = new EmployeeOutOfOffice();
 	    	employeeOOO.setApprovedBy(rs.getInt("approvedBy"));
 	    	employeeOOO.setCreatedBy(rs.getInt("createdBy"));
 	    	employeeOOO.setCreatedDate(rs.getDate("createdDate"));
 	    	employeeOOO.setDateFrom(rs.getString("dateFrom"));
 	    	employeeOOO.setDateTo(rs.getString("dateTo"));
 	    	employeeOOO.setEmpId(rs.getInt("empId"));
 	    	employeeOOO.setEmpOOOId(rs.getInt("empOOOId"));
 	    	employeeOOO.setNoOfHours(rs.getInt("noOfHours"));
 	    	employeeOOO.setProvider(rs.getString("provider"));
 	    	employeeOOO.setRemarks(rs.getString("remarks"));
 	    	employeeOOO.setSecondaryApprover(rs.getInt("secondaryApprover"));
 	    	employeeOOO.setStatus(rs.getInt("status"));
 	    	employeeOOO.setTitleActivity(rs.getString("titleActivity"));
 	    	
	    	list.add(employeeOOO);
	    	
	    	
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;  	
    	

    }
    
    
}
