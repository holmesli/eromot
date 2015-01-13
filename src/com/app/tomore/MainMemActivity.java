package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;
import com.app.tomore.beans.CardModel;
import com.app.tomore.net.CardsParse;
import com.app.tomore.net.CardsRequest;
import com.google.gson.JsonSyntaxException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.app.tomore.R;
import com.app.tomore.utils.AppUtil;
import com.app.tomore.utils.ToastUtils;
import com.app.tomore.utils.PullToRefreshListView;
import com.app.tomore.utils.PullToRefreshBase;
import com.app.tomore.utils.PullToRefreshBase.OnLastItemVisibleListener;
import com.app.tomore.utils.PullToRefreshBase.OnRefreshListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainMemActivity extends Activity {
	private DialogActivity dialog;
	private ArrayList<CardModel> cardList;
	private DisplayImageOptions otp;
	private PullToRefreshListView mListView;
	private Activity mContext;
	private TextView noneData;
	private View no_net_lay;
	MemberAdapter memberListAdapter;
	private boolean onRefresh = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_member_activity);
		mContext = this;
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		new GetData(MainMemActivity.this, 1).execute("");
		otp = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).showImageForEmptyUri(R.drawable.ic_launcher)
				.build();

		mListView = (PullToRefreshListView) findViewById(R.id.list);
		mListView.setOnRefreshListener(onRefreshListener);
		mListView.setOnLastItemVisibleListener(onLastItemVisibleListener);
		mListView.setOnItemClickListener(itemClickListener);
		noneData = (TextView) findViewById(R.id.noneData);
		no_net_lay = findViewById(R.id.no_net_lay);
		Button reloadData = (Button) findViewById(R.id.reloadData);
		reloadData.setOnClickListener(reloadClickListener);

		final Button btnAdd = (Button) findViewById(R.id.bar_title_bt_member);
		
		btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainMemActivity.this,
						MemberAddActivity.class);
				intent.putExtra("memberID", "34");
				startActivity(intent);
			}
		});


	}

	private void BindDataToListView() {
		if (onRefresh) {
			onRefresh = false;
		}
		if (memberListAdapter == null) {
			memberListAdapter = new MemberAdapter();
			mListView.setAdapter(memberListAdapter);
		} else {
			memberListAdapter.notifyDataSetChanged();
		}
		if (cardList != null && cardList.size() > 0) {
			showDataUi();
		} else {
			showNoDataUi();
		}
	}

	void showDataUi() {
		mListView.setVisibility(View.VISIBLE);
		noneData.setVisibility(View.GONE);
		no_net_lay.setVisibility(View.GONE);
	}

	void showNoDataUi() {
		mListView.setVisibility(View.GONE);
		noneData.setVisibility(View.VISIBLE);
		no_net_lay.setVisibility(View.GONE);
	}

	protected void showNoNetUi() {
		no_net_lay.setVisibility(View.VISIBLE);
		noneData.setVisibility(View.GONE);
		mListView.setVisibility(View.GONE);
	}

	private class GetData extends AsyncTask<String, String, String> {
		private int mType;

		private GetData(Context context, int type) {
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
			mListView.onRefreshComplete();
			Log.d("onPostExecute", "postExec state");
			if (result == null || result.equals("")) {
				ToastUtils.showToast(mContext, "列表为空");
			} else {
				if (cardList != null && cardList.size() > 0) {
					cardList.clear();
				} else {
					cardList = new ArrayList<CardModel>();
				}
				try {
					cardList = new CardsParse().parseCardResponse(result);
					BindDataToListView();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (!AppUtil.networkAvailable(mContext)) {
				ToastUtils.showToast(mContext, "列表为空");
				return;
			}
			if (cardList == null) {
				return;
			}
			Object obj = (Object) cardList.get(position - 1);
			if (obj instanceof String) {
				return;
			}
			Intent intent = new Intent(MainMemActivity.this,
					MemberDetailActivity.class);
			intent.putExtra("cardList", (Serializable) obj);
			startActivity(intent);
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
			if (AppUtil.networkAvailable(mContext)) {
				onRefresh = true;
				new GetData(MainMemActivity.this, 1).execute("");
			} else {
				ToastUtils.showToast(mContext, "到头了");
				mListView.onRefreshComplete();
			}
		}
	};

	private OnLastItemVisibleListener onLastItemVisibleListener = new OnLastItemVisibleListener() {
		@Override
		public void onLastItemVisible() {
			if (AppUtil.networkAvailable(mContext)) {
				// new GetData(MainMemActivity.this, 1).execute("");
			} else {
				ToastUtils.showToast(mContext, "到头了");
			}
		}
	};

	OnClickListener reloadClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			onRefresh = true;

			new GetData(MainMemActivity.this, 1).execute("");
		}
	};

	class ViewHolder {
		TextView textViewTitle;
		TextView textViewDes;
		TextView textViewTomoreCard;
		ImageView imageView;
	}

	private class MemberAdapter extends BaseAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			CardModel cardItem = (CardModel) getItem(position);
			ViewHolder viewHolder = null;
			if (convertView != null) {
				viewHolder = (ViewHolder) convertView.getTag();
			} else {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.member_listview_item, null);
				viewHolder.textViewTitle = (TextView) convertView
						.findViewById(R.id.title);
				viewHolder.textViewDes = (TextView) convertView
						.findViewById(R.id.des);
				viewHolder.textViewTomoreCard = (TextView) convertView
						.findViewById(R.id.tomoreCard);
				viewHolder.imageView = (ImageView) convertView
						.findViewById(R.id.img);
				convertView.setTag(viewHolder);
			}
			ImageLoader.getInstance().displayImage(
					cardItem.getFrontViewImage(), viewHolder.imageView, otp);
			viewHolder.textViewTitle.setText(cardItem.getCardTitle());
			viewHolder.textViewDes.setText(cardItem.getCardDes());

			String cardType = cardItem.getCardType();

			if (!cardType.equals("0")) {
				viewHolder.textViewTomoreCard.setVisibility(View.INVISIBLE);
			} else if (cardType.equals("0")) {
				viewHolder.textViewTomoreCard.setVisibility(View.VISIBLE);
			}

			return convertView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return cardList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return cardList.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
	}
}
