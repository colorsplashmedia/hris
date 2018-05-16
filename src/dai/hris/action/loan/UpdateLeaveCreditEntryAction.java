/**
 * 
 */
package dai.hris.action.loan;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import com.google.gson.Gson;
import dai.hris.model.LeaveCard;
import dai.hris.service.loan.ILoanEntryService;
import dai.hris.service.loan.LoanEntryService;


@WebServlet("/UpdateLeaveCreditEntryAction")
public class UpdateLeaveCreditEntryAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		LeaveCard model = new LeaveCard();		
		try {
			BeanUtils.populate(model, request.getParameterMap());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		ILoanEntryService service = new LoanEntryService();

		try {
			service.updateLeaveCredit(model);
			response.getWriter().print("{\"Result\":\"OK\"}");
		} catch (Exception e) {
			e.printStackTrace();
			String error = "{\"Result\":\"ERROR\"}";
			 response.getWriter().print(error);
		}

		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doGet(request, response);
	}

}
