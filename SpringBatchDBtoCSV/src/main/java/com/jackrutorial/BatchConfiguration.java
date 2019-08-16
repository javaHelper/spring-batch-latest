package com.jackrutorial;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.jackrutorial.mapper.UserRowMapper;
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

	@Bean
	public UserItemProcessor processor() {
		return new UserItemProcessor();
	}

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
	public JdbcCursorItemReader<User> reader() {
		JdbcCursorItemReader<User> reader = new JdbcCursorItemReader<>();
		reader.setDataSource(dataSource);
		reader.setSql("SELECT id, name FROM user");
		reader.setRowMapper(new UserRowMapper());
		return reader;
	}

	@Bean
	public FlatFileItemWriter<User> writer() {
		FlatFileItemWriter<User> writer = new FlatFileItemWriter<User>();
		writer.setResource(new ClassPathResource("users.csv"));
		writer.setLineAggregator(new DelimitedLineAggregator<User>() {
			{
				setDelimiter(",");
				setFieldExtractor(new BeanWrapperFieldExtractor<User>() {
					{
						setNames(new String[] { "id", "name" });
					}
				});
			}
		});

		return writer;
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<User, User>chunk(10).reader(reader()).processor(processor())
				.writer(writer()).build();
	}

	@Bean
	public Job exportUserJob() {
		return jobBuilderFactory.get("exportUserJob").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
	}
}
