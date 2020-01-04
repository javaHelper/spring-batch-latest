package com.example.listener;

import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Value;

import com.example.model.Payment;

public class PaymentStepExecutionListener implements StepExecutionListener{
	@Value("#{stepExecution.jobExecution.jobInstance.jobName}")
	private String jobName;
	
	@Value("#{stepExecution.jobExecution.jobId}")
	private String jobId;
	
	@Value("#{stepExecution.jobExecution.executionContext}")
	private ExecutionContext executionContext;
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("PaymentStepExecutionListener - beforeStep executed ...");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		List<Payment> payments = (List<Payment>) stepExecution.getJobExecution().getExecutionContext().get("payments");
		System.out.println(payments);
		return stepExecution.getExitStatus();
	}

}
