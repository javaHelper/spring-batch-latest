package com.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String>{

}
