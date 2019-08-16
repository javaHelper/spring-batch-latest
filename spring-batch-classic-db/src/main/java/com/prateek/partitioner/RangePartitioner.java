package com.prateek.partitioner;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RangePartitioner implements Partitioner {
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		log.info("partition called gridsize= " + gridSize);

		Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();

		int range = 50;
		int fromId = 100;
		int toId = range;

		for (int i = 1; i <= gridSize; i++) {
			ExecutionContext value = new ExecutionContext();

			System.out.println("\nStarting : Thread" + i + ", fromId : " + fromId + ", toId : " + toId);
			value.putInt("fromId", fromId);
			value.putInt("toId", toId);
			// give each thread a name, thread 1,2,3
			value.putString("name", "Thread" + i);

			result.put("partition" + i, value);
			fromId = toId + 1;
			toId += range;
		}
		return result;
	}
}
