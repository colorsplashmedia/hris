package dai.hris.dao.filemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dai.hris.action.filemanager.util.EmployeeUtil;
import dai.hris.dao.DBConstants;
import dai.hris.model.EmployeeMemo;

/**
 * 
 * @author danielpadilla
 *
 */
public class EmployeeMemoDAO {
	Connection conn = null;
	EmployeeUtil util = new EmployeeUtil();
	public EmployeeMemoDAO() {
		
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("EmployeeMemoDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("EmployeeMemoDAO :" + e.getMessage());
		}
	}
	
	public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
		//no dependencies
		return false;
	}
	
	public  int getCountWithFilter(int memoRecipientEmpId, String filter) throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM employeeMemo WHERE toRecipientEmpId = ");
    	sql.append(memoRecipientEmpId);
    	sql.append(" AND subject like '%");
    	sql.append(filter);  
    	sql.append("%'");  
    	
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
    
    public  int getCount(int memoRecipientEmpId) throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM employeeMemo WHERE toRecipientEmpId = ");
    	sql.append(memoRecipientEmpId);
    	  	
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

	public EmployeeMemo getEmployeeMemoByEmployeeMemoId(int employeeMemoId) throws SQLException, Exception {			    		
		String sql = "SELECT * FROM employeeMemo where EmployeeMemoId = ?";
		EmployeeMemo employeeMemo = null;
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, employeeMemoId);
		
	    ResultSet rs = ps.executeQuery();
	    
	    while (rs.next()) {
	    	employeeMemo = new EmployeeMemo();
	    	employeeMemo.setCcRecipient(rs.getString("ccRecipient"));
	    	employeeMemo.setCreatedBy(rs.getInt("createdBy"));
	    	employeeMemo.setEmployeeMemoId(rs.getInt("employeeMemoId"));
	    	employeeMemo.setFromSender(rs.getString("fromSender"));
	    	employeeMemo.setMemoFiledDate(util.sqlDateToString(rs.getDate("memofiledDate")));
	    	employeeMemo.setMessage(rs.getString("message"));
	    	employeeMemo.setRemarks(rs.getString("remarks"));
	    	employeeMemo.setSubject(rs.getString("subject"));
	    	employeeMemo.setToRecipientEmpId(rs.getInt("toRecipientEmpId"));
	    }
	    
	    ps.close();
	    rs.close();      
	    return employeeMemo;		
	}
	

	/**
	 * get all memos that were produced by "createdBy" empId (assumed to be supervisor or admin)
	 * @param createdByEmpId
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<EmployeeMemo> getEmployeeMemoListByCreatedByEmpId(int createdByEmpId) throws SQLException, Exception {			    		
		String sql = "SELECT * FROM employeeMemo where createdBy = ?";		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, createdByEmpId);
	
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeMemo> list = new ArrayList<EmployeeMemo>();
	    
	    while (rs.next()) {
	    	EmployeeMemo employeeMemo = new EmployeeMemo();
	    	employeeMemo.setCcRecipient(rs.getString("ccRecipient"));
	    	employeeMemo.setCreatedBy(rs.getInt("createdBy"));
	    	employeeMemo.setEmployeeMemoId(rs.getInt("employeeMemoId"));
	    	employeeMemo.setFromSender(rs.getString("fromSender"));
	    	employeeMemo.setMemoFiledDate(util.sqlDateToString(rs.getDate("memofiledDate")));
	    	employeeMemo.setMessage(rs.getString("message"));
	    	employeeMemo.setRemarks(rs.getString("remarks"));
	    	employeeMemo.setSubject(rs.getString("subject"));
	    	employeeMemo.setToRecipientEmpId(rs.getInt("toRecipientEmpId"));
	    	list.add(employeeMemo);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;		
	}
	
	
	/**
	 * get all memos that are intended for the recipient empId
	 * @param createdByEmpId
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<EmployeeMemo> getEmployeeMemoListByToRecipientEmpId(int memoRecipientEmpId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {			    		
		
		StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "memofiledDate DESC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT ccRecipient, createdBy, employeeMemoId, fromSender, memofiledDate, message, remarks, subject, toRecipientEmpId, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employeeMemo WHERE toRecipientEmpId = ");
    	sql.append(memoRecipientEmpId);    	
    	sql.append(") SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeMemo> list = new ArrayList<EmployeeMemo>();
	    
	    while (rs.next()) {
	    	EmployeeMemo employeeMemo = new EmployeeMemo();
	    	employeeMemo.setCcRecipient(rs.getString("ccRecipient"));
	    	employeeMemo.setCreatedBy(rs.getInt("createdBy"));
	    	employeeMemo.setEmployeeMemoId(rs.getInt("employeeMemoId"));
	    	employeeMemo.setFromSender(rs.getString("fromSender"));
	    	employeeMemo.setMemoFiledDate(util.sqlDateToString(rs.getDate("memofiledDate")));
	    	employeeMemo.setMessage(rs.getString("message"));
	    	employeeMemo.setRemarks(rs.getString("remarks"));
	    	employeeMemo.setSubject(rs.getString("subject"));
	    	employeeMemo.setToRecipientEmpId(rs.getInt("toRecipientEmpId"));
	    	list.add(employeeMemo);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;	
		
	}
	 
	
	
	public List<EmployeeMemo> getEmployeeMemoListByToRecipientEmpIdWithFilter(int memoRecipientEmpId, int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {			    		
		
		StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "memofiledDate DESC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT ccRecipient, createdBy, employeeMemoId, fromSender, memofiledDate, message, remarks, subject, toRecipientEmpId, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM employeeMemo WHERE toRecipientEmpId = ");
    	sql.append(memoRecipientEmpId);    	
    	sql.append(" AND subject like '%");
    	sql.append(filter);    	
    	sql.append("%') SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeMemo> list = new ArrayList<EmployeeMemo>();
	    
	    while (rs.next()) {
	    	EmployeeMemo employeeMemo = new EmployeeMemo();
	    	employeeMemo.setCcRecipient(rs.getString("ccRecipient"));
	    	employeeMemo.setCreatedBy(rs.getInt("createdBy"));
	    	employeeMemo.setEmployeeMemoId(rs.getInt("employeeMemoId"));
	    	employeeMemo.setFromSender(rs.getString("fromSender"));
	    	employeeMemo.setMemoFiledDate(util.sqlDateToString(rs.getDate("memofiledDate")));
	    	employeeMemo.setMessage(rs.getString("message"));
	    	employeeMemo.setRemarks(rs.getString("remarks"));
	    	employeeMemo.setSubject(rs.getString("subject"));
	    	employeeMemo.setToRecipientEmpId(rs.getInt("toRecipientEmpId"));
	    	list.add(employeeMemo);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;	
		
	}

	/**
	 * 
	 * @param employeeMemo
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean add(EmployeeMemo employeeMemo) throws SQLException, Exception {
		String sql = "INSERT INTO employeeMemo (memoFiledDate, toRecipientEmpId, ccRecipient, createdBy, fromSender, message, remarks, subject) VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setDate(1, util.convertToSqlDate(employeeMemo.getMemoFiledDate()));
	    ps.setInt(2, employeeMemo.getToRecipientEmpId());
	    ps.setString(3, employeeMemo.getCcRecipient());
	    ps.setInt(4, employeeMemo.getCreatedBy());
	    ps.setString(5, employeeMemo.getFromSender());
	    ps.setString(6, employeeMemo.getMessage());
	    ps.setString(7, employeeMemo.getRemarks());
	    ps.setString(8, employeeMemo.getSubject());
	    
	    int count = ps.executeUpdate();
	    boolean status = false;  
	    ResultSet keyResultSet = ps.getGeneratedKeys();
	     int generatedAutoIncrementId = 0;
	     if (keyResultSet.next()) {
	      	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
	      	employeeMemo.setEmployeeMemoId(generatedAutoIncrementId);
	      	conn.commit();
	      }
		
	     ps.close();
	     keyResultSet.close();
		 if (count == 1) {
			 status = true;
		 }		
	    return status;
	}

	    
	public void delete(int id)  throws SQLException, Exception {
		String sql = "DELETE FROM employeeMemo WHERE employeeMemoId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        conn.commit();
        ps.close();
	}
	
	/**
	 * 
	 * @param employeeMemo
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
    public boolean update(EmployeeMemo employeeMemo) throws SQLException, Exception {		
		String sql = "UPDATE employeeMemo SET memoFiledDate=?, toRecipientEmpId=?, ccRecipient=?, createdBy=?, fromSender=?, message=?, remarks=?, subject=? WHERE employeeMemoId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
	    ps.setDate(1, util.convertToSqlDate(employeeMemo.getMemoFiledDate()));
	    ps.setInt(2, employeeMemo.getToRecipientEmpId());
	    ps.setString(3, employeeMemo.getCcRecipient());
	    ps.setInt(4, employeeMemo.getCreatedBy());
	    ps.setString(5, employeeMemo.getFromSender());
	    ps.setString(6, employeeMemo.getMessage());
	    ps.setString(7, employeeMemo.getRemarks());
	    ps.setString(8, employeeMemo.getSubject());
	    ps.setInt(9, employeeMemo.getEmployeeMemoId());
		int count = ps.executeUpdate();
		boolean status = false;
		conn.commit();
		ps.close();
		if (count == 1) {
			status = true;
		}			
		return status;

 	}
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}
}
