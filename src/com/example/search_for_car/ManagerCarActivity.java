package com.example.search_for_car;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dvhung.fragment.FragmentTab;
import com.dvhung.fragment.MenuFragmentManagerCar;
import com.layout.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.layout.slidingmenu.lib.app.SlidingFragmentActivity;

public class ManagerCarActivity extends SlidingFragmentActivity implements
		OnClickListener {
	Button activity_main_content_button_menu;
	private Fragment mContent;
	private CanvasTransformer mTransformer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_frame);
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		} else {
			mContent = new FragmentTab();

		}
		setBehindContentView(R.layout.activity_menu);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.content_frame, mContent).commit();
		getSlidingMenu().setBehindOffset(160);
		getSlidingMenu().setFadeDegree(0.35f);
		getSlidingMenu().setBehindScrollScale(0.0f);
		getSlidingMenu().setBehindCanvasTransformer(mTransformer);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.menu_frame, new MenuFragmentManagerCar()).commit();
		activity_main_content_button_menu = (Button) findViewById(R.id.activity_main_content_button_menu);
		activity_main_content_button_menu.setOnClickListener(this);
	}

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
