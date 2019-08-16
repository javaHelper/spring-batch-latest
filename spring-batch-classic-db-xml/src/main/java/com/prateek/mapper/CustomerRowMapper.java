package com.prateek.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.prateek.model.Customers;

public class CustomerRowMapper implements RowMapper<Customers> {

	@Override
	public Customers mapRow(ResultSet rs, int rowNum) throws SQLException {
		Customers customers = new Customers();
		customers.setCustomerNumber(rs.getInt("customerNumber"));
		customers.setCustomerName(rs.getString("customerName"));
		customers.setContactLastName(rs.getString("contactLastName"));
		customers.setContactLastName(rs.getString("contactFirstName"));
		customers.setPhone(rs.getString("phone"));
		customers.setAddressLine1(rs.getString("addressLine1"));
		customers.setAddressLine2(rs.getString("addressLine2"));
		customers.setCity(rs.getString("city"));
		customers.setPostalCode(rs.getString("postalCode"));
		customers.setState(rs.getString("state"));
		customers.setCountry(rs.getString("country"));
		customers.setSalesRepEmployeeNumber(rs.getString("salesRepEmployeeNumber"));
		customers.setCreditLimit(rs.getDouble("creditLimit"));

		return customers;
	}

}
