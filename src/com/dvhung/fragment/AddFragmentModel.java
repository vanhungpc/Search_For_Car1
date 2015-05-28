package com.dvhung.fragment;

import com.dvhung.common.ActionEvent;
import com.dvhung.constants.Constants;
import com.dvhung.controller.AddModelDetailController;
import com.dvhung.dto.AddModelDetailDTO;
import com.dvhung.dto.LocationDTO;
import com.dvhung.dto.LoginDTO;
import com.dvhung.dto.ModelDTO;
import com.dvhung.dto.ProducerDTO;
import com.dvhung.model.utils.AddModelDetail;
import com.dvhung.model.utils.LoginModel;
import com.example.search_for_car.LocationActivity;
import com.example.search_for_car.ModelActivity;
import com.example.search_for_car.ProduceActivity;
import com.example.search_for_car.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddFragmentModel extends Fragment implements OnClickListener {
	LinearLayout lnProduce, lnModel, lnLocation;
	TextView txtProduce, txtModel, txtNameAddress;
	String nameProduce = "";
	String nameModel = "";
	int idProduce = 0;
	public String nameCar = "";
	ModelDTO model = new ModelDTO();
	int idModel = 0;
	String nameAddress = "";
	double lat;
	double lon;
	Button btnAdd;

	public static AddFragmentModel instance;

	public static AddFragmentModel getInstance() {
		if (instance == null)
			instance = new AddFragmentModel();
		return instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_add_model,
				container, false);
		lnProduce = (LinearLayout) rootView.findViewById(R.id.lnProduce);
		txtProduce = (TextView) rootView.findViewById(R.id.txtProduce);
		txtModel = (TextView) rootView.findViewById(R.id.txtModel);
		txtNameAddress = (TextView) rootView.findViewById(R.id.txtNameAddress);
		lnModel = (LinearLayout) rootView.findViewById(R.id.lnModel);
		lnLocation = (LinearLayout) rootView.findViewById(R.id.lnLocation);
		lnProduce.setOnClickListener(this);
		lnModel.setOnClickListener(this);
		lnLocation.setOnClickListener(this);

		Bundle bundle = new Bundle();
		bundle.putString("name", "From Activity");
		// set Fragmentclass Arguments
		AddFragmentImages fragobj = new AddFragmentImages();
		fragobj.setArguments(bundle);
		btnAdd = (Button) rootView.findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		AddModelDetailDTO dto = AddModelDetail.getInstance()
				.getAddModel(getActivity());
		if (dto != null) {
			nameProduce = dto.getNameProduce();
			nameModel = dto.getNameModel();
			nameAddress = dto.getNameAddress();
			idProduce = dto.getIdPro();
		}
		txtProduce.setText(nameProduce);
		txtModel.setText(nameModel);
		nameCar = nameProduce + " - " + nameModel;
		txtNameAddress.setText(nameAddress);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.lnProduce:
			intent = new Intent(getActivity(), ProduceActivity.class);
			startActivityForResult(intent, 2);
			break;
		case R.id.lnModel:
			if (idProduce != 0) {
				intent = new Intent(getActivity(), ModelActivity.class);
				Bundle data = new Bundle();
				data.putInt("idProduce", idProduce);
				intent.putExtras(data);
				startActivityForResult(intent, 3);
			}

			break;
		case R.id.lnLocation:
			intent = new Intent(getActivity(), LocationActivity.class);
			startActivityForResult(intent, 4);
			break;
		case R.id.btnAdd:
			if (txtProduce.getText().toString().equalsIgnoreCase("")) {

			} else if (txtModel.getText().toString().equalsIgnoreCase("")) {

			} else if (txtNameAddress.getText().toString().equalsIgnoreCase("")) {

			} else if (!txtProduce.getText().toString().equalsIgnoreCase("")
					&& !txtModel.getText().toString().equalsIgnoreCase("")
					&& !txtNameAddress.getText().toString()
							.equalsIgnoreCase("")) {
				AddModelDetailDTO addModelDTO = new AddModelDetailDTO();
				LoginDTO login = LoginModel.getInstance().getUser(
						getActivity());
				addModelDTO.setIdAcc(login.getId());
				addModelDTO.setIdModel(idModel);
				addModelDTO.setNameProduce(nameProduce);
				addModelDTO.setNameModel(nameModel);
				addModelDTO.setLat(lat);
				addModelDTO.setLon(lon);
				addModelDTO.setNameAddress(nameAddress);
				addModelDTO.setFlag(0);
				addModelDTO.setIdPro(idProduce);
				
				AddModelDetail model = AddModelDetail.getInstance();
				boolean checkAddModelUpdate = model.checkAddModelUpdate(this);
				if (checkAddModelUpdate) {
					ActionEvent e = new ActionEvent(this,
							Constants.UPDATE_ADD_MODEL, addModelDTO, null);
					AddModelDetailController.getInstance().handleViewEvent(e);
				} else {
					ActionEvent e = new ActionEvent(this,
							Constants.INSERT_ADD_MODEL, addModelDTO, null);
					AddModelDetailController.getInstance().handleViewEvent(e);
				}

			}
			break;

		default:
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
			ProducerDTO pro = (ProducerDTO) data.getSerializableExtra("myobj");
			nameProduce = pro.getNameProducer();
			txtProduce.setText(nameProduce);
			idProduce = pro.getId();
			txtModel.setText("");
			nameCar = nameProduce;

		}
		if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
			ModelDTO model = (ModelDTO) data.getSerializableExtra("objModel");
			nameModel = model.getNameModel();
			idModel = model.getIdMode();
			txtModel.setText(nameModel);
			nameCar = nameProduce + " - " + nameModel;

		}
		if (requestCode == 4 && resultCode == Activity.RESULT_OK) {
			LocationDTO location = (LocationDTO) data
					.getSerializableExtra("objLocation");
			nameAddress = location.getNameAddress();
			lat = location.getLat();
			lon = location.getLon();
			txtNameAddress.setText(nameAddress);
		}

	}

	public String getNameCar() {
		return nameCar;
	}

	private class VerySlowTask extends AsyncTask<Void, Void, Void> {

		public void VerySlowTask() {

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
