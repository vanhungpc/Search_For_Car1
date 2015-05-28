package com.dvhung.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.dvhung.constants.Constants;
import com.dvhung.utils.PicassoUtils;
import com.example.search_for_car.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.pkt.rest.client.pojo.Car;
import com.pkt.rest.client.pojo.Image;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class PhotosAdapter extends ArrayAdapter<Object> {
	Context mContext;
	private ArrayList<Object> arrObject;
	DisplayImageOptions options;

	public PhotosAdapter(Context context, int resource,
			ArrayList<Object> arr) {
		super(context, resource, arr);
		mContext = context;
		arrObject = arr;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrObject.get(position);
	}

	class viewHolder {
		ImageView picture;
		TextView txtImageName;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrObject.size();
	}

	


	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView;
		if(convertView == null){
			rowView = inflater.inflate(R.layout.grid_row, parent, false);
		}else{
			rowView = convertView;
		}
		

		viewHolder holder = new viewHolder();

		// holder.icon = (ImageView) rowView.findViewById(R.id.icon);
		holder.picture = (ImageView) rowView.findViewById(R.id.picture);
		for(int i = 0; i < arrObject.size(); i++){
			Image img = (Image)arrObject.get(i);

					String path = img.getUrl();
					path = path.replaceAll(" ", "%20");
					if (path != null) {
						String[] strs = path.split("/");
						File f = new File(Environment.getExternalStorageDirectory() + "/"
								+ Constants.APP_NAME + "/" + strs[4] + "/" + strs[5]);
						String pathImg = "file://" + f.getAbsolutePath();
						if (f.exists()) {
							ImageLoader.getInstance().displayImage(pathImg,
									holder.picture, options,
									new SimpleImageLoadingListener() {
										@Override
										public void onLoadingStarted(String imageUri,
												View view) {

										}

										@Override
										public void onLoadingFailed(String imageUri,
												View view, FailReason failReason) {

										}

										@Override
										public void onLoadingComplete(String imageUri,
												View view, Bitmap loadedImage) {

										}
									}, new ImageLoadingProgressListener() {
										@Override
										public void onProgressUpdate(String imageUri,
												View view, int current, int total) {

										}
									});
							// Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
							// holder.imageAnimal.setImageBitmap(bmp);
						} else {
							PicassoUtils.getInstance().showImageFromUrl(mContext, path,
									R.drawable.spin_animation, holder.picture);
							// TODO: create folder?

							PicassoUtils.getInstance().saveImageFromUrl(
									mContext.getApplicationContext(), path, strs[5],
									strs[4]);
						}
					}
		}
		

		return rowView;
	}

}
