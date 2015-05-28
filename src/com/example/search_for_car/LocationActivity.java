package com.example.search_for_car;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dvhung.adapter.PlacesAutoCompleteAdapter;
import com.dvhung.dto.LocationDTO;
import com.dvhung.utils.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

public class LocationActivity extends FragmentActivity implements
		OnClickListener {
	AutoCompleteTextView edSearchName;
	ImageView imgCancel;
	GPSTracker gpsTracker;
	GoogleMap googleMap;
	String strMyAddress = "";
	Button btnCurrentLocation, btnLocation, btnAdd;
	public double longituteAddress = 0.0, latitudeAddress = 0.0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		edSearchName = (AutoCompleteTextView) findViewById(R.id.edSearchName);
		edSearchName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (edSearchName.getText().length() > 0) {
					imgCancel.setVisibility(View.VISIBLE);
				} else {
					imgCancel.setVisibility(View.GONE);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		edSearchName.setAdapter(new PlacesAutoCompleteAdapter(this,
				R.layout.item_places_adapter));

		imgCancel = (ImageView) findViewById(R.id.imgCancel);
		imgCancel.setOnClickListener(this);
		gpsTracker = new GPSTracker(this);
		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		googleMap = fm.getMap();

		googleMap.getUiSettings().setZoomControlsEnabled(false);
		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
				gpsTracker.getLatitude(), gpsTracker.getLongitude()), 14.0f));
		

		googleMap.addMarker(new MarkerOptions()
				.position(
						new LatLng(gpsTracker.getLatitude(), gpsTracker
								.getLongitude())).title(strMyAddress)
				.snippet(""));
		longituteAddress = gpsTracker.getLongitude();
		latitudeAddress = gpsTracker.getLatitude();
		try {
			strMyAddress = getAdressLocation(latitudeAddress,
					longituteAddress);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		btnCurrentLocation = (Button) findViewById(R.id.btnCurrentLocation);
		btnLocation = (Button) findViewById(R.id.btnLocation);
		btnAdd = (Button) findViewById(R.id.btnAdd);

		btnLocation.setOnClickListener(this);
		btnCurrentLocation.setOnClickListener(this);
		btnAdd.setOnClickListener(this);
	}

	public String getAdressLocation(double latitude, double longitude)
			throws IOException {
		String myAdress = "";
		Geocoder geocoder;
		List<Address> addresses;
		geocoder = new Geocoder(this, Locale.getDefault());
		addresses = geocoder.getFromLocation(latitude, longitude, 1);
		myAdress = addresses.get(0).getAddressLine(0) + ","
				+ addresses.get(0).getAddressLine(1)
				+ addresses.get(0).getAddressLine(2);
		return myAdress;
	}

	@Override
	public void onClick(View v) {
		LatLng latLng;
		switch (v.getId()) {
		case R.id.imgCancel:
			edSearchName.setText("");
			break;
		case R.id.btnCurrentLocation:
			latLng = new LatLng(gpsTracker.getLatitude(),
					gpsTracker.getLongitude());
			edSearchName.setText(strMyAddress);
			// Showing the current location in Google Map
			googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

			// Zoom in the Google Map
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));
			longituteAddress = gpsTracker.getLongitude();
			latitudeAddress = gpsTracker.getLatitude();
			try {
				strMyAddress = getAdressLocation(latitudeAddress,
						longituteAddress);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			break;
		case R.id.btnLocation:
			new VerySlowTask().execute();
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
					new LatLng(latitudeAddress, longituteAddress), 14.0f));
			googleMap.addMarker(new MarkerOptions()
					.position(new LatLng(latitudeAddress, longituteAddress))
					.title(edSearchName.getText().toString()).snippet(""));
			
			break;
		case R.id.btnAdd:
			if (!edSearchName.getText().toString().equalsIgnoreCase("")) {
				if (longituteAddress != 0.0)
					getLocation();
			}
			break;
		default:
			break;
		}

	}

	public void getLocation() {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		LocationDTO dto = new LocationDTO();
		dto.setNameAddress(edSearchName.getText().toString());
		dto.setLat(latitudeAddress);
		dto.setLon(longituteAddress);
		bundle.putSerializable("objLocation", dto);
		intent.putExtras(bundle);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	private class VerySlowTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			JSONObject json = getLocationInfo(edSearchName.getText().toString());
			getLatLong(json);
			return null;
		}

	}

	public JSONObject getLocationInfo(String address) {
		StringBuilder stringBuilder = new StringBuilder();
		try {

			address = address.replaceAll(" ", "%20");

			HttpPost httppost = new HttpPost(
					"http://maps.google.com/maps/api/geocode/json?address="
							+ address + "&sensor=false");
			HttpClient client = new DefaultHttpClient();
			HttpResponse response;
			stringBuilder = new StringBuilder();

			response = client.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonObject;
	}

	public boolean getLatLong(JSONObject jsonObject) {

		try {

			longituteAddress = ((JSONArray) jsonObject.get("results"))
					.getJSONObject(0).getJSONObject("geometry")
					.getJSONObject("location").getDouble("lng");

			latitudeAddress = ((JSONArray) jsonObject.get("results"))
					.getJSONObject(0).getJSONObject("geometry")
					.getJSONObject("location").getDouble("lat");

		} catch (JSONException e) {
			return false;

		}

		return true;
	}
}
