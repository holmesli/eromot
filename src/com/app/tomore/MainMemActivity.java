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
import com.google.gson.JsonSyntaxException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.app.tomore.R;
import com.app.tomore.utils.AppUtil;
import com.app.tomore.utils.ToastUtils;
import com.app.tomore.utils.PullToRefreshListView;
import com.app.tomore.utils.PullToRefreshBase;
import com.app.tomore.utils.PullToRefreshBase.OnLastItemVisibleListener;
import com.app.tomore.utils.PullToRefreshBase.OnRefreshListener;
import com.app.tomore.utils.PullToRefreshListView;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainMemActivity extends Activity {

	private DialogActivity dialog;
	private ArrayList<CardModel> cardList;
	private ListView listView;
	private DisplayImageOptions otp;
	private PullToRefreshListView mListView;
	private Activity mContext;
	private TextView noneData;
	private View no_net_lay;
	private ImageView adImg;
	
	private boolean onRefresh = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		new GetData(MainMemActivity.this, 1).execute("");
		otp = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).showImageForEmptyUri(R.drawable.ic_launcher)
				.build();
		setContentView(R.layout.main_mag_activity);

			mListView = (PullToRefreshListView) findViewById(R.id.list);
			mListView.setOnRefreshListener(onRefreshListener);
			mListView.setOnLastItemVisibleListener(onLastItemVisibleListener);
			mListView.setOnItemClickListener(itemClickListener);
			noneData = (TextView)findViewById(R.id.noneData);
			no_net_lay = findViewById(R.id.no_net_lay);
			Button reloadData = (Button)findViewById(R.id.reloadData);
			reloadData.setOnClickListener(reloadClickListener);
			adImg = (ImageView)findViewById(R.id.adImg);
	
		
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

	protected void setUi() {
		if (onRefresh) {
			onRefresh = false;
			cardList.clear();
		}
		//ArrayList<News> newsData = newsWrapper.getNews();
		//allData.addAll(newsData);
		//listView.setAdapter(new MemberAdapter(this, cardList, listView));
		MemberAdapter newsListAdapter = new MemberAdapter(this, cardList, listView);
		
		if (newsListAdapter == null) {
			newsListAdapter = new MemberAdapter(this, cardList, listView);
			mListView.setAdapter(newsListAdapter);
		} else {
			newsListAdapter.notifyDataSetChanged();
		}
		if(cardList!=null && cardList.size()>0){
			showDataUi();
		}else{
			showNoDataUi();
		}
	}
	
	void showDataUi(){
		mListView.setVisibility(View.VISIBLE);
		noneData.setVisibility(View.GONE);
		no_net_lay.setVisibility(View.GONE);
	}

	void showNoDataUi(){
		mListView.setVisibility(View.GONE);
		noneData.setVisibility(View.VISIBLE);
		no_net_lay.setVisibility(View.GONE); 
	}

	protected void showNoNetUi() {
		no_net_lay.setVisibility(View.VISIBLE);
		noneData.setVisibility(View.GONE);
		mListView.setVisibility(View.GONE);
	}
	
	ProgressDialog pd;
	private void showDialog() {
		if (pd == null) {
			pd = new ProgressDialog(mContext);
			pd.setMessage("正在加载数据");
		}
		pd.show();
	}

	private void closeDialog() {
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
		}
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
		//listView = (AutoListView) findViewById(R.id.member_listview);
		listView.setAdapter(new MemberAdapter(this, cardList, listView));
	}
	

	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if(!AppUtil.networkAvailable(mContext)){
				ToastUtils.showToast(mContext, "请连接网络");
				return;
			}
			
		}
	};
	

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			mListView.onRefreshComplete();
		}
	};

	public OnRefreshListener<ListView> onRefreshListener = new OnRefreshListener<ListView>() {
		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			if(AppUtil.networkAvailable(mContext)){
				onRefresh = true;
//				currPage = 1;
//				getData();
			}else{
				ToastUtils.showToast(mContext, "没有网|络");
				mListView.onRefreshComplete();
			}
		}
	};

	private OnLastItemVisibleListener onLastItemVisibleListener = new OnLastItemVisibleListener() {

		@Override
		public void onLastItemVisible() {
			if(AppUtil.networkAvailable(mContext)){
//				if ((currPage - 1) * pageCount < total) {
//					currPage++;
//					getData();
//				}
			}else{
				ToastUtils.showToast(mContext, "没有网络");
			}
		}
	};

OnClickListener reloadClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			onRefresh = true;
			//currPage = 1;
			//getData();
		}
	};
	
	private class MemberAdapter extends ArrayAdapter<CardModel> {

		public MemberAdapter(Activity activity, List<CardModel> cardList,
				ListView listview1) {
			super(activity, 0, cardList);
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
			ImageLoader.getInstance().displayImage(imageUrl,
					imageView, otp);
			
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

}
