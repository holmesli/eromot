package com.app.tomore;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import uk.co.senab.photoview.PhotoViewAttacher;

import com.app.tomore.beans.CardModel;
import com.app.tomore.beans.ImageAndTexts;
import com.app.tomore.net.CardsParse;
import com.app.tomore.net.CardsRequest;
import com.app.tomore.util.TouchImageView;
import com.google.gson.JsonSyntaxException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MemberDetailActivity extends Activity {

	private DialogActivity dialog;
	private CardModel cardItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_detail);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);

		Intent intent = getIntent();
		cardItem = (CardModel) intent.getSerializableExtra("cardList");
		BindData();
		new GetData(MemberDetailActivity.this, 1).execute("");

		RelativeLayout rl = (RelativeLayout)getWindow().getDecorView()
				.findViewById(R.id.bar_title_member_add);
		
		final Button btnEdit = (Button) rl
				.findViewById(R.id.bar_title_bt_edit);

		btnEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (cardItem == null) {
					return;
				}
				TouchImageView frontImageView = (TouchImageView) getWindow()
						.getDecorView().findViewById(R.id.frontViewImg);
				TouchImageView backImageView = (TouchImageView) getWindow()
						.getDecorView().findViewById(R.id.backViewImg);

				Button btnFrontEdit = (Button) getWindow().getDecorView()
						.findViewById(R.id.btnFrontEdit);
				Button btnBackEdit = (Button) getWindow().getDecorView()
						.findViewById(R.id.btnBackEdit);
				Button btnGenerateBarcode = (Button) getWindow().getDecorView()
						.findViewById(R.id.btnGenerateBarcode);
				Button btnSubmit = (Button) getWindow().getDecorView()
						.findViewById(R.id.btnSubmit);
				EditText editTitle = (EditText) getWindow().getDecorView()
						.findViewById(R.id.editTitle);
				EditText editDes = (EditText) getWindow().getDecorView()
						.findViewById(R.id.editDes);
				EditText editBarcode = (EditText) getWindow().getDecorView()
						.findViewById(R.id.editBarcode);
				TextView barcodeValueLable = (TextView) getWindow()
						.getDecorView().findViewById(R.id.barcodeValueLable);

				if (btnEdit.getText().equals("取消")) {
					
					btnFrontEdit.setVisibility(View.INVISIBLE);
					btnBackEdit.setVisibility(View.INVISIBLE);
					btnGenerateBarcode.setVisibility(View.INVISIBLE);
					btnSubmit.setVisibility(View.INVISIBLE);

					editTitle.setEnabled(false);
					editDes.setEnabled(false);
					editBarcode.setEnabled(false);

					btnEdit.setText("编辑");
					return;
				} else {
					
					btnFrontEdit.setVisibility(View.VISIBLE);
					btnBackEdit.setVisibility(View.VISIBLE);
					btnGenerateBarcode.setVisibility(View.VISIBLE);
					btnSubmit.setVisibility(View.VISIBLE);

					editTitle.setEnabled(true);
					editDes.setEnabled(true);
					editBarcode.setEnabled(true);

					btnEdit.setText("取消");
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
					BindData();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
				if (cardItem != null) {
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

	private void BindData() {

		TouchImageView frontImageView = (TouchImageView) getWindow()
				.getDecorView().findViewById(R.id.frontViewImg);
		TouchImageView backImageView = (TouchImageView) getWindow()
				.getDecorView().findViewById(R.id.backViewImg);

		Button btnFrontEdit = (Button) getWindow().getDecorView().findViewById(
				R.id.btnFrontEdit);
		Button btnBackEdit = (Button) getWindow().getDecorView().findViewById(
				R.id.btnBackEdit);
		Button btnGenerateBarcode = (Button) getWindow().getDecorView()
				.findViewById(R.id.btnGenerateBarcode);
		Button btnSubmit = (Button) getWindow().getDecorView().findViewById(
				R.id.btnSubmit);
		EditText editTitle = (EditText) getWindow().getDecorView()
				.findViewById(R.id.editTitle);
		EditText editDes = (EditText) getWindow().getDecorView().findViewById(
				R.id.editDes);
		EditText editBarcode = (EditText) getWindow().getDecorView()
				.findViewById(R.id.editBarcode);
		TextView barcodeValueLable = (TextView) getWindow().getDecorView()
				.findViewById(R.id.barcodeValueLable);

		Picasso.with(MemberDetailActivity.this)
				.load(cardItem.getFrontViewImage()).resize(550, 280)
				.into(frontImageView);
		Picasso.with(MemberDetailActivity.this)
				.load(cardItem.getBackViewImage()).resize(550, 280)
				.into(backImageView);

		btnFrontEdit.setVisibility(View.INVISIBLE);
		btnBackEdit.setVisibility(View.INVISIBLE);
		btnGenerateBarcode.setVisibility(View.INVISIBLE);
		btnSubmit.setVisibility(View.INVISIBLE);

		editTitle.setText(cardItem.getCardTitle());
		editTitle.setEnabled(false);
		editDes.setText(cardItem.getCardDes());
		editDes.setEnabled(false);
		editBarcode.setText(cardItem.getCardBarcode());
		editBarcode.setEnabled(false);
		barcodeValueLable.setText(cardItem.getCardBarcode());

	}

}
