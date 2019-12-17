package com.example.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;

import com.example.model.Customer;

public class CustomerRowMapper implements RowMapper<Customer> {
	private static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	
	@Override
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		return Customer.builder()
				.id(rs.getLong("id"))
				.firstName(rs.getString("firstName"))
				.lastName(rs.getString("lastName"))
				.birthdate(rs.getString("birthdate"))
				.build();
	}
}
