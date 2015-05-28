package com.dvhung.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dvhung.adapter.NewsCarAdapter;
import com.dvhung.dto.LoginDTO;
import com.dvhung.model.utils.LoginModel;
import com.example.search_for_car.R;
import com.pkt.rest.client.pojo.Car;
import com.pkt.rest.client.pojo.DetailCar;
import com.pkt.rest.client.pojo.Image;
import com.pkt.rest.client.services.CarService;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListCarFragment extends Fragment implements OnItemClickListener {

	NewsCarAdapter adapter;
	ArrayList<Car> arrData;
	ListView lvCard;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.list_car_fragment, container,
				false);
		arrData = new ArrayList<Car>();
		lvCard = (ListView) rootView.findViewById(R.id.lvCard);
		// initData();

		LoginDTO dto = LoginModel.getInstance().getUser(getActivity());
		String idAcc = (String.valueOf(dto.getId()));
		lvCard.setOnItemClickListener(this);
		new VerySlowTask(idAcc).execute();
		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	private class VerySlowTask extends AsyncTask<Void, Void, Void> {
		// ArrayList<Car> arrCar;
		String idAcc;

		public VerySlowTask(String _idAcc) {
			idAcc = _idAcc;
		}

		@Override
		protected Void doInBackground(Void... params) {
			String carJson = CarService.getInstance().getCarAndroidByAccount(
					idAcc);
			if (carJson != null) {
				try {

					// JSONObject json = new JSONObject(carJson);
					JSONObject obj = new JSONObject(carJson);
					Object item = obj.get("car");

					if (item instanceof JSONArray) {
						JSONArray jsonArr = (JSONArray) item;
						for (int i = 0; i < jsonArr.length(); i++) {
							JSONObject jsonOb = jsonArr.getJSONObject(i);
							Car objCar = new Car();
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
										String name = indexImg.getString("name");
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
							// String idCar = jsonOb.getString("idCar");
							String idModel = jsonOb.getString("idModel");
							String lat = jsonOb.getString("lat");
							String lon = jsonOb.getString("lon");
							String nameCar = jsonOb.getString("nameCar");

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
						// String idCar = jsonOb.getString("idCar");
						String idModel = jsonOb.getString("idModel");
						String lat = jsonOb.getString("lat");
						String lon = jsonOb.getString("lon");
						String nameCar = jsonOb.getString("nameCar");

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

			adapter = new NewsCarAdapter(getActivity(),
					R.layout.adapter_news_car, arrData);
			lvCard.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}

	}
}
