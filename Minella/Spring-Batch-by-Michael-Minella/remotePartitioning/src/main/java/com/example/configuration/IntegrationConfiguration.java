package com.example.configuration;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.PollableChannel;


@Configuration
public class IntegrationConfiguration {
	@Bean
	public MessagingTemplate messagingTemplate() {
		MessagingTemplate messagingTemplate = new MessagingTemplate();
		messagingTemplate.setReceiveTimeout(600000000L);
		return messagingTemplate;
	}
	
	@Bean
	public DirectChannel directChannel() {
		return new DirectChannel();
	}
	
	@Bean
	@ServiceActivator(inputChannel = "outboundRequest")
	public AmqpOutboundEndpoint amqpOutboundEndpoint(AmqpTemplate amqpTemplate) {
		AmqpOutboundEndpoint endpoint = new AmqpOutboundEndpoint(amqpTemplate);
		endpoint.setExpectReply(true);
		endpoint.setOutputChannel(inboundRequest());
		endpoint.setRoutingKey("partition.requests");
		return endpoint;
	}
	
	@Bean
	public Queue requestQueue() {
		return new Queue("partition.request", false);
	}
	
	@Bean
	@Profile("salve")
	public AmqpInboundChannelAdapter inboud(SimpleMessageListenerContainer listenerContainer) {
		AmqpInboundChannelAdapter adapter = new AmqpInboundChannelAdapter(listenerContainer);
		adapter.setOutputChannel(inboundRequest());
		adapter.afterPropertiesSet();
		return adapter;
	}
	
	@Bean
	public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueueNames("partition.requests");
		container.setAutoStartup(false);
		return container;
	}
	
	@Bean
	public PollableChannel outboundStaging() {
		return new NullChannel();
	}
	
	@Bean
	public QueueChannel inboundRequest() {
		return new QueueChannel();
	}
}
