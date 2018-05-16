package dai.hris.dao.filemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dai.hris.dao.DBConstants;
import dai.hris.model.SalaryGrade;

public class SalaryGradeDAO {
	
Connection conn = null;
    
    public SalaryGradeDAO() {
        
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("SalaryGradeDAO :" + sqle.getMessage());
			
		} catch (Exception e) {
			System.out.println("SalaryGradeDAO :" + e.getMessage());
		
		}
    	
    }
    
    public SalaryGrade getSalaryGrade(int salaryGradeId) throws SQLException, Exception {
    	SalaryGrade model = null;
		String sql = "SELECT * FROM salaryGrade where salaryGradeId = ?";
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, salaryGradeId);

	    ResultSet rs = ps.executeQuery();
	    
	    if (rs.next()) {
	    	model = new SalaryGrade();
	    	
	    	model.setSalaryGradeId(salaryGradeId);
	    	model.setSalaryGradeName(rs.getString("salaryGradeName"));
	    	model.setStep(rs.getInt("step"));
	    	model.setBasicSalary(rs.getBigDecimal("basicSalary"));
		}
	    sql = null;
	    ps.close();
	    rs.close();	 
	    return model;
    }
    
    public boolean isExist(String name) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM salaryGrade WHERE salaryGradeName = '");
    	sql.append(name);
    	sql.append("'");   	
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    if (rs.next()) {    	
	    	return true;
	    }
	    
	    ps.close();
	    rs.close();      
	    
    	
    	return false;
    }
    
    public boolean isExistUpdate(String name, int id) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM salaryGrade WHERE salaryGradeName = '");
    	sql.append(name);
    	sql.append("' and salaryGradeId <> ");  
    	sql.append(id);
    	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    if (rs.next()) {    	
	    	return true;
	    }
	    
	    ps.close();
	    rs.close();      
	    
    	
    	return false;
    }
    
    public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM jobTitle WHERE salaryGradeId = ");   	
    	sql.append(id);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("checkIfRecordHasBeenUsed Salary Grade: " + sql.toString());

	    ResultSet rs = ps.executeQuery();
	    
	    
	    if (rs.next()) {  
	    	ps.close();
		    rs.close(); 
		    
	    	return true;
	    }
	    
	    ps.close();
	    rs.close();   
		
		return false;
	}
    
    public  int getCountWithFilter(String filter) throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM salaryGrade WHERE salaryGradeName like '%"); 
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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM salaryGrade");   	
    	  	
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
    
    
    public List<SalaryGrade> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "salaryGradeName ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT salaryGradeId,  salaryGradeName, step, basicSalary, ROW_NUMBER() OVER(ORDER BY ");		
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM salaryGrade ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<SalaryGrade> list = new ArrayList<SalaryGrade>();
	    
	    while (rs.next()) {
	    	SalaryGrade model = new SalaryGrade();
	    	model.setSalaryGradeId(rs.getInt("salaryGradeId"));
	    	model.setSalaryGradeName(rs.getString("salaryGradeName"));	    
	    	model.setStep(rs.getInt("step"));
	    	model.setBasicSalary(rs.getBigDecimal("basicSalary"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    public List<SalaryGrade> getAllSalaryGradeList() throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();    	
    	
    	
    	sql.append("SELECT salaryGradeId,  salaryGradeName, step, basicSalary FROM salaryGrade");   	
		
		System.out.println("getAllSalaryGradeList SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<SalaryGrade> list = new ArrayList<SalaryGrade>();
	    
	    while (rs.next()) {
	    	SalaryGrade model = new SalaryGrade();
	    	model.setSalaryGradeId(rs.getInt("salaryGradeId"));
	    	model.setSalaryGradeName(rs.getString("salaryGradeName"));	    	
	    	model.setStep(rs.getInt("step"));
	    	model.setBasicSalary(rs.getBigDecimal("basicSalary"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    
    public List<SalaryGrade> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
    	
		StringBuffer sql = new StringBuffer(); 
		
		if(jtSorting == null){
    		jtSorting = "salaryGradeName ASC";
    	}
		
		sql.append("WITH OrderedList AS (SELECT salaryGradeId,  salaryGradeName, step, basicSalary, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM salaryGrade WHERE salaryGradeName like '%");
    	sql.append(filter);    	
    	sql.append("%') SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());
		
		

	    ResultSet rs = ps.executeQuery();
	    List<SalaryGrade> list = new ArrayList<SalaryGrade>();
	    
	    while (rs.next()) {
	    	SalaryGrade model = new SalaryGrade();
	    	model.setSalaryGradeId(rs.getInt("salaryGradeId"));
	    	model.setSalaryGradeName(rs.getString("salaryGradeName"));
	    	model.setStep(rs.getInt("step"));
	    	model.setBasicSalary(rs.getBigDecimal("basicSalary"));
	    	list.add(model);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     

	}
    

    
    
    public void add(SalaryGrade model) throws SQLException, Exception {
  		String sql = "INSERT INTO salaryGrade (salaryGradeName, step, basicSalary) VALUES (?, ?, ?)";
  		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setString(1, model.getSalaryGradeName()); 
        ps.setInt(2, model.getStep());
        ps.setBigDecimal(3, model.getBasicSalary());
        ps.executeUpdate();        
        conn.commit();               
        ps.close();
        sql = null;
  	}
    
    public  void delete(int id) throws SQLException, Exception {
		String sql = "DELETE FROM salaryGrade WHERE salaryGradeId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        conn.commit();
        ps.close();
        sql = null;
	}
    
    public  void update(SalaryGrade model) throws SQLException, Exception {		
 		String sql = "UPDATE salaryGrade SET salaryGradeName = ?, step = ?, basicSalary = ? WHERE salaryGradeId = ?";
 		PreparedStatement ps  = conn.prepareStatement(sql);
 		ps.setString(1, model.getSalaryGradeName());
 		ps.setInt(2, model.getStep());
        ps.setBigDecimal(3, model.getBasicSalary());
 		ps.setInt(4, model.getSalaryGradeId());         
        ps.executeUpdate();
        conn.commit();
        ps.close();
        sql = null;
 	}
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}

}
