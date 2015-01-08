package com.app.tomore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.app.tomore.beans.BLMenuModel;
import com.app.tomore.beans.ImageAndText;
import com.app.tomore.adapters.ImageAndTextListAdapter;
import com.app.tomore.net.*;
import com.app.tomore.utils.TouchImageView;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;

import android.widget.*;
import com.nostra13.universalimageloader.core.DisplayImageOptions;



public class RestaurantDetailActivity extends Activity{
	
	private DialogActivity dialog;
	private BLMenuModel MenuItem;
	private ArrayList<BLMenuModel> menulist;
	private TouchImageView MenudetailImage;
	private TextView MenutestView;
	private FrameLayout frame;
	private DisplayImageOptions otp;
	private Activity mContext;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_detail_layout);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		new GetData(RestaurantDetailActivity.this, 1).execute("");
		otp = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).showImageForEmptyUri(R.drawable.ic_launcher)
				.build();
		mContext = this;

	}
	
	private class GetData extends AsyncTask<String, String, String>{
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
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			//http://54.213.167.5/APIV2/getItemsByRestID.php?restid=48&page=1&limit=10
			String result = null;
			YellowPageRequest request = new YellowPageRequest(RestaurantDetailActivity.this);
			   String restid="48";
			   try{
				Log.d("doInBackground", "start request");
				 result = request.getRestaurantDetail(restid);
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
				//cardList = new ArrayList<CardModel>();
				try {
					menulist = new YellowPageParse().parseRestaurantDetailResponse(result);
					BindDataToGridView();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
				if (MenuItem != null) {

				} else {
					// show empty alert
				}
			}
	}
	
	}
	class ViewHolder {
		TextView textViewTitle;
		ImageView imageView;
		TouchImageView MenudetailImage;
	}
	private void BindDataToGridView(){
		final List<ImageAndText> imageAndTextList = new ArrayList<ImageAndText>();
		for(BLMenuModel c: menulist)
		{
			imageAndTextList.add(new ImageAndText(c.getItemImage(), c.getItemName()));
		}
		GridView gridView = (GridView) findViewById(R.id.menugridView);
		gridView.setAdapter(new ImageAndTextListAdapter(this, imageAndTextList,
				gridView));
		
	}
}
