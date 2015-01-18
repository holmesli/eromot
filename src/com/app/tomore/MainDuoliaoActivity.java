package com.app.tomore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import com.app.tomore.beans.ThreadCmtModel;
import com.app.tomore.beans.ThreadModel;
import com.app.tomore.fragment.BackToMainActivity;
import com.app.tomore.net.ThreadsParse;
import com.app.tomore.net.ThreadsRequest;
import com.app.tomore.utils.ExpandedListView;
import com.app.tomore.utils.PullToRefreshListView;
import com.app.tomore.utils.SpUtils;
import com.google.gson.JsonSyntaxException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainDuoliaoActivity extends Activity implements OnClickListener {
	private TextView bt1;
	private TextView bt2;
	private TextView bt3;
	private TextView bt4, bt5, bt6, bt7;
	private Context context;
	private ImageButton menubtn;
	private SlidingMenu menu;
	private Activity mContext;
	private DialogActivity dialog;
	private boolean headerRefresh;
	private ArrayList<ThreadModel> threadList;
	//private ArrayList<ThreadCmtModel> commentList;
	private int pageNumber = 1;
	private int limit = 20;
	private PullToRefreshListView mListView;
	DuoliaoAdapter duoliaoAdapter;
	private boolean onRefresh = false;
	private DisplayImageOptions otp;
	protected ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_duoliao_activity);
		mContext = this;
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);

		context = this;
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		View view = LayoutInflater.from(this).inflate(
				R.layout.main_left_fragment, null);
		menu.setMenu(view);
		bt1 = (TextView) view.findViewById(R.id.my_backtomain_bt);
		bt2 = (TextView) view.findViewById(R.id.my_tiezi_bt);
		bt3 = (TextView) view.findViewById(R.id.my_guanzhu_bt);
		bt4 = (TextView) view.findViewById(R.id.my_fensi_bt);
		bt5 = (TextView) view.findViewById(R.id.my_blacklist_bt);
		bt6 = (TextView) view.findViewById(R.id.my_aboutus_bt);
		bt7 = (TextView) view.findViewById(R.id.my_logout_bt);
		menubtn = (ImageButton) findViewById(R.id.ivTitleBtnLeft);
		bt1.setOnClickListener(this);
		bt2.setOnClickListener(this);
		bt3.setOnClickListener(this);
		bt4.setOnClickListener(this);
		bt5.setOnClickListener(this);
		bt6.setOnClickListener(this);
		bt7.setOnClickListener(this);
		menubtn.setOnClickListener(this);
		mListView = (PullToRefreshListView) findViewById(R.id.threadlist);
		new GetData(MainDuoliaoActivity.this, 1).execute("");
		otp = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).showImageForEmptyUri(R.drawable.ic_launcher)
				.build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
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
			onMyFollowingClick(v);
