package com.prateek.step.listerns;

import org.springframework.batch.core.ItemReadListener;

import com.prateek.model.Customers;

public class CustomerItemReadListener implements ItemReadListener<Customers>{

	@Override
	public void beforeRead() {
		System.out.println("ItemReadListener - beforeRead");
	}

	@Override
	public void afterRead(Customers item) {
		System.out.println("ItemReadListener - afterRead");
	}

	@Override
	public void onReadError(Exception ex) {
		System.out.println("ItemReadListener - onReadError");
	}
}
