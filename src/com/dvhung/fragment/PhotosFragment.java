package com.dvhung.fragment;

import java.util.ArrayList;
import java.util.List;

import com.dvhung.adapter.PhotosAdapter;
import com.example.search_for_car.DetailerCarActivity;
import com.example.search_for_car.FullScreenViewActivity;
import com.example.search_for_car.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.pkt.rest.client.pojo.Car;
import com.pkt.rest.client.pojo.Image;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class PhotosFragment extends Fragment implements OnItemClickListener {
	GridView girdImage;
	ArrayList<Object> arrData;
	PhotosAdapter adapter;
	ArrayList<String> arrImage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_photos, container,
				false);
		girdImage = (GridView) rootView.findViewById(R.id.girdImage);
		girdImage.setOnItemClickListener(this);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initImageLoader(getActivity().getApplicationContext());
		DetailerCarActivity activity = (DetailerCarActivity) getActivity();
		arrData = activity.arrCar();
		Car car = activity.getDetail();
		List<Image> lstImage = car.getArrImage();
		arrData = new ArrayList<Object>();
		arrImage = new ArrayList<String>();
		for (Image im : lstImage) {
			Image img = new Image();
			img.setIdCar(im.getIdCar());
			img.setIdImg(im.getIdImg());
			img.setName(im.getName());
			img.setUrl(im.getUrl());
			arrData.add(img);
			arrImage.add(img.getUrl());
		}
		adapter = new PhotosAdapter(getActivity(), R.layout.grid_row, arrData);
		girdImage.setAdapter(adapter);
	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	// public class VerySlowTask extends AsyncTask<String, Object, Void> {
	// List<AnimalImagesDTO> _newArr;
	//
	// public VerySlowTask(List<AnimalImagesDTO> arr) {
	// _newArr = arr;
	// }
	//
	// @Override
	// protected Void doInBackground(String... params) {
	// try {
	// for (int i = 0; i < _newArr.size(); i++) {
	// publishProgress(_newArr.get(i));
	//
	// }
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	//
	// return null;
	// }
	//
	// @Override
	// protected void onProgressUpdate(Object... values) {
	// arrData.add(values[0]);
	//
	// }
	//
	// @Override
	// protected void onPostExecute(Void result) {
	// // TODO Auto-generated method stub
	// super.onPostExecute(result);
	// adapter.notifyDataSetChanged();
	// }
	//
	// }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		try {
			Image img = (Image) girdImage.getItemAtPosition(position);
			Intent intent = new Intent(getActivity(),
					FullScreenViewActivity.class);
			Bundle data = new Bundle();
			data.putSerializable("objImg", img);
			data.putInt("position", position);
			data.putSerializable("arrImg", arrImage);
			intent.putExtras(data);
			startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
