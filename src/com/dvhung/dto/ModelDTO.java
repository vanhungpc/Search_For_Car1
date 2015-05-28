package com.dvhung.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ModelDTO implements Serializable {
	private int idMode;
	private int idProduce;
	private String nameModel;
	public int getIdMode() {
		return idMode;
	}
	public void setIdMode(int idMode) {
		this.idMode = idMode;
	}
	public int getIdProduce() {
		return idProduce;
	}
	public void setIdProduce(int idProduce) {
		this.idProduce = idProduce;
	}
	public String getNameModel() {
		return nameModel;
	}
	public void setNameModel(String nameModel) {
		this.nameModel = nameModel;
	}
}
