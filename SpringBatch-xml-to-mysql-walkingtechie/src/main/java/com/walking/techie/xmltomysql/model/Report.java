package com.walking.techie.xmltomysql.model;


import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {

  private int id;
  private Date date;
  private long impression;
  private int clicks;
  private BigDecimal earning;
}
