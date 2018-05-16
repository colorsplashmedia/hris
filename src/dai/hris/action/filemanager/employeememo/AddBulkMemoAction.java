package dai.hris.action.filemanager.employeememo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dai.hris.model.Employee;
import dai.hris.model.EmployeeMemo;
import dai.hris.service.filemanager.employeememo.EmployeeMemoService;
import dai.hris.service.filemanager.employeememo.IEmployeeMemoService;


@WebServlet("/AddBulkMemoAction")
public class AddBulkMemoAction extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IEmployeeMemoService service = new EmployeeMemoService();
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		
		String memoFiledDate = request.getParameter("memoFiledDate");	
		String ccRecipient = request.getParameter("ccRecipient");	
		String fromSender = request.getParameter("fromSender");	
		String subject = request.getParameter("subject");	
		String message = request.getParameter("message");	
		String remarks = request.getParameter("remarks");	
		String empId[] = request.getParameterValues("empId")[0].split(",");		
		

		EmployeeMemo model = new EmployeeMemo();	
		
		model.setMemoFiledDate(memoFiledDate);
		model.setCcRecipient(ccRecipient);
		model.setFromSender(fromSender);
		model.setSubject(subject);
		model.setMessage(message);
		model.setRemarks(remarks);
		model.setCreatedBy(employeeLoggedIn.getEmpId());
		
		
		try {
			for(int i=0;i<empId.length;i++) {
				model.setToRecipientEmpId(Integer.parseInt(empId[i]));
				service.add(model);
			}		
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}		

		String json = gson.toJson("SUCCESS");
		System.out.println("json: " + json);
		String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
		response.getWriter().print(jsonData);

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