//			Toast.makeText(context, "����3", 1).show();
		} else if (id == R.id.ivTitleBtnLeft) {
			menu.toggle();
		} else if (id == R.id.my_fensi_bt) {
			onMyFansClick(v);
//			Toast.makeText(context, "����1", 1).show();
		} else if (id == R.id.my_blacklist_bt) {
			onMyBlockedClick(v);
//			Toast.makeText(context, "����1", 1).show();
		} else if (id == R.id.my_aboutus_bt) {
			Toast.makeText(context, "����1", 1).show();
		}else if (id == R.id.my_logout_bt) {
			onLogoutClick(v);
		}

	}

	public void onLogoutClick(View view) {

		SpUtils.clearUserInfo(mContext);//退出登录
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	public void onMyFansClick(View view) {
		Intent intent = new Intent(this, MainFansActivity.class);
		startActivity(intent);
	}
	
	public void onMyFollowingClick(View view) {
		Intent intent = new Intent(this, MainFollowingActivity.class);
		startActivity(intent);
	}
	
	public void onMyBlockedClick(View view) {
		Intent intent = new Intent(this, MainBlockedActivity.class);
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
			ThreadsRequest request = new ThreadsRequest(
					MainDuoliaoActivity.this);
			try {
				Log.d("doInBackground", "start request");
				result = request.getThreadList(pageNumber, limit, 25, 0);// for
																			// test
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

				if (threadList != null && threadList.size() != 0) {
					if (headerRefresh)
						threadList = new ArrayList<ThreadModel>();
				} else
					threadList = new ArrayList<ThreadModel>();
				try {
					if (headerRefresh)
						threadList = new ThreadsParse()
								.parseThreadModel(result);
					else {
						threadList.addAll(new ThreadsParse()
								.parseThreadModel(result));
					}
					BindDataToListView();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void BindDataToListView() {
		if (onRefresh) {
			onRefresh = false;
		}
		if (duoliaoAdapter == null) {
			duoliaoAdapter = new DuoliaoAdapter();
			mListView.setAdapter(duoliaoAdapter);
		} else {
			duoliaoAdapter.notifyDataSetChanged();
		}
	}

	private class DuoliaoAdapter extends BaseAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ThreadModel threadItem = (ThreadModel) getItem(position);
			ViewHolder viewHolder = null;
			if (convertView != null) {
				viewHolder = (ViewHolder) convertView.getTag();
			} else {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.duoliao_listview_item, null);
				viewHolder.account_name = (TextView) convertView
						.findViewById(R.id.account_name);
				viewHolder.content = (TextView) convertView
						.findViewById(R.id.content);
				viewHolder.time = (TextView) convertView
						.findViewById(R.id.time);
				viewHolder.comment_num = (TextView) convertView
						.findViewById(R.id.comment_num);
				viewHolder.like_num = (TextView) convertView
						.findViewById(R.id.like_num);
				viewHolder.avatar = (ImageView) convertView
						.findViewById(R.id.avatar);
				viewHolder.content_img = (ImageView) convertView
						.findViewById(R.id.content_img);
				viewHolder.liker_img1 = (ImageView) convertView
						.findViewById(R.id.liker_img1);
				viewHolder.liker_img2 = (ImageView) convertView
						.findViewById(R.id.liker_img2);
				viewHolder.liker_img3 = (ImageView) convertView
						.findViewById(R.id.liker_img3);
				viewHolder.comment_listview = (ExpandedListView) convertView
						.findViewById(R.id.comment_listview);
				
				viewHolder.comment_img1 = (ImageView) convertView
						.findViewById(R.id.comment_img);
				viewHolder.comment_img2 = (ImageView) convertView
						.findViewById(R.id.comment_img2);
				viewHolder.like_img1 = (ImageView) convertView
						.findViewById(R.id.like_img);
				viewHolder.like_img2 = (ImageView) convertView
						.findViewById(R.id.like_img2);

				convertView.setTag(viewHolder);
			}
			imageLoader.displayImage(threadItem.getMemberImage(),
					viewHolder.avatar, otp);

			//get screen width
			Display display = getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			int screenWidth = size.x;
			// change size of content image
			LayoutParams params = (LayoutParams) viewHolder.content_img
					.getLayoutParams();
			params.width = screenWidth;
			params.height = (screenWidth/Integer.parseInt(threadItem.getThreadImageList().get(0).getImageWidth()))
					*Integer.parseInt(threadItem.getThreadImageList().get(0).getImageHeight());
			//params.height = (int) Math.round(Integer.parseInt(threadItem
			//		.getThreadImageList().get(0).getImageHeight()) * 2.5);
			viewHolder.content_img.setLayoutParams(params);
			//

			imageLoader.displayImage(threadItem.getThreadImageList().get(0)
					.getImageUrl(), viewHolder.content_img, otp);
			viewHolder.account_name.setText(threadItem.getAccountName());
			viewHolder.comment_num.setText(String.valueOf(threadItem
					.getThreadCmtList().size()));
			viewHolder.content.setText(threadItem.getThreadContent());
			viewHolder.like_num.setText(String.valueOf(threadItem
					.getThreadLikeList().size()));
			viewHolder.time.setText(threadItem.getTimeDiff());
			
			if(!threadItem.getThreadCmtList().isEmpty())
			{
				ArrayList<ThreadCmtModel> commentList = new ArrayList<ThreadCmtModel>();
				commentList = threadItem.getThreadCmtList();
				
	
				DuoliaoCommentAdapter duoliaoCommentAdapter = new DuoliaoCommentAdapter(commentList);
				viewHolder.comment_listview.setAdapter(duoliaoCommentAdapter);
				duoliaoCommentAdapter.notifyDataSetChanged();
				viewHolder.comment_img2.setVisibility(View.VISIBLE);
			}
			else
			{
				viewHolder.comment_img2.setVisibility(View.GONE);
			}
			
			if(threadItem.getThreadLikeList().isEmpty())
			{
				viewHolder.like_img2.setVisibility(View.GONE);
			}
			else
			{
				viewHolder.like_img2.setVisibility(View.VISIBLE);
				//display liker list view
			}
			
			return convertView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return threadList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return threadList.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	private class DuoliaoCommentAdapter extends BaseAdapter {
		
		private ArrayList<ThreadCmtModel> commentList;
		public DuoliaoCommentAdapter(ArrayList<ThreadCmtModel> list)
		{
			super();
			commentList = list;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ThreadCmtModel commentItem = (ThreadCmtModel) getItem(position);
			ViewHolder viewHolder = null;
			if (convertView != null) {
				viewHolder = (ViewHolder) convertView.getTag();
			} else {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.duoliao_listview_item_comment_listview_item,
						null);
				viewHolder.comment_accountName = (TextView) convertView
						.findViewById(R.id.account_name);
				viewHolder.comment_content = (TextView) convertView
						.findViewById(R.id.content);
				viewHolder.comment_avatar = (ImageView) convertView
						.findViewById(R.id.avatar);
				convertView.setTag(viewHolder);
			}
			imageLoader.displayImage(commentItem.getMemberImage(),
					viewHolder.comment_avatar, otp);

			viewHolder.comment_accountName
					.setText(commentItem.getAccountName());
			viewHolder.comment_content.setText(commentItem.getCommentContent());
			
			// change size of comment list view
//			int commentHeight;
//			commentHeight = viewHolder.comment_content.getHeight()+viewHolder.comment_accountName.getHeight();
//			LayoutParams params2 = (LayoutParams) ((ListView)convertView
//					.findViewById(R.id.comment_listview)).getLayoutParams();
//			params2.height += commentHeight;
//			viewHolder.comment_listview.setLayoutParams(params2);
			//

			return convertView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return commentList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return commentList.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	class ViewHolder {
		TextView account_name;
		TextView content;
		TextView time;
		TextView comment_num;
		TextView like_num;
		ImageView avatar;
		ImageView content_img;
		ImageView liker_img1;
		ImageView liker_img2;
		ImageView liker_img3;
		ImageView line;
		ExpandedListView comment_listview;
		ImageView comment_avatar;
		TextView comment_accountName;
		TextView comment_content;
		ImageView comment_img1;
		ImageView comment_img2;
		ImageView like_img1;
		ImageView like_img2;
		
	}
}
