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
import dai.hris.model.DisbursementVoucher;
import dai.hris.service.cba.EmployeeCBAService;
import dai.hris.service.cba.IEmployeeCBAService;
/**
 * Servlet implementation class SaveDisbursementVoucherAction
 */
@WebServlet("/SaveDisbursementVoucherAction")
public class SaveDisbursementVoucherAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IEmployeeCBAService service = new EmployeeCBAService();
	Gson gson = new Gson();


    public SaveDisbursementVoucherAction() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost SaveDisbursementVoucherAction");
		DisbursementVoucher model = new DisbursementVoucher();
	

		try {			    

			//BeanUtils.populate(model, request.getParameterMap());
			
			model.setModeOfPayment(request.getParameter("modeOfPayment"));
			model.setFundCluster(request.getParameter("fundCluster"));
			model.setDisbursementDate(request.getParameter("disbursementDate"));
			model.setDvNo(request.getParameter("dvNo"));
			model.setOthersDetail(request.getParameter("othersDetail"));
			model.setPayee(request.getParameter("payee"));
			model.setTin(request.getParameter("tin"));
			model.setOrs(request.getParameter("ors"));
			model.setAddress(request.getParameter("address"));
			model.setParticulars(request.getParameter("particulars"));
			model.setResponsibilityCenter(request.getParameter("responsibilityCenter"));
			model.setMfo(request.getParameter("mfo"));
			model.setAmount(request.getParameter("amount") != null ? new BigDecimal(request.getParameter("amount")) : BigDecimal.ZERO);
			model.setTotalAmount(request.getParameter("amount") != null ? new BigDecimal(request.getParameter("amount")) : BigDecimal.ZERO);
			model.setAccountingTitle(request.getParameter("accountingTitle"));
			model.setUacsCode(request.getParameter("uacsCode"));
			model.setDebit(request.getParameter("debit") != null && request.getParameter("debit").length() > 0 ? new BigDecimal(request.getParameter("debit")) : BigDecimal.ZERO);
			model.setCredit(request.getParameter("credit") != null && request.getParameter("credit").length() > 0 ? new BigDecimal(request.getParameter("credit")) : BigDecimal.ZERO);
			model.setAmountInWords(request.getParameter("amountInWords"));
			model.setSignatory1(request.getParameter("signatory1"));
			model.setSignatory2(request.getParameter("signatory2"));
			model.setSignatory3(request.getParameter("signatory3"));
			model.setPosition1(request.getParameter("position1"));
			model.setPosition2(request.getParameter("position2"));
			model.setPosition3(request.getParameter("position3"));
			model.setCheckNo(request.getParameter("checkNo"));
			model.setCheckDate(request.getParameter("checkDate"));
			model.setBankDetails(request.getParameter("bankDetails"));
			model.setOrNo(request.getParameter("orNo"));
			model.setOrDate(request.getParameter("orDate"));
			model.setJevNo(request.getParameter("jevNo"));
			model.setCertifiedMethod(request.getParameter("certifiedMethod"));
			model.setPrintedName(request.getParameter("printedName"));
			model.setDisbursementVoucherId(request.getParameter("disbursementVoucherId") != null && request.getParameter("disbursementVoucherId").length() > 0 ? Integer.parseInt(request.getParameter("disbursementVoucherId")) : null);
			
			if("Y".equals(request.getParameter("forEdit"))) {
				service.updateDisbursementVoucher(model);
			} else {
				service.addDisbursementVoucher(model);
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
