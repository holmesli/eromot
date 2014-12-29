package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import com.app.tomore.beans.CardModel;
import com.app.tomore.net.CardsParse;
import com.app.tomore.net.CardsRequest;
import com.uit.pullrefresh.base.impl.PullRefreshListView;
//import com.app.tomore.util.PullToRefreshListView;
//import com.app.tomore.util.PullToRefreshListView.OnRefreshListener;
//import com.app.tomore.utils.AutoListView;
//import com.app.tomore.utils.AutoListView.OnLoadListener;
//import com.app.tomore.utils.AutoListView.OnRefreshListener;
import com.uit.pullrefresh.listener.OnLoadListener;
import com.uit.pullrefresh.listener.OnRefreshListener;
import com.uit.pullrefresh.scroller.impl.RefreshListView;
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
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

public class MainMemActivity extends Activity {

	private DialogActivity dialog;
	private ArrayList<CardModel> cardList;
	private ListView listView;
	private PullRefreshListView mPullRefreshListView;

	// private ListViewAdapter adapter;

	// private Handler handler = new Handler() {
	// public void handleMessage(Message msg) {
	// List<CardModel> result = (List<CardModel>) msg.obj;
	// switch (msg.what) {
	// case AutoListView.REFRESH:
	// listView.onRefreshComplete();
	// cardList.clear();
	// cardList.addAll(result);
	// break;
	// case AutoListView.LOAD:
	// listView.onLoadComplete();
	// cardList.addAll(result);
	// break;
	// }
	// listView.setResultSize(result.size());
	// adapter.notifyDataSetChanged();
	// };
	// };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main_member_activity);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);

		mPullRefreshListView = new PullRefreshListView(this);
		// listView = (PullRefreshListView )
		// getWindow().getDecorView().findViewById(
		// R.id.member_listview);
		listView = mPullRefreshListView.getContentView();
		new GetData(MainMemActivity.this, 1).execute("");
		// adapter = new ListViewAdapter(this, cardList, listView);
		// listView.setAdapter(adapter);
		// listView.setOnRefreshListener(this);
		// listView.setOnLoadListener(this);
		// initData();

		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				Toast.makeText(getApplicationContext(), "refresh",
						Toast.LENGTH_SHORT).show();
				mPullRefreshListView.postDelayed(new Runnable() {

					@Override
					public void run() {
						new GetData(MainMemActivity.this, 1).execute("");
						mPullRefreshListView.refreshComplete();
					}
				}, 2000);
			}
		});

		// 不设置的话到底部不会自动加载
		mPullRefreshListView.setOnLoadMoreListener(new OnLoadListener() {

			@Override
			public void onLoadMore() {
				Toast.makeText(getApplicationContext(), "load more",
						Toast.LENGTH_SHORT).show();
				mPullRefreshListView.postDelayed(new Runnable() {

					@Override
					public void run() {
						mPullRefreshListView.loadMoreComplete();
					}
				}, 1500);
			}
		});

		setContentView(mPullRefreshListView);
		// setContentView(R.layout.main_member_activity);

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
		// listView = (AutoListView) findViewById(R.id.member_listview);
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

	// private void initData() {
	// loadData(AutoListView.REFRESH);
	// }
	//
	// private void loadData(final int what) {
	// // 这里模拟从服务器获取数据
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// try {
	// Thread.sleep(700);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// Message msg = handler.obtainMessage();
	// msg.what = what;
	// msg.obj = getData();
	// handler.sendMessage(msg);
	// }
	// }).start();
	// }
	//
	// @Override
	// public void onRefresh() {
	// new GetData(MainMemActivity.this, 1).execute("");
	// }

	// @Override
	// public void onLoad() {
	// loadData(AutoListView.LOAD);
	// }
	//
	// // 测试数据
	// public List<CardModel> getData() {
	// String result = null;
	// CardsRequest request = new CardsRequest(MainMemActivity.this);
	//
	// String memberID = "34";
	// String limit = "5";
	// String page = "1";
	// Log.d("doInBackground", "start request");
	// try {
	// result = request.getCardByMemberID(memberID, limit, page);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (TimeoutException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// Log.d("doInBackground", "returned");
	//
	// cardList = new CardsParse().parseCardResponse(result);
	//
	// return cardList;
	// }
	//
	// public class ListViewAdapter extends BaseAdapter {
	//
	// private List<CardModel> list;
	// private Context context;
	// private ListView listview;
	//
	// public ListViewAdapter(Context context, List<CardModel> cardList,
	// AutoListView listview1) {
	// this.list = cardList;
	// this.context = context;
	// this.listview = listview1;
	// ImageLoader.getInstance().init(
	// ImageLoaderConfiguration.createDefault(context));
	// }
	//
	// @Override
	// public int getCount() {
	// return list.size();
	// }
	//
	// @Override
	// public Object getItem(int position) {
	// return null;
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// return 0;
	// }
	//
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// if (convertView == null) {
	// convertView = LayoutInflater.from(context).inflate(
	// R.layout.member_listview_item, null);
	//
	// } else {
	// }
	//
	// final String imageUrl = list.get(position).getFrontViewImage();
	// ImageView imageView = (ImageView) convertView
	// .findViewById(R.id.img);
	// imageView.setTag(imageUrl);
	// ImageLoader.getInstance().loadImage(imageUrl,
	// new SimpleImageLoadingListener() {
	// @Override
	// public void onLoadingComplete(String imageUri,
	// View view, Bitmap loadedImage) {
	// ImageView imageViewByTag = (ImageView) listview
	// .findViewWithTag(imageUrl);
	// if (imageViewByTag != null) {
	// imageViewByTag.setImageBitmap(loadedImage);
	// }
	// }
	// });
	//
	// // Set the text on the TextView
	// TextView textViewTitle = (TextView) convertView
	// .findViewById(R.id.title);
	// textViewTitle.setText(list.get(position).getCardTitle());
	//
	// TextView textViewDes = (TextView) convertView
	// .findViewById(R.id.des);
	// textViewDes.setText(list.get(position).getCardDes());
	//
	// TextView textViewTomoreCard = (TextView) convertView
	// .findViewById(R.id.tomoreCard);
	// String cardType = list.get(position).getCardType();
	//
	// if (!cardType.equals("0")) {
	// textViewTomoreCard.setVisibility(View.INVISIBLE);
	// } else if (cardType.equals("0")) {
	// textViewTomoreCard.setVisibility(View.VISIBLE);
	// }
	//
	// return convertView;
	// }
	//
	// }
}
