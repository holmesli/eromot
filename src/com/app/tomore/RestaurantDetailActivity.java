package com.app.tomore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.app.tomore.beans.BLMenuModel;
import com.app.tomore.beans.BLMenuSpecial;
import com.app.tomore.beans.BLRestaurantModel;
import com.app.tomore.beans.GeneralBLModel;
import com.app.tomore.beans.ImageAndText;
import com.app.tomore.RestaurantBLActivity.ViewHolder;
import com.app.tomore.adapters.ImageAndTextListAdapter;
import com.app.tomore.net.*;
import com.app.tomore.utils.AndroidShare;
import com.app.tomore.utils.TouchImageView;
import com.app.tomore.utils.AndroidShare;
import com.app.tomore.utils.TouchImageView;
import com.app.tomore.utils.NoScrollGridview;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.GridView;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;

import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.app.AlertDialog;
import android.app.ProgressDialog;





































import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;



public class RestaurantDetailActivity extends Activity{
	
	private DialogActivity dialog;
	private ArrayList<BLMenuSpecial> MenuItem;
	private ArrayList<BLMenuModel> menulist;
	private TouchImageView MenudetailImage;
	private TextView MenutestView;
	private FrameLayout frame;
	private DisplayImageOptions otp;
	private Activity mContext;
	private ListView listView;
	ViewHolder viewHolder = new ViewHolder();

