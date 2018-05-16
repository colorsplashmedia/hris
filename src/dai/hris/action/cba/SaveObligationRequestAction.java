package dai.hris.action.cba;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dai.hris.model.ObligationRequest;
import dai.hris.service.cba.EmployeeCBAService;
import dai.hris.service.cba.IEmployeeCBAService;


/**
 * Servlet implementation class SaveObligationRequestAction
 */
@WebServlet("/SaveObligationRequestAction")
public class SaveObligationRequestAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IEmployeeCBAService service = new EmployeeCBAService();
	Gson gson = new Gson();


    public SaveObligationRequestAction() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost SaveObligationRequestAction");
		ObligationRequest model = new ObligationRequest();
	

		try {			    

			//BeanUtils.populate(model, request.getParameterMap());
			
			
			model.setFundCluster(request.getParameter("fundCluster"));
			model.setObligationDate(request.getParameter("obligationDate"));
			model.setPayee(request.getParameter("payee"));
			model.setDvNo(request.getParameter("dvNo"));
			model.setAddress(request.getParameter("address"));
			model.setParticulars(request.getParameter("particulars"));
			model.setResponsibilityCenter(request.getParameter("responsibilityCenter"));
			model.setMfo(request.getParameter("mfo"));
			model.setAmount(request.getParameter("amount") != null ? new BigDecimal(request.getParameter("amount")) : BigDecimal.ZERO);
			model.setTotalAmount(request.getParameter("amount") != null ? new BigDecimal(request.getParameter("amount")) : BigDecimal.ZERO);
			model.setUacsCode(request.getParameter("uacsCode"));
			model.setAmountInWords(request.getParameter("amountInWords"));
			model.setSignatory1(request.getParameter("signatory1"));
			model.setSignatory2(request.getParameter("signatory2"));
			model.setPosition1(request.getParameter("position1"));
			model.setPosition2(request.getParameter("position2"));
			model.setJevNo(request.getParameter("jevNo"));
			
			model.setObligationAmount(request.getParameter("obligationAmount") != null ? new BigDecimal(request.getParameter("obligationAmount")) : BigDecimal.ZERO);
			model.setPaymentAmount(request.getParameter("paymentAmount") != null ? new BigDecimal(request.getParameter("paymentAmount")) : BigDecimal.ZERO);
			model.setNotDueAmount(request.getParameter("notDueAmount") != null ? new BigDecimal(request.getParameter("notDueAmount")) : BigDecimal.ZERO);
			model.setDemandAmount(request.getParameter("demandAmount") != null ? new BigDecimal(request.getParameter("demandAmount")) : BigDecimal.ZERO);
			model.setRefDate(request.getParameter("refDate"));
			model.setRefParticular(request.getParameter("refParticular"));			
			model.setObligationRequestId(request.getParameter("obligationRequestId") != null && request.getParameter("obligationRequestId").length() > 0 ? Integer.parseInt(request.getParameter("obligationRequestId")) : 0);
			
			
			
			if("Y".equals(request.getParameter("forEdit"))) {
				service.updateObligationRequest(model);
			} else {
				service.addObligationRequest(model);
			}
			
			String json = gson.toJson(model);
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
