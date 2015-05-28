package com.dvhung.adapter;

import java.util.ArrayList;

import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.search_for_car.R;

public class AdapterMake extends AbstractWheelTextAdapter {

	ArrayList<String> arrMake;

	// An object of this class must be initialized with an array of Date
	// type
	public AdapterMake(Context context, ArrayList<String> arrData) {
		// Pass the context and the custom layout for the text to the super
		// method
		super(context, R.layout.item_make);
		this.arrMake = arrData;
	}

	@Override
	public View getItem(int index, View cachedView, ViewGroup parent) {
		View view = super.getItem(index, cachedView, parent);
		TextView txtMake = (TextView) view.findViewById(R.id.txtMake);

		// Format the date (Name of the day / number of the day)

		// Assign the text
		txtMake.setText(arrMake.get(index));

		if (index == 0) {
			// If it is the first date of the array, set the color blue
			txtMake.setTextColor(0xFF111111);
//			weekday.setTextColor(0xFF0000F0);
		} else {
			// If not set the color to black
			txtMake.setTextColor(0xFF111111);
		}

		return view;
	}

	@Override
	public int getItemsCount() {
		return arrMake.size();
	}

	@Override
	protected CharSequence getItemText(int index) {
		return "";
	}

}
