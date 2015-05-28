package com.example.search_for_car;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.dvhung.adapter.ProduceAdapter;
import com.dvhung.common.ActionEvent;
import com.dvhung.constants.Constants;
import com.dvhung.controller.ProduceController;
import com.dvhung.dto.ProducerDTO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ProduceActivity extends Activity implements OnItemClickListener {

	ProduceAdapter adapter;
	ArrayList<Object> arrData;
	ListView lvProduce;
	EditText edSearchName;
	ArrayList<ProducerDTO> newData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_produce);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		lvProduce = (ListView) findViewById(R.id.lvProduce);
		arrData = new ArrayList<Object>();
		adapter = new ProduceAdapter(ProduceActivity.this,
				R.layout.item_produce, arrData);
		lvProduce.setAdapter(adapter);
		initData();
		lvProduce.setOnItemClickListener(this);
		edSearchName = (EditText) findViewById(R.id.edSearchName);
		edSearchName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!newData.contains(null)) {
					String text = edSearchName.getText().toString()
							.toLowerCase(Locale.getDefault());
					filter(text);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		
	}
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		arrData.clear();
		if (charText.length() == 0) {
			arrData.addAll(newData);
			adapter.notifyDataSetChanged();
		} else {
			for (ProducerDTO pro : newData) {
				if (pro.getNameProducer().toLowerCase(Locale.getDefault())
						.contains(charText)) {
					arrData.add(pro);
					adapter.notifyDataSetChanged();
				}
			}
		}

	}

	public void initData() {
		ActionEvent e = new ActionEvent(this, Constants.GET_ALL_PRODUCE, null,
				null);
		ProduceController.getInstance().handleViewEvent(e);
	}

	public void handleControllerViewEvent(ActionEvent e) {
		switch (e.action) {
		case Constants.GET_ALL_PRODUCE:
			@SuppressWarnings("unchecked")
			
			ArrayList<ProducerDTO> newArr = (ArrayList<ProducerDTO>) e.viewData;
			newData = newArr;
			
			new VerySlowTask(newArr).execute();
			break;
		default:
			break;
		}
	}

	public class VerySlowTask extends AsyncTask<String, Object, Void> {

		List<ProducerDTO> _newArr;

		public VerySlowTask(ArrayList<ProducerDTO> arr) {
			_newArr = arr;
			
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				for (int i = 0; i < _newArr.size(); i++) {
					publishProgress(_newArr.get(i));

				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Object... values) {
			arrData.add(values[0]);
			
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent();
		ProducerDTO pro = (ProducerDTO) lvProduce.getItemAtPosition(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable("myobj", pro);
		intent.putExtras(bundle);
		setResult(Activity.RESULT_OK, intent);
		finish();

	}
}
