package com.prateek.partion;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

public class RangePartitioner implements Partitioner {

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {

		Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();

		int range = 100;
		int fromId = 1;
		int toId = range;

		for (int i = 1; i <= gridSize; i++) {
			System.out.println("\nStarting : Thread" + i+"fromId : " + fromId+"toId : " + toId);

			ExecutionContext value = new ExecutionContext();
			// give each thread a name, thread 1,2,3
			value.putString("name", "Thread" + i);
			value.putInt("fromId", fromId);
			value.putInt("toId", toId);

			result.put("partition" + i, value);
			fromId = toId + 1;
			toId += range;
		}
		return result;
	}
}