package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;


import com.app.tomore.beans.CardModel;
import com.app.tomore.net.CardsParse;
import com.app.tomore.net.CardsRequest;
import com.google.gson.JsonSyntaxException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainMemActivity extends Activity {

	private DialogActivity dialog;
	private ArrayList<CardModel> cardList;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_member_activity);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		new GetData(MainMemActivity.this, 1).execute("");
		listView = (ListView)findViewById(R.id.member_listview);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (cardList == null) {
					return;
				}
				CardModel cardItem = cardList.get(position);
				Object obj = (Object) cardList.get(position);
				if (obj instanceof String) {
					return;
				}
				Intent intent = new Intent(MainMemActivity.this,
						MemberDetailActivity.class);
				intent.putExtra("cardList", (Serializable) cardItem);
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
			CardsRequest request = new CardsRequest(MainMemActivity.this);
			try {
				String memberID = "34";
				String limit = "5";
				String page = "1";
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
					intent.putExtra("cardList", (Serializable) cardList);
					// startActivity(intent);
				} else {
					// show empty alert
				}
			}
		}
	}

	private void BindDataToListView() {
		listView = (ListView)findViewById(R.id.member_listview);
		listView.setAdapter(new MemberAdapter(this, cardList, listView));
	}

	private class MemberAdapter extends ArrayAdapter<CardModel> {

		private ListView listview;

		public MemberAdapter(Activity activity, List<CardModel> cardList,
				ListView listview1) {
			super(activity, 0, cardList);
			this.listview = listview1;
			ImageLoader.getInstance().init(
					ImageLoaderConfiguration.createDefault(activity));
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Activity activity = (Activity) getContext();
			View rowView = convertView;
			if (rowView == null) {
				LayoutInflater inflater = activity.getLayoutInflater();
				rowView = inflater.inflate(R.layout.member_listview_item, null);
			} else {
				
			}
			CardModel cardItem = getItem(position);
			final String imageUrl = cardItem.getFrontViewImage();
			ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
			imageView.setTag(imageUrl);
			ImageLoader.getInstance().loadImage(imageUrl,
					new SimpleImageLoadingListener() {
						@Override
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
							ImageView imageViewByTag = (ImageView) listview
									.findViewWithTag(imageUrl);
							if (imageViewByTag != null) {
								imageViewByTag.setImageBitmap(loadedImage);
							}
						}
					});

			// Set the text on the TextView
			TextView textViewTitle = (TextView) rowView
					.findViewById(R.id.title);
			textViewTitle.setText(cardItem.getCardTitle());

			TextView textViewDes = (TextView) rowView.findViewById(R.id.des);
			textViewDes.setText(cardItem.getCardDes());

			TextView textViewTomoreCard = (TextView) rowView
					.findViewById(R.id.tomoreCard);
			String cardType = cardItem.getCardType();

			if (!cardType.equals("0")) {
				textViewTomoreCard.setVisibility(View.INVISIBLE);
			} else if (cardType.equals("0")) {
				textViewTomoreCard.setVisibility(View.VISIBLE);
			}

			return rowView;
		}

	}
	
	private class LoadMoreDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			if (isCancelled()) {
				return null;
			}

			// Simulates a background task
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			//for (int i = 0; i < mNames.length; i++)
			//	mListItems.add(mNames[i]);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
		//	mListItems.add("Added after load more");

			// We need notify the adapter that the data have been changed
		//	((BaseAdapter) getListAdapter()).notifyDataSetChanged();

			// Call onLoadMoreComplete when the LoadMore task, has finished
		//	((PullAndLoadListView) getListView()).onLoadMoreComplete();

			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			// Notify the loading more operation has finished
		//	((PullAndLoadListView) getListView()).onLoadMoreComplete();
		}
	}

	private class PullToRefreshDataTask extends AsyncTask<String, String, String> {
		
		@Override
		protected String doInBackground(String... params) {
			String result = null;
			CardsRequest request = new CardsRequest(MainMemActivity.this);
			
			if (isCancelled()) {
				return null;
			}
			
			try {
				String memberID = "34";
				String limit = "5";
				String page = "1";
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
					//((BaseAdapter) getListAdapter()).notifyDataSetChanged();
					//(listView).onRefreshComplete();
					super.onPostExecute(result);
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
				if (cardList != null) {
					Intent intent = new Intent(MainMemActivity.this,
							MyCameraActivity.class); // fake redirect..
					intent.putExtra("cardList", (Serializable) cardList);
					// startActivity(intent);
				} else {
					// show empty alert
				}
			}
		}

		@Override
		protected void onCancelled() {
			// Notify the loading more operation has finished
			//(listView).onLoadMoreComplete();
		}
	}
}
