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
import dai.hris.model.SubUnit;
import dai.hris.service.filemanager.subunit.ISubUnitService;
import dai.hris.service.filemanager.subunit.SubUnitService;

@WebServlet("/GetAllSubUnitAction")
public class GetAllSubUnitAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();
	ISubUnitService service = new SubUnitService();
	
	
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub	
		
		String forListJTable = request.getParameter("forListJTable");		
		List<GenericDisplayOptionModel> genericDisplayOptionList = null;		
		
		if (forListJTable != null && "true".equalsIgnoreCase(forListJTable)) {	
			int subUnitId = Integer.parseInt(request.getParameter("subUnitId"));
			
			
			try {	
				SubUnit origSec = service.getSubUnit(subUnitId);
				
				//if need to be displayed in jTable,use generic model
				genericDisplayOptionList = new ArrayList <GenericDisplayOptionModel>() ;
				
				if(origSec != null) {
					GenericDisplayOptionModel model = new GenericDisplayOptionModel();
					model.setValue(origSec.getSubUnitId());
					model.setDisplayText(origSec.getSubUnitName());
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
			List<SubUnit> list = new ArrayList<SubUnit>();
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
					int subUnitId = Integer.parseInt(request.getParameter("subUnitId"));
					
					
					try {	
						SubUnit origSec = service.getSubUnit(subUnitId);
						
						//if need to be displayed in jTable,use generic model
						genericDisplayOptionList = new ArrayList <GenericDisplayOptionModel>() ;
																	
						if(origSec != null) {
							GenericDisplayOptionModel model = new GenericDisplayOptionModel();
							model.setValue(origSec.getSubUnitId());
							model.setDisplayText(origSec.getSubUnitName());
						    genericDisplayOptionList.add(model);
						} else {
							GenericDisplayOptionModel model = new GenericDisplayOptionModel();
							model.setValue(0);
							model.setDisplayText("SELECT SUB UNIT");
					    	genericDisplayOptionList.add(model);
						}
						
							
					    for (SubUnit modelObj:list){	
					    	
					    	if(origSec != null) {
					    		if(modelObj.getSubUnitId() != origSec.getSubUnitId()){
						    		GenericDisplayOptionModel genericDisplayOptionModel = new GenericDisplayOptionModel();
								   	genericDisplayOptionModel.setValue(modelObj.getSubUnitId());
								   	genericDisplayOptionModel.setDisplayText(modelObj.getSubUnitName());
								   	genericDisplayOptionList.add(genericDisplayOptionModel);
						    	}
							} else {
								GenericDisplayOptionModel genericDisplayOptionModel = new GenericDisplayOptionModel();
						    	genericDisplayOptionModel.setValue(modelObj.getSubUnitId());
						    	genericDisplayOptionModel.setDisplayText(modelObj.getSubUnitName());
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
					model.setDisplayText("SELECT UNIT");
				    genericDisplayOptionList.add(model);
						
				    for (SubUnit modelObj:list){				
					   	GenericDisplayOptionModel genericDisplayOptionModel = new GenericDisplayOptionModel();
					   	genericDisplayOptionModel.setValue(modelObj.getSubUnitId());
					   	genericDisplayOptionModel.setDisplayText(modelObj.getSubUnitName());
					   	genericDisplayOptionList.add(genericDisplayOptionModel);
				    	
					}
						
					//now also add a not applicable (empId=0 option)
//					GenericDisplayOptionModel dummyModel = new GenericDisplayOptionModel();
//					dummyModel.setValue(0);
//					dummyModel.setDisplayText("N/A");
//					genericDisplayOptionList.add(dummyModel);
					
					String json = gson.toJson(genericDisplayOptionList);
				    System.out.println("json: " + json);
			 
				    json = "{\"Result\":\"OK\",\"Options\":"+ json + "}";   ///note the change from Records to Option
			        response.getWriter().print(json);
				}
				
				
				
				
			
			} else {							
//				String json = gson.toJson(list);				 
//				json = "{\"Section\":"+json+"}";
//				response.getWriter().print(json);	
				
				JsonElement element = gson.toJsonTree(list,new TypeToken<List<SubUnit>>(){}.getType());
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
