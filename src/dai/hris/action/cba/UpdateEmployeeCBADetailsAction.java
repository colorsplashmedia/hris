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
import dai.hris.model.EmployeeCBADetails;
import dai.hris.service.cba.EmployeeCBAService;
import dai.hris.service.cba.IEmployeeCBAService;

@WebServlet("/UpdateEmployeeCBADetailsAction")
public class UpdateEmployeeCBADetailsAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IEmployeeCBAService service = new EmployeeCBAService();
	Gson gson = new Gson();

    public UpdateEmployeeCBADetailsAction() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost UpdateEmployeeCBAAction");
		EmployeeCBADetails model = new EmployeeCBADetails();
		

		try {
			BeanUtils.populate(model, request.getParameterMap());	
			
			service.updateDetails(model);
			
			String json = gson.toJson(model);
			String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
			System.out.println(jsonData);
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
