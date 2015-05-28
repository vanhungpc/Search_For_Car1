package com.dvhung.dto;

import java.io.Serializable;

public class ProducerDTO implements Serializable {
	private int id;
	private String nameProducer;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNameProducer() {
		return nameProducer;
	}
	public void setNameProducer(String nameProducer) {
		this.nameProducer = nameProducer;
	}
	
}
