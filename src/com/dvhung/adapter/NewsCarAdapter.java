package com.dvhung.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.dvhung.constants.Constants;
import com.dvhung.dto.NewsCarDTO;
import com.dvhung.utils.PicassoUtils;
import com.example.search_for_car.R;
import com.example.search_for_car.R.color;
import com.pkt.rest.client.pojo.Car;
import com.pkt.rest.client.pojo.Image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


@SuppressLint("ViewHolder")
public class NewsCarAdapter extends ArrayAdapter<Car> {

	Context mContext;
	private ArrayList<Car> arrObject;
	

	public NewsCarAdapter(Context context, int resource, ArrayList<Car> arrData) {
		super(context, resource, arrData);
		mContext = context;
		setArrObject(arrData);

	}

	class viewHolder {
		ImageView imgNews;
		TextView txtNameCar;
		TextView txtPrice;
		TextView txtMiliage;
		TextView txtColor;
	
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return getArrObject().size();
	}

	@Override
	public Car getItem(int position) {
		// TODO Auto-generated method stub
		return getArrObject().get(position);
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
		View rowView = inflater.inflate(R.layout.adapter_news_car, parent, false);

		viewHolder holder = new viewHolder();
		holder.imgNews = (ImageView) rowView.findViewById(R.id.ivnewscar);
		holder.txtNameCar = (TextView) rowView
				.findViewById(R.id.txtNameCar);
		holder.txtPrice = (TextView) rowView
				.findViewById(R.id.txtPrice);
		holder.txtMiliage = (TextView) rowView
				.findViewById(R.id.txtMiliage);
		holder.txtColor = (TextView) rowView
				.findViewById(R.id.txtColor);
//		holder.lnContentCar = (LinearLayout)rowView.findViewById(R.id.lnContentCar);
//		holder.lnContentCar1 = (LinearLayout)rowView.findViewById(R.id.lnContentCar1);
//		if(position %2 == 0){
//			holder.lnContentCar.setBackgroundColor(color.item);
//			holder.lnContentCar1.setBackgroundColor(color.item1);
//		}

		Car dto = (Car) getArrObject().get(position);
		try {
			List<Image> lstImg = dto.getArrImage();
			if(lstImg.size() > 0){
				String url = "";
				for(Image img: lstImg){
					url = img.getUrl();
					break;
				}
				
				String[] strs = url.split("/");
				File f = new File(Environment.getExternalStorageDirectory() + "/"
						+ Constants.APP_NAME + "/" + strs[0] + "/" + strs[1]);
				if (f.exists()) {
					Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
					holder.imgNews.setImageBitmap(bmp);
				} else {
					PicassoUtils.getInstance().showImageFromUrl(
							mContext.getApplicationContext(), url,
							R.drawable.arrowleft, holder.imgNews);
					PicassoUtils.getInstance().saveImageFromUrl(
							mContext.getApplicationContext(), url, strs[1],
							strs[0]);
				}
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
//		holder.imgNews.setImageResource(dto.getImages());
		holder.txtNameCar.setText(dto.getNameCar());
		holder.txtPrice.setText(dto.getDetailCar().getPrice()+"$");
		holder.txtMiliage.setText("Mileage: " +dto.getDetailCar().getMilliage());
		holder.txtColor.setText("Color: "+dto.getDetailCar().getExteriorColor());
		return rowView;
	}

	public ArrayList<Car> getArrObject() {
		return arrObject;
	}

	public void setArrObject(ArrayList<Car> arrObject) {
		this.arrObject = arrObject;
	}

}