package com.dvhung.controller;

import com.dvhung.common.ActionEvent;
import com.dvhung.constants.Constants;
import com.dvhung.model.utils.ModelModel;
import com.example.search_for_car.ModelActivity;

public class ModelController {
	public static  ModelController instance;
	public static  ModelController getInstance(){
		if(instance == null)
			instance = new ModelController();
		return instance;
	}
	
	public void handleViewEvent(ActionEvent e) {
		switch (e.action) {
		case Constants.GET_MODEL_BY_ID_PRODUCE:
			ModelModel.getInstance().handleControllerEvent(e);
			break;

		default:
			break;
		}
	}

	public void handleModelViewEvent(ActionEvent e) {
		switch (e.action) {
		case Constants.GET_MODEL_BY_ID_PRODUCE:
			ModelActivity sender = (ModelActivity) e.sender;
			sender.handleControllerViewEvent(e);
			break;

		default:
			break;
		}
	}
}
