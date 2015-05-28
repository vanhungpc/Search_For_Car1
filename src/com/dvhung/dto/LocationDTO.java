package com.dvhung.dto;

import java.io.Serializable;

public class LocationDTO implements Serializable {
	private String nameAddress;
	private double lat;
	private double lon;
	public String getNameAddress() {
		return nameAddress;
	}
	public void setNameAddress(String nameAddress) {
		this.nameAddress = nameAddress;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
}
