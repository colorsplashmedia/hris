package dai.hris.action.filemanager;

import java.io.IOException;
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
import dai.hris.model.Holiday;
import dai.hris.service.filemanager.holiday.HolidayService;
import dai.hris.service.filemanager.holiday.IHolidayService;

@WebServlet("/GetAllHolidayAction")
public class GetAllHolidayAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		doPost(request, response);


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		IHolidayService service = new HolidayService();
		List<Holiday> list = null;
		
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
		}
		catch(Exception e ){
			e.printStackTrace();
		}


		JsonElement element = gson.toJsonTree(list,new TypeToken<List<Holiday>>(){}.getType());
		JsonArray jsonArray = element.getAsJsonArray();
		String listData=jsonArray.toString();           //Return Json in the format required by jTable plugin
		listData = "{\"Result\":\"OK\",\"Records\":"+listData+",\"TotalRecordCount\":"+count+"}";   
		       response.getWriter().print(listData);
		
//	    String json = gson.toJson(list);
//	    System.out.println("json: " + json);
// 
//	    json = "{\"Result\":\"OK\",\"Records\":"+ json + "}";
//        response.getWriter().print(json);
	}
	

}
