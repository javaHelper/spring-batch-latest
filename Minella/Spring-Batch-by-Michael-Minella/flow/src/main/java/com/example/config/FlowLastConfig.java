package com.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowLastConfig {
	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Bean
	public Step myStep1() {
		return stepBuilderFactory.get("myStep1")
				.tasklet((contribution, chunkContext) -> {
					System.out.println("myStep was executed");
					return RepeatStatus.FINISHED;
				}).build();
	}
	
	@Bean
	public Job flowLastJob(Flow flow) {
		return jobBuilderFactory.get("flowLastJob")
				.start(flow)
				.on("COMPLETED").to(flow)
				.end()
				.build();
	}
}
