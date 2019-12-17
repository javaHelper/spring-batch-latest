package com.example.config;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class MyJobConfiguration extends DefaultBatchConfigurer {

	@Override
	protected JobRepository createJobRepository() throws Exception {
		MapJobRepositoryFactoryBean factoryBean = new MapJobRepositoryFactoryBean();
		factoryBean.afterPropertiesSet();
		return factoryBean.getObject();
	}
}