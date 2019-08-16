package com.prateek.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import com.prateek.model.Orders;

public class OrdersRowMapper implements RowMapper<Orders>{

	@Override
	public Orders mapRow(ResultSet rs, int rowNum) throws SQLException {
		Orders orders = new Orders();

		orders.setOrderNumber(rs.getInt("orderNumber"));
		orders.setOrderDate(rs.getDate("orderDate"));
		orders.setRequiredDate(rs.getDate("requiredDate"));
		orders.setShippedDate(rs.getDate("shippedDate"));
		orders.setStatus(rs.getString("status"));
		orders.setComments(rs.getString("comments"));
		orders.setTotal(rs.getDouble("total"));

		if(StringUtils.isEmpty(rs.getInt("customerNumber")))
			orders.setCustomerNumber(rs.getInt("customerNumber"));

		return orders;
	}
}
