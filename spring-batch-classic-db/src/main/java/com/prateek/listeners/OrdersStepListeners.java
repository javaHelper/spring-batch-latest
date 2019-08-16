package com.prateek.listeners;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.prateek.mapper.OrdersRowMapper;
import com.prateek.model.Orders;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrdersStepListeners implements StepExecutionListener{
	@Autowired
	@Qualifier("primaryDataSource")
	private DataSource dataSource;
	
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.info("pipe step listener called");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		List<String> results = jdbcTemplate.queryForList("SELECT customerNumber FROM orders  ORDER BY customerNumber desc LIMIT 1", String.class);
		if(!results.isEmpty()) {
			stepExecution.getExecutionContext().putString("customerNumber", results.get(0));
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return null;
	}
}
