package com.hris.attendance.quartz;
 
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hris.attendance.manager.AttendanceManager;
 
public class SchedulerJob implements Job
{
	
	public void execute(JobExecutionContext context)
	throws JobExecutionException {
		{
			System.out.println("Sync...");
			AttendanceManager aManager = new AttendanceManager();
			aManager.syncData();
			System.out.println("Done!");
		}
	}
}
