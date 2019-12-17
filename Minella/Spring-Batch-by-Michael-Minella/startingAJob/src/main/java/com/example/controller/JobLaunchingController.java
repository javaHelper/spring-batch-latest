package com.example.controller;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobLaunchingController {
	/*@Autowired
	private JobLauncher jobLauncher;*/
	
	@Autowired
	private JobOperator jobOperator;
	
	@Autowired
	private Job job;
	
	@PostMapping("/")
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void launch(@RequestParam("name") String name) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, NoSuchJobException, JobInstanceAlreadyExistsException {
		/*JobParameters jobParameters = new JobParametersBuilder()
				.addString("name", name)
				.toJobParameters();*/
		//JobExecution jobExecution = this.jobLauncher.run(job, jobParameters);
		
		this.jobOperator.start("job", String.format("name=%s", name));
	}
	
}
