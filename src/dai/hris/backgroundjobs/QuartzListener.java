package dai.hris.backgroundjobs;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzListener implements ServletContextListener {
	private Scheduler scheduler = null;

	public void contextInitialized(ServletContextEvent servletContext) {
		System.out.println("QuartzListener: Context Initialized");

		try {
			// Setup the Job class and the Job group
			JobDetail job = newJob(LoanPaymentsJob.class).withIdentity(
					"CronQuartzJob", "Group").build();

			// Create a Trigger that fires every 10 minutes.
			Trigger trigger = newTrigger()
					.withIdentity("TriggerName", "Group")

					// to use: https://en.wikipedia.org/wiki/Cron
					.withSchedule(CronScheduleBuilder.cronSchedule("0/600 * * * * ?"))
					.build();

			// Setup the Job and Trigger with Scheduler & schedule jobs
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent servletContext) {
		System.out.println("QuartzListener: Context Destroyed");
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}