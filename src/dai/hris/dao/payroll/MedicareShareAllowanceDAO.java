package dai.hris.dao.payroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dai.hris.dao.DBConstants;
import dai.hris.model.MedicareShareAllowance;

public class MedicareShareAllowanceDAO {

	private Connection conn = null;

	public MedicareShareAllowanceDAO() {    	
		try {
			/* MS SQL CODE */    		
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
			conn.setAutoCommit(false);
		} catch (SQLException sqle) {
			System.out.println("MedicareShareAllowanceDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("MedicareShareAllowanceDAO :" + e.getMessage());
		}
	}
	
	
	public void saveMedicareShare(MedicareShareAllowance model) throws SQLException, Exception {
		add(model);
	}
	
	public void saveOrUpdate(MedicareShareAllowance model) throws SQLException, Exception {
		if (model.getMedicareShareAllowanceId() > 0) {
			update(model);
		} else {
			add(model);
		}
	}
    
	
	public int getCount(int empId) throws SQLException, Exception {
		int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM medicareShareAllowance WHERE empId = ");
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
    
	
	public List<MedicareShareAllowance> getMedicareShareAllowanceListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "date ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT hp.medicareShareAllowanceId, hp.numDays, hp.ratePerDay, hp.netAmountDue, hp.date, hp.remarks, hp.empId, e.empNo, e.firstname, e.lastname, ROW_NUMBER() OVER(ORDER BY ");		
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM medicareShareAllowance hp, employee e WHERE hp.empId = e.empId AND hp.empId =  ");
    	sql.append(empId);
    	sql.append(" ) SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<MedicareShareAllowance> list = new ArrayList<MedicareShareAllowance>();
	    
	    while (rs.next()) {	    	
	    	MedicareShareAllowance model = new MedicareShareAllowance();
	    	
	    	model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("firstname") + " " + rs.getString("lastname"));
	    	model.setMedicareShareAllowanceId(rs.getInt("medicareShareAllowanceId"));
	    	model.setNumDays(rs.getInt("numDays"));
	    	model.setRatePerDay(rs.getBigDecimal("ratePerDay"));
	    	model.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
	    	model.setDate(rs.getString("date").substring(0, 10));
	    	model.setRemarks(rs.getString("remarks"));
	    	model.setEmpId(rs.getInt("empId"));
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   
	}
	
	public List<MedicareShareAllowance> getMedicareShareAllowanceListByDateRange(String dateFrom, String dateTo) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();   
    	
    	
    	sql.append("SELECT e.empNo, (e.firstname + ' ' + e.lastname) as name, hp.medicareShareAllowanceId, hp.numDays, hp.ratePerDay, hp.netAmountDue, hp.date, hp.remarks, hp.empId ");	
    	sql.append("FROM medicareShareAllowance hp, employee e WHERE hp.empid = e.empid AND hp.date BETWEEN '");
    	sql.append(dateFrom);
    	sql.append("' AND '");
    	sql.append(dateTo);    	    	
    	sql.append("' ORDER BY name");
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<MedicareShareAllowance> list = new ArrayList<MedicareShareAllowance>();
	    
	    while (rs.next()) {	    	
	    	MedicareShareAllowance model = new MedicareShareAllowance();
	    	
	    	model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name"));
	    	model.setMedicareShareAllowanceId(rs.getInt("medicareShareAllowanceId"));
	    	model.setNumDays(rs.getInt("numDays"));
	    	model.setRatePerDay(rs.getBigDecimal("ratePerDay"));
	    	model.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
	    	model.setDate(rs.getString("date"));
	    	model.setRemarks(rs.getString("remarks"));
	    	model.setEmpId(rs.getInt("empId"));
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   
	}

	public void add(MedicareShareAllowance msa) throws SQLException, Exception {
		String sql = "insert into medicareShareAllowance "
				+ " (numDays, ratePerDay, netAmountDue, date, remarks, empId) "
				+ " values (?,?,?,?,?,?)";
		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, msa.getNumDays());
        ps.setBigDecimal(2, msa.getRatePerDay());
        ps.setBigDecimal(3, msa.getNetAmountDue());
        ps.setString(4, msa.getDate());
        ps.setString(5, msa.getRemarks());
        ps.setInt(6, msa.getEmpId());
        ps.executeUpdate();
          
        ResultSet keyResultSet = ps.getGeneratedKeys();
        int generatedAutoIncrementId = 0;
        if (keyResultSet.next()) {
        	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
        	msa.setMedicareShareAllowanceId(generatedAutoIncrementId);
        	conn.commit();
        }

        keyResultSet.close();
        ps.close();
        conn.close();
	}

