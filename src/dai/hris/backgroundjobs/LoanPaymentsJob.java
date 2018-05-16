package dai.hris.backgroundjobs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import dai.hris.service.loan.ILoanEntryService;
import dai.hris.service.loan.LoanEntryService;
import dai.hris.model.LoanEntry;


public class LoanPaymentsJob implements Job {
	
	//private ILoanEntryService loanEntryService = new LoanEntryService();
	//private List<LoanEntry> loanEntryList = new ArrayList<LoanEntry>();
			
        public void execute(JobExecutionContext context) throws JobExecutionException {
        	System.out.println("This is a scheduled job for HRIS using Quartz 2.2.1 running every 10 minutes");
        	System.out.println("Change QuartzListener class to change schedule of execution");
        	
        	/*
        	try {
				loanEntryList = loanEntryService.getAllActiveLoanEntry();
				for (LoanEntry le: loanEntryList) {
					System.out.println("LoanEntry: " + le.getEmpId());
				}
			} catch (SQLException e) {
				System.out.println("Exception encountered when trying to get all active loan entry..");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Exception encountered when trying to get all active loan entry..");
				e.printStackTrace();
			}
			
			*/               
                
        }
}