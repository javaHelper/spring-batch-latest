package com.prateek.listeners;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrdersJobExecutionListener implements JobExecutionListener {
	private DateTime startTime, stopTime;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		startTime = new DateTime();
		log.info("ExamResult Job starts at :" + startTime);
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		stopTime = new DateTime();
		log.info("ExamResult Job stops at :" + stopTime);
		log.info("Total time take in millis :" + getTimeInMillis(startTime, stopTime));

		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("=========================>>>>>>>>>>> ExamResult job completed successfully");
		} else if (jobExecution.getStatus() == BatchStatus.FAILED) {
			log.info("ExamResult job failed with following exceptions ");
			List<Throwable> exceptionList = jobExecution.getAllFailureExceptions();
			for (Throwable th : exceptionList) {
				log.error("exception :" + th.getLocalizedMessage());
			}
		}
	}

	private long getTimeInMillis(DateTime start, DateTime stop) {
		return stop.getMillis() - start.getMillis();
	}
}
