package io.spring.batch.partitiondemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

@EnableTask
@EnableBatchProcessing
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		String [] newArgs = new String[] {"inputFiles=/data/csv/transactions*.csv"};

		List<String> strings = Arrays.asList(args);

		List<String> finalArgs = new ArrayList<>(strings.size() + 1);
		finalArgs.addAll(strings);
		finalArgs.add("inputFiles=/data/csv/transactions*.csv");

		SpringApplication.run(DemoApplication.class, finalArgs.toArray(new String[finalArgs.size()]));
	}
}
