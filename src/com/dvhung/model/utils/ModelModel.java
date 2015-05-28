package com.dvhung.model.utils;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import com.dvhung.common.ActionEvent;
import com.dvhung.constants.Constants;
import com.dvhung.controller.ModelController;
import com.dvhung.dto.ModelDTO;

public class ModelModel {
	public static ModelModel instance;

	public static ModelModel getInstance() {
		if (instance == null)
			instance = new ModelModel();
		return instance;
	}

	public void handleControllerEvent(ActionEvent e) {
		Bundle data = (Bundle) e.bundle;
		switch (e.action) {
		case Constants.GET_MODEL_BY_ID_PRODUCE:
			int id = data.getInt("key_model");
			ArrayList<ModelDTO> arrData = getAllModelByIdModel((Context) e.sender, id);
			e.viewData = arrData;
			ModelController.getInstance().handleModelViewEvent(e);
			break;

		default:
			break;
		}
	}

	public ArrayList<ModelDTO> getAllModelByIdModel(Context context, int id) {
		ArrayList<ModelDTO> model = new ArrayList<ModelDTO>();
		String sql = "select * from MODEL where ID_Pro = " + id;
		DBHelper db = DBHelper.getInstance(context);
		Cursor c = db.Excutequery(sql);
		while (c.moveToNext()) {
			ModelDTO dto = new ModelDTO();

			dto.setIdMode(c.getInt(c.getColumnIndex("ID_Mo")));
			dto.setNameModel(c.getString(c.getColumnIndex("Name_Model")));
			model.add(dto);
		}
		c.close();
		db.close();
		return model;
	}
}
