package com.prateek.writer;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.prateek.model.Product;

@Repository
public class ProductRedisWriter implements ItemWriter<Product>{

	@Resource(name="redisTemplate")
	private HashOperations<String, String, Product> hashOperations;	
	
	@Resource(name="redisTemplate")
	private RedisTemplate<String, Product> redisTemplate;

	@Override
	public void write(List<? extends Product> products) throws Exception {

		for (Product product : products) {
			System.out.println("********* Prodcus saved to Redis  "+product.toString());
			hashOperations.put("Product", product.getProductCode(), product);
		}
	}
}
