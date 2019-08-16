package com.prateek.step.listerns;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class CustomerStepListerners implements StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("StepExecutionListener - beforeStep");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("StepExecutionListener - afterStep");
		return null;
	}
}
