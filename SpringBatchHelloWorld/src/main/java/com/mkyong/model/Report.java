package com.mkyong.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
public class Report {
	@XmlAttribute(name = "id")
	private int id;
	
	@XmlElement(name = "sales")
	private BigDecimal sales;
	
	@XmlElement(name = "qty")
	private int qty;
	
	@XmlElement(name = "staffName")
	private String staffName;
	
	private Date date;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getSales() {
		return sales;
	}

	public void setSales(BigDecimal sales) {
		this.sales = sales;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Report [id=" + id + ", sales=" + sales + ", qty=" + qty + ", staffName=" + staffName + "]";
	}

}