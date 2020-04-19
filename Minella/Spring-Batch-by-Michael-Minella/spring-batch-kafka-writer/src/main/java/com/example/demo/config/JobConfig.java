package com.example.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.kafka.KafkaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.example.demo.mapper.CustomerFieldSetMapper;
import com.example.demo.model.Customer;
import com.fasterxml.jackson.databind.JsonDeserializer;

@Configuration
public class JobConfig {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private KafkaTemplate<Long, Customer> kafkaTemplate;

	@Bean
	public FlatFileItemReader<Customer> customerItemReader() {
		FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
		reader.setLinesToSkip(1);
		reader.setResource(new ClassPathResource("/data/customer.csv"));

		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(new String[] { "id", "firstName", "lastName", "birthdate" });

		DefaultLineMapper<Customer> customerLineMapper = new DefaultLineMapper<>();
		customerLineMapper.setLineTokenizer(tokenizer);
		customerLineMapper.setFieldSetMapper(new CustomerFieldSetMapper());
		customerLineMapper.afterPropertiesSet();

		reader.setLineMapper(customerLineMapper);

		return reader;
	}
	
	@Bean
	public KafkaItemWriter<Long, Customer> kafkaItemWriter() throws Exception{
		KafkaItemWriter<Long, Customer> writer = new KafkaItemWriter<>();
		writer.setKafkaTemplate(kafkaTemplate);
		writer.setItemKeyMapper(Customer::getId);
		writer.setDelete(false);
		writer.afterPropertiesSet();
		return writer;
	}
		
	@Bean
	public Step step1() throws Exception {
		return stepBuilderFactory.get("step1")
				.<Customer, Customer>chunk(10)
				.reader(customerItemReader())
				.writer(kafkaItemWriter())
				.build();
	}
	
	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("job")
				.incrementer(new RunIdIncrementer())
				.start(step1())
				.build();
	}
}
