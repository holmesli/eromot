package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;






import java.util.List;
import java.util.concurrent.TimeoutException;





import com.app.tomore.beans.ArticleAdapter;
import com.app.tomore.beans.ArticleModel;
import com.app.tomore.httpclient.AndroidHttpClient;
import com.app.tomore.httpclient.AsyncCallback;
import com.app.tomore.httpclient.HttpResponse;
import com.app.tomore.httpclient.ParameterMap;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainMagActivity extends Activity{
	
	private Intent intent;
	private String mobelNo;
	private ArrayList<ArticleModel> ArticleList;
	private MyBaseAdapter myBaseAdapter;
	//static String path=AppConstants.path;
	private Bitmap bitmap;
	private ArrayList<ArticleModel> articleModel;
	private ArticleModel article;
	private int noteId;
	public ImageLoader imageLoader; 
	private String[] data;

    
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_mag_activity);
		
		
		AndroidHttpClient httpClient = new AndroidHttpClient("http://54.213.167.5/APIV2/");
        httpClient.setMaxRetries(5);
        ParameterMap params = httpClient.newParams()
                .add("articleIssue", "0");
             
        httpClient.post("getArticleByArticleIssue.php", params, new AsyncCallback() {
            
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
			@Override
			public void onComplete(HttpResponse httpResponse) {
				// TODO Auto-generated method stub
				System.out.println(httpResponse.getBodyAsString());
			}
        });
		
		
		
		ListView listView=(ListView)findViewById(R.id.mag_listview);
		myBaseAdapter=new MyBaseAdapter();
		listView.setAdapter(myBaseAdapter);
//		
		
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(ArticleList==null){
					return;
				}
				ArticleModel noetItem = ArticleList.get(position);
				noteId = Integer.parseInt(noetItem.getArticleID());
				Object obj=(Object)ArticleList.get(position);
				//nextPageNote = new MyBaseAdapter();
				//nextPageNote = (ArticleModel)obj;
				//ShowMyDialog(1, null);
				//handler.sendEmptyMessage(0);
				if(obj instanceof String){
					return;
				}
			}
		});
	}
	
	class MyBaseAdapter extends BaseAdapter{
		
		//public ImageLoader imageloader;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ArticleList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(ArticleList==null){
				return convertView;
			}
			final View view=convertView.inflate(MainMagActivity.this, R.layout.mag_listview,null);
			Object obj=ArticleList.get(position);
			ImageView mag_item_image=(ImageView)view.findViewById(R.id.img);
			TextView mag_item_title=(TextView)view.findViewById(R.id.info);
//			if(obj instanceof ArticleModel){
//				
//				final ArticleModel Article=(ArticleModel)obj;
//				String ArticleTitle=Article.getArticleTitle();

//				if(bitmap!=null){
//					news_item_image.setImageBitmap(bitmap);
//				}else{
//					if(cateName.contains("ICT")){
//						news_item_image.setImageResource(R.drawable.computer);
//					}else if(cateName.contains("Business")){
//						news_item_image.setImageResource(R.drawable.business);
//					}else if(cateName.contains("Math")){
//						news_item_image.setImageResource(R.drawable.math);
//					}
//				}
				//mag_item_image.setImageBitmap(Article.getArticleSmallImage());
				mag_item_title.setText(article.getArticleTitle());
				imageLoader.displayImage(data[position], mag_item_image);
				//mag_item_title.setText(Article.getArticleTitle());
//			}else{
//			}
			return view;
			}
	}
	
	public List praseFromJosn(String httpResponse) {
		Type ListType = new TypeToken<ArrayList<ArticleModel>>() {
		}.getType();
		Gson gson = new Gson();
		ArticleList = gson.fromJson(httpResponse, ListType);

		return ArticleList;

	}

}


