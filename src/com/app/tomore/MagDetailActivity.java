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
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MagDetailActivity extends Activity {

	private DialogActivity dialog;
	private ArticleModel articleItem;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.magdetail);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		
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
		ImageView detailImage = (ImageView) getWindow().getDecorView()
				.findViewById(R.id.news_image);
		WebView detailWeb = (WebView)findViewById(R.id.news_content_text);

		detailWeb.getSettings().setJavaScriptEnabled(true);

		detailWeb.loadDataWithBaseURL(null,articleItem.getArticleContent(),
	    "text/html", "utf-8",null );
		
		detailTitle.setText(articleItem.getArticleTitle());
		//detailImage.setImageBitmap(articleItem.getArticleLargeImage());
		Picasso.with(MagDetailActivity.this).load(articleItem.getArticleLargeImage()).resize(200, 200).into(detailImage);
		
	}
}
