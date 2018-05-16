package dai.hris.action.payroll;

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
import dai.hris.model.MedicareShareAllowance;
import dai.hris.service.payroll.IMedicareShareAllowanceService;
import dai.hris.service.payroll.impl.MedicareShareAllowanceService;

/**
 * Servlet implementation class GetMedicareShareAllowanceAction
 */
@WebServlet("/GetMedicareShareAllowanceAction")
public class GetMedicareShareAllowanceAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	IMedicareShareAllowanceService service = new MedicareShareAllowanceService();
	Gson gson = new Gson();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		int empId = Integer.parseInt(request.getParameter("empId"));
//		try {
//			List<MedicareShareAllowance> msaList = svc.getAllByEmployeeId(empId);
//			String json = gson.toJson(msaList);
//            System.out.println("json: " + json);
//			String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
//			response.getWriter().print(jsonData);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		List<MedicareShareAllowance> list = new ArrayList<MedicareShareAllowance>();

		
		int toRecipientEmpId = Integer.parseInt(request.getParameter("toRecipientEmpId"));
		int count = 0;
		String startIndex = request.getParameter("jtStartIndex");
		String pageSize = request.getParameter("jtPageSize");

		String sorting = request.getParameter("jtSorting");
		
		try {

			
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
			

			
			list = service.getMedicareShareAllowanceListByEmpId(toRecipientEmpId, startPageIndex,numRecordsPerPage, sorting);
			
			
			
		} catch (SQLException sqle) {
			// TODO Auto-generated catch block
			sqle.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    String json = gson.toJson(list);
	    System.out.println("json: " + json);
 
	    json = "{\"Result\":\"OK\",\"Records\":"+ json + "}";
        response.getWriter().print(json);
	}
}
