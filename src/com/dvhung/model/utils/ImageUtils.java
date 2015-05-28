package com.dvhung.model.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

public class ImageUtils {
	private static ImageUtils instance;

	public static ImageUtils getInstance() {
		if (instance == null)
			instance = new ImageUtils();
		return instance;
	}

	public String uploadFile(String strrarr, Bitmap bm, String urlPath, String name) {
		String nameImageUpload = "";
		String upLoadServerUri = urlPath;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		String fileName = strrarr;

		bm.compress(CompressFormat.PNG, 50, bos);
		byte[] data = bos.toByteArray();
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(upLoadServerUri);
		ByteArrayBody bab = new ByteArrayBody(data, fileName+ name);
		MultipartEntity reqEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);

		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";

		// byte[] buffer;
		// int maxBufferSize = 1 * 1024 * 1024;
		// File sourceFile = new File(sourceFileUri);
		// if (!sourceFile.isFile()) {
		// Log.e("uploadFile", "Source File Does not exist");
		// return 0;
		// }
		reqEntity.addPart("image", bab);
		postRequest.setEntity(reqEntity);
		try {
			HttpResponse response = httpClient.execute(postRequest);
			System.out.println("Status is " + response.getStatusLine());
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			String sResponse;
			StringBuilder s = new StringBuilder();
			while ((sResponse = reader.readLine()) != null) {
				s = s.append(sResponse);
				nameImageUpload = sResponse;
			}
			System.out.println("Response: " + s);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return nameImageUpload;
	}

	//
	// /**
	// * Decode string to image
	// * @param imageString The string to decode
	// * @return decoded image
	// */
	// public static BufferedImage decodeToImage(String imageString) {
	//
	// BufferedImage image = null;
	// byte[] imageByte;
	// try {
	// BASE64Decoder decoder = new BASE64Decoder();
	// imageByte = decoder.decodeBuffer(imageString);
	// ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
	// image = ImageIO.read(bis);
	// bis.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return image;
	// }
	//
	// private String getStringFromBitmap(Bitmap bitmapPicture) {
	// final int COMPRESSION_QUALITY = 100;
	// String encodedImage;
	// ByteArrayOutputStream byteArrayBitmapStream = new
	// ByteArrayOutputStream();
	// bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
	// byteArrayBitmapStream);
	// byte[] b = byteArrayBitmapStream.toByteArray();
	// encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
	// return encodedImage;
	// }
	//
	// private Bitmap getBitmapFromString(String jsonString) {
	// /*
	// * This Function converts the String back to Bitmap
	// * */
	// byte[] decodedString = Base64.decode(jsonString, Base64.DEFAULT);
	// Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
	// decodedString.length);
	// return decodedByte;
	// }
	//
	// /**
	// * Encode image to string
	// * @param image The image to encode
	// * @param type jpeg, bmp, ...
	// * @return encoded string
	// */
	// public static String encodeToString(BufferedImage image, String type) {
	// String imageString = null;
	// ByteArrayOutputStream bos = new ByteArrayOutputStream();
	//
	// try {
	// ImageIO.write(image, type, bos);
	// byte[] imageBytes = bos.toByteArray();
	//
	// BASE64Encoder encoder = new BASE64Encoder();
	// imageString = encoder.encode(imageBytes);
	//
	// bos.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return imageString;
	// }

}
