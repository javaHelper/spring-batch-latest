package com.example.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.example.domain.Customer;

public class CustomerFieldSetMapper implements FieldSetMapper<Customer> {
	private static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	
	@Override
	public Customer mapFieldSet(FieldSet fieldSet) throws BindException {
		return Customer.builder()
				.id(fieldSet.readLong("id"))
				.firstName(fieldSet.readRawString("firstName"))
				.lastName(fieldSet.readRawString("lastName"))
				.birthdate(LocalDateTime.parse(fieldSet.readRawString("birthdate"), DT_FORMAT))
				.build();
	}

}
