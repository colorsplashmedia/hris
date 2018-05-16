package dai.hris.dao.filemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dai.hris.dao.DBConstants;
import dai.hris.model.EducationalBackground;

/**
 * 
 * @author danielpadilla
 *
 */
public class EducationalBackgroundDAO {
	Connection conn = null;
	
	public EducationalBackgroundDAO() {
		
    	try {

    		/* MS SQL CODE */    		
    		Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
        	conn = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL);    		
    		conn.setAutoCommit(false);

		} catch (SQLException sqle) {
			System.out.println("EducationBackgroundDAO :" + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("EducationBackgroundDAO :" + e.getMessage());
		}
	}
	
	public boolean isExist(String name, String course) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM educationalBkgrnd WHERE school = '");
    	sql.append(name);
    	sql.append("' AND course = '");
    	sql.append(course);
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
    
    public boolean isExistUpdate(String name, int id, String course) throws SQLException, Exception {
    	
    	StringBuffer sql = new StringBuffer();
    	
    	
    	sql.append("SELECT * FROM educationalBkgrnd WHERE school = '");
    	sql.append("' AND course = '");
    	sql.append(course);
    	sql.append(name);
    	sql.append("' and educBkgrndId <> ");  
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
		//no dependencies		
		return false;
	}
	
	/**tested ok 062315
	 * get only specific Educational background details via educBkgrndId
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public  EducationalBackground getEmployeeEducationalBackgroundByEducBkgrndId(int educBkgrndId) throws SQLException, Exception {			    		
		String sql = "SELECT educBkgrndId, empId, school, course, yearGraduated, remarks FROM educationalBkgrnd where educBkgrndId = ?";
		EducationalBackground educationalBackground = null;
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, educBkgrndId);
	
	    ResultSet rs = ps.executeQuery();
	    
	    while (rs.next()) {
	    	 educationalBackground = new EducationalBackground();
	    	 educationalBackground.setEducBkgrndId(rs.getInt("educBkgrndId"));
	    	 educationalBackground.setEmpId(rs.getInt("empId"));
	    	 educationalBackground.setSchool(rs.getString("school"));
	    	 educationalBackground.setCourse(rs.getString("course"));
	    	 educationalBackground.setYearGraduated(rs.getString("yearGraduated"));
	    	 educationalBackground.setYearAttended(rs.getString("yearAttended"));
	    }
	    
	    ps.close();
	    rs.close();      
	    return educationalBackground;		
	}
	
	/**tested ok 062315
	 * get Employee's educational background as an arrayList
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<EducationalBackground> getEmployeeEducationalBackgroundListByEmpId(int empId) throws SQLException, Exception {			    		
		String sql = "SELECT educBkgrndId, empId, school, course, yearGraduated, remarks, yearAttended FROM educationalBkgrnd where empId = ?";		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, empId);
	
	    ResultSet rs = ps.executeQuery();
	    ArrayList<EducationalBackground> list = new ArrayList<EducationalBackground>();
	    
	    while (rs.next()) {
	    	 EducationalBackground educationalBackground = new EducationalBackground();
	    	 educationalBackground.setEducBkgrndId(rs.getInt("educBkgrndId"));
	    	 educationalBackground.setEmpId(rs.getInt("empId"));
	    	 educationalBackground.setSchool(rs.getString("school"));
	    	 educationalBackground.setCourse(rs.getString("course"));
	    	 educationalBackground.setYearGraduated(rs.getString("yearGraduated"));
	    	 educationalBackground.setYearAttended(rs.getString("yearAttended"));
	    	 educationalBackground.setRemarks(rs.getString("remarks"));
	    	 list.add(educationalBackground);
	    }
	    
	    ps.close();
	    rs.close();      
	    return list;		
	}
	    
	/**
	 * tested ok 062315
	 * @param educationalBackground
	 * @throws SQLException
	 * @throws Exception
	 */
	public int add(EducationalBackground educationalBackground) throws SQLException, Exception {
		String sql = "INSERT INTO educationalBkgrnd (empId, school, course, yearAttended, yearGraduated, remarks) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement ps  = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	    ps.setInt(1, educationalBackground.getEmpId());
	    ps.setString(2, educationalBackground.getSchool());
	    ps.setString(3, educationalBackground.getCourse());
	    ps.setString(4, educationalBackground.getYearAttended());
	    ps.setString(5, educationalBackground.getYearGraduated());
	    ps.setString(6, educationalBackground.getRemarks());
	    
	    int count = ps.executeUpdate();
	    int status = 0;  
	    ResultSet keyResultSet = ps.getGeneratedKeys();
	    int generatedAutoIncrementId = 0;
	    if (keyResultSet.next()) {
	      	generatedAutoIncrementId = (int) keyResultSet.getInt(1);
	      	educationalBackground.setEducBkgrndId(generatedAutoIncrementId);
	      	conn.commit();
	     }
		
	    ps.close();
	    keyResultSet.close();
		if (count == 1) {
			 status = generatedAutoIncrementId;
		}		
	    return status;
	}

	    
	public void delete(int id)  throws SQLException, Exception {
		String sql = "DELETE FROM educationalBkgrnd WHERE educBkgrndId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        conn.commit();
        ps.close();
	}
	
	/**
	 * tested ok 062315
	 * @param educationalBackground
	 * @throws SQLException
	 * @throws Exception
	 */
    public int update(EducationalBackground educationalBackground) throws SQLException, Exception {		
		String sql = "UPDATE educationalBkgrnd SET empId=?, school=?, course=?, yearGraduated=?, remarks=?, yearAttended=? WHERE educBkgrndId = ?";
		PreparedStatement ps  = conn.prepareStatement(sql);
		ps.setInt(1, educationalBackground.getEmpId());	 		
		ps.setString(2, educationalBackground.getSchool());
		ps.setString(3, educationalBackground.getCourse());
		ps.setString(4, educationalBackground.getYearGraduated());
		ps.setString(5, educationalBackground.getRemarks());
		ps.setString(6, educationalBackground.getYearAttended());
		ps.setInt(7, educationalBackground.getEducBkgrndId());	 
			
		int count = ps.executeUpdate();
		conn.commit();
		ps.close();
		return count;

 	}
    
    public void closeConnection() throws SQLException, Exception {
		conn.close();
	}
}
