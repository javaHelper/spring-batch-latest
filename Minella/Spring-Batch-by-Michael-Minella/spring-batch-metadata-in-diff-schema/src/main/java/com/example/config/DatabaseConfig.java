package com.example.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
public class DatabaseConfig {
	@Autowired
	private Environment env;

	// For Test schema
	@Bean(name="secondaryDS")
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource getSeconadaryBatchDataSource(){
		return DataSourceBuilder.create()
				.url(env.getProperty("spring.datasource.url"))
				.driverClassName(env.getProperty("spring.datasource.driver-class-name"))
				.username(env.getProperty("spring.datasource.username"))
				.password(env.getProperty("spring.datasource.password"))
				.build();
	}

	// For "batchmetadata" tables
	@Bean(name="primaryDS")
	@Primary
	@ConfigurationProperties(prefix="spring.hello.datasource")
	public DataSource getPrimaryBatchDataSource(){
		return DataSourceBuilder.create()
				.url(env.getProperty("spring.hello.datasource.url"))
				.driverClassName(env.getProperty("spring.hello.datasource.driver-class-name"))
				.username(env.getProperty("spring.hello.datasource.username"))
				.password(env.getProperty("spring.hello.datasource.password"))
				.build();
	}
	
	//Ref: https://stackoverflow.com/questions/28275448/multiple-data-source-and-schema-creation-in-spring-boot
	@Bean(name = "primaryEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory( EntityManagerFactoryBuilder builder) {
	    Map<String, Object> properties = new HashMap<String, Object>();
	    properties.put("hibernate.hbm2ddl.auto", "update");
	    return builder
	            .dataSource(getSeconadaryBatchDataSource())
	            .packages("com.example.model")
	            .persistenceUnit("default")
	            .properties(properties)
	            .build();
	}

	/*@Bean(name = "secondaryEntityManagerFactory")
	@Primary
	public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
	        EntityManagerFactoryBuilder builder) {
	    Map<String, Object> properties = new HashMap<String, Object>();
	    properties.put("hibernate.hbm2ddl.auto", "create");
	    return builder
	            .dataSource(getPrimaryBatchDataSource())
	            .packages("com.example.model")
	            .persistenceUnit("default")
	            .properties(properties)
	            .build();
	}*/
}
