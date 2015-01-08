package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.app.tomore.adapters.ArticleAdapter;
import com.app.tomore.beans.ArticleCommentModel;
import com.app.tomore.beans.ArticleModel;
import com.app.tomore.beans.CardModel;
import com.app.tomore.beans.ImageAndText;
import com.app.tomore.beans.ImageAndTexts;
import com.app.tomore.net.ToMoreHttpRequest;
import com.app.tomore.net.ToMoreParse;
import com.google.gson.JsonSyntaxException;
import com.app.tomore.utils.AndroidShare;
import com.app.tomore.utils.AppUtil;
import com.app.tomore.utils.ToastUtils;
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
	private Activity mContext;
	private Button commentButton;
	private ArrayList<ArticleCommentModel> articleComment;
	
	
	private ImageView backImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.magdetail);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		
		detailTitle = (TextView) getWindow().getDecorView().findViewById(R.id.news_title_text);
		detailImage = (TouchImageView)getWindow().getDecorView().findViewById(R.id.news_image);
		detailWeb = (WebView)findViewById(R.id.news_content_text);
		myVideoView = (VideoView) findViewById(R.id.videoView);
		frame = (FrameLayout) findViewById(R.id.videoFrame);
		RelativeLayout rl = (RelativeLayout) getWindow().getDecorView()
				.findViewById(R.id.bar_title_mag_detail);
		commentButton = (Button)findViewById(R.id.bar_title_bt_share);
		commentButton.setOnClickListener(new buttonComment());
			
		//findViewById(R.id.bar_title_bt_share).setOnClickListener((OnClickListener) itemClickListener );
//		{
			
			//String title = articleItem.getArticleTitle();
//
//			public void onClick(View v) {
//				AndroidShare as = new AndroidShare(
//						MagDetailActivity.this,
//						"我正在使用多伦多最潮的ToMore应用，快来看看吧 www.tomoreapp.com",
//						"http://img6.cache.netease.com/cnews/news2012/img/logo_news.png");
//				as.show();
//			}
//		});
		
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
	
	private class buttonComment implements OnClickListener  
    {  
        public void onClick(View v)  
        {  
            Intent intent=new Intent(MagDetailActivity.this,MagCommentActivity.class);   
			intent.putExtra("articleid", articleItem.getArticleID());
            startActivity(intent);  
        }  
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
				Log.d("doInBackground", "start request");	
				Log.d("doInBackground", "returned");

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
				try {
					BindData();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
				if (articleItem != null) {
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

		String video = articleItem.getArticleVideo();
		if(video.equals(""))
		{
			myVideoView.setVisibility(View.INVISIBLE);
			frame.setVisibility(View.GONE);

			detailImage.setVisibility(View.VISIBLE);
		}
		else
		{
			frame.setVisibility(View.VISIBLE);
			myVideoView.setVisibility(View.VISIBLE);

			detailImage.setVisibility(View.GONE);
			
		}

	}
	
	public void GetVideo()
	{
		if (mediaControls == null) {
			mediaControls = new MediaController(MagDetailActivity.this);
		}

		// Find your VideoView in your video_main.xml layout

		// Create a progressbar
		progressDialog = new ProgressDialog(MagDetailActivity.this);


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
