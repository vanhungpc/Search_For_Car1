package com.example.search_for_car;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.dvhung.adapter.ModelAdapter;
import com.dvhung.common.ActionEvent;
import com.dvhung.constants.Constants;
import com.dvhung.controller.ModelController;
import com.dvhung.dto.ModelDTO;
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
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ModelActivity extends Activity implements OnItemClickListener {
	ListView lvModel;
	ArrayList<Object> arrData;
	ModelAdapter adapter;
	ArrayList<ModelDTO> newData;
	EditText edSearchName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_model);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		lvModel = (ListView) findViewById(R.id.lvModel);
		Bundle data = getIntent().getExtras();
		int idProduce = data.getInt("idProduce");
		Bundle bundle = new Bundle();
		bundle.putInt("key_model", idProduce);
		initData(bundle);
		arrData = new ArrayList<Object>();
		adapter = new ModelAdapter(ModelActivity.this, R.layout.item_produce,
				arrData);

		lvModel.setAdapter(adapter);
		lvModel.setOnItemClickListener(this);
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
			for (ModelDTO model : newData) {
				if (model.getNameModel().toLowerCase(Locale.getDefault())
						.contains(charText)) {
					arrData.add(model);
					adapter.notifyDataSetChanged();
				}
			}
		}

	}

	public void initData(Bundle data) {
		ActionEvent e = new ActionEvent(this,
				Constants.GET_MODEL_BY_ID_PRODUCE, null, data);
		ModelController.getInstance().handleViewEvent(e);
	}

	public void handleControllerViewEvent(ActionEvent e) {
		switch (e.action) {
		case Constants.GET_MODEL_BY_ID_PRODUCE:
			@SuppressWarnings("unchecked")
			ArrayList<ModelDTO> newArr = (ArrayList<ModelDTO>) e.viewData;
			newData = newArr;
			new VerySlowTask(newArr).execute();
			break;
		default:
			break;
		}
	}

	public class VerySlowTask extends AsyncTask<String, Object, Void> {

		List<ModelDTO> _newArr;

		public VerySlowTask(ArrayList<ModelDTO> arr) {
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
		ModelDTO model = (ModelDTO) lvModel.getItemAtPosition(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable("objModel", model);
		intent.putExtras(bundle);
		setResult(Activity.RESULT_OK, intent);
		finish();

	}
}
