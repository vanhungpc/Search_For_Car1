package com.dvhung.controller;

import com.dvhung.common.ActionEvent;
import com.dvhung.constants.Constants;
import com.dvhung.model.utils.AddModelDetail;

public class AddModelDetailController {
	public static AddModelDetailController instance;

	public static AddModelDetailController getInstance() {
		if (instance == null)
			instance = new AddModelDetailController();
		return instance;
	}

	public void handleViewEvent(ActionEvent e) {
		switch (e.action) {
		case Constants.INSERT_ADD_MODEL:
			AddModelDetail.getInstance().handleControllerEvent(e);
			break;

		case Constants.UPDATE_ADD_MODEL:
			AddModelDetail.getInstance().handleControllerEvent(e);
			break;

		default:
			break;
		}
	}
}
