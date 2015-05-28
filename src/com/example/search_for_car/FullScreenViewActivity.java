package com.example.search_for_car;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.dvhung.adapter.FullScreenImageAdapter;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class FullScreenViewActivity extends Activity {
	private FullScreenImageAdapter adapter;
	private ViewPager viewPager;
	ArrayList<String> urlImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_view);

		viewPager = (ViewPager) findViewById(R.id.pager);
		initImageLoader(getApplicationContext());
		Bundle data = getIntent().getExtras();
//		Image dto = (Image) data.getSerializable("objImg");
	
		@SuppressWarnings("unchecked")
		ArrayList<String> urlImg = (ArrayList<String>) data
				.getSerializable("arrImg");
	
		// AnimalImagesModel model = AnimalImagesModel.getInstance();
		// ArrayList<String> urlImg = model.GallaryById(
		// FullScreenViewActivity.this, dto.getAnimalId());
		//

		adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,
				urlImg);

		viewPager.setAdapter(adapter);
		int position = data.getInt("position");
		// displaying selected image first
		viewPager.setCurrentItem(position);
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
}
