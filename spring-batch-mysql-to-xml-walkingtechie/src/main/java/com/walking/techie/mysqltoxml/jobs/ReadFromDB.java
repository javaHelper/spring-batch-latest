package com.walking.techie.mysqltoxml.jobs;

import com.walking.techie.mysqltoxml.model.User;
import com.walking.techie.mysqltoxml.orm.JpaQueryProviderImpl;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

@Configuration
@EnableBatchProcessing
public class ReadFromDB {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Bean
	public Job readUser() throws Exception {
		return jobBuilderFactory.get("readUser").flow(step1()).end().build();
	}

	@Bean
	public Step step1() throws Exception {
		return stepBuilderFactory.get("step1").<User, User>chunk(10).reader(reader()).writer(writer()).build();
	}

	@Bean(destroyMethod="")
	public JpaPagingItemReader<User> reader() throws Exception {
		JpaPagingItemReader<User> databaseReader = new JpaPagingItemReader<>();
		databaseReader.setEntityManagerFactory(entityManagerFactory);
		
		JpaQueryProviderImpl<User> jpaQueryProvider = new JpaQueryProviderImpl<>();
		jpaQueryProvider.setQuery("User.findAll");
		
		databaseReader.setQueryProvider(jpaQueryProvider);
		databaseReader.setPageSize(1000);
		databaseReader.afterPropertiesSet();
		return databaseReader;
	}

	@Bean(destroyMethod="")
	public StaxEventItemWriter<User> writer() {
		StaxEventItemWriter<User> writer = new StaxEventItemWriter<>();
		writer.setResource(new FileSystemResource("xml/user.xml"));
		writer.setMarshaller(userUnmarshaller());
		writer.setRootTagName("users");
		return writer;
	}

	@Bean
	public XStreamMarshaller userUnmarshaller() {
		XStreamMarshaller unMarshaller = new XStreamMarshaller();
		Map<String, Class> aliases = new HashMap<String, Class>();
		aliases.put("user", User.class);
		unMarshaller.setAliases(aliases);
		return unMarshaller;
	}
}
