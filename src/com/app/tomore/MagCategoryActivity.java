package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.json.JSONException;

import com.app.tomore.beans.ArticleCatogoryModel;
import com.app.tomore.beans.BLRestaurantModel;
import com.app.tomore.beans.CategoryModel;
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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
public class MagCategoryActivity extends Activity {
	private DialogActivity dialog;
	private ArrayList<ArticleCatogoryModel> articleList;
	private ArticleCatogoryModel articleCatogory;
	private DisplayImageOptions otp;
	private ListView mListView;
	private Activity mContext;
	private TextView noneData;
	private View no_net_lay;
	ArticleAdapter articleListAdapter;
	private boolean onRefresh = false;
	private boolean headerRefresh = false;
	private boolean footerRefresh = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.magcategory_listview);
		mContext = this;
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		new GetData(MagCategoryActivity.this, 1).execute("");
		//ListView listView = (ListView) findViewById(R.id.mag_listviews);
		otp = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).showImageForEmptyUri(R.drawable.ic_launcher)
				.build();
		ImageLoader.getInstance().init(
				ImageLoaderConfiguration.createDefault(this));

			mListView = (ListView) findViewById(R.id.magcategory_listviews);
			mListView.setOnItemClickListener(itemClickListener);
			noneData = (TextView)findViewById(R.id.noneData);
			no_net_lay = findViewById(R.id.no_net_lay);

	
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
			MagRequest request = new MagRequest(MagCategoryActivity.this);
			try {
				
				Log.d("doInBackground", "start request");			
					result = request.getAllArticleCategories();
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
			//mListView.onRefreshComplete();
			Log.d("onPostExecute", "postExec state");
			if (result == null || result.equals("")) {
				ToastUtils.showToast(mContext, "列表为空");
			} else {
				if(articleList!=null && articleList.size()>0)
				{
					articleList.clear();
				}
				else
				{
					articleList = new ArrayList<ArticleCatogoryModel>();
				}
				try {
						articleList = new MagParse().parseCtegoryResponse(result);
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

			Intent intent = new Intent(MagCategoryActivity.this,
					MainMagActivity.class);
			ArticleCatogoryModel item = articleList.get(position);
			intent.putExtra("categoryId", item.getCategoryID());
			startActivity(intent);

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
			articleCatogory = (ArticleCatogoryModel) getItem(position);
			final String imageUrl = articleCatogory.getCategoryImage();
			//final String imagePosition = articleItem.getImagePosition();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.magcategory_item, null);    
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.categoryImg);
			
			ImageLoader.getInstance().displayImage(imageUrl,
					viewHolder.imageView, otp);

			viewHolder.textViewTitle = (TextView) convertView
					.findViewById(R.id.categoryInfo);
			viewHolder.textViewTitle.setText(articleCatogory.getCategoryName());

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
