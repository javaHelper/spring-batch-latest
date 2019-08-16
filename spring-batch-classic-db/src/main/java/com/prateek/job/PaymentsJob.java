package com.prateek.job;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.prateek.model.Payments;
import com.prateek.partitioner.RangePartitioner;
import com.prateek.processor.PaymentProcessor;
import com.prateek.tasklet.PaymentTasklet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableBatchProcessing
public class PaymentsJob {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	@Qualifier("primaryDataSource")
	private DataSource dataSource;

	@Bean
	public PaymentTasklet paymentTasklet() {
		return new PaymentTasklet();
	}

	@Bean
	public RangePartitioner rangePartitioner() {
		return new RangePartitioner();
	}

	@Bean
	public SimpleAsyncTaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

	@Bean
	@StepScope
	public PaymentProcessor slaveProcessor(@Value("#{stepExecutionContext[name]}") String name) {
		log.info("********called slave processor **********");
		PaymentProcessor paymentProcessor = new PaymentProcessor();
		paymentProcessor.setThreadName(name);
		return paymentProcessor;
	}

	@Bean
	@StepScope
	public JdbcPagingItemReader<Payments> slaveReader(
			@Value("#{stepExecutionContext[fromId]}") final String fromId,
			@Value("#{stepExecutionContext[toId]}") final String toId,
			@Value("#{stepExecutionContext[name]}") final String name){

		log.info("slaveReader start " + fromId + " " + toId);

		// for reading database records using JDBC in a paging fashion. 
		JdbcPagingItemReader<Payments> reader = new JdbcPagingItemReader<>();
		reader.setDataSource(dataSource);
		reader.setQueryProvider(queryProvider());
		reader.setPageSize(1000);

		// This is to set place holders
		Map<String, Object> parameterValues = new HashMap<>();
		parameterValues.put("fromId", fromId);
		parameterValues.put("toId", toId);
		log.info("Parameter Value " + name + " " + parameterValues);

		reader.setParameterValues(parameterValues);

		BeanPropertyRowMapper<Payments> beanPropertyRowMapper = new BeanPropertyRowMapper<>();
		beanPropertyRowMapper.setMappedClass(Payments.class);

		reader.setRowMapper(beanPropertyRowMapper);
		log.info("slaveReader end " + fromId + " " + toId);
		return reader;
	}


	private PagingQueryProvider queryProvider() {
		log.info("queryProvider start ");
		SqlPagingQueryProviderFactoryBean provider = new SqlPagingQueryProviderFactoryBean();
		provider.setDataSource(dataSource);
		provider.setSelectClause("select customerNumber, checkNumber, paymentDate, amount");
		provider.setFromClause("from payments");
		provider.setWhereClause("where customerNumber >= :fromId and customerNumber <= :toId");
		provider.setSortKey("customerNumber");

		try {
			return provider.getObject();
		} catch (Exception e) {
			log.error("Exception | queryProvider() "+e.getMessage());
		}

		log.info("queryProvider end ");
		return null;
	}

	@Bean
	@StepScope
	public FlatFileItemWriter<Payments> slaveWriter(@Value("#{stepExecutionContext[fromId]}") final String fromId,
			@Value("#{stepExecutionContext[toId]}") final String toId) {
		
		FlatFileItemWriter<Payments> reader = new FlatFileItemWriter<>();
		reader.setResource(new FileSystemResource("csv/outputs/users.processed" + fromId + "-" + toId + ".csv"));
		reader.setAppendAllowed(true);

		reader.setLineAggregator(new DelimitedLineAggregator<Payments>() {
			{
				setDelimiter(",");
				setFieldExtractor(new BeanWrapperFieldExtractor<Payments>() {
					{
						setNames(new String[] { "customerNumber", "checkNumber", "paymentDate", "amount"});
					}
				});
			}
		});
		return reader;
	}

	@Bean(name = "slave")
	public Step slave() {
		log.info("...........called slave .........");
		return stepBuilderFactory.get("slave")
				.<Payments, Payments>chunk(100)
				.reader(slaveReader(null, null, null))
				.processor(slaveProcessor(null))
				.writer(slaveWriter(null, null))
				.build();
	}

	@Bean
	public PartitionHandler masterSlaveHandler() {
		TaskExecutorPartitionHandler handler = new TaskExecutorPartitionHandler();
		handler.setGridSize(10);
		handler.setTaskExecutor(taskExecutor());
		handler.setStep(slave());
		try {
			handler.afterPropertiesSet();
		} catch (Exception e) {
			log.error("Exception | PartitionHandler | masterSlaveHandler() "+e.getMessage());
		}
		return handler;
	}

	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2").tasklet(paymentTasklet()).build();
	}

	@Bean
	public Step masterStep() {
		return stepBuilderFactory.get("masterStep").partitioner(slave().getName(), rangePartitioner())
				.partitionHandler(masterSlaveHandler()).build();
	}

	@Bean
	public Job PartitionJob() {
		return jobBuilderFactory.get("partitionJob").incrementer(new RunIdIncrementer())
				.start(masterStep()).next(step2()).build();
	}
}
