package com.app.tomore;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import com.app.tomore.GeneralBLActivity.ViewHolder;
import com.app.tomore.beans.FansModel;
import com.app.tomore.beans.GeneralBLModel;
import com.app.tomore.net.UserCenterParse;
import com.app.tomore.net.UserCenterRequest;
import com.app.tomore.net.YellowPageParse;
import com.app.tomore.utils.AppUtil;
import com.app.tomore.utils.PullToRefreshBase;
import com.app.tomore.utils.PullToRefreshListView;
import com.app.tomore.utils.ToastUtils;
import com.app.tomore.utils.PullToRefreshBase.OnLastItemVisibleListener;
import com.app.tomore.utils.PullToRefreshBase.OnRefreshListener;
import com.google.gson.JsonSyntaxException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainFansActivity extends Activity {

	private DialogActivity dialog;
	private Activity mContext;
	private ArrayList<FansModel> fansList;
	private FansModel fansItem;
	private DisplayImageOptions otp;
	FollowingAdapter fansListAdapter;
	private PullToRefreshListView mListView;
	private boolean onRefresh = false;
	private boolean headerRefresh = false; // false -> footer
	private int pageNumber;
	private int limit;
	private TextView noneData;
	private View no_net_lay;
	private LayoutInflater inflater; 
	private View layout;
	private Bitmap bitmap;
	private TextView btnFollow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_fans);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		mContext = this;
		limit = 20;
		pageNumber = 1;
		mListView = (PullToRefreshListView) findViewById(R.id.list);
		mListView.setOnRefreshListener(onRefreshListener);
		mListView.setOnLastItemVisibleListener(onLastItemVisibleListener);
		mListView.setOnItemClickListener(itemClickListener);
		noneData = (TextView) findViewById(R.id.noneData);
		no_net_lay = findViewById(R.id.no_net_lay);
		inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = findViewById(R.id.MainFansLayout);
		otp = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).showImageForEmptyUri(R.drawable.ic_launcher)
				.build();
		TextView header_Text = (TextView) layout.findViewById(R.id.btFans);
//		header_Text.setText(name);
		final Button btnBack = (Button) layout.findViewById(R.id.bar_title_fans_go_back);

		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		new MyFans(MainFansActivity.this, 1).execute("");
	}
	
	private class MyFans extends AsyncTask<String, String, String> {
		private int mType;

		private MyFans(Context context, int type) {
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
			UserCenterRequest request = new UserCenterRequest(MainFansActivity.this);
			//memberID, viewerID, limit, page
			String sLimite = Integer.toString(limit);
			String sPageNumber = Integer.toString(pageNumber);
			try {
				result = request.getFansRequest("25", "34", sLimite, sPageNumber);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
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
				
				if(fansList!=null && fansList.size()!=0)
				{
					if(headerRefresh)
						fansList = new ArrayList<FansModel>();
				}
				else
					fansList = new ArrayList<FansModel>();
				try {
					if(headerRefresh)
						fansList = new UserCenterParse().parseFansResponse(result);
					else
					{
						fansList.addAll(new UserCenterParse().parseFansResponse(result));
					}
					BindDataToListView();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	private void BindDataToListView(){
		if (onRefresh) {
			onRefresh = false;
		}
		if (fansListAdapter == null) {
			fansListAdapter = new FollowingAdapter();
			mListView.setAdapter(fansListAdapter);
			fansListAdapter.notifyDataSetChanged();
		} else {
			fansListAdapter.notifyDataSetChanged();
		}
		if (fansList != null && fansList.size() > 0) {
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
	
	public class FollowingAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fansList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return fansList.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final FansModel fansText = (FansModel) getItem(position);
			ViewHolder viewHolder = null;
			if (convertView != null) {
				viewHolder = (ViewHolder) convertView.getTag();
			} else {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.main_fans_text, null);
				viewHolder.MemberImage = (ImageView) convertView.findViewById(R.id.MemberImage);
				viewHolder.AccountName = (TextView) convertView.findViewById(R.id.AccountName);
				btnFollow = (Button) convertView.findViewById(R.id.Followed);
				convertView.setTag(viewHolder);
			}
			
			String follow = "";
			if(fansText.getFollowed().equals("0")){
				follow = "+关注";
			} else if(fansText.getFollowed().equals("1")){
				follow = "取消关注";
			}
			ImageLoader.getInstance().displayImage(fansText.getMemberImage(), viewHolder.MemberImage, otp);
			viewHolder.AccountName.setText(fansText.getAccountName());
			btnFollow.setText(follow);
			return convertView;
		}
	}

	public void onFollowClick(View view){
		Toast.makeText(getApplicationContext(), "follow", 1).show();
	}
	
	class ViewHolder {
		ImageView MemberImage;
	    TextView AccountName;
	    Button Followed;
	}
	
	public OnRefreshListener<ListView> onRefreshListener = new OnRefreshListener<ListView>() {
		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			if (AppUtil.networkAvailable(mContext)) {
				headerRefresh = true;
				onRefresh = true;
				pageNumber = 1;
				new MyFans(MainFansActivity.this, 1).execute("");
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
				headerRefresh = false;
				pageNumber ++;
				new MyFans(MainFansActivity.this, 1).execute("");
			} else {
				ToastUtils.showToast(mContext, "到头了");
			}
		}
	};
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (!AppUtil.networkAvailable(mContext)) {
				ToastUtils.showToast(mContext, "列表为空");
				return;
			}
			if (fansList == null) {
				return;
			}
			Object obj = (Object) fansList.get(position - 1);
			if (obj instanceof String) {
				return;
			}
			Open_Activity(position-1);
		}
	};
	
	private void Open_Activity(int position) {
		Intent intent;
		intent = new Intent(MainFansActivity.this,
				GeneralBLDetailActivity.class); // Should open fans detail activity
		intent.putExtra("FansData", (Serializable)fansList.get(position));
		startActivityForResult(intent, 100);
	}
}
