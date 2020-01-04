package com.example.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.decider.ContextDecider;
import com.example.listener.PaymentStepExecutionListener;
import com.example.model.Payment;
import com.example.rowmapper.PaymentRowMapper;
import com.example.tasklet.PaymentDataTasklet;
import com.example.tasklet.SendPaymentBatchFilesTasklet;
import com.example.tasklet.PaymentContextTasklet;

@Configuration
public class JobConfiguration {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	@StepScope
	public PaymentContextTasklet paymentContextTasklet() {
		return new PaymentContextTasklet();
	}
	
	// Either execute for "Payment" or "Order"
	@Bean
	public ContextDecider contextDecider() {
		return new ContextDecider();
	}
	
	@Bean
	public JdbcPagingItemReader<Payment> pagingItemReader(){
		JdbcPagingItemReader<Payment> reader = new JdbcPagingItemReader<>();
		reader.setDataSource(this.dataSource);
		reader.setFetchSize(10);
		reader.setRowMapper(new PaymentRowMapper());
	
		Map<String, Order> sortKeys = new HashMap<>();
		sortKeys.put("paymentId", Order.ASCENDING);

		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		queryProvider.setSelectClause("select paymentId, amount, customerId, paymentDate");
		queryProvider.setFromClause("from payment");
		queryProvider.setSortKeys(sortKeys);
		
		reader.setQueryProvider(queryProvider);
		return reader;
	}
	
	@Bean
	public ItemWriter<Payment> paymentItemWriter(){
		return items -> {
			for(Payment c : items) {
				System.out.println(c.toString());
			}
		};
	}
	
	// You can access the stepExecutionContext only within a bean defined in the scope="step".
	@Bean
	@StepScope
	public PaymentStepExecutionListener paymentStepExecutionListener() {
		return new PaymentStepExecutionListener();
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Payment, Payment>chunk(10)
				.reader(pagingItemReader())
				.writer(paymentItemWriter())
				.listener(paymentStepExecutionListener())
				.build();
	}
	
	@Bean
	public PaymentDataTasklet paymentDataTasklet() {
		return new PaymentDataTasklet();
	}
	
	@Bean
	public Step paymentContextStep() {
		return stepBuilderFactory.get("paymentContextStep")
				.tasklet(paymentContextTasklet())
				.build();
	}
	
	@Bean
	public Step paymentDataStep() {
		return stepBuilderFactory.get("paymentDataStep")
				.tasklet(paymentDataTasklet())
				.build();
	}
	
	@Bean
	public Step endStep() {
		return stepBuilderFactory.get("endStep")
				.tasklet(null)
				.build();
	}
	
	@Bean 
	public SendPaymentBatchFilesTasklet sendPaymentBatchFilesTasklet() {
		return new SendPaymentBatchFilesTasklet();
	}
	
	@Bean 
	public Step sendPaymentBatchFiles() {
		return stepBuilderFactory.get("sendPaymentBatchFiles")
				.tasklet(sendPaymentBatchFilesTasklet())
				.build();
	}
	
	@Bean
	public Job paymentDataBatchJob() {
		return jobBuilderFactory.get("paymentDataBatchJob")
				.start(paymentContextStep())
				.next(contextDecider())
					.on("Payment").to(paymentDataStep()).on("COMPLETED").to(step1())
						.next(sendPaymentBatchFiles())
				.from(contextDecider())
					.on("Order").to(endStep()).end()
				.build();
	}
}
