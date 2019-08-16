package com.prateek.callback;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

public class OrdersFlatFileItemWriterCallback implements FlatFileHeaderCallback{
	// Here you can pass String parameter through constructor and can set values too
	
	@Override
	public void writeHeader(Writer writer) throws IOException {
		writer.write("OrderNumber, OrderDate, RequiredDate, ShippedDate, Status, Comments, CustomerNumber, Total");
	}
}
