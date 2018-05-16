package dai.hris.dao.payroll;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dai.hris.dao.DBConstants;
import dai.hris.model.CaseRatePayment;

public class CaseRatePaymentDAO {

	private Connection conn = null;

	public CaseRatePaymentDAO() {    	
		try {
			/* MS SQL CODE */    		
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
			conn.setAutoCommit(false);
		} catch (SQLException sqle) {
			System.out.println("CaseRatePaymentDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("CaseRatePaymentDAO :" + e.getMessage());
		}
	}
	
	public void saveCaseRatePayment(CaseRatePayment model) throws SQLException, Exception {
		String sql = "insert into caseRatePayment (officialReceiptDate, grossAmount, withHoldingTax, finalTax, netAmountDue, year, month, batch, remarks, empId, createdAt) "
				+ "values (?,?,?,?,?,?,?,?,?,?,SYSDATETIME())";
		
		PreparedStatement ps  = conn.prepareStatement(sql);
		int idx = 1;
		ps.setString(idx++, model.getOfficialReceiptDate());
        ps.setBigDecimal(idx++, model.getGrossAmount());
        ps.setBigDecimal(idx++, model.getWithHoldingTax());
        ps.setBigDecimal(idx++, model.getFinalTax());
        ps.setBigDecimal(idx++, model.getNetAmountDue());
        ps.setInt(idx++,  model.getYear());
        ps.setInt(idx++, model.getMonth());
        ps.setString(idx++, model.getBatch());
        ps.setString(idx++, model.getRemarks());
        ps.setInt(idx++, model.getEmpId());
        
        ps.executeUpdate();
          
        conn.commit(); 
    	
    	sql = null;
		ps.close();
	}
	
	public List<CaseRatePayment> getCaseRateListByDateRange(String month, String year) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();       	    	
    	
    	sql.append("SELECT e.empNo, (e.firstname + ' ' + e.lastname) as name, cr.caseRatePaymentId, cr.officialReceiptDate, cr.grossAmount, cr.withHoldingTax, cr.finalTax, cr.netAmountDue, cr.year, cr.month, cr.batch, cr.patientId, cr.patientName, cr.remarks, cr.empId, e.firstname, e.lastname ");    	
    	sql.append(" FROM caseRatePayment cr, employee e WHERE cr.empid = e.empid AND cr.month = ");
    	sql.append(month);
    	sql.append(" AND cr.year = ");
    	sql.append(year);    
    	sql.append(" ORDER BY name");
    	
		System.out.println("SQL getCaseRateListByDateRange: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<CaseRatePayment> list = new ArrayList<CaseRatePayment>();
	    
	    while (rs.next()) {
	    	
	    	CaseRatePayment crp =  new CaseRatePayment();
	    	
    		crp.setCaseRatePaymentId(rs.getInt("caseRatePaymentId"));
    		crp.setOfficialReceiptDate(rs.getString("officialReceiptDate"));
    		crp.setGrossAmount(rs.getBigDecimal("grossAmount"));
    		crp.setWithHoldingTax(rs.getBigDecimal("withHoldingTax"));
    		crp.setFinalTax(rs.getBigDecimal("finalTax"));
    		crp.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
    		crp.setYear(rs.getInt("year"));
    		crp.setMonth(rs.getInt("month"));
    		crp.setBatch(rs.getString("batch"));
    		crp.setEmpName(rs.getString("name"));
    		crp.setEmpNo(rs.getString("empNo"));    		
    		crp.setEmpId(rs.getInt("empId"));
    		crp.setPatientName(rs.getString("patientName"));
    		crp.setPatientId(rs.getString("patientId"));
    		crp.setRemarks(rs.getString("remarks"));
    		
    		
    		
	    	 list.add(crp);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   
	}
	
	public List<CaseRatePayment> getCaseRatePaymentListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "grossAmount ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT e.empNo, cr.caseRatePaymentId, cr.officialReceiptDate, cr.grossAmount, cr.withHoldingTax, cr.finalTax, cr.netAmountDue, cr.year, cr.month, cr.batch, cr.patientId, cr.patientName, cr.remarks, cr.empId, e.firstname, e.lastname, ROW_NUMBER() OVER(ORDER BY ");		
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM caseRatePayment cr, employee e WHERE cr.empId = e.empId AND cr.empId =  ");
    	sql.append(empId);
    	sql.append(" ) SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL getCaseRatePaymentListByEmpId: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<CaseRatePayment> list = new ArrayList<CaseRatePayment>();
	    
	    while (rs.next()) {
	    	
	    	CaseRatePayment crp =  new CaseRatePayment();
	    	
    		crp.setCaseRatePaymentId(rs.getInt("caseRatePaymentId"));
    		crp.setOfficialReceiptDate(rs.getString("officialReceiptDate"));
    		crp.setGrossAmount(rs.getBigDecimal("grossAmount"));
    		crp.setWithHoldingTax(rs.getBigDecimal("withHoldingTax"));
    		crp.setFinalTax(rs.getBigDecimal("finalTax"));
    		crp.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
    		crp.setYear(rs.getInt("year"));
    		crp.setMonth(rs.getInt("month"));
    		crp.setBatch(rs.getString("batch"));
    		crp.setEmpName(rs.getString("firstname") + " " + rs.getString("lastname"));
    		crp.setEmpNo(rs.getString("empNo"));    		
    		crp.setEmpId(rs.getInt("empId"));
    		crp.setPatientName(rs.getString("patientName"));
    		crp.setPatientId(rs.getString("patientId"));
    		crp.setRemarks(rs.getString("remarks"));
    		crp.setEmpId(rs.getInt("empId"));
    		
	    	 list.add(crp);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   
	}

	public void saveOrUpdate(CaseRatePayment caseRatePayment) throws SQLException {
		if (caseRatePayment.getCaseRatePaymentId() > 0) {
			String sql = "update CaseRatePayment set "
					//+ " officialReceiptDate=?, grossAmount=?, withHoldingTax=?, finalTax=?, netAmountDue=?, year=?, month=?, batch=?, patientId=?, patientName=?, remarks=?, empId=?"
					+ " officialReceiptDate=?, grossAmount=?, withHoldingTax=?, finalTax=?, netAmountDue=?, year=?, month=?, batch=?, remarks=?, empId=?"
					+ " where caseRatePaymentId = ?";
			PreparedStatement ps  = conn.prepareStatement(sql);
			int idx = 1;
			ps.setString(idx++, caseRatePayment.getOfficialReceiptDate());
	        ps.setBigDecimal(idx++, caseRatePayment.getGrossAmount());
	        ps.setBigDecimal(idx++, caseRatePayment.getWithHoldingTax());
	        ps.setBigDecimal(idx++, caseRatePayment.getFinalTax());
	        ps.setBigDecimal(idx++, caseRatePayment.getNetAmountDue());
	        ps.setInt(idx++,  caseRatePayment.getYear());
	        ps.setInt(idx++, caseRatePayment.getMonth());
	        ps.setString(idx++, caseRatePayment.getBatch());
//	        ps.setString(idx++, caseRatePayment.getPatientId());
//	        ps.setString(idx++, caseRatePayment.getPatientName());
	        ps.setString(idx++, caseRatePayment.getRemarks());
	        ps.setInt(idx++, caseRatePayment.getEmpId());
	        ps.setInt(idx++, caseRatePayment.getCaseRatePaymentId());
	 		ps.executeUpdate();
	 		conn.commit();
	 		ps.close();
		} else {
			String sql = "insert into CaseRatePayment "
//					+ "(officialReceiptDate, grossAmount, withHoldingTax, finalTax, netAmountDue, year, month, batch, patientId, patientName, remarks, empId, createdAt) "
					+ "(officialReceiptDate, grossAmount, withHoldingTax, finalTax, netAmountDue, year, month, batch, remarks, empId, createdAt) "
					+ "values (?,?,?,?,?,?,?,?,?,?,SYSDATETIME())";
			PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			int idx = 1;
			ps.setString(idx++, caseRatePayment.getOfficialReceiptDate());
	        ps.setBigDecimal(idx++, caseRatePayment.getGrossAmount());
	        ps.setBigDecimal(idx++, caseRatePayment.getWithHoldingTax());
	        ps.setBigDecimal(idx++, caseRatePayment.getFinalTax());
	        ps.setBigDecimal(idx++, caseRatePayment.getNetAmountDue());
	        ps.setInt(idx++,  caseRatePayment.getYear());
	        ps.setInt(idx++, caseRatePayment.getMonth());
	        ps.setString(idx++, caseRatePayment.getBatch());
	        ps.setString(idx++, caseRatePayment.getRemarks());
	        ps.setInt(idx++, caseRatePayment.getEmpId());
	        
	        ps.executeUpdate();
	          
	        ResultSet keyResultSet = ps.getGeneratedKeys();
	        int generatedAutoIncrementId = 0;
	        if (keyResultSet.next()) {
	        	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
	        	caseRatePayment.setCaseRatePaymentId(generatedAutoIncrementId);
	        	conn.commit();
	        }

	        keyResultSet.close();
	        ps.close();
		}
	}

	public void closeConnection() throws SQLException {
		conn.close();
	}
	
	public  int getCount(int empId) throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM CaseRatePayment WHERE empId = ");
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
	
	public List<CaseRatePayment> getAllByEmployeeId(int empId) throws SQLException {
    	List<CaseRatePayment> resultList = new ArrayList<CaseRatePayment>();
    	String sql = "select cr.patientId, cr.patientName, cr.caseRatePaymentId, cr.officialReceiptDate, cr.grossAmount, cr.withHoldingTax, cr.finalTax, cr.netAmountDue, cr.year, cr.month, cr.batch, cr.empId, e.firstname, e.lastname, e.empNo, cr.remarks  from caseRatePayment cr, employee e  where cr.empId = e.empId AND cr.empId = ?";
    	PreparedStatement ps = conn.prepareStatement(sql);
    	ps.setInt(1, empId);
    	
    	ResultSet rs = ps.executeQuery();
    	while (rs.next()) {
    		CaseRatePayment crp =  new CaseRatePayment();
    		crp.setCaseRatePaymentId(rs.getInt("caseRatePaymentId"));
    		crp.setOfficialReceiptDate(rs.getString("officialReceiptDate"));
    		crp.setGrossAmount(rs.getBigDecimal("grossAmount"));
    		crp.setWithHoldingTax(rs.getBigDecimal("withHoldingTax"));
    		crp.setFinalTax(rs.getBigDecimal("finalTax"));
    		crp.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
    		crp.setYear(rs.getInt("year"));
    		crp.setMonth(rs.getInt("month"));
    		crp.setBatch(rs.getString("batch"));
    		crp.setEmpName(rs.getString("firstname") + " " + rs.getString("lastname"));
    		crp.setEmpNo(rs.getString("empNo"));    		
    		crp.setEmpId(rs.getInt("empId"));
    		crp.setPatientName(rs.getString("patientName"));
    		crp.setPatientId(rs.getString("patientId"));
    		crp.setRemarks(rs.getString("remarks"));
    		
    		resultList.add(crp);
    	}
    	rs.close();
    	ps.close();
    	return resultList;
    }
    
	public List<CaseRatePayment> getAllByEmpIdAndOrDateRange(int empId, Date from, Date to) throws SQLException {
    	List<CaseRatePayment> resultList = new ArrayList<CaseRatePayment>();
    	String sql = "select cr.patientId, cr.patientName, cr.caseRatePaymentId, cr.officialReceiptDate, cr.grossAmount, cr.withHoldingTax, cr.finalTax, cr.netAmountDue, cr.year, cr.month, cr.batch, cr.empId, e.firstname, e.lastname, e.empNo, cr.remarks  from caseRatePayment cr, employee e  where cr.empId = e.empId AND cr.empId=? and officialReceiptDate between ? and ? ";
    	PreparedStatement ps = conn.prepareStatement(sql);
    	ps.setInt(1, empId);
    	ps.setDate(2, from);
    	ps.setDate(3, to);
    	ResultSet rs = ps.executeQuery();
    	while(rs.next()) {
    		CaseRatePayment crp =  new CaseRatePayment();
    		crp.setCaseRatePaymentId(rs.getInt("caseRatePaymentId"));
    		crp.setOfficialReceiptDate(rs.getString("officialReceiptDate"));
    		crp.setGrossAmount(rs.getBigDecimal("grossAmount"));
    		crp.setWithHoldingTax(rs.getBigDecimal("withHoldingTax"));
    		crp.setFinalTax(rs.getBigDecimal("finalTax"));
    		crp.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
    		crp.setYear(rs.getInt("year"));
    		crp.setMonth(rs.getInt("month"));
    		crp.setBatch(rs.getString("batch"));    		
    		crp.setRemarks(rs.getString("remarks"));
    		crp.setEmpName(rs.getString("firstname") + " " + rs.getString("lastname"));
    		crp.setEmpNo(rs.getString("empNo"));    		
    		crp.setEmpId(rs.getInt("empId"));
    		crp.setPatientName(rs.getString("patientName"));
    		crp.setPatientId(rs.getString("patientId"));
    		resultList.add(crp);
    	}
    	rs.close();
    	ps.close();
    	return resultList;
    }
    
}
