package com.example.domain;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@XmlRootElement(name = "Customer")
public class Customer {
	private Long id;
	private String firstName;
	private String lastName;
	private LocalDateTime birthdate;
}