	private BLRestaurantModel restaurantmodel;
	String restid;
	private BLMenuModel menupicture;
	String resttitle;




	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_detail_layout);
		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		Intent intent = getIntent();
		restaurantmodel = (BLRestaurantModel) intent.getSerializableExtra("restlist");
		restid = restaurantmodel.getAdID();
		resttitle= restaurantmodel.getTitle();
		new GetData(RestaurantDetailActivity.this, 1).execute("");
		otp = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).showImageForEmptyUri(R.drawable.ic_launcher)
				.build();
		mContext = this;
		ImageView Call = (ImageView) getWindow().getDecorView()
				.findViewById(R.id.RestCall);
		Call.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	showPopup();
		    }
		});
		ImageView Dialog = (ImageView) getWindow().getDecorView()
				.findViewById(R.id.contentinfo);
        Dialog.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v){
		    	showdialog();
		    }
        });
		RelativeLayout whole_layout = (RelativeLayout)findViewById(R.id.Restauranttitle);
		TextView header_Text = (TextView) whole_layout.findViewById(R.id.btMeg);
		header_Text.setText(resttitle);
		otp = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).showImageForEmptyUri(R.drawable.ic_launcher)
				.build();
		//LinearLayout whole_layout = (LinearLayout)findViewById(R.id.GeneralBLLayout);
		final Button btnBack = (Button) whole_layout.findViewById(R.id.bar_title_bl_go_back);

		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
      

	}
	private void showbigpicture(int positon){
		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		Context mContext = RestaurantDetailActivity.this;
		


		LayoutInflater inflater = (LayoutInflater) mContext
		.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.restaurant_picturelarge, null);
		ImageView largeimage = (ImageView) layout.findViewById(R.id.MenuImagelargesize);
        
	    ImageLoader.getInstance().displayImage(menulist.get(positon).getItemImage(),
	    		largeimage,otp);
		
		builder = new AlertDialog.Builder(mContext);
		builder.setView(layout);
		alertDialog = builder.create();
		alertDialog.show();
		
		
		
	}

	private void showdialog(){
		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		Context mContext = RestaurantDetailActivity.this;

		LayoutInflater inflater = (LayoutInflater) mContext
		.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.restaurant_content, null);
		TextView text = (TextView) layout.findViewById(R.id.resttitle);
		text.setText(restaurantmodel.getTitle());
		TextView text1 = (TextView) layout.findViewById(R.id.restcontent);
		text1.setText(restaurantmodel.getContent());
		builder = new AlertDialog.Builder(mContext);
		builder.setView(layout);
		alertDialog = builder.create();
		alertDialog.show();
		
		
		
	}

	private void showPopup(){
		String Cancel = getString(R.string.Cancel);
		String Phone1 = restaurantmodel.getPhone();
		List<CharSequence>  cs = new ArrayList<CharSequence>();
		cs.add(Phone1);
		cs.add(Cancel);
    	CharSequence [] options = cs.toArray(new CharSequence[cs.size()]);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.PhoneCall));
		builder.setItems(options, new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface Optiondialog, int which) {
		        if (which == 0){
	        		Intent call = new Intent(Intent.ACTION_DIAL);
	        		call.setData(Uri.parse("tel:"+restaurantmodel.getPhone()));
	        		startActivity(call);
		        }
		    }
		});
		builder.show();
	}


	
	private class GetData extends AsyncTask<String, String, String>{
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
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			//http://54.213.167.5/APIV2/getItemsByRestID.php?restid=48&page=1&limit=10
			String result = null;
			YellowPageRequest request = new YellowPageRequest(RestaurantDetailActivity.this);
			   try{
				Log.d("doInBackground", "start request");
				 result = request.getRestaurantDetail(restid);
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
				//cardList = new ArrayList<CardModel>();
				try {
					MenuItem = new YellowPageParse().parseRestaurantDetailResponse1(result);
					menulist = new YellowPageParse().parseRestaurantDetailResponse(result);

					BindDataToGridView();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
				if (MenuItem != null) {

				} else {
					// show empty alert
				}
			}
	}
	
	}
	class ViewHolder {
		TextView discountdec;
		ImageView dsicountimapge;
		TouchImageView MenudetailImage;
	}
	private void BindDataToGridView(){
		final List<ImageAndText> imageAndTexts = new ArrayList<ImageAndText>();
		

	    for(BLMenuModel c: menulist)
		{	
	    	//imageAndTextList1.add(new ImageAndText(c.getDiscountImage(), c.getItemName()) );
	    	imageAndTexts.add(new ImageAndText(c.getItemImage(), c.getItemName()));

		}
		 NoScrollGridview  gridView = (NoScrollGridview) findViewById(R.id.menugridView);

		gridView.setAdapter(new GridViewAdapter(this, imageAndTexts,
				gridView));
		gridView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		    	showbigpicture(position);
		    }
		});

		

		
		TextView DiscountView = (TextView) getWindow().getDecorView()
				.findViewById(R.id.discountdec);
		ImageView dsicountimapge = (ImageView) findViewById(R.id.discounimage);
		TextView SpecialView = (TextView) getWindow().getDecorView()
				.findViewById(R.id.specialdec);
		ImageView Specialimapge = (ImageView) findViewById(R.id.specialimage);

	    if(MenuItem.isEmpty() == false){

	
	      DiscountView.setText(MenuItem.get(0).getDiscountDes());
	    ImageLoader.getInstance().displayImage(MenuItem.get(0).getDiscountImage(),
	    		dsicountimapge,otp);

		SpecialView.setText(MenuItem.get(0).getSpecialDes());
	    ImageLoader.getInstance().displayImage(MenuItem.get(0).getSpecialImage(),
	    		Specialimapge,otp);

	    }else{
	    	DiscountView.setVisibility(View.GONE);	 
	    	
	    	dsicountimapge.setVisibility(View.GONE);
	    	SpecialView.setVisibility(View.GONE);
	    	Specialimapge.setVisibility(View.GONE);
	    	}

	}
	  
	public class GridViewAdapter extends ArrayAdapter<ImageAndText> {  
	  
	        private GridView gridView;  
	        public GridViewAdapter(Activity activity, List<ImageAndText> imageAndTexts, GridView gridView2) {  
	            super(activity, 0, imageAndTexts);  
	            this.gridView = gridView2;  
	            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(activity));

	        }  
	  
	        public View getView(int position, View convertView, ViewGroup parent) {  
	            Activity activity = (Activity) getContext();  
	  
	            View rowView = convertView;  
	            GridViewCache viewCache;  
	            if (rowView == null) {  
	                LayoutInflater inflater = activity.getLayoutInflater();  
	                rowView = inflater.inflate(R.layout.menugridview, null);  
	                viewCache = new GridViewCache(rowView);  
	                rowView.setTag(viewCache);  
	            } else {  
	                viewCache = (GridViewCache) rowView.getTag();  
	            }  
	            ImageAndText imageAndText = getItem(position);  
	  
	            // Load the image and set it on the ImageView  
	            final String imageUrl = imageAndText.getImageUrl();  
	            ImageView imageView = viewCache.getImageView();  
	            imageView.setTag(imageUrl);  
	           
	            /*ImageLoader.getInstance().loadImage(imageUrl, new SimpleImageLoadingListener() {
	                @Override
	                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
	                	ImageView imageViewByTag = (ImageView) gridView.findViewWithTag(imageUrl);  
	                    if (imageViewByTag != null) {  
	                        imageViewByTag.setImageBitmap(loadedImage); 
	                    }  
	                }
	            }); */
	            
	            ImageLoader.getInstance().displayImage(imageUrl,
	            		imageView);
	            
	            // Set the text on the TextView  
	            TextView textView = viewCache.getTextView();  
	            textView.setText(imageAndText.getText());  
	            return rowView;  
	        }  
	  
	}  
	public class GridViewCache {

	    private View baseView;
	    private TextView textView;
	    private ImageView imageView;

	    public GridViewCache(View baseView) {
	        this.baseView = baseView;
	    }

	    public TextView getTextView() {
	        if (textView == null) {
	            textView = (TextView) baseView.findViewById(R.id.MenuText);
	        }
	        return textView;
	    }

	    public ImageView getImageView() {
	        if (imageView == null) {
	            imageView = (ImageView) baseView.findViewById(R.id.MenuImage);
	        }
	        return imageView;
	    }

}


	
}
	
