package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.domain.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String>{

}
