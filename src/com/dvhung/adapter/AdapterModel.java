package com.dvhung.adapter;

import java.util.ArrayList;

import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.search_for_car.R;

public class AdapterModel extends AbstractWheelTextAdapter {

	
	
	ArrayList<String> arrModel;

	// An object of this class must be initialized with an array of Date
	// type
	public AdapterModel(Context context, ArrayList<String> arrData) {
		// Pass the context and the custom layout for the text to the super
		// method
		super(context, R.layout.item_model);
		this.arrModel = arrData;
	}

	@Override
	public View getItem(int index, View cachedView, ViewGroup parent) {
		View view = super.getItem(index, cachedView, parent);
		TextView txtModel = (TextView) view.findViewById(R.id.txtModel);

		// Format the date (Name of the day / number of the day)

		// Assign the text
		txtModel.setText(arrModel.get(index));

		if (index == 0) {
			// If it is the first date of the array, set the color blue

			txtModel.setTextColor(0xFF111111);
		} else {
			// If not set the color to black
			txtModel.setTextColor(0xFF111111);
		}

		return view;
	}

	@Override
	public int getItemsCount() {
		return arrModel.size();
	}

	@Override
	protected CharSequence getItemText(int index) {
		return "";
	}

}
