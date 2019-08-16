package com.prateek.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.prateek.model.Product;

public class ProductRowMapper implements RowMapper<Product>{

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product product = new Product();
		product.setProductCode(rs.getString("productCode"));
		product.setProductName(rs.getString("productName"));
		product.setProductLine(rs.getString("productLine"));
		product.setProductScale(rs.getString("productScale"));
		product.setProductVendor(rs.getString("productVendor"));
		product.setProductDescription(rs.getString("productDescription"));
		product.setQuantityInStock(rs.getInt("quantityInStock"));
		product.setBuyPrice(rs.getDouble("buyPrice"));
		product.setMSRP(rs.getDouble("MSRP"));

		return product;
	}

}
