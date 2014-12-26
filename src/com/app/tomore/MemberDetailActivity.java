package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.app.tomore.adapters.MemberAdapter;
import com.app.tomore.beans.CardModel;
import com.app.tomore.beans.ImageAndTexts;
import com.app.tomore.net.CardsParse;
import com.app.tomore.net.CardsRequest;
import com.google.gson.JsonSyntaxException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class MemberDetailActivity extends Activity{

	private DialogActivity dialog;
	private ArrayList<CardModel> cardList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_detail);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		new GetData(MemberDetailActivity.this, 1).execute("");
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
			String result=null;
			//try {
				Log.d("doInBackground", "start request");
				Intent intent = getIntent();
				result = intent.getStringExtra("cardList");
				Log.d("doInBackground", "returned");
			//} 
//			catch (IOException e) {
//				e.printStackTrace();
//			} catch (TimeoutException e) {
//				e.printStackTrace();
//			}

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
				cardList = new ArrayList<CardModel>();
				try {
					cardList = new CardsParse().parseCardResponse(result);
					BindDataToListView();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
				if (cardList != null) {
					Intent intent = new Intent(MemberDetailActivity.this,
							MyCameraActivity.class); // fake redirect..
					intent.putExtra("cardList", (Serializable) cardList);
					//startActivity(intent);
				} else {
					// show empty alert
				}
			}
		}
	}
	
	private void BindDataToListView()
	{
		List<ImageAndTexts> imageAndTextlist = new ArrayList<ImageAndTexts>();
		for(CardModel c:cardList)
		{
			imageAndTextlist.add(new ImageAndTexts(c.getFrontViewImage(),c.getCardTitle(),c.getCardDes(),c.getCardType()));
		}
		ListView listView = (ListView) findViewById(R.id.member_listview);
		listView.setAdapter(new MemberAdapter(this, imageAndTextlist,
				listView));
	}
}

