package dai.hris.action.reports;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.log4j.Logger;

import dai.hris.dao.DBConstants;

/**
 * Servlet implementation class ViewCaseRateReport
 */
@WebServlet("/ViewCaseRateReport")
public class ViewCaseRateReport extends HttpServlet {
	private static final long serialVersionUID = 5676921778006010751L;
	private static Logger logger = Logger.getLogger(ViewCaseRateReport.class);
	
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/pdf");

		
		
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		
		InputStream reportStream = getClass().getClassLoader().getResourceAsStream( "dai/hris/reports/CaseRateReport.jasper");
		if(reportStream == null){
			logger.debug("reportStream is NULL");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("HOSPITAL_NAME", ReportConstant.COMPANY_NAME);
		map.put("dateFrom", dateFrom);
		map.put("dateTo", dateTo);

		try {
			Connection connection = null;
			Class.forName(DBConstants.DB_DRIVER_CLASS_NAME);
			connection = DriverManager.getConnection(DBConstants.MS_SQL_DB_URL); 			
			JasperRunManager.runReportToPdfStream(reportStream,	response.getOutputStream(), map, connection);
			connection.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
