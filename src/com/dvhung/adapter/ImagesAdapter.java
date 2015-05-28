package com.dvhung.adapter;

import java.util.ArrayList;

import com.dvhung.dto.ImageDTO;
import com.example.search_for_car.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class ImagesAdapter extends ArrayAdapter<Object> {

	Context mContext;
	private ArrayList<Object> arrObject;

	public ImagesAdapter(Context context, int resource,
			ArrayList<Object> arrData) {
		super(context, resource, arrData);
		mContext = context;
		arrObject = arrData;

	}

	class viewHolder {
		ImageView imageCar;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrObject.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrObject.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.adapter_insert_cart, parent,
				false);

		viewHolder holder = new viewHolder();
		holder.imageCar = (ImageView) rowView.findViewById(R.id.imageCar);
		ImageDTO dto = (ImageDTO) arrObject.get(position);
		holder.imageCar.setImageBitmap(dto.getImgBitmap());
		return rowView;
		
	}
}