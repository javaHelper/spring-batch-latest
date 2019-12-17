package com.example.validation;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

import com.example.model.Customer;

public class CustomerValidator implements Validator<Customer>{

	@Override
	public void validate(Customer value) throws ValidationException {
		if(value.getFirstName().startsWith("A")) {
			throw new ValidationException("First Name that begine with A are invalid"+ value);
		}
	}
}
