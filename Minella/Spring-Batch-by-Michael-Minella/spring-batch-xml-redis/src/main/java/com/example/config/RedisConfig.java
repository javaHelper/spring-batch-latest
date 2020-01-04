package com.example.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;


@Configuration
public class RedisConfig {
	@Autowired
	private Environment env;
	
	/*@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setDatabase(0);
		redisStandaloneConfiguration.setHostName(env.getProperty("spring.redis.host"));
		redisStandaloneConfiguration.setPort(Integer.parseInt(env.getProperty("spring.redis.port")));
		
		JedisClientConfigurationBuilder builder = JedisClientConfiguration.builder();
		builder.connectTimeout(Duration.ofSeconds(60)); //60 s connection timeout
		return new JedisConnectionFactory();
	}*/
	
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}
}
