package com.example.tasklet;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.model.Payment;

public class PaymentDataTasklet implements Tasklet{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		List<Payment> payments = jdbcTemplate.query("select * from payment", (resultSet, i) -> {
            return toPayment(resultSet);
        });
		
		chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("payments", payments);
		return RepeatStatus.FINISHED;
	}
	
	private Payment toPayment(ResultSet resultSet) throws SQLException {
		Payment payment = new Payment();
        payment.setPaymentId(resultSet.getLong("paymentId"));
        payment.setAmount(resultSet.getDouble("amount"));
        payment.setCustomerId(resultSet.getLong("customerId"));
        payment.setPaymentDate(getPaymentDate(resultSet.getDate("paymentDate")));
        return payment;
    }

	private LocalDate getPaymentDate(Date paymentDate) {
		Instant instant = Instant.ofEpochMilli(paymentDate.getTime()); 
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()); 
		LocalDate localDate = localDateTime.toLocalDate();
		return localDate;
	}
}
