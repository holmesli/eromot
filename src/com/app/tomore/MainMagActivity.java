package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import com.app.tomore.beans.ArticleModel;
import com.app.tomore.net.MagParse;
import com.app.tomore.net.MagRequest;
import com.google.gson.JsonSyntaxException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.widget.BaseAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainMagActivity extends Activity {

	private DialogActivity dialog;
	private ArrayList<ArticleModel> articleList;
	private Context mContext;

	private DisplayImageOptions otp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.main_mag_activity);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		new GetData(MainMagActivity.this, 1).execute("");
		ListView listView = (ListView) findViewById(R.id.mag_listviews);
		otp = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).showImageForEmptyUri(R.drawable.ic_launcher)
				.build();
		ImageLoader.getInstance().init(
				ImageLoaderConfiguration.createDefault(this));
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
				String magId = "3";
				Log.d("doInBackground", "start request");
				result = request.getMagById(magId);
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
		listView.setAdapter(new ArticleAdapter());
	}

	class ViewHolder {
		TextView textViewTitle;
		ImageView imageView;
	}

	private class ArticleAdapter extends BaseAdapter {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = new ViewHolder();
			ArticleModel articleItem = (ArticleModel) getItem(position);
			final String imageUrl = articleItem.getArticleSmallImage();
			final String imagePosition = articleItem.getImagePosition();
			if (imagePosition.equals("2")) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.mag_listview, null);
			} else {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.mag_largeicon_listview_item, null);
			}
			        
	       
			
			
			
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.img);
			
//			 Bitmap bitmap = BitmapFactory.decodeFile(imageUrl);
//			 viewHolder.imageView.setImageBitmap(getRoundCornerImage(bitmap, 50));
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
	
//	public static Bitmap getRoundCornerImage(Bitmap bitmap, int roundPixels)
//    {
//        //创建一个和原始图片一样大小位图
//        Bitmap roundConcerImage = Bitmap.createBitmap(bitmap.getWidth(),
//                bitmap.getHeight(), Config.ARGB_8888);
//        //创建带有位图roundConcerImage的画布
//        Canvas canvas = new Canvas(roundConcerImage);
//        //创建画笔
//        Paint paint = new Paint();
//        //创建一个和原始图片一样大小的矩形
//        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//        RectF rectF = new RectF(rect);
//        // 去锯齿
//        paint.setAntiAlias(true);
//        //画一个和原始图片一样大小的圆角矩形
//        canvas.drawRoundRect(rectF, roundPixels, roundPixels, paint);
//        //设置相交模式
//        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//        //把图片画到矩形去
//        canvas.drawBitmap(bitmap, null, rect, paint);
//        return roundConcerImage;
//    }
}
