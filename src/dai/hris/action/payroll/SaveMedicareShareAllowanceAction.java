package dai.hris.action.payroll;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import com.google.gson.Gson;
import dai.hris.model.MedicareShareAllowance;
import dai.hris.service.payroll.IMedicareShareAllowanceService;
import dai.hris.service.payroll.impl.MedicareShareAllowanceService;

@WebServlet("/SaveMedicareShareAllowanceAction")
public class SaveMedicareShareAllowanceAction extends HttpServlet {
	private static final long serialVersionUID = 5398739944589430743L;

	private IMedicareShareAllowanceService service = new MedicareShareAllowanceService();
	Gson gson = new Gson();

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
				
		try {
			
			MedicareShareAllowance model = new MedicareShareAllowance();
			try {
				BeanUtils.populate(model, request.getParameterMap());
			}
			catch(Exception e) {
				e.printStackTrace();
			}	
			
			service.saveOrUpdate(model);			
			
			String json = gson.toJson(model);
			System.out.println("json: " + json);
			String jsonData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
			response.getWriter().print(jsonData);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}
