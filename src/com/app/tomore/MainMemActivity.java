package com.app.tomore;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.tomore.beans.ArticleModel;
import com.app.tomore.beans.CardModel;
import com.app.tomore.httpclient.AndroidHttpClient;
import com.app.tomore.httpclient.AsyncCallback;
import com.app.tomore.httpclient.HttpResponse;
import com.app.tomore.httpclient.ParameterMap;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainMemActivity extends Activity{
	
	private Intent intent;
	private String mobelNo;
	private ArrayList<CardModel> cardlist;
	private MyBaseAdapter myBaseAdapter;
	private Bitmap bitmap;
	private CardModel cardmodel;
	public ImageLoader imageLoader; 
	private String[] data;
	private TextView textview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_member_activity);
		
		AndroidHttpClient httpClient = new AndroidHttpClient("http://54.213.167.5/APIV2/");
        httpClient.setMaxRetries(5);
        ParameterMap params = httpClient.newParams()
                .add("memberID", "25");
             
        httpClient.post("getCardByMemberID.php", params, new AsyncCallback() {
            
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
			@Override
			public void onComplete(HttpResponse httpResponse) {
				// TODO Auto-generated method stub
				String response = httpResponse.getBodyAsString();
				//System.out.println(response);
				cardlist=parserRecommend(response);
			}
        });
		
		
		ListView listView=(ListView)findViewById(R.id.member_listview);
		myBaseAdapter=new MyBaseAdapter();
		listView.setAdapter(myBaseAdapter);		
		
		
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
			if(cardlist==null){
				return convertView;
			}
			final View view=convertView.inflate(MainMemActivity.this, R.layout.card_listview,null);
			ImageView mem_item_image=(ImageView)view.findViewById(R.id.cardimg);
			TextView mem_item_title=(TextView)view.findViewById(R.id.cardinfo);
			cardmodel = cardlist.get(position);
				//mag_item_image.setImageBitmap(Article.getArticleSmallImage());
				//mag_item_title.setText(article.getArticleTitle());
				//imageLoader.displayImage(data[position], mag_item_image);
			mem_item_title.setText(cardmodel.getCardTitle());
//			}else{
//			}
			return view;
			}
	}
	

	public static ArrayList<CardModel> parserRecommend(String content) {
		ArrayList<CardModel> list = new ArrayList<CardModel>();
		CardModel cardmodel = null;
		try {

			JSONArray jsonArray = new JSONArray(content);
			for (int i = 0; i < jsonArray.length(); ++i) {
				JSONObject o = (JSONObject) jsonArray.get(i);
				cardmodel = new CardModel();
				cardmodel.setCardTitle(o.getString("CardTitle"));
				cardmodel.setCardID(o.getString("CardID"));
				cardmodel.setCardType(o.getString("CardType"));
				cardmodel.setCardBarcode(o.getString("CardBarcode"));
				cardmodel.setCardNumber(o.getString("CardNumber"));
				cardmodel.setCardDes(o.getString("CardDes"));
				cardmodel.setFrontViewImage(o.getString("FrontViewImage"));
				cardmodel.setBackViewImage(o.getString("BackViewImage"));
				cardmodel.setcoupon(o.getString("coupon"));
				

				list.add(cardmodel);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

}

