package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.widget.VideoView;

import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class MagDetailActivity extends Activity {

	private DialogActivity dialog;
	private ArticleModel articleItem;
	
	private VideoView myVideoView;
	private int position = 0;
	private ProgressDialog progressDialog;
	private MediaController mediaControls;
	private AlertDialog alertDialog;
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
	
	private Button button;
	final UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.share");
	
	
	private String[] allOptionsMenuTexts = {"评论","分享"};  
	   private int[] allOptionsMenuOrders = {2,6};  
	   private int[] allOptionsMenuIds = {Menu.FIRST+2,Menu.FIRST+6};  
	   private int[] allOptionsMenuIcons = {   
	        android.R.drawable.ic_menu_edit,  
	        android.R.drawable.ic_menu_send,  
	        };  

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
			
		Intent intent = getIntent();
		articleItem = (ArticleModel) intent.getSerializableExtra("articleList");
		BindData();
		GetVideo();
		
		new GetData(MagDetailActivity.this, 1).execute("");
//		init();
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
        	showDialog8();
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
		
	
	public void showDialog8(){  
	    final Context context = this;  
	       
	    LayoutInflater layoutInflater = getLayoutInflater();  
	    View menuView = layoutInflater.inflate(R.layout.group_list, null);  
	    
	    GridView gridView = (GridView)menuView.findViewById(R.id.gridview);  
	    SimpleAdapter menuSimpleAdapter = createSimpleAdapter(allOptionsMenuTexts,allOptionsMenuIcons);  
	    gridView.setAdapter(menuSimpleAdapter);  
	    
	    AlertDialog.Builder builder ;
	    builder = new AlertDialog.Builder(context);  
        builder.setView(menuView);  
        //alertDialog = builder.create();  
        alertDialog=builder.show();  
	    gridView.setOnItemClickListener(new OnItemClickListener(){  
	        @Override  
	        public void onItemClick(AdapterView<?> parent, View view,  
	                int position, long id) {  
	        	if(position==0)
	        	{
	        		
	        		Intent intent=new Intent(MagDetailActivity.this,MagCommentActivity.class);   
	    			intent.putExtra("articleid", articleItem.getArticleID());
	                startActivity(intent);  
	                alertDialog.dismiss();
	                //finish();
	        	}
	        	else if(position==1)
	        	{
	        		AndroidShare as = new AndroidShare(
	        				MagDetailActivity.this,
	        				"你正在使用多伦多最潮的APP，快来看看吧",
	        				"www.tomoreapp.com");
	        		as.show();
	        		alertDialog.dismiss();
//	        		mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
//	    					SHARE_MEDIA.DOUBAN);
//	    			//Ĭ�Ϸ��?ʽ
//	    			mController.openShare(MagDetailActivity.this, null);
	        				
	        	}

	        }  
	    });  
	   // new AlertDialog.Builder(context).setView(menuView).show();  
	}  
	  
	public SimpleAdapter createSimpleAdapter(String[] menuNames,int[] menuImages){  
	    List<Map<String,?>> data = new ArrayList<Map<String,?>>();  
	    String[] fromsAdapter = {"item_text","item_image"};  
	    int[] tosAdapter = {R.id.item_text,R.id.item_image};  
	    for(int i=0;i<menuNames.length;i++){  
	        Map<String,Object> map = new HashMap<String,Object>();  
	        map.put(fromsAdapter[0], menuNames[i]);  
	        map.put(fromsAdapter[1], menuImages[i]);  
	        data.add(map);  
	    }  
	      
	    SimpleAdapter SimpleAdapter = new SimpleAdapter(this, data, R.layout.group_item_view, fromsAdapter, tosAdapter);  
	    return SimpleAdapter;  
	}  
	
	
	
	
//	private void init() {
//		// ���÷�������
//		mController
//				.setShareContent("快来看看多伦多最潮的ToMore App, www.tomoreapp.com");
//		// ���÷���ͼƬ, ����2ΪͼƬ��url��ַ
//		mController.setShareMedia(new UMImage(this, R.drawable.tomorelogo));
////--------------------------------------------------------------------------------------------------------
////		 ����1Ϊ��ǰActivity������2Ϊ��������QQ���������APP ID������3Ϊ��������QQ���������APP kEY.���Լ����룩
//		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1103520645",
//				"uIveeHinob7PhSGv");
//		qqSsoHandler.addToSocialSDK();
//		QQShareContent qqShareContent = new QQShareContent();
//		// ���÷�������
//		qqShareContent.setShareContent("����������ữ�����SDK�����ƶ�Ӧ�ÿ�������罻���?�� -- QQ");
//		// ���÷���title
//		qqShareContent.setTitle("hello, title");
//		// ���÷���ͼƬ
//		qqShareContent.setShareImage(new UMImage(this, R.drawable.tomorelogo));
//		// ���õ���������ݵ���ת����
//		qqShareContent.setTargetUrl("http://www.baidu.com");
//		mController.setShareMedia(qqShareContent);
////--------------------------------------------------------------------------------------------------------
////		 ��������SSO handler
//		mController.getConfig().setSsoHandler(new SinaSsoHandler());
////--------------------------------------------------------------------------------------------------------
////		 ���΢�ŵ�appID appSecretҪ�Լ�����
//		String appID = "wxc9197d3be76aca03";
//		String appSecret = "9c253edcab52fdb8458c99ec798c3c91";
//		// ���΢��ƽ̨
//		UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
//		wxHandler.addToSocialSDK();
//		// ����΢�ź��ѷ�������
//		WeiXinShareContent weixinContent = new WeiXinShareContent();
//		// ���÷�������
//		weixinContent.setShareContent("����������ữ�����SDK�����ƶ�Ӧ�ÿ�������罻���?�ܣ�΢��");
//		// ����title
//		weixinContent.setTitle("������ữ�������-΢��");
//		// ���÷���������תURL
//		weixinContent.setTargetUrl("http://www.baidu.com");
//		// ���÷���ͼƬ
//		weixinContent.setShareImage(new UMImage(getApplicationContext(),
//				R.drawable.tomorelogo));
//		mController.setShareMedia(weixinContent);
////--------------------------------------------------------------------------------------------------------
//		// ���΢������Ȧ(�Ի���ʾtitle��������ʾ���ݣ���������˵��)
//		UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, appSecret);
//		// ����΢������Ȧ��������
//		CircleShareContent circleMedia = new CircleShareContent();
//		circleMedia.setShareContent("����������ữ�����SDK�����ƶ�Ӧ�ÿ�������罻���?�ܣ�����Ȧ");
//		// ��������Ȧtitle
//		circleMedia.setTitle("������ữ�������-����Ȧ");
//		circleMedia.setShareImage(new UMImage(getApplicationContext(),
//				R.drawable.tomorelogo));
//		circleMedia.setTargetUrl("http://www.baidu.com");
//		mController.setShareMedia(circleMedia);
//		wxCircleHandler.setToCircle(true);
//		wxCircleHandler.addToSocialSDK();
////--------------------------------------------------------------------------------------------------------		
//		// ��Ӷ���
////		SmsHandler smsHandler = new SmsHandler();
////		smsHandler.addToSocialSDK();
////--------------------------------------------------------------------------------------------------------
////		button = (Button) findViewById(R.id.bar_title_bt_mag);
////		button.setOnClickListener(this);
//	}
//	
//	public void onClick(View arg0) {
//		switch (arg0.getId()) {
//		case R.id.bar_title_bt_mag:
//			mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
//					SHARE_MEDIA.DOUBAN);
//			//Ĭ�Ϸ��?ʽ
//			mController.openShare(this, false);
//			break;
//		}
//	}
	
}
