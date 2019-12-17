package com.example;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class MultipleFlatFilesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultipleFlatFilesApplication.class, args);
	}
}
