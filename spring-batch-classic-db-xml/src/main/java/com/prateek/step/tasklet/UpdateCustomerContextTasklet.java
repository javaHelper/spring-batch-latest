package com.prateek.step.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import lombok.Data;


@Data
public class UpdateCustomerContextTasklet implements Tasklet{
	private String customerRunMode ;
	private ExecutionContext executionContext;
	

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		System.out.println("=======> Customer Run Mode : "+customerRunMode);
		chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("customerRunMode", customerRunMode);

		return RepeatStatus.FINISHED;
	}
}
