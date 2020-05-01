package com.example;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.PushGateway;

import org.springframework.boot.actuate.autoconfigure.metrics.export.prometheus.PrometheusProperties;
import org.springframework.boot.actuate.metrics.export.prometheus.PrometheusPushGatewayManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Disable It
 */
//@Configuration
public class PrometheusConfiguration {

	// @Bean
	// public LoggingMeterRegistry loggingMeterRegistry() {
	// return new LoggingMeterRegistry();
	// }

	@Bean
	public PrometheusPushGatewayManager prometheusPushGatewayManager(CollectorRegistry collectorRegistry,
			PrometheusProperties prometheusProperties, Environment environment) throws MalformedURLException {

		PrometheusProperties.Pushgateway properties = prometheusProperties.getPushgateway();
		PushGateway pushGateway = new PushGateway(new URL(properties.getBaseUrl()));
		Duration pushRate = properties.getPushRate();
		String job = getJob(properties, environment);
		Map<String, String> groupingKey = properties.getGroupingKey();
		PrometheusPushGatewayManager.ShutdownOperation shutdownOperation = properties.getShutdownOperation();
		return new PrometheusPushGatewayManager(pushGateway, collectorRegistry, pushRate, job, groupingKey,
				shutdownOperation);
	}

	private String getJob(PrometheusProperties.Pushgateway properties, Environment environment) {
		String job = properties.getJob();
		job = (job != null) ? job : environment.getProperty("spring.application.name");
		return (job != null) ? job : "spring";
	}
}