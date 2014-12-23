package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.tomore.beans.ArticleAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainMagActivity extends Activity{
	
	TextView resultView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_mag_activity);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		//resultView = (TextView) findViewById(R.id.TestData);
		getData();
	}
	
	public void getData()
	{
		String result = "";
	    //resultView.setText(result);
		GetBL(result);
	}
	
	//get BianLi data
	public void GetBL(String jsonString)
	{
		//load data into a list
		List<String> list = new ArrayList<String>();
		try{
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray personJSONobject = jsonObject.getJSONArray("magdata");
			for(int i =0; i < personJSONobject.length();i++)
			{
				list.add(personJSONobject.getJSONObject(i).toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//load data into a  hash table, then add the hash tables into a list
		List<Hashtable<String,Object>> hashtableDataList = new ArrayList<Hashtable<String,Object>>(); 
		//Make a list for arrayAdapter
		List<ImageAndText> ImageAndTextlist = new ArrayList<ImageAndText>(); 
        for(int i = 0; i < list.size(); i++){
        	try {
				JSONObject DataObject = new JSONObject(list.get(i));
				Hashtable<String,Object> articledata = new Hashtable<String, Object>();
				articledata.put("ArticleTitle",DataObject.getString("ArticleTitle"));
				articledata.put("ArticleContent",DataObject.getString("ArticleContent"));
				articledata.put("ArticleLargeImage",DataObject.getString("ArticleLargeImage"));
				articledata.put("ArticleSmallImage",DataObject.getString("ArticleSmallImage"));
				articledata.put("ImagePosition",DataObject.getString("ImagePosition"));
				articledata.put("ArticleDate",DataObject.getString("ArticleDate"));
				articledata.put("Author",DataObject.getString("Author"));
				articledata.put("ArticleIssue",DataObject.getString("ArticleIssue"));
				articledata.put("DisplayStyle",DataObject.getString("DisplayStyle"));
				articledata.put("ArticleID",DataObject.getString("ArticleID"));
				articledata.put("ArticleVideo",DataObject.getString("ArticleVideo"));
				articledata.put("VideoUrl",DataObject.getString("VideoUrl"));
				articledata.put("TextUrl",DataObject.getString("TextUrl"));
				
				ImageAndTextlist.add(new ImageAndText(DataObject.getString("ArticleSmallImage"),DataObject.getString("ArticleTitle")));
				hashtableDataList.add(articledata);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        ListView listview = (ListView) findViewById(R.id.mag_listviews);
        listview.setAdapter(new ArticleAdapter(this, ImageAndTextlist, listview));
	}
}

	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main_mag_activity);
//
//		
////		AsyncHttpClient client = new AsyncHttpClient();
////		final RequestParams params = new RequestParams();
////		params.put("articleIssue", "1");
////		//params.put("more", "data");
////		client.get("http://54.213.167.5/APIV2/getArticleByArticleIssue.php?",params, new AsyncHttpResponseHandler() {
////			public void onSuccess(String httpResponse) {
////		    	
////		    	try {
////                    JSONObject jObject = new JSONObject(httpResponse);
////                    textview.setText("²ËÆ×Ãû×Ö£º"
////                            + jObject.getJSONArray("title").getJSONObject(2)
////                                    .getString("name"));
////                    Log.i("hck", params.getEntity(null).toString());
////                } catch (Exception e) {
////                }
////		        //System.out.println(response);
//////		        String response = httpResponse.getBodyAsString();
//////		        ArticleList=GetArticle(response);
////		    }
////
////			@Override
////			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
////					Throwable arg3) {
////				// TODO Auto-generated method stub
////				
////			}
////
////			@Override
////			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
////				// TODO Auto-generated method stub
////				
////			}
//
////			@Override
////			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
////				// TODO Auto-generated method stub
////				
////			}
//
////			@Override
////			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
////				// TODO Auto-generated method stub
////				
////			}
////		});
////		
//		AndroidHttpClient httpClient = new AndroidHttpClient("http://54.213.167.5/APIV2/");
//        httpClient.setMaxRetries(5);
//        ParameterMap params = httpClient.newParams()
//                .add("articleIssue", "1");
//             
//        httpClient.post("getArticleByArticleIssue.php", params, new AsyncCallback() {
//            
//            @Override
//            public void onError(Exception e) {
//                e.printStackTrace();
//            }
//			@Override
//			public void onComplete(HttpResponse httpResponse) {
//				// TODO Auto-generated method stub
//				String response = httpResponse.getBodyAsString();
//				//System.out.println(response);
//				articleList=parserRecommend(response);
//			}
//        });
//		
//		
//        //getNoteList();
//		ListView listView=(ListView)findViewById(R.id.mag_listview);
//		myBaseAdapter=new MyBaseAdapter();
//		listView.setAdapter(myBaseAdapter);
//
//	}
//	
//	
//	
//	class MyBaseAdapter extends BaseAdapter{
//		
//		//public ImageLoader imageloader;
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return 1;
//		}
//
//		@Override
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			if(articleList==null){
//				return convertView;
//			}
//			final View view=convertView.inflate(MainMagActivity.this, R.layout.mag_listview,null);
//			//Object obj=ArticleList.get(position);
//			ImageView mag_item_image=(ImageView)view.findViewById(R.id.img);
//			TextView mag_item_title=(TextView)view.findViewById(R.id.info);
//			//article = articleList.get(position);
//				//mag_item_image.setImageBitmap(Article.getArticleSmallImage());
//				//mag_item_title.setText(article.getArticleTitle());
//				//imageLoader.displayImage(data[position], mag_item_image);
//				//mag_item_title.setText(article.getArticleTitle());
////			}else{
////			}
//			return view;
//			}
//	}
//	
//
//	public static ArrayList<ArticleModel> parserRecommend(String content) {
//		ArrayList<ArticleModel> list = new ArrayList<ArticleModel>();
//		ArticleModel article = null;
//		try {
//
//			JSONArray jsonArray = new JSONArray(content);
//			for (int i = 0; i < jsonArray.length(); ++i) {
//				JSONObject o = (JSONObject) jsonArray.get(i);
//				article = new ArticleModel();
//				article.setArticleTitle(o.getString("articleTitle"));
//				article.setArticleID(o.getString("articleID"));
//				article.setArticleContent(o.getString("articleContent"));
//				article.setArticleDate(o.getString("articleDate"));
//				article.setArticleLargeImage(o.getString("articleLargeImage"));
//				article.setArticleIssue(o.getString("articleIssue"));
//				article.setArticleSmallImage(o.getString("articleSmallImage"));
//				article.setArticleVideo(o.getString("articleVideo"));
//				article.setAuthor(o.getString("author"));
//				article.setDisplayStyle(o.getString("displayStyle"));
//				article.setImagePosition(o.getString("imagePosition"));
//				article.setTextUrl(o.getString("textUrl"));
//				article.setVideoUrl(o.getString("videoUrl"));
//
//				list.add(article);
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
//
//}
//
//
