package dai.hris.dao.payroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dai.hris.dao.DBConstants;
import dai.hris.model.YearEndBonusCashGift;

public class YearEndBonusCashGiftDAO {

	private Connection conn = null;

	public YearEndBonusCashGiftDAO() {    	
		try {
			/* MS SQL CODE */    		
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
			conn.setAutoCommit(false);
		} catch (SQLException sqle) {
			System.out.println("YearEndBonusCashGiftDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("YearEndBonusCashGiftDAO :" + e.getMessage());
		}
	}
	
	public void saveYearEndBonusCashGift(YearEndBonusCashGift model) throws SQLException, Exception {
		add(model);
	}
	
	public void saveOrUpdate(YearEndBonusCashGift model) throws SQLException, Exception {
		if (model.getYearEndBonusId() > 0) {
			update(model);
		} else {
			add(model);
		}
	}
	
	public int getCount(int empId) throws SQLException, Exception {
		int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM yearEndBonusCashGift WHERE empId = ");
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
	
	public List<YearEndBonusCashGift> getYearEndBonusCashGiftListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "year ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT hp.yearEndBonusId, hp.salaryGrade, hp.basicSalary, hp.netAmountDue, hp.year, hp.STEP, hp.cashGift, hp.total, ");
    	sql.append("hp.basicSalaryOct31, hp.cashGift1, hp.firstHalf13thMonth, hp.firstHalfCashGift, hp.secondHalf13thMonth, hp.secondHalfCashGift, hp.totalYearEndBonusCashGift, ");
    	sql.append("hp.eamcCoopDeduction, hp.empId, e.empNo, e.firstname, e.lastname, ROW_NUMBER() OVER(ORDER BY ");		
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM yearEndBonusCashGift hp, employee e WHERE hp.empId = e.empId AND hp.empId =  ");
    	sql.append(empId);
    	sql.append(" ) SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<YearEndBonusCashGift> list = new ArrayList<YearEndBonusCashGift>();
	    
	    while (rs.next()) {	    	
	    	YearEndBonusCashGift model = new YearEndBonusCashGift();
	    	
	    	model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("firstname") + " " + rs.getString("lastname"));
	    	model.setYearEndBonusId(rs.getInt("yearEndBonusId"));
	    	model.setSalaryGrade(rs.getInt("salaryGrade"));
	    	model.setSTEP(rs.getBigDecimal("STEP"));
	    	model.setBasicSalary(rs.getBigDecimal("basicSalary"));
	    	model.setCashGift(rs.getBigDecimal("cashGift"));
	    	model.setTotal(rs.getBigDecimal("total")); 
	    	model.setBasicSalaryOct31(rs.getBigDecimal("basicSalaryOct31"));
	    	model.setCashGift1(rs.getBigDecimal("cashGift1"));
	    	model.setFirstHalf13thMonth(rs.getBigDecimal("firstHalf13thMonth"));
	    	model.setFirstHalfCashGift(rs.getBigDecimal("firstHalfCashGift"));
	    	model.setSecondHalf13thMonth(rs.getBigDecimal("secondHalf13thMonth"));      
    		model.setSecondHalfCashGift(rs.getBigDecimal("secondHalfCashGift"));       
    		model.setTotalYearEndBonusCashGift(rs.getBigDecimal("totalYearEndBonusCashGift"));
    		model.setEamcCoopDeduction(rs.getBigDecimal("eamcCoopDeduction"));
    		model.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
    		model.setYear(rs.getInt("year"));
    		model.setEmpId(rs.getInt("empId"));
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   
	}
	
	public List<YearEndBonusCashGift> getYearEndBonusCashGiftListByDateRange(String year) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();   
    	
    	
    	sql.append("SELECT e.empNo, (e.firstname + ' ' + e.lastname) as name, hp.yearEndBonusId, hp.salaryGrade, hp.basicSalary, hp.netAmountDue, hp.year, hp.STEP, hp.cashGift, hp.total, ");	
    	sql.append("hp.basicSalaryOct31, hp.cashGift1, hp.firstHalf13thMonth, hp.firstHalfCashGift, hp.secondHalf13thMonth, hp.secondHalfCashGift, hp.totalYearEndBonusCashGift, ");
    	sql.append("hp.eamcCoopDeduction, hp.empId ");		
    	sql.append("FROM yearEndBonusCashGift hp, employee e WHERE hp.empid = e.empid AND hp.year = ");
    	sql.append(year);  
    	sql.append(" ORDER BY name");
		
		System.out.println("SQL getLongevityPayListByDateRange: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<YearEndBonusCashGift> list = new ArrayList<YearEndBonusCashGift>();
	    
	    while (rs.next()) {	    	
	    	YearEndBonusCashGift model = new YearEndBonusCashGift();
	    	
	    	model.setEmpNo(rs.getString("empNo"));
			model.setEmpName(rs.getString("name"));
	    	model.setYearEndBonusId(rs.getInt("yearEndBonusId"));
	    	model.setSalaryGrade(rs.getInt("salaryGrade"));
	    	model.setSTEP(rs.getBigDecimal("STEP"));
	    	model.setBasicSalary(rs.getBigDecimal("basicSalary"));
	    	model.setCashGift(rs.getBigDecimal("cashGift"));
	    	model.setTotal(rs.getBigDecimal("total")); 
	    	model.setBasicSalaryOct31(rs.getBigDecimal("basicSalaryOct31"));
	    	model.setCashGift1(rs.getBigDecimal("cashGift1"));
	    	model.setFirstHalf13thMonth(rs.getBigDecimal("firstHalf13thMonth"));
	    	model.setFirstHalfCashGift(rs.getBigDecimal("firstHalfCashGift"));
	    	model.setSecondHalf13thMonth(rs.getBigDecimal("secondHalf13thMonth"));      
    		model.setSecondHalfCashGift(rs.getBigDecimal("secondHalfCashGift"));       
    		model.setTotalYearEndBonusCashGift(rs.getBigDecimal("totalYearEndBonusCashGift"));
    		model.setEamcCoopDeduction(rs.getBigDecimal("eamcCoopDeduction"));
    		model.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
    		model.setYear(rs.getInt("year"));
    		model.setEmpId(rs.getInt("empId"));
    		
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   
	}

	public void add(YearEndBonusCashGift bonus) throws SQLException, Exception {
		String sql = "insert into yearEndBonusCashGift "
				+ " (salaryGrade, STEP, basicSalary, cashGift, total, basicSalaryOct31, cashGift1, "
				+ " firstHalf13thMonth, firstHalfCashGift, secondHalf13thMonth, secondHalfCashGift, "
				+ " totalYearEndBonusCashGift, eamcCoopDeduction, netAmountDue, year, empId) "
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, bonus.getSalaryGrade());
        ps.setBigDecimal(2, bonus.getSTEP());
        ps.setBigDecimal(3, bonus.getBasicSalary());
        ps.setBigDecimal(4, bonus.getCashGift());
        ps.setBigDecimal(5, bonus.getTotal());
        ps.setBigDecimal(6, bonus.getBasicSalaryOct31());
        ps.setBigDecimal(7, bonus.getCashGift1());
        ps.setBigDecimal(8, bonus.getFirstHalf13thMonth());
        ps.setBigDecimal(9, bonus.getFirstHalfCashGift());
        ps.setBigDecimal(10, bonus.getSecondHalf13thMonth());
        ps.setBigDecimal(11, bonus.getSecondHalfCashGift());
        ps.setBigDecimal(12, bonus.getTotalYearEndBonusCashGift());
        ps.setBigDecimal(13, bonus.getEamcCoopDeduction());
        ps.setBigDecimal(14, bonus.getNetAmountDue());
        ps.setInt(15, bonus.getYear());
        ps.setInt(16, bonus.getEmpId());
        ps.executeUpdate();
          
        ResultSet keyResultSet = ps.getGeneratedKeys();
        int generatedAutoIncrementId = 0;
        if (keyResultSet.next()) {
        	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
        	bonus.setYearEndBonusId(generatedAutoIncrementId);
        	conn.commit();
        }

        ps.close();
        keyResultSet.close();
        conn.close();
	}

	public void update(YearEndBonusCashGift bonus) throws SQLException, Exception {
		String sql = "update yearEndBonusCashGift set "
				+ " salaryGrade=?, STEP=?, basicSalary=?, cashGift=?, total=?, basicSalaryOct31=?, cashGift1=?, "
				+ " firstHalf13thMonth=?, firstHalfCashGift=?, secondHalf13thMonth=?, secondHalfCashGift=?, "
				+ " totalYearEndBonusCashGift=?, eamcCoopDeduction=?, netAmountDue=?, year=?, empId=? "
				+ " where yearEndBonusId=?";
		PreparedStatement ps  = conn.prepareStatement(sql);
		ps.setInt(1, bonus.getSalaryGrade());
        ps.setBigDecimal(2, bonus.getSTEP());
        ps.setBigDecimal(3, bonus.getBasicSalary());
        ps.setBigDecimal(4, bonus.getCashGift());
        ps.setBigDecimal(5, bonus.getTotal());
        ps.setBigDecimal(6, bonus.getBasicSalaryOct31());
        ps.setBigDecimal(7, bonus.getCashGift1());
        ps.setBigDecimal(8, bonus.getFirstHalf13thMonth());
        ps.setBigDecimal(9, bonus.getFirstHalfCashGift());
        ps.setBigDecimal(10, bonus.getSecondHalf13thMonth());
        ps.setBigDecimal(11, bonus.getSecondHalfCashGift());
        ps.setBigDecimal(12, bonus.getTotalYearEndBonusCashGift());
        ps.setBigDecimal(13, bonus.getEamcCoopDeduction());
        ps.setBigDecimal(14, bonus.getNetAmountDue());
        ps.setInt(15, bonus.getYear());
        ps.setInt(16, bonus.getEmpId());
        ps.setInt(17, bonus.getYearEndBonusId());
 		ps.executeUpdate();
 		
 		conn.commit();
 		ps.close();
 		conn.close();
	}

	public void delete(YearEndBonusCashGift bonus) throws SQLException, Exception {
		String sql = "delete from yearEndBonusCashGift where yearEndBonusId=?";
		PreparedStatement ps  = conn.prepareStatement(sql);
		ps.setInt(1, bonus.getYearEndBonusId());
        ps.executeUpdate();
        
        conn.commit();
        ps.close();
        conn.close();
	}
	
	public void closeConnection() throws SQLException, Exception {
		conn.close();
	}
	
	public int[] batchUpdate(List<YearEndBonusCashGift> yebList) throws SQLException, Exception {
		int[] result = null;
		String sql = "update yearEndBonusCashGift set "
				+ " salaryGrade=?, STEP=?, basicSalary=?, cashGift=?, total=?, basicSalaryOct31=?, cashGift1=?, "
				+ " firstHalf13thMonth=?, firstHalfCashGift=?, secondHalf13thMonth=?, secondHalfCashGift=?, "
				+ " totalYearEndBonusCashGift=?, eamcCoopDeduction=?, netAmountDue=?, year=?, empId=? "
				+ " where yearEndBonusId=?";
		PreparedStatement ps  = conn.prepareStatement(sql);
		for (int i = 0; i < yebList.size(); i++) {
			YearEndBonusCashGift bonus = yebList.get(i);
			ps.setInt(1, bonus.getSalaryGrade());
			ps.setBigDecimal(2, bonus.getSTEP());
			ps.setBigDecimal(3, bonus.getBasicSalary());
			ps.setBigDecimal(4, bonus.getCashGift());
			ps.setBigDecimal(5, bonus.getTotal());
			ps.setBigDecimal(6, bonus.getBasicSalaryOct31());
			ps.setBigDecimal(7, bonus.getCashGift1());
			ps.setBigDecimal(8, bonus.getFirstHalf13thMonth());
			ps.setBigDecimal(9, bonus.getFirstHalfCashGift());
			ps.setBigDecimal(10, bonus.getSecondHalf13thMonth());
			ps.setBigDecimal(11, bonus.getSecondHalfCashGift());
			ps.setBigDecimal(12, bonus.getTotalYearEndBonusCashGift());
			ps.setBigDecimal(13, bonus.getEamcCoopDeduction());
			ps.setBigDecimal(14, bonus.getNetAmountDue());
			ps.setInt(15, bonus.getYear());
			ps.setInt(16, bonus.getEmpId());
			ps.setInt(17, bonus.getYearEndBonusId());
			ps.addBatch();
		}
		result = ps.executeBatch();
		conn.commit();
		ps.close();
		return result;
	}
	
    public int[] batchInsert(List<YearEndBonusCashGift> yebList) throws SQLException, Exception {
    	int[] result = null;
    	String sql = "insert into yearEndBonusCashGift "
				+ " (salaryGrade, STEP, basicSalary, cashGift, total, basicSalaryOct31, cashGift1, "
				+ " firstHalf13thMonth, firstHalfCashGift, secondHalf13thMonth, secondHalfCashGift, "
				+ " totalYearEndBonusCashGift, eamcCoopDeduction, netAmountDue, year, empId) "
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < yebList.size(); i++) {
        	YearEndBonusCashGift bonus = yebList.get(i);
        	ps.setInt(1, bonus.getSalaryGrade());
        	ps.setBigDecimal(2, bonus.getSTEP());
        	ps.setBigDecimal(3, bonus.getBasicSalary());
        	ps.setBigDecimal(4, bonus.getCashGift());
        	ps.setBigDecimal(5, bonus.getTotal());
        	ps.setBigDecimal(6, bonus.getBasicSalaryOct31());
        	ps.setBigDecimal(7, bonus.getCashGift1());
        	ps.setBigDecimal(8, bonus.getFirstHalf13thMonth());
        	ps.setBigDecimal(9, bonus.getFirstHalfCashGift());
        	ps.setBigDecimal(10, bonus.getSecondHalf13thMonth());
        	ps.setBigDecimal(11, bonus.getSecondHalfCashGift());
        	ps.setBigDecimal(12, bonus.getTotalYearEndBonusCashGift());
        	ps.setBigDecimal(13, bonus.getEamcCoopDeduction());
        	ps.setBigDecimal(14, bonus.getNetAmountDue());
        	ps.setInt(15, bonus.getYear());
        	ps.setInt(16, bonus.getEmpId());
        	ps.addBatch();
    }
        result = ps.executeBatch();
		conn.commit();
		ps.close();
		return result;
    }
    
