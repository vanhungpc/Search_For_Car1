package com.dvhung.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dvhung.dto.ModelDTO;
import com.example.search_for_car.R;

public class ModelAdapter extends ArrayAdapter<Object> {
	Context mContext;
	private ArrayList<Object> arrObject;

	public ModelAdapter(Context context, int resource, ArrayList<Object> arr) {
		super(context, resource, arr);
		mContext = context;
		arrObject = arr;
	}

	class viewHolder {
		TextView txtChooseProduce;
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

		View rowView;
		rowView = inflater.inflate(R.layout.item_produce, parent, false);

		viewHolder holder = new viewHolder();

		// holder.icon = (ImageView) rowView.findViewById(R.id.icon);
		holder.txtChooseProduce = (TextView) rowView
				.findViewById(R.id.txtChooseProduce);

		ModelDTO dto = (ModelDTO) arrObject.get(position);
		// holder.icon.setImageResource(dto.getImg());
		holder.txtChooseProduce.setText(dto.getNameModel());

		return rowView;
	}

}
