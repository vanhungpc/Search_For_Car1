package com.dvhung.controller;

import com.dvhung.common.ActionEvent;
import com.dvhung.constants.Constants;
import com.dvhung.model.utils.LoginModel;

public class LoginController {
	public static  LoginController instance;
	public static  LoginController getInstance(){
		if(instance == null)
			instance = new LoginController();
		return instance;
	}
	
	public void handleViewEvent(ActionEvent e) {
		switch (e.action) {
		case Constants.INSERT_USER:
			LoginModel.getInstance().handleControllerEvent(e);
			break;
		case Constants.INSERT_ACCOUNT:
			LoginModel.getInstance().handleControllerEvent(e);
			break;
		case Constants.UPDATE_ACCOUNT:
			LoginModel.getInstance().handleControllerEvent(e);
			break;

		default:
			break;
		}
	}

/*	public void handleModelViewEvent(ActionEvent e) {
		switch (e.action) {
		case Constants.INSERT_USER:
			ModelActivity sender = (ModelActivity) e.sender;
			sender.handleControllerViewEvent(e);
			break;

		default:
			break;
		}
	}*/
}
