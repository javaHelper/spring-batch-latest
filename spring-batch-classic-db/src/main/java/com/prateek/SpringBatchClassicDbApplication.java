package com.prateek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class SpringBatchClassicDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchClassicDbApplication.class, args);
	}
}
