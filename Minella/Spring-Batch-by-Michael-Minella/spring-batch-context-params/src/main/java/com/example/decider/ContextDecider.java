package com.example.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class ContextDecider implements JobExecutionDecider{

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		String runMode = (String) jobExecution.getExecutionContext().get("runMode");
		if(runMode != null && "Payment".equalsIgnoreCase(runMode)) {
			 return new FlowExecutionStatus("Payment");
		}
		return new FlowExecutionStatus("Order");
	}
}
