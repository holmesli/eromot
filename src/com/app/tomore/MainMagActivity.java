package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;






import java.util.List;
import java.util.concurrent.TimeoutException;













import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.tomore.beans.ArticleAdapter;
import com.app.tomore.beans.ArticleModel;
import com.app.tomore.httpclient.AndroidHttpClient;
import com.app.tomore.httpclient.AsyncCallback;
import com.app.tomore.httpclient.HttpResponse;
import com.app.tomore.httpclient.ParameterMap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;















import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
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
	private ArrayList<ArticleModel> articleList;
	private MyBaseAdapter myBaseAdapter;
	//static String path=AppConstants.path;
	private Bitmap bitmap;
	//private ArrayList<ArticleModel> articleModel;
	private ArticleModel article;
	private int noteId;
	public ImageLoader imageLoader; 
	private String[] data;
	private TextView textview;
	

    
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_mag_activity);

		
		//textview = (TextView) findViewById(R.id.text2);


//		AsyncHttpClient client = new AsyncHttpClient();
//		final RequestParams params = new RequestParams();
//		params.put("articleIssue", "1");
//		//params.put("more", "data");
//		client.get("http://54.213.167.5/APIV2/getArticleByArticleIssue.php?",params, new AsyncHttpResponseHandler() {
//			public void onSuccess(String httpResponse) {
//		    	
//		    	try {
//                    JSONObject jObject = new JSONObject(httpResponse);
//                    textview.setText("²ËÆ×Ãû×Ö£º"
//                            + jObject.getJSONArray("title").getJSONObject(2)
//                                    .getString("name"));
//                    Log.i("hck", params.getEntity(null).toString());
//                } catch (Exception e) {
//                }
//		        //System.out.println(response);
////		        String response = httpResponse.getBodyAsString();
////		        ArticleList=GetArticle(response);
//		    }
//
//			@Override
//			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//					Throwable arg3) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//				// TODO Auto-generated method stub
//				
//			}

//			@Override
//			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//				// TODO Auto-generated method stub
//				
//			}

//			@Override
//			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		
		AndroidHttpClient httpClient = new AndroidHttpClient("http://54.213.167.5/APIV2/");
        httpClient.setMaxRetries(5);
        ParameterMap params = httpClient.newParams()
                .add("articleIssue", "1");
             
        httpClient.post("getArticleByArticleIssue.php", params, new AsyncCallback() {
            
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
			@Override
			public void onComplete(HttpResponse httpResponse) {
				// TODO Auto-generated method stub
				String response = httpResponse.getBodyAsString();
				//System.out.println(response);
				articleList=parserRecommend(response);
			}
        });
		
		
        //getNoteList();
		ListView listView=(ListView)findViewById(R.id.mag_listview);
		myBaseAdapter=new MyBaseAdapter();
		listView.setAdapter(myBaseAdapter);
//		
		
		
//		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				if(ArticleList==null){
//					return;
//				}
//				ArticleModel noetItem = ArticleList.get(position);
//				noteId = Integer.parseInt(noetItem.getArticleID());
//				Object obj=(Object)ArticleList.get(position);
//				//nextPageNote = new MyBaseAdapter();
//				//nextPageNote = (ArticleModel)obj;
//				//ShowMyDialog(1, null);
//				//handler.sendEmptyMessage(0);
//				if(obj instanceof String){
//					return;
//				}
//			}
//		});
	}
	
	
	
	class MyBaseAdapter extends BaseAdapter{
		
		//public ImageLoader imageloader;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 1;
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
			if(articleList==null){
				return convertView;
			}
			final View view=convertView.inflate(MainMagActivity.this, R.layout.mag_listview,null);
			//Object obj=ArticleList.get(position);
			ImageView mag_item_image=(ImageView)view.findViewById(R.id.img);
			TextView mag_item_title=(TextView)view.findViewById(R.id.info);
			//article = ArticleList.get(position);
				//mag_item_image.setImageBitmap(Article.getArticleSmallImage());
				//mag_item_title.setText(article.getArticleTitle());
				//imageLoader.displayImage(data[position], mag_item_image);
				//mag_item_title.setText(article.getArticleTitle());
//			}else{
//			}
			return view;
			}
	}
	
//	public List praseFromJosn(String response) {
//		Type ListType = new TypeToken<ArrayList<ArticleModel>>() {
//		}.getType();
//		Gson gson = new Gson();
//		ArticleList = gson.fromJson(response, ListType);
//
//		return ArticleList;
//
//	}
//	public ArrayList<ArticleModel> GetArticle(String str)
//			throws JsonSyntaxException {
//		Gson gson = new Gson(); 
//		//JsonReader jsonReader = new JsonReader(null);
//	    JsonParser parser = new JsonParser(); 
//	    JsonArray Jarray = parser.parse(str).getAsJsonArray(); 
//	    //JsonArray Jarray = parser.parse(str).getAsJsonObject().getAsJsonArray("ArticleList");
//	    ArrayList<ArticleModel> lcs = new ArrayList<ArticleModel>(); 
//	    for(JsonElement obj : Jarray ){ 
//	    	ArticleModel cse = gson.fromJson( obj , ArticleModel.class); 
//	        lcs.add(cse); 
//	    }
//
//		return lcs;
//	}
	public static ArrayList<ArticleModel> parserRecommend(String content) {
		ArrayList<ArticleModel> list = new ArrayList<ArticleModel>();
		ArticleModel article = null;
		try {

			JSONArray jsonArray = new JSONArray(content);
			for (int i = 0; i < jsonArray.length(); ++i) {
				JSONObject o = (JSONObject) jsonArray.get(i);
				article = new ArticleModel();
				article.setArticleTitle(o.getString("articleTitle"));
				article.setArticleID(o.getString("articleID"));
				article.setArticleContent(o.getString("articleContent"));
				article.setArticleDate(o.getString("articleDate"));
				article.setArticleLargeImage(o.getString("articleLargeImage"));
				article.setArticleIssue(o.getString("articleIssue"));
				article.setArticleSmallImage(o.getString("articleSmallImage"));
				article.setArticleVideo(o.getString("articleVideo"));
				article.setAuthor(o.getString("author"));
				article.setDisplayStyle(o.getString("displayStyle"));
				article.setImagePosition(o.getString("imagePosition"));
				article.setTextUrl(o.getString("textUrl"));
				article.setVideoUrl(o.getString("videoUrl"));
//				article.setTitle(o.getString("Title"));
//
//				article.setID(o.getString("ID"));
//
//				article.setFirstPicUrl(o.getString("FirstPicUrl"));
				list.add(article);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

}


