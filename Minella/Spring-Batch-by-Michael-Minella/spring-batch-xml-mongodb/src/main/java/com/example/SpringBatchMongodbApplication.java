package com.example;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableBatchProcessing
@EnableMongoRepositories(basePackages = "com.example.repository")
public class SpringBatchMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchMongodbApplication.class, args);
	}
}
