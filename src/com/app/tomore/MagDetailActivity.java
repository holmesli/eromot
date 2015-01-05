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
import com.google.gson.JsonSyntaxException;
import com.app.tomore.utils.AndroidShare;
import com.app.tomore.utils.TouchImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.widget.VideoView;

public class MagDetailActivity extends Activity {

	private DialogActivity dialog;
	private ArticleModel articleItem;
	
	private VideoView myVideoView;
	private int position = 0;
	private ProgressDialog progressDialog;
	private MediaController mediaControls;
	
	private TouchImageView detailImage;
	private TextView testView1;
	private TextView testView2;
	private WebView detailWeb;
	private TextView detailTitle;
	private FrameLayout frame;
	
	private ImageView backImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.magdetail);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		
		detailTitle = (TextView) getWindow().getDecorView().findViewById(R.id.news_title_text);
		detailImage = (TouchImageView)getWindow().getDecorView().findViewById(R.id.news_image);
		detailWeb = (WebView)findViewById(R.id.news_content_text);
		//testView1 = (TextView) getWindow().getDecorView().findViewById(R.id.testweb);
		//testView2 = (TextView) getWindow().getDecorView().findViewById(R.id.testweb2);
		myVideoView = (VideoView) findViewById(R.id.videoView);
		frame = (FrameLayout) findViewById(R.id.videoFrame);
		

		
		RelativeLayout rl = (RelativeLayout) getWindow().getDecorView()
				.findViewById(R.id.bar_title_mag_detail);
		
		findViewById(R.id.bar_title_bt_share).setOnClickListener(new OnClickListener() {
			
			String title = articleItem.getArticleTitle();

			public void onClick(View v) {
				AndroidShare as = new AndroidShare(
						MagDetailActivity.this,title,"http://img6.cache.netease.com/cnews/news2012/img/logo_news.png");
//						"我正在使用多伦多最潮的ToMore应用，快来看看吧 www.tomoreapp.com",
//						"http://img6.cache.netease.com/cnews/news2012/img/logo_news.png");
				as.show();
			}
		});
		
		Intent intent = getIntent();
		articleItem = (ArticleModel) intent.getSerializableExtra("articleList");
		BindData();
		GetVideo();
		new GetData(MagDetailActivity.this, 1).execute("");
		
		final Button btnBack = (Button) rl
				.findViewById(R.id.bar_title_bt_mag);

		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

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
		String webUrl = articleItem.getArticleContent();
		detailWeb.setWebChromeClient(new WebChromeClient());
		detailWeb.loadUrl(articleItem.getArticleContent());
		detailWeb.getSettings().setJavaScriptEnabled(true);
		detailWeb.loadDataWithBaseURL(null,webUrl,
	    "text/html", "utf-8",null );
		detailWeb.setWebChromeClient(new WebChromeClient()); 
		detailTitle.setText(articleItem.getArticleTitle());
		Picasso.with(MagDetailActivity.this).load(articleItem.getArticleLargeImage()).into(detailImage);
//		String videoUrl = articleItem.getVideoUrl();
//		String textUrl = articleItem.getTextUrl();
//		
		//testView1.setText(videoUrl);
		//testView2.setText(textUrl);
		String video = articleItem.getArticleVideo();
		if(video.equals(""))
		{
			myVideoView.setVisibility(View.INVISIBLE);
			frame.setVisibility(View.GONE);
//			testView1.setVisibility(View.INVISIBLE);
//			testView2.setVisibility(View.INVISIBLE);
			detailImage.setVisibility(View.VISIBLE);
		}
		else
		{
			frame.setVisibility(View.VISIBLE);
			myVideoView.setVisibility(View.VISIBLE);
//			testView1.setVisibility(View.VISIBLE);
//			testView2.setVisibility(View.VISIBLE);
			detailImage.setVisibility(View.GONE);
			
		}

	}
	
	public void GetVideo()
	{
		if (mediaControls == null) {
			mediaControls = new MediaController(MagDetailActivity.this);
		}

		// Find your VideoView in your video_main.xml layout
		//myVideoView = (VideoView) findViewById(R.id.videoView);

		// Create a progressbar
		progressDialog = new ProgressDialog(MagDetailActivity.this);
		// Set progressbar title
//		progressDialog.setTitle("JavaCodeGeeks Android Video View Example");
//		// Set progressbar message
//		progressDialog.setMessage("Loading...");
//
//		progressDialog.setCancelable(true);
//		// Show progressbar
//		progressDialog.show();

		try {
			myVideoView.setMediaController(mediaControls);
			myVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.kitkat));

		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}

		myVideoView.requestFocus();
		myVideoView.setOnPreparedListener(new OnPreparedListener() {
			// Close the progress bar and play the video
			public void onPrepared(MediaPlayer mp) {
				progressDialog.dismiss();
				myVideoView.seekTo(position);
				if (position == 0) {
					myVideoView.start();
				} else {
					myVideoView.pause();
				}
			}
		});
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
		myVideoView.pause();
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		position = savedInstanceState.getInt("Position");
		myVideoView.seekTo(position);
	}
		
	
	
}
