package com.dvhung.fragment;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;

import com.dvhung.model.utils.LoginModel;
import com.example.search_for_car.R;
import com.google.gson.reflect.TypeToken;
import com.pkmmte.circularimageview.CircularImageView;
import com.pkt.rest.client.helper.JsonHelper;
import com.pkt.rest.client.pojo.Account;
import com.pkt.rest.client.services.AccountService;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialogafter;
import com.fourmob.datetimepicker.date.DatePickerDialogafter.OnDateSetListener;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

public class AccountFragment extends Fragment implements OnClickListener,
		OnDateSetListener, TimePickerDialog.OnTimeSetListener {

	Button btnday, btnCheckusername, btnCheckpassword, btnconfirmpassword;
	EditText edUserName, edPassword, edConfirmPassword, edday, edEmail,
			edPhoneNumber, edAddress;
	Button btnUpdateAccount;
	Account acc;
	TextView txtResult;
	AnimationDrawable frameAnimation;
	Dialog dialog;
	public static final String DATEPICKER_TAG = "datepicker";
	public static final String TIMEPICKER_TAG = "timepicker";
	DatePickerDialogafter datePickerDialog;
	CircularImageView imageProfile;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_account, container,
				false);
		btnday = (Button) rootView.findViewById(R.id.btnday);
		edday = (EditText) rootView.findViewById(R.id.edday);
		edUserName = (EditText) rootView.findViewById(R.id.edUserName);
		edPassword = (EditText) rootView.findViewById(R.id.edPassword);
		edConfirmPassword = (EditText) rootView
				.findViewById(R.id.edConfirmPassword);
		edEmail = (EditText) rootView.findViewById(R.id.edEmail);
		edPhoneNumber = (EditText) rootView.findViewById(R.id.edPhoneNumber);
		edAddress = (EditText) rootView.findViewById(R.id.edAddress);
		btnCheckusername = (Button) rootView
				.findViewById(R.id.btnCheckusername);
		btnCheckpassword = (Button) rootView
				.findViewById(R.id.btnCheckpassword);
		btnconfirmpassword = (Button) rootView
				.findViewById(R.id.btnconfirmpassword);
		txtResult = (TextView) rootView.findViewById(R.id.txtResult);
		btnday.setOnClickListener(this);

		btnUpdateAccount = (Button) rootView
				.findViewById(R.id.btnUpdateAccount);
		btnUpdateAccount.setOnClickListener(this);
		// get calendar
		Account acc = LoginModel.getInstance().getAccount(getActivity());
		username = acc.getName();
		password = acc.getPassword();
		birthDay = acc.getBirthDay();
		address = acc.getAddress();
		phonenumber = acc.getPhoneNumber();
		email = acc.getEmail();
		edUserName.setText(username);
		edPassword.setText(password);
		edday.setText(birthDay);
		edAddress.setText(address);
		edPhoneNumber.setText(phonenumber);
		edEmail.setText(email);
		acc = new Account();

		if (savedInstanceState != null) {
			DatePickerDialogafter dpd = (DatePickerDialogafter) getActivity()
					.getSupportFragmentManager().findFragmentByTag(
							DATEPICKER_TAG);
			if (dpd != null) {
				dpd.setOnDateSetListener(this);
			}

			TimePickerDialog tpd = (TimePickerDialog) getActivity()
					.getSupportFragmentManager().findFragmentByTag(
							TIMEPICKER_TAG);
			if (tpd != null) {
				tpd.setOnTimeSetListener(this);
			}
		}
		return rootView;
	}

	// @Override
	// @Deprecated
	// protected Dialog onCreateDialog(int id) {
	// return new DatePickerDialog(getActivity(), datePickerListener, year,
	// month, day);
	// }
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		final Calendar calendar = Calendar.getInstance();
		datePickerDialog = DatePickerDialogafter.newInstance(this,
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), isVibrate());
		

	}

	private boolean isVibrate() {
		return true;
	}

	private boolean isCloseOnSingleTapDay() {
		return false;
	}

	@SuppressWarnings("static-access")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnday:
			datePickerDialog.setVibrate(isVibrate());
			datePickerDialog.setYearRange(1945, 2015);
			datePickerDialog.setCloseOnSingleTapDay(isCloseOnSingleTapDay());
			datePickerDialog.show(getActivity().getSupportFragmentManager(),
					DATEPICKER_TAG);
			break;
		case R.id.btnUpdateAccount:

			break;

		default:
			break;
		}

	}

	String username, password, confirmpassword, birthDay, address, email;
	String phonenumber;
	boolean flag1, flag2 = true, flag3, flag4 = false;

	public void updateAccount() {
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
	}

	private class VerySlowTask extends AsyncTask<Void, Void, Void> {
		String resulterr = "";

		public VerySlowTask() {
			dialog = new Dialog(getActivity(), R.style.NewDialog);
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
				// String accountsJson = AccountService.getInstance()
				// .getUseAndroid();
				// Type typeOfT = new TypeToken<List<Account>>() {
				// }.getType();
				// Object object = JsonHelper.GetInstance().GetObjectUsingGson(
				// accountsJson, typeOfT, "account");
				// @SuppressWarnings("unchecked")
				// List<Account> accounts = (List<Account>) object;
				//
				// System.out.println(accounts.get(0).getName());

				//

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

					// for (Account ac : accounts) {
					// if (ac.getName().contains(username)) {
					// flag4 = false;
					// break;
					// }
					//
					// }
					boolean result1 = AccountService.getInstance()
							.updateUserAndroid(acc);
					if (result1) {
						flag4 = true;
					} else if (!result1) {
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
			if (!flag4) {
				btnCheckusername.setVisibility(View.VISIBLE);
				btnCheckusername.setBackgroundResource(R.drawable.check_false);
				resulterr = "Account already exist";
			} else if (flag4) {
				// Intent intent = new Intent(
				// getActivity(),
				// LoginActivity.class);
				// startActivity(intent);

			}
			txtResult.setText(resulterr);
		}
	}

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
		Toast.makeText(getActivity(), "new time:" + hourOfDay + "-" + minute,
				Toast.LENGTH_LONG).show();

	}

	@Override
	public void onDateSet(
			com.fourmob.datetimepicker.date.DatePickerDialogafter datePickerDialog,
			int year, int month, int day) {
		birthDay = year + "-" + month + "-" + day;
		edday.setText(birthDay);
	}
}
