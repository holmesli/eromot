package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.app.tomore.adapters.RestaurantAdapter;
import com.app.tomore.beans.BLRestaurantModel;
import com.app.tomore.beans.CardModel;
import com.app.tomore.beans.ImageAndText;
import com.app.tomore.beans.ImageAndTexts;
import com.app.tomore.net.YellowPageParse;
import com.app.tomore.net.YellowPageRequest;
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
import android.widget.*;

public class RestaurantBLActivity  extends Activity{
	private DialogActivity dialog;
	private ArrayList<BLRestaurantModel> restlist;
	BLRestaurantModel Restaurant= new BLRestaurantModel();
	ListView listveiew;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blrestaurantlist);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		
		new GetData(RestaurantBLActivity.this,1).execute("");
		//ListView listView = (ListView) findViewById(R.id.bianlirestaurant_listview);
		
		/*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (blrest == null) {
					return;
				}
				BLRestaurantModel RestaurantItem = blrest.get(position);
				Object obj = (Object) blrest.get(position);
				if (obj instanceof String) {
					return;
				}
				Intent intent = new Intent(RestaurantBLActivity.this,
						RestaurantBLActivity.class);
				intent.putExtra("blrest", (Serializable) RestaurantItem);
				startActivity(intent);
			}
		});
		*/

	}
	private void BindDataToListView() {
	
		ListView listView = (ListView) findViewById(R.id.bianlirestaurant_listview);
		listView.setAdapter(new RestaurantAdapter(this, restlist, listView));
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
			// TODO Auto-generated method stub
			String result = null;
			YellowPageRequest request = new YellowPageRequest(RestaurantBLActivity.this);
			try {
				String page ="1";
				String limit="5";
				String region="4";
				Log.d("doInBackground", "start request");
				result = request.getRestaurantId(page,limit,region);
				Log.d("doInBackground", "returned");
			}catch (IOException e) {
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
				restlist = new ArrayList<BLRestaurantModel>();
				try {
					restlist = new YellowPageParse().parseRestaurantResponse(result);
					BindDataToListView();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
				if(restlist !=null){
					Intent intent =new Intent(RestaurantBLActivity.this,MyCameraActivity.class);
					intent.putExtra("menuList",(Serializable) restlist);
					// startActivity(intent);
				}
				else{
					// show empty alert
				}
			
			}
		}
	}
	}

		
	  /* private class RestaurantBLAdapter extends ArrayAdapter<BLRestaurantModel>{
		   
		   private ListView listview;
		   
		   public RestaurantBLAdapter(Activity activity, List<BLRestaurantModel> blrest,
				   ListView listview1) {
			   super(activity, 0, blrest);
			   this.listview = listview1;
			   ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(activity));
			   
		   }
		  /* public View getView(int position, View convertView, ViewGroup parent){
			   Activity activity =(Activity) getContext();
			   View rowView = convertView;
			   if(rowView == null){
				   
				   LayoutInflater inflater = activity.getLayoutInflater();
				   rowView = inflater.inflate(R.layout.blrestaurantlist, null);
				   
			   }else{
				   
			   }
			   BLRestaurantModel blrest = getItem(position);
			   final String imageUrl = blrest.getImage();
			   ImageView imageView = (ImageView) rowView.findViewById(R.layout.blrestaurantlist);
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

				return rowView;
			   
			   
		   }*/
		


	
	
