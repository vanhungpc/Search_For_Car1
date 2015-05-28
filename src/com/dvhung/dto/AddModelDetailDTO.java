package com.dvhung.dto;

public class AddModelDetailDTO {
	private int id;
	private int idAcc;
	private int idModel;
	private String nameProduce;
	private String nameModel;
	private String nameAddress;
	private double lat;
	private double lon;
	private int flag;
	private int idPro;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdAcc() {
		return idAcc;
	}
	public void setIdAcc(int idAcc) {
		this.idAcc = idAcc;
	}
	public int getIdModel() {
		return idModel;
	}
	public void setIdModel(int idModel) {
		this.idModel = idModel;
	}
	public String getNameProduce() {
		return nameProduce;
	}
	public void setNameProduce(String nameProduce) {
		this.nameProduce = nameProduce;
	}
	public String getNameModel() {
		return nameModel;
	}
	public void setNameModel(String nameModel) {
		this.nameModel = nameModel;
	}
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
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getIdPro() {
		return idPro;
	}
	public void setIdPro(int idPro) {
		this.idPro = idPro;
	}
	
}
