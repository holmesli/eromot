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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainMemActivity extends Activity{

	private DialogActivity dialog;
	private ArrayList<CardModel> cardList;
	private ListView listView;
	private int cardID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_member_activity);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		new GetData(MainMemActivity.this, 1).execute("");
		ListView listView = (ListView) findViewById(R.id.member_listview);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			if(cardList==null){
				return;
			}
			CardModel cardItem = cardList.get(position);
			cardID = Integer.parseInt(cardItem.getCardID());
			Object obj=(Object)cardList.get(position);
			new GetData(MainMemActivity.this,0).execute("");
			if(obj instanceof String){
				return;
			}
			Intent intent = new Intent(MainMemActivity.this,
					MemberDetailActivity.class);
			intent.putExtra("cardInfo", "info");
			startActivity(intent);
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
			CardsRequest request = new CardsRequest(
					MainMemActivity.this);
			try {
				String memberID="34";
				String limit="5";
				String page="1";
				Log.d("doInBackground", "start request");
				result = request.getCardByMemberID(memberID, limit, page);
				Log.d("doInBackground", "returned");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}

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
					Intent intent = new Intent(MainMemActivity.this,
							MyCameraActivity.class); // fake redirect..
					intent.putExtra("menuList", (Serializable) cardList);
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
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			if(cardList==null){
				return;
			}
			CardModel cardItem = cardList.get(position);
			cardID = Integer.parseInt(cardItem.getCardID());
			Object obj=(Object)cardList.get(position);
			new GetData(MainMemActivity.this,0).execute("");
			if(obj instanceof String){
				return;
			}
			Intent intent = new Intent(MainMemActivity.this,
					MemberDetailActivity.class);
			intent.putExtra("cardInfo", "info");
			startActivity(intent);
		}
	});
	}
}

