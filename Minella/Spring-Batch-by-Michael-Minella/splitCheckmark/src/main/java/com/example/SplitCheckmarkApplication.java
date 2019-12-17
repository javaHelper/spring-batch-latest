package com.example;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SplitCheckmarkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SplitCheckmarkApplication.class, args);
	}
}
