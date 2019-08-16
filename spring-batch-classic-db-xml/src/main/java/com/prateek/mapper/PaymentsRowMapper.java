package com.prateek.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.prateek.model.Payments;

public class PaymentsRowMapper implements RowMapper<Payments> {

	@Override
	public Payments mapRow(ResultSet rs, int rowNum) throws SQLException {
		Payments payments = new Payments();
		payments.setAmount(rs.getDouble("amount"));
		payments.setCheckNumber(rs.getString("checkNumber"));
		payments.setCustomerNumber(rs.getInt("customerNumber"));
		payments.setPaymentDate(rs.getDate("paymentDate"));

		return payments;
	}

}
