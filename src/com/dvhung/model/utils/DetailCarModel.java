package com.dvhung.model.utils;

import java.util.ArrayList;

import com.dvhung.common.ActionEvent;
import com.dvhung.constants.Constants;
import com.dvhung.dto.DetailCarDTO;
import com.dvhung.fragment.AddFragmentDetail;
import com.dvhung.fragment.AddFragmentImages;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DetailCarModel {
	public static DetailCarModel instance;

	public static DetailCarModel getInstance() {
		if (instance == null)
			instance = new DetailCarModel();
		return instance;
	}

	public void handleControllerEvent(ActionEvent e) {

		switch (e.action) {
		case Constants.INSERT_CAR_DETAIL:
			DetailCarDTO dto = (DetailCarDTO) e.viewData;
			insertCarDetail(dto, (Object) e.sender);
			break;

		case Constants.UPDATE_CAR_DETAIL:
			DetailCarDTO dtoud = (DetailCarDTO) e.viewData;
			UpdateCarDetail(dtoud, (Object) e.sender);
			break;

		default:
			break;
		}
	}

	// get detailer car the last
	public DetailCarDTO getDetailerCar(Context context) {
		DetailCarDTO detailer = new DetailCarDTO();
		String sql = "SELECT * FROM DETAILCAR where flag = " + 0
				+ " order by ID_Detail DESC";
		DBHelper db = new DBHelper(context);
		Cursor c = db.Excutequery(sql);
		while (c.moveToNext()) {
			detailer.setNameDetail(c.getString(c.getColumnIndex("Name_Detail")));
			detailer.setPrice(c.getString(c.getColumnIndex("Price")));
			detailer.setBodyType(c.getString(c.getColumnIndex("Body_Type")));
			detailer.setDoors(c.getString(c.getColumnIndex("Doors")));
			detailer.setExteriorColor(c.getString(c
					.getColumnIndex("Exterior_Color")));
			detailer.setFuel(c.getString(c.getColumnIndex("Fuel")));
			detailer.setEngine(c.getString(c.getColumnIndex("Engine")));
			detailer.setTransmission(c.getString(c
					.getColumnIndex("Transmission")));
			detailer.setOrigin(c.getString(c.getColumnIndex("Origin")));
			detailer.setMilliage(c.getString(c.getColumnIndex("Milliage")));
			break;
		}
		c.close();
		db.close();
		return detailer;
	}

	// insert car detail
	public boolean insertCarDetail(DetailCarDTO dto, Object ob) {
		AddFragmentDetail frag = (AddFragmentDetail) ob;
		Context ctx = frag.getActivity().getApplicationContext();
		boolean flag = false;
		boolean check = checkDetailCar(ctx);
		if (!check) {
			String DATABASE_TABLE = "DETAILCAR";
			ContentValues values = new ContentValues();
			values.put("Name_Detail", dto.getNameDetail());
			values.put("Price", dto.getPrice());
			values.put("Body_Type", dto.getBodyType());
			values.put("Doors", dto.getDoors());
			values.put("Exterior_Color", dto.getExteriorColor());
			values.put("Fuel", dto.getFuel());
			values.put("Engine", dto.getEngine());
			values.put("Transmission", dto.getTransmission());
			values.put("Origin", dto.getOrigin());
			values.put("Milliage", dto.getMilliage());
			values.put("ID_DetailSV", dto.getIdSV());
			values.put("flag", dto.getFlag());
			DBHelper db = DBHelper.getInstance(ctx);
			flag = db.ExcuteInsert(null, DATABASE_TABLE, values);
			db.close();
		}

		return flag;

	}

	// insert car detail
	public boolean UpdateCarDetail(DetailCarDTO dto, Object ob) {
		AddFragmentDetail frag = (AddFragmentDetail) ob;
		Context ctx = frag.getActivity().getApplicationContext();
		boolean flag = false;
		String DATABASE_TABLE = "DETAILCAR";
		ContentValues values = new ContentValues();
		int idTheLast = getIdTheLast(ctx);
		String where = "ID_Detail= " + idTheLast;
		values.put("Name_Detail", dto.getNameDetail());
		values.put("Price", dto.getPrice());
		values.put("Body_Type", dto.getBodyType());
		values.put("Doors", dto.getDoors());
		values.put("Exterior_Color", dto.getExteriorColor());
		values.put("Fuel", dto.getFuel());
		values.put("Engine", dto.getEngine());
		values.put("Transmission", dto.getTransmission());
		values.put("Origin", dto.getOrigin());
		values.put("Milliage", dto.getMilliage());
		values.put("ID_DetailSV", dto.getIdSV());
		values.put("flag", dto.getFlag());
		DBHelper db = DBHelper.getInstance(ctx);
		flag = db.ExcuteUpdate(null, DATABASE_TABLE, values, where, null);
		db.close();

		return flag;

	}

	public boolean checkDetailCar(Context context) {
		boolean flag = false;
		String sql = "SELECT * FROM DETAILCAR where flag = " + 0;
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

	// check car news
	public boolean checkNewsDetailCar(Object ob) {
		AddFragmentDetail frag = (AddFragmentDetail) ob;
		Context ctx = frag.getActivity().getApplicationContext();
		boolean flag = false;
		String sql = "SELECT * FROM DETAILCAR where flag = " + 0;
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
		String sql = "SELECT * FROM DETAILCAR where flag = " + 0
				+ " order by ID_Detail DESC";
		DBHelper db = new DBHelper(context);
		Cursor c = db.Excutequery(sql);
		while (c.moveToNext()) {
			id = c.getInt(c.getColumnIndex("ID_Detail"));
			break;
		}
		c.close();
		db.close();
		return id;
	}

}
