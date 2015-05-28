package com.example.search_for_car;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.fourmob.datetimepicker.date.DatePickerDialogafter;
import com.fourmob.datetimepicker.date.DatePickerDialogafter.OnDateSetListener;
import com.google.gson.reflect.TypeToken;
import com.pkt.rest.client.helper.JsonHelper;
import com.pkt.rest.client.pojo.Account;
import com.pkt.rest.client.services.AccountService;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends FragmentActivity implements OnClickListener,OnDateSetListener, TimePickerDialog.OnTimeSetListener {
	Button btnday, btnCheckusername, btnCheckpassword, btnconfirmpassword;
	EditText edUserName, edPassword, edConfirmPassword, edday, edEmail,
			edPhoneNumber, edAddress;
	private Calendar cal;
	private int day;
	private int month;
	private int year;
	Button btnRegister;
	Account acc;
	TextView txtResult;
	AnimationDrawable frameAnimation;
	Dialog dialog;
	public static final String DATEPICKER_TAG = "datepicker";
	public static final String TIMEPICKER_TAG = "timepicker";
	DatePickerDialogafter datePickerDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		btnday = (Button) findViewById(R.id.btnday);
		edday = (EditText) findViewById(R.id.edday);
		edUserName = (EditText) findViewById(R.id.edUserName);
		edPassword = (EditText) findViewById(R.id.edPassword);
		edConfirmPassword = (EditText) findViewById(R.id.edConfirmPassword);
		edEmail = (EditText) findViewById(R.id.edEmail);
		edPhoneNumber = (EditText) findViewById(R.id.edPhoneNumber);
		edAddress = (EditText) findViewById(R.id.edAddress);
		btnCheckusername = (Button) findViewById(R.id.btnCheckusername);
		btnCheckpassword = (Button) findViewById(R.id.btnCheckpassword);
		btnconfirmpassword = (Button) findViewById(R.id.btnconfirmpassword);
		txtResult = (TextView) findViewById(R.id.txtResult);
		btnday.setOnClickListener(this);

		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(this);
		// get calendar
		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);

		acc = new Account();
		final Calendar calendar = Calendar.getInstance();
		datePickerDialog = DatePickerDialogafter.newInstance(this,
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), isVibrate());
		if (savedInstanceState != null) {
			DatePickerDialogafter dpd = (DatePickerDialogafter) getSupportFragmentManager().findFragmentByTag(
							DATEPICKER_TAG);
			if (dpd != null) {
				dpd.setOnDateSetListener(this);
			}

			TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(
							TIMEPICKER_TAG);
			if (tpd != null) {
				tpd.setOnTimeSetListener(this);
			}
		}

	}
	private boolean isVibrate() {
		return true;
	}

	private boolean isCloseOnSingleTapDay() {
		return false;
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		return new DatePickerDialog(this, datePickerListener, year, month, day);
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			edday.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
					+ selectedYear);
		}
	};

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.btnday:
			datePickerDialog.setVibrate(isVibrate());
			datePickerDialog.setYearRange(1945, 2015);
			datePickerDialog.setCloseOnSingleTapDay(isCloseOnSingleTapDay());
			datePickerDialog.show(getSupportFragmentManager(),
					DATEPICKER_TAG);
			break;
		case R.id.btnRegister:
			txtResult.setText("");
			new VerySlowTask().execute();
			break;
		default:
			break;
		}

	}

	String username, password, confirmpassword, birthDay, address, email;
	String phonenumber;
	boolean flag1, flag2 = true, flag3, flag4 = false;

	private class VerySlowTask extends AsyncTask<Void, Void, Void> {
		String resulterr = "";
		public VerySlowTask(){
			dialog = new Dialog(RegisterActivity.this, R.style.NewDialog);
			dialog.setContentView(R.layout.loading);

			dialog.setCancelable(false);
			dialog.findViewById(R.id.loading_icon).setBackgroundResource(
					R.drawable.spin_animation);
			frameAnimation = (AnimationDrawable) dialog.findViewById(
					R.id.loading_icon).getBackground();
			dialog.show();
			frameAnimation.start();
		}
		@Override
		protected Void doInBackground(Void... params) {
			try {
				// get all account
				String accountsJson = AccountService.getInstance()
						.getUseAndroid();
				Type typeOfT = new TypeToken<List<Account>>() {
				}.getType();
				Object object = JsonHelper.GetInstance().GetObjectUsingGson(
						accountsJson, typeOfT, "account");
				@SuppressWarnings("unchecked")
				List<Account> accounts = (List<Account>) object;

				System.out.println(accounts.get(0).getName());

				//
				username = edUserName.getText().toString().trim();
				password = edPassword.getText().toString().trim();
				confirmpassword = edConfirmPassword.getText().toString().trim();
				birthDay = edday.getText().toString();
				address = edAddress.getText().toString();
				phonenumber = edPhoneNumber.getText().toString();
				email = edEmail.getText().toString();
				acc.setBirthDay(birthDay);
				acc.setAddress(address);
				// acc.setPhoneNumber(phonenumber);
				acc.setEmail(email);
				// check error

				if (username.equalsIgnoreCase("")) {
					flag1 = false;
					resulterr += "username ";
				} else {
					acc.setName(username);
					flag1 = true;
				}
				if (password.equalsIgnoreCase("")) {
					flag2 = false;
					resulterr += ", password";
				} else {
					flag2 = true;
				}
				if (confirmpassword.equalsIgnoreCase("")) {
					flag3 = false;
					resulterr += ", confint pass";
				} else if (!password.contains(confirmpassword)) {
					flag3 = false;
					resulterr += "pass trung";
				} else {
					acc.setPassword(password);
					flag3 = true;
				}

				if (flag1 && flag2 && flag3) {

//					for (Account ac : accounts) {
//						if (ac.getName().contains(username)) {
//							flag4 = false;
//							break;
//						}
//						
//					}
					String result1 = AccountService.getInstance()
							.createUserAndroid(acc);
					if(result1.equalsIgnoreCase("success")){
						flag4 = true;
					}else if(result1.equalsIgnoreCase("error")){
						flag4 = false;
					}
					

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			frameAnimation.stop();
			if (username.equalsIgnoreCase("")) {
				btnCheckusername.setVisibility(View.VISIBLE);
				btnCheckusername.setBackgroundResource(R.drawable.check_false);
				flag1 = false;
				resulterr = "Please enter your username";
				return;
			} else {
				btnCheckusername.setVisibility(View.VISIBLE);
				btnCheckusername.setBackgroundResource(R.drawable.check_true);
				flag1 = true;
			}
			if (password.equalsIgnoreCase("")) {
				btnCheckpassword.setVisibility(View.VISIBLE);
				btnCheckpassword.setBackgroundResource(R.drawable.check_false);
				flag2 = false;
				resulterr = "Please enter your password";
				return;
			} else {
				btnCheckpassword.setVisibility(View.VISIBLE);
				btnCheckpassword.setBackgroundResource(R.drawable.check_true);
				flag2 = true;
			}
			if (!password.contains(confirmpassword)) {
				btnconfirmpassword.setVisibility(View.VISIBLE);
				btnconfirmpassword
						.setBackgroundResource(R.drawable.check_false);
				flag3 = false;
				resulterr += "Passwords don't match";
			} else {
				btnconfirmpassword.setVisibility(View.VISIBLE);
				btnconfirmpassword.setBackgroundResource(R.drawable.check_true);
				acc.setPassword(password);
				flag3 = true;
			}
			if(!flag4){
				btnCheckusername.setVisibility(View.VISIBLE);
				btnCheckusername.setBackgroundResource(R.drawable.check_false);
				resulterr = "Account already exist";
			}else if(flag4){
				Intent intent = new Intent(
						RegisterActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
			}
			txtResult.setText(resulterr);
		}
	}

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDateSet(DatePickerDialogafter datePickerDialog, int year,
			int month, int day) {
		birthDay = year + "-" + month + "-" + day;
		edday.setText(birthDay);
		
	}
}
