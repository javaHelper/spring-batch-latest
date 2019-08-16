package com.jackrutorial;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.jackrutorial.model.User;

// A convenient strategy for SQL updates, acting effectively as the inverse of RowMapper
public class UserItemPreparedStmSetter implements ItemPreparedStatementSetter<User>{

	@Override
	public void setValues(User item, PreparedStatement ps) throws SQLException {
		ps.setInt(1, item.getId());
		ps.setString(2, item.getName());
	}
}
