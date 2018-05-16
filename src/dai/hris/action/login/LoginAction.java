package dai.hris.action.login;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dai.hris.model.SystemParameters;
import dai.hris.model.Employee;
import dai.hris.model.ModuleAccess;
import dai.hris.model.Section;
import dai.hris.model.SubUnit;
import dai.hris.model.Unit;
import dai.hris.service.login.ILoginService;
import dai.hris.service.login.IModuleAccessService;
import dai.hris.service.login.LoginService;
import dai.hris.service.login.ModuleAccessService;




/**
 * Servlet implementation class for Servlet: LoginAction
 *
 */
@WebServlet("/LoginAction")
 public class LoginAction extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	 private static final long serialVersionUID = -6185891323760506163L;	 
	 private IModuleAccessService svc = new ModuleAccessService();
	 
	 /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public LoginAction() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doPost(request, response);
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);   				
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");			
		
		ILoginService ls = new LoginService();	
        
		try {
			
				if(ls.checkCredentials(username, password)){	
					
					SystemParameters approvers = ls.getApprovers();
					session.setAttribute("approvers", approvers);	
					session.setAttribute("isAdminChecked", approvers.getIsAdminChecked());	
					session.setAttribute("UserName", username);	
					
					Employee emp = ls.getEmployee(username);
					//ls.updateLoginActivity(emp.getEmpId(), emp.getDepartmentId(), "LOGIN");
					session.setAttribute("employeeLoggedIn", emp);	
					session.setAttribute("empName", emp.getFirstname() + " " + emp.getLastname());	
		        	
					ModuleAccess ma = svc.getModuleAccessByEmpId(emp.getEmpId());
					session.setAttribute("moduleAccess", ma);
					
					List<String> employeeSettingsHeaderList = new ArrayList<String>();
					List<String> payrollSettingsHeaderList = new ArrayList<String>();
					List<String> locationSettingsHeaderList = new ArrayList<String>();
					List<String> empManagementHeaderList = new ArrayList<String>();
					List<String> empEntriesHeaderList = new ArrayList<String>();
					List<String> empApprovalsHeaderList = new ArrayList<String>();
					
					employeeSettingsHeaderList.addAll((ma != null && ma.getFileManagementList() != null) ? ma.getFileManagementList() : new ArrayList<String>());
					payrollSettingsHeaderList.addAll((ma != null && ma.getFileManagementList() != null) ? ma.getFileManagementList() : new ArrayList<String>());
					locationSettingsHeaderList.addAll((ma != null && ma.getFileManagementList() != null) ? ma.getFileManagementList() : new ArrayList<String>());
					empManagementHeaderList.addAll((ma != null && ma.getEmployeeList() != null) ? ma.getEmployeeList() : new ArrayList<String>());
					empEntriesHeaderList.addAll((ma != null && ma.getEmployeeList() != null) ? ma.getEmployeeList() : new ArrayList<String>());
					empApprovalsHeaderList.addAll((ma != null && ma.getEmployeeList() != null) ? ma.getEmployeeList() : new ArrayList<String>());
										
					
					session.setAttribute("employeeSettingsHeaderList", employeeSettingsHeaderList);
					session.setAttribute("payrollSettingsHeaderList", payrollSettingsHeaderList);
					session.setAttribute("locationSettingsHeaderList", locationSettingsHeaderList);
					session.setAttribute("empManagementHeaderList", empManagementHeaderList);
					session.setAttribute("empEntriesHeaderList", empEntriesHeaderList);
					session.setAttribute("empApprovalsHeaderList", empApprovalsHeaderList);
					
					
					boolean isApprover = false;
					String approverType = "";
					
					//check if Approver
					if(approvers != null) {
						if(emp.getEmpId() == approvers.getAdminId()){
							isApprover = true;
							approverType = "ADMIN";
						} else if(emp.getEmpId() == approvers.getAdminAssistantId()){
							isApprover = true;
							approverType = "ADMIN";
						} else if(emp.getEmpId() == approvers.getHrAdminId()){
							isApprover = true;
							approverType = "HRADMIN";
						} else if(emp.getEmpId() == approvers.getHrAdminAssistantId()){
							isApprover = true;
							approverType = "HRADMIN";
						} else if(emp.getEmpId() == approvers.getHrAdminLiasonId()){
							isApprover = true;
							approverType = "HRADMIN";
						} else {
							
							//check if section approver
							if(approvers.getSectionHeadList() != null && !approvers.getSectionHeadList().isEmpty()) {							
								for(Section section : approvers.getSectionHeadList()){
									if(emp.getEmpId() == section.getEmpId()){
										isApprover = true;
										approverType = "SECTIONADMIN";
										break;
									} else if(emp.getEmpId() == section.getAssistantId()){
										isApprover = true;
										approverType = "SECTIONADMIN";
										break;
									}
								}
							}
							
							if(approvers.getUnitHeadList() != null && !approvers.getUnitHeadList().isEmpty()) {
								if(!isApprover){
									//check if unit approver
									for(Unit unit : approvers.getUnitHeadList()){
										if(emp.getEmpId() == unit.getEmpId()){
											isApprover = true;
											approverType = "UNITADMIN";
											break;
										} else if(emp.getEmpId() == unit.getAssistantId()){
											isApprover = true;
											approverType = "UNITADMIN";
											break;
										}
									}
								}
							}
							
							
							if(approvers.getSubUnitHeadList() != null && !approvers.getSubUnitHeadList().isEmpty()) {
								if(!isApprover){
									//check if sub unit approver
									for(SubUnit subUnit : approvers.getSubUnitHeadList()){
										if(emp.getEmpId() == subUnit.getEmpId()){
											isApprover = true;
											approverType = "SUBUNITADMIN";
											break;
										} else if(emp.getEmpId() == subUnit.getAssistantId()){
											isApprover = true;
											approverType = "SUBUNITADMIN";
											break;
										}
									}
								}		
							}
							
						}
					}
					
					
					if(isApprover){
						session.setAttribute("isApprover", "YES");
						session.setAttribute("approverType", approverType);
					} else {
						session.setAttribute("isApprover", "NO");
						session.setAttribute("approverType", approverType);
					}
					
					RequestDispatcher dispatcher = null;
//					dispatcher = getServletContext().getRequestDispatcher("/employeeDashBoard.jsp");	
					dispatcher = getServletContext().getRequestDispatcher("/dashboard.jsp");	

					if (dispatcher != null) {
			        	response.setContentType("text/html");
			            dispatcher.include(request, response);
			        }											
				} else {
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp?isExist=0");
					if (dispatcher != null) {
						response.setContentType("text/html");
						dispatcher.include(request, response);
					}
				}
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}   	  	    
}