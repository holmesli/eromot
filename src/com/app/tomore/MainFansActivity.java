package com.app.tomore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import com.app.tomore.beans.FansModel;
import com.app.tomore.net.UserCenterParse;
import com.app.tomore.net.UserCenterRequest;
import com.app.tomore.utils.PullToRefreshListView;
import com.app.tomore.utils.ToastUtils;
import com.google.gson.JsonSyntaxException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainFansActivity extends Activity {

	private DialogActivity dialog;
	private Activity mContext;
	private ArrayList<FansModel> fansList;
	private FansModel fansItem;
	private DisplayImageOptions otp;
	FansAdapter fansListAdapter;
	private PullToRefreshListView mListView;
	private boolean onRefresh = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_fans);
		mContext = this;
		otp = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).showImageForEmptyUri(R.drawable.ic_launcher)
				.build();
		
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
			try {
				result = request.getFansRequest("25", "34", "10", "1");
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
//			mListView.onRefreshComplete();
			Log.d("onPostExecute", "postExec state");
			if (result == null || result.equals("")) {
				ToastUtils.showToast(mContext, "ÁÐ±íÎª¿Õ");
			} else {
				if(fansList!=null && fansList.size()>0)
				{
					fansList.clear();
				}
				else
				{
					fansList = new ArrayList<FansModel>();
				}
				try {
					fansList = new UserCenterParse().parseFansResponse(result);
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
			fansListAdapter = new FansAdapter();
			mListView.setAdapter(fansListAdapter);
		} else {
			fansListAdapter.notifyDataSetChanged();
		}
		if (fansList != null && fansList.size() > 0) {
			//showDataUi();
		} else {
			//showNoDataUi();
		}
	}
	
	public class FansAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = new ViewHolder();
			fansItem = (FansModel) getItem(position);
			final String imageUrl = fansItem.getMemberImage();
			viewHolder.memberImage = (ImageView) convertView.findViewById(R.id.img);
			ImageLoader.getInstance().displayImage(imageUrl, viewHolder.memberImage, otp);
			viewHolder.accountName = (TextView) convertView.findViewById(R.id.info);
			viewHolder.accountName.setText(fansItem.getAccountName());
			viewHolder.followed = (TextView) convertView.findViewById(R.id.info);
			viewHolder.followed.setText(fansItem.getFollowed());
			viewHolder.blocked = (TextView) convertView.findViewById(R.id.info);
			viewHolder.blocked.setText(fansItem.getBlocked());			
			return convertView;
		}
	}
	
	class ViewHolder {
		ImageView memberImage;
		TextView accountName;
		TextView followed;
		TextView blocked;
	}
}
