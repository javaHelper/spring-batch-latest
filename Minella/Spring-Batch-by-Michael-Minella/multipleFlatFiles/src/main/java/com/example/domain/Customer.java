package com.example.domain;

import java.time.LocalDateTime;

import org.springframework.batch.item.ResourceAware;
import org.springframework.core.io.Resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Customer implements ResourceAware{
	private Long id;
	private String firstName;
	private String lastName;
	private LocalDateTime birthdate;
	private Resource resource;
}
