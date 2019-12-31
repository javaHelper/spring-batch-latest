package com.example.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.example.model.Customer;

public class CustomerFieldSetMapper implements FieldSetMapper<Customer> {

	@Override
	public Customer mapFieldSet(FieldSet fieldSet) throws BindException {
		return Customer.builder()
				.id(fieldSet.readLong("id"))
				.firstName(fieldSet.readRawString("firstName"))
				.lastName(fieldSet.readRawString("lastName"))
				.birthdate(fieldSet.readRawString("birthdate"))
				.build();
	}
}
