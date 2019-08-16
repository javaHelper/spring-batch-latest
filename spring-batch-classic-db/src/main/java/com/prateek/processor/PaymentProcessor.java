package com.prateek.processor;

import org.springframework.batch.item.ItemProcessor;

import com.prateek.model.Payments;

import lombok.Data;

@Data
public class PaymentProcessor implements ItemProcessor<Payments, Payments> {
	private String threadName;

	@Override
	public Payments process(Payments item) throws Exception {
		System.out.println(threadName + " processing : " + item.getCheckNumber() + " : " + item.getAmount());
		return item;
	}
}
