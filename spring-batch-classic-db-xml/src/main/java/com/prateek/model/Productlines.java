package com.prateek.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Productlines {
	private String productLine;
	private String textDescription;
	private String htmlDescription;
	private byte[] image;
}
