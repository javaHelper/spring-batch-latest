package com.prateek.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
	private String productCode;
	private String productName;
	private String productLine;
	private String productScale;
	private String productVendor;
	private String productDescription;
	private Integer quantityInStock;
	private Double buyPrice;
	private Double MSRP;
}
