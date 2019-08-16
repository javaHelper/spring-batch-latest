package com.prateek.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class OrdersTasklet implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("customerNumber", "363");
		return RepeatStatus.FINISHED;
	}
}
