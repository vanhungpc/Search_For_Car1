package com.dvhung.fragment;

import com.dvhung.common.ActionEvent;
import com.dvhung.constants.Constants;
import com.dvhung.controller.DetailCarController;
import com.dvhung.dto.AddModelDetailDTO;
import com.dvhung.dto.DetailCarDTO;
import com.dvhung.model.utils.AddModelDetail;
import com.dvhung.model.utils.DetailCarModel;
import com.example.search_for_car.R;
import com.google.android.gms.internal.en;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddFragmentDetail extends Fragment implements OnClickListener {
	String nameCar = "", bodyType = "", doors = "", exteriorColor = "",
			fuel = "", engine = "", transmission = "", origin = "";
	String price = "";
	String milliage = "";
	EditText edNameCar, edPrice, edBodyType, edDoors, edExteriorColor, edFuel,
			edEngine, edTransmission, edOrigin, edMiliage;
	public static AddFragmentDetail instance;
	Button btnAdd;
	DetailCarDTO detail;

	public static AddFragmentDetail getInstance() {
		if (instance == null)
			instance = new AddFragmentDetail();
		return instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_add_detail,
				container, false);
		btnAdd = (Button) rootView.findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(this);
		edNameCar = (EditText) rootView.findViewById(R.id.edNameCar);
		edPrice = (EditText) rootView.findViewById(R.id.edPrice);
		edBodyType = (EditText) rootView.findViewById(R.id.edBody_Type);
		edDoors = (EditText) rootView.findViewById(R.id.edDoors);
		edExteriorColor = (EditText) rootView
				.findViewById(R.id.edExterior_Color);
		edFuel = (EditText) rootView.findViewById(R.id.edFuel);
		edEngine = (EditText) rootView.findViewById(R.id.edEngine);
		edTransmission = (EditText) rootView.findViewById(R.id.edTransmission);
		edOrigin = (EditText) rootView.findViewById(R.id.edOrigin);
		edMiliage = (EditText) rootView.findViewById(R.id.edMilliage);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		DetailCarDTO dto = DetailCarModel.getInstance().getDetailerCar(
				getActivity());
		if (dto != null) {
			// nameCar = dto.getNameDetail();
			price = dto.getPrice();
			bodyType = dto.getBodyType();
			doors = dto.getDoors();
			exteriorColor = dto.getExteriorColor();
			fuel = dto.getFuel();
			engine = dto.getEngine();
			transmission = dto.getTransmission();
			origin = dto.getOrigin();
			milliage = dto.getMilliage();

		}
		AddModelDetailDTO dtomodel = AddModelDetail.getInstance().getAddModel(
				getActivity());
		if (dtomodel != null) {
			nameCar = dtomodel.getNameProduce() + " " + dtomodel.getNameModel();
		}
		edNameCar.setText(nameCar);
		edPrice.setText(price);
		edBodyType.setText(bodyType);
		edDoors.setText(doors);
		edExteriorColor.setText(exteriorColor);
		edFuel.setText(fuel);
		edEngine.setText(engine);
		edTransmission.setText(transmission);
		edOrigin.setText(origin);
		edMiliage.setText(milliage);

		// edNameCar.setText(AddFragmentModel.getInstance().getNameCar());

	}

	public void addDetailCar() {
		nameCar = edNameCar.getText().toString();
		price = edPrice.getText().toString();
		bodyType = edBodyType.getText().toString();
		doors = edDoors.getText().toString();
		exteriorColor = edExteriorColor.getText().toString();
		fuel = edFuel.getText().toString();
		engine = edEngine.getText().toString();
		transmission = edTransmission.getText().toString();
		origin = edOrigin.getText().toString();
		milliage = edMiliage.getText().toString();
		detail = new DetailCarDTO();
		detail.setNameDetail(nameCar);
		detail.setPrice(price);
		detail.setBodyType(bodyType);
		detail.setDoors(doors);
		detail.setExteriorColor(exteriorColor);
		detail.setFuel(fuel);
		detail.setEngine(engine);
		detail.setTransmission(transmission);
		detail.setOrigin(origin);
		detail.setMilliage(milliage);
		detail.setIdSV(0);
		detail.setFlag(0);
		// new VerySlowTask(detail).execute();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAdd:
			addDetailCar();
			DetailCarModel model = DetailCarModel.getInstance();
			boolean checkCarUpdate = model.checkNewsDetailCar(this);
			if (checkCarUpdate) {
				ActionEvent e = new ActionEvent(this,
						Constants.UPDATE_CAR_DETAIL, detail, null);
				DetailCarController.getInstance().handleViewEvent(e);
			} else {
				ActionEvent e = new ActionEvent(this,
						Constants.INSERT_CAR_DETAIL, detail, null);
				DetailCarController.getInstance().handleViewEvent(e);
			}

			break;

		default:
			break;
		}

	}
}
