package com.example.domain;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@XmlRootElement(name = "Customer")
@RedisHash(value="customer")
public class Customer {
	@Id
	private Long id;
	private String firstName;
	private String lastName;
	private LocalDate birthdate;
}
