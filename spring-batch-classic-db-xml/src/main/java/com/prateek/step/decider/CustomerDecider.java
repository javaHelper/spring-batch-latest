package com.prateek.step.decider;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.Data;


@Data
public class CustomerDecider implements JobExecutionDecider {

	private JdbcTemplate jdbcTemplate;
	
	private BigDecimal jobInstanceId;
	
	public CustomerDecider(@Autowired @Qualifier("dataSource") DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecutionStatus) {
		//String customerRunMode = (String) jobExecution.getExecutionContext().get("customerRunMode");
		
		String sql = "SELECT COUNT(*) FROM classicmodels.customers";
		Integer countOfCustomers = jdbcTemplate.queryForObject(sql, Integer.class);
		

		if (countOfCustomers > 0) 
			return new FlowExecutionStatus("YES_SUCCESS");
		else
			return new FlowExecutionStatus("NO_FAILURE");
	}
}