package dai.hris.action.filemanager;

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
import dai.hris.model.SalaryGrade;
import dai.hris.service.filemanager.salarygrade.ISalaryGradeService;
import dai.hris.service.filemanager.salarygrade.SalaryGradeService;

@WebServlet("/GetAllSalaryGradeAction")
public class GetAllSalaryGradeAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();
	ISalaryGradeService service = new SalaryGradeService();
	
	
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub	
		
		String forListJTable = request.getParameter("forListJTable");		
		List<GenericDisplayOptionModel> genericDisplayOptionList = null;		
		
		if (forListJTable != null && "true".equalsIgnoreCase(forListJTable)) {	
			int salaryGradeId = Integer.parseInt(request.getParameter("salaryGradeId"));
			
			
			try {	
				SalaryGrade origSec = service.getSalaryGrade(salaryGradeId);
				
				//if need to be displayed in jTable,use generic model
				genericDisplayOptionList = new ArrayList <GenericDisplayOptionModel>() ;
				
				if(origSec != null) {
					GenericDisplayOptionModel model = new GenericDisplayOptionModel();
					model.setValue(origSec.getSalaryGradeId());
					model.setDisplayText(origSec.getSalaryGradeName());
				    genericDisplayOptionList.add(model);
				}			
				
				String json = gson.toJson(genericDisplayOptionList);
			    System.out.println("json: " + json);
		 
			    json = "{\"Result\":\"OK\",\"Options\":"+ json + "}";   ///note the change from Records to Option
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
			List<SalaryGrade> list = new ArrayList<SalaryGrade>();
			int count = 0;
			
			try {
				
				String startIndex = request.getParameter("jtStartIndex");
				String pageSize = request.getParameter("jtPageSize");
				String name = request.getParameter("name");
				String sorting = request.getParameter("jtSorting");
								
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
			} catch (SQLException sqe1) {
				//TODO add proper logging
				System.err.println(sqe1.getMessage());
				sqe1.printStackTrace();
			} catch (Exception e) {
				//TODO add proper logging
				System.err.println(e.getMessage());
				e.printStackTrace();			
			}
			
			String displayOption = request.getParameter("displayOption");		
			String forEdit = request.getParameter("forEdit");		
			
			if (displayOption != null && "true".equalsIgnoreCase(displayOption)) {	
				
				if (forEdit != null &&  "true".equalsIgnoreCase(forEdit)) {	
					int salaryGradeId = Integer.parseInt(request.getParameter("salaryGradeId"));
					
					
					try {	
						SalaryGrade origSec = service.getSalaryGrade(salaryGradeId);
						
						//if need to be displayed in jTable,use generic model
						genericDisplayOptionList = new ArrayList <GenericDisplayOptionModel>() ;
						
						if(origSec != null) {
							GenericDisplayOptionModel model = new GenericDisplayOptionModel();
							model.setValue(origSec.getSalaryGradeId());
							model.setDisplayText(origSec.getSalaryGradeName());
						    genericDisplayOptionList.add(model);
						} else {
							GenericDisplayOptionModel model = new GenericDisplayOptionModel();
							model.setValue(0);
							model.setDisplayText("SELECT SALARY GRADE");
					    	genericDisplayOptionList.add(model);
						}
						
							
					    for (SalaryGrade modelObj:list){			
					    	
					    	if(origSec != null) {
					    		if(modelObj.getSalaryGradeId() != origSec.getSalaryGradeId()){
						    		GenericDisplayOptionModel genericDisplayOptionModel = new GenericDisplayOptionModel();
								   	genericDisplayOptionModel.setValue(modelObj.getSalaryGradeId());
								   	genericDisplayOptionModel.setDisplayText(modelObj.getSalaryGradeName());
								   	genericDisplayOptionList.add(genericDisplayOptionModel);
						    	}
							} else {
								GenericDisplayOptionModel genericDisplayOptionModel = new GenericDisplayOptionModel();
						    	genericDisplayOptionModel.setValue(modelObj.getSalaryGradeId());
						    	genericDisplayOptionModel.setDisplayText(modelObj.getSalaryGradeName());
						    	genericDisplayOptionList.add(genericDisplayOptionModel);
							}
					    	
					    							   	
					    	
						}
							
						
						String json = gson.toJson(genericDisplayOptionList);
					    System.out.println("json: " + json);
				 
					    json = "{\"Result\":\"OK\",\"Options\":"+ json + "}";   ///note the change from Records to Option
				        response.getWriter().print(json);
						
					} catch (SQLException sqe1) {
						//TODO add proper logging
						System.err.println(sqe1.getMessage());
						sqe1.printStackTrace();
					} catch (Exception e) {
						//TODO add proper logging
						System.err.println(e.getMessage());
					}
					
					
				}  else {
					//if need to be displayed in jTable,use generic model
					genericDisplayOptionList = new ArrayList <GenericDisplayOptionModel>() ;
					GenericDisplayOptionModel model = new GenericDisplayOptionModel();
					model.setValue(0);
					model.setDisplayText("SELECT SALARY GRADE");
				    genericDisplayOptionList.add(model);
						
				    for (SalaryGrade modelObj:list){				
					   	GenericDisplayOptionModel genericDisplayOptionModel = new GenericDisplayOptionModel();
					   	genericDisplayOptionModel.setValue(modelObj.getSalaryGradeId());
					   	genericDisplayOptionModel.setDisplayText(modelObj.getSalaryGradeName());
					   	genericDisplayOptionList.add(genericDisplayOptionModel);
				    
					}

					
					String json = gson.toJson(genericDisplayOptionList);
				    System.out.println("json: " + json);
			 
				    json = "{\"Result\":\"OK\",\"Options\":"+ json + "}";   ///note the change from Records to Option
			        response.getWriter().print(json);
				}
				
				
				
				
			
			} else {			
				
				JsonElement element = gson.toJsonTree(list,new TypeToken<List<SalaryGrade>>(){}.getType());
				JsonArray jsonArray = element.getAsJsonArray();
				String listData=jsonArray.toString();           //Return Json in the format required by jTable plugin
				listData = "{\"Result\":\"OK\",\"Records\":"+listData+",\"TotalRecordCount\":"+count+"}";   
				       response.getWriter().print(listData);
			}
		}
		
		
		
		
//		ISectionService service = new SectionService();
//		List<Section> list = null;
//		
//		int count = 0;
//		String startIndex = request.getParameter("jtStartIndex");
//		String pageSize = request.getParameter("jtPageSize");
//		String name = request.getParameter("name");
//		String sorting = request.getParameter("jtSorting");
//		
//		try {
//						
//			if(name != null && !name.isEmpty()){
//				count=service.getCountWithFilter(name);
//			} else {
//				count=service.getCount();
//			}
//			
//			
//			if(startIndex != null && startIndex.length() > 0) {
//				//do nothing
//			} else {
//				startIndex = "0";
//			}
//			
//			if(pageSize != null && pageSize.length() > 0) {
//				//do nothing
//			} else {
//				pageSize = "" + count;
//			}
//			
//			int startPageIndex = Integer.parseInt(startIndex);
//			int numRecordsPerPage = Integer.parseInt(pageSize);			
//			
//			if(name != null && !name.isEmpty()){
//				list = service.getAllWithFilter(startPageIndex,numRecordsPerPage, sorting, name);				
//			} else {
//				list = service.getAll(startPageIndex,numRecordsPerPage, sorting);
//			}
//		}
//		catch(Exception e ){
//			e.printStackTrace();
//		}
//
//		
//		JsonElement element = gson.toJsonTree(list,new TypeToken<List<Section>>(){}.getType());
//		JsonArray jsonArray = element.getAsJsonArray();
//		String listData=jsonArray.toString();           //Return Json in the format required by jTable plugin
//		listData = "{\"Result\":\"OK\",\"Records\":"+listData+",\"TotalRecordCount\":"+count+"}";   
//		       response.getWriter().print(listData);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doGet(request, response);
	}
	

}
