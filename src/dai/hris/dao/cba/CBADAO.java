package dai.hris.dao.cba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dai.hris.action.filemanager.util.EmployeeUtil;
import dai.hris.dao.DBConstants;
import dai.hris.model.DisbursementVoucher;
import dai.hris.model.EmployeeCBA;
import dai.hris.model.EmployeeCBADetails;
import dai.hris.model.ObligationRequest;
import dai.hris.model.PayrollExclusion;


public class CBADAO {
	Connection conn = null;
	
	public CBADAO() {
		
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("CBADAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("CBADAO :" + e.getMessage());
		}
	}
	
	public void deletePayrollExlusionById(int empPayrollExclusionId) throws SQLException, Exception {
		StringBuffer sql = new StringBuffer();
    	sql.append("DELETE FROM empPayrollExclusion WHERE empPayrollExclusionId = ");
    	sql.append(empPayrollExclusionId);  	    	
    	
    	PreparedStatement ps = conn.prepareStatement(sql.toString()); 
		
		ps.executeUpdate();    	
		
		conn.commit();
	}
	
	public List<PayrollExclusion> getAllPayrollExlusion() throws SQLException, Exception {
		PayrollExclusion model = null;
    	List<PayrollExclusion> list = new ArrayList<PayrollExclusion>();
    	StringBuffer sql = new StringBuffer();  
    	
		sql.append("SELECT ep.empPayrollExclusionId, ep.empId, ep.payrollPeriodId, e.empNo, (e.firstname + ' ' + e.lastname) as name, (SUBSTRING(CONVERT(VARCHAR,pp.fromDate), 1, 11) + ' To ' + SUBSTRING(CONVERT(VARCHAR,pp.toDate), 1, 11)) as payPeriod ");
		sql.append("FROM empPayrollExclusion ep, employee e, payrollPeriod pp WHERE ep.empId = e.empId AND ep.payrollPeriodId = pp.payrollPeriodId ");
		sql.append("ORDER BY name, pp.fromDate ");
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL : " + sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    while (rs.next()) {
	    	
	    	model = new PayrollExclusion();
    		
	    	model.setEmpPayrollExclusionId(rs.getInt("empPayrollExclusionId"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setPayrollPeriodId(rs.getInt("payrollPeriodId"));
	    	model.setEmpName(rs.getString("name"));
	    	model.setPayrollPeriod(rs.getString("payPeriod"));
	    	model.setEmpNo(rs.getString("empNo"));
	    	
	    	list.add(model);
	    	
	    	
		}
	    sql = null;
	    ps.close();
	    rs.close();	 
	    return list;
	}
	
	public DisbursementVoucher getDisbursementVoucherById(int disbursementVoucherId) throws SQLException, Exception {
		String sql = "SELECT * FROM disbursementVoucher WHERE disbursementVoucherId = " + disbursementVoucherId;
		DisbursementVoucher model = null;
		PreparedStatement ps = conn.prepareStatement(sql.toString());
	
	    ResultSet rs = ps.executeQuery();
	    
	    if (rs.next()) {	    	
	    	model = new DisbursementVoucher();    	
	    	
	    	model.setModeOfPayment(rs.getString("modeOfPayment"));
			model.setFundCluster(rs.getString("fundCluster"));
			model.setDisbursementDate(rs.getString("disbursementDate"));
			model.setDvNo(rs.getString("dvNo"));
			model.setOthersDetail(rs.getString("othersDetail"));
			model.setPayee(rs.getString("payee"));
			model.setTin(rs.getString("tin"));
			model.setOrs(rs.getString("ors"));
			model.setAddress(rs.getString("address"));
			model.setParticulars(rs.getString("particulars"));
			model.setResponsibilityCenter(rs.getString("responsibilityCenter"));
			model.setMfo(rs.getString("mfo"));
			model.setAmount(rs.getBigDecimal("amount"));
			model.setTotalAmount(rs.getBigDecimal("amount"));
			model.setAccountingTitle(rs.getString("accountingTitle"));
			model.setUacsCode(rs.getString("uacsCode"));
			model.setDebit(rs.getBigDecimal("debit"));
			model.setCredit(rs.getBigDecimal("credit"));
			model.setAmountInWords(rs.getString("amountInWords"));
			model.setSignatory1(rs.getString("signatory1"));
			model.setSignatory2(rs.getString("signatory2"));
			model.setSignatory3(rs.getString("signatory3"));
			model.setPosition1(rs.getString("position1"));
			model.setPosition2(rs.getString("position2"));
			model.setPosition3(rs.getString("position3"));
			model.setCheckNo(rs.getString("checkNo"));
			model.setCheckDate(rs.getString("checkDate"));
			model.setBankDetails(rs.getString("bankDetails"));
			model.setOrNo(rs.getString("orNo"));
			model.setOrDate(rs.getString("orDate"));
			model.setJevNo(rs.getString("jevNo"));
			model.setCertifiedMethod(rs.getString("certifiedMethod"));
			model.setPrintedName(rs.getString("printedName"));
			model.setDisbursementVoucherId(rs.getInt("disbursementVoucherId"));
	    	
	    }
	    
	    ps.close();
	    rs.close();
	    return model;
	}
	
	public List<DisbursementVoucher> getAllDisbursementVouchers() throws SQLException, Exception {
		String sql = "SELECT * FROM disbursementVoucher ORDER BY disbursementDate desc";
		DisbursementVoucher model = null;
		PreparedStatement ps = conn.prepareStatement(sql.toString());
	
	    ResultSet rs = ps.executeQuery();
	    List<DisbursementVoucher> list = new ArrayList<DisbursementVoucher>();
	    
	    while (rs.next()) {	    	
	    	model = new DisbursementVoucher();
	    	
	    	
	    	model.setModeOfPayment(rs.getString("modeOfPayment"));
			model.setFundCluster(rs.getString("fundCluster"));
			model.setDisbursementDate(rs.getString("disbursementDate"));
			model.setDvNo(rs.getString("dvNo"));
			model.setOthersDetail(rs.getString("othersDetail"));
			model.setPayee(rs.getString("payee"));
			model.setTin(rs.getString("tin"));
			model.setOrs(rs.getString("ors"));
			model.setAddress(rs.getString("address"));
			model.setParticulars(rs.getString("particulars"));
			model.setResponsibilityCenter(rs.getString("responsibilityCenter"));
			model.setMfo(rs.getString("mfo"));
			model.setAmount(rs.getBigDecimal("amount"));
			model.setTotalAmount(rs.getBigDecimal("amount"));
			model.setAccountingTitle(rs.getString("accountingTitle"));
			model.setUacsCode(rs.getString("uacsCode"));
			model.setDebit(rs.getBigDecimal("debit"));
			model.setCredit(rs.getBigDecimal("credit"));
			model.setAmountInWords(rs.getString("amountInWords"));
			model.setSignatory1(rs.getString("signatory1"));
			model.setSignatory2(rs.getString("signatory2"));
			model.setSignatory3(rs.getString("signatory3"));
			model.setPosition1(rs.getString("position1"));
			model.setPosition2(rs.getString("position2"));
			model.setPosition3(rs.getString("position3"));
			model.setCheckNo(rs.getString("checkNo"));
			model.setCheckDate(rs.getString("checkDate"));
			model.setBankDetails(rs.getString("bankDetails"));
			model.setOrNo(rs.getString("orNo"));
			model.setOrDate(rs.getString("orDate"));
			model.setJevNo(rs.getString("jevNo"));
			model.setCertifiedMethod(rs.getString("certifiedMethod"));
			model.setPrintedName(rs.getString("printedName"));
			model.setDisbursementVoucherId(rs.getInt("disbursementVoucherId"));
	    	
	    	
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();
	    return list;	
	}
	
	public void addDisbursementVoucher(DisbursementVoucher model) throws SQLException, Exception {
		EmployeeUtil util = new EmployeeUtil();
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO disbursementVoucher(empId, fundCluster, disbursementDate, dvNo, modeOfPayment, othersDetail, payee, tin, ors, address, particulars, ");
		sql.append("responsibilityCenter, mfo, amount, totalAmount, accountingTitle, uacsCode, amountInWords, debit, credit, certifiedMethod, signatory1, ");
		sql.append("signatory2, signatory3, position1, position2, position3, checkNo, checkDate, bankDetails, printedName, orNo, orDate, jevNo, payrollPeriodId) ");
		sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		PreparedStatement ps  = conn.prepareStatement(sql.toString());
		ps.setInt(1, model.getEmpId());
		ps.setString(2, model.getFundCluster());
		ps.setDate(3, model.getDisbursementDate() != null && model.getDisbursementDate().length() > 0 ? util.convertToSqlDate(model.getDisbursementDate()) : null);
		ps.setString(4, model.getDvNo());
		ps.setString(5, model.getModeOfPayment());
		ps.setString(6, model.getOthersDetail());
		ps.setString(7, model.getPayee());
		ps.setString(8, model.getTin());
		ps.setString(9, model.getOrs());
		ps.setString(10, model.getAddress());
		ps.setString(11, model.getParticulars());
		ps.setString(12, model.getResponsibilityCenter());
		ps.setString(13, model.getMfo());
		ps.setBigDecimal(14, model.getAmount());
		ps.setBigDecimal(15, model.getTotalAmount());
		ps.setString(16, model.getAccountingTitle());
		ps.setString(17, model.getUacsCode());
		ps.setString(18, model.getAmountInWords());
		ps.setBigDecimal(19, model.getDebit());
		ps.setBigDecimal(20, model.getCredit());
		ps.setString(21, model.getCertifiedMethod());
		ps.setString(22, model.getSignatory1());
		ps.setString(23, model.getSignatory2());
		ps.setString(24, model.getSignatory3());
		ps.setString(25, model.getPosition1());
		ps.setString(26, model.getPosition2());
		ps.setString(27, model.getPosition3());
		ps.setString(28, model.getCheckNo());
		ps.setDate(29, model.getCheckDate() != null && model.getCheckDate().length() > 0 ? util.convertToSqlDate(model.getCheckDate()) : null);
		ps.setString(30, model.getBankDetails());
		ps.setString(31, model.getPrintedName());
		ps.setString(32, model.getOrNo());
		ps.setDate(33, model.getOrDate() != null && model.getOrDate().length() > 0 ? util.convertToSqlDate(model.getOrDate()) : null);
		ps.setString(34, model.getJevNo());
		ps.setInt(35, model.getPayrollPeriodId());
		
		ps.executeUpdate();
		
		conn.commit();
		 
		ps.close(); 
	}
	
	public void updateDisbursementVoucher(DisbursementVoucher model) throws SQLException, Exception {
		String sql = "UPDATE disbursementVoucher SET empId=?, fundCluster=?, disbursementDate=?, dvNo=?, modeOfPayment=?, othersDetail=?, payee=?, tin=?, ors=?, address=?, particulars=?, responsibilityCenter=?, mfo=?, amount=?, totalAmount=?, accountingTitle=?, uacsCode=?, amountInWords=?, debit=?, credit=?, certifiedMethod=?, signatory1=?, signatory2=?, signatory3=?, position1=?, position2=?, position3=?, checkNo=?, checkDate=?, bankDetails=?, printedName=?, orNo=?, orDate=?, jevNo=?, payrollPeriodId=? WHERE disbursementVoucherId=?";
		PreparedStatement ps  = conn.prepareStatement(sql);
		ps.setInt(1, model.getEmpId());
		ps.setString(2, model.getFundCluster());
		ps.setString(3, model.getDisbursementDate());
		ps.setString(4, model.getDvNo());
		ps.setString(5, model.getModeOfPayment());
		ps.setString(6, model.getOthersDetail());
		ps.setString(7, model.getPayee());
		ps.setString(8, model.getTin());
		ps.setString(9, model.getOrs());
		ps.setString(10, model.getAddress());
		ps.setString(11, model.getParticulars());
		ps.setString(12, model.getResponsibilityCenter());
		ps.setString(13, model.getMfo());
		ps.setBigDecimal(14, model.getAmount());
		ps.setBigDecimal(15, model.getTotalAmount());
		ps.setString(16, model.getAccountingTitle());
		ps.setString(17, model.getUacsCode());
		ps.setString(18, model.getAmountInWords());
		ps.setBigDecimal(19, model.getDebit());
		ps.setBigDecimal(20, model.getCredit());
		ps.setString(21, model.getCertifiedMethod());
		ps.setString(22, model.getSignatory1());
		ps.setString(23, model.getSignatory2());
		ps.setString(24, model.getSignatory3());
		ps.setString(25, model.getPosition1());
		ps.setString(26, model.getPosition2());
		ps.setString(27, model.getPosition3());
		ps.setString(28, model.getCheckNo());
		ps.setString(29, model.getCheckDate());
		ps.setString(30, model.getBankDetails());
		ps.setString(31, model.getPrintedName());
		ps.setString(32, model.getOrNo());
		ps.setString(33, model.getOrDate());
		ps.setString(34, model.getJevNo());
		ps.setInt(35, model.getPayrollPeriodId());
		ps.setInt(36, model.getDisbursementVoucherId());
	    
	    ps.executeUpdate();
        conn.commit();
        ps.close();

	}
	
	public void addObligationRequest(ObligationRequest model) throws SQLException, Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO obligationRequest(empId, fundCluster, obligationDate, dvNo, payee, office, address, particulars, responsibilityCenter, mfo, amount, ");
		sql.append("totalAmount, uacsCode, amountInWords, signatory1, signatory2, position1, position2, jevNo, refDate, refParticular, obligationAmount, ");
		sql.append("paymentAmount, notDueAmount, demandAmount) ");
		sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
		PreparedStatement ps  = conn.prepareStatement(sql.toString());
		ps.setInt(1, model.getEmpId());
		ps.setString(2, model.getFundCluster());
		ps.setString(3, model.getObligationDate());
		ps.setString(4, model.getDvNo());
		ps.setString(5, model.getPayee());
		ps.setString(6, model.getOffice());
		ps.setString(7, model.getAddress());
		ps.setString(8, model.getParticulars());
		ps.setString(9, model.getResponsibilityCenter());
		ps.setString(10, model.getMfo());
		ps.setBigDecimal(11, model.getAmount());
		ps.setBigDecimal(12, model.getTotalAmount());
		ps.setString(13, model.getUacsCode());
		ps.setString(14, model.getAmountInWords());
		ps.setString(15, model.getSignatory1());
		ps.setString(16, model.getSignatory2());
		ps.setString(17, model.getPosition1());
		ps.setString(18, model.getPosition2());
		ps.setString(19, model.getJevNo());
		ps.setString(20, model.getRefDate());
		ps.setString(21, model.getRefParticular());
		ps.setBigDecimal(22, model.getObligationAmount());
		ps.setBigDecimal(23, model.getPaymentAmount());
		ps.setBigDecimal(24, model.getNotDueAmount());
		ps.setBigDecimal(25, model.getDemandAmount());
		
		ps.executeUpdate();
		
		conn.commit();
		 
		ps.close(); 
	}
	
	public void updateObligationRequest(ObligationRequest model) throws SQLException, Exception {
		String sql = "UPDATE obligationRequest SET empId=?, fundCluster=?, obligationDate=?, dvNo=?, payee=?, office=?, address=?, particulars=?, responsibilityCenter=?, mfo=?, amount=?, totalAmount=?, uacsCode=?, amountInWords=?, signatory1=?, signatory2=?, position1=?, position2=?, jevNo=?, refDate=?, refParticular=?, obligationAmount=?, paymentAmount=?, notDueAmount=?, demandAmount=? WHERE obligationRequestId=?";
		PreparedStatement ps  = conn.prepareStatement(sql);

		ps.setInt(1, model.getEmpId());
		ps.setString(2, model.getFundCluster());
		ps.setString(3, model.getObligationDate());
		ps.setString(4, model.getDvNo());
		ps.setString(5, model.getPayee());
		ps.setString(6, model.getOffice());
		ps.setString(7, model.getAddress());
		ps.setString(8, model.getParticulars());
		ps.setString(9, model.getResponsibilityCenter());
		ps.setString(10, model.getMfo());
		ps.setBigDecimal(11, model.getAmount());
		ps.setBigDecimal(12, model.getTotalAmount());
		ps.setString(13, model.getUacsCode());
		ps.setString(14, model.getAmountInWords());
		ps.setString(15, model.getSignatory1());
		ps.setString(16, model.getSignatory2());
		ps.setString(17, model.getPosition1());
		ps.setString(18, model.getPosition2());
		ps.setString(19, model.getJevNo());
		ps.setString(20, model.getRefDate());
		ps.setString(21, model.getRefParticular());
		ps.setBigDecimal(22, model.getObligationAmount());
		ps.setBigDecimal(23, model.getPaymentAmount());
		ps.setBigDecimal(24, model.getNotDueAmount());
		ps.setBigDecimal(25, model.getDemandAmount());
		
		ps.setInt(26, model.getObligationRequestId());
	    
	    ps.executeUpdate();
        conn.commit();
        ps.close();

	}
	
	public ObligationRequest getObligationRequestById(int obligationRequestId) throws SQLException, Exception {
		String sql = "SELECT * FROM obligationRequest WHERE obligationRequestId = " + obligationRequestId;
		ObligationRequest model = null;
		PreparedStatement ps = conn.prepareStatement(sql.toString());
	
	    ResultSet rs = ps.executeQuery();
	    
	    while (rs.next()) {	    	
	    	model = new ObligationRequest();
	    	
	    	model.setFundCluster(rs.getString("fundCluster"));
	    	model.setObligationDate(rs.getString("obligationDate"));
	    	model.setDvNo(rs.getString("dvNo"));
			model.setPayee(rs.getString("payee"));
			model.setOffice(rs.getString("office"));
			model.setAddress(rs.getString("address"));
			model.setParticulars(rs.getString("particulars"));
			model.setResponsibilityCenter(rs.getString("responsibilityCenter"));
			model.setMfo(rs.getString("mfo"));
			model.setAmount(rs.getBigDecimal("amount"));
			model.setTotalAmount(rs.getBigDecimal("amount"));
			model.setUacsCode(rs.getString("uacsCode"));
			model.setAmountInWords(rs.getString("amountInWords"));
			model.setSignatory1(rs.getString("signatory1"));
			model.setSignatory2(rs.getString("signatory2"));
			model.setPosition1(rs.getString("position1"));
			model.setPosition2(rs.getString("position2"));
			model.setJevNo(rs.getString("jevNo"));
			model.setRefDate(rs.getString("refDate"));
			model.setRefParticular(rs.getString("refParticular"));			
			model.setObligationAmount(rs.getBigDecimal("obligationAmount"));
			model.setPaymentAmount(rs.getBigDecimal("paymentAmount"));
			model.setNotDueAmount(rs.getBigDecimal("notDueAmount"));
			model.setDemandAmount(rs.getBigDecimal("demandAmount"));
			model.setObligationRequestId(rs.getInt("obligationRequestId"));	    	
	    	
	    }
	    
	    ps.close();
	    rs.close();
	    return model;	
	}
	
	public List<ObligationRequest> getAllObligationRequest() throws SQLException, Exception {
		String sql = "SELECT * FROM obligationRequest ORDER BY obligationDate desc";
		ObligationRequest model = null;
		PreparedStatement ps = conn.prepareStatement(sql.toString());
	
	    ResultSet rs = ps.executeQuery();
	    List<ObligationRequest> list = new ArrayList<ObligationRequest>();
	    
	    while (rs.next()) {	    	
	    	model = new ObligationRequest();	    	
	    	
	    	model.setFundCluster(rs.getString("fundCluster"));
	    	model.setObligationDate(rs.getString("obligationDate"));
	    	model.setDvNo(rs.getString("dvNo"));
			model.setPayee(rs.getString("payee"));
			model.setOffice(rs.getString("office"));
			model.setAddress(rs.getString("address"));
			model.setParticulars(rs.getString("particulars"));
			model.setResponsibilityCenter(rs.getString("responsibilityCenter"));
			model.setMfo(rs.getString("mfo"));
			model.setAmount(rs.getBigDecimal("amount"));
			model.setTotalAmount(rs.getBigDecimal("amount"));
			model.setUacsCode(rs.getString("uacsCode"));
			model.setAmountInWords(rs.getString("amountInWords"));
			model.setSignatory1(rs.getString("signatory1"));
			model.setSignatory2(rs.getString("signatory2"));
			model.setPosition1(rs.getString("position1"));
			model.setPosition2(rs.getString("position2"));
			model.setJevNo(rs.getString("jevNo"));
			model.setRefDate(rs.getString("refDate"));
			model.setRefParticular(rs.getString("refParticular"));			
			model.setObligationAmount(rs.getBigDecimal("obligationAmount"));
			model.setPaymentAmount(rs.getBigDecimal("paymentAmount"));
			model.setNotDueAmount(rs.getBigDecimal("notDueAmount"));
			model.setDemandAmount(rs.getBigDecimal("demandAmount"));
			model.setObligationRequestId(rs.getInt("obligationRequestId"));	
	    	
	    	
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();
	    return list;	
	}
	

	public EmployeeCBA getEmployeeCbaByCbaId(int cbaId) throws SQLException, Exception {			    		
		String sql = "SELECT * FROM cba WHERE cbaId = ?";
		EmployeeCBA model = null;
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, cbaId);
		ResultSet rs = ps.executeQuery();	    
	    while (rs.next()) {
	    	model = new EmployeeCBA();
	    	model.setCbaId(rs.getInt("cbaId"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setPerformanceYear(rs.getInt("performanceYear"));
	    	model.setMonthFrom(rs.getInt("monthFrom"));
	    	model.setMonthTo(rs.getInt("monthTo"));
	    	model.setAssessmentDate(rs.getString("assessmentDate"));
	    	model.setApprovedBy(rs.getInt("approvedBy"));
	    	model.setFinalRatingBy(rs.getInt("finalRatingBy"));
	    	model.setApprovedBy(rs.getInt("approvedBy"));
	    	model.setFinalRating(rs.getDouble("finalRating"));
	    	model.setComments(rs.getString("comments"));
	    }
	    
	    ps.close();
	    rs.close();      
	    return model;
	}
	

	
	public List<EmployeeCBA> getEmployeeCBAByEmpId(int empId) throws SQLException, Exception {			    		
		String sql = "SELECT * FROM cba WHERE empId = ? ORDER BY performanceYear desc";
		EmployeeCBA model = null;
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, empId);
	
	    ResultSet rs = ps.executeQuery();
	    List<EmployeeCBA> list = new ArrayList<EmployeeCBA>();
	    
	    while (rs.next()) {	    	
	    	model = new EmployeeCBA();
	    	model.setCbaId(rs.getInt("cbaId"));
	    	model.setEmpId(rs.getInt("empId"));
	    	model.setPerformanceYear(rs.getInt("performanceYear"));
	    	model.setMonthFrom(rs.getInt("monthFrom"));
	    	model.setMonthTo(rs.getInt("monthTo"));
	    	model.setAssessmentDate(rs.getString("assessmentDate"));
	    	model.setApprovedBy(rs.getInt("approvedBy"));
	    	model.setFinalRatingBy(rs.getInt("finalRatingBy"));
	    	model.setApprovedBy(rs.getInt("approvedBy"));
	    	model.setFinalRating(rs.getDouble("finalRating"));
	    	model.setComments(rs.getString("comments"));
	    	
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();
	    return list;		
	}
	
	public List<EmployeeCBADetails> getEmployeeCbaDetailsByCbaId(int cbaId) throws SQLException, Exception {			    		
		String sql = "SELECT * FROM cbaDetails WHERE cbaId = ?";
		EmployeeCBADetails model = null;
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		ps.setInt(1, cbaId);
		ResultSet rs = ps.executeQuery();	
		List<EmployeeCBADetails> list = new ArrayList<EmployeeCBADetails>();
		
	    while (rs.next()) {
	    	model = new EmployeeCBADetails();
	    	model.setCbaDetailsId(rs.getInt("cbaDetailsId"));
	    	model.setCbaId(rs.getInt("cbaId"));
	    	model.setMajorFinalOutput(rs.getString("majorFinalOutput"));
	    	model.setPerformanceIndicator(rs.getString("performanceIndicator"));
	    	model.setActualAccomplishment(rs.getString("actualAccomplishment"));
	    	model.setqRating(rs.getDouble("qRating"));
	    	model.seteRating(rs.getDouble("eRating"));
	    	model.settRating(rs.getDouble("tRating"));
	    	model.setAveRating(rs.getDouble("aveRating"));
	    	model.setRemarks(rs.getString("remarks"));
	    	
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    
	    return list;
	}
	    

	public void add(EmployeeCBA model) throws SQLException, Exception {
		String sql = "INSERT INTO cba(empId, monthFrom, monthTo, performanceYear, assessmentDate, approvedBy, assessedBy, finalRatingBy, finalRating, comments) "
						+ "VALUES (?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps  = conn.prepareStatement(sql);
	    ps.setInt(1, model.getEmpId());
	    ps.setInt(2, model.getMonthFrom());
	    ps.setInt(3, model.getMonthTo());
	    ps.setInt(4, model.getPerformanceYear());
	    ps.setString(5, model.getAssessmentDate());
	    ps.setInt(6, model.getApprovedBy());
	    ps.setInt(7, model.getAssessedBy());
	    ps.setInt(8, model.getFinalRatingBy());
	    ps.setDouble(9, model.getFinalRating());
	    ps.setString(10, model.getComments());
	    
	    ps.executeUpdate();
		
		conn.commit();
		 
		ps.close();      

	}

	    
	
    public void update(EmployeeCBA model) throws SQLException, Exception {		
		String sql = "UPDATE cba SET empId=?, monthFrom=?, monthTo=?, performanceYear=?, assessmentDate=?, approvedBy=?, assessedBy=?, finalRatingBy=?, finalRating=?, comments=? WHERE cbaId=?";
		PreparedStatement ps  = conn.prepareStatement(sql);
		ps.setInt(1, model.getEmpId());
	    ps.setInt(2, model.getMonthFrom());
	    ps.setInt(3, model.getMonthTo());
	    ps.setInt(4, model.getPerformanceYear());
	    ps.setString(5, model.getAssessmentDate());
	    ps.setInt(6, model.getApprovedBy());
	    ps.setInt(7, model.getAssessedBy());
	    ps.setInt(8, model.getFinalRatingBy());
	    ps.setDouble(9, model.getFinalRating());
	    ps.setString(10, model.getComments());
	    ps.setInt(11, model.getCbaId());
	    
	    ps.executeUpdate();
        conn.commit();
        ps.close();

 	}
    
    public void addDetails(EmployeeCBADetails model) throws SQLException, Exception {
		String sql = "INSERT INTO cbaDetails(cbaId, majorFinalOutput, performanceIndicator, actualAccomplishment, qRating, eRating, tRating, aveRating, remarks) "
						+ "VALUES (?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps  = conn.prepareStatement(sql);
	    ps.setInt(1, model.getCbaId());
	    ps.setString(2, model.getMajorFinalOutput());
	    ps.setString(3, model.getPerformanceIndicator());
	    ps.setString(4, model.getActualAccomplishment());
	    ps.setDouble(5, model.getqRating());
	    ps.setDouble(6, model.geteRating());
	    ps.setDouble(7, model.gettRating());
	    ps.setDouble(8, model.getAveRating());
	    ps.setString(9, model.getRemarks());
	    
	    ps.executeUpdate();
		
		conn.commit();
		 
		ps.close();      

	}

	    
	
    public void updateDetails(EmployeeCBADetails model) throws SQLException, Exception {		
		String sql = "UPDATE cbaDetails SET cbaId=?, majorFinalOutput=?, performanceIndicator=?, actualAccomplishment=?, qRating=?, eRating=?, tRating=?, aveRating=?, remarks=? WHERE cbaDetailsId=?";
		PreparedStatement ps  = conn.prepareStatement(sql);
		ps.setInt(1, model.getCbaId());
	    ps.setString(2, model.getMajorFinalOutput());
	    ps.setString(3, model.getPerformanceIndicator());
	    ps.setString(4, model.getActualAccomplishment());
	    ps.setDouble(5, model.getqRating());
	    ps.setDouble(6, model.geteRating());
	    ps.setDouble(7, model.gettRating());
	    ps.setDouble(8, model.getAveRating());
	    ps.setString(9, model.getRemarks());
	    ps.setInt(10, model.getCbaDetailsId());
	    
	    ps.executeUpdate();
        conn.commit();
        ps.close();

 	}

    
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}
}
