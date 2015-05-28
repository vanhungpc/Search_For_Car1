package com.dvhung.model.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.dvhung.common.ActionEvent;
import com.dvhung.constants.Constants;
import com.dvhung.dto.LoginDTO;
import com.dvhung.fragment.AddFragmentImages;
import com.pkt.rest.client.pojo.Account;

public class LoginModel {
	public static LoginModel instance;

	public static LoginModel getInstance() {
		if (instance == null)
			instance = new LoginModel();
		return instance;
	}

	public void handleControllerEvent(ActionEvent e) {
		switch (e.action) {
		case Constants.INSERT_USER:
			LoginDTO login = (LoginDTO) e.viewData;
			insertUser(login, (Context) e.sender);
			break;
			
		case Constants.INSERT_ACCOUNT:
			Account acc = (Account) e.viewData;
			insertAccount(acc, (Context) e.sender);
			break;
		case Constants.UPDATE_ACCOUNT:
			Account accupdate = (Account) e.viewData;
			updateAccount(accupdate, (Context) e.sender);
			break;

		default:
			break;
		}
	}

	// get account
	public LoginDTO getUser(Context context) {
		LoginDTO dto = new LoginDTO();
		String sql = "SELECT * FROM LOGIN where flag = " + 1;
		DBHelper db = new DBHelper(context);
		Cursor c = db.Excutequery(sql);
		while (c.moveToNext()) {
			dto.setId(c.getInt(c.getColumnIndex("id")));
			dto.setUserName(c.getString(c.getColumnIndex("username")));
			break;
		}
		c.close();
		db.close();
		return dto;
	}
	//get user 
	public Account getAccount(Context context){
		Account acc = new Account();
		String sql = "SELECT * FROM ACCOUNT";
		DBHelper db = new DBHelper(context);
		Cursor c = db.Excutequery(sql);
		while (c.moveToNext()) {
			acc.setIdAcc(c.getInt(c.getColumnIndex("ID_Acc")));
			acc.setName(c.getString(c.getColumnIndex("Name")));
			acc.setPassword(c.getString(c.getColumnIndex("Password")));
			acc.setAddress(c.getString(c.getColumnIndex("Address")));
			acc.setBirthDay(c.getString(c.getColumnIndex("BirtDay")));
			acc.setEmail(c.getString(c.getColumnIndex("Email")));
			acc.setPhoneNumber(c.getString(c.getColumnIndex("PhoneNumber")));
			acc.setFlag(c.getInt(c.getColumnIndex("flag")));
			break;
		}
		c.close();
		db.close();
		return acc;
	}
	public boolean insertUser(LoginDTO dto, Context context) {
		boolean flag = false;
		boolean check = checkUser(dto.getId(), context);
		if (!check) {
			String DATABASE_TABLE = "LOGIN";
			ContentValues values = new ContentValues();

			values.put("id", dto.getId());
			values.put("username", dto.getUserName());
			values.put("password", dto.getPassword());
			values.put("flag", dto.getFlag());
			DBHelper db = DBHelper.getInstance(context);
			flag = db.ExcuteInsert(null, DATABASE_TABLE, values);
			db.close();
		}

		return flag;

	}

	public boolean insertAccount(Account dto, Context context) {
		boolean flag = false;
		boolean check = checkAccount(dto.getIdAcc(), context);
		if (!check) {
			String DATABASE_TABLE = "ACCOUNT";
			ContentValues values = new ContentValues();

			values.put("ID_Acc", dto.getIdAcc());
			values.put("Name", dto.getName());
			values.put("Password", dto.getPassword());
			values.put("BirtDay", dto.getBirthDay());
			values.put("Address", dto.getAddress());
			values.put("PhoneNumber", dto.getPhoneNumber());
			values.put("Email", dto.getEmail());
			values.put("flag", dto.getFlag());
			DBHelper db = DBHelper.getInstance(context);
			flag = db.ExcuteInsert(null, DATABASE_TABLE, values);
			db.close();
		}

		return flag;

	}

	// check account login
	public boolean checkAccount(int id, Context context) {
		boolean flag = false;
		String sql = "SELECT * FROM ACCOUNT where ID_Acc = " + id;
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

	// update account
	public boolean updateAccount(Account dto, Context context) {
		boolean flag = false;
		boolean check = checkAccount(dto.getIdAcc(), context);
		if (!check) {
			String DATABASE_TABLE = "ACCOUNT";
			ContentValues values = new ContentValues();
			int idTheLast = getIdTheLast(context);
			String where = "ID_Acc = " + idTheLast;
			values.put("ID_Acc", dto.getIdAcc());
			values.put("Name", dto.getName());
			values.put("Password", dto.getPassword());
			values.put("BirtDay", dto.getBirthDay());
			values.put("Address", dto.getAddress());
			values.put("PhoneNumber", dto.getPhoneNumber());
			values.put("Email", dto.getEmail());
			values.put("flag", dto.getFlag());
			DBHelper db = DBHelper.getInstance(context);
			flag = db.ExcuteUpdate(null, DATABASE_TABLE, values, where, null);
			db.close();
		}

		return flag;

	}

	// get id the last
	public int getIdTheLast(Context context) {
		int id = 0;
		String sql = "SELECT * FROM ACCOUNT order by ID_Acc DESC";
		DBHelper db = new DBHelper(context);
		Cursor c = db.Excutequery(sql);
		while (c.moveToNext()) {
			id = c.getInt(c.getColumnIndex("ID_Acc"));
			break;
		}
		c.close();
		db.close();
		return id;
	}

	// check user login
	public boolean checkUser(int id, Context context) {
		boolean flag = false;
		String sql = "SELECT * FROM LOGIN where id = " + id;
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

	// check username exits login
	public boolean CheckLogin(Context context) {
		boolean flag = false;
		String sql = "SELECT * FROM LOGIN";
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
	
	//check user exits account
	public boolean CheckUserExits(Context context) {
		boolean flag = false;
		String sql = "SELECT * FROM ACCOUNT";
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
}
