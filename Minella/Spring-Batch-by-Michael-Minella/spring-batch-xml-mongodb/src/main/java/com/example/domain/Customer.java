package com.example.domain;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@XmlRootElement(name = "Customer")
@Document
public class Customer {
	@Id
	private Long id;
	@Field
	private String firstName;
	@Field
	private String lastName;
	@Field
	private LocalDate birthdate;
}
