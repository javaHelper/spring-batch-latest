package com.prateek.step.listerns;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;

import com.prateek.model.Customers;

public class CustomerItemWriterListners implements ItemWriteListener<Customers> {

	@Override
	public void beforeWrite(List<? extends Customers> items) {
		System.out.println("ItemWriteListener - beforeWrite");
	}

	@Override
	public void afterWrite(List<? extends Customers> items) {
		System.out.println("ItemWriteListener - afterWrite");
	}

	@Override
	public void onWriteError(Exception exception, List<? extends Customers> items) {
		System.out.println("ItemWriteListener - onWriteError");
	}
}
