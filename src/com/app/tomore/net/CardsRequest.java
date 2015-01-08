package com.app.tomore.net;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;

import com.app.tomore.httpclient.BasicHttpClient;
import com.app.tomore.httpclient.HttpResponse;
import com.app.tomore.httpclient.NormalHttpClient;
import com.app.tomore.httpclient.ParameterMap;
import com.app.tomore.utils.AndroidMultiPartEntity;
import com.app.tomore.utils.AndroidMultiPartEntity.ProgressListener;

public class CardsRequest {

	private final String url = "http://54.213.167.5/";
	protected Context mContext;
	private BasicHttpClient baseRequest;
	private NormalHttpClient nhc;

	public CardsRequest(Context context) {
		mContext = context;
	}

	/*
	 * get card by memberID
	 */
	// http://54.213.167.5/APIV2/getCardByMemberID.php?memberID=25&limit=5&page=1
	public String getCardByMemberID(String memberID, String limit, String page)
			throws IOException, TimeoutException {
		baseRequest = new BasicHttpClient(url);
		baseRequest.setConnectionTimeout(2000);

		ParameterMap params = baseRequest.newParams().add("memberID", memberID)
				.add("limit", limit).add("page", page);
		HttpResponse httpResponse = baseRequest.post(
				"/APIV2/getCardByMemberID.php", params);
		return httpResponse.getBodyAsString();
	}

	/*
	 * update card by cardID
	 */
	// http://54.213.167.5/updateCardInfo.php?cardID=1&cardTitle=title&cardBarcode=barcode&cardNumber=123456
	// &cardDes=Des&frontViewImage=~/image/123.jpg&backViewImage=~/image/123.jpg
    @SuppressWarnings("deprecation")
	public String updateCardInfo(Bitmap frontImage, Bitmap backImage,
			String cardID, String cardTitle, String cardDes, String cardBarcode) {
		String responseString = null;

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url + "updateCardInfo.php");

		try {
			AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
					new ProgressListener() {

						@Override
						public void transferred(long num) {
							// publishProgress((int) ((num / (float) totalSize)
							// * 100));
						}
					});
			//create temp file for upload
			String fileName = "temp.jpg";

			String path = android.os.Environment.getExternalStorageDirectory()
					.toString();
			File f = new File(path, fileName);
			f.createNewFile();

			// Convert bitmap to byte array
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			frontImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] bitmapdata = stream.toByteArray();

			// write the bytes in file
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(bitmapdata);
			fos.flush();
			fos.close();

			String fileName2 = "temp2.jpg";

			String path2 = android.os.Environment.getExternalStorageDirectory()
					.toString();
			File f2 = new File(path2, fileName2);
			f2.createNewFile();

			// Convert bitmap to byte array
			ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
			backImage.compress(Bitmap.CompressFormat.PNG, 100, stream2);
			byte[] bitmapdata2 = stream2.toByteArray();

			// write the bytes in file
			FileOutputStream fos2 = new FileOutputStream(f2);
			fos2.write(bitmapdata2);
			fos2.flush();
			fos2.close();

			entity.addPart("uploaded1", new FileBody(f));
			entity.addPart("uploaded2", new FileBody(f2));
			entity.addPart("cardID", new StringBody(cardID));
			entity.addPart("cardTitle", new StringBody(cardTitle));
			entity.addPart("cardDes", new StringBody(cardDes));
			entity.addPart("cardBarcode", new StringBody(cardBarcode));
			entity.addPart("cardNumber", new StringBody(cardBarcode));

			// totalSize = entity.getContentLength();
			httppost.setEntity(entity);

			// Making server call
			org.apache.http.HttpResponse response = httpclient
					.execute(httppost);
			HttpEntity r_entity = response.getEntity();
			// delete temp file after upload
			f.delete();
			f2.delete();

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				// Server response
				responseString = EntityUtils.toString(r_entity);
			} else {
				responseString = "Error occurred! Http Status Code: "
						+ statusCode;
			}

		} catch (ClientProtocolException e) {
			responseString = e.toString();
		} catch (IOException e) {
			responseString = e.toString();
		}
		
		return responseString;
	}

	/*
	 * upload card by memberID
	 */
	// http://54.213.167.5/uploadCard.php?memberID=34&cardDes=des&cardBarcode=barcode&cardTitle=title&cardType=1
    @SuppressWarnings("deprecation")
   	public String uploadCard(Bitmap frontImage, Bitmap backImage,
   			String cardTitle, String cardDes, String cardBarcode, String memberID, String cardType) {
   		String responseString = null;

   		HttpClient httpclient = new DefaultHttpClient();
   		HttpPost httppost = new HttpPost(url + "uploadCard.php");

   		try {
   			AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
   					new ProgressListener() {

   						@Override
   						public void transferred(long num) {
   							// publishProgress((int) ((num / (float) totalSize)
   							// * 100));
   						}
   					});
   			//create temp file for upload
   			String fileName = "temp.jpg";

   			String path = android.os.Environment.getExternalStorageDirectory()
   					.toString();
   			File f = new File(path, fileName);
   			f.createNewFile();

   			// Convert bitmap to byte array
   			ByteArrayOutputStream stream = new ByteArrayOutputStream();
   			frontImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
   			byte[] bitmapdata = stream.toByteArray();

   			// write the bytes in file
   			FileOutputStream fos = new FileOutputStream(f);
   			fos.write(bitmapdata);
   			fos.flush();
   			fos.close();

   			String fileName2 = "temp2.jpg";

   			String path2 = android.os.Environment.getExternalStorageDirectory()
   					.toString();
   			File f2 = new File(path2, fileName2);
   			f2.createNewFile();

   			// Convert bitmap to byte array
   			ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
   			backImage.compress(Bitmap.CompressFormat.PNG, 100, stream2);
   			byte[] bitmapdata2 = stream2.toByteArray();

   			// write the bytes in file
   			FileOutputStream fos2 = new FileOutputStream(f2);
   			fos2.write(bitmapdata2);
   			fos2.flush();
   			fos2.close();

   			entity.addPart("uploaded1", new FileBody(f));
   			entity.addPart("uploaded2", new FileBody(f2));
   			entity.addPart("cardType", new StringBody(cardType));
   			entity.addPart("cardTitle", new StringBody(cardTitle));
   			entity.addPart("cardDes", new StringBody(cardDes));
   			entity.addPart("cardBarcode", new StringBody(cardBarcode));
   			entity.addPart("cardNumber", new StringBody(cardBarcode));
   			entity.addPart("memberID", new StringBody(memberID));

   			// totalSize = entity.getContentLength();
   			httppost.setEntity(entity);

   			// Making server call
   			org.apache.http.HttpResponse response = httpclient
   					.execute(httppost);
   			HttpEntity r_entity = response.getEntity();
   			// delete temp file after upload
   			f.delete();
   			f2.delete();

   			int statusCode = response.getStatusLine().getStatusCode();
   			if (statusCode == 200) {
   				// Server response
   				responseString = EntityUtils.toString(r_entity);
   			} else {
   				responseString = "Error occurred! Http Status Code: "
   						+ statusCode;
   			}

   		} catch (ClientProtocolException e) {
   			responseString = e.toString();
   		} catch (IOException e) {
   			responseString = e.toString();
   		}
   		
   		return responseString;
   	}
}
