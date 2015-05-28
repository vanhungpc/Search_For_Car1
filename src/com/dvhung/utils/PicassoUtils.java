package com.dvhung.utils;

import java.io.File;
import java.io.FileOutputStream;

import com.dvhung.constants.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback.EmptyCallback;
import com.squareup.picasso.Target;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;

public class PicassoUtils {
	private String imageNameForSave = "";
	private String folderNameForSave = "";
	private static PicassoUtils instance;

	public static PicassoUtils getInstance() {
		if (instance == null)
			instance = new PicassoUtils();

		return instance;
	}

	/**
	 * 
	 * @param activity
	 *            - context object
	 * @param urlImage
	 *            - url image pointer image on server
	 * @param imageErrorId
	 *            - drawable to show image error to imageview if not network
	 * @param ivShow
	 *            - ImageView to show image from urlImage
	 */
	public void showImageFromUrl(Context context, String urlImage,
			int imageErrorId, ImageView ivShow) {
		Picasso.with(context).load(urlImage).error(imageErrorId)
				.into(ivShow, new EmptyCallback() {
					@Override
					public void onSuccess() {
						// progressBar.setVisibility(View.GONE);
					}

					@Override
					public void onError() {
						// progressBar.setVisibility(View.GONE);
					}
				});
	}

	/**
	 * 
	 * @param context
	 * @param urlImage
	 *            - url image on server to get image by picasso
	 * @param imageName
	 *            - image name don't include "/" mark.
	 * @param folderName
	 *            - if NULL will save into sdcard direct, otherwise save image
	 *            into folder in sdcard
	 */
	public void saveImageFromUrl(Context context, String urlImage,
			String imageName, String folderName) {
		// Create folder if folderName != NULL

		// Check for SD Card
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			if (folderName != null) {
				//1. Create folder name APP_NAME if not exists
				File fApp = new File(Environment.getExternalStorageDirectory()
						+ "/" + Constants.APP_NAME);
				if(!(fApp.exists() && fApp.isDirectory())){
					File createFolderApp = new File(Environment.getExternalStorageDirectory()
							+ "/" + Constants.APP_NAME);
					createFolderApp.mkdir();
				}
				
				//2. Create folder dependence in foldername
				File f = new File(Environment.getExternalStorageDirectory() + "/" + Constants.APP_NAME
						+ "/" + folderName);
				if (!(f.exists() && f.isDirectory())) {
					File file = new File(
							Environment.getExternalStorageDirectory()
									+ File.separator + Constants.APP_NAME + File.separator + folderName);
					file.mkdirs();
				}

				folderNameForSave = folderName;
			} else {
				folderNameForSave = null;
			}

			imageNameForSave = imageName;
			Picasso.with(context).load(urlImage).into(target);
		}
	}

	private Target target = new Target() {
		@Override
		public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
			new Thread(new Runnable() {
				@Override
				public void run() {

					if (imageNameForSave != "") {
						String str = "/" + Constants.APP_NAME + "/";
						str += folderNameForSave == null ? "" : folderNameForSave + "/";
						str += imageNameForSave;
						File file = new File(Environment
								.getExternalStorageDirectory().getPath() + str);
						try {
							file.createNewFile();
							FileOutputStream ostream = new FileOutputStream(
									file);
							bitmap.compress(CompressFormat.JPEG, 75, ostream);
							ostream.close();
							imageNameForSave = "";
						} catch (Exception e) {
							imageNameForSave = "";
							e.printStackTrace();
						}
					}
				}
			}).start();
		}

		@Override
		public void onBitmapFailed(Drawable errorDrawable) {
		}

		@Override
		public void onPrepareLoad(Drawable placeHolderDrawable) {
			if (placeHolderDrawable != null) {
			}
		}
	};

}
