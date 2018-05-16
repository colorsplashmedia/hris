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
import dai.hris.model.ProfessionalFee;

public class ProfessionalFeeDAO {

	private Connection conn = null;

	public ProfessionalFeeDAO() {    	
		try {
			/* MS SQL CODE */    		
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
			conn.setAutoCommit(false);
		} catch (SQLException sqle) {
			System.out.println("ProfessionalFeeDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("ProfessionalFeeDAO :" + e.getMessage());
		}
	}
	
	public void saveProfessionalFee(ProfessionalFee model) throws SQLException, Exception {
		String sql = "insert into ProfessionalFee (officialReceiptNumber, officialReceiptDate, grossAmount, withHoldingTax, finalTax, netAmountDue, remarks, empId, patientId, patientName, createdAt) "
				+ " values (?,?,?,?,?,?,?,?,?,?,SYSDATETIME()) ";
		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		int idx = 1;
        ps.setString(idx++, model.getOfficialReceiptNumber());
        ps.setString(idx++, model.getOfficialReceiptDate());
        ps.setBigDecimal(idx++, model.getGrossAmount());
        ps.setBigDecimal(idx++, model.getWithHoldingTax());
        ps.setBigDecimal(idx++, model.getFinalTax());
        ps.setBigDecimal(idx++, model.getNetAmountDue());
        ps.setString(idx++, model.getRemarks());
        ps.setInt(idx++, model.getEmpId());     
        ps.setString(idx++, model.getPatientId());
        ps.setString(idx++, model.getPatientName());
        
        ps.executeUpdate();
		
    	conn.commit(); 
    	
    	sql = null;
		ps.close();
	}

	public void saveOrUpdate(ProfessionalFee pf) throws SQLException {
		if (pf.getProfessionalFeeId() > 0) {
			String sql = "update ProfessionalFee set "
					+ " officialReceiptNumber=?, officialReceiptDate=?, grossAmount=?, withHoldingTax=?, finalTax=?, netAmountDue=?, patientId=?, patientName=?, remarks=?, empId=? "
					//+ " officialReceiptNumber=?, officialReceiptDate=?, grossAmount=?, withHoldingTax=?, finalTax=?, netAmountDue=?, remarks=?, empId=? "
					+ " where professionalFeeId=?";
			PreparedStatement ps  = conn.prepareStatement(sql);
			int idx = 1;
			ps.setString(idx++, pf.getOfficialReceiptNumber());
	        ps.setString(idx++, pf.getOfficialReceiptDate());
	        ps.setBigDecimal(idx++, pf.getGrossAmount());
	        ps.setBigDecimal(idx++, pf.getWithHoldingTax());
	        ps.setBigDecimal(idx++, pf.getFinalTax());
	        ps.setBigDecimal(idx++, pf.getNetAmountDue());
	        ps.setString(idx++, pf.getPatientId());
	        ps.setString(idx++, pf.getPatientName());
	        ps.setString(idx++, pf.getRemarks());
	        ps.setInt(idx++, pf.getEmpId());
//	        ps.setDate(idx++, new Date(System.currentTimeMillis()));
	        ps.setInt(idx++, pf.getProfessionalFeeId());
			ps.executeUpdate();
	 		conn.commit();
	 		ps.close();
		} else {
			String sql = "insert into ProfessionalFee "
					+ " (officialReceiptNumber, officialReceiptDate, grossAmount, withHoldingTax, finalTax, netAmountDue, patientId, patientName, remarks, empId, createdAt) "
					+ " (officialReceiptNumber, officialReceiptDate, grossAmount, withHoldingTax, finalTax, netAmountDue, remarks, empId, createdAt) "
					+ " values (?,?,?,?,?,?,?,?,?) ";
			PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			int idx = 1;
	        ps.setString(idx++, pf.getOfficialReceiptNumber());
	        ps.setString(idx++, pf.getOfficialReceiptDate());
	        ps.setBigDecimal(idx++, pf.getGrossAmount());
	        ps.setBigDecimal(idx++, pf.getWithHoldingTax());
	        ps.setBigDecimal(idx++, pf.getFinalTax());
	        ps.setBigDecimal(idx++, pf.getNetAmountDue());
	        ps.setString(idx++, pf.getPatientId());
	        ps.setString(idx++, pf.getPatientName());
	        ps.setString(idx++, pf.getRemarks());
	        ps.setInt(idx++, pf.getEmpId());
	        ps.setDate(idx++, new Date(System.currentTimeMillis()));
	        ps.executeUpdate();
	        ResultSet keyResultSet = ps.getGeneratedKeys();
	        int generatedAutoIncrementId = 0;
	        if (keyResultSet.next()) {
	        	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
	        	pf.setProfessionalFeeId(generatedAutoIncrementId);
	        	conn.commit();
	        }
	        keyResultSet.close();
	        ps.close();
		}
	}

	public void delete(ProfessionalFee pf) throws SQLException {
		String sql = "delete from ProfessionalFee where professionalFeeId=?";
		PreparedStatement ps  = conn.prepareStatement(sql);
		ps.setInt(1, pf.getProfessionalFeeId());
		ps.executeUpdate();
        conn.commit();
        ps.close();
	}
	
	public void closeConnection() throws SQLException {
		conn.close();
	}
	
	public List<ProfessionalFee> getAllByEmployeeId(int empId) throws SQLException {
    	List<ProfessionalFee> resultList = new ArrayList<ProfessionalFee>();
    	String sql = "select * from ProfessionalFee where empId = ?";
    	PreparedStatement ps = conn.prepareStatement(sql);
    	ps.setInt(1, empId);
    	
    	ResultSet rs = ps.executeQuery();
    	while (rs.next()) {
    		ProfessionalFee pf = new ProfessionalFee();
    		pf.setProfessionalFeeId(rs.getInt("professionalFeeId"));
    		pf.setOfficialReceiptNumber(rs.getString("officialReceiptNumber"));
    		pf.setOfficialReceiptDate(rs.getString("officialReceiptDate"));
    		pf.setGrossAmount(rs.getBigDecimal("grossAmount"));
    		pf.setWithHoldingTax(rs.getBigDecimal("withHoldingTax"));
    		pf.setFinalTax(rs.getBigDecimal("finalTax"));
    		pf.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
    		pf.setPatientId(rs.getString("patientId"));
    		pf.setPatientName(rs.getString("patientName"));
    		pf.setRemarks(rs.getString("remarks"));
    		pf.setEmpId(rs.getInt("empId"));
    		resultList.add(pf);
    	}
    	rs.close();
    	ps.close();
    	return resultList;
    }
    
	public List<ProfessionalFee> getProfessionalFeeListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "grossAmount ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT e.empNo, fr.professionalFeeId, fr.officialReceiptNumber, fr.officialReceiptDate, fr.grossAmount, fr.withHoldingTax, fr.finalTax, fr.netAmountDue, fr.patientId, fr.patientName, fr.remarks, fr.empId, fr.createdAt, e.firstname, e.lastname, ROW_NUMBER() OVER(ORDER BY ");		
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM professionalFee fr, employee e WHERE fr.empId = e.empId AND fr.empId =  ");
    	sql.append(empId);
    	sql.append(" ) SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    
	    List<ProfessionalFee> list = new ArrayList<ProfessionalFee>();
	    
	    while (rs.next()) {
	    	
	    	ProfessionalFee dao =  new ProfessionalFee();
	    	
	    	dao.setProfessionalFeeId(rs.getInt("professionalFeeId"));
	    	dao.setOfficialReceiptNumber(rs.getString("officialReceiptNumber"));
	    	dao.setOfficialReceiptDate(rs.getString("officialReceiptDate"));
	    	dao.setGrossAmount(rs.getBigDecimal("grossAmount"));
	    	dao.setWithHoldingTax(rs.getBigDecimal("withHoldingTax"));
    		dao.setFinalTax(rs.getBigDecimal("finalTax"));
    		dao.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
    		dao.setPatientId(rs.getString("patientId"));
    		dao.setPatientName(rs.getString("patientName"));
    		dao.setRemarks(rs.getString("remarks"));
    		dao.setEmpId(rs.getInt("empId"));
    		dao.setEmpName(rs.getString("firstname") + " " + rs.getString("lastname"));
    		dao.setEmpNo(rs.getString("empNo"));   
    		
	    	 list.add(dao);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;   
	}
	
	public  int getCount(int empId) throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM ProfessionalFee WHERE empId = ");
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
	
	public List<ProfessionalFee> getProfessionalFeeListByDateRange(String dateFrom, String dateTo) throws SQLException {
    	List<ProfessionalFee> resultList = new ArrayList<ProfessionalFee>();
    	StringBuffer sql = new StringBuffer();       	    	
    	
    	sql.append("SELECT e.empNo, (e.firstname + ' ' + e.lastname) as name, e.empNo, fr.professionalFeeId, fr.officialReceiptNumber, fr.officialReceiptDate, fr.grossAmount, fr.withHoldingTax, fr.finalTax, fr.netAmountDue, fr.patientId, fr.patientName, fr.remarks, fr.empId, fr.createdAt, e.firstname, e.lastname ");    	
    	sql.append(" FROM professionalFee fr, employee e WHERE fr.empid = e.empid AND fr.officialReceiptDate BETWEEN '");
    	sql.append(dateFrom);
    	sql.append("' AND '");
    	sql.append(dateTo);    	    	
    	sql.append("' ORDER BY name");
    	
		System.out.println("SQL getProfessionalFeeListByDateRange: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    
    	while(rs.next()) {
    		ProfessionalFee pf = new ProfessionalFee();
    		pf.setProfessionalFeeId(rs.getInt("professionalFeeId"));
    		pf.setOfficialReceiptNumber(rs.getString("officialReceiptNumber"));
    		pf.setOfficialReceiptDate(rs.getString("officialReceiptDate"));
    		pf.setGrossAmount(rs.getBigDecimal("grossAmount"));
    		pf.setWithHoldingTax(rs.getBigDecimal("withHoldingTax"));
    		pf.setFinalTax(rs.getBigDecimal("finalTax"));
    		pf.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
    		pf.setPatientId(rs.getString("patientId"));
    		pf.setPatientName(rs.getString("patientName"));
    		pf.setRemarks(rs.getString("remarks"));
    		pf.setEmpName(rs.getString("name"));
    		resultList.add(pf);
    	}
    	
    	
    	rs.close();
    	ps.close();
    	return resultList;
    }
	
    public List<ProfessionalFee> getAllByEmpIdAndOrDateRange(int empId, Date from, Date to) throws SQLException {
    	List<ProfessionalFee> resultList = new ArrayList<ProfessionalFee>();
    	String sql = "select * from ProfessionalFee "
    			+ " where empId=? and officialReceiptDate between ? and ? ";
    	PreparedStatement ps = conn.prepareStatement(sql);
    	ps.setInt(1, empId);
    	ps.setDate(2, from);
    	ps.setDate(3, to);
    	ResultSet rs = ps.executeQuery();
    	while(rs.next()) {
    		ProfessionalFee pf = new ProfessionalFee();
    		pf.setProfessionalFeeId(rs.getInt("professionalFeeId"));
    		pf.setOfficialReceiptNumber(rs.getString("orNumber"));
    		pf.setOfficialReceiptDate(rs.getString("orDate"));
    		pf.setGrossAmount(rs.getBigDecimal("grossAmount"));
    		pf.setWithHoldingTax(rs.getBigDecimal("withHoldingTax"));
    		pf.setFinalTax(rs.getBigDecimal("finalTax"));
    		pf.setNetAmountDue(rs.getBigDecimal("netAmountDue"));
    		pf.setPatientId(rs.getString("patientId"));
    		pf.setPatientName(rs.getString("patientName"));
    		pf.setRemarks(rs.getString("remarks"));
    		pf.setEmpId(empId);
    		pf.setCreatedAt(rs.getString("createdAt"));
    		pf.setUpdatedAt(rs.getString("updatedAt"));
    		resultList.add(pf);
    	}
    	rs.close();
    	ps.close();
    	return resultList;
    }
    
}
