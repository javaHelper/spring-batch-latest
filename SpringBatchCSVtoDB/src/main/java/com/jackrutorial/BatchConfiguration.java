package com.jackrutorial;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.jackrutorial.model.User;
import com.jackrutorial.processor.UserItemProcessor;

@Configuration
@EnableBatchProcessing
@PropertySource("classpath:db.properties")
public class BatchConfiguration {
	@Value("${mysql.driverClassName}")
	private String driverClassName;

	@Value("${mysql.url}")
	private String url;

	@Value("${mysql.username}")
	private String username;

	@Value("${mysql.password}")
	private String password;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;

	// To resolve ${} in @Value
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	// Data Source
	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean
	public FlatFileItemReader<User> reader() {
		FlatFileItemReader<User> reader = new FlatFileItemReader<User>();
		reader.setResource(new ClassPathResource("users.csv"));
		reader.setLineMapper(new DefaultLineMapper<User>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "name" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<User>() {
					{
						setTargetType(User.class);
					}
				});

			}
		});

		return reader;
	}

	@Bean
	public JdbcBatchItemWriter<User> writer() {
		JdbcBatchItemWriter<User> writer = new JdbcBatchItemWriter<>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<User>());
		writer.setSql("INSERT INTO user(name) VALUES (:name)");
		writer.setDataSource(dataSource);
		return writer;
	}

	@Bean
	public UserItemProcessor processor() {
		return new UserItemProcessor();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<User, User>chunk(3).reader(reader()).processor(processor())
				.writer(writer()).build();
	}

	@Bean
	public Job importUserJob() {
		return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
	}
}
