package com.iocl.fb.scheduling;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableScheduling
@Slf4j
public class DynamicSchedulingConfig {

	@Autowired
	JobScheduleRepo jobRepo;

	@Autowired
	SchedularCalls schedularCalls;

	@Autowired
	private TaskScheduler taskScheduler;

	private List<ScheduledFuture<?>> scheduledFutures = new ArrayList<>();

	@PostConstruct
	public void init() {
		// Initial configuration
		configureTasks();
	}

	public void configureTasks() {
		List<JobEntity> jobInfoList = jobRepo.findAll();

		List<TriggerTask> triggerTasks = new ArrayList<>();

		for (JobEntity jobInfo : jobInfoList) {
			if (jobInfo.getStatus()) {
				Runnable task = createRunnable(jobInfo);
				Trigger trigger = new CronTrigger(jobInfo.getCronTime());
				TriggerTask triggerTask = new TriggerTask(task, trigger);
				triggerTasks.add(triggerTask);
			}

		}

		// Cancel existing tasks
		for (ScheduledFuture<?> future : scheduledFutures) {
			future.cancel(true);
		}

		// Schedule new tasks
		scheduledFutures.clear();
		for (TriggerTask triggerTask : triggerTasks) {
			ScheduledFuture<?> future = taskScheduler.schedule(triggerTask.getRunnable(), triggerTask.getTrigger());
			scheduledFutures.add(future);
		}

	}

	private Runnable createRunnable(JobEntity jobInfo) {
		return () -> executeTask(jobInfo.getMethodName());
	}

	private void executeTask(String methodName) {
		try {
			Method method = SchedularCalls.class.getMethod(methodName);
			method.invoke(schedularCalls);
		} catch (Exception e) {
			log.error("Error executing task: {}", e.getMessage());
		}
	}
}
