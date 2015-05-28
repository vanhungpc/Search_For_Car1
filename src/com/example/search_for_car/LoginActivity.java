package com.example.search_for_car;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONObject;

import com.dvhung.common.ActionEvent;
import com.dvhung.constants.Constants;
import com.dvhung.controller.LoginController;
import com.dvhung.dto.LoginDTO;
import com.dvhung.model.utils.LoginModel;
import com.google.gson.reflect.TypeToken;
import com.pkt.rest.client.helper.JsonHelper;
import com.pkt.rest.client.pojo.Account;
import com.pkt.rest.client.services.AccountService;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener {
	Button btnRegister, btnLogin;
	EditText edUserName, edpassword;
	TextView txtContenterr;
	AnimationDrawable frameAnimation;
	Dialog dialog, dialogerr;
	int idAcc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		edUserName = (EditText) findViewById(R.id.edUserName);
		edpassword = (EditText) findViewById(R.id.edpassword);
		txtContenterr = (TextView) findViewById(R.id.txtContenterr);
		btnRegister.setOnClickListener(this);
		btnLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		int id = v.getId();
		switch (id) {
		case R.id.btnRegister:
			intent = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.btnLogin:
			new VerySlowTask().execute();
			break;
		default:
			break;
		}

	}

	String userName;
	String password;
	boolean flag1, flag2, flag3;

	private class VerySlowTask extends AsyncTask<Void, Void, Void> {

		String resulterr = "";
		LoginDTO loginDTO;

		public VerySlowTask() {
			dialog = new Dialog(LoginActivity.this, R.style.NewDialog);
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
				loginDTO = new LoginDTO();
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
				userName = edUserName.getText().toString().trim();
				password = edpassword.getText().toString().trim();
				Account acc = new Account();
				acc.setName(userName);
				acc.setPassword(password);

				if (userName.equalsIgnoreCase("")) {
					flag1 = false;
				} else {
					flag1 = true;
				}
				if (password.equalsIgnoreCase("")) {
					flag2 = false;

				} else {
					flag2 = true;
				}

				if (flag1 && flag2) {
					String checkLogin = AccountService.getInstance()
							.checkLogin(acc);
					idAcc = Integer.parseInt(checkLogin);

					if (idAcc != 0) {
						flag3 = true;
						loginDTO.setId(idAcc);
						loginDTO.setUserName(userName);
						loginDTO.setPassword(password);
						loginDTO.setFlag(1);
					} else {
						flag3 = false;
					}

					// for (Account ac : accounts) {
					//
					// if (ac.getName().contains(userName)
					// && ac.getPassword().contains(password)) {
					// flag3 = true;
					//
					// } else {
					// flag3 = false;
					// }
					//
					// }

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
			if (!flag1 || !flag2) {
				dialog.dismiss();
				frameAnimation.stop();
				dialogerr = new Dialog(LoginActivity.this,
						android.R.style.Theme_Translucent);
				dialogerr.requestWindowFeature(Window.FEATURE_NO_TITLE);

				dialogerr.setCancelable(true);
				dialogerr.setContentView(R.layout.dialog_err);
				TextView txtContenterr = (TextView) dialogerr
						.findViewById(R.id.txtContenterr);
				txtContenterr.setText("Please enter username and password");
				Button btnOke = (Button) dialogerr.findViewById(R.id.bntOk);

				btnOke.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						dialogerr.dismiss();
					}
				});

				dialogerr.show();
			} else if (flag3) {
				dialog.dismiss();
				frameAnimation.stop();
				ActionEvent e = new ActionEvent(LoginActivity.this,
						Constants.INSERT_USER, loginDTO, null);
				LoginController.getInstance().handleViewEvent(e);

				new VerySlowTaskUpdateUserLocal(idAcc).execute();
				
			} else if (!flag3) {
				dialog.dismiss();
				frameAnimation.stop();
				dialogerr = new Dialog(LoginActivity.this,
						android.R.style.Theme_Translucent);
				dialogerr.requestWindowFeature(Window.FEATURE_NO_TITLE);

				dialogerr.setCancelable(true);
				dialogerr.setContentView(R.layout.dialog_err);
				TextView txtContenterr = (TextView) dialogerr
						.findViewById(R.id.txtContenterr);
				txtContenterr.setText("Incorrect username or password");
				Button btnOke = (Button) dialogerr.findViewById(R.id.bntOk);

				btnOke.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						dialogerr.dismiss();
					}
				});

				dialogerr.show();
			}
		}
	}

	private class VerySlowTaskUpdateUserLocal extends
			AsyncTask<Void, Void, Void> {
		int idAcc;
		String getUser = "";
		Account acc;

		public VerySlowTaskUpdateUserLocal(int _idAcc) {
			idAcc = _idAcc;
			acc = new Account();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				getUser = AccountService.getInstance().getUseAndroid(
						String.valueOf(idAcc));
				if (getUser != null) {
					JSONObject obj = new JSONObject(getUser);
					String idAcc = obj.getString("idAcc");
					String name = obj.getString("name");
					String password = obj.getString("password");
					String email = obj.getString("email");
					String address = obj.getString("address");
					String birthDay = obj.getString("birthDay");
					String phoneNumber = obj.getString("phoneNumber");
					acc.setIdAcc(Integer.parseInt(idAcc));
					acc.setName(name);
					acc.setPassword(password);
					acc.setEmail(email);
					acc.setAddress(address);
					acc.setBirthDay(birthDay);
					acc.setPhoneNumber(phoneNumber);
					
					
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (getUser != null) {
				boolean flag = LoginModel.getInstance().CheckUserExits(
						LoginActivity.this);
				if (flag) {
					// update user
					ActionEvent e = new ActionEvent(LoginActivity.this,
							Constants.UPDATE_ACCOUNT, acc, null);
					LoginController.getInstance().handleViewEvent(e);
				} else {
					// insert user
					ActionEvent e = new ActionEvent(LoginActivity.this,
							Constants.INSERT_ACCOUNT, acc, null);
					LoginController.getInstance().handleViewEvent(e);
				}
				
				Intent intent;
				intent = new Intent(LoginActivity.this,
						ManagerCarActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}
}
