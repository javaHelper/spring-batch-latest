package com.prateek.job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;

import com.prateek.callback.EmployeesFlatFileWriterCallback;
import com.prateek.mapper.EmployeeRowMapper;
import com.prateek.model.Employees;
import com.prateek.processor.EmployeesProcessor;
import com.prateek.scheduler.EmployeesRunScheduler;

// @Ref: https://github.com/in-the-keyhole/khs-spring-batch-boot-example/blob/master/src/main/java/khs/example/config/TickerPriceConversionConfig.java
// Ref: https://github.com/SoatGroup/json-file-itemwriter/blob/master/jsonitem-writer-impl-objectif2/src/main/java/org/jsonitem/writer/impl/objectif2/config/BatchConfiguration.java


@Configuration
@EnableBatchProcessing
public class EmployeesJob {
	public static final String DATE_FORMAT = "dd-MM-yyyy-hhmmssss";
	public static final DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("primaryDataSource")
	private DataSource dataSource;

	@Bean 
	public EmployeesProcessor employeesProcessor() {
		return new EmployeesProcessor();
	}
	
	@Bean
	public EmployeesRunScheduler scheduler() {
		return new EmployeesRunScheduler();
	}
	
	// This file helps to create CSV column aliases
	@Bean
	public EmployeesFlatFileWriterCallback headerCallback() {
		return new EmployeesFlatFileWriterCallback();
	}
		
	@Bean(destroyMethod="")
	@StepScope
	public JdbcCursorItemReader<Employees> employeesReader(){
		JdbcCursorItemReader<Employees> itemReader = new JdbcCursorItemReader<>();
		itemReader.setDataSource(dataSource);
		itemReader.setSql("SELECT employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle FROM employees ");
		itemReader.setRowMapper(new EmployeeRowMapper());
		// The fetch size can be controlled from the application.properties 
		//itemReader.setFetchSize(200);
		return itemReader;
	}


	@Bean(destroyMethod="")
	public FlatFileItemWriter<Employees> employeesWriter(){
		FlatFileItemWriter<Employees> fileItemWriter = new FlatFileItemWriter<>();
		//fileItemWriter.setResource(new FileSystemResource("csv/employees.csv"));
		fileItemWriter.setResource(new FileSystemResource("csv/employees-{"+ formatter.format(new Date()) +"}.csv"));
		fileItemWriter.setHeaderCallback(headerCallback());

		BeanWrapperFieldExtractor<Employees> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(new String[] {"employeeNumber", "lastName", "firstName", "extension", "email", "officeCode", "reportsTo", "jobTitle"});

		DelimitedLineAggregator<Employees> lineAggregator = new DelimitedLineAggregator<>();
		lineAggregator.setDelimiter(",");
		lineAggregator.setFieldExtractor(fieldExtractor);

		fileItemWriter.setLineAggregator(lineAggregator);

		return fileItemWriter;
	} 
	
	// Step Execution
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Employees, Employees>chunk(10)
				.reader(employeesReader())
				.processor(employeesProcessor())
				.writer(employeesWriter())
				.build();
	}

	// Job Execution
	@Primary
	@Bean
	public Job exportEmployeesJob() {
		return jobBuilderFactory
				.get("exportEmployeesJob")
				.incrementer(new RunIdIncrementer())
				.flow(step1())
				.end()
				.build();
	}
}
