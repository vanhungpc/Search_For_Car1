package com.example.search_for_car;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dvhung.adapter.NewsCarAdapter;
import com.dvhung.dto.NewsCarDTO;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.pkt.rest.client.helper.JsonHelper;
import com.pkt.rest.client.pojo.Car;
import com.pkt.rest.client.pojo.Comment;
import com.pkt.rest.client.pojo.DetailCar;
import com.pkt.rest.client.pojo.Image;
import com.pkt.rest.client.services.CarService;

public class NewsCarActivity extends Activity implements OnItemClickListener,
		OnClickListener {
	NewsCarAdapter adapter;
	ArrayList<Car> arrData;
	ListView lvCard;
	TextView txtTitle;
	Button btnBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_card);
		arrData = new ArrayList<Car>();
		lvCard = (ListView) findViewById(R.id.lvCard);
		// initData();

		lvCard.setOnItemClickListener(this);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtTitle.setText("New car");
		btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(this);

		// Intent intent = new Intent(NewsCarActivity.this,
		// DetailerCarActivity.class);
		// startActivity(intent);
		new VerySlowTask().execute();

	}

	// int[] image = new int[] { R.drawable.car1, R.drawable.car2,
	// R.drawable.car3, R.drawable.car4, R.drawable.car5 };
	// String arrName[] = { "Toyota Fortuner", "Toyota Fortuner",
	// "Toyota Fortuner", "Toyota Fortuner", "Toyota Fortuner" };
	// String arrPrice[] = { "5000$", "5000$", "5000$", "5000$", "5000$" };
	// String arrMiliage[] = { "Mileage: 93,194", "Mileage: 93,194",
	// "Mileage: 93,194", "Mileage: 93,194", "Mileage: 93,194" };
	// String arrColor[] = { "Color: black silver", "Color: black silver",
	// "Color: black silver", "Color: black silver", "Color: black silver" };

	// public void initData() {
	// NewsCarDTO dto;
	// for (int i = 0; i < arrName.length; i++) {
	// dto = new NewsCarDTO();
	// dto.setImages(image[i]);
	// dto.setNameCar(arrName[i]);
	// dto.setMiliage(arrMiliage[i]);
	// dto.setPrice(arrPrice[i]);
	// dto.setColor(arrColor[i]);
	// arrData.add(dto);
	// }
	// }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(NewsCarActivity.this,
				DetailerCarActivity.class);
		try {
			Car car = (Car) lvCard.getItemAtPosition(position);
			Bundle data = new Bundle();
			data.putSerializable("objCar", car);
			intent.putExtras(data);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnBack:
			finish();
			break;

		default:
			break;
		}

	}

	private class VerySlowTask extends AsyncTask<Void, Void, Void> {
		// ArrayList<Car> arrCar;

		@Override
		protected Void doInBackground(Void... params) {
			String carJson = CarService.getInstance().getCarAndroid();
			// Type typeOfT = new TypeToken<List<Car>>() {
			// }.getType();
			// Object object = JsonHelper.GetInstance().GetObjectUsingGson(
			// carJson, typeOfT, "car");
			//
			// @SuppressWarnings("unchecked")
			// List<Car> car = (List<Car>) object;
			//
			// for (Car c : car) {
			//
			// }
			// arrCar = new ArrayList<Car>();
			if (carJson != null) {
				try {

					// JSONObject json = new JSONObject(carJson);
					JSONObject obj = new JSONObject(carJson);
					// json comment

					Object item = obj.get("car");

					if (item instanceof JSONArray) {
						JSONArray jsonArr = (JSONArray) item;
						for (int i = 0; i < jsonArr.length(); i++) {
							JSONObject jsonOb = jsonArr.getJSONObject(i);
							Car objCar = new Car();
							// json comment
							if (jsonOb.has("arrComment")) {
								Object itemComment = jsonOb.get("arrComment");
								if (itemComment instanceof JSONArray) {
									JSONArray arrComment = (JSONArray) itemComment;
									List<Comment> lstComment = new ArrayList<Comment>();
									for (int k = 0; k < arrComment.length(); k++) {
										Comment com = new Comment();
										JSONObject objCm = arrComment
												.getJSONObject(k);
										String contentComment = objCm
												.getString("contentComment");
										String idAcc = objCm.getString("idAcc");
										String idCar = objCm.getString("idCar");
										String idCom = objCm.getString("idCom");
										String userName = objCm.getString("userName");
										com.setContentComment(contentComment);
										com.setIdAcc(Integer.parseInt(idAcc));
										com.setIdCar(Integer.parseInt(idCar));
										com.setIdCom(Integer.parseInt(idCom));
										com.setUserName(userName);
										
										lstComment.add(com);

									}
									objCar.setArrComment(lstComment);
								} else if (itemComment instanceof JSONObject) {
									List<Comment> lstComment = new ArrayList<Comment>();

									Comment com = new Comment();
									JSONObject objCm = (JSONObject) itemComment;
									String contentComment = objCm
											.getString("contentComment");
									String idAcc = objCm.getString("idAcc");
									String idCar = objCm.getString("idCar");
									String idCom = objCm.getString("idCom");
									String userName = objCm.getString("userName");
									com.setContentComment(contentComment);
									com.setIdAcc(Integer.parseInt(idAcc));
									com.setIdCar(Integer.parseInt(idCar));
									com.setIdCom(Integer.parseInt(idCom));
									com.setUserName(userName);
									lstComment.add(com);
									objCar.setArrComment(lstComment);
								}
							}

							// json images
							if (jsonOb.has("arrImage")) {
								Object itemImg = jsonOb.get("arrImage");
								if (itemImg instanceof JSONArray) {
									JSONArray arrImg = (JSONArray) itemImg;

									List<Image> lstImg = new ArrayList<Image>();
									for (int j = 0; j < arrImg.length(); j++) {
										Image img = new Image();

										JSONObject indexImg = arrImg
												.getJSONObject(j);
										String idCar = indexImg
												.getString("idCar");
										String idImg = indexImg
												.getString("idImg");
										String name = indexImg
												.getString("name");
										String url = indexImg.getString("url");
										img.setIdCar(Integer.parseInt(idCar));
										img.setIdImg(Integer.parseInt(idImg));
										img.setName(name);
										img.setUrl(url);
										lstImg.add(img);
									}
									objCar.setArrImage(lstImg);
								} else if (itemImg instanceof JSONObject) {
									JSONObject objImg = (JSONObject) itemImg;

									List<Image> lstImg = new ArrayList<Image>();
									Image img = new Image();

									String idCar = objImg.getString("idCar");
									String idImg = objImg.getString("idImg");
									String name = objImg.getString("name");
									String url = objImg.getString("url");
									img.setIdCar(Integer.parseInt(idCar));
									img.setIdImg(Integer.parseInt(idImg));
									img.setName(name);
									img.setUrl(url);
									lstImg.add(img);
									objCar.setArrImage(lstImg);
								}

							}

							// json detail car
							DetailCar objDetail = new DetailCar();
							JSONObject obDetail = jsonOb
									.getJSONObject("detailCar");
							if (obDetail != null) {

								String bodyType = obDetail
										.getString("bodyType");
								String doors = obDetail.getString("doors");
								String engine = obDetail.getString("engine");
								String exteriorColor = obDetail
										.getString("exteriorColor");
								String fuel = obDetail.getString("fuel");
								String idDetail = obDetail
										.getString("idDetail");
								String milliage = obDetail
										.getString("milliage");
								String nameDetail = obDetail
										.getString("nameDetail");
								String origin = obDetail.getString("origin");
								String price = obDetail.getString("price");
								String transmission = obDetail
										.getString("transmission");
								objDetail.setBodyType(bodyType);
								objDetail.setDoors(doors);
								objDetail.setEngine(engine);
								objDetail.setExteriorColor(exteriorColor);
								objDetail.setFuel(fuel);
								objDetail.setId(Integer.parseInt(idDetail));
								objDetail.setMilliage(milliage);
								objDetail.setNameDetail(nameDetail);
								objDetail.setOrigin(origin);
								objDetail.setPrice(price);
								objDetail.setTransmission(transmission);
								objCar.setDetailCar(objDetail);
							}

							// json car
							String idAcc = jsonOb.getString("idAcc");
							String idCar = jsonOb.getString("idCar");
							String idModel = jsonOb.getString("idModel");
							String lat = jsonOb.getString("lat");
							String lon = jsonOb.getString("lon");
							String nameCar = jsonOb.getString("nameCar");

							objCar.setIdCar(Integer.parseInt(idCar));
							objCar.setIdAcc(Integer.parseInt(idAcc));
							objCar.setIdModel(Integer.parseInt(idModel));
							objCar.setLat(lat);
							objCar.setLon(lon);
							objCar.setNameCar(nameCar);
							arrData.add(objCar);

						}
					} else if (item instanceof JSONObject) {
						// for (int i = 0; i < jsonArr.length(); i++) {
						JSONObject jsonOb = (JSONObject) item;
						Car objCar = new Car();
						// json comment
						if (jsonOb.has("arrComment")) {
							Object itemComment = jsonOb.get("arrComment");
							if (itemComment instanceof JSONArray) {
								JSONArray arrComment = (JSONArray) itemComment;
								List<Comment> lstComment = new ArrayList<Comment>();
								for (int k = 0; k < arrComment.length(); k++) {
									Comment com = new Comment();
									JSONObject objCm = arrComment
											.getJSONObject(k);
									String contentComment = objCm
											.getString("contentComment");
									String idAcc = objCm.getString("idAcc");
									String idCar = objCm.getString("idCar");
									String idCom = objCm.getString("idCom");
									String userName = objCm.getString("userName");
									com.setContentComment(contentComment);
									com.setIdAcc(Integer.parseInt(idAcc));
									com.setIdCar(Integer.parseInt(idCar));
									com.setIdCom(Integer.parseInt(idCom));
									com.setUserName(userName);
									lstComment.add(com);

								}
								objCar.setArrComment(lstComment);
							} else if (itemComment instanceof JSONObject) {
								List<Comment> lstComment = new ArrayList<Comment>();

								Comment com = new Comment();
								JSONObject objCm = (JSONObject) itemComment;
								String contentComment = objCm
										.getString("contentComment");
								String idAcc = objCm.getString("idAcc");
								String idCar = objCm.getString("idCar");
								String idCom = objCm.getString("idCom");
								String userName = objCm.getString("userName");
								com.setContentComment(contentComment);
								com.setIdAcc(Integer.parseInt(idAcc));
								com.setIdCar(Integer.parseInt(idCar));
								com.setIdCom(Integer.parseInt(idCom));
								com.setUserName(userName);
								lstComment.add(com);
								objCar.setArrComment(lstComment);
							}
						}
						// json images
						if (jsonOb.has("arrImage")) {
							JSONArray arrImg = jsonOb.getJSONArray("arrImage");
							List<Image> lstImg = new ArrayList<Image>();
							for (int j = 0; j < arrImg.length(); j++) {
								Image img = new Image();

								JSONObject indexImg = arrImg.getJSONObject(j);
								String idCar = indexImg.getString("idCar");
								String idImg = indexImg.getString("idImg");
								String url = indexImg.getString("url");
								img.setIdCar(Integer.parseInt(idCar));
								img.setIdImg(Integer.parseInt(idImg));
								img.setUrl(url);
								lstImg.add(img);
							}
							objCar.setArrImage(lstImg);
						}

						// json detail car
						DetailCar objDetail = new DetailCar();
						JSONObject obDetail = jsonOb.getJSONObject("detailCar");
						if (obDetail != null) {

							String bodyType = obDetail.getString("bodyType");
							String doors = obDetail.getString("doors");
							String engine = obDetail.getString("engine");
							String exteriorColor = obDetail
									.getString("exteriorColor");
							String fuel = obDetail.getString("fuel");
							String idDetail = obDetail.getString("idDetail");
							String milliage = obDetail.getString("milliage");
							String nameDetail = obDetail
									.getString("nameDetail");
							String origin = obDetail.getString("origin");
							String price = obDetail.getString("price");
							String transmission = obDetail
									.getString("transmission");
							objDetail.setBodyType(bodyType);
							objDetail.setDoors(doors);
							objDetail.setEngine(engine);
							objDetail.setExteriorColor(exteriorColor);
							objDetail.setFuel(fuel);
							objDetail.setId(Integer.parseInt(idDetail));
							objDetail.setMilliage(milliage);
							objDetail.setNameDetail(nameDetail);
							objDetail.setOrigin(origin);
							objDetail.setPrice(price);
							objDetail.setTransmission(transmission);
							objCar.setDetailCar(objDetail);
						}

						// json car
						String idAcc = jsonOb.getString("idAcc");
						String idCar = jsonOb.getString("idCar");
						String idModel = jsonOb.getString("idModel");
						String lat = jsonOb.getString("lat");
						String lon = jsonOb.getString("lon");
						String nameCar = jsonOb.getString("nameCar");

						objCar.setIdCar(Integer.parseInt(idCar));
						objCar.setIdAcc(Integer.parseInt(idAcc));
						objCar.setIdModel(Integer.parseInt(idModel));
						objCar.setLat(lat);
						objCar.setLon(lon);
						objCar.setNameCar(nameCar);
						arrData.add(objCar);

						// }
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				 * Type typeOfT = new TypeToken<List<Car>>() { }.getType();
				 * Object object = JsonHelper.GetInstance().GetObjectUsingGson(
				 * carJson, typeOfT, "addcar");
				 * 
				 * @SuppressWarnings("unchecked") List<Car> car = (List<Car>)
				 * object;
				 * 
				 * for (Car c : car) {
				 * 
				 * }
				 */
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			adapter = new NewsCarAdapter(NewsCarActivity.this,
					R.layout.adapter_news_car, arrData);
			lvCard.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}

	}
}
