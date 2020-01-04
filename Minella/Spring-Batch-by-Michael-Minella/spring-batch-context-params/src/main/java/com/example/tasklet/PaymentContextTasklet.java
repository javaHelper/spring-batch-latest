package com.example.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

public class PaymentContextTasklet implements Tasklet {

	@Value("#{jobParameters['runMode']}")
	private String runMode;

	@Value("#{stepExecution.jobExecution.executionContext}")
	private ExecutionContext executionContext;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("runMode",runMode);
		return RepeatStatus.FINISHED;
	}
}
