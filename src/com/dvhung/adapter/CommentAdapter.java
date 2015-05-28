package com.dvhung.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.search_for_car.R;
import com.pkt.rest.client.pojo.Comment;

public class CommentAdapter extends ArrayAdapter<Object> {
	Context mContext;
	private ArrayList<Object> arrObject;

	public CommentAdapter(Context context, int resource, ArrayList<Object> arr) {
		super(context, resource, arr);
		mContext = context;
		arrObject = arr;
	}

	class viewHolder {
		TextView txtUseName, txtComment;
		ImageView imgAvatar;
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

		View rowView = inflater.inflate(R.layout.adapter_comment, parent, false);

		viewHolder holder = new viewHolder();

		// holder.icon = (ImageView) rowView.findViewById(R.id.icon);
		holder.txtUseName = (TextView) rowView.findViewById(R.id.txtUseName);
		holder.txtComment = (TextView) rowView.findViewById(R.id.txtComment);
		holder.imgAvatar = (ImageView) rowView.findViewById(R.id.imgAvatar);
		Comment comment = (Comment) arrObject.get(position);

		// holder.icon.setImageResource(dto.getImg());
		holder.txtComment.setText(comment.getContentComment());
		holder.txtUseName.setText(comment.getUserName());

		return rowView;
	}

}
