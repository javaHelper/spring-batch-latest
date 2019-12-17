package com.example.domain;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EntityScan
public class Customer {
	private Long id;
	private String firstName;
	private String lastName;
	private LocalDateTime birthdate;
	
}
