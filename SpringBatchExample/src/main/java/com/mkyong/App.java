package com.mkyong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) {

		String[] springConfig  = {
				"spring/batch/config/database.xml", 
				"spring/batch/config/context.xml",
				"spring/batch/jobs/job-report.xml" 
		};

		ApplicationContext context =new ClassPathXmlApplicationContext(springConfig);

		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("reportJob");

		try {

			JobExecution execution = jobLauncher.run(job, new JobParameters());
			//System.out.println("Exit Status : " + execution.getStatus());
			logger.debug("Exit Status : "+ execution.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}

		//System.out.println("Done");
		logger.debug("Done !");
	}
}
