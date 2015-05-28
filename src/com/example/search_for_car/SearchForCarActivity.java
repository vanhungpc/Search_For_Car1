package com.example.search_for_car;

import java.util.ArrayList;

import net.simonvt.numberpicker.NumberPicker;
import net.simonvt.numberpicker.NumberPicker.OnValueChangeListener;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;

import com.dvhung.adapter.AdapterMake;
import com.dvhung.adapter.AdapterModel;
import com.dvhung.model.utils.ProducerModel;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SearchForCarActivity extends Activity implements OnClickListener {
	ArrayList<String> arrMake;
	String[] strMake;
	ArrayList<String> arrModel;
	String[] strModel;

	LinearLayout layMakeAndModel, id1;
	Dialog dialogMakeAndModel;
	ProducerModel pro;
	NumberPicker npMake, npModel;
	ImageView icon;
	TextView titleMakeandModel,contentMakeandModel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_for_car);
		layMakeAndModel = (LinearLayout) findViewById(R.id.layMakeAndModel);
		id1 = (LinearLayout) findViewById(R.id.id1);
		layMakeAndModel.setOnClickListener(this);
		id1.setOnClickListener(this);
		
		icon = (ImageView)findViewById(R.id.icon);
		icon.setImageResource(R.drawable.doge);
		titleMakeandModel = (TextView)findViewById(R.id.titleMakeandModel);
		titleMakeandModel.setText("Make and Model");
		
		contentMakeandModel = (TextView)findViewById(R.id.contentMakeandModel);
		contentMakeandModel.setText("Acura-Intergra");

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.layMakeAndModel:
			dialogMakeAndModel = new Dialog(SearchForCarActivity.this,
					android.R.style.Theme_Translucent);
			dialogMakeAndModel.requestWindowFeature(Window.FEATURE_NO_TITLE);

			dialogMakeAndModel.setCancelable(true);
			dialogMakeAndModel.setContentView(R.layout.dialog_picker);

			pro = ProducerModel.getInstance();
			arrMake = pro.getAllProducer(this);

			npMake = (NumberPicker) dialogMakeAndModel
					.findViewById(R.id.npMake);
			npModel = (NumberPicker) dialogMakeAndModel
					.findViewById(R.id.npModel);
			Button btnYes = (Button) dialogMakeAndModel
					.findViewById(R.id.btnYes);
			Button btnNo = (Button) dialogMakeAndModel.findViewById(R.id.btnNo);

			strMake = new String[arrMake.size()];
			strMake = arrMake.toArray(strMake);
			npMake.setMaxValue(strMake.length - 1);
			npMake.setMinValue(0);
			npMake.setDisplayedValues(strMake);
			npMake.setFocusable(true);
			npMake.setFocusableInTouchMode(true);

			npMake.setOnValueChangedListener(new OnValueChangeListener() {

				@Override
				public void onValueChange(NumberPicker picker, int oldVal,
						int newVal) {
					int id = npMake.getValue() + 1;
					arrModel = pro.getModelById(SearchForCarActivity.this, id);

					strModel = new String[arrModel.size()];
					strModel = arrModel.toArray(strModel);
					int maxx = strModel.length;
					int max = npModel.getMaxValue();
					if (maxx > max) {

						npModel.setMinValue(0);
						npModel.setValue(0);
						npModel.setDisplayedValues(strModel);
						npModel.setMaxValue(strModel.length - 1);
						npModel.invalidate();

						// np1.setFocusable(true);
						// np1.setFocusableInTouchMode(true);
					} else {
						npModel.setMinValue(0);
						npModel.setValue(0);

						npModel.setMaxValue(strModel.length - 1);
						npModel.setDisplayedValues(strModel);
						npModel.invalidate();
					}

				}
			});

			btnYes.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// String s = strMake + "-"+ strModel;
					// txtMakeAndModel.setText(s);
					dialogMakeAndModel.dismiss();

				}
			});
			btnNo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialogMakeAndModel.dismiss();

				}
			});
			dialogMakeAndModel.show();
			break;
		case R.id.id1:
			Toast.makeText(SearchForCarActivity.this, "id1", Toast.LENGTH_LONG).show();
			break;
			

		default:
			break;
		}

	}
}