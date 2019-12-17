package com.example.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	//@StepScope - No need, this will not save state 
	public StatefullItemReader itemReader() {
		List<String> items = new ArrayList<>(100);
		
		for (int i = 1; i <= 100; i++) {
			items.add(String.valueOf(i));
		}
		
		return new StatefullItemReader(items);
	}
	
	
	@Bean 
	public ItemWriter<String> itemWriter(){
		return new ItemWriter<String>() {

			@Override
			public void write(List<? extends String> items) throws Exception {
				for (String item : items) {
					System.out.println(">> "+item);
				}
			}
		};
	}
	
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<String, String> chunk(10)
				.reader(itemReader())
				.writer(itemWriter())
				.stream(itemReader())
				.build();
	}
	
	@Bean
	public Job statefulJob() {
		return jobBuilderFactory.get("statefulJob")
				.start(step1())
				.build();
	}
}
