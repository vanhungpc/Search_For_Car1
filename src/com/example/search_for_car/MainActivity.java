package com.example.search_for_car;

import java.io.File;

import com.dvhung.model.utils.ImageUtils;
import com.dvhung.model.utils.LoginModel;
import com.pkt.rest.client.pojo.DetailCar;
import com.pkt.rest.client.services.DetailCarService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements OnClickListener {

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private ViewFlipper mViewFlipper;
	private AnimationListener mAnimationListener;
	private Context mContext;

	Button btnSearchForCar, btnInsertCar, btnNewcar, btnBrand, btnModel;
	
//	String upLoadServerUri = "http://192.168.0.109:8888/RestService_SFC/images";
	String nameCar, gear, transType, origin, fuel, colorCar, colorInterior,
	description;
float price;
int inProduction, numberKm;
	@SuppressWarnings("deprecation")
	private final GestureDetector detector = new GestureDetector(
			new SwipeGestureDetector());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String sourceFileUri = Environment.getExternalStorageDirectory()
				+ "/abc.png";
		String sourceFileUri1 = Environment.getExternalStorageDirectory()
				+ "/cl2 447.jpg";
		String sourceFileUri2 = Environment.getExternalStorageDirectory()
				+ "/Clouded leopard 538.jpg";

		// run
		// int i = 0;
		// String[] myFiles = { sourceFileUri, sourceFileUri, sourceFileUri };
		// for (String selectedPath : myFiles) {
		//
		// try {
		// bm = BitmapFactory.decodeFile(selectedPath);
		// if (bm == null)
		// throw new Exception("no picture!");
		// new TaskUploadImage().execute();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// end run

		// test example

		// String upLoadServerUri =
		// "http://49.212.156.64:8080/RestService_SFC/images";
		String[] params = { sourceFileUri, sourceFileUri1, sourceFileUri2 };
		Object object = params;
//		new TaskUploadImage().execute(object);
		

		mContext = this;
		mViewFlipper = (ViewFlipper) this.findViewById(R.id.view_flipper);
		mViewFlipper.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(final View view, final MotionEvent event) {
				detector.onTouchEvent(event);
				return true;
			}
		});
		mViewFlipper.setAutoStart(true);
		mViewFlipper.setFlipInterval(4000);
		mViewFlipper.startFlipping();

		// animation listener
		mAnimationListener = new Animation.AnimationListener() {
			public void onAnimationStart(Animation animation) {
				// animation started event
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
				// TODO animation stopped event
			}
		};

		btnSearchForCar = (Button) findViewById(R.id.btnSearchForCar);
		btnInsertCar = (Button) findViewById(R.id.btnInsertCar);
		btnNewcar = (Button) findViewById(R.id.btnNewcar);
		btnBrand = (Button) findViewById(R.id.btnBrand);
		btnModel = (Button) findViewById(R.id.btnModel);
		btnNewcar.setOnClickListener(this);
		btnSearchForCar.setOnClickListener(this);
		btnInsertCar.setOnClickListener(this);
		btnBrand.setOnClickListener(this);
		btnModel.setOnClickListener(this);
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class SwipeGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(
							mContext, R.anim.left_in));
					mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
							mContext, R.anim.left_out));
					// controlling animation
					mViewFlipper.getInAnimation().setAnimationListener(
							mAnimationListener);
					mViewFlipper.showNext();
					return true;
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(
							mContext, R.anim.right_in));
					mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
							mContext, R.anim.right_out));
					// controlling animation
					mViewFlipper.getInAnimation().setAnimationListener(
							mAnimationListener);
					mViewFlipper.showPrevious();
					return true;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return false;
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent;
		switch (id) {
		case R.id.btnSearchForCar:
			intent = new Intent(MainActivity.this, SearchForCarActivity.class);
			startActivity(intent);
			break;
		case R.id.btnInsertCar:
			LoginModel model = LoginModel.getInstance();
			boolean Login = model.CheckLogin(MainActivity.this);
			if(Login){
				intent = new Intent(MainActivity.this, ManagerCarActivity.class);
				startActivity(intent);
			}else{
				intent = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		
			break;
		case R.id.btnNewcar:
			intent = new Intent(MainActivity.this, NewsCarActivity.class);
			startActivity(intent);
			break;
		case R.id.btnBrand:
			intent = new Intent(MainActivity.this, BrandActivity.class);
			startActivity(intent);
			break;
		case R.id.btnModel:
			intent = new Intent(MainActivity.this, ModelSearchActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	

	// private class TaskUploadImage extends AsyncTask<Void, Void, Void> {
	// @Override
	// protected void onPostExecute(Void result) {
	// // dialog.dismiss();
	// }
	//
	// @Override
	// protected Void doInBackground(Void... params) {
	// try {
	// ByteArrayOutputStream bos = new ByteArrayOutputStream();
	// bm.compress(CompressFormat.PNG, 50, bos);
	// byte[] data = bos.toByteArray();
	// HttpClient httpClient = new DefaultHttpClient();
	// HttpPost postRequest = new HttpPost(
	// "http://49.212.156.64:8080/RestService_SFC/images");
	// ByteArrayBody bab = new ByteArrayBody(data, "Car.JPG");
	// MultipartEntity reqEntity = new MultipartEntity(
	// HttpMultipartMode.BROWSER_COMPATIBLE);
	//
	// reqEntity.addPart("image", bab);
	// postRequest.setEntity(reqEntity);
	// HttpResponse response = httpClient.execute(postRequest);
	// System.out.println("Status is " + response.getStatusLine());
	// BufferedReader reader = new BufferedReader(
	// new InputStreamReader(
	// response.getEntity().getContent(), "UTF-8"));
	// String sResponse;
	// StringBuilder s = new StringBuilder();
	// while ((sResponse = reader.readLine()) != null) {
	// s = s.append(sResponse);
	// }
	// System.out.println("Response: " + s);
	// } catch (Exception e) {
	// // handle exception here
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// }

}
