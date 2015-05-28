package com.dvhung.controller;

import com.dvhung.common.ActionEvent;
import com.dvhung.constants.Constants;
import com.dvhung.model.utils.DetailCarModel;

public class DetailCarController {
	public static DetailCarController instance;

	public static DetailCarController getInstance() {
		if (instance == null)
			instance = new DetailCarController();
		return instance;
	}

	public void handleViewEvent(ActionEvent e) {
		switch (e.action) {
		case Constants.INSERT_CAR_DETAIL:
			DetailCarModel.getInstance().handleControllerEvent(e);
			break;
			
		case Constants.UPDATE_CAR_DETAIL:
			DetailCarModel.getInstance().handleControllerEvent(e);
			break;

		default:
			break;
		}
	}
//
//	public void handleModelViewEvent(ActionEvent e) {
//		switch (e.action) {
//		case Constants.GET_ALL_PRODUCE:
//			ProduceActivity sender = (ProduceActivity) e.sender;
//			sender.handleControllerViewEvent(e);
//			break;
//
//		default:
//			break;
//		}
//	}
}
