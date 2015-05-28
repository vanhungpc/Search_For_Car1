package com.dvhung.model.utils;

import java.util.ArrayList;

import com.dvhung.common.ActionEvent;
import com.dvhung.constants.Constants;
import com.dvhung.controller.ProduceController;
import com.dvhung.dto.ProducerDTO;

import android.content.Context;
import android.database.Cursor;

public class ProducerModel {
	public static ProducerModel instance;

	public static ProducerModel getInstance() {
		if (instance == null)
			instance = new ProducerModel();
		return instance;
	}

	public void handleControllerEvent(ActionEvent e) {

		switch (e.action) {
		case Constants.GET_ALL_PRODUCE:
			ArrayList<ProducerDTO> arrData = getAllArrayProducer((Context) e.sender);
			e.viewData = arrData;
			ProduceController.getInstance().handleModelViewEvent(e);
			break;

		default:
			break;
		}
	}

	public ArrayList<ProducerDTO> getAllArrayProducer(Context context) {
		ArrayList<ProducerDTO> produce = new ArrayList<ProducerDTO>();
		String sql = "select * from PRODUCER";
		DBHelper db = DBHelper.getInstance(context);
		Cursor c = db.Excutequery(sql);
		while (c.moveToNext()) {
			ProducerDTO dto = new ProducerDTO();

			int id = c.getInt(c.getColumnIndex("ID_Pro"));
			String name = c.getString(c.getColumnIndex("Name_Producer"));
			dto.setId(id);
			dto.setNameProducer(name);
			produce.add(dto);
		}
		c.close();
		db.close();
		return produce;
	}

	public ArrayList<String> getAllProducer(Context context) {
		ArrayList<String> produce = new ArrayList<String>();
		String sql = "select * from PRODUCER";
		DBHelper db = DBHelper.getInstance(context);
		Cursor c = db.Excutequery(sql);
		while (c.moveToNext()) {
			ProducerDTO dto = new ProducerDTO();

			int id = c.getInt(c.getColumnIndex("ID_Pro"));
			String name = c.getString(c.getColumnIndex("Name_Producer"));
			dto.setNameProducer(name);
			produce.add(name);
		}
		c.close();
		db.close();
		return produce;
	}

	public ArrayList<String> getModelById(Context context, int id) {
		ArrayList<String> model = new ArrayList<String>();
		String sql = "select * from MODEL where ID_Pro =" + id;
		DBHelper db = DBHelper.getInstance(context);
		Cursor c = db.Excutequery(sql);
		while (c.moveToNext()) {
			String name = c.getString(c.getColumnIndex("Name_Model"));
			model.add(name);
		}
		c.close();
		db.close();
		return model;
	}

}
