package com.prateek.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.prateek.model.Report;

public class ReportWriter implements ItemWriter<Report> {

	@Override
	public void write(List<? extends Report> items) throws Exception {
		System.out.println("writer..." + items.size());		
		for(Report item : items){
			System.out.println(item);
		}
	}
}
