package com.example.processor;

import org.springframework.batch.item.ItemProcessor;

import com.example.model.Customer;

public class FilteringItemProcessor implements ItemProcessor<Customer, Customer> {

	@Override
	public Customer process(Customer item) throws Exception {
		if(item.getId() % 2 == 0)
			return null;
		else
			return item;
	}

}
