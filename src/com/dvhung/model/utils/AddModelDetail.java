package com.dvhung.model.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.dvhung.common.ActionEvent;
import com.dvhung.constants.Constants;
import com.dvhung.dto.AddModelDetailDTO;
import com.dvhung.fragment.AddFragmentDetail;
import com.dvhung.fragment.AddFragmentImages;
import com.dvhung.fragment.AddFragmentModel;

public class AddModelDetail {
	public static AddModelDetail instance;

	public static AddModelDetail getInstance() {
		if (instance == null)
			instance = new AddModelDetail();
		return instance;
	}

	public void handleControllerEvent(ActionEvent e) {
		switch (e.action) {
		case Constants.INSERT_ADD_MODEL:
			AddModelDetailDTO addModel = (AddModelDetailDTO) e.viewData;
			insertAddModel(addModel, (Object) e.sender);
			break;
		case Constants.UPDATE_ADD_MODEL:
			AddModelDetailDTO updateModel = (AddModelDetailDTO) e.viewData;
			updateAddModel(updateModel, (Object) e.sender);
			break;

		default:
			break;
		}
	}

	// get addModel the last
	public AddModelDetailDTO getAddModel(Context context) {
		AddModelDetailDTO dto = new AddModelDetailDTO();
		// AddFragmentImages frag = (AddFragmentImages) ob;
		// Context ctx = frag.getActivity().getApplicationContext();
		String sql = "SELECT * FROM ADDMODEL where flag = " + 0
				+ " order by ID DESC";
		DBHelper db = new DBHelper(context);
		Cursor c = db.Excutequery(sql);
		while (c.moveToNext()) {
			dto.setId(c.getInt(c.getColumnIndex("ID")));
			dto.setIdAcc(c.getInt(c.getColumnIndex("ID_Acc")));
			dto.setIdModel(c.getInt(c.getColumnIndex("ID_Model")));
			dto.setNameProduce(c.getString(c.getColumnIndex("Name_Pro")));
			dto.setNameModel(c.getString(c.getColumnIndex("Name_Model")));
			dto.setNameAddress(c.getString(c.getColumnIndex("Name_Address")));
			dto.setLat(c.getDouble(c.getColumnIndex("lat")));
			dto.setLon(c.getDouble(c.getColumnIndex("lon")));
			dto.setFlag(c.getInt(c.getColumnIndex("flag")));
			dto.setIdPro(c.getInt(c.getColumnIndex("ID_Pro")));
//			break;
		}
		c.close();
		db.close();
		return dto;
	}

	// insert model produce
	public boolean insertAddModel(AddModelDetailDTO dto, Object obj) {
		AddFragmentModel fm = (AddFragmentModel) obj;
		Context ctx = fm.getActivity().getApplicationContext();
		boolean flag = false;
		boolean check = checkAddModel(ctx);
		if (!check) {
			String DATABASE_TABLE = "ADDMODEL";
			ContentValues values = new ContentValues();
			values.put("ID_Acc", dto.getIdAcc());
			values.put("ID_Model", dto.getIdModel());
			values.put("Name_Pro", dto.getNameProduce());
			values.put("Name_Model", dto.getNameModel());
			values.put("Name_Address", dto.getNameAddress());
			values.put("lat", dto.getLat());
			values.put("lon", dto.getLon());
			values.put("flag", dto.getFlag());
			values.put("ID_Pro", dto.getIdPro());
			DBHelper db = DBHelper.getInstance(ctx);
			flag = db.ExcuteInsert(null, DATABASE_TABLE, values);
			db.close();
		}

		return flag;
	}

	public boolean updateAddModel(AddModelDetailDTO dto, Object obj) {
		AddFragmentModel fm = (AddFragmentModel) obj;
		Context ctx = fm.getActivity().getApplicationContext();
		boolean flag = false;
		String DATABASE_TABLE = "ADDMODEL";
		ContentValues values = new ContentValues();
		int idTheLast = getIdTheLast(ctx);
		String where = "ID = " + idTheLast;
		values.put("ID_Acc", dto.getIdAcc());
		values.put("ID_Model", dto.getIdModel());
		values.put("Name_Pro", dto.getNameProduce());
		values.put("Name_Model", dto.getNameModel());
		values.put("Name_Address", dto.getNameAddress());
		values.put("lat", dto.getLat());
		values.put("lon", dto.getLon());
		values.put("flag", dto.getFlag());
		values.put("ID_Pro", dto.getIdPro());
		DBHelper db = DBHelper.getInstance(ctx);
		flag = db.ExcuteUpdate(null, DATABASE_TABLE, values, where, null);
		db.close();
		return flag;
	}

	public boolean checkAddModel(Context context) {
		boolean flag = false;
		String sql = "SELECT * FROM ADDMODEL where flag = " + 0;
		DBHelper db = new DBHelper(context);
		Cursor c = db.Excutequery(sql);
		while (c.moveToNext()) {
			flag = true;
			break;
		}
		c.close();
		db.close();
		return flag;
	}

	// check model news
	public boolean checkAddModelUpdate(Object ob) {
		AddFragmentModel frag = (AddFragmentModel) ob;
		Context ctx = frag.getActivity().getApplicationContext();
		boolean flag = false;
		String sql = "SELECT * FROM ADDMODEL where flag = " + 0;
		DBHelper db = new DBHelper(ctx);
		Cursor c = db.Excutequery(sql);
		while (c.moveToNext()) {
			flag = true;
			break;
		}
		c.close();
		db.close();
		return flag;
	}

	// get id the last
	public int getIdTheLast(Context context) {
		int id = 0;
		String sql = "SELECT * FROM ADDMODEL where flag = " + 0
				+ " order by ID DESC";
		DBHelper db = new DBHelper(context);
		Cursor c = db.Excutequery(sql);
		while (c.moveToNext()) {
			id = c.getInt(c.getColumnIndex("ID"));
			break;
		}
		c.close();
		db.close();
		return id;
	}
}
