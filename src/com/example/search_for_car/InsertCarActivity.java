package com.example.search_for_car;


import com.dvhung.fragment.FragmentTab;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class InsertCarActivity extends FragmentActivity{
	
	private Fragment mContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		} else {
			mContent = new FragmentTab();

		}
		setContentView(R.layout.activity_insert_car);
		getSupportFragmentManager().beginTransaction()
		.add(R.id.fl_about, mContent).commit();
		
		
		

	}
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.btnBack:
//			finish();
//			break;
//
//		default:
//			break;
//		}
//		
//	}


}
