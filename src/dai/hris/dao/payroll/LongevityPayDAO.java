package dai.hris.dao.payroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dai.hris.dao.DBConstants;
import dai.hris.model.LongevityPay;

public class LongevityPayDAO {

	private Connection conn = null;

	public LongevityPayDAO() {    	
		try {
			/* MS SQL CODE */    		
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
			conn.setAutoCommit(false);
		} catch (SQLException sqle) {
			System.out.println("LongevityPayDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("LongevityPayDAO :" + e.getMessage());
		}
	}
	
	public void saveLongevityPay(LongevityPay model) throws SQLException, Exception {
		add(model);
	}
	
	public void saveOrUpdate(LongevityPay model) throws SQLException, Exception {
		if (model.getLongevityPayId() > 0) {
			update(model);
		} else {
			add(model);
		}
	}
	
	public int getCount(int empId) throws SQLException, Exception {
		int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM longevityPay WHERE empId = ");
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
	
	public List<LongevityPay> getLongevityPayListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "year ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT hp.longevityPayId, hp.salaryGrade, hp.basicSalary, hp.netAmountDue, hp.year, hp.month, hp.remarks, hp.empId, e.empNo, e.firstname, e.lastname, ROW_NUMBER() OVER(ORDER BY ");		
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM longevityPay hp, employee e WHERE hp.empId = e.empId AND hp.empId =  ");
    	sql.append(empId);
    	sql.append(" ) SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<LongevityPay> list = new ArrayList<LongevityPay>();
	    
	    while (rs.next()) {	    	
	    	LongevityPay model = new LongevityPay();
	    	
	    	model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("firstname") + " " + rs.getString("lastname"));
	    	model.setLongevityPayId(rs.getInt("longevityPayId"));
	    	model.setSalaryGrade(rs.getInt("salaryGrade"));
	    	model.setBasicSalary(rs.getBigDecimal("basicSalary"));
	    	model.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
	    	model.setYear(rs.getInt("year"));
	    	model.setMonth(rs.getInt("month"));
	    	model.setRemarks(rs.getString("remarks"));
	    	model.setEmpId(rs.getInt("empId"));
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   
	}
	
	public List<LongevityPay> getLongevityPayListByDateRange(String month, String year) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();   
    	
    	
    	sql.append("SELECT e.empNo, (e.firstname + ' ' + e.lastname) as name, hp.longevityPayId, hp.salaryGrade, hp.basicSalary, hp.netAmountDue, hp.year, hp.month, hp.remarks, hp.empId ");	
    	sql.append("FROM longevityPay hp, employee e WHERE hp.empid = e.empid AND hp.month = ");
    	sql.append(month);
    	sql.append(" AND hp.year = ");
    	sql.append(year);    
    	sql.append(" ORDER BY name");
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<LongevityPay> list = new ArrayList<LongevityPay>();
	    
	    while (rs.next()) {	    	
	    	LongevityPay model = new LongevityPay();
	    	
	    	model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name"));
	    	model.setLongevityPayId(rs.getInt("longevityPayId"));
	    	model.setSalaryGrade(rs.getInt("salaryGrade"));
	    	model.setBasicSalary(rs.getBigDecimal("basicSalary"));
	    	model.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
	    	model.setYear(rs.getInt("year"));
	    	model.setMonth(rs.getInt("month"));
	    	model.setRemarks(rs.getString("remarks"));
	    	model.setEmpId(rs.getInt("empId"));
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   
	}

	public void add(LongevityPay lp) throws SQLException, Exception {
		String sql = "insert into longevityPay "
				+ " (salaryGrade, basicSalary, netAmountDue, year, month, remarks, empId) "
				+ " values (?,?,?,?,?,?,?) ";
		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, lp.getSalaryGrade());
        ps.setBigDecimal(2, lp.getBasicSalary());
        ps.setBigDecimal(3, lp.getNetAmountDue());
        ps.setInt(4, lp.getYear());
        ps.setInt(5, lp.getMonth());
        ps.setString(6, lp.getRemarks());
        ps.setInt(7, lp.getEmpId());
        ps.executeUpdate();
          
        ResultSet keyResultSet = ps.getGeneratedKeys();
        int generatedAutoIncrementId = 0;
        if (keyResultSet.next()) {
        	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
        	lp.setLongevityPayId(generatedAutoIncrementId);
        	conn.commit();
        }

        ps.close();
        keyResultSet.close();
        conn.close();
	}

	public void update(LongevityPay lp) throws SQLException, Exception {
		String sql = "update longevityPay set "
				+ " salaryGrade=?, basicSalary=?, netAmountDue=?, year=?, month=?, remarks=?, empId=? "
				+ " where longevityPayId=?";
		PreparedStatement ps  = conn.prepareStatement(sql);
		ps.setInt(1, lp.getSalaryGrade());
        ps.setBigDecimal(2, lp.getBasicSalary());
        ps.setBigDecimal(3, lp.getNetAmountDue());
        ps.setInt(4, lp.getYear());
        ps.setInt(5, lp.getMonth());
        ps.setString(6, lp.getRemarks());
        ps.setInt(7, lp.getEmpId());
        ps.setInt(8, lp.getLongevityPayId());
 		ps.executeUpdate();
 		
 		conn.commit();
 		ps.close();
 		conn.close();
	}

