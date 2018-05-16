/**
 * 
 */
package dai.hris.action.timeentry;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dai.hris.service.cba.EmployeeCBAService;
import dai.hris.service.cba.IEmployeeCBAService;


@WebServlet("/DeletePayrollExclusionAction")
public class DeletePayrollExclusionAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
				
		IEmployeeCBAService service = new EmployeeCBAService();
		
		try {
			
			int empPayrollExclusionId = Integer.parseInt(request.getParameter("empPayrollExclusionId"));
	
			service.deletePayrollExlusionById(empPayrollExclusionId);
			
			RequestDispatcher dispatcher = null;
	        dispatcher = getServletContext().getRequestDispatcher("/viewPayrollExclusionList.jsp");
	        

	        if (dispatcher != null) {
	        	response.setContentType("text/html");
	            dispatcher.include(request, response);
	        }
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
