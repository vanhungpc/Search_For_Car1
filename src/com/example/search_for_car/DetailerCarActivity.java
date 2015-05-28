package com.example.search_for_car;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dvhung.fragment.DetailerFragment;
import com.dvhung.fragment.MenuFragment;
import com.layout.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.layout.slidingmenu.lib.app.SlidingFragmentActivity;
import com.pkt.rest.client.pojo.Car;
import com.pkt.rest.client.pojo.Image;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DetailerCarActivity extends SlidingFragmentActivity implements OnClickListener {
	Button activity_main_content_button_menu;
	private Fragment mContent;
	private CanvasTransformer mTransformer;
	private Car objCar;
//	public DetailerCarActivity(CanvasTransformer transformer) {
//		super();
//		mTransformer = transformer;
//	}
	ArrayList<Object> arrData;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_frame);
		arrData = new ArrayList<Object>();
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		} else {
			mContent = new DetailerFragment();

		}
		Bundle data = getIntent().getExtras();
		objCar = (Car)data.getSerializable("objCar");
		List<Image> lstImage = objCar.getArrImage();
		for(Image im: lstImage){
			Image img = new Image();
			img.setIdCar(im.getIdCar());
			img.setIdImg(im.getIdImg());
			img.setName(im.getName());
			img.setUrl(im.getUrl());
			arrData.add(img);
		}
		setBehindContentView(R.layout.activity_menu);
		
		
		getSupportFragmentManager().beginTransaction()
				.add(R.id.content_frame, mContent).commit();

		getSlidingMenu().setBehindOffset(160);
		getSlidingMenu().setFadeDegree(0.35f);
		getSlidingMenu().setBehindScrollScale(0.0f);
		getSlidingMenu().setBehindCanvasTransformer(mTransformer);

		getSupportFragmentManager().beginTransaction()
				.add(R.id.menu_frame, new MenuFragment()).commit();
		activity_main_content_button_menu = (Button) findViewById(R.id.activity_main_content_button_menu);
		activity_main_content_button_menu.setOnClickListener(this);
		
	}
	public Car getDetail(){
		return objCar;
	}
	public ArrayList<Object> arrCar(){
		return arrData;
	}

/*	public void switchContent(Fragment fragment) {
		mContent = fragment;

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		if (fragment != null) {
			ft.remove(fragment).replace(R.id.content_frame, fragment);
			ft.commit();
		}
		getSlidingMenu().showContent();
	}*/
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		
		if (fragment != null) {
			ft.remove(fragment).replace(R.id.content_frame, fragment);
			ft.commit();
		}
		getSlidingMenu().showContent();
	}
	@Override
	public void onClick(View v) {
		int id = v.getId();

		switch (id) {
		case R.id.activity_main_content_button_menu:
			toggle();
			break;

		default:
			break;
		}

		
	}

}
