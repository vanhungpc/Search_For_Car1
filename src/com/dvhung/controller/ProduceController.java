package com.dvhung.controller;

import com.dvhung.common.ActionEvent;
import com.dvhung.constants.Constants;
import com.dvhung.model.utils.ProducerModel;
import com.example.search_for_car.ProduceActivity;

public class ProduceController {
	public static ProduceController instance;

	public static ProduceController getInstance() {
		if (instance == null)
			instance = new ProduceController();
		return instance;
	}

	public void handleViewEvent(ActionEvent e) {
		switch (e.action) {
		case Constants.GET_ALL_PRODUCE:
			ProducerModel.getInstance().handleControllerEvent(e);
			break;

		default:
			break;
		}
	}

	public void handleModelViewEvent(ActionEvent e) {
		switch (e.action) {
		case Constants.GET_ALL_PRODUCE:
			ProduceActivity sender = (ProduceActivity) e.sender;
			sender.handleControllerViewEvent(e);
			break;

		default:
			break;
		}
	}
}
