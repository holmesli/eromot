package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.app.tomore.adapters.ArticleAdapter;
import com.app.tomore.beans.ArticleModel;
import com.app.tomore.beans.CardModel;
import com.app.tomore.beans.ImageAndText;
import com.app.tomore.beans.ImageAndTexts;
import com.app.tomore.net.ToMoreHttpRequest;
import com.app.tomore.net.ToMoreParse;
import com.app.tomore.util.TouchImageView;
import com.google.gson.JsonSyntaxException;
import com.app.tomore.utils.AndroidShare;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.VideoView;

public class MagDetailActivity extends Activity {

	private DialogActivity dialog;
	private ArticleModel articleItem;
	public VideoView vidPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.magdetail);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		
		
		findViewById(R.id.bar_title_bt_share).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AndroidShare as = new AndroidShare(
						MagDetailActivity.this,
						"我正在使用多伦多最潮的ToMore应用，快来看看吧 www.tomoreapp.com",
						"http://img6.cache.netease.com/cnews/news2012/img/logo_news.png");
				as.show();
			}
		});
		
		Intent intent = getIntent();
		articleItem = (ArticleModel) intent.getSerializableExtra("articleList");
		BindData();
		new GetData(MagDetailActivity.this, 1).execute("");
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
			String result= null;
			//try {
				Log.d("doInBackground", "start request");	
				Log.d("doInBackground", "returned");
			//} 
//			catch (IOException e) {
//				e.printStackTrace();
//			} catch (TimeoutException e) {
//				e.printStackTrace();
//			}

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
					//cardList = new CardsParse().parseCardResponse(result);
					//BindDataToListView();
					BindData();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
				if (articleItem != null) {
					//Intent intent = new Intent(MemberDetailActivity.this,
						//	MyCameraActivity.class); // fake redirect..
					//intent.putExtra("cardList", (Serializable) cardList);
					//startActivity(intent);
				} else {
					// show empty alert
				}
			}
		}
	}
	
	private void BindData()
	{
		TextView detailTitle = (TextView) getWindow().getDecorView()
				.findViewById(R.id.news_title_text);
		TouchImageView detailImage = (TouchImageView)getWindow().getDecorView().findViewById(R.id.news_image);
		WebView detailWeb = (WebView)findViewById(R.id.news_content_text);
		//VideoView detailView = (VideoView)findViewById(R.id.videoView);
		//String video = articleItem.getArticleVideo();
		String webUrl = articleItem.getArticleContent();
		//Uri uri = Uri.parse(articleItem.getArticleVideo());
		detailWeb.setWebChromeClient(new WebChromeClient());
		detailWeb.loadUrl(articleItem.getArticleContent());
		detailWeb.getSettings().setJavaScriptEnabled(true);


//			MediaController videoMediaController = new MediaController(this);
//		    videoMediaController.setMediaPlayer(detailView);
//			detailView.setVideoURI(uri);
//		      detailView.setMediaController(videoMediaController);
//		      detailView.requestFocus();
//		      detailView.start();
//		      super.onStart();

		detailWeb.getSettings().setJavaScriptEnabled(true);
		detailWeb.loadDataWithBaseURL(null,webUrl,
	    "text/html", "utf-8",null );
		detailWeb.setWebChromeClient(new WebChromeClient()); 
		detailTitle.setText(articleItem.getArticleTitle());

		Picasso.with(MagDetailActivity.this).load(articleItem.getArticleLargeImage()).into(detailImage);
		
	}
	
}
