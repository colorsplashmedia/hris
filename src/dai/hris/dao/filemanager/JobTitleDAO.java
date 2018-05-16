package dai.hris.dao.filemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dai.hris.dao.DBConstants;
import dai.hris.model.JobTitle;

public class JobTitleDAO {
	
Connection conn = null;
    
    public JobTitleDAO() {
        
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("JobTitleDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("JobTitleDAO :" + e.getMessage());
		}
    	
    }
    
    public JobTitle getJobTitle(int jobTitleId) throws SQLException, Exception {
    	JobTitle model = null;
    	
    	String sql = "SELECT * FROM jobTitle where jobTitleId = ?";
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, jobTitleId);

	    ResultSet rs = ps.executeQuery();
	    
	    if (rs.next()) {
	    	model = new JobTitle();
	    	
	    	model.setJobTitleId(jobTitleId);
	    	model.setJobTitle(rs.getString("jobTitle"));
	    	model.setSalaryGradeId(rs.getInt("salaryGradeId"));
		}
	    sql = null;
	    ps.close();
	    rs.close();	 
	    return model;
    }
    
    public boolean isExist(String name) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM jobTitle WHERE jobTitle = '");
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
    	
    	
    	sql.append("SELECT * FROM jobTitle WHERE jobTitle = '");
    	sql.append(name);
    	sql.append("' and jobTitleId <> ");  
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
    
    public  int getCountWithFilter(String filter) throws SQLException, Exception {
    	int count = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT count(*) as ctr FROM jobTitle WHERE jobTitle like '%"); 
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
    	
    	
    	sql.append("SELECT count(*) as ctr FROM jobTitle");   	
    	  	
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
    
    public  List<JobTitle> getAll(int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {

    	StringBuffer sql = new StringBuffer();   
    	
    	if(jtSorting == null){
    		jtSorting = "jobTitle ASC";
    	}
    	
    	sql.append("WITH OrderedList AS (SELECT jobTitleId, jobTitle, salaryGradeId, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM jobTitle ) ");
    	sql.append("SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		
		System.out.println("SQL: " + sql.toString());   	
    		
		PreparedStatement ps = conn.prepareStatement(sql.toString());		
		
	    ResultSet rs = ps.executeQuery();
	    List<JobTitle> list = new ArrayList<JobTitle>();
	    
	    while (rs.next()) {
	    	JobTitle jobTitle = new JobTitle();
	    	jobTitle.setJobTitleId(rs.getInt("jobTitleId"));
	    	jobTitle.setJobTitle(rs.getString("jobTitle"));	    	 
	    	jobTitle.setSalaryGradeId(rs.getInt("salaryGradeId"));
	    	list.add(jobTitle);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;    		

	}
    
    public List<JobTitle> getAllWithFilter(int jtStartIndex, int jtPageSize, String jtSorting, String filter) throws SQLException, Exception {
    	
		StringBuffer sql = new StringBuffer(); 
		
		if(jtSorting == null){
    		jtSorting = "jobTitle ASC";
    	}
		
		sql.append("WITH OrderedList AS (SELECT jobTitleId, jobTitle, salaryGradeId, ROW_NUMBER() OVER(ORDER BY ");	
    	sql.append(jtSorting);    	
    	sql.append(") AS RowNumber FROM jobTitle WHERE jobTitle like '%");
    	sql.append(filter);    	
    	sql.append("%') SELECT * FROM OrderedList WHERE RowNumber BETWEEN ");
    	sql.append(jtStartIndex);
    	sql.append(" AND ");
    	sql.append(jtStartIndex + jtPageSize);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("SQL: " + sql.toString());
		
		

	    ResultSet rs = ps.executeQuery();
	    List<JobTitle> list = new ArrayList<JobTitle>();
	    
	    while (rs.next()) {
	    	JobTitle jobTitle = new JobTitle();
	    	jobTitle.setJobTitleId(rs.getInt("jobTitleId"));
	    	jobTitle.setJobTitle(rs.getString("jobTitle"));	    
	    	jobTitle.setSalaryGradeId(rs.getInt("salaryGradeId"));
	    	list.add(jobTitle);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;     

	}
    
    
    public  void add(JobTitle jobTitle) throws SQLException, Exception {
  		String sql = "INSERT INTO jobTitle (jobTitle, salaryGradeId) VALUES (?,?)";
  		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, jobTitle.getJobTitle());
        ps.setInt(2, jobTitle.getSalaryGradeId());
        
        ps.executeUpdate();
          
        ResultSet keyResultSet = ps.getGeneratedKeys();
         int generatedAutoIncrementId = 0;
         if (keyResultSet.next()) {
          	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
          	jobTitle.setJobTitleId(generatedAutoIncrementId);
          	conn.commit();
          }

          ps.close();
          keyResultSet.close();

  	}
    
    public boolean checkIfRecordHasBeenUsed(int id) throws SQLException, Exception {
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM employee WHERE jobTitleId = ");   	
    	sql.append(id);
    	  	
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		System.out.println("checkIfRecordHasBeenUsed JobTitle: " + sql.toString());

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
    
    public  void delete(int id) throws SQLException, Exception {
		String sql = "DELETE from jobTitle where jobTitleId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        conn.commit();
        ps.close();

	}
    
    public  void update(JobTitle jobTitle) throws SQLException, Exception {
		
 		String sql = "UPDATE jobTitle SET jobTitle = ?, salaryGradeId = ?  WHERE jobTitleId = ?";
 		PreparedStatement ps  = conn.prepareStatement(sql);
 		ps.setString(1, jobTitle.getJobTitle());
 		ps.setInt(2, jobTitle.getSalaryGradeId());
 		ps.setInt(3, jobTitle.getJobTitleId());
 		
         
         ps.executeUpdate();
         conn.commit();
         ps.close();

 	}
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}

}
