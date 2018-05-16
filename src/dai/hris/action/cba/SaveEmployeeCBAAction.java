package dai.hris.action.cba;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import com.google.gson.Gson;
import dai.hris.model.EmployeeCBA;
import dai.hris.service.cba.EmployeeCBAService;
import dai.hris.service.cba.IEmployeeCBAService;
/**
 * Servlet implementation class GetEmployeePayrollInfoAction
 */
@WebServlet("/SaveEmployeeCBAAction")
public class SaveEmployeeCBAAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IEmployeeCBAService service = new EmployeeCBAService();
	Gson gson = new Gson();


    public SaveEmployeeCBAAction() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost SaveEmployeeCBAAction");
		EmployeeCBA empCBA = new EmployeeCBA();
	
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		Enumeration<String> names = request.getParameterNames();
//		while (names.hasMoreElements()) {
//		  String name = (String) names.nextElement();
//		  map.put(name, request.getParameterValues(name));
//		}
		try {
			    
//			BeanUtils.populate(empCBA, map);		
			BeanUtils.populate(empCBA, request.getParameterMap());
			
			service.add(empCBA);
			String json = gson.toJson(empCBA);
			System.out.println("json: " + json);
			String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
			response.getWriter().print(jsonData);
			
		} catch (SQLException e) {
			e.printStackTrace();
			String error = "{\"Result\":\"ERROR\"}";
			 response.getWriter().print(error);
		} catch (Exception e) {
			e.printStackTrace();
			String error = "{\"Result\":\"ERROR\"}";
			 response.getWriter().print(error);
		}
	}

}
