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
		//@// @formatter:off
		return Customer.builder()
				.id(fieldSet.readLong("id"))
				.firstName(fieldSet.readString("firstName"))
				.lastName(fieldSet.readString("lastName"))
				.birthdate(LocalDateTime.parse(fieldSet.readString("birthdate"), DT_FORMAT))
				.build(); 
		// @formatter:on
	}

}
