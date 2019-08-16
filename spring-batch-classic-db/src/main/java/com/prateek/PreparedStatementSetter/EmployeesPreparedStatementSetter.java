package com.prateek.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementSetter;

public class EmployeesPreparedStatementSetter implements PreparedStatementSetter{
	private final String firstName;
	private final String jobTitle;
	
	public EmployeesPreparedStatementSetter(String firstName, String jobTitle) {
		this.firstName = firstName;
		this.jobTitle = jobTitle;
	}

	@Override
	public void setValues(PreparedStatement ps) throws SQLException {
		ps.setString(1, firstName);
		ps.setString(2, jobTitle);
	}
}
