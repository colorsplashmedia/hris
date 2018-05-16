package dai.hris.action.filemanager.employeememo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import dai.hris.model.CaseRatePayment;
import dai.hris.service.payroll.ICaseRatePaymentService;
import dai.hris.service.payroll.impl.CaseRatePaymentService;

/**
 * Servlet implementation class GetEmployeeCaseRatePayment
 */

//get all of the employee's received memos (employee is the recipient)
@WebServlet("/GetEmployeeCaseRatePaymentByToRecipientAction")
public class GetEmployeeCaseRatePaymentByToRecipientAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Gson gson = new Gson();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetEmployeeCaseRatePaymentByToRecipientAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet GetEmployeeCaseRatePaymentByToRecipientAction");
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost GetEmployeeCaseRatePaymentByToRecipientAction");
		List<CaseRatePayment> list = new ArrayList<CaseRatePayment>();
		ICaseRatePaymentService service = new CaseRatePaymentService();
		
		int toRecipientEmpId = Integer.parseInt(request.getParameter("toRecipientEmpId"));
		int count = 0;
		String startIndex = request.getParameter("jtStartIndex");
		String pageSize = request.getParameter("jtPageSize");
//		String name = request.getParameter("name");
		String sorting = request.getParameter("jtSorting");
		
		try {
//			if(name != null && !name.isEmpty()){
//				count=service.getCountWithFilter(toRecipientEmpId, name);
//			} else {
//				count=service.getCount(toRecipientEmpId);
//			}
			
			count=service.getCount(toRecipientEmpId);
			
			
			if(startIndex != null && startIndex.length() > 0) {
				//do nothing
			} else {
				startIndex = "0";
			}
			
			if(pageSize != null && pageSize.length() > 0) {
				//do nothing
			} else {
				pageSize = "" + count;
			}
			
			int startPageIndex = Integer.parseInt(startIndex);
			int numRecordsPerPage = Integer.parseInt(pageSize);			
			

			
			list = service.getCaseRatePaymentListByEmpId(toRecipientEmpId, startPageIndex,numRecordsPerPage, sorting);
			
			
			//list = employeeMemoService.getEmployeeMemoListByToRecipientEmpId(toRecipientEmpId);
		} catch (SQLException sqle) {
			// TODO Auto-generated catch block
			sqle.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

        
		JsonElement element = gson.toJsonTree(list,new TypeToken<List<CaseRatePayment>>(){}.getType());
		JsonArray jsonArray = element.getAsJsonArray();
		String listData=jsonArray.toString();           //Return Json in the format required by jTable plugin
		listData = "{\"Result\":\"OK\",\"Records\":"+listData+",\"TotalRecordCount\":"+count+"}";   
		       response.getWriter().print(listData);
		
	}

}
