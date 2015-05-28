package com.dvhung.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.dvhung.constants.Constants;
import com.dvhung.pageindicator.CirclePageIndicator;
import com.dvhung.pageindicator.PageIndicator;
import com.dvhung.utils.PicassoUtils;
import com.example.search_for_car.DetailerCarActivity;
import com.example.search_for_car.R;
import com.google.android.gms.internal.ac;
import com.pkt.rest.client.pojo.Car;
import com.pkt.rest.client.pojo.Image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailerFragment extends Fragment implements OnClickListener {
	Handler handler;
	public static final int DELAY = 5000;
	ViewPager mPager;
	PageIndicator mIndicator;
	ImageView imageView;
	public ArrayList<Object> mImages;
	ImagePagerAdapter adapter;
	Button btnPreview, btnNext;
	TextView txtMilliage, txtBodyStyle, txtDoors, txtExteriorColor, txtFuel,
			txtEngine, txtTransmission;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_detail, container,
				false);

		DetailerCarActivity activity = (DetailerCarActivity) getActivity();
		Car car = activity.getDetail();
		List<Image> lstImage = car.getArrImage();
		mImages = new ArrayList<Object>();
		for (Image img : lstImage) {
			mImages.add(img.getUrl());
		}
		mIndicator = (CirclePageIndicator) rootView
				.findViewById(R.id.indicator);

		mPager = (ViewPager) rootView.findViewById(R.id.pager);
		adapter = new ImagePagerAdapter();
		mPager.setAdapter(adapter);
		mIndicator.setViewPager(mPager);
		handler = new Handler();
		handler.removeCallbacks(incrementPage);
		handler.postDelayed(incrementPage, DELAY);
		btnPreview = (Button) rootView.findViewById(R.id.btnPreview);
		btnNext = (Button) rootView.findViewById(R.id.btnNext);
		txtMilliage = (TextView) rootView.findViewById(R.id.txtMilliage);
		txtBodyStyle = (TextView) rootView.findViewById(R.id.txtBodyStyle);
		txtDoors = (TextView) rootView.findViewById(R.id.txtDoors);
		txtExteriorColor = (TextView) rootView
				.findViewById(R.id.txtExteriorColor);
		txtFuel = (TextView) rootView.findViewById(R.id.txtFuel);
		txtEngine = (TextView) rootView.findViewById(R.id.txtEngine);
		txtTransmission = (TextView) rootView
				.findViewById(R.id.txtTransmission);

		txtMilliage.setText(car.getDetailCar().getMilliage());
		txtBodyStyle.setText(car.getDetailCar().getBodyType());
		txtDoors.setText(car.getDetailCar().getDoors());
		txtExteriorColor.setText(car.getDetailCar().getExteriorColor());
		txtFuel.setText(car.getDetailCar().getFuel());
		txtEngine.setText(car.getDetailCar().getEngine());
		txtTransmission.setText(car.getDetailCar().getTransmission());

		btnPreview.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		return rootView;
	}

	private Runnable incrementPage = new Runnable() {

		@Override
		public void run() {
			int currentItem = mPager.getCurrentItem();

			int maxItems = mPager.getAdapter().getCount();

			if (maxItems != 0) {

				mPager.setCurrentItem((currentItem + 1) % maxItems, true);

			} else {

				mPager.setCurrentItem(0, true);

			}

			handler.postDelayed(incrementPage, DELAY);

		}
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	}

	public class ImagePagerAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return mImages.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Context context = getActivity();
			imageView = new ImageView(context);
			// txtTextChange = new TextView(context);
			@SuppressWarnings("unused")
			int padding = context.getResources().getDimensionPixelSize(
					R.dimen.padding_medium);
			imageView.setPadding(0, 0, 0, 0);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

			String path = (String) mImages.get(position);
			String currentUrl = path;

			if (path != null) {
				String[] strs = path.split("/");
				File f = new File(Environment.getExternalStorageDirectory()
						+ "/" + Constants.APP_NAME + "/" + strs[0] + "/"
						+ strs[1]);
				if (f.exists()) {
					Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
					imageView.setImageBitmap(bmp);
				} else {
					PicassoUtils.getInstance().showImageFromUrl(
							DetailerFragment.this.getActivity(), currentUrl,
							R.drawable.arrowleft, imageView);
					PicassoUtils.getInstance().saveImageFromUrl(
							DetailerFragment.this.getActivity(), currentUrl,
							strs[1], strs[0]);
				}

				// TODO: create folder?

			}

			((ViewPager) container).addView(imageView, 0);
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((ImageView) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return (view == object);
		}

		/**
		 * Called when the a change in the shown pages has been completed. At
		 * this point you must ensure that all of the pages have actually been
		 * added or removed from the container as appropriate.
		 * 
		 * @param arg0
		 *            The containing View which is displaying this adapter's
		 *            page views.
		 */
		@Override
		public void finishUpdate(ViewGroup arg0) {
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(ViewGroup arg0) {
		}

	}

	@Override
	public void onClick(View v) {
		int currentItem = mPager.getCurrentItem();

		int maxItems = mPager.getAdapter().getCount();
		switch (v.getId()) {
		case R.id.btnNext:
			mPager.setCurrentItem((currentItem + 1) % maxItems, true);
			break;
		case R.id.btnPreview:
			mPager.setCurrentItem((currentItem - 1) % maxItems, true);
			break;

		default:
			break;
		}

	}
}
