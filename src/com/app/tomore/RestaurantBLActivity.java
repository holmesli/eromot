package com.app.tomore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import com.app.tomore.beans.BLRestaurantModel;
import com.app.tomore.net.YellowPageParse;
import com.app.tomore.net.YellowPageRequest;
import com.app.tomore.utils.AppUtil;
import com.app.tomore.utils.ToastUtils;
import com.google.gson.JsonSyntaxException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class RestaurantBLActivity  extends Activity{
	private DialogActivity dialog;
	private ArrayList<BLRestaurantModel> restlist;
	private DisplayImageOptions otp;
	BLRestaurantModel Restaurant= new BLRestaurantModel();
	ListView listveiew;
	private Activity mContext;
	RestaurantAdapter newsListAdapter;
	private BLRestaurantModel RestaurantItem;
	private String [] regionlist;
	private Spinner spinner;
	private String [] SpinnerList;
	private ArrayList<BLRestaurantModel> FullRestList;
	private HashMap<String, ArrayList<BLRestaurantModel>> FullRestMap;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bianli_restaurant);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		RelativeLayout whole_layout = (RelativeLayout)findViewById(R.id.BLrest);
		TextView header_Text = (TextView) whole_layout.findViewById(R.id.btMeg);
		header_Text.setText(getString(R.string.RestaurantsList));
		otp = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).showImageForEmptyUri(R.drawable.ic_launcher)
				.build();
		listView  = (ListView) findViewById(R.id.bianlirestaurant_listview);
		//LinearLayout whole_layout = (LinearLayout)findViewById(R.id.GeneralBLLayout);
		final Button btnBack = (Button) whole_layout.findViewById(R.id.bar_title_bl_go_back);

		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		new GetData(RestaurantBLActivity.this,1).execute("");
		mContext = this;
		regionlist = new String[]{"downtown","eastYork","northYork","scarborough","markham","mississauga","vaughan","richmondHill","others"};
		SpinnerList = new String[]{"ALL","DOWNTOWN","East York","North York","SCARBOROUGH","MARKHAM","MISSISSAUGA","VAUGHAN","RCHIMONDHILL","OTHERS"};
		spinner = (Spinner)this.findViewById(R.id.spinner);
		otp = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).showImageForEmptyUri(R.drawable.ic_launcher)
				.build();
		ArrayAdapter<String> adpater = new ArrayAdapter<String>(RestaurantBLActivity.this,R.layout.rest_spinner_item_layout, R.id.rest_list_item_text,SpinnerList);
		spinner.setAdapter(adpater);
		spinner.setOnItemSelectedListener(onitemSelectedListener);
	}
	
    private OnItemSelectedListener onitemSelectedListener = new OnItemSelectedListener(){
	    public void onItemSelected(AdapterView<?> parent, View view, int position,long id){
			restlist = new ArrayList<BLRestaurantModel>();
			String item = parent.getItemAtPosition(position).toString();
			if (item.equals("ALL")){
				restlist = FullRestList;
				listView.setAdapter(newsListAdapter);

			}
			else{
				BLRestaurantModel region_Model = new BLRestaurantModel();
				region_Model.setShowRegion(position);
				restlist.add(region_Model);
				restlist.addAll(FullRestMap.get(regionlist[position-1]));
				listView.setAdapter(newsListAdapter);

			}
	      }
	      public void onNothingSelected(AdapterView<?> parent){
	    	  Toast.makeText(RestaurantBLActivity.this, "NothingSelected", Toast.LENGTH_SHORT).show();
	      }
	    };
	private OnItemClickListener itemClickListener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id){
				if(!AppUtil.networkAvailable(mContext)){
					ToastUtils.showToast(mContext, "??????????");
	                  return;
				}
				if (restlist ==null){
					return;
					
				}
				Object obj = (Object) restlist.get(position);
				BLRestaurantModel restModel = restlist.get(position);
				if (obj instanceof String){
					return;
				}
				if(restModel.getShowRegion() != -1)
				{
					return;
				}
				Intent intent = new Intent(RestaurantBLActivity.this,
						RestaurantDetailActivity.class);
				intent.putExtra("restlist", (Serializable) obj);
				startActivity(intent);

			}
		};
	    
	    
	    
	    
	private void BindDataToListView() {
	
		//ListView listView = (ListView) findViewById(R.id.bianlirestaurant_listview);
		newsListAdapter = new RestaurantAdapter();
		listView.setAdapter(newsListAdapter);
		listView.setOnItemClickListener(itemClickListener );
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
				String limit="1000";
				String region="-1";
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
				HashMap<String, ArrayList<BLRestaurantModel>> RestMap = new HashMap<String, ArrayList<BLRestaurantModel>>();
				RestMap.putAll(new YellowPageParse().parseRestaurantResponse(result));
				for (int i = 0;i < regionlist.length;i++){
					BLRestaurantModel region_Model = new BLRestaurantModel();
					region_Model.setShowRegion(i + 1);
					restlist.add(region_Model);
					restlist.addAll(RestMap.get(regionlist[i]));
				}
				FullRestList = restlist;
				FullRestMap = RestMap;
				try {
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
	
	class ViewHolder {
		private TextView Title;
	    private ImageView Image;
	}
	
	public class RestaurantAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return restlist.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return restlist.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			 RestaurantItem = (BLRestaurantModel) getItem(position);
			ViewHolder viewHolder = new ViewHolder();
			if (RestaurantItem.getShowRegion() == -1)
			{
				final String hotlevel= RestaurantItem.getHotLevel();
					if(hotlevel.equals("9")){
						convertView = LayoutInflater.from(mContext).inflate(
								R.layout.hotlv9_restaurant_listview, null);
								ImageView SpecialIcon = (ImageView) convertView.findViewById(R.id.SpeicalIcon);
								ImageView TopIcon = (ImageView) convertView.findViewById(R.id.TopIcon);
								ImageView DiscountIcon = (ImageView) convertView.findViewById(R.id.DiscountIcon);
								if(RestaurantItem.getSpecial().equals("1")){
									SpecialIcon.setVisibility(View.VISIBLE);
								}
								if(RestaurantItem.getDiscount().equals("1")){
									DiscountIcon.setVisibility(View.VISIBLE);
								}
					}
					else{
						convertView = LayoutInflater.from(mContext).inflate(
								R.layout.blrestaurantlist, null);;
					}
					viewHolder.Image= (ImageView) convertView.findViewById(R.id.RestImage);
					ImageLoader.getInstance().displayImage(RestaurantItem.getImage(),
							viewHolder.Image,otp);
					viewHolder.Title = (TextView) convertView.findViewById(R.id.RestText);
					viewHolder.Title.setText(RestaurantItem.getTitle());
	
				return convertView;
			}
			else{
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.restaurant_region, null);
				TextView Title = (TextView) convertView.findViewById(R.id.rest_region_name);
				Title.setText(SpinnerList[RestaurantItem.getShowRegion()]);
				return convertView;
			}
		}
	}
	
}

	
	
