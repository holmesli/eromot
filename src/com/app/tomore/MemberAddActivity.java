package com.app.tomore;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.app.tomore.beans.CardModel;
import com.app.tomore.beans.CommonModel;
import com.app.tomore.net.CardsRequest;
import com.app.tomore.net.ToMoreParse;
import com.google.gson.JsonSyntaxException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MemberAddActivity extends Activity {

	private DialogActivity dialog;
	private String memberID;
	private ImageView frontImageView;
	private ImageView backImageView;
	private ImageView barcodeImageView;
	private Button btnFrontEdit;
	private Button btnBackEdit;
	private Button btnGenerateBarcode;
	private Button btnSubmit;
	private Button btnEdit;
	private EditText editTitle;
	private EditText editDes;
	private EditText editBarcode;
	private TextView barcodeValueLable;
	private TextView barcodeLable;
	private TextView titleLable;
	private TextView desLable;
	private Bitmap bitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		memberID = intent.getStringExtra("memberID");
		setContentView(R.layout.member_detail);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);

		frontImageView = (ImageView) getWindow().getDecorView().findViewById(
				R.id.frontViewImg);
		backImageView = (ImageView) getWindow().getDecorView().findViewById(
				R.id.backViewImg);
		barcodeImageView = (ImageView) getWindow().getDecorView().findViewById(
				R.id.barcodeImg);

		btnFrontEdit = (Button) getWindow().getDecorView().findViewById(
				R.id.btnFrontEdit);
		btnBackEdit = (Button) getWindow().getDecorView().findViewById(
				R.id.btnBackEdit);
		btnGenerateBarcode = (Button) getWindow().getDecorView().findViewById(
				R.id.btnGenerateBarcode);
		btnSubmit = (Button) getWindow().getDecorView().findViewById(
				R.id.btnSubmit);
		editTitle = (EditText) getWindow().getDecorView().findViewById(
				R.id.editTitle);
		editDes = (EditText) getWindow().getDecorView().findViewById(
				R.id.editDes);
		editBarcode = (EditText) getWindow().getDecorView().findViewById(
				R.id.editBarcode);
		barcodeValueLable = (TextView) getWindow().getDecorView().findViewById(
				R.id.barcodeValueLable);
		barcodeLable = (TextView) getWindow().getDecorView().findViewById(
				R.id.barcodeLable);
		titleLable = (TextView) getWindow().getDecorView().findViewById(
				R.id.titleLable);
		desLable = (TextView) getWindow().getDecorView().findViewById(
				R.id.desLable);
		
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (memberID == null) {
					return;
				}

				String  cardTitle, cardBarcode, cardDes, cardType;

				cardTitle = editTitle.getText().toString();
				cardDes = editDes.getText().toString();
				cardBarcode = editBarcode.getText().toString();
				cardType = "1";

				// need image uploader

				String result = null;
				CardsRequest request = new CardsRequest(
						MemberAddActivity.this);

				try {
					Log.d("doInBackground", "start request");
					result = request.uploadCard(cardTitle,
							cardBarcode, cardDes, cardType, memberID);
					Log.d("doInBackground", "returned");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				CommonModel returnResult = new ToMoreParse()
						.CommonPares(result);
				// result alert

			}
		});
		
		btnGenerateBarcode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String barcode_data = editBarcode.getText().toString();
				try {
					bitmap = encodeAsBitmap(barcode_data,
							BarcodeFormat.CODE_128, 600, 250);
					barcodeImageView.setImageBitmap(bitmap);
					barcodeValueLable.setText(barcode_data);
				} catch (WriterException e) {
					e.printStackTrace();
				}
			}
		});
	

	}

	private class GetData extends AsyncTask<String, String, String> {
		// private Context mContext;
		private int mType;

		private GetData(Context context, int type) {
			// this.mContext = context;
			this.mType = type;
			dialog = new DialogActivity(context, type);
		}

		@Override
		protected void onPreExecute() {
			if (mType == 1) {
				if (null != dialog && !dialog.isShowing()) {
					dialog.show();
				}
			}
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			// try {
			Log.d("doInBackground", "start request");
			Log.d("doInBackground", "returned");
			// }
			// catch (IOException e) {
			// e.printStackTrace();
			// } catch (TimeoutException e) {
			// e.printStackTrace();
			// }

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (null != dialog) {
				dialog.dismiss();
			}
			Log.d("onPostExecute", "postExec state");
			if (result == null || result.equals("")) {
				// show empty alert
			} else {
				// cardList = new ArrayList<CardModel>();
				try {
					// cardList = new CardsParse().parseCardResponse(result);

				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
				if (memberID != null) {
					// Intent intent = new Intent(MemberDetailActivity.this,
					// MyCameraActivity.class); // fake redirect..
					// intent.putExtra("cardList", (Serializable) cardList);
					// startActivity(intent);
				} else {
					// show empty alert
				}
			}
		}
	}



	/**************************************************************
	 * getting from com.google.zxing.client.android.encode.QRCodeEncoder
	 * 
	 * See the sites below http://code.google.com/p/zxing/
	 * http://code.google.com
	 * /p/zxing/source/browse/trunk/android/src/com/google/
	 * zxing/client/android/encode/EncodeActivity.java
	 * http://code.google.com/p/zxing
	 * /source/browse/trunk/android/src/com/google/
	 * zxing/client/android/encode/QRCodeEncoder.java
	 */

	private static final int WHITE = 0xFFFFFFFF;
	private static final int BLACK = 0xFF000000;

	Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width,
			int img_height) throws WriterException {
		String contentsToEncode = contents;
		if (contentsToEncode == null) {
			return null;
		}
		Map<EncodeHintType, Object> hints = null;
		String encoding = guessAppropriateEncoding(contentsToEncode);
		if (encoding != null) {
			hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hints.put(EncodeHintType.CHARACTER_SET, encoding);
		}
		MultiFormatWriter writer = new MultiFormatWriter();
		BitMatrix result;
		try {
			result = writer.encode(contentsToEncode, format, img_width,
					img_height, hints);
		} catch (IllegalArgumentException iae) {
			// Unsupported format
			return null;
		}
		int width = result.getWidth();
		int height = result.getHeight();
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			int offset = y * width;
			for (int x = 0; x < width; x++) {
				pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	private static String guessAppropriateEncoding(CharSequence contents) {
		// Very crude at the moment
		for (int i = 0; i < contents.length(); i++) {
			if (contents.charAt(i) > 0xFF) {
				return "UTF-8";
			}
		}
		return null;
	}

}
