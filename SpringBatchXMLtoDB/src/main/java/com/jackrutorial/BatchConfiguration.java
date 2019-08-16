package com.jackrutorial;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.jackrutorial.model.User;

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

	//To resolve ${} in @Value
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
		
		/*dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/springbatch");
		dataSource.setUsername("root");
		dataSource.setPassword("root");*/
		return dataSource;
	}

	// Read XML file
	@Bean
	public StaxEventItemReader<User> reader() {
		Map<String, String> aliases = new HashMap<>();
		aliases.put("user", "com.jackrutorial.model.User");

		XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
		xStreamMarshaller.setAliases(aliases);

		StaxEventItemReader<User> reader = new StaxEventItemReader<>();
		reader.setResource(new ClassPathResource("user.xml"));
		reader.setFragmentRootElementName("user");
		reader.setUnmarshaller(xStreamMarshaller);
		return reader;
	}

	// Write to DB
	@Bean
	public JdbcBatchItemWriter<User> writer() {
		JdbcBatchItemWriter<User> writer = new JdbcBatchItemWriter<>();
		writer.setDataSource(dataSource);
		writer.setSql("INSERT INTO user(id, name) VALUES (?,?)");
		writer.setItemPreparedStatementSetter(new UserItemPreparedStmSetter());
		return writer;
	}

	// Actual Job
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<User, User>chunk(10).reader(reader()).writer(writer()).build();
	}

	@Bean
	public Job importUserJob() {
		return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
	}
}
