package com.dvhung.adapter;

import java.util.ArrayList;

import com.dvhung.dto.DetailerList;
import com.example.search_for_car.R;
import com.example.search_for_car.R.color;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListviewAdapter extends ArrayAdapter<Object> {
	Context mContext;
	private ArrayList<Object> arrObject;

	public ListviewAdapter(Context context, int resource, ArrayList<Object> arr) {
		super(context, resource, arr);
		mContext = context;
		arrObject = arr;
	}

	class viewHolder {
		LinearLayout lnList;
		ImageView icon;
		TextView title;
		TextView count;
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
		rowView = inflater.inflate(R.layout.activity_list_item, parent, false);
//		if(position == 0 || position == 1 || position == 2 || position == 3 || position == 5){
//			rowView.setBackgroundColor(color.list);
//		}else if(position == 4){
//			rowView.setBackgroundColor(color.list_background);
//		}
//		else{
//			rowView.setBackgroundColor(color.list_background);  
//		}
//	
		viewHolder holder = new viewHolder();

//		holder.lnList = (LinearLayout) rowView.findViewById(R.id.lnList);
		holder.icon = (ImageView) rowView.findViewById(R.id.icon);
		holder.title = (TextView) rowView.findViewById(R.id.title);
		holder.count = (TextView) rowView.findViewById(R.id.counter);

		DetailerList dto = (DetailerList) arrObject.get(position);
		holder.icon.setImageResource(dto.getImg());
		holder.title.setText(dto.getTitle());
		holder.count.setText(dto.getCounter());

		return rowView;
	}

}
