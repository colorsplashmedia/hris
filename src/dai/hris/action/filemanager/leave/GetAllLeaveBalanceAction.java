package dai.hris.action.filemanager.leave;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import dai.hris.model.Employee;
//import dai.hris.model.LeaveBalance;
import dai.hris.model.LeaveCard;
import dai.hris.service.loan.ILoanEntryService;
import dai.hris.service.loan.LoanEntryService;

@WebServlet("/GetAllLeaveBalanceAction")
public class GetAllLeaveBalanceAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		//int empId = Integer.parseInt(request.getParameter("empId"));
		//get the employeeLoggedIn from session instead
//		List<LeaveBalance> leaveBalanceList = new ArrayList<LeaveBalance>();
		HttpSession session = request.getSession(true);  
		Employee employee = (Employee) session.getAttribute("employeeLoggedIn");		
		//ILeaveService service = new LeaveService();
//		double leaveCount = 0d;
//		Map<String, Object> allLeavesCountMap = new TreeMap<String, Object>();
		
//		 DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		
		 LeaveCard lastLeaveCard = null; 
		
		try {
//			System.out.println(employee.getEmpId());
//			System.out.println(EmployeeUtil.getCurrentSystemDateSqlFormat());
//			leaveCount = service.getAllEarnedLeavesCountByEmpId(employee.getEmpId(), EmployeeUtil.getCurrentSystemDateSqlFormat(), 1.25);
//			Date employmentStartDate = new Date(formatter.parse(employee.getEmploymentDate()).getTime());
//			System.out.println(employmentStartDate);
//			//use 3 = Approved status
//			allLeavesCountMap = service.getAllLeavesCountByEmpId(employee.getEmpId(), employmentStartDate, EmployeeUtil.getCurrentSystemDateSqlFormat(), 3);
//			allLeavesCountMap.put("All Earned Leaves", leaveCount);
//			
//			
//			LeaveBalance leaveBalance = null;
//			for (Map.Entry<String, Object> entry : allLeavesCountMap.entrySet()) {
//				leaveBalance = new LeaveBalance();
//				leaveBalance.setCategory(entry.getKey());
//				leaveBalance.setCount(entry.getValue());
//				leaveBalanceList.add(leaveBalance);
//			}
			ILoanEntryService service = new LoanEntryService();
			List<LeaveCard> list = service.getAllLeaveCreditEntryByEmpId(employee.getEmpId());			
			
			
			
			for(LeaveCard model : list){
				lastLeaveCard = model;
			}
			
			
		}
		catch(Exception e ){
			e.printStackTrace();
		}	

	    String json = gson.toJson(lastLeaveCard);
 
	    json = "{\"Result\":\"OK\",\"Records\":"+ json + "}";
	    System.out.println(json);
        response.getWriter().print(json);

	}
	

}
