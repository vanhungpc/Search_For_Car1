package com.example.search_for_car;

import java.util.ArrayList;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;

import com.dvhung.adapter.AdapterMake;
import com.dvhung.adapter.AdapterModel;
import com.dvhung.model.utils.ProducerModel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class ModelSearchActivity extends Activity implements OnClickListener {
	ArrayList<String> arrMake;
	ArrayList<String> arrModel;
	private boolean scrolling = false;
	ProducerModel pro;
	WheelView npMake, npModel;
	Button btnBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_modelnew);
		
		pro  = ProducerModel.getInstance();
		arrMake = pro.getAllProducer(this);

		npMake = (WheelView) findViewById(R.id.npMake);
		npModel = (WheelView) findViewById(R.id.npModel);
		npMake.setViewAdapter(new AdapterMake(this, arrMake));

		int id = 1;
		arrModel = pro.getModelById(ModelSearchActivity.this, id);
		npModel.setViewAdapter(new AdapterModel(ModelSearchActivity.this,
				arrModel));
		npModel.invalidate();
		npModel.setCurrentItem(0);

		npMake.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!scrolling) {
					int id = newValue + 1;
					arrModel = pro.getModelById(ModelSearchActivity.this, id);
					npModel.setViewAdapter(new AdapterModel(
							ModelSearchActivity.this, arrModel));
					npModel.invalidate();
					npModel.setCurrentItem(0);
				}
			}
		});
		npMake.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {
				scrolling = true;

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				scrolling = false;
				int id = npMake.getCurrentItem() + 1;
				arrModel = pro.getModelById(ModelSearchActivity.this, id);
				npModel.setViewAdapter(new AdapterModel(
						ModelSearchActivity.this, arrModel));
				npModel.invalidate();
				npModel.setCurrentItem(0);
			}
		});
		btnBack = (Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(this);

		// ArrayWheelAdapter<String> adapter = new
		// ArrayWheelAdapter<String>(this,
		// arr1);
		// adapter.setTextSize(20);
		//
		// np.setViewAdapter(adapter);
		// np.setCurrentItem(arr1.length / 2);

		// np.setMaxValue(arr1.length - 1);
		// np.setMinValue(0);
		// np.setDisplayedValues(arr1);
		// np.setFocusable(true);
		// np.setFocusableInTouchMode(true);
		// np.setPadding(5, 0, 5, 0);
		//
		// // final NumberPicker np1 = (NumberPicker)
		// // findViewById(R.id.numberPicker1);
		// // np1.setMaxValue(6);
		// // np1.setMinValue(0);
		// final String[] t1 = { "Accord", "CR-V", "CR-Z", "Civic", "Element",
		// "Crosstour", "Accord \rCrosstour" };
		// final String[] t2 = { "Accord1", "CR-V1", "CR-Z1", "Civic1",
		// "Element1", "Crosstour1", "Accord \rCrosstour1" };
		// final NumberPicker np1 = (NumberPicker)
		// findViewById(R.id.numberPicker1);
		//
		// np.setOnValueChangedListener(new OnValueChangeListener() {
		//
		// @Override
		// public void onValueChange(NumberPicker picker, int oldVal,
		// int newVal) {
		// int id = np.getValue() + 1;
		// arrnp1 = pro.getModelById(ModelSearchActivity.this, id);
		//
		// strnp1 = new String[arrnp1.size()];
		// strnp1 = arrnp1.toArray(strnp1);
		// int maxx = strnp1.length;
		// int max = np1.getMaxValue();
		// if (maxx > max) {
		//
		// np1.setMinValue(0);
		// np1.setValue(0);
		// np1.setDisplayedValues(strnp1);
		// np1.setMaxValue(strnp1.length - 1);
		// np1.invalidate();
		//
		// // np1.setFocusable(true);
		// // np1.setFocusableInTouchMode(true);
		// } else {
		// np1.setMinValue(0);
		// np1.setValue(0);
		//
		// np1.setMaxValue(strnp1.length - 1);
		// np1.setDisplayedValues(strnp1);
		// np1.invalidate();
		// }
		//
		// }
		// });

		// NumberPicker np2 = (NumberPicker) findViewById(R.id.numberPicker2);
		// np2.setMaxValue(10);
		// np2.setMinValue(0);
		// np2.setDisplayedValues(new String[] { "2004", "2005", "2006", "2007",
		// "2008", "2009", "2010", "2011", "2012", "2013", "2014" });
		// np2.setFocusable(true);
		// np2.setFocusableInTouchMode(true);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnBack:
			finish();
			break;

		default:
			break;
		}
		
	}

}