	public void update(MedicareShareAllowance msa) throws SQLException, Exception {
		String sql = "update medicareShareAllowance set "
				+ " numDays=?, ratePerDay=?, netAmountDue=?, date=?, remarks=?, empId=? "
				+ " where medicareShareAllowanceId=?";
		PreparedStatement ps  = conn.prepareStatement(sql);
		ps.setInt(1, msa.getNumDays());
        ps.setBigDecimal(2, msa.getRatePerDay());
        ps.setBigDecimal(3, msa.getNetAmountDue());
        ps.setString(4, msa.getDate());
        ps.setString(5, msa.getRemarks());
        ps.setInt(6, msa.getEmpId());
        ps.setInt(7, msa.getMedicareShareAllowanceId());
 		ps.executeUpdate();
 		
 		conn.commit();
 		ps.close();
 		conn.close();
	}

	public void delete(MedicareShareAllowance msa) throws SQLException, Exception {
		String sql = "delete from medicareShareAllowance where medicareShareAllowanceId=?";
		PreparedStatement ps  = conn.prepareStatement(sql);
		ps.setInt(1, msa.getMedicareShareAllowanceId());
        ps.executeUpdate();
        
        conn.commit();
        ps.close();
        conn.close();
	}
	
	public void closeConnection() throws SQLException, Exception {
		conn.close();
	}
	
	public int[] batchUpdate(List<MedicareShareAllowance> msaList) throws SQLException, Exception {
		int[] result = null;
		String sql = "update medicareShareAllowance set "
				+ " numDays=?, ratePerDay=?, netAmountDue=?, date=?, remarks=?, empId=? "
				+ " where medicareShareAllowanceId=?";
		PreparedStatement ps  = conn.prepareStatement(sql);
		for (int i = 0; i < msaList.size(); i++) {
			MedicareShareAllowance msa = msaList.get(i);
			ps.setInt(1, msa.getNumDays());
			ps.setBigDecimal(2, msa.getRatePerDay());
			ps.setBigDecimal(3, msa.getNetAmountDue());
			ps.setString(4, msa.getDate());
			ps.setString(5, msa.getRemarks());
			ps.setInt(6, msa.getEmpId());
			ps.setInt(7, msa.getMedicareShareAllowanceId());
			ps.addBatch();
		}
 		result = ps.executeBatch();
 		
 		conn.commit();
 		ps.close();
		return result;
	}
	
    public int[] batchInsert(List<MedicareShareAllowance> msaList) throws SQLException, Exception {
    	int[] result = null;
    	String sql = "insert into medicareShareAllowance "
				+ " (numDays, ratePerDay, netAmountDue, date, remarks, empId) "
				+ " values (?,?,?,?,?,?)";
		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < msaList.size(); i++) {
        	MedicareShareAllowance msa = msaList.get(i);
        	ps.setInt(1, msa.getNumDays());
        	ps.setBigDecimal(2, msa.getRatePerDay());
        	ps.setBigDecimal(3, msa.getNetAmountDue());
        	ps.setString(4, msa.getDate());
        	ps.setString(5, msa.getRemarks());
        	ps.setInt(6, msa.getEmpId());
        	ps.addBatch();
        }
        result = ps.executeBatch();
        conn.commit();
        ps.close();
		return result;
    }
    
    public List<MedicareShareAllowance> getAllByEmployeeId(int empId) throws SQLException, Exception {
    	List<MedicareShareAllowance> resultList = new ArrayList<MedicareShareAllowance>();
    	String sql = "select * from payrollPeriod where employeeId = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, empId);
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			MedicareShareAllowance msa = new MedicareShareAllowance();
			msa.setMedicareShareAllowanceId(rs.getInt("medicareShareAllowanceId"));
			msa.setNumDays(rs.getInt("numDays"));
			msa.setRatePerDay(rs.getBigDecimal("ratePerDay"));
			msa.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
			msa.setDate(rs.getString("date"));
			msa.setRemarks(rs.getString("remarks"));
			msa.setEmpId(rs.getInt("empId"));
		}
		rs.close();
		conn.close();
    	return resultList;
    }
    
    public List<MedicareShareAllowance> getAllByJobTitleId(int jtId) throws SQLException, Exception {
    	List<MedicareShareAllowance> resultList = new ArrayList<MedicareShareAllowance>();
    	String sql = "select * from payrollPeriod "
    			+ " where employeeId in "
    			+ " (select empId from Employee where jobTitleId = ?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, jtId);
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			MedicareShareAllowance msa = new MedicareShareAllowance();
			msa.setMedicareShareAllowanceId(rs.getInt("medicareShareAllowanceId"));
			msa.setNumDays(rs.getInt("numDays"));
			msa.setRatePerDay(rs.getBigDecimal("ratePerDay"));
			msa.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
			msa.setDate(rs.getString("date"));
			msa.setRemarks(rs.getString("remarks"));
			msa.setEmpId(rs.getInt("empId"));
		}
		rs.close();
		conn.close();
    	return resultList;
    }
}