    public List<YearEndBonusCashGift> getAllByEmployeeId(int empId) throws SQLException, Exception {
    	List<YearEndBonusCashGift> resultList = new ArrayList<YearEndBonusCashGift>();
    	String sql = "select * from yearEndBonusCashGift where empId = ?";
    	PreparedStatement ps = conn.prepareStatement(sql);
    	ps.setInt(1, empId);
    	
    	ResultSet rs = ps.executeQuery();
    	while (rs.next()) {
    		YearEndBonusCashGift yeb = new YearEndBonusCashGift();
    		yeb.setYearEndBonusId(rs.getInt("yearEndBonusCashGiftId"));
    		yeb.setSalaryGrade(rs.getInt("salaryGrade"));
    		yeb.setSTEP(rs.getBigDecimal("STEP"));
    		yeb.setBasicSalary(rs.getBigDecimal("basicSalary"));
    		yeb.setCashGift(rs.getBigDecimal("cashGift"));
    		yeb.setTotal(rs.getBigDecimal("total")); 
    		yeb.setBasicSalaryOct31(rs.getBigDecimal("basicSalaryOct31"));
    		yeb.setCashGift1(rs.getBigDecimal("cashGift1"));
    		yeb.setFirstHalf13thMonth(rs.getBigDecimal("firstHalf13thMonth"));
    		yeb.setFirstHalfCashGift(rs.getBigDecimal("firstHalfCashGift"));
    		yeb.setSecondHalf13thMonth(rs.getBigDecimal("secondHalf13thMonth"));      
    		yeb.setSecondHalfCashGift(rs.getBigDecimal("secondHalfCashGift"));       
    		yeb.setTotalYearEndBonusCashGift(rs.getBigDecimal("totalYearEndBonusCashGift"));
    		yeb.setEamcCoopDeduction(rs.getBigDecimal("eamcCoopDeduction"));
    		yeb.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
    		yeb.setYear(rs.getInt("year"));
    		yeb.setEmpId(rs.getInt("empId"));
    		resultList.add(yeb);
    	}
    	rs.close();
    	ps.close();
    	return resultList;
    }
    
