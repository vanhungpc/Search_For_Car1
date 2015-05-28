package com.dvhung.fragment;

import java.util.ArrayList;
import java.util.List;

import com.dvhung.adapter.CommentAdapter;
import com.dvhung.dto.LoginDTO;
import com.dvhung.model.utils.LoginModel;
import com.example.search_for_car.DetailerCarActivity;
import com.example.search_for_car.R;
import com.pkt.rest.client.pojo.Car;
import com.pkt.rest.client.pojo.Comment;
import com.pkt.rest.client.services.AccountService;
import com.pkt.rest.client.services.CommentService;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CommentFragment extends Fragment implements OnClickListener {
	TextView txtUseName;
	EditText edComment;
	ImageView imgAvatar;
	ListView lvComment;
	CommentAdapter adapter;
	ArrayList<Object> arrData;
	String contentComment = "";
	Button btnSendComment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_comment, container,
				false);
		txtUseName = (TextView) rootView.findViewById(R.id.txtUseName);
		edComment = (EditText) rootView.findViewById(R.id.edComment);
		imgAvatar = (ImageView) rootView.findViewById(R.id.imgAvatar);
		lvComment = (ListView) rootView.findViewById(R.id.lvComment);

		btnSendComment = (Button) rootView.findViewById(R.id.btnSendComment);
		btnSendComment.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		DetailerCarActivity activity = (DetailerCarActivity) getActivity();
		Car car = activity.getDetail();
		List<Comment> lstComment = car.getArrComment();
		arrData = new ArrayList<Object>();
		if (lstComment != null) {

			for (Comment cm : lstComment) {
				Comment comment = new Comment();
				comment.setUserName(cm.getUserName());
				comment.setContentComment(cm.getContentComment());
				arrData.add(comment);
			}
			adapter = new CommentAdapter(getActivity(),
					R.layout.adapter_comment, arrData);
			lvComment.setAdapter(adapter);
		}
	}

	public void postComment() {
		LoginDTO login = LoginModel.getInstance().getUser(getActivity());
		DetailerCarActivity activity = (DetailerCarActivity) getActivity();
		Car car = activity.getDetail();

		Comment comment = new Comment();
		contentComment = edComment.getText().toString();
		comment.setIdAcc(login.getId());
		comment.setIdCar(car.getIdCar());
		comment.setContentComment(contentComment);
		comment.setUserName(login.getUserName());
		new VerySlowTaskPostComment(comment).execute();
	}

	private class VerySlowTaskPostComment extends AsyncTask<Void, Void, Void> {
		Comment comment;
		String result1 = "";

		public VerySlowTaskPostComment(Comment _comment) {
			comment = _comment;
		}

		@Override
		protected Void doInBackground(Void... params) {
			result1 = CommentService.getInstance()
					.createCommentAndroid(comment);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result1.equalsIgnoreCase("OK")) {
				arrData.add(comment);
				adapter = new CommentAdapter(getActivity(),
						R.layout.adapter_comment, arrData);
				lvComment.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSendComment:
			postComment();
			break;

		default:
			break;
		}

	}

}
