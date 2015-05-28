package com.dvhung.fragment;

import java.util.ArrayList;

import com.dvhung.adapter.ListviewAdapter;
import com.dvhung.dto.DetailerList;
import com.example.search_for_car.DetailerCarActivity;
import com.example.search_for_car.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Switch;

public class MenuFragment extends ListFragment {

	ListView fragment_listview_listview;
	ListviewAdapter adapter;
	ArrayList<Object> arrData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.list, container, false);
		fragment_listview_listview = (ListView) rootView
				.findViewById(R.id.fragment_listview_listview);
		arrData = new ArrayList<Object>();

		adapter = new ListviewAdapter(getActivity(),
				R.id.fragment_listview_listview, arrData);
		setListAdapter(adapter);
		new Thread(new Runnable() {

			@Override
			public void run() {
				initData();

			}
		}).start();
		return rootView;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		FragmentManager fm = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment newContent = null;
		switch (position) {
		case 0:
			newContent = new DetailerFragment();
			break;
		case 1:
			newContent = new CommentFragment();
			break;
		case 2:
			newContent = new PhotosFragment();
			break;
		case 3:
			newContent = new SpecsFragment();
			// ft.addToBackStack(null);
			break;
		case 4:
			break;
		case 5:
			newContent = new NavigationFragment();
			break;
		default:
			break;
		}
		if (newContent != null) {
			
			switchFragment(newContent);
		}
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	}

	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		if (getActivity() instanceof DetailerCarActivity) {
			DetailerCarActivity fchang = (DetailerCarActivity) getActivity();
			fchang.switchContent(fragment);
		}
	}

	String arr[] = { "Detail", "comment", "Photo", "Specs", "", "Navigation"};
	int[] img = { R.drawable.ic_detail, R.drawable.ic_comment,
			R.drawable.ic_photos, R.drawable.ic_specs,0,
			R.drawable.ic_navigation};

	private void initData() {
		for (int i = 0; i < arr.length; i++) {
			DetailerList dto = new DetailerList();
			dto.setImg(img[i]);
			dto.setTitle(arr[i]);
			arrData.add(dto);
		}
	}
	
	
}
