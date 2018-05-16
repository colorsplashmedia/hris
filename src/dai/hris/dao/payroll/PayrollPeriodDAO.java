package dai.hris.dao.payroll;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import dai.hris.dao.DBConstants;
import dai.hris.model.PayrollPeriod;
import dai.hris.model.ProfessionalFee;
public class PayrollPeriodDAO {

	private Connection conn = null;

	public PayrollPeriodDAO() {    	
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
	
	public  int getCountWithFilter(String filter) throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM payrollPeriod WHERE payrollCode like '%"); 
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
    
    public  int getCount() throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM payrollPeriod");   	
    	  	
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
    
    public  List<PayrollPeriod> getAll(int jtStartIndex, int jtPageSize, String jtSorting, String payrollPeriodStatus) throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "fromDate ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT payrollPeriodId, payYear, payMonth, payrollType, fromDate, toDate, payDate, payrollCode, numWorkDays, payPeriod, lockedAt, createdAt, updatedAt, deductGovtFlag, isForSecondHalf, status, isContractual, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	
    	if("L".equals(payrollPeriodStatus)) {
    		sql.append(") AS RowNumber FROM payrollPeriod WHERE status = 'L' ) ");
    	} else {
    		sql.append(") AS RowNumber FROM payrollPeriod ) ");
    	}
    	
    	
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<PayrollPeriod> list = new ArrayList<PayrollPeriod>();
	    
	    while (rs.next()) {
	    	PayrollPeriod pp = new PayrollPeriod();
			pp.setPayrollPeriodId(rs.getInt("payrollPeriodId"));
			pp.setPayYear(rs.getInt("payYear"));
			pp.setPayMonth(rs.getInt("payMonth"));
			pp.setPayrollType(rs.getString("payrollType"));
			pp.setFromDate(rs.getDate("fromDate"));
			pp.setToDate(rs.getDate("toDate"));
			pp.setPayDate(rs.getDate("payDate"));
			pp.setPayrollCode(rs.getString("payrollCode"));
			pp.setNumWorkDays(rs.getInt("numWorkDays"));
			pp.setPayPeriod(rs.getString("payPeriod"));
			pp.setLockedAt(rs.getDate("lockedAt"));
			pp.setCreatedAt(rs.getDate("createdAt"));
			pp.setUpdatedAt(rs.getDate("updatedAt"));
			pp.setDeductGovtFlag(rs.getString("deductGovtFlag"));
			pp.setIsForSecondHalf(rs.getString("isForSecondHalf"));
			pp.setStatus(rs.getString("status"));
			pp.setIsContractual(rs.getString("isContractual"));
			
			switch (pp.getPayMonth()) {
	            case 1:  pp.setPayMonthStr("January");
	                     break;
	            case 2:  pp.setPayMonthStr("February");
	                     break;
	            case 3:  pp.setPayMonthStr("March");
	                     break;
	            case 4:  pp.setPayMonthStr("April");
	                     break;
	            case 5:  pp.setPayMonthStr("May");
	                     break;
	            case 6:  pp.setPayMonthStr("June");
	                     break;
	            case 7:  pp.setPayMonthStr("July");
	                     break;
	            case 8:  pp.setPayMonthStr("August");
	                     break;
	            case 9:  pp.setPayMonthStr("September");
	                     break;
	            case 10: pp.setPayMonthStr("October");
	                     break;
	            case 11: pp.setPayMonthStr("November");
	                     break;
	            case 12: pp.setPayMonthStr("December");
	                     break;
	            default: pp.setPayMonthStr("Invalid Month");
	                     break;
	        }
			
			if(pp.getPayrollType().equals("M")){
				pp.setPayrollTypeStr("Monthly");
			}
			
			if(pp.getPayrollType().equals("SM")){
				pp.setPayrollTypeStr("Semi Monthly");
			}
			
			if(pp.getPayrollType().equals("W")){
				pp.setPayrollTypeStr("Weekly");
			}
			
			list.add(pp);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    public List<PayrollPeriod> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter, String payrollPeriodStatus) throws SQLException, Exception {
    	
		StringBuffer sql = new StringBuffer(); 
		
		if(jtSorting == null){
    		jtSorting = "fromDate ASC";
    	}
		
		sql.append("WITH OrderedList AS (SELECT payrollPeriodId, payYear, payMonth, payrollType, fromDate, toDate, payDate, payrollCode, numWorkDays, payPeriod, lockedAt, createdAt, updatedAt, deductGovtFlag, isForSecondHalf, status, isContractual, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	
    	if("L".equals(payrollPeriodStatus)) {
    		sql.append(") AS RowNumber FROM payrollPeriod WHERE status = 'L' AND payrollCode like '%");
        	sql.append(filter);    	
        	sql.append("%') SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	} else {
    		sql.append(") AS RowNumber FROM payrollPeriod WHERE payrollCode like '%");
        	sql.append(filter);    	
        	sql.append("%') SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	}
    	
    	
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());
		
		

	    ResultSet rs = ps.executeQuery();
	    List<PayrollPeriod> list = new ArrayList<PayrollPeriod>();
	    
	    while (rs.next()) {
	    	PayrollPeriod pp = new PayrollPeriod();
			pp.setPayrollPeriodId(rs.getInt("payrollPeriodId"));
			pp.setPayYear(rs.getInt("payYear"));
			pp.setPayMonth(rs.getInt("payMonth"));
			pp.setPayrollType(rs.getString("payrollType"));
			pp.setFromDate(rs.getDate("fromDate"));
			pp.setToDate(rs.getDate("toDate"));
			pp.setPayDate(rs.getDate("payDate"));
			pp.setPayrollCode(rs.getString("payrollCode"));
			pp.setNumWorkDays(rs.getInt("numWorkDays"));
			pp.setPayPeriod(rs.getString("payPeriod"));
			pp.setLockedAt(rs.getDate("lockedAt"));
			pp.setCreatedAt(rs.getDate("createdAt"));
			pp.setUpdatedAt(rs.getDate("updatedAt"));
			pp.setDeductGovtFlag(rs.getString("deductGovtFlag"));
			pp.setIsForSecondHalf(rs.getString("isForSecondHalf"));
			pp.setStatus(rs.getString("status"));
			pp.setIsContractual(rs.getString("isContractual"));
			list.add(pp);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     

	}

	public int saveOrUpdate(PayrollPeriod payrollPeriod) throws SQLException {
		int res = -1;
		if (payrollPeriod.getPayrollPeriodId() > 0) {
			String sql = "update PayrollPeriod set "
					+ " payYear=?, isContractual=?, payMonth=?, payrollType=?, fromDate=?, toDate=?, payDate=?, payrollCode=?, numWorkDays=?, payPeriod=?, deductGovtFlag=?, isForSecondHalf=? "
					+ " where payrollPeriodId = ?";
			PreparedStatement ps  = conn.prepareStatement(sql);
			int index = 1;
			ps.setInt(index++, payrollPeriod.getPayYear());
			ps.setString(index++, payrollPeriod.getIsContractual());
	        ps.setInt(index++, payrollPeriod.getPayMonth());
	        ps.setString(index++, payrollPeriod.getPayrollType());
	        ps.setDate(index++, payrollPeriod.getFromDate());
	        ps.setDate(index++, payrollPeriod.getToDate());
	        ps.setDate(index++, payrollPeriod.getPayDate());
	        ps.setString(index++, payrollPeriod.getPayrollCode());
	        ps.setInt(index++, payrollPeriod.getNumWorkDays());
	        ps.setString(index++, payrollPeriod.getPayPeriod());
	        ps.setString(index++, payrollPeriod.getDeductGovtFlag().trim());
	        ps.setString(index++, payrollPeriod.getIsForSecondHalf());
	 		ps.setInt(index++, payrollPeriod.getPayrollPeriodId());
	 		res = ps.executeUpdate();
	 		conn.commit();
	 		ps.close();
		} else {
			
	 		String sql = "insert into PayrollPeriod "
					+ "(payYear, isContractual, payMonth, payrollType, fromDate, toDate, payDate, payrollCode, numWorkDays, payPeriod, deductGovtFlag, isForSecondHalf, createdAt) " 
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			int index = 1;
			ps.setInt(index++, payrollPeriod.getPayYear());
			ps.setString(index++, payrollPeriod.getIsContractual());
			ps.setInt(index++, payrollPeriod.getPayMonth());
			ps.setString(index++, payrollPeriod.getPayrollType());
			ps.setDate(index++, payrollPeriod.getFromDate());
			ps.setDate(index++, payrollPeriod.getToDate());
			ps.setDate(index++, payrollPeriod.getPayDate());
			ps.setString(index++, payrollPeriod.getPayrollCode());
			ps.setInt(index++, payrollPeriod.getNumWorkDays());
			ps.setString(index++, payrollPeriod.getPayPeriod());
			ps.setString(index++, payrollPeriod.getDeductGovtFlag().trim());
			ps.setString(index++, payrollPeriod.getIsForSecondHalf());
			ps.setDate(index++, new Date(System.currentTimeMillis()));
			res = ps.executeUpdate();
			ResultSet keyResultSet = ps.getGeneratedKeys();
			int generatedAutoIncrementId = 0;
			if (keyResultSet.next()) {
				generatedAutoIncrementId = (int) keyResultSet.getInt(1);
				payrollPeriod.setPayrollPeriodId(generatedAutoIncrementId);
				conn.commit();
			}
			keyResultSet.close();
			ps.close();
		}
		return res;
	}
	
	public List<PayrollPeriod> getPayrollRegisterByDateRange(String dateFrom, String dateTo) throws SQLException {
    	List<PayrollPeriod> resultList = new ArrayList<PayrollPeriod>();
    	StringBuffer sql = new StringBuffer();       	    	
    	
    	sql.append("SELECT e.empNo, (e.firstname + ' ' + e.lastname) as name, e.empNo, fr.professionalFeeId, fr.officialReceiptNumber, fr.officialReceiptDate, fr.grossAmount, fr.withHoldingTax, fr.finalTax, fr.netAmountDue, fr.patientId, fr.patientName, fr.remarks, fr.empId, fr.createdAt, e.firstname, e.lastname ");    	
    	sql.append(" FROM PayrollPeriod fr, employee e WHERE fr.empid = e.empid AND fr.officialReceiptDate BETWEEN '");
    	sql.append(dateFrom);
    	sql.append("' AND '");
    	sql.append(dateTo);    	    	
    	sql.append("' ORDER BY name");
    	
		System.out.println("SQL getPayrollRegisterByDateRange: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    
    	while(rs.next()) {
    		
    		PayrollPeriod pp = new PayrollPeriod();
			pp.setPayrollPeriodId(rs.getInt("payrollPeriodId"));
			pp.setPayYear(rs.getInt("payYear"));
			pp.setPayMonth(rs.getInt("payMonth"));
			pp.setPayrollType(rs.getString("payrollType"));
			pp.setFromDate(rs.getDate("fromDate"));
			pp.setToDate(rs.getDate("toDate"));
			pp.setPayDate(rs.getDate("payDate"));
			pp.setPayrollCode(rs.getString("payrollCode"));
			pp.setNumWorkDays(rs.getInt("numWorkDays"));
			pp.setPayPeriod(rs.getString("payPeriod"));
			pp.setLockedAt(rs.getDate("lockedAt"));
			pp.setCreatedAt(rs.getDate("createdAt"));
			pp.setUpdatedAt(rs.getDate("updatedAt"));
			pp.setDeductGovtFlag(rs.getString("deductGovtFlag"));
			pp.setIsForSecondHalf(rs.getString("isForSecondHalf"));
			pp.setStatus(rs.getString("status"));
			pp.setIsContractual(rs.getString("isContractual"));
    		
    		resultList.add(pp);
    	}
    	
    	
    	rs.close();
    	ps.close();
    	return resultList;
    }
	
//	public PayrollPeriod getLatestPayrollPeriodByEmpId(int empId) throws SQLException {
//		PayrollPeriod res = null;
//		String sql = "SELECT * FROM PayrollPeriod where payrollPeriodId = ?";
//		PreparedStatement ps = conn.prepareStatement(sql);
//		ps.setInt(1, id);
//		ResultSet rs = ps.executeQuery();
//		if (rs.next()) {
//			res = new PayrollPeriod();
//			res.setPayrollPeriodId(id);
//			res.setPayYear(rs.getInt("payYear"));
//			res.setPayMonth(rs.getInt("payMonth"));
//			res.setPayrollType(rs.getString("payrollType"));
//			res.setFromDate(rs.getDate("fromDate"));
//			res.setToDate(rs.getDate("toDate"));
//			res.setPayDate(rs.getDate("payDate"));
//			res.setPayrollCode(rs.getString("payrollCode"));
//			res.setNumWorkDays(rs.getInt("numWorkDays"));
//			res.setPayPeriod(rs.getString("payPeriod"));
//			res.setCreatedAt(rs.getDate("createdAt"));
//			res.setUpdatedAt(rs.getDate("updatedAt"));
//			res.setDeductGovtFlag(rs.getString("deductGovtFlag"));
//			res.setIsForSecondHalf(rs.getString("isForSecondHalf"));
//			res.setStatus(rs.getString("status"));
//			res.setIsContractual(rs.getString("isContractual"));
//			
//			res.setPayPeriod(rs.getString("payPeriod2"));
//		}
//		conn.close();
//		return res;
//	}
	
	public PayrollPeriod getPayrollPeriodById(int id, String payrollPeriodStatus) throws SQLException {
		PayrollPeriod res = null;
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT *, (SUBSTRING(CONVERT(VARCHAR,fromDate), 1, 11) + ' To ' + SUBSTRING(CONVERT(VARCHAR,toDate), 1, 11)) as payPeriod2 FROM payrollPeriod WHERE payrollPeriodId = ");
		sql.append(id);
		if("L".equals(payrollPeriodStatus)){
			sql.append(" AND status = 'L'");
		}		
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			res = new PayrollPeriod();
			res.setPayrollPeriodId(id);
			res.setPayYear(rs.getInt("payYear"));
			res.setPayMonth(rs.getInt("payMonth"));
			res.setPayrollType(rs.getString("payrollType"));
			res.setFromDate(rs.getDate("fromDate"));
			res.setToDate(rs.getDate("toDate"));
			res.setPayDate(rs.getDate("payDate"));
			res.setPayrollCode(rs.getString("payrollCode"));
			res.setNumWorkDays(rs.getInt("numWorkDays"));
			res.setPayPeriod(rs.getString("payPeriod"));
			res.setCreatedAt(rs.getDate("createdAt"));
			res.setUpdatedAt(rs.getDate("updatedAt"));
			res.setDeductGovtFlag(rs.getString("deductGovtFlag"));
			res.setIsForSecondHalf(rs.getString("isForSecondHalf"));
			res.setStatus(rs.getString("status"));
			res.setIsContractual(rs.getString("isContractual"));
			
			res.setPayPeriod(rs.getString("payPeriod2"));
		}
		conn.close();
		return res;
	}
	
	public List<PayrollPeriod> getAll() throws SQLException {
		List<PayrollPeriod> list = new ArrayList<PayrollPeriod>();
		String sql = "select * from PayrollPeriod";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			PayrollPeriod pp = new PayrollPeriod();
			pp.setPayrollPeriodId(rs.getInt("payrollPeriodId"));
			pp.setPayYear(rs.getInt("payYear"));
			pp.setPayMonth(rs.getInt("payMonth"));
			pp.setPayrollType(rs.getString("payrollType"));
			pp.setFromDate(rs.getDate("fromDate"));
			pp.setToDate(rs.getDate("toDate"));
			pp.setPayDate(rs.getDate("payDate"));
			pp.setPayrollCode(rs.getString("payrollCode"));
			pp.setNumWorkDays(rs.getInt("numWorkDays"));
			pp.setPayPeriod(rs.getString("payPeriod"));
			pp.setLockedAt(rs.getDate("lockedAt"));
			pp.setCreatedAt(rs.getDate("createdAt"));
			pp.setUpdatedAt(rs.getDate("updatedAt"));
			pp.setDeductGovtFlag(rs.getString("deductGovtFlag"));
			pp.setIsForSecondHalf(rs.getString("isForSecondHalf"));
			pp.setStatus(rs.getString("status"));
			pp.setIsContractual(rs.getString("isContractual"));
			list.add(pp);
		}
		conn.close();
		//The Java 8 way of doing this is to use List.sort as follows:
		//personList.sort(Comparator.comparing(Person::getName));
		list.sort(Comparator.comparing(PayrollPeriod::getCreatedAt));
		System.out.println(list.size());
		return list;
	}
	
	public void closeConnection() throws SQLException {
		conn.close();
	}
	
	public int[] batchUpdate(List<PayrollPeriod> ppList) throws SQLException {
		int[] result = null;
		String sql = "update PayrollPeriod set "
				+ " payYear=?, isContractual=?, payMonth=?, payrollType=?, fromDate=?, toDate=?, payDate=?, payrollCode=?, numWorkDays=?, payPeriod=?, deductGovtFlag=?, isForSecondHalf=?"
				+ " where payrollPeriodId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
		for (int i = 0; i < ppList.size(); i++) {
			int index = 1;
			PayrollPeriod payrollPeriod = ppList.get(i);
			ps.setInt(index++, payrollPeriod.getPayYear());
			ps.setString(index++, payrollPeriod.getIsContractual());
			ps.setInt(index++, payrollPeriod.getPayMonth());
			ps.setString(index++, payrollPeriod.getPayrollType());
			ps.setDate(index++, payrollPeriod.getFromDate());
			ps.setDate(index++, payrollPeriod.getToDate());
			ps.setDate(index++, payrollPeriod.getPayDate());
			ps.setString(index++, payrollPeriod.getPayrollCode());
			ps.setInt(index++, payrollPeriod.getNumWorkDays());
			ps.setString(index++, payrollPeriod.getPayPeriod());
			ps.setString(index++, payrollPeriod.getDeductGovtFlag().trim());
			ps.setString(index++, payrollPeriod.getIsForSecondHalf());
			ps.setInt(index++, payrollPeriod.getPayrollPeriodId());
			ps.addBatch();
		}
 		result = ps.executeBatch();
 		
 		conn.commit();
 		ps.close();
		return result;
	}
	
    public int[] batchInsert(List<PayrollPeriod> ppList) throws SQLException {
    	int[] result = null;
    	String sql = "insert into PayrollPeriod "
				+ "(payYear, isContractual, payMonth, payrollType, fromDate, toDate, payDate, payrollCode, numWorkDays, deductGovtFlag, payPeriod, isForSecondHalf, payPeriod) " 
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		for (int i = 0; i < ppList.size(); i++) {
			int index = 1;
			PayrollPeriod payrollPeriod = ppList.get(i);
			ps.setInt(index++, payrollPeriod.getPayYear());
			ps.setString(index++, payrollPeriod.getIsContractual());
			ps.setInt(index++, payrollPeriod.getPayMonth());
			ps.setString(index++, payrollPeriod.getPayrollType());
			ps.setDate(index++, payrollPeriod.getFromDate());
			ps.setDate(index++, payrollPeriod.getToDate());
			ps.setDate(index++, payrollPeriod.getPayDate());
			ps.setString(index++, payrollPeriod.getPayrollCode());
			ps.setInt(index++, payrollPeriod.getNumWorkDays());
			ps.setString(index++, payrollPeriod.getDeductGovtFlag().trim());
			ps.setString(index++, payrollPeriod.getIsForSecondHalf());
			ps.setString(index++, payrollPeriod.getPayPeriod());
			ps.addBatch();
		}
 		result = ps.executeBatch();
 		conn.commit();
 		ps.close();
		return result;
    }
    
    
    public ArrayList<PayrollPeriod> getAll(boolean includeLocked) throws SQLException{
    	ArrayList<PayrollPeriod> result = new ArrayList<PayrollPeriod>();
		//include locked = all
		String sql = "SELECT * FROM payrollPeriod ORDER BY fromDate";
		if (includeLocked == false) {
			sql = "SELECT * FROM payrollPeriod WHERE lockedAt is NULL ORDER BY fromDate";
		}

		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			PayrollPeriod pp = new PayrollPeriod();
			pp.setPayrollPeriodId(rs.getInt("payrollPeriodId"));
			pp.setPayYear(rs.getInt("payYear"));
			pp.setPayMonth(rs.getInt("payMonth"));
			pp.setPayrollType(rs.getString("payrollType"));
			pp.setFromDate(rs.getDate("fromDate"));
			pp.setToDate(rs.getDate("toDate"));
			pp.setPayDate(rs.getDate("payDate"));
			pp.setPayrollCode(rs.getString("payrollCode"));
			pp.setNumWorkDays(rs.getInt("numWorkDays"));
			pp.setPayPeriod(rs.getString("payPeriod"));
			pp.setLockedAt(rs.getDate("lockedAt"));
			pp.setCreatedAt(rs.getDate("createdAt"));
			pp.setUpdatedAt(rs.getDate("updatedAt"));
			pp.setDeductGovtFlag(rs.getString("deductGovtFlag"));
			pp.setIsForSecondHalf(rs.getString("isForSecondHalf"));
			pp.setStatus(rs.getString("status"));
			pp.setIsContractual(rs.getString("isContractual"));
			result.add(pp);
		}
		conn.close();
		//result.sort(Comparator.comparing(PayrollPeriod::getCreatedAt));
		//System.out.println(result.size());
		return result;
    }
    
    
    public ArrayList<PayrollPeriod> getAllGenerated() throws SQLException{
    	ArrayList<PayrollPeriod> result = new ArrayList<PayrollPeriod>();
		String sql ="select * from PayrollPeriod where status = 'G' ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			PayrollPeriod pp = new PayrollPeriod();
			pp.setPayrollPeriodId(rs.getInt("payrollPeriodId"));
			pp.setPayYear(rs.getInt("payYear"));
			pp.setPayMonth(rs.getInt("payMonth"));
			pp.setPayrollType(rs.getString("payrollType"));
			pp.setFromDate(rs.getDate("fromDate"));
			pp.setToDate(rs.getDate("toDate"));
			pp.setPayDate(rs.getDate("payDate"));
			pp.setPayrollCode(rs.getString("payrollCode"));
			pp.setNumWorkDays(rs.getInt("numWorkDays"));
			pp.setPayPeriod(rs.getString("payPeriod"));
			pp.setLockedAt(rs.getDate("lockedAt"));
			pp.setCreatedAt(rs.getDate("createdAt"));
			pp.setUpdatedAt(rs.getDate("updatedAt"));
			pp.setDeductGovtFlag(rs.getString("deductGovtFlag"));
			pp.setIsForSecondHalf(rs.getString("isForSecondHalf"));
			pp.setStatus(rs.getString("status"));
			pp.setIsContractual(rs.getString("isContractual"));
			result.add(pp);
		}
		conn.close();
		result.sort(Comparator.comparing(PayrollPeriod::getCreatedAt));
		System.out.println(result.size());
		return result;
    }
    
    /*public List<PayrollPeriod> getAllByEmployeeId(int empId) throws SQLException {
		List<PayrollPeriod> resultList = new ArrayList<PayrollPeriod>();
		String sql = "select * from PayrollPeriod where employeeId = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, empId);
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			PayrollPeriod payrollPeriod = new PayrollPeriod();
			payrollPeriod.setPayrollPeriodId(rs.getInt("payrollPeriodId"));
			payrollPeriod.setPayYear(rs.getInt("payYear"));
			payrollPeriod.setPayMonth(rs.getInt("payMonth"));
			payrollPeriod.setPayrollType(rs.getString("payrollType"));
			payrollPeriod.setFromDate(rs.getDate("fromDate"));
			payrollPeriod.setToDate(rs.getDate("toDate"));
			payrollPeriod.setPayPeriod(rs.getString("payPeriod"));
			payrollPeriod.setPayDate(rs.getDate("payDate"));
			payrollPeriod.setPayrollCode(rs.getString("payrollCode"));
			resultList.add(payrollPeriod);
		}
		rs.close();
		return resultList;
	}
	
	public List<PayrollPeriod> getAllByJobTitleId(int jtId) throws SQLException {
    	List<PayrollPeriod> resultList = new ArrayList<PayrollPeriod>();
    	String sql = "select * from PayrollPeriod "
    			+ "where employeeId in "
    			+ " (select empId from Employee where jobTitleId = ?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, jtId);
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			PayrollPeriod payrollPeriod = new PayrollPeriod();
			payrollPeriod.setPayrollPeriodId(rs.getInt("payrollPeriodId"));
			payrollPeriod.setPayYear(rs.getInt("payYear"));
			payrollPeriod.setPayMonth(rs.getInt("payMonth"));
			payrollPeriod.setPayrollType(rs.getString("payrollType"));
			payrollPeriod.setFromDate(rs.getDate("fromDate"));
			payrollPeriod.setToDate(rs.getDate("toDate"));
			payrollPeriod.setPayPeriod(rs.getString("payPeriod"));
			payrollPeriod.setPayDate(rs.getDate("payDate"));
			payrollPeriod.setPayrollCode(rs.getString("payrollCode"));
			resultList.add(payrollPeriod);
		}
		rs.close();
    	return resultList;
    }*/
}
