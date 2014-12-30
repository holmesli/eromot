package com.app.tomore.adapters;
import java.util.List;

import com.app.tomore.beans.BLRestaurantModel;
import com.app.tomore.beans.CardModel;
import com.app.tomore.beans.ImageAndText;
import com.app.tomore.beans.MagViewCache;
import com.app.tomore.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.app.Activity;  
import android.graphics.Bitmap;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.ArrayAdapter;  
import android.widget.ImageView;  
import android.widget.ListView;
import android.widget.TextView;  

public class RestaurantAdapter extends ArrayAdapter<BLRestaurantModel>{
	
	private ListView listview;
	
	public RestaurantAdapter(Activity activity, List<BLRestaurantModel> restlist, ListView listview1){
		super(activity, 0, restlist);
	    this.listview = listview1;
	    ImageLoader.getInstance().init(
			ImageLoaderConfiguration.createDefault(activity));
}
	

}
