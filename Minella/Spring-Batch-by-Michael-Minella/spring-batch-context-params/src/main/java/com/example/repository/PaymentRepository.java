package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
