package com.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.decider.MyJobExecutionDecider;
import com.example.tasklet.Step1Tasklet;
import com.example.tasklet.Step2Tasklet;
import com.example.tasklet.Step3Tasklet;

@Configuration
@EnableBatchProcessing
public class MyJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step1Tasklet step1Tasklet() {
		return new Step1Tasklet();
	}

	@Bean
	public Step2Tasklet step2Tasklet() {
		return new Step2Tasklet();
	}
	
	@Bean
	public Step3Tasklet step3Tasklet() {
		return new Step3Tasklet();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").tasklet(step1Tasklet()).build();
	}

	@Bean
	public MyJobExecutionDecider decider() {
		return new MyJobExecutionDecider();
	}

	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2").tasklet(step2Tasklet()).build();
	}

	@Bean
	public Step step3() {
		return stepBuilderFactory.get("step3").tasklet(step3Tasklet()).build();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("job")
				.start(step1())
				.next(decider())
					.on("YES").to(step2())
				.from(decider())
					.on("NO").to(step3()).end()
				.build();
	}
}