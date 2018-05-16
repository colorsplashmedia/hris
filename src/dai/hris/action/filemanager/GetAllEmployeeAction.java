/**
 * 
 */
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

import dai.hris.model.Employee;
import dai.hris.model.GenericDisplayOptionModel;
import dai.hris.service.filemanager.employee.EmployeeService;
import dai.hris.service.filemanager.employee.IEmployeeService;


@WebServlet("/GetAllEmployeeAction")
public class GetAllEmployeeAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IEmployeeService employeeService = new EmployeeService();	
	
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {				
		
		String forListJTable = request.getParameter("forListJTable");		
		List<GenericDisplayOptionModel> genericDisplayOptionList = null;		
		
		if (forListJTable != null && "true".equalsIgnoreCase(forListJTable)) {	
			int empId = Integer.parseInt(request.getParameter("empId"));
			
			
			try {	
				Employee origEmp = employeeService.getEmployee(empId);
				
				//if need to be displayed in jTable,use generic model
				genericDisplayOptionList = new ArrayList <GenericDisplayOptionModel>() ;
				GenericDisplayOptionModel model = new GenericDisplayOptionModel();
			    
				
				if(origEmp != null) {					
					model.setValue(origEmp.getEmpId());
					model.setDisplayText(origEmp.getFirstname().toUpperCase() + " " + origEmp.getLastname().toUpperCase());
				} else {
					model.setValue(0);
					model.setDisplayText("NONE");
				}
				
				genericDisplayOptionList.add(model);
				
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
			List<Employee> list = new ArrayList<Employee>();
			
			try {
				list = employeeService.getAll();				
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
			String forDropDown = request.getParameter("forDropDown");		
			String forEdit = request.getParameter("forEdit");	
			
			if(forDropDown != null && "true".equalsIgnoreCase(forDropDown)) {
				genericDisplayOptionList = new ArrayList <GenericDisplayOptionModel>() ;
				
				for (Employee employee:list){		
					GenericDisplayOptionModel genericDisplayOptionModel = new GenericDisplayOptionModel();
				   	genericDisplayOptionModel.setValue(employee.getEmpId());
				   	genericDisplayOptionModel.setDisplayText(employee.getFirstname().toUpperCase() + " " + employee.getLastname().toUpperCase());
				   	genericDisplayOptionList.add(genericDisplayOptionModel);				    	
				}
				
				String json = gson.toJson(genericDisplayOptionList);
			    System.out.println("json: " + json);
		 
			    json = "{\"Result\":\"OK\",\"Options\":"+ json + "}";   ///note the change from Records to Option
		        response.getWriter().print(json);
		        
			} else if (displayOption != null && "true".equalsIgnoreCase(displayOption)) {	
				
				if (forEdit != null &&  "true".equalsIgnoreCase(forEdit)) {	
					int empId = Integer.parseInt(request.getParameter("empId"));
					
					
					try {	
						Employee origEmp = employeeService.getEmployee(empId);
						
						genericDisplayOptionList = new ArrayList <GenericDisplayOptionModel>() ;
						GenericDisplayOptionModel model = new GenericDisplayOptionModel();
					    
						
						if(origEmp != null) {					
							model.setValue(origEmp.getEmpId());
							model.setDisplayText(origEmp.getFirstname().toUpperCase() + " " + origEmp.getLastname().toUpperCase());
						} else {
							model.setValue(0);
							model.setDisplayText("SELECT EMPLOYEE");
						}
						
						genericDisplayOptionList.add(model);
							
					    for (Employee employee:list){		
					    	if(origEmp == null){
					    		GenericDisplayOptionModel genericDisplayOptionModel = new GenericDisplayOptionModel();
							   	genericDisplayOptionModel.setValue(employee.getEmpId());
							   	genericDisplayOptionModel.setDisplayText(employee.getFirstname().toUpperCase() + " " + employee.getLastname().toUpperCase());
							   	genericDisplayOptionList.add(genericDisplayOptionModel);
					    	} else {
					    		if(employee.getEmpId() != origEmp.getEmpId()){
						    		GenericDisplayOptionModel genericDisplayOptionModel = new GenericDisplayOptionModel();
								   	genericDisplayOptionModel.setValue(employee.getEmpId());
								   	genericDisplayOptionModel.setDisplayText(employee.getFirstname().toUpperCase() + " " + employee.getLastname().toUpperCase());
								   	genericDisplayOptionList.add(genericDisplayOptionModel);
						    	}	
					    	}					    	
						}
					    
					    if(origEmp != null) {					
					    	//now also add a remove (empId=0 option)
							GenericDisplayOptionModel dummyModel = new GenericDisplayOptionModel();
							dummyModel.setValue(0);
							dummyModel.setDisplayText("NONE");
							genericDisplayOptionList.add(dummyModel);
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
					model.setDisplayText("SELECT EMPLOYEE");
				    genericDisplayOptionList.add(model);
						
				    for (Employee employee:list){				
					   	GenericDisplayOptionModel genericDisplayOptionModel = new GenericDisplayOptionModel();
					   	genericDisplayOptionModel.setValue(employee.getEmpId());
			//		   	genericDisplayOptionModel.setDisplayText(employee.getEmpNo() + " " + employee.getFirstname() + " "+employee.getLastname());
					   	genericDisplayOptionModel.setDisplayText(employee.getFirstname().toUpperCase() + " "+employee.getLastname().toUpperCase());
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
				String json = gson.toJson(list);				 
				json = "{\"Employee\":"+json+"}";
				//json = "{\"Result\":\"OK\",\"Options\":"+ json + "}";
				response.getWriter().print(json);	
			}
		}
		
		
		
	}

}
