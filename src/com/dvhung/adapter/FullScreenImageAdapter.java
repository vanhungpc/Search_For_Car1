package com.dvhung.adapter;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.dvhung.constants.Constants;
import com.dvhung.utils.PicassoUtils;
import com.dvhung.utils.TouchImageView;
import com.example.search_for_car.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class FullScreenImageAdapter extends PagerAdapter {
	private Activity _activity;
	private ArrayList<String> _imagePaths;
	private LayoutInflater inflater;
	DisplayImageOptions options;
	// constructor
	public FullScreenImageAdapter(Activity activity,
			ArrayList<String> imagePaths) {
		this._activity = activity;
		this._imagePaths = imagePaths;
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
		.cacheOnDisk(true).considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	@Override
	public int getCount() {
		return this._imagePaths.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		TouchImageView imgDisplay;
		Button btnClose;

		inflater = (LayoutInflater) _activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image,
				container, false);


		imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
		btnClose = (Button) viewLayout.findViewById(R.id.btnClose);

//		BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//		Bitmap bitmap = BitmapFactory.decodeFile(_imagePaths.get(position),
//				options);
//		imgDisplay.setImageBitmap(bitmap);
		String path = _imagePaths.get(position);
		path = path.replaceAll(" ", "%20");
		if(path != null){
			String[] strs = path.split("/");
			File f = new File(Environment.getExternalStorageDirectory() + "/"
					+ Constants.APP_NAME + "/" + strs[4] + "/" + strs[5]);
			String pathImg = "file://"+ f.getAbsolutePath();
			if (f.exists()) {
				ImageLoader.getInstance().displayImage(pathImg,
						imgDisplay, options,
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
//				Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
//				imgDisplay.setImageBitmap(bmp);
			} else {
				PicassoUtils.getInstance().showImageFromUrl(_activity, path,
						R.drawable.spin_animation, imgDisplay);
				// TODO: create folder?

				PicassoUtils.getInstance().saveImageFromUrl(
						_activity.getApplicationContext(), path, strs[5], strs[4]);
			}
		}

		// close button click event
		btnClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				_activity.finish();
			}
		});

		((ViewPager) container).addView(viewLayout);

		return viewLayout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((RelativeLayout) object);

	}

}
