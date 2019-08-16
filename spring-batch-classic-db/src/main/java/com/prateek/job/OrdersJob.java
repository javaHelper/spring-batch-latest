package com.prateek.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.prateek.listeners.OrdersJobExecutionListener;
import com.prateek.listeners.OrdersStepListeners;
import com.prateek.model.Orders;

@Configuration
public class OrdersJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private JdbcCursorItemReader<Orders> ordersReader;

	@Autowired
	private FlatFileItemWriter<Orders> ordersWriter;

	@Bean
	public OrdersStepListeners ordersStepListeners() {
		return new OrdersStepListeners();
	}
	
	@Bean
	public OrdersJobExecutionListener ordersJobExecutionListener() {
		return new OrdersJobExecutionListener();
	}

	// Step-2 Execution
	@Bean
	public Step orderStep1() {
		return stepBuilderFactory.get("orderStep1")
				.<Orders, Orders>chunk(10)
				.reader(ordersReader)
				.writer(ordersWriter)
				.listener(ordersStepListeners())
				.build();
	}

	// Job Execution
	@Bean
	public Job exportOrdersJob() {
		System.out.println("************************************************ EXPORT ORDER JOB");
		return jobBuilderFactory
				.get("exportOrdersJob")
				.incrementer(new RunIdIncrementer())
				.flow(orderStep1())
				.end()
				.listener(ordersJobExecutionListener())
				//.start(orderStep1()) // THIS ALSO WORKS FINE !
				.build();
	}
}
