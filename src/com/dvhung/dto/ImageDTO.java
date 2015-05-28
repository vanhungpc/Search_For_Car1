package com.dvhung.dto;

import android.graphics.Bitmap;

public class ImageDTO {
	private int id;
	private Bitmap imgBitmap;
	public Bitmap getImgBitmap() {
		return imgBitmap;
	}
	public void setImgBitmap(Bitmap imgBitmap) {
		this.imgBitmap = imgBitmap;
	}
	private String url;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
