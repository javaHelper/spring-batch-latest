package com.example.config;

import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class StatefullItemReader implements ItemStreamReader<String> {

	private final List<String> items;
	private int curIndex = -1;
	private boolean restart = false;

	public StatefullItemReader(List<String> items) {
		this.items = items;
		this.curIndex = 0;
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		if (executionContext.containsKey("curIndex")) {
			this.curIndex = executionContext.getInt("curIndex");
			this.restart = true;
		} else {
			this.curIndex = 0;
			executionContext.put("curIndex", this.curIndex);
		}

	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		executionContext.put("curIndex", this.curIndex);
	}

	@Override
	public void close() throws ItemStreamException {

	}

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		String item = null;

		if (this.curIndex < this.items.size()) {
			item = this.items.get(this.curIndex);
			this.curIndex++;
		}

		if (this.curIndex == 42 && !restart) {
			throw new RuntimeException("The Ansdwer to the Ultimate Question of Life, the universe & everthing...");
		}

		return item;
	}

}
