package com.prateek.callback;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

/**
 * This class will create column header aliases while writing to CSV file
 * @author Prateek
 *
 */
public class CustomerFlatFileWriterCallback implements FlatFileHeaderCallback{
	// Here you can pass String parameter through constructor and can set values
	@Override
	public void writeHeader(Writer writer) throws IOException {
		writer.write("customerNumber, customerName, contactLastName, contactFirstName, phone,addressLine1, addressLine2, city, state, postalCode, country,salesRepEmployeeNumber,creditLimit");
	}
}
