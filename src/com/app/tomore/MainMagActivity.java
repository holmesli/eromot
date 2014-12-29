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
import com.app.tomore.net.CardsParse;
import com.app.tomore.net.CardsRequest;
import com.app.tomore.net.MagParse;
import com.app.tomore.net.MagRequest;
import com.app.tomore.net.ToMoreHttpRequest;
import com.app.tomore.net.ToMoreParse;
import com.google.gson.JsonSyntaxException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainMagActivity extends Activity {
	
	private DialogActivity dialog;
	private ArrayList<ArticleModel> articleList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_mag_activity);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		new GetData(MainMagActivity.this, 1).execute("");
		ListView listView = (ListView) findViewById(R.id.mag_listviews);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (articleList == null) {
					return;
				}
				ArticleModel articleItem = articleList.get(position);
				Object obj = (Object) articleList.get(position);
				if (obj instanceof String) {
					return;
				}
				Intent intent = new Intent(MainMagActivity.this,
						MagDetailActivity.class);
				intent.putExtra("articleList", (Serializable) articleItem);
				startActivity(intent);
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
			String result = null;
			MagRequest request = new MagRequest(MainMagActivity.this);
			try {
				//String magId = "";
				Log.d("doInBackground", "start request");
				result = request.getMagById(null);
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
				articleList = new ArrayList<ArticleModel>();
				try {
					articleList = new MagParse().parseArticleResponse(result);
					BindDataToListView();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
				if (articleList != null) {
					Intent intent = new Intent(MainMagActivity.this,
							MyCameraActivity.class); // fake redirect..
					intent.putExtra("cardList", (Serializable) articleList);
					// startActivity(intent);
				} else {
					// show empty alert
				}
			}
		}
	}

	private void BindDataToListView() {
		ListView listView = (ListView) findViewById(R.id.mag_listviews);
		listView.setAdapter(new ArticleAdapter(this, articleList, listView));
	}

	private class ArticleAdapter extends ArrayAdapter<ArticleModel> {

		private ListView listview;

		public ArticleAdapter(Activity activity, List<ArticleModel> articleList,
				ListView listview1) {
			super(activity, 0, articleList);
			this.listview = listview1;
			ImageLoader.getInstance().init(
					ImageLoaderConfiguration.createDefault(activity));
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Activity activity = (Activity) getContext();

			
			View rowView = convertView;
			if (rowView == null) {
				LayoutInflater inflater = activity.getLayoutInflater();
				rowView = inflater.inflate(R.layout.mag_listview, null);
			} else {
				
			}
			ArticleModel articleItem = getItem(position);
			final String imageUrl = articleItem.getArticleSmallImage();
			ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
			imageView.setTag(imageUrl);
			ImageLoader.getInstance().loadImage(imageUrl,
					new SimpleImageLoadingListener() {
						@Override
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
							ImageView imageViewByTag = (ImageView) listview
									.findViewWithTag(imageUrl);
							if (imageViewByTag != null) {
								imageViewByTag.setImageBitmap(loadedImage);
							}
						}
					});

			// Set the text on the TextView
			TextView textViewTitle = (TextView) rowView
					.findViewById(R.id.info);
			textViewTitle.setText(articleItem.getArticleTitle());

			return rowView;
		}

	}
}
