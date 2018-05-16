package dai.hris.action.loan;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dai.hris.model.LeaveCard;
import dai.hris.service.loan.ILoanEntryService;
import dai.hris.service.loan.LoanEntryService;

@WebServlet("/GetAllLeaveCreditByEmpIdAction")
public class GetAllLeaveCreditByEmpIdAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		
		// TODO Auto-generated method stub

		int empId = Integer.parseInt(request.getParameter("empId"));
		System.out.println("GetAllLeaveCreditByEmpIdAction empId is " +empId);
		ILoanEntryService service = new LoanEntryService();
		List<LeaveCard> list = null;
		try {
			list = service.getAllLeaveCreditEntryByEmpId(empId);
			System.out.println("GetAllLeaveCreditByEmpIdAction with empId " + empId);
		}
		catch(Exception e ){
			e.printStackTrace();
		}
	    String json = gson.toJson(list);
	    System.out.println("json: " + json);
 
	    json = "{\"Result\":\"OK\",\"Records\":"+ json + "}";
        response.getWriter().print(json);

	}
	

}
