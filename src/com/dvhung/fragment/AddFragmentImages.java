package com.dvhung.fragment;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import com.dvhung.adapter.ImagesAdapter;
import com.dvhung.constants.Constants;
import com.dvhung.dto.AddModelDetailDTO;
import com.dvhung.dto.DetailCarDTO;
import com.dvhung.dto.ImageDTO;
import com.dvhung.model.utils.AddModelDetail;
import com.dvhung.model.utils.DetailCarModel;
import com.dvhung.model.utils.ImageUtils;
import com.example.search_for_car.LoginActivity;
import com.example.search_for_car.R;
import com.pkt.rest.client.pojo.Car;
import com.pkt.rest.client.pojo.DetailCar;
import com.pkt.rest.client.pojo.Image;
import com.pkt.rest.client.services.CarService;
import com.pkt.rest.client.services.DetailCarService;
import com.pkt.rest.client.services.ImageService;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class AddFragmentImages extends Fragment implements OnClickListener,
		OnItemLongClickListener {
	ListView lvPhoto;
	Button btnPostCar, btnCamera, btnGalary;
	ImagesAdapter adapter = null;
	ArrayList<Object> arrData = null;
	ImageView imgGallary, imgCapture;

	ArrayList<ImageDTO> arrImage = new ArrayList<ImageDTO>();
	ArrayList<String> urlImg;
	Dialog dialog, dialog1, dialog2, dialog3;
	AnimationDrawable frameAnimation, frameAnimation1, frameAnimation2,
			frameAnimation3;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		arrData = new ArrayList<Object>();
		// initData();
		adapter = new ImagesAdapter(getActivity(),
				R.layout.adapter_insert_cart, arrData);
		lvPhoto.setAdapter(adapter);

		if (arrImage != null) {
			for (ImageDTO img : arrImage) {
				arrData.add(img);
				adapter.notifyDataSetChanged();
			}

		}
		urlImg = new ArrayList<String>();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_add_photos, container,
				false);

		lvPhoto = (ListView) rootView.findViewById(R.id.lvPhoto);
		imgCapture = (ImageView) rootView.findViewById(R.id.imgCapture);
		imgGallary = (ImageView) rootView.findViewById(R.id.imgGallary);
		btnPostCar = (Button) rootView.findViewById(R.id.btnPostCar);

		btnPostCar.setOnClickListener(this);
		lvPhoto.setOnItemLongClickListener(this);
		imgCapture.setOnClickListener(this);
		imgGallary.setOnClickListener(this);
		return rootView;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		return false;
	}

	// public void initData() {
	// ImageDTO dto = new ImageDTO();
	// dto.setId(id);
	// arrData.add(dto);
	// }

	String t = "teo";
	String t1 = "ti";
	int id = R.drawable.car1;

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent = new Intent();
		switch (id) {
		case R.id.btnPostCar:
			AddDetailerCar();
			break;
		case R.id.imgCapture:

			break;
		case R.id.imgGallary:
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			intent.setType("image/*");
			startActivityForResult(intent, 1);
			break;
		default:
			break;
		}

	}

	public void AddDetailerCar() {
		DetailCarDTO dto = DetailCarModel.getInstance().getDetailerCar(
				getActivity());
		DetailCar detail = new DetailCar();
		detail.setNameDetail(dto.getNameDetail());
		detail.setPrice(dto.getPrice());
		detail.setDoors(dto.getDoors());
		detail.setExteriorColor(dto.getExteriorColor());
		detail.setFuel(dto.getFuel());
		detail.setEngine(dto.getEngine());
		detail.setTransmission(dto.getTransmission());
		detail.setOrigin(dto.getOrigin());
		detail.setMilliage(dto.getMilliage());
		new VerySlowTaskAddDetailCar(detail).execute();
	}

	public void AddModelCar(int idDetail) {
		AddModelDetailDTO dto = AddModelDetail.getInstance().getAddModel(
				getActivity());
		Car car = new Car();
		car.setIdAcc(dto.getIdAcc());
		car.setIdModel(dto.getIdModel());
		car.setNameCar(dto.getNameProduce() + " " + dto.getNameModel());
		car.setIdDetal(idDetail);
		car.setLat(String.valueOf(dto.getLat()));
		car.setLon(String.valueOf(dto.getLon()));
		car.setTimeSale("2");
		car.setIdCarline(1);
		car.setIdCity(1);
		new VerySlowTaskAddProduce(car).execute();
	}

	public void AddImages(int idCar) {
		ArrayList<String> arrImg = new ArrayList<String>();
		if (arrImage != null) {
			for (ImageDTO dto : arrImage) {
				arrImg.add(dto.getUrl());

			}
			Object object = arrImg;
			new TaskUploadImage(idCar).execute(object);

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		InputStream stream = null;
		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
			try {
				Uri selectedImageUri = data.getData();
				String selectedImagePath = getPath(selectedImageUri);

				stream = getActivity().getContentResolver().openInputStream(
						data.getData());
				Bitmap bitmap = BitmapFactory.decodeStream(stream);
				ImageDTO dto = new ImageDTO();
				dto.setImgBitmap(bitmap);
				dto.setUrl(selectedImagePath);
				arrImage.add(dto);
				arrData.add(dto);

				adapter.notifyDataSetChanged();
				// image_holder1.setImageBitmap(original);

			} catch (Exception e) {
				e.printStackTrace();
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = getActivity().managedQuery(uri, projection, null, null,
				null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	private class TaskUploadImage extends AsyncTask<Object, Void, Void> {

		String nameImage = "";
		int idCar;
		ArrayList<String> arrURL;

		public TaskUploadImage(int _idCar) {
			idCar = _idCar;
			arrURL = new ArrayList<String>();
			dialog2 = new Dialog(getActivity(), R.style.NewDialog);
			dialog2.setContentView(R.layout.loading);

			dialog2.setCancelable(false);
			dialog2.findViewById(R.id.loading_icon).setBackgroundResource(
					R.drawable.spin_animation);
			frameAnimation2 = (AnimationDrawable) dialog2.findViewById(
					R.id.loading_icon).getBackground();
			dialog2.show();
			frameAnimation2.start();
		}

		@Override
		protected Void doInBackground(Object... params) {
			// String[] arr = (String[]) params[0];
			@SuppressWarnings("unchecked")
			ArrayList<String> arr = (ArrayList<String>) params[0];
			// String pathImg = arr[0];
			// String urlPath = arr[1];
			int i = 0;

			String strname = "hinh_";
			for (String strarr : arr) {
				File file = new File(strarr);
				String name = file.getName();
				Bitmap bm = BitmapFactory.decodeFile(strarr);
				nameImage = ImageUtils.getInstance().uploadFile(strname + i++,
						bm, Constants.URL_SERVICE + Constants.SV_IMAGES, name);
				arrURL.add(nameImage);

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// dialog.dismiss();
			dialog2.dismiss();
			frameAnimation2.stop();
			if (!nameImage.equalsIgnoreCase("")) {
				// Image img = new Image();
				// img.setIdCar(idCar);
				// img.setName(nameImage);
				// img.setUrl(nameImage);
				//
				new VerySlowTaskUploadImages(idCar, arrURL).execute();

			}
		}

	}

	/*
	 * private class TaskUploadImage extends AsyncTask<Void, Void, Void> {
	 * 
	 * @Override protected void onPostExecute(Void result) { //
	 * dialog.dismiss(); }
	 * 
	 * @Override protected Void doInBackground(Void... params) { try {
	 * ByteArrayOutputStream bos = new ByteArrayOutputStream();
	 * //bm.compress(CompressFormat.PNG, 50, bos);
	 * 
	 * byte[] data = bos.toByteArray(); HttpClient httpClient = new
	 * DefaultHttpClient(); HttpPost postRequest = new
	 * HttpPost("http://192.168.0.100:8080/RestService_SFC/images");
	 * 
	 * ByteArrayBody bab = new ByteArrayBody(data, "FinalM8.JPG");
	 * MultipartEntity reqEntity = new MultipartEntity(
	 * HttpMultipartMode.BROWSER_COMPATIBLE); reqEntity.addPart("image", bab);
	 * postRequest.setEntity(reqEntity); HttpResponse response =
	 * httpClient.execute(postRequest);
	 * System.out.println("Status is "+response.getStatusLine()); BufferedReader
	 * reader = new BufferedReader(new InputStreamReader(
	 * response.getEntity().getContent(), "UTF-8")); String sResponse;
	 * StringBuilder s = new StringBuilder(); while ((sResponse =
	 * reader.readLine()) != null) { s = s.append(sResponse); }
	 * System.out.println("Response: " + s); } catch (Exception e) { // handle
	 * exception here e.printStackTrace(); } return null; }
	 * 
	 * }
	 */

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	protected void removeItemFromList() {
		PopupMenu popup = new PopupMenu(getActivity(), lvPhoto);
		popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
		popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.Update:

					break;
				case R.id.Delete:

					break;

				default:
					break;
				}
				return false;
			}
		});
		popup.show();

	}

	private class VerySlowTaskAddDetailCar extends AsyncTask<Void, Void, Void> {
		DetailCar dto;
		String output = "";

		@SuppressWarnings("unused")
		public VerySlowTaskAddDetailCar(DetailCar _dto) {
			dto = _dto;
			dialog = new Dialog(getActivity(), R.style.NewDialog);
			dialog.setContentView(R.layout.loading);

			dialog.setCancelable(false);
			dialog.findViewById(R.id.loading_icon).setBackgroundResource(
					R.drawable.spin_animation);
			frameAnimation = (AnimationDrawable) dialog.findViewById(
					R.id.loading_icon).getBackground();
			dialog.show();
			frameAnimation.start();
		}

		@Override
		protected Void doInBackground(Void... params) {
			output = DetailCarService.getInstance().insertDetailCar(dto);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			frameAnimation.stop();
			int idDetail = Integer.parseInt(output);
			if (idDetail == 0) {

			} else {
				AddModelCar(idDetail);
			}
		}

	}

	@SuppressWarnings("unused")
	private class VerySlowTaskAddProduce extends AsyncTask<Void, Void, Void> {
		String output = "";
		Car car;

		public VerySlowTaskAddProduce(Car _car) {
			car = _car;
			dialog1 = new Dialog(getActivity(), R.style.NewDialog);
			dialog1.setContentView(R.layout.loading);

			dialog1.setCancelable(false);
			dialog1.findViewById(R.id.loading_icon).setBackgroundResource(
					R.drawable.spin_animation);
			frameAnimation1 = (AnimationDrawable) dialog1.findViewById(
					R.id.loading_icon).getBackground();
			dialog1.show();
			frameAnimation1.start();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			output = CarService.getInstance().insertCar(car);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog1.dismiss();
			frameAnimation1.stop();
			int idCar = Integer.parseInt(output);
			if (idCar != 0) {
				AddImages(idCar);
			}
		}

	}

	private class VerySlowTaskUploadImages extends AsyncTask<Void, Void, Void> {
		Image dto;
		int idCar;
		ArrayList<String> arr;
		String output = "";

		public VerySlowTaskUploadImages(int _idCar, ArrayList<String> _arr) {
			idCar = _idCar;
			arr = _arr;
			dto = new Image();
			dto.setIdCar(idCar);
			dialog3 = new Dialog(getActivity(), R.style.NewDialog);
			dialog3.setContentView(R.layout.loading);

			dialog3.setCancelable(false);
			dialog3.findViewById(R.id.loading_icon).setBackgroundResource(
					R.drawable.spin_animation);
			frameAnimation3 = (AnimationDrawable) dialog3.findViewById(
					R.id.loading_icon).getBackground();
			dialog3.show();
			frameAnimation3.start();
		}

		@Override
		protected Void doInBackground(Void... params) {

			for (String str : arr) {
				dto.setName(str);
				dto.setUrl(str);
				output = ImageService.getInstance().insertImagesCar(dto);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog3.dismiss();
			frameAnimation3.stop();
		}

	}
}
