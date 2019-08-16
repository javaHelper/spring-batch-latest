package com.jackrutorial.processor;

import org.springframework.batch.item.ItemProcessor;

import com.jackrutorial.model.User;

public class UserItemProcessor implements ItemProcessor<User, User>{

	@Override
	public User process(User user) throws Exception {
		return user;
	}
}
