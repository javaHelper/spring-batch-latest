package com.prateek.processor;

import org.springframework.batch.item.ItemProcessor;

import com.prateek.model.Employees;

public class EmployeesProcessor implements ItemProcessor<Employees, Employees>{

	@Override
	public Employees process(Employees employees) throws Exception {
		return employees;
	}
}
