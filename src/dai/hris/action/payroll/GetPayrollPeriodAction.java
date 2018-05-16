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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import dai.hris.model.GenericDisplayOptionModel;
import dai.hris.model.PayrollPeriod;
import dai.hris.service.payroll.IPayrollPeriodService;
import dai.hris.service.payroll.impl.PayrollPeriodService;

/**
 * Servlet implementation class GetPayrollPeriodAction
 */
@WebServlet("/GetPayrollPeriodAction")
public class GetPayrollPeriodAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
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
		
		IPayrollPeriodService service = new PayrollPeriodService();
		List<PayrollPeriod> list = null;
		
		String forListJTable = request.getParameter("forListJTable");		
		List<GenericDisplayOptionModel> genericDisplayOptionList = null;
		
		String payrollPeriodStatus = request.getParameter("payrollPeriodStatus");
		
		
		if (forListJTable != null && "true".equalsIgnoreCase(forListJTable)) {	
			int payrollPeriodId = Integer.parseInt(request.getParameter("payrollPeriodId"));
			
			try {	
				PayrollPeriod origModel = service.getPayrollPeriodById(payrollPeriodId, payrollPeriodStatus);
				
				genericDisplayOptionList = new ArrayList <GenericDisplayOptionModel>() ;
				GenericDisplayOptionModel model = new GenericDisplayOptionModel();
			    
				
				if(origModel != null) {					
					model.setValue(origModel.getPayrollPeriodId());
					model.setDisplayText(origModel.getFromDate() + " to " + origModel.getToDate());
				} else {
					model.setValue(0);
					model.setDisplayText("None");
				}
				
				genericDisplayOptionList.add(model);
				
				String json = gson.toJson(genericDisplayOptionList);
			    System.out.println("json: " + json);
		 
			    json = "{\"Result\":\"OK\",\"Options\":"+ json + "}"; 
			    
			} catch (SQLException sqe1) {
				//TODO add proper logging
				System.err.println(sqe1.getMessage());
				sqe1.printStackTrace();
			} catch (Exception e) {
				//TODO add proper logging
				System.err.println(e.getMessage());
			}
		} else {
			int count = 0;
			String startIndex = request.getParameter("jtStartIndex");
			String pageSize = request.getParameter("jtPageSize");
			String name = request.getParameter("name");
			String sorting = request.getParameter("jtSorting");
			
			try {
				//List<PayrollPeriod> ppList = service.getAll();
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
					list = service.getAllWithFilter(startPageIndex,numRecordsPerPage, sorting, name, payrollPeriodStatus);				
				} else {
					list = service.getAll(startPageIndex,numRecordsPerPage, sorting, payrollPeriodStatus);
				}				
				
//				String json = gson.toJson(list);
//	            System.out.println("json: " + json);
//				String jsonData = "{\"Result\":\"OK\",\"Records\":" + json + "}";
//				response.getWriter().print(jsonData);
			} catch (SQLException e) {
				e.printStackTrace();
				String error = "{\"Result\":\"ERROR\"}";
				 response.getWriter().print(error);
			} catch (Exception e) {
				e.printStackTrace();
				String error = "{\"Result\":\"ERROR\"}";
				 response.getWriter().print(error);
			}
			
			String displayOption = request.getParameter("displayOption");		
			String forEdit = request.getParameter("forEdit");		
			
			if (displayOption != null && "true".equalsIgnoreCase(displayOption)) {
				if (forEdit != null &&  "true".equalsIgnoreCase(forEdit)) {	
					int payrollPeriodId = Integer.parseInt(request.getParameter("payrollPeriodId"));					
					
					try {	
						PayrollPeriod origModel = service.getPayrollPeriodById(payrollPeriodId, payrollPeriodStatus);
						
						//if need to be displayed in jTable,use generic model
						genericDisplayOptionList = new ArrayList <GenericDisplayOptionModel>() ;
						
						if(origModel != null) {
							GenericDisplayOptionModel model = new GenericDisplayOptionModel();
							model.setValue(origModel.getPayrollPeriodId());
							model.setDisplayText(origModel.getFromDate() + " to " + origModel.getToDate());
						    genericDisplayOptionList.add(model);
						} else {
							GenericDisplayOptionModel model = new GenericDisplayOptionModel();
							model.setValue(0);
							model.setDisplayText("Select");
					    	genericDisplayOptionList.add(model);
						}
						
						
							
					    for (PayrollPeriod modelObj:list){	
					    	
					    	if(origModel != null) {
					    		if(modelObj.getPayrollPeriodId() != origModel.getPayrollPeriodId()){
						    		GenericDisplayOptionModel genericDisplayOptionModel = new GenericDisplayOptionModel();
								   	genericDisplayOptionModel.setValue(modelObj.getPayrollPeriodId());
								   	genericDisplayOptionModel.setDisplayText(modelObj.getFromDate() + " to " + modelObj.getToDate());
								   	genericDisplayOptionList.add(genericDisplayOptionModel);
						    	}
							} else {
								GenericDisplayOptionModel genericDisplayOptionModel = new GenericDisplayOptionModel();
						    	genericDisplayOptionModel.setValue(modelObj.getPayrollPeriodId());
						    	genericDisplayOptionModel.setDisplayText(modelObj.getFromDate() + " to " + modelObj.getToDate());
						    	genericDisplayOptionList.add(genericDisplayOptionModel);
							}				    					   	
					    	
						}
							
						
						String json = gson.toJson(genericDisplayOptionList);
					    System.out.println("json: " + json);
				 
					    json = "{\"Result\":\"OK\",\"Options\":"+ json + "}";
				        response.getWriter().print(json);
						
					} catch (SQLException sqe1) {
						//TODO add proper logging
						System.err.println(sqe1.getMessage());
						sqe1.printStackTrace();
					} catch (Exception e) {
						//TODO add proper logging
						System.err.println(e.getMessage());
					}
				} else {
					
					genericDisplayOptionList = new ArrayList <GenericDisplayOptionModel>() ;
					GenericDisplayOptionModel model = new GenericDisplayOptionModel();
					model.setValue(0);
					model.setDisplayText("Select");
				    genericDisplayOptionList.add(model);
						
				    for (PayrollPeriod modelObj:list){				
					   	GenericDisplayOptionModel genericDisplayOptionModel = new GenericDisplayOptionModel();
					   	genericDisplayOptionModel.setValue(modelObj.getPayrollPeriodId());
					   	genericDisplayOptionModel.setDisplayText(modelObj.getFromDate() + " to " + modelObj.getToDate());
					   	genericDisplayOptionList.add(genericDisplayOptionModel);
				    
					}
					
					String json = gson.toJson(genericDisplayOptionList);
				    System.out.println("json: " + json);
			 
				    json = "{\"Result\":\"OK\",\"Options\":"+ json + "}";   ///note the change from Records to Option
			        response.getWriter().print(json);
					
				}
				
			} else {
				JsonElement element = gson.toJsonTree(list,new TypeToken<List<PayrollPeriod>>(){}.getType());
				JsonArray jsonArray = element.getAsJsonArray();
				String listData=jsonArray.toString();           //Return Json in the format required by jTable plugin
				listData = "{\"Result\":\"OK\",\"Records\":"+listData+",\"TotalRecordCount\":"+count+"}";   
				response.getWriter().print(listData);
			}
			
			
			
		}
		
		
	}
    
    

}
