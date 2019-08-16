package com.prateek.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing
@EnableScheduling
@PropertySource("classpath:scheduler.properties")
public class CommonConfig {

	@Bean
	public ThreadPoolTaskExecutor orderThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
		t.setCorePoolSize(20);
		t.setMaxPoolSize(20);
		t.setAllowCoreThreadTimeOut(true);
		return t;
	}
	
	@Bean
	public SimpleAsyncTaskExecutor asyncTaskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}
}
