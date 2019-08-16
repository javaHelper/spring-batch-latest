package com.prateek.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payments {
	private Integer customerNumber;
	private String checkNumber;
	private Date paymentDate;
	private Double amount;
}
