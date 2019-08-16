package com.prateek.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orderdetails {
	private Integer orderNumber;
	private String productCode;
	private Integer quantityOrdered;
	private Double priceEach;
	private Integer orderLineNumber;
}
