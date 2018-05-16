/**
 * 
 */
package dai.hris.action.overtime;


import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import com.google.gson.Gson;
import dai.hris.model.EmployeeOffSet;
import dai.hris.service.overtime.EmployeeOffSetService;
import dai.hris.service.overtime.IEmployeeOffSetService;


@WebServlet("/UpdateOffSetAction")
public class UpdateOffSetAction extends HttpServlet {

	private static final long serialVersionUID = 1L;
	IEmployeeOffSetService service = new EmployeeOffSetService();
	Gson gson = new Gson();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		System.out.println("doPost UpdateOutOfOfficeAction");

		//instead of getting all attributes manually, try to use Beanutils
		EmployeeOffSet employeeOffSet = new EmployeeOffSet();
		HashMap<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
		  String name = (String) names.nextElement();
		  map.put(name, request.getParameterValues(name));
		}
		try {
		    
			BeanUtils.populate(employeeOffSet, map);
			
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		try {

			service.update(employeeOffSet);
					
			String json = gson.toJson(employeeOffSet);
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
