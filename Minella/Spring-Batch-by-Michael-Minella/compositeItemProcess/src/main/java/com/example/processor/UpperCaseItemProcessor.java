package com.example.processor;

import org.springframework.batch.item.ItemProcessor;

import com.example.model.Customer;

public class UpperCaseItemProcessor implements ItemProcessor<Customer, Customer> {

	@Override
	public Customer process(Customer item) throws Exception {
		
		return Customer.builder()
				.id(item.getId())
				.firstName(item.getFirstName().toUpperCase())
				.lastName(item.getLastName().toUpperCase())
				.birthdate(item.getBirthdate())
				.build();
	}

}