	public void delete(LongevityPay lp) throws SQLException, Exception {
		String sql = "delete from longevityPay where longevityPayId=?";
		PreparedStatement ps  = conn.prepareStatement(sql);
		ps.setInt(1, lp.getLongevityPayId());
        ps.executeUpdate();
        
        conn.commit();
        ps.close();
        conn.close();
	}
	
	public void closeConnection() throws SQLException, Exception {
		conn.close();
	}
	
	public int[] batchUpdate(List<LongevityPay> lpList) throws SQLException, Exception {
		int[] result = null;
		String sql = "update longevityPay set "
				+ " salaryGrade=?, basicSalary=?, netAmountDue=?, year=?, month=?, remarks=?, empId=? "
				+ " where longevityPayId=?";
		PreparedStatement ps  = conn.prepareStatement(sql);
		for (int i = 0; i < lpList.size(); i++) {
			LongevityPay lp = lpList.get(i);
			ps.setInt(1, lp.getSalaryGrade());
			ps.setBigDecimal(2, lp.getBasicSalary());
			ps.setBigDecimal(3, lp.getNetAmountDue());
			ps.setInt(4, lp.getYear());
			ps.setInt(5, lp.getMonth());
			ps.setString(6, lp.getRemarks());
			ps.setInt(7, lp.getEmpId());
			ps.setInt(8, lp.getLongevityPayId());
			ps.addBatch();
		}
		result = ps.executeBatch();
		conn.commit();
		ps.close();
		return result;
	}
	
    public int[] batchInsert(List<LongevityPay> lpList) throws SQLException, Exception {
    	int[] result = null;
    	String sql = "insert into longevityPay "
				+ " (salaryGrade, basicSalary, netAmountDue, year, month, remarks, empId) "
				+ " values (?,?,?,?,?,?,?) ";
		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		for (int i = 0; i < lpList.size(); i++) {
			LongevityPay lp = lpList.get(i);
			ps.setInt(1, lp.getSalaryGrade());
			ps.setBigDecimal(2, lp.getBasicSalary());
			ps.setBigDecimal(3, lp.getNetAmountDue());
			ps.setInt(4, lp.getYear());
			ps.setInt(5, lp.getMonth());
			ps.setString(6, lp.getRemarks());
			ps.setInt(7, lp.getEmpId());
			ps.addBatch();
		}
		result = ps.executeBatch();
		conn.commit();
		ps.close();
		return result;
    }
    
    public List<LongevityPay> getAllByEmployeeId(int empId) throws SQLException, Exception {
    	List<LongevityPay> resultList = new ArrayList<LongevityPay>();
    	String sql = "select * from longevityPay where empId = ?";
    	PreparedStatement ps = conn.prepareStatement(sql);
    	ps.setInt(1, empId);
    	
    	ResultSet rs = ps.executeQuery();
    	while (rs.next()) {
    		LongevityPay lp = new LongevityPay();
    		lp.setSalaryGrade(rs.getInt("salaryGrade"));
    		lp.setBasicSalary(rs.getBigDecimal("basicSalary"));
    		lp.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
    		lp.setYear(rs.getInt("year"));
    		lp.setMonth(rs.getInt("month"));
    		lp.setRemarks(rs.getString("remarks"));
    		lp.setEmpId(rs.getInt("empId"));
    		resultList.add(lp);
    	}
    	rs.close();
    	ps.close();
    	return resultList;
    }
    
    public List<LongevityPay> getAllByJobTitleId(int jtId) throws SQLException, Exception {
    	List<LongevityPay> resultList = new ArrayList<LongevityPay>();
    	String sql = "select * from longevityPay "
    			+ " where empId in "
    			+ " (select empId from employee where jobTitleId = ?) ";
    	PreparedStatement ps = conn.prepareStatement(sql);
    	ps.setInt(1, jtId);
    	
    	ResultSet rs = ps.executeQuery();
    	while (rs.next()) {
    		LongevityPay lp = new LongevityPay();
    		lp.setSalaryGrade(rs.getInt("salaryGrade"));
    		lp.setBasicSalary(rs.getBigDecimal("basicSalary"));
    		lp.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
    		lp.setYear(rs.getInt("year"));
    		lp.setMonth(rs.getInt("month"));
    		lp.setRemarks(rs.getString("remarks"));
    		lp.setEmpId(rs.getInt("empId"));
    		resultList.add(lp);
    	}
    	rs.close();
    	ps.close();
    	return resultList;
    }
}
