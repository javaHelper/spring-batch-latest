package com.prateek.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {
	private Integer orderNumber;
	private Date orderDate;
	private Date requiredDate;
	private Date shippedDate;
	private String status;
	private String comments;
	private Integer customerNumber;
	private Double total;
}
