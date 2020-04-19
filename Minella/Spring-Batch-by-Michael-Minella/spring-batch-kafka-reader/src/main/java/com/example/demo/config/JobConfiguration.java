package com.example.demo.config;

import java.io.File;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.example.demo.lineAggregator.CustomerLineAggregator;
import com.example.demo.model.Customer;

@Configuration
public class JobConfiguration {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private KafkaProperties properties;
	
	@Bean
	public KafkaItemReader<Long, Customer> kafkaItemReader() {
		Properties props = new Properties();
		props.putAll(this.properties.buildConsumerProperties());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		return new KafkaItemReaderBuilder<Long, Customer>()
			.partitions(0)
			.consumerProperties(props)
			.name("customers-reader")
			.saveState(true)
			.topic("customers")
			.build();
	}
	
	
	@Bean
	public FlatFileItemWriter<Customer> customerItemWriter() throws Exception{
		String customerOutputPath = File.createTempFile("customerOutput", ".out").getAbsolutePath();
		System.out.println(">> Output Path = "+customerOutputPath);
		
		FlatFileItemWriter<Customer> itemWriter = new FlatFileItemWriter<>();
		//A LineAggregator implementation that simply calls Object.toString() on the given object
		//itemWriter.setLineAggregator(new PassThroughLineAggregator<>());
		
		//Alternate ways
		itemWriter.setLineAggregator(new CustomerLineAggregator());
		
		itemWriter.setResource(new FileSystemResource(customerOutputPath));
		itemWriter.afterPropertiesSet();
		
		return itemWriter;
	}
	
	
	@Bean
	public Step step1() throws Exception {
		return stepBuilderFactory.get("step1")
				.<Customer, Customer>chunk(100)
				.reader(kafkaItemReader())
				.writer(customerItemWriter())
				.build();
	}
	
	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer())
				.start(step1())
				.build();
	}
}
