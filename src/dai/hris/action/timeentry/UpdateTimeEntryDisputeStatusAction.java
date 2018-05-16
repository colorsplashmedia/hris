package dai.hris.action.timeentry;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dai.hris.model.Employee;
import dai.hris.service.timeentry.ITimeEntryService;
import dai.hris.service.timeentry.TimeEntryService;




/**
 * Servlet implementation class for Servlet: LoginAction
 *
 */
@WebServlet("/UpdateTimeEntryDisputeStatusAction")
 public class UpdateTimeEntryDisputeStatusAction extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	 private static final long serialVersionUID = -6185891323760506163L;	
	 Gson gson = new Gson();
	 /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public UpdateTimeEntryDisputeStatusAction() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doPost(request, response);
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String approverType = request.getSession().getAttribute("approverType").toString();
		String isAdminChecked = request.getSession().getAttribute("isAdminChecked").toString();
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		//String resolutionCategory = request.getParameter("resolutionCategory");
		//String newStatus = request.getParameter("newStatus");	
		
		String newStatus = "";
		String timeEntryDisputeId = request.getParameter("timeEntryDisputeId");		
			
        ITimeEntryService timeEntryService = new TimeEntryService();        
        
		try {
			
			String approveFlag = request.getParameter("approveFlag");     
			//0 - FOR APPROVAL
            //1 - NOT APPROVED
            //2 - FOR UNIT SUPERVISOR APPROVAL
            //3 - FOR SECTION SUPERVISOR APPROVAL
            //4 - FOR HR APPROVAL
            //5 - FOR ADMIN APPROVAL
			//6 - APPROVED
			if("N".equals(approveFlag)){
				newStatus = "1";
			} else {
				if("ADMIN".equals(approverType)){
					newStatus = "6";
				} else if("HRADMIN".equals(approverType)) {
					if("Y".equals(isAdminChecked)){
						newStatus = "5";
					} else {
						newStatus = "6";
					}
				} else if("SECTIONADMIN".equals(approverType)) {
					newStatus = "4";
				} else if("UNITADMIN".equals(approverType)) {
					newStatus = "3";
				} else if("SUBUNITADMIN".equals(approverType)) {
					newStatus = "2";
				}
			}			
			
			//0 - FOR APPROVAL
            //1 - NOT APPROVED
            //2 - FOR UNIT SUPERVISOR APPROVAL
            //3 - FOR SECTION SUPERVISOR APPROVAL
            //4 - FOR HR APPROVAL
            //5 - FOR ADMIN APPROVAL
			//6 - APPROVED
			timeEntryService.updateTimeEntryDispute(employeeLoggedIn.getEmpId(), newStatus, Integer.parseInt(timeEntryDisputeId));
			
	
        	
			String json = gson.toJson("YES");
			System.out.println("json: " + json);
			String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
			response.getWriter().print(jsonData);
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}   	  	    
}