package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;
import com.app.tomore.utils.SpUtils;
import org.json.JSONException;

import com.app.tomore.beans.ArticleCommentModel;
import com.app.tomore.beans.ArticleModel;
import com.app.tomore.beans.BLRestaurantModel;
import com.app.tomore.net.MagParse;
import com.app.tomore.net.MagRequest;
import com.app.tomore.net.YellowPageParse;
import com.google.gson.JsonSyntaxException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.app.tomore.R;
import com.app.tomore.utils.AppUtil;
import com.app.tomore.utils.PullToRefreshBase.OnLastRefreshListener;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
public class MainMagActivity extends Activity {
	private DialogActivity dialog;
	private ArrayList<ArticleModel> articleList;
	private ArticleModel articleItem;
	private DisplayImageOptions otp;
	private PullToRefreshListView mListView;
	private Activity mContext;
	private TextView noneData;
	private View no_net_lay;
	ArticleAdapter articleListAdapter;
	private boolean onRefresh = false;
	private boolean headerRefresh = false;
	private String magId="0";
	private String categoryID;
	private String pre;
	private String next;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_mag_activity);
		mContext = this;
		String userId = SpUtils.getUserId(MainMagActivity.this);
		Intent i = getIntent();
		categoryID = i.getStringExtra("categoryId");
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		new GetData(MainMagActivity.this, 1).execute("");
		otp = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).showImageForEmptyUri(R.drawable.ic_launcher)
				.build();
		ImageLoader.getInstance().init(
				ImageLoaderConfiguration.createDefault(this));

			mListView = (PullToRefreshListView) findViewById(R.id.mag_listviews);
			mListView.setOnRefreshListener(onRefreshListener);
			mListView.setOnLastItemVisibleListener(onLastItemVisibleListener);
			mListView.setOnItemClickListener(itemClickListener);
			noneData = (TextView)findViewById(R.id.noneData);
			no_net_lay = findViewById(R.id.no_net_lay);
			Button reloadData = (Button)findViewById(R.id.reloadData);
			reloadData.setOnClickListener(reloadClickListener);
			
			
			RelativeLayout rl = (RelativeLayout) getWindow().getDecorView()
					.findViewById(R.id.bar_title_allmag);
			final Button btnBack = (Button) rl
					.findViewById(R.id.bar_title_bt_backtocategory);

			btnBack.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					finish();
				}
			});
			
	
	}

	private void BindDataToListView() {
		if (onRefresh) {
			onRefresh = false;
		}
		if (articleListAdapter == null) {
			articleListAdapter = new ArticleAdapter();
			mListView.setAdapter(articleListAdapter);
		} else {
			articleListAdapter.notifyDataSetChanged();
		}
		if(articleList!=null && articleList.size()>0){
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
			MagRequest request = new MagRequest(MainMagActivity.this);
			try {
				
				Log.d("doInBackground", "start request");			
					result = request.getMagById(magId,categoryID);
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
				if(articleList!=null && articleList.size()>0)
				{
					if(headerRefresh)
						articleList = new ArrayList<ArticleModel>();
				}
				else
				{
					articleList = new ArrayList<ArticleModel>();
				}
				try {
						next = new MagParse().parseNext(result);
						pre = new MagParse().parsePre(result);
					articleList = new MagParse().parseArticleResponse(result);

					BindDataToListView();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) { 
			if(!AppUtil.networkAvailable(mContext)){
				ToastUtils.showToast(mContext, "列表为空");
				return;
			}
			if (articleList == null) {
				return;
			}
			Object obj = (Object) articleList.get(position-1);
			if (obj instanceof String) {
				return;
			}

			Intent intent = new Intent(MainMagActivity.this,
					MagDetailActivity.class);
			intent.putExtra("articleList", (Serializable) obj);
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
			if(AppUtil.networkAvailable(mContext) ){
				onRefresh = true;
				headerRefresh = true;
				if(next.equals("0")||next==null)
				{
					Toast.makeText(getApplicationContext(), "到头了，休息一下~", Toast.LENGTH_SHORT).show();
				}
				else
				{
					magId=next;
				}
				
				new GetData(MainMagActivity.this, 1).execute("");

			}else{
				ToastUtils.showToast(mContext, "到头了");
				mListView.onRefreshComplete();
			}
		}
	};

	private OnLastItemVisibleListener  onLastItemVisibleListener = new OnLastItemVisibleListener() {
		@Override
		public void onLastItemVisible() {
			if(AppUtil.networkAvailable(mContext)){

				headerRefresh = false;
				if(pre.equals("0")||next==null)
				{
					Toast.makeText(getApplicationContext(), "到头了，休息一下~", Toast.LENGTH_SHORT).show();
				}
				else
				{
					magId=pre;
				}
				
				new GetData(MainMagActivity.this, 1).execute("");

			}else{
				ToastUtils.showToast(mContext, "到头了");
			}
		}

		
	};

	OnClickListener reloadClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//onRefresh = true;
			new GetData(MainMagActivity.this, 1).execute("");
		}
	};
	
	class ViewHolder {
		TextView textViewTitle;
		TextView textViewDes;
		TextView textViewTomoreCard;
		ImageView imageView;
	}
	
	private class ArticleAdapter extends BaseAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = new ViewHolder();
			articleItem = (ArticleModel) getItem(position);
			final String imageUrl = articleItem.getArticleSmallImage();
			final String imagePosition = articleItem.getImagePosition();
			if (imagePosition.equals("2")) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.mag_listview, null);
			} 
			else if(imagePosition.equals("1")) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.mag_largeicon_listview_item, null);
			}        
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.img);
			
			ImageLoader.getInstance().displayImage(imageUrl,
					viewHolder.imageView, otp);

			viewHolder.textViewTitle = (TextView) convertView
					.findViewById(R.id.info);
			viewHolder.textViewTitle.setText(articleItem.getArticleTitle());

			return convertView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return articleList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
		 return articleList.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}
	
	
}


