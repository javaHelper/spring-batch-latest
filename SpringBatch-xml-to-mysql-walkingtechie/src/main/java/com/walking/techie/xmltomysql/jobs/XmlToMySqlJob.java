package com.walking.techie.xmltomysql.jobs;

import com.walking.techie.xmltomysql.converter.ReportConverter;
import com.walking.techie.xmltomysql.listener.CustomJobCompletionListener;
import com.walking.techie.xmltomysql.model.Report;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

@Configuration
@EnableBatchProcessing
public class XmlToMySqlJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private ReportConverter reportConverter;

	@Autowired
	private DataSource dataSource;

	@Bean
	public Job reportJob(CustomJobCompletionListener listener) {
		return jobBuilderFactory.get("reportJob").incrementer(new RunIdIncrementer()).listener(listener)
				.flow(step1())
				.end().build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<Report, Report>chunk(10).reader(reader())
				.writer(writer()).build();
	}

	@Bean
	public StaxEventItemReader<Report> reader() {
		StaxEventItemReader<Report> reader = new StaxEventItemReader<>();
		reader.setResource(new ClassPathResource("report.xml"));
		reader.setFragmentRootElementName("record");
		reader.setUnmarshaller(unmarshaller());
		
		// The flag that determines whether to save internal state for restarts.
		reader.setSaveState(false);
		return reader;
	}

	@Bean
	public XStreamMarshaller unmarshaller() {
		Map<String, Class> aliases = new HashMap<String, Class>();
		aliases.put("record", Report.class);

		XStreamMarshaller unmarshal = new XStreamMarshaller();
		unmarshal.setAliases(aliases);
		unmarshal.setConverters(reportConverter);
		return unmarshal;
	}

	@Bean
	public JdbcBatchItemWriter<Report> writer() {
		JdbcBatchItemWriter<Report> writer = new JdbcBatchItemWriter<Report>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Report>());
		writer.setSql(
				"INSERT INTO report (id, date, impression, clicks, earning) VALUES (:id, :date, :impression, :clicks, :earning)");
		writer.setDataSource(dataSource);
		return writer;
	}
}
