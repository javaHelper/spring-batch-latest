package com.walking.techie.xmltomysql.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.walking.techie.xmltomysql.model.Report;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component
public class ReportConverter implements Converter {

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {

  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    Report report = new Report();
    report.setId(Integer.valueOf(reader.getAttribute("id")));
    reader.moveDown();// move down

    Date date = null;
    try {
      date = new SimpleDateFormat("MM/dd/yyyy").parse(reader.getValue());
    } catch (ParseException e) {
      System.out.println("ParseException has occured : "+e.getMessage());
    }
    report.setDate(date);
    reader.moveUp();
    reader.moveDown();//get impression
    String impression = reader.getValue();
    NumberFormat format = NumberFormat.getInstance(Locale.US);
    Number number = 0;
    try {
      number = format.parse(impression);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    report.setImpression(number.longValue());

    reader.moveUp();
    reader.moveDown();//get click
    report.setClicks(Integer.valueOf(reader.getValue()));
    reader.moveUp();

    reader.moveDown();
    report.setEarning(new BigDecimal(reader.getValue()));
    reader.moveUp();
    return report;
  }

  @Override
  public boolean canConvert(Class type) {
    return type.equals(Report.class);
  }
}
