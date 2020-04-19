package com.example.demo.lineAggregator;

import org.springframework.batch.item.file.transform.LineAggregator;

import com.example.demo.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomerLineAggregator implements LineAggregator<Customer>{
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public String aggregate(Customer item) {
		try {
			return objectMapper.writeValueAsString(item);
		} catch (Exception e) {
			throw new RuntimeException("Unable to Serialized Customer", e);
		}
	}
}
