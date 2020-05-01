package io.spring.versionedbatchjob.configuration;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;


@EnableBatchProcessing
@EnableTask
@Configuration
public class BatchConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private Environment environment;

	@Autowired
	private VersionedBatchJobProperties versionedBatchJobProperties;

	@Bean
	public Job job() {
		return this.jobBuilderFactory.get("job")
				.incrementer(new RunIdIncrementer())
				.start(step1())
				.build();
	}

	@Bean
	public Step step1() {
		return this.stepBuilderFactory.get("step1")
				.tasklet(new EnvironmentalTasklet(this.environment, this.versionedBatchJobProperties.getWait()))
				.build();
	}

	public static class EnvironmentalTasklet implements Tasklet {

		final private Environment environment;

		final private long wait;

		private long timeWaited = 0L;

		public EnvironmentalTasklet(Environment environment, long wait) {
			this.environment = environment;
			this.wait = wait;
		}

		@Override
		public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

			System.out.println(">> THIS IS VERSION TWO OF THE JOB");
			if(timeWaited == 0) {
				Map<String, Object> properties = this.getProperties();

//				Gson gson = new GsonBuilder().setPrettyPrinting().create();
//				String jsonProperties = gson.toJson(properties);

				System.out.println("********** PROPERTIES *************");
				System.out.println(properties.toString());
			}

			System.out.println(">> Sleeping for 3 seconds");
			Thread.sleep(3000L);
			this.timeWaited += 3000L;

			if(this.timeWaited >= this.wait) {
				return RepeatStatus.FINISHED;
			}
			else {
				return RepeatStatus.CONTINUABLE;
			}
		}

		@SuppressWarnings("rawtypes")
		private Map<String, Object> getProperties() {
			Map<String, Object> map = new TreeMap<>();

			for(Iterator it = ((AbstractEnvironment) this.environment).getPropertySources().iterator(); it.hasNext(); ) {
				PropertySource propertySource = (PropertySource) it.next();
				if (propertySource instanceof MapPropertySource) {
					map.putAll(((MapPropertySource) propertySource).getSource());
				}
			}

			return map;
		}
	}
}
