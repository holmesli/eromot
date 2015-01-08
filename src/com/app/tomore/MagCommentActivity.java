package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import org.json.JSONException;

import com.app.tomore.beans.ArticleCommentModel;
import com.app.tomore.beans.ArticleModel;
import com.app.tomore.net.MagParse;
import com.app.tomore.net.MagRequest;
import com.google.gson.JsonSyntaxException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.app.tomore.R;
import com.app.tomore.utils.AppUtil;
import com.app.tomore.utils.ToastUtils;
import com.app.tomore.utils.PullToRefreshListView;
import com.app.tomore.utils.PullToRefreshBase;
import com.app.tomore.utils.PullToRefreshBase.OnLastItemVisibleListener;
import com.app.tomore.utils.PullToRefreshBase.OnRefreshListener;

import android.annotation.SuppressLint;
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
public class MagCommentActivity extends Activity {
	private DialogActivity dialog;
	private ArrayList<ArticleCommentModel> articleComment;
	private ArticleCommentModel articleComentModel;
	private DisplayImageOptions otp;
	private PullToRefreshListView mListView;
	private Activity mContext;
	private TextView noneData;
	private View no_net_lay;
	ArticleAdapter articleListAdapter;
	private boolean onRefresh = false;
	private String articleId;
	private String memberId="34";
	private String page="1";
	private String limit="10";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mag_comment_listview);
		mContext = this;
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		new GetData(MagCommentActivity.this, 1).execute("");
		otp = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).showImageForEmptyUri(R.drawable.ic_launcher)
				.build();
		ImageLoader.getInstance().init(
				ImageLoaderConfiguration.createDefault(this));

			mListView = (PullToRefreshListView) findViewById(R.id.mag_comment_listviews);
			mListView.setOnRefreshListener(onRefreshListener);
			mListView.setOnLastItemVisibleListener(onLastItemVisibleListener);
			noneData = (TextView)findViewById(R.id.noData);
			no_net_lay = findViewById(R.id.no_net_lay);
			Button reloadData = (Button)findViewById(R.id.reloadData);
			reloadData.setOnClickListener(reloadClickListener);
			
			
			Intent intent=getIntent();
			articleId=intent.getStringExtra("articleid");
			
			
			Button postComment = (Button)findViewById(R.id.bar_title_bt_postcomment);
			postComment.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(); 
					if(memberId==null)
					{
						intent.setClass(MagCommentActivity.this, LoginActivity.class);
						startActivity(intent);
					}
					else
					{
						intent.putExtra("articleCommentId", articleId);
						intent.putExtra("memberid", memberId);
			            intent.setClass(MagCommentActivity.this, PostCommentActivity.class);  
			            startActivityForResult(intent, 100);
					}
					
				}
			});
			
			RelativeLayout rl = (RelativeLayout) getWindow().getDecorView()
					.findViewById(R.id.bar_title_commentlistbar);
			final Button btnBack = (Button) rl
					.findViewById(R.id.bar_title_bt_detail);

			btnBack.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					finish();
				}
			});
            
	
	}

	private void BindDataToListView() {
		if (articleListAdapter == null) {
			articleListAdapter = new ArticleAdapter();
			mListView.setAdapter(articleListAdapter);
		} else {
			articleListAdapter.notifyDataSetChanged();
		}
		if(articleComment!=null && articleComment.size()>0){
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
			MagRequest request = new MagRequest(MagCommentActivity.this);
			try {
				
				Log.d("doInBackground", "start request");
				result = request.getCommentByArticleId(articleId, page, limit);
				Log.d("doInBackground", "returned");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}

			return result;
		}

		protected void onPostExecute(String result) {
			if (null != dialog) {
				dialog.dismiss();
			}
			mListView.onRefreshComplete();
			Log.d("onPostExecute", "postExec state");
			if (result == null || result.equals("")) {
				ToastUtils.showToast(mContext, "列表为空");
			} else {
				if(articleComment!=null && articleComment.size()>0)
				{
					articleComment.clear();
					//articleComment = new ArrayList<ArticleCommentModel>();
				}
				else
				{
					articleComment = new ArrayList<ArticleCommentModel>();
				}
				try {
					articleComment = new MagParse().parseArticleComment(result);
					BindDataToListView();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
			}
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			mListView.onRefreshComplete();
		}
	};

	public OnRefreshListener<ListView> onRefreshListener = new OnRefreshListener<ListView>() {
		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			if(AppUtil.networkAvailable(mContext) ){
				onRefresh = true;
					new GetData(MagCommentActivity.this, 1).execute("");

			}else{
				ToastUtils.showToast(mContext, "没有网络");
				mListView.onRefreshComplete();
			}
		}
	};

	private OnLastItemVisibleListener onLastItemVisibleListener = new OnLastItemVisibleListener() {
		@Override
		public void onLastItemVisible() {
			if(AppUtil.networkAvailable(mContext)){
				onRefresh = true;
					new GetData(MagCommentActivity.this, 1).execute("");

				
			}else{
				ToastUtils.showToast(mContext, "没有网络");
			}
		}
	};

	OnClickListener reloadClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			onRefresh = true;

			new GetData(MagCommentActivity.this, 1).execute("");
		}
	};
	
	class ViewHolder {
		TextView textViewTitle;
		TextView textViewComment;
		TextView TimeDiff;
		ImageView imageView;
	}
	
	private class ArticleAdapter extends BaseAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = new ViewHolder();
			articleComentModel = (ArticleCommentModel) getItem(position);
			final String speakerName = articleComentModel.getAccountName();
			final String content = articleComentModel.getCommentContent();
			final String time = articleComentModel.getTimeDiff();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.comment_list_item, null);      
			viewHolder.textViewTitle = (TextView) convertView
					.findViewById(R.id.speakerName);
			viewHolder.textViewTitle.setText(speakerName);
			
			viewHolder.textViewComment = (TextView) convertView
					.findViewById(R.id.content);
			viewHolder.textViewComment.setText(content);
			
			viewHolder.TimeDiff = (TextView) convertView
					.findViewById(R.id.time);
			viewHolder.TimeDiff.setText(time);

			return convertView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return articleComment.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
		 return articleComment.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}
}