    public List<YearEndBonusCashGift> getAllByJobTitleId(int jtId) throws SQLException, Exception {
    	List<YearEndBonusCashGift> resultList = new ArrayList<YearEndBonusCashGift>();
    	String sql = "select * from yearEndBonusCashGift "
    			+ " where empId in "
    			+ " (select empId from Employee where jobTitleId = ?)";
    	PreparedStatement ps = conn.prepareStatement(sql);
    	ps.setInt(1, jtId);
    	
    	ResultSet rs = ps.executeQuery();
    	while (rs.next()) {
    		YearEndBonusCashGift yeb = new YearEndBonusCashGift();
    		yeb.setYearEndBonusId(rs.getInt("yearEndBonusCashGiftId"));
    		yeb.setSalaryGrade(rs.getInt("salaryGrade"));
    		yeb.setSTEP(rs.getBigDecimal("STEP"));
    		yeb.setBasicSalary(rs.getBigDecimal("basicSalary"));
    		yeb.setCashGift(rs.getBigDecimal("cashGift"));
    		yeb.setTotal(rs.getBigDecimal("total")); 
    		yeb.setBasicSalaryOct31(rs.getBigDecimal("basicSalaryOct31"));
    		yeb.setCashGift1(rs.getBigDecimal("cashGift1"));
    		yeb.setFirstHalf13thMonth(rs.getBigDecimal("firstHalf13thMonth"));
    		yeb.setFirstHalfCashGift(rs.getBigDecimal("firstHalfCashGift"));
    		yeb.setSecondHalf13thMonth(rs.getBigDecimal("secondHalf13thMonth"));      
    		yeb.setSecondHalfCashGift(rs.getBigDecimal("secondHalfCashGift"));       
    		yeb.setTotalYearEndBonusCashGift(rs.getBigDecimal("totalYearEndBonusCashGift"));
    		yeb.setEamcCoopDeduction(rs.getBigDecimal("eamcCoopDeduction"));
    		yeb.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
    		yeb.setYear(rs.getInt("year"));
    		yeb.setEmpId(rs.getInt("empId"));
    		resultList.add(yeb);
    	}
    	rs.close();
    	ps.close();
    	return resultList;
    }

}
