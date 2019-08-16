package com.prateek.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@PropertySource("classpath:scheduler.properties")
@Slf4j
public class EmployeesRunScheduler {
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	//@Scheduled(cron = "${cron.job.expression}")
	public void runEmployees() {
		log.debug("########## Executing the Scheduled Job ###########");
		try {
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
					.toJobParameters();
			
			JobExecution execution = jobLauncher.run(job, jobParameters);
			log.debug("Exit status : " + execution.getStatus());
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
			System.out.println("Exception has occured : "+e.getMessage());
		}

	}
}
