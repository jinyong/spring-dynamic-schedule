package jy.com.config;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

import jy.com.task.DynamicTask;

@Service
public class DynamicTaskConfig implements SchedulingConfigurer {
	
	@Autowired
	private DynamicTask dynamicTask;
	
	private static int executedTimes = 0;

	@Bean
	public TaskScheduler schedulePool() {
		
		ThreadPoolTaskScheduler schedulePool = new ThreadPoolTaskScheduler();
		schedulePool.setThreadNamePrefix("DynamicSchedulePool");
		schedulePool.setPoolSize(1);
		schedulePool.initialize();
		
		return schedulePool;
	}

	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		
		taskRegistrar.setScheduler(schedulePool());
		
		taskRegistrar.addTriggerTask(
				new Runnable() {
					
					public void run() {
						dynamicTask.executeTask();
					}
				},
				new Trigger() {
					
					public Date nextExecutionTime(TriggerContext triggerContext) {
						
						Date lastActualExetutionTime = triggerContext.lastActualExecutionTime();
						
						if(lastActualExetutionTime == null)
							return new Date();
						
						Calendar nextExecutionTime = new GregorianCalendar();
						nextExecutionTime.setTime(lastActualExetutionTime);
						nextExecutionTime.add(Calendar.SECOND, executedTimes % 2 == 0? 10: 5);
						System.out.println(executedTimes % 2 == 0? 10: 5);
						
						executedTimes++;
						
						return nextExecutionTime.getTime();
					}
				}
		);
	}
}