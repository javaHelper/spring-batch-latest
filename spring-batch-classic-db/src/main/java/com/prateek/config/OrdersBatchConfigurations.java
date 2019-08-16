package com.prateek.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.support.ListPreparedStatementSetter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.prateek.callback.OrdersFlatFileItemWriterCallback;
import com.prateek.mapper.OrdersRowMapper;
import com.prateek.model.Orders;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableBatchProcessing
public class OrdersBatchConfigurations {
	@Autowired
	@Qualifier("primaryDataSource")
	private DataSource dataSource;

	
	
	// This creates header in the CSV file !
	@Bean
	public OrdersFlatFileItemWriterCallback ordersHeaderCallback() {
		return new OrdersFlatFileItemWriterCallback();
	}

	@Bean(destroyMethod="")
	@StepScope
	public JdbcCursorItemReader<Orders> ordersReader(@Value("#{stepExecutionContext['customerNumber']}") String customerNumber){
		log.debug("THE VALUES FROM STEP_CONTEXT : "+customerNumber);
		JdbcCursorItemReader<Orders> itemReader = new JdbcCursorItemReader<>();
		itemReader.setDataSource(dataSource);

		itemReader.setSql("SELECT T1.orderNumber, T1.orderDate, T1.requiredDate, T1.shippedDate, T1.status, T1.comments, T1.customerNumber,SUM(quantityOrdered * priceEach) total "
				+ "FROM orders AS T1 "
				+ "INNER JOIN orderdetails AS T2 "
				+ "ON T1.orderNumber = T2.orderNumber "
				+ "WHERE T1.customerNumber = "+ customerNumber+ " "
				+ "GROUP BY orderNumber ");
		itemReader.setRowMapper(new OrdersRowMapper());
		itemReader.setIgnoreWarnings(true);

		return itemReader;
	}


	@Bean(destroyMethod="")
	public FlatFileItemWriter<Orders> ordersWriter(){
		FlatFileItemWriter<Orders> fileItemWriter = new FlatFileItemWriter<>();
		fileItemWriter.setResource(new FileSystemResource("csv/Orders.csv"));
		fileItemWriter.setHeaderCallback(ordersHeaderCallback());

		BeanWrapperFieldExtractor<Orders> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(new String[] {"orderNumber", "orderDate", "requiredDate", "shippedDate", "status", "comments", "customerNumber", "total"});

		DelimitedLineAggregator<Orders> lineAggregator = new DelimitedLineAggregator<>();
		lineAggregator.setDelimiter(",");
		lineAggregator.setFieldExtractor(fieldExtractor);


		// The PassThrough will implement like toString - Don't do 
		//lineAggregator.setFieldExtractor(new PassThroughFieldExtractor<Orders>());

		fileItemWriter.setLineAggregator(lineAggregator);
		fileItemWriter.setAppendAllowed(true);

		return fileItemWriter;
	}
}
