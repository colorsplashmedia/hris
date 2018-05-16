package dai.hris.action.payroll;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dai.hris.model.Deduction;
import dai.hris.service.payroll.IDeductionService;
import dai.hris.service.payroll.impl.DeductionService;

/**
 * Servlet implementation class GetDeductionAction
 */
@WebServlet("/GetDeductionAction")
public class GetDeductionAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Gson gson = new Gson();

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
		IDeductionService service = new DeductionService();
		List<Deduction> list = null;
		
		String id = request.getParameter("id");
		if (id == null) {
			int count = 0;
			String startIndex = request.getParameter("jtStartIndex");
			String pageSize = request.getParameter("jtPageSize");
			String name = request.getParameter("name");
			String sorting = request.getParameter("jtSorting");
			try {
				if(name != null && !name.isEmpty()){
					count=service.getCountWithFilter(name);
				} else {
					count=service.getCount();
				}
				
				
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
				
				if(name != null && !name.isEmpty()){
					list = service.getAllWithFilter(startPageIndex,numRecordsPerPage, sorting, name);				
				} else {
					list = service.getAll(startPageIndex,numRecordsPerPage, sorting);
				}
				//List<Deduction> list = svc.getAll();
				String json = gson.toJson(list);
				json = "{\"Result\":\"OK\",\"Records\":"+ json + "}";
				response.getWriter().print(json);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			try {
				Deduction in = service.getDeductionById(Integer.parseInt(id));
				String json = gson.toJson(in);
				json = "{\"Result\":\"OK\",\"Records\":"+ json + "}";
				response.getWriter().print(json);
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().print("{\"Result\":\"ERROR\",\"Message\":\"ERROR: GetDeductionAction: "+e.getMessage()+"\"}");
			}
		}
	}

}
