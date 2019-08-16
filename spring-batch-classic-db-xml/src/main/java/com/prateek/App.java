package com.prateek;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		String[] springConfig = { "config/database.xml", "config/context.xml", "jobs/customer-write-csv.xml" ,"jobs/payments-write-csv.xml", 
				"jobs/product-redis-writer.xml"};

		ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);

		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		//Job job = (Job) context.getBean("customerJob");
		//Job job = (Job) context.getBean("paymentsPartitionJob");
		//Job job = (Job) context.getBean("reportJob");
		Job job = (Job) context.getBean("productRedisJob");
		
				
		try {
			
			//JobParameters jobParameter = new JobParametersBuilder().addString("runMode", "My_Sample_Customer").toJobParameters();

			JobExecution execution = jobLauncher.run(job, new JobParameters());
			System.out.println("############## Exit Status : " + execution.getStatus());
			System.out.println("############## Exit Status : " + execution.getAllFailureExceptions());
		} catch (Exception e) {
			System.out.println("Exceptioj in main method :: "+e.getMessage());
		}

		System.out.println("Done");
	}
}
