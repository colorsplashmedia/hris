package dai.hris.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



import dai.hris.model.Employee;
import dai.hris.model.EmployeeSchedule;
import dai.hris.model.ShiftingSchedule;
import dai.hris.service.filemanager.employee.EmployeeService;
import dai.hris.service.filemanager.employee.IEmployeeService;
import dai.hris.service.timeentry.ITimeEntryService;
import dai.hris.service.timeentry.TimeEntryService;
import dai.hris.services.shiftingSchedule.IShiftingScheduleService;
import dai.hris.services.shiftingSchedule.ShiftingScheduleService;


@WebServlet("/UploadFile")
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String UPLOAD_DIRECTORY = "C:/EmpScheduleFiles/";
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		Employee employeeLoggedIn = (Employee) request.getSession().getAttribute("employeeLoggedIn");
		int supervisorId = employeeLoggedIn.getEmpId();
		
		IEmployeeService empService = new EmployeeService();
		ITimeEntryService service = new TimeEntryService();
		IShiftingScheduleService shiftService = new ShiftingScheduleService();
		
		
		
		
		List<EmployeeSchedule> list = new ArrayList<EmployeeSchedule>();

		// process only if its multipart content
		if (isMultipart) {
			
			
			
			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List<ShiftingSchedule> shiftList = shiftService.getAll();
				
				Iterator<ShiftingSchedule> iShift = shiftList.iterator();
				
				Map<Integer, String> mapShift = new HashMap<Integer, String>();
				
				while(iShift.hasNext()) {
					ShiftingSchedule newShiftingSchedule = iShift.next();
					mapShift.put(newShiftingSchedule.getShiftingScheduleId(), "Y");					
				}
				
				// Parse the request
				List<FileItem> multiparts = upload.parseRequest(request);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				

				for (FileItem item : multiparts) {
					if (!item.isFormField()) {
						String name = new File(item.getName()).getName();
						
						String dateNow = sdf.format(new Date());
						
						File newFile = new File(UPLOAD_DIRECTORY + File.separator + dateNow + employeeLoggedIn.getSectionId() + name);
						
						item.write(newFile);

						// START OF CODE HERE

						FileInputStream file = new FileInputStream(newFile);

						// Create Workbook instance holding reference to .xlsx
						// file
						XSSFWorkbook workbook = new XSSFWorkbook(file);

						// Get first/desired sheet from the workbook
						XSSFSheet sheet = workbook.getSheetAt(0);

						// Iterate through each rows one by one
						Iterator<Row> rowIterator = sheet.iterator();
						while (rowIterator.hasNext()) {
							Row row = rowIterator.next();
							
							EmployeeSchedule empSched = new EmployeeSchedule();
							empSched.setUpdatedBy(supervisorId);
							
							Employee emp = new Employee();

							// Change these parameters as to what row to start
							// and end
							if (row.getRowNum() >= 15) {

								// For each row, iterate through all the columns
								Iterator<Cell> cellIterator = row.cellIterator();

								while (cellIterator.hasNext()) {
									Cell cell = cellIterator.next();
									// Check the cell type and format
									// accordingly

									if(cell.getColumnIndex() == 3 || cell.getColumnIndex() == 4){
										continue;
									} else {
										System.out.print("Column # " + cell.getColumnIndex() + " - ");
									}
									
									
									if (cell.getColumnIndex() >= 0	&& cell.getColumnIndex() <= 6) {
										
										switch (cell.getColumnIndex()) {
											case 0:
												switch (cell.getCellType()) {
													case Cell.CELL_TYPE_NUMERIC:												
														System.out.print(cell.getNumericCellValue()	+ "\t");														
														break;
													case Cell.CELL_TYPE_STRING:
														System.out.print(cell.getStringCellValue()	+ "\t");														
														break;
												}
												break;
												
											case 1:												
												switch (cell.getCellType()) {
													case Cell.CELL_TYPE_NUMERIC:	
														Double empNo = new Double(cell.getNumericCellValue());
														
														emp = empService.getEmployee(empNo.intValue() + "");
														empSched.setEmpId(emp.getEmpId());
														empSched.setSuperVisorId(employeeLoggedIn.getEmpId());
														System.out.print(empNo.intValue()	+ "\t");														
														break;
													case Cell.CELL_TYPE_STRING:
														
														emp = empService.getEmployee(cell.getStringCellValue());
														empSched.setEmpId(emp.getEmpId());
														System.out.print(cell.getStringCellValue()	+ "\t");														
														break;
												}
												break;
											case 2:
												switch (cell.getCellType()) {
													
													case Cell.CELL_TYPE_NUMERIC:	
														Double sheduleId = new Double(cell.getNumericCellValue());
														empSched.setShiftingScheduleId(sheduleId.intValue());
														
														String isFound = mapShift.get(empSched.getShiftingScheduleId());
														
														if(isFound != null && isFound.equals("Y")){
															
														} else {
															throw new IllegalStateException();
														}
														
														
														
														System.out.print(sheduleId.intValue()	+ "\t");														
														break;												
												}
												break;
												
												
												
											case 5:
												switch (cell.getCellType()) {
													case Cell.CELL_TYPE_BLANK :
														break;
													case Cell.CELL_TYPE_NUMERIC:	
														empSched.setScheduleDate(sdf.format(cell.getDateCellValue()));
														
														System.out.print(sdf.format(cell.getDateCellValue()) + "\t");													
														break;
													case Cell.CELL_TYPE_STRING:
														System.out.print(cell.getStringCellValue()	+ "\t");														
														break;
												}
												break;
											case 6:
												switch (cell.getCellType()) {
													case Cell.CELL_TYPE_BLANK :
														break;
													case Cell.CELL_TYPE_NUMERIC:		
														empSched.setToDate(sdf.format(cell.getDateCellValue()));
														System.out.print(sdf.format(cell.getDateCellValue()) + "\t");													
														break;
													case Cell.CELL_TYPE_STRING:
														System.out.print(cell.getStringCellValue()	+ "\t");														
														break;
												}
												break;
										}
										
										

									}
									
									
								}
								System.out.println("");
								
								if(emp != null && emp.getEmpId() > 0) {
									list.add(empSched);
								}
								
							}
						}
						file.close();

						// END OF CODE HERE

					}
				}
				
				Iterator<EmployeeSchedule> i = list.iterator();
				
				while (i.hasNext()) {
					EmployeeSchedule model = i.next();
					
					service.saveEmployeeScheduleUpload(model);		
				}

				// File uploaded successfully
				request.setAttribute("message", "Your file has been uploaded!");
			} catch (IllegalStateException e1) {
				request.setAttribute("message", "File Upload Failed due to "
						+ e1 + " - Please check if numeric and date values are correct. Also check if the Shift Id is valid.");
				
				e1.printStackTrace();
			} catch (Exception e) {
				request.setAttribute("message", "File Upload Failed due to "
						+ e);
				e.printStackTrace();
			}
		} else {
			request.setAttribute("message",
					"This Servlet only handles file upload request");
		}
		request.getRequestDispatcher("/uploadEmpScheduleBulk.jsp").forward(request, response);
	}
}