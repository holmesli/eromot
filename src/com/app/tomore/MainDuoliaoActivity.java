
package com.app.tomore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import com.app.tomore.MainMemActivity.ViewHolder;
import com.app.tomore.beans.ThreadModel;
import com.app.tomore.fragment.BackToMainActivity;
import com.app.tomore.net.ThreadsParse;
import com.app.tomore.net.ThreadsRequest;
import com.app.tomore.utils.PullToRefreshListView;
import com.google.gson.JsonSyntaxException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainDuoliaoActivity extends Activity implements OnClickListener{
	private TextView bt1;
	private TextView bt2;
	private TextView bt3;
	private TextView bt4,bt5,bt6,bt7;
	private Context context;
	private ImageButton menubtn;
	private SlidingMenu menu;
	private Activity mContext;
	private DialogActivity dialog;
	private boolean headerRefresh;
	private ArrayList<ThreadModel> threadList;
	private int pageNumber = 1;
	private int limit = 20;
	private PullToRefreshListView mListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_duoliao_activity);
		mContext = this;
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		
		context=this;
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		View view=LayoutInflater.from(this).inflate(R.layout.main_left_fragment, null);
		menu.setMenu(view);
		bt1=(TextView)view.findViewById(R.id.my_backtomain_bt);
		bt2=(TextView)view.findViewById(R.id.my_tiezi_bt);
		bt3=(TextView)view.findViewById(R.id.my_guanzhu_bt);
		bt4=(TextView)view.findViewById(R.id.my_fensi_bt);
		bt4=(TextView)view.findViewById(R.id.my_blacklist_bt);
		bt4=(TextView)view.findViewById(R.id.my_aboutus_bt);
		bt4=(TextView)view.findViewById(R.id.my_logout_bt);
		menubtn=(ImageButton)findViewById(R.id.ivTitleBtnLeft);
		bt1.setOnClickListener(this);
		bt2.setOnClickListener(this);
		bt3.setOnClickListener(this);
		menubtn.setOnClickListener(this);
		mListView = (PullToRefreshListView) findViewById(R.id.list);
		new GetData(MainDuoliaoActivity.this, 1).execute("");
	}

	@Override
	public void onClick(View v) {
		BackToMainActivity newContent = null;
		int id = v.getId();
		if (id == R.id.my_backtomain_bt) {
			newContent = new BackToMainActivity();
		} else if (id == R.id.my_tiezi_bt) {
			Toast.makeText(context, "����2", 1).show();
		} else if (id == R.id.my_guanzhu_bt) {
			Toast.makeText(context, "����3", 1).show();
		} else if (id == R.id.ivTitleBtnLeft) {
			menu.toggle();
		} else if (id == R.id.my_fensi_bt) {
			Toast.makeText(context, "����1", 1).show();
		} else if (id == R.id.my_blacklist_bt) {
			Toast.makeText(context, "����1", 1).show();
		} else if (id == R.id.my_aboutus_bt) {
			Toast.makeText(context, "����1", 1).show();
		}

	}
	
	public void onLogoutClick (View view){		
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);   
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
			ThreadsRequest request = new ThreadsRequest(MainDuoliaoActivity.this);
			try {
				Log.d("doInBackground", "start request");
				result = request.getThreadList(pageNumber, limit, 25, 0);//for test
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
				// show empty alert
			} else {
				
				if(threadList!=null && threadList.size()!=0)
				{
					if(headerRefresh)
						threadList = new ArrayList<ThreadModel>();
				}
				else
					threadList = new ArrayList<ThreadModel>();
				try {
					if(headerRefresh)
						threadList = new ThreadsParse().parseThreadModel(result);
					else
					{
						threadList.addAll(new ThreadsParse().parseThreadModel(result));
					}
					BindDataToListView();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void BindDataToListView()
	{
		
	}
	public void onMyFansClick(View view){		
		//Intent intent = new Intent(this, MainFansActivity.class);
		//startActivity(intent);   		
	}
	
//	private class duoliaoAdapter extends BaseAdapter {
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			CardModel cardItem = (CardModel) getItem(position);
//			ViewHolder viewHolder = null;
//			if (convertView != null) {
//				viewHolder = (ViewHolder) convertView.getTag();
//			} else {
//				viewHolder = new ViewHolder();
//				convertView = LayoutInflater.from(mContext).inflate(
//						R.layout.member_listview_item, null);
//				viewHolder.textViewTitle = (TextView) convertView
//						.findViewById(R.id.title);
//				viewHolder.textViewDes = (TextView) convertView
//						.findViewById(R.id.des);
//				viewHolder.textViewTomoreCard = (TextView) convertView
//						.findViewById(R.id.tomoreCard);
//				viewHolder.imageView = (ImageView) convertView
//						.findViewById(R.id.img);
//				convertView.setTag(viewHolder);
//			}
//			ImageLoader.getInstance().displayImage(
//					cardItem.getFrontViewImage(), viewHolder.imageView, otp);
//			viewHolder.textViewTitle.setText(cardItem.getCardTitle());
//			viewHolder.textViewDes.setText(cardItem.getCardDes());
//
//			String cardType = cardItem.getCardType();
//
//			if (!cardType.equals("0")) {
//				viewHolder.textViewTomoreCard.setVisibility(View.INVISIBLE);
//			} else if (cardType.equals("0")) {
//				viewHolder.textViewTomoreCard.setVisibility(View.VISIBLE);
//			}
//
//			return convertView;
//		}
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return cardList.size();
//		}
//
//		@Override
//		public Object getItem(int arg0) {
//			// TODO Auto-generated method stub
//			return cardList.get(arg0);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//	}
}



