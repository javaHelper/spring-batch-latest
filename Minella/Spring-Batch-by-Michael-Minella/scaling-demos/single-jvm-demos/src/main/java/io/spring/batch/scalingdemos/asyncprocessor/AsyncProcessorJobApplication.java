package io.spring.batch.scalingdemos.asyncprocessor;

import javax.sql.DataSource;

import io.spring.batch.scalingdemos.domain.Transaction;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;


@EnableBatchProcessing
@SpringBootApplication
public class AsyncProcessorJobApplication {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	@StepScope
	public FlatFileItemReader<Transaction> fileTransactionReader(
			@Value("#{jobParameters['inputFlatFile']}") Resource resource) {

		return new FlatFileItemReaderBuilder<Transaction>()
				.saveState(false)
				.resource(resource)
				.delimited()
				.names(new String[] {"account", "amount", "timestamp"})
				.fieldSetMapper(fieldSet -> {
					Transaction transaction = new Transaction();

					transaction.setAccount(fieldSet.readString("account"));
					transaction.setAmount(fieldSet.readBigDecimal("amount"));
					transaction.setTimestamp(fieldSet.readDate("timestamp", "yyyy-MM-dd HH:mm:ss"));

					return transaction;
				})
				.build();
	}

	@Bean
	@StepScope
	public JdbcBatchItemWriter<Transaction> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Transaction>()
				.dataSource(dataSource)
				.beanMapped()
				.sql("INSERT INTO TRANSACTION (ACCOUNT, AMOUNT, TIMESTAMP) VALUES (:account, :amount, :timestamp)")
				.build();
	}

	@Bean
	public AsyncItemProcessor<Transaction, Transaction> asyncItemProcessor() {
		AsyncItemProcessor<Transaction, Transaction> processor = new AsyncItemProcessor<>();

		processor.setDelegate(processor());
		processor.setTaskExecutor(new SimpleAsyncTaskExecutor());

		return processor;
	}

	@Bean
	public AsyncItemWriter<Transaction> asyncItemWriter() {
		AsyncItemWriter<Transaction> writer = new AsyncItemWriter<>();

		writer.setDelegate(writer(null));

		return writer;
	}

	@Bean
	public ItemProcessor<Transaction, Transaction> processor() {
		return (transaction) -> {
			Thread.sleep(5);
			return transaction;
		};
	}

	@Bean
	public Job asyncJob() {
		return this.jobBuilderFactory.get("asyncJob")
				.start(step1async())
				.build();
	}

//	@Bean
//	public Job job1() {
//		return this.jobBuilderFactory.get("job1")
//				.start(step1())
//				.build();
//	}

	@Bean
	public Step step1async() {
		return this.stepBuilderFactory.get("step1async")
				.<Transaction, Transaction>chunk(100)
				.reader(fileTransactionReader(null))
				.processor((ItemProcessor) asyncItemProcessor())
				.writer(asyncItemWriter())
				.build();
	}
//
//	@Bean
//	public Step step1() {
//		return this.stepBuilderFactory.get("step1")
//				.<Transaction, Transaction>chunk(100)
//				.reader(fileTransactionReader(null))
//				.processor(processor())
//				.writer(writer(null))
//				.build();
//	}
//
	public static void main(String[] args) {
		String [] newArgs = new String[] {"inputFlatFile=/data/csv/bigtransactions.csv"};

		SpringApplication.run(AsyncProcessorJobApplication.class, newArgs);
	}
}
